package dev.sinap.validation.rules;

import dev.sinap.validation.Resources;
import dev.sinap.validation.Rule;

/**
 * A validation rule that checks that a string has at most
 * a given number of characters.
 * <p>
 * A {@code null} value is considered valid, since it has no length
 * to exceed the limit; combine with {@link NotNullRule} if the value
 * is required.
 *
 * @param maxLength the maximum allowed length (inclusive)
 * @param message   the error message used when validation fails
 */
public record MaxLengthRule(int maxLength, String message) implements Rule<String> {

    /**
     * Creates the rule with the default localized error message.
     *
     * @param maxLength the maximum allowed length (inclusive)
     */
    public MaxLengthRule(int maxLength) {
        this(maxLength, Resources.format("invalid.maxLength", maxLength));
    }

    /**
     * Checks whether the given string is at most {@code maxLength} characters long.
     *
     * @param value the input string (may be {@code null})
     * @return {@code true} if the string is short enough or {@code null},
     *         {@code false} otherwise
     */
    @Override
    public boolean isValid(String value) {
        return value == null || value.length() <= maxLength;
    }
}
