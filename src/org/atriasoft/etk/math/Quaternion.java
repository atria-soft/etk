package org.atriasoft.etk.math;

public class Quaternion {
	// a * diff = b
	public static Quaternion diff(final Quaternion a, final Quaternion b) {
		// Log.info("diff " + a + " " + b);
		final Quaternion inv = a.inverseNew();
		return inv.multiply(b);
	}
	
	/** @brief get an identity quaternion
	 * @return an identity quaternion */
	public static Quaternion identity() {
		return new Quaternion(0, 0, 0, 1);
	}
	
	/** @brief get a 0 value on all a quaternion
	 * @return a (float)Math. quaternion */
	public static Quaternion zero() {
		return new Quaternion(0, 0, 0, 0);
	}
	
	public float x;
	
	public float y;
	
	public float z;
	
	public float w;
	
	/** @brief No initialization constructor (faster ...) */
	public Quaternion() {
		this.x = 0.0f;
		this.y = 0.0f;
		this.z = 0.0f;
		this.w = 1.0f;
	}
	
	/* void checkValues() { if ( isinf(this.x) == true || isnan(this.x) == true || isinf(this.y) == true || isnan(this.y) == true || isinf(this.z) == true || isnan(this.z) == true || isinf(this.w) ==
	 * true || isnan(this.w) == true) { TKCRITICAL("         set transform: (" << this.x << "," << this.y << "," << this.z << "," << this.w << ")"); } } */
	/**
	 * @brief Constructor from scalars.
	 * @param xxx X value
	 * @param yyy Y value
	 * @param zzz Z value
	 * @param www W value */
	public Quaternion(final float xxx, final float yyy, final float zzz, final float www) {
		this.x = xxx;
		this.y = yyy;
		this.z = zzz;
		this.w = www;
	}
	
	/**
	 * @brief Constructor with the component w and a vector 3D.
	 * @param www W value
	 * @param vec 3D vector value
	 */
	public Quaternion(final float www, final Vector3f vec) {
		this.x = vec.x;
		this.y = vec.y;
		this.z = vec.z;
		this.w = www;
	}
	
	/** @brief Create a unit quaternion from a rotation matrix
	 * @param matrix generic matrix */
	public Quaternion(final Matrix3f matrix) {
		final float trace = matrix.getTrace();
		if (trace < 0.0f) {
			if (matrix.mat[4] > matrix.mat[0]) {
				if (matrix.mat[8] > matrix.mat[4]) {
					final float rrr = (float) Math.sqrt(matrix.mat[8] - matrix.mat[0] - matrix.mat[4] + 1.0f);
					final float sss = 0.5f / rrr;
					this.x = (matrix.mat[6] + matrix.mat[2]) * sss;
					this.y = (matrix.mat[5] + matrix.mat[7]) * sss;
					this.z = 0.5f * rrr;
					this.w = (matrix.mat[3] - matrix.mat[1]) * sss;
				} else {
					final float rrr = (float) Math.sqrt(matrix.mat[4] - matrix.mat[8] - matrix.mat[0] + 1.0f);
					final float sss = 0.5f / rrr;
					this.x = (matrix.mat[1] + matrix.mat[3]) * sss;
					this.y = 0.5f * rrr;
					this.z = (matrix.mat[5] + matrix.mat[7]) * sss;
					this.w = (matrix.mat[2] - matrix.mat[6]) * sss;
				}
			} else if (matrix.mat[8] > matrix.mat[0]) {
				final float rrr = (float) Math.sqrt(matrix.mat[8] - matrix.mat[0] - matrix.mat[4] + 1.0f);
				final float sss = 0.5f / rrr;
				this.x = (matrix.mat[6] + matrix.mat[2]) * sss;
				this.y = (matrix.mat[5] + matrix.mat[7]) * sss;
				this.z = 0.5f * rrr;
				this.w = (matrix.mat[3] - matrix.mat[1]) * sss;
			} else {
				final float rrr = (float) Math.sqrt(matrix.mat[0] - matrix.mat[4] - matrix.mat[8] + 1.0f);
				final float sss = 0.5f / rrr;
				this.x = 0.5f * rrr;
				this.y = (matrix.mat[1] + matrix.mat[3]) * sss;
				this.z = (matrix.mat[6] - matrix.mat[2]) * sss;
				this.w = (matrix.mat[7] - matrix.mat[5]) * sss;
			}
		} else {
			final float rrr = (float) Math.sqrt(trace + 1.0f);
			final float sss = 0.5f / rrr;
			this.x = (matrix.mat[7] - matrix.mat[5]) * sss;
			this.y = (matrix.mat[2] - matrix.mat[6]) * sss;
			this.z = (matrix.mat[3] - matrix.mat[1]) * sss;
			this.w = 0.5f * rrr;
		}
	}
	
	public Quaternion(final Quaternion obj) {
		this.x = obj.x;
		this.y = obj.y;
		this.z = obj.z;
		this.w = obj.w;
	}
	
	/** @brief Constructor with Euler angles (in radians) to a quaternion
	 * @param eulerAngles list of all euler angle */
	public Quaternion(final Vector3f eulerAngles) {
		setEulerAngles(eulerAngles);
	}
	
	/** @brief Set the absolute values of each element */
	public Quaternion absolute() {
		this.x = Math.abs(this.x);
		this.y = Math.abs(this.y);
		this.z = Math.abs(this.z);
		this.w = Math.abs(this.w);
		return this;
	}
	
	/** @brief Return a quaternion will the absolute values of each element
	 * @return New quaternion with the absolute value */
	public Quaternion absoluteNew() {
		return new Quaternion(Math.abs(this.x), Math.abs(this.y), Math.abs(this.z), Math.abs(this.w));
	}
	
	/** @brief Add a vector to this one.
	 * @param obj The vector to add to this one
	 * @return Local reference of the vector */
	public Quaternion add(final Quaternion obj) {
		this.x += obj.x;
		this.y += obj.y;
		this.z += obj.z;
		this.w += obj.w;
		return this;
	}
	
	/** @brief Add a vector to this one.
	 * @param obj The vector to add to this one
	 * @return New vector containing the value */
	public Quaternion addNew(final Quaternion obj) {
		return new Quaternion(this.x + obj.x, this.y + obj.y, this.z + obj.z, this.w + obj.w);
	}
	
	/** @brief Clone the current Quaternion.
	 * @return New Quaternion containing the value */
	@Override
	public Quaternion clone() {
		return new Quaternion(this);
	}
	
	/** @brief Conjugate the quaternion */
	public Quaternion conjugate() {
		this.x *= -1.0f;
		this.y *= -1.0f;
		this.z *= -1.0f;
		return this;
	}
	
	/** @brief Return the conjugate of the quaternion
	 * @return Conjugate quaternion */
	public Quaternion conjugateNew() {
		final Quaternion tmp = new Quaternion(this);
		tmp.conjugate();
		return tmp;
	}
	
	/** @brief Inversely scale the quaternion
	 * @param val Scale factor to divide by.
	 * @return Local reference of the quaternion */
	public Quaternion devide(final float val) {
		if (val != 0) {
			this.x /= val;
			this.y /= val;
			this.z /= val;
			this.w /= val;
		}
		return this;
	}
	
	/** @brief Inversely scale the quaternion
	 * @param val Scale factor to divide by.
	 * @return New quaternion containing the value */
	public Quaternion devideNew(final float val) {
		if (val != 0) {
			return new Quaternion(this.x / val, this.y / val, this.z / val, this.w / val);
		}
		return clone();
	}
	
	/** @brief Return the dot product
	 * @param obj The other quaternion in the dot product
	 * @return Dot result value */
	public float dot(final Quaternion obj) {
		return this.x * obj.x + this.y * obj.y + this.z * obj.z + this.w * obj.w;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Quaternion other = (Quaternion) obj;
		if (Float.floatToIntBits(this.x) != Float.floatToIntBits(other.x)) {
			return false;
		}
		if (Float.floatToIntBits(this.y) != Float.floatToIntBits(other.y)) {
			return false;
		}
		if (Float.floatToIntBits(this.z) != Float.floatToIntBits(other.z)) {
			return false;
		}
		return Float.floatToIntBits(this.w) == Float.floatToIntBits(other.w);
	}
	
	/** @brief Compute the rotation angle (in radians) and the rotation axis
	 * @param angle Angle of the quaternion
	 * @param axis Axis of the quaternion */
	public void getAngleAxis(float angle, final Vector3f axis) {
		final Quaternion quaternion = getUnit();
		angle = (float) Math.acos(quaternion.w) * 2.0f;
		Vector3f rotationAxis = new Vector3f(quaternion.x, quaternion.y, quaternion.z);
		rotationAxis = rotationAxis.normalizeNew();
		axis.setValue(rotationAxis.x, rotationAxis.y, rotationAxis.z);
	}
	
	/** @brief Get the orientation matrix corresponding to this quaternion
	 * @return the 3x3 transformation matrix */
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
		return new Matrix3f(1.0f - yys - zzs, xys - wzs, xzs + wys, xys + wzs, 1.0f - xxs - zzs, yzs - wxs, xzs - wys, yzs + wxs, 1.0f - xxs - yys);
	}
	
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
		return new Matrix4f(1.0f - yys - zzs, xys - wzs, xzs + wys, 0, xys + wzs, 1.0f - xxs - zzs, yzs - wxs, 0, xzs - wys, yzs + wxs, 1.0f - xxs - yys, 0, 0, 0, 0, 1);
	}
	
	public void getMatrixTo(final Matrix3f out) {
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
		out.set(1.0f - yys - zzs, xys - wzs, xzs + wys, xys + wzs, 1.0f - xxs - zzs, yzs - wxs, xzs - wys, yzs + wxs, 1.0f - xxs - yys);
	}
	
	/** @brief Return the unit quaternion
	 * @return Quaternion unitarised */
	public Quaternion getUnit() {
		return normalizeNew();
	}
	
	/** @brief get x, y, z in a Vector3f */
	public Vector3f getVectorV() {
		return new Vector3f(this.x, this.y, this.z);
	}
	
	/** @brief Get W value
	 * @return the w value */
	public float getW() {
		return this.w;
	}
	
	/** @brief Get X value
	 * @return the x value */
	public float getX() {
		return this.x;
	};
	
	/** @brief Get Y value
	 * @return the y value */
	public float getY() {
		return this.y;
	};
	
	/** @brief Get Z value
	 * @return the z value */
	public float getZ() {
		return this.z;
	};
	
	@Override
	public int hashCode() {
		int hash = 7564;
		hash += Float.floatToIntBits(this.x);
		hash += Float.floatToIntBits(this.y);
		hash += Float.floatToIntBits(this.z);
		hash += Float.floatToIntBits(this.w);
		return hash;
	}
	
	/** @brief Inverse the quaternion */
	public Quaternion inverse() {
		final float invLengthSquare = 1.0f / length2();
		this.x *= -invLengthSquare;
		this.y *= -invLengthSquare;
		this.z *= -invLengthSquare;
		this.w *= invLengthSquare;
		return this;
	}
	
	/** @brief Return the inverse of the quaternion
	 * @return inverted quaternion */
	public Quaternion inverseNew() {
		final Quaternion tmp = new Quaternion(this);
		tmp.inverse();
		return tmp;
	}
	
	/** @brief In-Equality compare operator with an other object.
	 * @param obj Reference on the comparing object
	 * @return true The Objects are NOT identical
	 * @return false The Objects are identical */
	public boolean isDifferent(final Quaternion obj) {
		return ((this.w != obj.w) || (this.z != obj.z) || (this.y != obj.y) || (this.x != obj.x));
	}
	
	/** @brief Equality compare operator with an other object.
	 * @param obj Reference on the comparing object
	 * @return true The Objects are identical
	 * @return false The Objects are NOT identical */
	public boolean isEqual(final Quaternion obj) {
		return ((this.w == obj.w) && (this.z == obj.z) && (this.y == obj.y) && (this.x == obj.x));
	}
	
	/** @brief Check if the quaternion is equal to (0,0,0,0)
	 * @return true The value is equal to (0,0,0,0)
	 * @return false The value is NOT equal to (0,0,0,0) */
	public boolean isZero() {
		return this.x == 0 && this.y == 0 && this.z == 0 && this.w == 0;
	}
	
	/** @brief Return the length of the quaternion
	 * @return Length value */
	public float length() {
		return (float) Math.sqrt(length2());
	}
	
	/** @brief Return the squared length of the quaternion.
	 * @return Squared length value. */
	public float length2() {
		return dot(this);
	}
	
	/** @brief Subtract a vector from this one
	 * @param obj The vector to subtract
	 * @return Local reference of the vector */
	public Quaternion less(final Quaternion obj) {
		this.x -= obj.x;
		this.y -= obj.y;
		this.z -= obj.z;
		this.w -= obj.w;
		return this;
	}
	
	/** @brief Subtract a vector from this one
	 * @param obj The vector to subtract
	 * @return New quaternion containing the value */
	public Quaternion lessNew(final Quaternion obj) {
		return new Quaternion(this.x - obj.x, this.y - obj.y, this.z - obj.z, this.w - obj.w);
	}
	
	/** @brief Scale the quaternion
	 * @param val Scale factor
	 * @return Local reference of the quaternion */
	public Quaternion multiply(final float val) {
		this.x *= val;
		this.y *= val;
		this.z *= val;
		this.w *= val;
		return this;
	}
	
	public Vector3f multiply(final float xxx, final float yyy, final float zzz) {
		final Vector3f point = new Vector3f(xxx, yyy, zzz);
		final Vector3f qvec = getVectorV();
		final Vector3f uv = qvec.cross(point);
		final Vector3f uuv = qvec.cross(uv);
		uv.multiply(2.0f * this.w);
		uuv.multiply(2.0f);
		return uv.add(point).add(uuv);
	}
	
	/** @brief Multiply this quaternion by the other.
	 * @param obj The other quaternion
	 * @return Local reference of the quaternion */
	public Quaternion multiply(final Quaternion obj) {
		final Vector3f base = getVectorV();
		final Vector3f crossValue = base.cross(obj.getVectorV());
		this.x = this.w * obj.x + obj.w * this.x + crossValue.x;
		this.y = this.w * obj.y + obj.w * this.y + crossValue.y;
		this.z = this.w * obj.z + obj.w * this.z + crossValue.z;
		this.w = this.w * obj.w - base.dot(obj.getVectorV());
		safeNormalize();
		return this;
	}
	
	/** @brief Operator* with a vector. This methods rotates a point given the rotation of a quaternion
	 * @param point Point to move
	 * @return Point with the updated position */
	public Vector3f multiply(final Vector3f point) {
		final Vector3f qvec = getVectorV();
		final Vector3f uv = qvec.cross(point);
		final Vector3f uuv = qvec.cross(uv);
		uv.multiply(2.0f * this.w);
		uuv.multiply(2.0f);
		return uv.add(point).add(uuv);
	}
	
	/** @brief Scale the quaternion
	 * @param val Scale factor
	 * @return New quaternion containing the value */
	public Quaternion multiplyNew(final float val) {
		return new Quaternion(this.x * val, this.y * val, this.z * val, this.w * val);
	}
	
	/** @brief Multiply this quaternion by the other.
	 * @param obj The other quaternion
	 * @return New quaternion containing the value */
	public Quaternion multiplyNew(final Quaternion obj) {
		final Quaternion tmp = new Quaternion(this);
		tmp.multiply(obj);
		return tmp;
	}
	
	/** @brief Normalize this quaternion x^2 + y^2 + z^2 + w^2 = 1
	 * @return Local reference of the quaternion normalized */
	public Quaternion normalize() {
		final float invLength = 1.0f / length();
		this.x *= invLength;
		this.y *= invLength;
		this.z *= invLength;
		this.w *= invLength;
		return this;
	}
	
	/** @brief Return a normalized version of this quaternion
	 * @return New quaternion containing the value */
	public Quaternion normalizeNew() {
		final Quaternion tmp = new Quaternion(this);
		tmp.normalize();
		return tmp;
	}
	
	/** @brief Normalize this quaternion x^2 + y^2 + z^2 + w^2 = 1
	 * @return Local reference of the quaternion normalized */
	public Quaternion safeNormalize() {
		final float lengthTmp = length();
		if (lengthTmp == 0.0f) {
			this.x = 0.0f;
			this.y = 0.0f;
			this.z = 0.0f;
			this.w = 1.0f;
			return this;
		}
		final float invLength = 1.0f / lengthTmp;
		this.x *= invLength;
		this.y *= invLength;
		this.z *= invLength;
		this.w *= invLength;
		return this;
	}
	
	/** @brief Return a normalized version of this quaternion
	 * @return New quaternion containing the value */
	public Quaternion safeNormalizeNew() {
		final Quaternion tmp = new Quaternion(this);
		tmp.safeNormalize();
		return tmp;
	}
	
	/**
	 * @brief Constructor from scalars.
	 * @param xxx X value
	 * @param yyy Y value
	 * @param zzz Z value
	 * @param www W value */
	public Quaternion set(final float xxx, final float yyy, final float zzz, final float www) {
		this.x = xxx;
		this.y = yyy;
		this.z = zzz;
		this.w = www;
		return this;
	}
	
	/**
	 * @brief Constructor with the component w and a vector 3D.
	 * @param www W value
	 * @param vec 3D vector value
	 */
	public Quaternion set(final float www, final Vector3f obj) {
		this.x = obj.x;
		this.y = obj.y;
		this.z = obj.z;
		this.w = www;
		return this;
	}
	
	public Quaternion set(final Quaternion obj) {
		this.x = obj.x;
		this.y = obj.y;
		this.z = obj.z;
		this.w = obj.w;
		return this;
	}
	
	/** @brief Configure the quaternion with euler angles.
	 * @param angles Eular angle of the quaternion. */
	public void setEulerAngles(final Vector3f angles) {
		float angle = angles.x * 0.5f;
		final float sinX = (float) Math.sin(angle);
		final float cosX = (float) Math.cos(angle);
		angle = angles.y * 0.5f;
		final float sinY = (float) Math.sin(angle);
		final float cosY = (float) Math.cos(angle);
		angle = angles.z * 0.5f;
		final float sinZ = (float) Math.sin(angle);
		final float cosZ = (float) Math.cos(angle);
		final float cosYcosZ = cosY * cosZ;
		final float sinYcosZ = sinY * cosZ;
		final float cosYsinZ = cosY * sinZ;
		final float sinYsinZ = sinY * sinZ;
		this.x = sinX * cosYcosZ - cosX * sinYsinZ;
		this.y = cosX * sinYcosZ + sinX * cosYsinZ;
		this.z = cosX * cosYsinZ - sinX * sinYcosZ;
		this.w = cosX * cosYcosZ + sinX * sinYsinZ;
		normalize();
	}
	
	/** @brief Set identity value at the quaternion */
	public void setIdentity() {
		setValue(0, 0, 0, 1);
	}
	
	/** @brief Set each element to the max of the current values and the values of another Vector
	 * @param obj The other Vector to compare with */
	public void setMax(final Quaternion obj) {
		this.x = Math.max(this.x, obj.x);
		this.y = Math.max(this.y, obj.y);
		this.z = Math.max(this.z, obj.z);
		this.w = Math.max(this.w, obj.w);
	}
	
	/** @brief Set each element to the min of the current values and the values of another Vector
	 * @param obj The other Vector to compare with */
	public void setMin(final Quaternion obj) {
		this.x = Math.min(this.x, obj.x);
		this.y = Math.min(this.y, obj.y);
		this.z = Math.min(this.z, obj.z);
		this.w = Math.min(this.w, obj.w);
	}
	
	/** @brief Set Value on the quaternion
	 * @param xxx X value.
	 * @param yyy Y value.
	 * @param zzz Z value.
	 * @param www W value. */
	public void setValue(final float xxx, final float yyy, final float zzz, final float www) {
		this.x = xxx;
		this.y = yyy;
		this.z = zzz;
		this.w = www;
	}
	
	/** @brief Set the w value
	 * @param w New value */
	public Quaternion setW(final float w) {
		this.w = w;
		return this;
	}
	
	/** @brief Set the x value
	 * @param x New value */
	public Quaternion setX(final float x) {
		this.x = x;
		return this;
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
	
	/** @brief Set the y value
	 * @param y New value */
	public Quaternion setY(final float y) {
		this.y = y;
		return this;
	}
	
	/** @brief Set the z value
	 * @param z New value */
	public Quaternion setZ(final float z) {
		this.z = z;
		return this;
	}
	
	/** @brief Set 0 value on all the quaternion */
	public void setZero() {
		setValue(0, 0, 0, 0);
	}
	
	/** @brief Compute the spherical linear interpolation between two quaternions.
	 * @param obj1 First quaternion
	 * @param obj2 Second quaternion
	 * @param ttt linar coefficient interpolation to be such that [0..1] */
	public Quaternion slerp(final Quaternion obj2, final float ttt) {
		// TKASSERT(ttt >= 0.0f ttt <= 1.0f, "wrong intermolation");
		float invert = 1.0f;
		float cosineTheta = dot(obj2);
		if (cosineTheta < 0.0f) {
			cosineTheta = -cosineTheta;
			invert = -1.0f;
		}
		if (1 - cosineTheta < 0.00001f) {
			return this.multiplyNew(1.0f - ttt).add(obj2.multiplyNew(ttt * invert));
		}
		final float theta = (float) Math.acos(cosineTheta);
		final float sineTheta = (float) Math.sin(theta);
		final float coeff1 = (float) Math.sin((1.0f - ttt) * theta) / sineTheta;
		final float coeff2 = (float) Math.sin(ttt * theta) / sineTheta * invert;
		return this.multiplyNew(coeff1).add(obj2.multiplyNew(coeff2));
	}
	
	@Override
	public String toString() {
		return "Quaternion(" + this.x + "," + this.y + "," + this.z + "," + this.w + ")";
	}
}
