package ch.flossrennen.managementsystem.initialisation;

import ch.flossrennen.managementsystem.dataaccess.persistence.model.Benutzer;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.BenutzerRolle;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Helfer;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Ressort;
import ch.flossrennen.managementsystem.dataaccess.persistence.repository.BenutzerRepository;
import ch.flossrennen.managementsystem.dataaccess.persistence.repository.HelferRepository;
import ch.flossrennen.managementsystem.dataaccess.persistence.repository.RessortRepository;
import ch.flossrennen.managementsystem.initialisation.constants.InitialDataConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile({"dev", "test"})
@Order(2)
@RequiredArgsConstructor
public class InitialData implements CommandLineRunner {

    private final BenutzerRepository benutzerRepository;
    private final RessortRepository ressortRepository;
    private final HelferRepository helferRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (benutzerRepository.count() > 1 || ressortRepository.count() > 0 || helferRepository.count() > 0) {
            return;
        }

        // Benutzer / Ressortleiter
        Benutzer u1 = benutzerRepository.save(
                new Benutzer(
                        null,
                        InitialDataConstants.VERKEHR_USER_VORNAME,
                        InitialDataConstants.VERKEHR_USER_NACHNAME,
                        InitialDataConstants.VERKEHR_USER_TEL,
                        InitialDataConstants.VERKEHR_USER_EMAIL,
                        passwordEncoder.encode(InitialDataConstants.VERKEHR_USER_PW),
                        BenutzerRolle.RESSORTLEITER));

        Benutzer u2 = benutzerRepository.save(
                new Benutzer(
                        null,
                        InitialDataConstants.RENNLEITUNG_USER_VORNAME,
                        InitialDataConstants.RENNLEITUNG_USER_NACHNAME,
                        InitialDataConstants.RENNLEITUNG_USER_TEL,
                        InitialDataConstants.RENNLEITUNG_USER_EMAIL,
                        passwordEncoder.encode(InitialDataConstants.RENNLEITUNG_USER_PW),
                        BenutzerRolle.RESSORTLEITER));

        Benutzer u3 = benutzerRepository.save(
                new Benutzer(
                        null,
                        InitialDataConstants.FESTWIRTSCHAFT_USER_VORNAME,
                        InitialDataConstants.FESTWIRTSCHAFT_USER_NACHNAME,
                        InitialDataConstants.FESTWIRTSCHAFT_USER_TEL,
                        InitialDataConstants.FESTWIRTSCHAFT_USER_EMAIL,
                        passwordEncoder.encode(InitialDataConstants.FESTWIRTSCHAFT_USER_PW),
                        BenutzerRolle.RESSORTLEITER));

        Benutzer u4 = benutzerRepository.save(
                new Benutzer(
                        null,
                        InitialDataConstants.BAU_USER_VORNAME,
                        InitialDataConstants.BAU_USER_NACHNAME,
                        InitialDataConstants.BAU_USER_TEL,
                        InitialDataConstants.BAU_USER_EMAIL,
                        passwordEncoder.encode(InitialDataConstants.BAU_USER_PW),
                        BenutzerRolle.RESSORTLEITER));

        Benutzer u5 = benutzerRepository.save(
                new Benutzer(null,
                        InitialDataConstants.FINANZEN_USER_VORNAME,
                        InitialDataConstants.FINANZEN_USER_NACHNAME,
                        InitialDataConstants.FINANZEN_USER_TEL,
                        InitialDataConstants.FINANZEN_USER_EMAIL,
                        passwordEncoder.encode(InitialDataConstants.FINANZEN_USER_PW),
                        BenutzerRolle.RESSORTLEITER));

        // Ressorts
        Ressort r1 = ressortRepository.save(
                new Ressort(
                        null,
                        InitialDataConstants.RESSORT_VERKEHR_NAME,
                        InitialDataConstants.RESSORT_VERKEHR_BESCHREIBUNG,
                        InitialDataConstants.RESSORT_VERKEHR_ZUSTAENDIGKEIT,
                        u1));

        Ressort r2 = ressortRepository.save(
                new Ressort(null,
                        InitialDataConstants.RESSORT_RENNLEITUNG_NAME,
                        InitialDataConstants.RESSORT_RENNLEITUNG_BESCHREIBUNG,
                        InitialDataConstants.RESSORT_RENNLEITUNG_ZUSTAENDIGKEIT,
                        u2));

        Ressort r3 = ressortRepository.save(
                new Ressort(
                        null,
                        InitialDataConstants.RESSORT_FESTWIRTSCHAFT_NAME,
                        InitialDataConstants.RESSORT_FESTWIRTSCHAFT_BESCHREIBUNG,
                        InitialDataConstants.RESSORT_FESTWIRTSCHAFT_ZUSTAENDIGKEIT,
                        u3));

        Ressort r4 = ressortRepository.save(
                new Ressort(
                        null,
                        InitialDataConstants.RESSORT_BAU_NAME,
                        InitialDataConstants.RESSORT_BAU_BESCHREIBUNG,
                        InitialDataConstants.RESSORT_BAU_ZUSTAENDIGKEIT,
                        u4));

        Ressort r5 = ressortRepository.save(
                new Ressort(
                        null,
                        InitialDataConstants.RESSORT_FINANZEN_NAME,
                        InitialDataConstants.RESSORT_FINANZEN_BESCHREIBUNG,
                        InitialDataConstants.RESSORT_FINANZEN_ZUSTAENDIGKEIT,
                        u5));

        // Helfer
        helferRepository.save(
                new Helfer(
                        null,
                        InitialDataConstants.VERKEHR_HELFER1_VORNAME,
                        InitialDataConstants.VERKEHR_HELFER1_NACHNAME,
                        InitialDataConstants.VERKEHR_HELFER1_EMAIL,
                        InitialDataConstants.VERKEHR_HELFER1_TEL,
                        r1));

        helferRepository.save(
                new Helfer(
                        null,
                        InitialDataConstants.RENNLEITUNG_HELFER1_VORNAME,
                        InitialDataConstants.RENNLEITUNG_HELFER1_NACHNAME,
                        InitialDataConstants.RENNLEITUNG_HELFER1_EMAIL,
                        InitialDataConstants.RENNLEITUNG_HELFER1_TEL,
                        r2));

        helferRepository.save(
                new Helfer(
                        null,
                        InitialDataConstants.FESTWIRTSCHAFT_HELFER1_VORNAME,
                        InitialDataConstants.FESTWIRTSCHAFT_HELFER1_NACHNAME,
                        InitialDataConstants.FESTWIRTSCHAFT_HELFER1_EMAIL,
                        InitialDataConstants.FESTWIRTSCHAFT_HELFER1_TEL,
                        r3));

        helferRepository.save(
                new Helfer(
                        null,
                        InitialDataConstants.BAU_HELFER1_VORNAME,
                        InitialDataConstants.BAU_HELFER1_NACHNAME,
                        InitialDataConstants.BAU_HELFER1_EMAIL,
                        InitialDataConstants.BAU_HELFER1_TEL,
                        r4));

        helferRepository.save(
                new Helfer(
                        null,
                        InitialDataConstants.FINANZEN_HELFER1_VORNAME,
                        InitialDataConstants.FINANZEN_HELFER1_NACHNAME,
                        InitialDataConstants.FINANZEN_HELFER1_EMAIL,
                        InitialDataConstants.FINANZEN_HELFER1_TEL,
                        r5));


        helferRepository.save(
                new Helfer(
                        null,
                        InitialDataConstants.VERKEHR_HELFER2_VORNAME,
                        InitialDataConstants.VERKEHR_HELFER2_NACHNAME,
                        InitialDataConstants.VERKEHR_HELFER2_EMAIL,
                        InitialDataConstants.VERKEHR_HELFER2_TEL,
                        r1));

        helferRepository.save(
                new Helfer(
                        null,
                        InitialDataConstants.RENNLEITUNG_HELFER2_VORNAME,
                        InitialDataConstants.RENNLEITUNG_HELFER2_NACHNAME,
                        InitialDataConstants.RENNLEITUNG_HELFER2_EMAIL,
                        InitialDataConstants.RENNLEITUNG_HELFER2_TEL,
                        r2));

        helferRepository.save(
                new Helfer(
                        null,
                        InitialDataConstants.FESTWIRTSCHAFT_HELFER2_VORNAME,
                        InitialDataConstants.FESTWIRTSCHAFT_HELFER2_NACHNAME,
                        InitialDataConstants.FESTWIRTSCHAFT_HELFER2_EMAIL,
                        InitialDataConstants.FESTWIRTSCHAFT_HELFER2_TEL,
                        r3));

        helferRepository.save(
                new Helfer(
                        null,
                        InitialDataConstants.BAU_HELFER2_VORNAME,
                        InitialDataConstants.BAU_HELFER2_NACHNAME,
                        InitialDataConstants.BAU_HELFER2_EMAIL,
                        InitialDataConstants.BAU_HELFER2_TEL,
                        r4));

        helferRepository.save(
                new Helfer(
                        null,
                        InitialDataConstants.FINANZEN_HELFER2_VORNAME,
                        InitialDataConstants.FINANZEN_HELFER2_NACHNAME,
                        InitialDataConstants.FINANZEN_HELFER2_EMAIL,
                        InitialDataConstants.FINANZEN_HELFER2_TEL,
                        r5));


        helferRepository.save(
                new Helfer(
                        null,
                        InitialDataConstants.VERKEHR_HELFER3_VORNAME,
                        InitialDataConstants.VERKEHR_HELFER3_NACHNAME,
                        InitialDataConstants.VERKEHR_HELFER3_EMAIL,
                        InitialDataConstants.VERKEHR_HELFER3_TEL,
                        r1));

        helferRepository.save(
                new Helfer(
                        null,
                        InitialDataConstants.RENNLEITUNG_HELFER3_VORNAME,
                        InitialDataConstants.RENNLEITUNG_HELFER3_NACHNAME,
                        InitialDataConstants.RENNLEITUNG_HELFER3_EMAIL,
                        InitialDataConstants.RENNLEITUNG_HELFER3_TEL,
                        r2));

        helferRepository.save(
                new Helfer(
                        null,
                        InitialDataConstants.FESTWIRTSCHAFT_HELFER3_VORNAME,
                        InitialDataConstants.FESTWIRTSCHAFT_HELFER3_NACHNAME,
                        InitialDataConstants.FESTWIRTSCHAFT_HELFER3_EMAIL,
                        InitialDataConstants.FESTWIRTSCHAFT_HELFER3_TEL,
                        r3));

        helferRepository.save(
                new Helfer(
                        null,
                        InitialDataConstants.BAU_HELFER3_VORNAME,
                        InitialDataConstants.BAU_HELFER3_NACHNAME,
                        InitialDataConstants.BAU_HELFER3_EMAIL,
                        InitialDataConstants.BAU_HELFER3_TEL,
                        r4));

        helferRepository.save(
                new Helfer(
                        null,
                        InitialDataConstants.FINANZEN_HELFER3_VORNAME,
                        InitialDataConstants.FINANZEN_HELFER3_NACHNAME,
                        InitialDataConstants.FINANZEN_HELFER3_EMAIL,
                        InitialDataConstants.FINANZEN_HELFER3_TEL,
                        r5));
    }
}