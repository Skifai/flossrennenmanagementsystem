package ch.flossrennen.managementsystem.view.editor;

import ch.flossrennen.managementsystem.dataaccess.dto.BenutzerDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.BenutzerDTOProperties;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.BenutzerRolle;
import ch.flossrennen.managementsystem.util.TranslationConstants;
import ch.flossrennen.managementsystem.view.ViewStyles;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.converter.StringToLongConverter;
import org.jspecify.annotations.NonNull;

public class BenutzerEditor extends AbstractEditorView<BenutzerDTO> {

    public BenutzerEditor() {
        super(BenutzerDTO.class);
    }

    @Override
    protected @NonNull FormLayout createFormFields() {
        FormLayout formFields = new FormLayout();
        formFields.setAutoResponsive(true);

        TextField fieldID = new TextField(getTranslation(TranslationConstants.BENUTZER_ID));
        fieldID.addClassName(ViewStyles.EDITOR_FIELD);
        fieldID.setReadOnly(true);
        fieldID.setWidthFull();
        fieldID.setPrefixComponent(VaadinIcon.HASH.create());

        TextField fieldVorname = new TextField(getTranslation(TranslationConstants.BENUTZER_VORNAME));
        fieldVorname.addClassName(ViewStyles.EDITOR_FIELD);
        fieldVorname.setRequired(true);
        fieldVorname.setMaxLength(100);
        fieldVorname.setWidthFull();
        fieldVorname.setPrefixComponent(VaadinIcon.USER.create());

        TextField fieldNachname = new TextField(getTranslation(TranslationConstants.BENUTZER_NACHNAME));
        fieldNachname.addClassName(ViewStyles.EDITOR_FIELD);
        fieldNachname.setRequired(true);
        fieldNachname.setMaxLength(100);
        fieldNachname.setWidthFull();
        fieldNachname.setPrefixComponent(VaadinIcon.USER.create());

        TextField fieldEmail = new TextField(getTranslation(TranslationConstants.BENUTZER_EMAIL));
        fieldEmail.addClassName(ViewStyles.EDITOR_FIELD);
        fieldEmail.setRequired(true);
        fieldEmail.setMaxLength(254);
        fieldEmail.setWidthFull();
        fieldEmail.setPrefixComponent(VaadinIcon.ENVELOPE.create());

        TextField fieldTelefon = new TextField(getTranslation(TranslationConstants.BENUTZER_TELEFONNUMMER));
        fieldTelefon.addClassName(ViewStyles.EDITOR_FIELD);
        fieldTelefon.setMaxLength(15);
        fieldTelefon.setWidthFull();
        fieldTelefon.setPrefixComponent(VaadinIcon.PHONE.create());

        PasswordField fieldPassword = new PasswordField(getTranslation(TranslationConstants.BENUTZER_PASSWORT));
        fieldPassword.addClassName(ViewStyles.EDITOR_FIELD);
        fieldPassword.setMaxLength(100);
        fieldPassword.setWidthFull();
        fieldPassword.setPrefixComponent(VaadinIcon.KEY.create());

        ComboBox<BenutzerRolle> fieldRolle = new ComboBox<>(getTranslation(TranslationConstants.BENUTZER_ROLLE));
        fieldRolle.addClassName(ViewStyles.EDITOR_FIELD);
        fieldRolle.setItems(BenutzerRolle.values());
        fieldRolle.setRequired(true);
        fieldRolle.setWidthFull();
        fieldRolle.setPrefixComponent(VaadinIcon.USER_CHECK.create());

        formFields.addFormRow(fieldID);
        formFields.setColspan(fieldID, 1);

        formFields.addFormRow(fieldVorname, fieldNachname);

        formFields.addFormRow(fieldEmail);
        formFields.setColspan(fieldEmail, 2);

        formFields.addFormRow(fieldPassword);
        formFields.setColspan(fieldPassword, 2);

        formFields.addFormRow(fieldTelefon, fieldRolle);

        binder.forField(fieldID)
                .withNullRepresentation("")
                .withConverter(new StringToLongConverter(getTranslation(TranslationConstants.VALIDATION_ID_NAN)))
                .bind(BenutzerDTOProperties.ID.getSchemaKey());
        binder.bind(fieldVorname, BenutzerDTOProperties.VORNAME.getSchemaKey());
        binder.bind(fieldNachname, BenutzerDTOProperties.NACHNAME.getSchemaKey());
        binder.bind(fieldEmail, BenutzerDTOProperties.EMAIL.getSchemaKey());
        binder.bind(fieldTelefon, BenutzerDTOProperties.TELEFONNUMMER.getSchemaKey());
        binder.bind(fieldPassword, BenutzerDTOProperties.PASSWORD.getSchemaKey());
        binder.bind(fieldRolle, BenutzerDTOProperties.ROLLE.getSchemaKey());

        return formFields;
    }
}
