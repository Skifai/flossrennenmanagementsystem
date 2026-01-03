package ch.flossrennen.managementsystem.view;

import ch.flossrennen.managementsystem.dataaccess.dto.EinsatzDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.EinsatzDTOProperties;
import ch.flossrennen.managementsystem.dataaccess.dto.RessortDTO;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.EinsatzStatus;
import ch.flossrennen.managementsystem.service.DTOService;
import ch.flossrennen.managementsystem.util.textprovider.TranslationConstants;
import ch.flossrennen.managementsystem.view.editor.AbstractEditorView;
import ch.flossrennen.managementsystem.view.editor.EinsatzEditor;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.jspecify.annotations.NonNull;

@PermitAll
@Route(value = ViewRoutes.EINSATZ, layout = MainView.class)
public class EinsatzView extends AbstractContentBaseView<EinsatzDTO, EinsatzDTOProperties> {

    private final DTOService<RessortDTO> ressortDTOService;

    public EinsatzView(DTOService<EinsatzDTO> einsatzDTOService, DTOService<RessortDTO> ressortDTOService) {
        super(einsatzDTOService,
                EinsatzDTO.class,
                EinsatzDTOProperties.values(),
                TranslationConstants.EINSATZ_TITLE,
                TranslationConstants.EINSATZ_NEW,
                EinsatzDTO::createEmptyDTO);
        this.ressortDTOService = ressortDTOService;
    }

    @Override
    protected @NonNull AbstractEditorView<EinsatzDTO> createEditor() {
        return new EinsatzEditor();
    }

    @Override
    protected Object getPropertyValue(EinsatzDTO dto, EinsatzDTOProperties property) {
        Object value = super.getPropertyValue(dto, property);
        if (property == EinsatzDTOProperties.STATUS && value instanceof EinsatzStatus status) {
            return getTranslation(status.getTranslationKey());
        }
        return value;
    }

    @Override
    protected void editDTO(EinsatzDTO einsatzDTO) {
        if (einsatzDTO != null) {
            ((EinsatzEditor) editor).setAvailableRessorts(ressortDTOService.findAll());
        }
        super.editDTO(einsatzDTO);
    }
}