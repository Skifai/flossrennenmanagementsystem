package ch.flossrennen.managementsystem.dataaccess;

import ch.flossrennen.managementsystem.dataaccess.dto.BenutzerDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.RessortDTO;
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

    private Long savedBenutzerId;

    @BeforeEach
    void setUp() {
        BenutzerDTO benutzer = new BenutzerDTO(null, "Hans", "Muster", "0791234567", "hans.muster@test.ch", "hash", "ADMIN");
        CheckResult<BenutzerDTO> result = benutzerDataAccess.save(benutzer);
        assertTrue(result.isSuccess());
        savedBenutzerId = result.getData().orElseThrow().id();
    }

    @Test
    void save() {
        RessortDTO dto = new RessortDTO(null, "TestRessort", "Test Beschreibung", "Test Zustaendigkeit", savedBenutzerId);
        CheckResult<RessortDTO> result = dataAccess.save(dto);
        assertTrue(result.isSuccess());
        RessortDTO saved = result.getData().orElseThrow();
        assertNotNull(saved.id());
        assertEquals("TestRessort", saved.name());
    }

    @Test
    void findAll() {
        RessortDTO dto = new RessortDTO(null, "TestRessort", "Test Beschreibung", "Test Zustaendigkeit", savedBenutzerId);
        dataAccess.save(dto);

        List<RessortDTO> all = dataAccess.findAll();
        assertFalse(all.isEmpty());
    }

    @Test
    void findById() {
        RessortDTO dto = new RessortDTO(null, "FindMe", "Desc", "Zust", savedBenutzerId);
        CheckResult<RessortDTO> result = dataAccess.save(dto);
        assertTrue(result.isSuccess());
        RessortDTO saved = result.getData().orElseThrow();

        Optional<RessortDTO> found = dataAccess.findById(saved.id());
        assertTrue(found.isPresent());
        assertEquals("FindMe", found.get().name());
    }

    @Test
    void deleteById() {
        RessortDTO dto = new RessortDTO(null, "DeleteMe", "Desc", "Zust", savedBenutzerId);
        CheckResult<RessortDTO> result = dataAccess.save(dto);
        assertTrue(result.isSuccess());
        RessortDTO saved = result.getData().orElseThrow();

        dataAccess.deleteById(saved.id());
        Optional<RessortDTO> found = dataAccess.findById(saved.id());
        assertFalse(found.isPresent());
    }
}
