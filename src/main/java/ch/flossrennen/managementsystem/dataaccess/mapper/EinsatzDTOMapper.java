package ch.flossrennen.managementsystem.dataaccess.mapper;

import ch.flossrennen.managementsystem.dataaccess.dto.EinsatzDTO;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Einsatz;
import org.jspecify.annotations.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        uses = {RessortDTOMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EinsatzDTOMapper extends DTOMapper<Einsatz, EinsatzDTO> {

    @Override
    @NonNull
    EinsatzDTO toDTO(@NonNull Einsatz entity);

    @Override
    @NonNull
    Einsatz toEntity(@NonNull EinsatzDTO dto);

    @Override
    void updateEntity(@NonNull EinsatzDTO dto, @MappingTarget @NonNull Einsatz entity);
}