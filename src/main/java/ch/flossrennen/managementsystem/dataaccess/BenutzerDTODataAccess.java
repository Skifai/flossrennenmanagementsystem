package ch.flossrennen.managementsystem.dataaccess;

import ch.flossrennen.managementsystem.dataaccess.dto.BenutzerDTO;
import ch.flossrennen.managementsystem.dataaccess.mapper.DTOMapper;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.Benutzer;
import ch.flossrennen.managementsystem.dataaccess.persistence.repository.BenutzerRepository;
import ch.flossrennen.managementsystem.util.CheckResult;
import ch.flossrennen.managementsystem.util.textprovider.TextProvider;
import ch.flossrennen.managementsystem.util.textprovider.TranslationConstants;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class BenutzerDTODataAccess implements DTODataAccess<BenutzerDTO> {
    private final BenutzerRepository benutzerRepository;
    private final DTOMapper<Benutzer, BenutzerDTO> benutzerDTOMapper;
    private final TextProvider textProvider;

    public BenutzerDTODataAccess(BenutzerRepository benutzerRepository,
                                 DTOMapper<Benutzer, BenutzerDTO> benutzerDTOMapper,
                                 TextProvider textProvider) {
        this.benutzerRepository = benutzerRepository;
        this.benutzerDTOMapper = benutzerDTOMapper;
        this.textProvider = textProvider;
    }

    @NonNull
    public List<BenutzerDTO> findAll() {
        return benutzerRepository.findAll().stream()
                .map(benutzerDTOMapper::toDTO)
                .toList();
    }

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

    @NonNull
    @Transactional
    public CheckResult<Void> deleteById(@NonNull Long id) {
        try {
            Optional<Benutzer> existing = benutzerRepository.findById(id);
            if (existing.isEmpty()) {
                return CheckResult.failure(textProvider.getTranslation(TranslationConstants.ERROR_DELETE));
            }
            benutzerRepository.deleteById(id);
            return CheckResult.success(null, textProvider.getTranslation(TranslationConstants.SUCCESS_DELETE));
        } catch (Exception e) {
            return CheckResult.failure(textProvider.getTranslation(TranslationConstants.ERROR_DELETE));
        }
    }

    @NonNull
    @Transactional
    public CheckResult<BenutzerDTO> save(@NonNull BenutzerDTO benutzerDTO) {
        try {
            Benutzer entity = (benutzerDTO.id() != null)
                    ? updateExistingBenutzer(benutzerDTO)
                    : createNewBenutzer(benutzerDTO);

            if (entity.getPasswordhash() == null) {
                return CheckResult.failure(textProvider.getTranslation(TranslationConstants.ERROR_NOPASSWORD));
            }

            Benutzer savedEntity = benutzerRepository.saveAndFlush(entity);
            return CheckResult.success(benutzerDTOMapper.toDTO(savedEntity),
                    textProvider.getTranslation(TranslationConstants.SUCCESS_SAVE));
        } catch (Exception e) {
            return CheckResult.failure(textProvider.getTranslation(TranslationConstants.ERROR_SAVE));
        }
    }

    private Benutzer updateExistingBenutzer(BenutzerDTO benutzerDTO) {
        return benutzerRepository.findById(benutzerDTO.id())
                .map(existing -> {
                    benutzerDTOMapper.updateEntity(benutzerDTO, existing);
                    return existing;
                })
                .orElseThrow(() -> new IllegalArgumentException("Benutzer not found"));
    }

    private Benutzer createNewBenutzer(BenutzerDTO benutzerDTO) {
        return benutzerDTOMapper.toEntity(benutzerDTO);
    }
}
