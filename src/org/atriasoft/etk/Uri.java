package org.atriasoft.etk;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Uri {
	final static Logger LOGGER = LoggerFactory.getLogger(Uri.class);
	
	private record LibraryElement(
			Class<?> klass,
			String basePath) {}

	private static Map<String, String> genericMap = new HashMap<>();
	private static Map<String, LibraryElement> libraries = new HashMap<>();
	private static Class<?> applicationClass = null;
	private static String applicationBasePath = "";

	static {
		Uri.genericMap.put("DATA", "data/");
		Uri.genericMap.put("THEME", "theme/");
		Uri.genericMap.put("FONTS", "fonts/");
		Uri.genericMap.put("TRANSLATE", "translate/");
	}

	public static void addLibrary(final String libName, final Class<?> classHandle, String basePath) {
		LOGGER.trace("Add library reference: lib={} ==> {} base path={}", libName, classHandle.getCanonicalName(),
				basePath);
		if (basePath == null || basePath.isEmpty()) {
			basePath = "/";
		}
		if (basePath.charAt(basePath.length() - 1) != '/') {
			basePath += "/";
		}
		if (basePath.charAt(0) != '/') {
			basePath = "/" + basePath;
		}
		Uri.libraries.put(libName.toLowerCase(), new LibraryElement(classHandle, basePath));
	}

	public static byte[] getAllData(final Uri resourceName) {
		final InputStream out = Uri.getStream(resourceName);
		if (out == null) {
			return null;
		}
		byte[] data = null;
		try {
			data = out.readAllBytes();
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	private static List<String> getResourceFiles(final Class<?> clazz, final String path) throws IOException {
		final List<String> filenames = new ArrayList<>();

		try (InputStream in = clazz.getResourceAsStream(path);
				BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
			String resource;
			while ((resource = br.readLine()) != null) {
				filenames.add(resource);
			}
		}
		return filenames;
	}

	public static InputStream getStream(final Uri uri) {
		LOGGER.trace("????????????????????????????????????????????");
		LOGGER.trace("Load resource: {}", uri);
		String offsetGroup = "";
		if (uri.group != null) {
			if (uri.group.equals("FILE")) {
				LOGGER.trace("Load resource direct file: {}", uri);
				try {
					return new FileInputStream(new File(uri.getPath()));
				} catch (final FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
			}
			LOGGER.trace("    find group: {}", uri.group);
			final String ret = Uri.genericMap.get(uri.group);
			if (ret != null) {
				LOGGER.trace("        ==> {}", ret);
				offsetGroup = ret;
			}
		}
		InputStream out = null;
		if (Uri.applicationClass == null) {
			LOGGER.trace("    !! Application data class is not defined ...");
		} else {
			String tmpPath = "/" + Uri.applicationBasePath + offsetGroup + uri.path;
			tmpPath = tmpPath.replace("///", "/").replace("//", "/").replaceFirst("^/*", "");
			LOGGER.trace("(appl) Try to load '{}' in {}", tmpPath, Uri.applicationClass.getCanonicalName());
			final URL realFileName = Uri.applicationClass.getClassLoader().getResource(tmpPath);
			if (realFileName != null) {
				LOGGER.trace("(appl)    >>> {}", realFileName.getFile());
			} else {
				LOGGER.trace("(appl)    ??? base folder: {}",
						Uri.applicationClass.getProtectionDomain().getCodeSource().getLocation().getPath() + tmpPath);
			}
			LOGGER.trace("(appl)    {} getResourceAsStream({})", Uri.applicationClass.getCanonicalName(), tmpPath);
			
			out = Uri.applicationClass.getResourceAsStream("/" + tmpPath);

			if (out == null) {
				LOGGER.error("(appl) ==> element does not exist ... {}", uri);
				/*
				try {
					LOGGER.warn("elements: " + getResourceFiles(applicationClass,
							BASE_RESOURCE_FOLDER + applicationBasePath + offsetGroup + "/*.*"));
				} catch (final IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				*/
			}
		}
		if (out == null) {
			// search in the libraries ...
			if (uri.properties.get("lib") == null) {
				LOGGER.trace("    !! No library specified");
				return null;
			}
			final LibraryElement libraryElement = Uri.libraries.get(uri.properties.get("lib"));
			if (libraryElement == null) {
				LOGGER.trace("     Can not get element in library");
				return null;
			}
			//				try {
			//					LOGGER.warn("elements: " + getResourceFiles(libraryElement.klass, libraryElement.basePath + offsetGroup + "/"));
			//				} catch (IOException e) {
			//					// TODO Auto-generated catch block
			//					e.printStackTrace();
			//				}
			String tmpPath = "/" + libraryElement.basePath + offsetGroup + uri.path;
			tmpPath = tmpPath.replace("///", "/").replace("//", "/").replaceFirst("^/*", "");
			;
			LOGGER.trace("(lib)  Try to load '{}' in {}", tmpPath, libraryElement.klass.getCanonicalName());
			final URL realFileName = libraryElement.klass.getClassLoader().getResource(tmpPath);
			if (realFileName != null) {
				LOGGER.trace("(lib)     >>> {}", realFileName.getFile());
			} else {
				LOGGER.trace("(lib)     ??? base folder: {}",
						libraryElement.klass.getProtectionDomain().getCodeSource().getLocation().getPath() + tmpPath);
			}
			out = libraryElement.klass.getResourceAsStream("/" + tmpPath);
			if (out == null) {
				LOGGER.trace("(lib)  ==> element does not exist ...");
			}
		}

		if (out == null) {
			LOGGER.error("Can not load resource: '" + uri + "'");
		} else {
			LOGGER.trace("   =====> DATA LOADED <====== ");
		}
		return out;
	}

	public static List<Uri> listRecursive(final Uri uri) {
		final List<Uri> out = new ArrayList<>();
		LOGGER.error("TODO: not implemented function ...");
		return out;
	}

	public static void setApplication(final Class<?> classHandle) {
		Uri.setApplication(classHandle, "");
	}

	public static void setApplication(final Class<?> classHandle, String basePath) {
		LOGGER.info("Set application reference : {}  base path={}", classHandle.getCanonicalName(), basePath);
		Uri.applicationClass = classHandle;
		if (basePath == null || basePath.isEmpty()) {
			basePath = "/";
		}
		if (basePath.charAt(basePath.length() - 1) != '/') {
			basePath += "/";
		}
		if (basePath.charAt(0) != '/') {
			basePath = "/" + basePath;
		}
		Uri.applicationBasePath = basePath;
	}

	public static void setGroup(final String groupName, String basePath) {
		LOGGER.info("Set Group : {}  base path={}", groupName, basePath);
		if (basePath == null || basePath.isEmpty()) {
			basePath = "/";
		}
		if (basePath.charAt(basePath.length() - 1) != '/') {
			basePath += "/";
		}
		Uri.genericMap.put(groupName.toUpperCase(), basePath);
	}

	public static Uri valueOf(String value) {
		String group = null;
		String path = null;
		final Map<String, String> prop = new HashMap<>();
		if (value.contains(":")) {
			final String[] ret = value.split(":", 2);
			group = ret[0].toUpperCase();
			value = ret[1];
		} else {
			group = "DATA";
		}
		final int index = value.indexOf('?');
		if (index != -1) {
			final String valuesMetadata = value.substring(index + 1);
			final String[] elementMetaData = valuesMetadata.split("&");
			for (final String element : elementMetaData) {
				final String[] keyVal = element.split("=", 2);
				if (keyVal.length == 1) {
					prop.put(keyVal[0], "");
				} else if (keyVal[0].equals("lib")) {
					prop.put(keyVal[0], keyVal[1].toLowerCase());
				} else {
					prop.put(keyVal[0], keyVal[1]);
				}
			}
			path = value.substring(0, index);
		} else {
			path = value;
		}
		return new Uri(group, path, prop);
	}

	public static void writeAll(final Uri uri, final String data) throws IOException {
		BufferedWriter out = null;
		try {
			final FileWriter fstream = new FileWriter(uri.getPath(), false); //true tells to append data.
			out = new BufferedWriter(fstream);
			out.write(data);
		} catch (final IOException e) {
			LOGGER.error("Error: {}", e.getMessage());
			throw e;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (final IOException e) {
					// TODO Auto-generated catch block
					LOGGER.error("Error: ", e);
					throw e;
				}
			}
		}
	}

	public static void writeAllAppend(final Uri uri, final String data) {
		BufferedWriter out = null;
		try {
			final FileWriter fstream = new FileWriter(uri.getPath(), true); //true tells to append data.
			out = new BufferedWriter(fstream);
			out.write(data);
		} catch (final IOException e) {
			LOGGER.error("Error: " + e.getMessage());
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (final IOException e) {
					// TODO Auto-generated catch block
					LOGGER.error("Error: ", e);
				}
			}
		}
	}

	private final String group;

	private final String path;

	private final Map<String, String> properties;

	public Uri(final String path) {
		this(null, path);
	}

	public Uri(final String group, final String path) {
		if (group == null) {
			this.group = null;
		} else {
			this.group = group.toUpperCase();
		}
		this.path = path;
		this.properties = new HashMap<>();
	}

	public Uri(final String group, final String path, final Map<String, String> properties) {
		if (group == null) {
			this.group = null;
		} else {
			this.group = group.toUpperCase();
		}
		this.path = path;
		this.properties = new HashMap<>(properties);
	}

	public Uri(final String group, final String path, final String lib) {
		this(group, path);
		if (lib != null) {
			this.properties.put("lib", lib.toLowerCase());
		}
	}

	@Override
	public Uri clone() {
		return new Uri(this.group, this.path, new HashMap<>(this.properties));
	}

	public boolean exist() {
		final InputStream stream = Uri.getStream(this);
		if (stream == null) {
			return false;
		}
		try {
			stream.close();
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public String get() {
		return getPath();
	}

	public String getExtention() {
		final String[] ret = this.path.split("\\.");
		return ret[ret.length - 1];
	}

	/**
	 * Get the filename of the URI with the extension "plop.txt"
	 * @return simple filename
	 */
	public String getFileName() {
		return this.path.substring(this.path.lastIndexOf("/") + 1);
	}

	/**
	 * Get the filename of the URI without the extension "plop"
	 * @return simple filename
	 */
	public String getFileNameNoExt() {
		final String ext = getExtention();
		return this.path.substring(this.path.lastIndexOf("/") + 1, this.path.length() - ext.length() + 1);
	}

	public String getGroup() {
		return this.group;
	}

	public Uri getParent() {
		final String path = this.path.substring(0, this.path.lastIndexOf("/"));
		return new Uri(getGroup(), path, this.properties);
	}

	public String getPath() {
		return this.path;
	}

	public Map<String, String> getproperties() {
		return this.properties;
	}

	public String getProperty(final String key) {
		return this.properties.get(key);
	}

	public String getValue() {
		return toString();
	}

	public boolean hasProperty(final String key) {
		return this.properties.containsKey(key);
	}

	public boolean isEmpty() {
		return this.path == null || this.path.isEmpty();
	}

	public Uri pathAdd(final String value) {
		if (this.path.charAt(this.path.length() - 1) == '/') {
			return withPath(this.path + value);
		}
		return withPath(this.path + "/" + value);
	}

	public void setProperty(final String key, final String value) {
		this.properties.put(key, value);
	}

	@Override
	public String toString() {
		final StringBuilder out = new StringBuilder();
		if (this.group != null) {
			out.append(this.group);
			out.append(":");
		}
		out.append(this.path);
		final boolean first = true;
		for (final Map.Entry<String, String> entry : this.properties.entrySet()) {
			if (first) {
				out.append("?");
			} else {
				out.append("&");
			}
			out.append(entry.getKey());
			final String value = entry.getValue();
			if (value != null) {
				out.append("=");
				out.append(value);
			}
		}
		return out.toString();
	}

	// Format : DATA:jlfqkjsdflkjqs/sqldkhjflqksdjf/lll.png?lib=ewol
	public Uri withGroup(final String group) {
		return new Uri(group, this.path, new HashMap<>(this.properties));
	}

	public Uri withLib(final String lib) {
		final Map<String, String> tmp = new HashMap<>(this.properties);
		tmp.put("lib", lib);
		return new Uri(this.group, this.path, tmp);
	}

	public Uri withPath(final String path) {
		return new Uri(this.group, path, new HashMap<>(this.properties));
	}
}
