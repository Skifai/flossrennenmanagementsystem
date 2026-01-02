package ch.flossrennen.managementsystem.service;

import ch.flossrennen.managementsystem.dataaccess.DTODataAccess;
import ch.flossrennen.managementsystem.dataaccess.dto.RessortDTO;
import ch.flossrennen.managementsystem.service.validation.Validator;
import ch.flossrennen.managementsystem.util.CheckResult;
import ch.flossrennen.managementsystem.util.textprovider.TextProvider;
import ch.flossrennen.managementsystem.util.textprovider.TranslationConstants;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RessortDTOService implements DTOService<RessortDTO> {

    private final DTODataAccess<RessortDTO> ressortDTODataAccess;
    private final Validator<RessortDTO> ressortValidator;
    private final TextProvider textProvider;

    public RessortDTOService(DTODataAccess<RessortDTO> ressortDTODataAccess,
                             Validator<RessortDTO> ressortValidator,
                             TextProvider textProvider) {
        this.ressortDTODataAccess = ressortDTODataAccess;
        this.ressortValidator = ressortValidator;
        this.textProvider = textProvider;
    }

    @Override
    public @NonNull List<RessortDTO> findAll() {
        return ressortDTODataAccess.findAll();
    }

    @Override
    public @NonNull Optional<RessortDTO> findById(@NonNull Long id) {
        return ressortDTODataAccess.findById(id);
    }

    @Override
    public @NonNull CheckResult<RessortDTO> save(@NonNull RessortDTO ressortDTO) {
        CheckResult<RessortDTO> validationResult = ressortValidator.validate(ressortDTO);
        if (!validationResult.isSuccess()) {
            return validationResult;
        }
        return ressortDTODataAccess.save(ressortDTO);
    }

    @Override
    public @NonNull CheckResult<Void> delete(@NonNull RessortDTO ressortDTO) {
        if (ressortDTO.id() != null) {
            return ressortDTODataAccess.deleteById(ressortDTO.id());
        }
        return CheckResult.failure(textProvider.getTranslation(TranslationConstants.ERROR_MISSING_ID));
    }
}
