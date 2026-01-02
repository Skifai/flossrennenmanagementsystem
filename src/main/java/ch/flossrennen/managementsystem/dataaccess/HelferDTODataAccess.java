package ch.flossrennen.managementsystem.dataaccess;

import ch.flossrennen.managementsystem.dataaccess.dto.HelferDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.HelferDTOProperties;
import ch.flossrennen.managementsystem.dataaccess.mapper.HelferDTOMapper;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Helfer;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.LogLevel;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.LogType;
import ch.flossrennen.managementsystem.dataaccess.persistence.repository.HelferRepository;
import ch.flossrennen.managementsystem.logging.LogService;
import ch.flossrennen.managementsystem.util.CheckResult;
import ch.flossrennen.managementsystem.util.textprovider.TextProvider;
import ch.flossrennen.managementsystem.util.textprovider.TranslationConstants;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class HelferDTODataAccess implements DTODataAccess<HelferDTO> {
    private final HelferRepository helferRepository;
    private final HelferDTOMapper helferDTOMapper;
    private final TextProvider textProvider;
    private final LogService logService;

    public HelferDTODataAccess(HelferRepository helferRepository, HelferDTOMapper helferDTOMapper, TextProvider textProvider, LogService logService) {
        this.helferRepository = helferRepository;
        this.helferDTOMapper = helferDTOMapper;
        this.textProvider = textProvider;
        this.logService = logService;
    }

    @NonNull
    public List<HelferDTO> findAll() {
        return helferRepository.findAll().stream()
                .map(helferDTOMapper::toDTO)
                .toList();
    }

    @NonNull
    public Optional<HelferDTO> findById(@NonNull Long id) {
        return helferRepository.findById(id).map(helferDTOMapper::toDTO);
    }

    @NonNull
    public Optional<HelferDTO> findByEmail(@NonNull String email) {
        return helferRepository.findByEmail(email).map(helferDTOMapper::toDTO);
    }

    @NonNull
    public Optional<HelferDTO> findByTelefonnummer(@NonNull String telefonnummer) {
        return helferRepository.findByTelefonnummer(telefonnummer).map(helferDTOMapper::toDTO);
    }

    @NonNull
    @Transactional
    public CheckResult<Void> deleteById(@NonNull Long id) {
        try {
            Optional<Helfer> existing = helferRepository.findById(id);
            if (existing.isEmpty()) {
                logService.log(LogType.APPLICATION_ERROR, LogLevel.ERROR, textProvider.getTranslation(TranslationConstants.LOG_DELETE_NOT_FOUND, "Helfer", id));
                return CheckResult.failure(textProvider.getTranslation(TranslationConstants.ERROR_DELETE));
            }
            HelferDTO helferDTO = helferDTOMapper.toDTO(existing.get());
            helferRepository.deleteById(id);
            CheckResult<Void> result = CheckResult.success(null, textProvider.getTranslation(TranslationConstants.SUCCESS_DELETE));
            logService.log(LogType.HELFER_DELETED, LogLevel.INFO, logService.createMessage(helferDTO, HelferDTOProperties.values()));
            return result;
        } catch (Exception e) {
            logService.log(LogType.APPLICATION_ERROR, LogLevel.ERROR, e.getMessage());
            return CheckResult.failure(textProvider.getTranslation(TranslationConstants.ERROR_DELETE));
        }
    }

    @NonNull
    @Transactional
    public CheckResult<HelferDTO> save(@NonNull HelferDTO helferDTO) {
        try {
            validateRessortExists(helferDTO);

            Helfer entity;
            LogType operationType;
            String message = null;
            if (helferDTO.id() != null) {
                Helfer existing = helferRepository.findById(helferDTO.id())
                        .orElseThrow(() -> new IllegalArgumentException(textProvider.getTranslation(TranslationConstants.EXCEPTION_NOT_FOUND, "Helfer")));
                HelferDTO oldDTO = helferDTOMapper.toDTO(existing);
                helferDTOMapper.updateEntity(helferDTO, existing);
                entity = existing;
                operationType = LogType.HELFER_UPDATED;
                message = logService.createChangeMessage(oldDTO, helferDTO, HelferDTOProperties.values());
            } else {
                entity = helferDTOMapper.toEntity(helferDTO);
                operationType = LogType.HELFER_CREATED;
                message = logService.createMessage(helferDTO, HelferDTOProperties.values());
            }

            Helfer savedHelfer = helferRepository.saveAndFlush(entity);
            CheckResult<HelferDTO> result = CheckResult.success(helferDTOMapper.toDTO(savedHelfer),
                    textProvider.getTranslation(TranslationConstants.SUCCESS_SAVE));

            logService.log(operationType, LogLevel.INFO, message);

            return result;
        } catch (Exception e) {
            logService.log(LogType.APPLICATION_ERROR, LogLevel.ERROR, e.getMessage());
            return CheckResult.failure(textProvider.getTranslation(TranslationConstants.ERROR_SAVE));
        }
    }

    private void validateRessortExists(HelferDTO helferDTO) {
        if (helferDTO.ressort() == null) {
            throw new IllegalArgumentException(textProvider.getTranslation(TranslationConstants.ERROR_MISSING_RESSORT));
        }
    }
}