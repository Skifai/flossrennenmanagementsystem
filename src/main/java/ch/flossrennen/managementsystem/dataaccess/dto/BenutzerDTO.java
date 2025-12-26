package ch.flossrennen.managementsystem.dataaccess.dto;

public record BenutzerDTO(Long id,
                          String vorname,
                          String nachname,
                          String telefonnummer,
                          String email,
                          String passwordhash,
                          String rolle) {

    public static BenutzerDTO createEmptyDTO() {
        return new BenutzerDTO(null, "", "", "", "", "", "");
    }
}
