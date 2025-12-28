package ch.flossrennen.managementsystem.view.editor;

import ch.flossrennen.managementsystem.dataaccess.dto.BenutzerDTO;
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

        TextField fieldID = new TextField("ID");
        fieldID.setReadOnly(true);
        fieldID.setPrefixComponent(VaadinIcon.HASH.create());

        TextField fieldVorname = new TextField("Vorname");
        fieldVorname.setRequired(true);
        fieldVorname.setPrefixComponent(VaadinIcon.USER.create());

        TextField fieldNachname = new TextField("Nachname");
        fieldNachname.setRequired(true);
        fieldNachname.setPrefixComponent(VaadinIcon.USER.create());

        TextField fieldEmail = new TextField("Email");
        fieldEmail.setRequired(true);
        fieldEmail.setPrefixComponent(VaadinIcon.ENVELOPE.create());

        TextField fieldTelefon = new TextField("Telefonnummer");
        fieldTelefon.setPrefixComponent(VaadinIcon.PHONE.create());

        PasswordField fieldPassword = new PasswordField("Passwort");
        fieldPassword.setPrefixComponent(VaadinIcon.KEY.create());

        TextField fieldRolle = new TextField("Rolle");
        fieldRolle.setRequired(true);
        fieldRolle.setPrefixComponent(VaadinIcon.USER_CHECK.create());

        formFields.addFormRow(fieldID);
        formFields.addFormRow(fieldVorname, fieldNachname);
        formFields.addFormRow(fieldEmail, fieldTelefon);
        formFields.addFormRow(fieldPassword);
        formFields.addFormRow(fieldRolle);

        binder.forField(fieldID)
                .withNullRepresentation("")
                .withConverter(new StringToLongConverter("ID muss eine Zahl sein."))
                .bind("id");
        binder.forField(fieldVorname)
                .asRequired("Der Vorname ist erforderlich.")
                .bind("vorname");
        binder.forField(fieldNachname)
                .asRequired("Der Nachname ist erforderlich.")
                .bind("nachname");
        binder.forField(fieldEmail)
                .asRequired("Die Email ist erforderlich.")
                .bind("email");
        binder.forField(fieldTelefon)
                .bind("telefonnummer");
        binder.forField(fieldPassword)
                .bind("passwordhash");
        binder.forField(fieldRolle)
                .asRequired("Die Rolle ist erforderlich.")
                .bind("rolle");

        return formFields;
    }
}
