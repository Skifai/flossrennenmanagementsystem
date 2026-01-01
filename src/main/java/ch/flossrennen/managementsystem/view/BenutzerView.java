package ch.flossrennen.managementsystem.view;

import ch.flossrennen.managementsystem.dataaccess.dto.BenutzerDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.BenutzerDTOProperties;
import ch.flossrennen.managementsystem.service.DTOService;
import ch.flossrennen.managementsystem.util.textprovider.TranslationConstants;
import ch.flossrennen.managementsystem.view.editor.AbstractEditorView;
import ch.flossrennen.managementsystem.view.editor.BenutzerEditor;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.jspecify.annotations.NonNull;

@PermitAll
@Route(value = ViewRoutes.BENUTZER, layout = MainView.class)
public class BenutzerView extends AbstractContentBaseView<BenutzerDTO, BenutzerDTOProperties> {

    public BenutzerView(DTOService<BenutzerDTO> benutzerDTOService) {
        super(benutzerDTOService, BenutzerDTO.class, BenutzerDTOProperties.values(), TranslationConstants.BENUTZER_TITLE, TranslationConstants.BENUTZER_NEW, BenutzerDTO::createEmptyDTO);
    }

    @Override
    protected @NonNull AbstractEditorView<BenutzerDTO> createEditor() {
        return new BenutzerEditor();
    }
}
