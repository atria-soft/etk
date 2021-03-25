package org.atriasoft.etk.math;

import edu.umd.cs.findbugs.annotations.CheckReturnValue;

@SuppressWarnings("preview")
public record Transform3D(
		// Position
		Vector3f position,
		// Orientation
		Quaternion orientation) {
	/**
	 * Get the identity of the transformation
	 */
	public static final Transform3D IDENTITY = new Transform3D(Vector3f.ZERO, Quaternion.IDENTITY);
	
	public Transform3D(final Vector3f position) {
		this(position, Quaternion.IDENTITY);
	}
	
	public Transform3D(final Vector3f position, final Matrix3f orientation) {
		this(position, Quaternion.createFromMatrix(orientation));
	}
	
	public Transform3D(final Vector3f position, final Quaternion orientation) {
		this.position = position;
		this.orientation = orientation;
	}
	
	@CheckReturnValue
	public Transform3D rotate(final Quaternion rotation) {
		return new Transform3D(this.position, this.orientation.multiply(rotation));
	}
	
	@CheckReturnValue
	public Transform3D withOrientation(final Quaternion orientation) {
		return new Transform3D(this.position, orientation);
	}
	
	@CheckReturnValue
	public Transform3D withPosition(final Vector3f position) {
		return new Transform3D(position, this.orientation);
	}
	
	/// Get the OpenGL matrix of the transform
	@CheckReturnValue
	public Matrix4f getOpenGLMatrix() {
		final Matrix3f tmpMatrix = this.orientation.getMatrix();
		return new Matrix4f(tmpMatrix.a1(), tmpMatrix.a2(), tmpMatrix.a3(), this.position.x(), tmpMatrix.b1(), tmpMatrix.b2(), tmpMatrix.b3(), this.position.y(), tmpMatrix.c1(), tmpMatrix.c2(),
				tmpMatrix.c3(), this.position.z(), 0.0f, 0.0f, 0.0f, 1.0f);
	}
	
	/// Get the OpenGL matrix of the transform
	@CheckReturnValue
	public Matrix4f getOpenGLMatrixTransposed() {
		final Matrix3f tmpMatrix = this.orientation.getMatrix();
		return new Matrix4f(tmpMatrix.a1(), tmpMatrix.b1(), tmpMatrix.c1(), 0.0f, tmpMatrix.a2(), tmpMatrix.b2(), tmpMatrix.c2(), 0.0f, tmpMatrix.a3(), tmpMatrix.b3(), tmpMatrix.c3(), 0.0f,
				this.position.x(), this.position.y(), this.position.z(), 1.0f);
	}
	
	@CheckReturnValue
	public Quaternion getOrientation() {
		return this.orientation;
	}
	
	@CheckReturnValue
	public Vector3f getPosition() {
		return this.position;
	}
	
	/// Return an interpolated transform
	@CheckReturnValue
	public Transform3D interpolateTransforms(final Transform3D newOne, final float interpolationFactor) {
		final Vector3f interPosition = this.position.multiply(1.0f - interpolationFactor).add(newOne.position.multiply(interpolationFactor));
		final Quaternion interOrientation = this.orientation.slerp(newOne.orientation, interpolationFactor);
		return new Transform3D(interPosition, interOrientation);
	}
	
	/// Return the inverse of the transform
	@CheckReturnValue
	public Transform3D inverseNew() {
		final Quaternion invQuaternion = this.orientation.inverse();
		final Matrix3f invMatrix = invQuaternion.getMatrix();
		return new Transform3D(invMatrix.multiply(this.position.multiply(-1)), invQuaternion);
	}
	
	/// Return true if the two transforms are different
	@CheckReturnValue
	public boolean isDifferent(final Transform3D transform2) {
		return this.position.isDifferent(transform2.position) || this.orientation.isDifferent(transform2.orientation);
	}
	
	/// Return true if the two transforms are equal
	@CheckReturnValue
	public boolean isEqual(final Transform3D transform2) {
		return this.position.isEqual(transform2.position) && this.orientation.isEqual(transform2.orientation);
	}
	
	/// Return the transformed vector
	@CheckReturnValue
	public Vector3f multiply(final Vector3f vector) {
		return this.orientation.getMatrix().multiply(vector).add(this.position);
	}
	
	/// Operator of multiplication of a transform with another one
	/*
	@CheckReturnValue
	public Transform3D multiply(Transform3D transform2) {
		this.position = this.orientation.getMatrix().multiply(transform2.position).add(this.position);
		this.orientation.multiply(transform2.orientation);
	}
	*/
	/// Operator of multiplication of a transform with another one
	@CheckReturnValue
	public Transform3D multiply(final Transform3D transform2) {
		return new Transform3D(this.orientation.getMatrix().multiply(transform2.position).add(this.position), this.orientation.multiply(transform2.orientation));
	}
	
	/// Set the transform from an OpenGL transform matrix
	@CheckReturnValue
	public Transform3D createFromOpenGL(final float[] matrix) {
		final Matrix3f tmpMatrix = new Matrix3f(matrix[0], matrix[4], matrix[8], matrix[1], matrix[5], matrix[9], matrix[2], matrix[6], matrix[10]);
		Quaternion orientation = Quaternion.createFromMatrix(tmpMatrix);
		Vector3f position = new Vector3f(matrix[12], matrix[13], matrix[14]);
		return new Transform3D(position, orientation);
	}
	
	@Override
	public String toString() {
		return "Transform3D(" + this.position + " & " + this.orientation + ")";
	}
}
