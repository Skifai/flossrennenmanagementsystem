package ch.flossrennen.managementsystem.dataaccess.mapper;

import org.jspecify.annotations.NonNull;

/**
 * Basis-Interface für Mapper zwischen Entitäten und DTOs.
 *
 * @param <Model> Der Typ der Entität.
 * @param <DTO>   Der Typ des DTOs.
 */
public interface DTOMapper<Model, DTO> {
    /**
     * Konvertiert eine Entität in ein DTO.
     *
     * @param entity Die zu konvertierende Entität.
     * @return Das resultierende DTO.
     */
    @NonNull
    DTO toDTO(@NonNull Model entity);

    /**
     * Konvertiert ein DTO in eine neue Entität.
     *
     * @param dto Das zu konvertierende DTO.
     * @return Die resultierende Entität.
     */
    @NonNull
    Model toEntity(@NonNull DTO dto);

    /**
     * Aktualisiert eine bestehende Entität mit den Daten aus einem DTO.
     *
     * @param dto Das DTO mit den neuen Daten.
     * @param entity Die zu aktualisierende Entität.
     */
    void updateEntity(@NonNull DTO dto, @NonNull Model entity);
}
