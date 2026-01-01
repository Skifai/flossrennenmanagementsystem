package ch.flossrennen.managementsystem.dataaccess.persistence.model;

public interface LogSchema {
    String TABLE_NAME = "log";
    String ID = "id";
    String TIMESTAMP = "timestamp";
    String TYPE = "type";
    String LOG_LEVEL = "log_level";
    String BENUTZER = "benutzer";
    String MESSAGE = "message";
}
