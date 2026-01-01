package ch.flossrennen.managementsystem.view;

import ch.flossrennen.managementsystem.util.textprovider.TranslationConstants;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@PermitAll
@Route(value = ViewRoutes.HOME, layout = MainView.class)
public class HomeView extends VerticalLayout {

    public HomeView() {
        H1 h1 = new H1(getTranslation(TranslationConstants.HOME_WELCOME));
        h1.addClassName(ViewStyles.APP_TITEL);
        setClassName(ViewStyles.MAIN_VIEW_CONTENT);
        add(h1);
    }
}
