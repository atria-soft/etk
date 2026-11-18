package org.atriasoft.etk.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Quaternion Tests")
class QuaternionTest {

	private static final float EPSILON = 0.0001f;

	@Test
	@DisplayName("multiplyRaw keeps the magnitude of the angular velocity derivative")
	void testMultiplyRawKeepsMagnitude() {
		final Vector3f angularVelocity = new Vector3f(0.0f, 4.0f, 0.0f);
		final Quaternion orientation = Quaternion.IDENTITY;
		final Quaternion raw = new Quaternion(0.0f, angularVelocity).multiplyRaw(orientation);
		assertEquals(0.0f, raw.x(), EPSILON);
		assertEquals(4.0f, raw.y(), EPSILON);
		assertEquals(0.0f, raw.z(), EPSILON);
		assertEquals(0.0f, raw.w(), EPSILON);
		assertEquals(4.0f, raw.length(), EPSILON);
	}

	@Test
	@DisplayName("multiplyRaw is the Hamilton product")
	void testMultiplyRawHamiltonProduct() {
		final Quaternion aaa = new Quaternion(1.0f, 2.0f, 3.0f, 4.0f);
		final Quaternion bbb = new Quaternion(5.0f, 6.0f, 7.0f, 8.0f);
		final Quaternion raw = aaa.multiplyRaw(bbb);
		// (1i+2j+3k+4)(5i+6j+7k+8) = 24i + 48j + 48k - 6
		assertEquals(24.0f, raw.x(), EPSILON);
		assertEquals(48.0f, raw.y(), EPSILON);
		assertEquals(48.0f, raw.z(), EPSILON);
		assertEquals(-6.0f, raw.w(), EPSILON);
	}

	@Test
	@DisplayName("multiply still returns the normalised product")
	void testMultiplyStillNormalises() {
		final Quaternion aaa = new Quaternion(1.0f, 2.0f, 3.0f, 4.0f);
		final Quaternion bbb = new Quaternion(5.0f, 6.0f, 7.0f, 8.0f);
		final Quaternion normalised = aaa.multiply(bbb);
		final Quaternion expected = aaa.multiplyRaw(bbb).normalize();
		assertEquals(1.0f, normalised.length(), EPSILON);
		assertEquals(expected.x(), normalised.x(), EPSILON);
		assertEquals(expected.y(), normalised.y(), EPSILON);
		assertEquals(expected.z(), normalised.z(), EPSILON);
		assertEquals(expected.w(), normalised.w(), EPSILON);
	}

	@Test
	@DisplayName("multiplyRaw of unit rotations matches multiply")
	void testMultiplyRawOfRotations() {
		final Quaternion aaa = Quaternion.fromEulerAngles(new Vector3f(0.3f, 0.5f, -0.2f));
		final Quaternion bbb = Quaternion.fromEulerAngles(new Vector3f(-0.7f, 0.1f, 0.9f));
		final Quaternion raw = aaa.multiplyRaw(bbb);
		final Quaternion normalised = aaa.multiply(bbb);
		assertEquals(normalised.x(), raw.x(), EPSILON);
		assertEquals(normalised.y(), raw.y(), EPSILON);
		assertEquals(normalised.z(), raw.z(), EPSILON);
		assertEquals(normalised.w(), raw.w(), EPSILON);
	}
}
