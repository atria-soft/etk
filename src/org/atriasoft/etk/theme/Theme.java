package org.atriasoft.etk.theme;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.atriasoft.etk.internal.Log;

public class Theme {
	private static Map<String, Path> g_listTheme = new HashMap<>();
	private static Map<String, Path> g_listThemeDefault = new HashMap<>();
	
	/**
	 * @brief get the folder from a Reference theme
	 * @param[in] _refName Theme cathegorie ex : "GUI" "SHADER" "DEFAULT" 
	 * @return the path of the theme
	 */
	public static Path getName(final String _refName) {
		return g_listTheme.get(_refName);
	}
	
	/**
	 * @brief get the default folder from a Reference theme 
	 * @param[in] _refName Theme cathegorie ex : "GUI" "SHADER" "DEFAULT" 
	 * @return the path of the theme
	 */
	public static Path getNameDefault(final String _refName) {
		return g_listThemeDefault.get(_refName);
	};
	
	/**
	 * @brief initialize the theme system
	 */
	public static void init() {
		
	};
	
	/**
	 * @brief Get the list of all the theme folder availlable in the user Home/appl
	 * @return The list of elements
	 */
	public static Set<String> list() {
		return g_listTheme.keySet();
	}
	
	/**
	 * @brief Set the Folder of a subset of a theme ...
	 * @param[in] _refName Theme cathegorie ex : "GUI" "SHADER" "DEFAULT" 
	 * @param[in] _folderName The associated folder of the Theme (like "myTheme/folder/folder2/")
	 */
	public static void setName(final String _refName, final Path _folderName) {
		Log.warning("Change theme : '" + _refName + "' : '" + _folderName + "'");
		g_listTheme.put(_refName, _folderName);
		updateProvider(_refName);
	}
	
	/**
	 * @brief Set the default folder of a subset of a theme ...
	 * @param[in] _refName Theme cathegorie ex : "GUI" "SHADER" "DEFAULT" 
	 * @param[in] _folderName The associated default folder of the Theme (like "myTheme/color/default/")
	 */
	public static void setNameDefault(final String _refName, final Path _folderName) {
		g_listThemeDefault.put(_refName, _folderName);
		updateProvider(_refName);
	}
	
	/**
	 * @brief un-initialize the theme system
	 */
	public static void unInit() {
		g_listTheme.clear();
		g_listThemeDefault.clear();
	}
	
	public static void updateProvider(final String _refName) {
		final Path base = getName(_refName);
		final Path baseDefault = getNameDefault(_refName);
		if (base == null) {
			//etk::uri::provider::add("THEME_" + _refName, new ProviderTheme(new Path("theme") / baseDefault, Path("theme") / base));
		} else {
			//etk::uri::provider::add("THEME_" + _refName, new ProviderTheme(Path("theme") / base, Path("theme") / baseDefault));
		}
	};
	
}
