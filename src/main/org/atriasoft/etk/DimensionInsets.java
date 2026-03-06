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
public record DimensionInsets(
		Insets size,
		Distance type) {
	final static Logger LOGGER = LoggerFactory.getLogger(DimensionInsets.class);

	public static final DimensionInsets ZERO = new DimensionInsets(Insets.ZERO, Distance.PIXEL);
	private static Insets ratio = new Insets(9999999, 888888, 7777777, 66666666);
	private static Insets invRatio = Insets.ONE;
	private static DimensionInsets windowsSize = new DimensionInsets(Insets.MAX_VALUE, Distance.PIXEL);

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
		final DimensionInsets conversion = new DimensionInsets(new Insets(72, 72, 72, 72), Distance.INCH);
		ratio = conversion.getMillimeter();
		invRatio = new Insets(1.0f / ratio.top(), 1.0f / ratio.right(), 1.0f / ratio.bottom(), 1.0f / ratio.left());
		windowsSize = new DimensionInsets(new Insets(200, 200, 200, 200), Distance.PIXEL);
	}

	/**
	 * set the current Windows size
	 * @param size size of the current windows in pixel.
	 */
	public static void setPixelWindowsSize(final Insets size) {
		windowsSize = new DimensionInsets(size);
	}
	
	/**
	 * Constructor (default :0,0 mode pixel)
	 */
	public DimensionInsets() {
		this(Insets.ZERO, Distance.PIXEL);
	}

	/**
	 * Constructor
	 * @param size Requested dimension
	 */
	public DimensionInsets(final float size) {
		this(new Insets(size, size, size, size), Distance.PIXEL);
	}
	
	/**
	 * Constructor
	 * @param size Requested dimension
	 */
	public DimensionInsets(final float top, final float right, final float bottom, final float left) {
		this(new Insets(top, right, bottom, left), Distance.PIXEL);
	}

	public DimensionInsets(final float yyy, final float xxx) {
		this(new Insets(yyy, xxx, yyy, xxx), Distance.PIXEL);
	}

	public DimensionInsets(final Insets size) {
		this(size, Distance.PIXEL);
	}

	public DimensionInsets(final Insets size, final Distance type) {
		this.size = size;
		this.type = type;
	}

	/**
	 * get the current dimension in requested type
	 * @param type Type of unit requested.
	 * @return dimension requested.
	 */
	public Insets get(final Distance type) {
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
				LOGGER.warn("Does not support other than Px and % type of dimention : {} automaticly convert with {{72,72}} pixel/inch", type);
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
	public Insets getCentimeter() {
		return getMillimeter().multiply(MILLIMETER_TO_CENTIMETER);
	}

	/**
	 * get the current dimension in Foot
	 * @return dimension in Foot
	 */
	public Insets getFoot() {
		return getMillimeter().multiply(MILLIMETER_TO_FOOT);
	}

	/**
	 * get the current dimension in Inch
	 * @return dimension in Inch
	 */
	public Insets getInch() {
		return getMillimeter().multiply(MILLIMETER_TO_INCH);
	}

	/**
	 * get the current dimension in Kilometer
	 * @return dimension in Kilometer
	 */
	public Insets getKilometer() {
		return getMillimeter().multiply(MILLIMETER_TO_KILOMETER);
	}

	/**
	 * get the current dimension in Meter
	 * @return dimension in Meter
	 */
	public Insets getMeter() {
		return getMillimeter().multiply(MILLIMETER_TO_METER);
	}

	/**
	 * get the current dimension in Millimeter
	 * @return dimension in Millimeter
	 */
	public Insets getMillimeter() {
		return new Insets(getPixel().top() * invRatio.top(), getPixel().right() * invRatio.right(),
				getPixel().bottom() * invRatio.bottom(), getPixel().left() * invRatio.left());
	}

	/**
	 * get the current dimension in pixel
	 * @return dimension in Pixel
	 */
	public Insets getPixel() {
		if (this.type != Distance.POURCENT) {
			return this.size;
		}
		return getPixel(windowsSize.getPixel());
	}

	public Insets getPixel(final Insets uppersize) {
		if (this.type != Distance.POURCENT) {
			return this.size;
		}
		final Insets res = new Insets(uppersize.top() * this.size.top() * 0.01f,
				uppersize.right() * this.size.right() * 0.01f, uppersize.bottom() * this.size.bottom() * 0.01f,
				uppersize.left() * this.size.left() * 0.01f);
		//GALE_DEBUG("Get % : " + m_data + " / " + windDim + " == > " + res);
		return res;
	}
	
	/**
	 * get the current dimension in Pourcent
	 * @return dimension in Pourcent
	 */
	public Insets getPourcent() {
		if (this.type != Distance.POURCENT) {
			final Insets windDim = windowsSize.getPixel();
			return new Insets(//
					(this.size.top() / windDim.top()) * 100.0f, //
					(this.size.right() / windDim.right()) * 100.0f, //
					(this.size.bottom() / windDim.bottom()) * 100.0f, //
					(this.size.left() / windDim.left()) * 100.0f);
		}
		return new Insets(//
				this.size.top() * 100.0f, //
				this.size.right() * 100.0f, //
				this.size.bottom() * 100.0f, //
				this.size.left() * 100.0f);
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
	public static DimensionInsets valueOf(String config) {
		final Distance type = Distance.parseEndSmallString(config);
		config = type.removeEndString(config);
		if (type == Distance.UNKNOW) {
			LOGGER.warn("Can not parse dimension : '{}'", config);
			return null;
		}
		final Insets tmp = Insets.valueOf(config);
		final DimensionInsets ret = new DimensionInsets(tmp, type);
		return ret;
	}

	public DimensionInsets withSize(final Insets size) {
		return new DimensionInsets(size, this.type);
	}

	public DimensionInsets withType(final Distance type) {
		return new DimensionInsets(this.size, type);
	}

	/**
	 * string cast :
	 */
	@Override
	public String toString() {
		return get(getType()).toString() + getType().toSmallString();
	}

	public static DimensionInsets valueOf(
			String contentX,
			final String contentY,
			final String contentZ,
			final String contentW) {

		final Distance typeX = Distance.parseEndSmallString(contentX);
		contentX = typeX.removeEndString(contentX);
		final float tmpX = Float.parseFloat(contentX);

		final Distance typeY = Distance.parseEndSmallString(contentX);
		contentX = typeY.removeEndString(contentY);
		final float tmpY = Float.parseFloat(contentY);
		
		final Distance typeZ = Distance.parseEndSmallString(contentX);
		contentX = typeZ.removeEndString(contentZ);
		final float tmpZ = Float.parseFloat(contentZ);

		final Distance typeW = Distance.parseEndSmallString(contentX);
		contentX = typeW.removeEndString(contentW);
		final float tmpW = Float.parseFloat(contentW);

		if (typeX != Distance.UNKNOW) {
			return new DimensionInsets(new Insets(tmpX, tmpY, tmpZ, tmpW), typeX);
		}
		if (typeY != Distance.UNKNOW) {
			return new DimensionInsets(new Insets(tmpX, tmpY, tmpZ, tmpW), typeY);
		}
		if (typeZ != Distance.UNKNOW) {
			return new DimensionInsets(new Insets(tmpX, tmpY, tmpZ, tmpW), typeZ);
		}
		if (typeW != Distance.UNKNOW) {
			return new DimensionInsets(new Insets(tmpX, tmpY, tmpZ, tmpW), typeW);
		}
		return new DimensionInsets(new Insets(tmpX, tmpY, tmpZ, tmpW), Distance.PIXEL);
	}

}
