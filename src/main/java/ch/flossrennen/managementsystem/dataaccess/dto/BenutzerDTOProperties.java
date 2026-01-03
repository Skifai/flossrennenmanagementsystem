package ch.flossrennen.managementsystem.dataaccess.dto;

import ch.flossrennen.managementsystem.dataaccess.persistence.model.BenutzerSchema;
import ch.flossrennen.managementsystem.util.textprovider.TranslationConstants;

import java.util.function.Function;

public enum BenutzerDTOProperties implements DTOProperty<BenutzerDTO> {
    ID(
            BenutzerDTO::id,
            BenutzerSchema.ID,
            TranslationConstants.HELFER_ID,
            true,
            false),
    VORNAME(
            BenutzerDTO::vorname,
            BenutzerSchema.VORNAME,
            TranslationConstants.BENUTZER_VORNAME,
            true,
            false),
    NACHNAME(
            BenutzerDTO::nachname,
            BenutzerSchema.NACHNAME,
            TranslationConstants.BENUTZER_NACHNAME,
            true,
            false),
    TELEFONNUMMER(
            BenutzerDTO::telefonnummer,
            BenutzerSchema.TELEFONNUMMER,
            TranslationConstants.BENUTZER_TELEFONNUMMER,
            true,
            false),
    EMAIL(
            BenutzerDTO::email,
            BenutzerSchema.EMAIL,
            TranslationConstants.BENUTZER_EMAIL,
            true,
            false),
    ROLLE(
            dto -> dto.rolle() != null ? dto.rolle().getDisplayValue() : "",
            BenutzerSchema.ROLLE,
            TranslationConstants.BENUTZER_ROLLE,
            true,
            false),
    PASSWORD(
            BenutzerDTO::password,
            "password",
            TranslationConstants.BENUTZER_PASSWORT,
            true,
            false);

    private final Function<BenutzerDTO, ?> getter;
    private final String schemaKey;
    private final String translationKey;
    private final boolean sortable;
    private final boolean translatable;

    BenutzerDTOProperties(Function<BenutzerDTO, ?> getter, String schemaKey, String translationKey, boolean sortable, boolean translatable) {
        this.getter = getter;
        this.schemaKey = schemaKey;
        this.translationKey = translationKey;
        this.sortable = sortable;
        this.translatable = translatable;
    }

    @Override
    public Function<BenutzerDTO, ?> getGetter() {
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