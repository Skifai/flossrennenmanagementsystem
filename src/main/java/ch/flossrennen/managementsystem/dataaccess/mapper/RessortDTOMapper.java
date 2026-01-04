package ch.flossrennen.managementsystem.dataaccess.mapper;

import ch.flossrennen.managementsystem.dataaccess.dto.BenutzerDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.RessortDTO;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Benutzer;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Ressort;
import org.jspecify.annotations.NonNull;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring",
        uses = {BenutzerDTOMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class RessortDTOMapper implements DTOMapper<Ressort, RessortDTO> {

    @Autowired
    protected BenutzerDTOMapper benutzerDTOMapper;

    @Override
    @NonNull
    public abstract RessortDTO toDTO(@NonNull Ressort entity);

    @Override
    @NonNull
    public abstract Ressort toEntity(@NonNull RessortDTO dto);

    @Override
    @Mapping(target = "ressortleitung", source = "ressortleitung", qualifiedByName = "replaceRessortleitung")
    public abstract void updateEntity(@NonNull RessortDTO dto, @MappingTarget @NonNull Ressort entity);

    @Named("replaceRessortleitung")
    protected Benutzer replaceRessortleitung(BenutzerDTO dto) {
        return benutzerDTOMapper.toEntity(dto);
    }
}