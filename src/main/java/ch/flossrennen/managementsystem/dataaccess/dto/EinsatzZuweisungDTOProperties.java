package ch.flossrennen.managementsystem.dataaccess.dto;

import ch.flossrennen.managementsystem.dataaccess.persistence.model.EinsatzZuweisungSchema;
import ch.flossrennen.managementsystem.util.textprovider.TranslationConstants;

import java.util.function.Function;

public enum EinsatzZuweisungDTOProperties implements DTOProperty<EinsatzZuweisungDTO> {
    ID(
            EinsatzZuweisungDTO::id,
            EinsatzZuweisungSchema.ID,
            TranslationConstants.HELFER_ID,
            true,
            false),
    EINSATZ_ID(
            EinsatzZuweisungDTO::einsatzId,
            EinsatzZuweisungSchema.EINSATZ_ID,
            TranslationConstants.EINSATZ_ID,
            true,
            false),
    HELFER_ID(
            EinsatzZuweisungDTO::helferId,
            EinsatzZuweisungSchema.HELFER_ID,
            TranslationConstants.HELFER_ID,
            true,
            false);

    private final Function<EinsatzZuweisungDTO, ?> getter;
    private final String schemaKey;
    private final String translationKey;
    private final boolean sortable;
    private final boolean translatable;

    EinsatzZuweisungDTOProperties(Function<EinsatzZuweisungDTO, ?> getter,
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
    public Function<EinsatzZuweisungDTO, ?> getGetter() {
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