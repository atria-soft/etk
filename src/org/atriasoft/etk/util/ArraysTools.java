package org.atriasoft.etk.util;

public class ArraysTools {
	public static <T> void fill(final T[] buffer, final T value) {
		if (buffer == null) {
			return;
		}
		for (int iii = 0; iii < buffer.length; iii++) {
			buffer[iii] = value;
		}
	}
	
	public static <T> void fill2(final float[][] buffer, final float value) {
		if (buffer == null) {
			return;
		}
		for (int iii = 0; iii < buffer.length; iii++) {
			for (int jjj = 0; jjj < buffer[iii].length; jjj++) {
				buffer[iii][jjj] = value;
			}
		}
	}
	
	public static <T> void fill2(final T[][] buffer, final T value) {
		if (buffer == null) {
			return;
		}
		for (int iii = 0; iii < buffer.length; iii++) {
			for (int jjj = 0; jjj < buffer[iii].length; jjj++) {
				buffer[iii][jjj] = value;
			}
		}
	}
	
	private ArraysTools() {}
}
