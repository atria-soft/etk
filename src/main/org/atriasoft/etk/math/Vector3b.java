package org.atriasoft.etk.math;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umd.cs.findbugs.annotations.CheckReturnValue;

public record Vector3b(
		boolean x,
		boolean y,
		boolean z) {
	
	final static Logger LOGGER = LoggerFactory.getLogger(Vector3b.class);
	public static Vector3b valueOf(String value) {
		boolean val1 = false;
		boolean val2 = false;
		boolean val3 = false;
		// copy to permit to modify it :
		while (value.length() > 0 && value.charAt(0) == '(') {
			value = value.substring(1);
		}
		while (value.length() > 0 && value.charAt(0) == ')') {
			value = value.substring(0, value.length() - 1);
		}
		final String[] values = value.split(",| ");
		if (values.length > 3) {
			LOGGER.warn("Can not parse Vector3b with more than 3 values: '{}'", value);
		}
		if (values.length == 1) {
			// no coma ...
			// in every case, we parse the first element :
			val1 = Boolean.valueOf(values[0]);
			val2 = val1;
			val3 = val1;
		} else if (values.length == 2) {
			val1 = Boolean.valueOf(values[0]);
			val2 = Boolean.valueOf(values[1]);
			val3 = val2;
		} else {
			val1 = Boolean.valueOf(values[0]);
			val2 = Boolean.valueOf(values[1]);
			val3 = Boolean.valueOf(values[2]);
		}
		return new Vector3b(val1, val2, val3);
	}

	/*
	 * **************************************************** Constructor
	 *****************************************************/
	public Vector3b() {
		this(false, false, false);
	}

	public Vector3b(final boolean x, final boolean y, final boolean z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * In-Equality compare operator with an other object.
	 * @param obj Reference on the comparing object
	 * @return true The Objects are NOT identical
	 * @return false The Objects are identical
	 */
	@CheckReturnValue
	public boolean isDifferent(final Vector3b obj) {
		return (obj.x != this.x || obj.y != this.y || obj.z != this.z);
	}

	/**
	 * Equality compare operator with an other object.
	 * @param obj Reference on the comparing object
	 * @return true The Objects are identical
	 * @return false The Objects are NOT identical
	 */
	@CheckReturnValue
	public boolean isEqual(final Vector3b obj) {
		return (obj.x == this.x && obj.y == this.y && obj.z == this.z);
	}

	public static final Vector3b FALSE = new Vector3b(false, false, false);
	public static final Vector3b TRUE = new Vector3b(true, true, true);
	public static final Vector3b FALSE_FALSE_FALSE = FALSE;
	public static final Vector3b TRUE_TRUE_TRUE = TRUE;
	public static final Vector3b TRUE_FALSE_FALSE = new Vector3b(true, false, false);
	public static final Vector3b FALSE_TRUE_FALSE = new Vector3b(false, true, false);
	public static final Vector3b FALSE_FALSE_TRUE = new Vector3b(false, false, true);

	@Override
	public String toString() {
		return "(" + this.x + "," + this.y + ")";
	}

	@CheckReturnValue
	public Vector3b withX(final boolean xxx) {
		return new Vector3b(xxx, this.y, this.z);
	}

	@CheckReturnValue
	public Vector3b withY(final boolean yyy) {
		return new Vector3b(this.x, yyy, this.z);
	}

	@CheckReturnValue
	public Vector3b withZ(final boolean zzz) {
		return new Vector3b(this.x, this.y, zzz);
	}
}
