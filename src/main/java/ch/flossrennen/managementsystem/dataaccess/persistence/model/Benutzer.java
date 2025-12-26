package ch.flossrennen.managementsystem.dataaccess.persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = BenutzerSchema.TABLE_NAME)
public class Benutzer implements BenutzerSchema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = VORNAME, nullable = false, length = 100)
    private String vorname;

    @Column(name = NACHNAME, nullable = false, length = 100)
    private String nachname;

    @Column(name = TELEFONNUMMER, nullable = false, unique = true, length = 15)
    private String telefonnummer;

    @Column(name = EMAIL, nullable = false, unique = true, length = 254)
    private String email;

    @Column(name = PASSWORDHASH, nullable = false, length = 100)
    private String passwordhash;

    @Column(name = ROLLE, nullable = false, length = 50)
    private String rolle;

    protected Benutzer() {}

    public Benutzer(Long id, String vorname, String nachname, String telefonnummer, String email, String passwordhash, String rolle) {
        this.id = id;
        this.vorname = vorname;
        this.nachname = nachname;
        this.telefonnummer = telefonnummer;
        this.email = email;
        this.passwordhash = passwordhash;
        this.rolle = rolle;
    }
}
