package ch.flossrennen.managementsystem.dataaccess.dto;

import ch.flossrennen.managementsystem.dataaccess.persistence.model.RessortSchema;
import ch.flossrennen.managementsystem.util.textprovider.TranslationConstants;

import java.util.function.Function;

public enum RessortDTOProperties implements DTOProperty<RessortDTO> {
    ID(
            RessortDTO::id,
            RessortSchema.ID,
            TranslationConstants.HELFER_ID,
            true,
            false),
    NAME(
            RessortDTO::name,
            RessortSchema.NAME,
            TranslationConstants.RESSORT_NAME,
            true,
            false),
    BESCHREIBUNG(
            RessortDTO::beschreibung,
            RessortSchema.BESCHREIBUNG,
            TranslationConstants.RESSORT_BESCHREIBUNG,
            true,
            false),
    ZUSTAENDIGKEIT(
            RessortDTO::zustaendigkeit,
            RessortSchema.ZUSTAENDIGKEIT,
            TranslationConstants.RESSORT_ZUSTAENDIGKEIT,
            true,
            false),
    RESSORTLEITUNG(
            ressortDTO -> ressortDTO.ressortleitung() != null ? ressortDTO.ressortleitung().vorname() + " " + ressortDTO.ressortleitung().nachname() : "",
            RessortSchema.RESSORTLEITUNG,
            TranslationConstants.RESSORT_RESSORTLEITUNG,
            true,
            false);

    private final Function<RessortDTO, ?> getter;
    private final String schemaKey;
    private final String translationKey;
    private final boolean sortable;
    private final boolean translatable;

    RessortDTOProperties(Function<RessortDTO, ?> getter,
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
    public Function<RessortDTO, ?> getGetter() {
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

    @Override
    public boolean isTranslatable() {
        return translatable;
    }

    @Override
    public boolean isSortable() {
        return sortable;
    }
}