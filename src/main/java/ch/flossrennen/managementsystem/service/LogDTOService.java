package ch.flossrennen.managementsystem.service;

import ch.flossrennen.managementsystem.dataaccess.LogDTODataAccess;
import ch.flossrennen.managementsystem.dataaccess.dto.LogDTO;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.LogLevel;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.LogType;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service für die Verwaltung von Log-DTOs.
 */
@Service
public class LogDTOService implements DTOService<LogDTO> {

    private final LogDTODataAccess logDTODataAccess;

    /**
     * Erstellt einen neuen LogDTOService.
     *
     * @param logDTODataAccess Der Datenzugriff für Log-DTOs.
     */
    public LogDTOService(LogDTODataAccess logDTODataAccess) {
        this.logDTODataAccess = logDTODataAccess;
    }

    @Override
    @NonNull
    public List<LogDTO> findAll() {
        return logDTODataAccess.findAll();
    }

    @Override
    @NonNull
    public Optional<LogDTO> findById(@NonNull Long id) {
        return logDTODataAccess.findById(id);
    }

    /**
     * Findet Log-DTOs basierend auf verschiedenen Filterkriterien.
     *
     * @param type     Der Log-Typ.
     * @param logLevel Die Log-Ebene.
     * @param benutzer Der Benutzername.
     * @param from     Startzeitpunkt.
     * @param to       Endzeitpunkt.
     * @param types    Eine Liste von Log-Typen.
     * @return Eine gefilterte Liste von Log-DTOs.
     */
    @NonNull
    public List<LogDTO> findAllFiltered(LogType type, LogLevel logLevel, String benutzer, LocalDateTime from, LocalDateTime to, List<LogType> types) {
        return logDTODataAccess.findAllFiltered(type, logLevel, benutzer, from, to, types);
    }

    /**
     * Findet alle eindeutigen Log-Typen.
     *
     * @return Eine Liste eindeutiger Log-Typen.
     */
    @NonNull
    public List<LogType> findUniqueTypes() {
        return logDTODataAccess.findUniqueTypes();
    }

    /**
     * Findet alle eindeutigen Log-Ebenen.
     *
     * @return Eine Liste eindeutiger Log-Ebenen.
     */
    @NonNull
    public List<LogLevel> findUniqueLogLevels() {
        return logDTODataAccess.findUniqueLogLevels();
    }

    /**
     * Findet alle eindeutigen Benutzernamen in den Logs.
     *
     * @return Eine Liste eindeutiger Benutzernamen.
     */
    @NonNull
    public List<String> findUniqueUsers() {
        return logDTODataAccess.findUniqueUsers();
    }
}