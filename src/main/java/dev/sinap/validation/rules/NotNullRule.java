package dev.sinap.validation.rules;

import dev.sinap.validation.Resources;
import dev.sinap.validation.Rule;

/**
 * A validation rule that checks that a value is not {@code null}.
 *
 * @param message the error message used when validation fails
 * @param <T>     the type of value this rule validates
 */
public record NotNullRule<T>(String message) implements Rule<T> {

    /**
     * Creates the rule with the default localized error message.
     */
    public NotNullRule() {
        this(Resources.get("invalid.notNull"));
    }

    /**
     * Checks whether the given value is not {@code null}.
     *
     * @param value the input value (may be {@code null})
     * @return {@code true} if the value is not {@code null}, {@code false} otherwise
     */
    @Override
    public boolean isValid(T value) {
        return value != null;
    }
}
