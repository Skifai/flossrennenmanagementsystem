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

/**
 * Provider für Internationalisierung (i18n) und Übersetzungen.
 * Implementiert das I18NProvider-Interface von Vaadin.
 */
@Component
public class TextProvider implements I18NProvider {

    /**
     * Gibt die unterstützten Locales zurück.
     *
     * @return Eine Liste der unterstützten Locales.
     */
    @Override
    public List<Locale> getProvidedLocales() {
        return List.of(Locale.GERMAN);
    }

    /**
     * Holt die Übersetzung für den angegebenen Schlüssel und die angegebene Locale.
     *
     * @param key Der Übersetzungsschlüssel.
     * @param locale Die Locale, für die die Übersetzung abgerufen werden soll.
     * @param params Optionale Parameter für die Formatierung der Nachricht.
     * @return Die übersetzte Nachricht.
     */
    @Override
    public String getTranslation(String key, Locale locale, Object... params) {
        ResourceBundle bundle = ResourceBundle.getBundle(ResourceConstants.TRANSLATION_PATH, locale);
        String value = bundle.getString(key);
        if (params.length > 0) {
            return String.format(value, params);
        }
        return value;
    }

    /**
     * Holt die Übersetzung für den angegebenen Schlüssel basierend auf der aktuellen Locale.
     *
     * @param key    Der Übersetzungsschlüssel.
     * @param params Optionale Parameter für die Formatierung der Nachricht.
     * @return Die übersetzte Nachricht.
     */
    public String getTranslation(String key, Object... params) {
        return getTranslation(key, getCurrentLocale(), params);
    }

    private Locale getCurrentLocale() {
        return Optional.ofNullable(UI.getCurrent())
                .map(UI::getLocale)
                .or(() -> Optional.ofNullable(VaadinSession.getCurrent()).map(VaadinSession::getLocale))
                .orElse(Locale.GERMAN);
    }
}
