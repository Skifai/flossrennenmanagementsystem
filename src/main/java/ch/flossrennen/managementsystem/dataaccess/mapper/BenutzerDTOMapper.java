package ch.flossrennen.managementsystem.dataaccess.mapper;

import ch.flossrennen.managementsystem.dataaccess.dto.BenutzerDTO;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Benutzer;
import org.jspecify.annotations.NonNull;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class BenutzerDTOMapper implements DTOMapper<Benutzer, BenutzerDTO> {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Override
    @Mapping(target = "password", constant = "")
    @NonNull
    public abstract BenutzerDTO toDTO(@NonNull Benutzer benutzer);

    @Override
    @Mapping(target = "passwordhash", source = "password", qualifiedByName = "passwordToHash")
    @NonNull
    public abstract Benutzer toEntity(@NonNull BenutzerDTO benutzerDTO);

    @Override
    @Mapping(target = "passwordhash", source = "password", qualifiedByName = "passwordToHash")
    public abstract void updateEntity(@NonNull BenutzerDTO dto, @MappingTarget @NonNull Benutzer entity);

    @Named("passwordToHash")
    protected String passwordToHash(String password) {
        if (password != null && !password.isBlank()) {
            return passwordEncoder.encode(password);
        }
        return null;
    }

    @Condition
    protected boolean isNotBlank(String value) {
        return value != null && !value.isBlank();
    }
}
