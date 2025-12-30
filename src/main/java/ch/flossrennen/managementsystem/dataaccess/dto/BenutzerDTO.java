package ch.flossrennen.managementsystem.dataaccess.dto;

import ch.flossrennen.managementsystem.dataaccess.persistence.model.BenutzerRolle;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BenutzerDTO(Long id,
                          @NotBlank(message = "{validation.required}")
                          @Size(max = 100, message = "{validation.size}")
                          String vorname,
                          @NotBlank(message = "{validation.required}")
                          @Size(max = 100, message = "{validation.size}")
                          String nachname,
                          @Size(max = 15, message = "{validation.size}")
                          String telefonnummer,
                          @NotBlank(message = "{validation.required}")
                          @Email(message = "{validation.email}")
                          @Size(max = 254, message = "{validation.size}")
                          String email,
                          @Size(max = 100, message = "{validation.size}")
                          String password,
                          @NotNull(message = "{validation.required}")
                          BenutzerRolle rolle) {

    public static BenutzerDTO createEmptyDTO() {
        return new BenutzerDTO(
                null,
                "",
                "",
                "",
                "",
                "",
                BenutzerRolle.KEINE);
    }
}
