package ch.flossrennen.managementsystem.view;

import ch.flossrennen.managementsystem.util.ResourceConstants;
import ch.flossrennen.managementsystem.util.textprovider.TranslationConstants;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@PermitAll
@Route(value = ViewRoutes.HOME, layout = MainView.class)
public class HomeView extends VerticalLayout {

    public HomeView() {
        H2 h2 = new H2(getTranslation(TranslationConstants.HOME_WELCOME));
        h2.addClassName(ViewStyles.APP_TITEL);
        Image logo = new Image(ResourceConstants.LOGO, getTranslation(TranslationConstants.LOGO_DESC));
        setClassName(ViewStyles.MAIN_VIEW_CONTENT);
        setAlignItems(Alignment.CENTER);
        add(h2, logo);
    }
}