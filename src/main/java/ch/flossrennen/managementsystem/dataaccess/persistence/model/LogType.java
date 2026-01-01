package ch.flossrennen.managementsystem.dataaccess.persistence.model;

import ch.flossrennen.managementsystem.util.TranslationConstants;
import lombok.Getter;

@Getter
public enum LogType {
    HELFER_CREATED(TranslationConstants.LOG_TYPE_HELFER_CREATED),
    HELFER_UPDATED(TranslationConstants.LOG_TYPE_HELFER_UPDATED),
    HELFER_DELETED(TranslationConstants.LOG_TYPE_HELFER_DELETED),
    RESSORT_CREATED(TranslationConstants.LOG_TYPE_RESSORT_CREATED),
    RESSORT_UPDATED(TranslationConstants.LOG_TYPE_RESSORT_UPDATED),
    RESSORT_DELETED(TranslationConstants.LOG_TYPE_RESSORT_DELETED),
    BENUTZER_CREATED(TranslationConstants.LOG_TYPE_BENUTZER_CREATED),
    BENUTZER_UPDATED(TranslationConstants.LOG_TYPE_BENUTZER_UPDATED),
    BENUTZER_DELETED(TranslationConstants.LOG_TYPE_BENUTZER_DELETED);

    private final String translationKey;

    LogType(String translationKey) {
        this.translationKey = translationKey;
    }
}
