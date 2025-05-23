package org.atriasoft.etk;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** @file
 * @author Edouard DUPIN
 * @copyright 2011, Edouard DUPIN, all right reserved
 * @license MPL v2.0 (see license file)
 */

public class ConfigFont {
	final static Logger LOGGER = LoggerFactory.getLogger(ConfigFont.class);
	private final Map<String, Uri> fonts = new HashMap<>();
	private String name = "FreeSans";
	private int size = 12;
	
	public void add(final String string, final Uri uri) {
		this.fonts.put(string, uri);
	}
	
	public Uri getFontUri(final String fontName) {
		Uri out = this.fonts.get(fontName);
		if (out == null) {
			LOGGER.warn(" try to get unexistant font : " + fontName);
		}
		return out;
	}
	
	/**
	 * get the current default font name
	 * @return a reference on the font name string
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * get the default font size.
	 * @return the font size.
	 */
	public int getSize() {
		return this.size;
	}
	
	/**
	 * set the defaut font for all the widgets and basics display.
	 * @param fontName The font name requested (not case sensitive) ex "Arial" or multiple separate by ';' ex : "Arial;Helvetica".
	 * @param size The default size of the font default=10.
	 */
	public void set(final String fontName, final int size) {
		this.name = fontName;
		this.size = size;
		LOGGER.trace("Set default Font : '" + this.name + "' size=" + this.size);
	}
	
	/**
	 * Set the current default font name
	 * @param fontName The font name requested (not case sensitive) ex "Arial" or multiple separate by ';' ex : "Arial;Helvetica".
	 */
	public void setName(final String fontName) {
		this.name = fontName;
		LOGGER.trace("Set default Font : '" + this.name + "' size=" + this.size + " (change name only)");
	}
	
	/**
	 * Set the default font size.
	 * @param size new font size.
	 */
	public void setSize(final int size) {
		this.size = size;
		LOGGER.trace("Set default Font : '" + this.name + "' size=" + this.size + " (change size only)");
	}
}
