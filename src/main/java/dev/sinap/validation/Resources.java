package dev.sinap.validation;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Provides access to localized validation messages.
 * <p>
 * Messages are loaded from the {@code i18n/messages} resource bundle.
 * The default bundle is English; a Persian ({@code fa}) translation
 * is also provided. Additional languages can be added by placing a
 * {@code messages_<lang>.properties} file on the classpath under {@code i18n/}.
 */
public final class Resources {

    private static final String BUNDLE_NAME = "i18n/messages";

    private Resources() {
    }

    /**
     * Returns the message for the given key in the JVM's default locale.
     *
     * @param key the message key
     * @return the localized message
     */
    public static String get(String key) {
        return ResourceBundle.getBundle(BUNDLE_NAME).getString(key);
    }

    /**
     * Returns the message for the given key in the given locale.
     *
     * @param key        the message key
     * @param localeCode an ISO 639 language code, e.g. {@code "en"} or {@code "fa"}
     * @return the localized message
     */
    public static String get(String key, String localeCode) {
        return get(key, Locale.of(localeCode));
    }

    /**
     * Returns the message for the given key in the given locale.
     *
     * @param key    the message key
     * @param locale the target locale
     * @return the localized message
     */
    public static String get(String key, Locale locale) {
        return ResourceBundle.getBundle(BUNDLE_NAME, locale).getString(key);
    }

    /**
     * Returns the message for the given key in the JVM's default locale,
     * formatted with the given arguments using {@link MessageFormat}.
     *
     * @param key  the message key
     * @param args the format arguments
     * @return the localized, formatted message
     */
    public static String format(String key, Object... args) {
        return MessageFormat.format(get(key), args);
    }

    /**
     * Returns the message for the given key in the given locale,
     * formatted with the given arguments using {@link MessageFormat}.
     *
     * @param key    the message key
     * @param locale the target locale
     * @param args   the format arguments
     * @return the localized, formatted message
     */
    public static String format(String key, Locale locale, Object... args) {
        return MessageFormat.format(get(key, locale), args);
    }
}
