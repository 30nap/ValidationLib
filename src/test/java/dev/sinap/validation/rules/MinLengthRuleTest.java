package dev.sinap.validation.rules;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MinLengthRuleTest {

    private final MinLengthRule rule = new MinLengthRule(3, "too short");

    @Test
    void acceptsStringsAtOrAboveMinLength() {
        assertTrue(rule.isValid("abc"));
        assertTrue(rule.isValid("abcd"));
    }

    @Test
    void rejectsShorterStringsAndNull() {
        assertFalse(rule.isValid("ab"));
        assertFalse(rule.isValid(""));
        assertFalse(rule.isValid(null));
    }

    @Test
    void defaultMessageContainsTheLimit() {
        assertTrue(new MinLengthRule(8).message().contains("8"));
    }
}
