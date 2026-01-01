package ch.flossrennen.managementsystem.dataaccess.dto;

import ch.flossrennen.managementsystem.dataaccess.persistence.model.BenutzerSchema;
import ch.flossrennen.managementsystem.util.textprovider.TranslationConstants;

import java.util.function.Function;

public enum BenutzerDTOProperties implements DTOProperty<BenutzerDTO> {
    ID(BenutzerDTO::id, BenutzerSchema.ID, TranslationConstants.HELFER_ID),
    VORNAME(BenutzerDTO::vorname, BenutzerSchema.VORNAME, TranslationConstants.BENUTZER_VORNAME),
    NACHNAME(BenutzerDTO::nachname, BenutzerSchema.NACHNAME, TranslationConstants.BENUTZER_NACHNAME),
    TELEFONNUMMER(BenutzerDTO::telefonnummer, BenutzerSchema.TELEFONNUMMER, TranslationConstants.BENUTZER_TELEFONNUMMER),
    EMAIL(BenutzerDTO::email, BenutzerSchema.EMAIL, TranslationConstants.BENUTZER_EMAIL),
    ROLLE(BenutzerDTO::rolle, BenutzerSchema.ROLLE, TranslationConstants.BENUTZER_ROLLE),
    PASSWORD(BenutzerDTO::password, "password", TranslationConstants.BENUTZER_PASSWORT);

    private final Function<BenutzerDTO, ?> getter;
    private final String schemaKey;
    private final String translationKey;

    BenutzerDTOProperties(Function<BenutzerDTO, ?> getter, String schemaKey, String translationKey) {
        this.getter = getter;
        this.schemaKey = schemaKey;
        this.translationKey = translationKey;
    }

    public Function<BenutzerDTO, ?> getGetter() {
        return getter;
    }

    public String getSchemaKey() {
        return schemaKey;
    }

    public String getTranslationKey() {
        return translationKey;
    }
}
