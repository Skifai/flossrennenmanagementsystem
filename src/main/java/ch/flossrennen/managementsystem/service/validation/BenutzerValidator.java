package ch.flossrennen.managementsystem.service.validation;

import ch.flossrennen.managementsystem.dataaccess.BenutzerDTODataAccess;
import ch.flossrennen.managementsystem.dataaccess.dto.BenutzerDTO;
import ch.flossrennen.managementsystem.util.CheckResult;
import ch.flossrennen.managementsystem.util.textprovider.TextProvider;
import ch.flossrennen.managementsystem.util.textprovider.TranslationConstants;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Validator für Benutzer-DTOs.
 */
@Component
public class BenutzerValidator implements Validator<BenutzerDTO> {
    private final BenutzerDTODataAccess benutzerDTODataAccess;
    private final TextProvider textProvider;

    /**
     * Erstellt einen neuen BenutzerValidator.
     *
     * @param benutzerDTODataAccess Der Datenzugriff für Benutzer-DTOs.
     * @param textProvider          Der TextProvider für Übersetzungen.
     */
    public BenutzerValidator(BenutzerDTODataAccess benutzerDTODataAccess, TextProvider textProvider) {
        this.benutzerDTODataAccess = benutzerDTODataAccess;
        this.textProvider = textProvider;
    }

    @Override
    public @NonNull CheckResult<BenutzerDTO> validate(@NonNull BenutzerDTO benutzerDTO) {
        CheckResult<BenutzerDTO> emailResult = validateEmail(benutzerDTO);
        if (!emailResult.isSuccess()) {
            return emailResult;
        }

        CheckResult<BenutzerDTO> telefonResult = validateTelefonnummer(benutzerDTO);
        if (!telefonResult.isSuccess()) {
            return telefonResult;
        }

        return CheckResult.success(benutzerDTO);
    }

    private CheckResult<BenutzerDTO> validateEmail(BenutzerDTO benutzerDTO) {
        if (benutzerDTO.email() != null && !benutzerDTO.email().isBlank()) {
            Optional<BenutzerDTO> existingByEmail = benutzerDTODataAccess.findByEmail(benutzerDTO.email());
            if (existingByEmail.isPresent() && !existingByEmail.get().id().equals(benutzerDTO.id())) {
                return CheckResult.failure(textProvider.getTranslation(TranslationConstants.VALIDATION_UNIQUE_EMAIL));
            }
        }
        return CheckResult.success(benutzerDTO);
    }

    private CheckResult<BenutzerDTO> validateTelefonnummer(BenutzerDTO benutzerDTO) {
        if (benutzerDTO.telefonnummer() != null && !benutzerDTO.telefonnummer().isBlank()) {
            Optional<BenutzerDTO> existingByTelefon = benutzerDTODataAccess.findByTelefonnummer(benutzerDTO.telefonnummer());
            if (existingByTelefon.isPresent() && !existingByTelefon.get().id().equals(benutzerDTO.id())) {
                return CheckResult.failure(textProvider.getTranslation(TranslationConstants.VALIDATION_UNIQUE_TELEFONNUMMER));
            }
        }
        return CheckResult.success(benutzerDTO);
    }
}
