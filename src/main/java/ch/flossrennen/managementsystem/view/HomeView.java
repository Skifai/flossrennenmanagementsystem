package ch.flossrennen.managementsystem.view;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed
@Route(value = "", layout = MainView.class)
public class HomeView extends VerticalLayout {

    public HomeView() {
     H1 h1 = new H1("Hallo");
     addClassName("AppTitel");
     add(h1);
    }
}
