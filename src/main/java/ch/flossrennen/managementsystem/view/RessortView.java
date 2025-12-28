package ch.flossrennen.managementsystem.view;

import ch.flossrennen.managementsystem.dataaccess.dto.RessortDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.RessortDTOProperties;
import ch.flossrennen.managementsystem.service.DTOService;
import ch.flossrennen.managementsystem.view.editor.AbstractEditorView;
import ch.flossrennen.managementsystem.view.editor.RessortEditor;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.jspecify.annotations.NonNull;

@Route(value = ViewRoutes.RESSORT, layout = MainView.class)
@AnonymousAllowed
public class RessortView extends AbstractContentBaseView<RessortDTO, RessortDTOProperties> {

    public RessortView(DTOService<RessortDTO> ressortDTOService) {
        super(ressortDTOService, RessortDTO.class, RessortDTOProperties.values(), "Ressortverwaltung", "Neues Ressort", RessortDTO::createEmptyDTO);
    }

    @Override
    protected @NonNull AbstractEditorView<RessortDTO> createEditor() {
        return new RessortEditor();
    }
}
