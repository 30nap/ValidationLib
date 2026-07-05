package dev.sinap.validation.rules;

import dev.sinap.validation.Resources;
import dev.sinap.validation.Rule;

/**
 * A validation rule that checks that a string has at least
 * a given number of characters.
 *
 * @param minLength the minimum allowed length (inclusive)
 * @param message   the error message used when validation fails
 */
public record MinLengthRule(int minLength, String message) implements Rule<String> {

    /**
     * Creates the rule with the default localized error message.
     *
     * @param minLength the minimum allowed length (inclusive)
     */
    public MinLengthRule(int minLength) {
        this(minLength, Resources.format("invalid.minLength", minLength));
    }

    /**
     * Checks whether the given string is at least {@code minLength} characters long.
     *
     * @param value the input string (may be {@code null})
     * @return {@code true} if the string is long enough, {@code false} otherwise
     */
    @Override
    public boolean isValid(String value) {
        return value != null && value.length() >= minLength;
    }
}
