package ch.flossrennen.managementsystem.logging;

import ch.flossrennen.managementsystem.dataaccess.LogDTODataAccess;
import ch.flossrennen.managementsystem.dataaccess.dto.DTOProperty;
import ch.flossrennen.managementsystem.dataaccess.dto.LogDTO;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.LogLevel;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.LogType;
import ch.flossrennen.managementsystem.util.textprovider.StringConstants;
import ch.flossrennen.managementsystem.util.textprovider.TextProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Service für die Protokollierung von Systemereignissen und Benutzeraktionen.
 */
@Service
public class LogService {

    private final LogDTODataAccess logDTODataAccess;
    private final TextProvider textProvider;

    /**
     * Erstellt einen neuen LogService.
     *
     * @param logDTODataAccess Der Datenzugriff für Logs.
     * @param textProvider     Der TextProvider für Übersetzungen.
     */
    public LogService(LogDTODataAccess logDTODataAccess, TextProvider textProvider) {
        this.logDTODataAccess = logDTODataAccess;
        this.textProvider = textProvider;
    }

    /**
     * Erstellt einen Log-Eintrag.
     *
     * @param type Der Typ des Logs.
     * @param logLevel Die Log-Ebene.
     * @param message Die Log-Nachricht.
     */
    public void log(LogType type, LogLevel logLevel, String message) {
        String benutzer = getCurrentUser();
        LogDTO logDTO = new LogDTO(null, LocalDateTime.now(), type, logLevel, benutzer, message);
        logDTODataAccess.save(logDTO);
    }

    private String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return StringConstants.SYSTEM;
    }

    /**
     * Erstellt eine Nachricht, die die Änderungen zwischen zwei DTOs beschreibt.
     *
     * @param header Der Kopf der Nachricht.
     * @param oldDTO Das ursprüngliche DTO.
     * @param newDTO Das aktualisierte DTO.
     * @param properties Die zu vergleichenden Eigenschaften.
     * @param <DTO> Der DTO-Typ.
     * @param <PROPERTY> Der Property-Typ.
     * @return Eine formatierte Nachricht mit den Änderungen.
     */
    public <DTO, PROPERTY extends DTOProperty<DTO>> String createChangeMessage(String header, DTO oldDTO, DTO newDTO, PROPERTY[] properties) {
        StringBuilder stringBuilder = new StringBuilder(header);
        for (PROPERTY property : properties) {
            Object oldValue = property.getFormattedValue(oldDTO, textProvider::getTranslation, null);
            Object newValue = property.getFormattedValue(newDTO, textProvider::getTranslation, null);

            if (!Objects.equals(oldValue, newValue)) {
                stringBuilder.append(StringConstants.LINE_BREAK);
                stringBuilder.append(textProvider.getTranslation(property.getTranslationKey()))
                        .append(StringConstants.COLON_SPACE)
                        .append(oldValue != null ? oldValue : StringConstants.DASH)
                        .append(StringConstants.ARROW_SPACE)
                        .append(newValue != null ? newValue : StringConstants.DASH);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * Erstellt eine Nachricht, die alle relevanten Eigenschaften eines DTOs auflistet.
     *
     * @param header Der Kopf der Nachricht.
     * @param dto Das DTO.
     * @param properties Die aufzulistenden Eigenschaften.
     * @param <DTO> Der DTO-Typ.
     * @param <PROPERTY> Der Property-Typ.
     * @return Eine formatierte Nachricht mit den DTO-Details.
     */
    public <DTO, PROPERTY extends DTOProperty<DTO>> String createMessage(String header, DTO dto, PROPERTY[] properties) {
        StringBuilder stringBuilder = new StringBuilder(header);
        for (PROPERTY property : properties) {
            Object value = property.getFormattedValue(dto, textProvider::getTranslation, null);
            if (value != null && !value.toString().isEmpty()) {
                stringBuilder.append(StringConstants.LINE_BREAK);
                stringBuilder.append(textProvider.getTranslation(property.getTranslationKey()))
                        .append(StringConstants.COLON_SPACE)
                        .append(value);
            }
        }
        return stringBuilder.toString();
    }
}