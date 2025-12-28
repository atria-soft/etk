package org.atriasoft.etk.theme;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.atriasoft.etk.Uri;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Theme {
	final static Logger LOGGER = LoggerFactory.getLogger(Theme.class);
	private static Map<String, Path> globalListTheme = new HashMap<>();
	private static Map<String, Path> globalListThemeDefault = new HashMap<>();
	
	/**
	 * get the folder from a Reference theme
	 * @param refName Theme cathegorie ex : "GUI" "SHADER" "DEFAULT" 
	 * @return the path of the theme
	 */
	public static Path getName(final String refName) {
		return globalListTheme.get(refName);
	}
	
	/**
	 * get the default folder from a Reference theme 
	 * @param refName Theme cathegorie ex : "GUI" "SHADER" "DEFAULT" 
	 * @return the path of the theme
	 */
	public static Path getNameDefault(final String refName) {
		return globalListThemeDefault.get(refName);
	}
	
	/**
	 * initialize the theme system
	 */
	public static void init() {
		
	};
	
	/**
	 * Get the list of all the theme folder availlable in the user Home/appl
	 * @return The list of elements
	 */
	public static Set<String> list() {
		return globalListTheme.keySet();
	};
	
	/**
	 * Set the Folder of a subset of a theme ...
	 * @param refName Theme cathegorie ex : "GUI" "SHADER" "DEFAULT" 
	 * @param folderName The associated folder of the Theme (like "myTheme/folder/folder2/")
	 */
	public static void setName(final String refName, final Path folderName) {
		LOGGER.debug("Change theme : '{}' : '{}'", refName, folderName);
		globalListTheme.put(refName, folderName);
		updateProvider(refName);
	}
	
	/**
	 * Set the default folder of a subset of a theme ...
	 * @param _refName Theme cathegorie ex : "GUI" "SHADER" "DEFAULT" 
	 * @param _folderName The associated default folder of the Theme (like "myTheme/color/default/")
	 */
	public static void setNameDefault(final String refName, final Path folderName) {
		globalListThemeDefault.put(refName, folderName);
		updateProvider(refName);
	}
	
	/**
	 * un-initialize the theme system
	 */
	public static void unInit() {
		globalListTheme.clear();
		globalListThemeDefault.clear();
	}
	
	public static void updateProvider(final String refName) {
		final Path base = getName(refName);
		final Path baseDefault = getNameDefault(refName);
		if (base == null) {
			//etk::uri::provider::add("THEME_" + refName, new ProviderTheme(new Path("theme") / baseDefault, Path("theme") / base));
		} else {
			//etk::uri::provider::add("THEME_" + refName, new ProviderTheme(Path("theme") / base, Path("theme") / baseDefault));
		}
	}
	
	private Theme() {};
	
}
