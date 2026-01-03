package ch.flossrennen.managementsystem.dataaccess.dto;

import ch.flossrennen.managementsystem.dataaccess.persistence.model.EinsatzStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record EinsatzDTO(Long id,
                         @NotBlank(message = "{validation.required}")
                         @Size(max = 100, message = "{validation.size}")
                         String name,
                         @NotBlank(message = "{validation.required}")
                         @Size(max = 300, message = "{validation.size}")
                         String beschreibung,
                         @NotNull(message = "{validation.required}")
                         LocalDateTime startzeit,
                         @NotNull(message = "{validation.required}")
                         LocalDateTime endzeit,
                         @NotBlank(message = "{validation.required}")
                         @Size(max = 150, message = "{validation.size}")
                         String ort,
                         @Size(max = 200, message = "{validation.size}")
                         String einsatzmittel,
                         @NotNull(message = "{validation.required}")
                         @Min(value = 1, message = "{validation.min}")
                         Integer benoetigte_helfer,
                         @NotNull(message = "{validation.required}")
                         EinsatzStatus status,
                         @NotNull(message = "{validation.required}")
                         RessortDTO ressort) {

    public static EinsatzDTO createEmptyDTO() {
        return new EinsatzDTO(
                null,
                "",
                "",
                null,
                null,
                "",
                "",
                1,
                EinsatzStatus.ERSTELLT,
                null);
    }
}