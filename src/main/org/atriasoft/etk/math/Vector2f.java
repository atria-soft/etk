package org.atriasoft.etk.math;

import org.atriasoft.etk.Uri;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umd.cs.findbugs.annotations.CheckReturnValue;

public record Vector2f(
		float x,
		float y) {
	final static Logger LOGGER = LoggerFactory.getLogger(Vector2f.class);
	public static Vector2f valueOf(String value) {
		float val1 = 0;
		float val2 = 0;
		// copy to permit to modify it :
		while (value.length() > 0 && value.charAt(0) == '(') {
			value = value.substring(1);
		}
		while (value.length() > 0 && value.charAt(0) == ')') {
			value = value.substring(0, value.length() - 1);
		}
		final String[] values = value.split(",| ");
		if (values.length > 2) {
			LOGGER.error("Can not parse Vector2f with more than 2 values: '" + value + "'");
		}
		if (values.length == 1) {
			// no coma ...
			// in every case, we parse the first element :
			val1 = Float.parseFloat(values[0]);
			val2 = val1;
		} else {
			val1 = Float.parseFloat(values[0]);
			val2 = Float.parseFloat(values[1]);
		}
		return new Vector2f(val1, val2);
	}
	
	/*
	 * **************************************************** Constructor
	 *****************************************************/
	
	@CheckReturnValue
	public static Vector2f clipInt(final Vector2f obj1) {
		return new Vector2f((int) obj1.x, (int) obj1.y);
	}
	
	@CheckReturnValue
	public static Vector2f max(final Vector2f obj1, final Vector2f obj2) {
		return new Vector2f(Math.max(obj1.x, obj2.x), Math.max(obj1.y, obj2.y));
	}
	
	@CheckReturnValue
	public static Vector2f max(final Vector2f obj1, final Vector2f obj2, final Vector2f obj3) {
		return new Vector2f(FMath.max(obj1.x, obj2.x, obj3.x), FMath.max(obj1.y, obj2.y, obj3.y));
	}
	
	@CheckReturnValue
	public static Vector2f min(final Vector2f obj1, final Vector2f obj2) {
		return new Vector2f(Math.min(obj1.x, obj2.x), Math.min(obj1.y, obj2.y));
	}
	
	@CheckReturnValue
	public static Vector2f min(final Vector2f obj1, final Vector2f obj2, final Vector2f obj3) {
		return new Vector2f(FMath.min(obj1.x, obj2.x, obj3.x), FMath.min(obj1.y, obj2.y, obj3.y));
	}
	
	/**
	 * Return a vector will the absolute values of each element
	 * @return New vector containing the value
	 */
	@CheckReturnValue
	public Vector2f abs() {
		return new Vector2f(Math.abs(this.x), Math.abs(this.y));
	}
	
	@CheckReturnValue
	public Vector2f add(final float val) {
		return new Vector2f(this.x + val, this.y + val);
	}
	
	@CheckReturnValue
	public Vector2f addX(final float val) {
		return new Vector2f(this.x + val, this.y);
	}
	
	@CheckReturnValue
	public Vector2f addY(final float val) {
		return new Vector2f(this.x, this.y + val);
	}
	
	@CheckReturnValue
	public Vector2f add(final Vector2f obj) {
		return new Vector2f(this.x + obj.x, this.y + obj.y);
	}
	
	@CheckReturnValue
	public Vector2f add(final Vector2i obj) {
		return new Vector2f(this.x + obj.x(), this.y + obj.y());
	}
	
	@CheckReturnValue
	public Vector2f add(final float xxx, final float yyy) {
		return new Vector2f(this.x + xxx, this.y + yyy);
	}
	
	/**
	 * Return the axis with the largest ABSOLUTE value
	 * @return values 0,1 for x or y
	 */
	@CheckReturnValue
	public int closestAxis() {
		return abs().maxAxis();
	}
	
	/**
	 * Return the cross product / determinant
	 * @param obj The other vector in the cross product
	 * @return cross product value
	 */
	@CheckReturnValue
	public float cross(final Vector2f obj) {
		return this.x * obj.y - this.y * obj.x;
	}
	
	/**
	 * Decrementation of this vector (-1 of 2 elements)
	 */
	@CheckReturnValue
	public Vector2f decrement() {
		return new Vector2f(this.x - 1, this.y - 1);
	}
	
	@CheckReturnValue
	public Vector2f devide(final float val) {
		return new Vector2f(this.x / val, this.y / val);
	}
	
	@CheckReturnValue
	public Vector2f devide(final Vector2f obj) {
		return new Vector2f(this.x / obj.x, this.y / obj.y);
	}
	
	/**
	 * Return the distance between the ends of this and another vector This
	 *        is semantically treating the vector like a point
	 * @param obj The other vector to compare distance
	 * @return the distance of the 2 points
	 */
	@CheckReturnValue
	public float distance(final Vector2f obj) {
		return (float) Math.sqrt(distance2(obj));
	}
	
	/**
	 * Return the distance squared between the ends of this and another
	 *        vector This is semantically treating the vector like a point
	 * @param obj The other vector to compare distance
	 * @return the square distance of the 2 points
	 */
	@CheckReturnValue
	public float distance2(final Vector2f obj) {
		final float deltaX = obj.x - this.x;
		final float deltaY = obj.y - this.y;
		return deltaX * deltaX + deltaY * deltaY;
	}
	
	/**
	 * Return the dot product
	 * @param obj The other vector in the dot product
	 * @return Dot product value
	 */
	@CheckReturnValue
	public float dot(final Vector2f obj) {
		return this.x * obj.x + this.y * obj.y;
	}
	
	/**
	 * Return the axis with the smallest ABSOLUTE value
	 * @return values 0,1 for x, or z
	 */
	@CheckReturnValue
	public int furthestAxis() {
		return abs().minAxis();
	}
	
	/**
	 * get the value with his index
	 * @param index Index of the value (0: x, 1: y)
	 * @return The value associated
	 */
	@CheckReturnValue
	public float get(final int index) {
		if (index == 0) {
			return this.x;
		}
		if (index == 1) {
			return this.y;
		}
		throw new IllegalArgumentException("Unknown index: " + index);
	}
	
	/**
	 * Incrementation of this vector (+1 of 2 elements)
	 */
	@CheckReturnValue
	public Vector2f increment() {
		return new Vector2f(this.x + 1, this.y + 1);
	}
	
	// Overloaded operator for the negative of a vector
	@CheckReturnValue
	public Vector2f invert() {
		return new Vector2f(-this.x, -this.y);
	}
	
	/**
	 * In-Equality compare operator with an other object.
	 * @param obj Reference on the comparing object
	 * @return true The Objects are NOT identical
	 * @return false The Objects are identical
	 */
	@CheckReturnValue
	public boolean isDifferent(final Vector2f obj) {
		return (obj.x != this.x || obj.y != this.y);
	}
	
	/**
	 * Equality compare operator with an other object.
	 * @param obj Reference on the comparing object
	 * @return true The Objects are identical
	 * @return false The Objects are NOT identical
	 */
	@CheckReturnValue
	public boolean isEqual(final Vector2f obj) {
		return (obj.x == this.x && obj.y == this.y);
	}
	
	@CheckReturnValue
	public boolean isGreater(final Vector2f obj) {
		return (this.x > obj.x && this.y > obj.y);
	}
	
	@CheckReturnValue
	public boolean isGreaterOrEqual(final Vector2f obj) {
		return (this.x >= obj.x && this.y >= obj.y);
	}
	
	@CheckReturnValue
	public boolean isLower(final Vector2f obj) {
		return (this.x < obj.x && this.y < obj.y);
	}
	
	@CheckReturnValue
	public boolean isLowerOrEqual(final Vector2f obj) {
		return (this.x <= obj.x && this.y <= obj.y);
	}
	
	/**
	 * Check if the vector is unitary (langth = 10f=)
	 * @return true if unit , false otherwise
	 */
	@CheckReturnValue
	public boolean isUnit() {
		return FMath.approxEqual(length2(), 1.0f, Constant.MACHINE_EPSILON);
	}
	
	/**
	 * Check if the vector is equal to (0,0)
	 * @return true The value is equal to (0,0)
	 * @return false The value is NOT equal to (0,0)
	 */
	@CheckReturnValue
	public boolean isZero() {
		return FMath.approxEqual(length2(), 0.0f, Constant.MACHINE_EPSILON);
	}
	
	/**
	 * Get the length of the vector
	 * @return Length value
	 */
	@CheckReturnValue
	public float length() {
		return (float) Math.sqrt(length2());
	}
	
	/**
	 * Get the length of the vector squared
	 * @return Squared length value.
	 */
	@CheckReturnValue
	public float length2() {
		return dot(this);
	}
	
	@CheckReturnValue
	public Vector2f less(final float val) {
		return new Vector2f(this.x - val, this.y - val);
	}
	
	@CheckReturnValue
	public Vector2f lessX(final float val) {
		return new Vector2f(this.x - val, this.y);
	}
	
	@CheckReturnValue
	public Vector2f lessY(final float val) {
		return new Vector2f(this.x, this.y - val);
	}
	
	@CheckReturnValue
	public Vector2f less(final float xxx, final float yyy) {
		return new Vector2f(this.x - xxx, this.y - yyy);
	}
	
	@CheckReturnValue
	public Vector2f less(final Vector2f obj) {
		return new Vector2f(this.x - obj.x, this.y - obj.y);
	}
	
	@CheckReturnValue
	public Vector2f less(final Vector2i obj) {
		return new Vector2f(this.x - obj.x(), this.y - obj.y());
	}
	
	/**
	 * Return the axis with the largest value
	 * @return values are 0,1 for x or y
	 */
	@CheckReturnValue
	public int maxAxis() {
		return this.x < this.y ? 1 : 0;
	}
	
	/**
	 * Return the axis with the smallest value
	 * @return values are 0,1 for x or y
	 */
	@CheckReturnValue
	public int minAxis() {
		return this.x < this.y ? 0 : 1;
	}
	
	@CheckReturnValue
	public Vector2f multiply(final float val) {
		return new Vector2f(this.x * val, this.y * val);
	}
	
	@CheckReturnValue
	public Vector2f multiply(final Vector2f obj) {
		return new Vector2f(this.x * obj.x, this.y * obj.y);
	}
	
	/**
	 * Normalize this vector x^2 + y^2 = 1
	 */
	@CheckReturnValue
	public Vector2f normalize() {
		return this.devide(length());
	}
	
	/**
	 * Normalize this vector x^2 + y^2 = 1 (check if not deviding by 0, if it
	 *        is the case ==> return (1,0))
	 * @return Local reference of the vector normalized
	 */
	@CheckReturnValue
	public Vector2f safeNormalize() {
		final float tmp = length();
		if (tmp != 0) {
			return this.devide(length());
		}
		return new Vector2f(1, 0);
	}
	
	/**
	 * Set each element to the max of the current values and the values of
	 *        another vector
	 * @param other The other vector to compare with
	 */
	@CheckReturnValue
	public Vector2f max(final Vector2f other) {
		return new Vector2f(Math.max(this.x, other.x), Math.max(this.y, other.y));
	}
	
	/**
	 * Set each element to the min of the current values and the values of
	 *        another vector
	 * @param other The other vector to compare with
	 */
	@CheckReturnValue
	public Vector2f min(final Vector2f other) {
		return new Vector2f(Math.min(this.x, other.x), Math.min(this.y, other.y));
	}
	
	// Return one unit orthogonal vector of the current vector
	@CheckReturnValue
	public Vector2f unitOrthogonal() {
		return (new Vector2f(this.x, -this.y)).safeNormalize();
	}
	
	/**
	 * Set 0 value on all the vector
	 */
	public static final Vector2f MAX_VALUE = new Vector2f(Float.MAX_VALUE, Float.MAX_VALUE);
	public static final Vector2f MIN_VALUE = new Vector2f(-Float.MAX_VALUE, -Float.MAX_VALUE);
	public static final Vector2f ZERO = new Vector2f(0, 0);
	public static final Vector2f ONE = new Vector2f(1, 1);
	public static final Vector2f VALUE_2 = new Vector2f(2, 2);
	public static final Vector2f VALUE_4 = new Vector2f(4, 4);
	public static final Vector2f VALUE_8 = new Vector2f(8, 8);
	public static final Vector2f VALUE_16 = new Vector2f(16, 16);
	public static final Vector2f VALUE_32 = new Vector2f(32, 32);
	public static final Vector2f VALUE_64 = new Vector2f(64, 64);
	public static final Vector2f VALUE_128 = new Vector2f(128, 128);
	public static final Vector2f VALUE_256 = new Vector2f(256, 256);
	public static final Vector2f VALUE_512 = new Vector2f(512, 512);
	public static final Vector2f VALUE_1024 = new Vector2f(1024, 1024);
	
	@Override
	public String toString() {
		return "(" + this.x + "," + this.y + ")";
	}
	
	@CheckReturnValue
	public Vector2f withX(final float xxx) {
		return new Vector2f(xxx, this.y);
	}
	
	@CheckReturnValue
	public Vector2f withY(final float yyy) {
		return new Vector2f(this.x, yyy);
	}
}
