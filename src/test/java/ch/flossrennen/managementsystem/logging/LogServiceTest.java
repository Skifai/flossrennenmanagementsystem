package ch.flossrennen.managementsystem.logging;

import ch.flossrennen.managementsystem.dataaccess.LogDTODataAccess;
import ch.flossrennen.managementsystem.dataaccess.dto.EinsatzDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.EinsatzDTOProperties;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.EinsatzStatus;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.LogLevel;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.LogType;
import ch.flossrennen.managementsystem.util.textprovider.TextProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogServiceTest {

    @Mock
    private LogDTODataAccess logDTODataAccess;

    @Mock
    private TextProvider textProvider;

    private LogService logService;

    @BeforeEach
    void setUp() {
        logService = new LogService(logDTODataAccess, textProvider);
    }

    @Test
    void log_CallsDataAccess() {
        logService.log(LogType.APPLICATION_ERROR, LogLevel.ERROR, "Test Message");
        verify(logDTODataAccess).save(any());
    }

    @Test
    void createMessage_FormatsCorrectly() {
        EinsatzDTO dto;
        dto = new EinsatzDTO(1L, "Test", "Desc", null, null, "Ort", "Mittel", 1, EinsatzStatus.ERSTELLT, null);

        when(textProvider.getTranslation(anyString())).thenReturn("Key");
        when(textProvider.getTranslation(EinsatzStatus.ERSTELLT.getTranslationKey())).thenReturn("Erstellt");

        String message = logService.createMessage("Header", dto, EinsatzDTOProperties.values());

        assertTrue(message.contains("Header"));
        assertTrue(message.contains("Erstellt"));
        verify(textProvider, atLeastOnce()).getTranslation(anyString());
    }

    @Test
    void createChangeMessage_FormatsCorrectly() {
        EinsatzDTO oldDto = new EinsatzDTO(1L, "Old", "Desc", null, null, "Ort", "Mittel", 1, EinsatzStatus.ERSTELLT, null);
        EinsatzDTO newDto = new EinsatzDTO(1L, "New", "Desc", null, null, "Ort", "Mittel", 1, EinsatzStatus.OFFEN, null);

        when(textProvider.getTranslation(anyString())).thenReturn("Key");
        when(textProvider.getTranslation(EinsatzStatus.ERSTELLT.getTranslationKey())).thenReturn("Erstellt");
        when(textProvider.getTranslation(EinsatzStatus.OFFEN.getTranslationKey())).thenReturn("Offen");

        String message = logService.createChangeMessage("Header", oldDto, newDto, EinsatzDTOProperties.values());

        assertTrue(message.contains("Header"));
        assertTrue(message.contains("Old"));
        assertTrue(message.contains("New"));
        assertTrue(message.contains("Erstellt"));
        assertTrue(message.contains("Offen"));
    }
}