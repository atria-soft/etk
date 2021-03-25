package org.atriasoft.etk.math;

import edu.umd.cs.findbugs.annotations.CheckReturnValue;

public record Matrix4f(
		float a1,
		float b1,
		float c1,
		float d1,
		float a2,
		float b2,
		float c2,
		float d2,
		float a3,
		float b3,
		float c3,
		float d3,
		float a4,
		float b4,
		float c4,
		float d4) {
	/**
	* Create projection matrix with the box parameter (camera view in -z axis)
	* @param xmin X minimum size of the frustum
	* @param xmax X maximum size of the frustum
	* @param ymin Y minimum size of the frustum
	* @param ymax Y maximum size of the frustum
	* @param zNear Z minimum size of the frustum
	* @param zFar Z maximum size of the frustum
	* @return New matrix of the transformation requested
	*/
	public static Matrix4f createMatrixFrustum(final float xmin, final float xmax, final float ymin, final float ymax, final float zNear, final float zFar) {
		float a1 = 0;
		float b1 = 0;
		float c1 = 0;
		float d1 = 0;
		float a2 = 0;
		float b2 = 0;
		float c2 = 0;
		float d2 = 0;
		float a3 = 0;
		float b3 = 0;
		float c3 = 0;
		float d3 = 0;
		float a4 = 0;
		float b4 = 0;
		float c4 = 0;
		float d4 = 0;
		//  0  1  2  3
		//  4  5  6  7
		//  8  9 10 11
		// 12 13 14 15
		a1 = (2.0f * zNear) / (xmax - xmin);
		b2 = (2.0f * zNear) / (ymax - ymin);
		c3 = -(zFar + zNear) / (zFar - zNear);
		c1 = (xmax + xmin) / (xmax - xmin);
		c2 = (ymax + ymin) / (ymax - ymin);
		c4 = -1.0f;
		d3 = -(2.0f * zFar * zNear) / (zFar - zNear);
		return new Matrix4f(a1, b1, c1, d1, a2, b2, c2, d2, a3, b3, c3, d3, a4, b4, c4, d4);
	}
	
	/**
	* Create projection matrix with camera property (camera view in -z axis)
	* @param eye Optical center of the camera
	* @param target Point of where the camera is showing
	* @param up Up vector of the camera
	* @return New matrix of the transformation requested
	*/
	public static Matrix4f createMatrixLookAt(final Vector3f eye, final Vector3f target, final Vector3f up) {
		final Vector3f forward = eye.less(target).safeNormalize();
		Vector3f xaxis = target.cross(up.normalize());
		xaxis = xaxis.safeNormalize();
		final Vector3f up2 = xaxis.cross(forward);
		xaxis = xaxis.safeNormalize(); // TODO ??????
		
		float a1 = xaxis.x();
		float b1 = up2.x();
		float c1 = forward.x();
		float d1 = eye.x();
		
		float a2 = xaxis.y();
		float b2 = up2.y();
		float c2 = forward.y();
		float d2 = eye.y();
		
		float a3 = xaxis.z();
		float b3 = up2.z();
		float c3 = forward.z();
		float d3 = eye.z();
		
		float a4 = 0.0f;
		float b4 = 0.0f;
		float c4 = 0.0f;
		float d4 = 1.0f;
		return new Matrix4f(a1, b1, c1, d1, a2, b2, c2, d2, a3, b3, c3, d3, a4, b4, c4, d4);
	}
	
	/**
	* Create orthogonal projection matrix with the box parameter (camera view in -z axis)
	* @param left left size of the camera
	* @param right Right size of the camera
	* @param bottom Buttom size of the camera
	* @param top Top Size of the camera
	* @param nearVal Z near size of the camera
	* @param farVal Z far size of the camera
	* @return New matrix of the transformation requested
	*/
	public static Matrix4f createMatrixOrtho(final float left, final float right, final float bottom, final float top, final float nearVal, final float farVal) {
		float a1 = 1.0f;
		float b1 = 0;
		float c1 = 0;
		float d1 = 0;
		float a2 = 0;
		float b2 = 1.0f;
		float c2 = 0;
		float d2 = 0;
		float a3 = 0;
		float b3 = 0;
		float c3 = 1.0f;
		float d3 = 0;
		float a4 = 0;
		float b4 = 0;
		float c4 = 0;
		float d4 = 1.0f;
		a1 = 2.0f / (right - left);
		b2 = 2.0f / (top - bottom);
		c3 = -2.0f / (farVal - nearVal);
		d1 = -1 * (right + left) / (right - left);
		d2 = -1 * (top + bottom) / (top - bottom);
		d3 = -1 * (farVal + nearVal) / (farVal - nearVal);
		return new Matrix4f(a1, b1, c1, d1, a2, b2, c2, d2, a3, b3, c3, d3, a4, b4, c4, d4);
		
	}
	
	/**
	* Create projection matrix with human repensentation view (camera view in -z axis)
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
	* Create a matrix 3D with a simple rotation
	* @param normal vector aroud witch apply the rotation
	* @param angleRad Radian angle to set at the matrix
	* @return New matrix of the transformation requested
	*/
	public static Matrix4f createMatrixRotate(final Vector3f normal, final float angleRad) {
		float a1 = 1.0f;
		float b1 = 0;
		float c1 = 0;
		float d1 = 0;
		float a2 = 0;
		float b2 = 1.0f;
		float c2 = 0;
		float d2 = 0;
		float a3 = 0;
		float b3 = 0;
		float c3 = 1.0f;
		float d3 = 0;
		float a4 = 0;
		float b4 = 0;
		float c4 = 0;
		float d4 = 1.0f;
		
		final float cosVal = (float) Math.cos(angleRad);
		final float sinVal = (float) Math.sin(angleRad);
		final float invVal = 1.0f - cosVal;
		// set rotation : 
		a1 = normal.x() * normal.x() * invVal + cosVal;
		b1 = normal.x() * normal.y() * invVal - normal.z() * sinVal;
		c1 = normal.x() * normal.z() * invVal + normal.y() * sinVal;
		
		a2 = normal.y() * normal.x() * invVal + normal.z() * sinVal;
		b2 = normal.y() * normal.y() * invVal + cosVal;
		c2 = normal.y() * normal.z() * invVal - normal.x() * sinVal;
		
		a3 = normal.z() * normal.x() * invVal - normal.y() * sinVal;
		b3 = normal.z() * normal.y() * invVal + normal.x() * sinVal;
		c3 = normal.z() * normal.z() * invVal + cosVal;
		return new Matrix4f(a1, b1, c1, d1, a2, b2, c2, d2, a3, b3, c3, d3, a4, b4, c4, d4);
	}
	
	//! @notindoc
	public static Matrix4f createMatrixRotate2(final Vector3f vect) {
		return createMatrixLookAt(vect, new Vector3f(0, 0, 0), new Vector3f(0, 1, 0));
	}
	
	/**
	* Create a matrix 3D with a simple scale
	* @param scale 3 dimension scale
	* @return New matrix of the transformation requested
	*/
	public static Matrix4f createMatrixScale(final Vector3f scale) {
		return new Matrix4f(scale.x(), 0, 0, 0, 0, scale.y(), 0, 0, 0, 0, scale.z(), 0, 0, 0, 0, 1);
	}
	
	public static Matrix4f createMatrixScale(final float xxx, final float yyy, final float zzz) {
		return new Matrix4f(xxx, 0, 0, 0, 0, yyy, 0, 0, 0, 0, zzz, 0, 0, 0, 0, 1);
	}
	
	/**
	* Create a matrix 3D with a simple translation
	* @param translate 3 dimention translation
	* @return New matrix of the transformation requested
	*/
	public static Matrix4f createMatrixTranslate(final Vector3f translate) {
		float a1 = 1.0f;
		float b1 = 0;
		float c1 = 0;
		float d1 = translate.x();
		float a2 = 0;
		float b2 = 1.0f;
		float c2 = 0;
		float d2 = translate.y();
		float a3 = 0;
		float b3 = 0;
		float c3 = 1.0f;
		float d3 = translate.z();
		float a4 = 0;
		float b4 = 0;
		float c4 = 0;
		float d4 = 1.0f;
		return new Matrix4f(a1, b1, c1, d1, a2, b2, c2, d2, a3, b3, c3, d3, a4, b4, c4, d4);
	}
	
	/**
	 * Configuration constructor.
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
		this.a1 = a1;
		this.b1 = b1;
		this.c1 = c1;
		this.d1 = d1;
		this.a2 = a2;
		this.b2 = b2;
		this.c2 = c2;
		this.d2 = d2;
		this.a3 = a3;
		this.b3 = b3;
		this.c3 = c3;
		this.d3 = d3;
		this.a4 = a4;
		this.b4 = b4;
		this.c4 = c4;
		this.d4 = d4;
	}
	
	/**
	 * Configuration constructor.
	 * @param values vector of values
	 */
	public Matrix4f(final float[] values) {
		this(values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7], values[8], values[9], values[10], values[11], values[12], values[13], values[14], values[15]);
	}
	
	public Matrix4f(final Matrix3f matrix) {
		this(matrix.a1(), matrix.a2(), matrix.a3(), 0, matrix.b1(), matrix.b2(), matrix.b3(), 0, matrix.c1(), matrix.c2(), matrix.c3(), 0, 0, 0, 0, 1);
	}
	
	/**
	 * Operator+= Addition an other matrix with this one
	 * @param obj Reference on the external object
	 */
	@CheckReturnValue
	public Matrix4f add(final Matrix4f obj) {
		return new Matrix4f(this.a1 + obj.a1, this.b1 + obj.b1, this.c1 + obj.c1, this.d1 + obj.d1, this.a2 + obj.a2, this.b2 + obj.b2, this.c2 + obj.c2, this.d2 + obj.d2, this.a3 + obj.a3,
				this.b3 + obj.b3, this.c3 + obj.c3, this.d3 + obj.d3, this.a4 + obj.a4, this.b4 + obj.b4, this.c4 + obj.c4, this.d4 + obj.d4);
	}
	
	/**
	 * Operator-= Decrement an other matrix with this one
	 * @param obj Reference on the external object
	 */
	@CheckReturnValue
	public Matrix4f decrement(final Matrix4f obj) {
		return new Matrix4f(this.a1 - obj.a1, this.b1 - obj.b1, this.c1 - obj.c1, this.d1 - obj.d1, this.a2 - obj.a2, this.b2 - obj.b2, this.c2 - obj.c2, this.d2 - obj.d2, this.a3 - obj.a3,
				this.b3 - obj.b3, this.c3 - obj.c3, this.d3 - obj.d3, this.a4 - obj.a4, this.b4 - obj.b4, this.c4 - obj.c4, this.d4 - obj.d4);
	}
	
	private float coFactorRaw0Col0() {
		return this.b2 * this.c3 * this.d4 - this.b2 * this.d3 * this.c4 - this.c2 * this.b3 * this.d4 + this.c2 * this.d3 * this.b4 + this.d2 * this.b3 * this.c4 - this.d2 * this.c3 * this.b4;
	}
	
	private float coFactorRaw0Col1() {
		return this.a2 * this.c3 * this.d4 - this.a2 * this.d3 * this.c4 - this.c2 * this.a3 * this.d4 + this.c2 * this.d3 * this.a4 + this.d2 * this.a3 * this.c4 - this.d2 * this.c3 * this.a4;
	}
	
	private float coFactorRaw0Col2() {
		return this.a2 * this.b3 * this.d4 - this.a2 * this.d3 * this.b4 - this.b2 * this.a3 * this.d4 + this.b2 * this.d3 * this.a4 + this.d2 * this.a3 * this.b4 - this.d2 * this.b3 * this.a4;
	}
	
	private float coFactorRaw0Col3() {
		return this.a2 * this.b3 * this.c4 - this.a2 * this.c3 * this.b4 - this.b2 * this.a3 * this.c4 + this.b2 * this.c3 * this.a4 + this.c2 * this.a3 * this.b4 - this.c2 * this.b3 * this.a4;
	}
	
	private float coFactorRaw1Col0() {
		return this.b1 * this.c3 * this.d4 - this.b1 * this.d3 * this.c4 - this.c1 * this.b3 * this.d4 + this.c1 * this.d3 * this.b4 + this.d1 * this.b3 * this.c4 - this.d1 * this.c3 * this.b4;
	}
	
	private float coFactorRaw1Col1() {
		return this.a1 * this.c3 * this.d4 - this.a1 * this.d3 * this.c4 - this.c1 * this.a3 * this.d4 + this.c1 * this.d3 * this.a4 + this.d1 * this.a3 * this.c4 - this.d1 * this.c3 * this.a4;
	}
	
	private float coFactorRaw1Col2() {
		return this.a1 * this.b3 * this.d4 - this.a1 * this.d3 * this.b4 - this.b1 * this.a3 * this.d4 + this.b1 * this.d3 * this.a4 + this.d1 * this.a3 * this.b4 - this.d1 * this.b3 * this.a4;
	}
	
	private float coFactorRaw1Col3() {
		return this.a1 * this.b3 * this.c4 - this.a1 * this.c3 * this.b4 - this.b1 * this.a3 * this.c4 + this.b1 * this.c3 * this.a4 + this.c1 * this.a3 * this.b4 - this.c1 * this.b3 * this.a4;
	}
	
	private float coFactorRaw2Col0() {
		return this.b1 * this.c2 * this.d4 - this.b1 * this.d2 * this.c4 - this.c1 * this.b2 * this.d4 + this.c1 * this.d2 * this.b4 + this.d1 * this.b2 * this.c4 - this.d1 * this.c2 * this.b4;
	}
	
	private float coFactorRaw2Col1() {
		return this.a1 * this.c2 * this.d4 - this.a1 * this.d2 * this.c4 - this.c1 * this.a2 * this.d4 + this.c1 * this.d2 * this.a4 + this.d1 * this.a2 * this.c4 - this.d1 * this.c2 * this.a4;
	}
	
	private float coFactorRaw2Col2() {
		return this.a1 * this.b2 * this.d4 - this.a1 * this.d2 * this.b4 - this.b1 * this.a2 * this.d4 + this.b1 * this.d2 * this.a4 + this.d1 * this.a2 * this.b4 - this.d1 * this.b2 * this.a4;
	}
	
	private float coFactorRaw2Col3() {
		return this.a1 * this.b2 * this.c4 - this.a1 * this.c2 * this.b4 - this.b1 * this.a2 * this.c4 + this.b1 * this.c2 * this.a4 + this.c1 * this.a2 * this.b4 - this.c1 * this.b2 * this.a4;
	}
	
	private float coFactorRaw3Col0() {
		return this.b1 * this.c2 * this.d3 - this.b1 * this.d2 * this.c3 - this.c1 * this.b2 * this.d3 + this.c1 * this.d2 * this.b3 + this.d1 * this.b2 * this.c3 - this.d1 * this.c2 * this.b3;
	}
	
	private float coFactorRaw3Col1() {
		return this.a1 * this.c2 * this.d3 - this.a1 * this.d2 * this.c3 - this.c1 * this.a2 * this.d3 + this.c1 * this.d2 * this.a3 + this.d1 * this.a2 * this.c3 - this.d1 * this.c2 * this.a3;
	}
	
	private float coFactorRaw3Col2() {
		return this.a1 * this.b2 * this.d3 - this.a1 * this.d2 * this.b3 - this.b1 * this.a2 * this.d3 + this.b1 * this.d2 * this.a3 + this.d1 * this.a2 * this.b3 - this.d1 * this.b2 * this.a3;
	}
	
	private float coFactorRaw3Col3() {
		return this.a1 * this.b2 * this.c3 - this.a1 * this.c2 * this.b3 - this.b1 * this.a2 * this.c3 + this.b1 * this.c2 * this.a3 + this.c1 * this.a2 * this.b3 - this.c1 * this.b2 * this.a3;
	}
	
	/**
	 * Computes the determinant of the matrix.
	 * @return The determinent Value.
	 */
	//                                   https://www.dcode.fr/determinant-matrice
	
	@CheckReturnValue
	public float determinant() {
		return this.a1 * coFactorRaw0Col0() - this.b1 * coFactorRaw0Col1() + this.c1 * coFactorRaw0Col2() - this.d1 * coFactorRaw0Col3();
		/*
		return a *(f *k *p −f *l *o −g *j *p +g *l *n +h *j *o −h *k *n )−b *(e *k *p −e *l *o −g *i *p +g *l *m +h *i *o −h *k *m )+c *(e *j *p −e *l *n −f *i *p +f *l *m +h *i *n −h *j *m )−d *(e *j *o −e *k *n −f *i *o +f *k *m +g *i *n −g *j *m );
				a b c d
				e f g h
				i j k l
				m n o p
		 */
	}
	
	@CheckReturnValue
	@Deprecated
	public float[] getTable() {
		float[] mat = new float[16];
		mat[0] = this.a1;
		mat[1] = this.b1;
		mat[2] = this.c1;
		mat[3] = this.d1;
		mat[4] = this.a2;
		mat[5] = this.b2;
		mat[6] = this.c2;
		mat[7] = this.d2;
		mat[8] = this.a3;
		mat[9] = this.b3;
		mat[10] = this.c3;
		mat[11] = this.d3;
		mat[12] = this.a4;
		mat[13] = this.b4;
		mat[14] = this.c4;
		mat[15] = this.d4;
		return mat;
	}
	
	@CheckReturnValue
	public float[] asArray() {
		float[] mat = new float[16];
		mat[0] = this.a1;
		mat[1] = this.b1;
		mat[2] = this.c1;
		mat[3] = this.d1;
		mat[4] = this.a2;
		mat[5] = this.b2;
		mat[6] = this.c2;
		mat[7] = this.d2;
		mat[8] = this.a3;
		mat[9] = this.b3;
		mat[10] = this.c3;
		mat[11] = this.d3;
		mat[12] = this.a4;
		mat[13] = this.b4;
		mat[14] = this.c4;
		mat[15] = this.d4;
		return mat;
	}
	
	@CheckReturnValue
	public float[] asArrayTransposed() {
		float[] mat = new float[16];
		mat[0] = this.a1;
		mat[1] = this.a2;
		mat[2] = this.a3;
		mat[3] = this.a4;
		mat[4] = this.b1;
		mat[5] = this.b2;
		mat[6] = this.b3;
		mat[7] = this.b4;
		mat[8] = this.c1;
		mat[9] = this.c2;
		mat[10] = this.c3;
		mat[11] = this.c4;
		mat[12] = this.d1;
		mat[13] = this.d2;
		mat[14] = this.d3;
		mat[15] = this.d4;
		return mat;
	}
	
	/**
	 * Inverts the matrix.
	 * @note The determinant must be != 0, otherwithe the matrix can't be inverted.
	 * @return The inverted matrix.
	 */
	@CheckReturnValue
	public Matrix4f invert() {
		final float det = determinant();
		if (Math.abs(det) < (1.0e-7f)) {
			// The matrix is not invertible! Singular case!
			return this;
		}
		float a1 = coFactorRaw0Col0() / det;
		float b1 = coFactorRaw0Col1() / det;
		float c1 = coFactorRaw0Col2() / det;
		float d1 = coFactorRaw0Col3() / det;
		float a2 = coFactorRaw1Col0() / det;
		float b2 = coFactorRaw1Col1() / det;
		float c2 = coFactorRaw1Col2() / det;
		float d2 = coFactorRaw1Col3() / det;
		float a3 = coFactorRaw2Col0() / det;
		float b3 = coFactorRaw2Col1() / det;
		float c3 = coFactorRaw2Col2() / det;
		float d3 = coFactorRaw2Col3() / det;
		float a4 = coFactorRaw3Col0() / det;
		float b4 = coFactorRaw3Col1() / det;
		float c4 = coFactorRaw3Col2() / det;
		float d4 = coFactorRaw3Col3() / det;
		return new Matrix4f(a1, b1, c1, d1, a2, b2, c2, d2, a3, b3, c3, d3, a4, b4, c4, d4);
	}
	
	/**
	 * Operator*= Multiplication an other matrix with this one
	 * @param obj Reference on the external object
	 */
	@CheckReturnValue
	public Matrix4f multiply(final Matrix4f obj) {
		float a1 = this.a1 * obj.a1 + this.a2 * obj.b1 + this.a3 * obj.c1 + this.a3 * obj.d1;
		float b1 = this.b1 * obj.a1 + this.b2 * obj.b1 + this.b3 * obj.c1 + this.b3 * obj.d1;
		float c1 = this.c1 * obj.a1 + this.c2 * obj.b1 + this.c3 * obj.c1 + this.c3 * obj.d1;
		float d1 = this.d1 * obj.a1 + this.d2 * obj.b1 + this.d3 * obj.c1 + this.d3 * obj.d1;
		
		float a2 = this.a1 * obj.a2 + this.a2 * obj.b2 + this.a3 * obj.c2 + this.a3 * obj.d2;
		float b2 = this.b1 * obj.a2 + this.b2 * obj.b2 + this.b3 * obj.c2 + this.b3 * obj.d2;
		float c2 = this.c1 * obj.a2 + this.c2 * obj.b2 + this.c3 * obj.c2 + this.c3 * obj.d2;
		float d2 = this.d1 * obj.a2 + this.d2 * obj.b2 + this.d3 * obj.c2 + this.d3 * obj.d2;
		
		float a3 = this.a1 * obj.a3 + this.a2 * obj.b3 + this.a3 * obj.c3 + this.a3 * obj.d3;
		float b3 = this.b1 * obj.a3 + this.b2 * obj.b3 + this.b3 * obj.c3 + this.b3 * obj.d3;
		float c3 = this.c1 * obj.a3 + this.c2 * obj.b3 + this.c3 * obj.c3 + this.c3 * obj.d3;
		float d3 = this.d1 * obj.a3 + this.d2 * obj.b3 + this.d3 * obj.c3 + this.d3 * obj.d3;
		
		float a4 = this.a1 * obj.a4 + this.a2 * obj.b4 + this.a3 * obj.c4 + this.a3 * obj.d4;
		float b4 = this.b1 * obj.a4 + this.b2 * obj.b4 + this.b3 * obj.c4 + this.b3 * obj.d4;
		float c4 = this.c1 * obj.a4 + this.c2 * obj.b4 + this.c3 * obj.c4 + this.c3 * obj.d4;
		float d4 = this.d1 * obj.a4 + this.d2 * obj.b4 + this.d3 * obj.c4 + this.d3 * obj.d4;
		return new Matrix4f(a1, b1, c1, d1, a2, b2, c2, d2, a3, b3, c3, d3, a4, b4, c4, d4);
	}
	
	/**
	 * Operator* apply matrix on a vector
	 * @param point Point value to apply the matrix
	 * @return New vector containing the value
	 */
	@CheckReturnValue
	public Vector3f multiply(final Vector3f point) {
		return new Vector3f(this.a1 * point.x() + this.b1 * point.y() + this.c1 * point.z() + this.d1, this.a2 * point.x() + this.b2 * point.y() + this.c2 * point.z() + this.d2,
				this.a3 * point.x() + this.b3 * point.y() + this.c3 * point.z() + this.d3);
	}
	
	/**
	 * Makes a rotation matrix about an arbitrary axis.
	 * @param vect vector to apply the angle.
	 * @param angleRad angle to apply.
	 */
	@CheckReturnValue
	public Matrix4f rotate(final Vector3f vect, final float angleRad) {
		final Matrix4f tmpMat = createMatrixRotate(vect, angleRad);
		return this.multiply(tmpMat);
	}
	
	/**
	 * Scale the current Matrix in all direction with 1 value.
	 * @param scale Scale XYZ value to apply.
	 */
	@CheckReturnValue
	public Matrix4f scale(final float scale) {
		return scale(scale, scale, scale);
	}
	
	/**
	 * Scale the current Matrix.
	 * @param sx Scale X value to apply.
	 * @param sy Scale Y value to apply.
	 * @param sz Scale Z value to apply.
	 */
	@CheckReturnValue
	public Matrix4f scale(final float sx, final float sy, final float sz) {
		float a1 = this.a1 * sx;
		float b1 = this.b1 * sy;
		float c1 = this.c1 * sz;
		float a2 = this.a2 * sx;
		float b2 = this.b2 * sy;
		float c2 = this.c2 * sz;
		float a3 = this.a3 * sx;
		float b3 = this.b3 * sy;
		float c3 = this.c3 * sz;
		return new Matrix4f(a1, b1, c1, this.d1, a2, b2, c2, this.d2, a3, b3, c3, this.d3, this.a4, this.b4, this.c4, this.d4);
	}
	
	/**
	 * Scale the current Matrix.
	 * @param vect Scale vector to apply.
	 */
	@CheckReturnValue
	public Matrix4f scale(final Vector3f vect) {
		return scale(vect.x(), vect.y(), vect.z());
	}
	
	/**
	 * configure identity of the matrix
	 */
	public static final Matrix4f IDENTITY = new Matrix4f(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
	
	/**
	 * Makes a translation of the matrix
	 * @param vect Translation to apply.
	 */
	@CheckReturnValue
	public Matrix4f translate(final Vector3f vect) {
		final Matrix4f tmpMat = createMatrixTranslate(vect);
		return this.multiply(tmpMat);
	}
	
	/**
	 * Transpose the current matix (usefull for OpenGL display)
	 */
	@CheckReturnValue
	public Matrix4f transpose() {
		return new Matrix4f(this.a1, this.a2, this.a3, this.a4, this.b1, this.b2, this.b3, this.b4, this.c1, this.c2, this.c3, this.c4, this.d1, this.d2, this.d3, this.d4);
	}
	
}
