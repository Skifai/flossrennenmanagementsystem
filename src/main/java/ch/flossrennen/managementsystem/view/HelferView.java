package ch.flossrennen.managementsystem.view;

import ch.flossrennen.managementsystem.dataaccess.dto.HelferDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.HelferDTOProperties;
import ch.flossrennen.managementsystem.service.HelferDTOService;
import ch.flossrennen.managementsystem.service.RessortDTOService;
import ch.flossrennen.managementsystem.view.editor.HelferEditor;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.jspecify.annotations.NonNull;

import java.util.Set;

//@PermitAll
@Route(value = ViewRoutes.HELFER, layout = MainView.class)
@AnonymousAllowed
public class HelferView extends VerticalLayout {

    private final HelferDTOService helferDTOService;
    private final RessortDTOService ressortDTOService;

    private final Grid<HelferDTO> helferGrid;
    private final TextField fieldFilter;
    private final HelferEditor editorView;

    public HelferView(HelferDTOService helferDTOService, RessortDTOService ressortDTOService) {

        setClassName("MainViewContent");
        setSizeFull();

        this.helferDTOService = helferDTOService;
        this.ressortDTOService = ressortDTOService;

        H1 titel = new H1("Helferverwaltung");
        titel.addClassName("AppTitel");
        add(titel);

        fieldFilter = new TextField();
        editorView = createHelferEditor();
        Button neuerHelferButton = new Button("Neuer Helfer", VaadinIcon.PLUS.create());
        neuerHelferButton.addClickListener(e -> editHelferDTO(HelferDTO.createEmptyDTO()));

        HorizontalLayout actionBar = new HorizontalLayout(neuerHelferButton, fieldFilter);
        add(actionBar, editorView);

        helferGrid = createHelferGrid();

        updateHelferGrid();
        add(helferGrid);
    }

    private @NonNull HelferEditor createHelferEditor() {
        final HelferEditor editorView = new HelferEditor();
        editorView.setVisible(false);

        editorView.setSaveListener(helferDTO -> saveHelferDTO(editorView.getHelferDTO()));
        editorView.setCancelListener(() -> editHelferDTO(null));
        editorView.setDeleteListener(helferDTO -> deleteHelferDTO(editorView.getHelferDTO()));

        return editorView;
    }

    private @NonNull Grid<HelferDTO> createHelferGrid() {
        Grid<HelferDTO> helferGrid = new Grid<>(HelferDTO.class, false);

        for (HelferDTOProperties property : HelferDTOProperties.values()) {
            helferGrid.addColumn(property.getGetter()::apply)
                    .setHeader(getTranslation(property.getTranslationKey()))
                    .setKey(property.getSchemaKey())
                    .setSortable(true)
                    .setAutoWidth(true)
                    .setResizable(true);
        }
        helferGrid.asSingleSelect().addValueChangeListener(event -> editHelferDTO(event.getValue()));

        HelferDTOProperties[] allProperties = HelferDTOProperties.values();
        CheckboxGroup<HelferDTOProperties> checkboxGroup = new CheckboxGroup<>();
        checkboxGroup.setItems(allProperties);
        checkboxGroup.setItemLabelGenerator(property ->
                getTranslation(property.getTranslationKey()));
        checkboxGroup.addValueChangeListener(event -> {
            Set<HelferDTOProperties> selectedProperties = event.getValue();
            for (HelferDTOProperties property : allProperties) {
                boolean visible = selectedProperties.contains(property);
                helferGrid.getColumnByKey(property.getSchemaKey()).setVisible(visible);
            }
        });

        return helferGrid;

    }

    // TODO: Implement Filtering for HelferDTOs
    private void updateHelferGrid() {
        helferGrid.setItems(helferDTOService.findAll());
    }

    private void editHelferDTO(HelferDTO helferDTO) {
        if (helferDTO == null) {
            editorView.setVisible(false);
            return;
        }

        editorView.setVisible(true);
        editorView.setAvailableRessorts(ressortDTOService.findAll());
        editorView.setHelferDTO(helferDTO);
    }

    private void saveHelferDTO(HelferDTO helferDTO) {
        helferDTOService.save(helferDTO);
        updateHelferGrid();
    }

    private void deleteHelferDTO(HelferDTO helferDTO) {
        helferDTOService.delete(helferDTO);
        updateHelferGrid();
    }
}
