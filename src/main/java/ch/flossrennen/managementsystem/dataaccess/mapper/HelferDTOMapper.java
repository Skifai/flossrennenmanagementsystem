package ch.flossrennen.managementsystem.dataaccess.mapper;

import ch.flossrennen.managementsystem.dataaccess.dto.HelferDTO;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Helfer;
import org.jspecify.annotations.NonNull;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        uses = {RessortDTOMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface HelferDTOMapper extends DTOMapper<Helfer, HelferDTO> {

    @Override
    @NonNull
    HelferDTO toDTO(@NonNull Helfer entity);

    @Override
    @NonNull
    Helfer toEntity(@NonNull HelferDTO dto);

    @Override
    void updateEntity(@NonNull HelferDTO dto, @MappingTarget @NonNull Helfer entity);
}