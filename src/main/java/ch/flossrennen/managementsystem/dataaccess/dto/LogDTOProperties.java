package ch.flossrennen.managementsystem.dataaccess.dto;

import ch.flossrennen.managementsystem.dataaccess.persistence.model.LogSchema;
import ch.flossrennen.managementsystem.util.textprovider.TranslationConstants;

import java.util.function.Function;

public enum LogDTOProperties implements DTOProperty<LogDTO> {
    ID(
            LogDTO::id,
            LogSchema.ID,
            TranslationConstants.LOG_ID,
            true,
            false),
    TIMESTAMP(
            LogDTO::timestamp,
            LogSchema.TIMESTAMP,
            TranslationConstants.LOG_TIMESTAMP,
            true,
            false),
    TYPE(
            dto -> dto.type() != null ? dto.type().getTranslationKey() : "",
            LogSchema.TYPE,
            TranslationConstants.LOG_TYPE,
            true,
            true),
    LOG_LEVEL(
            dto -> dto.logLevel() != null ? dto.logLevel().getTranslationKey() : "",
            LogSchema.LOG_LEVEL,
            TranslationConstants.LOG_LEVEL,
            true,
            true),
    BENUTZER(
            LogDTO::benutzer,
            LogSchema.BENUTZER,
            TranslationConstants.LOG_BENUTZER,
            true,
            false),
    MESSAGE(
            LogDTO::message,
            LogSchema.MESSAGE,
            TranslationConstants.LOG_MESSAGE,
            true,
            false);

    private final Function<LogDTO, ?> getter;
    private final String schemaKey;
    private final String translationKey;
    private final boolean sortable;
    private final boolean translatable;

    LogDTOProperties(Function<LogDTO, ?> getter,
                     String schemaKey,
                     String translationKey,
                     boolean sortable,
                     boolean translatable) {
        this.getter = getter;
        this.schemaKey = schemaKey;
        this.translationKey = translationKey;
        this.sortable = sortable;
        this.translatable = translatable;
    }

    @Override
    public boolean isTranslatable() {
        return translatable;
    }

    @Override
    public boolean isSortable() {
        return sortable;
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