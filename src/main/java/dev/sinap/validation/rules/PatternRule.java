package dev.sinap.validation.rules;

import dev.sinap.validation.Resources;
import dev.sinap.validation.Rule;

import java.util.regex.Pattern;

/**
 * A validation rule that checks that a string matches a regular expression.
 *
 * <pre>{@code
 * Validator.of(username)
 *         .rule(new PatternRule("[a-z0-9_]{3,16}", "Invalid username"))
 *         .validate();
 * }</pre>
 *
 * @param pattern the compiled regular expression the value must match
 * @param message the error message used when validation fails
 */
public record PatternRule(Pattern pattern, String message) implements Rule<String> {

    /**
     * Validates the components.
     *
     * @throws NullPointerException if {@code pattern} is {@code null}
     */
    public PatternRule {
        if (pattern == null) {
            throw new NullPointerException("pattern must not be null");
        }
    }

    /**
     * Creates the rule from a regular expression string and a custom message.
     *
     * @param regex   the regular expression the value must match
     * @param message the error message used when validation fails
     */
    public PatternRule(String regex, String message) {
        this(Pattern.compile(regex), message);
    }

    /**
     * Creates the rule from a regular expression string with the
     * default localized error message.
     *
     * @param regex the regular expression the value must match
     */
    public PatternRule(String regex) {
        this(Pattern.compile(regex), Resources.get("invalid.pattern"));
    }

    /**
     * Checks whether the given string fully matches the pattern.
     *
     * @param value the input string (may be {@code null})
     * @return {@code true} if the string matches, {@code false} otherwise
     */
    @Override
    public boolean isValid(String value) {
        return value != null && pattern.matcher(value).matches();
    }
}
