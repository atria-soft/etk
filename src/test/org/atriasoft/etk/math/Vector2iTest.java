package org.atriasoft.etk.math;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Vector2i Tests")
class Vector2iTest {

    // ==================== Constructor Tests ====================

    @Test
    @DisplayName("Default constructor should create (0,0)")
    void testDefaultConstructor() {
        Vector2i v = new Vector2i();
        assertEquals(0, v.x());
        assertEquals(0, v.y());
    }

    @Test
    @DisplayName("Constructor with parameters should create vector with specified values")
    void testConstructorWithParameters() {
        Vector2i v = new Vector2i(3, 4);
        assertEquals(3, v.x());
        assertEquals(4, v.y());
    }

    // ==================== valueOf Tests ====================

    @Test
    @DisplayName("valueOf should parse single value")
    void testValueOfSingleValue() {
        Vector2i v = Vector2i.valueOf("5");
        assertEquals(5, v.x());
        assertEquals(5, v.y());
    }

    @Test
    @DisplayName("valueOf should parse two comma-separated values")
    void testValueOfTwoValues() {
        Vector2i v = Vector2i.valueOf("3,4");
        assertEquals(3, v.x());
        assertEquals(4, v.y());
    }

    @Test
    @DisplayName("valueOf should parse two space-separated values")
    void testValueOfSpaceSeparated() {
        Vector2i v = Vector2i.valueOf("3 4");
        assertEquals(3, v.x());
        assertEquals(4, v.y());
    }

    @Test
    @DisplayName("valueOf should handle parentheses")
    void testValueOfWithParentheses() {
        Vector2i v = Vector2i.valueOf("(3,4)");
        assertEquals(3, v.x());
        assertEquals(4, v.y());
    }

    @Test
    @DisplayName("valueOf should warn about more than 2 values")
    void testValueOfMoreThanTwoValues() {
        Vector2i v = Vector2i.valueOf("1,2,3");
        assertEquals(1, v.x());
        assertEquals(2, v.y());
    }

    @Test
    @DisplayName("valueOf should throw exception for invalid number")
    void testValueOfInvalidNumber() {
        assertThrows(NumberFormatException.class, () -> Vector2i.valueOf("abc"));
    }

    // ==================== Conversion Tests ====================

    @Test
    @DisplayName("toVector2f should convert to Vector2f")
    void testToVector2f() {
        Vector2i v = new Vector2i(3, 4);
        Vector2f vf = v.toVector2f();
        assertEquals(3.0f, vf.x(), 0.001f);
        assertEquals(4.0f, vf.y(), 0.001f);
    }

    // ==================== Static Method Tests ====================

    @Test
    @DisplayName("max with two vectors")
    void testMaxTwoVectors() {
        Vector2i v1 = new Vector2i(1, 4);
        Vector2i v2 = new Vector2i(3, 2);
        Vector2i result = Vector2i.max(v1, v2);
        assertEquals(3, result.x());
        assertEquals(4, result.y());
    }

    @Test
    @DisplayName("min with two vectors")
    void testMinTwoVectors() {
        Vector2i v1 = new Vector2i(1, 4);
        Vector2i v2 = new Vector2i(3, 2);
        Vector2i result = Vector2i.min(v1, v2);
        assertEquals(1, result.x());
        assertEquals(2, result.y());
    }

    // ==================== Arithmetic Operations ====================

    @Test
    @DisplayName("add scalar")
    void testAddScalar() {
        Vector2i v = new Vector2i(3, 4);
        Vector2i result = v.add(2);
        assertEquals(5, result.x());
        assertEquals(6, result.y());
    }

    @Test
    @DisplayName("add Vector2i")
    void testAddVector2i() {
        Vector2i v1 = new Vector2i(3, 4);
        Vector2i v2 = new Vector2i(1, 2);
        Vector2i result = v1.add(v2);
        assertEquals(4, result.x());
        assertEquals(6, result.y());
    }

    @Test
    @DisplayName("add components")
    void testAddComponents() {
        Vector2i v = new Vector2i(3, 4);
        Vector2i result = v.add(1, 2);
        assertEquals(4, result.x());
        assertEquals(6, result.y());
    }

    @Test
    @DisplayName("less scalar")
    void testLessScalar() {
        Vector2i v = new Vector2i(5, 6);
        Vector2i result = v.less(2);
        assertEquals(3, result.x());
        assertEquals(4, result.y());
    }

    @Test
    @DisplayName("less Vector2i")
    void testLessVector2i() {
        Vector2i v1 = new Vector2i(5, 6);
        Vector2i v2 = new Vector2i(1, 2);
        Vector2i result = v1.less(v2);
        assertEquals(4, result.x());
        assertEquals(4, result.y());
    }

    @Test
    @DisplayName("less components")
    void testLessComponents() {
        Vector2i v = new Vector2i(5, 6);
        Vector2i result = v.less(1, 2);
        assertEquals(4, result.x());
        assertEquals(4, result.y());
    }

    @Test
    @DisplayName("multiply scalar")
    void testMultiplyScalar() {
        Vector2i v = new Vector2i(3, 4);
        Vector2i result = v.multiply(2);
        assertEquals(6, result.x());
        assertEquals(8, result.y());
    }

    @Test
    @DisplayName("multiply vector")
    void testMultiplyVector() {
        Vector2i v1 = new Vector2i(3, 4);
        Vector2i v2 = new Vector2i(2, 3);
        Vector2i result = v1.multiply(v2);
        assertEquals(6, result.x());
        assertEquals(12, result.y());
    }

    @Test
    @DisplayName("devide scalar")
    void testDevideScalar() {
        Vector2i v = new Vector2i(6, 8);
        Vector2i result = v.devide(2);
        assertEquals(3, result.x());
        assertEquals(4, result.y());
    }

    @Test
    @DisplayName("devide vector")
    void testDevideVector() {
        Vector2i v1 = new Vector2i(6, 12);
        Vector2i v2 = new Vector2i(2, 3);
        Vector2i result = v1.devide(v2);
        assertEquals(3, result.x());
        assertEquals(4, result.y());
    }

    @Test
    @DisplayName("devide by zero should throw ArithmeticException")
    void testDevideByZero() {
        Vector2i v = new Vector2i(6, 8);
        assertThrows(ArithmeticException.class, () -> v.devide(0));
    }

    @Test
    @DisplayName("increment")
    void testIncrement() {
        Vector2i v = new Vector2i(3, 4);
        Vector2i result = v.increment();
        assertEquals(4, result.x());
        assertEquals(5, result.y());
    }

    @Test
    @DisplayName("decrement")
    void testDecrement() {
        Vector2i v = new Vector2i(3, 4);
        Vector2i result = v.decrement();
        assertEquals(2, result.x());
        assertEquals(3, result.y());
    }

    // ==================== Vector Operations ====================

    @Test
    @DisplayName("absolute should return absolute values")
    void testAbsolute() {
        Vector2i v = new Vector2i(-3, 4);
        Vector2i result = v.absolute();
        assertEquals(3, result.x());
        assertEquals(4, result.y());
    }

    @Test
    @DisplayName("dot product")
    void testDot() {
        Vector2i v1 = new Vector2i(3, 4);
        Vector2i v2 = new Vector2i(2, 1);
        int result = v1.dot(v2);
        assertEquals(10, result);
    }

    @Test
    @DisplayName("cross product")
    void testCross() {
        Vector2i v1 = new Vector2i(3, 4);
        Vector2i v2 = new Vector2i(2, 1);
        int result = v1.cross(v2);
        assertEquals(-5, result);
    }

    @Test
    @DisplayName("length")
    void testLength() {
        Vector2i v = new Vector2i(3, 4);
        assertEquals(5, v.length());
    }

    @Test
    @DisplayName("length2")
    void testLength2() {
        Vector2i v = new Vector2i(3, 4);
        assertEquals(25, v.length2());
    }

    @Test
    @DisplayName("distance")
    void testDistance() {
        Vector2i v1 = new Vector2i(0, 0);
        Vector2i v2 = new Vector2i(3, 4);
        assertEquals(5, v1.distance(v2));
    }

    @Test
    @DisplayName("distance2")
    void testDistance2() {
        Vector2i v1 = new Vector2i(0, 0);
        Vector2i v2 = new Vector2i(3, 4);
        assertEquals(25, v1.distance2(v2));
    }

    @Test
    @DisplayName("normalize")
    void testNormalize() {
        Vector2i v = new Vector2i(6, 8);
        Vector2i normalized = v.normalize();
        assertEquals(0, normalized.x()); // 6/10 = 0 (integer division)
        assertEquals(0, normalized.y()); // 8/10 = 0 (integer division)
    }

    @Test
    @DisplayName("safeNormalize with non-zero vector")
    void testSafeNormalize() {
        Vector2i v = new Vector2i(6, 8);
        Vector2i normalized = v.safeNormalize();
        assertEquals(0, normalized.x());
        assertEquals(0, normalized.y());
    }

    @Test
    @DisplayName("safeNormalize with zero vector returns (1,0)")
    void testSafeNormalizeZero() {
        Vector2i v = new Vector2i(0, 0);
        Vector2i normalized = v.safeNormalize();
        assertEquals(1, normalized.x());
        assertEquals(0, normalized.y());
    }

    // ==================== Comparison Operations ====================

    @Test
    @DisplayName("isEqual")
    void testIsEqual() {
        Vector2i v1 = new Vector2i(3, 4);
        Vector2i v2 = new Vector2i(3, 4);
        Vector2i v3 = new Vector2i(3, 5);
        assertTrue(v1.isEqual(v2));
        assertFalse(v1.isEqual(v3));
    }

    @Test
    @DisplayName("isDifferent")
    void testIsDifferent() {
        Vector2i v1 = new Vector2i(3, 4);
        Vector2i v2 = new Vector2i(3, 4);
        Vector2i v3 = new Vector2i(3, 5);
        assertFalse(v1.isDifferent(v2));
        assertTrue(v1.isDifferent(v3));
    }

    @Test
    @DisplayName("isGreater")
    void testIsGreater() {
        Vector2i v1 = new Vector2i(5, 6);
        Vector2i v2 = new Vector2i(3, 4);
        Vector2i v3 = new Vector2i(5, 4);
        assertTrue(v1.isGreater(v2));
        assertFalse(v1.isGreater(v3));
    }

    @Test
    @DisplayName("isGreaterOrEqual")
    void testIsGreaterOrEqual() {
        Vector2i v1 = new Vector2i(5, 6);
        Vector2i v2 = new Vector2i(3, 4);
        Vector2i v3 = new Vector2i(5, 6);
        Vector2i v4 = new Vector2i(5, 7);
        assertTrue(v1.isGreaterOrEqual(v2));
        assertTrue(v1.isGreaterOrEqual(v3));
        assertFalse(v1.isGreaterOrEqual(v4));
    }

    @Test
    @DisplayName("isLower")
    void testIsLower() {
        Vector2i v1 = new Vector2i(3, 4);
        Vector2i v2 = new Vector2i(5, 6);
        Vector2i v3 = new Vector2i(3, 6);
        assertTrue(v1.isLower(v2));
        assertFalse(v1.isLower(v3));
    }

    @Test
    @DisplayName("isLowerOrEqual")
    void testIsLowerOrEqual() {
        Vector2i v1 = new Vector2i(3, 4);
        Vector2i v2 = new Vector2i(5, 6);
        Vector2i v3 = new Vector2i(3, 4);
        Vector2i v4 = new Vector2i(2, 4);
        assertTrue(v1.isLowerOrEqual(v2));
        assertTrue(v1.isLowerOrEqual(v3));
        assertFalse(v1.isLowerOrEqual(v4));
    }

    @Test
    @DisplayName("isZero")
    void testIsZero() {
        Vector2i v1 = new Vector2i(0, 0);
        Vector2i v2 = new Vector2i(1, 0);
        assertTrue(v1.isZero());
        assertFalse(v2.isZero());
    }

    // ==================== Axis Operations ====================

    @Test
    @DisplayName("maxAxis")
    void testMaxAxis() {
        Vector2i v1 = new Vector2i(3, 5);
        Vector2i v2 = new Vector2i(5, 3);
        assertEquals(1, v1.maxAxis());
        assertEquals(0, v2.maxAxis());
    }

    @Test
    @DisplayName("minAxis")
    void testMinAxis() {
        Vector2i v1 = new Vector2i(3, 5);
        Vector2i v2 = new Vector2i(5, 3);
        assertEquals(0, v1.minAxis());
        assertEquals(1, v2.minAxis());
    }

    @Test
    @DisplayName("closestAxis")
    void testClosestAxis() {
        Vector2i v1 = new Vector2i(-3, 5);
        assertEquals(1, v1.closestAxis());
    }

    @Test
    @DisplayName("furthestAxis")
    void testFurthestAxis() {
        Vector2i v1 = new Vector2i(-3, 5);
        assertEquals(0, v1.furthestAxis());
    }

    // ==================== Instance min/max ====================

    @Test
    @DisplayName("instance max with components")
    void testInstanceMaxComponents() {
        Vector2i v = new Vector2i(1, 4);
        Vector2i result = v.max(3, 2);
        assertEquals(3, result.x());
        assertEquals(4, result.y());
    }

    @Test
    @DisplayName("instance min with components")
    void testInstanceMinComponents() {
        Vector2i v = new Vector2i(1, 4);
        Vector2i result = v.min(3, 2);
        assertEquals(1, result.x());
        assertEquals(2, result.y());
    }

    // ==================== With Methods ====================

    @Test
    @DisplayName("withX")
    void testWithX() {
        Vector2i v = new Vector2i(3, 4);
        Vector2i result = v.withX(10);
        assertEquals(10, result.x());
        assertEquals(4, result.y());
    }

    @Test
    @DisplayName("withY")
    void testWithY() {
        Vector2i v = new Vector2i(3, 4);
        Vector2i result = v.withY(10);
        assertEquals(3, result.x());
        assertEquals(10, result.y());
    }

    // ==================== Static Constants ====================

    @Test
    @DisplayName("ZERO constant")
    void testZeroConstant() {
        assertEquals(0, Vector2i.ZERO.x());
        assertEquals(0, Vector2i.ZERO.y());
    }

    @Test
    @DisplayName("ONE constant")
    void testOneConstant() {
        assertEquals(1, Vector2i.ONE.x());
        assertEquals(1, Vector2i.ONE.y());
    }

    @Test
    @DisplayName("VALUE_2 constant")
    void testValue2Constant() {
        assertEquals(2, Vector2i.VALUE_2.x());
        assertEquals(2, Vector2i.VALUE_2.y());
    }

    @Test
    @DisplayName("VALUE_1024 constant")
    void testValue1024Constant() {
        assertEquals(1024, Vector2i.VALUE_1024.x());
        assertEquals(1024, Vector2i.VALUE_1024.y());
    }

    // ==================== toString Test ====================

    @Test
    @DisplayName("toString")
    void testToString() {
        Vector2i v = new Vector2i(3, 4);
        assertEquals("Vector2i(3,4)", v.toString());
    }

    // ==================== Edge Cases ====================

    @Test
    @DisplayName("operations with negative values")
    void testNegativeValues() {
        Vector2i v = new Vector2i(-3, -4);
        assertEquals(5, v.length());
        assertEquals(3, v.absolute().x());
        assertEquals(4, v.absolute().y());
    }

    @Test
    @DisplayName("operations with zero vector")
    void testZeroVector() {
        Vector2i v = new Vector2i(0, 0);
        assertTrue(v.isZero());
        assertEquals(0, v.length());
    }

    @Test
    @DisplayName("integer overflow in multiplication")
    void testIntegerOverflow() {
        Vector2i v = new Vector2i(Integer.MAX_VALUE, Integer.MAX_VALUE);
        Vector2i result = v.multiply(2);
        // Should overflow to negative value
        assertTrue(result.x() < 0);
        assertTrue(result.y() < 0);
    }
}
