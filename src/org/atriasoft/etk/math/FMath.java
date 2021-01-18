package org.atriasoft.etk.math;

public class FMath {
	public static float abs(final float a) {
		if (a < 0.0f) {
			return -a;
		}
		return a;
	}
	
	/**
	 * Test if the value id in the correct range
	 * @param a fist value
	 * @param b second value (a-b)
	 * @return true if it is in the range
	 */
	public static boolean approxEqual(final float a, final float b) {
		return approxEqual(a, b, Constant.MACHINE_EPSILON);
	}
	
	/**
	 * Test if the value id in the correct range
	 * @param a fist value
	 * @param b second value (a-b)
	 * @param epsilon delta to check
	 * @return true if it is in the range
	 */
	public static boolean approxEqual(final float a, final float b, final float epsilon) {
		final float difference = a - b;
		return (abs(difference) < epsilon);
	}
	
	public static float atan2(final float sinHalfAngleAbs, final float cosHalfAngle) {
		return (float) Math.atan2(sinHalfAngleAbs, cosHalfAngle);
	}
	
	public static float avg(final float min, final float value, final float max) {
		return Math.max(min, Math.min(value, max));
	}
	
	/// Function that returns the result of the "value" clamped by
	/// two others values "lowerLimit" and "upperLimit"
	public static float clamp(final float value, final float lowerLimit, final float upperLimit) {
		assert (lowerLimit <= upperLimit);
		return FMath.min(FMath.max(value, lowerLimit), upperLimit);
	}
	
	public static int clamp(final int value, final int lowerLimit, final int upperLimit) {
		assert (lowerLimit <= upperLimit);
		return FMath.min(FMath.max(value, lowerLimit), upperLimit);
	}
	
	/// Function that returns the result of the "value" clamped by
	/// two others values "lowerLimit" and "upperLimit"
	public static long clamp(final long value, final long lowerLimit, final long upperLimit) {
		assert (lowerLimit <= upperLimit);
		return FMath.min(FMath.max(value, lowerLimit), upperLimit);
	}
	
	// TODO check this basic function ...
	public static int floor(final float f) {
		return (int) Math.floor(f);
	}
	
	public static float max(final float a, final float b) {
		return Math.max(a, b);
	}
	
	public static float max(final float a, final float b, final float c) {
		return Math.max(Math.max(a, b), c);
	}
	
	public static float max(final float a, final float b, final float c, final float d) {
		return Math.max(Math.max(Math.max(a, b), c), d);
	}
	
	public static int max(final int a, final int b) {
		return Math.max(a, b);
	}
	
	public static long max(final long a, final long b) {
		return Math.max(a, b);
	}
	
	public static Vector3f max(final Vector3f a, final Vector3f b) {
		return new Vector3f(Math.max(a.x, b.x), Math.max(a.y, b.y), Math.max(a.z, b.z));
	}
	
	public static float min(final float a, final float b) {
		return Math.min(a, b);
	}
	
	public static float min(final float a, final float b, final float c) {
		return Math.min(Math.min(a, b), c);
	}
	
	public static float min(final float a, final float b, final float c, final float d) {
		return Math.min(Math.min(Math.min(a, b), c), d);
	}
	
	public static int min(final int a, final int b) {
		return Math.min(a, b);
	}
	
	public static long min(final long a, final long b) {
		return Math.min(a, b);
	}
	
	public static Vector3f min(final Vector3f a, final Vector3f b) {
		return new Vector3f(Math.min(a.x, b.x), Math.min(a.y, b.y), Math.min(a.z, b.z));
	}
	
	public static float mod(final float value, final float modulo) {
		// TODO Auto-generated method stub
		return value % modulo;
	}
	
	public static float pow(final float value, final float exponent) {
		// TODO Auto-generated method stub
		return (float) Math.pow(value, exponent);
	}
	
	/// Return true if two values have the same sign
	public static boolean sameSign(final float a, final float b) {
		return a * b >= 0.0f;
	}
	
	public static float sqrt(final float value) {
		return (float) Math.sqrt(value);
	}
}
