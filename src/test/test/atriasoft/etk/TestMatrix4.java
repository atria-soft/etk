/*******************************************************************************
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Contributors:
 *     Edouard DUPIN - initial API and implementation
 ******************************************************************************/
package test.atriasoft.etk;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.atriasoft.etk.math.Matrix4f;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class TestMatrix4 {
	
	@Test
	@Order(4)
	public void invert() {
		Matrix4f tmp = new Matrix4f(1, 1, 1, -1, 1, 1, -1, 1, 1, -1, 1, 1, -1, 1, 1, 1);
		Matrix4f tmpInvert = tmp.invert();
		assertEquals(tmpInvert.a1(), 0.25f);
		assertEquals(tmpInvert.b1(), 0.25f);
		assertEquals(tmpInvert.c1(), 0.25f);
		assertEquals(tmpInvert.d1(), -0.25f);

		assertEquals(tmpInvert.a2(), 0.25f);
		assertEquals(tmpInvert.b2(), 0.25f);
		assertEquals(tmpInvert.c2(), -0.25f);
		assertEquals(tmpInvert.d2(), 0.25f);

		assertEquals(tmpInvert.a3(), 0.25f);
		assertEquals(tmpInvert.b3(), -0.25f);
		assertEquals(tmpInvert.c3(), 0.25f);
		assertEquals(tmpInvert.d3(), 0.25f);

		assertEquals(tmpInvert.a4(), -0.25f);
		assertEquals(tmpInvert.b4(), 0.25f);
		assertEquals(tmpInvert.c4(), 0.25f);
		assertEquals(tmpInvert.d4(), 0.25f);
	}

	@Test
	@Order(5)
	public void invertNonSymmetric() {
		// Non-symmetric matrix — the old code passed the symmetric test by luck
		// because transpose(cofactorMatrix) == cofactorMatrix for symmetric matrices.
		final Matrix4f m = new Matrix4f(
				2, 3, 1, 5,
				1, 0, 3, 1,
				0, 2, -3, 2,
				0, 2, 3, 1);
		final Matrix4f inv = m.invert();
		// Verify: inv * m ≈ identity
		final Matrix4f product = inv.multiply(m);
		final float eps = 1e-5f;
		assertEquals(1.0f, product.a1(), eps, "inv*m [0,0]");
		assertEquals(0.0f, product.b1(), eps, "inv*m [0,1]");
		assertEquals(0.0f, product.c1(), eps, "inv*m [0,2]");
		assertEquals(0.0f, product.d1(), eps, "inv*m [0,3]");
		assertEquals(0.0f, product.a2(), eps, "inv*m [1,0]");
		assertEquals(1.0f, product.b2(), eps, "inv*m [1,1]");
		assertEquals(0.0f, product.c2(), eps, "inv*m [1,2]");
		assertEquals(0.0f, product.d2(), eps, "inv*m [1,3]");
		assertEquals(0.0f, product.a3(), eps, "inv*m [2,0]");
		assertEquals(0.0f, product.b3(), eps, "inv*m [2,1]");
		assertEquals(1.0f, product.c3(), eps, "inv*m [2,2]");
		assertEquals(0.0f, product.d3(), eps, "inv*m [2,3]");
		assertEquals(0.0f, product.a4(), eps, "inv*m [3,0]");
		assertEquals(0.0f, product.b4(), eps, "inv*m [3,1]");
		assertEquals(0.0f, product.c4(), eps, "inv*m [3,2]");
		assertEquals(1.0f, product.d4(), eps, "inv*m [3,3]");
	}

	@Test
	@Order(6)
	public void invertRoundTrip() {
		// Verify: m.invert().invert() ≈ m for a non-symmetric matrix
		final Matrix4f m = new Matrix4f(
				1, 2, 0, 1,
				0, 1, 3, 0,
				2, 0, 1, 4,
				1, 3, 0, 1);
		final Matrix4f roundTrip = m.invert().invert();
		final float eps = 1e-4f;
		assertEquals(m.a1(), roundTrip.a1(), eps);
		assertEquals(m.b1(), roundTrip.b1(), eps);
		assertEquals(m.c1(), roundTrip.c1(), eps);
		assertEquals(m.d1(), roundTrip.d1(), eps);
		assertEquals(m.a2(), roundTrip.a2(), eps);
		assertEquals(m.b2(), roundTrip.b2(), eps);
		assertEquals(m.c2(), roundTrip.c2(), eps);
		assertEquals(m.d2(), roundTrip.d2(), eps);
		assertEquals(m.a3(), roundTrip.a3(), eps);
		assertEquals(m.b3(), roundTrip.b3(), eps);
		assertEquals(m.c3(), roundTrip.c3(), eps);
		assertEquals(m.d3(), roundTrip.d3(), eps);
		assertEquals(m.a4(), roundTrip.a4(), eps);
		assertEquals(m.b4(), roundTrip.b4(), eps);
		assertEquals(m.c4(), roundTrip.c4(), eps);
		assertEquals(m.d4(), roundTrip.d4(), eps);
	}
	
}
