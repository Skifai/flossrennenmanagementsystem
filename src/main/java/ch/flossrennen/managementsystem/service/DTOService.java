package ch.flossrennen.managementsystem.service;

import ch.flossrennen.managementsystem.util.CheckResult;
import org.jspecify.annotations.NonNull;

import java.util.List;

public interface DTOService<DTO> {
    @NonNull
    List<DTO> findAll();

    @NonNull
    default CheckResult<DTO> save(@NonNull DTO dto) {
        throw new UnsupportedOperationException("Save not implemented for this service");
    }

    @NonNull
    default CheckResult<Void> delete(@NonNull DTO dto) {
        throw new UnsupportedOperationException("Delete not implemented for this service");
    }
}
