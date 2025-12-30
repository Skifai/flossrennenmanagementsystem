package ch.flossrennen.managementsystem.dataaccess.dto;

import ch.flossrennen.managementsystem.dataaccess.persistence.model.BenutzerRolle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DTOTest {

    @Test
    void testBenutzerDTOCreateEmpty() {
        BenutzerDTO dto = BenutzerDTO.createEmptyDTO();
        assertNull(dto.id());
        assertEquals("", dto.vorname());
        assertEquals("", dto.nachname());
        assertEquals("", dto.telefonnummer());
        assertEquals("", dto.email());
        assertEquals("", dto.password());
        assertEquals(BenutzerRolle.KEINE, dto.rolle());
    }

    @Test
    void testHelferDTOCreateEmpty() {
        HelferDTO dto = HelferDTO.createEmptyDTO();
        assertNull(dto.id());
        assertEquals("", dto.vorname());
        assertEquals("", dto.nachname());
        assertEquals("", dto.email());
        assertEquals("", dto.telefonnummer());
        assertNull(dto.ressort());
    }

    @Test
    void testRessortDTOCreateEmpty() {
        RessortDTO dto = RessortDTO.createEmptyDTO();
        assertNull(dto.id());
        assertEquals("", dto.name());
        assertEquals("", dto.beschreibung());
        assertEquals("", dto.zustaendigkeit());
        assertNull(dto.ressortleitung());
    }
}
