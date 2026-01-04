package ch.flossrennen.managementsystem.service.validation;

import ch.flossrennen.managementsystem.dataaccess.dto.EinsatzDTO;
import ch.flossrennen.managementsystem.util.CheckResult;
import ch.flossrennen.managementsystem.util.textprovider.TextProvider;
import ch.flossrennen.managementsystem.util.textprovider.TranslationConstants;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

/**
 * Validator für Einsatz-DTOs.
 */
@Component
@RequiredArgsConstructor
public class EinsatzValidator implements Validator<EinsatzDTO> {

    private final TextProvider textProvider;

    @Override
    public @NonNull CheckResult<EinsatzDTO> validate(@NonNull EinsatzDTO einsatzDTO) {
        if (einsatzDTO.startzeit() != null && einsatzDTO.endzeit() != null) {
            if (einsatzDTO.startzeit().isAfter(einsatzDTO.endzeit())) {
                return CheckResult.failure(textProvider.getTranslation(TranslationConstants.VALIDATION_EINSATZ_START_BEFORE_END));
            }
        }
        return CheckResult.success(einsatzDTO);
    }
}