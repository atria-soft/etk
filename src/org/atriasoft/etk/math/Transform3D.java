package org.atriasoft.etk.math;

public class Transform3D {
	/**
	 * @brief Get the identity of the transformation
	 */
	public static Transform3D identity() {
		return new Transform3D(Vector3f.zero(), Quaternion.identity());
	}
	
	// Position
	protected Vector3f position;
	// Orientation
	protected Quaternion orientation;
	
	public Transform3D() {
		this.position = Vector3f.zero();
		this.orientation = Quaternion.identity();
	}
	
	public Transform3D(final Transform3D transform3d) {
		this.position = transform3d.position.clone();
		this.orientation = transform3d.orientation.clone();
	}
	
	public Transform3D(final Vector3f position) {
		this.position = position.clone();
		this.orientation = Quaternion.identity();
	}
	
	public Transform3D(final Vector3f position, final Matrix3f orientation) {
		this.position = position.clone();
		this.orientation = new Quaternion(orientation);
	}
	
	public Transform3D(final Vector3f position, final Quaternion orientation) {
		this.position = position.clone();
		this.orientation = orientation.clone();
	}
	
	public void applyRotation(final Quaternion rotation) {
		this.orientation = this.orientation.multiply(rotation);
	}
	
	/**
	 * @brief Clone the current Transform3D.
	 * @return New Transform3D containing the value
	 */
	@Override
	public Transform3D clone() {
		return new Transform3D(this);
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Transform3D other = (Transform3D) obj;
		if (!this.position.equals(this.position)) {
			return false;
		}
		return this.orientation.equals(other.orientation);
	}
	
	/// Get the OpenGL matrix of the transform
	public Matrix4f getOpenGLMatrix() {
		final Matrix4f out = new Matrix4f();
		final Matrix3f tmpMatrix = this.orientation.getMatrix();
		out.mat[0] = tmpMatrix.mat[0];
		out.mat[1] = tmpMatrix.mat[1];
		out.mat[2] = tmpMatrix.mat[2];
		out.mat[3] = this.position.x;
		out.mat[4] = tmpMatrix.mat[3];
		out.mat[5] = tmpMatrix.mat[4];
		out.mat[6] = tmpMatrix.mat[5];
		out.mat[7] = this.position.y;
		out.mat[8] = tmpMatrix.mat[6];
		out.mat[9] = tmpMatrix.mat[7];
		out.mat[10] = tmpMatrix.mat[8];
		out.mat[11] = this.position.z;
		out.mat[12] = 0.0f;
		out.mat[13] = 0.0f;
		out.mat[14] = 0.0f;
		out.mat[15] = 1.0f;
		return out;
	}
	
	/// Get the OpenGL matrix of the transform
	public Matrix4f getOpenGLMatrixTransposed() {
		final Matrix4f out = new Matrix4f();
		final Matrix3f tmpMatrix = this.orientation.getMatrix();
		// version transposer...
		out.mat[0] = tmpMatrix.mat[0];
		out.mat[1] = tmpMatrix.mat[3];
		out.mat[2] = tmpMatrix.mat[6];
		out.mat[3] = 0.0f;
		out.mat[4] = tmpMatrix.mat[1];
		out.mat[5] = tmpMatrix.mat[4];
		out.mat[6] = tmpMatrix.mat[7];
		out.mat[7] = 0.0f;
		out.mat[8] = tmpMatrix.mat[2];
		out.mat[9] = tmpMatrix.mat[5];
		out.mat[10] = tmpMatrix.mat[8];
		out.mat[11] = 0.0f;
		out.mat[12] = this.position.x;
		out.mat[13] = this.position.y;
		out.mat[14] = this.position.z;
		out.mat[15] = 1.0f;
		return out;
	}
	
	public Quaternion getOrientation() {
		return this.orientation;
	}
	
	public Vector3f getPosition() {
		return this.position;
	}
	
	@Override
	public int hashCode() {
		int hash = 38542;
		hash += this.position.hashCode();
		hash += this.orientation.hashCode();
		return hash;
	}
	
	/// Return an interpolated transform
	public Transform3D interpolateTransforms(final Transform3D newOne, final float interpolationFactor) {
		final Vector3f interPosition = this.position.multiplyNew(1.0f - interpolationFactor).add(newOne.position.multiplyNew(interpolationFactor));
		final Quaternion interOrientation = this.orientation.slerp(newOne.orientation, interpolationFactor);
		return new Transform3D(interPosition, interOrientation);
	}
	
	/// Return the inverse of the transform
	public Transform3D inverseNew() {
		final Quaternion invQuaternion = this.orientation.inverseNew();
		final Matrix3f invMatrix = invQuaternion.getMatrix();
		return new Transform3D(invMatrix.multiplyNew(this.position.multiplyNew(-1)), invQuaternion);
	}
	
	/// Return true if the two transforms are different
	public boolean isDifferent(final Transform3D transform2) {
		return this.position.isDifferent(transform2.position) || this.orientation.isDifferent(transform2.orientation);
	}
	
	/// Return true if the two transforms are equal
	public boolean isEqual(final Transform3D transform2) {
		return this.position.isEqual(transform2.position) && this.orientation.isEqual(transform2.orientation);
	}
	
	/// Return the transformed vector
	public Vector3f multiply(final Vector3f vector) {
		return this.orientation.getMatrix().multiplyNew(vector).add(this.position);
	}
	
	/// Operator of multiplication of a transform with another one
	/*
	public Transform3D multiply(Transform3D transform2) {
		this.position = this.orientation.getMatrix().multiply(transform2.position).add(this.position);
		this.orientation.multiply(transform2.orientation);
	}
	*/
	/// Operator of multiplication of a transform with another one
	public Transform3D multiplyNew(final Transform3D transform2) {
		return new Transform3D(this.orientation.getMatrix().multiplyNew(transform2.position).add(this.position), this.orientation.multiplyNew(transform2.orientation));
	}
	
	/// Return the transformed vector
	public Vector3f multiplyNew(final Vector3f vector) {
		return new Matrix3f(this.orientation.getMatrix()).multiplyNew(vector).add(this.position);
	}
	
	/// Assignment operator
	public Transform3D set(final Transform3D transform) {
		this.position = transform.position.clone();
		this.orientation = transform.orientation.clone();
		return this;
	}
	
	/// Set the transform from an OpenGL transform matrix
	public void setFromOpenGL(final float[] matrix) {
		final Matrix3f tmpMatrix = new Matrix3f(matrix[0], matrix[4], matrix[8], matrix[1], matrix[5], matrix[9], matrix[2], matrix[6], matrix[10]);
		this.orientation = new Quaternion(tmpMatrix);
		this.position.setValue(matrix[12], matrix[13], matrix[14]);
	}
	
	/// Set the Transform3D to the identity transform
	public void setIdentity() {
		this.position = Vector3f.zero();
		this.orientation = Quaternion.identity();
	}
	
	public void setOrientation(final Quaternion orientation) {
		this.orientation = orientation;
	}
	
	public void setPosition(final Vector3f position) {
		this.position = position;
	}
	
	@Override
	public String toString() {
		return "Transform3D(" + this.position + " & " + this.orientation + ")";
	}
}
