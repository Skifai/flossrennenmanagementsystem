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
@Table(name = EinsatzZuweisungSchema.TABLE_NAME)
public class EinsatzZuweisung implements EinsatzZuweisungSchema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = EINSATZ_ID, nullable = false)
    private Einsatz einsatz;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = HELFER_ID, nullable = false)
    private Helfer helfer;
}