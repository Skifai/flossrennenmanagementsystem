package ch.flossrennen.managementsystem.util;

public interface TranslationConstants {

    // Global
    String TITEL = "titel";
    String SUBTITEL = "subtitel";
    String LOGO_DESC = "logodesc";

    // Login
    String LOGIN_TITLE = "logintitel";
    String USERNAME = "username";
    String PASSWORD = "password";
    String LOGIN_BUTTON = "loginbutton";
    String LOGIN_ERROR_TITLE = "loginerrortitel";
    String LOGIN_ERROR_MESSAGE = "loginerrormessage";

    // Entity Helfer
    String HELFER_ID = "helfer.id";
    String HELFER_VORNAME = "helfer.vorname";
    String HELFER_NACHNAME = "helfer.nachname";
    String HELFER_EMAIL = "helfer.email";
    String HELFER_TELEFONNUMMER = "helfer.telefonnummer";
    String HELFER_RESSORT = "helfer.ressort";

    // Entity Benutzer
    String BENUTZER_ID = "benutzer.id";
    String BENUTZER_VORNAME = "benutzer.vorname";
    String BENUTZER_NACHNAME = "benutzer.nachname";
    String BENUTZER_TELEFONNUMMER = "benutzer.telefonnummer";
    String BENUTZER_EMAIL = "benutzer.email";
    String BENUTZER_PASSWORT = "benutzer.passwort";
    String BENUTZER_ROLLE = "benutzer.rolle";

    // Entity Ressort
    String RESSORT_ID = "ressort.id";
    String RESSORT_NAME = "ressort.name";
    String RESSORT_BESCHREIBUNG = "ressort.beschreibung";
    String RESSORT_ZUSTAENDIGKEIT = "ressort.zustaendigkeit";
    String RESSORT_RESSORTLEITUNG = "ressort.ressortleitung";

    // Notifications
    String SUCCESS_SAVE = "notification.success.save";
    String SUCCESS_DELETE = "notification.success.delete";
    String ERROR_SAVE = "notification.error.save";
    String ERROR_DELETE = "notification.error.delete";
    String ERROR_MISSING_ID = "notification.error.missing.id";
    String ERROR_MISSING_RESSORT = "notification.error.missing.ressort";
}
