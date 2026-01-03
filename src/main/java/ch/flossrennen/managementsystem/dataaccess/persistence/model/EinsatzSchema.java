package ch.flossrennen.managementsystem.dataaccess.persistence.model;

public interface EinsatzSchema {
    String TABLE_NAME = "einsatz";
    String ID = "id";
    String NAME = "name";
    String BESCHREIBUNG = "beschreibung";
    String STARTZEIT = "startzeit";
    String ENDZEIT = "endzeit";
    String ORT = "ort";
    String EINSATZMITTEL = "einsatzmittel";
    String BENOETIGTE_HELFER = "benoetigte_helfer";
    String STATUS = "status";
    String RESSORT = "ressort";
}