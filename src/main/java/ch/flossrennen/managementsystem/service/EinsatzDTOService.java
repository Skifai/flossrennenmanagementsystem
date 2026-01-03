package ch.flossrennen.managementsystem.service;

import ch.flossrennen.managementsystem.dataaccess.DTODataAccess;
import ch.flossrennen.managementsystem.dataaccess.dto.EinsatzDTO;
import ch.flossrennen.managementsystem.service.validation.Validator;
import ch.flossrennen.managementsystem.util.CheckResult;
import ch.flossrennen.managementsystem.util.textprovider.TextProvider;
import ch.flossrennen.managementsystem.util.textprovider.TranslationConstants;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EinsatzDTOService implements DTOService<EinsatzDTO> {

    private final DTODataAccess<EinsatzDTO> einsatzDTODataAccess;
    private final Validator<EinsatzDTO> einsatzValidator;
    private final TextProvider textProvider;

    public EinsatzDTOService(DTODataAccess<EinsatzDTO> einsatzDTODataAccess,
                             Validator<EinsatzDTO> einsatzValidator,
                             TextProvider textProvider) {
        this.einsatzDTODataAccess = einsatzDTODataAccess;
        this.einsatzValidator = einsatzValidator;
        this.textProvider = textProvider;
    }

    @Override
    public @NonNull List<EinsatzDTO> findAll() {
        return einsatzDTODataAccess.findAll();
    }

    @Override
    public @NonNull Optional<EinsatzDTO> findById(@NonNull Long id) {
        return einsatzDTODataAccess.findById(id);
    }

    @Override
    public @NonNull CheckResult<EinsatzDTO> save(@NonNull EinsatzDTO einsatzDTO) {
        CheckResult<EinsatzDTO> validationResult = einsatzValidator.validate(einsatzDTO);
        if (!validationResult.isSuccess()) {
            return validationResult;
        }
        return einsatzDTODataAccess.save(einsatzDTO);
    }

    @Override
    public @NonNull CheckResult<Void> delete(@NonNull EinsatzDTO einsatzDTO) {
        if (einsatzDTO.id() != null) {
            return einsatzDTODataAccess.deleteById(einsatzDTO.id());
        }
        return CheckResult.failure(textProvider.getTranslation(TranslationConstants.ERROR_MISSING_ID));
    }
}