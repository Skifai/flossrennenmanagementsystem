package ch.flossrennen.managementsystem.dataaccess;

import ch.flossrennen.managementsystem.dataaccess.dto.EinsatzDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.RessortDTO;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.EinsatzStatus;
import ch.flossrennen.managementsystem.util.CheckResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class EinsatzDTODataAccessTest {

    @Autowired
    private EinsatzDTODataAccess dataAccess;

    @Autowired
    private RessortDTODataAccess ressortDataAccess;

    private RessortDTO savedRessort;

    @BeforeEach
    void setUp() {
        savedRessort = ressortDataAccess.findAll().getFirst();
    }

    @Test
    void save() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(2);
        EinsatzDTO dto = new EinsatzDTO(null, "New Einsatz", "Test Desc", start, end, "Test Ort", "Mittel", 5, EinsatzStatus.ERSTELLT, savedRessort);

        CheckResult<EinsatzDTO> result = dataAccess.save(dto);
        assertTrue(result.isSuccess());
        EinsatzDTO saved = result.getData().orElseThrow();
        assertNotNull(saved.id());
        assertEquals("New Einsatz", saved.name());
        assertEquals(EinsatzStatus.ERSTELLT, saved.status());
    }

    @Test
    void findAll() {
        List<EinsatzDTO> all = dataAccess.findAll();
        assertFalse(all.isEmpty(), "Initial deployments should be loaded");
        assertTrue(all.size() >= 10);
    }

    @Test
    void findById() {
        List<EinsatzDTO> all = dataAccess.findAll();
        EinsatzDTO first = all.getFirst();

        Optional<EinsatzDTO> found = dataAccess.findById(first.id());
        assertTrue(found.isPresent());
        assertEquals(first.name(), found.get().name());
    }

    @Test
    void deleteById() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(2);
        EinsatzDTO dto = new EinsatzDTO(null, "Einsatz to Delete", "Desc", start, end, "Ort", null, 1, EinsatzStatus.ERSTELLT, savedRessort);

        CheckResult<EinsatzDTO> result = dataAccess.save(dto);
        assertTrue(result.isSuccess());
        EinsatzDTO saved = result.getData().orElseThrow();

        dataAccess.deleteById(saved.id());
        Optional<EinsatzDTO> found = dataAccess.findById(saved.id());
        assertFalse(found.isPresent());
    }
}