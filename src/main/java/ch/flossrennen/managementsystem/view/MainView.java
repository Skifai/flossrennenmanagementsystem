package ch.flossrennen.managementsystem.view;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@PermitAll
@Route("")
public class MainView extends VerticalLayout {

    public MainView() {

        H1 titel = new H1("Flossrennen Management System");

        Paragraph subtitle = new Paragraph("Von Handarbeit zu Klickarbeit - smarte Planung für das Mammut Flossrennen Sitter-Thur");

        H2 modulTitel = new H2("Modulübersicht");

        HorizontalLayout modulLayout = new HorizontalLayout();

        Anchor helferModul = new Anchor("helfer", "Helferverwaltung");

        modulLayout.add(helferModul);

        add(titel, subtitle, modulTitel, modulLayout);
    }
}
