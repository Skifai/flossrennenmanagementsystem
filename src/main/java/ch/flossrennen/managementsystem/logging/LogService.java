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

@Service
public class LogService {

    private final LogDTODataAccess logDTODataAccess;
    private final TextProvider textProvider;

    public LogService(LogDTODataAccess logDTODataAccess, TextProvider textProvider) {
        this.logDTODataAccess = logDTODataAccess;
        this.textProvider = textProvider;
    }

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

    public <DTO, PROPERTY extends DTOProperty<DTO>> String createChangeMessage(DTO oldDTO, DTO newDTO, PROPERTY[] properties) {
        StringBuilder stringBuilder = new StringBuilder();
        for (PROPERTY property : properties) {
            Object oldValue = property.getGetter().apply(oldDTO);
            Object newValue = property.getGetter().apply(newDTO);

            if (!Objects.equals(oldValue, newValue)) {
                if (!stringBuilder.isEmpty()) {
                    stringBuilder.append(StringConstants.LINE_BREAK);
                }
                stringBuilder.append(textProvider.getTranslation(property.getTranslationKey()))
                        .append(StringConstants.COLON_SPACE)
                        .append(oldValue != null ? oldValue : StringConstants.DASH)
                        .append(StringConstants.ARROW_SPACE)
                        .append(newValue != null ? newValue : StringConstants.DASH);
            }
        }
        return stringBuilder.toString();
    }

    public <DTO, PROPERTY extends DTOProperty<DTO>> String createCreationMessage(DTO dto, PROPERTY[] properties) {
        StringBuilder stringBuilder = new StringBuilder();
        for (PROPERTY property : properties) {
            Object value = property.getGetter().apply(dto);
            if (value != null && !value.toString().isEmpty()) {
                if (!stringBuilder.isEmpty()) {
                    stringBuilder.append(StringConstants.LINE_BREAK);
                }
                stringBuilder.append(textProvider.getTranslation(property.getTranslationKey()))
                        .append(StringConstants.COLON_SPACE)
                        .append(value);
            }
        }
        return stringBuilder.toString();
    }
}