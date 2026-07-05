package dev.sinap.validation;

import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.MissingResourceException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ResourcesTest {

    @Test
    void returnsEnglishMessage() {
        assertEquals("Email is invalid", Resources.get("invalid.email", "en"));
    }

    @Test
    void returnsPersianTranslation() {
        String persian = Resources.get("invalid.email", "fa");

        assertFalse(persian.isBlank());
        assertNotEquals(Resources.get("invalid.email", "en"), persian);
    }

    @Test
    void acceptsLocaleObjects() {
        assertEquals("Email is invalid", Resources.get("invalid.email", Locale.ENGLISH));
    }

    @Test
    void formatsMessagesWithArguments() {
        String message = Resources.format("invalid.minLength", Locale.ENGLISH, 8);
        assertEquals("Value must be at least 8 characters long", message);
    }

    @Test
    void throwsForUnknownKey() {
        assertThrows(MissingResourceException.class, () -> Resources.get("does.not.exist"));
    }
}
