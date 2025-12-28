package ch.flossrennen.managementsystem.service;

import ch.flossrennen.managementsystem.dataaccess.dto.BenutzerDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.RessortDTO;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.BenutzerRolle;
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
        BenutzerDTO benutzer = new BenutzerDTO(null, "Hans", "Muster", "0791234567", "hans.muster@test.ch", "hash", BenutzerRolle.ADMINISTRATOR);
        CheckResult<BenutzerDTO> result = benutzerService.save(benutzer);
        assertTrue(result.isSuccess());
        savedBenutzer = result.getData().orElseThrow();
    }

    @Test
    void findAllAndSave() {
        RessortDTO dto = new RessortDTO(null, "TestRessort", "Desc", "Zust", savedBenutzer);
        CheckResult<RessortDTO> result = service.save(dto);
        assertTrue(result.isSuccess());
        RessortDTO saved = result.getData().orElseThrow();
        assertNotNull(saved.id());

        List<RessortDTO> all = service.findAll();
        assertTrue(all.stream().anyMatch(r -> r.id().equals(saved.id())));
    }

    @Test
    void delete() {
        RessortDTO dto = new RessortDTO(null, "DeleteMe", "Desc", "Zust", savedBenutzer);
        CheckResult<RessortDTO> saveResult = service.save(dto);
        assertTrue(saveResult.isSuccess());
        RessortDTO saved = saveResult.getData().orElseThrow();

        CheckResult<Void> deleteResult = service.delete(saved);
        assertTrue(deleteResult.isSuccess());
        List<RessortDTO> all = service.findAll();
        assertFalse(all.stream().anyMatch(r -> r.id().equals(saved.id())));
    }
}
