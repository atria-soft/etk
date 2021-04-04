/** @file
 * @author Edouard DUPIN
 * @copyright 2011, Edouard DUPIN, all right reserved
 * @license MPL v2.0 (see license file)
 */
package org.atriasoft.etk;

import org.atriasoft.etk.internal.Log;
import org.atriasoft.etk.math.Vector2f;

/**
 * in the dimension class we store the data as the more usefull unit (pixel) 
 * but one case need to be dynamic the %, then when requested in % the register the % value
 */
@SuppressWarnings("preview")
public record Dimension1D(
		float size,
		Distance type) {
	private static final float BASIC_RATIO = 72.0f / 25.4f;
	public static final Dimension1D ZERO = new Dimension1D(0);
	public static final float INCH_TO_MILLIMETER = 1.0f / 25.4f;
	public static final float FOOT_TO_MILLIMETER = 1.0f / 304.8f;
	public static final float METER_TO_MILLIMETER = 1.0f / 1000.0f;
	public static final float CENTIMETER_TO_MILLIMETER = 1.0f / 10.0f;
	public static final float KILOMETER_TO_MILLIMETER = 1.0f / 1000000.0f;
	public static final float MILLIMETER_TO_INCH = 25.4f;
	public static final float MILLIMETER_TO_FOOT = 304.8f;
	public static final float MILLIMETER_TO_METER = 1000.0f;
	public static final float MILLIMETER_TO_CENTIMETER = 10.0f;
	public static final float MILLIMETER_TO_KILOMETER = 1000000.0f;
	
	/**
	 * Constructor (default :0,0 mode pixel)
	 */
	public Dimension1D() {
		this(0, Distance.PIXEL);
	}
	
	/**
	 * Constructor
	 * @param size Requested dimension
	 */
	public Dimension1D(final float size) {
		this(size, Distance.PIXEL);
	}
	
	public Dimension1D(final float size, final Distance type) {
		this.size = size;
		this.type = type;
	}
	
	/**
	 * get the current dimension in pixel
	 * @return dimension in Pixel
	 */
	public float getPixel(final float upperSize) {
		switch (this.type) {
			case POURCENT:
				return upperSize * this.size * 0.01f;
			case PIXEL:
				return this.size;
			case METER:
				return this.size * METER_TO_MILLIMETER * BASIC_RATIO;
			case CENTIMETER:
				return this.size * CENTIMETER_TO_MILLIMETER * BASIC_RATIO;
			case MILLIMETER:
				return this.size * BASIC_RATIO;
			case KILOMETER:
				return this.size * KILOMETER_TO_MILLIMETER * BASIC_RATIO;
			case INCH:
				return this.size * INCH_TO_MILLIMETER * BASIC_RATIO;
			case FOOT:
				return this.size * FOOT_TO_MILLIMETER * BASIC_RATIO;
			default:
				return 128.0f;
		}
	}
	
	/**
	 * get the dimension type
	 * @return the type
	 */
	public Distance getType() {
		return this.type;
	}
	
	/**
	 * set the current dimension in requested type
	 * @param config dimension configuration.
	 */
	public static Dimension1D valueOf(String config) {
		final Vector2f size = Vector2f.ZERO;
		Distance type = Distance.PIXEL;
		if (config.endsWith("%")) {
			type = Distance.POURCENT;
			config = config.substring(0, config.length() - 1);
		} else if (config.endsWith("px")) {
			type = Distance.PIXEL;
			config = config.substring(0, config.length() - 2);
		} else if (config.endsWith("ft")) {
			type = Distance.FOOT;
			config = config.substring(0, config.length() - 2);
		} else if (config.endsWith("in")) {
			type = Distance.INCH;
			config = config.substring(0, config.length() - 2);
		} else if (config.endsWith("km")) {
			type = Distance.KILOMETER;
			config = config.substring(0, config.length() - 2);
		} else if (config.endsWith("mm")) {
			type = Distance.MILLIMETER;
			config = config.substring(0, config.length() - 2);
		} else if (config.endsWith("cm")) {
			type = Distance.CENTIMETER;
			config = config.substring(0, config.length() - 2);
		} else if (config.endsWith("m")) {
			type = Distance.METER;
			config = config.substring(0, config.length() - 1);
		} else if (config.endsWith("em")) {
			type = Distance.ELEMENT;
			config = config.substring(0, config.length() - 2);
		} else if (config.endsWith("ex")) {
			type = Distance.EX;
			config = config.substring(0, config.length() - 2);
		} else if (config.endsWith("pt")) {
			type = Distance.POINT;
			config = config.substring(0, config.length() - 2);
		} else if (config.endsWith("pc")) {
			type = Distance.PC;
			config = config.substring(0, config.length() - 2);
		} else {
			Log.critical("Can not parse dimension : '" + config + "'");
			return null;
		}
		final float tmp = Float.valueOf(config);
		final Dimension1D ret = new Dimension1D(tmp, type);
		Log.verbose(" config dimension : '" + config + "'  == > " + ret.toString());
		return ret;
	}
	
	/**
	 * string cast :
	 */
	@Override
	public String toString() {
		String str = Float.toString(this.size);
		switch (getType()) {
			case POURCENT -> str += "%";
			case PIXEL -> str += "px";
			case METER -> str += "m";
			case CENTIMETER -> str += "cm";
			case MILLIMETER -> str += "mm";
			case KILOMETER -> str += "km";
			case INCH -> str += "in";
			case FOOT -> str += "ft";
			case ELEMENT -> str += "em";
			case EX -> str += "ex";
			case POINT -> str += "pt";
			case PC -> str += "pc";
			default -> str += "";
		}
		return str;
	}
	
}
