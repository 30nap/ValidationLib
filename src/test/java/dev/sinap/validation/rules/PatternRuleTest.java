package dev.sinap.validation.rules;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PatternRuleTest {

    private final PatternRule rule = new PatternRule("[a-z0-9_]{3,16}", "invalid username");

    @Test
    void acceptsMatchingStrings() {
        assertTrue(rule.isValid("user_42"));
    }

    @Test
    void rejectsNonMatchingStringsAndNull() {
        assertFalse(rule.isValid("AB"));
        assertFalse(rule.isValid("way-too-long-and-invalid!"));
        assertFalse(rule.isValid(null));
    }

    @Test
    void requiresFullMatch() {
        assertFalse(rule.isValid("valid but with spaces"));
    }

    @Test
    void acceptsPrecompiledPatterns() {
        PatternRule digits = new PatternRule(Pattern.compile("\\d+"), "digits only");
        assertTrue(digits.isValid("12345"));
        assertFalse(digits.isValid("12a45"));
    }

    @Test
    void rejectsNullPattern() {
        assertThrows(NullPointerException.class, () -> new PatternRule((Pattern) null, "msg"));
    }

    @Test
    void usesLocalizedDefaultMessage() {
        assertFalse(new PatternRule("\\d+").message().isBlank());
    }
}
