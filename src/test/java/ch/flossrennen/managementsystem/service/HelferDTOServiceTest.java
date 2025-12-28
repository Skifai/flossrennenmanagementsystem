package ch.flossrennen.managementsystem.service;

import ch.flossrennen.managementsystem.dataaccess.dto.BenutzerDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.HelferDTO;
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
class HelferDTOServiceTest {

    @Autowired
    private HelferDTOService helferService;

    @Autowired
    private RessortDTOService ressortService;

    @Autowired
    private BenutzerDTOService benutzerService;

    private RessortDTO savedRessort;

    @BeforeEach
    void setUp() {
        BenutzerDTO benutzer = new BenutzerDTO(null, "Hans", "Muster", "0791234567", "hans.muster@test.ch", "hash", "ADMIN");
        CheckResult<BenutzerDTO> benutzerResult = benutzerService.save(benutzer);
        assertTrue(benutzerResult.isSuccess());
        BenutzerDTO savedBenutzer = benutzerResult.getData().orElseThrow();

        RessortDTO ressortDto = new RessortDTO(null, "TestRessort", "Desc", "Zust", savedBenutzer.id());
        CheckResult<RessortDTO> ressortResult = ressortService.save(ressortDto);
        assertTrue(ressortResult.isSuccess());
        savedRessort = ressortResult.getData().orElseThrow();
    }

    @Test
    void findAllAndSave() {
        HelferDTO dto = new HelferDTO(null, "Hans", "Muster", "hans.muster@test.ch", "0791234567", savedRessort);
        CheckResult<HelferDTO> result = helferService.save(dto);
        assertTrue(result.isSuccess());
        HelferDTO saved = result.getData().orElseThrow();
        assertNotNull(saved.id());

        List<HelferDTO> all = helferService.findAll();
        assertTrue(all.stream().anyMatch(h -> h.id().equals(saved.id())));
    }

    @Test
    void delete() {
        HelferDTO dto = new HelferDTO(null, "DeleteMe", "Me", "delete.me@test.ch", "0000000000", savedRessort);
        CheckResult<HelferDTO> saveResult = helferService.save(dto);
        assertTrue(saveResult.isSuccess());
        HelferDTO saved = saveResult.getData().orElseThrow();

        CheckResult<Void> deleteResult = helferService.delete(saved);
        assertTrue(deleteResult.isSuccess());
        List<HelferDTO> all = helferService.findAll();
        assertFalse(all.stream().anyMatch(h -> h.id().equals(saved.id())));
    }
}
