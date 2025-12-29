package ch.flossrennen.managementsystem.dataaccess;

import ch.flossrennen.managementsystem.dataaccess.dto.RessortDTO;
import ch.flossrennen.managementsystem.dataaccess.mapper.DTOMapper;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Ressort;
import ch.flossrennen.managementsystem.dataaccess.persistence.repository.RessortRepository;
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
public class RessortDTODataAccess implements DTODataAccess<RessortDTO> {
    private static final Logger log = LoggerFactory.getLogger(RessortDTODataAccess.class);
    private final RessortRepository ressortRepository;
    private final DTOMapper<Ressort, RessortDTO> ressortDTOMapper;
    private final TextProvider textProvider;

    public RessortDTODataAccess(RessortRepository ressortRepository, DTOMapper<Ressort, RessortDTO> ressortDTOMapper, TextProvider textProvider) {
        this.ressortRepository = ressortRepository;
        this.ressortDTOMapper = ressortDTOMapper;
        this.textProvider = textProvider;
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
    public CheckResult<Void> deleteById(@NonNull Long id) {
        try {
            ressortRepository.deleteById(id);
            return CheckResult.success(null, textProvider.getTranslation(TranslationConstants.SUCCESS_DELETE));
        } catch (Exception e) {
            log.error("Error deleting Ressort with id {}: {}", id, e.getMessage(), e);
            return CheckResult.failure(textProvider.getTranslation(TranslationConstants.ERROR_DELETE));
        }
    }

    @NonNull
    public CheckResult<RessortDTO> save(@NonNull RessortDTO ressortDTO) {
        try {
            Ressort entity = ressortDTOMapper.toEntity(ressortDTO);
            Ressort savedEntity = ressortRepository.saveAndFlush(entity);
            return CheckResult.success(ressortDTOMapper.toDTO(savedEntity), textProvider.getTranslation(TranslationConstants.SUCCESS_SAVE));
        } catch (Exception e) {
            log.error("Error saving Ressort {}: {}", ressortDTO, e.getMessage(), e);
            return CheckResult.failure(textProvider.getTranslation(TranslationConstants.ERROR_SAVE));
        }
    }
}
