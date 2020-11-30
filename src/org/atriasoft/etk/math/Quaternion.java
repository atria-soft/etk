package org.atriasoft.etk.math;

public class Quaternion {
	public float x;
	public float y;
	public float z;
	public float w;

	/** @brief No initialization constructor (faster ...) */
	public Quaternion() {
		this.x = 0.0f;
		this.y = 0.0f;
		this.z = 0.0f;
		this.w = 0.0f;
	}

	/* void checkValues() { if ( isinf(this.x) == true || isnan(this.x) == true || isinf(this.y) == true || isnan(this.y) == true || isinf(this.z) == true || isnan(this.z) == true || isinf(this.w) ==
	 * true || isnan(this.w) == true) { TKCRITICAL("         set transform: (" << this.x << "," << this.y << "," << this.z << "," << this.w << ")"); } } */
	/**
	 * @brief Constructor from scalars.
	 * @param xxx X value
	 * @param yyy Y value
	 * @param zzz Z value
	 * @param www W value */
	public Quaternion(float xxx, float yyy, float zzz, float www) {
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
	public Quaternion(float www, Vector3f vec) {
		this.x = vec.x;
		this.y = vec.y;
		this.z = vec.z;
		this.w = www;
	}

	/** @brief Constructor with Euler angles (in radians) to a quaternion
	 * @param eulerAngles list of all euleu angle */
	public Quaternion(Vector3f eulerAngles) {
		setEulerAngles(eulerAngles);
	}

	/** @brief Create a unit quaternion from a rotation matrix
	 * @param matrix generic matrix */
	public Quaternion(Matrix3f matrix) {
		float trace = matrix.getTrace();
		if (trace < 0.0f) {
			if (matrix.mat[4] > matrix.mat[0]) {
				if (matrix.mat[8] > matrix.mat[4]) {
					float rrr = (float) Math.sqrt(matrix.mat[8] - matrix.mat[0] - matrix.mat[4] + 1.0f);
					float sss = 0.5f / rrr;
					this.x = (matrix.mat[6] + matrix.mat[2]) * sss;
					this.y = (matrix.mat[5] + matrix.mat[7]) * sss;
					this.z = 0.5f * rrr;
					this.w = (matrix.mat[3] - matrix.mat[1]) * sss;
				} else {
					float rrr = (float) Math.sqrt(matrix.mat[4] - matrix.mat[8] - matrix.mat[0] + 1.0f);
					float sss = 0.5f / rrr;
					this.x = (matrix.mat[1] + matrix.mat[3]) * sss;
					this.y = 0.5f * rrr;
					this.z = (matrix.mat[5] + matrix.mat[7]) * sss;
					this.w = (matrix.mat[2] - matrix.mat[6]) * sss;
				}
			} else if (matrix.mat[8] > matrix.mat[0]) {
				float rrr = (float) Math.sqrt(matrix.mat[8] - matrix.mat[0] - matrix.mat[4] + 1.0f);
				float sss = 0.5f / rrr;
				this.x = (matrix.mat[6] + matrix.mat[2]) * sss;
				this.y = (matrix.mat[5] + matrix.mat[7]) * sss;
				this.z = 0.5f * rrr;
				this.w = (matrix.mat[3] - matrix.mat[1]) * sss;
			} else {
				float rrr = (float) Math.sqrt(matrix.mat[0] - matrix.mat[4] - matrix.mat[8] + 1.0f);
				float sss = 0.5f / rrr;
				this.x = 0.5f * rrr;
				this.y = (matrix.mat[1] + matrix.mat[3]) * sss;
				this.z = (matrix.mat[6] - matrix.mat[2]) * sss;
				this.w = (matrix.mat[7] - matrix.mat[5]) * sss;
			}
		} else {
			float rrr = (float) Math.sqrt(trace + 1.0f);
			float sss = 0.5f / rrr;
			this.x = (matrix.mat[7] - matrix.mat[5]) * sss;
			this.y = (matrix.mat[2] - matrix.mat[6]) * sss;
			this.z = (matrix.mat[3] - matrix.mat[1]) * sss;
			this.w = 0.5f * rrr;
		}
	}

	public Quaternion(Quaternion obj) {
		this.x = obj.x;
		this.y = obj.y;
		this.z = obj.z;
		this.w = obj.w;
	}

	/** @brief Add a vector to this one.
	 * @param obj The vector to add to this one
	 * @return Local reference of the vector */
	public Quaternion add(Quaternion obj) {
		this.x += obj.x;
		this.y += obj.y;
		this.z += obj.z;
		this.w += obj.w;
		return this;
	}

	/** @brief Add a vector to this one.
	 * @param obj The vector to add to this one
	 * @return New vector containing the value */
	public Quaternion addNew(Quaternion obj) {
		return new Quaternion(this.x + obj.x, this.y + obj.y, this.z + obj.z, this.w + obj.w);
	}

	/** @brief Subtract a vector from this one
	 * @param obj The vector to subtract
	 * @return Local reference of the vector */
	public Quaternion less(Quaternion obj) {
		this.x -= obj.x;
		this.y -= obj.y;
		this.z -= obj.z;
		this.w -= obj.w;
		return this;
	}

	/** @brief Subtract a vector from this one
	 * @param obj The vector to subtract
	 * @return New quaternion containing the value */
	public Quaternion lessNew(Quaternion obj) {
		return new Quaternion(this.x - obj.x, this.y - obj.y, this.z - obj.z, this.w - obj.w);
	}

	/** @brief Scale the quaternion
	 * @param val Scale factor
	 * @return Local reference of the quaternion */
	public Quaternion multiply(float val) {
		this.x *= val;
		this.y *= val;
		this.z *= val;
		this.w *= val;
		return this;
	}

	/** @brief Scale the quaternion
	 * @param val Scale factor
	 * @return New quaternion containing the value */
	public Quaternion multiplyNew(float val) {
		return new Quaternion(this.x * val, this.y * val, this.z * val, this.w * val);
	}

	/** @brief Inversely scale the quaternion
	 * @param val Scale factor to divide by.
	 * @return Local reference of the quaternion */
	public Quaternion devide(float val) {
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
	public Quaternion devideNew(float val) {
		if (val != 0) {
			return new Quaternion(this.x / val, this.y / val, this.z / val, this.w / val);
		}
		return new Quaternion(this);
	}

	/** @brief Return the dot product
	 * @param obj The other quaternion in the dot product
	 * @return Dot result value */
	public float dot(Quaternion obj) {
		return this.x * obj.x + this.y * obj.y + this.z * obj.z + this.w * obj.w;
	}

	/** @brief Return the squared length of the quaternion.
	 * @return Squared length value. */
	public float length2() {
		return dot(this);
	}

	/** @brief Return the length of the quaternion
	 * @return Length value */
	public float length() {
		return (float) Math.sqrt(length2());
	}

	/** @brief Normalize this quaternion x^2 + y^2 + z^2 + w^2 = 1
	 * @return Local reference of the quaternion normalized */
	public Quaternion normalize() {
		float invLength = 1.0f / length();
		this.x *= invLength;
		this.y *= invLength;
		this.z *= invLength;
		this.w *= invLength;
		return this;
	}

	/** @brief Return a normalized version of this quaternion
	 * @return New quaternion containing the value */
	public Quaternion normalizeNew() {
		Quaternion tmp = new Quaternion(this);
		tmp.normalize();
		return tmp;
	}

	/** @brief Normalize this quaternion x^2 + y^2 + z^2 + w^2 = 1
	 * @return Local reference of the quaternion normalized */
	public Quaternion safeNormalize() {
		float lengthTmp = length();
		if (lengthTmp == 0.0f) {
			this.x = 0.0f;
			this.y = 0.0f;
			this.z = 0.0f;
			this.w = 1.0f;
		}
		float invLength = 1.0f / lengthTmp;
		this.x *= invLength;
		this.y *= invLength;
		this.z *= invLength;
		this.w *= invLength;
		return this;
	}

	/** @brief Return a normalized version of this quaternion
	 * @return New quaternion containing the value */
	public Quaternion safeNormalizeNew() {
		Quaternion tmp = new Quaternion(this);
		tmp.safeNormalize();
		return tmp;
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

	/** @brief Get X value
	 * @return the x value */
	public float getX() {
		return this.x;
	}

	/** @brief Get Y value
	 * @return the y value */
	public float getY() {
		return this.y;
	}

	/** @brief Get Z value
	 * @return the z value */
	public float getZ() {
		return this.z;
	}

	/** @brief Get W value
	 * @return the w value */
	public float getW() {
		return this.w;
	}

	/** @brief Set the x value
	 * @param x New value */
	public Quaternion setX(float x) {
		this.x = x;
		return this;
	};

	/** @brief Set the y value
	 * @param y New value */
	public Quaternion setY(float y) {
		this.y = y;
		return this;
	};

	/** @brief Set the z value
	 * @param z New value */
	public Quaternion setZ(float z) {
		this.z = z;
		return this;
	};

	/** @brief Set the w value
	 * @param w New value */
	public Quaternion setW(float w) {
		this.w = w;
		return this;
	}

	/** @brief Equality compare operator with an other object.
	 * @param obj Reference on the comparing object
	 * @return true The Objects are identical
	 * @return false The Objects are NOT identical */
	public boolean isEqual(Quaternion obj) {
		return ((this.w == obj.w) && (this.z == obj.z) && (this.y == obj.y) && (this.x == obj.x));
	}

	/** @brief In-Equality compare operator with an other object.
	 * @param obj Reference on the comparing object
	 * @return true The Objects are NOT identical
	 * @return false The Objects are identical */
	public boolean isDifferent(Quaternion obj) {
		return ((this.w != obj.w) || (this.z != obj.z) || (this.y != obj.y) || (this.x != obj.x));
	}

	/** @brief Multiply this quaternion by the other.
	 * @param obj The other quaternion
	 * @return Local reference of the quaternion */
	public Quaternion multiply(Quaternion obj) {
		Vector3f base = getVectorV();
		Vector3f crossValue = base.cross(obj.getVectorV());
		this.x = this.w * obj.x + obj.w * this.x + crossValue.x;
		this.y = this.w * obj.y + obj.w * this.y + crossValue.y;
		this.z = this.w * obj.z + obj.w * this.z + crossValue.z;
		this.w = this.w * obj.w - base.dot(obj.getVectorV());
		safeNormalize();
		return this;
	}

	/** @brief Multiply this quaternion by the other.
	 * @param obj The other quaternion
	 * @return New quaternion containing the value */
	public Quaternion multiplyNew(Quaternion obj) {
		Quaternion tmp = new Quaternion(this);
		tmp.multiply(obj);
		return tmp;
	}

	/** @brief Operator* with a vector. This methods rotates a point given the rotation of a quaternion
	 * @param point Point to move
	 * @return Point with the updated position */
	public Vector3f multiply(Vector3f point) {
		Vector3f qvec = getVectorV();
		Vector3f uv = qvec.cross(point);
		Vector3f uuv = qvec.cross(uv);
		uv.multiply(2.0f * this.w);
		uuv.multiply(2.0f);
		return uv.add(point).add(uuv);
	}

	public Vector3f multiply(float xxx, float yyy, float zzz) {
		Vector3f point = new Vector3f(xxx, yyy, zzz);
		Vector3f qvec = getVectorV();
		Vector3f uv = qvec.cross(point);
		Vector3f uuv = qvec.cross(uv);
		uv.multiply(2.0f * this.w);
		uuv.multiply(2.0f);
		return uv.add(point).add(uuv);
	}

	/** @brief Set each element to the max of the current values and the values of another Vector
	 * @param obj The other Vector to compare with */
	public void setMax(Quaternion obj) {
		this.x = Math.max(this.x, obj.x);
		this.y = Math.max(this.y, obj.y);
		this.z = Math.max(this.z, obj.z);
		this.w = Math.max(this.w, obj.w);
	}

	/** @brief Set each element to the min of the current values and the values of another Vector
	 * @param obj The other Vector to compare with */
	public void setMin(Quaternion obj) {
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
	public void setValue(float xxx, float yyy, float zzz, float www) {
		this.x = xxx;
		this.y = yyy;
		this.z = zzz;
		this.w = www;
	}

	/** @brief Set 0 value on all the quaternion */
	public void setZero() {
		setValue(0, 0, 0, 0);
	}

	/** @brief get a 0 value on all a quaternion
	 * @return a (float)Math. quaternion */
	public static Quaternion zero() {
		return new Quaternion(0, 0, 0, 0);
	}

	/** @brief Check if the quaternion is equal to (0,0,0,0)
	 * @return true The value is equal to (0,0,0,0)
	 * @return false The value is NOT equal to (0,0,0,0) */
	public boolean isZero() {
		return this.x == 0 && this.y == 0 && this.z == 0 && this.w == 0;
	}

	/** @brief Set identity value at the quaternion */
	public void setIdentity() {
		setValue(0, 0, 0, 1);
	}

	/** @brief get an identity quaternion
	 * @return an identity quaternion */
	public static Quaternion identity() {
		return new Quaternion(0, 0, 0, 1);
	}

	/** @brief get x, y, z in a Vector3f */
	public Vector3f getVectorV() {
		return new Vector3f(this.x, this.y, this.z);
	}

	/** @brief Inverse the quaternion */
	public void inverse() {
		float invLengthSquare = 1.0f / length2();
		this.x *= -invLengthSquare;
		this.y *= -invLengthSquare;
		this.z *= -invLengthSquare;
		this.w *= invLengthSquare;
	}

	/** @brief Return the inverse of the quaternion
	 * @return inverted quaternion */
	public Quaternion inverseNew() {
		Quaternion tmp = new Quaternion(this);
		tmp.inverse();
		return tmp;
	}

	/** @brief Return the unit quaternion
	 * @return Quaternion unitarised */
	public Quaternion getUnit() {
		return normalizeNew();
	}

	/** @brief Conjugate the quaternion */
	public void conjugate() {
		this.x *= -1.0f;
		this.y *= -1.0f;
		this.z *= -1.0f;
	}

	/** @brief Return the conjugate of the quaternion
	 * @return Conjugate quaternion */
	public Quaternion conjugateNew() {
		Quaternion tmp = new Quaternion(this);
		tmp.conjugate();
		return tmp;
	}

	/** @brief Compute the rotation angle (in radians) and the rotation axis
	 * @param angle Angle of the quaternion
	 * @param axis Axis of the quaternion */
	public void getAngleAxis(float angle, Vector3f axis) {
		Quaternion quaternion = getUnit();
		angle = (float) Math.acos(quaternion.w) * 2.0f;
		Vector3f rotationAxis = new Vector3f(quaternion.x, quaternion.y, quaternion.z);
		rotationAxis = rotationAxis.normalizeNew();
		axis.setValue(rotationAxis.x, rotationAxis.y, rotationAxis.z);
	}

	/** @brief Get the orientation matrix corresponding to this quaternion
	 * @return the 3x3 transformation matrix */
	public Matrix3f getMatrix() {
		float nQ = this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
		float sss = 0.0f;
		if (nQ > 0.0f) {
			sss = 2.0f / nQ;
		}
		float xs = this.x * sss;
		float ys = this.y * sss;
		float zs = this.z * sss;
		float wxs = this.w * xs;
		float wys = this.w * ys;
		float wzs = this.w * zs;
		float xxs = this.x * xs;
		float xys = this.x * ys;
		float xzs = this.x * zs;
		float yys = this.y * ys;
		float yzs = this.y * zs;
		float zzs = this.z * zs;
		return new Matrix3f(1.0f - yys - zzs, xys - wzs, xzs + wys, xys + wzs, 1.0f - xxs - zzs, yzs - wxs, xzs - wys, yzs + wxs, 1.0f - xxs - yys);
	}

	public Matrix4f getMatrix4() {

		float nQ = this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
		float sss = 0.0f;
		if (nQ > 0.0f) {
			sss = 2.0f / nQ;
		}
		float xs = this.x * sss;
		float ys = this.y * sss;
		float zs = this.z * sss;
		float wxs = this.w * xs;
		float wys = this.w * ys;
		float wzs = this.w * zs;
		float xxs = this.x * xs;
		float xys = this.x * ys;
		float xzs = this.x * zs;
		float yys = this.y * ys;
		float yzs = this.y * zs;
		float zzs = this.z * zs;
		return new Matrix4f(1.0f - yys - zzs, xys - wzs, xzs + wys, 0, xys + wzs, 1.0f - xxs - zzs, yzs - wxs, 0, xzs - wys, yzs + wxs, 1.0f - xxs - yys, 0, 0, 0, 0, 1);
	}

	/** @brief Compute the spherical linear interpolation between two quaternions.
	 * @param obj1 First quaternion
	 * @param obj2 Second quaternion
	 * @param ttt linar coefficient interpolation to be such that [0..1] */
	public static Quaternion slerp(Quaternion obj1, Quaternion obj2, float ttt) {
		// TKASSERT(ttt >= 0.0f ttt <= 1.0f, "wrong intermolation");
		float invert = 1.0f;
		float cosineTheta = obj1.dot(obj2);
		if (cosineTheta < 0.0f) {
			cosineTheta = -cosineTheta;
			invert = -1.0f;
		}
		if (1 - cosineTheta < 0.00001f) {
			return obj1.multiplyNew(1.0f - ttt).add(obj2.multiplyNew(ttt * invert));
		}
		float theta = (float) Math.acos(cosineTheta);
		float sineTheta = (float) Math.sin(theta);
		float coeff1 = (float) Math.sin((1.0f - ttt) * theta) / sineTheta;
		float coeff2 = (float) Math.sin(ttt * theta) / sineTheta * invert;
		return obj1.multiplyNew(coeff1).add(obj2.multiplyNew(coeff2));
	}

	/** @brief Configure the quaternion with euler angles.
	 * @param angles Eular angle of the quaternion. */
	public void setEulerAngles(Vector3f angles) {
		float angle = angles.x * 0.5f;
		float sinX = (float) Math.sin(angle);
		float cosX = (float) Math.cos(angle);
		angle = angles.y * 0.5f;
		float sinY = (float) Math.sin(angle);
		float cosY = (float) Math.cos(angle);
		angle = angles.z * 0.5f;
		float sinZ = (float) Math.sin(angle);
		float cosZ = (float) Math.cos(angle);
		float cosYcosZ = cosY * cosZ;
		float sinYcosZ = sinY * cosZ;
		float cosYsinZ = cosY * sinZ;
		float sinYsinZ = sinY * sinZ;
		this.x = sinX * cosYcosZ - cosX * sinYsinZ;
		this.y = cosX * sinYcosZ + sinX * cosYsinZ;
		this.z = cosX * cosYsinZ - sinX * sinYcosZ;
		this.w = cosX * cosYcosZ + sinX * sinYsinZ;
		normalize();
	}

	/** @brief Clone the current Quaternion.
	 * @return New Quaternion containing the value */
	@Override
	public Quaternion clone() {
		return new Quaternion(this);
	}

	@Override
	public String toString() {
		return "Quaternion(" + this.x + "," + this.y + "," + this.z + "," + this.w + ")";
	}

	// a * diff = b
	public static Quaternion diff(Quaternion a, Quaternion b) {
		// Log.info("diff " + a + " " + b);
		Quaternion inv = a.inverseNew();
		return inv.multiply(b);
	}
}
