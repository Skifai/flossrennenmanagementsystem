package ch.flossrennen.managementsystem.textprovider;

import ch.flossrennen.managementsystem.util.ResourceConstants;
import com.vaadin.flow.i18n.I18NProvider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@Component
public class TextProvider implements I18NProvider {

    @Override
    public List<Locale> getProvidedLocales() {
        return List.of(Locale.GERMAN);
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
}
