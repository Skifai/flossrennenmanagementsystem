package ch.flossrennen.managementsystem.view;

import ch.flossrennen.managementsystem.dataaccess.dto.LogDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.LogDTOProperties;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.BenutzerRolle;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.LogLevel;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.LogType;
import ch.flossrennen.managementsystem.service.LogDTOService;
import ch.flossrennen.managementsystem.util.textprovider.TranslationConstants;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@PermitAll
@Route(value = ViewRoutes.SYSTEMPROTOKOLL, layout = MainView.class)
public class SystemprotokollView extends VerticalLayout {

    private final LogDTOService logDTOService;
    private final Grid<LogDTO> grid;
    private final String filterPlaceholder;
    private ComboBox<LogType> typeFilter;
    private ComboBox<LogLevel> levelFilter;
    private ComboBox<String> userFilter;
    private DateTimePicker fromFilter;
    private DateTimePicker toFilter;

    public SystemprotokollView(LogDTOService logDTOService) {
        this.logDTOService = logDTOService;
        this.filterPlaceholder = getTranslation(TranslationConstants.SYSTEMPROTOKOLL_FILTER_PLACEHOLDER);

        setClassName(ViewStyles.MAIN_VIEW_CONTENT);
        setSizeFull();

        H2 titel = new H2(getTranslation(TranslationConstants.SYSTEMPROTOKOLL_TITLE));
        titel.addClassName(ViewStyles.APP_TITEL);
        add(titel);

        HorizontalLayout filterBar = createFilterBar();
        add(filterBar);

        grid = createLogGrid();
        add(grid);
        updateGrid();
    }

    private @NonNull HorizontalLayout createFilterBar() {
        HorizontalLayout filterBar = new HorizontalLayout();

        typeFilter = new ComboBox<>(getTranslation(TranslationConstants.SYSTEMPROTOKOLL_FILTER_TYPE));
        typeFilter.setItems(logDTOService.findUniqueTypes());
        typeFilter.setItemLabelGenerator(type -> getTranslation(type.getTranslationKey()));
        typeFilter.setPlaceholder(filterPlaceholder);
        typeFilter.setClearButtonVisible(true);

        levelFilter = new ComboBox<>(getTranslation(TranslationConstants.SYSTEMPROTOKOLL_FILTER_LEVEL));
        levelFilter.setItems(logDTOService.findUniqueLogLevels());
        levelFilter.setItemLabelGenerator(level -> getTranslation(level.getTranslationKey()));
        levelFilter.setPlaceholder(filterPlaceholder);
        levelFilter.setClearButtonVisible(true);

        userFilter = new ComboBox<>(getTranslation(TranslationConstants.SYSTEMPROTOKOLL_FILTER_USER));
        userFilter.setItems(logDTOService.findUniqueUsers());
        userFilter.setPlaceholder(filterPlaceholder);
        userFilter.setClearButtonVisible(true);

        fromFilter = new DateTimePicker(getTranslation(TranslationConstants.SYSTEMPROTOKOLL_FILTER_FROM));
        fromFilter.setValue(LocalDateTime.now().minusDays(30).toLocalDate().atStartOfDay());

        toFilter = new DateTimePicker(getTranslation(TranslationConstants.SYSTEMPROTOKOLL_FILTER_TO));
        toFilter.setValue(LocalDateTime.now().plusDays(1).toLocalDate().atStartOfDay());

        Button filterButton = new Button(getTranslation(TranslationConstants.FILTER));
        filterButton.addClickListener(e -> updateGrid());

        filterBar.add(typeFilter, levelFilter, userFilter, fromFilter, toFilter, filterButton);
        filterBar.setWidthFull();
        filterBar.setSpacing(true);
        filterBar.setVerticalComponentAlignment(Alignment.END, filterButton);

        return filterBar;
    }

    private @NonNull Grid<LogDTO> createLogGrid() {
        final Grid<LogDTO> grid;
        grid = new Grid<>(LogDTO.class, false);
        grid.addClassName(ViewStyles.DATA_GRID);

        for (LogDTOProperties property : LogDTOProperties.values()) {
            Grid.Column<LogDTO> column;
            switch (property) {
                case TIMESTAMP -> column = grid.addColumn(new LocalDateTimeRenderer<>(LogDTO::timestamp));
                case MESSAGE -> column = grid.addColumn(new ComponentRenderer<>(logDTO -> {
                    Span span = new Span(logDTO.message());
                    span.getStyle().set("white-space", "pre-wrap");
                    return span;
                }));
                case TYPE -> column = grid.addColumn(logDTO -> getTranslation(logDTO.type().getTranslationKey()));
                case LOG_LEVEL ->
                        column = grid.addColumn(logDTO -> getTranslation(logDTO.logLevel().getTranslationKey()));
                default -> column = grid.addColumn(property.getGetter()::apply);
            }
            column.setHeader(getTranslation(property.getTranslationKey()))
                    .setKey(property.getSchemaKey())
                    .setResizable(true)
                    .setSortable(true)
                    .setAutoWidth(true)
                    .setFlexGrow(0)
                    .addClassName(ViewStyles.DATA_GRID_COLUMN);
        }

        return grid;
    }

    private void updateGrid() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + BenutzerRolle.ADMINISTRATOR.name()));
        boolean isRessortleiter = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + BenutzerRolle.RESSORTLEITER.name()));

        List<LogType> allowedTypes = null;
        if (isRessortleiter) {
            allowedTypes = Arrays.asList(
                    LogType.HELFER_CREATED,
                    LogType.HELFER_UPDATED,
                    LogType.HELFER_DELETED,
                    LogType.RESSORT_CREATED,
                    LogType.RESSORT_UPDATED,
                    LogType.RESSORT_DELETED
            );
        } else if (!isAdmin) {
            grid.setItems(List.of());
            return;
        }

        grid.setItems(logDTOService.findAllFiltered(
                typeFilter.getValue(),
                levelFilter.getValue(),
                userFilter.getValue(),
                fromFilter.getValue(),
                toFilter.getValue(),
                allowedTypes
        ));
    }
}