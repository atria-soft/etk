package org.atriasoft.etk.math;

import edu.umd.cs.findbugs.annotations.CheckReturnValue;

public record Matrix3f(
		float a1,
		float a2,
		float a3,
		float b1,
		float b2,
		float b3,
		float c1,
		float c2,
		float c3) {

	/**
	 * create a skew-symmetric matrix using a given vector that can be used to compute cross product with another vector using matrix multiplication
	 * @param vector Vector to comute
	 * @return Matrix to compute
	 */
	public static Matrix3f computeSkewSymmetricMatrixForCrossProduct(final Vector3f vector) {
		return new Matrix3f(0.0f, -vector.z(), vector.y(), vector.z(), 0.0f, -vector.x(), -vector.y(), vector.x(),
				0.0f);
	}

	/**
	* Create a matrix 3D with a simple rotation
	* @param normal vector aroud witch apply the rotation
	* @param angleRad Radian angle to set at the matrix
	* @return New matrix of the transformation requested
	*/
	public static Matrix3f createMatrixRotate(final Vector3f normal, final float angleRad) {
		final float cosVal = (float) Math.cos(angleRad);
		final float sinVal = (float) Math.sin(angleRad);
		final float invVal = 1.0f - cosVal;
		// set rotation :
		final float a1 = normal.x() * normal.x() * invVal + cosVal;
		final float a2 = normal.x() * normal.y() * invVal - normal.z() * sinVal;
		final float a3 = normal.x() * normal.z() * invVal + normal.y() * sinVal;

		final float b1 = normal.y() * normal.x() * invVal + normal.z() * sinVal;
		final float b2 = normal.y() * normal.y() * invVal + cosVal;
		final float b3 = normal.y() * normal.z() * invVal - normal.x() * sinVal;

		final float c1 = normal.z() * normal.x() * invVal - normal.y() * sinVal;
		final float c2 = normal.z() * normal.y() * invVal + normal.x() * sinVal;
		final float c3 = normal.z() * normal.z() * invVal + cosVal;
		return new Matrix3f(a1, a2, a3, b1, b2, b3, c1, c2, c3);
	}

	/**
	 * create a Identity matrix
	 * @return created new matrix
	 */
	public static final Matrix3f IDENTITY = new Matrix3f(1, 0, 0, 0, 1, 0, 0, 0, 1);

	/**
	 * create a ZERO matrix
	 * @return created new matrix
	 */
	public static final Matrix3f ZERO = new Matrix3f(0, 0, 0, 0, 0, 0, 0, 0, 0);

	/**
	 * Configuration constructorwith single value.
	 * @param value single value
	 */
	public Matrix3f(final float value) {
		this(value, value, value, value, value, value, value, value, value);
	}

	/**
	 * Configuration constructor.
	 * @param a1 element 0x0
	 * @param a2 element 0x1
	 * @param a3 element 0x2
	 * @param b1 element 1x0
	 * @param b2 element 1x1
	 * @param b3 element 1x2
	 * @param c1 element 2x0
	 * @param c2 element 2x1
	 * @param c3 element 2x2
	 */
	public Matrix3f(final float a1, final float a2, final float a3, final float b1, final float b2, final float b3,
			final float c1, final float c2, final float c3) {
		this.a1 = a1;
		this.a2 = a2;
		this.a3 = a3;
		this.b1 = b1;
		this.b2 = b2;
		this.b3 = b3;
		this.c1 = c1;
		this.c2 = c2;
		this.c3 = c3;
	}

	/**
	 * absolutise the matrix
	 */
	@CheckReturnValue
	public Matrix3f abs() {
		return new Matrix3f(Math.abs(this.a1), Math.abs(this.a2), Math.abs(this.a3), Math.abs(this.b1),
				Math.abs(this.b2), Math.abs(this.b3), Math.abs(this.c1), Math.abs(this.c2), Math.abs(this.c3));
	}

	/**
	 * Operator+= Addition an other matrix with this one
	 * @param obj Reference on the external object
	 * @return Local reference of the vector additionned
	 */
	@CheckReturnValue
	public Matrix3f add(final Matrix3f obj) {
		return new Matrix3f(this.a1 + obj.a1, this.a2 + obj.a2, this.a3 + obj.a3, this.b1 + obj.b1, this.b2 + obj.b2,
				this.b3 + obj.b3, this.c1 + obj.c1, this.c2 + obj.c2, this.c3 + obj.c3);
	}

	// Return a skew-symmetric matrix using a given vector that can be used
	// to compute cross product with another vector using matrix multiplication
	@CheckReturnValue
	public Matrix3f computeSkewSymmetricMatrixForCrossProductNew(final Vector3f vector) {
		return new Matrix3f(0.0f, -vector.z(), vector.y(), vector.z(), 0, -vector.x(), -vector.y(), vector.x(), 0.0f);
	}

	/**
	 * Computes the determinant of the matrix.
	 * @return The determinent Value.
	 */
	@CheckReturnValue
	public float determinant() {
		return this.a1 * (this.b2 * this.c3 - this.c2 * this.b3) - this.a2 * (this.b1 * this.c3 - this.c1 * this.b3)
				+ this.a3 * (this.b1 * this.c2 - this.c1 * this.b2);
	}

	/**
	 * devide a value
	 * @param value value to devide all the matrix
	 */
	@CheckReturnValue
	public Matrix3f divide(final float value) {
		return new Matrix3f(this.a1 / value, this.a2 / value, this.a3 / value, this.b1 / value, this.b2 / value,
				this.b3 / value, this.c1 / value, this.c2 / value, this.c3 / value);
	}

	@CheckReturnValue
	public float get(final int iii) {
		return switch (iii) {
			case 0 -> this.a1;
			case 1 -> this.a2;
			case 2 -> this.a3;
			case 3 -> this.b1;
			case 4 -> this.b2;
			case 5 -> this.b3;
			case 6 -> this.c1;
			case 7 -> this.c2;
			case 8 -> this.c3;
			default -> 0;
		};
	}

	/**
	 * get the colom id values
	 * @param iii Id of the colomn
	 * @return Vector 3D vith the values
	 */
	@CheckReturnValue
	public Vector3f getColumn(final int iii) {
		if (iii == 0) {
			return new Vector3f(this.a1, this.b1, this.c1);
		} else if (iii == 1) {
			return new Vector3f(this.a2, this.b2, this.c2);
		}
		return new Vector3f(this.a3, this.b3, this.c3);
	}

	/**
	 * get the row id values
	 * @param iii Id of the row
	 * @return Vector 3D vith the values
	 */
	@CheckReturnValue
	public Vector3f getRow(final int iii) {
		if (iii == 0) {
			return new Vector3f(this.a1, this.a2, this.a3);
		} else if (iii == 1) {
			return new Vector3f(this.b1, this.b2, this.b3);
		}
		return new Vector3f(this.c1, this.c2, this.c3);
	}

	/**
	 * Calculate the trace of the matrix
	 * @return value of addition of all element in the diagonal
	 */
	@CheckReturnValue
	public float getTrace() {
		return (this.a1 + this.b2 + this.c3);
	}

	/**
	 * Inverts the current matrix.
	 * @note The determinant must be != 0, otherwithe the matrix can't be inverted.
	 */
	@CheckReturnValue
	public Matrix3f inverse() {
		final float det = determinant();
		//assert(Math.abs(det) > MACHINEEPSILON);
		return new Matrix3f((this.b2 * this.c3 - this.c2 * this.b3) / det,
				-(this.a2 * this.c3 - this.c2 * this.a3) / det, (this.a2 * this.b3 - this.a3 * this.b2) / det,
				-(this.b1 * this.c3 - this.c1 * this.b3) / det, (this.a1 * this.c3 - this.c1 * this.a3) / det,
				-(this.a1 * this.b3 - this.b1 * this.a3) / det, (this.b1 * this.c2 - this.c1 * this.b2) / det,
				-(this.a1 * this.c2 - this.c1 * this.a2) / det, (this.a1 * this.b2 - this.a2 * this.b1) / det);
	}

	// Overloaded operator for the negative of the matrix
	@CheckReturnValue
	public Matrix3f invert() {
		return new Matrix3f(-this.a1, -this.a2, -this.a3, -this.b1, -this.b2, -this.b3, -this.c1, -this.c2, -this.c3);
	}

	/**
	 * In-Equality compare operator with an other object.
	 * @param obj Reference on the comparing object
	 * @return true The Objects are NOT identical
	 * @return false The Objects are identical
	 */
	@CheckReturnValue
	public boolean isDifferent(final Matrix3f obj) {
		if (this.a1 != obj.a1 || this.a2 != obj.a2 || this.a3 != obj.a3 || this.b1 != obj.b1 || this.b2 != obj.b2
				|| this.b3 != obj.b3 || this.c1 != obj.c1 || this.c2 != obj.c2 || this.c3 != obj.c3) {
			return true;
		}
		return false;
	}

	/**
	 * Equality compare operator with an other object.
	 * @param obj Reference on the comparing object
	 * @return true The Objects are identical
	 * @return false The Objects are NOT identical
	 */
	@CheckReturnValue
	boolean isEqual(final Matrix3f obj) {
		if (this.a1 != obj.a1 || this.a2 != obj.a2 || this.a3 != obj.a3 || this.b1 != obj.b1 || this.b2 != obj.b2
				|| this.b3 != obj.b3 || this.c1 != obj.c1 || this.c2 != obj.c2 || this.c3 != obj.c3) {
			return false;
		}
		return true;
	}

	/**
	 * Operator-= Decrement an other matrix with this one
	 * @param obj Reference on the external object
	 * @return Local reference of the vector decremented
	 */
	@CheckReturnValue
	public Matrix3f less(final Matrix3f obj) {
		return new Matrix3f(this.a1 - obj.a1, this.a2 - obj.a2, this.a3 - obj.a3, this.b1 - obj.b1, this.b2 - obj.b2,
				this.b3 - obj.b3, this.c1 - obj.c1, this.c2 - obj.c2, this.c3 - obj.c3);
	}

	/**
	 * Operator*= Multiplication a value
	 * @param value value to multiply all the matrix
	 * @return Local reference of the vector multiplicated
	 */
	@CheckReturnValue
	public Matrix3f multiply(final float value) {
		return new Matrix3f(this.a1 * value, this.a2 * value, this.a3 * value, this.b1 * value, this.b2 * value,
				this.b3 * value, this.c1 * value, this.c2 * value, this.c3 * value);
	}

	/**
	 * Operator*= Multiplication an other matrix with this one
	 * @param obj Reference on the external object
	 * @return Local reference of the vector multiplied
	 */
	@CheckReturnValue
	public Matrix3f multiply(final Matrix3f obj) {
		final float a1 = this.a1 * obj.a1 + this.a2 * obj.b1 + this.a3 * obj.c1;
		final float b1 = this.b1 * obj.a1 + this.b2 * obj.b1 + this.b3 * obj.c1;
		final float c1 = this.c1 * obj.a1 + this.c2 * obj.b1 + this.c3 * obj.c1;
		final float a2 = this.a1 * obj.a2 + this.a2 * obj.b2 + this.a3 * obj.c2;
		final float b2 = this.b1 * obj.a2 + this.b2 * obj.b2 + this.b3 * obj.c2;
		final float c2 = this.c1 * obj.a2 + this.c2 * obj.b2 + this.c3 * obj.c2;
		final float tmpA3 = this.a1 * obj.a3 + this.a2 * obj.b3 + this.a3 * obj.c3;
		final float tmpB3 = this.b1 * obj.a3 + this.b2 * obj.b3 + this.b3 * obj.c3;
		final float tmpC3 = this.c1 * obj.a3 + this.c2 * obj.b3 + this.c3 * obj.c3;
		return new Matrix3f(a1, a2, tmpA3, b1, b2, tmpB3, c1, c2, tmpC3);
	}

	/**
	 * Operator* apply matrix on a vector
	 * @param point Point value to apply the matrix
	 * @return New vector containing the value
	 */
	@CheckReturnValue
	public Vector3f multiply(final Vector3f point) {
		return new Vector3f(
				(float) (point.x() * (double) this.a1 + point.y() * (double) this.a2 + point.z() * (double) this.a3),
				(float) (point.x() * (double) this.b1 + point.y() * (double) this.b2 + point.z() * (double) this.b3),
				(float) (point.x() * (double) this.c1 + point.y() * (double) this.c2 + point.z() * (double) this.c3));
	}

	@Override
	public String toString() {
		return "Matrix3f(" + FMath.floatToString(this.a1) + "," + FMath.floatToString(this.a2) + ","
				+ FMath.floatToString(this.a3) + "," + FMath.floatToString(this.b1) + "," + FMath.floatToString(this.b2)
				+ "," + FMath.floatToString(this.b3) + "," + FMath.floatToString(this.c1) + ","
				+ FMath.floatToString(this.c2) + "," + FMath.floatToString(this.c3) + ")";
	}

	/**
	 * get a transpose matrix of this one.
	 * @return the transpose matrix
	 */
	@CheckReturnValue
	public Matrix3f transpose() {
		return new Matrix3f(this.a1, this.b1, this.c1, this.a2, this.b2, this.c2, this.a3, this.b3, this.c3);
	}
}
