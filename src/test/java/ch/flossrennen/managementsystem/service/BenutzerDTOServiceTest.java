package ch.flossrennen.managementsystem.service;

import ch.flossrennen.managementsystem.dataaccess.dto.BenutzerDTO;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.BenutzerRolle;
import ch.flossrennen.managementsystem.util.CheckResult;
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
class BenutzerDTOServiceTest {

    @Autowired
    private BenutzerDTOService service;

    @Test
    void save_DuplicateEmail_ReturnsFailure() {
        BenutzerDTO dto1 = new BenutzerDTO(null, "Hans", "Muster", "0791234567", "duplicate@test.ch", "hash", BenutzerRolle.ADMINISTRATOR);
        service.save(dto1);

        BenutzerDTO dto2 = new BenutzerDTO(null, "Peter", "Lustig", "0797654321", "duplicate@test.ch", "hash", BenutzerRolle.RESSORTLEITER);
        CheckResult<BenutzerDTO> result = service.save(dto2);
        assertFalse(result.isSuccess());
        assertEquals("Fehler beim Speichern", result.getMessage());
    }

    @Test
    void findAllAndSave() {
        BenutzerDTO dto = new BenutzerDTO(null, "Hans", "Muster", "0791234567", "hans.muster@test.ch", "hash", BenutzerRolle.ADMINISTRATOR);
        CheckResult<BenutzerDTO> result = service.save(dto);
        assertTrue(result.isSuccess());
        BenutzerDTO saved = result.getData().orElseThrow();
        assertNotNull(saved.id());

        List<BenutzerDTO> all = service.findAll();
        assertTrue(all.stream().anyMatch(b -> b.id().equals(saved.id())));
    }

    @Test
    void delete() {
        BenutzerDTO dto = new BenutzerDTO(null, "Delete", "Me", "0000000000", "delete.me@test.ch", "hash", BenutzerRolle.RESSORTLEITER);
        CheckResult<BenutzerDTO> saveResult = service.save(dto);
        assertTrue(saveResult.isSuccess());
        BenutzerDTO saved = saveResult.getData().orElseThrow();

        CheckResult<Void> deleteResult = service.delete(saved);
        assertTrue(deleteResult.isSuccess());
        List<BenutzerDTO> all = service.findAll();
        assertFalse(all.stream().anyMatch(b -> b.id().equals(saved.id())));
    }
}
