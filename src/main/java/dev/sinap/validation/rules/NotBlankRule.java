package dev.sinap.validation.rules;

import dev.sinap.validation.Resources;
import dev.sinap.validation.Rule;

/**
 * A validation rule that checks that a string is not {@code null},
 * not empty, and not made up only of whitespace.
 *
 * @param message the error message used when validation fails
 */
public record NotBlankRule(String message) implements Rule<String> {

    /**
     * Creates the rule with the default localized error message.
     */
    public NotBlankRule() {
        this(Resources.get("invalid.notBlank"));
    }

    /**
     * Checks whether the given string contains at least one
     * non-whitespace character.
     *
     * @param value the input string (may be {@code null})
     * @return {@code true} if the string is not blank, {@code false} otherwise
     */
    @Override
    public boolean isValid(String value) {
        return value != null && !value.isBlank();
    }
}
