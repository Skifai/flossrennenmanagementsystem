package ch.flossrennen.managementsystem.dataaccess;

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

    @Autowired
    private BenutzerDTODataAccess benutzerDataAccess;

    private RessortDTO savedRessort;

    @BeforeEach
    void setUp() {
        BenutzerDTO benutzer = new BenutzerDTO(null, "Hans", "Muster", "0791234567", "hans.muster@test.ch", "hash", "ADMIN");
        CheckResult<BenutzerDTO> benutzerResult = benutzerDataAccess.save(benutzer);
        assertTrue(benutzerResult.isSuccess());
        BenutzerDTO savedBenutzer = benutzerResult.getData().orElseThrow();

        RessortDTO ressortDto = new RessortDTO(null, "TestRessort", "Test Desc", "Test Zust", savedBenutzer.id());
        CheckResult<RessortDTO> ressortResult = ressortDataAccess.save(ressortDto);
        assertTrue(ressortResult.isSuccess());
        savedRessort = ressortResult.getData().orElseThrow();
    }

    @Test
    void save() {
        HelferDTO dto = new HelferDTO(null, "Hans", "Muster", "hans.muster@test.ch", "0791234567", savedRessort);
        CheckResult<HelferDTO> result = helferDataAccess.save(dto);
        assertTrue(result.isSuccess());
        HelferDTO saved = result.getData().orElseThrow();
        assertNotNull(saved.id());
        assertEquals("Hans", saved.vorname());
    }

    @Test
    void findAll() {
        HelferDTO dto = new HelferDTO(null, "Hans", "Muster", "hans.muster@test.ch", "0791234567", savedRessort);
        helferDataAccess.save(dto);

        List<HelferDTO> all = helferDataAccess.findAll();
        assertFalse(all.isEmpty());
    }

    @Test
    void findById() {
        HelferDTO dto = new HelferDTO(null, "Peter", "Lustig", "peter.lustig@test.ch", "0797654321", savedRessort);
        CheckResult<HelferDTO> result = helferDataAccess.save(dto);
        assertTrue(result.isSuccess());
        HelferDTO saved = result.getData().orElseThrow();

        Optional<HelferDTO> found = helferDataAccess.findById(saved.id());
        assertTrue(found.isPresent());
        assertEquals("Peter", found.get().vorname());
    }

    @Test
    void deleteById() {
        HelferDTO dto = new HelferDTO(null, "Delete", "Me", "delete.me@test.ch", "0000000000", savedRessort);
        CheckResult<HelferDTO> result = helferDataAccess.save(dto);
        assertTrue(result.isSuccess());
        HelferDTO saved = result.getData().orElseThrow();

        helferDataAccess.deleteById(saved.id());
        Optional<HelferDTO> found = helferDataAccess.findById(saved.id());
        assertFalse(found.isPresent());
    }
}
