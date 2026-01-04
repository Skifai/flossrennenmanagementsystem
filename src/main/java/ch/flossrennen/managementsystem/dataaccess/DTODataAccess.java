package ch.flossrennen.managementsystem.dataaccess;

import ch.flossrennen.managementsystem.util.CheckResult;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * Basis-Interface für den Datenzugriff mit DTOs.
 *
 * @param <DTO> Der Typ des DTOs.
 */
public interface DTODataAccess<DTO> {
    /**
     * Findet alle Instanzen des DTO-Typs.
     *
     * @return Eine Liste aller gefundenen DTOs.
     */
    @NonNull
    List<DTO> findAll();

    /**
     * Findet ein DTO anhand seiner ID.
     *
     * @param id Die ID des gesuchten DTOs.
     * @return Ein Optional, das das DTO enthält, falls es gefunden wurde.
     */
    @NonNull
    Optional<DTO> findById(@NonNull Long id);

    /**
     * Löscht ein DTO anhand seiner ID.
     *
     * @param id Die ID des zu löschenden DTOs.
     * @return Ein CheckResult, das den Erfolg oder Misserfolg der Operation anzeigt.
     */
    @NonNull
    CheckResult<Void> deleteById(@NonNull Long id);

    /**
     * Speichert das angegebene DTO.
     *
     * @param dto Das zu speichernde DTO.
     * @return Ein CheckResult, das das Ergebnis der Operation und das gespeicherte DTO enthält.
     */
    @NonNull
    CheckResult<DTO> save(@NonNull DTO dto);
}
