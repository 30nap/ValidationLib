package dev.sinap.validation;

import dev.sinap.validation.rules.EmailRule;
import dev.sinap.validation.rules.MinLengthRule;
import dev.sinap.validation.rules.NotBlankRule;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidatorTest {

    @Test
    void passesWhenAllRulesPass() {
        ValidationResult result = Validator.of("user@example.com")
                .rule(new NotBlankRule())
                .rule(new EmailRule())
                .validate();

        assertTrue(result.isValid());
        assertTrue(result.getErrors().isEmpty());
    }

    @Test
    void collectsErrorsFromAllFailedRules() {
        ValidationResult result = Validator.of("ab")
                .rule(new MinLengthRule(5, "too short"))
                .rule(new EmailRule("not an email"))
                .validate();

        assertFalse(result.isValid());
        assertEquals(List.of("too short", "not an email"), result.getErrors());
    }

    @Test
    void supportsPredicateRules() {
        ValidationResult result = Validator.of(42)
                .rule(n -> n % 2 == 0, "must be even")
                .rule(n -> n > 100, "must be greater than 100")
                .validate();

        assertFalse(result.isValid());
        assertEquals(List.of("must be greater than 100"), result.getErrors());
    }

    @Test
    void validatorWithoutRulesIsValid() {
        assertTrue(Validator.of("anything").validate().isValid());
    }

    @Test
    void supportsNullValues() {
        ValidationResult result = Validator.of((String) null)
                .rule(new NotBlankRule("required"))
                .validate();

        assertFalse(result.isValid());
        assertEquals(List.of("required"), result.getErrors());
    }

    @Test
    void rejectsNullRule() {
        Validator<String> validator = Validator.of("value");
        assertThrows(NullPointerException.class, () -> validator.rule((Rule<String>) null));
    }

    @Test
    void rulesAreEvaluatedLazilyOnValidate() {
        boolean[] evaluated = {false};
        Validator<String> validator = Validator.of("value")
                .rule(v -> {
                    evaluated[0] = true;
                    return true;
                }, "never");

        assertFalse(evaluated[0]);
        validator.validate();
        assertTrue(evaluated[0]);
    }
}
