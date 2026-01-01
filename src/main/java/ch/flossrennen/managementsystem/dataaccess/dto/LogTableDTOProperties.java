package ch.flossrennen.managementsystem.dataaccess.dto;

import ch.flossrennen.managementsystem.dataaccess.persistence.model.LogSchema;
import ch.flossrennen.managementsystem.util.TranslationConstants;

import java.util.function.Function;

public enum LogTableDTOProperties implements DTOProperty<LogDTO> {
    ID(LogDTO::id, LogSchema.ID, TranslationConstants.LOGTABLE_ID),
    TIMESTAMP(LogDTO::timestamp, LogSchema.TIMESTAMP, TranslationConstants.LOGTABLE_TIMESTAMP),
    TYPE(LogDTO::type, LogSchema.TYPE, TranslationConstants.LOGTABLE_TYPE),
    LOG_LEVEL(LogDTO::logLevel, LogSchema.LOG_LEVEL, TranslationConstants.LOGTABLE_LOG_LEVEL),
    BENUTZER(LogDTO::benutzer, LogSchema.BENUTZER, TranslationConstants.LOGTABLE_BENUTZER),
    MESSAGE(LogDTO::message, LogSchema.MESSAGE, TranslationConstants.LOGTABLE_MESSAGE);

    private final Function<LogDTO, ?> getter;
    private final String schemaKey;
    private final String translationKey;

    LogTableDTOProperties(Function<LogDTO, ?> getter, String schemaKey, String translationKey) {
        this.getter = getter;
        this.schemaKey = schemaKey;
        this.translationKey = translationKey;
    }

    @Override
    public Function<LogDTO, ?> getGetter() {
        return getter;
    }

    @Override
    public String getSchemaKey() {
        return schemaKey;
    }

    @Override
    public String getTranslationKey() {
        return translationKey;
    }
}
