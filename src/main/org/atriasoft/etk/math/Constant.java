package org.atriasoft.etk.math;

/**
 * Mathematical constants for floating-point operations.
 *
 * <p>Defines commonly used mathematical constants with appropriate precision for
 * float-based calculations, including Pi and various epsilon values for comparisons.</p>
 *
 * @author Edouard DUPIN
 * @since 0.1.0
 */
public class Constant {

    private Constant() {
    }

    // Machine epsilon
    public static final float FLOAT_EPSILON = 0.0000001192f;
    // Machine epsilon
    public static final float MACHINE_EPSILON = 0.000001f;
    // Pi constant
    public static final float PI = 3.14159265f;
    // 2*Pi constant
    public static final float PI_2 = 6.28318530f;
}
