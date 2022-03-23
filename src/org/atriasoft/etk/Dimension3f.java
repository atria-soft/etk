/** @file
 * @author Edouard DUPIN
 * @copyright 2011, Edouard DUPIN, all right reserved
 * @license MPL v2.0 (see license file)
 */
package org.atriasoft.etk;

import org.atriasoft.etk.internal.Log;
import org.atriasoft.etk.math.Vector2i;
import org.atriasoft.etk.math.Vector3f;

/**
 * in the dimension class we store the data as the more usefull unit (pixel)
 * but one case need to be dynamic the %, then when requested in % the register the % value
 */
public record Dimension3f(
		Vector3f size,
		Distance type) {
	
	public static final Dimension3f ZERO = new Dimension3f(Vector3f.ZERO, Distance.PIXEL);
	private static Vector3f ratio = new Vector3f(9999999, 888888, 7777777);
	private static Vector3f invRatio = Vector3f.ONE;
	private static Dimension3f windowsSize = new Dimension3f(Vector3f.MAX_VALUE, Distance.PIXEL);
	
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
		final Dimension3f conversion = new Dimension3f(new Vector3f(72, 72, 72), Distance.INCH);
		ratio = conversion.getMillimeter();
		invRatio = new Vector3f(1.0f / ratio.x(), 1.0f / ratio.y(), 1.0f / ratio.z());
		windowsSize = new Dimension3f(new Vector3f(200, 200, 200), Distance.PIXEL);
	}
	
	/**
	 * get the Windows diagonal size in the request unit
	 * @param type Unit type requested.
	 * @return the requested size
	 */
	public static float getWindowsDiag(final Distance type) {
		final Vector3f size = getWindowsSize(type);
		return size.length();
	}
	
	/**
	 * get the Windows size in the request unit
	 * @param type Unit type requested.
	 * @return the requested size
	 */
	public static Vector3f getWindowsSize(final Distance type) {
		return windowsSize.get(type);
	}
	
	/**
	 * set the Milimeter ratio for calculation
	 * @param ratio Milimeter ration for the screen calculation interpolation
	 * @param type Unit type requested.
	 * @note: same as @ref setPixelPerInch (internal manage convertion)
	 */
	public static void setPixelRatio(final Vector3f ratio, final Distance type) {
		Log.info("Set a new screen ratio for the screen : ratio=" + ratio + " type=" + type);
		final Dimension3f conversion = new Dimension3f(ratio, type);
		Log.info("     == > " + conversion);
		Dimension3f.ratio = conversion.getMillimeter();
		invRatio = new Vector3f(1.0f / Dimension3f.ratio.x(), 1.0f / Dimension3f.ratio.y(), 1.0f / Dimension3f.ratio.z());
		Log.info("Set a new screen ratio for the screen : ratioMm=" + Dimension3f.ratio);
	}
	
	/**
	 * set the current Windows size
	 * @param size size of the current windows in pixel.
	 */
	public static void setPixelWindowsSize(final Vector3f size) {
		windowsSize = new Dimension3f(size);
		Log.verbose("Set a new Windows property size " + windowsSize + "px");
	}
	
	/**
	 * Constructor (default :0,0 mode pixel)
	 */
	public Dimension3f() {
		this(Vector3f.ZERO, Distance.PIXEL);
	}
	
	/**
	 * Constructor
	 * @param size Requested dimension
	 */
	public Dimension3f(final Vector3f size) {
		this(size, Distance.PIXEL);
	}
	
	public Dimension3f(final Vector3f size, final Distance type) {
		this.size = size;
		this.type = type;
	}
	
	/**
	 * get the current dimension in requested type
	 * @param type Type of unit requested.
	 * @return dimension requested.
	 */
	public Vector3f get(final Distance type) {
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
				Log.error("Does not support other than Px and % type of dimention : " + type + " automaticly convert with {72,72} pixel/inch");
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
	public Vector3f getCentimeter() {
		return getMillimeter().multiply(MILLIMETER_TO_CENTIMETER);
	}
	
	/**
	 * get the current dimension in Foot
	 * @return dimension in Foot
	 */
	public Vector3f getFoot() {
		return getMillimeter().multiply(MILLIMETER_TO_FOOT);
	}
	
	/**
	 * get the current dimension in Inch
	 * @return dimension in Inch
	 */
	public Vector3f getInch() {
		return getMillimeter().multiply(MILLIMETER_TO_INCH);
	}
	
	/**
	 * get the current dimension in Kilometer
	 * @return dimension in Kilometer
	 */
	public Vector3f getKilometer() {
		return getMillimeter().multiply(MILLIMETER_TO_KILOMETER);
	}
	
	/**
	 * get the current dimension in Meter
	 * @return dimension in Meter
	 */
	public Vector3f getMeter() {
		return getMillimeter().multiply(MILLIMETER_TO_METER);
	}
	
	/**
	 * get the current dimension in Millimeter
	 * @return dimension in Millimeter
	 */
	public Vector3f getMillimeter() {
		return new Vector3f(getPixel().x() * invRatio.x(), getPixel().y() * invRatio.y(), getPixel().z() * invRatio.z());
	}
	
	/**
	 * get the current dimension in pixel
	 * @return dimension in Pixel
	 */
	public Vector3f getPixel() {
		if (this.type != Distance.POURCENT) {
			return this.size;
		}
		return getPixel(windowsSize.getPixel());
	}
	
	public Vector3f getPixel(final Vector3f uppersize) {
		if (this.type != Distance.POURCENT) {
			return this.size;
		}
		final Vector3f res = new Vector3f(uppersize.x() * this.size.x() * 0.01f, uppersize.y() * this.size.y() * 0.01f, uppersize.z() * this.size.z() * 0.01f);
		//GALE_DEBUG("Get % : " + m_data + " / " + windDim + " == > " + res);
		return res;
	}
	
	public Vector2i getPixeli() {
		Vector3f tmpSize = windowsSize.getPixel();
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
	 * get the current dimension in Pourcent
	 * @return dimension in Pourcent
	 */
	public Vector3f getPourcent() {
		if (this.type != Distance.POURCENT) {
			final Vector3f windDim = windowsSize.getPixel();
			//GALE_DEBUG(" windows dimension : " /*+ windowsSize*/ + "  == > " + windDim + "px"); // ==> infinite loop ...
			//printf(" windows dimension : %f,%f", windDim.x(),windDim.y());
			//printf(" data : %f,%f", m_data.x(),m_data.y());
			return new Vector3f((this.size.x() / windDim.x()) * 100.0f, (this.size.y() / windDim.y()) * 100.0f, (this.size.z() / windDim.z()) * 100.0f);
		}
		return new Vector3f(this.size.x() * 100.0f, this.size.y() * 100.0f, this.size.z() * 100.0f);
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
	public static Dimension3f valueOf(String config) {
		Distance type = Distance.parseEndSmallString(config);
		config = type.removeEndString(config);
		if (type == Distance.UNKNOW) {
			Log.critical("Can not parse dimension : '" + config + "'");
			return null;
		}
		final Vector3f tmp = Vector3f.valueOf(config);
		final Dimension3f ret = new Dimension3f(tmp, type);
		Log.verbose(" config dimension : '" + config + "'  == > " + ret.toString());
		return ret;
	}
	
	/**
	 * string cast :
	 */
	@Override
	public String toString() {
		return get(getType()).toString() + getType().toSmallString();
	}
	
	public static Dimension3f valueOf(String contentX, String contentY, String contentZ) {
		
		Distance typeX = Distance.parseEndSmallString(contentX);
		contentX = typeX.removeEndString(contentX);
		float tmpX = Float.valueOf(contentX);
		
		Distance typeY = Distance.parseEndSmallString(contentY);
		contentX = typeY.removeEndString(contentY);
		float tmpY = Float.valueOf(contentY);
		
		Distance typeZ = Distance.parseEndSmallString(contentZ);
		contentX = typeZ.removeEndString(contentZ);
		float tmpZ = Float.valueOf(contentZ);
		
		if (typeX != Distance.UNKNOW) {
			return new Dimension3f(new Vector3f(tmpX, tmpY, tmpZ), typeX);
		}
		if (typeY != Distance.UNKNOW) {
			return new Dimension3f(new Vector3f(tmpX, tmpY, tmpZ), typeY);
		}
		if (typeZ != Distance.UNKNOW) {
			return new Dimension3f(new Vector3f(tmpX, tmpY, tmpZ), typeZ);
		}
		return new Dimension3f(new Vector3f(tmpX, tmpY, tmpZ), Distance.PIXEL);
	}
	
}
