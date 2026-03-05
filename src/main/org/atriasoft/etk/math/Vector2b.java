package org.atriasoft.etk.math;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umd.cs.findbugs.annotations.CheckReturnValue;

public record Vector2b(
		boolean x,
		boolean y) {
	final static Logger LOGGER = LoggerFactory.getLogger(Vector2b.class);
	
	public static Vector2b valueOf(String value) {
		boolean val1 = false;
		boolean val2 = false;
		// copy to permit to modify it :
		while (value.length() > 0 && value.charAt(0) == '(') {
			value = value.substring(1);
		}
		while (value.length() > 0 && value.charAt(value.length() - 1) == ')') {
			value = value.substring(0, value.length() - 1);
		}
		final String[] values = value.split(",| ");
		if (values.length > 2) {
			LOGGER.warn("Can not parse Vector2f with more than 2 values: '{}'", value);
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

	/*
	 * **************************************************** Constructor
	 *****************************************************/
	public Vector2b() {
		this(false, false);
	}

	public Vector2b(final boolean x, final boolean y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * In-Equality compare operator with an other object.
	 * @param obj Reference on the comparing object
	 * @return true The Objects are NOT identical
	 * @return false The Objects are identical
	 */
	@CheckReturnValue
	public boolean isDifferent(final Vector2b obj) {
		return (obj.x != this.x || obj.y != this.y);
	}

	/**
	 * Equality compare operator with an other object.
	 * @param obj Reference on the comparing object
	 * @return true The Objects are identical
	 * @return false The Objects are NOT identical
	 */
	@CheckReturnValue
	public boolean isEqual(final Vector2b obj) {
		return (obj.x == this.x && obj.y == this.y);
	}

	public static final Vector2b FALSE = new Vector2b(false, false);
	public static final Vector2b TRUE = new Vector2b(true, true);
	public static final Vector2b FALSE_FALSE = FALSE;
	public static final Vector2b TRUE_TRUE = TRUE;
	public static final Vector2b TRUE_FALSE = new Vector2b(true, false);
	public static final Vector2b FALSE_TRUE = new Vector2b(false, true);

	@Override
	public String toString() {
		return "(" + this.x + "," + this.y + ")";
	}

	@CheckReturnValue
	public Vector2b withX(final boolean xxx) {
		return new Vector2b(xxx, this.y);
	}

	@CheckReturnValue
	public Vector2b withY(final boolean yyy) {
		return new Vector2b(this.x, yyy);
	}
}
