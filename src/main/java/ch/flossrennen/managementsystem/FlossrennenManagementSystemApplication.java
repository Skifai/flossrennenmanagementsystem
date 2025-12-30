package ch.flossrennen.managementsystem;

import ch.flossrennen.managementsystem.view.ViewStyles;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Theme(ViewStyles.THEME_NAME)
public class FlossrennenManagementSystemApplication implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(FlossrennenManagementSystemApplication.class, args);
    }
}
