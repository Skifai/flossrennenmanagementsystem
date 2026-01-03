package ch.flossrennen.managementsystem.view.editor;

import ch.flossrennen.managementsystem.dataaccess.dto.EinsatzDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.EinsatzDTOProperties;
import ch.flossrennen.managementsystem.dataaccess.dto.RessortDTO;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.EinsatzStatus;
import ch.flossrennen.managementsystem.util.textprovider.TranslationConstants;
import ch.flossrennen.managementsystem.view.ViewStyles;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.converter.StringToLongConverter;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class EinsatzEditor extends AbstractEditorView<EinsatzDTO> {

    private ComboBox<RessortDTO> fieldRessort;

    public EinsatzEditor() {
        super(EinsatzDTO.class);
    }

    public void setAvailableRessorts(List<RessortDTO> ressorts) {
        if (fieldRessort != null) {
            fieldRessort.setItems(ressorts);
            fieldRessort.setItemLabelGenerator(RessortDTO::name);
        }
    }

    @Override
    protected @NonNull FormLayout createFormFields() {
        FormLayout formFields = new FormLayout();
        formFields.setAutoResponsive(true);

        TextField fieldID = new TextField(getTranslation(TranslationConstants.EINSATZ_ID));
        fieldID.addClassName(ViewStyles.EDITOR_FIELD);
        fieldID.setReadOnly(true);
        fieldID.setWidthFull();
        fieldID.setPrefixComponent(VaadinIcon.HASH.create());

        TextField fieldName = new TextField(getTranslation(TranslationConstants.EINSATZ_NAME));
        fieldName.addClassName(ViewStyles.EDITOR_FIELD);
        fieldName.setRequired(true);
        fieldName.setWidthFull();
        fieldName.setMaxLength(100);

        TextArea fieldBeschreibung = new TextArea(getTranslation(TranslationConstants.EINSATZ_BESCHREIBUNG));
        fieldBeschreibung.addClassName(ViewStyles.EDITOR_FIELD);
        fieldBeschreibung.setRequired(true);
        fieldBeschreibung.setWidthFull();
        fieldBeschreibung.setMaxLength(300);

        DateTimePicker fieldStartzeit = new DateTimePicker(getTranslation(TranslationConstants.EINSATZ_STARTZEIT));
        fieldStartzeit.addClassName(ViewStyles.EDITOR_FIELD);
        fieldStartzeit.setRequiredIndicatorVisible(true);

        DateTimePicker fieldEndzeit = new DateTimePicker(getTranslation(TranslationConstants.EINSATZ_ENDZEIT));
        fieldEndzeit.addClassName(ViewStyles.EDITOR_FIELD);
        fieldEndzeit.setRequiredIndicatorVisible(true);

        TextField fieldOrt = new TextField(getTranslation(TranslationConstants.EINSATZ_ORT));
        fieldOrt.addClassName(ViewStyles.EDITOR_FIELD);
        fieldOrt.setRequired(true);
        fieldOrt.setWidthFull();
        fieldOrt.setMaxLength(150);

        TextField fieldEinsatzmittel = new TextField(getTranslation(TranslationConstants.EINSATZ_MITTEL));
        fieldEinsatzmittel.addClassName(ViewStyles.EDITOR_FIELD);
        fieldEinsatzmittel.setWidthFull();
        fieldEinsatzmittel.setMaxLength(200);

        IntegerField fieldBenoetigteHelfer = new IntegerField(getTranslation(TranslationConstants.EINSATZ_BENOETIGTE_HELFER));
        fieldBenoetigteHelfer.addClassName(ViewStyles.EDITOR_FIELD);
        fieldBenoetigteHelfer.setRequiredIndicatorVisible(true);
        fieldBenoetigteHelfer.setMin(1);

        ComboBox<EinsatzStatus> fieldStatus = new ComboBox<>(getTranslation(TranslationConstants.EINSATZ_STATUS));
        fieldStatus.addClassName(ViewStyles.EDITOR_FIELD);
        fieldStatus.setItems(EinsatzStatus.values());
        fieldStatus.setItemLabelGenerator(status -> getTranslation(status.getTranslationKey()));
        fieldStatus.setRequired(true);

        fieldRessort = new ComboBox<>(getTranslation(TranslationConstants.EINSATZ_RESSORT));
        fieldRessort.addClassName(ViewStyles.EDITOR_FIELD);
        fieldRessort.setRequired(true);

        formFields.addFormRow(fieldID);
        formFields.addFormRow(fieldName, fieldStatus);
        formFields.addFormRow(fieldBeschreibung);
        formFields.setColspan(fieldBeschreibung, 2);
        formFields.addFormRow(fieldStartzeit);
        formFields.setColspan(fieldStartzeit, 2);
        formFields.addFormRow(fieldEndzeit);
        formFields.setColspan(fieldEndzeit, 2);
        formFields.addFormRow(fieldOrt, fieldEinsatzmittel);
        formFields.addFormRow(fieldBenoetigteHelfer, fieldRessort);

        binder.forField(fieldID)
                .withNullRepresentation("")
                .withConverter(new StringToLongConverter(getTranslation(TranslationConstants.VALIDATION_ID_NAN)))
                .bind(EinsatzDTOProperties.ID.getSchemaKey());
        binder.bind(fieldName, EinsatzDTOProperties.NAME.getSchemaKey());
        binder.bind(fieldBeschreibung, EinsatzDTOProperties.BESCHREIBUNG.getSchemaKey());
        binder.bind(fieldStartzeit, EinsatzDTOProperties.STARTZEIT.getSchemaKey());
        binder.bind(fieldEndzeit, EinsatzDTOProperties.ENDZEIT.getSchemaKey());
        binder.bind(fieldOrt, EinsatzDTOProperties.ORT.getSchemaKey());
        binder.bind(fieldEinsatzmittel, EinsatzDTOProperties.EINSATZMITTEL.getSchemaKey());
        binder.bind(fieldBenoetigteHelfer, EinsatzDTOProperties.BENOETIGTE_HELFER.getSchemaKey());
        binder.bind(fieldStatus, EinsatzDTOProperties.STATUS.getSchemaKey());
        binder.bind(fieldRessort, EinsatzDTOProperties.RESSORT.getSchemaKey());

        return formFields;
    }
}