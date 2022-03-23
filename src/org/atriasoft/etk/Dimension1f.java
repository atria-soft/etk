/** @file
 * @author Edouard DUPIN
 * @copyright 2011, Edouard DUPIN, all right reserved
 * @license MPL v2.0 (see license file)
 */
package org.atriasoft.etk;

import org.atriasoft.etk.internal.Log;

/**
 * in the dimension class we store the data as the more usefull unit (pixel)
 * but one case need to be dynamic the %, then when requested in % the register the % value
 */
public record Dimension1f(
		float size,
		Distance type) {
	
	private static final float BASIC_RATIO = 72.0f / 25.4f;
	public static final Dimension1f ZERO = new Dimension1f(0);
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
	public Dimension1f() {
		this(0, Distance.PIXEL);
	}
	
	/**
	 * Constructor
	 * @param size Requested dimension
	 */
	public Dimension1f(final float size) {
		this(size, Distance.PIXEL);
	}
	
	public Dimension1f(final float size, final Distance type) {
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
	public static Dimension1f valueOf(String config) {
		Distance type = Distance.parseEndSmallString(config);
		config = type.removeEndString(config);
		if (type == Distance.UNKNOW) {
			Log.critical("Can not parse dimension : '" + config + "'");
			return null;
		}
		final float tmp = Float.valueOf(config);
		final Dimension1f ret = new Dimension1f(tmp, type);
		Log.verbose(" config dimension : '" + config + "'  == > " + ret.toString());
		return ret;
	}
	
	/**
	 * string cast :
	 */
	@Override
	public String toString() {
		return Float.toString(this.size) + getType().toSmallString();
	}
	
}
