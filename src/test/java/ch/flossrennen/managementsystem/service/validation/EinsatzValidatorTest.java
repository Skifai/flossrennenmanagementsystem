package ch.flossrennen.managementsystem.service.validation;

import ch.flossrennen.managementsystem.dataaccess.dto.EinsatzDTO;
import ch.flossrennen.managementsystem.util.CheckResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EinsatzValidatorTest {

    private EinsatzValidator validator;

    @BeforeEach
    void setUp() {
        validator = new EinsatzValidator();
    }

    @Test
    void validate_Success() {
        EinsatzDTO dto = EinsatzDTO.createEmptyDTO();
        LocalDateTime now = LocalDateTime.now();
        dto = new EinsatzDTO(null, "Test", "Desc", now, now.plusHours(1), "Ort", "Mittel", 1, dto.status(), null);

        CheckResult<EinsatzDTO> result = validator.validate(dto);

        assertTrue(result.isSuccess());
    }

    @Test
    void validate_StartTimeAfterEndTime_Fails() {
        EinsatzDTO dto = EinsatzDTO.createEmptyDTO();
        LocalDateTime now = LocalDateTime.now();
        dto = new EinsatzDTO(null, "Test", "Desc", now.plusHours(1), now, "Ort", "Mittel", 1, dto.status(), null);

        CheckResult<EinsatzDTO> result = validator.validate(dto);

        assertFalse(result.isSuccess());
        assertEquals("Die Startzeit muss vor der Endzeit liegen.", result.getMessage());
    }
}