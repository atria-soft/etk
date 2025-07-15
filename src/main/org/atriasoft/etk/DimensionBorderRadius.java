/** @file
 * @author Edouard DUPIN
 * @copyright 2011, Edouard DUPIN, all right reserved
 * @license MPL v2.0 (see license file)
 */
package org.atriasoft.etk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * in the dimension class we store the data as the more usefull unit (pixel)
 * but one case need to be dynamic the %, then when requested in % the register the % value
 */
public record DimensionBorderRadius(
		BorderRadius size,
		Distance type) {
	final static Logger LOGGER = LoggerFactory.getLogger(DimensionBorderRadius.class);
	
	public static final DimensionBorderRadius ZERO = new DimensionBorderRadius(BorderRadius.ZERO, Distance.PIXEL);
	private static BorderRadius ratio = new BorderRadius(9999999, 888888, 7777777, 66666666);
	private static BorderRadius invRatio = BorderRadius.ONE;
	private static DimensionBorderRadius windowsSize = new DimensionBorderRadius(BorderRadius.MAX_VALUE,
			Distance.PIXEL);
	
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
	 * basic init
	 */
	static {
		final DimensionBorderRadius conversion = new DimensionBorderRadius(new BorderRadius(72, 72, 72, 72),
				Distance.INCH);
		ratio = conversion.getMillimeter();
		invRatio = new BorderRadius(1.0f / ratio.topLeft(), 1.0f / ratio.topRight(), 1.0f / ratio.bottomRight(),
				1.0f / ratio.bottomLeft());
		windowsSize = new DimensionBorderRadius(new BorderRadius(200, 200, 200, 200), Distance.PIXEL);
	}
	
	/**
	 * set the current Windows size
	 * @param size size of the current windows in pixel.
	 */
	public static void setPixelWindowsSize(final BorderRadius size) {
		windowsSize = new DimensionBorderRadius(size);
	}
	
	/**
	 * Constructor (default :0,0 mode pixel)
	 */
	public DimensionBorderRadius() {
		this(BorderRadius.ZERO, Distance.PIXEL);
	}
	
	/**
	 * Constructor
	 * @param size Requested dimension
	 */
	public DimensionBorderRadius(final float size) {
		this(new BorderRadius(size, size, size, size), Distance.PIXEL);
	}
	
	public DimensionBorderRadius(final BorderRadius size) {
		this(size, Distance.PIXEL);
	}
	
	public DimensionBorderRadius(final BorderRadius size, final Distance type) {
		this.size = size;
		this.type = type;
	}
	
	/**
	 * get the current dimension in requested type
	 * @param type Type of unit requested.
	 * @return dimension requested.
	 */
	public BorderRadius get(final Distance type) {
		return switch (type) {
			case POURCENT -> getPourcent();
			case PIXEL -> getPixel();
			case METER -> getMeter();
			case CENTIMETER -> getCentimeter();
			case MILLIMETER -> getMillimeter();
			case KILOMETER -> getKilometer();
			case INCH -> getInch();
			case FOOT -> getFoot();
			case ELEMENT -> throw new UnsupportedOperationException("Unimplemented case: " + type);
			case EX -> throw new UnsupportedOperationException("Unimplemented case: " + type);
			case PC -> {
				LOGGER.error("Does not support other than Px and % type of dimention : " + type
						+ " automaticly convert with {72,72} pixel/inch");
				yield null;
			}
			case POINT -> throw new UnsupportedOperationException("Unimplemented case: " + type);
			default -> throw new IllegalArgumentException("Unexpected value: " + type);
		};
	}
	
	/**
	 * get the current dimension in Centimeter
	 * @return dimension in Centimeter
	 */
	public BorderRadius getCentimeter() {
		return getMillimeter().multiply(MILLIMETER_TO_CENTIMETER);
	}
	
	/**
	 * get the current dimension in Foot
	 * @return dimension in Foot
	 */
	public BorderRadius getFoot() {
		return getMillimeter().multiply(MILLIMETER_TO_FOOT);
	}
	
	/**
	 * get the current dimension in Inch
	 * @return dimension in Inch
	 */
	public BorderRadius getInch() {
		return getMillimeter().multiply(MILLIMETER_TO_INCH);
	}
	
	/**
	 * get the current dimension in Kilometer
	 * @return dimension in Kilometer
	 */
	public BorderRadius getKilometer() {
		return getMillimeter().multiply(MILLIMETER_TO_KILOMETER);
	}
	
	/**
	 * get the current dimension in Meter
	 * @return dimension in Meter
	 */
	public BorderRadius getMeter() {
		return getMillimeter().multiply(MILLIMETER_TO_METER);
	}
	
	/**
	 * get the current dimension in Millimeter
	 * @return dimension in Millimeter
	 */
	public BorderRadius getMillimeter() {
		return new BorderRadius(getPixel().topLeft() * invRatio.topLeft(), getPixel().topRight() * invRatio.topRight(),
				getPixel().bottomRight() * invRatio.bottomRight(), getPixel().bottomLeft() * invRatio.bottomLeft());
	}
	
	/**
	 * get the current dimension in pixel
	 * @return dimension in Pixel
	 */
	public BorderRadius getPixel() {
		if (this.type != Distance.POURCENT) {
			return this.size;
		}
		return getPixel(windowsSize.getPixel());
	}
	
	public BorderRadius getPixel(final BorderRadius uppersize) {
		if (this.type != Distance.POURCENT) {
			return this.size;
		}
		final BorderRadius res = new BorderRadius(uppersize.topLeft() * this.size.topLeft() * 0.01f,
				uppersize.topRight() * this.size.topRight() * 0.01f,
				uppersize.bottomRight() * this.size.bottomRight() * 0.01f,
				uppersize.bottomLeft() * this.size.bottomLeft() * 0.01f);
		//GALE_DEBUG("Get % : " + m_data + " / " + windDim + " == > " + res);
		return res;
	}
	
	/**
	 * get the current dimension in Pourcent
	 * @return dimension in Pourcent
	 */
	public BorderRadius getPourcent() {
		if (this.type != Distance.POURCENT) {
			final BorderRadius windDim = windowsSize.getPixel();
			//GALE_DEBUG(" windows dimension : " /*+ windowsSize*/ + "  == > " + windDim + "px"); // ==> infinite loop ...
			//printf(" windows dimension : %f,%f", windDim.topLeft(),windDim.topRight());
			//printf(" data : %f,%f", m_data.topLeft(),m_data.topRight());
			return new BorderRadius((this.size.topLeft() / windDim.topLeft()) * 100.0f,
					(this.size.topRight() / windDim.topRight()) * 100.0f,
					(this.size.bottomRight() / windDim.bottomRight()) * 100.0f,
					(this.size.bottomLeft() / windDim.bottomLeft()) * 100.0f);
		}
		return new BorderRadius(this.size.topLeft() * 100.0f, this.size.topRight() * 100.0f,
				this.size.bottomRight() * 100.0f, this.size.bottomLeft() * 100.0f);
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
	public static DimensionBorderRadius valueOf(String config) {
		final Distance type = Distance.parseEndSmallString(config);
		config = type.removeEndString(config);
		if (type == Distance.UNKNOW) {
			LOGGER.error("Can not parse dimension : '" + config + "'");
			return null;
		}
		final BorderRadius tmp = BorderRadius.valueOf(config);
		final DimensionBorderRadius ret = new DimensionBorderRadius(tmp, type);
		return ret;
	}
	
	public DimensionBorderRadius withSize(final BorderRadius size) {
		return new DimensionBorderRadius(size, this.type);
	}
	
	public DimensionBorderRadius withType(final Distance type) {
		return new DimensionBorderRadius(this.size, type);
	}
	
	/**
	 * string cast :
	 */
	@Override
	public String toString() {
		return get(getType()).toString() + getType().toSmallString();
	}
	
	public static DimensionBorderRadius valueOf(
			String contentX,
			final String contentY,
			final String contentZ,
			final String contentW) {
		
		final Distance typeX = Distance.parseEndSmallString(contentX);
		contentX = typeX.removeEndString(contentX);
		final float tmpX = Float.valueOf(contentX);
		
		final Distance typeY = Distance.parseEndSmallString(contentY);
		contentX = typeY.removeEndString(contentY);
		final float tmpY = Float.valueOf(contentY);
		
		final Distance typeZ = Distance.parseEndSmallString(contentZ);
		contentX = typeZ.removeEndString(contentZ);
		final float tmpZ = Float.valueOf(contentZ);

		final Distance typeW = Distance.parseEndSmallString(contentW);
		contentX = typeW.removeEndString(contentZ);
		final float tmpW = Float.valueOf(contentZ);
		
		if (typeX != Distance.UNKNOW) {
			return new DimensionBorderRadius(new BorderRadius(tmpX, tmpY, tmpZ, tmpW), typeX);
		}
		if (typeY != Distance.UNKNOW) {
			return new DimensionBorderRadius(new BorderRadius(tmpX, tmpY, tmpZ, tmpW), typeY);
		}
		if (typeZ != Distance.UNKNOW) {
			return new DimensionBorderRadius(new BorderRadius(tmpX, tmpY, tmpZ, tmpW), typeZ);
		}
		if (typeW != Distance.UNKNOW) {
			return new DimensionBorderRadius(new BorderRadius(tmpX, tmpY, tmpZ, tmpW), typeW);
		}
		return new DimensionBorderRadius(new BorderRadius(tmpX, tmpY, tmpZ, tmpW), Distance.PIXEL);
	}
	
}
