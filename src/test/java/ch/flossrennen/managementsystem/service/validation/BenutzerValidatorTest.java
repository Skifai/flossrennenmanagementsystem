package ch.flossrennen.managementsystem.service.validation;

import ch.flossrennen.managementsystem.dataaccess.BenutzerDTODataAccess;
import ch.flossrennen.managementsystem.dataaccess.dto.BenutzerDTO;
import ch.flossrennen.managementsystem.textprovider.TextProvider;
import ch.flossrennen.managementsystem.util.CheckResult;
import ch.flossrennen.managementsystem.util.TranslationConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class BenutzerValidatorTest {

    private BenutzerValidator validator;

    @Mock
    private BenutzerDTODataAccess benutzerDTODataAccess;

    @Mock
    private TextProvider textProvider;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validator = new BenutzerValidator(benutzerDTODataAccess, textProvider);
    }

    @Test
    void validate_NewUserWithUniqueEmailAndPhone_Success() {
        BenutzerDTO dto = new BenutzerDTO(null, "Hans", "Muster", "0791234567", "hans@muster.com", "pass", null);
        when(benutzerDTODataAccess.findByEmail(dto.email())).thenReturn(Optional.empty());
        when(benutzerDTODataAccess.findByTelefonnummer(dto.telefonnummer())).thenReturn(Optional.empty());

        CheckResult<BenutzerDTO> result = validator.validate(dto);

        assertTrue(result.isSuccess());
        assertEquals(dto, result.getData().orElseThrow());
    }

    @Test
    void validate_ExistingEmail_Failure() {
        BenutzerDTO dto = new BenutzerDTO(null, "Hans", "Muster", "0791234567", "hans@muster.com", "pass", null);
        BenutzerDTO existing = new BenutzerDTO(1L, "Other", "User", null, "hans@muster.com", null, null);
        when(benutzerDTODataAccess.findByEmail(dto.email())).thenReturn(Optional.of(existing));
        when(textProvider.getTranslation(TranslationConstants.VALIDATION_UNIQUE_EMAIL)).thenReturn("Email already in use");

        CheckResult<BenutzerDTO> result = validator.validate(dto);

        assertFalse(result.isSuccess());
        assertEquals("Email already in use", result.getMessage());
    }

    @Test
    void validate_ExistingPhone_Failure() {
        BenutzerDTO dto = new BenutzerDTO(null, "Hans", "Muster", "0791234567", "hans@muster.com", "pass", null);
        BenutzerDTO existing = new BenutzerDTO(1L, "Other", "User", "0791234567", "other@test.com", null, null);
        when(benutzerDTODataAccess.findByEmail(dto.email())).thenReturn(Optional.empty());
        when(benutzerDTODataAccess.findByTelefonnummer(dto.telefonnummer())).thenReturn(Optional.of(existing));
        when(textProvider.getTranslation(TranslationConstants.VALIDATION_UNIQUE_TELEFONNUMMER)).thenReturn("Phone already in use");

        CheckResult<BenutzerDTO> result = validator.validate(dto);

        assertFalse(result.isSuccess());
        assertEquals("Phone already in use", result.getMessage());
    }

    @Test
    void validate_UpdateOwnData_Success() {
        BenutzerDTO dto = new BenutzerDTO(1L, "Hans", "Muster", "0791234567", "hans@muster.com", "pass", null);
        BenutzerDTO existing = new BenutzerDTO(1L, "Hans", "Muster", "0791234567", "hans@muster.com", "pass", null);
        when(benutzerDTODataAccess.findByEmail(dto.email())).thenReturn(Optional.of(existing));
        when(benutzerDTODataAccess.findByTelefonnummer(dto.telefonnummer())).thenReturn(Optional.of(existing));

        CheckResult<BenutzerDTO> result = validator.validate(dto);

        assertTrue(result.isSuccess());
    }
}
