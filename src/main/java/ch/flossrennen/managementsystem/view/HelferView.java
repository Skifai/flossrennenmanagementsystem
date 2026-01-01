package ch.flossrennen.managementsystem.view;

import ch.flossrennen.managementsystem.dataaccess.dto.HelferDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.HelferDTOProperties;
import ch.flossrennen.managementsystem.dataaccess.dto.RessortDTO;
import ch.flossrennen.managementsystem.service.DTOService;
import ch.flossrennen.managementsystem.util.textprovider.TranslationConstants;
import ch.flossrennen.managementsystem.view.editor.HelferEditor;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.jspecify.annotations.NonNull;

@PermitAll
@Route(value = ViewRoutes.HELFER, layout = MainView.class)
public class HelferView extends AbstractContentBaseView<HelferDTO, HelferDTOProperties> {

    private final DTOService<RessortDTO> ressortDTOService;

    public HelferView(DTOService<HelferDTO> helferDTOService, DTOService<RessortDTO> ressortDTOService) {
        super(helferDTOService,
                HelferDTO.class,
                HelferDTOProperties.values(),
                TranslationConstants.HELFER_TITLE,
                TranslationConstants.HELFER_NEW,
                HelferDTO::createEmptyDTO);
        this.ressortDTOService = ressortDTOService;
    }

    @Override
    protected @NonNull HelferEditor createEditor() {
        return new HelferEditor();
    }

    @Override
    protected void editDTO(HelferDTO helferDTO) {
        if (helferDTO != null) {
            ((HelferEditor) editor).setAvailableRessorts(ressortDTOService.findAll());
        }
        super.editDTO(helferDTO);
    }
}
