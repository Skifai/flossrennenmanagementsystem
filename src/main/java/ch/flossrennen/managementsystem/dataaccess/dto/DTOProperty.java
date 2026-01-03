package ch.flossrennen.managementsystem.dataaccess.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

public interface DTOProperty<DTO> {
    Function<DTO, ?> getGetter();

    default Object getFormattedValue(DTO dto, Function<String, String> translator, DateTimeFormatter dateTimeFormatter) {
        Object value = getGetter().apply(dto);
        if (isTranslatable() && value instanceof String translationKey && translator != null) {
            return translator.apply(translationKey);
        }
        if (value instanceof LocalDateTime localDateTime && dateTimeFormatter != null) {
            return localDateTime.format(dateTimeFormatter);
        }
        return value;
    }

    String getSchemaKey();

    String getTranslationKey();

    boolean isTranslatable();

    boolean isSortable();
}