package ch.flossrennen.managementsystem.view;

import ch.flossrennen.managementsystem.dataaccess.dto.*;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.EinsatzStatus;
import ch.flossrennen.managementsystem.service.DTOService;
import ch.flossrennen.managementsystem.service.EinsatzZuweisungDTOService;
import ch.flossrennen.managementsystem.util.CheckResult;
import ch.flossrennen.managementsystem.util.textprovider.TranslationConstants;
import ch.flossrennen.managementsystem.view.components.FlossrennenGrid;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@PermitAll
@Route(value = ViewRoutes.EINSATZZUWEISUNG, layout = MainView.class)
public class EinsatzZuweisungView extends VerticalLayout {

    private final DTOService<EinsatzDTO> einsatzDTOService;
    private final EinsatzZuweisungDTOService zuweisungDTOService;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private FlossrennenGrid<EinsatzDTO> einsatzGrid;
    private FlossrennenGrid<HelferDTO> verfuegbareHelferGrid;
    private FlossrennenGrid<HelferDTO> zugewieseneHelferGrid;
    private Button zuweisenButton;
    private Button entfernenButton;

    private final List<HelferDTO> zugewieseneHelfer = new ArrayList<>();
    private final List<HelferDTO> verfuegbareHelfer = new ArrayList<>();

    public EinsatzZuweisungView(DTOService<EinsatzDTO> einsatzDTOService,
                                EinsatzZuweisungDTOService zuweisungDTOService) {
        this.einsatzDTOService = einsatzDTOService;
        this.zuweisungDTOService = zuweisungDTOService;

        addClassName(ViewStyles.MAIN_VIEW_CONTENT);
        setSizeFull();

        H2 title = new H2(getTranslation(TranslationConstants.EINSATZ_ZUWEISUNG_TITLE));
        title.addClassName(ViewStyles.APP_TITEL);
        add(title);

        createEinsatzGrid();
        updateEinsatzGrid();
        createHelferGrids();
    }

    private void createEinsatzGrid() {
        einsatzGrid = new FlossrennenGrid<>(EinsatzDTO.class);
        einsatzGrid.addClassName(ViewStyles.DATA_GRID);

        Grid.Column<EinsatzDTO> idColumn = addEinsatzColumn(EinsatzDTOProperties.ID);
        addEinsatzColumn(EinsatzDTOProperties.RESSORT);
        addEinsatzColumn(EinsatzDTOProperties.NAME);

        einsatzGrid.addStaticColumn(getTranslation(EinsatzDTOProperties.STARTZEIT.getTranslationKey()),
                        einsatzDTO ->
                                EinsatzDTOProperties.STARTZEIT
                                        .getFormattedValue(einsatzDTO, this::getTranslation, dateTimeFormatter))
                .setSortable(false);

        einsatzGrid.addStaticColumn(getTranslation(EinsatzDTOProperties.ENDZEIT.getTranslationKey()),
                        einsatzDTO ->
                                EinsatzDTOProperties.ENDZEIT
                                        .getFormattedValue(einsatzDTO, this::getTranslation, dateTimeFormatter))
                .setSortable(false);

        addEinsatzColumn(EinsatzDTOProperties.ORT);
        addEinsatzColumn(EinsatzDTOProperties.STATUS);
        addEinsatzColumn(EinsatzDTOProperties.BENOETIGTE_HELFER);

        einsatzGrid.addStaticColumn(getTranslation(TranslationConstants.EINSATZ_ZUWEISUNG_MISSING_HELFER),
                einsatzDTO -> String.valueOf(zuweisungDTOService.getMissingHelferCount(einsatzDTO)));

        einsatzGrid.setDefaultSort(idColumn, SortDirection.ASCENDING);

        einsatzGrid.addSelectionListener(event ->
                onEinsatzSelected(event.getFirstSelectedItem().orElse(null)));

        H4 subtitle = new H4(getTranslation(TranslationConstants.EINSATZ_ZUWEISUNG_EINSATZ));
        subtitle.addClassName(ViewStyles.APP_SUB_TITEL);

        VerticalLayout einsatzLayout = new VerticalLayout(subtitle, einsatzGrid);
        einsatzLayout.setPadding(false);
        einsatzLayout.setHeightFull();

        add(einsatzLayout);
        setFlexGrow(1, einsatzLayout);
    }

    private Grid.Column<EinsatzDTO> addEinsatzColumn(EinsatzDTOProperties property) {
        return einsatzGrid.addFilterableColumn(getTranslation(property.getTranslationKey()),
                einsatzDTO ->
                        property.getFormattedValue(einsatzDTO, this::getTranslation, dateTimeFormatter),
                property.getSchemaKey(),
                einsatzDTO ->
                        String.valueOf(property.getFormattedValue(einsatzDTO, this::getTranslation, dateTimeFormatter)));
    }

    private void onEinsatzSelected(EinsatzDTO einsatz) {
        HelferSelectionDTO selection = zuweisungDTOService.getHelferSelection(einsatz);
        zugewieseneHelfer.clear();
        zugewieseneHelfer.addAll(selection.zugewieseneHelfer());
        verfuegbareHelfer.clear();
        verfuegbareHelfer.addAll(selection.verfuegbareHelfer());

        updateHelperGrids();
        updateButtonStates();
    }

    private void updateButtonStates() {
        EinsatzDTO selectedEinsatz = einsatzGrid.asSingleSelect().getValue();
        HelferDTO selectedVerfuegbar = verfuegbareHelferGrid.asSingleSelect().getValue();
        HelferDTO selectedZugewiesen = zugewieseneHelferGrid.asSingleSelect().getValue();

        boolean canAssign = selectedEinsatz != null
                && selectedVerfuegbar != null
                && selectedEinsatz.status() != EinsatzStatus.ABGESCHLOSSEN;
        zuweisenButton.setEnabled(canAssign);

        boolean canUnassign = selectedEinsatz != null
                && selectedZugewiesen != null;
        entfernenButton.setEnabled(canUnassign);
    }

    private Grid.Column<HelferDTO> addHelferColumn(FlossrennenGrid<HelferDTO> grid, HelferDTOProperties property) {
        return grid.addFilterableColumn(getTranslation(property.getTranslationKey()),
                helferDTO ->
                        property.getFormattedValue(helferDTO, this::getTranslation, dateTimeFormatter),
                property.getSchemaKey(),
                helferDTO ->
                        String.valueOf(property.getFormattedValue(helferDTO, this::getTranslation, dateTimeFormatter)));
    }

    private void updateEinsatzGrid() {
        einsatzGrid.setFilterableItems(zuweisungDTOService.findAllEinsatzForZuweisung());
    }

    private void createHelferGrids() {
        HorizontalLayout helferGridsLayout = new HorizontalLayout();
        helferGridsLayout.setSizeFull();

        verfuegbareHelferGrid = new FlossrennenGrid<>(HelferDTO.class);
        verfuegbareHelferGrid.addClassName(ViewStyles.DATA_GRID);

        Grid.Column<HelferDTO> verfuegbareIdColumn = addHelferColumn(verfuegbareHelferGrid, HelferDTOProperties.ID);
        addHelferColumn(verfuegbareHelferGrid, HelferDTOProperties.RESSORT);

        addHelferColumn(verfuegbareHelferGrid, HelferDTOProperties.VORNAME);
        addHelferColumn(verfuegbareHelferGrid, HelferDTOProperties.NACHNAME);
        addHelferColumn(verfuegbareHelferGrid, HelferDTOProperties.EMAIL);

        verfuegbareHelferGrid.setDefaultSort(verfuegbareIdColumn, SortDirection.ASCENDING);

        verfuegbareHelferGrid.setFilterableItems(verfuegbareHelfer);
        verfuegbareHelferGrid.addSelectionListener(event -> updateButtonStates());

        HorizontalLayout verfuegbareHelferTitleBar = new HorizontalLayout();

        H4 verfuegbarTitel = new H4(getTranslation(TranslationConstants.EINSATZ_ZUWEISUNG_VERFUEGBAR));
        verfuegbarTitel.addClassName(ViewStyles.APP_SUB_TITEL);

        zuweisenButton = new Button(getTranslation(TranslationConstants.EINSATZ_ZUWEISUNG_BUTTON_ASSIGN));
        zuweisenButton.addClickListener(event -> helferZuweisen());
        zuweisenButton.addClassName(ViewStyles.BUTTON);
        zuweisenButton.setEnabled(false);

        verfuegbareHelferTitleBar.add(verfuegbarTitel, zuweisenButton);

        VerticalLayout verfuegbareHelferLayout = new VerticalLayout();
        verfuegbareHelferLayout.add(verfuegbareHelferTitleBar, verfuegbareHelferGrid);

        verfuegbareHelferLayout.setPadding(false);
        verfuegbareHelferLayout.setSizeFull();

        zugewieseneHelferGrid = new FlossrennenGrid<>(HelferDTO.class);
        zugewieseneHelferGrid.addClassName(ViewStyles.DATA_GRID);

        Grid.Column<HelferDTO> zugewieseneIdColumn = addHelferColumn(zugewieseneHelferGrid, HelferDTOProperties.ID);
        addHelferColumn(zugewieseneHelferGrid, HelferDTOProperties.RESSORT);

        addHelferColumn(zugewieseneHelferGrid, HelferDTOProperties.VORNAME);
        addHelferColumn(zugewieseneHelferGrid, HelferDTOProperties.NACHNAME);
        addHelferColumn(zugewieseneHelferGrid, HelferDTOProperties.EMAIL);

        zugewieseneHelferGrid.setDefaultSort(zugewieseneIdColumn, SortDirection.ASCENDING);

        zugewieseneHelferGrid.setFilterableItems(zugewieseneHelfer);
        zugewieseneHelferGrid.addSelectionListener(event -> updateButtonStates());

        HorizontalLayout zugewieseneHelferTitleBar = new HorizontalLayout();

        H4 zugewiesenTitle = new H4(getTranslation(TranslationConstants.EINSATZ_ZUWEISUNG_ZUGEWIESEN));
        zugewiesenTitle.addClassName(ViewStyles.APP_SUB_TITEL);

        entfernenButton = new Button(getTranslation(TranslationConstants.EINSATZ_ZUWEISUNG_BUTTON_UNASSIGN));
        entfernenButton.addClickListener(event -> helferEntfernen());
        entfernenButton.addClassName(ViewStyles.BUTTON);
        entfernenButton.setEnabled(false);

        zugewieseneHelferTitleBar.add(zugewiesenTitle, entfernenButton);

        VerticalLayout zugewieseneHelferLayout = new VerticalLayout();
        zugewieseneHelferLayout.add(zugewieseneHelferTitleBar, zugewieseneHelferGrid);

        zugewieseneHelferLayout.setPadding(false);
        zugewieseneHelferLayout.setSizeFull();

        helferGridsLayout.add(verfuegbareHelferLayout, zugewieseneHelferLayout);
        add(helferGridsLayout);
        setFlexGrow(1, helferGridsLayout);
    }

    private void helferZuweisen() {
        EinsatzDTO selectedEinsatz = einsatzGrid.asSingleSelect().getValue();
        HelferDTO selectedHelfer = verfuegbareHelferGrid.asSingleSelect().getValue();

        if (selectedEinsatz != null && selectedHelfer != null) {
            EinsatzZuweisungDTO zuweisung = new EinsatzZuweisungDTO(null, selectedEinsatz.id(), selectedHelfer.id());
            CheckResult<EinsatzZuweisungDTO> result = zuweisungDTOService.save(zuweisung);

            if (result.isSuccess()) {
                Notification.show(getTranslation(TranslationConstants.SUCCESS_SAVE));
                updateEinsatzGrid();
                verfuegbareHelferGrid.deselectAll();
                einsatzDTOService.findById(selectedEinsatz.id()).ifPresent(einsatzDTO -> {
                    if (einsatzDTO.status() != EinsatzStatus.OFFEN) {
                        einsatzGrid.deselectAll();
                        onEinsatzSelected(null);
                    } else {
                        einsatzGrid.select(einsatzDTO);
                        onEinsatzSelected(einsatzDTO);
                    }
                });
            } else {
                Notification.show(result.getMessage(), 5000, Notification.Position.MIDDLE);
            }
        }
    }

    private void helferEntfernen() {
        EinsatzDTO selectedEinsatz = einsatzGrid.asSingleSelect().getValue();
        HelferDTO selectedHelfer = zugewieseneHelferGrid.asSingleSelect().getValue();

        if (selectedEinsatz != null && selectedHelfer != null) {
            zuweisungDTOService.deleteByEinsatzIdAndHelferId(selectedEinsatz.id(), selectedHelfer.id());
            Notification.show(getTranslation(TranslationConstants.SUCCESS_DELETE));
            updateEinsatzGrid();
            zugewieseneHelferGrid.deselectAll();
            einsatzDTOService.findById(selectedEinsatz.id()).ifPresent(einsatzDTO -> {
                einsatzGrid.select(einsatzDTO);
                onEinsatzSelected(einsatzDTO);
            });
        }
    }

    private void updateHelperGrids() {
        verfuegbareHelferGrid.getDataView().refreshAll();
        zugewieseneHelferGrid.getDataView().refreshAll();
    }
}
