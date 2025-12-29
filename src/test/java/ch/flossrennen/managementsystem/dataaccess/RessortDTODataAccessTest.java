package ch.flossrennen.managementsystem.dataaccess;

import ch.flossrennen.managementsystem.dataaccess.dto.BenutzerDTO;
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
class RessortDTODataAccessTest {

    @Autowired
    private RessortDTODataAccess dataAccess;

    @Autowired
    private BenutzerDTODataAccess benutzerDataAccess;

    private BenutzerDTO savedBenutzer;

    @BeforeEach
    void setUp() {
        savedBenutzer = benutzerDataAccess.findById(InitialDataConstants.ADMIN_ID).orElseThrow();
    }

    @Test
    void save() {
        RessortDTO dto = new RessortDTO(null, "New Ressort", "Test Beschreibung", "Test Zustaendigkeit", savedBenutzer);
        CheckResult<RessortDTO> result = dataAccess.save(dto);
        assertTrue(result.isSuccess());
        RessortDTO saved = result.getData().orElseThrow();
        assertNotNull(saved.id());
        assertEquals("New Ressort", saved.name());
    }

    @Test
    void findAll() {
        List<RessortDTO> all = dataAccess.findAll();
        assertFalse(all.isEmpty(), "Initial ressorts should be loaded");
        // InitialData creates 5 ressorts
        assertTrue(all.size() >= 5);
    }

    @Test
    void findById() {
        List<RessortDTO> all = dataAccess.findAll();
        RessortDTO first = all.getFirst();

        Optional<RessortDTO> found = dataAccess.findById(first.id());
        assertTrue(found.isPresent());
        assertEquals(first.name(), found.get().name());
    }

    @Test
    void deleteById() {
        RessortDTO dto = new RessortDTO(null, "Ressort to Delete", "Desc", "Zust", savedBenutzer);
        CheckResult<RessortDTO> result = dataAccess.save(dto);
        assertTrue(result.isSuccess());
        RessortDTO saved = result.getData().orElseThrow();

        dataAccess.deleteById(saved.id());
        Optional<RessortDTO> found = dataAccess.findById(saved.id());
        assertFalse(found.isPresent());
    }
}
