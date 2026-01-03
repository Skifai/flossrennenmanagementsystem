package ch.flossrennen.managementsystem.dataaccess.dto;

import ch.flossrennen.managementsystem.dataaccess.persistence.model.EinsatzSchema;
import ch.flossrennen.managementsystem.util.textprovider.TranslationConstants;

import java.util.function.Function;

public enum EinsatzDTOProperties implements DTOProperty<EinsatzDTO> {
    ID(
            EinsatzDTO::id,
            EinsatzSchema.ID,
            TranslationConstants.EINSATZ_ID,
            true,
            false),
    NAME(
            EinsatzDTO::name,
            EinsatzSchema.NAME,
            TranslationConstants.EINSATZ_NAME,
            true,
            false),
    BESCHREIBUNG(
            EinsatzDTO::beschreibung,
            EinsatzSchema.BESCHREIBUNG,
            TranslationConstants.EINSATZ_BESCHREIBUNG,
            true,
            false),
    STARTZEIT(
            EinsatzDTO::startzeit,
            EinsatzSchema.STARTZEIT,
            TranslationConstants.EINSATZ_STARTZEIT,
            false,
            false),
    ENDZEIT(
            EinsatzDTO::endzeit,
            EinsatzSchema.ENDZEIT,
            TranslationConstants.EINSATZ_ENDZEIT,
            false,
            false),
    ORT(
            EinsatzDTO::ort,
            EinsatzSchema.ORT,
            TranslationConstants.EINSATZ_ORT,
            true,
            false),
    EINSATZMITTEL(
            EinsatzDTO::einsatzmittel,
            EinsatzSchema.EINSATZMITTEL,
            TranslationConstants.EINSATZ_MITTEL,
            true,
            false),
    BENOETIGTE_HELFER(
            EinsatzDTO::benoetigte_helfer,
            EinsatzSchema.BENOETIGTE_HELFER,
            TranslationConstants.EINSATZ_BENOETIGTE_HELFER,
            true,
            false),
    STATUS(
            dto -> dto.status() != null ? dto.status().getTranslationKey() : "",
            EinsatzSchema.STATUS,
            TranslationConstants.EINSATZ_STATUS,
            true,
            true),
    RESSORT(
            einsatzDTO -> einsatzDTO.ressort() != null ? einsatzDTO.ressort().name() : "",
            EinsatzSchema.RESSORT,
            TranslationConstants.EINSATZ_RESSORT,
            true,
            false);

    private final Function<EinsatzDTO, ?> getter;
    private final String schemaKey;
    private final String translationKey;
    private final boolean sortable;
    private final boolean translatable;

    EinsatzDTOProperties(Function<EinsatzDTO, ?> getter,
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
    public Function<EinsatzDTO, ?> getGetter() {
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