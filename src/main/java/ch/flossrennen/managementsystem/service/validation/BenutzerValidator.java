package ch.flossrennen.managementsystem.service.validation;

import ch.flossrennen.managementsystem.dataaccess.BenutzerDTODataAccess;
import ch.flossrennen.managementsystem.dataaccess.dto.BenutzerDTO;
import ch.flossrennen.managementsystem.textprovider.TextProvider;
import ch.flossrennen.managementsystem.util.CheckResult;
import ch.flossrennen.managementsystem.util.TranslationConstants;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BenutzerValidator implements Validator<BenutzerDTO> {
    private static final Logger log = LoggerFactory.getLogger(BenutzerValidator.class);
    private final BenutzerDTODataAccess benutzerDTODataAccess;
    private final TextProvider textProvider;

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
                log.warn("Validation failed: Email {} is already in use by user {}",
                        benutzerDTO.email(),
                        existingByEmail.get().id());
                return CheckResult.failure(textProvider.getTranslation(TranslationConstants.VALIDATION_UNIQUE_EMAIL));
            }
        }
        return CheckResult.success(benutzerDTO);
    }

    private CheckResult<BenutzerDTO> validateTelefonnummer(BenutzerDTO benutzerDTO) {
        if (benutzerDTO.telefonnummer() != null && !benutzerDTO.telefonnummer().isBlank()) {
            Optional<BenutzerDTO> existingByTelefon = benutzerDTODataAccess.findByTelefonnummer(benutzerDTO.telefonnummer());
            if (existingByTelefon.isPresent() && !existingByTelefon.get().id().equals(benutzerDTO.id())) {
                log.warn("Validation failed: Telefon {} is already in use by user {}",
                        benutzerDTO.telefonnummer(),
                        existingByTelefon.get().id());
                return CheckResult.failure(textProvider.getTranslation(TranslationConstants.VALIDATION_UNIQUE_TELEFONNUMMER));
            }
        }
        return CheckResult.success(benutzerDTO);
    }
}
