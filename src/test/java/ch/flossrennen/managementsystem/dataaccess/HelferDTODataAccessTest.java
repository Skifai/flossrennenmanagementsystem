package ch.flossrennen.managementsystem.dataaccess;

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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class HelferDTODataAccessTest {

    @Autowired
    private HelferDTODataAccess helferDataAccess;

    @Autowired
    private RessortDTODataAccess ressortDataAccess;

    private RessortDTO savedRessort;

    @BeforeEach
    void setUp() {
        savedRessort = ressortDataAccess.findById(InitialDataConstants.RESSORT_VERKEHR_ID).orElseThrow();
    }

    @Test
    void save() {
        HelferDTO dto = new HelferDTO(null, "New", "Helfer", "new.dataaccess@test.ch", "0760000000", savedRessort);
        CheckResult<HelferDTO> result = helferDataAccess.save(dto);
        assertTrue(result.isSuccess());
        HelferDTO saved = result.getData().orElseThrow();
        assertNotNull(saved.id());
        assertEquals("New", saved.vorname());
    }

    @Test
    void findAll() {
        List<HelferDTO> all = helferDataAccess.findAll();
        assertFalse(all.isEmpty(), "Initial helfers should be loaded");
        // InitialData creates 15 helfers
        assertTrue(all.size() >= 15);
    }

    @Test
    void findById() {
        List<HelferDTO> all = helferDataAccess.findAll();
        HelferDTO first = all.get(0);

        Optional<HelferDTO> found = helferDataAccess.findById(first.id());
        assertTrue(found.isPresent());
        assertEquals(first.email(), found.get().email());
    }

    @Test
    void deleteById() {
        HelferDTO dto = new HelferDTO(null, "Delete", "Me", "delete.dataaccess@test.ch", "0770000001", savedRessort);
        CheckResult<HelferDTO> result = helferDataAccess.save(dto);
        assertTrue(result.isSuccess());
        HelferDTO saved = result.getData().orElseThrow();

        helferDataAccess.deleteById(saved.id());
        Optional<HelferDTO> found = helferDataAccess.findById(saved.id());
        assertFalse(found.isPresent());
    }
}
