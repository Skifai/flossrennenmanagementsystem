package ch.flossrennen.managementsystem.dataaccess;

import ch.flossrennen.managementsystem.dataaccess.dto.BenutzerDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.BenutzerDTOProperties;
import ch.flossrennen.managementsystem.dataaccess.mapper.DTOMapper;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Benutzer;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.LogLevel;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.LogType;
import ch.flossrennen.managementsystem.dataaccess.persistence.repository.BenutzerRepository;
import ch.flossrennen.managementsystem.logging.LogService;
import ch.flossrennen.managementsystem.util.CheckResult;
import ch.flossrennen.managementsystem.util.textprovider.TextProvider;
import ch.flossrennen.managementsystem.util.textprovider.TranslationConstants;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementierung des Datenzugriffs für Benutzer-DTOs.
 */
@Component
public class BenutzerDTODataAccess implements DTODataAccess<BenutzerDTO> {
    private final BenutzerRepository benutzerRepository;
    private final DTOMapper<Benutzer, BenutzerDTO> benutzerDTOMapper;
    private final TextProvider textProvider;
    private final LogService logService;

    /**
     * Erstellt eine neue BenutzerDTODataAccess-Instanz.
     *
     * @param benutzerRepository Das Repository für Benutzer-Entitäten.
     * @param benutzerDTOMapper  Der Mapper zwischen Benutzer-Entität und DTO.
     * @param textProvider       Der TextProvider für Übersetzungen.
     * @param logService         Der Service für Protokollierungen.
     */
    public BenutzerDTODataAccess(BenutzerRepository benutzerRepository,
                                 DTOMapper<Benutzer, BenutzerDTO> benutzerDTOMapper,
                                 TextProvider textProvider,
                                 LogService logService) {
        this.benutzerRepository = benutzerRepository;
        this.benutzerDTOMapper = benutzerDTOMapper;
        this.textProvider = textProvider;
        this.logService = logService;
    }

    @Override
    @NonNull
    public List<BenutzerDTO> findAll() {
        return benutzerRepository.findAll().stream()
                .map(benutzerDTOMapper::toDTO)
                .toList();
    }

    @Override
    @NonNull
    public Optional<BenutzerDTO> findById(@NonNull Long id) {
        return benutzerRepository.findById(id).map(benutzerDTOMapper::toDTO);
    }

    @NonNull
    public Optional<BenutzerDTO> findByEmail(@NonNull String email) {
        return benutzerRepository.findByEmail(email).map(benutzerDTOMapper::toDTO);
    }

    @NonNull
    public Optional<BenutzerDTO> findByTelefonnummer(@NonNull String telefonnummer) {
        return benutzerRepository.findByTelefonnummer(telefonnummer).map(benutzerDTOMapper::toDTO);
    }

    @Override
    @NonNull
    @Transactional
    public CheckResult<Void> deleteById(@NonNull Long id) {
        try {
            Optional<Benutzer> existing = benutzerRepository.findById(id);
            if (existing.isEmpty()) {
                logService.log(LogType.APPLICATION_ERROR, LogLevel.ERROR, textProvider.getTranslation(TranslationConstants.LOG_DELETE_NOT_FOUND, "Benutzer", id));
                return CheckResult.failure(textProvider.getTranslation(TranslationConstants.ERROR_DELETE));
            }
            BenutzerDTO benutzerDTO = benutzerDTOMapper.toDTO(existing.get());
            benutzerRepository.delete(existing.get());
            benutzerRepository.flush();
            CheckResult<Void> result = CheckResult.success(null, textProvider.getTranslation(TranslationConstants.SUCCESS_DELETE));
            String header = textProvider.getTranslation(TranslationConstants.LOG_HEADER_DELETED, "Benutzer", id);
            logService.log(LogType.BENUTZER_DELETED, LogLevel.INFO, logService.createMessage(header, benutzerDTO, BenutzerDTOProperties.values()));
            return result;
        } catch (Exception e) {
            logService.log(LogType.APPLICATION_ERROR, LogLevel.ERROR, e.getMessage());
            return CheckResult.failure(textProvider.getTranslation(TranslationConstants.ERROR_DELETE));
        }
    }

    @NonNull
    @Transactional
    public CheckResult<BenutzerDTO> save(@NonNull BenutzerDTO benutzerDTO) {
        try {
            Benutzer entity;
            LogType operationType;
            String message;
            if (benutzerDTO.id() != null) {
                Benutzer existing = benutzerRepository.findById(benutzerDTO.id())
                        .orElseThrow(() -> new IllegalArgumentException(textProvider.getTranslation(TranslationConstants.EXCEPTION_NOT_FOUND, "Benutzer")));
                BenutzerDTO oldDTO = benutzerDTOMapper.toDTO(existing);
                benutzerDTOMapper.updateEntity(benutzerDTO, existing);
                entity = existing;
                operationType = LogType.BENUTZER_UPDATED;
                String header = textProvider.getTranslation(TranslationConstants.LOG_HEADER_UPDATED, "Benutzer", benutzerDTO.id());
                message = logService.createChangeMessage(header, oldDTO, benutzerDTO, BenutzerDTOProperties.values());
            } else {
                entity = benutzerDTOMapper.toEntity(benutzerDTO);
                operationType = LogType.BENUTZER_CREATED;
                String header = textProvider.getTranslation(TranslationConstants.LOG_HEADER_CREATED, "Benutzer");
                message = logService.createMessage(header, benutzerDTO, BenutzerDTOProperties.values());
            }

            if (entity.getPasswordhash() == null) {
                String errorMsg = textProvider.getTranslation(TranslationConstants.ERROR_NOPASSWORD);
                String identifier = benutzerDTO.id() != null ? "ID " + benutzerDTO.id() : textProvider.getTranslation(TranslationConstants.LOG_NEW);
                logService.log(LogType.APPLICATION_ERROR, LogLevel.ERROR, textProvider.getTranslation(TranslationConstants.LOG_SAVE_NO_PASSWORD, identifier));
                return CheckResult.failure(errorMsg);
            }

            Benutzer savedEntity = benutzerRepository.saveAndFlush(entity);
            CheckResult<BenutzerDTO> result = CheckResult.success(benutzerDTOMapper.toDTO(savedEntity),
                    textProvider.getTranslation(TranslationConstants.SUCCESS_SAVE));

            logService.log(operationType, LogLevel.INFO, message);
            return result;
        } catch (Exception e) {
            logService.log(LogType.APPLICATION_ERROR, LogLevel.ERROR, e.getMessage());
            return CheckResult.failure(textProvider.getTranslation(TranslationConstants.ERROR_SAVE));
        }
    }
}