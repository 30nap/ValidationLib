package dev.sinap.validation.rules;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NotBlankRuleTest {

    private final NotBlankRule rule = new NotBlankRule("required");

    @ParameterizedTest
    @ValueSource(strings = {"a", " a ", "hello world"})
    void acceptsNonBlankStrings(String value) {
        assertTrue(rule.isValid(value));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " ", "   ", "\t", "\n"})
    void rejectsBlankStrings(String value) {
        assertFalse(rule.isValid(value));
    }

    @Test
    void usesLocalizedDefaultMessage() {
        assertFalse(new NotBlankRule().message().isBlank());
    }
}
