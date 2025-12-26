package ch.flossrennen.managementsystem.dataaccess.persistence.model;

public interface BenutzerSchema {
    String TABLE_NAME = "benutzer";
    String ID = "id";
    String VORNAME = "vorname";
    String NACHNAME = "nachname";
    String TELEFONNUMMER = "telefonnummer";
    String EMAIL = "email";
    String PASSWORDHASH = "passwordhash";
    String ROLLE = "rolle";
}
