package ch.flossrennen.managementsystem.util.textprovider;

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

    // Entity LogEntry
    String LOGTABLE_ID = "logevent.id";
    String LOGTABLE_TIMESTAMP = "logevent.timestamp";
    String LOGTABLE_TYPE = "logevent.type";
    String LOGTABLE_LOG_LEVEL = "logevent.log_level";
    String LOGTABLE_BENUTZER = "logevent.benutzer";
    String LOGTABLE_MESSAGE = "logevent.message";

    // Notifications
    String SUCCESS_SAVE = "notification.success.save";
    String SUCCESS_DELETE = "notification.success.delete";
    String ERROR_SAVE = "notification.error.save";
    String ERROR_DELETE = "notification.error.delete";
    String ERROR_MISSING_ID = "notification.error.missing.id";
    String ERROR_MISSING_RESSORT = "notification.error.missing.ressort";

    // Validation
    String VALIDATION_REQUIRED = "validation.required";
    String VALIDATION_EMAIL = "validation.email";
    String VALIDATION_TELEFONNUMMER = "validation.telefonnummer";
    String VALIDATION_SIZE = "validation.size";
    String VALIDATION_UNIQUE_EMAIL = "validation.unique.email";
    String VALIDATION_UNIQUE_TELEFONNUMMER = "validation.unique.telefonnummer";
    String VALIDATION_UNIQUE_NAME = "validation.unique.name";
    String VALIDATION_ID_NAN = "validation.id.nan";

    // Common
    String SAVE = "common.save";
    String CANCEL = "common.cancel";
    String DELETE = "common.delete";
    String FILTER = "common.filter";

    // Main View
    String NAV_HOME = "nav.home";
    String NAV_HELFER = "nav.helfer";
    String NAV_RESSORT = "nav.ressort";
    String NAV_BENUTZER = "nav.benutzer";

    // Home View
    String HOME_WELCOME = "home.welcome";

    // Helfer View
    String HELFER_TITLE = "helfer.title";
    String HELFER_NEW = "helfer.new";

    // Ressort View
    String RESSORT_TITLE = "ressort.title";
    String RESSORT_NEW = "ressort.new";

    // Benutzer View
    String BENUTZER_TITLE = "benutzer.title";
    String BENUTZER_NEW = "benutzer.new";


    // LogEntry View
    String LOGTABLE_TITLE = "logevent.title";
    String NAV_LOGTABLE = "nav.logevent";
    String LOGTABLE_FILTER_TYPE = "logevent.filter.type";
    String LOGTABLE_FILTER_LEVEL = "logevent.filter.level";
    String LOGTABLE_FILTER_USER = "logevent.filter.user";
    String LOGTABLE_FILTER_FROM = "logevent.filter.from";
    String LOGTABLE_FILTER_TO = "logevent.filter.to";

    // Log Levels
    String LOG_LEVEL_INFO = "log.level.info";
    String LOG_LEVEL_ERROR = "log.level.error";

    // Log Types
    String LOG_TYPE_HELFER_CREATED = "log.type.helfer.created";
    String LOG_TYPE_HELFER_UPDATED = "log.type.helfer.updated";
    String LOG_TYPE_HELFER_DELETED = "log.type.helfer.deleted";
    String LOG_TYPE_RESSORT_CREATED = "log.type.ressort.created";
    String LOG_TYPE_RESSORT_UPDATED = "log.type.ressort.updated";
    String LOG_TYPE_RESSORT_DELETED = "log.type.ressort.deleted";
    String LOG_TYPE_BENUTZER_CREATED = "log.type.benutzer.created";
    String LOG_TYPE_BENUTZER_UPDATED = "log.type.benutzer.updated";
    String LOG_TYPE_BENUTZER_DELETED = "log.type.benutzer.deleted";
}
