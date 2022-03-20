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
	
}
