package ch.flossrennen.managementsystem.dataaccess.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RessortDTO(Long id,
                         @NotBlank(message = "{validation.required}")
                         @Size(max = 100, message = "{validation.size}")
                         String name,
                         @Size(max = 300, message = "{validation.size}")
                         String beschreibung,
                         @Size(max = 300, message = "{validation.size}")
                         String zustaendigkeit,
                         BenutzerDTO ressortleitung) {

    public static RessortDTO createEmptyDTO() {
        return new RessortDTO(
                null,
                "",
                "",
                "",
                null);
    }
}
