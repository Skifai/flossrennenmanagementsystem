package ch.flossrennen.managementsystem.service.validation;

import ch.flossrennen.managementsystem.dataaccess.dto.EinsatzDTO;
import ch.flossrennen.managementsystem.util.CheckResult;
import ch.flossrennen.managementsystem.util.textprovider.TextProvider;
import ch.flossrennen.managementsystem.util.textprovider.TranslationConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EinsatzValidatorTest {

    private EinsatzValidator validator;

    @Mock
    private TextProvider textProvider;

    @BeforeEach
    void setUp() {
        validator = new EinsatzValidator(textProvider);
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

        String errorMsg = "Die Startzeit muss vor der Endzeit liegen.";
        when(textProvider.getTranslation(TranslationConstants.VALIDATION_EINSATZ_START_BEFORE_END)).thenReturn(errorMsg);

        CheckResult<EinsatzDTO> result = validator.validate(dto);

        assertFalse(result.isSuccess());
        assertEquals(errorMsg, result.getMessage());
    }
}