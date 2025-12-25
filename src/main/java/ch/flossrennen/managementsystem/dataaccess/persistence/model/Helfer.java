package ch.flossrennen.managementsystem.dataaccess.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "helfer")
public class Helfer implements Persistable<Long>, HelferSchema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = VORNAME, nullable = false, length = 100)
    private String vorname;

    @Column(name = NACHNAME, nullable = false, length = 100)
    private String nachname;

    @Column(name = EMAIL, nullable = false, unique = true, length = 254)
    private String email;

    @Column(name = TELEFONNUMMER, nullable = false, length = 15)
    private String telefonnummer;

    @Override
    public boolean isNew() {
        return id == null;
    }
}