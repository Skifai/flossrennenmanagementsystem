package ch.flossrennen.managementsystem.service;

import ch.flossrennen.managementsystem.dataaccess.EinsatzZuweisungDTODataAccess;
import ch.flossrennen.managementsystem.dataaccess.dto.EinsatzZuweisungDTO;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Einsatz;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.EinsatzStatus;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.EinsatzZuweisung;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Helfer;
import ch.flossrennen.managementsystem.dataaccess.persistence.repository.EinsatzRepository;
import ch.flossrennen.managementsystem.dataaccess.persistence.repository.EinsatzZuweisungRepository;
import ch.flossrennen.managementsystem.dataaccess.persistence.repository.HelferRepository;
import ch.flossrennen.managementsystem.util.CheckResult;
import ch.flossrennen.managementsystem.util.textprovider.TextProvider;
import ch.flossrennen.managementsystem.util.textprovider.TranslationConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EinsatzZuweisungDTOService {

    private final EinsatzZuweisungDTODataAccess dataAccess;
    private final EinsatzZuweisungRepository repository;
    private final EinsatzRepository einsatzRepository;
    private final HelferRepository helferRepository;
    private final TextProvider textProvider;

    public List<EinsatzZuweisungDTO> findByEinsatzId(Long einsatzId) {
        return dataAccess.findByEinsatzId(einsatzId);
    }

    @Transactional
    public CheckResult<EinsatzZuweisungDTO> save(EinsatzZuweisungDTO dto) {
        Einsatz einsatz = einsatzRepository.findById(dto.einsatzId()).orElseThrow();

        List<EinsatzZuweisung> overlapping = repository.findOverlappingZuweisungen(
                dto.helferId(),
                dto.einsatzId(),
                einsatz.getStartzeit(),
                einsatz.getEndzeit()
        );

        if (!overlapping.isEmpty()) {
            Helfer helfer = helferRepository.findById(dto.helferId()).orElseThrow();
            String helferName = helfer.getVorname() + " " + helfer.getNachname();
            return CheckResult.failure(textProvider.getTranslation(TranslationConstants.VALIDATION_EINSATZ_HELFER_OVERLAP, helferName));
        }

        CheckResult<EinsatzZuweisungDTO> result = dataAccess.save(dto)
                .map(CheckResult::success)
                .orElse(CheckResult.failure(textProvider.getTranslation(TranslationConstants.ERROR_SAVE)));

        if (result.isSuccess()) {
            updateEinsatzStatusIfFull(dto.einsatzId());
        }

        return result;
    }

    private void updateEinsatzStatusIfFull(Long einsatzId) {
        Einsatz einsatz = einsatzRepository.findById(einsatzId).orElseThrow();
        long currentHelferCount = repository.findByEinsatzId(einsatzId).size();

        if (currentHelferCount >= einsatz.getBenoetigte_helfer()) {
            if (einsatz.getStatus() != EinsatzStatus.ABGESCHLOSSEN) {
                einsatz.setStatus(EinsatzStatus.ABGESCHLOSSEN);
                einsatzRepository.save(einsatz);
            }
        } else {
            if (einsatz.getStatus() == EinsatzStatus.ABGESCHLOSSEN) {
                einsatz.setStatus(EinsatzStatus.OFFEN);
                einsatzRepository.save(einsatz);
            }
        }
    }

    @Transactional
    public void deleteByEinsatzIdAndHelferId(Long einsatzId, Long helferId) {
        dataAccess.deleteByEinsatzIdAndHelferId(einsatzId, helferId);
        updateEinsatzStatusIfFull(einsatzId);
    }

    public List<Long> findAllHelferIdsWithOverlappingAssignments(Long currentEinsatzId, LocalDateTime start, LocalDateTime end) {
        return repository.findAll().stream()
                .filter(zuweisung -> !zuweisung.getEinsatz().getId().equals(currentEinsatzId))
                .filter(zuweisung ->
                        !(zuweisung.getEinsatz().getEndzeit().isBefore(start) ||
                                zuweisung.getEinsatz().getEndzeit().isEqual(start) ||
                                zuweisung.getEinsatz().getStartzeit().isAfter(end) ||
                                zuweisung.getEinsatz().getStartzeit().isEqual(end)))
                .map(zuweisung -> zuweisung.getHelfer().getId())
                .distinct()
                .toList();
    }
}
