package ch.flossrennen.managementsystem.service;

import ch.flossrennen.managementsystem.dataaccess.dto.HelferDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.RessortDTO;
import ch.flossrennen.managementsystem.initialisation.constants.InitialDataConstants;
import ch.flossrennen.managementsystem.util.CheckResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class HelferDTOServiceTest {

    @Autowired
    private HelferDTOService helferService;

    @Autowired
    private RessortDTOService ressortService;

    private RessortDTO savedRessort;

    @BeforeEach
    void setUp() {
        savedRessort = ressortService.findById(InitialDataConstants.RESSORT_BAU_ID).orElseThrow();
    }

    @Test
    void findAllAndSave() {
        List<HelferDTO> allBefore = helferService.findAll();
        assertFalse(allBefore.isEmpty(), "Initial helfers should be loaded");

        HelferDTO dto = new HelferDTO(null, "New", "Helfer", "new.helfer@test.ch", "0790000000", savedRessort);
        CheckResult<HelferDTO> result = helferService.save(dto);
        assertTrue(result.isSuccess());
        HelferDTO saved = result.getData().orElseThrow();
        assertNotNull(saved.id());

        List<HelferDTO> allAfter = helferService.findAll();
        assertEquals(allBefore.size() + 1, allAfter.size());
        assertTrue(allAfter.stream().anyMatch(h -> h.id().equals(saved.id())));
    }

    @Test
    void delete() {
        HelferDTO dto = new HelferDTO(null, "Delete", "Me", "delete.me@test.ch", "0770000000", savedRessort);
        CheckResult<HelferDTO> saveResult = helferService.save(dto);
        assertTrue(saveResult.isSuccess());
        HelferDTO saved = saveResult.getData().orElseThrow();

        CheckResult<Void> deleteResult = helferService.delete(saved);
        assertTrue(deleteResult.isSuccess());
        List<HelferDTO> all = helferService.findAll();
        assertFalse(all.stream().anyMatch(h -> h.id().equals(saved.id())));
    }
}
