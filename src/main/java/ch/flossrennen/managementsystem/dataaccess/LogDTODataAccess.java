package ch.flossrennen.managementsystem.dataaccess;

import ch.flossrennen.managementsystem.dataaccess.dto.LogDTO;
import ch.flossrennen.managementsystem.dataaccess.mapper.LogDTOMapper;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.LogEntry;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.LogLevel;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.LogType;
import ch.flossrennen.managementsystem.dataaccess.persistence.repository.LogRepository;
import ch.flossrennen.managementsystem.util.CheckResult;
import ch.flossrennen.managementsystem.util.textprovider.TextProvider;
import ch.flossrennen.managementsystem.util.textprovider.TranslationConstants;
import jakarta.persistence.criteria.Predicate;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementierung des Datenzugriffs für Log-DTOs.
 */
@Component
public class LogDTODataAccess implements DTODataAccess<LogDTO> {
    private final LogRepository logRepository;
    private final LogDTOMapper logDTOMapper;
    private final TextProvider textProvider;

    /**
     * Erstellt eine neue LogDTODataAccess-Instanz.
     *
     * @param logRepository Das Repository für Log-Entitäten.
     * @param logDTOMapper  Der Mapper zwischen Log-Entität und DTO.
     * @param textProvider  Der TextProvider für Übersetzungen.
     */
    public LogDTODataAccess(LogRepository logRepository, LogDTOMapper logDTOMapper, TextProvider textProvider) {
        this.logRepository = logRepository;
        this.logDTOMapper = logDTOMapper;
        this.textProvider = textProvider;
    }

    @NonNull
    @Override
    public List<LogDTO> findAll() {
        return logRepository.findAll().stream()
                .map(logDTOMapper::toDTO)
                .toList();
    }

    @NonNull
    @Override
    public Optional<LogDTO> findById(@NonNull Long id) {
        return logRepository.findById(id).map(logDTOMapper::toDTO);
    }

    @NonNull
    @Override
    @Transactional
    public CheckResult<Void> deleteById(@NonNull Long id) {
        try {
            logRepository.deleteById(id);
            logRepository.flush();
            return CheckResult.success(null, textProvider.getTranslation(TranslationConstants.SUCCESS_DELETE));
        } catch (Exception e) {
            return CheckResult.failure(textProvider.getTranslation(TranslationConstants.ERROR_DELETE));
        }
    }

    @NonNull
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CheckResult<LogDTO> save(@NonNull LogDTO logTableDTO) {
        try {
            LogEntry entity = logDTOMapper.toEntity(logTableDTO);
            LogEntry savedEntity = logRepository.saveAndFlush(entity);
            return CheckResult.success(logDTOMapper.toDTO(savedEntity), textProvider.getTranslation(TranslationConstants.SUCCESS_SAVE));
        } catch (Exception e) {
            return CheckResult.failure(textProvider.getTranslation(TranslationConstants.ERROR_SAVE));
        }
    }

    /**
     * Findet Log-DTOs basierend auf verschiedenen Filterkriterien.
     *
     * @param type Der Log-Typ.
     * @param logLevel Die Log-Ebene.
     * @param benutzer Der Benutzername.
     * @param from Startzeitpunkt.
     * @param to Endzeitpunkt.
     * @param types Eine Liste von Log-Typen.
     * @return Eine gefilterte Liste von Log-DTOs.
     */
    @NonNull
    public List<LogDTO> findAllFiltered(LogType type, LogLevel logLevel, String benutzer, LocalDateTime from, LocalDateTime to, List<LogType> types) {
        Specification<LogEntry> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (type != null) {
                predicates.add(cb.equal(root.get("type"), type));
            }
            if (logLevel != null) {
                predicates.add(cb.equal(root.get("logLevel"), logLevel));
            }
            if (benutzer != null && !benutzer.isEmpty()) {
                predicates.add(cb.equal(root.get("benutzer"), benutzer));
            }
            if (from != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("timestamp"), from));
            }
            if (to != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("timestamp"), to));
            }
            if (types != null && !types.isEmpty()) {
                predicates.add(root.get("type").in(types));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return logRepository.findAll(spec).stream()
                .map(logDTOMapper::toDTO)
                .toList();
    }

    /**
     * Findet alle eindeutigen Log-Typen in der Datenbank.
     *
     * @return Eine Liste eindeutiger Log-Typen.
     */
    @NonNull
    public List<LogType> findUniqueTypes() {
        return logRepository.findAll().stream()
                .map(LogEntry::getType)
                .distinct()
                .toList();
    }

    /**
     * Findet alle eindeutigen Log-Ebenen in der Datenbank.
     *
     * @return Eine Liste eindeutiger Log-Ebenen.
     */
    @NonNull
    public List<LogLevel> findUniqueLogLevels() {
        return logRepository.findAll().stream()
                .map(LogEntry::getLogLevel)
                .distinct()
                .toList();
    }

    /**
     * Findet alle eindeutigen Benutzernamen in der Datenbank.
     *
     * @return Eine Liste eindeutiger Benutzernamen.
     */
    @NonNull
    public List<String> findUniqueUsers() {
        return logRepository.findAll().stream()
                .map(LogEntry::getBenutzer)
                .filter(u -> u != null && !u.isEmpty())
                .distinct()
                .toList();
    }
}
