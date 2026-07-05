package dev.sinap.validation.rules;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MaxLengthRuleTest {

    private final MaxLengthRule rule = new MaxLengthRule(5, "too long");

    @Test
    void acceptsStringsAtOrBelowMaxLength() {
        assertTrue(rule.isValid(""));
        assertTrue(rule.isValid("abcde"));
    }

    @Test
    void acceptsNullBecauseItHasNoLength() {
        assertTrue(rule.isValid(null));
    }

    @Test
    void rejectsLongerStrings() {
        assertFalse(rule.isValid("abcdef"));
    }

    @Test
    void defaultMessageContainsTheLimit() {
        assertTrue(new MaxLengthRule(16).message().contains("16"));
    }
}
