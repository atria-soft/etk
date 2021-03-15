package org.atriasoft.etk;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.atriasoft.etk.internal.Log;

public class Uri {
	private static Map<String, String> genericMap = new HashMap<>();
	private static Map<String, Class<?>> libraries = new HashMap<>();
	private static Class<?> applicationClass = null;
	
	static {
		genericMap.put("DATA", "");
		genericMap.put("THEME_GUI", "theme/");
	}
	
	public static void addLibrary(final String libName, final Class<?> classHandle) {
		libraries.put(libName.toLowerCase(), classHandle);
	}
	
	public static byte[] getAllData(final Uri resourceName) {
		final InputStream out = getStream(resourceName);
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
	
	/*
	public static Stream<Path> getResources(final URL element) {
		try {
			final URI uri = element.toURI();
			FileSystem fs;
			Path path;
			if (uri.getScheme().contentEquals("jar")) {
				try {
					fs = FileSystems.getFileSystem(uri);
				} catch (final FileSystemNotFoundException e) {
					fs = FileSystems.newFileSystem(uri, Collections.<String, String> emptyMap());
				}
				String pathInJar = "/";
				final String tmpPath = element.getPath();
				final int idSeparate = tmpPath.indexOf('!');
				if (idSeparate != -1) {
					pathInJar = tmpPath.substring(idSeparate + 1);
					while (pathInJar.startsWith("/")) {
						pathInJar = pathInJar.substring(1);
					}
				}
				path = fs.getPath(pathInJar);
			} else {
				fs = FileSystems.getDefault();
				path = Paths.get(uri);
			}
			return Files.walk(path, 1);
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
			return Stream.of();
		}
	}
	*/
	public static InputStream getStream(final Uri resourceName) {
		Log.verbose("Load resource: " + resourceName);
		String offset = "";
		if (resourceName.group != null) {
			final String ret = genericMap.get(resourceName.group);
			if (ret != null) {
				offset = ret;
			}
		}
		InputStream out = null;
		if (applicationClass == null) {
			Log.warning("Application data class is not defined ...");
		} else {
			out = applicationClass.getResourceAsStream("/data/" + offset + resourceName.path);
		}
		if (out != null) {
			// search in the libraries ...
			if (resourceName.lib == null) {
				return null;
			} else {
				final Class<?> libClass = libraries.get(resourceName.lib);
				if (libClass == null) {
					return null;
				}
				out = libClass.getResourceAsStream("/data/" + offset + resourceName.path);
			}
		}
		if (out == null) {
			Log.error("Can not load resource: '" + resourceName + "'");
		}
		return out;
	}
	
	public static List<Uri> listRecursive(final Uri uri) {
		final List<Uri> out = new ArrayList<>();
		return out;
	}
	
	public static void setApplication(final Class<?> classHandle) {
		applicationClass = classHandle;
	}
	
	public static void setGroup(final String groupName, final String basePath) {
		genericMap.put(groupName.toUpperCase(), basePath);
	}
	
	private final String group;
	
	private final String path;
	
	private final String lib;
	
	// Format : DATA:jlfqkjsdflkjqs/sqldkhjflqksdjf/lll.png?lib=ewol
	public Uri(String value) {
		if (value.contains(":") == true) {
			final String[] ret = value.split(":", 2);
			this.group = ret[0].toUpperCase();
			;
			value = ret[1];
		} else {
			this.group = "DATA";
		}
		if (value.contains("?lib=") == true) {
			final String[] ret = value.split("?lib=", 2);
			this.path = ret[0];
			this.lib = ret[1].toLowerCase();
		} else {
			this.path = value;
			this.lib = null;
		}
	}
	
	public Uri(final String group, final String path) {
		this(group, path, null);
	}
	
	public Uri(final String group, final String path, final String lib) {
		if (group == null) {
			this.group = null;
		} else {
			this.group = group.toUpperCase();
		}
		this.path = path;
		if (lib == null) {
			this.lib = null;
		} else {
			this.lib = lib.toLowerCase();
		}
	}
	
	public String get() {
		return getPath();
	}
	
	public String getExtention() {
		final String[] ret = this.path.split(".");
		return ret[ret.length - 1];
	}
	
	public String getPath() {
		return this.path;
	}
	
	public String getValue() {
		return toString();
	}
	
	public boolean isEmpty() {
		return this.path == null || this.path.isEmpty();
	}
	
	@Override
	public String toString() {
		String out = "";
		if (this.group != null) {
			out += this.group + ":";
		}
		out += this.path;
		if (this.lib != null) {
			out += "?lib=" + this.lib;
		}
		return out;
	}
}
