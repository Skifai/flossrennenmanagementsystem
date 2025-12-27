package ch.flossrennen.managementsystem.view.editor;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import lombok.Setter;
import org.jspecify.annotations.NonNull;

public abstract class AbstractEditorView<DTO> extends Composite<VerticalLayout> {

    protected final Binder<DTO> binder;
    protected DTO dto;
    @Setter
    protected SaveListener<DTO> saveListener;
    @Setter
    protected CancelListener cancelListener;
    @Setter
    protected DeleteListener<DTO> deleteListener;

    public AbstractEditorView(Class<DTO> dtoClass) {
        this.binder = new BeanValidationBinder<>(dtoClass);

        VerticalLayout layout = getContent();

        FormLayout formFields = createFormFields();
        layout.add(formFields);

        HorizontalLayout buttonLayout = createButtonLayout();
        layout.add(buttonLayout);
    }

    public DTO getDTO() {
        try {
            return binder.writeRecord();
        } catch (ValidationException e) {
            return null;
        }
    }

    public void setDTO(DTO dto) {
        this.dto = dto;
        if (dto != null) {
            binder.readRecord(dto);
        }
    }

    private @NonNull HorizontalLayout createButtonLayout() {
        Button saveButton = new Button("Speichern");
        saveButton.addClickListener(e -> {
            if (binder.validate().isOk()) {
                DTO updatedDto = getDTO();
                if (saveListener != null) {
                    saveListener.onSave(updatedDto);
                }
            }
        });

        Button cancelButton = new Button("Abbrechen");
        cancelButton.addClickListener(e -> {
            if (cancelListener != null) {
                cancelListener.onCancel();
            }
        });

        Button deleteButton = new Button("Löschen");
        deleteButton.addClickListener(e -> {
            if (deleteListener != null) {
                deleteListener.onDelete(dto);
            }
        });

        return new HorizontalLayout(saveButton, cancelButton, deleteButton);
    }

    protected abstract @NonNull FormLayout createFormFields();

    public interface SaveListener<DTO> {
        void onSave(DTO dto);
    }

    public interface CancelListener {
        void onCancel();
    }

    public interface DeleteListener<DTO> {
        void onDelete(DTO dto);
    }
}
