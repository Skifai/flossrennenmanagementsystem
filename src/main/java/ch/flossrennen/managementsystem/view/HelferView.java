package ch.flossrennen.managementsystem.view;

import ch.flossrennen.managementsystem.model.Helfer;
import ch.flossrennen.managementsystem.repository.HelferRepository;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@PermitAll
@Route("")
public class HelferView extends VerticalLayout {
    private final HelferRepository helferRepository;

    private final TextField filter;
    private final Grid<Helfer> grid;


    public interface SaveListener {
        void onSave(Helfer helfer);
    }

    public interface DeleteListener {
        void onDelete(Helfer helfer);
    }

    public interface CloseListener {
        void onClose();
    }

    private Helfer helfer;

    private SaveListener saveListener;
    private DeleteListener deleteListener;
    private CloseListener closeListener;

    private final Binder<Helfer> binder = new BeanValidationBinder<>(Helfer.class);

    public void setHelfer(Helfer helfer) {
        this.helfer = helfer;
        binder.readBean(helfer);
    }

    public HelferView(HelferRepository helferRepository) {
        this.helferRepository = helferRepository;

        Button neu = new Button("Neu", VaadinIcon.PLUS.create());
        filter = new TextField("Filter");
        grid = new Grid<>(Helfer.class);

        TextField vorname = new TextField("Vorname");
        TextField nachname = new TextField("Nachname");
        TextField email = new TextField("Email");
        TextField telefonnummer = new TextField("Telefonnummer");

        Button speichern = new Button("Speichern", VaadinIcon.CHECK.create());
        Button abbrechen = new Button("Abbrechen");
        Button loeschen = new Button("Löschen", VaadinIcon.TRASH.create());

        binder.forField(vorname).bind("vorname");
        binder.forField(nachname).bind("nachname");
        binder.forField(email).bind("email");
        binder.forField(telefonnummer).bind("telefonnummer");

        speichern.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        speichern.addClickListener(e -> saveListener.onSave(helfer));
        speichern.addClickShortcut(Key.ENTER);

        loeschen.addThemeVariants(ButtonVariant.LUMO_ERROR);
        loeschen.addClickListener(e -> deleteListener.onDelete(helfer));

        abbrechen.addClickListener(e -> closeListener.onClose());

//        getContent().add(vorname, nachname, email, telefonnummer, speichern, loeschen, abbrechen);
    }
}
