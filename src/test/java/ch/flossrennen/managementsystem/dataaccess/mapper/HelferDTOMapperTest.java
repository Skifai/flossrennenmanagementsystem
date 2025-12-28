package ch.flossrennen.managementsystem.dataaccess.mapper;

import ch.flossrennen.managementsystem.dataaccess.dto.HelferDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.RessortDTO;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Helfer;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Ressort;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HelferDTOMapperTest {

    private final RessortDTOMapper ressortMapper = new RessortDTOMapper();
    private final HelferDTOMapper mapper = new HelferDTOMapper(ressortMapper);

    @Test
    void toDTO() {
        Ressort ressort = new Ressort(1L, "Sicherheit", "Sicherheit auf dem Fluss", "Rettung", 2L);
        Helfer helfer = new Helfer(1L, "Hans", "Muster", "hans.muster@test.ch", "0791234567", ressort);

        HelferDTO dto = mapper.toDTO(helfer);

        assertEquals(helfer.getId(), dto.id());
        assertEquals(helfer.getVorname(), dto.vorname());
        assertEquals(helfer.getNachname(), dto.nachname());
        assertEquals(helfer.getEmail(), dto.email());
        assertEquals(helfer.getTelefonnummer(), dto.telefonnummer());
        assertEquals(helfer.getRessort().getId(), dto.ressort().id());
    }

    @Test
    void toEntity() {
        RessortDTO ressortDto = new RessortDTO(1L, "Sicherheit", "Sicherheit auf dem Fluss", "Rettung", 2L);
        HelferDTO dto = new HelferDTO(1L, "Hans", "Muster", "hans.muster@test.ch", "0791234567", ressortDto);

        Helfer helfer = mapper.toEntity(dto);

        assertEquals(dto.id(), helfer.getId());
        assertEquals(dto.vorname(), helfer.getVorname());
        assertEquals(dto.nachname(), helfer.getNachname());
        assertEquals(dto.email(), helfer.getEmail());
        assertEquals(dto.telefonnummer(), helfer.getTelefonnummer());
        assertEquals(dto.ressort().id(), helfer.getRessort().getId());
    }
}
