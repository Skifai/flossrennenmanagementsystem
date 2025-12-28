package ch.flossrennen.managementsystem.dataaccess;

import ch.flossrennen.managementsystem.util.CheckResult;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Optional;

public interface DTODataAccess<DTO> {
    @NonNull
    List<DTO> findAll();

    @NonNull
    Optional<DTO> findById(@NonNull Long id);

    @NonNull
    CheckResult<Void> deleteById(@NonNull Long id);

    @NonNull
    CheckResult<DTO> save(@NonNull DTO dto);
}
