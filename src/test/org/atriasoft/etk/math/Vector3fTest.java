package org.atriasoft.etk.math;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Vector3f Tests")
class Vector3fTest {

    private static final float EPSILON = 0.0001f;

    // ==================== Constructor Tests ====================

    @Test
    @DisplayName("Constructor with three parameters")
    void testConstructorThreeParams() {
        Vector3f v = new Vector3f(3.0f, 4.0f, 5.0f);
        assertEquals(3.0f, v.x(), EPSILON);
        assertEquals(4.0f, v.y(), EPSILON);
        assertEquals(5.0f, v.z(), EPSILON);
    }

    @Test
    @DisplayName("Constructor with single parameter")
    void testConstructorSingleParam() {
        Vector3f v = new Vector3f(5.0f);
        assertEquals(5.0f, v.x(), EPSILON);
        assertEquals(5.0f, v.y(), EPSILON);
        assertEquals(5.0f, v.z(), EPSILON);
    }

    // ==================== valueOf Tests ====================

    @Test
    @DisplayName("valueOf should parse single value")
    void testValueOfSingleValue() {
        Vector3f v = Vector3f.valueOf("5.0");
        assertEquals(5.0f, v.x(), EPSILON);
        assertEquals(5.0f, v.y(), EPSILON);
        assertEquals(5.0f, v.z(), EPSILON);
    }

    @Test
    @DisplayName("valueOf should parse two values")
    void testValueOfTwoValues() {
        Vector3f v = Vector3f.valueOf("3.0,4.0");
        assertEquals(3.0f, v.x(), EPSILON);
        assertEquals(4.0f, v.y(), EPSILON);
        assertEquals(4.0f, v.z(), EPSILON);
    }

    @Test
    @DisplayName("valueOf should parse three values")
    void testValueOfThreeValues() {
        Vector3f v = Vector3f.valueOf("3.0,4.0,5.0");
        assertEquals(3.0f, v.x(), EPSILON);
        assertEquals(4.0f, v.y(), EPSILON);
        assertEquals(5.0f, v.z(), EPSILON);
    }

    @Test
    @DisplayName("valueOf with three separate strings")
    void testValueOfThreeStrings() {
        Vector3f v = Vector3f.valueOf("3.0", "4.0", "5.0");
        assertEquals(3.0f, v.x(), EPSILON);
        assertEquals(4.0f, v.y(), EPSILON);
        assertEquals(5.0f, v.z(), EPSILON);
    }

    @Test
    @DisplayName("clipInt should truncate to integers")
    void testClipInt() {
        Vector3f v = new Vector3f(3.7f, 4.2f, 5.8f);
        Vector3f clipped = Vector3f.clipInt(v);
        assertEquals(3.0f, clipped.x(), EPSILON);
        assertEquals(4.0f, clipped.y(), EPSILON);
        assertEquals(5.0f, clipped.z(), EPSILON);
    }

    // ==================== Static Methods ====================

    @Test
    @DisplayName("max with two vectors")
    void testMaxTwoVectors() {
        Vector3f v1 = new Vector3f(1.0f, 4.0f, 2.0f);
        Vector3f v2 = new Vector3f(3.0f, 2.0f, 5.0f);
        Vector3f result = Vector3f.max(v1, v2);
        assertEquals(3.0f, result.x(), EPSILON);
        assertEquals(4.0f, result.y(), EPSILON);
        assertEquals(5.0f, result.z(), EPSILON);
    }

    @Test
    @DisplayName("min with two vectors")
    void testMinTwoVectors() {
        Vector3f v1 = new Vector3f(1.0f, 4.0f, 2.0f);
        Vector3f v2 = new Vector3f(3.0f, 2.0f, 5.0f);
        Vector3f result = Vector3f.min(v1, v2);
        assertEquals(1.0f, result.x(), EPSILON);
        assertEquals(2.0f, result.y(), EPSILON);
        assertEquals(2.0f, result.z(), EPSILON);
    }

    @Test
    @DisplayName("avg should clamp value")
    void testAvg() {
        Vector3f min = new Vector3f(0.0f, 0.0f, 0.0f);
        Vector3f val = new Vector3f(5.0f, -1.0f, 2.0f);
        Vector3f max = new Vector3f(3.0f, 2.0f, 10.0f);
        Vector3f result = Vector3f.avg(min, val, max);
        assertEquals(3.0f, result.x(), EPSILON);
        assertEquals(0.0f, result.y(), EPSILON);
        assertEquals(2.0f, result.z(), EPSILON);
    }

    @Test
    @DisplayName("length2 static method")
    void testLength2Static() {
        Vector3f start = new Vector3f(0.0f, 0.0f, 0.0f);
        Vector3f stop = new Vector3f(3.0f, 4.0f, 0.0f);
        float result = Vector3f.length2(start, stop);
        assertEquals(25.0f, result, EPSILON);
    }

    // ==================== Arithmetic Operations ====================

    @Test
    @DisplayName("add scalar")
    void testAddScalar() {
        Vector3f v = new Vector3f(3.0f, 4.0f, 5.0f);
        Vector3f result = v.add(2.0f);
        assertEquals(5.0f, result.x(), EPSILON);
        assertEquals(6.0f, result.y(), EPSILON);
        assertEquals(7.0f, result.z(), EPSILON);
    }

    @Test
    @DisplayName("add vector")
    void testAddVector() {
        Vector3f v1 = new Vector3f(3.0f, 4.0f, 5.0f);
        Vector3f v2 = new Vector3f(1.0f, 2.0f, 3.0f);
        Vector3f result = v1.add(v2);
        assertEquals(4.0f, result.x(), EPSILON);
        assertEquals(6.0f, result.y(), EPSILON);
        assertEquals(8.0f, result.z(), EPSILON);
    }

    @Test
    @DisplayName("add components")
    void testAddComponents() {
        Vector3f v = new Vector3f(3.0f, 4.0f, 5.0f);
        Vector3f result = v.add(1.0f, 2.0f, 3.0f);
        assertEquals(4.0f, result.x(), EPSILON);
        assertEquals(6.0f, result.y(), EPSILON);
        assertEquals(8.0f, result.z(), EPSILON);
    }

    @Test
    @DisplayName("less scalar")
    void testLessScalar() {
        Vector3f v = new Vector3f(5.0f, 6.0f, 7.0f);
        Vector3f result = v.less(2.0f);
        assertEquals(3.0f, result.x(), EPSILON);
        assertEquals(4.0f, result.y(), EPSILON);
        assertEquals(5.0f, result.z(), EPSILON);
    }

    @Test
    @DisplayName("less vector")
    void testLessVector() {
        Vector3f v1 = new Vector3f(5.0f, 6.0f, 7.0f);
        Vector3f v2 = new Vector3f(1.0f, 2.0f, 3.0f);
        Vector3f result = v1.less(v2);
        assertEquals(4.0f, result.x(), EPSILON);
        assertEquals(4.0f, result.y(), EPSILON);
        assertEquals(4.0f, result.z(), EPSILON);
    }

    @Test
    @DisplayName("multiply scalar")
    void testMultiplyScalar() {
        Vector3f v = new Vector3f(3.0f, 4.0f, 5.0f);
        Vector3f result = v.multiply(2.0f);
        assertEquals(6.0f, result.x(), EPSILON);
        assertEquals(8.0f, result.y(), EPSILON);
        assertEquals(10.0f, result.z(), EPSILON);
    }

    @Test
    @DisplayName("multiply vector")
    void testMultiplyVector() {
        Vector3f v1 = new Vector3f(3.0f, 4.0f, 5.0f);
        Vector3f v2 = new Vector3f(2.0f, 3.0f, 4.0f);
        Vector3f result = v1.multiply(v2);
        assertEquals(6.0f, result.x(), EPSILON);
        assertEquals(12.0f, result.y(), EPSILON);
        assertEquals(20.0f, result.z(), EPSILON);
    }

    @Test
    @DisplayName("divide scalar")
    void testDivideScalar() {
        Vector3f v = new Vector3f(6.0f, 8.0f, 10.0f);
        Vector3f result = v.divide(2.0f);
        assertEquals(3.0f, result.x(), EPSILON);
        assertEquals(4.0f, result.y(), EPSILON);
        assertEquals(5.0f, result.z(), EPSILON);
    }

    @Test
    @DisplayName("divide by zero throws exception")
    void testDivideByZero() {
        Vector3f v = new Vector3f(6.0f, 8.0f, 10.0f);
        assertThrows(IllegalArgumentException.class, () -> v.divide(0.0f));
    }

    @Test
    @DisplayName("divide vector")
    void testDivideVector() {
        Vector3f v1 = new Vector3f(6.0f, 12.0f, 20.0f);
        Vector3f v2 = new Vector3f(2.0f, 3.0f, 4.0f);
        Vector3f result = v1.divide(v2);
        assertEquals(3.0f, result.x(), EPSILON);
        assertEquals(4.0f, result.y(), EPSILON);
        assertEquals(5.0f, result.z(), EPSILON);
    }

    @Test
    @DisplayName("invert")
    void testInvert() {
        Vector3f v = new Vector3f(3.0f, -4.0f, 5.0f);
        Vector3f result = v.invert();
        assertEquals(-3.0f, result.x(), EPSILON);
        assertEquals(4.0f, result.y(), EPSILON);
        assertEquals(-5.0f, result.z(), EPSILON);
    }

    // ==================== Vector Operations ====================

    @Test
    @DisplayName("abs")
    void testAbs() {
        Vector3f v = new Vector3f(-3.0f, 4.0f, -5.0f);
        Vector3f result = v.abs();
        assertEquals(3.0f, result.x(), EPSILON);
        assertEquals(4.0f, result.y(), EPSILON);
        assertEquals(5.0f, result.z(), EPSILON);
    }

    @Test
    @DisplayName("dot product")
    void testDot() {
        Vector3f v1 = new Vector3f(1.0f, 2.0f, 3.0f);
        Vector3f v2 = new Vector3f(4.0f, 5.0f, 6.0f);
        float result = v1.dot(v2);
        assertEquals(32.0f, result, EPSILON); // 1*4 + 2*5 + 3*6 = 32
    }

    @Test
    @DisplayName("cross product")
    void testCross() {
        Vector3f v1 = new Vector3f(1.0f, 0.0f, 0.0f);
        Vector3f v2 = new Vector3f(0.0f, 1.0f, 0.0f);
        Vector3f result = v1.cross(v2);
        assertEquals(0.0f, result.x(), EPSILON);
        assertEquals(0.0f, result.y(), EPSILON);
        assertEquals(1.0f, result.z(), EPSILON);
    }

    @Test
    @DisplayName("length")
    void testLength() {
        Vector3f v = new Vector3f(3.0f, 4.0f, 0.0f);
        assertEquals(5.0f, v.length(), EPSILON);
    }

    @Test
    @DisplayName("length2")
    void testLength2() {
        Vector3f v = new Vector3f(3.0f, 4.0f, 0.0f);
        assertEquals(25.0f, v.length2(), EPSILON);
    }

    @Test
    @DisplayName("distance")
    void testDistance() {
        Vector3f v1 = new Vector3f(0.0f, 0.0f, 0.0f);
        Vector3f v2 = new Vector3f(3.0f, 4.0f, 0.0f);
        assertEquals(5.0f, v1.distance(v2), EPSILON);
    }

    @Test
    @DisplayName("distance2")
    void testDistance2() {
        Vector3f v1 = new Vector3f(0.0f, 0.0f, 0.0f);
        Vector3f v2 = new Vector3f(3.0f, 4.0f, 0.0f);
        assertEquals(25.0f, v1.distance2(v2), EPSILON);
    }

    @Test
    @DisplayName("normalize")
    void testNormalize() {
        Vector3f v = new Vector3f(3.0f, 4.0f, 0.0f);
        Vector3f normalized = v.normalize();
        assertEquals(0.6f, normalized.x(), EPSILON);
        assertEquals(0.8f, normalized.y(), EPSILON);
        assertEquals(0.0f, normalized.z(), EPSILON);
        assertEquals(1.0f, normalized.length(), EPSILON);
    }

    @Test
    @DisplayName("safeNormalize with non-zero vector")
    void testSafeNormalize() {
        Vector3f v = new Vector3f(3.0f, 4.0f, 0.0f);
        Vector3f normalized = v.safeNormalize();
        assertEquals(0.6f, normalized.x(), EPSILON);
        assertEquals(0.8f, normalized.y(), EPSILON);
        assertTrue(normalized.isUnit());
    }

    @Test
    @DisplayName("safeNormalize with zero vector")
    void testSafeNormalizeZero() {
        Vector3f v = new Vector3f(0.0f, 0.0f, 0.0f);
        Vector3f normalized = v.safeNormalize();
        assertEquals(1.0f, normalized.x(), EPSILON);
        assertEquals(0.0f, normalized.y(), EPSILON);
        assertEquals(0.0f, normalized.z(), EPSILON);
    }

    @Test
    @DisplayName("angle between vectors")
    void testAngle() {
        Vector3f v1 = new Vector3f(1.0f, 0.0f, 0.0f);
        Vector3f v2 = new Vector3f(0.0f, 1.0f, 0.0f);
        float angle = v1.angle(v2);
        assertEquals(Math.PI / 2, angle, 0.01f);
    }

    @Test
    @DisplayName("lerp")
    void testLerp() {
        Vector3f v1 = new Vector3f(0.0f, 0.0f, 0.0f);
        Vector3f v2 = new Vector3f(10.0f, 10.0f, 10.0f);
        Vector3f result = v1.lerp(v2, 0.5f);
        assertEquals(5.0f, result.x(), EPSILON);
        assertEquals(5.0f, result.y(), EPSILON);
        assertEquals(5.0f, result.z(), EPSILON);
    }

    @Test
    @DisplayName("clamp")
    void testClamp() {
        Vector3f v = new Vector3f(3.0f, 4.0f, 0.0f);
        Vector3f clamped = v.clamp(2.0f);
        assertTrue(clamped.length() <= 2.0f + EPSILON);
    }

    @Test
    @DisplayName("reflect")
    void testReflect() {
        Vector3f v = new Vector3f(1.0f, -1.0f, 0.0f);
        Vector3f normal = new Vector3f(0.0f, 1.0f, 0.0f);
        Vector3f reflected = v.reflect(normal);
        assertEquals(1.0f, reflected.x(), EPSILON);
        assertEquals(1.0f, reflected.y(), EPSILON);
        assertEquals(0.0f, reflected.z(), EPSILON);
    }

    @Test
    @DisplayName("rotateNew")
    void testRotateNew() {
        Vector3f v = new Vector3f(1.0f, 0.0f, 0.0f);
        Vector3f axis = new Vector3f(0.0f, 0.0f, 1.0f);
        Vector3f rotated = v.rotateNew(axis, (float) Math.PI / 2);
        assertEquals(0.0f, rotated.x(), 0.01f);
        assertEquals(1.0f, rotated.y(), 0.01f);
        assertEquals(0.0f, rotated.z(), 0.01f);
    }

    @Test
    @DisplayName("triple product")
    void testTriple() {
        Vector3f v1 = new Vector3f(1.0f, 0.0f, 0.0f);
        Vector3f v2 = new Vector3f(0.0f, 1.0f, 0.0f);
        Vector3f v3 = new Vector3f(0.0f, 0.0f, 1.0f);
        float result = v1.triple(v2, v3);
        assertEquals(1.0f, result, EPSILON);
    }

    // ==================== Comparison Operations ====================

    @Test
    @DisplayName("isEqual")
    void testIsEqual() {
        Vector3f v1 = new Vector3f(3.0f, 4.0f, 5.0f);
        Vector3f v2 = new Vector3f(3.0f, 4.0f, 5.0f);
        Vector3f v3 = new Vector3f(3.0f, 4.0f, 6.0f);
        assertTrue(v1.isEqual(v2));
        assertFalse(v1.isEqual(v3));
    }

    @Test
    @DisplayName("isDifferent")
    void testIsDifferent() {
        Vector3f v1 = new Vector3f(3.0f, 4.0f, 5.0f);
        Vector3f v2 = new Vector3f(3.0f, 4.0f, 5.0f);
        Vector3f v3 = new Vector3f(3.0f, 4.0f, 6.0f);
        assertFalse(v1.isDifferent(v2));
        assertTrue(v1.isDifferent(v3));
    }

    @Test
    @DisplayName("isZero")
    void testIsZero() {
        Vector3f v1 = new Vector3f(0.0f, 0.0f, 0.0f);
        Vector3f v2 = new Vector3f(1.0f, 0.0f, 0.0f);
        assertTrue(v1.isZero());
        assertFalse(v2.isZero());
    }

    @Test
    @DisplayName("isUnit")
    void testIsUnit() {
        Vector3f v1 = new Vector3f(1.0f, 0.0f, 0.0f);
        Vector3f v2 = new Vector3f(1.0f, 1.0f, 0.0f);
        assertTrue(v1.isUnit());
        assertFalse(v2.isUnit());
    }

    // ==================== Axis Operations ====================

    @Test
    @DisplayName("get by index")
    void testGet() {
        Vector3f v = new Vector3f(3.0f, 4.0f, 5.0f);
        assertEquals(3.0f, v.get(0), EPSILON);
        assertEquals(4.0f, v.get(1), EPSILON);
        assertEquals(5.0f, v.get(2), EPSILON);
        assertThrows(IllegalArgumentException.class, () -> v.get(3));
    }

    @Test
    @DisplayName("getMax")
    void testGetMax() {
        Vector3f v = new Vector3f(3.0f, 7.0f, 5.0f);
        assertEquals(7.0f, v.getMax(), EPSILON);
    }

    @Test
    @DisplayName("getMin")
    void testGetMin() {
        Vector3f v = new Vector3f(3.0f, 7.0f, 5.0f);
        assertEquals(3.0f, v.getMin(), EPSILON);
    }

    @Test
    @DisplayName("getMaxAxis")
    void testGetMaxAxis() {
        Vector3f v = new Vector3f(3.0f, 7.0f, 5.0f);
        assertEquals(1, v.getMaxAxis());
    }

    @Test
    @DisplayName("getMinAxis")
    void testGetMinAxis() {
        Vector3f v = new Vector3f(3.0f, 7.0f, 5.0f);
        assertEquals(0, v.getMinAxis());
    }

    @Test
    @DisplayName("maxAxis")
    void testMaxAxis() {
        Vector3f v = new Vector3f(3.0f, 7.0f, 5.0f);
        assertEquals(1, v.maxAxis());
    }

    @Test
    @DisplayName("minAxis")
    void testMinAxis() {
        Vector3f v = new Vector3f(3.0f, 7.0f, 5.0f);
        assertEquals(0, v.minAxis());
    }

    @Test
    @DisplayName("closestAxis")
    void testClosestAxis() {
        Vector3f v = new Vector3f(-3.0f, 2.0f, -5.0f);
        assertEquals(2, v.closestAxis());
    }

    @Test
    @DisplayName("furthestAxis")
    void testFurthestAxis() {
        Vector3f v = new Vector3f(-3.0f, 2.0f, -5.0f);
        assertEquals(1, v.furthestAxis());
    }

    @Test
    @DisplayName("getOrthoVector")
    void testGetOrthoVector() {
        Vector3f v = new Vector3f(1.0f, 0.0f, 0.0f);
        Vector3f ortho = v.getOrthoVector();
        assertEquals(0.0f, v.dot(ortho), EPSILON);
    }

    @Test
    @DisplayName("getSkewSymmetricMatrix methods")
    void testGetSkewSymmetricMatrix() {
        Vector3f v = new Vector3f(1.0f, 2.0f, 3.0f);
        Vector3f m0 = v.getSkewSymmetricMatrix0();
        Vector3f m1 = v.getSkewSymmetricMatrix1();
        Vector3f m2 = v.getSkewSymmetricMatrix2();

        assertEquals(0.0f, m0.x(), EPSILON);
        assertEquals(-3.0f, m0.y(), EPSILON);
        assertEquals(2.0f, m0.z(), EPSILON);

        assertEquals(3.0f, m1.x(), EPSILON);
        assertEquals(0.0f, m1.y(), EPSILON);
        assertEquals(-1.0f, m1.z(), EPSILON);

        assertEquals(-2.0f, m2.x(), EPSILON);
        assertEquals(1.0f, m2.y(), EPSILON);
        assertEquals(0.0f, m2.z(), EPSILON);
    }

    // ==================== Instance min/max ====================

    @Test
    @DisplayName("instance max")
    void testInstanceMax() {
        Vector3f v1 = new Vector3f(1.0f, 4.0f, 2.0f);
        Vector3f v2 = new Vector3f(3.0f, 2.0f, 5.0f);
        Vector3f result = v1.max(v2);
        assertEquals(3.0f, result.x(), EPSILON);
        assertEquals(4.0f, result.y(), EPSILON);
        assertEquals(5.0f, result.z(), EPSILON);
    }

    @Test
    @DisplayName("instance min")
    void testInstanceMin() {
        Vector3f v1 = new Vector3f(1.0f, 4.0f, 2.0f);
        Vector3f v2 = new Vector3f(3.0f, 2.0f, 5.0f);
        Vector3f result = v1.min(v2);
        assertEquals(1.0f, result.x(), EPSILON);
        assertEquals(2.0f, result.y(), EPSILON);
        assertEquals(2.0f, result.z(), EPSILON);
    }

    // ==================== With Methods ====================

    @Test
    @DisplayName("withX")
    void testWithX() {
        Vector3f v = new Vector3f(3.0f, 4.0f, 5.0f);
        Vector3f result = v.withX(10.0f);
        assertEquals(10.0f, result.x(), EPSILON);
        assertEquals(4.0f, result.y(), EPSILON);
        assertEquals(5.0f, result.z(), EPSILON);
    }

    @Test
    @DisplayName("withY")
    void testWithY() {
        Vector3f v = new Vector3f(3.0f, 4.0f, 5.0f);
        Vector3f result = v.withY(10.0f);
        assertEquals(3.0f, result.x(), EPSILON);
        assertEquals(10.0f, result.y(), EPSILON);
        assertEquals(5.0f, result.z(), EPSILON);
    }

    @Test
    @DisplayName("withZ")
    void testWithZ() {
        Vector3f v = new Vector3f(3.0f, 4.0f, 5.0f);
        Vector3f result = v.withZ(10.0f);
        assertEquals(3.0f, result.x(), EPSILON);
        assertEquals(4.0f, result.y(), EPSILON);
        assertEquals(10.0f, result.z(), EPSILON);
    }

    @Test
    @DisplayName("clipInteger")
    void testClipInteger() {
        Vector3f v = new Vector3f(3.7f, 4.2f, 5.9f);
        Vector3f result = v.clipInteger();
        assertEquals(3.0f, result.x(), EPSILON);
        assertEquals(4.0f, result.y(), EPSILON);
        assertEquals(5.0f, result.z(), EPSILON);
    }

    @Test
    @DisplayName("setInterpolate3")
    void testSetInterpolate3() {
        Vector3f v = new Vector3f(0.0f, 0.0f, 0.0f);
        Vector3f v1 = new Vector3f(0.0f, 0.0f, 0.0f);
        Vector3f v2 = new Vector3f(10.0f, 10.0f, 10.0f);
        Vector3f result = v.setInterpolate3(v1, v2, 0.5f);
        assertEquals(5.0f, result.x(), EPSILON);
        assertEquals(5.0f, result.y(), EPSILON);
        assertEquals(5.0f, result.z(), EPSILON);
    }

    // ==================== Static Constants ====================

    @Test
    @DisplayName("ZERO constant")
    void testZeroConstant() {
        assertEquals(0.0f, Vector3f.ZERO.x(), EPSILON);
        assertEquals(0.0f, Vector3f.ZERO.y(), EPSILON);
        assertEquals(0.0f, Vector3f.ZERO.z(), EPSILON);
    }

    @Test
    @DisplayName("ONE constant")
    void testOneConstant() {
        assertEquals(1.0f, Vector3f.ONE.x(), EPSILON);
        assertEquals(1.0f, Vector3f.ONE.y(), EPSILON);
        assertEquals(1.0f, Vector3f.ONE.z(), EPSILON);
    }

    @Test
    @DisplayName("ONE_X constant")
    void testOneXConstant() {
        assertEquals(1.0f, Vector3f.ONE_X.x(), EPSILON);
        assertEquals(0.0f, Vector3f.ONE_X.y(), EPSILON);
        assertEquals(0.0f, Vector3f.ONE_X.z(), EPSILON);
    }

    @Test
    @DisplayName("ONE_Y constant")
    void testOneYConstant() {
        assertEquals(0.0f, Vector3f.ONE_Y.x(), EPSILON);
        assertEquals(1.0f, Vector3f.ONE_Y.y(), EPSILON);
        assertEquals(0.0f, Vector3f.ONE_Y.z(), EPSILON);
    }

    @Test
    @DisplayName("ONE_Z constant")
    void testOneZConstant() {
        assertEquals(0.0f, Vector3f.ONE_Z.x(), EPSILON);
        assertEquals(0.0f, Vector3f.ONE_Z.y(), EPSILON);
        assertEquals(1.0f, Vector3f.ONE_Z.z(), EPSILON);
    }

    // ==================== toString Test ====================

    @Test
    @DisplayName("toString")
    void testToString() {
        Vector3f v = new Vector3f(3.0f, 4.0f, 5.0f);
        assertTrue(v.toString().contains("3.0"));
        assertTrue(v.toString().contains("4.0"));
        assertTrue(v.toString().contains("5.0"));
    }
}
