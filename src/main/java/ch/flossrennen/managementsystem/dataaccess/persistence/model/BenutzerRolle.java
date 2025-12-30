package ch.flossrennen.managementsystem.dataaccess.persistence.model;

import lombok.Getter;

@Getter
public enum BenutzerRolle {
    ADMINISTRATOR("Administrator"),
    RESSORTLEITER("Ressortleiter"),
    KEINE("Keine");

    private final String displayValue;

    BenutzerRolle(String displayValue) {
        this.displayValue = displayValue;
    }

    @Override
    public String toString() {
        return displayValue;
    }
}