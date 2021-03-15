package org.atriasoft.etk.math;

import org.atriasoft.etk.internal.Log;

public class Vector2b {
	public static Vector2b valueOf(String value) {
		boolean val1 = false;
		boolean val2 = false;
		// copy to permit to modify it :
		while (value.length() > 0 && value.charAt(0) == '(') {
			value = value.substring(1);
		}
		while (value.length() > 0 && value.charAt(0) == ')') {
			value = value.substring(0, value.length() - 1);
		}
		final String[] values = value.split(",");
		if (values.length > 2) {
			Log.error("Can not parse Vector2f with more than 2 values: '" + value + "'");
		}
		if (values.length == 1) {
			// no coma ...
			// in every case, we parse the first element :
			val1 = Boolean.valueOf(values[0]);
			val2 = val1;
		} else {
			val1 = Boolean.valueOf(values[0]);
			val2 = Boolean.valueOf(values[1]);
		}
		return new Vector2b(val1, val2);
	}
	
	public boolean x = false;
	public boolean y = false;
	
	/* ****************************************************
	 *    Constructor
	 *****************************************************/
	public Vector2b() {
		this.x = false;
		this.y = false;
	}
	
	/**
	 * @brief Constructor from scalars
	 * @param xxx X value
	 * @param yyy Y value
	 */
	public Vector2b(final boolean xxx, final boolean yyy) {
		this.x = xxx;
		this.y = yyy;
	}
	
	/**
	 * @brief Constructor with external vector
	 * @param obj The vector to add to this one
	 */
	public Vector2b(final Vector2b obj) {
		this.x = obj.x;
		this.y = obj.y;
	}
	
	@Override
	public Vector2b clone() {
		return new Vector2b(this.x, this.y);
	}
	
	/**
	 * @brief Get X value
	 * @return the x value
	 */
	public boolean getX() {
		return this.x;
	}
	
	/**
	 * @brief Get Y value
	 * @return the y value
	 */
	public boolean getY() {
		return this.y;
	}
	
	/**
	 * @brief In-Equality compare operator with an other object.
	 * @param obj Reference on the comparing object
	 * @return true The Objects are NOT identical
	 * @return false The Objects are identical
	 */
	public boolean isDifferent(final Vector2b obj) {
		return (obj.x != this.x || obj.y != this.y);
	}
	
	/**
	 * @brief Equality compare operator with an other object.
	 * @param obj Reference on the comparing object
	 * @return true The Objects are identical
	 * @return false The Objects are NOT identical
	 */
	public boolean isEqual(final Vector2b obj) {
		return (obj.x == this.x && obj.y == this.y);
	}
	
	/**
	 * @brief Operator= Asign the current object with a value
	 * @param val Value to assign on the object
	 */
	public void set(final boolean val) {
		this.x = val;
		this.y = val;
	};
	
	/**
	 * @brief Operator= Asign the current object with a value
	 * @param xxx X value
	 * @param yyy Y value
	 */
	public void set(final boolean xxx, final boolean yyy) {
		this.x = xxx;
		this.y = yyy;
	}
	
	/**
	 * @brief Operator= Asign the current object with an other object
	 * @param obj Reference on the external object
	 */
	public void set(final Vector2b obj) {
		this.x = obj.x;
		this.y = obj.y;
	}
	
	public void setFalse() {
		this.x = false;
		this.y = false;
	}
	
	/**
	 * @brief Set 0 value on all the vector
	 */
	public void setTrue() {
		this.x = true;
		this.y = true;
	}
	
	/**
	 * @brief Set Value on the vector
	 * @param xxx X value.
	 * @param yyy Y value.
	 */
	public void setValue(final boolean xxx, final boolean yyy) {
		this.x = xxx;
		this.y = yyy;
	}
	
	/**
	 * @brief Set the x value
	 * @param xxx New value
	 */
	public void setX(final boolean xxx) {
		this.x = xxx;
	}
	
	/**
	 * @brief Set the y value
	 * @param yyy New value
	 */
	public void setY(final boolean yyy) {
		this.y = yyy;
	}
	
	@Override
	public String toString() {
		return "(" + this.x + "," + this.y + ")";
	}
}
