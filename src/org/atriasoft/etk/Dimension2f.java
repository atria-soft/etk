/** @file
 * @author Edouard DUPIN
 * @copyright 2011, Edouard DUPIN, all right reserved
 * @license MPL v2.0 (see license file)
 */
package org.atriasoft.etk;

import org.atriasoft.etk.math.Vector2f;
import org.atriasoft.etk.math.Vector2i;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * in the dimension class we store the data as the more usefull unit (pixel)
 * but one case need to be dynamic the %, then when requested in % the register the % value
 */
public record Dimension2f(
		Vector2f size,
		Distance type) {
	final static Logger LOGGER = LoggerFactory.getLogger(Dimension2f.class);
	
	public static final Dimension2f ZERO = new Dimension2f(Vector2f.ZERO, Distance.PIXEL);
	private static Vector2f ratio = new Vector2f(9999999, 888888);
	private static Vector2f invRatio = Vector2f.ONE;
	private static Dimension2f windowsSize = new Dimension2f(Vector2f.MAX_VALUE, Distance.PIXEL);
	
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
		final Dimension2f conversion = new Dimension2f(new Vector2f(72, 72), Distance.INCH);
		ratio = conversion.getMillimeter();
		invRatio = new Vector2f(1.0f / ratio.x(), 1.0f / ratio.y());
		windowsSize = new Dimension2f(new Vector2f(200, 200), Distance.PIXEL);
	}
	
	public Dimension2f withSize(final Vector2f size) {
		return new Dimension2f(size, this.type);
	}
	
	public Dimension2f withType(final Distance type) {
		return new Dimension2f(this.size, type);
	}
	
	/**
	 * get the Windows diagonal size in the request unit
	 * @param type Unit type requested.
	 * @return the requested size
	 */
	public static float getWindowsDiag(final Distance type) {
		final Vector2f size = getWindowsSize(type);
		return size.length();
	}
	
	/**
	 * get the Windows size in the request unit
	 * @param type Unit type requested.
	 * @return the requested size
	 */
	public static Vector2f getWindowsSize(final Distance type) {
		return windowsSize.get(type);
	}
	
	/**
	 * set the Millimeter ratio for calculation
	 * @param ratio Millimeter ration for the screen calculation interpolation
	 * @param type Unit type requested.
	 * @note: same as @ref setPixelPerInch (internal manage convention)
	 */
	public static void setPixelRatio(final Vector2f ratio, final Distance type) {
		LOGGER.info("Set a new screen ratio for the screen : ratio=" + ratio + " type=" + type);
		final Dimension2f conversion = new Dimension2f(ratio, type);
		LOGGER.info("     == > " + conversion);
		Dimension2f.ratio = conversion.getMillimeter();
		invRatio = new Vector2f(1.0f / Dimension2f.ratio.x(), 1.0f / Dimension2f.ratio.y());
		LOGGER.info("Set a new screen ratio for the screen : ratioMm=" + Dimension2f.ratio);
	}
	
	/**
	 * set the current Windows size
	 * @param size size of the current windows in pixel.
	 */
	public static void setPixelWindowsSize(final Vector2f size) {
		windowsSize = new Dimension2f(size);
	}
	
	/**
	 * Constructor (default :0,0 mode pixel)
	 */
	public Dimension2f() {
		this(Vector2f.ZERO, Distance.PIXEL);
	}
	
	/**
	 * Constructor
	 * @param size Requested dimension
	 */
	public Dimension2f(final Vector2f size) {
		this(size, Distance.PIXEL);
	}
	
	public Dimension2f(final Vector2f size, final Distance type) {
		this.size = size;
		this.type = type;
	}
	
	/**
	 * get the current dimension in requested type
	 * @param type Type of unit requested.
	 * @return dimension requested.
	 */
	public Vector2f get(final Distance type) {
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
				LOGGER.error("Does not support other than Px and % type of dimention : " + type + " automaticly convert with {72,72} pixel/inch");
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
	public Vector2f getCentimeter() {
		return getMillimeter().multiply(MILLIMETER_TO_CENTIMETER);
	}
	
	/**
	 * get the current dimension in Foot
	 * @return dimension in Foot
	 */
	public Vector2f getFoot() {
		return getMillimeter().multiply(MILLIMETER_TO_FOOT);
	}
	
	/**
	 * get the current dimension in Inch
	 * @return dimension in Inch
	 */
	public Vector2f getInch() {
		return getMillimeter().multiply(MILLIMETER_TO_INCH);
	}
	
	/**
	 * get the current dimension in Kilometer
	 * @return dimension in Kilometer
	 */
	public Vector2f getKilometer() {
		return getMillimeter().multiply(MILLIMETER_TO_KILOMETER);
	}
	
	/**
	 * get the current dimension in Meter
	 * @return dimension in Meter
	 */
	public Vector2f getMeter() {
		return getMillimeter().multiply(MILLIMETER_TO_METER);
	}
	
	/**
	 * get the current dimension in Millimeter
	 * @return dimension in Millimeter
	 */
	public Vector2f getMillimeter() {
		return new Vector2f(getPixel().x() * invRatio.x(), getPixel().y() * invRatio.y());
	}
	
	/**
	 * get the current dimension in pixel
	 * @return dimension in Pixel
	 */
	public Vector2f getPixel() {
		if (this.type != Distance.POURCENT) {
			return this.size;
		}
		return getPixel(windowsSize.getPixel());
	}
	
	public Vector2f getPixel(final Vector2f uppersize) {
		if (this.type != Distance.POURCENT) {
			return this.size;
		}
		final Vector2f res = new Vector2f(uppersize.x() * this.size.x() * 0.01f, uppersize.y() * this.size.y() * 0.01f);
		//GALE_DEBUG("Get % : " + m_data + " / " + windDim + " == > " + res);
		return res;
	}
	
	public Vector2i getPixeli() {
		final Vector2f tmpSize = windowsSize.getPixel();
		return getPixeli(new Vector2i((int) tmpSize.x(), (int) tmpSize.y()));
	}
	
	public Vector2i getPixeli(final Vector2i uppersize) {
		if (this.type != Distance.POURCENT) {
			return new Vector2i((int) this.size.x(), (int) this.size.y());
		}
		final Vector2i res = new Vector2i((int) (uppersize.x() * this.size.x() * 0.01f), (int) (uppersize.y() * this.size.y() * 0.01f));
		//GALE_DEBUG("Get % : " + m_data + " / " + windDim + " == > " + res);
		return res;
	}
	
	/**
	 * get the current dimension in Percent
	 * @return dimension in Percent
	 */
	public Vector2f getPourcent() {
		if (this.type != Distance.POURCENT) {
			final Vector2f windDim = windowsSize.getPixel();
			//GALE_DEBUG(" windows dimension : " /*+ windowsSize*/ + "  == > " + windDim + "px"); // ==> infinite loop ...
			//printf(" windows dimension : %f,%f", windDim.x(),windDim.y());
			//printf(" data : %f,%f", m_data.x(),m_data.y());
			return new Vector2f((this.size.x() / windDim.x()) * 100.0f, (this.size.y() / windDim.y()) * 100.0f);
		}
		return new Vector2f(this.size.x() * 100.0f, this.size.y() * 100.0f);
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
	public static Dimension2f valueOf(String config) {
		final Distance type = Distance.parseEndSmallString(config);
		config = type.removeEndString(config);
		if (type == Distance.UNKNOW) {
			LOGGER.error("Can not parse dimension : '" + config + "'");
			return null;
		}
		final Vector2f tmp = Vector2f.valueOf(config);
		final Dimension2f ret = new Dimension2f(tmp, type);
		return ret;
	}
	
	/**
	 * string cast :
	 */
	@Override
	public String toString() {
		return get(getType()).toString() + getType().toSmallString();
	}
	
	public static Dimension2f valueOf(String contentX, final String contentY) {
		final Distance typeX = Distance.parseEndSmallString(contentX);
		contentX = typeX.removeEndString(contentX);
		final float tmpX = Float.valueOf(contentX);
		
		final Distance typeY = Distance.parseEndSmallString(contentY);
		contentX = typeY.removeEndString(contentY);
		final float tmpY = Float.valueOf(contentY);
		
		if (typeX != Distance.UNKNOW) {
			return new Dimension2f(new Vector2f(tmpX, tmpY), typeX);
		}
		if (typeY != Distance.UNKNOW) {
			return new Dimension2f(new Vector2f(tmpX, tmpY), typeY);
		}
		return new Dimension2f(new Vector2f(tmpX, tmpY), Distance.PIXEL);
	}
	
}
