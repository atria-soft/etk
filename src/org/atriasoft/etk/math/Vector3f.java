package org.atriasoft.etk.math;

import org.atriasoft.etk.internal.Log;

import edu.umd.cs.findbugs.annotations.CheckReturnValue;

public record Vector3f(
		float x,
		float y,
		float z) {
	/**
	 *  Get the length square between the 2 vectors
	 * @param start First vector
	 * @param stop  second vector
	 * @return Length value
	 */
	public static float length2(final Vector3f start, final Vector3f stop) {
		final float x = stop.x - start.x;
		final float y = stop.y - start.y;
		final float z = stop.z - start.z;
		return x * x + y * y + z * z;
	}
	
	public static Vector3f valueOf(String value) {
		float val1 = 0;
		float val2 = 0;
		float val3 = 0;
		// copy to permit to modify it :
		while (value.length() > 0 && value.charAt(0) == '(') {
			value = value.substring(1);
		}
		while (value.length() > 0 && value.charAt(0) == ')') {
			value = value.substring(0, value.length() - 1);
		}
		final String[] values = value.split(",| ");
		if (values.length > 3) {
			Log.error("Can not parse Vector3f with more than 3 values: '" + value + "'");
		}
		if (values.length == 1) {
			// no coma ...
			// in every case, we parse the first element :
			val1 = Float.valueOf(values[0]);
			val2 = val1;
			val3 = val1;
		} else if (values.length == 2) {
			// no coma ...
			// in every case, we parse the first element :
			val1 = Float.valueOf(values[0]);
			val2 = Float.valueOf(values[1]);
			val3 = val2;
		} else {
			val1 = Float.valueOf(values[0]);
			val2 = Float.valueOf(values[1]);
			val3 = Float.valueOf(values[2]);
		}
		return new Vector3f(val1, val2, val3);
	}
	
	/**
	 *  Constructor from scalars
	 * @param value unique value for X,Y and Z value
	 */
	public Vector3f(final float value) {
		this(value, value, value);
	}
	
	public Vector3f(final float x, final float y, final float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 *  Return a vector will the absolute values of each element
	 * @return the curent reference
	 */
	@CheckReturnValue
	public Vector3f abs() {
		return new Vector3f(Math.abs(this.x), Math.abs(this.y), Math.abs(this.z));
	}
	
	@CheckReturnValue
	public Vector3f add(final float value) {
		return new Vector3f(this.x + value, this.y + value, this.z + value);
	}
	
	@CheckReturnValue
	public Vector3f add(final float xxx, final float yyy, final float zzz) {
		return new Vector3f(this.x + xxx, this.y + yyy, this.z + zzz);
	}
	
	@CheckReturnValue
	public Vector3f clipInteger() {
		return new Vector3f((int) this.x, (int) this.y, (int) this.z);
	}
	
	/**
	 *  Add a vector to this one
	 * @param obj The vector to add to this one
	 */
	@CheckReturnValue
	public Vector3f add(final Vector3f obj) {
		return new Vector3f(this.x + obj.x, this.y + obj.y, this.z + obj.z);
	}
	
	/**
	 *  Calculate the angle between this and another vector
	 * @param obj The other vector
	 * @return Angle in radian
	 */
	@CheckReturnValue
	public float angle(final Vector3f obj) {
		final float s = (float) Math.sqrt(length2() * obj.length2());
		if (0 != s) {
			return (float) Math.acos(dot(obj) / s);
		}
		return 0;
	}
	
	@CheckReturnValue
	public Vector3f clamp(final float maxLength) {
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
		return abs().maxAxis();
	}
	
	/**
	 *  Return the cross product between this and another vector
	 * @param obj The other vector
	 * @return Vector with the result of the cross product
	 */
	@CheckReturnValue
	public Vector3f cross(final Vector3f obj) {
		return new Vector3f(this.y * obj.z - this.z * obj.y, this.z * obj.x - this.x * obj.z, this.x * obj.y - this.y * obj.x);
	}
	
	/**
	 *  Return the distance between the ends of this and another vector This
	 *        is symantically treating the vector like a point
	 * @param obj The other vector to compare distance
	 * @return the distance of the 2 points
	 */
	@CheckReturnValue
	public float distance(final Vector3f obj) {
		return (float) Math.sqrt(distance2(obj));
	}
	
	/**
	 *  Return the distance squared between the ends of this and another
	 *        vector This is symantically treating the vector like a point
	 * @param obj The other vector to compare distance
	 * @return the square distance of the 2 points
	 */
	@CheckReturnValue
	public float distance2(final Vector3f obj) {
		final float deltaX = obj.x - this.x;
		final float deltaY = obj.y - this.y;
		final float deltaZ = obj.z - this.z;
		return deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
	}
	
	/**
	 *  Inversely scale the vector
	 * @param val Scale factor to divide by
	 */
	@CheckReturnValue
	public Vector3f divide(final float val) {
		if (val != 0.0f) {
			return new Vector3f(this.x / val, this.y / val, this.z / val);
		}
		throw new IllegalArgumentException("divice by 0 (vector3f)");
	}
	
	/**
	 *  Inversely scale the vector
	 * @param val Scale factor to divide by
	 */
	@CheckReturnValue
	public Vector3f divide(final Vector3f val) {
		return new Vector3f(this.x / val.x, this.y / val.y, this.z / val.z);
	}
	
	/**
	 *  Return the dot product
	 * @param obj The other vector in the dot product
	 * @return Dot product value
	 */
	@CheckReturnValue
	public float dot(final Vector3f obj) {
		return this.x * obj.x + this.y * obj.y + this.z * obj.z;
	}
	
	/**
	 *  Return the axis with the smallest ABSOLUTE value
	 * @return values 0,1,2 for x, y, or z
	 */
	@CheckReturnValue
	public int furthestAxis() {
		return abs().minAxis();
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
		}
		throw new IllegalArgumentException("Unknown index: " + index);
	}
	
	/**
	 *  Get the maximum value of the vector (x, y, z)
	 * @return The max value
	 */
	@CheckReturnValue
	public float getMax() {
		return Math.max(Math.max(this.x, this.y), this.z);
	}
	
	/**
	 *  Get the Axis id with the maximum value
	 * @return Axis ID 0,1,2
	 */
	@CheckReturnValue
	public int getMaxAxis() {
		return (this.x < this.y ? (this.y < this.z ? 2 : 1) : (this.x < this.z ? 2 : 0));
	}
	
	/**
	 *  Get the minimum value of the vector (x, y, z)
	 * @return The min value
	 */
	@CheckReturnValue
	public float getMin() {
		return Math.min(Math.min(this.x, this.y), this.z);
	}
	
	/**
	 *  Get the Axis id with the minimum value
	 * @return Axis ID 0,1,2
	 */
	@CheckReturnValue
	public int getMinAxis() {
		return (this.x < this.y ? (this.x < this.z ? 0 : 2) : (this.y < this.z ? 1 : 2));
	}
	
	/**
	 * @breif Get the orthogonal vector of the current vector
	 * @return The ortho vector
	 */
	@CheckReturnValue
	public Vector3f getOrthoVector() {
		final Vector3f vectorAbs = new Vector3f(Math.abs(this.x), Math.abs(this.y), Math.abs(this.z));
		final int minElement = vectorAbs.getMinAxis();
		if (minElement == 0) {
			final float devider = (float) Math.sqrt(this.y * this.y + this.z * this.z);
			return new Vector3f(0.0f, -this.z / devider, this.y / devider);
		} else if (minElement == 1) {
			final float devider = (float) Math.sqrt(this.x * this.x + this.z * this.z);
			return new Vector3f(-this.z / devider, 0.0f, this.x / devider);
		}
		final float devider = (float) Math.sqrt(this.x * this.x + this.y * this.y);
		return new Vector3f(-this.y / devider, this.x / devider, 0.0f);
	}
	
	/*
	 * public void getSkewSymmetricMatrix(final Vector3f obj0, final Vector3f obj1,
	 * final Vector3f obj2) { obj0.setValue(0, -this.z, this.y);
	 * obj1.setValue(this.z, 0, -this.x); obj2.setValue(-this.y, this.x, 0); }
	 */
	@CheckReturnValue
	public Vector3f getSkewSymmetricMatrix0() {
		return new Vector3f(0, -this.z, this.y);
	}
	
	@CheckReturnValue
	public Vector3f getSkewSymmetricMatrix1() {
		return new Vector3f(this.z, 0, -this.x);
	}
	
	@CheckReturnValue
	public Vector3f getSkewSymmetricMatrix2() {
		return new Vector3f(-this.y, this.x, 0);
	}
	
	// Overloaded operator for the negative of a vector
	@CheckReturnValue
	public Vector3f invert() {
		return new Vector3f(-this.x, -this.y, -this.z);
	}
	
	/**
	 *  In-Equality compare operator with an other object.
	 * @param obj Reference on the comparing object
	 * @return true The Objects are NOT identical
	 * @return false The Objects are identical
	 */
	@CheckReturnValue
	public boolean isDifferent(final Vector3f obj) {
		return ((this.z != obj.z) || (this.y != obj.y) || (this.x != obj.x));
	}
	
	/**
	 *  Equality compare operator with an other object.
	 * @param obj Reference on the comparing object
	 * @return true The Objects are identical
	 * @return false The Objects are NOT identical
	 */
	@CheckReturnValue
	public boolean isEqual(final Vector3f obj) {
		return ((this.z == obj.z) && (this.y == obj.y) && (this.x == obj.x));
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
	public float length(final Vector3f start, final Vector3f stop) {
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
	public Vector3f lerp(final Vector3f obj, final float ratio) {
		return new Vector3f(this.x + (obj.x - this.x) * ratio, this.y + (obj.y - this.y) * ratio, this.z + (obj.z - this.z) * ratio);
	}
	
	@CheckReturnValue
	public Vector3f less(final float value) {
		return new Vector3f(this.x - value, this.y - value, this.z - value);
	}
	
	@CheckReturnValue
	public Vector3f less(final float xxx, final float yyy, final float zzz) {
		return new Vector3f(this.x - xxx, this.y - yyy, this.z - zzz);
	}
	
	/**
	 *  Subtract a vector from this one
	 * @param obj The vector to subtract
	 * @return A new vector with the data
	 */
	@CheckReturnValue
	public Vector3f less(final Vector3f obj) {
		return new Vector3f(this.x - obj.x, this.y - obj.y, this.z - obj.z);
	}
	
	/**
	 *  Return the axis with the largest value
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
	 *  Return the axis with the smallest value
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
	 *  Scale the vector
	 * @param val Scale factor
	 * @return A new vector with the data
	 */
	@CheckReturnValue
	public Vector3f multiply(final float val) {
		return new Vector3f(this.x * val, this.y * val, this.z * val);
	}
	
	/**
	 *  Elementwise multiply this vector by the other
	 * @param obj The other vector
	 */
	@CheckReturnValue
	public Vector3f multiply(final Vector3f obj) {
		return new Vector3f(this.x * obj.x, this.y * obj.y, this.z * obj.z);
	}
	
	/**
	 *  Normalize this vector x^2 + y^2 + z^2 = 1
	 * @return the current vector
	 */
	@CheckReturnValue
	public Vector3f normalize() {
		return this.divide(this.length());
	}
	
	/**
	 *  Return a rotated version of this vector
	 * @param wAxis The axis to rotate about
	 * @param angle The angle to rotate by
	 * @return New vector containing the value
	 */
	@CheckReturnValue
	public Vector3f rotateNew(final Vector3f wAxis, final float angle) {
		Vector3f out = wAxis.multiply(wAxis.dot(this));
		Vector3f x = this.less(out);
		Vector3f y = wAxis.cross(this);
		x = x.multiply((float) Math.cos(angle));
		y = y.multiply((float) Math.sin(angle));
		out = out.add(x);
		out = out.add(y);
		return out;
	}
	
	/**
	 *  Normalize this vector x^2 + y^2 + z^2 = 1 (check if not deviding by 0,
	 *        if it is the case ==> return (1,0,0))
	 * @return the current vector
	 */
	@CheckReturnValue
	public Vector3f safeNormalize() {
		final float length = length();
		if (length != 0.0f) {
			return this.divide(length);
		}
		return new Vector3f(1, 0, 0);
	}
	
	/**
	 *  Interpolate the vector with a ration between 2 others
	 * @param obj0  First vector
	 * @param obj1  Second vector
	 * @param ratio Ratio between obj0 and obj1
	 */
	@CheckReturnValue
	public Vector3f setInterpolate3(final Vector3f obj0, final Vector3f obj1, final float ratio) {
		final float inverse = 1.0f - ratio;
		return new Vector3f(inverse * obj0.x + ratio * obj1.x, inverse * obj0.y + ratio * obj1.y, inverse * obj0.z + ratio * obj1.z);
		// this.co[3] = s * v0[3] + rt * v1[3];
	}
	
	/**
	 *  Set each element to the max of the current values and the values of
	 *        another Vector3f
	 * @param obj The other Vector3f to compare with
	 */
	@CheckReturnValue
	public Vector3f max(final Vector3f obj) {
		return new Vector3f(Math.max(this.x, obj.x), Math.max(this.y, obj.y), Math.max(this.z, obj.z));
	}
	
	@CheckReturnValue
	public static Vector3f max(final Vector3f obj1, final Vector3f obj2) {
		return new Vector3f(Math.max(obj1.x, obj2.x), Math.max(obj1.y, obj2.y), Math.max(obj1.z, obj2.z));
	}
	
	@CheckReturnValue
	public static Vector3f min(final Vector3f obj1, final Vector3f obj2) {
		return new Vector3f(Math.min(obj1.x, obj2.x), Math.min(obj1.y, obj2.y), Math.min(obj1.z, obj2.z));
	}
	
	/**
	 *  Set each element to the min of the current values and the values of
	 *        another Vector3f
	 * @param obj The other Vector3f to compare with
	 */
	@CheckReturnValue
	public Vector3f min(final Vector3f obj) {
		return new Vector3f(Math.min(this.x, obj.x), Math.min(this.y, obj.y), Math.min(this.z, obj.z));
	}
	
	@CheckReturnValue
	public Vector3f withX(final float xxx) {
		return new Vector3f(xxx, this.y, this.z);
	}
	
	@CheckReturnValue
	public Vector3f withY(final float yyy) {
		return new Vector3f(this.x, yyy, this.z);
	}
	
	@CheckReturnValue
	public Vector3f withZ(final float zzz) {
		return new Vector3f(this.x, this.y, zzz);
	}
	
	/**
	 *  Set 0 value on all the vector
	 */
	public static final Vector3f ZERO = new Vector3f(0, 0, 0);
	public static final Vector3f ONE = new Vector3f(1, 1, 1);
	public static final Vector3f MAX = new Vector3f(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE);
	public static final Vector3f MIN = new Vector3f(-Float.MAX_VALUE, -Float.MAX_VALUE, -Float.MAX_VALUE);
	public static final Vector3f VALUE_2 = new Vector3f(2, 2, 2);
	public static final Vector3f VALUE_4 = new Vector3f(4, 4, 4);
	public static final Vector3f VALUE_8 = new Vector3f(8, 8, 8);
	public static final Vector3f VALUE_16 = new Vector3f(16, 16, 16);
	public static final Vector3f VALUE_32 = new Vector3f(32, 32, 32);
	public static final Vector3f VALUE_64 = new Vector3f(64, 64, 64);
	public static final Vector3f VALUE_128 = new Vector3f(128, 128, 128);
	public static final Vector3f VALUE_256 = new Vector3f(256, 256, 256);
	public static final Vector3f VALUE_512 = new Vector3f(512, 512, 512);
	public static final Vector3f VALUE_1024 = new Vector3f(1024, 1024, 1024);
	
	@Override
	public String toString() {
		return "Vector3f(" + FMath.floatToString(this.x) + "," + FMath.floatToString(this.y) + "," + FMath.floatToString(this.z) + ")";
	}
	
	/**
	 *  Return the triple product between this and another vector and another
	 * @param obj1 The other vector 1
	 * @param obj2 The other vector 2
	 * @return Value with the result of the triple product
	 */
	@CheckReturnValue
	public float triple(final Vector3f obj1, final Vector3f obj2) {
		return this.x * (obj1.y * obj2.z - obj1.z * obj2.y) + this.y * (obj1.z * obj2.x - obj1.x * obj2.z) + this.z * (obj1.x * obj2.y - obj1.y * obj2.x);
	}
	
	public static Vector3f valueOf(final String valuesX, final String valuesY, final String valuesZ) {
		float val1 = Float.valueOf(valuesX);
		float val2 = Float.valueOf(valuesY);
		float val3 = Float.valueOf(valuesZ);
		return new Vector3f(val1, val2, val3);
	}
}
