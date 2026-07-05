package dev.sinap.validation.rules;

import dev.sinap.validation.Resources;
import dev.sinap.validation.Rule;

import java.util.regex.Pattern;

/**
 * A validation rule that checks if a string is a valid email address.
 * <p>
 * This rule performs a pragmatic syntax check: it requires a local part,
 * an {@code @} sign, and a domain with at least one dot. It does not
 * perform DNS or MX record checks.
 *
 * @param message the error message used when validation fails
 */
public record EmailRule(String message) implements Rule<String> {

    private static final int MAX_EMAIL_LENGTH = 254;

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)+$");

    /**
     * Creates the rule with the default localized error message.
     */
    public EmailRule() {
        this(Resources.get("invalid.email"));
    }

    /**
     * Checks whether the given string is a valid email address.
     *
     * @param value the input string (may be {@code null})
     * @return {@code true} if the string matches the email pattern,
     *         {@code false} otherwise
     */
    @Override
    public boolean isValid(String value) {
        return value != null
                && value.length() <= MAX_EMAIL_LENGTH
                && EMAIL_PATTERN.matcher(value).matches();
    }
}
