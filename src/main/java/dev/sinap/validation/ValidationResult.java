package dev.sinap.validation;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents the result of a validation process.
 * <p>
 * A {@code ValidationResult} is either valid (no errors)
 * or invalid (with a list of error messages). Instances are immutable.
 */
public class ValidationResult {

    private final boolean valid;
    private final List<String> errors;

    private ValidationResult(boolean valid, List<String> errors) {
        this.valid = valid;
        this.errors = errors;
    }

    /**
     * Creates a valid result with no errors.
     *
     * @return a successful {@code ValidationResult}
     */
    public static ValidationResult ok() {
        return new ValidationResult(true, List.of());
    }

    /**
     * Creates an invalid result with the given errors.
     * <p>
     * The list is defensively copied, so later changes to the
     * original list do not affect this result.
     *
     * @param errors the list of error messages (must not be null or empty)
     * @return a failed {@code ValidationResult}
     * @throws NullPointerException     if {@code errors} is {@code null}
     * @throws IllegalArgumentException if {@code errors} is empty
     */
    public static ValidationResult fail(List<String> errors) {
        Objects.requireNonNull(errors, "errors must not be null");
        if (errors.isEmpty()) {
            throw new IllegalArgumentException("A failed result must contain at least one error");
        }
        return new ValidationResult(false, List.copyOf(errors));
    }

    /**
     * Returns whether the validation passed.
     *
     * @return {@code true} if all rules passed, {@code false} otherwise
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * Returns the list of validation error messages.
     * <p>
     * This list will be empty if {@link #isValid()} is {@code true}.
     *
     * @return an unmodifiable list of error messages
     */
    public List<String> getErrors() {
        return errors;
    }

    /**
     * Returns the first validation error, if any.
     *
     * @return an {@link Optional} containing the first error message,
     *         or an empty {@link Optional} if validation passed
     */
    public Optional<String> firstError() {
        return errors.isEmpty() ? Optional.empty() : Optional.of(errors.get(0));
    }

    @Override
    public String toString() {
        return valid ? "ValidationResult[valid]" : "ValidationResult[invalid, errors=" + errors + "]";
    }
}
