package ch.flossrennen.managementsystem.service.validation;

import ch.flossrennen.managementsystem.util.CheckResult;
import org.jspecify.annotations.NonNull;

/**
 * Basis-Interface für Validatoren.
 *
 * @param <DTO> Der Typ des zu validierenden Objekts.
 */
public interface Validator<DTO> {
    /**
     * Validiert das angegebene DTO.
     *
     * @param dto Das zu validierende DTO.
     * @return Ein CheckResult mit dem Ergebnis der Validierung.
     */
    @NonNull
    CheckResult<DTO> validate(@NonNull DTO dto);
}