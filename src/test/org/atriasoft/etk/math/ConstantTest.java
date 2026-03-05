package org.atriasoft.etk.math;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Constant Tests")
class ConstantTest {

    private static final float EPSILON = 0.00001f;

    @Test
    @DisplayName("FLOAT_EPSILON constant")
    void testFloatEpsilon() {
        assertEquals(0.0000001192f, Constant.FLOAT_EPSILON, 0.0000000001f);
        assertTrue(Constant.FLOAT_EPSILON > 0);
        assertTrue(Constant.FLOAT_EPSILON < 0.001f);
    }

    @Test
    @DisplayName("MACHINE_EPSILON constant")
    void testMachineEpsilon() {
        assertEquals(0.000001f, Constant.MACHINE_EPSILON, EPSILON);
        assertTrue(Constant.MACHINE_EPSILON > 0);
        assertTrue(Constant.MACHINE_EPSILON < 0.001f);
    }

    @Test
    @DisplayName("PI constant")
    void testPi() {
        assertEquals(3.14159265f, Constant.PI, EPSILON);
        assertEquals((float) Math.PI, Constant.PI, 0.0001f);
    }

    @Test
    @DisplayName("PI_2 constant")
    void testPi2() {
        assertEquals(6.28318530f, Constant.PI_2, EPSILON);
        assertEquals(2.0f * (float) Math.PI, Constant.PI_2, 0.0001f);
        assertEquals(2.0f * Constant.PI, Constant.PI_2, EPSILON);
    }

    @Test
    @DisplayName("MACHINE_EPSILON should be larger than FLOAT_EPSILON")
    void testEpsilonRelation() {
        assertTrue(Constant.MACHINE_EPSILON > Constant.FLOAT_EPSILON);
    }

    @Test
    @DisplayName("Constants should be usable in calculations")
    void testConstantsUsability() {
        float circle = 2.0f * Constant.PI;
        assertTrue(circle > 6.0f && circle < 7.0f);

        float diff = 1.0f + Constant.FLOAT_EPSILON;
        assertTrue(diff > 1.0f);
    }
}
