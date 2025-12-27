package ch.flossrennen.managementsystem.service;

import org.jspecify.annotations.NonNull;

import java.util.List;

public interface DTOService<DTO> {
    @NonNull
    List<DTO> findAll();

    @NonNull
    default DTO save(@NonNull DTO dto) {
        throw new UnsupportedOperationException("Save not implemented for this service");
    }

    default void delete(@NonNull DTO dto) {
        throw new UnsupportedOperationException("Delete not implemented for this service");
    }
}
