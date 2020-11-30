package org.atriasoft.etk.math;

public class Vector2i {
	public int x = 0;
	public int y = 0;
	/* ****************************************************
	 *    Constructor
	 *****************************************************/
	public Vector2i() {
		this.x = 0;
		this.y = 0;
	}
	/**
	 * @brief Constructor from scalars
	 * @param xxx X value
	 * @param yyy Y value
	 */
	public Vector2i(int xxx, int yyy) {
		this.x = xxx;
		this.y = yyy;
	}
	/**
	 * @brief Constructor with external vector
	 * @param obj The vector to add to this one
	 */
	public Vector2i(Vector2i obj) {
		this.x = obj.x;
		this.y = obj.y;
	}
	/**
	 * @brief Operator= Asign the current object with an other object
	 * @param obj Reference on the external object
	 */
	public void set(Vector2i obj) {
		this.x = obj.x;
		this.y = obj.y;
	}
	/**
	 * @brief Operator= Asign the current object with a value
	 * @param val Value to assign on the object
	 */
	public void set(int val) {
		this.x = val;
		this.y = val;
	}
	/**
	 * @brief Operator= Asign the current object with a value
	 * @param xxx X value
	 * @param yyy Y value
	 */
	public void set(int xxx, int yyy) {
		this.x = xxx;
		this.y = yyy;
	}
	/**
	 * @brief Equality compare operator with an other object.
	 * @param obj Reference on the comparing object
	 * @return true The Objects are identical
	 * @return false The Objects are NOT identical
	 */
	public boolean isEqual(Vector2i obj) {
		return (    obj.x == this.x
		         && obj.y == this.y);
	}
	/**
	 * @brief In-Equality compare operator with an other object.
	 * @param obj Reference on the comparing object
	 * @return true The Objects are NOT identical
	 * @return false The Objects are identical
	 */
	public boolean isDifferent(Vector2i obj) {
		return (    obj.x != this.x
		         || obj.y != this.y);
	}
	public boolean isLowerOrEqual(Vector2i obj) {
		return (    this.x <= obj.x
		         && this.y <= obj.y);
	}
	public boolean isLower(Vector2i obj) {
		return (    this.x < obj.x
		         && this.y < obj.y);
	}
	public boolean isGreaterOrEqual(Vector2i obj) {
		return (    this.x >= obj.x
		         && this.y >= obj.y);
	}
	public boolean isGreater(Vector2i obj) {
		return (    this.x > obj.x
		         && this.y > obj.y);
	}
	/**
	 * @brief Operator+= Addition an other vertor with this one
	 * @param obj Reference on the external object
	 */
	void add(Vector2i obj) {
		this.x += obj.x;
		this.y += obj.y;
	}
	/**
	 * @brief Operator+= Addition an other vertor with this one
	 * @param val Value to addition at x/y
	 */
	public void add(int val) {
		this.x += val;
		this.y += val;
	}
	/**
	 * @brief Operator-= Decrement an other vertor with this one
	 * @param obj Reference on the external object
	 */
	public void less(Vector2i obj) {
		this.x -= obj.x;
		this.y -= obj.y;
	}
	/**
	 * @brief Operator-= Decrement an other vertor with this one
	 * @param val Value to addition at x/y
	 */
	public void less(int val) {
		this.x -= val;
		this.y -= val;
	}
	/**
	 * @brief Operator*= Multiplication an other vertor with this one
	 * @param obj Reference on the external object
	 */
	public void multiply(Vector2i obj) {
		this.x *= obj.x;
		this.y *= obj.y;
	}
	/**
	 * @brief Operator*= Multiplication an other vertor with this one
	 * @param val Value to addition at x/y
	 */
	public void multiply(int val) {
		this.x *= val;
		this.y *= val;
	}
	/**
	 * @brief Operator/ Dividing an other vertor with this one
	 * @param obj Reference on the external object
	 */
	public void devide(Vector2i obj) {
		this.x /= obj.x;
		this.y /= obj.y;
	}
	/**
	 * @brief Operator/ Dividing an other vertor with this one
	 * @param val Value to addition at x/y
	 */
	public void devide(int val) {
		this.x /= val;
		this.y /= val;
	}
	/**
	 * @brief Incrementation of this vector (+1 of 2 elements)
	 */
	public void increment() {
		this.x++;
		this.y++;
	}
	/**
	 * @brief Decrementation of this vector (-1 of 2 elements)
	 */
	public void decrement() {
		this.x--;
		this.y--;
	}
	/**
	 * @brief Return the cross product / determinant
	 * @param obj The other vector in the cross product
	 * @return cross product value
	 */
	public int cross(Vector2i obj) {
		return   this.x * obj.y
		       - this.y * obj.x;
	}
	/**
	 * @brief Return the dot product
	 * @param obj The other vector in the dot product
	 * @return Dot product value
	 */
	public int dot(Vector2i obj) {
		return   this.x * obj.x
		       + this.y * obj.y;
	}
	/**
	 * @brief Get the length of the vector squared
	 * @return Squared length value.
	 */
	public int length2() {
		return dot(this);
	}
	/**
	 * @brief Get the length of the vector
	 * @return Length value
	 */
	public int length() {
		return (int) Math.sqrt(length2());
	}
	/**
	 * @brief Return the distance squared between the ends of this and another vector
	 * This is symantically treating the vector like a point
	 * @param obj The other vector to compare distance
	 * @return the square distance of the 2 points
	 */
	public int distance2(Vector2i obj) {
		int deltaX = obj.x - this.x;
		int deltaY = obj.y - this.y;
		return deltaX*deltaX + deltaY*deltaY;
	}
	/**
	 * @brief Return the distance between the ends of this and another vector
	 * This is symantically treating the vector like a point
	 * @param obj The other vector to compare distance
	 * @return the distance of the 2 points
	 */
	public int distance(Vector2i obj) {
		return (int)Math.sqrt(this.distance2(obj));
	}
	/**
	 * @brief Normalize this vector x^2 + y^2 = 1
	 */
	public void normalize() {
		this.devide(length());
	}
	/**
	 * @brief Normalize this vector x^2 + y^2 = 1 (check if not deviding by 0, if it is the case ==> return (1,0))
	 * @return Local reference of the vector normalized
	 */
	public void safeNormalize() {
		int tmp = length();
		if (tmp != 0) {
			this.devide(length());
			return;
		}
		setValue(1,0);
		return;
	}
	/**
	 * @brief Return a normalized version of this vector
	 * @return New vector containing the value
	 */
	public Vector2i normalized() {
		Vector2i tmp = this.clone();
		tmp.normalize();
		return tmp;
	}
	/**
	 * @brief Return a vector will the absolute values of each element
	 * @return New vector containing the value
	 */
	public Vector2i absolute() {
		return new Vector2i( Math.abs(this.x),
		                     Math.abs(this.y));
	}
	/**
	 * @brief Return the axis with the smallest value 
	 * @return values are 0,1 for x or y
	 */
	public int minAxis() {
		return this.x < this.y ? 0 : 1;
	}
	/**
	 * @brief Return the axis with the largest value
	 * @return values are 0,1 for x or y
	 */
	public int maxAxis() {
		return this.x < this.y ? 1 : 0;
	}
	/**
	 * @brief Return the axis with the smallest ABSOLUTE value
	 * @return values 0,1 for x, or z
	 */
	public int furthestAxis() {
		return absolute().minAxis();
	}
	/**
	 * @brief Return the axis with the largest ABSOLUTE value
	 * @return values 0,1 for x or y
	 */
	public int closestAxis() {
		return absolute().maxAxis();
	}
	/**
	 * @brief Set the x value
	 * @param xxx New value
	 */
	public void setX(int xxx) {
		this.x = xxx;
	};
	/**
	 * @brief Set the y value
	 * @param yyy New value
	 */
	public void setY(int yyy) {
		this.y = yyy;
	};
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
	 * @brief Set each element to the max of the current values and the values of another vector
	 * @param other The other vector to compare with
	 */
	public void setMax(Vector2i other) {
		this.x = Math.max(this.x, other.x);
		this.y = Math.max(this.y, other.y);
	}
	/**
	 * @brief Set each element to the min of the current values and the values of another vector
	 * @param other The other vector to compare with
	 */
	public void setMin(Vector2i other) {
		this.x = Math.min(this.x, other.x);
		this.y = Math.min(this.y, other.y);
	}
	/**
	 * @brief Set Value on the vector
	 * @param xxx X value.
	 * @param yyy Y value.
	 */
	public void setValue(int xxx, int yyy) {
		this.x = xxx;
		this.y = yyy;
	}
	/**
	 * @brief Set 0 value on all the vector
	 */
	public void setZero() {
		this.x = 0;
		this.y = 0;
	}
	/**
	 * @brief Check if the vector is equal to (0,0)
	 * @return true The value is equal to (0,0)
	 * @return false The value is NOT equal to (0,0)
	 */
	public boolean isZero() {
		return    this.x == 0
		       && this.y == 0;
	}
	
	public Vector2i clone() {
		return new Vector2i(this);
	}
	@Override
	public String toString() {
		return "Vector2i(" + this.x + "," + this.y + ")";
	}
}
