package ch.flossrennen.managementsystem.view;

import ch.flossrennen.managementsystem.dataaccess.dto.DTOProperty;
import ch.flossrennen.managementsystem.service.DTOService;
import ch.flossrennen.managementsystem.util.CheckResult;
import ch.flossrennen.managementsystem.util.textprovider.TranslationConstants;
import ch.flossrennen.managementsystem.view.editor.AbstractEditorView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.jspecify.annotations.NonNull;

import java.util.Set;
import java.util.function.Supplier;

public abstract class AbstractContentBaseView<DTO, PROPERTY extends DTOProperty<DTO>> extends VerticalLayout {

    protected final DTOService<DTO> dtoService;
    protected final Grid<DTO> grid;
    protected final TextField fieldFilter;
    protected final AbstractEditorView<DTO> editor;
    protected final Class<DTO> dtoClass;
    protected final PROPERTY[] allProperties;
    protected final Supplier<DTO> emptyDTOSupplier;

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

        fieldFilter = new TextField();
        fieldFilter.addClassName(ViewStyles.FIELD_FILTER);
        fieldFilter.setPlaceholder(getTranslation(TranslationConstants.FILTER));
        fieldFilter.setPrefixComponent(VaadinIcon.SEARCH.create());
        fieldFilter.setClearButtonVisible(true);

        editor = createEditor();
        editor.addClassName(ViewStyles.EDITOR_VIEW);
        editor.setVisible(false);
        editor.setSaveListener(this::saveDTO);
        editor.setCancelListener(() -> editDTO(null));
        editor.setDeleteListener(this::deleteDTO);

        Button newButton = new Button(getTranslation(newButtonLabelKey), VaadinIcon.PLUS.create());
        newButton.addClickListener(e -> editDTO(emptyDTOSupplier.get()));

        HorizontalLayout actionBar = new HorizontalLayout(newButton, fieldFilter);
        add(actionBar, editor);

        grid = createGrid();
        grid.addClassName(ViewStyles.DATA_GRID);
        updateGrid();
        add(grid);
    }

    protected abstract @NonNull AbstractEditorView<DTO> createEditor();

    private @NonNull Grid<DTO> createGrid() {
        Grid<DTO> grid = new Grid<>(dtoClass, false);

        for (PROPERTY property : allProperties) {
            grid.addColumn(dto -> getPropertyValue(dto, property))
                    .setHeader(getTranslation(property.getTranslationKey()))
                    .setKey(property.getSchemaKey())
                    .setSortable(true)
                    .setAutoWidth(true)
                    .setFlexGrow(0)
                    .setResizable(true);
        }
        grid.asSingleSelect().addValueChangeListener(event -> editDTO(event.getValue()));

        CheckboxGroup<PROPERTY> checkboxGroup = new CheckboxGroup<>();
        checkboxGroup.setItems(allProperties);
        checkboxGroup.setItemLabelGenerator(property -> getTranslation(property.getTranslationKey()));
        checkboxGroup.addValueChangeListener(event -> {
            Set<PROPERTY> selectedProperties = event.getValue();
            for (PROPERTY property : allProperties) {
                boolean visible = selectedProperties.contains(property);
                grid.getColumnByKey(property.getSchemaKey()).setVisible(visible);
            }
        });

        return grid;
    }

    protected Object getPropertyValue(DTO dto, PROPERTY property) {
        return property.getGetter().apply(dto);
    }

    protected void updateGrid() {
        grid.setItems(dtoService.findAll());
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