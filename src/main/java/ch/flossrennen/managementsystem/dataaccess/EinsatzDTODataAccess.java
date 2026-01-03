package ch.flossrennen.managementsystem.dataaccess;

import ch.flossrennen.managementsystem.dataaccess.dto.EinsatzDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.EinsatzDTOProperties;
import ch.flossrennen.managementsystem.dataaccess.mapper.DTOMapper;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Einsatz;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.LogLevel;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.LogType;
import ch.flossrennen.managementsystem.dataaccess.persistence.repository.EinsatzRepository;
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
public class EinsatzDTODataAccess implements DTODataAccess<EinsatzDTO> {
    private final EinsatzRepository einsatzRepository;
    private final DTOMapper<Einsatz, EinsatzDTO> einsatzDTOMapper;
    private final TextProvider textProvider;
    private final LogService logService;

    public EinsatzDTODataAccess(EinsatzRepository einsatzRepository, DTOMapper<Einsatz, EinsatzDTO> einsatzDTOMapper, TextProvider textProvider, LogService logService) {
        this.einsatzRepository = einsatzRepository;
        this.einsatzDTOMapper = einsatzDTOMapper;
        this.textProvider = textProvider;
        this.logService = logService;
    }

    @NonNull
    public List<EinsatzDTO> findAll() {
        return einsatzRepository.findAll().stream()
                .map(einsatzDTOMapper::toDTO)
                .toList();
    }

    @NonNull
    public Optional<EinsatzDTO> findById(@NonNull Long id) {
        return einsatzRepository.findById(id).map(einsatzDTOMapper::toDTO);
    }

    @NonNull
    @Transactional
    public CheckResult<Void> deleteById(@NonNull Long id) {
        try {
            Optional<Einsatz> existing = einsatzRepository.findById(id);
            if (existing.isEmpty()) {
                logService.log(LogType.APPLICATION_ERROR, LogLevel.ERROR, textProvider.getTranslation(TranslationConstants.LOG_DELETE_NOT_FOUND, "Einsatz", id));
                return CheckResult.failure(textProvider.getTranslation(TranslationConstants.ERROR_DELETE));
            }
            EinsatzDTO einsatzDTO = einsatzDTOMapper.toDTO(existing.get());
            einsatzRepository.delete(existing.get());
            einsatzRepository.flush();
            CheckResult<Void> result = CheckResult.success(null, textProvider.getTranslation(TranslationConstants.SUCCESS_DELETE));
            String header = textProvider.getTranslation(TranslationConstants.LOG_HEADER_DELETED, "Einsatz", id);
            logService.log(LogType.EINSATZ_DELETED, LogLevel.INFO, logService.createMessage(header, einsatzDTO, EinsatzDTOProperties.values()));
            return result;
        } catch (Exception e) {
            logService.log(LogType.APPLICATION_ERROR, LogLevel.ERROR, e.getMessage());
            return CheckResult.failure(textProvider.getTranslation(TranslationConstants.ERROR_DELETE));
        }
    }

    @NonNull
    @Transactional
    public CheckResult<EinsatzDTO> save(@NonNull EinsatzDTO einsatzDTO) {
        try {
            Einsatz entity;
            LogType operationType;
            String message;
            if (einsatzDTO.id() != null) {
                Einsatz existing = einsatzRepository.findById(einsatzDTO.id())
                        .orElseThrow(() -> new IllegalArgumentException(textProvider.getTranslation(TranslationConstants.EXCEPTION_NOT_FOUND, "Einsatz")));
                EinsatzDTO oldDTO = einsatzDTOMapper.toDTO(existing);
                einsatzDTOMapper.updateEntity(einsatzDTO, existing);
                entity = existing;
                operationType = LogType.EINSATZ_UPDATED;
                String header = textProvider.getTranslation(TranslationConstants.LOG_HEADER_UPDATED, "Einsatz", einsatzDTO.id());
                message = logService.createChangeMessage(header, oldDTO, einsatzDTO, EinsatzDTOProperties.values());
            } else {
                entity = einsatzDTOMapper.toEntity(einsatzDTO);
                operationType = LogType.EINSATZ_CREATED;
                String header = textProvider.getTranslation(TranslationConstants.LOG_HEADER_CREATED, "Einsatz");
                message = logService.createMessage(header, einsatzDTO, EinsatzDTOProperties.values());
            }

            Einsatz savedEntity = einsatzRepository.saveAndFlush(entity);
            CheckResult<EinsatzDTO> result = CheckResult.success(einsatzDTOMapper.toDTO(savedEntity),
                    textProvider.getTranslation(TranslationConstants.SUCCESS_SAVE));

            logService.log(operationType, LogLevel.INFO, message);

            return result;
        } catch (Exception e) {
            logService.log(LogType.APPLICATION_ERROR, LogLevel.ERROR, e.getMessage());
            return CheckResult.failure(textProvider.getTranslation(TranslationConstants.ERROR_SAVE));
        }
    }
}