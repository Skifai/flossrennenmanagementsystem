package ch.flossrennen.managementsystem.dataaccess;

import ch.flossrennen.managementsystem.dataaccess.dto.HelferDTO;
import ch.flossrennen.managementsystem.dataaccess.mapper.HelferDTOMapper;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Helfer;
import ch.flossrennen.managementsystem.dataaccess.persistence.repository.HelferRepository;
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
                return CheckResult.failure(textProvider.getTranslation(TranslationConstants.ERROR_DELETE));
            }
            helferRepository.deleteById(id);
            return CheckResult.success(null, textProvider.getTranslation(TranslationConstants.SUCCESS_DELETE));
        } catch (Exception e) {
            return CheckResult.failure(textProvider.getTranslation(TranslationConstants.ERROR_DELETE));
        }
    }

    @NonNull
    @Transactional
    public CheckResult<HelferDTO> save(@NonNull HelferDTO helferDTO) {
        try {
            validateRessortExists(helferDTO);

            Helfer entity = (helferDTO.id() != null)
                    ? updateExistingHelfer(helferDTO)
                    : createNewHelfer(helferDTO);

            Helfer savedHelfer = helferRepository.saveAndFlush(entity);
            return CheckResult.success(helferDTOMapper.toDTO(savedHelfer),
                    textProvider.getTranslation(TranslationConstants.SUCCESS_SAVE));
        } catch (Exception e) {
            return CheckResult.failure(textProvider.getTranslation(TranslationConstants.ERROR_SAVE));
        }
    }

    private void validateRessortExists(HelferDTO helferDTO) {
        if (helferDTO.ressort() == null) {
            throw new IllegalArgumentException(textProvider.getTranslation(TranslationConstants.ERROR_MISSING_RESSORT));
        }
    }

    private Helfer updateExistingHelfer(HelferDTO helferDTO) {
        return helferRepository.findById(helferDTO.id())
                .map(existing -> {
                    helferDTOMapper.updateEntity(helferDTO, existing);
                    return existing;
                })
                .orElseThrow(() -> new IllegalArgumentException("Helfer not found"));
    }

    private Helfer createNewHelfer(HelferDTO helferDTO) {
        return helferDTOMapper.toEntity(helferDTO);
    }
}
