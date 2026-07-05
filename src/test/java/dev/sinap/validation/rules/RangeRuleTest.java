package dev.sinap.validation.rules;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RangeRuleTest {

    private final RangeRule<Integer> rule = new RangeRule<>(18, 120, "out of range");

    @Test
    void acceptsValuesWithinRangeIncludingBounds() {
        assertTrue(rule.isValid(18));
        assertTrue(rule.isValid(50));
        assertTrue(rule.isValid(120));
    }

    @Test
    void rejectsValuesOutsideRangeAndNull() {
        assertFalse(rule.isValid(17));
        assertFalse(rule.isValid(121));
        assertFalse(rule.isValid(null));
    }

    @Test
    void worksWithOtherComparableTypes() {
        RangeRule<String> alphabetic = new RangeRule<>("a", "m", "out of range");
        assertTrue(alphabetic.isValid("g"));
        assertFalse(alphabetic.isValid("z"));
    }

    @Test
    void rejectsInvalidBounds() {
        assertThrows(IllegalArgumentException.class, () -> new RangeRule<>(10, 5, "msg"));
        assertThrows(NullPointerException.class, () -> new RangeRule<Integer>(null, 5, "msg"));
    }

    @Test
    void defaultMessageContainsTheBounds() {
        String message = new RangeRule<>(18, 120).message();
        assertTrue(message.contains("18"));
        assertTrue(message.contains("120"));
    }
}
