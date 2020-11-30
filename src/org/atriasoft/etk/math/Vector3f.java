package org.atriasoft.etk.math;

public class Vector3f {
	public float x;
	public float y;
	public float z;
	/**
	 * @brief Default contructor
	 */
	public Vector3f() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	/**
	 * @brief Constructor from scalars 
	 * @param xxx X value
	 * @param yyy Y value
	 * @param zzz Z value
	 */
	public Vector3f(float xxx, float yyy, float zzz) {
		this.x = xxx;
		this.y = yyy;
		this.z = zzz;
	}
	/**
	 * @brief Constructor from scalars 
	 * @param value unique value for X,Y and Z value
	 */
	public Vector3f(float value) {
		this.x = value;
		this.y = value;
		this.z = value;
	}
	/**
	 * @brief Constructor from other vector (copy)
	 * @param obj The vector to add to this one
	 */
	public Vector3f(Vector3f obj) {
		this.x += obj.x;
		this.y += obj.y;
		this.z += obj.z;
	}
	/**
	 * @brief Add a vector to this one 
	 * @param obj The vector to add to this one
	 */
	public Vector3f add(Vector3f obj) {
		this.x += obj.x;
		this.y += obj.y;
		this.z += obj.z;
		return this;
	}
	/**
	 * @brief Add a vector to this one 
	 * @param obj The vector to add to this one
	 */
	public Vector3f addNew(Vector3f obj) {
		return new Vector3f(this.x + obj.x,
				this.y + obj.y,
				this.z + obj.z);
	}
	/**
	 * @brief Subtract a vector from this one
	 * @param obj The vector to subtract
	 */
	public Vector3f less(Vector3f obj) {
		this.x -= obj.x; 
		this.y -= obj.y;
		this.z -= obj.z;
		return this;
	}
	/**
	 * @brief Subtract a vector from this one
	 * @param obj The vector to subtract
	 * @return A new vector with the data
	 */
	public Vector3f lessNew(Vector3f obj) {
		return new Vector3f(this.x - obj.x, this.y - obj.y, this.z - obj.z);
	}
	/**
	 * @brief Scale the vector
	 * @param val Scale factor
	 */
	public Vector3f multiply(float val) {
		this.x *= val; 
		this.y *= val;
		this.z *= val;
		return this;
	}
	/**
	 * @brief Scale the vector
	 * @param val Scale factor
	 * @return A new vector with the data
	 */
	public Vector3f multiplyNew(float val) {
		return new Vector3f(this.x * val, this.y * val, this.z * val);
	}
	/**
	 * @brief Inversely scale the vector 
	 * @param val Scale factor to divide by
	 */
	public void devide(float val) {
		if (val != 0.0f) {
			float tmpVal = 1.0f / val;
			this.x *= tmpVal;
			this.y *= tmpVal;
			this.z *= tmpVal;
		}
		// TODO maybe throw ...
	}
	/**
	 * @brief Return the dot product
	 * @param obj The other vector in the dot product
	 * @return Dot product value
	 */
	public float dot(Vector3f obj) {
		return   this.x * obj.x
		       + this.y * obj.y
		       + this.z * obj.z;
	}
	/**
	 * @brief Get the length of the vector squared
	 * @return Squared length value.
	 */
	public float length2() {
		return dot(this);
	}
	/**
	 * @brief Get the length of the vector
	 * @return Length value
	 */
	public float length() {
		return (float) Math.sqrt(length2());
	}
	/**
	 * @brief Return the distance squared between the ends of this and another vector
	 * This is symantically treating the vector like a point
	 * @param obj The other vector to compare distance
	 * @return the square distance of the 2 points
	 */
	public float distance2(Vector3f obj) {
		float deltaX = obj.x - this.x;
		float deltaY = obj.y - this.y;
		float deltaZ = obj.z - this.z;
		return deltaX*deltaX + deltaY*deltaY + deltaZ*deltaZ;
	}
	/**
	 * @brief Return the distance between the ends of this and another vector
	 * This is symantically treating the vector like a point
	 * @param obj The other vector to compare distance
	 * @return the distance of the 2 points
	 */
	public float distance(Vector3f obj) {
		return (float)Math.sqrt(this.distance2(obj));
	}
	/**
	 * @brief Normalize this vector x^2 + y^2 + z^2 = 1 (check if not deviding by 0, if it is the case ==> return (1,0,0))
	 * @return the current vector
	 */
	public Vector3f safeNormalize() {
		float length = length();
		if (length != 0.0f) {
			this.devide(length);
			return this;
		}
		this.setValue(1,0,0);
		return this;
	}
	/**
	 * @brief Normalize this vector x^2 + y^2 + z^2 = 1
	 * @return the current vector
	 */
	public Vector3f normalize() {
		this.devide(this.length());
		return this;
	}
	/**
	 * @brief Return a normalized version of this vector
	 * @return New vector containing the value
	 */
	public Vector3f normalizeNew() {
		Vector3f out = new Vector3f(this);
		out.normalize();
		return out;
	}
	/**
	 * @brief Return a normalized version of this vector (check if not deviding by 0, if it is the case ==> return (1,0,0))
	 * @return New vector containing the value
	 */
	public Vector3f safeNormalizeNew() {
		Vector3f out = new Vector3f(this);
		out.safeNormalize();
		return out;
	}
	/**
	 * @brief Return a rotated version of this vector
	 * @param wAxis The axis to rotate about
	 * @param angle The angle to rotate by
	 * @return New vector containing the value
	 */
	public Vector3f rotateNew( Vector3f wAxis, float angle ) {
		Vector3f out = wAxis.clone();
		out.multiply( wAxis.dot( this ) );
		Vector3f x = this.clone();
		x.less(out);
		Vector3f y = wAxis.cross( this );
		x.multiply((float)Math.cos(angle));
		y.multiply((float)Math.sin(angle));
		out.add(x);
		out.add(y);
		return out;
	}
	/**
	 * @brief Calculate the angle between this and another vector
	 * @param obj The other vector
	 * @return Angle in radian
	 */
	public float angle(Vector3f obj) {
		float s = (float) Math.sqrt(length2() * obj.length2());
		if (0!=s) {
			return (float) Math.acos(this.dot(obj) / s);
		}
		return 0;
	}
	/**
	 * @brief Return a vector will the absolute values of each element
	 * @return the curent reference
	 */
	public Vector3f absolute() {
		this.x = Math.abs(this.x);
		this.y = Math.abs(this.y);
		this.z = Math.abs(this.z);
		return this;
	}
	/**
	 * @brief Return a vector will the absolute values of each element
	 * @return New vector containing the value
	 */
	public Vector3f absoluteNew() {
		return new Vector3f( Math.abs(this.x),
		                     Math.abs(this.y),
		                     Math.abs(this.z));
	}
	/**
	 * @brief Return the cross product between this and another vector
	 * @param obj The other vector
	 * @return Vector with the result of the cross product
	 */
	public Vector3f cross(Vector3f obj) {
		return new Vector3f(this.y * obj.z - this.z * obj.y,
		                    this.z * obj.x - this.x * obj.z,
		                    this.x * obj.y - this.y * obj.x);
	}
	/**
	 * @brief Return the triple product between this and another vector and another
	 * @param obj1 The other vector 1
	 * @param obj2 The other vector 2
	 * @return Value with the result of the triple product
	 */
	public float triple(Vector3f obj1, Vector3f obj2) {
		return   this.x * (obj1.y * obj2.z - obj1.z * obj2.y)
		       + this.y * (obj1.z * obj2.x - obj1.x * obj2.z)
		       + this.z * (obj1.x * obj2.y - obj1.y * obj2.x);
	}
	/**
	 * @brief Return the axis with the smallest value
	 * @return values 0,1,2 for x, y, or z
	 */
	public int minAxis() {
		if (this.x < this.y) {
			return this.x < this.z ? 0 : 2;
		}
		return this.y < this.z ? 1 : 2;
	}
	/**
	 * @brief Return the axis with the largest value
	 * @return values 0,1,2 for x, y, or z
	 */
	public int maxAxis() {
		if (this.x < this.y) {
			return this.y < this.z ? 2 : 1;
		}
		return this.x < this.z ? 2 : 0;
	}
	/**
	 * @brief Return the axis with the smallest ABSOLUTE value
	 * @return values 0,1,2 for x, y, or z
	 */
	public int furthestAxis() {
		return absoluteNew().minAxis();
	}
	/**
	 * @brief Return the axis with the largest ABSOLUTE value
	 * @return values 0,1,2 for x, y, or z
	 */
	public int closestAxis() {
		return absoluteNew().maxAxis();
	}
	/**
	 * @brief Interpolate the vector with a ration between 2 others
	 * @param obj0 First vector
	 * @param obj1 Second vector
	 * @param ratio Ratio between obj0 and obj1
	 */
	public void setInterpolate3(Vector3f obj0, Vector3f obj1, float ratio) {
		float inverse = 1.0f - ratio;
		this.x = inverse * obj0.x + ratio * obj1.x;
		this.y = inverse * obj0.y + ratio * obj1.y;
		this.z = inverse * obj0.z + ratio * obj1.z;
		//		this.co[3] = s * v0[3] + rt * v1[3];
	}
	/**
	 * @brief Return the linear interpolation between this and another vector
	 * @param obj The other vector 
	 * @param ratio The ratio of this to obj (ratio = 0 => return copy of this, ratio=1 => return other)
	 * @return New vector containing the value
	 */
	public Vector3f lerp(Vector3f obj, float ratio) {
		return new Vector3f(this.x + (obj.x - this.x) * ratio,
		                    this.y + (obj.y - this.y) * ratio,
		                    this.z + (obj.z - this.z) * ratio);
	}
	/**
	 * @brief Elementwise multiply this vector by the other
	 * @param obj The other vector
	 * @return the current reference 
	 */
	public Vector3f multiply(Vector3f obj) {
		this.x *= obj.x;
		this.y *= obj.y;
		this.z *= obj.z;
		return this;
	}
	/**
	 * @brief Elementwise multiply this vector by the other
	 * @param obj The other vector
	 */
	public Vector3f multiplyNew(Vector3f obj) {
		return new Vector3f(this.x * obj.x, this.y * obj.y, this.z * obj.z);
	}
	/**
	 * @brief Set the x value
	 * @param x New value
	 */
	public void setX(float x) {
		this.x = x;
	}
	/**
	 * @brief Set the y value
	 * @param y New value
	 */
	public void setY(float y) {
		this.y = y;
	}
	/**
	 * @brief Set the z value
	 * @param z New value
	 */
	public void setZ(float z) {
		this.z = z;
	}
	/**
	 * @brief Get X value
	 * @return the x value
	 */
	public float getX() {
		return this.x;
	}
	/**
	 * @brief Get Y value
	 * @return the y value
	 */
	public float getY() {
		return this.y;
	}
	/**
	 * @brief Get Z value
	 * @return the z value
	 */
	public float getZ() {
		return this.z;
	}
	/**
	 * @brief Equality compare operator with an other object.
	 * @param obj Reference on the comparing object
	 * @return true The Objects are identical
	 * @return false The Objects are NOT identical
	 */
	public boolean isEqual(Vector3f obj) {
		return (    (this.z == obj.z)
		         && (this.y == obj.y)
		         && (this.x == obj.x));
	}
	/**
	 * @brief In-Equality compare operator with an other object.
	 * @param obj Reference on the comparing object
	 * @return true The Objects are NOT identical
	 * @return false The Objects are identical
	 */
	public boolean isDifferent(Vector3f obj) {
		return (    (this.z != obj.z)
		         || (this.y != obj.y)
		         || (this.x != obj.x));
	}
	/**
	 * @brief Set each element to the max of the current values and the values of another Vector3f
	 * @param obj The other Vector3f to compare with 
	 */
	public void setMax(Vector3f obj) {
		this.x = Math.max(this.x, obj.x);
		this.y = Math.max(this.y, obj.y);
		this.z = Math.max(this.z, obj.z);
	}
	/**
	 * @brief Set each element to the min of the current values and the values of another Vector3f
	 * @param obj The other Vector3f to compare with 
	 */
	public void setMin(Vector3f obj) {
		this.x = Math.min(this.x, obj.x);
		this.y = Math.min(this.y, obj.y);
		this.z = Math.min(this.z, obj.z);
	}
	/**
	 * @brief Get the minimum value of the vector (x, y, z)
	 * @return The min value
	 */
	public float getMin() {
		return Math.min(Math.min(this.x, this.y), this.z);
	}
	/**
	 * @brief Get the maximum value of the vector (x, y, z)
	 * @return The max value
	 */
	public float getMax() {
		return Math.max(Math.max(this.x, this.y), this.z);
	}
	/**
	 * @brief Set Value on the vector
	 * @param xxx X value.
	 * @param yyy Y value.
	 * @param zzz Z value.
	 */
	public void setValue(float xxx, float yyy, float zzz) {
		this.x = xxx;
		this.y = yyy;
		this.z = zzz;
	}
	/**
	 * @brief Create a skew matrix of the object
	 * @param obj0 Vector matric first line
	 * @param obj1 Vector matric second line
	 * @param obj2 Vector matric third line
	 */
	public void getSkewSymmetricMatrix(Vector3f obj0,Vector3f obj1,Vector3f obj2) {
		obj0.setValue(0  ,-z ,y);
		obj1.setValue(z  ,0  ,-x);
		obj2.setValue(-y ,x  ,0);
	}
	/**
	 * @brief Set 0 value on all the vector
	 */
	public void setZero() {
		setValue(0,0,0);
	}
	/**
	 * @brief Check if the vector is equal to (0,0,0)
	 * @return true The value is equal to (0,0,0)
	 * @return false The value is NOT equal to (0,0,0)
	 */
	public boolean isZero() {
		return    this.x == 0
		       && this.y == 0
		       && this.z == 0;
	}
	/**
	 * @brief Get the Axis id with the minimum value
	 * @return Axis ID 0,1,2
	 */
	public int getMinAxis() {
		return (this.x < this.y ? (this.x < this.z ? 0 : 2) : (this.y < this.z ? 1 : 2));
	}
	/**
	 * @brief Get the Axis id with the maximum value
	 * @return Axis ID 0,1,2
	 */
	public int getMaxAxis() {
		return (this.x < this.y ? (this.y < this.z ? 2 : 1) : (this.x < this.z ? 2 : 0));
	}
	/**
	 * @breif Get the orthogonal vector of the current vector
	 * @return The ortho vector
	 */
	public Vector3f getOrthoVector() {
		Vector3f vectorAbs = new Vector3f(Math.abs(this.x), Math.abs(this.y), Math.abs(this.z));
		int minElement = vectorAbs.getMinAxis();
		if (minElement == 0) {
			float devider = 1.0f / (float)Math.sqrt(this.y*this.y + this.z*this.z);
			return new Vector3f(0.0f, -this.z*devider, this.y*devider);
		} else if (minElement == 1) {
			float devider = 1.0f / (float)Math.sqrt(this.x*this.x + this.z*this.z);
			return new Vector3f(-this.z*devider, 0.0f, this.x*devider);
		}
		float devider = 1.0f / (float)Math.sqrt(this.x*this.x + this.y*this.y);
		return new Vector3f(-this.y*devider, this.x*devider, 0.0f);
	}
	/**
	 * @brief Clone the current vector.
	 * @return New vector containing the value
	 */
	public Vector3f clone() {
		return new Vector3f(this);
	}
	public static Vector3f zero() {
		return new Vector3f(0,0,0);
	}
	@Override
	public String toString() {
		return "Vector3f(" + this.x + "," + this.y + "," + this.z + ")";
	}
	
	
	
	

	/**
	 * @brief Get the length square between the 2 vectors
	 * @param start First vector
	 * @param stop second vector
	 * @return Length value
	 */
	public static float length2(Vector3f start, Vector3f stop) {
		float x = stop.x - start.x;
		float y = stop.y - start.y;
		float z = stop.z - start.z;
		return x * x + y * y + z * z;
	}
	/**
	 * @brief Get the length between the 2 vectors
	 * @param start First vector
	 * @param stop second vector
	 * @return Length value
	 */
	public float length(Vector3f start, Vector3f stop) {
		return (float) Math.sqrt(length2(start, stop));
	}
}
