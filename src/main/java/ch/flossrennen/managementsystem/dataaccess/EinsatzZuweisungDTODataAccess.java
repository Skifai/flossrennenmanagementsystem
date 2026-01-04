package ch.flossrennen.managementsystem.dataaccess;

import ch.flossrennen.managementsystem.dataaccess.dto.EinsatzZuweisungDTO;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Einsatz;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.EinsatzZuweisung;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Helfer;
import ch.flossrennen.managementsystem.dataaccess.persistence.repository.EinsatzRepository;
import ch.flossrennen.managementsystem.dataaccess.persistence.repository.EinsatzZuweisungRepository;
import ch.flossrennen.managementsystem.dataaccess.persistence.repository.HelferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Datenzugriff für Einsatz-Zuweisung-DTOs.
 */
@Component
@RequiredArgsConstructor
public class EinsatzZuweisungDTODataAccess {

    private final EinsatzZuweisungRepository repository;
    private final EinsatzRepository einsatzRepository;
    private final HelferRepository helferRepository;

    /**
     * Findet alle Zuweisungen für einen bestimmten Einsatz.
     *
     * @param einsatzId Die ID des Einsatzes.
     * @return Eine Liste von EinsatzZuweisung-DTOs.
     */
    public List<EinsatzZuweisungDTO> findByEinsatzId(Long einsatzId) {
        return repository.findByEinsatzId(einsatzId).stream()
                .map(this::toDTO)
                .toList();
    }

    private EinsatzZuweisungDTO toDTO(EinsatzZuweisung entity) {
        return new EinsatzZuweisungDTO(entity.getId(), entity.getEinsatz().getId(), entity.getHelfer().getId());
    }

    /**
     * Findet alle Zuweisungen für einen bestimmten Helfer.
     *
     * @param helferId Die ID des Helfers.
     * @return Eine Liste von EinsatzZuweisung-DTOs.
     */
    public List<EinsatzZuweisungDTO> findByHelferId(Long helferId) {
        return repository.findByHelferId(helferId).stream()
                .map(this::toDTO)
                .toList();
    }

    /**
     * Speichert eine Einsatz-Zuweisung.
     *
     * @param dto Das zu speichernde EinsatzZuweisung-DTO.
     * @return Ein Optional mit dem gespeicherten EinsatzZuweisung-DTO.
     */
    public Optional<EinsatzZuweisungDTO> save(EinsatzZuweisungDTO dto) {
        Einsatz einsatz = einsatzRepository.findById(dto.einsatzId()).orElseThrow();
        Helfer helfer = helferRepository.findById(dto.helferId()).orElseThrow();

        EinsatzZuweisung entity = new EinsatzZuweisung(dto.id(), einsatz, helfer);
        return Optional.of(toDTO(repository.save(entity)));
    }

    /**
     * Löscht eine Zuweisung basierend auf Einsatz-ID und Helfer-ID.
     *
     * @param einsatzId Die ID des Einsatzes.
     * @param helferId  Die ID des Helfers.
     */
    public void deleteByEinsatzIdAndHelferId(Long einsatzId, Long helferId) {
        repository.deleteByEinsatzIdAndHelferId(einsatzId, helferId);
    }
}
