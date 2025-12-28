package ch.flossrennen.managementsystem.dataaccess.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = RessortSchema.TABLE_NAME)
public class Ressort implements RessortSchema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = NAME, nullable = false, unique = true, length = 100)
    private String name;

    @Column(name = BESCHREIBUNG, length = 300)
    private String beschreibung;

    @Column(name = ZUSTAENDIGKEIT, length = 300)
    private String zustaendigkeit;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = RESSORTLEITUNG)
    private Benutzer ressortleitung;
}
