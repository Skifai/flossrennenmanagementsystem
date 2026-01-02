package ch.flossrennen.managementsystem.dataaccess;

import ch.flossrennen.managementsystem.dataaccess.dto.RessortDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.RessortDTOProperties;
import ch.flossrennen.managementsystem.dataaccess.mapper.DTOMapper;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.LogLevel;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.LogType;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Ressort;
import ch.flossrennen.managementsystem.dataaccess.persistence.repository.RessortRepository;
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
public class RessortDTODataAccess implements DTODataAccess<RessortDTO> {
    private final RessortRepository ressortRepository;
    private final DTOMapper<Ressort, RessortDTO> ressortDTOMapper;
    private final TextProvider textProvider;
    private final LogService logService;

    public RessortDTODataAccess(RessortRepository ressortRepository, DTOMapper<Ressort, RessortDTO> ressortDTOMapper, TextProvider textProvider, LogService logService) {
        this.ressortRepository = ressortRepository;
        this.ressortDTOMapper = ressortDTOMapper;
        this.textProvider = textProvider;
        this.logService = logService;
    }

    @NonNull
    public List<RessortDTO> findAll() {
        return ressortRepository.findAll().stream()
                .map(ressortDTOMapper::toDTO)
                .toList();
    }

    @NonNull
    public Optional<RessortDTO> findById(@NonNull Long id) {
        return ressortRepository.findById(id).map(ressortDTOMapper::toDTO);
    }

    @NonNull
    public Optional<RessortDTO> findByName(@NonNull String name) {
        return ressortRepository.findByName(name).map(ressortDTOMapper::toDTO);
    }

    @NonNull
    @Transactional
    public CheckResult<Void> deleteById(@NonNull Long id) {
        try {
            Optional<Ressort> existing = ressortRepository.findById(id);
            if (existing.isEmpty()) {
                logService.log(LogType.APPLICATION_ERROR, LogLevel.ERROR, textProvider.getTranslation(TranslationConstants.LOG_DELETE_NOT_FOUND, "Ressort", id));
                return CheckResult.failure(textProvider.getTranslation(TranslationConstants.ERROR_DELETE));
            }
            RessortDTO ressortDTO = ressortDTOMapper.toDTO(existing.get());
            ressortRepository.deleteById(id);
            CheckResult<Void> result = CheckResult.success(null, textProvider.getTranslation(TranslationConstants.SUCCESS_DELETE));
            logService.log(LogType.RESSORT_DELETED, LogLevel.INFO, logService.createMessage(ressortDTO, RessortDTOProperties.values()));
            return result;
        } catch (Exception e) {
            logService.log(LogType.APPLICATION_ERROR, LogLevel.ERROR, e.getMessage());
            return CheckResult.failure(textProvider.getTranslation(TranslationConstants.ERROR_DELETE));
        }
    }

    @NonNull
    @Transactional
    public CheckResult<RessortDTO> save(@NonNull RessortDTO ressortDTO) {
        try {
            Ressort entity;
            LogType operationType;
            String message = null;
            if (ressortDTO.id() != null) {
                Ressort existing = ressortRepository.findById(ressortDTO.id())
                        .orElseThrow(() -> new IllegalArgumentException(textProvider.getTranslation(TranslationConstants.EXCEPTION_NOT_FOUND, "Ressort")));
                RessortDTO oldDTO = ressortDTOMapper.toDTO(existing);
                ressortDTOMapper.updateEntity(ressortDTO, existing);
                entity = existing;
                operationType = LogType.RESSORT_UPDATED;
                message = logService.createChangeMessage(oldDTO, ressortDTO, RessortDTOProperties.values());
            } else {
                entity = ressortDTOMapper.toEntity(ressortDTO);
                operationType = LogType.RESSORT_CREATED;
                message = logService.createMessage(ressortDTO, RessortDTOProperties.values());
            }

            Ressort savedEntity = ressortRepository.saveAndFlush(entity);
            CheckResult<RessortDTO> result = CheckResult.success(ressortDTOMapper.toDTO(savedEntity),
                    textProvider.getTranslation(TranslationConstants.SUCCESS_SAVE));

            logService.log(operationType, LogLevel.INFO, message);

            return result;
        } catch (Exception e) {
            logService.log(LogType.APPLICATION_ERROR, LogLevel.ERROR, e.getMessage());
            return CheckResult.failure(textProvider.getTranslation(TranslationConstants.ERROR_SAVE));
        }
    }
}