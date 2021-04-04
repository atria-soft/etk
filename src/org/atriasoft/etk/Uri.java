package org.atriasoft.etk;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.atriasoft.etk.internal.Log;

public class Uri {
	private record LibraryElement(
			Class<?> klass,
			String basePath) {};
	
	private static Map<String, String> genericMap = new HashMap<>();
	private static Map<String, LibraryElement> libraries = new HashMap<>();
	private static Class<?> applicationClass = null;
	private static String applicationBasePath = "";
	
	static {
		genericMap.put("DATA", "data/");
		genericMap.put("THEME", "theme/");
		genericMap.put("TRANSLATE", "translate/");
	}
	
	public static void addLibrary(final String libName, final Class<?> classHandle, String basePath) {
		Log.info("Add library reference: lib=" + libName + " ==> " + classHandle.getCanonicalName() + "  base path=" + basePath);
		if (basePath == null || basePath.isEmpty()) {
			basePath = "/";
		}
		if (basePath.charAt(basePath.length() - 1) != '/') {
			basePath += "/";
		}
		if (basePath.charAt(0) != '/') {
			basePath = "/" + basePath;
		}
		libraries.put(libName.toLowerCase(), new LibraryElement(classHandle, basePath));
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
	 * public static Stream<Path> getResources(final URL element) { try { final URI
	 * uri = element.toURI(); FileSystem fs; Path path; if
	 * (uri.getScheme().contentEquals("jar")) { try { fs =
	 * FileSystems.getFileSystem(uri); } catch (final FileSystemNotFoundException e)
	 * { fs = FileSystems.newFileSystem(uri, Collections.<String, String>
	 * emptyMap()); } String pathInJar = "/"; final String tmpPath =
	 * element.getPath(); final int idSeparate = tmpPath.indexOf('!'); if
	 * (idSeparate != -1) { pathInJar = tmpPath.substring(idSeparate + 1); while
	 * (pathInJar.startsWith("/")) { pathInJar = pathInJar.substring(1); } } path =
	 * fs.getPath(pathInJar); } else { fs = FileSystems.getDefault(); path =
	 * Paths.get(uri); } return Files.walk(path, 1); } catch (URISyntaxException |
	 * IOException e) { e.printStackTrace(); return Stream.of(); } }
	 */
	public static InputStream getStream(final Uri uri) {
		Log.warning("Load resource: " + uri);
		String offsetGroup = "";
		if (uri.group != null) {
			Log.warning("    find group: " + uri.group);
			final String ret = genericMap.get(uri.group);
			if (ret != null) {
				Log.warning("        ==> " + ret);
				offsetGroup = ret;
			}
		}
		InputStream out = null;
		if (applicationClass == null) {
			Log.warning("    !! Application data class is not defined ...");
		} else {
			String tmpPath = applicationBasePath + offsetGroup + uri.path;
			tmpPath = tmpPath.replace("//", "/");
			Log.info("(appl) Try to load '" + tmpPath + "' in " + applicationClass.getCanonicalName());
			URL realFileName = applicationClass.getClassLoader().getResource(tmpPath);
			if (realFileName != null) {
				Log.info("(appl)    >>> " + realFileName.getFile());
			}
			out = applicationClass.getResourceAsStream(tmpPath);
			
			if (out == null) {
				Log.info("(appl) ==> element does not exist ...");
			}
		}
		if (out == null) {
			// search in the libraries ...
			if (uri.lib == null) {
				Log.warning("    !! No library specified");
				return null;
			} else {
				LibraryElement libraryElement = libraries.get(uri.lib);
				if (libraryElement == null) {
					Log.warning("     Can not get element in library");
					return null;
				}
				String tmpPath = libraryElement.basePath + offsetGroup + uri.path;
				tmpPath = tmpPath.replace("//", "/");
				Log.info("(lib) Try to load '" + tmpPath + "' in " + libraryElement.klass.getCanonicalName());
				URL realFileName = libraryElement.klass.getClassLoader().getResource(tmpPath);
				if (realFileName != null) {
					Log.info("(lib)    >>> " + realFileName.getFile());
				}
				out = libraryElement.klass.getResourceAsStream(tmpPath);
				if (out == null) {
					Log.info("(lib) ==> element does not exist ...");
				}
			}
		}
		if (out == null) {
			Log.error("Can not load resource: '" + uri + "'");
		} else {
			Log.warning("   =====> DATA LOADED <====== ");
		}
		return out;
	}
	
	public static List<Uri> listRecursive(final Uri uri) {
		final List<Uri> out = new ArrayList<>();
		return out;
	}
	
	public static void setApplication(final Class<?> classHandle) {
		setApplication(classHandle, "");
	}
	
	public static void setApplication(final Class<?> classHandle, String basePath) {
		Log.info("Set application reference : " + classHandle.getCanonicalName() + "  base path=" + basePath);
		applicationClass = classHandle;
		if (basePath == null || basePath.isEmpty()) {
			basePath = "/";
		}
		if (basePath.charAt(basePath.length() - 1) != '/') {
			basePath += "/";
		}
		if (basePath.charAt(0) != '/') {
			basePath = "/" + basePath;
		}
		applicationBasePath = basePath;
	}
	
	public static void setGroup(final String groupName, String basePath) {
		Log.info("Set Group : " + groupName + "  base path=" + basePath);
		if (basePath == null || basePath.isEmpty()) {
			basePath = "/";
		}
		if (basePath.charAt(basePath.length() - 1) != '/') {
			basePath += "/";
		}
		genericMap.put(groupName.toUpperCase(), basePath);
	}
	
	public static Uri valueOf(String value) {
		String group = null;
		String path = null;
		String lib = null;
		if (value.contains(":")) {
			final String[] ret = value.split(":", 2);
			group = ret[0].toUpperCase();
			value = ret[1];
		} else {
			group = "DATA";
		}
		if (value.contains("?lib=")) {
			final String[] ret = value.split("\\?lib=", 2);
			path = ret[0];
			lib = ret[1].toLowerCase();
		} else {
			path = value;
		}
		return new Uri(group, path, lib);
	}
	
	public static void writeAll(final Uri uri, final String data) {
		BufferedWriter out = null;
		try {
			FileWriter fstream = new FileWriter(uri.getPath(), true); //true tells to append data.
			out = new BufferedWriter(fstream);
			out.write(data);
		} catch (IOException e) {
			Log.error("Error: " + e.getMessage());
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Log.error("Error: ", e);
				}
			}
		}
	}
	
	private final String group;
	
	private final String path;
	
	private final String lib;
	
	public Uri(final String path) {
		this(null, path, null);
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
	
	// Format : DATA:jlfqkjsdflkjqs/sqldkhjflqksdjf/lll.png?lib=ewol
	public Uri withGroup(final String group) {
		return new Uri(group, this.path, this.lib);
	}
	
	public Uri withLib(final String lib) {
		return new Uri(this.group, this.path, lib);
	}
	
	public Uri withPath(final String path) {
		return new Uri(this.group, path, this.lib);
	}
}
