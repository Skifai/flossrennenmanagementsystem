package ch.flossrennen.managementsystem.dataaccess;

import ch.flossrennen.managementsystem.dataaccess.dto.BenutzerDTO;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.BenutzerRolle;
import ch.flossrennen.managementsystem.util.CheckResult;
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
class BenutzerDTODataAccessTest {

    @Autowired
    private BenutzerDTODataAccess dataAccess;

    @Test
    void save() {
        BenutzerDTO dto = new BenutzerDTO(null, "New", "User", "0761234567", "new.dataaccess@test.ch", "password123", BenutzerRolle.ADMINISTRATOR);
        CheckResult<BenutzerDTO> result = dataAccess.save(dto);
        assertTrue(result.isSuccess());
        BenutzerDTO saved = result.getData().orElseThrow();
        assertNotNull(saved.id());
        assertEquals("New", saved.vorname());
    }

    @Test
    void findAll() {
        List<BenutzerDTO> all = dataAccess.findAll();
        assertFalse(all.isEmpty(), "Initial data should be loaded");
        // 1 Admin + 5 test Benutzer
        assertTrue(all.size() >= 6);
    }

    @Test
    void findById() {
        List<BenutzerDTO> all = dataAccess.findAll();
        BenutzerDTO first = all.get(0);

        Optional<BenutzerDTO> found = dataAccess.findById(first.id());
        assertTrue(found.isPresent());
        assertEquals(first.email(), found.get().email());
    }

    @Test
    void deleteById() {
        // Create a new user to delete
        BenutzerDTO dto = new BenutzerDTO(null, "Delete", "Me", "0771112233", "delete.dataaccess@test.ch", "password", BenutzerRolle.RESSORTLEITER);
        CheckResult<BenutzerDTO> result = dataAccess.save(dto);
        assertTrue(result.isSuccess());
        BenutzerDTO saved = result.getData().orElseThrow();

        dataAccess.deleteById(saved.id());
        Optional<BenutzerDTO> found = dataAccess.findById(saved.id());
        assertFalse(found.isPresent());
    }
}
