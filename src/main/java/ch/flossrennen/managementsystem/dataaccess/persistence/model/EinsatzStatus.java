package ch.flossrennen.managementsystem.dataaccess.persistence.model;

import ch.flossrennen.managementsystem.util.textprovider.TranslationConstants;
import lombok.Getter;

@Getter
public enum EinsatzStatus {
    ERSTELLT(TranslationConstants.EINSATZ_STATUS_ERSTELLT),
    OFFEN(TranslationConstants.EINSATZ_STATUS_OFFEN),
    ABGESCHLOSSEN(TranslationConstants.EINSATZ_STATUS_ABGESCHLOSSEN);

    private final String translationKey;

    EinsatzStatus(String translationKey) {
        this.translationKey = translationKey;
    }
}