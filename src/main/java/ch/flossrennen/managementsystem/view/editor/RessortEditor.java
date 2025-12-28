package ch.flossrennen.managementsystem.view.editor;

import ch.flossrennen.managementsystem.dataaccess.dto.RessortDTO;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.converter.StringToLongConverter;
import org.jspecify.annotations.NonNull;

public class RessortEditor extends AbstractEditorView<RessortDTO> {

    public RessortEditor() {
        super(RessortDTO.class);
    }

    @Override
    protected @NonNull FormLayout createFormFields() {
        FormLayout formFields = new FormLayout();
        formFields.setAutoResponsive(true);

        TextField fieldID = new TextField("ID");
        fieldID.setReadOnly(true);
        fieldID.setPrefixComponent(VaadinIcon.HASH.create());

        TextField fieldName = new TextField("Name");
        fieldName.setRequired(true);
        fieldName.setMaxLength(100);

        TextField fieldBeschreibung = new TextField("Beschreibung");
        fieldBeschreibung.setMaxLength(255);

        TextField fieldZustaendigkeit = new TextField("Zuständigkeit");
        fieldZustaendigkeit.setMaxLength(255);

        TextField fieldRessortleitung = new TextField("Ressortleitung (ID)");

        formFields.addFormRow(fieldID);
        formFields.addFormRow(fieldName);
        formFields.addFormRow(fieldBeschreibung);
        formFields.addFormRow(fieldZustaendigkeit);
        formFields.addFormRow(fieldRessortleitung);

        binder.forField(fieldID)
                .withNullRepresentation("")
                .withConverter(new StringToLongConverter("ID muss eine Zahl sein."))
                .bind("id");
        binder.forField(fieldName)
                .asRequired("Der Name ist erforderlich.")
                .bind("name");
        binder.forField(fieldBeschreibung)
                .bind("beschreibung");
        binder.forField(fieldZustaendigkeit)
                .bind("zustaendigkeit");
        binder.forField(fieldRessortleitung)
                .withNullRepresentation("")
                .withConverter(new StringToLongConverter("Ressortleitung ID muss eine Zahl sein."))
                .bind("ressortleitung");

        return formFields;
    }
}
