package ch.flossrennen.managementsystem.dataaccess.mapper;

import ch.flossrennen.managementsystem.dataaccess.dto.LogDTO;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.LogEntry;
import org.jspecify.annotations.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LogDTOMapper extends DTOMapper<LogEntry, LogDTO> {

    @Override
    @NonNull
    LogDTO toDTO(@NonNull LogEntry entity);

    @Override
    @NonNull
    LogEntry toEntity(@NonNull LogDTO dto);

    @Override
    void updateEntity(@NonNull LogDTO dto, @MappingTarget @NonNull LogEntry entity);
}
