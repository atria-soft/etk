package org.atriasoft.etk.util;

import java.util.List;

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
	

	public static byte[] listByteToPrimitive(List<Object> input) {
		byte[] out = new byte[input.size()];
		for(int iii=0; iii<input.size(); iii++) {
			out[iii] = (Byte)input.get(iii);
		}
		return out;
	}

	public static byte[] toPrimitive(Byte[] input) {
		byte[] out = new byte[input.length];
		for(int iii=0; iii<input.length; iii++) {
			out[iii] = input[iii];
		}
		return out;
	}
	public static short[] listShortToPrimitive(List<Object> input) {
		short[] out = new short[input.size()];
		for(int iii=0; iii<input.size(); iii++) {
			out[iii] = (Short)input.get(iii);
		}
		return out;
	}

	public short[] toPrimitive(Short[] input) {
		short[] out = new short[input.length];
		for(int iii=0; iii<input.length; iii++) {
			out[iii] = input[iii];
		}
		return out;
	}
	public static int[] listIntegerToPrimitive(List<Object> tmpp) {
		int[] out = new int[tmpp.size()];
		for(int iii=0; iii<tmpp.size(); iii++) {
			out[iii] = (Integer)tmpp.get(iii);
		}
		return out;
	}

	public static int[] toPrimitive(Integer[] input) {
		int[] out = new int[input.length];
		for(int iii=0; iii<input.length; iii++) {
			out[iii] = input[iii];
		}
		return out;
	}
	public static long[] listLongToPrimitive(List<Object> input) {
		long[] out = new long[input.size()];
		for(int iii=0; iii<input.size(); iii++) {
			out[iii] = (Long)input.get(iii);
		}
		return out;
	}

	public static long[] toPrimitive(Long[] input) {
		long[] out = new long[input.length];
		for(int iii=0; iii<input.length; iii++) {
			out[iii] = input[iii];
		}
		return out;
	}
	public static boolean[] listBooleanToPrimitive(List<Object> input) {
		boolean[] out = new boolean[input.size()];
		for(int iii=0; iii<input.size(); iii++) {
			out[iii] = (Boolean)input.get(iii);
		}
		return out;
	}
	public static double[] listDoubleToPrimitive(List<Object> input) {
		double[] out = new double[input.size()];
		for(int iii=0; iii<input.size(); iii++) {
			out[iii] = (Double)input.get(iii);
		}
		return out;
	}
	public static float[] listFloatToPrimitive(List<Object> input) {
		float[] out = new float[input.size()];
		for(int iii=0; iii<input.size(); iii++) {
			out[iii] = (Float)input.get(iii);
		}
		return out;
	}

	public static boolean[] toPrimitive(boolean[] input) {
		boolean[] out = new boolean[input.length];
		for(int iii=0; iii<input.length; iii++) {
			out[iii] = (boolean)input[iii];
		}
		return out;
	}
	
	private ArraysTools() {}
}
