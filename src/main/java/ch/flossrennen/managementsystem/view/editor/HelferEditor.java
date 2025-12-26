package ch.flossrennen.managementsystem.view.editor;

import ch.flossrennen.managementsystem.dataaccess.dto.HelferDTO;
import ch.flossrennen.managementsystem.service.HelferDTOService;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToLongConverter;
import lombok.Setter;
import org.jspecify.annotations.NonNull;

public class HelferEditor extends Composite<VerticalLayout> {

    public HelferDTO getHelferDTO() {
        try {
            return binder.writeRecord();
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }
    }

    public interface SaveListener {
        void onSave(HelferDTO helferDTO);
    }

    public interface CancelListener {
        void onCancel();
    }

    public interface DeleteListener {
        void onDelete(HelferDTO helferDTO);
    }

    private HelferDTO helferDTO;

    @Setter
    private SaveListener saveListener;
    @Setter
    private CancelListener cancelListener;
    @Setter
    private DeleteListener deleteListener;

    private final Binder<HelferDTO> binder = new BeanValidationBinder<>(HelferDTO.class);

    public void setHelferDTO(HelferDTO helferDTO) {
        this.helferDTO = helferDTO;
        binder.readRecord(helferDTO);
    }

    public HelferEditor() {

        VerticalLayout layout = getContent();

        FormLayout helferFormFields = createHelferFormFields();

        layout.add(helferFormFields);


        Button saveButton = new Button("Speichern");
        saveButton.addClickListener(e -> saveListener.onSave(helferDTO));

        Button cancelButton = new Button("Abbrechen");
        cancelButton.addClickListener(e -> cancelListener.onCancel());

        Button deleteButton = new Button("Löschen");
        deleteButton.addClickListener(e -> deleteListener.onDelete(helferDTO));

        HorizontalLayout buttonLayout = new HorizontalLayout(saveButton, cancelButton, deleteButton);

        layout.add(buttonLayout);
    }

    private @NonNull FormLayout createHelferFormFields() {
        FormLayout helferFormFields = new FormLayout();
        helferFormFields.setAutoResponsive(true);

        TextField fieldID = new TextField("ID");
        fieldID.setReadOnly(true);
        fieldID.setPrefixComponent(VaadinIcon.HASH.create());

        TextField fieldVorname = new TextField("Vorname");
        fieldVorname.setRequired(true);
        fieldVorname.setMaxLength(100);
        fieldVorname.setMinLength(2);
        fieldVorname.setPrefixComponent(VaadinIcon.USER.create());

        TextField fieldNachname = new TextField("Nachname");
        fieldNachname.setRequired(true);
        fieldNachname.setMaxLength(100);
        fieldNachname.setMinLength(2);
        fieldNachname.setPrefixComponent(VaadinIcon.USER.create());

        TextField fieldEmail = new TextField("Email");
        fieldEmail.setRequired(true);
        fieldEmail.setMaxLength(254);
        fieldEmail.setMinLength(3);
        fieldEmail.setPrefixComponent(VaadinIcon.ENVELOPE.create());

        TextField fieldTelefon = new TextField("Telefonnummer");
        fieldTelefon.setRequired(true);
        fieldTelefon.setMaxLength(15);
        fieldTelefon.setMinLength(10);
        fieldTelefon.setPrefixComponent(VaadinIcon.PHONE.create());

        helferFormFields.addFormRow(fieldID);
        helferFormFields.addFormRow(fieldVorname, fieldNachname);
        helferFormFields.addFormRow(fieldEmail, fieldTelefon);

        binder.forField(fieldID)
                .withConverter(new StringToLongConverter("ID muss eine Zahl sein."))
                .withNullRepresentation(0L)
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

        return helferFormFields;
    }
}
