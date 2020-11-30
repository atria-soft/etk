package org.atriasoft.etk;

import java.util.HashMap;
import java.util.Map;

public class Uri {
	private static Map<String, String> genericMap = new HashMap<String, String>();
	
	public static void setGroup(String groupName, String basePath) {
		genericMap.put(groupName.toUpperCase(), basePath);
	}
	private final String value;
	
	public Uri(String value) {
		this.value = value;
	}
	
	public Uri(String group, String path) {
		this.value = group.toUpperCase() + ":" + path;
	}

	public String getValue() {
		return value;
	}
	
	public String getPath() {
		String[] ret = value.split(":",2);
		return genericMap.get(ret[0]) + "/" + ret[1];
	}
	public String get() {
		return getPath();
	}

	@Override
	public String toString() {
		return "Uri [value=" + value + "]";
	}
	
}
