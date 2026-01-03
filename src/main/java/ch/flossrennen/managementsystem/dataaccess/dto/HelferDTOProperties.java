package ch.flossrennen.managementsystem.dataaccess.dto;

import ch.flossrennen.managementsystem.dataaccess.persistence.model.HelferSchema;
import ch.flossrennen.managementsystem.util.textprovider.TranslationConstants;

import java.util.function.Function;

public enum HelferDTOProperties implements DTOProperty<HelferDTO> {
    ID(
            HelferDTO::id,
            HelferSchema.ID,
            TranslationConstants.HELFER_ID,
            true,
            false),
    VORNAME(
            HelferDTO::vorname,
            HelferSchema.VORNAME,
            TranslationConstants.HELFER_VORNAME,
            true,
            false),
    NACHNAME(
            HelferDTO::nachname,
            HelferSchema.NACHNAME,
            TranslationConstants.HELFER_NACHNAME,
            true,
            false),
    EMAIL(
            HelferDTO::email,
            HelferSchema.EMAIL,
            TranslationConstants.HELFER_EMAIL,
            true,
            false),
    TELEFONNUMMER(
            HelferDTO::telefonnummer,
            HelferSchema.TELEFONNUMMER,
            TranslationConstants.HELFER_TELEFONNUMMER,
            true,
            false),
    RESSORT(
            helferDTO -> helferDTO.ressort() != null ? helferDTO.ressort().name() : "",
            HelferSchema.RESSORT,
            TranslationConstants.HELFER_RESSORT,
            true,
            false);

    private final Function<HelferDTO, ?> getter;
    private final String schemaKey;
    private final String translationKey;
    private final boolean sortable;
    private final boolean translatable;

    HelferDTOProperties(Function<HelferDTO, ?> getter,
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
    public Function<HelferDTO, ?> getGetter() {
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