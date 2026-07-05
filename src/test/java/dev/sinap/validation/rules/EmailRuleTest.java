package dev.sinap.validation.rules;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmailRuleTest {

    private final EmailRule rule = new EmailRule("invalid");

    @ParameterizedTest
    @ValueSource(strings = {
            "user@example.com",
            "first.last@example.com",
            "user+tag@example.co.uk",
            "user_name@sub.domain.org",
            "u@d.io"
    })
    void acceptsValidEmails(String email) {
        assertTrue(rule.isValid(email));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            "",
            "plainaddress",
            "wrong-email.com",
            "@example.com",
            "user@",
            "user@domain",
            "user@@example.com",
            "user name@example.com"
    })
    void rejectsInvalidEmails(String email) {
        assertFalse(rule.isValid(email));
    }

    @Test
    void rejectsOverlongEmails() {
        String email = "a".repeat(250) + "@example.com";
        assertFalse(rule.isValid(email));
    }

    @Test
    void usesLocalizedDefaultMessage() {
        assertFalse(new EmailRule().message().isBlank());
    }

    @Test
    void exposesCustomMessage() {
        assertEquals("invalid", rule.message());
    }
}
