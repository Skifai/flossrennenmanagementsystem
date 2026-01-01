package ch.flossrennen.managementsystem.service;

import ch.flossrennen.managementsystem.dataaccess.DTODataAccess;
import ch.flossrennen.managementsystem.dataaccess.dto.HelferDTO;
import ch.flossrennen.managementsystem.service.validation.Validator;
import ch.flossrennen.managementsystem.util.CheckResult;
import ch.flossrennen.managementsystem.util.textprovider.TextProvider;
import ch.flossrennen.managementsystem.util.textprovider.TranslationConstants;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HelferDTOService implements DTOService<HelferDTO> {
    private static final Logger log = LoggerFactory.getLogger(HelferDTOService.class);

    private final DTODataAccess<HelferDTO> helferDTODataAccess;
    private final Validator<HelferDTO> helferValidator;
    private final TextProvider textProvider;

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

    // TODO: Implement Filtering for HelferDTOs
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
        log.error("Error deleting Helfer: Missing ID for DTO {}", helferDTO);
        return CheckResult.failure(textProvider.getTranslation(TranslationConstants.ERROR_MISSING_ID));
    }
}
