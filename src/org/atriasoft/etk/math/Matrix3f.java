package org.atriasoft.etk.math;

public class Matrix3f {
	/**
	 * @brief create a skew-symmetric matrix using a given vector that can be used to compute cross product with another vector using matrix multiplication
	 * @param vector Vector to comute
	 * @return Matrix to compute
	 */
	public static Matrix3f computeSkewSymmetricMatrixForCrossProduct(final Vector3f vector) {
		return new Matrix3f(0.0f, -vector.z, vector.y, vector.z, 0.0f, -vector.x, -vector.y, vector.x, 0.0f);
	}
	
	/**
	* @brief Create a matrix 3D with a simple rotation
	* @param normal vector aroud witch apply the rotation
	* @param angleRad Radian angle to set at the matrix
	* @return New matrix of the transformation requested
	*/
	public static Matrix3f createMatrixRotate(final Vector3f normal, final float angleRad) {
		final Matrix3f tmp = new Matrix3f();
		final float cosVal = (float) Math.cos(angleRad);
		final float sinVal = (float) Math.sin(angleRad);
		final float invVal = 1.0f - cosVal;
		// set rotation : 
		tmp.mat[0] = normal.x * normal.x * invVal + cosVal;
		tmp.mat[1] = normal.x * normal.y * invVal - normal.z * sinVal;
		tmp.mat[2] = normal.x * normal.z * invVal + normal.y * sinVal;
		
		tmp.mat[3] = normal.y * normal.x * invVal + normal.z * sinVal;
		tmp.mat[4] = normal.y * normal.y * invVal + cosVal;
		tmp.mat[5] = normal.y * normal.z * invVal - normal.x * sinVal;
		
		tmp.mat[6] = normal.z * normal.x * invVal - normal.y * sinVal;
		tmp.mat[7] = normal.z * normal.y * invVal + normal.x * sinVal;
		tmp.mat[8] = normal.z * normal.z * invVal + cosVal;
		return tmp;
	}
	
	/**
	 * @brief create a Identity matrix
	 * @return created new matrix
	 */
	public static Matrix3f identity() {
		return new Matrix3f(1, 0, 0, 0, 1, 0, 0, 0, 1);
	}
	
	/**
	 * @brief create a ZERO matrix
	 * @return created new matrix
	 */
	public static Matrix3f zero() {
		return new Matrix3f(0, 0, 0, 0, 0, 0, 0, 0, 0);
	}
	
	public float[] mat = new float[3 * 3]; //!< matrix data
	
	/**
	 * @brief Constructor that load zero matrix
	 */
	public Matrix3f() {
		this.mat[0] = 0.0f;
		this.mat[1] = 0.0f;
		this.mat[2] = 0.0f;
		this.mat[3] = 0.0f;
		this.mat[4] = 0.0f;
		this.mat[5] = 0.0f;
		this.mat[6] = 0.0f;
		this.mat[7] = 0.0f;
		this.mat[8] = 0.0f;
	}
	
	/**
	 * @brief Configuration constructorwith single value.
	 * @param value single value
	 */
	public Matrix3f(final float value) {
		this.mat[0] = value;
		this.mat[1] = value;
		this.mat[2] = value;
		this.mat[3] = value;
		this.mat[4] = value;
		this.mat[5] = value;
		this.mat[6] = value;
		this.mat[7] = value;
		this.mat[8] = value;
	}
	
	/**
	 * @brief Configuration constructor.
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
	public Matrix3f(final float a1, final float a2, final float a3, final float b1, final float b2, final float b3, final float c1, final float c2, final float c3) {
		this.mat[0] = a1;
		this.mat[1] = a2;
		this.mat[2] = a3;
		this.mat[3] = b1;
		this.mat[4] = b2;
		this.mat[5] = b3;
		this.mat[6] = c1;
		this.mat[7] = c2;
		this.mat[8] = c3;
	}
	
	/**
	 * @brief Copy constructor.
	 * @param obj Matrix object to copy
	 */
	public Matrix3f(final Matrix3f obj) {
		this.mat[0] = obj.mat[0];
		this.mat[1] = obj.mat[1];
		this.mat[2] = obj.mat[2];
		this.mat[3] = obj.mat[3];
		this.mat[4] = obj.mat[4];
		this.mat[5] = obj.mat[5];
		this.mat[6] = obj.mat[6];
		this.mat[7] = obj.mat[7];
		this.mat[8] = obj.mat[8];
	}
	
	/**
	 * @brief absolutise the matrix
	 */
	public Matrix3f abs() {
		this.mat[0] = Math.abs(this.mat[0]);
		this.mat[1] = Math.abs(this.mat[1]);
		this.mat[2] = Math.abs(this.mat[2]);
		this.mat[3] = Math.abs(this.mat[3]);
		this.mat[4] = Math.abs(this.mat[4]);
		this.mat[5] = Math.abs(this.mat[5]);
		this.mat[6] = Math.abs(this.mat[6]);
		this.mat[7] = Math.abs(this.mat[7]);
		this.mat[8] = Math.abs(this.mat[8]);
		return this;
	}
	
	@Deprecated
	public void absolute() {
		this.mat[0] = Math.abs(this.mat[0]);
		this.mat[1] = Math.abs(this.mat[1]);
		this.mat[2] = Math.abs(this.mat[2]);
		this.mat[3] = Math.abs(this.mat[3]);
		this.mat[4] = Math.abs(this.mat[4]);
		this.mat[5] = Math.abs(this.mat[5]);
		this.mat[6] = Math.abs(this.mat[6]);
		this.mat[7] = Math.abs(this.mat[7]);
		this.mat[8] = Math.abs(this.mat[8]);
	}
	
	/**
	 * @brief Operator+= Addition an other matrix with this one
	 * @param obj Reference on the external object
	 * @return Local reference of the vector additionned
	 */
	public Matrix3f add(final Matrix3f obj) {
		for (int iii = 0; iii < 3 * 3; ++iii) {
			this.mat[iii] += obj.mat[iii];
		}
		return this;
	}
	
	/**
	 * @brief Operator+ Addition an other matrix with this one
	 * @param obj Reference on the external object
	 * @return New vector containing the value
	 */
	public Matrix3f addNew(final Matrix3f obj) {
		final Matrix3f tmp = new Matrix3f(this);
		tmp.add(obj);
		return tmp;
	}
	
	// Return a skew-symmetric matrix using a given vector that can be used
	// to compute cross product with another vector using matrix multiplication
	public Matrix3f computeSkewSymmetricMatrixForCrossProductNew(final Vector3f vector) {
		return new Matrix3f(0.0f, -vector.z, vector.y, vector.z, 0, -vector.x, -vector.y, vector.x, 0.0f);
	}
	
	/**
	 * @brief Computes the determinant of the matrix.
	 * @return The determinent Value.
	 */
	public float determinant() {
		return this.mat[0] * (this.mat[4] * this.mat[8] - this.mat[7] * this.mat[5]) - this.mat[1] * (this.mat[3] * this.mat[8] - this.mat[6] * this.mat[5])
				+ this.mat[2] * (this.mat[3] * this.mat[7] - this.mat[6] * this.mat[4]);
	}
	
	/**
	 * @brief devide a value
	 * @param value value to devide all the matrix
	 */
	public Matrix3f devide(final float value) {
		this.mat[0] /= value;
		this.mat[1] /= value;
		this.mat[2] /= value;
		this.mat[3] /= value;
		this.mat[4] /= value;
		this.mat[5] /= value;
		this.mat[6] /= value;
		this.mat[7] /= value;
		this.mat[8] /= value;
		return this;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Matrix3f other = (Matrix3f) obj;
		for (int iii = 0; iii < 3 * 3; ++iii) {
			if (Float.floatToIntBits(this.mat[iii]) != Float.floatToIntBits(other.mat[iii])) {
				return false;
			}
		}
		return true;
	}
	
	public float get(final int iii) {
		return this.mat[iii];
	}
	
	/**
	 * @brief get the matrix with the absolute value
	 * @return matix in absolute
	 */
	public Matrix3f getAbsolute() {
		return new Matrix3f(Math.abs(this.mat[0]), Math.abs(this.mat[1]), Math.abs(this.mat[2]), Math.abs(this.mat[3]), Math.abs(this.mat[4]), Math.abs(this.mat[5]), Math.abs(this.mat[6]),
				Math.abs(this.mat[7]), Math.abs(this.mat[8]));
	}
	
	/**
	 * @brief get the colom id values
	 * @param iii Id of the colomn
	 * @return Vector 3D vith the values
	 */
	public Vector3f getColumn(final int iii) {
		if (iii == 0) {
			return new Vector3f(this.mat[0], this.mat[3], this.mat[6]);
		} else if (iii == 1) {
			return new Vector3f(this.mat[1], this.mat[4], this.mat[7]);
		}
		return new Vector3f(this.mat[2], this.mat[5], this.mat[8]);
	}
	
	/**
	 * @brief get the row id values
	 * @param iii Id of the row
	 * @return Vector 3D vith the values
	 */
	public Vector3f getRow(final int iii) {
		if (iii == 0) {
			return new Vector3f(this.mat[0], this.mat[1], this.mat[2]);
		} else if (iii == 1) {
			return new Vector3f(this.mat[3], this.mat[4], this.mat[5]);
		}
		return new Vector3f(this.mat[6], this.mat[7], this.mat[8]);
	}
	
	/**
	 * @brief Calculate the trace of the matrix
	 * @return value of addition of all element in the diagonal
	 */
	public float getTrace() {
		return (this.mat[0] + this.mat[4] + this.mat[8]);
	}
	
	@Override
	public int hashCode() {
		int hash = 1542;
		hash += Float.floatToIntBits(this.mat[0]);
		hash += Float.floatToIntBits(this.mat[1]);
		hash += Float.floatToIntBits(this.mat[2]);
		hash += Float.floatToIntBits(this.mat[3]);
		hash += Float.floatToIntBits(this.mat[4]);
		hash += Float.floatToIntBits(this.mat[5]);
		hash += Float.floatToIntBits(this.mat[6]);
		hash += Float.floatToIntBits(this.mat[7]);
		hash += Float.floatToIntBits(this.mat[8]);
		return hash;
	}
	
	/**
	 * @brief Inverts the current matrix.
	 * @note The determinant must be != 0, otherwithe the matrix can't be inverted.
	 */
	public Matrix3f inverse() {
		final float det = determinant();
		//assert(Math.abs(det) > MACHINEEPSILON);
		this.set((this.mat[4] * this.mat[8] - this.mat[7] * this.mat[5]), -(this.mat[1] * this.mat[8] - this.mat[7] * this.mat[2]), (this.mat[1] * this.mat[5] - this.mat[2] * this.mat[4]),
				-(this.mat[3] * this.mat[8] - this.mat[6] * this.mat[5]), (this.mat[0] * this.mat[8] - this.mat[6] * this.mat[2]), -(this.mat[0] * this.mat[5] - this.mat[3] * this.mat[2]),
				(this.mat[3] * this.mat[7] - this.mat[6] * this.mat[4]), -(this.mat[0] * this.mat[7] - this.mat[6] * this.mat[1]), (this.mat[0] * this.mat[4] - this.mat[1] * this.mat[3]));
		devide(det);
		return this;
	}
	
	/**
	 * @brief Inverse the matrix.
	 * @note The determinant must be != 0, otherwithe the matrix can't be inverted.
	 * @return The inverted matrix.
	 */
	public Matrix3f inverseNew() {
		final Matrix3f tmp = new Matrix3f(this);
		tmp.inverse();
		return tmp;
	}
	
	// Overloaded operator for the negative of the matrix
	public Matrix3f invert() {
		this.mat[0] = -this.mat[0];
		this.mat[1] = -this.mat[1];
		this.mat[2] = -this.mat[2];
		this.mat[3] = -this.mat[3];
		this.mat[4] = -this.mat[4];
		this.mat[5] = -this.mat[5];
		this.mat[6] = -this.mat[6];
		this.mat[7] = -this.mat[7];
		this.mat[8] = -this.mat[8];
		return this;
	}
	
	/**
	 * @brief In-Equality compare operator with an other object.
	 * @param obj Reference on the comparing object
	 * @return true The Objects are NOT identical
	 * @return false The Objects are identical
	 */
	public boolean isDifferent(final Matrix3f obj) {
		for (int iii = 0; iii < 3 * 3; ++iii) {
			if (this.mat[iii] != obj.mat[iii]) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @brief Equality compare operator with an other object.
	 * @param obj Reference on the comparing object
	 * @return true The Objects are identical
	 * @return false The Objects are NOT identical
	 */
	boolean isEqual(final Matrix3f obj) {
		for (int iii = 0; iii < 3 * 3; ++iii) {
			if (this.mat[iii] != obj.mat[iii]) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @brief Operator-= Decrement an other matrix with this one
	 * @param obj Reference on the external object
	 * @return Local reference of the vector decremented
	 */
	public Matrix3f less(final Matrix3f obj) {
		for (int iii = 0; iii < 3 * 3; ++iii) {
			this.mat[iii] -= obj.mat[iii];
		}
		return this;
	}
	
	/**
	 * @brief Operator- Decrement an other matrix with this one
	 * @param obj Reference on the external object
	 * @return New vector containing the value
	 */
	public Matrix3f lessNew(final Matrix3f obj) {
		final Matrix3f tmp = new Matrix3f(this);
		tmp.less(obj);
		return tmp;
	}
	
	/**
	 * @brief Operator*= Multiplication a value
	 * @param value value to multiply all the matrix
	 * @return Local reference of the vector multiplicated
	 */
	public Matrix3f multiply(final float value) {
		this.mat[0] *= value;
		this.mat[1] *= value;
		this.mat[2] *= value;
		this.mat[3] *= value;
		this.mat[4] *= value;
		this.mat[5] *= value;
		this.mat[6] *= value;
		this.mat[7] *= value;
		this.mat[8] *= value;
		return this;
	}
	
	/**
	 * @brief Operator*= Multiplication an other matrix with this one
	 * @param obj Reference on the external object
	 * @return Local reference of the vector multiplicated
	 */
	public Matrix3f multiply(final Matrix3f obj) {
		final float a1 = this.mat[0] * obj.mat[0] + this.mat[1] * obj.mat[3] + this.mat[2] * obj.mat[6];
		final float b1 = this.mat[3] * obj.mat[0] + this.mat[4] * obj.mat[3] + this.mat[5] * obj.mat[6];
		final float c1 = this.mat[6] * obj.mat[0] + this.mat[7] * obj.mat[3] + this.mat[8] * obj.mat[6];
		final float a2 = this.mat[0] * obj.mat[1] + this.mat[1] * obj.mat[4] + this.mat[2] * obj.mat[7];
		final float b2 = this.mat[3] * obj.mat[1] + this.mat[4] * obj.mat[4] + this.mat[5] * obj.mat[7];
		final float c2 = this.mat[6] * obj.mat[1] + this.mat[7] * obj.mat[4] + this.mat[8] * obj.mat[7];
		this.mat[2] = this.mat[0] * obj.mat[2] + this.mat[1] * obj.mat[5] + this.mat[2] * obj.mat[8];
		this.mat[5] = this.mat[3] * obj.mat[2] + this.mat[4] * obj.mat[5] + this.mat[5] * obj.mat[8];
		this.mat[8] = this.mat[6] * obj.mat[2] + this.mat[7] * obj.mat[5] + this.mat[8] * obj.mat[8];
		this.mat[0] = a1;
		this.mat[3] = b1;
		this.mat[6] = c1;
		this.mat[1] = a2;
		this.mat[4] = b2;
		this.mat[7] = c2;
		return this;
	}
	
	/**
	 * @brief Operator*= Multiplication a value
	 * @param value value to multiply all the matrix
	 * @return Local reference of the vector multiplicated
	 */
	public Matrix3f multiplyNew(final float value) {
		final Matrix3f tmp = new Matrix3f(this);
		tmp.multiply(value);
		return tmp;
	}
	
	/**
	 * @brief Operator* Multiplication an other matrix with this one
	 * @param obj Reference on the external object
	 * @return New vector containing the value
	 */
	public Matrix3f multiplyNew(final Matrix3f obj) {
		final Matrix3f tmp = new Matrix3f(this);
		tmp.multiply(obj);
		return tmp;
	}
	
	/**
	 * @brief Operator* apply matrix on a vector
	 * @param point Point value to apply the matrix
	 * @return New vector containing the value
	 */
	public Vector3f multiplyNew(final Vector3f point) {
		return new Vector3f((float) ((double) point.x * (double) this.mat[0] + (double) point.y * (double) this.mat[1] + (double) point.z * (double) this.mat[2]),
				(float) ((double) point.x * (double) this.mat[3] + (double) point.y * (double) this.mat[4] + (double) point.z * (double) this.mat[5]),
				(float) ((double) point.x * (double) this.mat[6] + (double) point.y * (double) this.mat[7] + (double) point.z * (double) this.mat[8]));
		/*
		return new Vector3f(point.x * this.mat[0] + point.y * this.mat[1] + point.z * this.mat[2], point.x * this.mat[3] + point.y * this.mat[4] + point.z * this.mat[5],
				point.x * this.mat[6] + point.y * this.mat[7] + point.z * this.mat[8]);
		*/
	}
	
	/**
	 * @brief Operator*= Multiplication an other matrix with this one
	 * @param obj Reference on the external object
	 * @return Local reference of the vector multiplicated
	 */
	public void multiplyTo(final Matrix3f obj, final Matrix3f out) {
		out.mat[2] = this.mat[0] * obj.mat[2] + this.mat[1] * obj.mat[5] + this.mat[2] * obj.mat[8];
		out.mat[5] = this.mat[3] * obj.mat[2] + this.mat[4] * obj.mat[5] + this.mat[5] * obj.mat[8];
		out.mat[8] = this.mat[6] * obj.mat[2] + this.mat[7] * obj.mat[5] + this.mat[8] * obj.mat[8];
		out.mat[0] = this.mat[0] * obj.mat[0] + this.mat[1] * obj.mat[3] + this.mat[2] * obj.mat[6];
		out.mat[3] = this.mat[3] * obj.mat[0] + this.mat[4] * obj.mat[3] + this.mat[5] * obj.mat[6];
		out.mat[6] = this.mat[6] * obj.mat[0] + this.mat[7] * obj.mat[3] + this.mat[8] * obj.mat[6];
		out.mat[1] = this.mat[0] * obj.mat[1] + this.mat[1] * obj.mat[4] + this.mat[2] * obj.mat[7];
		out.mat[4] = this.mat[3] * obj.mat[1] + this.mat[4] * obj.mat[4] + this.mat[5] * obj.mat[7];
		out.mat[7] = this.mat[6] * obj.mat[1] + this.mat[7] * obj.mat[4] + this.mat[8] * obj.mat[7];
	}
	
	public void multiplyTo(final Vector3f point, final Vector3f out) {
		out.set(point.x * this.mat[0] + point.y * this.mat[1] + point.z * this.mat[2], point.x * this.mat[3] + point.y * this.mat[4] + point.z * this.mat[5],
				point.x * this.mat[6] + point.y * this.mat[7] + point.z * this.mat[8]);
	}
	
	/**
	 * @brief Set Value on the matrix
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
	public Matrix3f set(final float a1, final float a2, final float a3, final float b1, final float b2, final float b3, final float c1, final float c2, final float c3) {
		this.mat[0] = a1;
		this.mat[1] = a2;
		this.mat[2] = a3;
		this.mat[3] = b1;
		this.mat[4] = b2;
		this.mat[5] = b3;
		this.mat[6] = c1;
		this.mat[7] = c2;
		this.mat[8] = c3;
		return this;
	}
	
	/**
	 * @brief Operator= Asign the current object with an other object
	 * @param obj Reference on the external object
	 * @return Local reference of the vector asigned
	 */
	public Matrix3f set(final Matrix3f obj) {
		for (int iii = 0; iii < 3 * 3; ++iii) {
			this.mat[iii] = obj.mat[iii];
		}
		return this;
	}
	
	/**
	 * @brief Load Identity matrix
	 */
	public Matrix3f setIdentity() {
		this.mat[0] = 1.0f;
		this.mat[1] = 0.0f;
		this.mat[2] = 0.0f;
		this.mat[3] = 0.0f;
		this.mat[4] = 1.0f;
		this.mat[5] = 0.0f;
		this.mat[6] = 0.0f;
		this.mat[7] = 0.0f;
		this.mat[8] = 1.0f;
		return this;
	}
	
	/**
	 * @brief Load Zero matrix
	 */
	public Matrix3f setZero() {
		this.mat[0] = 0.0f;
		this.mat[1] = 0.0f;
		this.mat[2] = 0.0f;
		this.mat[3] = 0.0f;
		this.mat[4] = 0.0f;
		this.mat[5] = 0.0f;
		this.mat[6] = 0.0f;
		this.mat[7] = 0.0f;
		this.mat[8] = 0.0f;
		return this;
	}
	
	@Override
	public String toString() {
		return "Matrix3f(" + FMath.floatToString(this.mat[0]) + "," + FMath.floatToString(this.mat[1]) + "," + FMath.floatToString(this.mat[2]) + "," + FMath.floatToString(this.mat[3]) + ","
				+ FMath.floatToString(this.mat[4]) + "," + FMath.floatToString(this.mat[5]) + "," + FMath.floatToString(this.mat[6]) + "," + FMath.floatToString(this.mat[7]) + ","
				+ FMath.floatToString(this.mat[8]) + ")";
	}
	
	/**
	 * @brief Transpose the current matrix.
	 */
	public Matrix3f transpose() {
		float tmp = this.mat[1];
		this.mat[1] = this.mat[3];
		this.mat[3] = tmp;
		tmp = this.mat[2];
		this.mat[2] = this.mat[6];
		this.mat[6] = tmp;
		tmp = this.mat[5];
		this.mat[5] = this.mat[7];
		this.mat[7] = tmp;
		return this;
	}
	
	/**
	 * @brief get a transpose matrix of this one.
	 * @return the transpose matrix
	 */
	public Matrix3f transposeNew() {
		return new Matrix3f(this.mat[0], this.mat[3], this.mat[6], this.mat[1], this.mat[4], this.mat[7], this.mat[2], this.mat[5], this.mat[8]);
	}
}
