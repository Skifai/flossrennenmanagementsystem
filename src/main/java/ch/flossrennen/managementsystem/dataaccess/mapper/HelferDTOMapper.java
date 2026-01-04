package ch.flossrennen.managementsystem.dataaccess.mapper;

import ch.flossrennen.managementsystem.dataaccess.dto.HelferDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.RessortDTO;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Helfer;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Ressort;
import org.jspecify.annotations.NonNull;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring",
        uses = {RessortDTOMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class HelferDTOMapper implements DTOMapper<Helfer, HelferDTO> {

    @Autowired
    protected RessortDTOMapper ressortDTOMapper;

    @Override
    @NonNull
    public abstract HelferDTO toDTO(@NonNull Helfer entity);

    @Override
    @NonNull
    public abstract Helfer toEntity(@NonNull HelferDTO dto);

    @Override
    @Mapping(target = "ressort", source = "ressort", qualifiedByName = "replaceRessort")
    public abstract void updateEntity(@NonNull HelferDTO dto, @MappingTarget @NonNull Helfer entity);

    @Named("replaceRessort")
    protected Ressort replaceRessort(RessortDTO dto) {
        return ressortDTOMapper.toEntity(dto);
    }
}