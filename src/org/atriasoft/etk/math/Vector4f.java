
package org.atriasoft.etk.math;

import org.atriasoft.etk.internal.Log;

import edu.umd.cs.findbugs.annotations.CheckReturnValue;

public record Vector4f(
		float x,
		float y,
		float z,
		float w) {
	/**
	 *  Get the length square between the 2 vectors
	 * @param start First vector
	 * @param stop  second vector
	 * @return Length value
	 */
	public static float length2(final Vector4f start, final Vector4f stop) {
		final float x = stop.x - start.x;
		final float y = stop.y - start.y;
		final float z = stop.z - start.z;
		final float w = stop.w - start.w;
		return x * x + y * y + z * z + w * w;
	}
	
	public static Vector4f valueOf(String value) {
		float val1 = 0;
		float val2 = 0;
		float val3 = 0;
		float val4 = 0;
		// copy to permit to modify it :
		while (value.length() > 0 && value.charAt(0) == '(') {
			value = value.substring(1);
		}
		while (value.length() > 0 && value.charAt(0) == ')') {
			value = value.substring(0, value.length() - 1);
		}
		final String[] values = value.split(",| ");
		if (values.length > 3) {
			Log.error("Can not parse Vector4f with more than 3 values: '" + value + "'");
		}
		if (values.length == 1) {
			// no coma ...
			// in every case, we parse the first element :
			val1 = Float.valueOf(values[0]);
			val2 = val1;
			val3 = val1;
			val4 = val1;
		} else if (values.length == 2) {
			// no coma ...
			// in every case, we parse the first element :
			val1 = Float.valueOf(values[0]);
			val2 = Float.valueOf(values[1]);
			val3 = val2;
			val4 = val2;
		} else if (values.length == 3) {
			val1 = Float.valueOf(values[0]);
			val2 = Float.valueOf(values[1]);
			val3 = Float.valueOf(values[2]);
			val4 = val3;
		} else {
			val1 = Float.valueOf(values[0]);
			val2 = Float.valueOf(values[1]);
			val3 = Float.valueOf(values[2]);
			val4 = Float.valueOf(values[3]);
		}
		return new Vector4f(val1, val2, val3, val4);
	}
	
	/**
	 *  Constructor from scalars
	 * @param value unique value for X,Y and Z value
	 */
	public Vector4f(final float value) {
		this(value, value, value, value);
	}
	
	public Vector4f(final float x, final float y, final float z, final float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	/**
	 *  Return a vector will the absolute values of each element
	 * @return the curent reference
	 */
	@CheckReturnValue
	public Vector4f abs() {
		return new Vector4f(Math.abs(this.x), Math.abs(this.y), Math.abs(this.z), Math.abs(this.w));
	}
	
	@CheckReturnValue
	public Vector4f add(final float value) {
		return new Vector4f(this.x + value, this.y + value, this.z + value, this.w + value);
	}
	
	@CheckReturnValue
	public Vector4f add(final float xxx, final float yyy, final float zzz, final float www) {
		return new Vector4f(this.x + xxx, this.y + yyy, this.z + zzz, this.w + www);
	}
	
	@CheckReturnValue
	public Vector4f clipInteger() {
		return new Vector4f((int) this.x, (int) this.y, (int) this.z, (int) this.w);
	}
	
	/**
	 *  Add a vector to this one
	 * @param obj The vector to add to this one
	 */
	@CheckReturnValue
	public Vector4f add(final Vector4f obj) {
		return new Vector4f(this.x + obj.x, this.y + obj.y, this.z + obj.w, this.z + obj.w);
	}
	
	/**
	 *  Calculate the angle between this and another vector
	 * @param obj The other vector
	 * @return Angle in radian
	 */
	@CheckReturnValue
	public float angle(final Vector4f obj) {
		final float s = (float) Math.sqrt(length2() * obj.length2());
		if (0 != s) {
			return (float) Math.acos(dot(obj) / s);
		}
		return 0;
	}
	
	@CheckReturnValue
	public Vector4f clamp(final float maxLength) {
		if (length2() > maxLength * maxLength) {
			return safeNormalize().multiply(maxLength);
		}
		return this;
	}
	
	/**
	 *  Return the axis with the largest ABSOLUTE value
	 * @return values 0,1,2 for x, y, or z
	 */
	@CheckReturnValue
	public int closestAxis() {
		return abs().getMaxAxis();
	}
	
	/**
	 *  Return the distance between the ends of this and another vector This
	 *        is symantically treating the vector like a point
	 * @param obj The other vector to compare distance
	 * @return the distance of the 2 points
	 */
	@CheckReturnValue
	public float distance(final Vector4f obj) {
		return (float) Math.sqrt(distance2(obj));
	}
	
	/**
	 *  Return the distance squared between the ends of this and another
	 *        vector This is symantically treating the vector like a point
	 * @param obj The other vector to compare distance
	 * @return the square distance of the 2 points
	 */
	@CheckReturnValue
	public float distance2(final Vector4f obj) {
		final float deltaX = obj.x - this.x;
		final float deltaY = obj.y - this.y;
		final float deltaZ = obj.z - this.z;
		final float deltaW = obj.w - this.w;
		return deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ + deltaW * deltaW;
	}
	
	/**
	 *  Inversely scale the vector
	 * @param val Scale factor to divide by
	 */
	@CheckReturnValue
	public Vector4f divide(final float val) {
		if (val != 0.0f) {
			return new Vector4f(this.x / val, this.y / val, this.z / val, this.w / val);
		}
		throw new IllegalArgumentException("divice by 0 (Vector4f)");
	}
	
	/**
	 *  Inversely scale the vector
	 * @param val Scale factor to divide by
	 */
	@CheckReturnValue
	public Vector4f divide(final Vector4f val) {
		return new Vector4f(this.x / val.x, this.y / val.y, this.z / val.w, this.z / val.w);
	}
	
	/**
	 *  Return the dot product
	 * @param obj The other vector in the dot product
	 * @return Dot product value
	 */
	@CheckReturnValue
	public float dot(final Vector4f obj) {
		return this.x * obj.x + this.y * obj.y + this.z * obj.z + this.w * obj.w;
	}
	
	/**
	 *  Return the axis with the smallest ABSOLUTE value
	 * @return values 0,1,2 for x, y, or z
	 */
	@CheckReturnValue
	public int furthestAxis() {
		return abs().getMinAxis();
	}
	
	/**
	 *  get the value with his index
	 * @param index Index of the value (0: x, 1: y, 2: z)
	 * @return The value associated
	 */
	@CheckReturnValue
	public float get(final int index) {
		if (index == 0) {
			return this.x;
		} else if (index == 1) {
			return this.y;
		} else if (index == 2) {
			return this.z;
		} else if (index == 3) {
			return this.w;
		}
		throw new IllegalArgumentException("Unknown index: " + index);
	}
	
	/**
	 *  Get the maximum value of the vector (x, y, z)
	 * @return The max value
	 */
	@CheckReturnValue
	public float getMax() {
		return Math.max(Math.max(Math.max(this.x, this.y), this.z), this.w);
	}
	
	/**
	 *  Get the Axis id with the maximum value
	 * @return Axis ID 0,1,2
	 */
	@CheckReturnValue
	public int getMaxAxis() {
		return (this.x < this.y ? (this.y < this.z ? (this.w < this.z ? 2 : 3) : (this.w < this.y ? 1 : 3)) : (this.x < this.z ? (this.w < this.z ? 2 : 3) : (this.w < this.x ? 0 : 3)));
	}
	
	/**
	 *  Get the minimum value of the vector (x, y, z)
	 * @return The min value
	 */
	@CheckReturnValue
	public float getMin() {
		return Math.min(Math.min(Math.min(this.x, this.y), this.z), this.w);
	}
	
	/**
	 *  Get the Axis id with the minimum value
	 * @return Axis ID 0,1,2
	 */
	@CheckReturnValue
	public int getMinAxis() {
		return (this.x < this.y ? (this.x < this.z ? (this.w < this.x ? 3 : 0) : (this.w < this.z ? 3 : 2)) : (this.y < this.z ? (this.w < this.y ? 3 : 1) : (this.w < this.z ? 3 : 2)));
	}
	
	// Overloaded operator for the negative of a vector
	@CheckReturnValue
	public Vector4f invert() {
		return new Vector4f(-this.x, -this.y, -this.z, -this.w);
	}
	
	/**
	 *  In-Equality compare operator with an other object.
	 * @param obj Reference on the comparing object
	 * @return true The Objects are NOT identical
	 * @return false The Objects are identical
	 */
	@CheckReturnValue
	public boolean isDifferent(final Vector4f obj) {
		return ((this.z != obj.z) || (this.y != obj.y) || (this.x != obj.x) || (this.w != obj.w));
	}
	
	/**
	 *  Equality compare operator with an other object.
	 * @param obj Reference on the comparing object
	 * @return true The Objects are identical
	 * @return false The Objects are NOT identical
	 */
	@CheckReturnValue
	public boolean isEqual(final Vector4f obj) {
		return ((this.z == obj.z) && (this.y == obj.y) && (this.x == obj.x) && (this.w == obj.w));
	}
	
	/**
	 *  Check if the vector is unitary (langth = 10f=)
	 * @return true if unit , false otherwise
	 */
	@CheckReturnValue
	public boolean isUnit() {
		return FMath.approxEqual(length2(), 1.0f, Constant.MACHINE_EPSILON);
	}
	
	/**
	 *  Check if the vector is equal to (0,0,0)
	 * @return true The value is equal to (0,0,0)
	 * @return false The value is NOT equal to (0,0,0)
	 */
	@CheckReturnValue
	public boolean isZero() {
		return FMath.approxEqual(length2(), 0.0f, Constant.MACHINE_EPSILON);
	}
	
	/**
	 *  Get the length of the vector
	 * @return Length value
	 */
	@CheckReturnValue
	public float length() {
		return (float) Math.sqrt(length2());
	}
	
	/**
	 *  Get the length between the 2 vectors
	 * @param start First vector
	 * @param stop  second vector
	 * @return Length value
	 */
	@CheckReturnValue
	public float length(final Vector4f start, final Vector4f stop) {
		return (float) Math.sqrt(length2(start, stop));
	}
	
	/**
	 *  Get the length of the vector squared
	 * @return Squared length value.
	 */
	@CheckReturnValue
	public float length2() {
		return dot(this);
	}
	
	/**
	 *  Return the linear interpolation between this and another vector
	 * @param obj   The other vector
	 * @param ratio The ratio of this to obj (ratio = 0 => return copy of this,
	 *              ratio=1 => return other)
	 * @return New vector containing the value
	 */
	@CheckReturnValue
	public Vector4f lerp(final Vector4f obj, final float ratio) {
		return new Vector4f(this.x + (obj.x - this.x) * ratio, this.y + (obj.y - this.y) * ratio, this.z + (obj.z - this.z) * ratio, this.w + (obj.w - this.w) * ratio);
	}
	
	@CheckReturnValue
	public Vector4f less(final float value) {
		return new Vector4f(this.x - value, this.y - value, this.z - value, this.w - value);
	}
	
	@CheckReturnValue
	public Vector4f less(final float xxx, final float yyy, final float zzz, final float www) {
		return new Vector4f(this.x - xxx, this.y - yyy, this.z - zzz, this.w - www);
	}
	
	/**
	 *  Subtract a vector from this one
	 * @param obj The vector to subtract
	 * @return A new vector with the data
	 */
	@CheckReturnValue
	public Vector4f less(final Vector4f obj) {
		return new Vector4f(this.x - obj.x, this.y - obj.y, this.z - obj.z, this.w - obj.w);
	}
	
	/**
	 *  Scale the vector
	 * @param val Scale factor
	 * @return A new vector with the data
	 */
	@CheckReturnValue
	public Vector4f multiply(final float val) {
		return new Vector4f(this.x * val, this.y * val, this.z * val, this.w * val);
	}
	
	/**
	 *  Elementwise multiply this vector by the other
	 * @param obj The other vector
	 */
	@CheckReturnValue
	public Vector4f multiply(final Vector4f obj) {
		return new Vector4f(this.x * obj.x, this.y * obj.y, this.z * obj.w, this.z * obj.w);
	}
	
	/**
	 *  Normalize this vector x^2 + y^2 + z^2 = 1
	 * @return the current vector
	 */
	@CheckReturnValue
	public Vector4f normalize() {
		return this.divide(this.length());
	}
	
	/**
	 *  Normalize this vector x^2 + y^2 + z^2 = 1 (check if not deviding by 0,
	 *        if it is the case ==> return (1,0,0))
	 * @return the current vector
	 */
	@CheckReturnValue
	public Vector4f safeNormalize() {
		final float length = length();
		if (length != 0.0f) {
			return this.divide(length);
		}
		return new Vector4f(1, 0, 0, 0);
	}
	
	/**
	 *  Interpolate the vector with a ration between 2 others
	 * @param obj0  First vector
	 * @param obj1  Second vector
	 * @param ratio Ratio between obj0 and obj1
	 */
	@CheckReturnValue
	public Vector4f setInterpolate3(final Vector4f obj0, final Vector4f obj1, final float ratio) {
		final float inverse = 1.0f - ratio;
		return new Vector4f(inverse * obj0.x + ratio * obj1.x, inverse * obj0.y + ratio * obj1.y, inverse * obj0.z + ratio * obj1.z, inverse * obj0.w + ratio * obj1.w);
	}
	
	/**
	 *  Set each element to the max of the current values and the values of
	 *        another Vector4f
	 * @param obj The other Vector4f to compare with
	 */
	@CheckReturnValue
	public Vector4f max(final Vector4f obj) {
		return new Vector4f(Math.max(this.x, obj.x), Math.max(this.y, obj.y), Math.max(this.z, obj.z), Math.max(this.w, obj.w));
	}
	
	@CheckReturnValue
	public static Vector4f max(final Vector4f obj1, final Vector4f obj2) {
		return new Vector4f(Math.max(obj1.x, obj2.x), Math.max(obj1.y, obj2.y), Math.max(obj1.z, obj2.z), Math.max(obj1.w, obj2.w));
	}
	
	@CheckReturnValue
	public static Vector4f min(final Vector4f obj1, final Vector4f obj2) {
		return new Vector4f(Math.min(obj1.x, obj2.x), Math.min(obj1.y, obj2.y), Math.min(obj1.z, obj2.z), Math.min(obj1.w, obj2.w));
	}
	
	/**
	 *  Set each element to the min of the current values and the values of
	 *        another Vector4f
	 * @param obj The other Vector4f to compare with
	 */
	@CheckReturnValue
	public Vector4f min(final Vector4f obj) {
		return new Vector4f(Math.min(this.x, obj.x), Math.min(this.y, obj.y), Math.min(this.z, obj.z), Math.min(this.w, obj.w));
	}
	
	@CheckReturnValue
	public Vector4f withX(final float xxx) {
		return new Vector4f(xxx, this.y, this.z, this.w);
	}
	
	@CheckReturnValue
	public Vector4f withY(final float yyy) {
		return new Vector4f(this.x, yyy, this.z, this.w);
	}
	
	@CheckReturnValue
	public Vector4f withZ(final float zzz) {
		return new Vector4f(this.x, this.y, zzz, this.w);
	}
	
	@CheckReturnValue
	public Vector4f withW(final float www) {
		return new Vector4f(this.x, this.y, this.z, www);
	}
	
	/**
	 *  Set 0 value on all the vector
	 */
	public static final Vector4f ZERO = new Vector4f(0, 0, 0, 0);
	public static final Vector4f ONE = new Vector4f(1, 1, 1, 1);
	public static final Vector4f ONE_W = new Vector4f(0, 0, 0, 1);
	public static final Vector4f VALUE_2 = new Vector4f(2, 2, 2, 2);
	public static final Vector4f VALUE_4 = new Vector4f(4, 4, 4, 4);
	public static final Vector4f VALUE_8 = new Vector4f(8, 8, 8, 8);
	public static final Vector4f VALUE_16 = new Vector4f(16, 16, 16, 16);
	public static final Vector4f VALUE_32 = new Vector4f(32, 32, 32, 32);
	public static final Vector4f VALUE_64 = new Vector4f(64, 64, 64, 64);
	public static final Vector4f VALUE_128 = new Vector4f(128, 128, 128, 128);
	public static final Vector4f VALUE_256 = new Vector4f(256, 256, 256, 256);
	public static final Vector4f VALUE_512 = new Vector4f(512, 512, 512, 512);
	public static final Vector4f VALUE_1024 = new Vector4f(1024, 1024, 1024, 1024);
	
	@Override
	public String toString() {
		return "Vector4f(" + FMath.floatToString(this.x) + "," + FMath.floatToString(this.y) + "," + FMath.floatToString(this.z) + ")";
	}
	
	/**
	 *  Return the triple product between this and another vector and another
	 * @param obj1 The other vector 1
	 * @param obj2 The other vector 2
	 * @return Value with the result of the triple product
	 */
	@CheckReturnValue
	public float triple(final Vector4f obj1, final Vector4f obj2) {
		return this.x * (obj1.y * obj2.z - obj1.z * obj2.y) + this.y * (obj1.z * obj2.x - obj1.x * obj2.z) + this.z * (obj1.x * obj2.y - obj1.y * obj2.x);
	}
	
	public static Vector4f valueOf(final String valuesX, final String valuesY, final String valuesZ, final String valuesW) {
		float val1 = Float.valueOf(valuesX);
		float val2 = Float.valueOf(valuesY);
		float val3 = Float.valueOf(valuesZ);
		float val4 = Float.valueOf(valuesW);
		return new Vector4f(val1, val2, val3, val4);
	}
}
