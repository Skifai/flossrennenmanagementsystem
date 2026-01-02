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

@Service
public class LogDTOService implements DTOService<LogDTO> {

    private final LogDTODataAccess logDTODataAccess;

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

    @NonNull
    public List<LogDTO> findAllFiltered(LogType type, LogLevel logLevel, String benutzer, LocalDateTime from, LocalDateTime to, List<LogType> types) {
        return logDTODataAccess.findAllFiltered(type, logLevel, benutzer, from, to, types);
    }

    @NonNull
    public List<LogType> findUniqueTypes() {
        return logDTODataAccess.findUniqueTypes();
    }

    @NonNull
    public List<LogLevel> findUniqueLogLevels() {
        return logDTODataAccess.findUniqueLogLevels();
    }

    @NonNull
    public List<String> findUniqueUsers() {
        return logDTODataAccess.findUniqueUsers();
    }
}