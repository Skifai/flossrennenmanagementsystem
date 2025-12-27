package ch.flossrennen.managementsystem.dataaccess.dto;

import java.util.function.Function;

public interface DTOProperty<DTO> {
    Function<DTO, ?> getGetter();

    String getSchemaKey();

    String getTranslationKey();
}
