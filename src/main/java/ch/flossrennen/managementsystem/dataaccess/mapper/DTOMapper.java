package ch.flossrennen.managementsystem.dataaccess.mapper;

import org.jspecify.annotations.NonNull;

public interface DTOMapper<Model, DTO> {
    @NonNull
    DTO toDTO(@NonNull Model entity);

    @NonNull
    Model toEntity(@NonNull DTO dto);
}
