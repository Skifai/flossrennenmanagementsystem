package ch.flossrennen.managementsystem.view.editor;

import ch.flossrennen.managementsystem.dataaccess.dto.HelferDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.HelferDTOProperties;
import ch.flossrennen.managementsystem.dataaccess.dto.RessortDTO;
import ch.flossrennen.managementsystem.util.textprovider.TranslationConstants;
import ch.flossrennen.managementsystem.view.ViewStyles;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.converter.StringToLongConverter;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class HelferEditor extends AbstractEditorView<HelferDTO> {

    private TextField fieldID;
    private TextField fieldVorname;
    private TextField fieldNachname;
    private TextField fieldEmail;
    private TextField fieldTelefon;
    private ComboBox<RessortDTO> fieldRessort;

    public HelferEditor() {
        super(HelferDTO.class);
    }

    public void setAvailableRessorts(List<RessortDTO> ressorts) {
        if (fieldRessort != null) {
            fieldRessort.setItems(ressorts);
            fieldRessort.setItemLabelGenerator(RessortDTO::name);
        }
    }

    @Override
    @NonNull
    protected FormLayout createFormFields() {
        FormLayout helferFormFields = new FormLayout();
        helferFormFields.setAutoResponsive(true);

        fieldRessort = new ComboBox<>(getTranslation(TranslationConstants.NAV_RESSORT));
        fieldRessort.addClassName(ViewStyles.EDITOR_FIELD);

        fieldID = new TextField(getTranslation(TranslationConstants.HELFER_ID));
        fieldID.addClassName(ViewStyles.EDITOR_FIELD);
        fieldID.setReadOnly(true);
        fieldID.setWidthFull();
        fieldID.setPrefixComponent(VaadinIcon.HASH.create());

        fieldVorname = new TextField(getTranslation(TranslationConstants.HELFER_VORNAME));
        fieldVorname.addClassName(ViewStyles.EDITOR_FIELD);
        fieldVorname.setRequired(true);
        fieldVorname.setMaxLength(100);
        fieldVorname.setWidthFull();
        fieldVorname.setMinLength(2);
        fieldVorname.setPrefixComponent(VaadinIcon.USER.create());

        fieldNachname = new TextField(getTranslation(TranslationConstants.HELFER_NACHNAME));
        fieldNachname.addClassName(ViewStyles.EDITOR_FIELD);
        fieldNachname.setRequired(true);
        fieldNachname.setMaxLength(100);
        fieldNachname.setWidthFull();
        fieldNachname.setMinLength(2);
        fieldNachname.setPrefixComponent(VaadinIcon.USER.create());

        fieldEmail = new TextField(getTranslation(TranslationConstants.HELFER_EMAIL));
        fieldEmail.addClassName(ViewStyles.EDITOR_FIELD);
        fieldEmail.setRequired(true);
        fieldEmail.setMaxLength(254);
        fieldEmail.setWidthFull();
        fieldEmail.setMinLength(3);
        fieldEmail.setPrefixComponent(VaadinIcon.ENVELOPE.create());

        fieldTelefon = new TextField(getTranslation(TranslationConstants.HELFER_TELEFONNUMMER));
        fieldTelefon.addClassName(ViewStyles.EDITOR_FIELD);
        fieldTelefon.setRequired(true);
        fieldTelefon.setMaxLength(15);
        fieldTelefon.setWidthFull();
        fieldTelefon.setMinLength(10);
        fieldTelefon.setPrefixComponent(VaadinIcon.PHONE.create());

        fieldRessort.setRequired(true);
        fieldRessort.setPrefixComponent(VaadinIcon.GROUP.create());

        helferFormFields.addFormRow(fieldID);

        helferFormFields.addFormRow(fieldVorname, fieldNachname);

        helferFormFields.addFormRow(fieldEmail);
        helferFormFields.setColspan(fieldEmail, 2);

        helferFormFields.addFormRow(fieldTelefon, fieldRessort);

        binder.forField(fieldID)
                .withNullRepresentation("")
                .withConverter(new StringToLongConverter(getTranslation(TranslationConstants.VALIDATION_ID_NAN)))
                .bind(HelferDTOProperties.ID.getSchemaKey());
        binder.bind(fieldVorname, HelferDTOProperties.VORNAME.getSchemaKey());
        binder.bind(fieldNachname, HelferDTOProperties.NACHNAME.getSchemaKey());
        binder.bind(fieldEmail, HelferDTOProperties.EMAIL.getSchemaKey());
        binder.bind(fieldTelefon, HelferDTOProperties.TELEFONNUMMER.getSchemaKey());
        binder.bind(fieldRessort, HelferDTOProperties.RESSORT.getSchemaKey());

        return helferFormFields;
    }
}
