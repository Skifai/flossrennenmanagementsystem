package ch.flossrennen.managementsystem.service;

import ch.flossrennen.managementsystem.dataaccess.EinsatzZuweisungDTODataAccess;
import ch.flossrennen.managementsystem.dataaccess.dto.EinsatzDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.EinsatzZuweisungDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.HelferDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.HelferSelectionDTO;
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
    private DTOService<EinsatzDTO> einsatzDTOService;
    @Mock
    private DTOService<HelferDTO> helferDTOService;
    @Mock
    private TextProvider textProvider;

    @BeforeEach
    void setUp() {
        service = new EinsatzZuweisungDTOService(dataAccess, repository, einsatzRepository, helferRepository, einsatzDTOService, helferDTOService, textProvider);
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

    @Test
    void save_EinsatzAlreadyAbgeschlossen_Fails() {
        Long einsatzId = 1L;
        Long helferId = 1L;
        EinsatzZuweisungDTO dto = new EinsatzZuweisungDTO(null, einsatzId, helferId);

        Einsatz einsatz = new Einsatz();
        einsatz.setStatus(EinsatzStatus.ABGESCHLOSSEN);

        when(einsatzRepository.findById(einsatzId)).thenReturn(Optional.of(einsatz));
        when(textProvider.getTranslation(TranslationConstants.VALIDATION_EINSATZ_FULL)).thenReturn("Einsatz is full");

        CheckResult<EinsatzZuweisungDTO> result = service.save(dto);

        assertFalse(result.isSuccess());
        assertEquals("Einsatz is full", result.getMessage());
        verify(dataAccess, never()).save(any());
    }

    @Test
    void findAllEinsatzForZuweisung_FiltersErstellt() {
        EinsatzDTO einsatz1 = mock(EinsatzDTO.class);
        when(einsatz1.status()).thenReturn(EinsatzStatus.ERSTELLT);
        EinsatzDTO einsatz2 = mock(EinsatzDTO.class);
        when(einsatz2.status()).thenReturn(EinsatzStatus.OFFEN);
        EinsatzDTO einsatz3 = mock(EinsatzDTO.class);
        when(einsatz3.status()).thenReturn(EinsatzStatus.ABGESCHLOSSEN);

        when(einsatzDTOService.findAll()).thenReturn(List.of(einsatz1, einsatz2, einsatz3));

        List<EinsatzDTO> result = service.findAllEinsatzForZuweisung();

        assertEquals(2, result.size());
        assertFalse(result.contains(einsatz1));
        assertTrue(result.contains(einsatz2));
        assertTrue(result.contains(einsatz3));
    }

    @Test
    void getHelferSelection_PartitionsHelfer() {
        Long einsatzId = 1L;
        EinsatzDTO einsatzDTO = new EinsatzDTO(einsatzId, "Test", null, LocalDateTime.now(), LocalDateTime.now().plusHours(1), "Ort", null, 2, EinsatzStatus.OFFEN, null);

        HelferDTO assignedHelfer = new HelferDTO(10L, "Assigned", "User", "a@test.com", "123", null);
        HelferDTO availableHelfer = new HelferDTO(11L, "Available", "User", "b@test.com", "123", null);
        HelferDTO overlappingHelfer = new HelferDTO(12L, "Overlapping", "User", "c@test.com", "123", null);

        when(dataAccess.findByEinsatzId(einsatzId)).thenReturn(List.of(new EinsatzZuweisungDTO(1L, einsatzId, assignedHelfer.id())));
        when(repository.findAll()).thenReturn(List.of()); // Simplified for overlap check if I don't want to mock the whole stream
        // Actually findAllHelferIdsWithOverlappingAssignments is a public method I might want to mock if I was using a spy, but here I'll mock what it calls.

        // Let's mock repository behavior for findAllHelferIdsWithOverlappingAssignments
        Einsatz otherEinsatz = new Einsatz();
        otherEinsatz.setId(2L);
        otherEinsatz.setStartzeit(einsatzDTO.startzeit());
        otherEinsatz.setEndzeit(einsatzDTO.endzeit());

        Helfer helferEntity = new Helfer();
        helferEntity.setId(overlappingHelfer.id());

        EinsatzZuweisung overlappingZuweisung = new EinsatzZuweisung();
        overlappingZuweisung.setEinsatz(otherEinsatz);
        overlappingZuweisung.setHelfer(helferEntity);

        when(repository.findAll()).thenReturn(List.of(overlappingZuweisung));

        when(helferDTOService.findAll()).thenReturn(List.of(assignedHelfer, availableHelfer, overlappingHelfer));

        HelferSelectionDTO result = service.getHelferSelection(einsatzDTO);

        assertEquals(1, result.zugewieseneHelfer().size());
        assertEquals(assignedHelfer.id(), result.zugewieseneHelfer().get(0).id());
        assertEquals(1, result.verfuegbareHelfer().size());
        assertEquals(availableHelfer.id(), result.verfuegbareHelfer().get(0).id());
    }

    @Test
    void getHelferSelection_NullEinsatz_ReturnsEmptyLists() {
        HelferSelectionDTO result = service.getHelferSelection(null);
        assertTrue(result.zugewieseneHelfer().isEmpty());
        assertTrue(result.verfuegbareHelfer().isEmpty());
    }

    @Test
    void getMissingHelferCount_CalculatesCorrectly() {
        Long einsatzId = 1L;
        EinsatzDTO einsatzDTO = new EinsatzDTO(einsatzId, "Test", null, null, null, "Ort", null, 5, EinsatzStatus.OFFEN, null);

        when(dataAccess.findByEinsatzId(einsatzId)).thenReturn(List.of(
                new EinsatzZuweisungDTO(1L, einsatzId, 10L),
                new EinsatzZuweisungDTO(2L, einsatzId, 11L)
        ));

        int missing = service.getMissingHelferCount(einsatzDTO);

        assertEquals(3, missing);
    }

    @Test
    void getMissingHelferCount_NullEinsatz_ReturnsZero() {
        assertEquals(0, service.getMissingHelferCount(null));
    }
}