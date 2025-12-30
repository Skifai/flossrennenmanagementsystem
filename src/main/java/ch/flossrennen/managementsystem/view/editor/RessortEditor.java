package ch.flossrennen.managementsystem.view.editor;

import ch.flossrennen.managementsystem.dataaccess.dto.BenutzerDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.RessortDTO;
import ch.flossrennen.managementsystem.util.TranslationConstants;
import ch.flossrennen.managementsystem.view.ViewStyles;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.converter.StringToLongConverter;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class RessortEditor extends AbstractEditorView<RessortDTO> {

    private ComboBox<BenutzerDTO> fieldRessortleitung;

    public RessortEditor() {
        super(RessortDTO.class);
    }

    public void setAvailableBenutzer(List<BenutzerDTO> benutzer) {
        if (fieldRessortleitung != null) {
            fieldRessortleitung.setItems(benutzer);
            fieldRessortleitung.setItemLabelGenerator(b -> b.vorname() + " " + b.nachname());
        }
    }

    @Override
    protected @NonNull FormLayout createFormFields() {
        FormLayout formFields = new FormLayout();
        formFields.setAutoResponsive(true);

        TextField fieldID = new TextField(getTranslation(TranslationConstants.RESSORT_ID));
        fieldID.addClassName(ViewStyles.EDITOR_FIELD);
        fieldID.setReadOnly(true);
        fieldID.setWidthFull();
        fieldID.setPrefixComponent(VaadinIcon.HASH.create());

        TextField fieldName = new TextField(getTranslation(TranslationConstants.RESSORT_NAME));
        fieldName.addClassName(ViewStyles.EDITOR_FIELD);
        fieldName.setRequired(true);
        fieldName.setWidthFull();
        fieldName.setMaxLength(100);

        TextArea fieldBeschreibung = new TextArea(getTranslation(TranslationConstants.RESSORT_BESCHREIBUNG));
        fieldBeschreibung.addClassName(ViewStyles.EDITOR_FIELD);
        fieldBeschreibung.setWidthFull();
        fieldBeschreibung.setMaxLength(300);

        TextArea fieldZustaendigkeit = new TextArea(getTranslation(TranslationConstants.RESSORT_ZUSTAENDIGKEIT));
        fieldZustaendigkeit.addClassName(ViewStyles.EDITOR_FIELD);
        fieldZustaendigkeit.setWidthFull();
        fieldZustaendigkeit.setMaxLength(300);

        fieldRessortleitung = new ComboBox<>(getTranslation(TranslationConstants.RESSORT_RESSORTLEITUNG));
        fieldRessortleitung.addClassName(ViewStyles.EDITOR_FIELD);
        fieldRessortleitung.setPrefixComponent(VaadinIcon.USER.create());

        formFields.addFormRow(fieldID);
        formFields.addFormRow(fieldName, fieldRessortleitung);

        formFields.addFormRow(fieldBeschreibung);
        formFields.setColspan(fieldBeschreibung, 2);

        formFields.addFormRow(fieldZustaendigkeit);
        formFields.setColspan(fieldZustaendigkeit, 2);

        binder.forField(fieldID)
                .withNullRepresentation("")
                .withConverter(new StringToLongConverter(getTranslation(TranslationConstants.VALIDATION_ID_NAN)))
                .bind("id");
        binder.forField(fieldName)
                .asRequired("Der Name ist erforderlich.")
                .bind("name");
        binder.forField(fieldBeschreibung)
                .bind("beschreibung");
        binder.forField(fieldZustaendigkeit)
                .bind("zustaendigkeit");
        binder.forField(fieldRessortleitung)
                .bind("ressortleitung");

        return formFields;
    }
}
