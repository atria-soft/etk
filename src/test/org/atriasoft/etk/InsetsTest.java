package org.atriasoft.etk;

import org.atriasoft.etk.math.Vector2f;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Insets Tests")
class InsetsTest {

    private static final float EPSILON = 0.001f;

    // ==================== Constructor Tests ====================

    @Test
    @DisplayName("Constructor with single value")
    void testConstructorSingleValue() {
        Insets insets = new Insets(5.0f);
        assertEquals(5.0f, insets.top(), EPSILON);
        assertEquals(5.0f, insets.right(), EPSILON);
        assertEquals(5.0f, insets.bottom(), EPSILON);
        assertEquals(5.0f, insets.left(), EPSILON);
    }

    @Test
    @DisplayName("Constructor with four values")
    void testConstructorFourValues() {
        Insets insets = new Insets(1.0f, 2.0f, 3.0f, 4.0f);
        assertEquals(1.0f, insets.top(), EPSILON);
        assertEquals(2.0f, insets.right(), EPSILON);
        assertEquals(3.0f, insets.bottom(), EPSILON);
        assertEquals(4.0f, insets.left(), EPSILON);
    }

    // ==================== valueOf() Tests ====================

    @Test
    @DisplayName("valueOf with single value")
    void testValueOfSingleValue() {
        Insets insets = Insets.valueOf("5.0");
        assertEquals(5.0f, insets.top(), EPSILON);
        assertEquals(5.0f, insets.right(), EPSILON);
        assertEquals(5.0f, insets.bottom(), EPSILON);
        assertEquals(5.0f, insets.left(), EPSILON);
    }

    @Test
    @DisplayName("valueOf with two values")
    void testValueOfTwoValues() {
        Insets insets = Insets.valueOf("5.0 10.0");
        assertEquals(5.0f, insets.top(), EPSILON);
        assertEquals(10.0f, insets.right(), EPSILON);
        assertEquals(10.0f, insets.bottom(), EPSILON);
        assertEquals(10.0f, insets.left(), EPSILON);
    }

    @Test
    @DisplayName("valueOf with three values")
    void testValueOfThreeValues() {
        Insets insets = Insets.valueOf("5.0 10.0 15.0");
        assertEquals(5.0f, insets.top(), EPSILON);
        assertEquals(10.0f, insets.right(), EPSILON);
        assertEquals(15.0f, insets.bottom(), EPSILON);
        assertEquals(15.0f, insets.left(), EPSILON);
    }

    @Test
    @DisplayName("valueOf with four values")
    void testValueOfFourValues() {
        Insets insets = Insets.valueOf("5.0 10.0 15.0 20.0");
        assertEquals(5.0f, insets.top(), EPSILON);
        assertEquals(10.0f, insets.right(), EPSILON);
        assertEquals(15.0f, insets.bottom(), EPSILON);
        assertEquals(20.0f, insets.left(), EPSILON);
    }

    @Test
    @DisplayName("valueOf with four separate strings")
    void testValueOfFourStrings() {
        Insets insets = Insets.valueOf("1.0", "2.0", "3.0", "4.0");
        assertEquals(1.0f, insets.top(), EPSILON);
        assertEquals(2.0f, insets.right(), EPSILON);
        assertEquals(3.0f, insets.bottom(), EPSILON);
        assertEquals(4.0f, insets.left(), EPSILON);
    }

    // ==================== Constants Tests ====================

    @Test
    @DisplayName("ZERO constant")
    void testZeroConstant() {
        assertTrue(Insets.ZERO.isZero());
    }

    @Test
    @DisplayName("ONE constant")
    void testOneConstant() {
        assertEquals(1.0f, Insets.ONE.top(), EPSILON);
        assertEquals(1.0f, Insets.ONE.right(), EPSILON);
        assertEquals(1.0f, Insets.ONE.bottom(), EPSILON);
        assertEquals(1.0f, Insets.ONE.left(), EPSILON);
    }

    // ==================== Arithmetic Operations ====================

    @Test
    @DisplayName("add with scalar")
    void testAddScalar() {
        Insets insets = new Insets(1.0f, 2.0f, 3.0f, 4.0f);
        Insets result = insets.add(5.0f);
        assertEquals(6.0f, result.top(), EPSILON);
        assertEquals(7.0f, result.right(), EPSILON);
        assertEquals(8.0f, result.bottom(), EPSILON);
        assertEquals(9.0f, result.left(), EPSILON);
    }

    @Test
    @DisplayName("add with Insets")
    void testAddInsets() {
        Insets insets1 = new Insets(1.0f, 2.0f, 3.0f, 4.0f);
        Insets insets2 = new Insets(5.0f, 6.0f, 7.0f, 8.0f);
        Insets result = insets1.add(insets2);
        assertEquals(6.0f, result.top(), EPSILON);
        assertEquals(8.0f, result.right(), EPSILON);
    }

    @Test
    @DisplayName("less with scalar")
    void testLessScalar() {
        Insets insets = new Insets(10.0f, 20.0f, 30.0f, 40.0f);
        Insets result = insets.less(5.0f);
        assertEquals(5.0f, result.top(), EPSILON);
        assertEquals(15.0f, result.right(), EPSILON);
        assertEquals(25.0f, result.bottom(), EPSILON);
        assertEquals(35.0f, result.left(), EPSILON);
    }

    @Test
    @DisplayName("less with Insets")
    void testLessInsets() {
        Insets insets1 = new Insets(10.0f, 20.0f, 30.0f, 40.0f);
        Insets insets2 = new Insets(5.0f, 10.0f, 15.0f, 20.0f);
        Insets result = insets1.less(insets2);
        assertEquals(5.0f, result.top(), EPSILON);
        assertEquals(10.0f, result.right(), EPSILON);
        assertEquals(15.0f, result.bottom(), EPSILON);
        assertEquals(20.0f, result.left(), EPSILON);
    }

    @Test
    @DisplayName("multiply with scalar")
    void testMultiplyScalar() {
        Insets insets = new Insets(2.0f, 3.0f, 4.0f, 5.0f);
        Insets result = insets.multiply(2.0f);
        assertEquals(4.0f, result.top(), EPSILON);
        assertEquals(6.0f, result.right(), EPSILON);
        assertEquals(8.0f, result.bottom(), EPSILON);
        assertEquals(10.0f, result.left(), EPSILON);
    }

    @Test
    @DisplayName("divide with scalar")
    void testDivideScalar() {
        Insets insets = new Insets(10.0f, 20.0f, 30.0f, 40.0f);
        Insets result = insets.divide(2.0f);
        assertEquals(5.0f, result.top(), EPSILON);
        assertEquals(10.0f, result.right(), EPSILON);
        assertEquals(15.0f, result.bottom(), EPSILON);
        assertEquals(20.0f, result.left(), EPSILON);
    }

    @Test
    @DisplayName("divide by zero should throw exception")
    void testDivideByZero() {
        Insets insets = new Insets(10.0f, 20.0f, 30.0f, 40.0f);
        assertThrows(IllegalArgumentException.class, () -> insets.divide(0.0f));
    }

    // ==================== Utility Methods ====================

    @Test
    @DisplayName("abs should return absolute values")
    void testAbs() {
        Insets insets = new Insets(-1.0f, -2.0f, 3.0f, -4.0f);
        Insets result = insets.abs();
        assertEquals(1.0f, result.top(), EPSILON);
        assertEquals(2.0f, result.right(), EPSILON);
        assertEquals(3.0f, result.bottom(), EPSILON);
        assertEquals(4.0f, result.left(), EPSILON);
    }

    @Test
    @DisplayName("invert should negate values")
    void testInvert() {
        Insets insets = new Insets(1.0f, -2.0f, 3.0f, -4.0f);
        Insets result = insets.invert();
        assertEquals(-1.0f, result.top(), EPSILON);
        assertEquals(2.0f, result.right(), EPSILON);
        assertEquals(-3.0f, result.bottom(), EPSILON);
        assertEquals(4.0f, result.left(), EPSILON);
    }

    @Test
    @DisplayName("clipInteger should truncate to integers")
    void testClipInteger() {
        Insets insets = new Insets(1.7f, 2.3f, 3.9f, 4.1f);
        Insets result = insets.clipInteger();
        assertEquals(1.0f, result.top(), EPSILON);
        assertEquals(2.0f, result.right(), EPSILON);
        assertEquals(3.0f, result.bottom(), EPSILON);
        assertEquals(4.0f, result.left(), EPSILON);
    }

    @Test
    @DisplayName("isZero should return true for zero values")
    void testIsZeroTrue() {
        Insets insets = new Insets(0.0f, 0.0f, 0.0f, 0.0f);
        assertTrue(insets.isZero());
    }

    @Test
    @DisplayName("isZero should return false for non-zero values")
    void testIsZeroFalse() {
        Insets insets = new Insets(0.0f, 0.0f, 0.0f, 0.1f);
        assertFalse(insets.isZero());
    }

    // ==================== Min/Max Operations ====================

    @Test
    @DisplayName("getMin should return minimum value")
    void testGetMin() {
        Insets insets = new Insets(5.0f, 2.0f, 8.0f, 3.0f);
        assertEquals(2.0f, insets.getMin(), EPSILON);
    }

    @Test
    @DisplayName("getMax should return maximum value")
    void testGetMax() {
        Insets insets = new Insets(5.0f, 2.0f, 8.0f, 3.0f);
        assertEquals(8.0f, insets.getMax(), EPSILON);
    }

    @Test
    @DisplayName("min should return element-wise minimum")
    void testMin() {
        Insets insets1 = new Insets(5.0f, 2.0f, 8.0f, 3.0f);
        Insets insets2 = new Insets(3.0f, 4.0f, 6.0f, 5.0f);
        Insets result = insets1.min(insets2);
        assertEquals(3.0f, result.top(), EPSILON);
        assertEquals(2.0f, result.right(), EPSILON);
        assertEquals(6.0f, result.bottom(), EPSILON);
        assertEquals(3.0f, result.left(), EPSILON);
    }

    @Test
    @DisplayName("max should return element-wise maximum")
    void testMax() {
        Insets insets1 = new Insets(5.0f, 2.0f, 8.0f, 3.0f);
        Insets insets2 = new Insets(3.0f, 4.0f, 6.0f, 5.0f);
        Insets result = insets1.max(insets2);
        assertEquals(5.0f, result.top(), EPSILON);
        assertEquals(4.0f, result.right(), EPSILON);
        assertEquals(8.0f, result.bottom(), EPSILON);
        assertEquals(5.0f, result.left(), EPSILON);
    }

    // ==================== Distance Operations ====================

    @Test
    @DisplayName("distance should calculate Euclidean distance")
    void testDistance() {
        Insets insets1 = new Insets(0.0f, 0.0f, 0.0f, 0.0f);
        Insets insets2 = new Insets(3.0f, 4.0f, 0.0f, 0.0f);
        assertEquals(5.0f, insets1.distance(insets2), EPSILON);
    }

    @Test
    @DisplayName("distance2 should calculate squared distance")
    void testDistance2() {
        Insets insets1 = new Insets(0.0f, 0.0f, 0.0f, 0.0f);
        Insets insets2 = new Insets(3.0f, 4.0f, 0.0f, 0.0f);
        assertEquals(25.0f, insets1.distance2(insets2), EPSILON);
    }

    @Test
    @DisplayName("dot product")
    void testDot() {
        Insets insets1 = new Insets(1.0f, 2.0f, 3.0f, 4.0f);
        Insets insets2 = new Insets(5.0f, 6.0f, 7.0f, 8.0f);
        float result = insets1.dot(insets2);
        // 1*5 + 2*6 + 3*7 + 4*8 = 5 + 12 + 21 + 32 = 70
        assertEquals(70.0f, result, EPSILON);
    }

    // ==================== Comparison Operations ====================

    @Test
    @DisplayName("isEqual should return true for equal Insets")
    void testIsEqualTrue() {
        Insets insets1 = new Insets(1.0f, 2.0f, 3.0f, 4.0f);
        Insets insets2 = new Insets(1.0f, 2.0f, 3.0f, 4.0f);
        assertTrue(insets1.isEqual(insets2));
    }

    @Test
    @DisplayName("isEqual should return false for different Insets")
    void testIsEqualFalse() {
        Insets insets1 = new Insets(1.0f, 2.0f, 3.0f, 4.0f);
        Insets insets2 = new Insets(1.0f, 2.0f, 3.0f, 5.0f);
        assertFalse(insets1.isEqual(insets2));
    }

    @Test
    @DisplayName("isDifferent should return false for equal Insets")
    void testIsDifferentFalse() {
        Insets insets1 = new Insets(1.0f, 2.0f, 3.0f, 4.0f);
        Insets insets2 = new Insets(1.0f, 2.0f, 3.0f, 4.0f);
        assertFalse(insets1.isDifferent(insets2));
    }

    @Test
    @DisplayName("isDifferent should return true for different Insets")
    void testIsDifferentTrue() {
        Insets insets1 = new Insets(1.0f, 2.0f, 3.0f, 4.0f);
        Insets insets2 = new Insets(1.0f, 2.0f, 3.0f, 5.0f);
        assertTrue(insets1.isDifferent(insets2));
    }

    // ==================== Lerp Operation ====================

    @Test
    @DisplayName("lerp with ratio 0 should return first Insets")
    void testLerpRatio0() {
        Insets insets1 = new Insets(0.0f, 0.0f, 0.0f, 0.0f);
        Insets insets2 = new Insets(10.0f, 20.0f, 30.0f, 40.0f);
        Insets result = insets1.lerp(insets2, 0.0f);
        assertEquals(0.0f, result.top(), EPSILON);
        assertEquals(0.0f, result.right(), EPSILON);
        assertEquals(0.0f, result.bottom(), EPSILON);
        assertEquals(0.0f, result.left(), EPSILON);
    }

    @Test
    @DisplayName("lerp with ratio 1 should return second Insets")
    void testLerpRatio1() {
        Insets insets1 = new Insets(0.0f, 0.0f, 0.0f, 0.0f);
        Insets insets2 = new Insets(10.0f, 20.0f, 30.0f, 40.0f);
        Insets result = insets1.lerp(insets2, 1.0f);
        assertEquals(10.0f, result.top(), EPSILON);
        assertEquals(20.0f, result.right(), EPSILON);
        assertEquals(30.0f, result.bottom(), EPSILON);
        assertEquals(40.0f, result.left(), EPSILON);
    }

    @Test
    @DisplayName("lerp with ratio 0.5 should return midpoint")
    void testLerpRatioHalf() {
        Insets insets1 = new Insets(0.0f, 0.0f, 0.0f, 0.0f);
        Insets insets2 = new Insets(10.0f, 20.0f, 30.0f, 40.0f);
        Insets result = insets1.lerp(insets2, 0.5f);
        assertEquals(5.0f, result.top(), EPSILON);
        assertEquals(10.0f, result.right(), EPSILON);
        assertEquals(15.0f, result.bottom(), EPSILON);
        assertEquals(20.0f, result.left(), EPSILON);
    }

    // ==================== Getter/With Methods ====================

    @Test
    @DisplayName("get should return value at index")
    void testGet() {
        Insets insets = new Insets(1.0f, 2.0f, 3.0f, 4.0f);
        assertEquals(1.0f, insets.get(0), EPSILON);
        assertEquals(2.0f, insets.get(1), EPSILON);
        assertEquals(3.0f, insets.get(2), EPSILON);
        assertEquals(4.0f, insets.get(3), EPSILON);
    }

    @Test
    @DisplayName("get with invalid index should throw exception")
    void testGetInvalidIndex() {
        Insets insets = new Insets(1.0f, 2.0f, 3.0f, 4.0f);
        assertThrows(IllegalArgumentException.class, () -> insets.get(4));
        assertThrows(IllegalArgumentException.class, () -> insets.get(-1));
    }

    @Test
    @DisplayName("withTop should update top")
    void testWithTop() {
        Insets insets = new Insets(1.0f, 2.0f, 3.0f, 4.0f);
        Insets result = insets.withTop(10.0f);
        assertEquals(10.0f, result.top(), EPSILON);
        assertEquals(2.0f, result.right(), EPSILON);
        assertEquals(3.0f, result.bottom(), EPSILON);
        assertEquals(4.0f, result.left(), EPSILON);
    }

    @Test
    @DisplayName("withRight should update right")
    void testWithRight() {
        Insets insets = new Insets(1.0f, 2.0f, 3.0f, 4.0f);
        Insets result = insets.withRight(20.0f);
        assertEquals(1.0f, result.top(), EPSILON);
        assertEquals(20.0f, result.right(), EPSILON);
        assertEquals(3.0f, result.bottom(), EPSILON);
        assertEquals(4.0f, result.left(), EPSILON);
    }

    @Test
    @DisplayName("withBottom should update bottom")
    void testWithBottom() {
        Insets insets = new Insets(1.0f, 2.0f, 3.0f, 4.0f);
        Insets result = insets.withBottom(30.0f);
        assertEquals(1.0f, result.top(), EPSILON);
        assertEquals(2.0f, result.right(), EPSILON);
        assertEquals(30.0f, result.bottom(), EPSILON);
        assertEquals(4.0f, result.left(), EPSILON);
    }

    @Test
    @DisplayName("withLeft should update left")
    void testWithLeft() {
        Insets insets = new Insets(1.0f, 2.0f, 3.0f, 4.0f);
        Insets result = insets.withLeft(40.0f);
        assertEquals(1.0f, result.top(), EPSILON);
        assertEquals(2.0f, result.right(), EPSILON);
        assertEquals(3.0f, result.bottom(), EPSILON);
        assertEquals(40.0f, result.left(), EPSILON);
    }

    // ==================== Vector Conversion ====================

    @Test
    @DisplayName("toVector2f should calculate width and height")
    void testToVector2f() {
        Insets insets = new Insets(10.0f, 20.0f, 30.0f, 40.0f);
        Vector2f vec = insets.toVector2f();
        assertEquals(60.0f, vec.x(), EPSILON); // left + right = 40 + 20
        assertEquals(40.0f, vec.y(), EPSILON); // top + bottom = 10 + 30
    }

    @Test
    @DisplayName("getOrigin should return bottom-left")
    void testGetOrigin() {
        Insets insets = new Insets(10.0f, 20.0f, 30.0f, 40.0f);
        Vector2f origin = insets.getOrigin();
        assertEquals(40.0f, origin.x(), EPSILON); // left
        assertEquals(30.0f, origin.y(), EPSILON); // bottom
    }

    @Test
    @DisplayName("getEnd should return top-right")
    void testGetEnd() {
        Insets insets = new Insets(10.0f, 20.0f, 30.0f, 40.0f);
        Vector2f end = insets.getEnd();
        assertEquals(20.0f, end.x(), EPSILON); // right
        assertEquals(10.0f, end.y(), EPSILON); // top
    }

    // ==================== toString Test ====================

    @Test
    @DisplayName("toString should format Insets")
    void testToString() {
        Insets insets = new Insets(1.5f, 2.5f, 3.5f, 4.5f);
        String str = insets.toString();
        assertTrue(str.contains("1.5"));
        assertTrue(str.contains("2.5"));
        assertTrue(str.contains("3.5"));
    }
}
