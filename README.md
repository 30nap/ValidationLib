# ValidationLib

[![Build](https://github.com/30nap/ValidationLib/actions/workflows/build.yml/badge.svg)](https://github.com/30nap/ValidationLib/actions/workflows/build.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
![Java](https://img.shields.io/badge/Java-21-orange)

A lightweight, fluent, and extensible Java validation library. Validate any value with built-in or custom rules, collect all error messages at once, and localize messages out of the box, with **zero external dependencies**.

```java
ValidationResult result = Validator.of("user@example.com")
        .rule(new NotBlankRule())
        .rule(new EmailRule())
        .validate();

if (!result.isValid()) {
    result.getErrors().forEach(System.out::println);
}
```

## Features

- **Fluent API** — chain any number of rules on a single value
- **Built-in rules** — `EmailRule`, `NotNullRule`, `NotBlankRule`, `MinLengthRule`, `MaxLengthRule`, `RangeRule`, `PatternRule`
- **Lambda rules** — define ad-hoc rules inline with a predicate and a message
- **All errors at once** — validation collects every failed rule, not just the first
- **i18n support** — localized default messages (English and Persian included, easily extendable)
- **Immutable results** — `ValidationResult` is defensive and thread-safe
- **Pure Java 21** — no external runtime dependencies
- **Fully documented** — JavaDoc on every public class and method, covered by a JUnit 5 test suite

## Installation

The library is published via [JitPack](https://jitpack.io/#30nap/ValidationLib).

**Gradle**

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.30nap:ValidationLib:v1.0.0'
}
```

**Maven**

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.30nap</groupId>
    <artifactId>ValidationLib</artifactId>
    <version>v1.0.0</version>
</dependency>
```

## Usage

### Chaining rules

```java
ValidationResult result = Validator.of(password)
        .rule(new NotBlankRule())
        .rule(new MinLengthRule(8))
        .rule(new MaxLengthRule(64))
        .validate();
```

### Inline rules with lambdas

```java
ValidationResult result = Validator.of(password)
        .rule(new MinLengthRule(8))
        .rule(p -> p.chars().anyMatch(Character::isDigit), "Password must contain a digit")
        .validate();
```

### Numeric ranges

`RangeRule` works with any `Comparable` type:

```java
Validator.of(age)
        .rule(new RangeRule<>(18, 120))
        .validate();
```

### Reading the result

```java
ValidationResult result = Validator.of(email).rule(new EmailRule()).validate();

result.isValid();     // true / false
result.getErrors();   // unmodifiable list of all error messages
result.firstError();  // Optional<String> with the first error
```

## Built-in rules

| Rule | Applies to | Checks |
|------|-----------|--------|
| `EmailRule` | `String` | valid email address syntax |
| `NotNullRule<T>` | any | value is not `null` |
| `NotBlankRule` | `String` | not `null`, empty, or whitespace-only |
| `MinLengthRule` | `String` | at least *n* characters |
| `MaxLengthRule` | `String` | at most *n* characters (`null` passes) |
| `RangeRule<T>` | `Comparable<T>` | value within `[min, max]` inclusive |
| `PatternRule` | `String` | full match against a regular expression |

Every rule has two constructors: one that takes a custom error message, and a default one that uses a localized message from the resource bundle.

## Custom rules

Implement the `Rule<T>` interface — records work great:

```java
public record PositiveRule(String message) implements Rule<Integer> {
    @Override
    public boolean isValid(Integer value) {
        return value != null && value > 0;
    }
}
```

Or create one from a predicate without a new class:

```java
Rule<String> noSpaces = Rule.of(s -> s != null && !s.contains(" "), "Must not contain spaces");
```

## Localization (i18n)

Default messages are resolved from the `i18n/messages` resource bundle. English and Persian are included:

```java
Resources.get("invalid.email");            // JVM default locale
Resources.get("invalid.email", "fa");      // ایمیل معتبر نیست
Resources.format("invalid.minLength", 8);  // "Value must be at least 8 characters long"
```

To add a language, place a `messages_<lang>.properties` file under `i18n/` on the classpath.

## Building from source

```bash
git clone https://github.com/30nap/ValidationLib.git
cd ValidationLib
./gradlew build   # compiles, runs tests, and builds the jar
```

Requires JDK 21 (a Gradle toolchain will download one automatically if missing).

## License

[MIT License](LICENSE) © 2025 Sina Pezeshki
