
package org.atriasoft.etk;

import org.atriasoft.etk.math.FMath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umd.cs.findbugs.annotations.CheckReturnValue;

/**
 * Immutable border radius representation for rectangular elements.
 *
 * <p>Defines the radius of each corner of a rectangle, allowing for rounded corners with different
 * radii. Each corner can have an independent radius value. Provides mathematical operations for
 * manipulation and comparison.</p>
 *
 * @param topLeft Radius of the top-left corner in pixels
 * @param topRight Radius of the top-right corner in pixels
 * @param bottomRight Radius of the bottom-right corner in pixels
 * @param bottomLeft Radius of the bottom-left corner in pixels
 *
 * @author Edouard DUPIN
 * @since 0.1.0
 */
public record BorderRadius(
		float topLeft,
		float topRight,
		float bottomRight,
		float bottomLeft) {
	final static Logger LOGGER = LoggerFactory.getLogger(BorderRadius.class);

	/**
	 * Parses a BorderRadius from a string representation.
	 *
	 * <p>Supports formats:</p>
	 * <ul>
	 *   <li>Single value: "5" - all corners get same radius</li>
	 *   <li>Two values: "5 10" - topLeft/bottomRight=5, topRight/bottomLeft=10</li>
	 *   <li>Three values: "5 10 15" - topLeft=5, topRight/bottomLeft=10, bottomRight=15</li>
	 *   <li>Four values: "5 10 15 20" - topLeft=5, topRight=10, bottomRight=15, bottomLeft=20</li>
	 * </ul>
	 *
	 * @param value String representation of border radius
	 * @return Parsed BorderRadius object
	 */
	public static BorderRadius valueOf(String value) {
		float val1 = 0;
		float val2 = 0;
		float val3 = 0;
		float val4 = 0;
		// copy to permit to modify it :
		while (value.length() > 0 && value.charAt(0) == '(') {
			value = value.substring(1);
		}
		while (value.length() > 0 && value.charAt(value.length() - 1) == ')') {
			value = value.substring(0, value.length() - 1);
		}
		final String[] values = value.split(",| ");
		if (values.length > 3) {
			LOGGER.warn("Can not parse Constraint4f with more than 3 values: '{}'", value);
		}
		if (values.length == 1) {
			// no coma ...
			// in every case, we parse the first element :
			val1 = Float.parseFloat(values[0]);
			val2 = val1;
			val3 = val1;
			val4 = val1;
		} else if (values.length == 2) {
			// no coma ...
			// in every case, we parse the first element :
			val1 = Float.parseFloat(values[0]);
			val2 = Float.parseFloat(values[1]);
			val3 = val2;
			val4 = val2;
		} else if (values.length == 3) {
			val1 = Float.parseFloat(values[0]);
			val2 = Float.parseFloat(values[1]);
			val3 = Float.parseFloat(values[2]);
			val4 = val3;
		} else {
			val1 = Float.parseFloat(values[0]);
			val2 = Float.parseFloat(values[1]);
			val3 = Float.parseFloat(values[2]);
			val4 = Float.parseFloat(values[3]);
		}
		return new BorderRadius(val1, val2, val3, val4);
	}

	/**
	 *  Constructor from scalars
	 * @param value unique value for X,Y and Z value
	 */
	public BorderRadius(final float value) {
		this(value, value, value, value);
	}

	public BorderRadius(final float topLeft, final float topRight, final float bottomRight, final float bottomLeft) {
		this.topLeft = topLeft;
		this.topRight = topRight;
		this.bottomRight = bottomRight;
		this.bottomLeft = bottomLeft;
	}

	/**
	 *  Return a vector will the absolute values of each element
	 * @return the curent reference
	 */
	@CheckReturnValue
	public BorderRadius abs() {
		return new BorderRadius(Math.abs(this.topLeft), Math.abs(this.topRight), Math.abs(this.bottomRight),
				Math.abs(this.bottomLeft));
	}

	@CheckReturnValue
	public BorderRadius add(final float value) {
		return new BorderRadius(this.topLeft + value, this.topRight + value, this.bottomRight + value,
				this.bottomLeft + value);
	}

	@CheckReturnValue
	public BorderRadius add(final float xxx, final float yyy, final float zzz, final float www) {
		return new BorderRadius(this.topLeft + xxx, this.topRight + yyy, this.bottomRight + zzz, this.bottomLeft + www);
	}

	@CheckReturnValue
	public BorderRadius clipInteger() {
		return new BorderRadius((int) this.topLeft, (int) this.topRight, (int) this.bottomRight, (int) this.bottomLeft);
	}

	/**
	 *  Add a vector to this one
	 * @param obj The vector to add to this one
	 */
	@CheckReturnValue
	public BorderRadius add(final BorderRadius obj) {
		return new BorderRadius(this.topLeft + obj.topLeft, this.topRight + obj.topRight,
				this.bottomRight + obj.bottomRight, this.bottomLeft + obj.bottomLeft);
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
	public float distance(final BorderRadius obj) {
		return (float) Math.sqrt(distance2(obj));
	}

	/**
	 *  Return the distance squared between the ends of this and another
	 *        vector This is symantically treating the vector like a point
	 * @param obj The other vector to compare distance
	 * @return the square distance of the 2 points
	 */
	@CheckReturnValue
	public float distance2(final BorderRadius obj) {
		final float deltaX = obj.topLeft - this.topLeft;
		final float deltaY = obj.topRight - this.topRight;
		final float deltaZ = obj.bottomRight - this.bottomRight;
		final float deltaW = obj.bottomLeft - this.bottomLeft;
		return deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ + deltaW * deltaW;
	}
	
	@CheckReturnValue
	public boolean isZero() {
		return this.topLeft == 0.0f && this.topRight == 0.0f && this.bottomRight == 0.0f && this.bottomLeft == 0.0f;
	}

	/**
	 *  Inversely scale the vector
	 * @param val Scale factor to divide by
	 */
	@CheckReturnValue
	public BorderRadius divide(final float val) {
		if (val != 0.0f) {
			return new BorderRadius(this.topLeft / val, this.topRight / val, this.bottomRight / val,
					this.bottomLeft / val);
		}
		throw new IllegalArgumentException("divice by 0 (BorderRadius)");
	}

	/**
	 *  Inversely scale the vector
	 * @param val Scale factor to divide by
	 */
	@CheckReturnValue
	public BorderRadius divide(final BorderRadius val) {
		return new BorderRadius(this.topLeft / val.topLeft, this.topRight / val.topRight,
				this.bottomRight / val.bottomRight, this.bottomLeft / val.bottomLeft);
	}

	/**
	 *  Return the dot product
	 * @param obj The other vector in the dot product
	 * @return Dot product value
	 */
	@CheckReturnValue
	public float dot(final BorderRadius obj) {
		return this.topLeft * obj.topLeft + this.topRight * obj.topRight + this.bottomRight * obj.bottomRight
				+ this.bottomLeft * obj.bottomLeft;
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
			return this.topLeft;
		} else if (index == 1) {
			return this.topRight;
		} else if (index == 2) {
			return this.bottomRight;
		} else if (index == 3) {
			return this.bottomLeft;
		}
		throw new IllegalArgumentException("Unknown index: " + index);
	}

	/**
	 *  Get the maximum value of the vector (x, y, z)
	 * @return The max value
	 */
	@CheckReturnValue
	public float getMax() {
		return Math.max(Math.max(Math.max(this.topLeft, this.topRight), this.bottomRight), this.bottomLeft);
	}

	/**
	 *  Get the Axis id with the maximum value
	 * @return Axis ID 0,1,2
	 */
	@CheckReturnValue
	public int getMaxAxis() {
		return (this.topLeft < this.topRight
				? (this.topRight < this.bottomRight ? (this.bottomLeft < this.bottomRight ? 2 : 3)
						: (this.bottomLeft < this.topRight ? 1 : 3))
				: (this.topLeft < this.bottomRight ? (this.bottomLeft < this.bottomRight ? 2 : 3)
						: (this.bottomLeft < this.topLeft ? 0 : 3)));
	}

	/**
	 *  Get the minimum value of the vector (x, y, z)
	 * @return The min value
	 */
	@CheckReturnValue
	public float getMin() {
		return Math.min(Math.min(Math.min(this.topLeft, this.topRight), this.bottomRight), this.bottomLeft);
	}

	/**
	 *  Get the Axis id with the minimum value
	 * @return Axis ID 0,1,2
	 */
	@CheckReturnValue
	public int getMinAxis() {
		return (this.topLeft < this.topRight
				? (this.topLeft < this.bottomRight ? (this.bottomLeft < this.topLeft ? 3 : 0)
						: (this.bottomLeft < this.bottomRight ? 3 : 2))
				: (this.topRight < this.bottomRight ? (this.bottomLeft < this.topRight ? 3 : 1)
						: (this.bottomLeft < this.bottomRight ? 3 : 2)));
	}

	// Overloaded operator for the negative of a vector
	@CheckReturnValue
	public BorderRadius invert() {
		return new BorderRadius(-this.topLeft, -this.topRight, -this.bottomRight, -this.bottomLeft);
	}

	/**
	 *  In-Equality compare operator with an other object.
	 * @param obj Reference on the comparing object
	 * @return true The Objects are NOT identical
	 * @return false The Objects are identical
	 */
	@CheckReturnValue
	public boolean isDifferent(final BorderRadius obj) {
		return ((this.bottomRight != obj.bottomRight) || (this.topRight != obj.topRight)
				|| (this.topLeft != obj.topLeft) || (this.bottomLeft != obj.bottomLeft));
	}

	/**
	 *  Equality compare operator with an other object.
	 * @param obj Reference on the comparing object
	 * @return true The Objects are identical
	 * @return false The Objects are NOT identical
	 */
	@CheckReturnValue
	public boolean isEqual(final BorderRadius obj) {
		return ((this.bottomRight == obj.bottomRight) && (this.topRight == obj.topRight)
				&& (this.topLeft == obj.topLeft) && (this.bottomLeft == obj.bottomLeft));
	}

	/**
	 *  Return the linear interpolation between this and another vector
	 * @param obj   The other vector
	 * @param ratio The ratio of this to obj (ratio = 0 => return copy of this,
	 *              ratio=1 => return other)
	 * @return New vector containing the value
	 */
	@CheckReturnValue
	public BorderRadius lerp(final BorderRadius obj, final float ratio) {
		return new BorderRadius(this.topLeft + (obj.topLeft - this.topLeft) * ratio,
				this.topRight + (obj.topRight - this.topRight) * ratio,
				this.bottomRight + (obj.bottomRight - this.bottomRight) * ratio,
				this.bottomLeft + (obj.bottomLeft - this.bottomLeft) * ratio);
	}

	@CheckReturnValue
	public BorderRadius less(final float value) {
		return new BorderRadius(this.topLeft - value, this.topRight - value, this.bottomRight - value,
				this.bottomLeft - value);
	}

	@CheckReturnValue
	public BorderRadius less(final float xxx, final float yyy, final float zzz, final float www) {
		return new BorderRadius(this.topLeft - xxx, this.topRight - yyy, this.bottomRight - zzz, this.bottomLeft - www);
	}

	/**
	 *  Subtract a vector from this one
	 * @param obj The vector to subtract
	 * @return A new vector with the data
	 */
	@CheckReturnValue
	public BorderRadius less(final BorderRadius obj) {
		return new BorderRadius(this.topLeft - obj.topLeft, this.topRight - obj.topRight,
				this.bottomRight - obj.bottomRight, this.bottomLeft - obj.bottomLeft);
	}

	/**
	 *  Scale the vector
	 * @param val Scale factor
	 * @return A new vector with the data
	 */
	@CheckReturnValue
	public BorderRadius multiply(final float val) {
		return new BorderRadius(this.topLeft * val, this.topRight * val, this.bottomRight * val, this.bottomLeft * val);
	}

	/**
	 *  Elementwise multiply this vector by the other
	 * @param obj The other vector
	 */
	@CheckReturnValue
	public BorderRadius multiply(final BorderRadius obj) {
		return new BorderRadius(this.topLeft * obj.topLeft, this.topRight * obj.topRight,
				this.bottomRight * obj.bottomRight, this.bottomLeft * obj.bottomLeft);
	}
	
	/**
	 *  Set each element to the max of the current values and the values of
	 *        another Vector4f
	 * @param obj The other Vector4f to compare with
	 */
	@CheckReturnValue
	public BorderRadius max(final BorderRadius obj) {
		return new BorderRadius(Math.max(this.topLeft, obj.topLeft), Math.max(this.topRight, obj.topRight),
				Math.max(this.bottomRight, obj.bottomRight), Math.max(this.bottomLeft, obj.bottomLeft));
	}

	@CheckReturnValue
	public static BorderRadius max(final BorderRadius obj1, final BorderRadius obj2) {
		return new BorderRadius(Math.max(obj1.topLeft, obj2.topLeft), Math.max(obj1.topRight, obj2.topRight),
				Math.max(obj1.bottomRight, obj2.bottomRight), Math.max(obj1.bottomLeft, obj2.bottomLeft));
	}

	@CheckReturnValue
	public static BorderRadius min(final BorderRadius obj1, final BorderRadius obj2) {
		return new BorderRadius(Math.min(obj1.topLeft, obj2.topLeft), Math.min(obj1.topRight, obj2.topRight),
				Math.min(obj1.bottomRight, obj2.bottomRight), Math.min(obj1.bottomLeft, obj2.bottomLeft));
	}

	/**
	 *  Set each element to the min of the current values and the values of
	 *        another Vector4f
	 * @param obj The other Vector4f to compare with
	 */
	@CheckReturnValue
	public BorderRadius min(final BorderRadius obj) {
		return new BorderRadius(Math.min(this.topLeft, obj.topLeft), Math.min(this.topRight, obj.topRight),
				Math.min(this.bottomRight, obj.bottomRight), Math.min(this.bottomLeft, obj.bottomLeft));
	}

	@CheckReturnValue
	public BorderRadius withTopLeft(final float value) {
		return new BorderRadius(value, this.topRight, this.bottomRight, this.bottomLeft);
	}

	@CheckReturnValue
	public BorderRadius withTopRight(final float value) {
		return new BorderRadius(this.topLeft, value, this.bottomRight, this.bottomLeft);
	}

	@CheckReturnValue
	public BorderRadius withBottomRight(final float value) {
		return new BorderRadius(this.topLeft, this.topRight, value, this.bottomLeft);
	}

	@CheckReturnValue
	public BorderRadius withBottomLeft(final float value) {
		return new BorderRadius(this.topLeft, this.topRight, this.bottomRight, value);
	}

	/**
	 *  Set 0 value on all the vector
	 */
	public static final BorderRadius ZERO = new BorderRadius(0, 0, 0, 0);
	public static final BorderRadius ONE = new BorderRadius(1, 1, 1, 1);
	public static final BorderRadius ONE_W = new BorderRadius(0, 0, 0, 1);
	public static final BorderRadius VALUE_2 = new BorderRadius(2, 2, 2, 2);
	public static final BorderRadius VALUE_4 = new BorderRadius(4, 4, 4, 4);
	public static final BorderRadius VALUE_8 = new BorderRadius(8, 8, 8, 8);
	public static final BorderRadius VALUE_16 = new BorderRadius(16, 16, 16, 16);
	public static final BorderRadius VALUE_32 = new BorderRadius(32, 32, 32, 32);
	public static final BorderRadius VALUE_64 = new BorderRadius(64, 64, 64, 64);
	public static final BorderRadius VALUE_128 = new BorderRadius(128, 128, 128, 128);
	public static final BorderRadius VALUE_256 = new BorderRadius(256, 256, 256, 256);
	public static final BorderRadius VALUE_512 = new BorderRadius(512, 512, 512, 512);
	public static final BorderRadius VALUE_1024 = new BorderRadius(1024, 1024, 1024, 1024);
	public static final BorderRadius MAX_VALUE = new BorderRadius(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE,
			Float.MAX_VALUE);

	@Override
	public String toString() {
		return "BorderRadius(" + FMath.floatToString(this.topLeft) + "," + FMath.floatToString(this.topRight) + ","
				+ FMath.floatToString(this.bottomRight) + ")";
	}

	/**
	 *  Return the triple product between this and another vector and another
	 * @param obj1 The other vector 1
	 * @param obj2 The other vector 2
	 * @return Value with the result of the triple product
	 */
	@CheckReturnValue
	public float triple(final BorderRadius obj1, final BorderRadius obj2) {
		return this.topLeft * (obj1.topRight * obj2.bottomRight - obj1.bottomRight * obj2.topRight)
				+ this.topRight * (obj1.bottomRight * obj2.topLeft - obj1.topLeft * obj2.bottomRight)
				+ this.bottomRight * (obj1.topLeft * obj2.topRight - obj1.topRight * obj2.topLeft);
	}

	public static BorderRadius valueOf(
			final String valuesX,
			final String valuesY,
			final String valuesZ,
			final String valuesW) {
		final float val1 = Float.parseFloat(valuesX);
		final float val2 = Float.parseFloat(valuesY);
		final float val3 = Float.parseFloat(valuesZ);
		final float val4 = Float.parseFloat(valuesW);
		return new BorderRadius(val1, val2, val3, val4);
	}
}
