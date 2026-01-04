package ch.flossrennen.managementsystem.service;

import ch.flossrennen.managementsystem.dataaccess.DTODataAccess;
import ch.flossrennen.managementsystem.dataaccess.dto.HelferDTO;
import ch.flossrennen.managementsystem.service.validation.Validator;
import ch.flossrennen.managementsystem.util.CheckResult;
import ch.flossrennen.managementsystem.util.textprovider.TextProvider;
import ch.flossrennen.managementsystem.util.textprovider.TranslationConstants;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service für die Verwaltung von Helfer-DTOs.
 */
@Service
public class HelferDTOService implements DTOService<HelferDTO> {

    private final DTODataAccess<HelferDTO> helferDTODataAccess;
    private final Validator<HelferDTO> helferValidator;
    private final TextProvider textProvider;

    /**
     * Erstellt einen neuen HelferDTOService.
     *
     * @param helferDTODataAccess Der Datenzugriff für Helfer-DTOs.
     * @param helferValidator     Der Validator für Helfer-DTOs.
     * @param textProvider        Der TextProvider für Übersetzungen.
     */
    public HelferDTOService(DTODataAccess<HelferDTO> helferDTODataAccess,
                            Validator<HelferDTO> helferValidator,
                            TextProvider textProvider) {
        this.helferDTODataAccess = helferDTODataAccess;
        this.helferValidator = helferValidator;
        this.textProvider = textProvider;
    }

    @Override
    @NonNull
    public CheckResult<HelferDTO> save(@NonNull HelferDTO helferDTO) {
        CheckResult<HelferDTO> validationResult = helferValidator.validate(helferDTO);
        if (!validationResult.isSuccess()) {
            return validationResult;
        }
        return helferDTODataAccess.save(helferDTO);
    }

    @Override
    @NonNull
    public Optional<HelferDTO> findById(@NonNull Long id) {
        return helferDTODataAccess.findById(id);
    }

    @Override
    @NonNull
    public List<HelferDTO> findAll() {
        return helferDTODataAccess.findAll();
    }

    @Override
    public @NonNull CheckResult<Void> delete(@NonNull HelferDTO helferDTO) {
        if (helferDTO.id() != null) {
            return helferDTODataAccess.deleteById(helferDTO.id());
        }
        return CheckResult.failure(textProvider.getTranslation(TranslationConstants.ERROR_MISSING_ID));
    }
}
