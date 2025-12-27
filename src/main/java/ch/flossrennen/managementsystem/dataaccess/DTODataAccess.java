package ch.flossrennen.managementsystem.dataaccess;

import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Optional;

public interface DTODataAccess<DTO> {
    @NonNull
    List<DTO> findAll();

    @NonNull
    Optional<DTO> findById(@NonNull Long id);

    void deleteById(@NonNull Long id);

    @NonNull
    DTO save(@NonNull DTO dto);
}
