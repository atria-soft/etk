package org.atriasoft.etk.math;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Vector2b Tests")
class Vector2bTest {

    // ==================== Constructor Tests ====================

    @Test
    @DisplayName("Default constructor should create (false,false)")
    void testDefaultConstructor() {
        Vector2b v = new Vector2b();
        assertFalse(v.x());
        assertFalse(v.y());
    }

    @Test
    @DisplayName("Constructor with parameters should create vector with specified values")
    void testConstructorWithParameters() {
        Vector2b v = new Vector2b(true, false);
        assertTrue(v.x());
        assertFalse(v.y());
    }

    // ==================== valueOf Tests ====================

    @Test
    @DisplayName("valueOf should parse single value")
    void testValueOfSingleValue() {
        Vector2b v = Vector2b.valueOf("true");
        assertTrue(v.x());
        assertTrue(v.y());
    }

    @Test
    @DisplayName("valueOf should parse two comma-separated values")
    void testValueOfTwoValues() {
        Vector2b v = Vector2b.valueOf("true,false");
        assertTrue(v.x());
        assertFalse(v.y());
    }

    @Test
    @DisplayName("valueOf should parse two space-separated values")
    void testValueOfSpaceSeparated() {
        Vector2b v = Vector2b.valueOf("true false");
        assertTrue(v.x());
        assertFalse(v.y());
    }

    @Test
    @DisplayName("valueOf should handle parentheses")
    void testValueOfWithParentheses() {
        Vector2b v = Vector2b.valueOf("(true,false)");
        assertTrue(v.x());
        assertFalse(v.y());
    }

    @Test
    @DisplayName("valueOf should warn about more than 2 values")
    void testValueOfMoreThanTwoValues() {
        Vector2b v = Vector2b.valueOf("true,false,true");
        assertTrue(v.x());
        assertFalse(v.y());
    }

    @Test
    @DisplayName("valueOf should parse false values")
    void testValueOfFalse() {
        Vector2b v = Vector2b.valueOf("false,false");
        assertFalse(v.x());
        assertFalse(v.y());
    }

    // ==================== Comparison Operations ====================

    @Test
    @DisplayName("isEqual")
    void testIsEqual() {
        Vector2b v1 = new Vector2b(true, false);
        Vector2b v2 = new Vector2b(true, false);
        Vector2b v3 = new Vector2b(true, true);
        assertTrue(v1.isEqual(v2));
        assertFalse(v1.isEqual(v3));
    }

    @Test
    @DisplayName("isDifferent")
    void testIsDifferent() {
        Vector2b v1 = new Vector2b(true, false);
        Vector2b v2 = new Vector2b(true, false);
        Vector2b v3 = new Vector2b(true, true);
        assertFalse(v1.isDifferent(v2));
        assertTrue(v1.isDifferent(v3));
    }

    // ==================== With Methods ====================

    @Test
    @DisplayName("withX")
    void testWithX() {
        Vector2b v = new Vector2b(true, false);
        Vector2b result = v.withX(false);
        assertFalse(result.x());
        assertFalse(result.y());
    }

    @Test
    @DisplayName("withY")
    void testWithY() {
        Vector2b v = new Vector2b(true, false);
        Vector2b result = v.withY(true);
        assertTrue(result.x());
        assertTrue(result.y());
    }

    // ==================== Static Constants ====================

    @Test
    @DisplayName("FALSE constant")
    void testFalseConstant() {
        assertFalse(Vector2b.FALSE.x());
        assertFalse(Vector2b.FALSE.y());
    }

    @Test
    @DisplayName("TRUE constant")
    void testTrueConstant() {
        assertTrue(Vector2b.TRUE.x());
        assertTrue(Vector2b.TRUE.y());
    }

    @Test
    @DisplayName("FALSE_FALSE constant")
    void testFalseFalseConstant() {
        assertFalse(Vector2b.FALSE_FALSE.x());
        assertFalse(Vector2b.FALSE_FALSE.y());
        assertSame(Vector2b.FALSE, Vector2b.FALSE_FALSE);
    }

    @Test
    @DisplayName("TRUE_TRUE constant")
    void testTrueTrueConstant() {
        assertTrue(Vector2b.TRUE_TRUE.x());
        assertTrue(Vector2b.TRUE_TRUE.y());
        assertSame(Vector2b.TRUE, Vector2b.TRUE_TRUE);
    }

    @Test
    @DisplayName("TRUE_FALSE constant")
    void testTrueFalseConstant() {
        assertTrue(Vector2b.TRUE_FALSE.x());
        assertFalse(Vector2b.TRUE_FALSE.y());
    }

    @Test
    @DisplayName("FALSE_TRUE constant")
    void testFalseTrueConstant() {
        assertFalse(Vector2b.FALSE_TRUE.x());
        assertTrue(Vector2b.FALSE_TRUE.y());
    }

    // ==================== toString Test ====================

    @Test
    @DisplayName("toString")
    void testToString() {
        Vector2b v = new Vector2b(true, false);
        assertEquals("(true,false)", v.toString());
    }

    @Test
    @DisplayName("toString all combinations")
    void testToStringAllCombinations() {
        assertEquals("(false,false)", Vector2b.FALSE_FALSE.toString());
        assertEquals("(true,true)", Vector2b.TRUE_TRUE.toString());
        assertEquals("(true,false)", Vector2b.TRUE_FALSE.toString());
        assertEquals("(false,true)", Vector2b.FALSE_TRUE.toString());
    }

    // ==================== Edge Cases ====================

    @Test
    @DisplayName("valueOf with mixed case")
    void testValueOfMixedCase() {
        Vector2b v = Vector2b.valueOf("True,False");
        assertTrue(v.x());
        assertFalse(v.y());
    }

    @Test
    @DisplayName("valueOf with invalid boolean returns false")
    void testValueOfInvalidBoolean() {
        Vector2b v = Vector2b.valueOf("notboolean,alsonotboolean");
        assertFalse(v.x());
        assertFalse(v.y());
    }
}
