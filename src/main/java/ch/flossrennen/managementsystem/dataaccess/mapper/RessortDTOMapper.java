package ch.flossrennen.managementsystem.dataaccess.mapper;

import ch.flossrennen.managementsystem.dataaccess.dto.RessortDTO;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Ressort;
import org.jspecify.annotations.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        uses = {BenutzerDTOMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RessortDTOMapper extends DTOMapper<Ressort, RessortDTO> {

    @Override
    @NonNull
    RessortDTO toDTO(@NonNull Ressort entity);

    @Override
    @NonNull
    Ressort toEntity(@NonNull RessortDTO dto);

    @Override
    void updateEntity(@NonNull RessortDTO dto, @MappingTarget @NonNull Ressort entity);
}
