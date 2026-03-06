/** @file
 * @author Edouard DUPIN
 * @copyright 2011, Edouard DUPIN, all right reserved
 * @license MPL v2.0 (see license file)
 */
package org.atriasoft.etk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * in the dimension class we store the data as the more usefull unit (pixel) but
 * one case need to be dynamic the %, then when requested in % the register the
 * % value
 */
public record Dimension1f(
        float size,
        Distance type) {

    final static Logger LOGGER = LoggerFactory.getLogger(Dimension1f.class);

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
     *
     * @param size Requested dimension
     */
    public Dimension1f(final float size) {
        this(size, Distance.PIXEL);
    }

    public Dimension1f withSize(final float size) {
        return new Dimension1f(size, this.type);
    }

    public Dimension1f withType(final Distance type) {
        return new Dimension1f(this.size, type);
    }

    /**
     * get the current dimension in pixel
     *
     * @return dimension in Pixel
     */
    public float getPixel(final float upperSize) {
        return switch (this.type) {
            case POURCENT ->
                upperSize * this.size * 0.01f;
            case PIXEL ->
                this.size;
            case METER ->
                this.size * METER_TO_MILLIMETER * BASIC_RATIO;
            case CENTIMETER ->
                this.size * CENTIMETER_TO_MILLIMETER * BASIC_RATIO;
            case MILLIMETER ->
                this.size * BASIC_RATIO;
            case KILOMETER ->
                this.size * KILOMETER_TO_MILLIMETER * BASIC_RATIO;
            case INCH ->
                this.size * INCH_TO_MILLIMETER * BASIC_RATIO;
            case FOOT ->
                this.size * FOOT_TO_MILLIMETER * BASIC_RATIO;
            default ->
                128.0f;
        };
    }

    /**
     * get the dimension type
     *
     * @return the type
     */
    public Distance getType() {
        return this.type;
    }

    /**
     * set the current dimension in requested type
     *
     * @param config dimension configuration.
     */
    public static Dimension1f valueOf(String config) {
        Distance type = Distance.parseEndSmallString(config);
        config = type.removeEndString(config);
        if (type == Distance.UNKNOW) {
            LOGGER.error("FATAL: Can not parse dimension : '{}' CAn not deterùmine extention ... px, cm, ...", config);
            type = Distance.PIXEL;
        }
        final float tmp = Float.parseFloat(config);
        return new Dimension1f(tmp, type);
    }

    /**
     * string cast :
     */
    @Override
    public String toString() {
        return Float.toString(this.size) + getType().toSmallString();
    }

}
