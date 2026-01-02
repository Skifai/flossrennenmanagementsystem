package ch.flossrennen.managementsystem.service.validation;

import ch.flossrennen.managementsystem.dataaccess.HelferDTODataAccess;
import ch.flossrennen.managementsystem.dataaccess.dto.HelferDTO;
import ch.flossrennen.managementsystem.util.CheckResult;
import ch.flossrennen.managementsystem.util.textprovider.TextProvider;
import ch.flossrennen.managementsystem.util.textprovider.TranslationConstants;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class HelferValidator implements Validator<HelferDTO> {
    private final HelferDTODataAccess helferDTODataAccess;
    private final TextProvider textProvider;

    public HelferValidator(HelferDTODataAccess helferDTODataAccess, TextProvider textProvider) {
        this.helferDTODataAccess = helferDTODataAccess;
        this.textProvider = textProvider;
    }

    @Override
    public @NonNull CheckResult<HelferDTO> validate(@NonNull HelferDTO helferDTO) {
        CheckResult<HelferDTO> emailResult = validateEmail(helferDTO);
        if (!emailResult.isSuccess()) {
            return emailResult;
        }

        CheckResult<HelferDTO> telefonResult = validateTelefonnummer(helferDTO);
        if (!telefonResult.isSuccess()) {
            return telefonResult;
        }

        return CheckResult.success(helferDTO);
    }

    private CheckResult<HelferDTO> validateEmail(HelferDTO helferDTO) {
        if (helferDTO.email() != null && !helferDTO.email().isBlank()) {
            Optional<HelferDTO> existingByEmail = helferDTODataAccess.findByEmail(helferDTO.email());
            if (existingByEmail.isPresent() && !existingByEmail.get().id().equals(helferDTO.id())) {
                return CheckResult.failure(textProvider.getTranslation(TranslationConstants.VALIDATION_UNIQUE_EMAIL));
            }
        }
        return CheckResult.success(helferDTO);
    }

    private CheckResult<HelferDTO> validateTelefonnummer(HelferDTO helferDTO) {
        if (helferDTO.telefonnummer() != null && !helferDTO.telefonnummer().isBlank()) {
            Optional<HelferDTO> existingByTelefon = helferDTODataAccess.findByTelefonnummer(helferDTO.telefonnummer());
            if (existingByTelefon.isPresent() && !existingByTelefon.get().id().equals(helferDTO.id())) {
                return CheckResult.failure(textProvider.getTranslation(TranslationConstants.VALIDATION_UNIQUE_TELEFONNUMMER));
            }
        }
        return CheckResult.success(helferDTO);
    }
}
