package ch.flossrennen.managementsystem.util;

import java.util.Optional;

/**
 * Repräsentiert das Ergebnis einer Operation oder Validierung.
 *
 * @param <T> Der Typ der Daten, die im Erfolgsfall zurückgegeben werden.
 */
public class CheckResult<T> {

    private final boolean success;
    private final String message;
    private final T data;

    /**
     * Erstellt ein neues CheckResult.
     *
     * @param success Gibt an, ob die Operation erfolgreich war.
     * @param message Eine optionale Nachricht (z. B. Fehlermeldung oder Erfolgsmeldung).
     * @param data    Die resultierenden Daten der Operation.
     */
    public CheckResult(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    /**
     * Erstellt ein erfolgreiches CheckResult mit Daten.
     *
     * @param data Die Daten der Operation.
     * @param <T> Der Typ der Daten.
     * @return Ein erfolgreiches CheckResult.
     */
    public static <T> CheckResult<T> success(T data) {
        return new CheckResult<>(true, null, data);
    }

    /**
     * Erstellt ein erfolgreiches CheckResult mit Daten und Nachricht.
     *
     * @param data Die Daten der Operation.
     * @param message Eine Erfolgsmeldung.
     * @param <T> Der Typ der Daten.
     * @return Ein erfolgreiches CheckResult.
     */
    public static <T> CheckResult<T> success(T data, String message) {
        return new CheckResult<>(true, message, data);
    }

    /**
     * Erstellt ein fehlerhaftes CheckResult mit einer Fehlermeldung.
     *
     * @param message Die Fehlermeldung.
     * @param <T> Der Typ der Daten.
     * @return Ein fehlerhaftes CheckResult.
     */
    public static <T> CheckResult<T> failure(String message) {
        return new CheckResult<>(false, message, null);
    }

    /**
     * Gibt zurück, ob die Operation erfolgreich war.
     *
     * @return true, wenn erfolgreich, sonst false.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Gibt die Nachricht des Ergebnisses zurück.
     *
     * @return Die Nachricht oder null.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gibt die Daten des Ergebnisses zurück.
     *
     * @return Ein Optional mit den Daten.
     */
    public Optional<T> getData() {
        return Optional.ofNullable(data);
    }
}
