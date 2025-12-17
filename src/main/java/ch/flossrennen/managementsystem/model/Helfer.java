package ch.flossrennen.managementsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "helfer")
public class Helfer {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 100)
    private String vorname;

    @Column(nullable = false, length = 100)
    private String nachname;

    @Column(nullable = false, unique = true, length = 254)
    private String email;

    @Column(nullable = false, length = 15)
    private String telefonnummer;

    protected Helfer() {}
}