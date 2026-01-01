package ch.flossrennen.managementsystem.dataaccess.dto;

import ch.flossrennen.managementsystem.dataaccess.persistence.model.LogLevel;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.LogType;

import java.time.LocalDateTime;

public record LogDTO(Long id,
                     LocalDateTime timestamp,
                     LogType type,
                     LogLevel logLevel,
                     String benutzer,
                     String message) {
}