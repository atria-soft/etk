package org.atriasoft.etk.math;

import org.atriasoft.etk.Uri;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umd.cs.findbugs.annotations.CheckReturnValue;

public record Vector3i(
		int x,
		int y,
		int z) {
	final static Logger LOGGER = LoggerFactory.getLogger(Vector3i.class);
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
		final String[] values = value.split(",| ");
		if (values.length > 3) {
			LOGGER.warn("Can not parse Vector3i with more than 3 values: '{}'", value);
		}
		if (values.length == 1) {
			// no coma ...
			// in every case, we parse the first element :
			val1 = Integer.valueOf(values[0]);
			val2 = val1;
			val3 = val1;
		} else if (values.length == 2) {
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
	
	/**
	 * Default constructor
	 */
	public Vector3i() {
		this(0, 0, 0);
	}
	
	public Vector3i(final int x, final int y, final int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public static Vector3i max(final Vector3i obj1, final Vector3i obj2) {
		return new Vector3i(Math.max(obj1.x, obj2.x), Math.max(obj1.y, obj2.y), Math.max(obj1.z, obj2.z));
	}
	
	public static Vector3i min(final Vector3i obj1, final Vector3i obj2) {
		return new Vector3i(Math.min(obj1.x, obj2.x), Math.min(obj1.y, obj2.y), Math.min(obj1.z, obj2.z));
	}
	
	/**
	 * Constructor from scalars
	 * @param value unique value for X,Y and Z value
	 */
	public Vector3i(final int value) {
		this(value, value, value);
	}
	
	/**
	 * Return a vector will the absolute values of each element
	 * @return the curent reference
	 */
	@CheckReturnValue
	public Vector3i abs() {
		return new Vector3i(Math.abs(this.x), Math.abs(this.y), Math.abs(this.z));
	}
	
	/**
	 * Add a vector to this one
	 * @param obj The vector to add to this one
	 */
	@CheckReturnValue
	public Vector3i add(final Vector3i obj) {
		return new Vector3i(this.x + obj.x, this.y + obj.y, this.z + obj.z);
	}
	
	@CheckReturnValue
	public Vector3i add(final int value) {
		return new Vector3i(this.x + value, this.y + value, this.z + value);
	}
	
	@CheckReturnValue
	public Vector3i add(final int xxx, final int yyy, final int zzz) {
		return new Vector3i(this.x + xxx, this.y + yyy, this.z + zzz);
	}
	
	/**
	 * Calculate the angle between this and another vector
	 * @param obj The other vector
	 * @return Angle in radian
	 */
	@CheckReturnValue
	public int angle(final Vector3i obj) {
		final int s = (int) Math.sqrt(length2() * obj.length2());
		if (0 != s) {
			return (int) Math.acos(dot(obj) / s);
		}
		return 0;
	}
	
	/**
	 * Return the axis with the largest ABSOLUTE value
	 * @return values 0,1,2 for x, y, or z
	 */
	@CheckReturnValue
	public int closestAxis() {
		return abs().maxAxis();
	}
	
	/**
	 * Return the cross product between this and another vector
	 * @param obj The other vector
	 * @return Vector with the result of the cross product
	 */
	@CheckReturnValue
	public Vector3i cross(final Vector3i obj) {
		return new Vector3i(this.y * obj.z - this.z * obj.y, this.z * obj.x - this.x * obj.z, this.x * obj.y - this.y * obj.x);
	}
	
	/**
	 * Inversely scale the vector
	 * @param val Scale factor to divide by
	 */
	@CheckReturnValue
	public Vector3i devide(final int val) {
		if (val != 0.0f) {
			return new Vector3i(this.x / val, this.y / val, this.z / val);
		}
		throw new ArithmeticException("Vector3i devide by 0");
	}
	
	/**
	 * Return the distance between the ends of this and another vector
	 * This is semantically treating the vector like a point
	 * @param obj The other vector to compare distance
	 * @return the distance of the 2 points
	 */
	@CheckReturnValue
	public float distance(final Vector3i obj) {
		return FMath.sqrt(distance2(obj));
	}
	
	/**
	 * Return the distance between the ends of this and another vector
	 * This is semantically treating the vector like a point
	 * @param xxx X position.
	 * @param yyy Y position.
	 * @param zzz Z position.
	 * @return the distance of the 2 points
	 */
	@CheckReturnValue
	public float distance(final int xxx, final int yyy, final int zzz) {
		return FMath.sqrt(distance2(xxx, yyy, zzz));
	}
	
	/**
	 * Return the distance squared between the ends of this and another vector
	 * This is semantically treating the vector like a point
	 * @param obj The other vector to compare distance
	 * @return The square distance of the 2 points.
	 */
	@CheckReturnValue
	public int distance2(final Vector3i obj) {
		final int deltaX = obj.x - this.x;
		final int deltaY = obj.y - this.y;
		final int deltaZ = obj.z - this.z;
		return deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
	}
	
	/**
	 * Return the distance squared between the ends of this and another vector
	 * This is semantically treating the vector like a point
	 * @param xxx X position.
	 * @param yyy Y position.
	 * @param zzz Z position.
	 * @return The square distance of the 2 points.
	 */
	@CheckReturnValue
	public int distance2(final int xxx, final int yyy, final int zzz) {
		final int deltaX = xxx - this.x;
		final int deltaY = yyy - this.y;
		final int deltaZ = zzz - this.z;
		return deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
	}
	
	/**
	 * Return the dot product
	 * @param obj The other vector in the dot product
	 * @return Dot product value
	 */
	@CheckReturnValue
	public int dot(final Vector3i obj) {
		return this.x * obj.x + this.y * obj.y + this.z * obj.z;
	}
	
	/**
	 * Return the axis with the smallest ABSOLUTE value
	 * @return values 0,1,2 for x, y, or z
	 */
	@CheckReturnValue
	public int furthestAxis() {
		return abs().minAxis();
	}
	
	/**
	 * Get the maximum value of the vector (x, y, z)
	 * @return The max value
	 */
	@CheckReturnValue
	public int getMax() {
		return Math.max(Math.max(this.x, this.y), this.z);
	}
	
	/**
	 * Get the Axis id with the maximum value
	 * @return Axis ID 0,1,2
	 */
	@CheckReturnValue
	public int getMaxAxis() {
		return (this.x < this.y ? (this.y < this.z ? 2 : 1) : (this.x < this.z ? 2 : 0));
	}
	
	/**
	 * Get the minimum value of the vector (x, y, z)
	 * @return The min value
	 */
	@CheckReturnValue
	public int getMin() {
		return Math.min(Math.min(this.x, this.y), this.z);
	}
	
	/**
	 * Get the Axis id with the minimum value
	 * @return Axis ID 0,1,2
	 */
	@CheckReturnValue
	public int getMinAxis() {
		return (this.x < this.y ? (this.x < this.z ? 0 : 2) : (this.y < this.z ? 1 : 2));
	}
	
	@CheckReturnValue
	public Vector3i getSkewSymmetricMatrix0() {
		return new Vector3i(0, -this.z, this.y);
	}
	
	@CheckReturnValue
	public Vector3i getSkewSymmetricMatrix1() {
		return new Vector3i(this.z, 0, -this.x);
	}
	
	@CheckReturnValue
	public Vector3i getSkewSymmetricMatrix2() {
		return new Vector3i(-this.y, this.x, 0);
	}
	
	/**
	 * In-Equality compare operator with an other object.
	 * @param obj Reference on the comparing object
	 * @return true The Objects are NOT identical
	 * @return false The Objects are identical
	 */
	@CheckReturnValue
	public boolean isDifferent(final Vector3i obj) {
		return ((this.z != obj.z) || (this.y != obj.y) || (this.x != obj.x));
	}
	
	/**
	 * Equality compare operator with an other object.
	 * @param obj Reference on the comparing object
	 * @return true The Objects are identical
	 * @return false The Objects are NOT identical
	 */
	@CheckReturnValue
	public boolean isEqual(final Vector3i obj) {
		return ((this.z == obj.z) && (this.y == obj.y) && (this.x == obj.x));
	}
	
	/**
	 * Check if the vector is equal to (0,0,0)
	 * @return true The value is equal to (0,0,0)
	 * @return false The value is NOT equal to (0,0,0)
	 */
	@CheckReturnValue
	public boolean isZero() {
		return this.x == 0 && this.y == 0 && this.z == 0;
	}
	
	/**
	 * Get the length of the vector
	 * @return Length value
	 */
	@CheckReturnValue
	public int length() {
		return (int) Math.sqrt(length2());
	}
	
	/**
	 * Get the length of the vector squared
	 * @return Squared length value.
	 */
	@CheckReturnValue
	public int length2() {
		return dot(this);
	}
	
	/**
	 * Return the linear interpolation between this and another vector
	 * @param obj The other vector
	 * @param ratio The ratio of this to obj (ratio = 0 => return copy of this, ratio=1 => return other)
	 * @return New vector containing the value
	 */
	@CheckReturnValue
	public Vector3i lerp(final Vector3i obj, final int ratio) {
		return new Vector3i(this.x + (obj.x - this.x) * ratio, this.y + (obj.y - this.y) * ratio, this.z + (obj.z - this.z) * ratio);
	}
	
	/**
	 * Subtract a vector from this one
	 * @param obj The vector to subtract
	 */
	@CheckReturnValue
	public Vector3i less(final Vector3i obj) {
		return new Vector3i(this.x - obj.x, this.y - obj.y, this.z - obj.z);
	}
	
	@CheckReturnValue
	public Vector3i less(final int value) {
		return new Vector3i(this.x - value, this.y - value, this.z - value);
	}
	
	@CheckReturnValue
	public Vector3i less(final int xxx, final int yyy, final int zzz) {
		return new Vector3i(this.x - xxx, this.y - yyy, this.z - zzz);
	}
	
	/**
	 * Return the axis with the largest value
	 * @return values 0,1,2 for x, y, or z
	 */
	@CheckReturnValue
	public int maxAxis() {
		if (this.x < this.y) {
			return this.y < this.z ? 2 : 1;
		}
		return this.x < this.z ? 2 : 0;
	}
	
	/**
	 * Return the axis with the smallest value
	 * @return values 0,1,2 for x, y, or z
	 */
	@CheckReturnValue
	public int minAxis() {
		if (this.x < this.y) {
			return this.x < this.z ? 0 : 2;
		}
		return this.y < this.z ? 1 : 2;
	}
	
	/**
	 * Scale the vector
	 * @param val Scale factor
	 */
	@CheckReturnValue
	public Vector3i multiply(final int val) {
		return new Vector3i(this.x * val, this.y * val, this.z * val);
	}
	
	/**
	 * Normalize this vector x^2 + y^2 + z^2 = 1
	 * @return New vector containing the value
	 */
	@CheckReturnValue
	public Vector3i normalize() {
		return devide(length());
	}
	
	/**
	 * Return a rotated version of this vector
	 * @param wAxis The axis to rotate about
	 * @param angle The angle to rotate by
	 * @return New vector containing the value
	 */
	@CheckReturnValue
	public Vector3i rotate(final Vector3i wAxis, final int angle) {
		Vector3i out = wAxis.multiply(wAxis.dot(this));
		Vector3i x = less(out);
		Vector3i y = wAxis.cross(this);
		x = x.multiply((int) Math.cos(angle));
		y = y.multiply((int) Math.sin(angle));
		out = out.add(x);
		out = out.add(y);
		return out;
	}
	
	/**
	 * Normalize this vector x^2 + y^2 + z^2 = 1 (check if not dividing by 0, if it is the case ==> return (1,0,0))
	 * @return New vector containing the value
	 */
	@CheckReturnValue
	public Vector3i safeNormalize() {
		final int length = length();
		if (length != 0.0f) {
			return devide(length);
		}
		return new Vector3i(1, 0, 0);
	}
	
	/**
	 * Set each element to the max of the current values and the values of another Vector3i
	 * @param obj The other Vector3i to compare with
	 */
	@CheckReturnValue
	public Vector3i max(final Vector3i obj) {
		return new Vector3i(Math.max(this.x, obj.x), Math.max(this.y, obj.y), Math.max(this.z, obj.z));
	}
	
	@CheckReturnValue
	public Vector3i max(final int xxx, final int yyy, final int zzz) {
		return new Vector3i(Math.max(this.x, xxx), Math.max(this.y, yyy), Math.max(this.z, zzz));
	}
	
	@CheckReturnValue
	public Vector3i min(final int xxx, final int yyy, final int zzz) {
		return new Vector3i(Math.min(this.x, xxx), Math.min(this.y, yyy), Math.min(this.z, zzz));
	}
	
	/**
	 * Set each element to the min of the current values and the values of another Vector3i
	 * @param obj The other Vector3i to compare with
	 */
	@CheckReturnValue
	public Vector3i min(final Vector3i obj) {
		return new Vector3i(Math.min(this.x, obj.x), Math.min(this.y, obj.y), Math.min(this.z, obj.z));
	}
	
	/**
	 * Set 0 value on all the vector
	 */
	public static final Vector3i ZERO = new Vector3i(0, 0, 0);
	public static final Vector3i ONE = new Vector3i(1, 1, 1);
	public static final Vector3i VALUE_2 = new Vector3i(2, 2, 2);
	public static final Vector3i VALUE_4 = new Vector3i(4, 4, 4);
	public static final Vector3i VALUE_8 = new Vector3i(8, 8, 8);
	public static final Vector3i VALUE_16 = new Vector3i(16, 16, 16);
	public static final Vector3i VALUE_32 = new Vector3i(32, 32, 32);
	public static final Vector3i VALUE_64 = new Vector3i(64, 64, 64);
	public static final Vector3i VALUE_128 = new Vector3i(128, 128, 128);
	public static final Vector3i VALUE_256 = new Vector3i(256, 256, 256);
	public static final Vector3i VALUE_512 = new Vector3i(512, 512, 512);
	public static final Vector3i VALUE_1024 = new Vector3i(1024, 1024, 1024);
	
	@Override
	public String toString() {
		return "Vector3i(" + this.x + "," + this.y + "," + this.z + ")";
	}
	
	/**
	 * Return the triple product between this and another vector and another
	 * @param obj1 The other vector 1
	 * @param obj2 The other vector 2
	 * @return Value with the result of the triple product
	 */
	@CheckReturnValue
	public int triple(final Vector3i obj1, final Vector3i obj2) {
		return this.x * (obj1.y * obj2.z - obj1.z * obj2.y) + this.y * (obj1.z * obj2.x - obj1.x * obj2.z) + this.z * (obj1.x * obj2.y - obj1.y * obj2.x);
	}
	
	@CheckReturnValue
	public Vector3i withX(final int xxx) {
		return new Vector3i(xxx, this.y, this.z);
	}
	
	@CheckReturnValue
	public Vector3i withY(final int yyy) {
		return new Vector3i(this.x, yyy, this.z);
	}
	
	@CheckReturnValue
	public Vector3i withZ(final int zzz) {
		return new Vector3i(this.x, this.y, zzz);
	}
}
