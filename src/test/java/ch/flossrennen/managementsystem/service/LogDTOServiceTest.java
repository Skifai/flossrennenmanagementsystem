package ch.flossrennen.managementsystem.service;

import ch.flossrennen.managementsystem.dataaccess.dto.LogDTO;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.LogLevel;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.LogType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class LogDTOServiceTest {

    @Autowired
    private LogDTOService logDTOService;

    @Test
    void findAll() {
        List<LogDTO> all = logDTOService.findAll();
        assertNotNull(all);
    }

    @Test
    void findUniqueValues() {
        List<LogType> types = logDTOService.findUniqueTypes();
        assertNotNull(types);

        List<LogLevel> levels = logDTOService.findUniqueLogLevels();
        assertNotNull(levels);

        List<String> users = logDTOService.findUniqueUsers();
        assertNotNull(users);
    }
}