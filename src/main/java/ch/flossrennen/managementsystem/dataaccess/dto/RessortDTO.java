package ch.flossrennen.managementsystem.dataaccess.dto;

public record RessortDTO(Long id,
                          String name,
                          String beschreibung,
                          String zustaendigkeit,
                         BenutzerDTO ressortleitung) {

    public static RessortDTO createEmptyDTO() {
        return new RessortDTO(null, "", "", "", null);
    }
}
