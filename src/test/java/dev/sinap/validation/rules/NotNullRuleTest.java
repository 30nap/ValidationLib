package dev.sinap.validation.rules;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NotNullRuleTest {

    @Test
    void acceptsNonNullValues() {
        assertTrue(new NotNullRule<String>("required").isValid(""));
        assertTrue(new NotNullRule<Integer>("required").isValid(0));
    }

    @Test
    void rejectsNull() {
        assertFalse(new NotNullRule<String>("required").isValid(null));
    }

    @Test
    void usesLocalizedDefaultMessage() {
        assertFalse(new NotNullRule<String>().message().isBlank());
    }
}
