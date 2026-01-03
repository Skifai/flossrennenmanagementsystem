package ch.flossrennen.managementsystem.dataaccess.dto;

import ch.flossrennen.managementsystem.dataaccess.persistence.model.EinsatzSchema;
import ch.flossrennen.managementsystem.util.textprovider.TranslationConstants;

import java.util.function.Function;

public enum EinsatzDTOProperties implements DTOProperty<EinsatzDTO> {
    ID(EinsatzDTO::id, EinsatzSchema.ID, TranslationConstants.EINSATZ_ID),
    NAME(EinsatzDTO::name, EinsatzSchema.NAME, TranslationConstants.EINSATZ_NAME),
    BESCHREIBUNG(EinsatzDTO::beschreibung, EinsatzSchema.BESCHREIBUNG, TranslationConstants.EINSATZ_BESCHREIBUNG),
    STARTZEIT(EinsatzDTO::startzeit, EinsatzSchema.STARTZEIT, TranslationConstants.EINSATZ_STARTZEIT),
    ENDZEIT(EinsatzDTO::endzeit, EinsatzSchema.ENDZEIT, TranslationConstants.EINSATZ_ENDZEIT),
    ORT(EinsatzDTO::ort, EinsatzSchema.ORT, TranslationConstants.EINSATZ_ORT),
    EINSATZMITTEL(EinsatzDTO::einsatzmittel, EinsatzSchema.EINSATZMITTEL, TranslationConstants.EINSATZ_MITTEL),
    BENOETIGTE_HELFER(EinsatzDTO::benoetigte_helfer, EinsatzSchema.BENOETIGTE_HELFER, TranslationConstants.EINSATZ_BENOETIGTE_HELFER),
    STATUS(EinsatzDTO::status, EinsatzSchema.STATUS, TranslationConstants.EINSATZ_STATUS),
    RESSORT(einsatzDTO -> einsatzDTO.ressort() != null ? einsatzDTO.ressort().name() : "", EinsatzSchema.RESSORT, TranslationConstants.EINSATZ_RESSORT);

    private final Function<EinsatzDTO, ?> getter;
    private final String schemaKey;
    private final String translationKey;

    EinsatzDTOProperties(Function<EinsatzDTO, ?> getter, String schemaKey, String translationKey) {
        this.getter = getter;
        this.schemaKey = schemaKey;
        this.translationKey = translationKey;
    }

    public Function<EinsatzDTO, ?> getGetter() {
        return getter;
    }

    public String getSchemaKey() {
        return schemaKey;
    }

    public String getTranslationKey() {
        return translationKey;
    }
}