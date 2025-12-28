package ch.flossrennen.managementsystem.view.editor;

import ch.flossrennen.managementsystem.dataaccess.dto.HelferDTO;
import ch.flossrennen.managementsystem.dataaccess.dto.RessortDTO;
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

        fieldRessort = new ComboBox<>("Ressort");

        fieldID = new TextField("ID");
        fieldID.setReadOnly(true);
        fieldID.setPrefixComponent(VaadinIcon.HASH.create());

        fieldVorname = new TextField("Vorname");
        fieldVorname.setRequired(true);
        fieldVorname.setMaxLength(100);
        fieldVorname.setMinLength(2);
        fieldVorname.setPrefixComponent(VaadinIcon.USER.create());

        fieldNachname = new TextField("Nachname");
        fieldNachname.setRequired(true);
        fieldNachname.setMaxLength(100);
        fieldNachname.setMinLength(2);
        fieldNachname.setPrefixComponent(VaadinIcon.USER.create());

        fieldEmail = new TextField("Email");
        fieldEmail.setRequired(true);
        fieldEmail.setMaxLength(254);
        fieldEmail.setMinLength(3);
        fieldEmail.setPrefixComponent(VaadinIcon.ENVELOPE.create());

        fieldTelefon = new TextField("Telefonnummer");
        fieldTelefon.setRequired(true);
        fieldTelefon.setMaxLength(15);
        fieldTelefon.setMinLength(10);
        fieldTelefon.setPrefixComponent(VaadinIcon.PHONE.create());

        fieldRessort.setRequired(true);
        fieldRessort.setPrefixComponent(VaadinIcon.GROUP.create());

        helferFormFields.addFormRow(fieldID);
        helferFormFields.addFormRow(fieldVorname, fieldNachname);
        helferFormFields.addFormRow(fieldEmail, fieldTelefon);
        helferFormFields.addFormRow(fieldRessort);

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
                .asRequired("Die Telefonnummer ist erforderlich.")
                .bind("telefonnummer");
        binder.forField(fieldRessort)
                .asRequired("Das Ressort ist erforderlich.")
                .bind("ressort");

        return helferFormFields;
    }
}
