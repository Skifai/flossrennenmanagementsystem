package ch.flossrennen.managementsystem.dataaccess.dto;

import java.time.LocalDateTime;

public record LogDTO(Long id,
                     LocalDateTime timestamp,
                     String type,
                     String logLevel,
                     String benutzer,
                     String message) {
}