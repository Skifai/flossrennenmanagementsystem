package ch.flossrennen.managementsystem.service.validation;

import ch.flossrennen.managementsystem.dataaccess.RessortDTODataAccess;
import ch.flossrennen.managementsystem.dataaccess.dto.RessortDTO;
import ch.flossrennen.managementsystem.textprovider.TextProvider;
import ch.flossrennen.managementsystem.util.CheckResult;
import ch.flossrennen.managementsystem.util.TranslationConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RessortValidatorTest {

    @Mock
    private RessortDTODataAccess ressortDTODataAccess;

    @Mock
    private TextProvider textProvider;

    private RessortValidator ressortValidator;

    @BeforeEach
    void setUp() {
        ressortValidator = new RessortValidator(ressortDTODataAccess, textProvider);
    }

    @Test
    void validate_Success() {
        RessortDTO ressortDTO = new RessortDTO(null, "Test Ressort", "Desc", "Zust", null);
        when(ressortDTODataAccess.findByName(ressortDTO.name())).thenReturn(Optional.empty());

        CheckResult<RessortDTO> result = ressortValidator.validate(ressortDTO);

        assertTrue(result.isSuccess());
        assertEquals(ressortDTO, result.getData().orElseThrow());
    }

    @Test
    void validate_DuplicateName_Fails() {
        RessortDTO ressortDTO = new RessortDTO(null, "Test Ressort", "Desc", "Zust", null);
        RessortDTO existingRessort = mock(RessortDTO.class);
        when(existingRessort.id()).thenReturn(1L);
        when(ressortDTODataAccess.findByName(ressortDTO.name())).thenReturn(Optional.of(existingRessort));
        when(textProvider.getTranslation(TranslationConstants.VALIDATION_UNIQUE_NAME)).thenReturn("Name already in use");

        CheckResult<RessortDTO> result = ressortValidator.validate(ressortDTO);

        assertFalse(result.isSuccess());
        assertEquals("Name already in use", result.getMessage());
    }

    @Test
    void validate_DuplicateName_SameId_Success() {
        RessortDTO ressortDTO = new RessortDTO(1L, "Test Ressort", "Desc", "Zust", null);
        RessortDTO existingRessort = mock(RessortDTO.class);
        when(existingRessort.id()).thenReturn(1L);
        when(ressortDTODataAccess.findByName(ressortDTO.name())).thenReturn(Optional.of(existingRessort));

        CheckResult<RessortDTO> result = ressortValidator.validate(ressortDTO);

        assertTrue(result.isSuccess());
    }

    @Test
    void validate_EmptyName_Success() {
        RessortDTO ressortDTO = new RessortDTO(null, "", "Desc", "Zust", null);

        CheckResult<RessortDTO> result = ressortValidator.validate(ressortDTO);

        assertTrue(result.isSuccess());
        verifyNoInteractions(ressortDTODataAccess);
    }
}