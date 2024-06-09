package org.atriasoft.etk.math;

import edu.umd.cs.findbugs.annotations.CheckReturnValue;

public record Quaternion(
		float x,
		float y,
		float z,
		float w) {
	// a * diff = b
	public static Quaternion diff(final Quaternion a, final Quaternion b) {
		// LOGGER.info("diff " + a + " " + b);
		final Quaternion inv = a.inverse();
		return inv.multiply(b);
	}

	/* void checkValues() { if ( isinf(this.x) == true || isnan(this.x) == true || isinf(this.y) == true || isnan(this.y) == true || isinf(this.z) == true || isnan(this.z) == true || isinf(this.w) ==
	 * true || isnan(this.w) == true) { TKCRITICAL("         set transform: (" << this.x << "," << this.y << "," << this.z << "," << this.w << ")"); } } */
	/**
	 * Constructor from scalars.
	 * @param xxx X value
	 * @param yyy Y value
	 * @param zzz Z value
	 * @param www W value */
	public Quaternion(final float x, final float y, final float z, final float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	/**
	 * Constructor with the component w and a vector 3D.
	 * @param www W value
	 * @param vec 3D vector value
	 */
	public Quaternion(final float www, final Vector3f vec) {
		this(vec.x(), vec.y(), vec.z(), www);
	}

	/** Create a unit quaternion from a rotation matrix
	 * @param matrix generic matrix */
	public static Quaternion createFromMatrix(final Matrix3f matrix) {
		final float trace = matrix.getTrace();
		float x, y, z, w;
		if (trace < 0.0f) {
			if (matrix.b2() > matrix.a1()) {
				if (matrix.c3() > matrix.b2()) {
					final float rrr = (float) Math.sqrt(matrix.c3() - matrix.a1() - matrix.b2() + 1.0f);
					final float sss = 0.5f / rrr;
					x = (matrix.c1() + matrix.a3()) * sss;
					y = (matrix.b3() + matrix.c2()) * sss;
					z = 0.5f * rrr;
					w = (matrix.b1() - matrix.a2()) * sss;
				} else {
					final float rrr = (float) Math.sqrt(matrix.b2() - matrix.c3() - matrix.a1() + 1.0f);
					final float sss = 0.5f / rrr;
					x = (matrix.a2() + matrix.b1()) * sss;
					y = 0.5f * rrr;
					z = (matrix.b3() + matrix.c2()) * sss;
					w = (matrix.a3() - matrix.c1()) * sss;
				}
			} else if (matrix.c3() > matrix.a1()) {
				final float rrr = (float) Math.sqrt(matrix.c3() - matrix.a1() - matrix.b2() + 1.0f);
				final float sss = 0.5f / rrr;
				x = (matrix.c1() + matrix.a3()) * sss;
				y = (matrix.b3() + matrix.c2()) * sss;
				z = 0.5f * rrr;
				w = (matrix.b1() - matrix.a2()) * sss;
			} else {
				final float rrr = (float) Math.sqrt(matrix.a1() - matrix.b2() - matrix.c3() + 1.0f);
				final float sss = 0.5f / rrr;
				x = 0.5f * rrr;
				y = (matrix.a2() + matrix.b1()) * sss;
				z = (matrix.c1() - matrix.a3()) * sss;
				w = (matrix.c2() - matrix.b3()) * sss;
			}
		} else {
			final float rrr = (float) Math.sqrt(trace + 1.0f);
			final float sss = 0.5f / rrr;
			x = (matrix.c2() - matrix.b3()) * sss;
			y = (matrix.a3() - matrix.c1()) * sss;
			z = (matrix.b1() - matrix.a2()) * sss;
			w = 0.5f * rrr;
		}
		return new Quaternion(x, y, z, w);
	}

	/** Set the absolute values of each element */
	@CheckReturnValue
	public Quaternion absolute() {
		return new Quaternion(Math.abs(this.x), Math.abs(this.y), Math.abs(this.z), Math.abs(this.w));
	}

	/** Add a vector to this one.
	 * @param obj The vector to add to this one
	 * @return New vector containing the value */
	@CheckReturnValue
	public Quaternion add(final Quaternion obj) {
		return new Quaternion(this.x + obj.x, this.y + obj.y, this.z + obj.z, this.w + obj.w);
	}

	/** Conjugate the quaternion */
	@CheckReturnValue
	public Quaternion conjugate() {
		return new Quaternion(this.x * -1.0f, this.y * -1.0f, this.z * -1.0f, this.w);
	}

	/** Inversely scale the quaternion
	 * @param val Scale factor to divide by.
	 * @return New quaternion containing the value */
	public Quaternion devide(final float val) {
		if (val != 0) {
			return new Quaternion(this.x / val, this.y / val, this.z / val, this.w / val);
		}
		return this;
	}

	/** Return the dot product
	 * @param obj The other quaternion in the dot product
	 * @return Dot result value */
	@CheckReturnValue
	public float dot(final Quaternion obj) {
		return this.x * obj.x + this.y * obj.y + this.z * obj.z + this.w * obj.w;
	}

	/** Compute the rotation angle (in radians) and the rotation axis
	 * @param angle Angle of the quaternion
	 * @return Axis of the quaternion */
	@CheckReturnValue
	public Vector3f getAngleAxis(float angle) {
		final Quaternion quaternion = getUnit();
		angle = (float) Math.acos(quaternion.w) * 2.0f;
		final Vector3f rotationAxis = quaternion.getVectorV();
		return rotationAxis.normalize();
	}

	/** Get the orientation matrix corresponding to this quaternion
	 * @return the 3x3 transformation matrix */
	@CheckReturnValue
	public Matrix3f getMatrix() {
		final float nQ = this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
		float sss = 0.0f;
		if (nQ > 0.0f) {
			sss = 2.0f / nQ;
		}
		final float xs = this.x * sss;
		final float ys = this.y * sss;
		final float zs = this.z * sss;
		final float wxs = this.w * xs;
		final float wys = this.w * ys;
		final float wzs = this.w * zs;
		final float xxs = this.x * xs;
		final float xys = this.x * ys;
		final float xzs = this.x * zs;
		final float yys = this.y * ys;
		final float yzs = this.y * zs;
		final float zzs = this.z * zs;
		return new Matrix3f(1.0f - yys - zzs, xys - wzs, xzs + wys, xys + wzs, 1.0f - xxs - zzs, yzs - wxs, xzs - wys,
				yzs + wxs, 1.0f - xxs - yys);
	}

	@CheckReturnValue
	public Matrix4f getMatrix4() {
		final float nQ = this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
		float sss = 0.0f;
		if (nQ > 0.0f) {
			sss = 2.0f / nQ;
		}
		final float xs = this.x * sss;
		final float ys = this.y * sss;
		final float zs = this.z * sss;
		final float wxs = this.w * xs;
		final float wys = this.w * ys;
		final float wzs = this.w * zs;
		final float xxs = this.x * xs;
		final float xys = this.x * ys;
		final float xzs = this.x * zs;
		final float yys = this.y * ys;
		final float yzs = this.y * zs;
		final float zzs = this.z * zs;
		return new Matrix4f(1.0f - yys - zzs, xys - wzs, xzs + wys, 0, xys + wzs, 1.0f - xxs - zzs, yzs - wxs, 0,
				xzs - wys, yzs + wxs, 1.0f - xxs - yys, 0, 0, 0, 0, 1);
	}

	/** Return the unit quaternion
	 * @return Quaternion unitarised */
	@CheckReturnValue
	public Quaternion getUnit() {
		return normalize();
	}

	/** get x, y, z in a Vector3f */
	@CheckReturnValue
	public Vector3f getVectorV() {
		return new Vector3f(this.x, this.y, this.z);
	}

	/** Inverse the quaternion */
	@CheckReturnValue
	public Quaternion inverse() {
		final float invLengthSquare = 1.0f / length2();
		return new Quaternion(this.x * -invLengthSquare, this.y * -invLengthSquare, this.z * -invLengthSquare,
				this.w * invLengthSquare);
	}

	/** In-Equality compare operator with an other object.
	 * @param obj Reference on the comparing object
	 * @return true The Objects are NOT identical
	 * @return false The Objects are identical */
	@CheckReturnValue
	public boolean isDifferent(final Quaternion obj) {
		return ((this.w != obj.w) || (this.z != obj.z) || (this.y != obj.y) || (this.x != obj.x));
	}

	/** Equality compare operator with an other object.
	 * @param obj Reference on the comparing object
	 * @return true The Objects are identical
	 * @return false The Objects are NOT identical */
	@CheckReturnValue
	public boolean isEqual(final Quaternion obj) {
		return ((this.w == obj.w) && (this.z == obj.z) && (this.y == obj.y) && (this.x == obj.x));
	}

	/** Check if the quaternion is equal to (0,0,0,0)
	 * @return true The value is equal to (0,0,0,0)
	 * @return false The value is NOT equal to (0,0,0,0) */
	@CheckReturnValue
	public boolean isZero() {
		return this.x == 0 && this.y == 0 && this.z == 0 && this.w == 0;
	}

	/** Return the length of the quaternion
	 * @return Length value */
	@CheckReturnValue
	public float length() {
		return (float) Math.sqrt(length2());
	}

	/** Return the squared length of the quaternion.
	 * @return Squared length value. */
	@CheckReturnValue
	public float length2() {
		return dot(this);
	}

	/** Subtract a vector from this one
	 * @param obj The vector to subtract
	 * @return New quaternion containing the value */
	@CheckReturnValue
	public Quaternion less(final Quaternion obj) {
		return new Quaternion(this.x - obj.x, this.y - obj.y, this.z - obj.z, this.w - obj.w);
	}

	@CheckReturnValue
	public Vector3f multiply(final float xxx, final float yyy, final float zzz) {
		final Vector3f point = new Vector3f(xxx, yyy, zzz);
		final Vector3f qvec = getVectorV();
		Vector3f uv = qvec.cross(point);
		Vector3f uuv = qvec.cross(uv);
		uv = uv.multiply(2.0f * this.w);
		uuv = uuv.multiply(2.0f);
		return uv.add(point).add(uuv);
	}

	/** Multiply this quaternion by the other.
	 * @param obj The other quaternion
	 * @return Local reference of the quaternion */
	@CheckReturnValue
	public Quaternion multiply(final Quaternion obj) {
		final Vector3f base = getVectorV();
		final Vector3f crossValue = base.cross(obj.getVectorV());
		final float x = this.w * obj.x + obj.w * this.x + crossValue.x();
		final float y = this.w * obj.y + obj.w * this.y + crossValue.y();
		final float z = this.w * obj.z + obj.w * this.z + crossValue.z();
		final float w = this.w * obj.w - base.dot(obj.getVectorV());
		return (new Quaternion(x, y, z, w)).safeNormalize();
	}

	/** Operator* with a vector. This methods rotates a point given the rotation of a quaternion
	 * @param point Point to move
	 * @return Point with the updated position */
	@CheckReturnValue
	public Vector3f multiply(final Vector3f point) {
		final Vector3f qvec = getVectorV();
		Vector3f uv = qvec.cross(point);
		Vector3f uuv = qvec.cross(uv);
		uv = uv.multiply(2.0f * this.w);
		uuv = uuv.multiply(2.0f);
		return uv.add(point).add(uuv);
	}

	/** Scale the quaternion
	 * @param val Scale factor
	 * @return New quaternion containing the value */
	@CheckReturnValue
	public Quaternion multiply(final float val) {
		return new Quaternion(this.x * val, this.y * val, this.z * val, this.w * val);
	}

	/** Normalize this quaternion x^2 + y^2 + z^2 + w^2 = 1
	 * @return Local reference of the quaternion normalized */
	@CheckReturnValue
	public Quaternion normalize() {
		final float invLength = 1.0f / length();
		return new Quaternion(this.x * invLength, this.y * invLength, this.z * invLength, this.w * invLength);
	}

	/** Normalize this quaternion x^2 + y^2 + z^2 + w^2 = 1
	 * @return Local reference of the quaternion normalized */
	@CheckReturnValue
	public Quaternion safeNormalize() {
		final float lengthTmp = length();
		if (lengthTmp == 0.0f) {
			return IDENTITY;
		}
		return normalize();
	}

	/** Configure the quaternion with euler angles.
	 * @param angles Eular angle of the quaternion. */
	@CheckReturnValue
	public static Quaternion fromEulerAngles(final Vector3f angles) {
		float angle = angles.x() * 0.5f;
		final float sinX = (float) Math.sin(angle);
		final float cosX = (float) Math.cos(angle);
		angle = angles.y() * 0.5f;
		final float sinY = (float) Math.sin(angle);
		final float cosY = (float) Math.cos(angle);
		angle = angles.z() * 0.5f;
		final float sinZ = (float) Math.sin(angle);
		final float cosZ = (float) Math.cos(angle);
		final float cosYcosZ = cosY * cosZ;
		final float sinYcosZ = sinY * cosZ;
		final float cosYsinZ = cosY * sinZ;
		final float sinYsinZ = sinY * sinZ;
		final float x = sinX * cosYcosZ - cosX * sinYsinZ;
		final float y = cosX * sinYcosZ + sinX * cosYsinZ;
		final float z = cosX * cosYsinZ - sinX * sinYcosZ;
		final float w = cosX * cosYcosZ + sinX * sinYsinZ;
		return (new Quaternion(x, y, z, w)).normalize();
	}

	/** Set identity value at the quaternion */
	public static final Quaternion IDENTITY = new Quaternion(0, 0, 0, 1);

	/** Set each element to the max of the current values and the values of another Vector
	 * @param obj The other Vector to compare with */
	@CheckReturnValue
	public Quaternion max(final Quaternion obj) {
		return new Quaternion(Math.max(this.x, obj.x), Math.max(this.y, obj.y), Math.max(this.z, obj.z),
				Math.max(this.w, obj.w));
	}

	/** Set each element to the min of the current values and the values of another Vector
	 * @param obj The other Vector to compare with */
	@CheckReturnValue
	public Quaternion min(final Quaternion obj) {
		return new Quaternion(Math.min(this.x, obj.x), Math.min(this.y, obj.y), Math.min(this.z, obj.z),
				Math.min(this.w, obj.w));
	}

	// Compute the rotation angle (in radians) and the rotation axis
	// This method is used to get the rotation angle (in radian) and the unit
	// rotation axis of an orientation quaternion.
	/*
	public Vector3f getRotationAngleAxis(Vector3f axis, float[] angle) {
		Quaternion quaternion;
		// If the quaternion is unit
		if (length() == 1.0) {
			quaternion = this;
		} else {
			// We compute the unit quaternion
			quaternion = new Quaternion(this).normalize();
		}
		// Compute the roation angle
		angle[0] = Mathematics.ArcCos(quaternion.w) * 2.0f;
		// Compute the 3D rotation axis
		Vector3f rotationAxis = new Vector3f(quaternion.x, quaternion.y, quaternion.z);
		// Normalize the rotation axis
		rotationAxis.normalize();
		// Set the rotation axis values
		return axis.set(rotationAxis);
	}
	*/

	public static final Quaternion ZERO = new Quaternion(0, 0, 0, 0);

	/** Compute the spherical linear interpolation between two quaternions.
	 * @param obj1 First quaternion
	 * @param obj2 Second quaternion
	 * @param ttt linar coefficient interpolation to be such that [0..1] */
	@CheckReturnValue
	public Quaternion slerp(final Quaternion obj2, final float ttt) {
		// TKASSERT(ttt >= 0.0f ttt <= 1.0f, "wrong intermolation");
		float invert = 1.0f;
		float cosineTheta = dot(obj2);
		if (cosineTheta < 0.0f) {
			cosineTheta = -cosineTheta;
			invert = -1.0f;
		}
		if (1 - cosineTheta < 0.00001f) {
			return this.multiply(1.0f - ttt).add(obj2.multiply(ttt * invert));
		}
		final float theta = (float) Math.acos(cosineTheta);
		final float sineTheta = (float) Math.sin(theta);
		final float coeff1 = (float) Math.sin((1.0f - ttt) * theta) / sineTheta;
		final float coeff2 = (float) Math.sin(ttt * theta) / sineTheta * invert;
		return this.multiply(coeff1).add(obj2.multiply(coeff2));
	}

	@Override
	public String toString() {
		return "Quaternion(" + FMath.floatToString(this.x) + "," + FMath.floatToString(this.y) + ","
				+ FMath.floatToString(this.z) + "," + FMath.floatToString(this.w) + ")";
	}
}
