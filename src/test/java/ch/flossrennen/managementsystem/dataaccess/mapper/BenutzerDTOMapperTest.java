package ch.flossrennen.managementsystem.dataaccess.mapper;

import ch.flossrennen.managementsystem.dataaccess.dto.BenutzerDTO;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Benutzer;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.BenutzerRolle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BenutzerDTOMapperTest {

    private final BenutzerDTOMapper mapper = new BenutzerDTOMapper();

    @Test
    void toDTO() {
        Benutzer benutzer = new Benutzer(1L, "Hans", "Muster", "0791234567", "hans.muster@test.ch", "hash", BenutzerRolle.ADMINISTRATOR);
        BenutzerDTO dto = mapper.toDTO(benutzer);

        assertEquals(benutzer.getId(), dto.id());
        assertEquals(benutzer.getVorname(), dto.vorname());
        assertEquals(benutzer.getNachname(), dto.nachname());
        assertEquals(benutzer.getTelefonnummer(), dto.telefonnummer());
        assertEquals(benutzer.getEmail(), dto.email());
        assertEquals(benutzer.getPasswordhash(), dto.passwordhash());
        assertEquals(benutzer.getRolle(), dto.rolle());
    }

    @Test
    void toEntity() {
        BenutzerDTO dto = new BenutzerDTO(1L, "Hans", "Muster", "0791234567", "hans.muster@test.ch", "hash", BenutzerRolle.ADMINISTRATOR);
        Benutzer benutzer = mapper.toEntity(dto);

        assertEquals(dto.id(), benutzer.getId());
        assertEquals(dto.vorname(), benutzer.getVorname());
        assertEquals(dto.nachname(), benutzer.getNachname());
        assertEquals(dto.telefonnummer(), benutzer.getTelefonnummer());
        assertEquals(dto.email(), benutzer.getEmail());
        assertEquals(dto.passwordhash(), benutzer.getPasswordhash());
        assertEquals(dto.rolle(), benutzer.getRolle());
    }
}
