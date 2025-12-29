package ch.flossrennen.managementsystem.dataaccess;

import ch.flossrennen.managementsystem.dataaccess.dto.HelferDTO;
import ch.flossrennen.managementsystem.dataaccess.mapper.HelferDTOMapper;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Helfer;
import ch.flossrennen.managementsystem.dataaccess.persistence.repository.HelferRepository;
import ch.flossrennen.managementsystem.textprovider.TextProvider;
import ch.flossrennen.managementsystem.util.CheckResult;
import ch.flossrennen.managementsystem.util.TranslationConstants;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class HelferDTODataAccess implements DTODataAccess<HelferDTO> {
    private static final Logger log = LoggerFactory.getLogger(HelferDTODataAccess.class);
    private final HelferRepository helferRepository;
    private final HelferDTOMapper helferDTOMapper;
    private final TextProvider textProvider;

    public HelferDTODataAccess(HelferRepository helferRepository, HelferDTOMapper helferDTOMapper, TextProvider textProvider) {
        this.helferRepository = helferRepository;
        this.helferDTOMapper = helferDTOMapper;
        this.textProvider = textProvider;
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
    public CheckResult<Void> deleteById(@NonNull Long id) {
        try {
            helferRepository.deleteById(id);
            return CheckResult.success(null, textProvider.getTranslation(TranslationConstants.SUCCESS_DELETE));
        } catch (Exception e) {
            log.error("Error deleting Helfer with id {}: {}", id, e.getMessage(), e);
            return CheckResult.failure(textProvider.getTranslation(TranslationConstants.ERROR_DELETE));
        }
    }

    @NonNull
    public CheckResult<HelferDTO> save(@NonNull HelferDTO helferDTO) {
        try {
            if (helferDTO.ressort() == null) {
                log.error("Error saving Helfer {}: Missing Ressort", helferDTO);
                return CheckResult.failure(textProvider.getTranslation(TranslationConstants.ERROR_MISSING_RESSORT));
            }
            Helfer helfer = helferDTOMapper.toEntity(helferDTO);
            Helfer savedHelfer = helferRepository.saveAndFlush(helfer);
            return CheckResult.success(helferDTOMapper.toDTO(savedHelfer), textProvider.getTranslation(TranslationConstants.SUCCESS_SAVE));
        } catch (Exception e) {
            log.error("Error saving Helfer {}: {}", helferDTO, e.getMessage(), e);
            return CheckResult.failure(textProvider.getTranslation(TranslationConstants.ERROR_SAVE));
        }
    }
}
