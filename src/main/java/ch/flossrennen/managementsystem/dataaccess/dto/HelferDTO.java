package ch.flossrennen.managementsystem.dataaccess.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record HelferDTO(Long id,
                        @NotBlank(message = "{validation.required}")
                        @Size(max = 100, message = "{validation.size}")
                        String vorname,
                        @NotBlank(message = "{validation.required}")
                        @Size(max = 100, message = "{validation.size}")
                        String nachname,
                        @NotBlank(message = "{validation.required}")
                        @Email(message = "{validation.email}")
                        @Size(max = 254, message = "{validation.size}")
                        String email,
                        @NotBlank(message = "{validation.required}")
                        @Size(max = 15, message = "{validation.size}")
                        String telefonnummer,
                        @NotNull(message = "{validation.required}")
                        RessortDTO ressort) {

    public static HelferDTO createEmptyDTO() {
        return new HelferDTO(null, "", "", "", "", null);
    }
}
