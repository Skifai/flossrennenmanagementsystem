package ch.flossrennen.managementsystem.view;

import ch.flossrennen.managementsystem.dataaccess.dto.BenutzerDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.RessortDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.RessortDTOProperties;
import ch.flossrennen.managementsystem.service.DTOService;
import ch.flossrennen.managementsystem.util.textprovider.TranslationConstants;
import ch.flossrennen.managementsystem.view.editor.AbstractEditorView;
import ch.flossrennen.managementsystem.view.editor.RessortEditor;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.jspecify.annotations.NonNull;

@Route(value = ViewRoutes.RESSORT, layout = MainView.class)
@AnonymousAllowed
public class RessortView extends AbstractContentBaseView<RessortDTO, RessortDTOProperties> {

    private final DTOService<BenutzerDTO> benutzerDTOService;

    public RessortView(DTOService<RessortDTO> ressortDTOService, DTOService<BenutzerDTO> benutzerDTOService) {
        super(ressortDTOService,
                RessortDTO.class,
                RessortDTOProperties.values(),
                TranslationConstants.RESSORT_TITLE,
                TranslationConstants.RESSORT_NEW,
                RessortDTO::createEmptyDTO);
        this.benutzerDTOService = benutzerDTOService;
    }

    @Override
    protected @NonNull AbstractEditorView<RessortDTO> createEditor() {
        return new RessortEditor();
    }

    @Override
    protected void editDTO(RessortDTO ressortDTO) {
        if (ressortDTO != null) {
            ((RessortEditor) editor).setAvailableBenutzer(benutzerDTOService.findAll());
        }
        super.editDTO(ressortDTO);
    }
}
