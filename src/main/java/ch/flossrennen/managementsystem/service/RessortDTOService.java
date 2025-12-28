package ch.flossrennen.managementsystem.service;

import ch.flossrennen.managementsystem.dataaccess.DTODataAccess;
import ch.flossrennen.managementsystem.dataaccess.dto.RessortDTO;
import ch.flossrennen.managementsystem.textprovider.TextProvider;
import ch.flossrennen.managementsystem.util.CheckResult;
import ch.flossrennen.managementsystem.util.TranslationConstants;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RessortDTOService implements DTOService<RessortDTO> {
    private static final Logger log = LoggerFactory.getLogger(RessortDTOService.class);

    private final DTODataAccess<RessortDTO> ressortDTODataAccess;
    private final TextProvider textProvider;

    public RessortDTOService(DTODataAccess<RessortDTO> ressortDTODataAccess, TextProvider textProvider) {
        this.ressortDTODataAccess = ressortDTODataAccess;
        this.textProvider = textProvider;
    }

    @Override
    public @NonNull List<RessortDTO> findAll() {
        return ressortDTODataAccess.findAll();
    }

    @Override
    public @NonNull CheckResult<RessortDTO> save(@NonNull RessortDTO ressortDTO) {
        return ressortDTODataAccess.save(ressortDTO);
    }

    @Override
    public @NonNull CheckResult<Void> delete(@NonNull RessortDTO ressortDTO) {
        if (ressortDTO.id() != null) {
            return ressortDTODataAccess.deleteById(ressortDTO.id());
        }
        log.error("Error deleting Ressort: Missing ID for DTO {}", ressortDTO);
        return CheckResult.failure(textProvider.getTranslation(TranslationConstants.ERROR_MISSING_ID));
    }
}
