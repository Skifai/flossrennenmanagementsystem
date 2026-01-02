package ch.flossrennen.managementsystem.service.validation;

import ch.flossrennen.managementsystem.dataaccess.RessortDTODataAccess;
import ch.flossrennen.managementsystem.dataaccess.dto.RessortDTO;
import ch.flossrennen.managementsystem.util.CheckResult;
import ch.flossrennen.managementsystem.util.textprovider.TextProvider;
import ch.flossrennen.managementsystem.util.textprovider.TranslationConstants;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RessortValidator implements Validator<RessortDTO> {

    private final RessortDTODataAccess ressortDTODataAccess;
    private final TextProvider textProvider;

    public RessortValidator(RessortDTODataAccess ressortDTODataAccess, TextProvider textProvider) {
        this.ressortDTODataAccess = ressortDTODataAccess;
        this.textProvider = textProvider;
    }

    @Override
    public @NonNull CheckResult<RessortDTO> validate(@NonNull RessortDTO ressortDTO) {
        CheckResult<RessortDTO> nameResult = validateName(ressortDTO);
        if (!nameResult.isSuccess()) {
            return nameResult;
        }

        return CheckResult.success(ressortDTO);
    }

    private CheckResult<RessortDTO> validateName(RessortDTO ressortDTO) {
        if (ressortDTO.name() != null && !ressortDTO.name().isBlank()) {
            Optional<RessortDTO> existingByName = ressortDTODataAccess.findByName(ressortDTO.name());
            if (existingByName.isPresent() && !existingByName.get().id().equals(ressortDTO.id())) {
                return CheckResult.failure(textProvider.getTranslation(TranslationConstants.VALIDATION_UNIQUE_NAME));
            }
        }
        return CheckResult.success(ressortDTO);
    }
}
