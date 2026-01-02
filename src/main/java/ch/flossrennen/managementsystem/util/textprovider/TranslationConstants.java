package ch.flossrennen.managementsystem.util.textprovider;

public interface TranslationConstants {

    //region Global

    //region Branding
    String TITEL = "titel";
    String SUBTITEL = "subtitel";
    String LOGO_DESC = "logodesc";
    //endregion

    //region Navigation
    String NAV_HOME = "nav.home";
    String NAV_HELFER = "nav.helfer";
    String NAV_RESSORT = "nav.ressort";
    String NAV_BENUTZER = "nav.benutzer";
    String NAV_SYSTEMPROTOKOLL = "nav.systemprotokoll";
    //endregion

    //region Common Actions
    String SAVE = "common.save";
    String CANCEL = "common.cancel";
    String DELETE = "common.delete";
    String FILTER = "common.filter";
    //endregion

    //region Notifications
    String SUCCESS_SAVE = "notification.success.save";
    String SUCCESS_DELETE = "notification.success.delete";
    String ERROR_SAVE = "notification.error.save";
    String ERROR_NOPASSWORD = "notification.error.nopassword";
    String ERROR_DELETE = "notification.error.delete";
    String ERROR_MISSING_ID = "notification.error.missing.id";
    String ERROR_MISSING_RESSORT = "notification.error.missing.ressort";
    //endregion

    //region Logging Messages
    String LOG_DELETE_NOT_FOUND = "log.delete.notfound";
    String LOG_SAVE_NO_PASSWORD = "log.save.nopassword";
    String EXCEPTION_NOT_FOUND = "exception.notfound";
    String LOG_NEW = "log.new";
    //endregion

    //region Validation
    String VALIDATION_REQUIRED = "validation.required";
    String VALIDATION_EMAIL = "validation.email";
    String VALIDATION_TELEFONNUMMER = "validation.telefonnummer";
    String VALIDATION_SIZE = "validation.size";
    String VALIDATION_UNIQUE_EMAIL = "validation.unique.email";
    String VALIDATION_UNIQUE_TELEFONNUMMER = "validation.unique.telefonnummer";
    String VALIDATION_UNIQUE_NAME = "validation.unique.name";
    String VALIDATION_ID_NAN = "validation.id.nan";
    //endregion

    //endregion

    //region Entities

    //region Helfer
    String HELFER_ID = "helfer.id";
    String HELFER_VORNAME = "helfer.vorname";
    String HELFER_NACHNAME = "helfer.nachname";
    String HELFER_EMAIL = "helfer.email";
    String HELFER_TELEFONNUMMER = "helfer.telefonnummer";
    String HELFER_RESSORT = "helfer.ressort";
    //endregion

    //region Benutzer
    String BENUTZER_ID = "benutzer.id";
    String BENUTZER_VORNAME = "benutzer.vorname";
    String BENUTZER_NACHNAME = "benutzer.nachname";
    String BENUTZER_TELEFONNUMMER = "benutzer.telefonnummer";
    String BENUTZER_EMAIL = "benutzer.email";
    String BENUTZER_PASSWORT = "benutzer.passwort";
    String BENUTZER_ROLLE = "benutzer.rolle";
    //endregion

    //region Ressort
    String RESSORT_ID = "ressort.id";
    String RESSORT_NAME = "ressort.name";
    String RESSORT_BESCHREIBUNG = "ressort.beschreibung";
    String RESSORT_ZUSTAENDIGKEIT = "ressort.zustaendigkeit";
    String RESSORT_RESSORTLEITUNG = "ressort.ressortleitung";
    //endregion

    //region LogEntry
    String LOG_ID = "log.id";
    String LOG_TIMESTAMP = "log.timestamp";
    String LOG_TYPE = "log.type";
    String LOG_LEVEL = "log.level";
    String LOG_BENUTZER = "log.benutzer";
    String LOG_MESSAGE = "log.message";
    //endregion

    //endregion

    //region Log Constants

    //region Log Levels
    String LOG_LEVEL_INFO = "log.level.info";
    String LOG_LEVEL_ERROR = "log.level.error";
    //endregion

    //region Log Types
    String LOG_TYPE_HELFER_CREATED = "log.type.helfer.created";
    String LOG_TYPE_HELFER_UPDATED = "log.type.helfer.updated";
    String LOG_TYPE_HELFER_DELETED = "log.type.helfer.deleted";
    String LOG_TYPE_RESSORT_CREATED = "log.type.ressort.created";
    String LOG_TYPE_RESSORT_UPDATED = "log.type.ressort.updated";
    String LOG_TYPE_RESSORT_DELETED = "log.type.ressort.deleted";
    String LOG_TYPE_BENUTZER_CREATED = "log.type.benutzer.created";
    String LOG_TYPE_BENUTZER_UPDATED = "log.type.benutzer.updated";
    String LOG_TYPE_BENUTZER_DELETED = "log.type.benutzer.deleted";
    String LOG_TYPE_APPERROR = "log.type.apperror";
    //endregion

    //endregion

    //region Views

    //region Login View
    String LOGIN_TITLE = "logintitel";
    String USERNAME = "username";
    String PASSWORD = "password";
    String LOGIN_BUTTON = "loginbutton";
    String LOGIN_ERROR_TITLE = "loginerrortitel";
    String LOGIN_ERROR_MESSAGE = "loginerrormessage";
    //endregion

    //region Home View
    String HOME_WELCOME = "home.welcome";
    //endregion

    //region Helfer View
    String HELFER_TITLE = "helfer.title";
    String HELFER_NEW = "helfer.new";
    //endregion

    //region Ressort View
    String RESSORT_TITLE = "ressort.title";
    String RESSORT_NEW = "ressort.new";
    //endregion

    //region Benutzer View
    String BENUTZER_TITLE = "benutzer.title";
    String BENUTZER_NEW = "benutzer.new";
    //endregion

    //region Systemprotokoll View
    String SYSTEMPROTOKOLL_TITLE = "systemprotokoll.title";
    String SYSTEMPROTOKOLL_FILTER_TYPE = "systemprotokoll.filter.type";
    String SYSTEMPROTOKOLL_FILTER_LEVEL = "systemprotokoll.filter.level";
    String SYSTEMPROTOKOLL_FILTER_USER = "systemprotokoll.filter.user";
    String SYSTEMPROTOKOLL_FILTER_FROM = "systemprotokoll.filter.from";
    String SYSTEMPROTOKOLL_FILTER_TO = "systemprotokoll.filter.to";
    String SYSTEMPROTOKOLL_FILTER_PLACEHOLDER = "systemprotokoll.filter.placeholder";
    //endregion

    //endregion
}
