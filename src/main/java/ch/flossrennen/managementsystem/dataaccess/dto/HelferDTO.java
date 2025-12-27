package ch.flossrennen.managementsystem.dataaccess.dto;

public record HelferDTO(Long id,
                        String vorname,
                        String nachname,
                        String email,
                        String telefonnummer,
                        String ressort) {

public static HelferDTO createEmptyDTO() {
    return new HelferDTO(null, "", "", "", "", "");
}
}
