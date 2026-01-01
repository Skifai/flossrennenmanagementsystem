package ch.flossrennen.managementsystem.util.textprovider;

import ch.flossrennen.managementsystem.util.ResourceConstants;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.i18n.I18NProvider;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
public class TextProvider implements I18NProvider {

    @Override
    public List<Locale> getProvidedLocales() {
        return List.of(Locale.GERMAN);
    }

    public String getTranslation(String key, Object... params) {
        return getTranslation(key, getCurrentLocale(), params);
    }

    @Override
    public String getTranslation(String key, Locale locale, Object... params) {
        ResourceBundle bundle = ResourceBundle.getBundle(ResourceConstants.TRANSLATION_PATH, locale);
        String value = bundle.getString(key);
        if (params.length > 0) {
            return String.format(value, params);
        }
        return value;
    }

    private Locale getCurrentLocale() {
        return Optional.ofNullable(UI.getCurrent())
                .map(UI::getLocale)
                .or(() -> Optional.ofNullable(VaadinSession.getCurrent()).map(VaadinSession::getLocale))
                .orElse(Locale.GERMAN);
    }
}
