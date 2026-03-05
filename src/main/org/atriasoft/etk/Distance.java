package org.atriasoft.etk;

/**
 * Enumeration of distance/dimension units.
 *
 * <p>Defines the various unit types that can be used for measurements in the ETK framework,
 * including absolute units (pixels, metric, imperial) and relative units (percentage, em, ex).</p>
 *
 * @author Edouard DUPIN
 * @since 0.1.0
 */
public enum Distance {
	UNKNOW, //!< "%"
	POURCENT, //!< "%"
	PIXEL, //!< "px"
	METER, //!< "m"
	CENTIMETER, //!< "cm"
	MILLIMETER, //!< "mm"
	KILOMETER, //!< "km"
	INCH, //!< "in"
	FOOT, //!< "ft"
	ELEMENT, //!< "em"
	EX, //!< "ex"
	POINT, //!< "pt"
	PC; //!< "pc"
	
	/**
	 * Parses the unit from the end of a string.
	 *
	 * <p>Analyzes the suffix of the string to determine the distance unit type.</p>
	 *
	 * @param data String ending with a unit suffix (e.g., "10px", "50%")
	 * @return Parsed Distance enum value, or UNKNOW if not recognized
	 */
	public static Distance parseEndSmallString(String data) {
		if (data.endsWith("%")) {
			return Distance.POURCENT;
		} else if (data.endsWith("px")) {
			return Distance.PIXEL;
		} else if (data.endsWith("ft")) {
			return Distance.FOOT;
		} else if (data.endsWith("in")) {
			return Distance.INCH;
		} else if (data.endsWith("km")) {
			return Distance.KILOMETER;
		} else if (data.endsWith("mm")) {
			return Distance.MILLIMETER;
		} else if (data.endsWith("cm")) {
			return Distance.CENTIMETER;
		} else if (data.endsWith("m")) {
			return Distance.METER;
		} else if (data.endsWith("em")) {
			return Distance.ELEMENT;
		} else if (data.endsWith("ex")) {
			return Distance.EX;
		} else if (data.endsWith("pt")) {
			return Distance.POINT;
		} else if (data.endsWith("pc")) {
			return Distance.PC;
		}
		return UNKNOW;
	}
	
	/**
	 * Parses a unit from an exact string match.
	 *
	 * @param data String representing a distance unit (e.g., "px", "%", "cm")
	 * @return Parsed Distance enum value, or UNKNOW if not recognized
	 */
	public static Distance parseSmallString(String data) {
		if (data.equals("%")) {
			return Distance.POURCENT;
		} else if (data.equals("px")) {
			return Distance.PIXEL;
		} else if (data.equals("ft")) {
			return Distance.FOOT;
		} else if (data.equals("in")) {
			return Distance.INCH;
		} else if (data.equals("km")) {
			return Distance.KILOMETER;
		} else if (data.equals("mm")) {
			return Distance.MILLIMETER;
		} else if (data.equals("cm")) {
			return Distance.CENTIMETER;
		} else if (data.equals("m")) {
			return Distance.METER;
		} else if (data.equals("em")) {
			return Distance.ELEMENT;
		} else if (data.equals("ex")) {
			return Distance.EX;
		} else if (data.equals("pt")) {
			return Distance.POINT;
		} else if (data.equals("pc")) {
			return Distance.PC;
		}
		return UNKNOW;
	}
	
	/**
	 * Removes this distance unit suffix from the end of a string.
	 *
	 * @param data String with unit suffix
	 * @return String with the unit suffix removed
	 */
	public String removeEndString(String data) {
		return switch (this) {
			case POURCENT -> data.substring(0, data.length() - 1);
			case PIXEL -> data.substring(0, data.length() - 2);
			case METER -> data.substring(0, data.length() - 1);
			case CENTIMETER -> data.substring(0, data.length() - 2);
			case MILLIMETER -> data.substring(0, data.length() - 2);
			case KILOMETER -> data.substring(0, data.length() - 2);
			case INCH -> data.substring(0, data.length() - 2);
			case FOOT -> data.substring(0, data.length() - 2);
			case ELEMENT -> data.substring(0, data.length() - 2);
			case EX -> data.substring(0, data.length() - 2);
			case POINT -> data.substring(0, data.length() - 2);
			case PC -> data.substring(0, data.length() - 2);
			default -> data;
		};
	}
	
	/**
	 * Converts this distance unit to its string representation.
	 *
	 * @return String representation of the unit (e.g., "px", "%", "cm")
	 */
	public String toSmallString() {
		return switch (this) {
			case POURCENT -> "%";
			case PIXEL -> "px";
			case METER -> "m";
			case CENTIMETER -> "cm";
			case MILLIMETER -> "mm";
			case KILOMETER -> "km";
			case INCH -> "in";
			case FOOT -> "ft";
			case ELEMENT -> "em";
			case EX -> "ex";
			case POINT -> "pt";
			case PC -> "pc";
			default -> "";
		};
	}
	
}