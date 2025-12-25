package ch.flossrennen.managementsystem.view;

import ch.flossrennen.managementsystem.util.TranslationConstants;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.jspecify.annotations.NonNull;

//@PermitAll
@AnonymousAllowed
@Layout
public class MainView extends AppLayout {

    public MainView() {

        HorizontalLayout navbarLayout = createNavbarLayout();

        SideNav sideNav = createSideNav();

        Scroller scroller = new Scroller(sideNav);

        addToDrawer(scroller);
        addToNavbar(navbarLayout);

    }

    private static @NonNull SideNav createSideNav() {
        SideNav sideNav = new SideNav();
        sideNav.addClassName("MainViewSideNav");
        SideNavItem home = new SideNavItem("Startseite", "");
        sideNav.addItem(home);
        SideNavItem helferModul = new SideNavItem("Helferverwaltung", "helfer");
        sideNav.addItem(helferModul);
        return sideNav;
    }

    private @NonNull HorizontalLayout createNavbarLayout() {
        HorizontalLayout navbarLayout = new HorizontalLayout();
        navbarLayout.addClassName("AppNavbarLayout");
        navbarLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        DrawerToggle toggle = new DrawerToggle();
        toggle.addClassName("AppDrawerToggle");
        navbarLayout.add(toggle);

        H1 titel = new H1(getTranslation(TranslationConstants.TITEL));
        titel.addClassName("AppTitel");
        navbarLayout.add(titel);
        return navbarLayout;
    }
}
