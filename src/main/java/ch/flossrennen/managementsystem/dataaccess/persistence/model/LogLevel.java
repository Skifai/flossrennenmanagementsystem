package ch.flossrennen.managementsystem.dataaccess.persistence.model;

import ch.flossrennen.managementsystem.util.TranslationConstants;
import lombok.Getter;

@Getter
public enum LogLevel {
    INFO(TranslationConstants.LOG_LEVEL_INFO),
    ERROR(TranslationConstants.LOG_LEVEL_ERROR);

    private final String translationKey;

    LogLevel(String translationKey) {
        this.translationKey = translationKey;
    }
}
