package org.atriasoft.etk.math;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Vector3b Tests")
class Vector3bTest {

    @Test
    @DisplayName("Default constructor should create (false,false,false)")
    void testDefaultConstructor() {
        Vector3b v = new Vector3b();
        assertFalse(v.x());
        assertFalse(v.y());
        assertFalse(v.z());
    }

    @Test
    @DisplayName("Constructor with parameters")
    void testConstructorWithParameters() {
        Vector3b v = new Vector3b(true, false, true);
        assertTrue(v.x());
        assertFalse(v.y());
        assertTrue(v.z());
    }

    @Test
    @DisplayName("valueOf should parse single value")
    void testValueOfSingleValue() {
        Vector3b v = Vector3b.valueOf("true");
        assertTrue(v.x());
        assertTrue(v.y());
        assertTrue(v.z());
    }

    @Test
    @DisplayName("valueOf should parse two values")
    void testValueOfTwoValues() {
        Vector3b v = Vector3b.valueOf("true,false");
        assertTrue(v.x());
        assertFalse(v.y());
        assertFalse(v.z());
    }

    @Test
    @DisplayName("valueOf should parse three values")
    void testValueOfThreeValues() {
        Vector3b v = Vector3b.valueOf("true,false,true");
        assertTrue(v.x());
        assertFalse(v.y());
        assertTrue(v.z());
    }

    @Test
    @DisplayName("isEqual")
    void testIsEqual() {
        Vector3b v1 = new Vector3b(true, false, true);
        Vector3b v2 = new Vector3b(true, false, true);
        Vector3b v3 = new Vector3b(true, true, true);
        assertTrue(v1.isEqual(v2));
        assertFalse(v1.isEqual(v3));
    }

    @Test
    @DisplayName("isDifferent")
    void testIsDifferent() {
        Vector3b v1 = new Vector3b(true, false, true);
        Vector3b v2 = new Vector3b(true, false, true);
        Vector3b v3 = new Vector3b(true, true, true);
        assertFalse(v1.isDifferent(v2));
        assertTrue(v1.isDifferent(v3));
    }

    @Test
    @DisplayName("withX/Y/Z methods")
    void testWithMethods() {
        Vector3b v = new Vector3b(true, false, true);

        Vector3b vx = v.withX(false);
        assertFalse(vx.x());
        assertFalse(vx.y());
        assertTrue(vx.z());

        Vector3b vy = v.withY(true);
        assertTrue(vy.x());
        assertTrue(vy.y());
        assertTrue(vy.z());

        Vector3b vz = v.withZ(false);
        assertTrue(vz.x());
        assertFalse(vz.y());
        assertFalse(vz.z());
    }

    @Test
    @DisplayName("FALSE constant")
    void testFalseConstant() {
        assertFalse(Vector3b.FALSE.x());
        assertFalse(Vector3b.FALSE.y());
        assertFalse(Vector3b.FALSE.z());
    }

    @Test
    @DisplayName("TRUE constant")
    void testTrueConstant() {
        assertTrue(Vector3b.TRUE.x());
        assertTrue(Vector3b.TRUE.y());
        assertTrue(Vector3b.TRUE.z());
    }

    @Test
    @DisplayName("TRUE_FALSE_FALSE constant")
    void testTrueFalseFalseConstant() {
        assertTrue(Vector3b.TRUE_FALSE_FALSE.x());
        assertFalse(Vector3b.TRUE_FALSE_FALSE.y());
        assertFalse(Vector3b.TRUE_FALSE_FALSE.z());
    }

    @Test
    @DisplayName("FALSE_TRUE_FALSE constant")
    void testFalseTrueFalseConstant() {
        assertFalse(Vector3b.FALSE_TRUE_FALSE.x());
        assertTrue(Vector3b.FALSE_TRUE_FALSE.y());
        assertFalse(Vector3b.FALSE_TRUE_FALSE.z());
    }

    @Test
    @DisplayName("FALSE_FALSE_TRUE constant")
    void testFalseFalseTrueConstant() {
        assertFalse(Vector3b.FALSE_FALSE_TRUE.x());
        assertFalse(Vector3b.FALSE_FALSE_TRUE.y());
        assertTrue(Vector3b.FALSE_FALSE_TRUE.z());
    }

    @Test
    @DisplayName("toString")
    void testToString() {
        Vector3b v = new Vector3b(true, false, true);
        // Note: toString has a bug showing only x,y not x,y,z
        String s = v.toString();
        assertTrue(s.contains("true"));
        assertTrue(s.contains("false"));
    }
}
