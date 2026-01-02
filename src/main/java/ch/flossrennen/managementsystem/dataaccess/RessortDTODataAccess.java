package ch.flossrennen.managementsystem.dataaccess;

import ch.flossrennen.managementsystem.dataaccess.dto.RessortDTO;
import ch.flossrennen.managementsystem.dataaccess.mapper.DTOMapper;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Ressort;
import ch.flossrennen.managementsystem.dataaccess.persistence.repository.RessortRepository;
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
    public Optional<RessortDTO> findByName(@NonNull String name) {
        return ressortRepository.findByName(name).map(ressortDTOMapper::toDTO);
    }

    @NonNull
    @Transactional
    public CheckResult<Void> deleteById(@NonNull Long id) {
        try {
            Optional<Ressort> existing = ressortRepository.findById(id);
            if (existing.isEmpty()) {
                return CheckResult.failure(textProvider.getTranslation(TranslationConstants.ERROR_DELETE));
            }
            ressortRepository.deleteById(id);
            return CheckResult.success(null, textProvider.getTranslation(TranslationConstants.SUCCESS_DELETE));
        } catch (Exception e) {
            return CheckResult.failure(textProvider.getTranslation(TranslationConstants.ERROR_DELETE));
        }
    }

    @NonNull
    @Transactional
    public CheckResult<RessortDTO> save(@NonNull RessortDTO ressortDTO) {
        try {
            Ressort entity = (ressortDTO.id() != null)
                    ? updateExistingRessort(ressortDTO)
                    : createNewRessort(ressortDTO);

            Ressort savedEntity = ressortRepository.saveAndFlush(entity);
            return CheckResult.success(ressortDTOMapper.toDTO(savedEntity),
                    textProvider.getTranslation(TranslationConstants.SUCCESS_SAVE));
        } catch (Exception e) {
            return CheckResult.failure(textProvider.getTranslation(TranslationConstants.ERROR_SAVE));
        }
    }

    private Ressort updateExistingRessort(RessortDTO ressortDTO) {
        return ressortRepository.findById(ressortDTO.id())
                .map(existing -> {
                    ressortDTOMapper.updateEntity(ressortDTO, existing);
                    return existing;
                })
                .orElseThrow(() -> new IllegalArgumentException("Ressort not found"));
    }

    private Ressort createNewRessort(RessortDTO ressortDTO) {
        return ressortDTOMapper.toEntity(ressortDTO);
    }
}
