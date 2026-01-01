package ch.flossrennen.managementsystem.service.validation;

import ch.flossrennen.managementsystem.dataaccess.HelferDTODataAccess;
import ch.flossrennen.managementsystem.dataaccess.dto.HelferDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.RessortDTO;
import ch.flossrennen.managementsystem.util.CheckResult;
import ch.flossrennen.managementsystem.util.textprovider.TextProvider;
import ch.flossrennen.managementsystem.util.textprovider.TranslationConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HelferValidatorTest {

    @Mock
    private HelferDTODataAccess helferDTODataAccess;

    @Mock
    private TextProvider textProvider;

    private HelferValidator helferValidator;

    @BeforeEach
    void setUp() {
        helferValidator = new HelferValidator(helferDTODataAccess, textProvider);
    }

    @Test
    void validate_Success() {
        HelferDTO helferDTO = new HelferDTO(null, "John", "Doe", "john.doe@example.com", "123456789", mock(RessortDTO.class));
        when(helferDTODataAccess.findByEmail(helferDTO.email())).thenReturn(Optional.empty());
        when(helferDTODataAccess.findByTelefonnummer(helferDTO.telefonnummer())).thenReturn(Optional.empty());

        CheckResult<HelferDTO> result = helferValidator.validate(helferDTO);

        assertTrue(result.isSuccess());
        assertEquals(helferDTO, result.getData().orElseThrow());
    }

    @Test
    void validate_DuplicateEmail_Fails() {
        HelferDTO helferDTO = new HelferDTO(null, "John", "Doe", "john.doe@example.com", "123456789", mock(RessortDTO.class));
        HelferDTO existingHelfer = mock(HelferDTO.class);
        when(existingHelfer.id()).thenReturn(1L);
        when(helferDTODataAccess.findByEmail(helferDTO.email())).thenReturn(Optional.of(existingHelfer));
        when(textProvider.getTranslation(TranslationConstants.VALIDATION_UNIQUE_EMAIL)).thenReturn("Email already in use");

        CheckResult<HelferDTO> result = helferValidator.validate(helferDTO);

        assertFalse(result.isSuccess());
        assertEquals("Email already in use", result.getMessage());
    }

    @Test
    void validate_DuplicateEmail_SameId_Success() {
        HelferDTO helferDTO = new HelferDTO(1L, "John", "Doe", "john.doe@example.com", "123456789", mock(RessortDTO.class));
        HelferDTO existingHelfer = mock(HelferDTO.class);
        when(existingHelfer.id()).thenReturn(1L);
        when(helferDTODataAccess.findByEmail(helferDTO.email())).thenReturn(Optional.of(existingHelfer));
        when(helferDTODataAccess.findByTelefonnummer(helferDTO.telefonnummer())).thenReturn(Optional.empty());

        CheckResult<HelferDTO> result = helferValidator.validate(helferDTO);

        assertTrue(result.isSuccess());
    }

    @Test
    void validate_DuplicatePhone_Fails() {
        HelferDTO helferDTO = new HelferDTO(null, "John", "Doe", "john.doe@example.com", "123456789", mock(RessortDTO.class));
        HelferDTO existingHelfer = mock(HelferDTO.class);
        when(existingHelfer.id()).thenReturn(1L);
        when(helferDTODataAccess.findByEmail(helferDTO.email())).thenReturn(Optional.empty());
        when(helferDTODataAccess.findByTelefonnummer(helferDTO.telefonnummer())).thenReturn(Optional.of(existingHelfer));
        when(textProvider.getTranslation(TranslationConstants.VALIDATION_UNIQUE_TELEFONNUMMER)).thenReturn("Phone already in use");

        CheckResult<HelferDTO> result = helferValidator.validate(helferDTO);

        assertFalse(result.isSuccess());
        assertEquals("Phone already in use", result.getMessage());
    }

    @Test
    void validate_EmptyEmailAndPhone_Success() {
        HelferDTO helferDTO = new HelferDTO(null, "John", "Doe", "", "", mock(RessortDTO.class));

        CheckResult<HelferDTO> result = helferValidator.validate(helferDTO);

        assertTrue(result.isSuccess());
        verifyNoInteractions(helferDTODataAccess);
    }
}
