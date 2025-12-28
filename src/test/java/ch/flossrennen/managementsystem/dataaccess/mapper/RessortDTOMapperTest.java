package ch.flossrennen.managementsystem.dataaccess.mapper;

import ch.flossrennen.managementsystem.dataaccess.dto.RessortDTO;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Ressort;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RessortDTOMapperTest {

    private final RessortDTOMapper mapper = new RessortDTOMapper();

    @Test
    void toDTO() {
        Ressort ressort = new Ressort(1L, "Sicherheit", "Sicherheit auf dem Fluss", "Rettung", 2L);
        RessortDTO dto = mapper.toDTO(ressort);

        assertEquals(ressort.getId(), dto.id());
        assertEquals(ressort.getName(), dto.name());
        assertEquals(ressort.getBeschreibung(), dto.beschreibung());
        assertEquals(ressort.getZustaendigkeit(), dto.zustaendigkeit());
        assertEquals(ressort.getRessortleitung(), dto.ressortleitung());
    }

    @Test
    void toEntity() {
        RessortDTO dto = new RessortDTO(1L, "Sicherheit", "Sicherheit auf dem Fluss", "Rettung", 2L);
        Ressort ressort = mapper.toEntity(dto);

        assertEquals(dto.id(), ressort.getId());
        assertEquals(dto.name(), ressort.getName());
        assertEquals(dto.beschreibung(), ressort.getBeschreibung());
        assertEquals(dto.zustaendigkeit(), ressort.getZustaendigkeit());
        assertEquals(dto.ressortleitung(), ressort.getRessortleitung());
    }
}
