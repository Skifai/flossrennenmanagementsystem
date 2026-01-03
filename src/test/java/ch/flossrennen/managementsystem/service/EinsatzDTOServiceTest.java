package ch.flossrennen.managementsystem.service;

import ch.flossrennen.managementsystem.dataaccess.dto.EinsatzDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.RessortDTO;
import ch.flossrennen.managementsystem.initialisation.constants.InitialDataConstants;
import ch.flossrennen.managementsystem.util.CheckResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class EinsatzDTOServiceTest {

    @Autowired
    private EinsatzDTOService einsatzService;

    @Autowired
    private DTOService<RessortDTO> ressortService;

    private RessortDTO savedRessort;

    @BeforeEach
    void setUp() {
        savedRessort = ressortService.findById(InitialDataConstants.RESSORT_BAU_ID).orElseThrow();
    }

    @Test
    void findAllAndSave() {
        List<EinsatzDTO> allBefore = einsatzService.findAll();
        assertFalse(allBefore.isEmpty(), "Initial einsätze should be loaded");

        EinsatzDTO dto = EinsatzDTO.createEmptyDTO();
        dto = new EinsatzDTO(null, "New Einsatz", "Test Description", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "Test Ort", "Test Mittel", 2, dto.status(), savedRessort);

        CheckResult<EinsatzDTO> result = einsatzService.save(dto);
        assertTrue(result.isSuccess());
        EinsatzDTO saved = result.getData().orElseThrow();
        assertNotNull(saved.id());

        List<EinsatzDTO> allAfter = einsatzService.findAll();
        assertEquals(allBefore.size() + 1, allAfter.size());
    }

    @Test
    void saveInvalidValidation_Fails() {
        EinsatzDTO dto = EinsatzDTO.createEmptyDTO();
        // Start time after end time
        dto = new EinsatzDTO(null, "Invalid", "Desc", LocalDateTime.now().plusHours(2), LocalDateTime.now(), "Ort", "Mittel", 1, dto.status(), savedRessort);

        CheckResult<EinsatzDTO> result = einsatzService.save(dto);
        assertFalse(result.isSuccess());
    }

    @Test
    void findById() {
        List<EinsatzDTO> all = einsatzService.findAll();
        EinsatzDTO first = all.get(0);

        assertTrue(einsatzService.findById(first.id()).isPresent());
    }

    @Test
    void delete() {
        EinsatzDTO dto = EinsatzDTO.createEmptyDTO();
        dto = new EinsatzDTO(null, "To Delete", "Desc", LocalDateTime.now(), LocalDateTime.now().plusHours(1), "Ort", "Mittel", 1, dto.status(), savedRessort);
        EinsatzDTO saved = einsatzService.save(dto).getData().orElseThrow();

        CheckResult<Void> result = einsatzService.delete(saved);
        assertTrue(result.isSuccess());
        assertTrue(einsatzService.findById(saved.id()).isEmpty());
    }
}