package org.atriasoft.etk;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.atriasoft.etk.internal.Log;

public class NativeLoader {
	private NativeLoader() {}
	
	/**
	 * Load a native library that is contained inside a JAR. this library is extracted in a temporary folder and open. the library is removed when program ends.
	 * @param fileToLoad Name of the shared library to load
	 * @throws IOException the library can not be loaded...
	 */
    public static void load(final Uri fileToLoad) throws IOException {
        Log.error("Start load library native ...");
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
    
    public static void loadAutoPlatform(final Uri fileToLoad) throws IOException {
    	String os = Platform.getOS();
    	String arch = Platform.getArch();
    	String ext = Platform.getDynamicLibraryExtension();
    	NativeLoader.load(fileToLoad.withPath(fileToLoad.getPath() + os + "-" + arch + ext));
    }
}
