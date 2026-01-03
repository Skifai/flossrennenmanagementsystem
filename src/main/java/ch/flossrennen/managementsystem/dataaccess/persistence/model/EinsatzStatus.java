package ch.flossrennen.managementsystem.dataaccess.persistence.model;

import lombok.Getter;

@Getter
public enum EinsatzStatus {
    ERSTELLT("Erstellt"),
    OFFEN("Offen"),
    ABGESCHLOSSEN("Abgeschlossen");

    private final String displayValue;

    EinsatzStatus(String displayValue) {
        this.displayValue = displayValue;
    }
}