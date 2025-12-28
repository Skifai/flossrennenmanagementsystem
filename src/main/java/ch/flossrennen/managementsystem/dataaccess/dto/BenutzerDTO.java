package ch.flossrennen.managementsystem.dataaccess.dto;

import ch.flossrennen.managementsystem.dataaccess.persistence.model.BenutzerRolle;

public record BenutzerDTO(Long id,
                          String vorname,
                          String nachname,
                          String telefonnummer,
                          String email,
                          String passwordhash,
                          BenutzerRolle rolle) {

    public static BenutzerDTO createEmptyDTO() {
        return new BenutzerDTO(null, "", "", "", "", "", BenutzerRolle.RESSORTLEITER);
    }
}
