package ch.flossrennen.managementsystem.dataaccess.mapper;

import ch.flossrennen.managementsystem.dataaccess.dto.BenutzerDTO;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Benutzer;
import org.jspecify.annotations.NonNull;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        uses = {MapperHelper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BenutzerDTOMapper extends DTOMapper<Benutzer, BenutzerDTO> {

    @Override
    @Mapping(target = "password", constant = "")
    @NonNull
    BenutzerDTO toDTO(@NonNull Benutzer benutzer);

    @Override
    @Mapping(target = "passwordhash", source = "password", qualifiedByName = "passwordToHash")
    @NonNull
    Benutzer toEntity(@NonNull BenutzerDTO benutzerDTO);

    @Override
    @Mapping(target = "passwordhash", source = "password", qualifiedByName = "passwordToHash")
    void updateEntity(@NonNull BenutzerDTO dto, @MappingTarget @NonNull Benutzer entity);

    @Condition
    default boolean isNotBlank(String value) {
        return value != null && !value.isBlank();
    }
}
