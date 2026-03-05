package org.atriasoft.etk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Global configuration manager for the ETK framework.
 *
 * <p>Provides centralized access to various configuration objects including font configuration.
 * This class follows the singleton pattern with static accessors.</p>
 *
 * @author Edouard DUPIN
 * @since 0.1.0
 */
public class Configs {
	final static Logger LOGGER = LoggerFactory.getLogger(Configs.class);
	private static ConfigFont fonts = new ConfigFont();
	
	/**
	 * Gets the global font configuration.
	 *
	 * @return The ConfigFont instance managing font settings
	 */
	public static ConfigFont getConfigFonts() {
		return fonts;
	}
	
	private Configs() {
		
	}
}
