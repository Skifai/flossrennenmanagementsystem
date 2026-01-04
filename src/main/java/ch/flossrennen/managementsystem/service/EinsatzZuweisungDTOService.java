package ch.flossrennen.managementsystem.service;

import ch.flossrennen.managementsystem.dataaccess.EinsatzZuweisungDTODataAccess;
import ch.flossrennen.managementsystem.dataaccess.dto.EinsatzDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.EinsatzZuweisungDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.HelferDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.HelferSelectionDTO;
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
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service für die Verwaltung von Einsatz-Zuweisungen.
 */
@Service
@RequiredArgsConstructor
public class EinsatzZuweisungDTOService {

    private final EinsatzZuweisungDTODataAccess dataAccess;
    private final EinsatzZuweisungRepository repository;
    private final EinsatzRepository einsatzRepository;
    private final HelferRepository helferRepository;
    private final DTOService<EinsatzDTO> einsatzDTOService;
    private final DTOService<HelferDTO> helferDTOService;
    private final TextProvider textProvider;

    /**
     * Findet alle Einsätze, die für eine Zuweisung zur Verfügung stehen (Status nicht ERSTELLT).
     *
     * @return Eine Liste von Einsatz-DTOs.
     */
    public List<EinsatzDTO> findAllEinsatzForZuweisung() {
        return einsatzDTOService.findAll().stream()
                .filter(einsatzDTO -> einsatzDTO.status() != EinsatzStatus.ERSTELLT)
                .toList();
    }

    /**
     * Gibt die Helfer-Selektion für einen bestimmten Einsatz zurück.
     * Teilt Helfer in bereits zugewiesene und verfügbare (nicht überlappende) Helfer auf.
     *
     * @param einsatz Das Einsatz-DTO, für das die Helfer-Selektion abgerufen werden soll.
     * @return Ein HelferSelectionDTO mit zugewiesenen und verfügbaren Helfern.
     */
    public HelferSelectionDTO getHelferSelection(EinsatzDTO einsatz) {
        if (einsatz == null) {
            return new HelferSelectionDTO(List.of(), List.of());
        }

        List<EinsatzZuweisungDTO> zuweisungen = findByEinsatzId(einsatz.id());
        Set<Long> zugewieseneIds = zuweisungen.stream().map(EinsatzZuweisungDTO::helferId).collect(Collectors.toSet());

        List<Long> overlappingHelferIds = findAllHelferIdsWithOverlappingAssignments(
                einsatz.id(), einsatz.startzeit(), einsatz.endzeit());

        List<HelferDTO> allHelfer = helferDTOService.findAll();

        List<HelferDTO> zugewieseneHelfer = allHelfer.stream()
                .filter(helferDTO -> zugewieseneIds.contains(helferDTO.id()))
                .toList();

        List<HelferDTO> verfuegbareHelfer = allHelfer.stream()
                .filter(helferDTO -> !zugewieseneIds.contains(helferDTO.id()))
                .filter(helferDTO -> !overlappingHelferIds.contains(helferDTO.id()))
                .toList();

        return new HelferSelectionDTO(zugewieseneHelfer, verfuegbareHelfer);
    }

    /**
     * Findet alle Zuweisungen für einen bestimmten Einsatz.
     *
     * @param einsatzId Die ID des Einsatzes.
     * @return Eine Liste von EinsatzZuweisung-DTOs.
     */
    public List<EinsatzZuweisungDTO> findByEinsatzId(Long einsatzId) {
        return dataAccess.findByEinsatzId(einsatzId);
    }

    /**
     * Findet alle Helfer-IDs, die bereits Zuweisungen haben, die mit dem angegebenen Zeitraum überlappen.
     *
     * @param currentEinsatzId Die ID des aktuellen Einsatzes (wird ignoriert).
     * @param start Die Startzeit des Zeitraums.
     * @param end Die Endzeit des Zeitraums.
     * @return Eine Liste von Helfer-IDs mit überlappenden Zuweisungen.
     */
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

    /**
     * Berechnet die Anzahl der noch fehlenden Helfer für einen Einsatz.
     *
     * @param einsatz Das Einsatz-DTO.
     * @return Die Anzahl der fehlenden Helfer.
     */
    public int getMissingHelferCount(EinsatzDTO einsatz) {
        if (einsatz == null) return 0;
        int assignedCount = findByEinsatzId(einsatz.id()).size();
        return einsatz.benoetigte_helfer() - assignedCount;
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

    /**
     * Speichert eine Einsatz-Zuweisung.
     * Prüft auf Überlappungen und aktualisiert den Einsatzstatus.
     *
     * @param dto Das zu speichernde EinsatzZuweisung-DTO.
     * @return Ein CheckResult mit dem Ergebnis der Operation.
     */
    @Transactional
    public CheckResult<EinsatzZuweisungDTO> save(EinsatzZuweisungDTO dto) {
        Einsatz einsatz = einsatzRepository.findById(dto.einsatzId()).orElseThrow();

        if (einsatz.getStatus() == EinsatzStatus.ABGESCHLOSSEN) {
            return CheckResult.failure(textProvider.getTranslation(TranslationConstants.VALIDATION_EINSATZ_FULL));
        }

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

    /**
     * Löscht eine Einsatz-Zuweisung anhand der Einsatz-ID und Helfer-ID.
     * Aktualisiert anschliessend den Einsatzstatus.
     *
     * @param einsatzId Die ID des Einsatzes.
     * @param helferId  Die ID des Helfers.
     */
    @Transactional
    public void deleteByEinsatzIdAndHelferId(Long einsatzId, Long helferId) {
        dataAccess.deleteByEinsatzIdAndHelferId(einsatzId, helferId);
        updateEinsatzStatusIfFull(einsatzId);
    }
}
