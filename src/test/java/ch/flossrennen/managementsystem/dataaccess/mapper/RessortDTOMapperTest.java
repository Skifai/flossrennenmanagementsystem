package ch.flossrennen.managementsystem.dataaccess.mapper;

import ch.flossrennen.managementsystem.dataaccess.dto.BenutzerDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.RessortDTO;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Benutzer;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Ressort;
import ch.flossrennen.managementsystem.initialisation.constants.InitialDataConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class RessortDTOMapperTest {

    @Autowired
    private RessortDTOMapper mapper;

    @Test
    void toDTO() {
        Benutzer benutzer = new Benutzer(2L, InitialDataConstants.VERKEHR_USER_VORNAME, InitialDataConstants.VERKEHR_USER_NACHNAME, InitialDataConstants.VERKEHR_USER_TEL, InitialDataConstants.VERKEHR_USER_EMAIL, "hash", InitialDataConstants.ADMIN_ROLLE);

        Ressort ressort = new Ressort(1L, InitialDataConstants.RESSORT_VERKEHR_NAME, InitialDataConstants.RESSORT_VERKEHR_BESCHREIBUNG, InitialDataConstants.RESSORT_VERKEHR_ZUSTAENDIGKEIT, benutzer);
        RessortDTO dto = mapper.toDTO(ressort);

        assertEquals(ressort.getId(), dto.id());
        assertEquals(ressort.getName(), dto.name());
        assertEquals(ressort.getBeschreibung(), dto.beschreibung());
        assertEquals(ressort.getZustaendigkeit(), dto.zustaendigkeit());
        assertEquals(2L, dto.ressortleitung().id());
    }

    @Test
    void toEntity() {
        BenutzerDTO ressortleitung = new BenutzerDTO(2L, InitialDataConstants.RENNLEITUNG_USER_VORNAME, InitialDataConstants.RENNLEITUNG_USER_NACHNAME, InitialDataConstants.RENNLEITUNG_USER_TEL, InitialDataConstants.RENNLEITUNG_USER_EMAIL, "hash", InitialDataConstants.ADMIN_ROLLE);
        RessortDTO dto = new RessortDTO(1L, InitialDataConstants.RESSORT_RENNLEITUNG_NAME, InitialDataConstants.RESSORT_RENNLEITUNG_BESCHREIBUNG, InitialDataConstants.RESSORT_RENNLEITUNG_ZUSTAENDIGKEIT, ressortleitung);
        Ressort ressort = mapper.toEntity(dto);

        assertEquals(dto.id(), ressort.getId());
        assertEquals(dto.name(), ressort.getName());
        assertEquals(dto.beschreibung(), ressort.getBeschreibung());
        assertEquals(dto.zustaendigkeit(), ressort.getZustaendigkeit());
        assertEquals(dto.ressortleitung().id(), ressort.getRessortleitung().getId());
    }
}
