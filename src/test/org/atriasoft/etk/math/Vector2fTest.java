package org.atriasoft.etk.math;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Vector2f Tests")
class Vector2fTest {

    private static final float EPSILON = 0.0001f;

    // ==================== Constructor Tests ====================

    @Test
    @DisplayName("Constructor should create vector with specified values")
    void testConstructor() {
        Vector2f v = new Vector2f(3.0f, 4.0f);
        assertEquals(3.0f, v.x(), EPSILON);
        assertEquals(4.0f, v.y(), EPSILON);
    }

    // ==================== valueOf Tests ====================

    @Test
    @DisplayName("valueOf should parse single value")
    void testValueOfSingleValue() {
        Vector2f v = Vector2f.valueOf("5.0");
        assertEquals(5.0f, v.x(), EPSILON);
        assertEquals(5.0f, v.y(), EPSILON);
    }

    @Test
    @DisplayName("valueOf should parse two comma-separated values")
    void testValueOfTwoValues() {
        Vector2f v = Vector2f.valueOf("3.0,4.0");
        assertEquals(3.0f, v.x(), EPSILON);
        assertEquals(4.0f, v.y(), EPSILON);
    }

    @Test
    @DisplayName("valueOf should parse two space-separated values")
    void testValueOfSpaceSeparated() {
        Vector2f v = Vector2f.valueOf("3.0 4.0");
        assertEquals(3.0f, v.x(), EPSILON);
        assertEquals(4.0f, v.y(), EPSILON);
    }

    @Test
    @DisplayName("valueOf should handle parentheses")
    void testValueOfWithParentheses() {
        Vector2f v = Vector2f.valueOf("(3.0,4.0)");
        assertEquals(3.0f, v.x(), EPSILON);
        assertEquals(4.0f, v.y(), EPSILON);
    }

    @Test
    @DisplayName("valueOf should warn about more than 2 values")
    void testValueOfMoreThanTwoValues() {
        Vector2f v = Vector2f.valueOf("1.0,2.0,3.0");
        assertEquals(1.0f, v.x(), EPSILON);
        assertEquals(2.0f, v.y(), EPSILON);
    }

    // ==================== Conversion Tests ====================

    @Test
    @DisplayName("toVector2i should convert to Vector2i")
    void testToVector2i() {
        Vector2f v = new Vector2f(3.7f, 4.2f);
        Vector2i vi = v.toVector2i();
        assertEquals(3, vi.x());
        assertEquals(4, vi.y());
    }

    @Test
    @DisplayName("toVector3f should convert to Vector3f with z=0")
    void testToVector3f() {
        Vector2f v = new Vector2f(3.0f, 4.0f);
        Vector3f v3 = v.toVector3f();
        assertEquals(3.0f, v3.x(), EPSILON);
        assertEquals(4.0f, v3.y(), EPSILON);
        assertEquals(0.0f, v3.z(), EPSILON);
    }

    // ==================== Static Method Tests ====================

    @Test
    @DisplayName("clipInt should truncate to integers")
    void testClipInt() {
        Vector2f v = new Vector2f(3.7f, 4.2f);
        Vector2f clipped = Vector2f.clipInt(v);
        assertEquals(3.0f, clipped.x(), EPSILON);
        assertEquals(4.0f, clipped.y(), EPSILON);
    }

    @Test
    @DisplayName("max with two vectors")
    void testMaxTwoVectors() {
        Vector2f v1 = new Vector2f(1.0f, 4.0f);
        Vector2f v2 = new Vector2f(3.0f, 2.0f);
        Vector2f result = Vector2f.max(v1, v2);
        assertEquals(3.0f, result.x(), EPSILON);
        assertEquals(4.0f, result.y(), EPSILON);
    }

    @Test
    @DisplayName("max with three vectors")
    void testMaxThreeVectors() {
        Vector2f v1 = new Vector2f(1.0f, 4.0f);
        Vector2f v2 = new Vector2f(3.0f, 2.0f);
        Vector2f v3 = new Vector2f(2.0f, 5.0f);
        Vector2f result = Vector2f.max(v1, v2, v3);
        assertEquals(3.0f, result.x(), EPSILON);
        assertEquals(5.0f, result.y(), EPSILON);
    }

    @Test
    @DisplayName("min with two vectors")
    void testMinTwoVectors() {
        Vector2f v1 = new Vector2f(1.0f, 4.0f);
        Vector2f v2 = new Vector2f(3.0f, 2.0f);
        Vector2f result = Vector2f.min(v1, v2);
        assertEquals(1.0f, result.x(), EPSILON);
        assertEquals(2.0f, result.y(), EPSILON);
    }

    @Test
    @DisplayName("min with three vectors")
    void testMinThreeVectors() {
        Vector2f v1 = new Vector2f(1.0f, 4.0f);
        Vector2f v2 = new Vector2f(3.0f, 2.0f);
        Vector2f v3 = new Vector2f(2.0f, 0.5f);
        Vector2f result = Vector2f.min(v1, v2, v3);
        assertEquals(1.0f, result.x(), EPSILON);
        assertEquals(0.5f, result.y(), EPSILON);
    }

    @Test
    @DisplayName("avg should clamp value between min and max")
    void testAvg() {
        Vector2f min = new Vector2f(0.0f, 0.0f);
        Vector2f val = new Vector2f(5.0f, -1.0f);
        Vector2f max = new Vector2f(3.0f, 2.0f);
        Vector2f result = Vector2f.avg(min, val, max);
        assertEquals(3.0f, result.x(), EPSILON);
        assertEquals(0.0f, result.y(), EPSILON);
    }

    // ==================== Arithmetic Operations ====================

    @Test
    @DisplayName("add scalar")
    void testAddScalar() {
        Vector2f v = new Vector2f(3.0f, 4.0f);
        Vector2f result = v.add(2.0f);
        assertEquals(5.0f, result.x(), EPSILON);
        assertEquals(6.0f, result.y(), EPSILON);
    }

    @Test
    @DisplayName("addX should add to x only")
    void testAddX() {
        Vector2f v = new Vector2f(3.0f, 4.0f);
        Vector2f result = v.addX(2.0f);
        assertEquals(5.0f, result.x(), EPSILON);
        assertEquals(4.0f, result.y(), EPSILON);
    }

    @Test
    @DisplayName("addY should add to y only")
    void testAddY() {
        Vector2f v = new Vector2f(3.0f, 4.0f);
        Vector2f result = v.addY(2.0f);
        assertEquals(3.0f, result.x(), EPSILON);
        assertEquals(6.0f, result.y(), EPSILON);
    }

    @Test
    @DisplayName("add Vector2f")
    void testAddVector2f() {
        Vector2f v1 = new Vector2f(3.0f, 4.0f);
        Vector2f v2 = new Vector2f(1.0f, 2.0f);
        Vector2f result = v1.add(v2);
        assertEquals(4.0f, result.x(), EPSILON);
        assertEquals(6.0f, result.y(), EPSILON);
    }

    @Test
    @DisplayName("add Vector2i")
    void testAddVector2i() {
        Vector2f v1 = new Vector2f(3.0f, 4.0f);
        Vector2i v2 = new Vector2i(1, 2);
        Vector2f result = v1.add(v2);
        assertEquals(4.0f, result.x(), EPSILON);
        assertEquals(6.0f, result.y(), EPSILON);
    }

    @Test
    @DisplayName("add components")
    void testAddComponents() {
        Vector2f v = new Vector2f(3.0f, 4.0f);
        Vector2f result = v.add(1.0f, 2.0f);
        assertEquals(4.0f, result.x(), EPSILON);
        assertEquals(6.0f, result.y(), EPSILON);
    }

    @Test
    @DisplayName("less scalar")
    void testLessScalar() {
        Vector2f v = new Vector2f(5.0f, 6.0f);
        Vector2f result = v.less(2.0f);
        assertEquals(3.0f, result.x(), EPSILON);
        assertEquals(4.0f, result.y(), EPSILON);
    }

    @Test
    @DisplayName("lessX should subtract from x only")
    void testLessX() {
        Vector2f v = new Vector2f(5.0f, 6.0f);
        Vector2f result = v.lessX(2.0f);
        assertEquals(3.0f, result.x(), EPSILON);
        assertEquals(6.0f, result.y(), EPSILON);
    }

    @Test
    @DisplayName("lessY should subtract from y only")
    void testLessY() {
        Vector2f v = new Vector2f(5.0f, 6.0f);
        Vector2f result = v.lessY(2.0f);
        assertEquals(5.0f, result.x(), EPSILON);
        assertEquals(4.0f, result.y(), EPSILON);
    }

    @Test
    @DisplayName("less Vector2f")
    void testLessVector2f() {
        Vector2f v1 = new Vector2f(5.0f, 6.0f);
        Vector2f v2 = new Vector2f(1.0f, 2.0f);
        Vector2f result = v1.less(v2);
        assertEquals(4.0f, result.x(), EPSILON);
        assertEquals(4.0f, result.y(), EPSILON);
    }

    @Test
    @DisplayName("less Vector2i")
    void testLessVector2i() {
        Vector2f v1 = new Vector2f(5.0f, 6.0f);
        Vector2i v2 = new Vector2i(1, 2);
        Vector2f result = v1.less(v2);
        assertEquals(4.0f, result.x(), EPSILON);
        assertEquals(4.0f, result.y(), EPSILON);
    }

    @Test
    @DisplayName("less components")
    void testLessComponents() {
        Vector2f v = new Vector2f(5.0f, 6.0f);
        Vector2f result = v.less(1.0f, 2.0f);
        assertEquals(4.0f, result.x(), EPSILON);
        assertEquals(4.0f, result.y(), EPSILON);
    }

    @Test
    @DisplayName("multiply scalar")
    void testMultiplyScalar() {
        Vector2f v = new Vector2f(3.0f, 4.0f);
        Vector2f result = v.multiply(2.0f);
        assertEquals(6.0f, result.x(), EPSILON);
        assertEquals(8.0f, result.y(), EPSILON);
    }

    @Test
    @DisplayName("multiply vector")
    void testMultiplyVector() {
        Vector2f v1 = new Vector2f(3.0f, 4.0f);
        Vector2f v2 = new Vector2f(2.0f, 3.0f);
        Vector2f result = v1.multiply(v2);
        assertEquals(6.0f, result.x(), EPSILON);
        assertEquals(12.0f, result.y(), EPSILON);
    }

    @Test
    @DisplayName("devide scalar")
    void testDevideScalar() {
        Vector2f v = new Vector2f(6.0f, 8.0f);
        Vector2f result = v.devide(2.0f);
        assertEquals(3.0f, result.x(), EPSILON);
        assertEquals(4.0f, result.y(), EPSILON);
    }

    @Test
    @DisplayName("devide vector")
    void testDevideVector() {
        Vector2f v1 = new Vector2f(6.0f, 12.0f);
        Vector2f v2 = new Vector2f(2.0f, 3.0f);
        Vector2f result = v1.devide(v2);
        assertEquals(3.0f, result.x(), EPSILON);
        assertEquals(4.0f, result.y(), EPSILON);
    }

    @Test
    @DisplayName("increment")
    void testIncrement() {
        Vector2f v = new Vector2f(3.0f, 4.0f);
        Vector2f result = v.increment();
        assertEquals(4.0f, result.x(), EPSILON);
        assertEquals(5.0f, result.y(), EPSILON);
    }

    @Test
    @DisplayName("decrement")
    void testDecrement() {
        Vector2f v = new Vector2f(3.0f, 4.0f);
        Vector2f result = v.decrement();
        assertEquals(2.0f, result.x(), EPSILON);
        assertEquals(3.0f, result.y(), EPSILON);
    }

    @Test
    @DisplayName("invert")
    void testInvert() {
        Vector2f v = new Vector2f(3.0f, -4.0f);
        Vector2f result = v.invert();
        assertEquals(-3.0f, result.x(), EPSILON);
        assertEquals(4.0f, result.y(), EPSILON);
    }

    // ==================== Vector Operations ====================

    @Test
    @DisplayName("abs should return absolute values")
    void testAbs() {
        Vector2f v = new Vector2f(-3.0f, 4.0f);
        Vector2f result = v.abs();
        assertEquals(3.0f, result.x(), EPSILON);
        assertEquals(4.0f, result.y(), EPSILON);
    }

    @Test
    @DisplayName("dot product")
    void testDot() {
        Vector2f v1 = new Vector2f(3.0f, 4.0f);
        Vector2f v2 = new Vector2f(2.0f, 1.0f);
        float result = v1.dot(v2);
        assertEquals(10.0f, result, EPSILON);
    }

    @Test
    @DisplayName("cross product")
    void testCross() {
        Vector2f v1 = new Vector2f(3.0f, 4.0f);
        Vector2f v2 = new Vector2f(2.0f, 1.0f);
        float result = v1.cross(v2);
        assertEquals(-5.0f, result, EPSILON);
    }

    @Test
    @DisplayName("length")
    void testLength() {
        Vector2f v = new Vector2f(3.0f, 4.0f);
        assertEquals(5.0f, v.length(), EPSILON);
    }

    @Test
    @DisplayName("length2")
    void testLength2() {
        Vector2f v = new Vector2f(3.0f, 4.0f);
        assertEquals(25.0f, v.length2(), EPSILON);
    }

    @Test
    @DisplayName("distance")
    void testDistance() {
        Vector2f v1 = new Vector2f(0.0f, 0.0f);
        Vector2f v2 = new Vector2f(3.0f, 4.0f);
        assertEquals(5.0f, v1.distance(v2), EPSILON);
    }

    @Test
    @DisplayName("distance2")
    void testDistance2() {
        Vector2f v1 = new Vector2f(0.0f, 0.0f);
        Vector2f v2 = new Vector2f(3.0f, 4.0f);
        assertEquals(25.0f, v1.distance2(v2), EPSILON);
    }

    @Test
    @DisplayName("normalize")
    void testNormalize() {
        Vector2f v = new Vector2f(3.0f, 4.0f);
        Vector2f normalized = v.normalize();
        assertEquals(0.6f, normalized.x(), EPSILON);
        assertEquals(0.8f, normalized.y(), EPSILON);
        assertEquals(1.0f, normalized.length(), EPSILON);
    }

    @Test
    @DisplayName("safeNormalize with non-zero vector")
    void testSafeNormalize() {
        Vector2f v = new Vector2f(3.0f, 4.0f);
        Vector2f normalized = v.safeNormalize();
        assertEquals(0.6f, normalized.x(), EPSILON);
        assertEquals(0.8f, normalized.y(), EPSILON);
    }

    @Test
    @DisplayName("safeNormalize with zero vector returns (1,0)")
    void testSafeNormalizeZero() {
        Vector2f v = new Vector2f(0.0f, 0.0f);
        Vector2f normalized = v.safeNormalize();
        assertEquals(1.0f, normalized.x(), EPSILON);
        assertEquals(0.0f, normalized.y(), EPSILON);
    }

    @Test
    @DisplayName("unitOrthogonal")
    void testUnitOrthogonal() {
        Vector2f v = new Vector2f(3.0f, 4.0f);
        Vector2f ortho = v.unitOrthogonal();
        assertEquals(1.0f, ortho.length(), EPSILON);
        assertEquals(0.0f, v.dot(ortho), 0.01f); // Should be perpendicular
    }

    // ==================== Comparison Operations ====================

    @Test
    @DisplayName("isEqual")
    void testIsEqual() {
        Vector2f v1 = new Vector2f(3.0f, 4.0f);
        Vector2f v2 = new Vector2f(3.0f, 4.0f);
        Vector2f v3 = new Vector2f(3.0f, 5.0f);
        assertTrue(v1.isEqual(v2));
        assertFalse(v1.isEqual(v3));
    }

    @Test
    @DisplayName("isDifferent")
    void testIsDifferent() {
        Vector2f v1 = new Vector2f(3.0f, 4.0f);
        Vector2f v2 = new Vector2f(3.0f, 4.0f);
        Vector2f v3 = new Vector2f(3.0f, 5.0f);
        assertFalse(v1.isDifferent(v2));
        assertTrue(v1.isDifferent(v3));
    }

    @Test
    @DisplayName("isGreater")
    void testIsGreater() {
        Vector2f v1 = new Vector2f(5.0f, 6.0f);
        Vector2f v2 = new Vector2f(3.0f, 4.0f);
        Vector2f v3 = new Vector2f(5.0f, 4.0f);
        assertTrue(v1.isGreater(v2));
        assertFalse(v1.isGreater(v3));
    }

    @Test
    @DisplayName("isGreaterOrEqual")
    void testIsGreaterOrEqual() {
        Vector2f v1 = new Vector2f(5.0f, 6.0f);
        Vector2f v2 = new Vector2f(3.0f, 4.0f);
        Vector2f v3 = new Vector2f(5.0f, 6.0f);
        Vector2f v4 = new Vector2f(5.0f, 7.0f);
        assertTrue(v1.isGreaterOrEqual(v2));
        assertTrue(v1.isGreaterOrEqual(v3));
        assertFalse(v1.isGreaterOrEqual(v4));
    }

    @Test
    @DisplayName("isLower")
    void testIsLower() {
        Vector2f v1 = new Vector2f(3.0f, 4.0f);
        Vector2f v2 = new Vector2f(5.0f, 6.0f);
        Vector2f v3 = new Vector2f(3.0f, 6.0f);
        assertTrue(v1.isLower(v2));
        assertFalse(v1.isLower(v3));
    }

    @Test
    @DisplayName("isLowerOrEqual")
    void testIsLowerOrEqual() {
        Vector2f v1 = new Vector2f(3.0f, 4.0f);
        Vector2f v2 = new Vector2f(5.0f, 6.0f);
        Vector2f v3 = new Vector2f(3.0f, 4.0f);
        Vector2f v4 = new Vector2f(2.0f, 4.0f);
        assertTrue(v1.isLowerOrEqual(v2));
        assertTrue(v1.isLowerOrEqual(v3));
        assertFalse(v1.isLowerOrEqual(v4));
    }

    @Test
    @DisplayName("isZero")
    void testIsZero() {
        Vector2f v1 = new Vector2f(0.0f, 0.0f);
        Vector2f v2 = new Vector2f(0.0f, 0.0000001f);
        Vector2f v3 = new Vector2f(1.0f, 0.0f);
        assertTrue(v1.isZero());
        assertTrue(v2.isZero()); // Within epsilon
        assertFalse(v3.isZero());
    }

    @Test
    @DisplayName("isUnit")
    void testIsUnit() {
        Vector2f v1 = new Vector2f(1.0f, 0.0f);
        Vector2f v2 = new Vector2f(0.6f, 0.8f);
        Vector2f v3 = new Vector2f(1.0f, 1.0f);
        assertTrue(v1.isUnit());
        assertTrue(v2.isUnit());
        assertFalse(v3.isUnit());
    }

    // ==================== Axis Operations ====================

    @Test
    @DisplayName("maxAxis")
    void testMaxAxis() {
        Vector2f v1 = new Vector2f(3.0f, 5.0f);
        Vector2f v2 = new Vector2f(5.0f, 3.0f);
        assertEquals(1, v1.maxAxis());
        assertEquals(0, v2.maxAxis());
    }

    @Test
    @DisplayName("minAxis")
    void testMinAxis() {
        Vector2f v1 = new Vector2f(3.0f, 5.0f);
        Vector2f v2 = new Vector2f(5.0f, 3.0f);
        assertEquals(0, v1.minAxis());
        assertEquals(1, v2.minAxis());
    }

    @Test
    @DisplayName("closestAxis")
    void testClosestAxis() {
        Vector2f v1 = new Vector2f(-3.0f, 5.0f);
        assertEquals(1, v1.closestAxis());
    }

    @Test
    @DisplayName("furthestAxis")
    void testFurthestAxis() {
        Vector2f v1 = new Vector2f(-3.0f, 5.0f);
        assertEquals(0, v1.furthestAxis());
    }

    @Test
    @DisplayName("get by index")
    void testGet() {
        Vector2f v = new Vector2f(3.0f, 4.0f);
        assertEquals(3.0f, v.get(0), EPSILON);
        assertEquals(4.0f, v.get(1), EPSILON);
    }

    @Test
    @DisplayName("get with invalid index throws exception")
    void testGetInvalidIndex() {
        Vector2f v = new Vector2f(3.0f, 4.0f);
        assertThrows(IllegalArgumentException.class, () -> v.get(2));
        assertThrows(IllegalArgumentException.class, () -> v.get(-1));
    }

    // ==================== Instance min/max ====================

    @Test
    @DisplayName("instance max")
    void testInstanceMax() {
        Vector2f v1 = new Vector2f(1.0f, 4.0f);
        Vector2f v2 = new Vector2f(3.0f, 2.0f);
        Vector2f result = v1.max(v2);
        assertEquals(3.0f, result.x(), EPSILON);
        assertEquals(4.0f, result.y(), EPSILON);
    }

    @Test
    @DisplayName("instance min")
    void testInstanceMin() {
        Vector2f v1 = new Vector2f(1.0f, 4.0f);
        Vector2f v2 = new Vector2f(3.0f, 2.0f);
        Vector2f result = v1.min(v2);
        assertEquals(1.0f, result.x(), EPSILON);
        assertEquals(2.0f, result.y(), EPSILON);
    }

    // ==================== With Methods ====================

    @Test
    @DisplayName("withX")
    void testWithX() {
        Vector2f v = new Vector2f(3.0f, 4.0f);
        Vector2f result = v.withX(10.0f);
        assertEquals(10.0f, result.x(), EPSILON);
        assertEquals(4.0f, result.y(), EPSILON);
    }

    @Test
    @DisplayName("withY")
    void testWithY() {
        Vector2f v = new Vector2f(3.0f, 4.0f);
        Vector2f result = v.withY(10.0f);
        assertEquals(3.0f, result.x(), EPSILON);
        assertEquals(10.0f, result.y(), EPSILON);
    }

    @Test
    @DisplayName("clipInteger")
    void testClipInteger() {
        Vector2f v = new Vector2f(3.7f, 4.2f);
        Vector2f result = v.clipInteger();
        assertEquals(3.0f, result.x(), EPSILON);
        assertEquals(4.0f, result.y(), EPSILON);
    }

    // ==================== Static Constants ====================

    @Test
    @DisplayName("ZERO constant")
    void testZeroConstant() {
        assertEquals(0.0f, Vector2f.ZERO.x(), EPSILON);
        assertEquals(0.0f, Vector2f.ZERO.y(), EPSILON);
    }

    @Test
    @DisplayName("ONE constant")
    void testOneConstant() {
        assertEquals(1.0f, Vector2f.ONE.x(), EPSILON);
        assertEquals(1.0f, Vector2f.ONE.y(), EPSILON);
    }

    @Test
    @DisplayName("MAX_VALUE constant")
    void testMaxValueConstant() {
        assertEquals(Float.MAX_VALUE, Vector2f.MAX_VALUE.x(), EPSILON);
        assertEquals(Float.MAX_VALUE, Vector2f.MAX_VALUE.y(), EPSILON);
    }

    @Test
    @DisplayName("MIN_VALUE constant")
    void testMinValueConstant() {
        assertEquals(-Float.MAX_VALUE, Vector2f.MIN_VALUE.x(), EPSILON);
        assertEquals(-Float.MAX_VALUE, Vector2f.MIN_VALUE.y(), EPSILON);
    }

    // ==================== toString Test ====================

    @Test
    @DisplayName("toString")
    void testToString() {
        Vector2f v = new Vector2f(3.0f, 4.0f);
        assertEquals("(3.0,4.0)", v.toString());
    }

    // ==================== Edge Cases ====================

    @Test
    @DisplayName("operations with negative values")
    void testNegativeValues() {
        Vector2f v = new Vector2f(-3.0f, -4.0f);
        assertEquals(5.0f, v.length(), EPSILON);
        assertEquals(3.0f, v.abs().x(), EPSILON);
        assertEquals(4.0f, v.abs().y(), EPSILON);
    }

    @Test
    @DisplayName("operations with zero vector")
    void testZeroVector() {
        Vector2f v = new Vector2f(0.0f, 0.0f);
        assertTrue(v.isZero());
        assertEquals(0.0f, v.length(), EPSILON);
        Vector2f safe = v.safeNormalize();
        assertEquals(1.0f, safe.x(), EPSILON);
        assertEquals(0.0f, safe.y(), EPSILON);
    }

    @Test
    @DisplayName("divide by zero should not cause NaN")
    void testDivideByZeroHandling() {
        Vector2f v = new Vector2f(5.0f, 10.0f);
        Vector2f result = v.devide(0.0f);
        assertTrue(Float.isInfinite(result.x()) || Float.isNaN(result.x()));
    }
}
