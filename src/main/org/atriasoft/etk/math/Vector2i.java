package org.atriasoft.etk.math;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umd.cs.findbugs.annotations.CheckReturnValue;

public record Vector2i(
		int x,
		int y) {
	final static Logger LOGGER = LoggerFactory.getLogger(Vector2i.class);
	
	public static Vector2i valueOf(String value) throws NumberFormatException {
		int val1 = 0;
		int val2 = 0;
		// copy to permit to modify it :
		while (value.length() > 0 && value.charAt(0) == '(') {
			value = value.substring(1);
		}
		while (value.length() > 0 && value.charAt(0) == ')') {
			value = value.substring(0, value.length() - 1);
		}
		final String[] values = value.split(",| ");
		if (values.length > 2) {
			LOGGER.warn("Can not parse Vector2i with more than 2 values: '{}'", value);
		}
		if (values.length == 1) {
			// no coma ...
			// in every case, we parse the first element :
			val1 = Integer.valueOf(values[0]);
			val2 = val1;
		} else {
			val1 = Integer.valueOf(values[0]);
			val2 = Integer.valueOf(values[1]);
		}
		return new Vector2i(val1, val2);
	}
	
	/*
	 * **************************************************** Constructor
	 *****************************************************/
	public Vector2i() {
		this(0, 0);
	}
	
	public Vector2i(final int x, final int y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2f toVector2f() {
		return new Vector2f(this.x, this.y);
	}
	
	public static Vector2i max(final Vector2i obj1, final Vector2i obj2) {
		return new Vector2i(Math.max(obj1.x, obj2.x), Math.max(obj1.y, obj2.y));
	}
	
	public static Vector2i min(final Vector2i obj1, final Vector2i obj2) {
		return new Vector2i(Math.min(obj1.x, obj2.x), Math.min(obj1.y, obj2.y));
	}
	
	/**
	 * Return a vector will the absolute values of each element
	 * @return New vector containing the value
	 */
	@CheckReturnValue
	public Vector2i absolute() {
		return new Vector2i(Math.abs(this.x), Math.abs(this.y));
	}
	
	@CheckReturnValue
	public Vector2i add(final int val) {
		return new Vector2i(this.x + val, this.y + val);
	}
	
	@CheckReturnValue
	public Vector2i add(final Vector2i obj) {
		return new Vector2i(this.x + obj.x, this.y + obj.y);
	}
	
	@CheckReturnValue
	public Vector2i add(final int xxx, final int yyy) {
		return new Vector2i(this.x + xxx, this.y + yyy);
	}
	
	/**
	 * Return the axis with the largest ABSOLUTE value
	 * @return values 0,1 for x or y
	 */
	@CheckReturnValue
	public int closestAxis() {
		return absolute().maxAxis();
	}
	
	/**
	 * Return the cross product / determinant
	 * @param obj The other vector in the cross product
	 * @return cross product value
	 */
	@CheckReturnValue
	public int cross(final Vector2i obj) {
		return this.x * obj.y - this.y * obj.x;
	}
	
	/**
	 * Decrementation of this vector (-1 of 2 elements)
	 */
	@CheckReturnValue
	public Vector2i decrement() {
		return new Vector2i(this.x - 1, this.y - 1);
	}
	
	@CheckReturnValue
	public Vector2i devide(final int val) {
		return new Vector2i(this.x / val, this.y / val);
	}
	
	@CheckReturnValue
	public Vector2i devide(final Vector2i obj) {
		return new Vector2i(this.x / obj.x, this.y / obj.y);
	}
	
	/**
	 * Return the distance between the ends of this and another vector This
	 *        is symantically treating the vector like a point
	 * @param obj The other vector to compare distance
	 * @return the distance of the 2 points
	 */
	@CheckReturnValue
	public int distance(final Vector2i obj) {
		return (int) Math.sqrt(distance2(obj));
	}
	
	/**
	 * Return the distance squared between the ends of this and another
	 *        vector This is symantically treating the vector like a point
	 * @param obj The other vector to compare distance
	 * @return the square distance of the 2 points
	 */
	@CheckReturnValue
	public int distance2(final Vector2i obj) {
		final int deltaX = obj.x - this.x;
		final int deltaY = obj.y - this.y;
		return deltaX * deltaX + deltaY * deltaY;
	}
	
	/**
	 * Return the dot product
	 * @param obj The other vector in the dot product
	 * @return Dot product value
	 */
	@CheckReturnValue
	public int dot(final Vector2i obj) {
		return this.x * obj.x + this.y * obj.y;
	}
	
	/**
	 * Return the axis with the smallest ABSOLUTE value
	 * @return values 0,1 for x, or z
	 */
	@CheckReturnValue
	public int furthestAxis() {
		return absolute().minAxis();
	}
	
	/**
	 * Incrementation of this vector (+1 of 2 elements)
	 */
	@CheckReturnValue
	public Vector2i increment() {
		return new Vector2i(this.x + 1, this.y + 1);
	}
	
	/**
	 * In-Equality compare operator with an other object.
	 * @param obj Reference on the comparing object
	 * @return true The Objects are NOT identical
	 * @return false The Objects are identical
	 */
	@CheckReturnValue
	public boolean isDifferent(final Vector2i obj) {
		return (obj.x != this.x || obj.y != this.y);
	}
	
	/**
	 * Equality compare operator with an other object.
	 * @param obj Reference on the comparing object
	 * @return true The Objects are identical
	 * @return false The Objects are NOT identical
	 */
	@CheckReturnValue
	public boolean isEqual(final Vector2i obj) {
		return (obj.x == this.x && obj.y == this.y);
	}
	
	@CheckReturnValue
	public boolean isGreater(final Vector2i obj) {
		return (this.x > obj.x && this.y > obj.y);
	}
	
	@CheckReturnValue
	public boolean isGreaterOrEqual(final Vector2i obj) {
		return (this.x >= obj.x && this.y >= obj.y);
	}
	
	@CheckReturnValue
	public boolean isLower(final Vector2i obj) {
		return (this.x < obj.x && this.y < obj.y);
	}
	
	@CheckReturnValue
	public boolean isLowerOrEqual(final Vector2i obj) {
		return (this.x <= obj.x && this.y <= obj.y);
	}
	
	/**
	 * Check if the vector is equal to (0,0)
	 * @return true The value is equal to (0,0)
	 * @return false The value is NOT equal to (0,0)
	 */
	@CheckReturnValue
	public boolean isZero() {
		return this.x == 0 && this.y == 0;
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
	
	@CheckReturnValue
	public Vector2i less(final int val) {
		return new Vector2i(this.x - val, this.y - val);
	}
	
	@CheckReturnValue
	public Vector2i less(final int xxx, final int yyy) {
		return new Vector2i(this.x - xxx, this.y - yyy);
	}
	
	@CheckReturnValue
	public Vector2i less(final Vector2i obj) {
		return new Vector2i(this.x - obj.x, this.y - obj.y);
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
	public Vector2i multiply(final int val) {
		return new Vector2i(this.x * val, this.y * val);
	}
	
	@CheckReturnValue
	public Vector2i multiply(final Vector2i obj) {
		return new Vector2i(this.x * obj.x, this.y * obj.y);
	}
	
	/**
	 * Normalize this vector x^2 + y^2 = 1
	 * @return New vector containing the value
	 */
	@CheckReturnValue
	public Vector2i normalize() {
		return this.devide(length());
	}
	
	/**
	 * Normalize this vector x^2 + y^2 = 1 (check if not deviding by 0, if it
	 *        is the case ==> return (1,0))
	 * @return Local reference of the vector normalized
	 */
	@CheckReturnValue
	public Vector2i safeNormalize() {
		final int tmp = length();
		if (tmp != 0) {
			return this.devide(length());
		}
		return new Vector2i(1, 0);
	};
	
	@CheckReturnValue
	public Vector2i max(final int xxx, final int yyy) {
		return new Vector2i(Math.max(this.x, xxx), Math.max(this.y, yyy));
	}
	
	@CheckReturnValue
	public Vector2i min(final int xxx, final int yyy) {
		return new Vector2i(Math.min(this.x, xxx), Math.min(this.y, yyy));
	}
	
	/**
	 * Set 0 value on all the vector
	 */
	public static final Vector2i ZERO = new Vector2i(0, 0);
	public static final Vector2i ONE = new Vector2i(1, 1);
	public static final Vector2i VALUE_2 = new Vector2i(2, 2);
	public static final Vector2i VALUE_4 = new Vector2i(4, 4);
	public static final Vector2i VALUE_8 = new Vector2i(8, 8);
	public static final Vector2i VALUE_16 = new Vector2i(16, 16);
	public static final Vector2i VALUE_32 = new Vector2i(32, 32);
	public static final Vector2i VALUE_64 = new Vector2i(64, 64);
	public static final Vector2i VALUE_128 = new Vector2i(128, 128);
	public static final Vector2i VALUE_256 = new Vector2i(256, 256);
	public static final Vector2i VALUE_512 = new Vector2i(512, 512);
	public static final Vector2i VALUE_1024 = new Vector2i(1024, 1024);
	
	@Override
	public String toString() {
		return "Vector2i(" + this.x + "," + this.y + ")";
	}
	
	@CheckReturnValue
	public Vector2i withX(final int xxx) {
		return new Vector2i(xxx, this.y);
	}
	
	@CheckReturnValue
	public Vector2i withY(final int yyy) {
		return new Vector2i(this.x, yyy);
	}
}
