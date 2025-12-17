package ch.flossrennen.managementsystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "benutzer")
public class Benutzer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 254)
    private String email;

    @Column(nullable = false, length = 100)
    private String passwordhash;

    @Column(nullable = false, length = 50)
    private String rolle;

    protected Benutzer() {}

    public Benutzer(String email, String passwordhash, String rolle) {
        this.email = email;
        this.passwordhash = passwordhash;
        this.rolle = rolle;
    }
}
