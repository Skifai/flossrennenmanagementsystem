package ch.flossrennen.managementsystem.view;

import ch.flossrennen.managementsystem.dataaccess.dto.BenutzerDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.BenutzerDTOProperties;
import ch.flossrennen.managementsystem.service.DTOService;
import ch.flossrennen.managementsystem.view.editor.AbstractEditorView;
import ch.flossrennen.managementsystem.view.editor.BenutzerEditor;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.jspecify.annotations.NonNull;

@Route(value = ViewRoutes.BENUTZER, layout = MainView.class)
@AnonymousAllowed
public class BenutzerView extends AbstractContentBaseView<BenutzerDTO, BenutzerDTOProperties> {

    public BenutzerView(DTOService<BenutzerDTO> benutzerDTOService) {
        super(benutzerDTOService, BenutzerDTO.class, BenutzerDTOProperties.values(), "Benutzerverwaltung", "Neuer Benutzer", BenutzerDTO::createEmptyDTO);
    }

    @Override
    protected @NonNull AbstractEditorView<BenutzerDTO> createEditor() {
        return new BenutzerEditor();
    }
}
