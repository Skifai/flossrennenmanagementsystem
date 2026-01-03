package ch.flossrennen.managementsystem.initialisation.constants;

import ch.flossrennen.managementsystem.dataaccess.persistence.model.BenutzerRolle;

public interface InitialDataConstants {

    // Admin
    Long ADMIN_ID = 1L;
    String ADMIN_VORNAME = "Admin";
    String ADMIN_NACHNAME = "Admin";
    String ADMIN_TELEFONNUMMER = "0794567890";
    String ADMIN_EMAIL = "admin@flossrennen-test.ch";
    String ADMIN_PASSWORD = "eli";
    BenutzerRolle ADMIN_ROLLE = BenutzerRolle.ADMINISTRATOR;

    // Benutzer / Ressortleiter
    Long VERKEHR_USER_ID = 2L;
    String VERKEHR_USER_VORNAME = "Lukas";
    String VERKEHR_USER_NACHNAME = "Müller";
    String VERKEHR_USER_EMAIL = "lukas.mueller@flossrennen-test.ch";
    String VERKEHR_USER_TEL = "079 123 45 01";
    String VERKEHR_USER_PW = "password1";

    Long RENNLEITUNG_USER_ID = 3L;
    String RENNLEITUNG_USER_VORNAME = "Sarah";
    String RENNLEITUNG_USER_NACHNAME = "Schmidt";
    String RENNLEITUNG_USER_EMAIL = "sarah.schmidt@flossrennen-test.ch";
    String RENNLEITUNG_USER_TEL = "079 123 45 02";
    String RENNLEITUNG_USER_PW = "password2";

    Long FESTWIRTSCHAFT_USER_ID = 4L;
    String FESTWIRTSCHAFT_USER_VORNAME = "Marco";
    String FESTWIRTSCHAFT_USER_NACHNAME = "Schneider";
    String FESTWIRTSCHAFT_USER_EMAIL = "marco.schneider@flossrennen-test.ch";
    String FESTWIRTSCHAFT_USER_TEL = "079 123 45 03";
    String FESTWIRTSCHAFT_USER_PW = "password3";

    Long BAU_USER_ID = 5L;
    String BAU_USER_VORNAME = "Elena";
    String BAU_USER_NACHNAME = "Fischer";
    String BAU_USER_EMAIL = "elena.fischer@flossrennen-test.ch";
    String BAU_USER_TEL = "079 123 45 04";
    String BAU_USER_PW = "password4";

    Long FINANZEN_USER_ID = 6L;
    String FINANZEN_USER_VORNAME = "Thomas";
    String FINANZEN_USER_NACHNAME = "Weber";
    String FINANZEN_USER_EMAIL = "thomas.weber@flossrennen-test.ch";
    String FINANZEN_USER_TEL = "079 123 45 05";
    String FINANZEN_USER_PW = "password5";

    // Ressorts
    Long RESSORT_VERKEHR_ID = 1L;
    String RESSORT_VERKEHR_NAME = "Verkehr";
    String RESSORT_VERKEHR_BESCHREIBUNG = "Organisation der Verkehrsführung und Parkplätze während des Events.";
    String RESSORT_VERKEHR_ZUSTAENDIGKEIT = "Parkplatzdienst, Strassensperren, Einweisung";

    Long RESSORT_RENNLEITUNG_ID = 2L;
    String RESSORT_RENNLEITUNG_NAME = "Rennleitung";
    String RESSORT_RENNLEITUNG_BESCHREIBUNG = "Gesamtleitung des Flossrennens und Koordination der Rennläufe.";
    String RESSORT_RENNLEITUNG_ZUSTAENDIGKEIT = "Start/Ziel-Koordination, Zeitmessung, Reglement";

    Long RESSORT_FESTWIRTSCHAFT_ID = 3L;
    String RESSORT_FESTWIRTSCHAFT_NAME = "Festwirtschaft";
    String RESSORT_FESTWIRTSCHAFT_BESCHREIBUNG = "Betrieb der Festwirtschaft, Verpflegung der Gäste und Helfer.";
    String RESSORT_FESTWIRTSCHAFT_ZUSTAENDIGKEIT = "Essen, Getränke, Standpersonal, Einkauf";

    Long RESSORT_BAU_ID = 4L;
    String RESSORT_BAU_NAME = "Bau";
    String RESSORT_BAU_BESCHREIBUNG = "Auf- und Abbau der Infrastruktur sowie technische Unterstützung.";
    String RESSORT_BAU_ZUSTAENDIGKEIT = "Zeltbau, Strom, Wasser, Absperrungen";

    Long RESSORT_FINANZEN_ID = 5L;
    String RESSORT_FINANZEN_NAME = "Finanzen";
    String RESSORT_FINANZEN_BESCHREIBUNG = "Verwaltung der Finanzen, Sponsoring und Kassenführung.";
    String RESSORT_FINANZEN_ZUSTAENDIGKEIT = "Buchhaltung, Sponsorenbetreuung, Ticketverkauf";

    // Helfer
    Long VERKEHR_HELFER1_ID = 1L;
    String VERKEHR_HELFER1_VORNAME = "Anna";
    String VERKEHR_HELFER1_NACHNAME = "Keller";
    String VERKEHR_HELFER1_EMAIL = "anna.keller@example.ch";
    String VERKEHR_HELFER1_TEL = "078 987 65 00";

    Long RENNLEITUNG_HELFER1_ID = 2L;
    String RENNLEITUNG_HELFER1_VORNAME = "Beat";
    String RENNLEITUNG_HELFER1_NACHNAME = "Meier";
    String RENNLEITUNG_HELFER1_EMAIL = "beat.meier@example.ch";
    String RENNLEITUNG_HELFER1_TEL = "078 987 65 01";

    Long FESTWIRTSCHAFT_HELFER1_ID = 3L;
    String FESTWIRTSCHAFT_HELFER1_VORNAME = "Christian";
    String FESTWIRTSCHAFT_HELFER1_NACHNAME = "Huber";
    String FESTWIRTSCHAFT_HELFER1_EMAIL = "christian.huber@example.ch";
    String FESTWIRTSCHAFT_HELFER1_TEL = "078 987 65 02";

    Long BAU_HELFER1_ID = 4L;
    String BAU_HELFER1_VORNAME = "Daniela";
    String BAU_HELFER1_NACHNAME = "Frei";
    String BAU_HELFER1_EMAIL = "daniela.frei@example.ch";
    String BAU_HELFER1_TEL = "078 987 65 03";

    Long FINANZEN_HELFER1_ID = 5L;
    String FINANZEN_HELFER1_VORNAME = "Erik";
    String FINANZEN_HELFER1_NACHNAME = "Widmer";
    String FINANZEN_HELFER1_EMAIL = "erik.widmer@example.ch";
    String FINANZEN_HELFER1_TEL = "078 987 65 04";

    Long VERKEHR_HELFER2_ID = 6L;
    String VERKEHR_HELFER2_VORNAME = "Fabienne";
    String VERKEHR_HELFER2_NACHNAME = "Graf";
    String VERKEHR_HELFER2_EMAIL = "fabienne.graf@example.ch";
    String VERKEHR_HELFER2_TEL = "078 987 65 05";

    Long RENNLEITUNG_HELFER2_ID = 7L;
    String RENNLEITUNG_HELFER2_VORNAME = "Gabriel";
    String RENNLEITUNG_HELFER2_NACHNAME = "Baumann";
    String RENNLEITUNG_HELFER2_EMAIL = "gabriel.baumann@example.ch";
    String RENNLEITUNG_HELFER2_TEL = "078 987 65 06";

    Long FESTWIRTSCHAFT_HELFER2_ID = 8L;
    String FESTWIRTSCHAFT_HELFER2_VORNAME = "Hanna";
    String FESTWIRTSCHAFT_HELFER2_NACHNAME = "Sutter";
    String FESTWIRTSCHAFT_HELFER2_EMAIL = "hanna.sutter@example.ch";
    String FESTWIRTSCHAFT_HELFER2_TEL = "078 987 65 07";

    Long BAU_HELFER2_ID = 9L;
    String BAU_HELFER2_VORNAME = "Ivan";
    String BAU_HELFER2_NACHNAME = "Ziegler";
    String BAU_HELFER2_EMAIL = "ivan.ziegler@example.ch";
    String BAU_HELFER2_TEL = "078 987 65 08";

    Long FINANZEN_HELFER2_ID = 10L;
    String FINANZEN_HELFER2_VORNAME = "Julia";
    String FINANZEN_HELFER2_NACHNAME = "Brun";
    String FINANZEN_HELFER2_EMAIL = "julia.brun@example.ch";
    String FINANZEN_HELFER2_TEL = "078 987 65 09";

    Long VERKEHR_HELFER3_ID = 11L;
    String VERKEHR_HELFER3_VORNAME = "Kevin";
    String VERKEHR_HELFER3_NACHNAME = "Vogt";
    String VERKEHR_HELFER3_EMAIL = "kevin.vogt@example.ch";
    String VERKEHR_HELFER3_TEL = "078 987 65 10";

    Long RENNLEITUNG_HELFER3_ID = 12L;
    String RENNLEITUNG_HELFER3_VORNAME = "Laura";
    String RENNLEITUNG_HELFER3_NACHNAME = "Bieri";
    String RENNLEITUNG_HELFER3_EMAIL = "laura.bieri@example.ch";
    String RENNLEITUNG_HELFER3_TEL = "078 987 65 11";

    Long FESTWIRTSCHAFT_HELFER3_ID = 13L;
    String FESTWIRTSCHAFT_HELFER3_VORNAME = "Marcel";
    String FESTWIRTSCHAFT_HELFER3_NACHNAME = "Arm";
    String FESTWIRTSCHAFT_HELFER3_EMAIL = "marcel.arm@example.ch";
    String FESTWIRTSCHAFT_HELFER3_TEL = "078 987 65 12";

    Long BAU_HELFER3_ID = 14L;
    String BAU_HELFER3_VORNAME = "Nadine";
    String BAU_HELFER3_NACHNAME = "Lutz";
    String BAU_HELFER3_EMAIL = "nadine.lutz@example.ch";
    String BAU_HELFER3_TEL = "078 987 65 13";

    Long FINANZEN_HELFER3_ID = 15L;
    String FINANZEN_HELFER3_VORNAME = "Oliver";
    String FINANZEN_HELFER3_NACHNAME = "Stucki";
    String FINANZEN_HELFER3_EMAIL = "oliver.stucki@example.ch";
    String FINANZEN_HELFER3_TEL = "078 987 65 14";

    // Einsätze
    String EINSATZ_DATE = "2026-05-11";

    String EINSATZ_VERKEHR_1_NAME = "Parkdienst Vormittag";
    String EINSATZ_VERKEHR_1_DESC = "Einweisung der Teilnehmerfahrzeuge auf die Parkplätze.";
    String EINSATZ_VERKEHR_1_START = "07:00";
    String EINSATZ_VERKEHR_1_END = "12:00";
    String EINSATZ_VERKEHR_1_ORT = "Parkplatz Sitter";

    String EINSATZ_VERKEHR_2_NAME = "Strassensperrung Nachmittag";
    String EINSATZ_VERKEHR_2_DESC = "Überwachung der Strassensperren während des Hauptrennens.";
    String EINSATZ_VERKEHR_2_START = "13:00";
    String EINSATZ_VERKEHR_2_END = "18:00";
    String EINSATZ_VERKEHR_2_ORT = "Hauptstrasse / Sitterbrücke";

    String EINSATZ_RENNLEITUNG_1_NAME = "Zeitmessung Block 1";
    String EINSATZ_RENNLEITUNG_1_DESC = "Bedienung der Zeitmessanlage für die erste Gruppe.";
    String EINSATZ_RENNLEITUNG_1_START = "09:00";
    String EINSATZ_RENNLEITUNG_1_END = "11:30";
    String EINSATZ_RENNLEITUNG_1_ORT = "Zielbereich";

    String EINSATZ_RENNLEITUNG_2_NAME = "Zeitmessung Block 2";
    String EINSATZ_RENNLEITUNG_2_DESC = "Bedienung der Zeitmessanlage für die zweite Gruppe (Überschneidet Block 1).";
    String EINSATZ_RENNLEITUNG_2_START = "11:00";
    String EINSATZ_RENNLEITUNG_2_END = "14:00";
    String EINSATZ_RENNLEITUNG_2_ORT = "Zielbereich";

    String EINSATZ_FESTWIRTSCHAFT_1_NAME = "Mittagsservice";
    String EINSATZ_FESTWIRTSCHAFT_1_DESC = "Ausgabe von Speisen und Getränken während der Mittagszeit.";
    String EINSATZ_FESTWIRTSCHAFT_1_START = "11:00";
    String EINSATZ_FESTWIRTSCHAFT_1_END = "14:30";
    String EINSATZ_FESTWIRTSCHAFT_1_ORT = "Festzelt";

    String EINSATZ_FESTWIRTSCHAFT_2_NAME = "Abendservice";
    String EINSATZ_FESTWIRTSCHAFT_2_DESC = "Bewirtung der Gäste nach dem Rennen.";
    String EINSATZ_FESTWIRTSCHAFT_2_START = "17:00";
    String EINSATZ_FESTWIRTSCHAFT_2_END = "22:00";
    String EINSATZ_FESTWIRTSCHAFT_2_ORT = "Festzelt";

    String EINSATZ_BAU_1_NAME = "Letzte Vorbereitungen";
    String EINSATZ_BAU_1_DESC = "Kontrolle der Absperrungen und Stromversorgung.";
    String EINSATZ_BAU_1_START = "06:00";
    String EINSATZ_BAU_1_END = "09:00";
    String EINSATZ_BAU_1_ORT = "Eventareal";

    String EINSATZ_BAU_2_NAME = "Sofortreparaturen";
    String EINSATZ_BAU_2_DESC = "Bereitschaft für technische Defekte (Überschneidet Vorbereitungen).";
    String EINSATZ_BAU_2_START = "08:30";
    String EINSATZ_BAU_2_END = "12:00";
    String EINSATZ_BAU_2_ORT = "Werkstatt / Areal";

    String EINSATZ_FINANZEN_1_NAME = "Tageskasse Vormittag";
    String EINSATZ_FINANZEN_1_DESC = "Verkauf von Eintrittskarten und Programmen.";
    String EINSATZ_FINANZEN_1_START = "08:00";
    String EINSATZ_FINANZEN_1_END = "13:00";
    String EINSATZ_FINANZEN_1_ORT = "Eingang Nord";

    String EINSATZ_FINANZEN_2_NAME = "Tageskasse Nachmittag";
    String EINSATZ_FINANZEN_2_DESC = "Verkauf von Eintrittskarten und Programmen.";
    String EINSATZ_FINANZEN_2_START = "13:00";
    String EINSATZ_FINANZEN_2_END = "17:00";
    String EINSATZ_FINANZEN_2_ORT = "Eingang Nord";
}
