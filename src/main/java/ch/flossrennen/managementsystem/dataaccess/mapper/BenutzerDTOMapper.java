package ch.flossrennen.managementsystem.dataaccess.mapper;

import ch.flossrennen.managementsystem.dataaccess.dto.BenutzerDTO;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Benutzer;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

@Component
public class BenutzerDTOMapper implements DTOMapper<Benutzer, BenutzerDTO> {

    @NonNull
    public BenutzerDTO toDTO(@NonNull Benutzer benutzer) {
        return new BenutzerDTO(
                benutzer.getId(),
                benutzer.getVorname(),
                benutzer.getNachname(),
                benutzer.getTelefonnummer(),
                benutzer.getEmail(),
                benutzer.getPasswordhash(),
                benutzer.getRolle());
    }

    @NonNull
    public Benutzer toEntity(@NonNull BenutzerDTO benutzerDTO) {
        return new Benutzer(
                benutzerDTO.id(),
                benutzerDTO.vorname(),
                benutzerDTO.nachname(),
                benutzerDTO.telefonnummer(),
                benutzerDTO.email(),
                benutzerDTO.passwordhash(),
                benutzerDTO.rolle()
        );
    }
}
