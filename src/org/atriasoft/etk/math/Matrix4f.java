package org.atriasoft.etk.math;

public class Matrix4f {
	/**
	* @brief Create projection matrix with the box parameter (camera view in -z axis)
	* @param xmin X minimum size of the frustum
	* @param xmax X maximum size of the frustum
	* @param ymin Y minimum size of the frustum
	* @param ymax Y maximum size of the frustum
	* @param zNear Z minimum size of the frustum
	* @param zFar Z maximum size of the frustum
	* @return New matrix of the transformation requested
	*/
	public static Matrix4f createMatrixFrustum(final float xmin, final float xmax, final float ymin, final float ymax, final float zNear, final float zFar) {
		final Matrix4f tmp = new Matrix4f();
		for (int iii = 0; iii < 4 * 4; iii++) {
			tmp.mat[iii] = 0;
		}
		//  0  1  2  3
		//  4  5  6  7
		//  8  9 10 11
		// 12 13 14 15
		tmp.mat[0] = (2.0f * zNear) / (xmax - xmin);
		tmp.mat[5] = (2.0f * zNear) / (ymax - ymin);
		tmp.mat[10] = -(zFar + zNear) / (zFar - zNear);
		tmp.mat[2] = (xmax + xmin) / (xmax - xmin);
		tmp.mat[6] = (ymax + ymin) / (ymax - ymin);
		tmp.mat[14] = -1.0f;
		tmp.mat[11] = -(2.0f * zFar * zNear) / (zFar - zNear);
		return tmp;
	}
	
	/**
	* @brief Create projection matrix with camera property (camera view in -z axis)
	* @param eye Optical center of the camera
	* @param target Point of where the camera is showing
	* @param up Up vector of the camera
	* @return New matrix of the transformation requested
	*/
	public static Matrix4f createMatrixLookAt(final Vector3f eye, final Vector3f target, final Vector3f up) {
		final Matrix4f tmp = new Matrix4f();
		
		final Vector3f forward = eye;
		forward.less(target);
		forward.safeNormalize();
		final Vector3f xaxis = target.cross(up.normalizeNew());
		xaxis.safeNormalize();
		final Vector3f up2 = xaxis.cross(forward);
		xaxis.safeNormalize();
		
		tmp.mat[0] = xaxis.x;
		tmp.mat[1] = up2.x;
		tmp.mat[2] = forward.x;
		tmp.mat[3] = eye.x;
		
		tmp.mat[4] = xaxis.y;
		tmp.mat[5] = up2.y;
		tmp.mat[6] = forward.y;
		tmp.mat[7] = eye.y;
		
		tmp.mat[8] = xaxis.z;
		tmp.mat[9] = up2.z;
		tmp.mat[10] = forward.z;
		tmp.mat[11] = eye.z;
		
		tmp.mat[12] = 0.0f;
		tmp.mat[13] = 0.0f;
		tmp.mat[14] = 0.0f;
		tmp.mat[15] = 1.0f;
		return tmp;
	}
	
	/**
	* @brief Create orthogonal projection matrix with the box parameter (camera view in -z axis)
	* @param left left size of the camera
	* @param right Right size of the camera
	* @param bottom Buttom size of the camera
	* @param top Top Size of the camera
	* @param nearVal Z near size of the camera
	* @param farVal Z far size of the camera
	* @return New matrix of the transformation requested
	*/
	public static Matrix4f createMatrixOrtho(final float left, final float right, final float bottom, final float top, final float nearVal, final float farVal) {
		final Matrix4f tmp = new Matrix4f();
		for (int iii = 0; iii < 4 * 4; iii++) {
			tmp.mat[iii] = 0;
		}
		tmp.mat[0] = 2.0f / (right - left);
		tmp.mat[5] = 2.0f / (top - bottom);
		tmp.mat[10] = -2.0f / (farVal - nearVal);
		tmp.mat[3] = -1 * (right + left) / (right - left);
		tmp.mat[7] = -1 * (top + bottom) / (top - bottom);
		tmp.mat[11] = -1 * (farVal + nearVal) / (farVal - nearVal);
		tmp.mat[15] = 1;
		return tmp;
	}
	
	/**
	* @brief Create projection matrix with human repensentation view (camera view in -z axis)
	* @param foxy Focal in radian of the camera
	* @param aspect aspect ratio of the camera
	* @param zNear Z near size of the camera
	* @param zFar Z far size of the camera
	* @return New matrix of the transformation requested
	*/
	public static Matrix4f createMatrixPerspective(final float foxy, final float aspect, final float zNear, final float zFar) {
		//TKDEBUG("drax perspective: foxy=" << foxy << "->" << aspect << "  " << zNear << "->" << zFar);
		final float xmax = zNear * (float) Math.tan(foxy / 2.0);
		final float xmin = -xmax;
		
		final float ymin = xmin / aspect;
		final float ymax = xmax / aspect;
		//TKDEBUG("drax perspective: " << xmin << "->" << xmax << " & " << ymin << "->" << ymax << " " << zNear << "->" << zFar);
		return createMatrixFrustum(xmin, xmax, ymin, ymax, zNear, zFar);
	}
	
	/**
	* @brief Create a matrix 3D with a simple rotation
	* @param normal vector aroud witch apply the rotation
	* @param angleRad Radian angle to set at the matrix
	* @return New matrix of the transformation requested
	*/
	public static Matrix4f createMatrixRotate(final Vector3f normal, final float angleRad) {
		final Matrix4f tmp = new Matrix4f();
		final float cosVal = (float) Math.cos(angleRad);
		final float sinVal = (float) Math.sin(angleRad);
		final float invVal = 1.0f - cosVal;
		// set rotation : 
		tmp.mat[0] = normal.x * normal.x * invVal + cosVal;
		tmp.mat[1] = normal.x * normal.y * invVal - normal.z * sinVal;
		tmp.mat[2] = normal.x * normal.z * invVal + normal.y * sinVal;
		
		tmp.mat[4] = normal.y * normal.x * invVal + normal.z * sinVal;
		tmp.mat[5] = normal.y * normal.y * invVal + cosVal;
		tmp.mat[6] = normal.y * normal.z * invVal - normal.x * sinVal;
		
		tmp.mat[8] = normal.z * normal.x * invVal - normal.y * sinVal;
		tmp.mat[9] = normal.z * normal.y * invVal + normal.x * sinVal;
		tmp.mat[10] = normal.z * normal.z * invVal + cosVal;
		return tmp;
	}
	
	//! @notindoc
	public static Matrix4f createMatrixRotate2(final Vector3f vect) {
		return createMatrixLookAt(vect, new Vector3f(0, 0, 0), new Vector3f(0, 1, 0));
	}
	
	/**
	* @brief Create a matrix 3D with a simple scale
	* @param scale 3 dimention scale
	* @return New matrix of the transformation requested
	*/
	public static Matrix4f createMatrixScale(final Vector3f scale) {
		final Matrix4f tmp = new Matrix4f();
		tmp.scale(scale);
		return tmp;
	}
	
	/**
	* @brief Create a matrix 3D with a simple translation
	* @param translate 3 dimention translation
	* @return New matrix of the transformation requested
	*/
	public static Matrix4f createMatrixTranslate(final Vector3f translate) {
		final Matrix4f tmp = new Matrix4f();
		// set translation :
		tmp.mat[3] = translate.x;
		tmp.mat[7] = translate.y;
		tmp.mat[11] = translate.z;
		return tmp;
	}
	
	public static Matrix4f identity() {
		final Matrix4f tmp = new Matrix4f();
		tmp.setIdentity();
		return tmp;
	}
	
	public float[] mat = new float[4 * 4]; //!< matrix data
	
	/**
	 * @brief Constructor that load identity
	 */
	public Matrix4f() {
		setIdentity();
	}
	
	/**
	 * @brief Configuration constructor.
	 * @param a1 1st colomn, 1 line value
	 * @param b1 2nd colomn, 1 line value
	 * @param c1 3rd colomn, 1 line value
	 * @param d1 4th colomn, 1 line value
	 * @param a2 1st colomn, 2 line value
	 * @param b2 2nd colomn, 2 line value
	 * @param c2 3rd colomn, 2 line value
	 * @param d2 4th colomn, 2 line value
	 * @param a3 1st colomn, 3 line value
	 * @param b3 2nd colomn, 3 line value
	 * @param c3 3rd colomn, 3 line value
	 * @param d3 4th colomn, 3 line value
	 * @param a4 1st colomn, 4 line value
	 * @param b4 2nd colomn, 4 line value
	 * @param c4 3rd colomn, 4 line value
	 * @param d4 4th colomn, 4 line value
	 */
	public Matrix4f(final float a1, final float b1, final float c1, final float d1, final float a2, final float b2, final float c2, final float d2, final float a3, final float b3, final float c3,
			final float d3, final float a4, final float b4, final float c4, final float d4) {
		this.mat[0] = a1;
		this.mat[1] = b1;
		this.mat[2] = c1;
		this.mat[3] = d1;
		this.mat[4] = a2;
		this.mat[5] = b2;
		this.mat[6] = c2;
		this.mat[7] = d2;
		this.mat[8] = a3;
		this.mat[9] = b3;
		this.mat[10] = c3;
		this.mat[11] = d3;
		this.mat[12] = a4;
		this.mat[13] = b4;
		this.mat[14] = c4;
		this.mat[15] = d4;
	}
	
	/**
	 * @brief Configuration constructor.
	 * @param values vector of values
	 */
	public Matrix4f(final float[] values) {
		if (values == null) {
			setIdentity();
			return;
		}
		for (int iii = 0; iii < 16; ++iii) {
			this.mat[iii] = values[iii];
		}
	}
	
	public Matrix4f(final Matrix3f matrix) {
		this.mat[0] = matrix.mat[0];
		this.mat[1] = matrix.mat[1];
		this.mat[2] = matrix.mat[2];
		this.mat[3] = 0;
		this.mat[4] = matrix.mat[3];
		this.mat[5] = matrix.mat[4];
		this.mat[6] = matrix.mat[5];
		this.mat[7] = 0;
		this.mat[8] = matrix.mat[6];
		this.mat[9] = matrix.mat[7];
		this.mat[10] = matrix.mat[8];
		this.mat[11] = 0;
		this.mat[12] = 0;
		this.mat[13] = 0;
		this.mat[14] = 0;
		this.mat[15] = 1;
		//		this.mat[0] = matrix.mat[0];
		//		this.mat[1] = matrix.mat[3];
		//		this.mat[2] = matrix.mat[6];
		//		this.mat[3] = 0;
		//		this.mat[4] = matrix.mat[1];
		//		this.mat[5] = matrix.mat[4];
		//		this.mat[6] = matrix.mat[7];
		//		this.mat[7] = 0;
		//		this.mat[8] = matrix.mat[2];
		//		this.mat[9] = matrix.mat[5];
		//		this.mat[10] = matrix.mat[8];
		//		this.mat[11] = 0;
		//		this.mat[12] = 0;
		//		this.mat[13] = 0;
		//		this.mat[14] = 0;
		//		this.mat[15] = 1;
	}
	
	// copy contructor:
	public Matrix4f(final Matrix4f matrix) {
		for (int iii = 0; iii < 16; ++iii) {
			this.mat[iii] = matrix.mat[iii];
		}
	}
	
	/**
	 * @brief Operator+= Addition an other matrix with this one
	 * @param obj Reference on the external object
	 */
	public void add(final Matrix4f obj) {
		for (int iii = 0; iii < 4 * 4; ++iii) {
			this.mat[iii] += obj.mat[iii];
		}
	}
	
	/**
	 * @brief Operator= Asign the current object with an other object
	 * @param obj Reference on the external object
	 */
	@Override
	public Matrix4f clone() {
		return new Matrix4f(this);
	}
	
	/**
	 * @brief Computes a cofactor. Used for matrix inversion.
	 * @param row Id of raw.
	 * @param col Id of colomn.
	 * @return the coFactorValue.
	 */
	public float coFactor(final int row, final int col) {
		return ((this.mat[((row + 1) & 3) * 4 + ((col + 1) & 3)] * this.mat[((row + 2) & 3) * 4 + ((col + 2) & 3)] * this.mat[((row + 3) & 3) * 4 + ((col + 3) & 3)]
				+ this.mat[((row + 1) & 3) * 4 + ((col + 2) & 3)] * this.mat[((row + 2) & 3) * 4 + ((col + 3) & 3)] * this.mat[((row + 3) & 3) * 4 + ((col + 1) & 3)]
				+ this.mat[((row + 1) & 3) * 4 + ((col + 3) & 3)] * this.mat[((row + 2) & 3) * 4 + ((col + 1) & 3)] * this.mat[((row + 3) & 3) * 4 + ((col + 2) & 3)])
				- (this.mat[((row + 3) & 3) * 4 + ((col + 1) & 3)] * this.mat[((row + 2) & 3) * 4 + ((col + 2) & 3)] * this.mat[((row + 1) & 3) * 4 + ((col + 3) & 3)]
						+ this.mat[((row + 3) & 3) * 4 + ((col + 2) & 3)] * this.mat[((row + 2) & 3) * 4 + ((col + 3) & 3)] * this.mat[((row + 1) & 3) * 4 + ((col + 1) & 3)]
						+ this.mat[((row + 3) & 3) * 4 + ((col + 3) & 3)] * this.mat[((row + 2) & 3) * 4 + ((col + 1) & 3)] * this.mat[((row + 1) & 3) * 4 + ((col + 2) & 3)]))
				* (((row + col) & 1) == 1 ? -1.0f : +1.0f);
	}
	
	/**
	 * @brief Operator-= Decrement an other matrix with this one
	 * @param obj Reference on the external object
	 */
	public void decrement(final Matrix4f obj) {
		for (int iii = 0; iii < 4 * 4; ++iii) {
			this.mat[iii] -= obj.mat[iii];
		}
	}
	
	/**
	 * @brief Computes the determinant of the matrix.
	 * @return The determinent Value.
	 */
	public float determinant() {
		return this.mat[0] * coFactor(0, 0) + this.mat[1] * coFactor(0, 1) + this.mat[2] * coFactor(0, 2) + this.mat[3] * coFactor(0, 3);
	}
	
	public float[] getTable() {
		return this.mat;
	}
	
	/**
	 * @brief Inverts the matrix.
	 * @note The determinant must be != 0, otherwithe the matrix can't be inverted.
	 * @return The inverted matrix.
	 */
	public Matrix4f invertNew() {
		final float det = determinant();
		if (Math.abs(det) < (1.0e-7f)) {
			// The matrix is not invertible! Singular case!
			return clone();
		}
		final Matrix4f temp = new Matrix4f();
		final float iDet = 1.0f / det;
		temp.mat[0] = coFactor(0, 0) * iDet;
		temp.mat[1] = coFactor(0, 1) * iDet;
		temp.mat[2] = coFactor(0, 2) * iDet;
		temp.mat[3] = coFactor(0, 3) * iDet;
		temp.mat[4] = coFactor(1, 0) * iDet;
		temp.mat[5] = coFactor(1, 1) * iDet;
		temp.mat[6] = coFactor(1, 2) * iDet;
		temp.mat[7] = coFactor(1, 3) * iDet;
		temp.mat[8] = coFactor(2, 0) * iDet;
		temp.mat[9] = coFactor(2, 1) * iDet;
		temp.mat[10] = coFactor(2, 2) * iDet;
		temp.mat[11] = coFactor(2, 3) * iDet;
		temp.mat[12] = coFactor(3, 0) * iDet;
		temp.mat[13] = coFactor(3, 1) * iDet;
		temp.mat[14] = coFactor(3, 2) * iDet;
		temp.mat[15] = coFactor(3, 3) * iDet;
		return temp;
	}
	
	/**
	 * @brief In-Equality compare operator with an other object.
	 * @param obj Reference on the comparing object
	 * @return true The Objects are NOT identical
	 * @return false The Objects are identical
	 */
	public boolean isDifferent(final Matrix4f obj) {
		for (int iii = 0; iii < 4 * 4; ++iii) {
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
	public boolean isEqual(final Matrix4f obj) {
		for (int iii = 0; iii < 4 * 4; ++iii) {
			if (this.mat[iii] != obj.mat[iii]) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @brief Operator*= Multiplication an other matrix with this one
	 * @param obj Reference on the external object
	 */
	public Matrix4f multiply(final Matrix4f obj) {
		// output Matrix
		final float[] matrixOut = new float[16];
		for (int xxx = 0; xxx < 4; xxx++) {
			for (int yyy = 0; yyy < 4; yyy++) {
				float value = 0;
				for (int kkk = 0; kkk < 4; kkk++) {
					value += this.mat[yyy * 4 + kkk] * obj.mat[kkk * 4 + xxx];
				}
				matrixOut[yyy * 4 + xxx] = value;
			}
		}
		// set it at the output
		for (int iii = 0; iii < 4 * 4; iii++) {
			this.mat[iii] = matrixOut[iii];
		}
		return this;
	}
	
	/**
	 * @brief Operator* apply matrix on a vector
	 * @param point Point value to apply the matrix
	 * @return New vector containing the value
	 */
	public Vector3f multiply(final Vector3f point) {
		return new Vector3f(this.mat[0] * point.x + this.mat[1] * point.y + this.mat[2] * point.z + this.mat[3], this.mat[4] * point.x + this.mat[5] * point.y + this.mat[6] * point.z + this.mat[7],
				this.mat[8] * point.x + this.mat[9] * point.y + this.mat[10] * point.z + this.mat[11]);
	}
	
	public Matrix4f multiplyNew(final Matrix4f obj) {
		return clone().multiply(obj);
	}
	
	/**
	 * @brief Makes a rotation matrix about an arbitrary axis.
	 * @param vect vector to apply the angle.
	 * @param angleRad angle to apply.
	 */
	public Matrix4f rotate(final Vector3f vect, final float angleRad) {
		final Matrix4f tmpMat = createMatrixRotate(vect, angleRad);
		return this.multiply(tmpMat);
	}
	
	public Matrix4f rotateNew(final Vector3f vect, final float angleRad) {
		final Matrix4f tmpMat = createMatrixRotate(vect, angleRad);
		return multiplyNew(tmpMat);
	}
	
	/**
	 * @brief Scale the current Matrix in all direction with 1 value.
	 * @param scale Scale XYZ value to apply.
	 */
	public Matrix4f scale(final float scale) {
		return scale(scale, scale, scale);
	}
	
	/**
	 * @brief Scale the current Matrix.
	 * @param sx Scale X value to apply.
	 * @param sy Scale Y value to apply.
	 * @param sz Scale Z value to apply.
	 */
	public Matrix4f scale(final float sx, final float sy, final float sz) {
		this.mat[0] *= sx;
		this.mat[1] *= sy;
		this.mat[2] *= sz;
		this.mat[4] *= sx;
		this.mat[5] *= sy;
		this.mat[6] *= sz;
		this.mat[8] *= sx;
		this.mat[9] *= sy;
		this.mat[10] *= sz;
		return this;
	}
	
	/**
	 * @brief Scale the current Matrix.
	 * @param vect Scale vector to apply.
	 */
	public Matrix4f scale(final Vector3f vect) {
		return scale(vect.x, vect.y, vect.z);
	}
	
	public Matrix4f scaleNew(final float scale) {
		return clone().scale(scale, scale, scale);
	}
	
	public Matrix4f scaleNew(final float sx, final float sy, final float sz) {
		return clone().scale(sx, sy, sz);
	}
	
	public Matrix4f scaleNew(final Vector3f vect) {
		return clone().scale(vect.x, vect.y, vect.z);
	}
	
	/**
	 * @brief Operator= Asign the current object with an other object
	 * @param obj Reference on the external object
	 */
	public void set(final Matrix4f obj) {
		for (int iii = 0; iii < 16; iii++) {
			this.mat[iii] = obj.mat[iii];
		}
	}
	
	/**
	 * @brief configure identity of the matrix
	 */
	public void setIdentity() {
		for (int iii = 0; iii < 4 * 4; iii++) {
			this.mat[iii] = 0;
		}
		this.mat[0] = 1.0f;
		this.mat[5] = 1.0f;
		this.mat[10] = 1.0f;
		this.mat[15] = 1.0f;
	}
	
	/**
	 * @brief Makes a translation of the matrix
	 * @param vect Translation to apply.
	 */
	public Matrix4f translate(final Vector3f vect) {
		final Matrix4f tmpMat = createMatrixTranslate(vect);
		return this.multiply(tmpMat);
	}
	
	public Matrix4f translateNew(final Vector3f vect) {
		final Matrix4f tmpMat = createMatrixTranslate(vect);
		return multiplyNew(tmpMat);
	}
	
	/**
	 * @brief Transpose the current matix (usefull for OpenGL display)
	 */
	public Matrix4f transpose() {
		float tmpVal = this.mat[1];
		this.mat[1] = this.mat[4];
		this.mat[4] = tmpVal;
		
		tmpVal = this.mat[2];
		this.mat[2] = this.mat[8];
		this.mat[8] = tmpVal;
		
		tmpVal = this.mat[6];
		this.mat[6] = this.mat[9];
		this.mat[9] = tmpVal;
		
		tmpVal = this.mat[3];
		this.mat[3] = this.mat[12];
		this.mat[12] = tmpVal;
		
		tmpVal = this.mat[7];
		this.mat[7] = this.mat[13];
		this.mat[13] = tmpVal;
		
		tmpVal = this.mat[11];
		this.mat[11] = this.mat[14];
		this.mat[14] = tmpVal;
		return this;
	}
	
	public Matrix4f transposeNew() {
		return clone().transpose();
	}
	
}
