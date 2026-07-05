package dev.sinap.validation.rules;

import dev.sinap.validation.Resources;
import dev.sinap.validation.Rule;

/**
 * A validation rule that checks that a comparable value lies
 * within an inclusive range.
 *
 * <pre>{@code
 * Validator.of(age)
 *         .rule(new RangeRule<>(18, 120))
 *         .validate();
 * }</pre>
 *
 * @param min     the lower bound (inclusive)
 * @param max     the upper bound (inclusive)
 * @param message the error message used when validation fails
 * @param <T>     the comparable type of value this rule validates
 */
public record RangeRule<T extends Comparable<T>>(T min, T max, String message) implements Rule<T> {

    /**
     * Validates the bounds of the range.
     *
     * @throws NullPointerException     if {@code min} or {@code max} is {@code null}
     * @throws IllegalArgumentException if {@code min} is greater than {@code max}
     */
    public RangeRule {
        if (min == null || max == null) {
            throw new NullPointerException("min and max must not be null");
        }
        if (min.compareTo(max) > 0) {
            throw new IllegalArgumentException("min must not be greater than max");
        }
    }

    /**
     * Creates the rule with the default localized error message.
     *
     * @param min the lower bound (inclusive)
     * @param max the upper bound (inclusive)
     */
    public RangeRule(T min, T max) {
        this(min, max, Resources.format("invalid.range", min, max));
    }

    /**
     * Checks whether the given value lies within {@code [min, max]}.
     *
     * @param value the input value (may be {@code null})
     * @return {@code true} if the value is within range, {@code false} otherwise
     */
    @Override
    public boolean isValid(T value) {
        return value != null
                && value.compareTo(min) >= 0
                && value.compareTo(max) <= 0;
    }
}
