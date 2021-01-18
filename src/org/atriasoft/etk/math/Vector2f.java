package org.atriasoft.etk.math;

public class Vector2f {
	public static Vector2f zero() {
		return new Vector2f(0, 0);
	}
	
	public float x = 0;
	public float y = 0;
	
	/* ****************************************************
	 *    Constructor
	 *****************************************************/
	public Vector2f() {
		this.x = 0;
		this.y = 0;
	}
	
	/**
	 * @brief Constructor from scalars
	 * @param xxx X value
	 * @param yyy Y value
	 */
	public Vector2f(final float xxx, final float yyy) {
		this.x = xxx;
		this.y = yyy;
	}
	
	/**
	 * @brief Constructor with external vector
	 * @param obj The vector to add to this one
	 */
	public Vector2f(final Vector2f obj) {
		this.x = obj.x;
		this.y = obj.y;
	}
	
	/**
	 * @brief Return a vector will the absolute values of each element
	 * @return New vector containing the value
	 */
	public Vector2f abs() {
		this.x = Math.abs(this.x);
		this.y = Math.abs(this.y);
		return this;
	}
	
	public Vector2f absolute() {
		return new Vector2f(Math.abs(this.x), Math.abs(this.y));
	}
	
	/**
	 * @brief Operator+= Addition an other vertor with this one
	 * @param val Value to addition at x/y
	 */
	public Vector2f add(final float val) {
		this.x += val;
		this.y += val;
		return this;
	}
	
	/**
	 * @brief Operator+= Addition an other vertor with this one
	 * @param obj Reference on the external object
	 */
	public Vector2f add(final Vector2f obj) {
		this.x += obj.x;
		this.y += obj.y;
		return this;
	}
	
	@Override
	public Vector2f clone() {
		return new Vector2f(this);
	}
	
	/**
	 * @brief Return the axis with the largest ABSOLUTE value
	 * @return values 0,1 for x or y
	 */
	public int closestAxis() {
		return absolute().maxAxis();
	}
	
	/**
	 * @brief Return the cross product / determinant
	 * @param obj The other vector in the cross product
	 * @return cross product value
	 */
	public float cross(final Vector2f obj) {
		return this.x * obj.y - this.y * obj.x;
	}
	
	/**
	 * @brief Decrementation of this vector (-1 of 2 elements)
	 */
	public Vector2f decrement() {
		this.x--;
		this.y--;
		return this;
	}
	
	/**
	 * @brief Operator/ Dividing an other vertor with this one
	 * @param val Value to addition at x/y
	 */
	public Vector2f devide(final float val) {
		this.x /= val;
		this.y /= val;
		return this;
	}
	
	/**
	 * @brief Operator/ Dividing an other vertor with this one
	 * @param obj Reference on the external object
	 */
	public Vector2f devide(final Vector2f obj) {
		this.x /= obj.x;
		this.y /= obj.y;
		return this;
	}
	
	/**
	 * @brief Return the distance between the ends of this and another vector
	 * This is semantically treating the vector like a point
	 * @param obj The other vector to compare distance
	 * @return the distance of the 2 points
	 */
	public float distance(final Vector2f obj) {
		return (float) Math.sqrt(distance2(obj));
	}
	
	/**
	 * @brief Return the distance squared between the ends of this and another vector
	 * This is semantically treating the vector like a point
	 * @param obj The other vector to compare distance
	 * @return the square distance of the 2 points
	 */
	public float distance2(final Vector2f obj) {
		final float deltaX = obj.x - this.x;
		final float deltaY = obj.y - this.y;
		return deltaX * deltaX + deltaY * deltaY;
	}
	
	/**
	 * @brief Return the dot product
	 * @param obj The other vector in the dot product
	 * @return Dot product value
	 */
	public float dot(final Vector2f obj) {
		return this.x * obj.x + this.y * obj.y;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		// check type
		if (getClass() != obj.getClass()) {
			return false;
		}
		// cast object
		final Vector2f other = (Vector2f) obj;
		// checks values
		if (Float.floatToIntBits(this.x) != Float.floatToIntBits(other.x)) {
			return false;
		}
		return Float.floatToIntBits(this.y) == Float.floatToIntBits(other.y);
	}
	
	/**
	 * @brief Return the axis with the smallest ABSOLUTE value
	 * @return values 0,1 for x, or z
	 */
	public int furthestAxis() {
		return absolute().minAxis();
	}
	
	/**
	 * @brief get the value with his index
	 * @param index Index of the value (0: x, 1: y)
	 * @return The value associated
	 */
	public float get(final int index) {
		if (index == 0) {
			return this.x;
		} else if (index == 1) {
			return this.y;
		}
		throw new IllegalArgumentException("Unknown index: " + index);
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
	
	@Override
	public int hashCode() {
		int hash = 38521;
		hash += hash + Float.floatToIntBits(this.x);
		hash += hash + Float.floatToIntBits(this.y);
		return hash;
	}
	
	/**
	 * @brief Incrementation of this vector (+1 of 2 elements)
	 */
	public Vector2f increment() {
		this.x++;
		this.y++;
		return this;
	}
	
	// Overloaded operator for the negative of a vector
	public Vector2f invert() {
		this.x = -this.x;
		this.y = -this.y;
		return this;
	}
	
	/**
	 * @brief In-Equality compare operator with an other object.
	 * @param obj Reference on the comparing object
	 * @return true The Objects are NOT identical
	 * @return false The Objects are identical
	 */
	public boolean isDifferent(final Vector2f obj) {
		return (obj.x != this.x || obj.y != this.y);
	}
	
	/**
	 * @brief Equality compare operator with an other object.
	 * @param obj Reference on the comparing object
	 * @return true The Objects are identical
	 * @return false The Objects are NOT identical
	 */
	public boolean isEqual(final Vector2f obj) {
		return (obj.x == this.x && obj.y == this.y);
	}
	
	public boolean isGreater(final Vector2f obj) {
		return (this.x > obj.x && this.y > obj.y);
	}
	
	public boolean isGreaterOrEqual(final Vector2f obj) {
		return (this.x >= obj.x && this.y >= obj.y);
	}
	
	public boolean isLower(final Vector2f obj) {
		return (this.x < obj.x && this.y < obj.y);
	}
	
	public boolean isLowerOrEqual(final Vector2f obj) {
		return (this.x <= obj.x && this.y <= obj.y);
	}
	
	/**
	 * @brief Check if the vector is unitary (langth = 10f=)
	 * @return true if unit , false otherwise
	 */
	public boolean isUnit() {
		return FMath.approxEqual(length2(), 1.0f, Constant.MACHINE_EPSILON);
	}
	
	/**
	 * @brief Check if the vector is equal to (0,0)
	 * @return true The value is equal to (0,0)
	 * @return false The value is NOT equal to (0,0)
	 */
	public boolean isZero() {
		return FMath.approxEqual(length2(), 0.0f, Constant.MACHINE_EPSILON);
	}
	
	/**
	 * @brief Get the length of the vector
	 * @return Length value
	 */
	public float length() {
		return (float) Math.sqrt(length2());
	}
	
	/**
	 * @brief Get the length of the vector squared
	 * @return Squared length value.
	 */
	public float length2() {
		return dot(this);
	}
	
	/**
	 * @brief Operator-= Decrement an other vertor with this one
	 * @param val Value to addition at x/y
	 */
	public Vector2f less(final float val) {
		this.x -= val;
		this.y -= val;
		return this;
	}
	
	/**
	 * @brief Operator-= Decrement an other vertor with this one
	 * @param obj Reference on the external object
	 */
	public Vector2f less(final Vector2f obj) {
		this.x -= obj.x;
		this.y -= obj.y;
		return this;
	}
	
	/**
	 * @brief Return the axis with the largest value
	 * @return values are 0,1 for x or y
	 */
	public int maxAxis() {
		return this.x < this.y ? 1 : 0;
	}
	
	/**
	 * @brief Return the axis with the smallest value 
	 * @return values are 0,1 for x or y
	 */
	public int minAxis() {
		return this.x < this.y ? 0 : 1;
	}
	
	/**
	 * @brief Operator*= Multiplication an other vertor with this one
	 * @param val Value to addition at x/y
	 */
	public Vector2f multiply(final float val) {
		this.x *= val;
		this.y *= val;
		return this;
	}
	
	/**
	 * @brief Operator*= Multiplication an other vertor with this one
	 * @param obj Reference on the external object
	 */
	public Vector2f multiply(final Vector2f obj) {
		this.x *= obj.x;
		this.y *= obj.y;
		return this;
	}
	
	public Vector2f multiplyNew(final float val) {
		return new Vector2f(this.x * val, this.y * val);
	};
	
	/**
	 * @brief Normalize this vector x^2 + y^2 = 1
	 */
	public Vector2f normalize() {
		this.devide(length());
		return this;
	};
	
	/**
	 * @brief Return a normalized version of this vector
	 * @return New vector containing the value
	 */
	public Vector2f normalized() {
		final Vector2f tmp = clone();
		tmp.normalize();
		return tmp;
	}
	
	/**
	 * @brief Normalize this vector x^2 + y^2 = 1 (check if not deviding by 0, if it is the case ==> return (1,0))
	 * @return Local reference of the vector normalized
	 */
	public Vector2f safeNormalize() {
		final float tmp = length();
		if (tmp != 0) {
			this.devide(length());
			return this;
		}
		setValue(1, 0);
		return this;
	}
	
	/**
	 * @brief Operator= Asign the current object with a value
	 * @param val Value to assign on the object
	 */
	public Vector2f set(final float val) {
		this.x = val;
		this.y = val;
		return this;
	}
	
	/**
	 * @brief Operator= Asign the current object with a value
	 * @param xxx X value
	 * @param yyy Y value
	 */
	public Vector2f set(final float xxx, final float yyy) {
		this.x = xxx;
		this.y = yyy;
		return this;
	}
	
	/**
	 * @brief Operator= Asign the current object with an other object
	 * @param obj Reference on the external object
	 */
	public Vector2f set(final Vector2f obj) {
		this.x = obj.x;
		this.y = obj.y;
		return this;
	}
	
	/**
	 * @brief Set each element to the max of the current values and the values of another vector
	 * @param other The other vector to compare with
	 */
	public Vector2f setMax(final Vector2f other) {
		this.x = Math.max(this.x, other.x);
		this.y = Math.max(this.y, other.y);
		return this;
	}
	
	/**
	 * @brief Set each element to the min of the current values and the values of another vector
	 * @param other The other vector to compare with
	 */
	public Vector2f setMin(final Vector2f other) {
		this.x = Math.min(this.x, other.x);
		this.y = Math.min(this.y, other.y);
		return this;
	}
	
	// Return one unit orthogonal vector of the current vector
	public Vector2f setUnitOrthogonal() {
		this.y = -this.y;
		return safeNormalize();
	}
	
	/**
	 * @brief Set Value on the vector
	 * @param xxx X value.
	 * @param yyy Y value.
	 */
	public Vector2f setValue(final float xxx, final float yyy) {
		this.x = xxx;
		this.y = yyy;
		return this;
	}
	
	/**
	 * @brief Set the x value
	 * @param xxx New value
	 */
	public Vector2f setX(final float xxx) {
		this.x = xxx;
		return this;
	}
	
	/**
	 * @brief Set the y value
	 * @param yyy New value
	 */
	public Vector2f setY(final float yyy) {
		this.y = yyy;
		return this;
	}
	
	/**
	 * @brief Set 0 value on all the vector
	 */
	public Vector2f setZero() {
		this.x = 0;
		this.y = 0;
		return this;
	}
	
	@Override
	public String toString() {
		return "Vector2f(" + this.x + "," + this.y + ")";
	}
	
}
