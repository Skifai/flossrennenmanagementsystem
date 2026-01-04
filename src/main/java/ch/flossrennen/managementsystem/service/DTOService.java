package ch.flossrennen.managementsystem.service;

import ch.flossrennen.managementsystem.util.CheckResult;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * Basis-Interface für DTO-Services, das Standard-CRUD-Operationen definiert.
 *
 * @param <DTO> Der Typ des DTOs, das von diesem Service verwaltet wird.
 */
public interface DTOService<DTO> {
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
     * Speichert das angegebene DTO.
     *
     * @param dto Das zu speichernde DTO.
     * @return Ein CheckResult, das das Ergebnis der Operation und das gespeicherte DTO enthält.
     */
    @NonNull
    default CheckResult<DTO> save(@NonNull DTO dto) {
        throw new UnsupportedOperationException("Save not implemented for this service");
    }

    /**
     * Löscht das angegebene DTO.
     *
     * @param dto Das zu löschende DTO.
     * @return Ein CheckResult, das den Erfolg oder Misserfolg der Operation anzeigt.
     */
    @NonNull
    default CheckResult<Void> delete(@NonNull DTO dto) {
        throw new UnsupportedOperationException("Delete not implemented for this service");
    }
}
