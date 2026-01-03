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

@Component
@RequiredArgsConstructor
public class EinsatzZuweisungDTODataAccess {

    private final EinsatzZuweisungRepository repository;
    private final EinsatzRepository einsatzRepository;
    private final HelferRepository helferRepository;

    public List<EinsatzZuweisungDTO> findByEinsatzId(Long einsatzId) {
        return repository.findByEinsatzId(einsatzId).stream()
                .map(this::toDTO)
                .toList();
    }

    private EinsatzZuweisungDTO toDTO(EinsatzZuweisung entity) {
        return new EinsatzZuweisungDTO(entity.getId(), entity.getEinsatz().getId(), entity.getHelfer().getId());
    }

    public List<EinsatzZuweisungDTO> findByHelferId(Long helferId) {
        return repository.findByHelferId(helferId).stream()
                .map(this::toDTO)
                .toList();
    }

    public Optional<EinsatzZuweisungDTO> save(EinsatzZuweisungDTO dto) {
        Einsatz einsatz = einsatzRepository.findById(dto.einsatzId()).orElseThrow();
        Helfer helfer = helferRepository.findById(dto.helferId()).orElseThrow();

        EinsatzZuweisung entity = new EinsatzZuweisung(dto.id(), einsatz, helfer);
        return Optional.of(toDTO(repository.save(entity)));
    }

    public void deleteByEinsatzIdAndHelferId(Long einsatzId, Long helferId) {
        repository.deleteByEinsatzIdAndHelferId(einsatzId, helferId);
    }
}
