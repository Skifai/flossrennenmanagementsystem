package ch.flossrennen.managementsystem;

import ch.flossrennen.managementsystem.config.NativeRuntimeHints;
import ch.flossrennen.managementsystem.view.ViewStyles;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

@SpringBootApplication
@Theme(ViewStyles.THEME_NAME)
@ImportRuntimeHints(NativeRuntimeHints.class)
public class FlossrennenManagementSystemApplication implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(FlossrennenManagementSystemApplication.class, args);
    }
}
