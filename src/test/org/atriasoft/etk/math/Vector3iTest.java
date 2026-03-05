package org.atriasoft.etk.math;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Vector3i Tests")
class Vector3iTest {

    @Test
    @DisplayName("Default constructor should create (0,0,0)")
    void testDefaultConstructor() {
        Vector3i v = new Vector3i();
        assertEquals(0, v.x());
        assertEquals(0, v.y());
        assertEquals(0, v.z());
    }

    @Test
    @DisplayName("Constructor with three parameters")
    void testConstructorThreeParams() {
        Vector3i v = new Vector3i(3, 4, 5);
        assertEquals(3, v.x());
        assertEquals(4, v.y());
        assertEquals(5, v.z());
    }

    @Test
    @DisplayName("Constructor with single parameter")
    void testConstructorSingleParam() {
        Vector3i v = new Vector3i(5);
        assertEquals(5, v.x());
        assertEquals(5, v.y());
        assertEquals(5, v.z());
    }

    @Test
    @DisplayName("valueOf should parse single value")
    void testValueOfSingleValue() {
        Vector3i v = Vector3i.valueOf("5");
        assertEquals(5, v.x());
        assertEquals(5, v.y());
        assertEquals(5, v.z());
    }

    @Test
    @DisplayName("valueOf should parse three values")
    void testValueOfThreeValues() {
        Vector3i v = Vector3i.valueOf("3,4,5");
        assertEquals(3, v.x());
        assertEquals(4, v.y());
        assertEquals(5, v.z());
    }

    @Test
    @DisplayName("zero() should return zero vector")
    void testZero() {
        Vector3i v = Vector3i.zero();
        assertEquals(0, v.x());
        assertEquals(0, v.y());
        assertEquals(0, v.z());
    }

    @Test
    @DisplayName("add vector")
    void testAddVector() {
        Vector3i v1 = new Vector3i(3, 4, 5);
        Vector3i v2 = new Vector3i(1, 2, 3);
        Vector3i result = v1.add(v2);
        assertEquals(4, result.x());
        assertEquals(6, result.y());
        assertEquals(8, result.z());
    }

    @Test
    @DisplayName("less vector")
    void testLessVector() {
        Vector3i v1 = new Vector3i(5, 6, 7);
        Vector3i v2 = new Vector3i(1, 2, 3);
        Vector3i result = v1.less(v2);
        assertEquals(4, result.x());
        assertEquals(4, result.y());
        assertEquals(4, result.z());
    }

    @Test
    @DisplayName("multiply scalar")
    void testMultiplyScalar() {
        Vector3i v = new Vector3i(3, 4, 5);
        Vector3i result = v.multiply(2);
        assertEquals(6, result.x());
        assertEquals(8, result.y());
        assertEquals(10, result.z());
    }

    @Test
    @DisplayName("devide scalar")
    void testDevideScalar() {
        Vector3i v = new Vector3i(6, 8, 10);
        Vector3i result = v.devide(2);
        assertEquals(3, result.x());
        assertEquals(4, result.y());
        assertEquals(5, result.z());
    }

    @Test
    @DisplayName("devide by zero throws exception")
    void testDevideByZero() {
        Vector3i v = new Vector3i(6, 8, 10);
        assertThrows(ArithmeticException.class, () -> v.devide(0));
    }

    @Test
    @DisplayName("abs should return absolute values")
    void testAbs() {
        Vector3i v = new Vector3i(-3, 4, -5);
        Vector3i result = v.abs();
        assertEquals(3, result.x());
        assertEquals(4, result.y());
        assertEquals(5, result.z());
    }

    @Test
    @DisplayName("dot product")
    void testDot() {
        Vector3i v1 = new Vector3i(1, 2, 3);
        Vector3i v2 = new Vector3i(4, 5, 6);
        int result = v1.dot(v2);
        assertEquals(32, result);
    }

    @Test
    @DisplayName("cross product")
    void testCross() {
        Vector3i v1 = new Vector3i(1, 0, 0);
        Vector3i v2 = new Vector3i(0, 1, 0);
        Vector3i result = v1.cross(v2);
        assertEquals(0, result.x());
        assertEquals(0, result.y());
        assertEquals(1, result.z());
    }

    @Test
    @DisplayName("length")
    void testLength() {
        Vector3i v = new Vector3i(3, 4, 0);
        assertEquals(5, v.length());
    }

    @Test
    @DisplayName("distance")
    void testDistance() {
        Vector3i v1 = new Vector3i(0, 0, 0);
        Vector3i v2 = new Vector3i(3, 4, 0);
        assertEquals(5.0f, v1.distance(v2), 0.01f);
    }

    @Test
    @DisplayName("isEqual")
    void testIsEqual() {
        Vector3i v1 = new Vector3i(3, 4, 5);
        Vector3i v2 = new Vector3i(3, 4, 5);
        assertTrue(v1.isEqual(v2));
    }

    @Test
    @DisplayName("isZero")
    void testIsZero() {
        Vector3i v1 = new Vector3i(0, 0, 0);
        Vector3i v2 = new Vector3i(1, 0, 0);
        assertTrue(v1.isZero());
        assertFalse(v2.isZero());
    }

    @Test
    @DisplayName("max with two vectors")
    void testMaxTwoVectors() {
        Vector3i v1 = new Vector3i(1, 4, 2);
        Vector3i v2 = new Vector3i(3, 2, 5);
        Vector3i result = Vector3i.max(v1, v2);
        assertEquals(3, result.x());
        assertEquals(4, result.y());
        assertEquals(5, result.z());
    }

    @Test
    @DisplayName("min with two vectors")
    void testMinTwoVectors() {
        Vector3i v1 = new Vector3i(1, 4, 2);
        Vector3i v2 = new Vector3i(3, 2, 5);
        Vector3i result = Vector3i.min(v1, v2);
        assertEquals(1, result.x());
        assertEquals(2, result.y());
        assertEquals(2, result.z());
    }

    @Test
    @DisplayName("withX/Y/Z methods")
    void testWithMethods() {
        Vector3i v = new Vector3i(3, 4, 5);

        Vector3i vx = v.withX(10);
        assertEquals(10, vx.x());
        assertEquals(4, vx.y());
        assertEquals(5, vx.z());

        Vector3i vy = v.withY(10);
        assertEquals(3, vy.x());
        assertEquals(10, vy.y());
        assertEquals(5, vy.z());

        Vector3i vz = v.withZ(10);
        assertEquals(3, vz.x());
        assertEquals(4, vz.y());
        assertEquals(10, vz.z());
    }

    @Test
    @DisplayName("ZERO constant")
    void testZeroConstant() {
        assertEquals(0, Vector3i.ZERO.x());
        assertEquals(0, Vector3i.ZERO.y());
        assertEquals(0, Vector3i.ZERO.z());
    }

    @Test
    @DisplayName("ONE constant")
    void testOneConstant() {
        assertEquals(1, Vector3i.ONE.x());
        assertEquals(1, Vector3i.ONE.y());
        assertEquals(1, Vector3i.ONE.z());
    }

    @Test
    @DisplayName("toString")
    void testToString() {
        Vector3i v = new Vector3i(3, 4, 5);
        assertEquals("Vector3i(3,4,5)", v.toString());
    }
}
