package ch.flossrennen.managementsystem.service;

import ch.flossrennen.managementsystem.dataaccess.DTODataAccess;
import ch.flossrennen.managementsystem.dataaccess.dto.BenutzerDTO;
import ch.flossrennen.managementsystem.service.validation.Validator;
import ch.flossrennen.managementsystem.util.CheckResult;
import ch.flossrennen.managementsystem.util.textprovider.TextProvider;
import ch.flossrennen.managementsystem.util.textprovider.TranslationConstants;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BenutzerDTOService implements DTOService<BenutzerDTO> {

    private final DTODataAccess<BenutzerDTO> benutzerDTODataAccess;
    private final Validator<BenutzerDTO> benutzerValidator;
    private final TextProvider textProvider;

    public BenutzerDTOService(DTODataAccess<BenutzerDTO> benutzerDTODataAccess,
                              Validator<BenutzerDTO> benutzerValidator,
                              TextProvider textProvider) {
        this.benutzerDTODataAccess = benutzerDTODataAccess;
        this.benutzerValidator = benutzerValidator;
        this.textProvider = textProvider;
    }

    @Override
    public @NonNull List<BenutzerDTO> findAll() {
        return benutzerDTODataAccess.findAll();
    }

    @Override
    public @NonNull Optional<BenutzerDTO> findById(@NonNull Long id) {
        return benutzerDTODataAccess.findById(id);
    }

    @Override
    public @NonNull CheckResult<BenutzerDTO> save(@NonNull BenutzerDTO benutzerDTO) {
        CheckResult<BenutzerDTO> validationResult = benutzerValidator.validate(benutzerDTO);
        if (!validationResult.isSuccess()) {
            return validationResult;
        }
        return benutzerDTODataAccess.save(benutzerDTO);
    }

    @Override
    public @NonNull CheckResult<Void> delete(@NonNull BenutzerDTO benutzerDTO) {
        if (benutzerDTO.id() != null) {
            return benutzerDTODataAccess.deleteById(benutzerDTO.id());
        }
        return CheckResult.failure(textProvider.getTranslation(TranslationConstants.ERROR_MISSING_ID));
    }
}
