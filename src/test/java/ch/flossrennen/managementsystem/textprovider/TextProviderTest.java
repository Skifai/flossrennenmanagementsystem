package ch.flossrennen.managementsystem.textprovider;

import ch.flossrennen.managementsystem.util.textprovider.TextProvider;
import ch.flossrennen.managementsystem.util.textprovider.TranslationConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class TextProviderTest {

    private TextProvider textProvider;

    @BeforeEach
    void setUp() {
        textProvider = new TextProvider();
    }

    @Test
    void getProvidedLocales() {
        assertTrue(textProvider.getProvidedLocales().contains(Locale.GERMAN));
    }

    @Test
    void getTranslation_WithLocale() {
        String translation = textProvider.getTranslation(TranslationConstants.LOGIN_TITLE, Locale.GERMAN);
        assertNotNull(translation);
        assertFalse(translation.isEmpty());
    }

    @Test
    void getTranslation_DefaultLocale() {
        String translation = textProvider.getTranslation(TranslationConstants.LOGIN_TITLE);
        assertNotNull(translation);
    }
}
