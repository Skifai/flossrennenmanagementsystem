package ch.flossrennen.managementsystem.service.validation;

import ch.flossrennen.managementsystem.util.CheckResult;
import org.jspecify.annotations.NonNull;

public interface Validator<DTO> {
    @NonNull
    CheckResult<DTO> validate(@NonNull DTO dto);
}