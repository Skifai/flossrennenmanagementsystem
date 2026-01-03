package ch.flossrennen.managementsystem.view;

import ch.flossrennen.managementsystem.util.textprovider.TranslationConstants;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@PermitAll
@Route(value = ViewRoutes.HOME, layout = MainView.class)
public class HomeView extends VerticalLayout {

    public HomeView() {
        H2 h2 = new H2(getTranslation(TranslationConstants.HOME_WELCOME));
        h2.addClassName(ViewStyles.APP_TITEL);
        setClassName(ViewStyles.MAIN_VIEW_CONTENT);
        add(h2);
    }
}