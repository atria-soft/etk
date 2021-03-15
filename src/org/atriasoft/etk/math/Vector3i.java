package org.atriasoft.etk.math;

import org.atriasoft.etk.internal.Log;

public class Vector3i {
	public static Vector3i valueOf(String value) {
		int val1 = 0;
		int val2 = 0;
		int val3 = 0;
		// copy to permit to modify it :
		while (value.length() > 0 && value.charAt(0) == '(') {
			value = value.substring(1);
		}
		while (value.length() > 0 && value.charAt(0) == ')') {
			value = value.substring(0, value.length() - 1);
		}
		final String[] values = value.split(",");
		if (values.length > 3) {
			Log.error("Can not parse Vector3f with more than 3 values: '" + value + "'");
		}
		if (values.length == 1) {
			// no coma ...
			// in every case, we parse the first element :
			val1 = Integer.valueOf(values[0]);
			val2 = val1;
			val3 = val1;
		} else if (values.length == 1) {
			// no coma ...
			// in every case, we parse the first element :
			val1 = Integer.valueOf(values[0]);
			val2 = Integer.valueOf(values[1]);
			val3 = val2;
		} else {
			val1 = Integer.valueOf(values[0]);
			val2 = Integer.valueOf(values[1]);
			val3 = Integer.valueOf(values[2]);
		}
		return new Vector3i(val1, val2, val3);
	}
	
	public static Vector3i zero() {
		return new Vector3i(0, 0, 0);
	}
	
	public int x = 0;
	public int y = 0;
	public int z = 0;
	
	/**
	 * @brief Default constructor
	 */
	public Vector3i() {}
	
	/**
	 * @brief Constructor from scalars 
	 * @param value unique value for X,Y and Z value
	 */
	public Vector3i(final int value) {
		this.x = value;
		this.y = value;
		this.z = value;
	}
	
	/**
	 * @brief Constructor from scalars 
	 * @param xxx X value
	 * @param yyy Y value
	 * @param zzz Z value
	 */
	public Vector3i(final int xxx, final int yyy, final int zzz) {
		this.x = xxx;
		this.y = yyy;
		this.z = zzz;
	}
	
	/**
	 * @brief Constructor from other vector (copy)
	 * @param obj The vector to add to this one
	 */
	public Vector3i(final Vector3i obj) {
		this.x += obj.x;
		this.y += obj.y;
		this.z += obj.z;
	}
	
	/**
	 * @brief Return a vector will the absolute values of each element
	 * @return the curent reference
	 */
	public Vector3i absolute() {
		this.x = Math.abs(this.x);
		this.y = Math.abs(this.y);
		this.z = Math.abs(this.z);
		return this;
	}
	
	/**
	 * @brief Return a vector will the absolute values of each element
	 * @return New vector containing the value
	 */
	public Vector3i absoluteNew() {
		return new Vector3i(Math.abs(this.x), Math.abs(this.y), Math.abs(this.z));
	}
	
	/**
	 * @brief Add a vector to this one 
	 * @param obj The vector to add to this one
	 */
	public Vector3i add(final Vector3i obj) {
		this.x += obj.x;
		this.y += obj.y;
		this.z += obj.z;
		return this;
	}
	
	/**
	 * @brief Add a vector to this one 
	 * @param obj The vector to add to this one
	 */
	public Vector3i addNew(final Vector3i obj) {
		return new Vector3i(this.x + obj.x, this.y + obj.y, this.z + obj.z);
	}
	
	/**
	 * @brief Calculate the angle between this and another vector
	 * @param obj The other vector
	 * @return Angle in radian
	 */
	public int angle(final Vector3i obj) {
		final int s = (int) Math.sqrt(length2() * obj.length2());
		if (0 != s) {
			return (int) Math.acos(dot(obj) / s);
		}
		return 0;
	}
	
	/**
	 * @brief Clone the current vector.
	 * @return New vector containing the value
	 */
	@Override
	public Vector3i clone() {
		return new Vector3i(this);
	}
	
	/**
	 * @brief Return the axis with the largest ABSOLUTE value
	 * @return values 0,1,2 for x, y, or z
	 */
	public int closestAxis() {
		return absoluteNew().maxAxis();
	}
	
	/**
	 * @brief Return the cross product between this and another vector
	 * @param obj The other vector
	 * @return Vector with the result of the cross product
	 */
	public Vector3i cross(final Vector3i obj) {
		return new Vector3i(this.y * obj.z - this.z * obj.y, this.z * obj.x - this.x * obj.z, this.x * obj.y - this.y * obj.x);
	}
	
	/**
	 * @brief Inversely scale the vector 
	 * @param val Scale factor to divide by
	 */
	public void devide(final int val) {
		if (val != 0.0f) {
			this.x /= val;
			this.y /= val;
			this.z /= val;
		}
		// TODO maybe throw ...
	}
	
	/**
	 * @brief Return the distance between the ends of this and another vector
	 * This is symantically treating the vector like a point
	 * @param obj The other vector to compare distance
	 * @return the distance of the 2 points
	 */
	public int distance(final Vector3i obj) {
		return (int) Math.sqrt(distance2(obj));
	}
	
	/**
	 * @brief Return the distance squared between the ends of this and another vector
	 * This is symantically treating the vector like a point
	 * @param obj The other vector to compare distance
	 * @return the square distance of the 2 points
	 */
	public int distance2(final Vector3i obj) {
		final int deltaX = obj.x - this.x;
		final int deltaY = obj.y - this.y;
		final int deltaZ = obj.z - this.z;
		return deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
	}
	
	/**
	 * @brief Return the dot product
	 * @param obj The other vector in the dot product
	 * @return Dot product value
	 */
	public int dot(final Vector3i obj) {
		return this.x * obj.x + this.y * obj.y + this.z * obj.z;
	}
	
	/**
	 * @brief Return the axis with the smallest ABSOLUTE value
	 * @return values 0,1,2 for x, y, or z
	 */
	public int furthestAxis() {
		return absoluteNew().minAxis();
	}
	
	/**
	 * @brief Get the maximum value of the vector (x, y, z)
	 * @return The max value
	 */
	public int getMax() {
		return Math.max(Math.max(this.x, this.y), this.z);
	}
	
	/**
	 * @brief Get the Axis id with the maximum value
	 * @return Axis ID 0,1,2
	 */
	public int getMaxAxis() {
		return (this.x < this.y ? (this.y < this.z ? 2 : 1) : (this.x < this.z ? 2 : 0));
	}
	
	/**
	 * @brief Get the minimum value of the vector (x, y, z)
	 * @return The min value
	 */
	public int getMin() {
		return Math.min(Math.min(this.x, this.y), this.z);
	}
	
	/**
	 * @brief Get the Axis id with the minimum value
	 * @return Axis ID 0,1,2
	 */
	public int getMinAxis() {
		return (this.x < this.y ? (this.x < this.z ? 0 : 2) : (this.y < this.z ? 1 : 2));
	}
	
	/**
	 * @brief Create a skew matrix of the object
	 * @param obj0 Vector matric first line
	 * @param obj1 Vector matric second line
	 * @param obj2 Vector matric third line
	 */
	public void getSkewSymmetricMatrix(final Vector3i obj0, final Vector3i obj1, final Vector3i obj2) {
		obj0.setValue(0, -this.z, this.y);
		obj1.setValue(this.z, 0, -this.x);
		obj2.setValue(-this.y, this.x, 0);
	}
	
	/**
	 * @brief Get X value
	 * @return the x value
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * @brief Get Y value
	 * @return the y value
	 */
	public int getY() {
		return this.y;
	}
	
	/**
	 * @brief Get Z value
	 * @return the z value
	 */
	public int getZ() {
		return this.z;
	}
	
	/**
	 * @brief In-Equality compare operator with an other object.
	 * @param obj Reference on the comparing object
	 * @return true The Objects are NOT identical
	 * @return false The Objects are identical
	 */
	public boolean isDifferent(final Vector3i obj) {
		return ((this.z != obj.z) || (this.y != obj.y) || (this.x != obj.x));
	}
	
	/**
	 * @brief Equality compare operator with an other object.
	 * @param obj Reference on the comparing object
	 * @return true The Objects are identical
	 * @return false The Objects are NOT identical
	 */
	public boolean isEqual(final Vector3i obj) {
		return ((this.z == obj.z) && (this.y == obj.y) && (this.x == obj.x));
	}
	
	/**
	 * @brief Check if the vector is equal to (0,0,0)
	 * @return true The value is equal to (0,0,0)
	 * @return false The value is NOT equal to (0,0,0)
	 */
	public boolean isZero() {
		return this.x == 0 && this.y == 0 && this.z == 0;
	}
	
	/**
	 * @brief Get the length of the vector
	 * @return Length value
	 */
	public int length() {
		return (int) Math.sqrt(length2());
	}
	
	/**
	 * @brief Get the length of the vector squared
	 * @return Squared length value.
	 */
	public int length2() {
		return dot(this);
	}
	
	/**
	 * @brief Return the linear interpolation between this and another vector
	 * @param obj The other vector 
	 * @param ratio The ratio of this to obj (ratio = 0 => return copy of this, ratio=1 => return other)
	 * @return New vector containing the value
	 */
	public Vector3i lerp(final Vector3i obj, final int ratio) {
		return new Vector3i(this.x + (obj.x - this.x) * ratio, this.y + (obj.y - this.y) * ratio, this.z + (obj.z - this.z) * ratio);
	}
	
	/**
	 * @brief Subtract a vector from this one
	 * @param obj The vector to subtract
	 */
	public Vector3i less(final Vector3i obj) {
		this.x -= obj.x;
		this.y -= obj.y;
		this.z -= obj.z;
		return this;
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
	 * @brief Scale the vector
	 * @param val Scale factor
	 */
	public Vector3i multiply(final int val) {
		this.x *= val;
		this.y *= val;
		this.z *= val;
		return this;
	}
	
	/**
	 * @brief Elementwise multiply this vector by the other
	 * @param obj The other vector
	 * @return the current reference 
	 */
	public Vector3i multiply(final Vector3i obj) {
		this.x *= obj.x;
		this.y *= obj.y;
		this.z *= obj.z;
		return this;
	}
	
	/**
	 * @brief Scale the vector
	 * @param val Scale factor
	 */
	public Vector3i multiplyNew(final int val) {
		return new Vector3i(this.x * val, this.y * val, this.z * val);
	}
	
	/**
	 * @brief Elementwise multiply this vector by the other
	 * @param obj The other vector
	 */
	public Vector3i multiplyNew(final Vector3i obj) {
		this.x *= obj.x;
		this.y *= obj.y;
		this.z *= obj.z;
		return this;
	}
	
	/**
	 * @brief Normalize this vector x^2 + y^2 + z^2 = 1
	 */
	public void normalize() {
		devide(length());
	}
	
	/**
	 * @brief Return a normalized version of this vector
	 * @return New vector containing the value
	 */
	public Vector3i normalizeNew() {
		final Vector3i out = new Vector3i(this);
		out.normalize();
		return out;
	}
	
	/**
	 * @brief Return a rotated version of this vector
	 * @param wAxis The axis to rotate about
	 * @param angle The angle to rotate by
	 * @return New vector containing the value
	 */
	public Vector3i rotateNew(final Vector3i wAxis, final int angle) {
		final Vector3i out = wAxis.clone();
		out.multiply(wAxis.dot(this));
		final Vector3i x = clone();
		x.less(out);
		final Vector3i y = wAxis.cross(this);
		x.multiply((int) Math.cos(angle));
		y.multiply((int) Math.sin(angle));
		out.add(x);
		out.add(y);
		return out;
	}
	
	/**
	 * @brief Normalize this vector x^2 + y^2 + z^2 = 1 (check if not deviding by 0, if it is the case ==> return (1,0,0))
	 */
	public void safeNormalize() {
		final int length = length();
		if (length != 0.0f) {
			devide(length);
		}
		setValue(1, 0, 0);
	}
	
	/**
	 * @brief Return a normalized version of this vector (check if not deviding by 0, if it is the case ==> return (1,0,0))
	 * @return New vector containing the value
	 */
	public Vector3i safeNormalizeNew() {
		final Vector3i out = new Vector3i(this);
		out.safeNormalize();
		return out;
	}
	
	/**
	 * @brief Set each element to the max of the current values and the values of another Vector3f
	 * @param obj The other Vector3f to compare with 
	 */
	public void setMax(final Vector3i obj) {
		this.x = Math.max(this.x, obj.x);
		this.y = Math.max(this.y, obj.y);
		this.z = Math.max(this.z, obj.z);
	}
	
	/**
	 * @brief Set each element to the min of the current values and the values of another Vector3f
	 * @param obj The other Vector3f to compare with 
	 */
	public void setMin(final Vector3i obj) {
		this.x = Math.min(this.x, obj.x);
		this.y = Math.min(this.y, obj.y);
		this.z = Math.min(this.z, obj.z);
	}
	
	/**
	 * @brief Set Value on the vector
	 * @param xxx X value.
	 * @param yyy Y value.
	 * @param zzz Z value.
	 */
	public void setValue(final int xxx, final int yyy, final int zzz) {
		this.x = xxx;
		this.y = yyy;
		this.z = zzz;
	}
	
	/**
	 * @brief Set the x value
	 * @param x New value
	 */
	public void setX(final int x) {
		this.x = x;
	}
	
	/**
	 * @brief Set the y value
	 * @param y New value
	 */
	public void setY(final int y) {
		this.y = y;
	}
	
	/**
	 * @brief Set the z value
	 * @param z New value
	 */
	public void setZ(final int z) {
		this.z = z;
	}
	
	/**
	 * @brief Set 0 value on all the vector
	 */
	public void setZero() {
		setValue(0, 0, 0);
	}
	
	@Override
	public String toString() {
		return "Vector3i(" + this.x + "," + this.y + "," + this.z + ")";
	}
	
	/**
	 * @brief Return the triple product between this and another vector and another
	 * @param obj1 The other vector 1
	 * @param obj2 The other vector 2
	 * @return Value with the result of the triple product
	 */
	public int triple(final Vector3i obj1, final Vector3i obj2) {
		return this.x * (obj1.y * obj2.z - obj1.z * obj2.y) + this.y * (obj1.z * obj2.x - obj1.x * obj2.z) + this.z * (obj1.x * obj2.y - obj1.y * obj2.x);
	}
}
