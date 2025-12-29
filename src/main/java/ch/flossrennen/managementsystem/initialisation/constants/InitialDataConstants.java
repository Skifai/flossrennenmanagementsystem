package ch.flossrennen.managementsystem.initialisation.constants;

import ch.flossrennen.managementsystem.dataaccess.persistence.model.BenutzerRolle;

public interface InitialDataConstants {

    // Admin
    String ADMIN_VORNAME = "Admin";
    String ADMIN_NACHNAME = "Admin";
    String ADMIN_TELEFONNUMMER = "0794567890";
    String ADMIN_EMAIL = "admin@flossrennen-test.ch";
    String ADMIN_PASSWORD = "eli";
    BenutzerRolle ADMIN_ROLLE = BenutzerRolle.ADMINISTRATOR;

    // Benutzer / Ressortleiter
    String VERKEHR_USER_VORNAME = "Lukas";
    String VERKEHR_USER_NACHNAME = "Müller";
    String VERKEHR_USER_EMAIL = "lukas.mueller@flossrennen-test.ch";
    String VERKEHR_USER_TEL = "079 123 45 01";
    String VERKEHR_USER_PW = "password1";

    String RENNLEITUNG_USER_VORNAME = "Sarah";
    String RENNLEITUNG_USER_NACHNAME = "Schmidt";
    String RENNLEITUNG_USER_EMAIL = "sarah.schmidt@flossrennen-test.ch";
    String RENNLEITUNG_USER_TEL = "079 123 45 02";
    String RENNLEITUNG_USER_PW = "password2";

    String FESTWIRTSCHAFT_USER_VORNAME = "Marco";
    String FESTWIRTSCHAFT_USER_NACHNAME = "Schneider";
    String FESTWIRTSCHAFT_USER_EMAIL = "marco.schneider@flossrennen-test.ch";
    String FESTWIRTSCHAFT_USER_TEL = "079 123 45 03";
    String FESTWIRTSCHAFT_USER_PW = "password3";

    String BAU_USER_VORNAME = "Elena";
    String BAU_USER_NACHNAME = "Fischer";
    String BAU_USER_EMAIL = "elena.fischer@flossrennen-test.ch";
    String BAU_USER_TEL = "079 123 45 04";
    String BAU_USER_PW = "password4";

    String FINANZEN_USER_VORNAME = "Thomas";
    String FINANZEN_USER_NACHNAME = "Weber";
    String FINANZEN_USER_EMAIL = "thomas.weber@flossrennen-test.ch";
    String FINANZEN_USER_TEL = "079 123 45 05";
    String FINANZEN_USER_PW = "password5";

    // Ressorts
    String RESSORT_VERKEHR_NAME = "Verkehr";
    String RESSORT_VERKEHR_BESCHREIBUNG = "Organisation der Verkehrsführung und Parkplätze während des Events.";
    String RESSORT_VERKEHR_ZUSTAENDIGKEIT = "Parkplatzdienst, Strassensperren, Einweisung";

    String RESSORT_RENNLEITUNG_NAME = "Rennleitung";
    String RESSORT_RENNLEITUNG_BESCHREIBUNG = "Gesamtleitung des Flossrennens und Koordination der Rennläufe.";
    String RESSORT_RENNLEITUNG_ZUSTAENDIGKEIT = "Start/Ziel-Koordination, Zeitmessung, Reglement";

    String RESSORT_FESTWIRTSCHAFT_NAME = "Festwirtschaft";
    String RESSORT_FESTWIRTSCHAFT_BESCHREIBUNG = "Betrieb der Festwirtschaft, Verpflegung der Gäste und Helfer.";
    String RESSORT_FESTWIRTSCHAFT_ZUSTAENDIGKEIT = "Essen, Getränke, Standpersonal, Einkauf";

    String RESSORT_BAU_NAME = "Bau";
    String RESSORT_BAU_BESCHREIBUNG = "Auf- und Abbau der Infrastruktur sowie technische Unterstützung.";
    String RESSORT_BAU_ZUSTAENDIGKEIT = "Zeltbau, Strom, Wasser, Absperrungen";

    String RESSORT_FINANZEN_NAME = "Finanzen";
    String RESSORT_FINANZEN_BESCHREIBUNG = "Verwaltung der Finanzen, Sponsoring und Kassenführung.";
    String RESSORT_FINANZEN_ZUSTAENDIGKEIT = "Buchhaltung, Sponsorenbetreuung, Ticketverkauf";

    // Helfer
    String VERKEHR_HELFER1_VORNAME = "Anna";
    String VERKEHR_HELFER1_NACHNAME = "Keller";
    String VERKEHR_HELFER1_EMAIL = "anna.keller@example.ch";
    String VERKEHR_HELFER1_TEL = "078 987 65 00";

    String RENNLEITUNG_HELFER1_VORNAME = "Beat";
    String RENNLEITUNG_HELFER1_NACHNAME = "Meier";
    String RENNLEITUNG_HELFER1_EMAIL = "beat.meier@example.ch";
    String RENNLEITUNG_HELFER1_TEL = "078 987 65 01";

    String FESTWIRTSCHAFT_HELFER1_VORNAME = "Christian";
    String FESTWIRTSCHAFT_HELFER1_NACHNAME = "Huber";
    String FESTWIRTSCHAFT_HELFER1_EMAIL = "christian.huber@example.ch";
    String FESTWIRTSCHAFT_HELFER1_TEL = "078 987 65 02";

    String BAU_HELFER1_VORNAME = "Daniela";
    String BAU_HELFER1_NACHNAME = "Frei";
    String BAU_HELFER1_EMAIL = "daniela.frei@example.ch";
    String BAU_HELFER1_TEL = "078 987 65 03";

    String FINANZEN_HELFER1_VORNAME = "Erik";
    String FINANZEN_HELFER1_NACHNAME = "Widmer";
    String FINANZEN_HELFER1_EMAIL = "erik.widmer@example.ch";
    String FINANZEN_HELFER1_TEL = "078 987 65 04";

    String VERKEHR_HELFER2_VORNAME = "Fabienne";
    String VERKEHR_HELFER2_NACHNAME = "Graf";
    String VERKEHR_HELFER2_EMAIL = "fabienne.graf@example.ch";
    String VERKEHR_HELFER2_TEL = "078 987 65 05";

    String RENNLEITUNG_HELFER2_VORNAME = "Gabriel";
    String RENNLEITUNG_HELFER2_NACHNAME = "Baumann";
    String RENNLEITUNG_HELFER2_EMAIL = "gabriel.baumann@example.ch";
    String RENNLEITUNG_HELFER2_TEL = "078 987 65 06";

    String FESTWIRTSCHAFT_HELFER2_VORNAME = "Hanna";
    String FESTWIRTSCHAFT_HELFER2_NACHNAME = "Sutter";
    String FESTWIRTSCHAFT_HELFER2_EMAIL = "hanna.sutter@example.ch";
    String FESTWIRTSCHAFT_HELFER2_TEL = "078 987 65 07";

    String BAU_HELFER2_VORNAME = "Ivan";
    String BAU_HELFER2_NACHNAME = "Ziegler";
    String BAU_HELFER2_EMAIL = "ivan.ziegler@example.ch";
    String BAU_HELFER2_TEL = "078 987 65 08";

    String FINANZEN_HELFER2_VORNAME = "Julia";
    String FINANZEN_HELFER2_NACHNAME = "Brun";
    String FINANZEN_HELFER2_EMAIL = "julia.brun@example.ch";
    String FINANZEN_HELFER2_TEL = "078 987 65 09";

    String VERKEHR_HELFER3_VORNAME = "Kevin";
    String VERKEHR_HELFER3_NACHNAME = "Vogt";
    String VERKEHR_HELFER3_EMAIL = "kevin.vogt@example.ch";
    String VERKEHR_HELFER3_TEL = "078 987 65 10";

    String RENNLEITUNG_HELFER3_VORNAME = "Laura";
    String RENNLEITUNG_HELFER3_NACHNAME = "Bieri";
    String RENNLEITUNG_HELFER3_EMAIL = "laura.bieri@example.ch";
    String RENNLEITUNG_HELFER3_TEL = "078 987 65 11";

    String FESTWIRTSCHAFT_HELFER3_VORNAME = "Marcel";
    String FESTWIRTSCHAFT_HELFER3_NACHNAME = "Arm";
    String FESTWIRTSCHAFT_HELFER3_EMAIL = "marcel.arm@example.ch";
    String FESTWIRTSCHAFT_HELFER3_TEL = "078 987 65 12";

    String BAU_HELFER3_VORNAME = "Nadine";
    String BAU_HELFER3_NACHNAME = "Lutz";
    String BAU_HELFER3_EMAIL = "nadine.lutz@example.ch";
    String BAU_HELFER3_TEL = "078 987 65 13";

    String FINANZEN_HELFER3_VORNAME = "Oliver";
    String FINANZEN_HELFER3_NACHNAME = "Stucki";
    String FINANZEN_HELFER3_EMAIL = "oliver.stucki@example.ch";
    String FINANZEN_HELFER3_TEL = "078 987 65 14";
}
