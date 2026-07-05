package dev.sinap.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * The main entry point for applying multiple validation rules
 * to a single value of type {@code T}.
 * <p>
 * A {@code Validator} instance is created with {@link #of(Object)} and rules
 * are added using {@link #rule(Rule)}. Rules are evaluated lazily when
 * {@link #validate()} is called, which returns a {@link ValidationResult}
 * indicating whether all rules passed.
 *
 * <pre>{@code
 * ValidationResult result = Validator.of("user@example.com")
 *         .rule(new NotBlankRule())
 *         .rule(new EmailRule())
 *         .validate();
 * }</pre>
 *
 * @param <T> the type of value being validated
 */
public class Validator<T> {

    private final T value;
    private final List<Rule<T>> rules = new ArrayList<>();

    private Validator(T value) {
        this.value = value;
    }

    /**
     * Creates a new {@code Validator} for the given value.
     *
     * @param value the value to validate (can be {@code null})
     * @param <T>   the type of the value
     * @return a new {@code Validator} instance
     */
    public static <T> Validator<T> of(T value) {
        return new Validator<>(value);
    }

    /**
     * Adds a validation rule to be checked against the value.
     * <p>
     * Rules are not evaluated until {@link #validate()} is called.
     *
     * @param rule the validation rule to apply
     * @return this {@code Validator} instance for method chaining
     * @throws NullPointerException if {@code rule} is {@code null}
     */
    public Validator<T> rule(Rule<T> rule) {
        rules.add(Objects.requireNonNull(rule, "rule must not be null"));
        return this;
    }

    /**
     * Adds an ad-hoc validation rule defined by a predicate and an error message.
     *
     * <pre>{@code
     * Validator.of(age)
     *         .rule(a -> a >= 18, "Must be an adult")
     *         .validate();
     * }</pre>
     *
     * @param predicate the condition the value must satisfy
     * @param message   the error message used when the predicate fails
     * @return this {@code Validator} instance for method chaining
     * @throws NullPointerException if {@code predicate} or {@code message} is {@code null}
     */
    public Validator<T> rule(Predicate<T> predicate, String message) {
        return rule(Rule.of(predicate, message));
    }

    /**
     * Evaluates all added rules against the value and returns a {@link ValidationResult}.
     * <p>
     * All rules are always evaluated, so the result contains the error
     * messages of every failed rule, not just the first one.
     *
     * @return a {@link ValidationResult} containing the outcome of validation
     */
    public ValidationResult validate() {
        List<String> errors = rules.stream()
                .filter(rule -> !rule.isValid(value))
                .map(Rule::message)
                .toList();
        return errors.isEmpty()
                ? ValidationResult.ok()
                : ValidationResult.fail(errors);
    }
}
