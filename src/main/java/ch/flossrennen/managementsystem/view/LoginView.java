package ch.flossrennen.managementsystem.view;

import ch.flossrennen.managementsystem.util.ResourceConstants;
import ch.flossrennen.managementsystem.util.textprovider.TranslationConstants;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.jspecify.annotations.NonNull;

@AnonymousAllowed
@Route(ViewRoutes.LOGIN)
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private final LoginForm loginForm = new LoginForm();

    public LoginView() {

        setSizeFull();

        HorizontalLayout headerBar = createHeaderBar();
        Div logoDiv = createLogoDiv();
        Div titelDiv = createTitleDiv();
        headerBar.add(logoDiv);
        headerBar.add(titelDiv);
        headerBar.setFlexGrow(1, titelDiv);

        VerticalLayout mainContent = createMainContent();
        loginForm.setI18n(getLoginTranslation());
        loginForm.setForgotPasswordButtonVisible(false);

        loginForm.setAction("login");
        mainContent.add(loginForm);

        add(headerBar, mainContent);
    }

    private LoginI18n getLoginTranslation() {
        LoginI18n loginTranslation = LoginI18n.createDefault();

        LoginI18n.Form form = loginTranslation.getForm();
        form.setTitle(getTranslation(TranslationConstants.LOGIN_TITLE));
        form.setUsername(getTranslation(TranslationConstants.USERNAME));
        form.setPassword(getTranslation(TranslationConstants.PASSWORD));
        form.setSubmit(getTranslation(TranslationConstants.LOGIN_BUTTON));

        LoginI18n.ErrorMessage errorMessage = loginTranslation.getErrorMessage();
        errorMessage.setTitle(getTranslation(TranslationConstants.LOGIN_ERROR_TITLE));
        errorMessage.setMessage(getTranslation(TranslationConstants.LOGIN_ERROR_MESSAGE));

        return loginTranslation;
    }

    private @NonNull VerticalLayout createMainContent() {
        VerticalLayout mainContent = new VerticalLayout();
        setFlexGrow(1, mainContent);
        mainContent.setAlignItems(Alignment.CENTER);
        mainContent.setJustifyContentMode(JustifyContentMode.CENTER);
        return mainContent;
    }

    private @NonNull Div createTitleDiv() {
        Div titelDiv = new Div();
        titelDiv.addClassNames(ViewStyles.APP_TITEL,
                LumoUtility.Display.FLEX,
                LumoUtility.FlexDirection.COLUMN,
                LumoUtility.JustifyContent.CENTER);
        H1 titel = new H1(getTranslation(TranslationConstants.TITEL));
        titelDiv.add(titel);
        H2 subtitle = new H2(getTranslation(TranslationConstants.SUBTITEL));
        titelDiv.add(subtitle);
        return titelDiv;
    }

    private @NonNull Div createLogoDiv() {
        Div logoDiv = new Div();
        logoDiv.addClassNames(ViewStyles.APP_TITEL, LumoUtility.Display.FLEX, LumoUtility.AlignItems.CENTER);
        Image logo = new Image(ResourceConstants.LOGO, getTranslation(TranslationConstants.LOGO_DESC));
        logo.getStyle().setHeight("100%");
        logoDiv.add(logo);
        return logoDiv;
    }

    private @NonNull HorizontalLayout createHeaderBar() {
        HorizontalLayout headerBar = new HorizontalLayout();
        headerBar.setWidthFull();
        headerBar.setHeight("12%");
        headerBar.setPadding(true);
        headerBar.setAlignItems(Alignment.STRETCH);
        return headerBar;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (event.getLocation().getQueryParameters().getParameters().containsKey("error")) {
            loginForm.setError(true);
        }
    }
}
