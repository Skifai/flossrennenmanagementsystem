package ch.flossrennen.managementsystem.dataaccess.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = EinsatzSchema.TABLE_NAME)
public class Einsatz implements EinsatzSchema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = NAME, nullable = false, length = 100)
    private String name;

    @Column(name = BESCHREIBUNG, nullable = false, length = 300)
    private String beschreibung;

    @Column(name = STARTZEIT, nullable = false)
    private LocalDateTime startzeit;

    @Column(name = ENDZEIT, nullable = false)
    private LocalDateTime endzeit;

    @Column(name = ORT, nullable = false, length = 150)
    private String ort;

    @Column(name = EINSATZMITTEL, length = 200)
    private String einsatzmittel;

    @Column(name = BENOETIGTE_HELFER, nullable = false)
    private Integer benoetigte_helfer;

    @Enumerated(EnumType.STRING)
    @Column(name = STATUS, nullable = false, length = 50)
    private EinsatzStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = RESSORT, nullable = false)
    private Ressort ressort;
}
