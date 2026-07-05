package dev.sinap.validation;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * A generic contract for validation rules.
 * <p>
 * Each rule checks a specific constraint on a value of type {@code T}.
 * If the value does not satisfy the rule, an error message is provided.
 *
 * @param <T> the type of value this rule validates
 */
public interface Rule<T> {

    /**
     * Checks if the given value satisfies the rule.
     *
     * @param value the input value to validate (can be {@code null})
     * @return {@code true} if the value is valid according to this rule,
     *         {@code false} otherwise
     */
    boolean isValid(T value);

    /**
     * Returns the error message that should be displayed
     * if the rule validation fails.
     *
     * @return a non-null error message describing the validation failure
     */
    String message();

    /**
     * Creates a rule from a predicate and an error message.
     * <p>
     * This is a convenient way to define ad-hoc rules without
     * implementing the interface explicitly:
     * <pre>{@code
     * Rule<String> startsWithA = Rule.of(s -> s != null && s.startsWith("A"), "Must start with A");
     * }</pre>
     *
     * @param predicate the condition the value must satisfy
     * @param message   the error message used when the predicate fails
     * @param <T>       the type of value the rule validates
     * @return a new {@code Rule} backed by the given predicate
     * @throws NullPointerException if {@code predicate} or {@code message} is {@code null}
     */
    static <T> Rule<T> of(Predicate<T> predicate, String message) {
        Objects.requireNonNull(predicate, "predicate must not be null");
        Objects.requireNonNull(message, "message must not be null");
        return new Rule<>() {
            @Override
            public boolean isValid(T value) {
                return predicate.test(value);
            }

            @Override
            public String message() {
                return message;
            }
        };
    }
}
