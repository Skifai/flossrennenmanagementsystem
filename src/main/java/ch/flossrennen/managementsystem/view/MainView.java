package ch.flossrennen.managementsystem.view;

import ch.flossrennen.managementsystem.util.textprovider.TranslationConstants;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import jakarta.annotation.security.PermitAll;
import org.jspecify.annotations.NonNull;

@PermitAll
public class MainView extends AppLayout {

    public MainView() {

        HorizontalLayout navbarLayout = createNavbarLayout();

        SideNav sideNav = createSideNav();

        Scroller scroller = new Scroller(sideNav);

        addToDrawer(scroller);
        addToNavbar(navbarLayout);

    }

    private @NonNull SideNav createSideNav() {
        SideNav sideNav = new SideNav();
        sideNav.addClassName(ViewStyles.MAIN_VIEW_SIDE_NAV);
        SideNavItem home = new SideNavItem(getTranslation(TranslationConstants.NAV_HOME), ViewRoutes.HOME);
        sideNav.addItem(home);
        SideNavItem helferModul = new SideNavItem(getTranslation(TranslationConstants.NAV_HELFER), ViewRoutes.HELFER);
        sideNav.addItem(helferModul);
        SideNavItem ressortModul = new SideNavItem(getTranslation(TranslationConstants.NAV_RESSORT), ViewRoutes.RESSORT);
        sideNav.addItem(ressortModul);
        SideNavItem benutzerModul = new SideNavItem(getTranslation(TranslationConstants.NAV_BENUTZER), ViewRoutes.BENUTZER);
        sideNav.addItem(benutzerModul);
        SideNavItem einsatzModul = new SideNavItem(getTranslation(TranslationConstants.NAV_EINSATZ), ViewRoutes.EINSATZ);
        sideNav.addItem(einsatzModul);
        SideNavItem systemprotokollModul = new SideNavItem(getTranslation(TranslationConstants.NAV_SYSTEMPROTOKOLL), ViewRoutes.SYSTEMPROTOKOLL);
        sideNav.addItem(systemprotokollModul);
        return sideNav;
    }

    private @NonNull HorizontalLayout createNavbarLayout() {
        HorizontalLayout navbarLayout = new HorizontalLayout();
        navbarLayout.addClassName(ViewStyles.APP_NAVBAR_LAYOUT);
        navbarLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        DrawerToggle toggle = new DrawerToggle();
        toggle.addClassName(ViewStyles.APP_DRAWER_TOGGLE);
        navbarLayout.add(toggle);

        H1 titel = new H1(getTranslation(TranslationConstants.TITEL));
        titel.addClassName(ViewStyles.APP_TITEL);
        navbarLayout.add(titel);
        return navbarLayout;
    }
}