package dev.sinap.validation;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidationResultTest {

    @Test
    void okIsValidAndHasNoErrors() {
        ValidationResult result = ValidationResult.ok();

        assertTrue(result.isValid());
        assertTrue(result.getErrors().isEmpty());
        assertTrue(result.firstError().isEmpty());
    }

    @Test
    void failIsInvalidAndExposesErrors() {
        ValidationResult result = ValidationResult.fail(List.of("first", "second"));

        assertFalse(result.isValid());
        assertEquals(List.of("first", "second"), result.getErrors());
        assertEquals("first", result.firstError().orElseThrow());
    }

    @Test
    void failCopiesTheErrorList() {
        List<String> errors = new ArrayList<>(List.of("original"));
        ValidationResult result = ValidationResult.fail(errors);

        errors.add("added later");

        assertEquals(List.of("original"), result.getErrors());
    }

    @Test
    void errorListIsUnmodifiable() {
        ValidationResult result = ValidationResult.fail(List.of("error"));
        assertThrows(UnsupportedOperationException.class, () -> result.getErrors().add("more"));
    }

    @Test
    void failRejectsNullAndEmptyErrors() {
        assertThrows(NullPointerException.class, () -> ValidationResult.fail(null));
        assertThrows(IllegalArgumentException.class, () -> ValidationResult.fail(List.of()));
    }
}
