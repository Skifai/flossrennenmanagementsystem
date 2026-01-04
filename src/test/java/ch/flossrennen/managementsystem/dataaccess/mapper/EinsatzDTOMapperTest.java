package ch.flossrennen.managementsystem.dataaccess.mapper;

import ch.flossrennen.managementsystem.dataaccess.dto.EinsatzDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.RessortDTO;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Benutzer;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Einsatz;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.EinsatzStatus;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Ressort;
import ch.flossrennen.managementsystem.initialisation.constants.InitialDataConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class EinsatzDTOMapperTest {

    @Autowired
    private EinsatzDTOMapper mapper;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Test
    void toDTO() {
        Benutzer benutzer = new Benutzer(2L, InitialDataConstants.VERKEHR_USER_VORNAME, InitialDataConstants.VERKEHR_USER_NACHNAME, InitialDataConstants.VERKEHR_USER_TEL, InitialDataConstants.VERKEHR_USER_EMAIL, "hash", InitialDataConstants.ADMIN_ROLLE);
        Ressort ressort = new Ressort(1L, InitialDataConstants.RESSORT_VERKEHR_NAME, InitialDataConstants.RESSORT_VERKEHR_BESCHREIBUNG, InitialDataConstants.RESSORT_VERKEHR_ZUSTAENDIGKEIT, benutzer);

        LocalDateTime start = LocalDateTime.parse(InitialDataConstants.EINSATZ_DATE + " " + InitialDataConstants.EINSATZ_VERKEHR_1_START, formatter);
        LocalDateTime end = LocalDateTime.parse(InitialDataConstants.EINSATZ_DATE + " " + InitialDataConstants.EINSATZ_VERKEHR_1_END, formatter);

        Einsatz einsatz = new Einsatz(1L, InitialDataConstants.EINSATZ_VERKEHR_1_NAME, InitialDataConstants.EINSATZ_VERKEHR_1_DESC, start, end, InitialDataConstants.EINSATZ_VERKEHR_1_ORT, "Mittel", 2, EinsatzStatus.ERSTELLT, ressort);

        EinsatzDTO dto = mapper.toDTO(einsatz);

        assertEquals(einsatz.getId(), dto.id());
        assertEquals(einsatz.getName(), dto.name());
        assertEquals(einsatz.getBeschreibung(), dto.beschreibung());
        assertEquals(einsatz.getStartzeit(), dto.startzeit());
        assertEquals(einsatz.getEndzeit(), dto.endzeit());
        assertEquals(einsatz.getOrt(), dto.ort());
        assertEquals(einsatz.getEinsatzmittel(), dto.einsatzmittel());
        assertEquals(einsatz.getBenoetigte_helfer(), dto.benoetigte_helfer());
        assertEquals(einsatz.getStatus(), dto.status());
        assertEquals(ressort.getId(), dto.ressort().id());
    }

    @Test
    void toEntity() {
        RessortDTO ressortDTO = new RessortDTO(1L, InitialDataConstants.RESSORT_VERKEHR_NAME, InitialDataConstants.RESSORT_VERKEHR_BESCHREIBUNG, InitialDataConstants.RESSORT_VERKEHR_ZUSTAENDIGKEIT, null);

        LocalDateTime start = LocalDateTime.parse(InitialDataConstants.EINSATZ_DATE + " " + InitialDataConstants.EINSATZ_VERKEHR_2_START, formatter);
        LocalDateTime end = LocalDateTime.parse(InitialDataConstants.EINSATZ_DATE + " " + InitialDataConstants.EINSATZ_VERKEHR_2_END, formatter);

        EinsatzDTO dto = new EinsatzDTO(1L, InitialDataConstants.EINSATZ_VERKEHR_2_NAME, InitialDataConstants.EINSATZ_VERKEHR_2_DESC, start, end, InitialDataConstants.EINSATZ_VERKEHR_2_ORT, "Mittel 2", 3, EinsatzStatus.OFFEN, ressortDTO);

        Einsatz einsatz = mapper.toEntity(dto);

        assertEquals(dto.id(), einsatz.getId());
        assertEquals(dto.name(), einsatz.getName());
        assertEquals(dto.beschreibung(), einsatz.getBeschreibung());
        assertEquals(dto.startzeit(), einsatz.getStartzeit());
        assertEquals(dto.endzeit(), einsatz.getEndzeit());
        assertEquals(dto.ort(), einsatz.getOrt());
        assertEquals(dto.einsatzmittel(), einsatz.getEinsatzmittel());
        assertEquals(dto.benoetigte_helfer(), einsatz.getBenoetigte_helfer());
        assertEquals(dto.status(), einsatz.getStatus());
        assertEquals(dto.ressort().id(), einsatz.getRessort().getId());
    }
}