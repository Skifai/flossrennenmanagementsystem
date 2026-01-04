package ch.flossrennen.managementsystem.view.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Eine spezialisierte Grid-Komponente für das Flossrennen-Managementsystem mit integrierter Filterfunktion.
 *
 * @param <T> Der Typ der im Grid angezeigten Daten.
 */
public class FlossrennenGrid<T> extends Grid<T> {

    private final Map<String, String> filterValues = new HashMap<>();
    private final Map<String, Function<T, String>> filterExtractors = new HashMap<>();
    private GridListDataView<T> dataView;
    private HeaderRow filterRow;

    /**
     * Erstellt ein neues FlossrennenGrid für den angegebenen DTO-Typ.
     *
     * @param dtoTyp Die Klasse des DTO-Typs.
     */
    public FlossrennenGrid(@NonNull Class<T> dtoTyp) {
        super(dtoTyp, false);
    }

    /**
     * Setzt die filterbaren Elemente für das Grid.
     *
     * @param items Eine Kollektion von Elementen.
     */
    public void setFilterableItems(@NonNull Collection<T> items) {
        this.dataView = setItems(items);
        this.dataView.addFilter(this::matchesFilters);
    }

    private boolean matchesFilters(@NonNull T item) {
        return filterValues.entrySet().stream()
                .allMatch(entry -> {
                    String searchTerm = entry.getValue();
                    if (searchTerm == null || searchTerm.isEmpty()) {
                        return true;
                    }

                    Function<T, String> extractor = filterExtractors.get(entry.getKey());
                    if (extractor == null) {
                        return true;
                    }

                    String value = extractor.apply(item);
                    return value != null && value.toLowerCase().contains(searchTerm.toLowerCase());
                });
    }

    /**
     * Fügt eine filterbare Spalte zum Grid hinzu.
     *
     * @param labelText       Die Beschriftung der Spalte (und Platzhalter für den Filter).
     * @param valueProvider   Der Provider für den Spaltenwert.
     * @param filterKey       Ein eindeutiger Schlüssel für den Filter.
     * @param filterExtractor Eine Funktion, die den zu filternden String aus dem Element extrahiert.
     * @return Die erstellte Spalte.
     */
    public @NonNull Column<T> addFilterableColumn(@NonNull String labelText,
                                                  @NonNull Function<T, Object> valueProvider,
                                                  @NonNull String filterKey,
                                                  @NonNull Function<T, String> filterExtractor) {
        Column<T> column = addColumn(valueProvider::apply)
                .setSortable(true)
                .setAutoWidth(true)
                .setFlexGrow(0)
                .setResizable(true);

        ensureFilterRow();

        filterExtractors.put(filterKey, filterExtractor);
        filterRow.getCell(column).setComponent(createFilterHeader(labelText, value -> {
            filterValues.put(filterKey, value);
            if (dataView != null) {
                dataView.refreshAll();
            }
        }));

        return column;
    }

    private void ensureFilterRow() {
        if (filterRow == null) {
            getHeaderRows().clear();
            filterRow = appendHeaderRow();
            addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COLUMN_BORDERS);
        }
    }

    private @NonNull Component createFilterHeader(@NonNull String labelText, @NonNull Consumer<String> filterChangeConsumer) {
        TextField textField = new TextField();
        textField.setPlaceholder(labelText);
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.setClearButtonVisible(true);
        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL, TextFieldVariant.LUMO_ALIGN_CENTER);
        textField.setWidthFull();
        textField.getStyle().set("max-width", "100%");
        textField.addValueChangeListener(event -> filterChangeConsumer.accept(event.getValue()));

        return textField;
    }

    /**
     * Fügt eine statische Spalte (ohne Filterfunktion) zum Grid hinzu.
     *
     * @param labelText Die Beschriftung der Spalte.
     * @param valueProvider Der Provider für den Spaltenwert.
     * @return Die erstellte Spalte.
     */
    public @NonNull Column<T> addStaticColumn(@NonNull String labelText,
                                              @NonNull Function<T, ?> valueProvider) {
        Column<T> column = addColumn(valueProvider::apply)
                .setSortable(true)
                .setAutoWidth(true)
                .setFlexGrow(0)
                .setResizable(true);

        ensureFilterRow();

        filterRow.getCell(column).setComponent(createStaticHeader(labelText));

        return column;
    }

    private @NonNull Component createStaticHeader(@NonNull String labelText) {
        TextField textField = new TextField();
        textField.setValue(labelText);
        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL, TextFieldVariant.LUMO_ALIGN_CENTER);
        textField.setWidthFull();
        textField.getStyle().set("max-width", "100%");
        textField.setReadOnly(true);
        textField.getStyle().set("color", "var(--lumo-header-text-color)");
        textField.getStyle().set("font-weight", "bold");
        textField.getStyle().set("pointer-events", "none");

        return textField;
    }

    /**
     * Gibt die DataView des Grids zurück.
     *
     * @return Die GridListDataView oder null.
     */
    public @Nullable GridListDataView<T> getDataView() {
        return dataView;
    }

    /**
     * Setzt die Standardsortierung für eine Spalte.
     *
     * @param column Die Spalte, nach der sortiert werden soll.
     * @param direction Die Sortierrichtung.
     */
    public void setDefaultSort(@NonNull Column<T> column, @NonNull SortDirection direction) {
        sort(List.of(new GridSortOrder<>(column, direction)));
    }
}