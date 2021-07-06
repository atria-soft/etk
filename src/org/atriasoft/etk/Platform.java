package org.atriasoft.etk;

import org.atriasoft.etk.internal.Log;

import edu.umd.cs.findbugs.annotations.CheckReturnValue;

public class Platform {
	private Platform() {}
	/**
	 * Get the current OS we are running:
	 * <ul>
	 * <li>"android": Google Android OS</li> 
	 * <li>"linux": Generic Linux OS</li>
	 * <li>"ios": Apple Embended OS</li>
	 * <li>"macosx": Apple PC OS</li>
	 * <li>"windows": Microsoft Generic OS</li>
	 * <li>property:"org.atriasoft.etk.platform.os": when not find</li>
	 * </ul> 
	 * @return The specific platform
	 */
	@CheckReturnValue
    public static String getOS() {
        String jvmName = System.getProperty("java.vm.name", "").toLowerCase();
        String osName  = System.getProperty("os.name", "").toLowerCase();
        if (osName.startsWith("linux")) {
        	if (jvmName.startsWith("dalvik")) {
        		osName = "android";
        	}
        	osName = "linux";
        } else if(osName.startsWith("darwin")) {
        	if (jvmName.startsWith("robovm")) {
        		osName = "ios";
        	} else{
        		osName = "macosx";
        	}
        }
        return System.getProperty("org.atriasoft.etk.platform.os", osName);
    }
	/**
	 * Get the current architecture of the platform:
	 * <ul>
	 * <li>"arm": 32 bit arm system</li>
	 * <li>"arm64": 64 bit arm system</li>
	 * <li>"armhf": 64 bit arm system with Hard Float implementation</li>
	 * <li>"x86": 32 bit I386 system</li>
	 * <li>"x86_64": 64 bit AMD64 system</li>
	 * <li>property:"org.atriasoft.etk.platform.arch": when not find</li>
	 * </ul> 
	 * @return the specific architecture model.
	 */
	@CheckReturnValue
    public static String getArch() {
        String osArch  = System.getProperty("os.arch", "").toLowerCase();
        String abiType = System.getProperty("sun.arch.abi", "").toLowerCase();
        String libPath = System.getProperty("sun.boot.library.path", "").toLowerCase();
        if (Platform.getOS().equals("ios")) {
            osArch = "arm";
        }
        if (osArch.equals("i386") || osArch.equals("i486") || osArch.equals("i586") || osArch.equals("i686")) {
            osArch = "x86";
        } else if (osArch.equals("amd64") || osArch.equals("x86-64") || osArch.equals("x64")) {
            osArch = "x86_64";
        } else if (osArch.startsWith("aarch64") || osArch.startsWith("armv8") || osArch.startsWith("arm64")) {
            osArch = "arm64";
        } else if ((osArch.startsWith("arm")) && ((abiType.equals("gnueabihf")) || (libPath.contains("openjdk-armhf")))) {
            osArch = "armhf";
        } else if (osArch.startsWith("arm")) {
            osArch = "arm";
        } else {
        	Log.warning("Maybe unknowx system... osArch=" + osArch + " abiType=" + abiType + " libPath=" + libPath);
        }
        return System.getProperty("org.atriasoft.etk.platform.arch", osArch);
    }
	/**
	 * Get the extension depending on the OS platform
	 * @return the ".so", ".dylib" or ".dll" depending on the current platform
	 */
	@CheckReturnValue
	public static String getDynamicLibraryExtension() {
		String os = Platform.getOS();
    	switch(os) {
    		case "linux":
    		case "android":
    			return ".so";
    		case "windows":
    			return ".dll";
    		case "ios":
    		case "macos":
    			return ".dylib";
    		default:
    			return "unknown";
    	}
	}
	
}
