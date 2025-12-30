package ch.flossrennen.managementsystem.service;

import ch.flossrennen.managementsystem.dataaccess.dto.BenutzerDTO;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.BenutzerRolle;
import ch.flossrennen.managementsystem.initialisation.constants.InitialDataConstants;
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
        String existingEmail = InitialDataConstants.ADMIN_EMAIL;
        BenutzerDTO dto2 = new BenutzerDTO(null, "Peter", "Lustig", "0797654321", existingEmail, "hash", BenutzerRolle.RESSORTLEITER);
        CheckResult<BenutzerDTO> result = service.save(dto2);
        assertFalse(result.isSuccess(), "Saving with duplicate email should fail");
        assertEquals("E-Mail-Adresse bereits vergeben", result.getMessage());
    }

    @Test
    void findAllAndSave() {
        List<BenutzerDTO> allBefore = service.findAll();
        assertFalse(allBefore.isEmpty());

        BenutzerDTO dto = new BenutzerDTO(null, "New", "User", "0790000000", "new.user@test.ch", "hash", BenutzerRolle.ADMINISTRATOR);
        CheckResult<BenutzerDTO> result = service.save(dto);
        assertTrue(result.isSuccess());
        BenutzerDTO saved = result.getData().orElseThrow();
        assertNotNull(saved.id());

        List<BenutzerDTO> allAfter = service.findAll();
        assertEquals(allBefore.size() + 1, allAfter.size());
        assertTrue(allAfter.stream().anyMatch(b -> b.id().equals(saved.id())));
    }

    @Test
    void delete() {
        BenutzerDTO dto = new BenutzerDTO(null, "Delete", "Me", "0770000000", "delete.me@test.ch", "hash", BenutzerRolle.RESSORTLEITER);
        CheckResult<BenutzerDTO> saveResult = service.save(dto);
        assertTrue(saveResult.isSuccess());
        BenutzerDTO saved = saveResult.getData().orElseThrow();

        CheckResult<Void> deleteResult = service.delete(saved);
        assertTrue(deleteResult.isSuccess());

        List<BenutzerDTO> allAfter = service.findAll();
        assertFalse(allAfter.stream().anyMatch(b -> b.id().equals(saved.id())));
    }
}
