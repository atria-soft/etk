package org.atriasoft.etk.math;

import edu.umd.cs.findbugs.annotations.CheckReturnValue;

public record Transform3D(
		// Position
		Vector3f position,
		// Orientation
		Quaternion orientation,
		// Scale
		Vector3f scale) {
	/**
	 * Get the identity of the transformation
	 */
	public static final Transform3D IDENTITY = new Transform3D(Vector3f.ZERO, Quaternion.IDENTITY, Vector3f.ONE);

	public Transform3D(final Vector3f position) {
		this(position, Quaternion.IDENTITY, Vector3f.ONE);
	}

	public Transform3D(final Vector3f position, final Matrix3f orientation) {
		this(position, Quaternion.createFromMatrix(orientation), Vector3f.ONE);
	}

	public Transform3D(final Vector3f position, final Quaternion orientation) {
		this(position, orientation, Vector3f.ONE);
	}

	public Transform3D(final Vector3f position, final Quaternion orientation, final Vector3f scale) {
		this.position = position;
		this.orientation = orientation;
		this.scale = scale;
	}

	@CheckReturnValue
	public Transform3D rotate(final Quaternion rotation) {
		return new Transform3D(this.position, this.orientation.multiply(rotation), this.scale);
	}

	@CheckReturnValue
	public Transform3D withOrientation(final Quaternion orientation) {
		return new Transform3D(this.position, orientation, this.scale);
	}

	@CheckReturnValue
	public Transform3D withPosition(final Vector3f position) {
		return new Transform3D(position, this.orientation, this.scale);
	}

	@CheckReturnValue
	public Transform3D withScale(final Vector3f scale) {
		return new Transform3D(this.position, this.orientation, scale);
	}

	/// Get the OpenGL matrix of the transform
	@CheckReturnValue
	public Matrix4f getOpenGLMatrix() {
		final Matrix3f tmpMatrix = this.orientation.getMatrix();
		final float sx = this.scale.x();
		final float sy = this.scale.y();
		final float sz = this.scale.z();
		return new Matrix4f(
				tmpMatrix.a1() * sx, tmpMatrix.a2() * sx, tmpMatrix.a3() * sx, this.position.x(),
				tmpMatrix.b1() * sy, tmpMatrix.b2() * sy, tmpMatrix.b3() * sy, this.position.y(),
				tmpMatrix.c1() * sz, tmpMatrix.c2() * sz, tmpMatrix.c3() * sz, this.position.z(),
				0.0f, 0.0f, 0.0f, 1.0f);
	}

	/// Get the OpenGL matrix of the transform (transposed)
	@CheckReturnValue
	public Matrix4f getOpenGLMatrixTransposed() {
		final Matrix3f tmpMatrix = this.orientation.getMatrix();
		final float sx = this.scale.x();
		final float sy = this.scale.y();
		final float sz = this.scale.z();
		return new Matrix4f(
				tmpMatrix.a1() * sx, tmpMatrix.b1() * sy, tmpMatrix.c1() * sz, 0.0f,
				tmpMatrix.a2() * sx, tmpMatrix.b2() * sy, tmpMatrix.c2() * sz, 0.0f,
				tmpMatrix.a3() * sx, tmpMatrix.b3() * sy, tmpMatrix.c3() * sz, 0.0f,
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

	@CheckReturnValue
	public Vector3f getScale() {
		return this.scale;
	}

	/// Return an interpolated transform
	@CheckReturnValue
	public Transform3D interpolateTransforms(final Transform3D newOne, final float interpolationFactor) {
		final Vector3f interPosition = this.position.multiply(1.0f - interpolationFactor)
				.add(newOne.position.multiply(interpolationFactor));
		final Quaternion interOrientation = this.orientation.slerp(newOne.orientation, interpolationFactor);
		final Vector3f interScale = this.scale.multiply(1.0f - interpolationFactor)
				.add(newOne.scale.multiply(interpolationFactor));
		return new Transform3D(interPosition, interOrientation, interScale);
	}

	/// Return the inverse of the transform
	@CheckReturnValue
	public Transform3D inverseNew() {
		final Quaternion invQuaternion = this.orientation.inverse();
		final Matrix3f invMatrix = invQuaternion.getMatrix();
		final Vector3f invScale = new Vector3f(1.0f / this.scale.x(), 1.0f / this.scale.y(), 1.0f / this.scale.z());
		return new Transform3D(invMatrix.multiply(this.position.multiply(-1)).multiply(invScale), invQuaternion, invScale);
	}

	/// Return true if the two transforms are different
	@CheckReturnValue
	public boolean isDifferent(final Transform3D transform2) {
		return this.position.isDifferent(transform2.position)
				|| this.orientation.isDifferent(transform2.orientation)
				|| this.scale.isDifferent(transform2.scale);
	}

	/// Return true if the two transforms are equal
	@CheckReturnValue
	public boolean isEqual(final Transform3D transform2) {
		return this.position.isEqual(transform2.position)
				&& this.orientation.isEqual(transform2.orientation)
				&& this.scale.isEqual(transform2.scale);
	}

	/// Return the transformed vector (applies scale, then rotation, then translation)
	@CheckReturnValue
	public Vector3f multiply(final Vector3f vector) {
		return this.orientation.getMatrix().multiply(vector.multiply(this.scale)).add(this.position);
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
		return new Transform3D(this.orientation.getMatrix().multiply(transform2.position).add(this.position),
				this.orientation.multiply(transform2.orientation),
				this.scale.multiply(transform2.scale));
	}

	/// Set the transform from an OpenGL transform matrix
	@CheckReturnValue
	public Transform3D createFromOpenGL(final float[] matrix) {
		final Matrix3f tmpMatrix = new Matrix3f(matrix[0], matrix[4], matrix[8], matrix[1], matrix[5], matrix[9],
				matrix[2], matrix[6], matrix[10]);
		final Quaternion orientation = Quaternion.createFromMatrix(tmpMatrix);
		final Vector3f position = new Vector3f(matrix[12], matrix[13], matrix[14]);
		return new Transform3D(position, orientation);
	}

	@Override
	public String toString() {
		if (this.scale.isEqual(Vector3f.ONE)) {
			return "Transform3D(" + this.position + " & " + this.orientation + ")";
		}
		return "Transform3D(" + this.position + " & " + this.orientation + " scale=" + this.scale + ")";
	}
}
