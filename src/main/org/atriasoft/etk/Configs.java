package org.atriasoft.etk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Configs {
	final static Logger LOGGER = LoggerFactory.getLogger(Configs.class);
	private static ConfigFont fonts = new ConfigFont();
	
	public static ConfigFont getConfigFonts() {
		return fonts;
	}
	
	private Configs() {
		
	}
}
