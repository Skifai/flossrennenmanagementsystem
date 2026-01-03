package ch.flossrennen.managementsystem.initialisation;

import ch.flossrennen.managementsystem.dataaccess.BenutzerDTODataAccess;
import ch.flossrennen.managementsystem.dataaccess.EinsatzDTODataAccess;
import ch.flossrennen.managementsystem.dataaccess.HelferDTODataAccess;
import ch.flossrennen.managementsystem.dataaccess.RessortDTODataAccess;
import ch.flossrennen.managementsystem.dataaccess.dto.BenutzerDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.EinsatzDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.HelferDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.RessortDTO;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.BenutzerRolle;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.EinsatzStatus;
import ch.flossrennen.managementsystem.initialisation.constants.InitialDataConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
//@Profile({"dev", "test"})
@Order(2)
@RequiredArgsConstructor
public class InitialData implements CommandLineRunner {

    private final BenutzerDTODataAccess benutzerDTODataAccess;
    private final RessortDTODataAccess ressortDTODataAccess;
    private final HelferDTODataAccess helferDTODataAccess;
    private final EinsatzDTODataAccess einsatzDTODataAccess;

    @Override
    public void run(String... args) throws Exception {
        if (benutzerDTODataAccess.findAll().size() > 1 || !ressortDTODataAccess.findAll().isEmpty() || !helferDTODataAccess.findAll().isEmpty() || !einsatzDTODataAccess.findAll().isEmpty()) {
            return;
        }

        // Benutzer / Ressortleiter
        BenutzerDTO u1 = benutzerDTODataAccess.save(
                new BenutzerDTO(
                        null,
                        InitialDataConstants.VERKEHR_USER_VORNAME,
                        InitialDataConstants.VERKEHR_USER_NACHNAME,
                        InitialDataConstants.VERKEHR_USER_TEL,
                        InitialDataConstants.VERKEHR_USER_EMAIL,
                        InitialDataConstants.VERKEHR_USER_PW,
                        BenutzerRolle.RESSORTLEITER)).getData().orElseThrow();

        BenutzerDTO u2 = benutzerDTODataAccess.save(
                new BenutzerDTO(
                        null,
                        InitialDataConstants.RENNLEITUNG_USER_VORNAME,
                        InitialDataConstants.RENNLEITUNG_USER_NACHNAME,
                        InitialDataConstants.RENNLEITUNG_USER_TEL,
                        InitialDataConstants.RENNLEITUNG_USER_EMAIL,
                        InitialDataConstants.RENNLEITUNG_USER_PW,
                        BenutzerRolle.RESSORTLEITER)).getData().orElseThrow();

        BenutzerDTO u3 = benutzerDTODataAccess.save(
                new BenutzerDTO(
                        null,
                        InitialDataConstants.FESTWIRTSCHAFT_USER_VORNAME,
                        InitialDataConstants.FESTWIRTSCHAFT_USER_NACHNAME,
                        InitialDataConstants.FESTWIRTSCHAFT_USER_TEL,
                        InitialDataConstants.FESTWIRTSCHAFT_USER_EMAIL,
                        InitialDataConstants.FESTWIRTSCHAFT_USER_PW,
                        BenutzerRolle.RESSORTLEITER)).getData().orElseThrow();

        BenutzerDTO u4 = benutzerDTODataAccess.save(
                new BenutzerDTO(
                        null,
                        InitialDataConstants.BAU_USER_VORNAME,
                        InitialDataConstants.BAU_USER_NACHNAME,
                        InitialDataConstants.BAU_USER_TEL,
                        InitialDataConstants.BAU_USER_EMAIL,
                        InitialDataConstants.BAU_USER_PW,
                        BenutzerRolle.RESSORTLEITER)).getData().orElseThrow();

        BenutzerDTO u5 = benutzerDTODataAccess.save(
                new BenutzerDTO(null,
                        InitialDataConstants.FINANZEN_USER_VORNAME,
                        InitialDataConstants.FINANZEN_USER_NACHNAME,
                        InitialDataConstants.FINANZEN_USER_TEL,
                        InitialDataConstants.FINANZEN_USER_EMAIL,
                        InitialDataConstants.FINANZEN_USER_PW,
                        BenutzerRolle.RESSORTLEITER)).getData().orElseThrow();

        // Ressorts
        RessortDTO r1 = ressortDTODataAccess.save(
                new RessortDTO(
                        null,
                        InitialDataConstants.RESSORT_VERKEHR_NAME,
                        InitialDataConstants.RESSORT_VERKEHR_BESCHREIBUNG,
                        InitialDataConstants.RESSORT_VERKEHR_ZUSTAENDIGKEIT,
                        u1)).getData().orElseThrow();

        RessortDTO r2 = ressortDTODataAccess.save(
                new RessortDTO(null,
                        InitialDataConstants.RESSORT_RENNLEITUNG_NAME,
                        InitialDataConstants.RESSORT_RENNLEITUNG_BESCHREIBUNG,
                        InitialDataConstants.RESSORT_RENNLEITUNG_ZUSTAENDIGKEIT,
                        u2)).getData().orElseThrow();

        RessortDTO r3 = ressortDTODataAccess.save(
                new RessortDTO(
                        null,
                        InitialDataConstants.RESSORT_FESTWIRTSCHAFT_NAME,
                        InitialDataConstants.RESSORT_FESTWIRTSCHAFT_BESCHREIBUNG,
                        InitialDataConstants.RESSORT_FESTWIRTSCHAFT_ZUSTAENDIGKEIT,
                        u3)).getData().orElseThrow();

        RessortDTO r4 = ressortDTODataAccess.save(
                new RessortDTO(
                        null,
                        InitialDataConstants.RESSORT_BAU_NAME,
                        InitialDataConstants.RESSORT_BAU_BESCHREIBUNG,
                        InitialDataConstants.RESSORT_BAU_ZUSTAENDIGKEIT,
                        u4)).getData().orElseThrow();

        RessortDTO r5 = ressortDTODataAccess.save(
                new RessortDTO(
                        null,
                        InitialDataConstants.RESSORT_FINANZEN_NAME,
                        InitialDataConstants.RESSORT_FINANZEN_BESCHREIBUNG,
                        InitialDataConstants.RESSORT_FINANZEN_ZUSTAENDIGKEIT,
                        u5)).getData().orElseThrow();

        // Helfer
        helferDTODataAccess.save(
                new HelferDTO(
                        null,
                        InitialDataConstants.VERKEHR_HELFER1_VORNAME,
                        InitialDataConstants.VERKEHR_HELFER1_NACHNAME,
                        InitialDataConstants.VERKEHR_HELFER1_EMAIL,
                        InitialDataConstants.VERKEHR_HELFER1_TEL,
                        r1));

        helferDTODataAccess.save(
                new HelferDTO(
                        null,
                        InitialDataConstants.RENNLEITUNG_HELFER1_VORNAME,
                        InitialDataConstants.RENNLEITUNG_HELFER1_NACHNAME,
                        InitialDataConstants.RENNLEITUNG_HELFER1_EMAIL,
                        InitialDataConstants.RENNLEITUNG_HELFER1_TEL,
                        r2));

        helferDTODataAccess.save(
                new HelferDTO(
                        null,
                        InitialDataConstants.FESTWIRTSCHAFT_HELFER1_VORNAME,
                        InitialDataConstants.FESTWIRTSCHAFT_HELFER1_NACHNAME,
                        InitialDataConstants.FESTWIRTSCHAFT_HELFER1_EMAIL,
                        InitialDataConstants.FESTWIRTSCHAFT_HELFER1_TEL,
                        r3));

        helferDTODataAccess.save(
                new HelferDTO(
                        null,
                        InitialDataConstants.BAU_HELFER1_VORNAME,
                        InitialDataConstants.BAU_HELFER1_NACHNAME,
                        InitialDataConstants.BAU_HELFER1_EMAIL,
                        InitialDataConstants.BAU_HELFER1_TEL,
                        r4));

        helferDTODataAccess.save(
                new HelferDTO(
                        null,
                        InitialDataConstants.FINANZEN_HELFER1_VORNAME,
                        InitialDataConstants.FINANZEN_HELFER1_NACHNAME,
                        InitialDataConstants.FINANZEN_HELFER1_EMAIL,
                        InitialDataConstants.FINANZEN_HELFER1_TEL,
                        r5));


        helferDTODataAccess.save(
                new HelferDTO(
                        null,
                        InitialDataConstants.VERKEHR_HELFER2_VORNAME,
                        InitialDataConstants.VERKEHR_HELFER2_NACHNAME,
                        InitialDataConstants.VERKEHR_HELFER2_EMAIL,
                        InitialDataConstants.VERKEHR_HELFER2_TEL,
                        r1));

        helferDTODataAccess.save(
                new HelferDTO(
                        null,
                        InitialDataConstants.RENNLEITUNG_HELFER2_VORNAME,
                        InitialDataConstants.RENNLEITUNG_HELFER2_NACHNAME,
                        InitialDataConstants.RENNLEITUNG_HELFER2_EMAIL,
                        InitialDataConstants.RENNLEITUNG_HELFER2_TEL,
                        r2));

        helferDTODataAccess.save(
                new HelferDTO(
                        null,
                        InitialDataConstants.FESTWIRTSCHAFT_HELFER2_VORNAME,
                        InitialDataConstants.FESTWIRTSCHAFT_HELFER2_NACHNAME,
                        InitialDataConstants.FESTWIRTSCHAFT_HELFER2_EMAIL,
                        InitialDataConstants.FESTWIRTSCHAFT_HELFER2_TEL,
                        r3));

        helferDTODataAccess.save(
                new HelferDTO(
                        null,
                        InitialDataConstants.BAU_HELFER2_VORNAME,
                        InitialDataConstants.BAU_HELFER2_NACHNAME,
                        InitialDataConstants.BAU_HELFER2_EMAIL,
                        InitialDataConstants.BAU_HELFER2_TEL,
                        r4));

        helferDTODataAccess.save(
                new HelferDTO(
                        null,
                        InitialDataConstants.FINANZEN_HELFER2_VORNAME,
                        InitialDataConstants.FINANZEN_HELFER2_NACHNAME,
                        InitialDataConstants.FINANZEN_HELFER2_EMAIL,
                        InitialDataConstants.FINANZEN_HELFER2_TEL,
                        r5));


        helferDTODataAccess.save(
                new HelferDTO(
                        null,
                        InitialDataConstants.VERKEHR_HELFER3_VORNAME,
                        InitialDataConstants.VERKEHR_HELFER3_NACHNAME,
                        InitialDataConstants.VERKEHR_HELFER3_EMAIL,
                        InitialDataConstants.VERKEHR_HELFER3_TEL,
                        r1));

        helferDTODataAccess.save(
                new HelferDTO(
                        null,
                        InitialDataConstants.RENNLEITUNG_HELFER3_VORNAME,
                        InitialDataConstants.RENNLEITUNG_HELFER3_NACHNAME,
                        InitialDataConstants.RENNLEITUNG_HELFER3_EMAIL,
                        InitialDataConstants.RENNLEITUNG_HELFER3_TEL,
                        r2));

        helferDTODataAccess.save(
                new HelferDTO(
                        null,
                        InitialDataConstants.FESTWIRTSCHAFT_HELFER3_VORNAME,
                        InitialDataConstants.FESTWIRTSCHAFT_HELFER3_NACHNAME,
                        InitialDataConstants.FESTWIRTSCHAFT_HELFER3_EMAIL,
                        InitialDataConstants.FESTWIRTSCHAFT_HELFER3_TEL,
                        r3));

        helferDTODataAccess.save(
                new HelferDTO(
                        null,
                        InitialDataConstants.BAU_HELFER3_VORNAME,
                        InitialDataConstants.BAU_HELFER3_NACHNAME,
                        InitialDataConstants.BAU_HELFER3_EMAIL,
                        InitialDataConstants.BAU_HELFER3_TEL,
                        r4));

        helferDTODataAccess.save(
                new HelferDTO(
                        null,
                        InitialDataConstants.FINANZEN_HELFER3_VORNAME,
                        InitialDataConstants.FINANZEN_HELFER3_NACHNAME,
                        InitialDataConstants.FINANZEN_HELFER3_EMAIL,
                        InitialDataConstants.FINANZEN_HELFER3_TEL,
                        r5));

        // Einsätze
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        einsatzDTODataAccess.save(new EinsatzDTO(null,
                InitialDataConstants.EINSATZ_VERKEHR_1_NAME,
                InitialDataConstants.EINSATZ_VERKEHR_1_DESC,
                LocalDateTime.parse(InitialDataConstants.EINSATZ_DATE + " " + InitialDataConstants.EINSATZ_VERKEHR_1_START, formatter),
                LocalDateTime.parse(InitialDataConstants.EINSATZ_DATE + " " + InitialDataConstants.EINSATZ_VERKEHR_1_END, formatter),
                InitialDataConstants.EINSATZ_VERKEHR_1_ORT,
                null,
                2,
                EinsatzStatus.ERSTELLT,
                r1));

        einsatzDTODataAccess.save(new EinsatzDTO(null,
                InitialDataConstants.EINSATZ_VERKEHR_2_NAME,
                InitialDataConstants.EINSATZ_VERKEHR_2_DESC,
                LocalDateTime.parse(InitialDataConstants.EINSATZ_DATE + " " + InitialDataConstants.EINSATZ_VERKEHR_2_START, formatter),
                LocalDateTime.parse(InitialDataConstants.EINSATZ_DATE + " " + InitialDataConstants.EINSATZ_VERKEHR_2_END, formatter),
                InitialDataConstants.EINSATZ_VERKEHR_2_ORT,
                null,
                4,
                EinsatzStatus.ERSTELLT,
                r1));

        einsatzDTODataAccess.save(new EinsatzDTO(null,
                InitialDataConstants.EINSATZ_RENNLEITUNG_1_NAME,
                InitialDataConstants.EINSATZ_RENNLEITUNG_1_DESC,
                LocalDateTime.parse(InitialDataConstants.EINSATZ_DATE + " " + InitialDataConstants.EINSATZ_RENNLEITUNG_1_START, formatter),
                LocalDateTime.parse(InitialDataConstants.EINSATZ_DATE + " " + InitialDataConstants.EINSATZ_RENNLEITUNG_1_END, formatter),
                InitialDataConstants.EINSATZ_RENNLEITUNG_1_ORT,
                "Zeitmessanlage",
                3,
                EinsatzStatus.OFFEN,
                r2));

        einsatzDTODataAccess.save(new EinsatzDTO(null,
                InitialDataConstants.EINSATZ_RENNLEITUNG_2_NAME,
                InitialDataConstants.EINSATZ_RENNLEITUNG_2_DESC,
                LocalDateTime.parse(InitialDataConstants.EINSATZ_DATE + " " + InitialDataConstants.EINSATZ_RENNLEITUNG_2_START, formatter),
                LocalDateTime.parse(InitialDataConstants.EINSATZ_DATE + " " + InitialDataConstants.EINSATZ_RENNLEITUNG_2_END, formatter),
                InitialDataConstants.EINSATZ_RENNLEITUNG_2_ORT,
                "Zeitmessanlage",
                3,
                EinsatzStatus.ERSTELLT,
                r2));

        einsatzDTODataAccess.save(new EinsatzDTO(null,
                InitialDataConstants.EINSATZ_FESTWIRTSCHAFT_1_NAME,
                InitialDataConstants.EINSATZ_FESTWIRTSCHAFT_1_DESC,
                LocalDateTime.parse(InitialDataConstants.EINSATZ_DATE + " " + InitialDataConstants.EINSATZ_FESTWIRTSCHAFT_1_START, formatter),
                LocalDateTime.parse(InitialDataConstants.EINSATZ_DATE + " " + InitialDataConstants.EINSATZ_FESTWIRTSCHAFT_1_END, formatter),
                InitialDataConstants.EINSATZ_FESTWIRTSCHAFT_1_ORT,
                "Grill, Fritteuse",
                5,
                EinsatzStatus.OFFEN,
                r3));

        einsatzDTODataAccess.save(new EinsatzDTO(null,
                InitialDataConstants.EINSATZ_FESTWIRTSCHAFT_2_NAME,
                InitialDataConstants.EINSATZ_FESTWIRTSCHAFT_2_DESC,
                LocalDateTime.parse(InitialDataConstants.EINSATZ_DATE + " " + InitialDataConstants.EINSATZ_FESTWIRTSCHAFT_2_START, formatter),
                LocalDateTime.parse(InitialDataConstants.EINSATZ_DATE + " " + InitialDataConstants.EINSATZ_FESTWIRTSCHAFT_2_END, formatter),
                InitialDataConstants.EINSATZ_FESTWIRTSCHAFT_2_ORT,
                "Grill, Zapfanlage",
                8,
                EinsatzStatus.ERSTELLT,
                r3));

        einsatzDTODataAccess.save(new EinsatzDTO(null,
                InitialDataConstants.EINSATZ_BAU_1_NAME,
                InitialDataConstants.EINSATZ_BAU_1_DESC,
                LocalDateTime.parse(InitialDataConstants.EINSATZ_DATE + " " + InitialDataConstants.EINSATZ_BAU_1_START, formatter),
                LocalDateTime.parse(InitialDataConstants.EINSATZ_DATE + " " + InitialDataConstants.EINSATZ_BAU_1_END, formatter),
                InitialDataConstants.EINSATZ_BAU_1_ORT,
                "Werkzeugkiste",
                2,
                EinsatzStatus.ABGESCHLOSSEN,
                r4));

        einsatzDTODataAccess.save(new EinsatzDTO(null,
                InitialDataConstants.EINSATZ_BAU_2_NAME,
                InitialDataConstants.EINSATZ_BAU_2_DESC,
                LocalDateTime.parse(InitialDataConstants.EINSATZ_DATE + " " + InitialDataConstants.EINSATZ_BAU_2_START, formatter),
                LocalDateTime.parse(InitialDataConstants.EINSATZ_DATE + " " + InitialDataConstants.EINSATZ_BAU_2_END, formatter),
                InitialDataConstants.EINSATZ_BAU_2_ORT,
                "Funkgerät",
                1,
                EinsatzStatus.OFFEN,
                r4));

        einsatzDTODataAccess.save(new EinsatzDTO(null,
                InitialDataConstants.EINSATZ_FINANZEN_1_NAME,
                InitialDataConstants.EINSATZ_FINANZEN_1_DESC,
                LocalDateTime.parse(InitialDataConstants.EINSATZ_DATE + " " + InitialDataConstants.EINSATZ_FINANZEN_1_START, formatter),
                LocalDateTime.parse(InitialDataConstants.EINSATZ_DATE + " " + InitialDataConstants.EINSATZ_FINANZEN_1_END, formatter),
                InitialDataConstants.EINSATZ_FINANZEN_1_ORT,
                "Kasse",
                2,
                EinsatzStatus.OFFEN,
                r5));

        einsatzDTODataAccess.save(new EinsatzDTO(null,
                InitialDataConstants.EINSATZ_FINANZEN_2_NAME,
                InitialDataConstants.EINSATZ_FINANZEN_2_DESC,
                LocalDateTime.parse(InitialDataConstants.EINSATZ_DATE + " " + InitialDataConstants.EINSATZ_FINANZEN_2_START, formatter),
                LocalDateTime.parse(InitialDataConstants.EINSATZ_DATE + " " + InitialDataConstants.EINSATZ_FINANZEN_2_END, formatter),
                InitialDataConstants.EINSATZ_FINANZEN_2_ORT,
                "Kasse",
                2,
                EinsatzStatus.ERSTELLT,
                r5));
    }
}