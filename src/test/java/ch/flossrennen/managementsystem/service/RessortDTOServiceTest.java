package ch.flossrennen.managementsystem.service;

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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class RessortDTOServiceTest {

    @Autowired
    private RessortDTOService service;

    @Autowired
    private BenutzerDTOService benutzerService;

    private BenutzerDTO savedBenutzer;

    @BeforeEach
    void setUp() {
        List<BenutzerDTO> users = benutzerService.findAll();
        assertFalse(users.isEmpty(), "Initial users should be loaded");
        savedBenutzer = users.get(0);
    }

    @Test
    void findAllAndSave() {
        List<RessortDTO> allBefore = service.findAll();
        assertFalse(allBefore.isEmpty(), "Initial ressorts should be loaded");

        RessortDTO dto = new RessortDTO(null, "New TestRessort", "Desc", "Zust", savedBenutzer);
        CheckResult<RessortDTO> result = service.save(dto);
        assertTrue(result.isSuccess());
        RessortDTO saved = result.getData().orElseThrow();
        assertNotNull(saved.id());

        List<RessortDTO> allAfter = service.findAll();
        assertEquals(allBefore.size() + 1, allAfter.size());
        assertTrue(allAfter.stream().anyMatch(r -> r.id().equals(saved.id())));
    }

    @Test
    void delete() {
        RessortDTO dto = new RessortDTO(null, "Ressort to Delete", "Desc", "Zust", savedBenutzer);
        CheckResult<RessortDTO> saveResult = service.save(dto);
        assertTrue(saveResult.isSuccess());
        RessortDTO saved = saveResult.getData().orElseThrow();

        CheckResult<Void> deleteResult = service.delete(saved);
        assertTrue(deleteResult.isSuccess());
        List<RessortDTO> all = service.findAll();
        assertFalse(all.stream().anyMatch(r -> r.id().equals(saved.id())));
    }
}
