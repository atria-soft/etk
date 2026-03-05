package org.atriasoft.etk;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("BorderRadius Tests")
class BorderRadiusTest {

    private static final float EPSILON = 0.001f;

    // ==================== Constructor Tests ====================

    @Test
    @DisplayName("Constructor with single value")
    void testConstructorSingleValue() {
        BorderRadius br = new BorderRadius(5.0f);
        assertEquals(5.0f, br.topLeft(), EPSILON);
        assertEquals(5.0f, br.topRight(), EPSILON);
        assertEquals(5.0f, br.bottomRight(), EPSILON);
        assertEquals(5.0f, br.bottomLeft(), EPSILON);
    }

    @Test
    @DisplayName("Constructor with four values")
    void testConstructorFourValues() {
        BorderRadius br = new BorderRadius(1.0f, 2.0f, 3.0f, 4.0f);
        assertEquals(1.0f, br.topLeft(), EPSILON);
        assertEquals(2.0f, br.topRight(), EPSILON);
        assertEquals(3.0f, br.bottomRight(), EPSILON);
        assertEquals(4.0f, br.bottomLeft(), EPSILON);
    }

    @Test
    @DisplayName("Record canonical constructor")
    void testRecordConstructor() {
        BorderRadius br = new BorderRadius(10.0f, 20.0f, 30.0f, 40.0f);
        assertEquals(10.0f, br.topLeft(), EPSILON);
        assertEquals(20.0f, br.topRight(), EPSILON);
        assertEquals(30.0f, br.bottomRight(), EPSILON);
        assertEquals(40.0f, br.bottomLeft(), EPSILON);
    }

    // ==================== valueOf() Tests ====================

    @Test
    @DisplayName("valueOf with single value")
    void testValueOfSingleValue() {
        BorderRadius br = BorderRadius.valueOf("5.0");
        assertEquals(5.0f, br.topLeft(), EPSILON);
        assertEquals(5.0f, br.topRight(), EPSILON);
        assertEquals(5.0f, br.bottomRight(), EPSILON);
        assertEquals(5.0f, br.bottomLeft(), EPSILON);
    }

    @Test
    @DisplayName("valueOf with two values")
    void testValueOfTwoValues() {
        BorderRadius br = BorderRadius.valueOf("5.0 10.0");
        assertEquals(5.0f, br.topLeft(), EPSILON);
        assertEquals(10.0f, br.topRight(), EPSILON);
        assertEquals(10.0f, br.bottomRight(), EPSILON);
        assertEquals(10.0f, br.bottomLeft(), EPSILON);
    }

    @Test
    @DisplayName("valueOf with three values")
    void testValueOfThreeValues() {
        BorderRadius br = BorderRadius.valueOf("5.0 10.0 15.0");
        assertEquals(5.0f, br.topLeft(), EPSILON);
        assertEquals(10.0f, br.topRight(), EPSILON);
        assertEquals(15.0f, br.bottomRight(), EPSILON);
        assertEquals(15.0f, br.bottomLeft(), EPSILON);
    }

    @Test
    @DisplayName("valueOf with four values")
    void testValueOfFourValues() {
        BorderRadius br = BorderRadius.valueOf("5.0 10.0 15.0 20.0");
        assertEquals(5.0f, br.topLeft(), EPSILON);
        assertEquals(10.0f, br.topRight(), EPSILON);
        assertEquals(15.0f, br.bottomRight(), EPSILON);
        assertEquals(20.0f, br.bottomLeft(), EPSILON);
    }

    @Test
    @DisplayName("valueOf with leading parenthesis")
    void testValueOfWithParentheses() {
        BorderRadius br = BorderRadius.valueOf("(5.0 10.0 15.0 20.0");
        assertEquals(5.0f, br.topLeft(), EPSILON);
        assertEquals(10.0f, br.topRight(), EPSILON);
        assertEquals(15.0f, br.bottomRight(), EPSILON);
        assertEquals(20.0f, br.bottomLeft(), EPSILON);
    }

    @Test
    @DisplayName("valueOf with comma separator")
    void testValueOfWithComma() {
        BorderRadius br = BorderRadius.valueOf("5.0,10.0,15.0,20.0");
        assertEquals(5.0f, br.topLeft(), EPSILON);
        assertEquals(10.0f, br.topRight(), EPSILON);
        assertEquals(15.0f, br.bottomRight(), EPSILON);
        assertEquals(20.0f, br.bottomLeft(), EPSILON);
    }

    @Test
    @DisplayName("valueOf with four separate strings")
    void testValueOfFourStrings() {
        BorderRadius br = BorderRadius.valueOf("1.0", "2.0", "3.0", "4.0");
        assertEquals(1.0f, br.topLeft(), EPSILON);
        assertEquals(2.0f, br.topRight(), EPSILON);
        assertEquals(3.0f, br.bottomRight(), EPSILON);
        assertEquals(4.0f, br.bottomLeft(), EPSILON);
    }

    // ==================== Constants Tests ====================

    @Test
    @DisplayName("ZERO constant")
    void testZeroConstant() {
        assertEquals(0.0f, BorderRadius.ZERO.topLeft(), EPSILON);
        assertEquals(0.0f, BorderRadius.ZERO.topRight(), EPSILON);
        assertEquals(0.0f, BorderRadius.ZERO.bottomRight(), EPSILON);
        assertEquals(0.0f, BorderRadius.ZERO.bottomLeft(), EPSILON);
    }

    @Test
    @DisplayName("ONE constant")
    void testOneConstant() {
        assertEquals(1.0f, BorderRadius.ONE.topLeft(), EPSILON);
        assertEquals(1.0f, BorderRadius.ONE.topRight(), EPSILON);
        assertEquals(1.0f, BorderRadius.ONE.bottomRight(), EPSILON);
        assertEquals(1.0f, BorderRadius.ONE.bottomLeft(), EPSILON);
    }

    @Test
    @DisplayName("VALUE_2 constant")
    void testValue2Constant() {
        assertEquals(2.0f, BorderRadius.VALUE_2.topLeft(), EPSILON);
        assertEquals(2.0f, BorderRadius.VALUE_2.topRight(), EPSILON);
        assertEquals(2.0f, BorderRadius.VALUE_2.bottomRight(), EPSILON);
        assertEquals(2.0f, BorderRadius.VALUE_2.bottomLeft(), EPSILON);
    }

    @Test
    @DisplayName("MAX_VALUE constant")
    void testMaxValueConstant() {
        assertEquals(Float.MAX_VALUE, BorderRadius.MAX_VALUE.topLeft(), EPSILON);
        assertEquals(Float.MAX_VALUE, BorderRadius.MAX_VALUE.topRight(), EPSILON);
        assertEquals(Float.MAX_VALUE, BorderRadius.MAX_VALUE.bottomRight(), EPSILON);
        assertEquals(Float.MAX_VALUE, BorderRadius.MAX_VALUE.bottomLeft(), EPSILON);
    }

    // ==================== Arithmetic Operations ====================

    @Test
    @DisplayName("add with scalar")
    void testAddScalar() {
        BorderRadius br = new BorderRadius(1.0f, 2.0f, 3.0f, 4.0f);
        BorderRadius result = br.add(5.0f);
        assertEquals(6.0f, result.topLeft(), EPSILON);
        assertEquals(7.0f, result.topRight(), EPSILON);
        assertEquals(8.0f, result.bottomRight(), EPSILON);
        assertEquals(9.0f, result.bottomLeft(), EPSILON);
    }

    @Test
    @DisplayName("add with four values")
    void testAddFourValues() {
        BorderRadius br = new BorderRadius(1.0f, 2.0f, 3.0f, 4.0f);
        BorderRadius result = br.add(10.0f, 20.0f, 30.0f, 40.0f);
        assertEquals(11.0f, result.topLeft(), EPSILON);
        assertEquals(22.0f, result.topRight(), EPSILON);
        assertEquals(33.0f, result.bottomRight(), EPSILON);
        assertEquals(44.0f, result.bottomLeft(), EPSILON);
    }

    @Test
    @DisplayName("add with BorderRadius")
    void testAddBorderRadius() {
        BorderRadius br1 = new BorderRadius(1.0f, 2.0f, 3.0f, 4.0f);
        BorderRadius br2 = new BorderRadius(5.0f, 6.0f, 7.0f, 8.0f);
        BorderRadius result = br1.add(br2);
        assertEquals(6.0f, result.topLeft(), EPSILON);
        assertEquals(8.0f, result.topRight(), EPSILON);
    }

    @Test
    @DisplayName("less with scalar")
    void testLessScalar() {
        BorderRadius br = new BorderRadius(10.0f, 20.0f, 30.0f, 40.0f);
        BorderRadius result = br.less(5.0f);
        assertEquals(5.0f, result.topLeft(), EPSILON);
        assertEquals(15.0f, result.topRight(), EPSILON);
        assertEquals(25.0f, result.bottomRight(), EPSILON);
        assertEquals(35.0f, result.bottomLeft(), EPSILON);
    }

    @Test
    @DisplayName("less with four values")
    void testLessFourValues() {
        BorderRadius br = new BorderRadius(10.0f, 20.0f, 30.0f, 40.0f);
        BorderRadius result = br.less(1.0f, 2.0f, 3.0f, 4.0f);
        assertEquals(9.0f, result.topLeft(), EPSILON);
        assertEquals(18.0f, result.topRight(), EPSILON);
        assertEquals(27.0f, result.bottomRight(), EPSILON);
        assertEquals(36.0f, result.bottomLeft(), EPSILON);
    }

    @Test
    @DisplayName("less with BorderRadius")
    void testLessBorderRadius() {
        BorderRadius br1 = new BorderRadius(10.0f, 20.0f, 30.0f, 40.0f);
        BorderRadius br2 = new BorderRadius(5.0f, 10.0f, 15.0f, 20.0f);
        BorderRadius result = br1.less(br2);
        assertEquals(5.0f, result.topLeft(), EPSILON);
        assertEquals(10.0f, result.topRight(), EPSILON);
        assertEquals(15.0f, result.bottomRight(), EPSILON);
        assertEquals(20.0f, result.bottomLeft(), EPSILON);
    }

    @Test
    @DisplayName("multiply with scalar")
    void testMultiplyScalar() {
        BorderRadius br = new BorderRadius(2.0f, 3.0f, 4.0f, 5.0f);
        BorderRadius result = br.multiply(2.0f);
        assertEquals(4.0f, result.topLeft(), EPSILON);
        assertEquals(6.0f, result.topRight(), EPSILON);
        assertEquals(8.0f, result.bottomRight(), EPSILON);
        assertEquals(10.0f, result.bottomLeft(), EPSILON);
    }

    @Test
    @DisplayName("multiply with BorderRadius")
    void testMultiplyBorderRadius() {
        BorderRadius br1 = new BorderRadius(2.0f, 3.0f, 4.0f, 5.0f);
        BorderRadius br2 = new BorderRadius(2.0f, 2.0f, 2.0f, 2.0f);
        BorderRadius result = br1.multiply(br2);
        assertEquals(4.0f, result.topLeft(), EPSILON);
        assertEquals(6.0f, result.topRight(), EPSILON);
    }

    @Test
    @DisplayName("divide with scalar")
    void testDivideScalar() {
        BorderRadius br = new BorderRadius(10.0f, 20.0f, 30.0f, 40.0f);
        BorderRadius result = br.divide(2.0f);
        assertEquals(5.0f, result.topLeft(), EPSILON);
        assertEquals(10.0f, result.topRight(), EPSILON);
        assertEquals(15.0f, result.bottomRight(), EPSILON);
        assertEquals(20.0f, result.bottomLeft(), EPSILON);
    }

    @Test
    @DisplayName("divide with BorderRadius")
    void testDivideBorderRadius() {
        BorderRadius br1 = new BorderRadius(10.0f, 20.0f, 30.0f, 40.0f);
        BorderRadius br2 = new BorderRadius(2.0f, 4.0f, 6.0f, 8.0f);
        BorderRadius result = br1.divide(br2);
        assertEquals(5.0f, result.topLeft(), EPSILON);
        assertEquals(5.0f, result.topRight(), EPSILON);
    }

    @Test
    @DisplayName("divide by zero should throw exception")
    void testDivideByZero() {
        BorderRadius br = new BorderRadius(10.0f, 20.0f, 30.0f, 40.0f);
        assertThrows(IllegalArgumentException.class, () -> br.divide(0.0f));
    }

    // ==================== Utility Methods ====================

    @Test
    @DisplayName("abs should return absolute values")
    void testAbs() {
        BorderRadius br = new BorderRadius(-1.0f, -2.0f, 3.0f, -4.0f);
        BorderRadius result = br.abs();
        assertEquals(1.0f, result.topLeft(), EPSILON);
        assertEquals(2.0f, result.topRight(), EPSILON);
        assertEquals(3.0f, result.bottomRight(), EPSILON);
        assertEquals(4.0f, result.bottomLeft(), EPSILON);
    }

    @Test
    @DisplayName("invert should negate values")
    void testInvert() {
        BorderRadius br = new BorderRadius(1.0f, -2.0f, 3.0f, -4.0f);
        BorderRadius result = br.invert();
        assertEquals(-1.0f, result.topLeft(), EPSILON);
        assertEquals(2.0f, result.topRight(), EPSILON);
        assertEquals(-3.0f, result.bottomRight(), EPSILON);
        assertEquals(4.0f, result.bottomLeft(), EPSILON);
    }

    @Test
    @DisplayName("clipInteger should truncate to integers")
    void testClipInteger() {
        BorderRadius br = new BorderRadius(1.7f, 2.3f, 3.9f, 4.1f);
        BorderRadius result = br.clipInteger();
        assertEquals(1.0f, result.topLeft(), EPSILON);
        assertEquals(2.0f, result.topRight(), EPSILON);
        assertEquals(3.0f, result.bottomRight(), EPSILON);
        assertEquals(4.0f, result.bottomLeft(), EPSILON);
    }

    @Test
    @DisplayName("isZero should return true for zero values")
    void testIsZeroTrue() {
        BorderRadius br = new BorderRadius(0.0f, 0.0f, 0.0f, 0.0f);
        assertTrue(br.isZero());
    }

    @Test
    @DisplayName("isZero should return false for non-zero values")
    void testIsZeroFalse() {
        BorderRadius br = new BorderRadius(0.0f, 0.0f, 0.0f, 0.1f);
        assertFalse(br.isZero());
    }

    // ==================== Min/Max Operations ====================

    @Test
    @DisplayName("getMin should return minimum value")
    void testGetMin() {
        BorderRadius br = new BorderRadius(5.0f, 2.0f, 8.0f, 3.0f);
        assertEquals(2.0f, br.getMin(), EPSILON);
    }

    @Test
    @DisplayName("getMax should return maximum value")
    void testGetMax() {
        BorderRadius br = new BorderRadius(5.0f, 2.0f, 8.0f, 3.0f);
        assertEquals(8.0f, br.getMax(), EPSILON);
    }

    @Test
    @DisplayName("getMinAxis should return index of minimum value")
    void testGetMinAxis() {
        BorderRadius br = new BorderRadius(5.0f, 2.0f, 8.0f, 3.0f);
        assertEquals(1, br.getMinAxis());
    }

    @Test
    @DisplayName("getMaxAxis should return index of maximum value")
    void testGetMaxAxis() {
        BorderRadius br = new BorderRadius(5.0f, 2.0f, 8.0f, 3.0f);
        assertEquals(2, br.getMaxAxis());
    }

    @Test
    @DisplayName("min should return element-wise minimum")
    void testMin() {
        BorderRadius br1 = new BorderRadius(5.0f, 2.0f, 8.0f, 3.0f);
        BorderRadius br2 = new BorderRadius(3.0f, 4.0f, 6.0f, 5.0f);
        BorderRadius result = br1.min(br2);
        assertEquals(3.0f, result.topLeft(), EPSILON);
        assertEquals(2.0f, result.topRight(), EPSILON);
        assertEquals(6.0f, result.bottomRight(), EPSILON);
        assertEquals(3.0f, result.bottomLeft(), EPSILON);
    }

    @Test
    @DisplayName("max should return element-wise maximum")
    void testMax() {
        BorderRadius br1 = new BorderRadius(5.0f, 2.0f, 8.0f, 3.0f);
        BorderRadius br2 = new BorderRadius(3.0f, 4.0f, 6.0f, 5.0f);
        BorderRadius result = br1.max(br2);
        assertEquals(5.0f, result.topLeft(), EPSILON);
        assertEquals(4.0f, result.topRight(), EPSILON);
        assertEquals(8.0f, result.bottomRight(), EPSILON);
        assertEquals(5.0f, result.bottomLeft(), EPSILON);
    }

    @Test
    @DisplayName("static min should return element-wise minimum")
    void testStaticMin() {
        BorderRadius br1 = new BorderRadius(5.0f, 2.0f, 8.0f, 3.0f);
        BorderRadius br2 = new BorderRadius(3.0f, 4.0f, 6.0f, 5.0f);
        BorderRadius result = BorderRadius.min(br1, br2);
        assertEquals(3.0f, result.topLeft(), EPSILON);
        assertEquals(2.0f, result.topRight(), EPSILON);
        assertEquals(6.0f, result.bottomRight(), EPSILON);
        assertEquals(3.0f, result.bottomLeft(), EPSILON);
    }

    @Test
    @DisplayName("static max should return element-wise maximum")
    void testStaticMax() {
        BorderRadius br1 = new BorderRadius(5.0f, 2.0f, 8.0f, 3.0f);
        BorderRadius br2 = new BorderRadius(3.0f, 4.0f, 6.0f, 5.0f);
        BorderRadius result = BorderRadius.max(br1, br2);
        assertEquals(5.0f, result.topLeft(), EPSILON);
        assertEquals(4.0f, result.topRight(), EPSILON);
        assertEquals(8.0f, result.bottomRight(), EPSILON);
        assertEquals(5.0f, result.bottomLeft(), EPSILON);
    }

    // ==================== Distance Operations ====================

    @Test
    @DisplayName("distance should calculate Euclidean distance")
    void testDistance() {
        BorderRadius br1 = new BorderRadius(0.0f, 0.0f, 0.0f, 0.0f);
        BorderRadius br2 = new BorderRadius(3.0f, 4.0f, 0.0f, 0.0f);
        assertEquals(5.0f, br1.distance(br2), EPSILON);
    }

    @Test
    @DisplayName("distance2 should calculate squared distance")
    void testDistance2() {
        BorderRadius br1 = new BorderRadius(0.0f, 0.0f, 0.0f, 0.0f);
        BorderRadius br2 = new BorderRadius(3.0f, 4.0f, 0.0f, 0.0f);
        assertEquals(25.0f, br1.distance2(br2), EPSILON);
    }

    @Test
    @DisplayName("dot product")
    void testDot() {
        BorderRadius br1 = new BorderRadius(1.0f, 2.0f, 3.0f, 4.0f);
        BorderRadius br2 = new BorderRadius(5.0f, 6.0f, 7.0f, 8.0f);
        float result = br1.dot(br2);
        // 1*5 + 2*6 + 3*7 + 4*8 = 5 + 12 + 21 + 32 = 70
        assertEquals(70.0f, result, EPSILON);
    }

    // ==================== Comparison Operations ====================

    @Test
    @DisplayName("isEqual should return true for equal BorderRadius")
    void testIsEqualTrue() {
        BorderRadius br1 = new BorderRadius(1.0f, 2.0f, 3.0f, 4.0f);
        BorderRadius br2 = new BorderRadius(1.0f, 2.0f, 3.0f, 4.0f);
        assertTrue(br1.isEqual(br2));
    }

    @Test
    @DisplayName("isEqual should return false for different BorderRadius")
    void testIsEqualFalse() {
        BorderRadius br1 = new BorderRadius(1.0f, 2.0f, 3.0f, 4.0f);
        BorderRadius br2 = new BorderRadius(1.0f, 2.0f, 3.0f, 5.0f);
        assertFalse(br1.isEqual(br2));
    }

    @Test
    @DisplayName("isDifferent should return false for equal BorderRadius")
    void testIsDifferentFalse() {
        BorderRadius br1 = new BorderRadius(1.0f, 2.0f, 3.0f, 4.0f);
        BorderRadius br2 = new BorderRadius(1.0f, 2.0f, 3.0f, 4.0f);
        assertFalse(br1.isDifferent(br2));
    }

    @Test
    @DisplayName("isDifferent should return true for different BorderRadius")
    void testIsDifferentTrue() {
        BorderRadius br1 = new BorderRadius(1.0f, 2.0f, 3.0f, 4.0f);
        BorderRadius br2 = new BorderRadius(1.0f, 2.0f, 3.0f, 5.0f);
        assertTrue(br1.isDifferent(br2));
    }

    // ==================== Lerp Operation ====================

    @Test
    @DisplayName("lerp with ratio 0 should return first BorderRadius")
    void testLerpRatio0() {
        BorderRadius br1 = new BorderRadius(0.0f, 0.0f, 0.0f, 0.0f);
        BorderRadius br2 = new BorderRadius(10.0f, 20.0f, 30.0f, 40.0f);
        BorderRadius result = br1.lerp(br2, 0.0f);
        assertEquals(0.0f, result.topLeft(), EPSILON);
        assertEquals(0.0f, result.topRight(), EPSILON);
        assertEquals(0.0f, result.bottomRight(), EPSILON);
        assertEquals(0.0f, result.bottomLeft(), EPSILON);
    }

    @Test
    @DisplayName("lerp with ratio 1 should return second BorderRadius")
    void testLerpRatio1() {
        BorderRadius br1 = new BorderRadius(0.0f, 0.0f, 0.0f, 0.0f);
        BorderRadius br2 = new BorderRadius(10.0f, 20.0f, 30.0f, 40.0f);
        BorderRadius result = br1.lerp(br2, 1.0f);
        assertEquals(10.0f, result.topLeft(), EPSILON);
        assertEquals(20.0f, result.topRight(), EPSILON);
        assertEquals(30.0f, result.bottomRight(), EPSILON);
        assertEquals(40.0f, result.bottomLeft(), EPSILON);
    }

    @Test
    @DisplayName("lerp with ratio 0.5 should return midpoint")
    void testLerpRatioHalf() {
        BorderRadius br1 = new BorderRadius(0.0f, 0.0f, 0.0f, 0.0f);
        BorderRadius br2 = new BorderRadius(10.0f, 20.0f, 30.0f, 40.0f);
        BorderRadius result = br1.lerp(br2, 0.5f);
        assertEquals(5.0f, result.topLeft(), EPSILON);
        assertEquals(10.0f, result.topRight(), EPSILON);
        assertEquals(15.0f, result.bottomRight(), EPSILON);
        assertEquals(20.0f, result.bottomLeft(), EPSILON);
    }

    // ==================== Getter/With Methods ====================

    @Test
    @DisplayName("get should return value at index")
    void testGet() {
        BorderRadius br = new BorderRadius(1.0f, 2.0f, 3.0f, 4.0f);
        assertEquals(1.0f, br.get(0), EPSILON);
        assertEquals(2.0f, br.get(1), EPSILON);
        assertEquals(3.0f, br.get(2), EPSILON);
        assertEquals(4.0f, br.get(3), EPSILON);
    }

    @Test
    @DisplayName("get with invalid index should throw exception")
    void testGetInvalidIndex() {
        BorderRadius br = new BorderRadius(1.0f, 2.0f, 3.0f, 4.0f);
        assertThrows(IllegalArgumentException.class, () -> br.get(4));
        assertThrows(IllegalArgumentException.class, () -> br.get(-1));
    }

    @Test
    @DisplayName("withTopLeft should update topLeft")
    void testWithTopLeft() {
        BorderRadius br = new BorderRadius(1.0f, 2.0f, 3.0f, 4.0f);
        BorderRadius result = br.withTopLeft(10.0f);
        assertEquals(10.0f, result.topLeft(), EPSILON);
        assertEquals(2.0f, result.topRight(), EPSILON);
        assertEquals(3.0f, result.bottomRight(), EPSILON);
        assertEquals(4.0f, result.bottomLeft(), EPSILON);
    }

    @Test
    @DisplayName("withTopRight should update topRight")
    void testWithTopRight() {
        BorderRadius br = new BorderRadius(1.0f, 2.0f, 3.0f, 4.0f);
        BorderRadius result = br.withTopRight(20.0f);
        assertEquals(1.0f, result.topLeft(), EPSILON);
        assertEquals(20.0f, result.topRight(), EPSILON);
        assertEquals(3.0f, result.bottomRight(), EPSILON);
        assertEquals(4.0f, result.bottomLeft(), EPSILON);
    }

    @Test
    @DisplayName("withBottomRight should update bottomRight")
    void testWithBottomRight() {
        BorderRadius br = new BorderRadius(1.0f, 2.0f, 3.0f, 4.0f);
        BorderRadius result = br.withBottomRight(30.0f);
        assertEquals(1.0f, result.topLeft(), EPSILON);
        assertEquals(2.0f, result.topRight(), EPSILON);
        assertEquals(30.0f, result.bottomRight(), EPSILON);
        assertEquals(4.0f, result.bottomLeft(), EPSILON);
    }

    @Test
    @DisplayName("withBottomLeft should update bottomLeft")
    void testWithBottomLeft() {
        BorderRadius br = new BorderRadius(1.0f, 2.0f, 3.0f, 4.0f);
        BorderRadius result = br.withBottomLeft(40.0f);
        assertEquals(1.0f, result.topLeft(), EPSILON);
        assertEquals(2.0f, result.topRight(), EPSILON);
        assertEquals(3.0f, result.bottomRight(), EPSILON);
        assertEquals(40.0f, result.bottomLeft(), EPSILON);
    }

    // ==================== Axis Methods ====================

    @Test
    @DisplayName("closestAxis should return axis with largest absolute value")
    void testClosestAxis() {
        BorderRadius br = new BorderRadius(1.0f, -5.0f, 3.0f, 2.0f);
        assertEquals(1, br.closestAxis());
    }

    @Test
    @DisplayName("furthestAxis should return axis with smallest absolute value")
    void testFurthestAxis() {
        BorderRadius br = new BorderRadius(5.0f, 2.0f, 3.0f, 1.0f);
        assertEquals(3, br.furthestAxis());
    }

    // ==================== Triple Product ====================

    @Test
    @DisplayName("triple should calculate triple product")
    void testTriple() {
        BorderRadius br1 = new BorderRadius(1.0f, 0.0f, 0.0f, 0.0f);
        BorderRadius br2 = new BorderRadius(0.0f, 1.0f, 0.0f, 0.0f);
        BorderRadius br3 = new BorderRadius(0.0f, 0.0f, 1.0f, 0.0f);
        float result = br1.triple(br2, br3);
        assertEquals(1.0f, result, EPSILON);
    }

    // ==================== toString Test ====================

    @Test
    @DisplayName("toString should format BorderRadius")
    void testToString() {
        BorderRadius br = new BorderRadius(1.5f, 2.5f, 3.5f, 4.5f);
        String str = br.toString();
        assertTrue(str.contains("BorderRadius"));
        assertTrue(str.contains("1.5"));
        assertTrue(str.contains("2.5"));
        assertTrue(str.contains("3.5"));
    }
}
