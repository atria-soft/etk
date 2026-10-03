package org.atriasoft.etk.math;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Vector4f Tests")
class Vector4fTest {

    private static final float EPSILON = 0.0001f;

    @Test
    @DisplayName("Constructor with four parameters")
    void testConstructorFourParams() {
        Vector4f v = new Vector4f(3.0f, 4.0f, 5.0f, 6.0f);
        assertEquals(3.0f, v.x(), EPSILON);
        assertEquals(4.0f, v.y(), EPSILON);
        assertEquals(5.0f, v.z(), EPSILON);
        assertEquals(6.0f, v.w(), EPSILON);
    }

    @Test
    @DisplayName("Constructor with single parameter")
    void testConstructorSingleParam() {
        Vector4f v = new Vector4f(5.0f);
        assertEquals(5.0f, v.x(), EPSILON);
        assertEquals(5.0f, v.y(), EPSILON);
        assertEquals(5.0f, v.z(), EPSILON);
        assertEquals(5.0f, v.w(), EPSILON);
    }

    @Test
    @DisplayName("valueOf should parse single value")
    void testValueOfSingleValue() {
        Vector4f v = Vector4f.valueOf("5.0");
        assertEquals(5.0f, v.x(), EPSILON);
        assertEquals(5.0f, v.y(), EPSILON);
        assertEquals(5.0f, v.z(), EPSILON);
        assertEquals(5.0f, v.w(), EPSILON);
    }

    @Test
    @DisplayName("valueOf should parse four values")
    void testValueOfFourValues() {
        Vector4f v = Vector4f.valueOf("3.0,4.0,5.0,6.0");
        assertEquals(3.0f, v.x(), EPSILON);
        assertEquals(4.0f, v.y(), EPSILON);
        assertEquals(5.0f, v.z(), EPSILON);
        assertEquals(6.0f, v.w(), EPSILON);
    }

    @Test
    @DisplayName("valueOf with four separate strings")
    void testValueOfFourStrings() {
        Vector4f v = Vector4f.valueOf("3.0", "4.0", "5.0", "6.0");
        assertEquals(3.0f, v.x(), EPSILON);
        assertEquals(4.0f, v.y(), EPSILON);
        assertEquals(5.0f, v.z(), EPSILON);
        assertEquals(6.0f, v.w(), EPSILON);
    }

    @Test
    @DisplayName("add scalar")
    void testAddScalar() {
        Vector4f v = new Vector4f(3.0f, 4.0f, 5.0f, 6.0f);
        Vector4f result = v.add(2.0f);
        assertEquals(5.0f, result.x(), EPSILON);
        assertEquals(6.0f, result.y(), EPSILON);
        assertEquals(7.0f, result.z(), EPSILON);
        assertEquals(8.0f, result.w(), EPSILON);
    }

    @Test
    @DisplayName("less scalar")
    void testLessScalar() {
        Vector4f v = new Vector4f(5.0f, 6.0f, 7.0f, 8.0f);
        Vector4f result = v.less(2.0f);
        assertEquals(3.0f, result.x(), EPSILON);
        assertEquals(4.0f, result.y(), EPSILON);
        assertEquals(5.0f, result.z(), EPSILON);
        assertEquals(6.0f, result.w(), EPSILON);
    }

    @Test
    @DisplayName("multiply scalar")
    void testMultiplyScalar() {
        Vector4f v = new Vector4f(3.0f, 4.0f, 5.0f, 6.0f);
        Vector4f result = v.multiply(2.0f);
        assertEquals(6.0f, result.x(), EPSILON);
        assertEquals(8.0f, result.y(), EPSILON);
        assertEquals(10.0f, result.z(), EPSILON);
        assertEquals(12.0f, result.w(), EPSILON);
    }

    @Test
    @DisplayName("divide scalar")
    void testDivideScalar() {
        Vector4f v = new Vector4f(6.0f, 8.0f, 10.0f, 12.0f);
        Vector4f result = v.divide(2.0f);
        assertEquals(3.0f, result.x(), EPSILON);
        assertEquals(4.0f, result.y(), EPSILON);
        assertEquals(5.0f, result.z(), EPSILON);
        assertEquals(6.0f, result.w(), EPSILON);
    }

    @Test
    @DisplayName("add vector")
    void testAddVector() {
        Vector4f v1 = new Vector4f(1.0f, 2.0f, 3.0f, 4.0f);
        Vector4f v2 = new Vector4f(5.0f, 6.0f, 7.0f, 8.0f);
        Vector4f result = v1.add(v2);
        assertEquals(6.0f, result.x(), EPSILON);
        assertEquals(8.0f, result.y(), EPSILON);
        assertEquals(10.0f, result.z(), EPSILON);
        assertEquals(12.0f, result.w(), EPSILON);
    }

    @Test
    @DisplayName("less vector")
    void testLessVector() {
        Vector4f v1 = new Vector4f(5.0f, 6.0f, 7.0f, 8.0f);
        Vector4f v2 = new Vector4f(1.0f, 2.0f, 3.0f, 4.0f);
        Vector4f result = v1.less(v2);
        assertEquals(4.0f, result.x(), EPSILON);
        assertEquals(4.0f, result.y(), EPSILON);
        assertEquals(4.0f, result.z(), EPSILON);
        assertEquals(4.0f, result.w(), EPSILON);
    }

    @Test
    @DisplayName("multiply vector")
    void testMultiplyVector() {
        Vector4f v1 = new Vector4f(2.0f, 3.0f, 4.0f, 5.0f);
        Vector4f v2 = new Vector4f(2.0f, 3.0f, 4.0f, 5.0f);
        Vector4f result = v1.multiply(v2);
        assertEquals(4.0f, result.x(), EPSILON);
        assertEquals(9.0f, result.y(), EPSILON);
        assertEquals(16.0f, result.z(), EPSILON);
        assertEquals(25.0f, result.w(), EPSILON);
    }

    @Test
    @DisplayName("divide vector")
    void testDivideVector() {
        Vector4f v1 = new Vector4f(10.0f, 20.0f, 30.0f, 40.0f);
        Vector4f v2 = new Vector4f(2.0f, 4.0f, 6.0f, 8.0f);
        Vector4f result = v1.divide(v2);
        assertEquals(5.0f, result.x(), EPSILON);
        assertEquals(5.0f, result.y(), EPSILON);
        assertEquals(5.0f, result.z(), EPSILON);
        assertEquals(5.0f, result.w(), EPSILON);
    }

    @Test
    @DisplayName("divide by zero throws exception")
    void testDivideByZero() {
        Vector4f v = new Vector4f(6.0f, 8.0f, 10.0f, 12.0f);
        assertThrows(IllegalArgumentException.class, () -> v.divide(0.0f));
    }

    @Test
    @DisplayName("abs should return absolute values")
    void testAbs() {
        Vector4f v = new Vector4f(-3.0f, 4.0f, -5.0f, 6.0f);
        Vector4f result = v.abs();
        assertEquals(3.0f, result.x(), EPSILON);
        assertEquals(4.0f, result.y(), EPSILON);
        assertEquals(5.0f, result.z(), EPSILON);
        assertEquals(6.0f, result.w(), EPSILON);
    }

    @Test
    @DisplayName("invert")
    void testInvert() {
        Vector4f v = new Vector4f(3.0f, -4.0f, 5.0f, -6.0f);
        Vector4f result = v.invert();
        assertEquals(-3.0f, result.x(), EPSILON);
        assertEquals(4.0f, result.y(), EPSILON);
        assertEquals(-5.0f, result.z(), EPSILON);
        assertEquals(6.0f, result.w(), EPSILON);
    }

    @Test
    @DisplayName("dot product")
    void testDot() {
        Vector4f v1 = new Vector4f(1.0f, 2.0f, 3.0f, 4.0f);
        Vector4f v2 = new Vector4f(5.0f, 6.0f, 7.0f, 8.0f);
        float result = v1.dot(v2);
        assertEquals(70.0f, result, EPSILON); // 1*5 + 2*6 + 3*7 + 4*8 = 70
    }

    @Test
    @DisplayName("length")
    void testLength() {
        Vector4f v = new Vector4f(2.0f, 0.0f, 0.0f, 0.0f);
        assertEquals(2.0f, v.length(), EPSILON);
    }

    @Test
    @DisplayName("length2")
    void testLength2() {
        Vector4f v = new Vector4f(2.0f, 0.0f, 0.0f, 0.0f);
        assertEquals(4.0f, v.length2(), EPSILON);
    }

    @Test
    @DisplayName("distance")
    void testDistance() {
        Vector4f v1 = new Vector4f(0.0f, 0.0f, 0.0f, 0.0f);
        Vector4f v2 = new Vector4f(2.0f, 0.0f, 0.0f, 0.0f);
        assertEquals(2.0f, v1.distance(v2), EPSILON);
    }

    @Test
    @DisplayName("normalize")
    void testNormalize() {
        Vector4f v = new Vector4f(2.0f, 0.0f, 0.0f, 0.0f);
        Vector4f normalized = v.normalize();
        assertEquals(1.0f, normalized.x(), EPSILON);
        assertEquals(0.0f, normalized.y(), EPSILON);
        assertEquals(1.0f, normalized.length(), EPSILON);
    }

    @Test
    @DisplayName("safeNormalize with zero vector")
    void testSafeNormalizeZero() {
        Vector4f v = new Vector4f(0.0f, 0.0f, 0.0f, 0.0f);
        Vector4f normalized = v.safeNormalize();
        assertEquals(1.0f, normalized.x(), EPSILON);
        assertEquals(0.0f, normalized.y(), EPSILON);
        assertEquals(0.0f, normalized.z(), EPSILON);
        assertEquals(0.0f, normalized.w(), EPSILON);
    }

    @Test
    @DisplayName("isEqual")
    void testIsEqual() {
        Vector4f v1 = new Vector4f(3.0f, 4.0f, 5.0f, 6.0f);
        Vector4f v2 = new Vector4f(3.0f, 4.0f, 5.0f, 6.0f);
        assertTrue(v1.isEqual(v2));
    }

    @Test
    @DisplayName("isDifferent")
    void testIsDifferent() {
        Vector4f v1 = new Vector4f(3.0f, 4.0f, 5.0f, 6.0f);
        Vector4f v2 = new Vector4f(3.0f, 4.0f, 5.0f, 7.0f);
        assertTrue(v1.isDifferent(v2));
    }

    @Test
    @DisplayName("isZero")
    void testIsZero() {
        Vector4f v1 = new Vector4f(0.0f, 0.0f, 0.0f, 0.0f);
        Vector4f v2 = new Vector4f(1.0f, 0.0f, 0.0f, 0.0f);
        assertTrue(v1.isZero());
        assertFalse(v2.isZero());
    }

    @Test
    @DisplayName("isUnit")
    void testIsUnit() {
        Vector4f v1 = new Vector4f(1.0f, 0.0f, 0.0f, 0.0f);
        Vector4f v2 = new Vector4f(1.0f, 1.0f, 0.0f, 0.0f);
        assertTrue(v1.isUnit());
        assertFalse(v2.isUnit());
    }

    @Test
    @DisplayName("get by index")
    void testGet() {
        Vector4f v = new Vector4f(3.0f, 4.0f, 5.0f, 6.0f);
        assertEquals(3.0f, v.get(0), EPSILON);
        assertEquals(4.0f, v.get(1), EPSILON);
        assertEquals(5.0f, v.get(2), EPSILON);
        assertEquals(6.0f, v.get(3), EPSILON);
        assertThrows(IllegalArgumentException.class, () -> v.get(4));
    }

    @Test
    @DisplayName("getMax")
    void testGetMax() {
        Vector4f v = new Vector4f(3.0f, 7.0f, 5.0f, 6.0f);
        assertEquals(7.0f, v.getMax(), EPSILON);
    }

    @Test
    @DisplayName("getMin")
    void testGetMin() {
        Vector4f v = new Vector4f(3.0f, 7.0f, 5.0f, 6.0f);
        assertEquals(3.0f, v.getMin(), EPSILON);
    }

    @Test
    @DisplayName("max with two vectors")
    void testMaxTwoVectors() {
        Vector4f v1 = new Vector4f(1.0f, 4.0f, 2.0f, 8.0f);
        Vector4f v2 = new Vector4f(3.0f, 2.0f, 5.0f, 6.0f);
        Vector4f result = Vector4f.max(v1, v2);
        assertEquals(3.0f, result.x(), EPSILON);
        assertEquals(4.0f, result.y(), EPSILON);
        assertEquals(5.0f, result.z(), EPSILON);
        assertEquals(8.0f, result.w(), EPSILON);
    }

    @Test
    @DisplayName("min with two vectors")
    void testMinTwoVectors() {
        Vector4f v1 = new Vector4f(1.0f, 4.0f, 2.0f, 8.0f);
        Vector4f v2 = new Vector4f(3.0f, 2.0f, 5.0f, 6.0f);
        Vector4f result = Vector4f.min(v1, v2);
        assertEquals(1.0f, result.x(), EPSILON);
        assertEquals(2.0f, result.y(), EPSILON);
        assertEquals(2.0f, result.z(), EPSILON);
        assertEquals(6.0f, result.w(), EPSILON);
    }

    @Test
    @DisplayName("withX/Y/Z/W methods")
    void testWithMethods() {
        Vector4f v = new Vector4f(3.0f, 4.0f, 5.0f, 6.0f);

        assertEquals(10.0f, v.withX(10.0f).x(), EPSILON);
        assertEquals(10.0f, v.withY(10.0f).y(), EPSILON);
        assertEquals(10.0f, v.withZ(10.0f).z(), EPSILON);
        assertEquals(10.0f, v.withW(10.0f).w(), EPSILON);
    }

    @Test
    @DisplayName("lerp")
    void testLerp() {
        Vector4f v1 = new Vector4f(0.0f, 0.0f, 0.0f, 0.0f);
        Vector4f v2 = new Vector4f(10.0f, 10.0f, 10.0f, 10.0f);
        Vector4f result = v1.lerp(v2, 0.5f);
        assertEquals(5.0f, result.x(), EPSILON);
        assertEquals(5.0f, result.y(), EPSILON);
        assertEquals(5.0f, result.z(), EPSILON);
        assertEquals(5.0f, result.w(), EPSILON);
    }

    @Test
    @DisplayName("ZERO constant")
    void testZeroConstant() {
        assertEquals(0.0f, Vector4f.ZERO.x(), EPSILON);
        assertEquals(0.0f, Vector4f.ZERO.y(), EPSILON);
        assertEquals(0.0f, Vector4f.ZERO.z(), EPSILON);
        assertEquals(0.0f, Vector4f.ZERO.w(), EPSILON);
    }

    @Test
    @DisplayName("ONE constant")
    void testOneConstant() {
        assertEquals(1.0f, Vector4f.ONE.x(), EPSILON);
        assertEquals(1.0f, Vector4f.ONE.y(), EPSILON);
        assertEquals(1.0f, Vector4f.ONE.z(), EPSILON);
        assertEquals(1.0f, Vector4f.ONE.w(), EPSILON);
    }

    @Test
    @DisplayName("toString")
    void testToString() {
        Vector4f v = new Vector4f(3.0f, 4.0f, 5.0f, 6.0f);
        String s = v.toString();
        assertTrue(s.contains("3.0"));
        assertTrue(s.contains("4.0"));
        assertTrue(s.contains("5.0"));
    }
}
