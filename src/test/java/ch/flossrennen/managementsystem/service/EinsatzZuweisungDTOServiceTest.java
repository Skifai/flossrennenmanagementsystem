package ch.flossrennen.managementsystem.service;

import ch.flossrennen.managementsystem.dataaccess.EinsatzZuweisungDTODataAccess;
import ch.flossrennen.managementsystem.dataaccess.dto.EinsatzZuweisungDTO;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Einsatz;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.EinsatzStatus;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.EinsatzZuweisung;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Helfer;
import ch.flossrennen.managementsystem.dataaccess.persistence.repository.EinsatzRepository;
import ch.flossrennen.managementsystem.dataaccess.persistence.repository.EinsatzZuweisungRepository;
import ch.flossrennen.managementsystem.dataaccess.persistence.repository.HelferRepository;
import ch.flossrennen.managementsystem.util.CheckResult;
import ch.flossrennen.managementsystem.util.textprovider.TextProvider;
import ch.flossrennen.managementsystem.util.textprovider.TranslationConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class EinsatzZuweisungDTOServiceTest {

    private EinsatzZuweisungDTOService service;

    @Mock
    private EinsatzZuweisungDTODataAccess dataAccess;
    @Mock
    private EinsatzZuweisungRepository repository;
    @Mock
    private EinsatzRepository einsatzRepository;
    @Mock
    private HelferRepository helferRepository;
    @Mock
    private TextProvider textProvider;

    @BeforeEach
    void setUp() {
        service = new EinsatzZuweisungDTOService(dataAccess, repository, einsatzRepository, helferRepository, textProvider);
    }

    @Test
    void save_Success() {
        Long einsatzId = 1L;
        Long helferId = 1L;
        EinsatzZuweisungDTO dto = new EinsatzZuweisungDTO(null, einsatzId, helferId);

        Einsatz einsatz = new Einsatz();
        einsatz.setId(einsatzId);
        einsatz.setBenoetigte_helfer(2);
        einsatz.setStatus(EinsatzStatus.OFFEN);
        einsatz.setStartzeit(LocalDateTime.now());
        einsatz.setEndzeit(LocalDateTime.now().plusHours(1));

        when(einsatzRepository.findById(einsatzId)).thenReturn(Optional.of(einsatz));
        when(repository.findOverlappingZuweisungen(any(), any(), any(), any())).thenReturn(List.of());
        when(dataAccess.save(dto)).thenReturn(Optional.of(new EinsatzZuweisungDTO(1L, einsatzId, helferId)));
        when(repository.findByEinsatzId(einsatzId)).thenReturn(List.of(new EinsatzZuweisung()));

        CheckResult<EinsatzZuweisungDTO> result = service.save(dto);

        assertTrue(result.isSuccess());
        verify(dataAccess).save(dto);
        assertEquals(EinsatzStatus.OFFEN, einsatz.getStatus());
    }

    @Test
    void save_AutoCompletesStatus() {
        Long einsatzId = 1L;
        Long helferId = 1L;
        EinsatzZuweisungDTO dto = new EinsatzZuweisungDTO(null, einsatzId, helferId);

        Einsatz einsatz = new Einsatz();
        einsatz.setId(einsatzId);
        einsatz.setBenoetigte_helfer(1);
        einsatz.setStatus(EinsatzStatus.OFFEN);
        einsatz.setStartzeit(LocalDateTime.now());
        einsatz.setEndzeit(LocalDateTime.now().plusHours(1));

        when(einsatzRepository.findById(einsatzId)).thenReturn(Optional.of(einsatz));
        when(repository.findOverlappingZuweisungen(any(), any(), any(), any())).thenReturn(List.of());
        when(dataAccess.save(dto)).thenReturn(Optional.of(new EinsatzZuweisungDTO(1L, einsatzId, helferId)));
        when(repository.findByEinsatzId(einsatzId)).thenReturn(List.of(new EinsatzZuweisung()));

        CheckResult<EinsatzZuweisungDTO> result = service.save(dto);

        assertTrue(result.isSuccess());
        assertEquals(EinsatzStatus.ABGESCHLOSSEN, einsatz.getStatus());
        verify(einsatzRepository).save(einsatz);
    }

    @Test
    void save_OverlapFails() {
        Long einsatzId = 1L;
        Long helferId = 1L;
        EinsatzZuweisungDTO dto = new EinsatzZuweisungDTO(null, einsatzId, helferId);

        Einsatz einsatz = new Einsatz();
        einsatz.setStartzeit(LocalDateTime.now());
        einsatz.setEndzeit(LocalDateTime.now().plusHours(1));

        Helfer helfer = new Helfer();
        helfer.setVorname("Max");
        helfer.setNachname("Mustermann");

        when(einsatzRepository.findById(einsatzId)).thenReturn(Optional.of(einsatz));
        when(repository.findOverlappingZuweisungen(any(), any(), any(), any())).thenReturn(List.of(new EinsatzZuweisung()));
        when(helferRepository.findById(helferId)).thenReturn(Optional.of(helfer));
        when(textProvider.getTranslation(eq(TranslationConstants.VALIDATION_EINSATZ_HELFER_OVERLAP), anyString())).thenReturn("Overlap error");

        CheckResult<EinsatzZuweisungDTO> result = service.save(dto);

        assertFalse(result.isSuccess());
        assertEquals("Overlap error", result.getMessage());
        verify(dataAccess, never()).save(any());
        verify(textProvider).getTranslation(TranslationConstants.VALIDATION_EINSATZ_HELFER_OVERLAP, "Max Mustermann");
    }
}