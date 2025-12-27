package ch.flossrennen.managementsystem.dataaccess.dto;

import ch.flossrennen.managementsystem.dataaccess.persistence.model.RessortSchema;
import ch.flossrennen.managementsystem.util.TranslationConstants;

import java.util.function.Function;

public enum RessortDTOProperties implements DTOProperty<RessortDTO> {
    ID(RessortDTO::id, RessortSchema.ID, TranslationConstants.HELFER_ID),
    NAME(RessortDTO::name, RessortSchema.NAME, TranslationConstants.RESSORT_NAME),
    BESCHREIBUNG(RessortDTO::beschreibung, RessortSchema.BESCHREIBUNG, TranslationConstants.RESSORT_BESCHREIBUNG),
    ZUSTAENDIGKEIT(RessortDTO::zustaendigkeit, RessortSchema.ZUSTAENDIGKEIT, TranslationConstants.RESSORT_ZUSTAENDIGKEIT),
    RESSORTLEITUNG(RessortDTO::ressortleitung, RessortSchema.RESSORTLEITUNG, TranslationConstants.RESSORT_RESSORTLEITUNG);

    private final Function<RessortDTO, ?> getter;
    private final String schemaKey;
    private final String translationKey;

    RessortDTOProperties(Function<RessortDTO, ?> getter, String schemaKey, String translationKey) {
        this.getter = getter;
        this.schemaKey = schemaKey;
        this.translationKey = translationKey;
    }

    public Function<RessortDTO, ?> getGetter() {
        return getter;
    }

    public String getSchemaKey() {
        return schemaKey;
    }

    public String getTranslationKey() {
        return translationKey;
    }
}
