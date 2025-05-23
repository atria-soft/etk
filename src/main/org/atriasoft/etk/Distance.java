package org.atriasoft.etk;


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
	 * string cast :
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