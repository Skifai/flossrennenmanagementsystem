package ch.flossrennen.managementsystem.dataaccess.dto;

import ch.flossrennen.managementsystem.dataaccess.persistence.model.HelferSchema;
import ch.flossrennen.managementsystem.util.TranslationConstants;

import java.util.function.Function;

public enum HelferDTOProperties {
    ID(HelferDTO::id, HelferSchema.ID, TranslationConstants.HELFER_ID),
    VORNAME(HelferDTO::vorname, HelferSchema.VORNAME, TranslationConstants.HELFER_VORNAME),
    NACHNAME(HelferDTO::nachname, HelferSchema.NACHNAME, TranslationConstants.HELFER_NACHNAME),
    EMAIL(HelferDTO::email, HelferSchema.EMAIL, TranslationConstants.HELFER_EMAIL),
    TELEFONNUMMER(HelferDTO::telefonnummer, HelferSchema.TELEFONNUMMER, TranslationConstants.HELFER_TELEFONNUMMER),
    RESSORT(helferDTO -> helferDTO.ressort().name(), HelferSchema.RESSORT, TranslationConstants.HELFER_RESSORT);

    private final Function<HelferDTO, ?> getter;
    private final String schemaKey;
    private final String translationKey;

    HelferDTOProperties(Function<HelferDTO, ?> getter, String schemaKey, String translationKey) {
        this.getter = getter;
        this.schemaKey = schemaKey;
        this.translationKey = translationKey;
    }

    public Function<HelferDTO, ?> getGetter() {
        return getter;
    }

    public String getSchemaKey() {
        return schemaKey;
    }

    public String getTranslationKey() {
        return translationKey;
    }
}
