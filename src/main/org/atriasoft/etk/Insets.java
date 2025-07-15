
package org.atriasoft.etk;

import org.atriasoft.etk.math.FMath;
import org.atriasoft.etk.math.Vector2f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umd.cs.findbugs.annotations.CheckReturnValue;

public record Insets(
		float top,
		float right,
		float bottom,
		float left) {
	final static Logger LOGGER = LoggerFactory.getLogger(Insets.class);

	public Vector2f toVector2f() {
		return new Vector2f(this.left + this.right, this.top + this.bottom);
	}
	
	public static Insets valueOf(String value) {
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
			LOGGER.error("Can not parse Constraint4f with more than 3 values: '" + value + "'");
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
		return new Insets(val1, val2, val3, val4);
	}

	/**
	 *  Constructor from scalars
	 * @param value unique value for X,Y and Z value
	 */
	public Insets(final float value) {
		this(value, value, value, value);
	}

	public Insets(final float top, final float right, final float bottom, final float left) {
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		this.left = left;
	}

	/**
	 *  Return a vector will the absolute values of each element
	 * @return the curent reference
	 */
	@CheckReturnValue
	public Insets abs() {
		return new Insets(Math.abs(this.top), Math.abs(this.right), Math.abs(this.bottom), Math.abs(this.left));
	}

	@CheckReturnValue
	public Insets add(final float value) {
		return new Insets(this.top + value, this.right + value, this.bottom + value, this.left + value);
	}

	@CheckReturnValue
	public Insets add(final float xxx, final float yyy, final float zzz, final float www) {
		return new Insets(this.top + xxx, this.right + yyy, this.bottom + zzz, this.left + www);
	}

	@CheckReturnValue
	public Insets clipInteger() {
		return new Insets((int) this.top, (int) this.right, (int) this.bottom, (int) this.left);
	}

	/**
	 *  Add a vector to this one
	 * @param obj The vector to add to this one
	 */
	@CheckReturnValue
	public Insets add(final Insets obj) {
		return new Insets(this.top + obj.top, this.right + obj.right, this.bottom + obj.left, this.bottom + obj.left);
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
	public float distance(final Insets obj) {
		return (float) Math.sqrt(distance2(obj));
	}

	/**
	 *  Return the distance squared between the ends of this and another
	 *        vector This is symantically treating the vector like a point
	 * @param obj The other vector to compare distance
	 * @return the square distance of the 2 points
	 */
	@CheckReturnValue
	public float distance2(final Insets obj) {
		final float deltaX = obj.top - this.top;
		final float deltaY = obj.right - this.right;
		final float deltaZ = obj.bottom - this.bottom;
		final float deltaW = obj.left - this.left;
		return deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ + deltaW * deltaW;
	}

	/**
	 *  Inversely scale the vector
	 * @param val Scale factor to divide by
	 */
	@CheckReturnValue
	public Insets divide(final float val) {
		if (val != 0.0f) {
			return new Insets(this.top / val, this.right / val, this.bottom / val, this.left / val);
		}
		throw new IllegalArgumentException("divice by 0 (Vector4f)");
	}

	/**
	 *  Inversely scale the vector
	 * @param val Scale factor to divide by
	 */
	@CheckReturnValue
	public Insets divide(final Insets val) {
		return new Insets(this.top / val.top, this.right / val.right, this.bottom / val.left, this.bottom / val.left);
	}

	/**
	 *  Return the dot product
	 * @param obj The other vector in the dot product
	 * @return Dot product value
	 */
	@CheckReturnValue
	public float dot(final Insets obj) {
		return this.top * obj.top + this.right * obj.right + this.bottom * obj.bottom + this.left * obj.left;
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
			return this.top;
		} else if (index == 1) {
			return this.right;
		} else if (index == 2) {
			return this.bottom;
		} else if (index == 3) {
			return this.left;
		}
		throw new IllegalArgumentException("Unknown index: " + index);
	}

	/**
	 *  Get the maximum value of the vector (x, y, z)
	 * @return The max value
	 */
	@CheckReturnValue
	public float getMax() {
		return Math.max(Math.max(Math.max(this.top, this.right), this.bottom), this.left);
	}

	/**
	 *  Get the Axis id with the maximum value
	 * @return Axis ID 0,1,2
	 */
	@CheckReturnValue
	public int getMaxAxis() {
		return (this.top < this.right
				? (this.right < this.bottom ? (this.left < this.bottom ? 2 : 3) : (this.left < this.right ? 1 : 3))
				: (this.top < this.bottom ? (this.left < this.bottom ? 2 : 3) : (this.left < this.top ? 0 : 3)));
	}

	/**
	 *  Get the minimum value of the vector (x, y, z)
	 * @return The min value
	 */
	@CheckReturnValue
	public float getMin() {
		return Math.min(Math.min(Math.min(this.top, this.right), this.bottom), this.left);
	}

	/**
	 *  Get the Axis id with the minimum value
	 * @return Axis ID 0,1,2
	 */
	@CheckReturnValue
	public int getMinAxis() {
		return (this.top < this.right
				? (this.top < this.bottom ? (this.left < this.top ? 3 : 0) : (this.left < this.bottom ? 3 : 2))
				: (this.right < this.bottom ? (this.left < this.right ? 3 : 1) : (this.left < this.bottom ? 3 : 2)));
	}

	// Overloaded operator for the negative of a vector
	@CheckReturnValue
	public Insets invert() {
		return new Insets(-this.top, -this.right, -this.bottom, -this.left);
	}

	/**
	 *  In-Equality compare operator with an other object.
	 * @param obj Reference on the comparing object
	 * @return true The Objects are NOT identical
	 * @return false The Objects are identical
	 */
	@CheckReturnValue
	public boolean isDifferent(final Insets obj) {
		return ((this.bottom != obj.bottom) || (this.right != obj.right) || (this.top != obj.top)
				|| (this.left != obj.left));
	}

	/**
	 *  Equality compare operator with an other object.
	 * @param obj Reference on the comparing object
	 * @return true The Objects are identical
	 * @return false The Objects are NOT identical
	 */
	@CheckReturnValue
	public boolean isEqual(final Insets obj) {
		return ((this.bottom == obj.bottom) && (this.right == obj.right) && (this.top == obj.top)
				&& (this.left == obj.left));
	}

	/**
	 *  Return the linear interpolation between this and another vector
	 * @param obj   The other vector
	 * @param ratio The ratio of this to obj (ratio = 0 => return copy of this,
	 *              ratio=1 => return other)
	 * @return New vector containing the value
	 */
	@CheckReturnValue
	public Insets lerp(final Insets obj, final float ratio) {
		return new Insets(this.top + (obj.top - this.top) * ratio, this.right + (obj.right - this.right) * ratio,
				this.bottom + (obj.bottom - this.bottom) * ratio, this.left + (obj.left - this.left) * ratio);
	}

	@CheckReturnValue
	public Insets less(final float value) {
		return new Insets(this.top - value, this.right - value, this.bottom - value, this.left - value);
	}

	@CheckReturnValue
	public Insets less(final float xxx, final float yyy, final float zzz, final float www) {
		return new Insets(this.top - xxx, this.right - yyy, this.bottom - zzz, this.left - www);
	}

	/**
	 *  Subtract a vector from this one
	 * @param obj The vector to subtract
	 * @return A new vector with the data
	 */
	@CheckReturnValue
	public Insets less(final Insets obj) {
		return new Insets(this.top - obj.top, this.right - obj.right, this.bottom - obj.bottom, this.left - obj.left);
	}

	/**
	 *  Scale the vector
	 * @param val Scale factor
	 * @return A new vector with the data
	 */
	@CheckReturnValue
	public Insets multiply(final float val) {
		return new Insets(this.top * val, this.right * val, this.bottom * val, this.left * val);
	}

	/**
	 *  Elementwise multiply this vector by the other
	 * @param obj The other vector
	 */
	@CheckReturnValue
	public Insets multiply(final Insets obj) {
		return new Insets(this.top * obj.top, this.right * obj.right, this.bottom * obj.left, this.bottom * obj.left);
	}
	
	/**
	 *  Set each element to the max of the current values and the values of
	 *        another Vector4f
	 * @param obj The other Vector4f to compare with
	 */
	@CheckReturnValue
	public Insets max(final Insets obj) {
		return new Insets(Math.max(this.top, obj.top), Math.max(this.right, obj.right),
				Math.max(this.bottom, obj.bottom), Math.max(this.left, obj.left));
	}

	@CheckReturnValue
	public static Insets max(final Insets obj1, final Insets obj2) {
		return new Insets(Math.max(obj1.top, obj2.top), Math.max(obj1.right, obj2.right),
				Math.max(obj1.bottom, obj2.bottom), Math.max(obj1.left, obj2.left));
	}

	@CheckReturnValue
	public static Insets min(final Insets obj1, final Insets obj2) {
		return new Insets(Math.min(obj1.top, obj2.top), Math.min(obj1.right, obj2.right),
				Math.min(obj1.bottom, obj2.bottom), Math.min(obj1.left, obj2.left));
	}

	/**
	 *  Set each element to the min of the current values and the values of
	 *        another Vector4f
	 * @param obj The other Vector4f to compare with
	 */
	@CheckReturnValue
	public Insets min(final Insets obj) {
		return new Insets(Math.min(this.top, obj.top), Math.min(this.right, obj.right),
				Math.min(this.bottom, obj.bottom), Math.min(this.left, obj.left));
	}

	@CheckReturnValue
	public Insets withTop(final float value) {
		return new Insets(value, this.right, this.bottom, this.left);
	}

	@CheckReturnValue
	public Insets withRight(final float value) {
		return new Insets(this.top, value, this.bottom, this.left);
	}

	@CheckReturnValue
	public Insets withBottom(final float value) {
		return new Insets(this.top, this.right, value, this.left);
	}

	@CheckReturnValue
	public Insets withLeft(final float value) {
		return new Insets(this.top, this.right, this.bottom, value);
	}

	/**
	 *  Set 0 value on all the vector
	 */
	public static final Insets ZERO = new Insets(0, 0, 0, 0);
	public static final Insets ONE = new Insets(1, 1, 1, 1);
	public static final Insets ONE_W = new Insets(0, 0, 0, 1);
	public static final Insets VALUE_2 = new Insets(2, 2, 2, 2);
	public static final Insets VALUE_4 = new Insets(4, 4, 4, 4);
	public static final Insets VALUE_8 = new Insets(8, 8, 8, 8);
	public static final Insets VALUE_16 = new Insets(16, 16, 16, 16);
	public static final Insets VALUE_32 = new Insets(32, 32, 32, 32);
	public static final Insets VALUE_64 = new Insets(64, 64, 64, 64);
	public static final Insets VALUE_128 = new Insets(128, 128, 128, 128);
	public static final Insets VALUE_256 = new Insets(256, 256, 256, 256);
	public static final Insets VALUE_512 = new Insets(512, 512, 512, 512);
	public static final Insets VALUE_1024 = new Insets(1024, 1024, 1024, 1024);
	public static final Insets MAX_VALUE = new Insets(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE,
			Float.MAX_VALUE);

	@Override
	public String toString() {
		return "Vector4f(" + FMath.floatToString(this.top) + "," + FMath.floatToString(this.right) + ","
				+ FMath.floatToString(this.bottom) + ")";
	}

	/**
	 *  Return the triple product between this and another vector and another
	 * @param obj1 The other vector 1
	 * @param obj2 The other vector 2
	 * @return Value with the result of the triple product
	 */
	@CheckReturnValue
	public float triple(final Insets obj1, final Insets obj2) {
		return this.top * (obj1.right * obj2.bottom - obj1.bottom * obj2.right)
				+ this.right * (obj1.bottom * obj2.top - obj1.top * obj2.bottom)
				+ this.bottom * (obj1.top * obj2.right - obj1.right * obj2.top);
	}

	public static Insets valueOf(
			final String valuesX,
			final String valuesY,
			final String valuesZ,
			final String valuesW) {
		final float val1 = Float.valueOf(valuesX);
		final float val2 = Float.valueOf(valuesY);
		final float val3 = Float.valueOf(valuesZ);
		final float val4 = Float.valueOf(valuesW);
		return new Insets(val1, val2, val3, val4);
	}
}
