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
        BenutzerDTO dto = new BenutzerDTO(null, "Hans", "Muster", "0791234567", "hans.muster@test.ch", "hash", BenutzerRolle.ADMINISTRATOR);
        CheckResult<BenutzerDTO> result = dataAccess.save(dto);
        assertTrue(result.isSuccess());
        BenutzerDTO saved = result.getData().orElseThrow();
        assertNotNull(saved.id());
        assertEquals("Hans", saved.vorname());
    }

    @Test
    void findAll() {
        BenutzerDTO dto = new BenutzerDTO(null, "Hans", "Muster", "0791234567", "hans.muster@test.ch", "hash", BenutzerRolle.ADMINISTRATOR);
        dataAccess.save(dto);

        List<BenutzerDTO> all = dataAccess.findAll();
        assertFalse(all.isEmpty());
    }

    @Test
    void findById() {
        BenutzerDTO dto = new BenutzerDTO(null, "Peter", "Lustig", "0797654321", "peter.lustig@test.ch", "hash", BenutzerRolle.RESSORTLEITER);
        CheckResult<BenutzerDTO> result = dataAccess.save(dto);
        assertTrue(result.isSuccess());
        BenutzerDTO saved = result.getData().orElseThrow();

        Optional<BenutzerDTO> found = dataAccess.findById(saved.id());
        assertTrue(found.isPresent());
        assertEquals("Peter", found.get().vorname());
    }

    @Test
    void deleteById() {
        BenutzerDTO dto = new BenutzerDTO(null, "Delete", "Me", "0000000000", "delete.me@test.ch", "hash", BenutzerRolle.RESSORTLEITER);
        CheckResult<BenutzerDTO> result = dataAccess.save(dto);
        assertTrue(result.isSuccess());
        BenutzerDTO saved = result.getData().orElseThrow();

        dataAccess.deleteById(saved.id());
        Optional<BenutzerDTO> found = dataAccess.findById(saved.id());
        assertFalse(found.isPresent());
    }
}
