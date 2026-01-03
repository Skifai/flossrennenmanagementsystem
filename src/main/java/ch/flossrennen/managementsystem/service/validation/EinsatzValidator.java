package ch.flossrennen.managementsystem.service.validation;

import ch.flossrennen.managementsystem.dataaccess.dto.EinsatzDTO;
import ch.flossrennen.managementsystem.util.CheckResult;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

@Component
public class EinsatzValidator implements Validator<EinsatzDTO> {

    @Override
    public @NonNull CheckResult<EinsatzDTO> validate(@NonNull EinsatzDTO einsatzDTO) {
        if (einsatzDTO.startzeit() != null && einsatzDTO.endzeit() != null) {
            if (einsatzDTO.startzeit().isAfter(einsatzDTO.endzeit())) {
                return CheckResult.failure("Die Startzeit muss vor der Endzeit liegen.");
            }
        }
        return CheckResult.success(einsatzDTO);
    }
}