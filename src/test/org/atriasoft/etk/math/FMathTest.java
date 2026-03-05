package org.atriasoft.etk.math;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FMath Tests")
class FMathTest {

    private static final float EPSILON = 0.0001f;

    // ==================== Basic Math Functions ====================

    @Test
    @DisplayName("abs should return absolute value")
    void testAbs() {
        assertEquals(5.0f, FMath.abs(5.0f), EPSILON);
        assertEquals(5.0f, FMath.abs(-5.0f), EPSILON);
        assertEquals(0.0f, FMath.abs(0.0f), EPSILON);
    }

    @Test
    @DisplayName("sin should compute sine")
    void testSin() {
        assertEquals(0.0f, FMath.sin(0.0f), EPSILON);
        assertEquals(1.0f, FMath.sin(FMath.PI / 2), EPSILON);
        assertEquals(0.0f, FMath.sin(FMath.PI), 0.001f);
    }

    @Test
    @DisplayName("cos should compute cosine")
    void testCos() {
        assertEquals(1.0f, FMath.cos(0.0f), EPSILON);
        assertEquals(0.0f, FMath.cos(FMath.PI / 2), 0.001f);
        assertEquals(-1.0f, FMath.cos(FMath.PI), EPSILON);
    }

    @Test
    @DisplayName("tan should compute tangent")
    void testTan() {
        assertEquals(0.0f, FMath.tan(0.0f), EPSILON);
        assertEquals(1.0f, FMath.tan(FMath.PI / 4), 0.001f);
    }

    @Test
    @DisplayName("asin should compute arc sine")
    void testAsin() {
        assertEquals(0.0f, FMath.asin(0.0f), EPSILON);
        assertEquals(FMath.PI / 2, FMath.asin(1.0f), EPSILON);
    }

    @Test
    @DisplayName("acos should compute arc cosine")
    void testAcos() {
        assertEquals(FMath.PI / 2, FMath.acos(0.0f), EPSILON);
        assertEquals(0.0f, FMath.acos(1.0f), EPSILON);
    }

    @Test
    @DisplayName("atan should compute arc tangent")
    void testAtan() {
        assertEquals(0.0f, FMath.atan(0.0f), EPSILON);
        assertEquals(FMath.PI / 4, FMath.atan(1.0f), 0.001f);
    }

    @Test
    @DisplayName("atan2 should compute arc tangent of y/x")
    void testAtan2() {
        assertEquals(FMath.PI / 4, FMath.atan2(1.0f, 1.0f), 0.001f);
        assertEquals(FMath.PI / 2, FMath.atan2(1.0f, 0.0f), 0.001f);
    }

    @Test
    @DisplayName("sqrt should compute square root")
    void testSqrt() {
        assertEquals(0.0f, FMath.sqrt(0.0f), EPSILON);
        assertEquals(1.0f, FMath.sqrt(1.0f), EPSILON);
        assertEquals(2.0f, FMath.sqrt(4.0f), EPSILON);
        assertEquals(3.0f, FMath.sqrt(9.0f), EPSILON);
    }

    @Test
    @DisplayName("pow should compute power")
    void testPow() {
        assertEquals(1.0f, FMath.pow(2.0f, 0.0f), EPSILON);
        assertEquals(2.0f, FMath.pow(2.0f, 1.0f), EPSILON);
        assertEquals(4.0f, FMath.pow(2.0f, 2.0f), EPSILON);
        assertEquals(8.0f, FMath.pow(2.0f, 3.0f), EPSILON);
    }

    @Test
    @DisplayName("mod should compute modulo")
    void testMod() {
        assertEquals(1.0f, FMath.mod(5.0f, 2.0f), EPSILON);
        assertEquals(0.5f, FMath.mod(2.5f, 1.0f), EPSILON);
    }

    @Test
    @DisplayName("floor should return floor value")
    void testFloor() {
        assertEquals(3, FMath.floor(3.7f));
        assertEquals(3, FMath.floor(3.0f));
        assertEquals(-4, FMath.floor(-3.2f));
    }

    // ==================== Min/Max Functions ====================

    @Test
    @DisplayName("min with two floats")
    void testMinTwoFloats() {
        assertEquals(1.0f, FMath.min(1.0f, 2.0f), EPSILON);
        assertEquals(1.0f, FMath.min(2.0f, 1.0f), EPSILON);
        assertEquals(1.5f, FMath.min(1.5f, 1.5f), EPSILON);
    }

    @Test
    @DisplayName("min with three floats")
    void testMinThreeFloats() {
        assertEquals(1.0f, FMath.min(1.0f, 2.0f, 3.0f), EPSILON);
        assertEquals(1.0f, FMath.min(3.0f, 1.0f, 2.0f), EPSILON);
        assertEquals(1.0f, FMath.min(2.0f, 3.0f, 1.0f), EPSILON);
    }

    @Test
    @DisplayName("min with four floats")
    void testMinFourFloats() {
        assertEquals(1.0f, FMath.min(1.0f, 2.0f, 3.0f, 4.0f), EPSILON);
        assertEquals(1.0f, FMath.min(4.0f, 1.0f, 2.0f, 3.0f), EPSILON);
    }

    @Test
    @DisplayName("min with two ints")
    void testMinTwoInts() {
        assertEquals(1, FMath.min(1, 2));
        assertEquals(1, FMath.min(2, 1));
    }

    @Test
    @DisplayName("min with two longs")
    void testMinTwoLongs() {
        assertEquals(1L, FMath.min(1L, 2L));
        assertEquals(1L, FMath.min(2L, 1L));
    }

    @Test
    @DisplayName("min with two Vector3f")
    void testMinTwoVector3f() {
        Vector3f v1 = new Vector3f(1.0f, 4.0f, 2.0f);
        Vector3f v2 = new Vector3f(3.0f, 2.0f, 5.0f);
        Vector3f result = FMath.min(v1, v2);
        assertEquals(1.0f, result.x(), EPSILON);
        assertEquals(2.0f, result.y(), EPSILON);
        assertEquals(2.0f, result.z(), EPSILON);
    }

    @Test
    @DisplayName("max with two floats")
    void testMaxTwoFloats() {
        assertEquals(2.0f, FMath.max(1.0f, 2.0f), EPSILON);
        assertEquals(2.0f, FMath.max(2.0f, 1.0f), EPSILON);
        assertEquals(1.5f, FMath.max(1.5f, 1.5f), EPSILON);
    }

    @Test
    @DisplayName("max with three floats")
    void testMaxThreeFloats() {
        assertEquals(3.0f, FMath.max(1.0f, 2.0f, 3.0f), EPSILON);
        assertEquals(3.0f, FMath.max(3.0f, 1.0f, 2.0f), EPSILON);
        assertEquals(3.0f, FMath.max(2.0f, 3.0f, 1.0f), EPSILON);
    }

    @Test
    @DisplayName("max with four floats")
    void testMaxFourFloats() {
        assertEquals(4.0f, FMath.max(1.0f, 2.0f, 3.0f, 4.0f), EPSILON);
        assertEquals(4.0f, FMath.max(4.0f, 1.0f, 2.0f, 3.0f), EPSILON);
    }

    @Test
    @DisplayName("max with two ints")
    void testMaxTwoInts() {
        assertEquals(2, FMath.max(1, 2));
        assertEquals(2, FMath.max(2, 1));
    }

    @Test
    @DisplayName("max with two longs")
    void testMaxTwoLongs() {
        assertEquals(2L, FMath.max(1L, 2L));
        assertEquals(2L, FMath.max(2L, 1L));
    }

    @Test
    @DisplayName("max with two Vector3f")
    void testMaxTwoVector3f() {
        Vector3f v1 = new Vector3f(1.0f, 4.0f, 2.0f);
        Vector3f v2 = new Vector3f(3.0f, 2.0f, 5.0f);
        Vector3f result = FMath.max(v1, v2);
        assertEquals(3.0f, result.x(), EPSILON);
        assertEquals(4.0f, result.y(), EPSILON);
        assertEquals(5.0f, result.z(), EPSILON);
    }

    // ==================== Clamp/Avg Functions ====================

    @Test
    @DisplayName("clamp float value")
    void testClampFloat() {
        assertEquals(5.0f, FMath.clamp(3.0f, 5.0f, 10.0f), EPSILON);
        assertEquals(7.0f, FMath.clamp(7.0f, 5.0f, 10.0f), EPSILON);
        assertEquals(10.0f, FMath.clamp(15.0f, 5.0f, 10.0f), EPSILON);
    }

    @Test
    @DisplayName("clamp int value")
    void testClampInt() {
        assertEquals(5, FMath.clamp(3, 5, 10));
        assertEquals(7, FMath.clamp(7, 5, 10));
        assertEquals(10, FMath.clamp(15, 5, 10));
    }

    @Test
    @DisplayName("clamp long value")
    void testClampLong() {
        assertEquals(5L, FMath.clamp(3L, 5L, 10L));
        assertEquals(7L, FMath.clamp(7L, 5L, 10L));
        assertEquals(10L, FMath.clamp(15L, 5L, 10L));
    }

    @Test
    @DisplayName("avg float value")
    void testAvgFloat() {
        assertEquals(5.0f, FMath.avg(5.0f, 3.0f, 10.0f), EPSILON);
        assertEquals(7.0f, FMath.avg(5.0f, 7.0f, 10.0f), EPSILON);
        assertEquals(10.0f, FMath.avg(5.0f, 15.0f, 10.0f), EPSILON);
    }

    @Test
    @DisplayName("avg int value")
    void testAvgInt() {
        assertEquals(5, FMath.avg(5, 3, 10));
        assertEquals(7, FMath.avg(5, 7, 10));
        assertEquals(10, FMath.avg(5, 15, 10));
    }

    // ==================== Comparison Functions ====================

    @Test
    @DisplayName("approxEqual with default epsilon")
    void testApproxEqualDefault() {
        assertTrue(FMath.approxEqual(1.0f, 1.0f));
        assertTrue(FMath.approxEqual(1.0f, 1.00000001f));
        assertFalse(FMath.approxEqual(1.0f, 1.1f));
    }

    @Test
    @DisplayName("approxEqual with custom epsilon")
    void testApproxEqualCustom() {
        assertTrue(FMath.approxEqual(1.0f, 1.0f, 0.01f));
        assertTrue(FMath.approxEqual(1.0f, 1.005f, 0.01f));
        assertFalse(FMath.approxEqual(1.0f, 1.02f, 0.01f));
    }

    @Test
    @DisplayName("sameSign should detect same signs")
    void testSameSign() {
        assertTrue(FMath.sameSign(5.0f, 3.0f));
        assertTrue(FMath.sameSign(-5.0f, -3.0f));
        assertTrue(FMath.sameSign(0.0f, 5.0f));
        assertFalse(FMath.sameSign(5.0f, -3.0f));
        assertFalse(FMath.sameSign(-5.0f, 3.0f));
    }

    // ==================== String/Array Functions ====================

    @Test
    @DisplayName("floatToString should format float")
    void testFloatToString() {
        String result = FMath.floatToString(3.14159f);
        assertTrue(result.contains("3."));
        assertTrue(result.contains("14"));
    }

    @Test
    @DisplayName("getTableFloat should parse float array")
    void testGetTableFloat() {
        float[] result = FMath.getTableFloat("1.0,2.0,3.0", ",", 3);
        assertNotNull(result);
        assertEquals(3, result.length);
        assertEquals(1.0f, result[0], EPSILON);
        assertEquals(2.0f, result[1], EPSILON);
        assertEquals(3.0f, result[2], EPSILON);
    }

    @Test
    @DisplayName("getTableFloat should return null for wrong count")
    void testGetTableFloatWrongCount() {
        float[] result = FMath.getTableFloat("1.0,2.0", ",", 3);
        assertNull(result);
    }

    @Test
    @DisplayName("getTableDouble should parse double array")
    void testGetTableDouble() {
        double[] result = FMath.getTableDouble("1.0,2.0,3.0", ",", 3);
        assertNotNull(result);
        assertEquals(3, result.length);
        assertEquals(1.0, result[0], EPSILON);
        assertEquals(2.0, result[1], EPSILON);
        assertEquals(3.0, result[2], EPSILON);
    }

    @Test
    @DisplayName("getTableDouble should return null for wrong count")
    void testGetTableDoubleWrongCount() {
        double[] result = FMath.getTableDouble("1.0,2.0", ",", 3);
        assertNull(result);
    }

    // ==================== Utility Functions ====================

    @Test
    @DisplayName("nextP2 should return next power of 2")
    void testNextP2() {
        assertEquals(1, FMath.nextP2(1));
        assertEquals(2, FMath.nextP2(2));
        assertEquals(4, FMath.nextP2(3));
        assertEquals(4, FMath.nextP2(4));
        assertEquals(8, FMath.nextP2(5));
        assertEquals(16, FMath.nextP2(15));
        assertEquals(16, FMath.nextP2(16));
        assertEquals(32, FMath.nextP2(17));
        assertEquals(1024, FMath.nextP2(1000));
    }

    @Test
    @DisplayName("PI constant")
    void testPiConstant() {
        assertEquals((float) Math.PI, FMath.PI, EPSILON);
    }

    // ==================== Edge Cases ====================

    @Test
    @DisplayName("operations with negative values")
    void testNegativeValues() {
        assertEquals(-5.0f, FMath.min(-5.0f, -3.0f), EPSILON);
        assertEquals(-3.0f, FMath.max(-5.0f, -3.0f), EPSILON);
        assertEquals(-5.0f, FMath.clamp(-10.0f, -5.0f, 5.0f), EPSILON);
    }

    @Test
    @DisplayName("operations with zero")
    void testZeroValues() {
        assertEquals(0.0f, FMath.min(0.0f, 1.0f), EPSILON);
        assertEquals(1.0f, FMath.max(0.0f, 1.0f), EPSILON);
        assertEquals(0.0f, FMath.abs(0.0f), EPSILON);
    }

    @Test
    @DisplayName("sqrt of zero")
    void testSqrtZero() {
        assertEquals(0.0f, FMath.sqrt(0.0f), EPSILON);
    }
}
