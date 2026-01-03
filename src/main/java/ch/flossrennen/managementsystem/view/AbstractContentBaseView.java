package ch.flossrennen.managementsystem.view;

import ch.flossrennen.managementsystem.dataaccess.dto.DTOProperty;
import ch.flossrennen.managementsystem.service.DTOService;
import ch.flossrennen.managementsystem.util.CheckResult;
import ch.flossrennen.managementsystem.view.components.FlossrennenGrid;
import ch.flossrennen.managementsystem.view.editor.AbstractEditorView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.SortDirection;
import org.jspecify.annotations.NonNull;

import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;

public abstract class AbstractContentBaseView<DTO, PROPERTY extends DTOProperty<DTO>> extends VerticalLayout {

    protected final DTOService<DTO> dtoService;
    protected final FlossrennenGrid<DTO> grid;
    protected final AbstractEditorView<DTO> editor;
    protected final Class<DTO> dtoClass;
    protected final PROPERTY[] allProperties;
    protected final Supplier<DTO> emptyDTOSupplier;
    protected final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public AbstractContentBaseView(DTOService<DTO> dtoService,
                                   Class<DTO> dtoClass,
                                   PROPERTY[] allProperties,
                                   String titleKey,
                                   String newButtonLabelKey,
                                   Supplier<DTO> emptyDTOSupplier) {

        this.dtoService = dtoService;
        this.dtoClass = dtoClass;
        this.allProperties = allProperties;
        this.emptyDTOSupplier = emptyDTOSupplier;

        setClassName(ViewStyles.MAIN_VIEW_CONTENT);
        setSizeFull();

        H2 titel = new H2(getTranslation(titleKey));
        titel.addClassName(ViewStyles.APP_TITEL);
        add(titel);

        editor = createEditor();
        editor.addClassName(ViewStyles.EDITOR_VIEW);
        editor.setVisible(false);
        editor.setSaveListener(this::saveDTO);
        editor.setCancelListener(() -> editDTO(null));
        editor.setDeleteListener(this::deleteDTO);

        Button newButton = new Button(getTranslation(newButtonLabelKey), VaadinIcon.PLUS.create());
        newButton.addClickListener(event -> editDTO(emptyDTOSupplier.get()));

        add(newButton, editor);

        grid = createGrid();
        grid.addClassName(ViewStyles.DATA_GRID);
        updateGrid();
        add(grid);
    }

    protected abstract @NonNull AbstractEditorView<DTO> createEditor();

    private @NonNull FlossrennenGrid<DTO> createGrid() {
        FlossrennenGrid<DTO> grid = new FlossrennenGrid<>(dtoClass);

        Grid.Column<DTO> firstColumn = null;
        for (PROPERTY property : allProperties) {
            Grid.Column<DTO> column = grid.addFilterableColumn(getTranslation(property.getTranslationKey()),
                    dto -> getDisplayValue(dto, property),
                    property.getSchemaKey(),
                    dto -> String.valueOf(getDisplayValue(dto, property)));

            column.setSortable(property.isSortable());

            if (firstColumn == null) {
                firstColumn = column;
            }
        }
        if (firstColumn != null) {
            grid.setDefaultSort(firstColumn, SortDirection.ASCENDING);
        }
        grid.asSingleSelect().addValueChangeListener(event -> editDTO(event.getValue()));

        return grid;
    }

    protected void updateGrid() {
        grid.setFilterableItems(dtoService.findAll());
    }

    protected Object getPropertyValue(DTO dto, PROPERTY property) {
        return property.getGetter().apply(dto);
    }

    protected Object getDisplayValue(DTO dto, PROPERTY property) {
        return property.getFormattedValue(dto, this::getTranslation, dateTimeFormatter);
    }

    protected void saveDTO(DTO dto) {
        CheckResult<DTO> result = dtoService.save(dto);
        if (result.isSuccess()) {
            Notification.show(result.getMessage())
                    .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            updateGrid();
            editDTO(null);
        } else {
            Notification.show(result.getMessage())
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    protected void editDTO(DTO dto) {
        if (dto == null) {
            editor.setDTO(null);
            editor.setVisible(false);
            return;
        }

        editor.setVisible(true);
        editor.setDTO(dto);
    }

    protected void deleteDTO(DTO dto) {
        CheckResult<Void> result = dtoService.delete(dto);
        if (result.isSuccess()) {
            Notification.show(result.getMessage())
                    .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            updateGrid();
            editDTO(null);
        } else {
            Notification.show(result.getMessage())
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }
}