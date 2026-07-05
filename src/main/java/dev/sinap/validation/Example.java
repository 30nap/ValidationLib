package dev.sinap.validation;

import dev.sinap.validation.rules.EmailRule;
import dev.sinap.validation.rules.MinLengthRule;
import dev.sinap.validation.rules.NotBlankRule;
import dev.sinap.validation.rules.RangeRule;

/**
 * A small runnable demonstration of the library's API.
 */
public final class Example {

    private Example() {
    }

    /**
     * Runs the demonstration.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        // Validate an email with a localized (Persian) error message
        ValidationResult emailResult = Validator.of("wrong-email.com")
                .rule(new EmailRule(Resources.get("invalid.email", "fa")))
                .validate();
        System.out.println("Email valid: " + emailResult.isValid());
        System.out.println("Errors: " + emailResult.getErrors());

        // Chain multiple rules on one value
        ValidationResult passwordResult = Validator.of("secret")
                .rule(new NotBlankRule())
                .rule(new MinLengthRule(8))
                .rule(s -> s.chars().anyMatch(Character::isDigit), "Password must contain a digit")
                .validate();
        System.out.println("Password valid: " + passwordResult.isValid());
        passwordResult.getErrors().forEach(error -> System.out.println(" - " + error));

        // Validate a number against a range
        ValidationResult ageResult = Validator.of(15)
                .rule(new RangeRule<>(18, 120))
                .validate();
        System.out.println("Age valid: " + ageResult.isValid());
        ageResult.firstError().ifPresent(System.out::println);
    }
}
