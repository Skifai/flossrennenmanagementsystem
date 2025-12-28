package ch.flossrennen.managementsystem.service;

import ch.flossrennen.managementsystem.dataaccess.DTODataAccess;
import ch.flossrennen.managementsystem.dataaccess.dto.BenutzerDTO;
import ch.flossrennen.managementsystem.textprovider.TextProvider;
import ch.flossrennen.managementsystem.util.CheckResult;
import ch.flossrennen.managementsystem.util.TranslationConstants;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class BenutzerDTOService implements DTOService<BenutzerDTO> {

    private final DTODataAccess<BenutzerDTO> benutzerDTODataAccess;
    private final TextProvider textProvider;

    public BenutzerDTOService(DTODataAccess<BenutzerDTO> benutzerDTODataAccess, TextProvider textProvider) {
        this.benutzerDTODataAccess = benutzerDTODataAccess;
        this.textProvider = textProvider;
    }

    @Override
    public @NonNull List<BenutzerDTO> findAll() {
        return benutzerDTODataAccess.findAll();
    }

    @Override
    public @NonNull CheckResult<BenutzerDTO> save(@NonNull BenutzerDTO benutzerDTO) {
        return benutzerDTODataAccess.save(benutzerDTO);
    }

    @Override
    public @NonNull CheckResult<Void> delete(@NonNull BenutzerDTO benutzerDTO) {
        if (benutzerDTO.id() != null) {
            return benutzerDTODataAccess.deleteById(benutzerDTO.id());
        }
        return CheckResult.failure(textProvider.getTranslation(TranslationConstants.ERROR_MISSING_ID, Locale.GERMAN));
    }
}
