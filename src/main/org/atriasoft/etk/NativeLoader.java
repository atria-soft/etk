package org.atriasoft.etk;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for loading native libraries from JAR resources.
 *
 * <p>Extracts native libraries (shared objects, DLLs, dylibs) from JAR files to temporary
 * locations and loads them into the JVM. Handles platform-specific library naming and cleanup.</p>
 *
 * @author Edouard DUPIN
 * @since 0.1.0
 */
public class NativeLoader {
	final static Logger LOGGER = LoggerFactory.getLogger(NativeLoader.class);
	private NativeLoader() {}
	
	/**
	 * Loads a native library from a JAR file.
	 *
	 * <p>Extracts the library to a temporary file, loads it into the JVM, and schedules it for deletion on exit.</p>
	 *
	 * @param fileToLoad URI pointing to the native library resource
	 * @throws IOException If the library cannot be extracted or loaded
	 */
    public static void load(final Uri fileToLoad) throws IOException {
        LOGGER.error("Start load library native ...");
        // in java the loading of .so need to be externalized to be loaded by the system as native library. then we copy in an external temporary folder and remove it when application close.
        try {
			InputStream is = Uri.getStream(fileToLoad);
	        File file = File.createTempFile(fileToLoad.getFileNameNoExt(), fileToLoad.getExtention());
	        OutputStream os = new FileOutputStream(file);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) != -1) {
	        	os.write(buffer, 0, length);
	        }
	        is.close();
	        os.close();
	        System.load(file.getAbsolutePath());
	        file.deleteOnExit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	        throw new IOException("Error while loading native library: '" + fileToLoad + "' ==> " + e.getMessage());
		}
    }
    
	/**
	 * Loads a native library with automatic platform detection.
	 *
	 * <p>Automatically appends the correct platform-specific suffix (OS and architecture)
	 * to the library path before loading.</p>
	 *
	 * @param fileToLoad Base URI for the native library (platform suffix will be added)
	 * @throws IOException If the library cannot be loaded
	 */
    public static void loadAutoPlatform(final Uri fileToLoad) throws IOException {
    	String os = Platform.getOS();
    	String arch = Platform.getArch();
    	String ext = Platform.getDynamicLibraryExtension();
    	NativeLoader.load(fileToLoad.withPath(fileToLoad.getPath() + os + "-" + arch + ext));
    }
}
