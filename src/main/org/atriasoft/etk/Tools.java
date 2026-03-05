package org.atriasoft.etk;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;


import org.atriasoft.etk.util.FilePos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class providing various helper methods for string manipulation, parsing, and data conversion.
 *
 * <p>Contains static utility methods for XML character escaping, number parsing, array conversions,
 * and text processing operations.</p>
 *
 * @author Edouard DUPIN
 * @since 0.1.0
 */
public class Tools {
	final static Logger LOGGER = LoggerFactory.getLogger(Tools.class);
	
	/**
	 * Add indentation of the string input.
	 * @param data String where the indentation is done.
	 * @param indent Number of tab to add at the string.
	 */
	public static void addIndent(final StringBuilder data, final int indent) {
		if (!data.isEmpty()) {
			data.append("\n");
		}
		for (int iii = 0; iii < indent; iii++) {
			data.append("\t");
		}
	}
	
	/**
	 * Check if an element or attribute is availlable (not : !"#$%&'()*+,/;<=>?@[\]^`{|}~ \\n\\t\\r and for first char : not -.0123456789).
	 * @param val Value to check the conformity.
	 * @param firstChar True if the element check is the first char.
	 * @return true The value can be a part of attribute name
	 * @return false The value can NOT be a part of attribute name
	 */
	public static boolean checkAvaillable(final Character val, final boolean firstChar) {
		if (val == '!' || val == '"' || val == '#' || val == '$' || val == '%' || val == '&' || val == '\'' || val == '(' || val == ')' || val == '*' || val == '+' || val == ',' || val == '/'
				|| val == ';' || val == '<' || val == '=' || val == '>' || val == '?' || val == '@' || val == '[' || val == '\\' || val == ']' || val == '^' || val == '`' || val == '{' || val == '|'
				|| val == '}' || val == '~' || val == ' ' || val == '\n' || val == '\t' || val == '\r') {
			return false;
		}
		if (firstChar) {
			if (val == '-' || val == '.' || (val >= '0' && val <= '9')) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean checkNumber(final Character val) {
		if (val == '-' || val == '+' || val == 'e' || val == '.' || (val >= '0' && val <= '9')) {
			return true;
		}
		return false;
	}
	
	public static boolean checkNumber(final Character val, final boolean firstChar) {
		if (val == '.' || (val >= '0' && val <= '9')) {
			return true;
		}
		if (firstChar && val == '-') {
			return true;
		}
		return false;
	}
	
	public static boolean checkString(final Character val) {
		if (val == '!' || val == '"' || val == '#' || val == '$' || val == '%' || val == '&' || val == '\'' // '
				|| val == '(' || val == ')' || val == '*' || val == '+' || val == ',' || val == '/' || val == ':' || val == ';' || val == '<' || val == '=' || val == '>' || val == '?' || val == '@'
				|| val == '[' || val == '\\' || val == ']' || val == '^' || val == '`' || val == '{' || val == '|' || val == '}' || val == '~' || val == ' ' || val == '\n' || val == '\t'
				|| val == '\r') {
			return false;
		}
		return true;
	}
	
	public static String cleanNumberList(final String data) {
		return data.replaceAll("[ \t\n\r]", "").replaceAll(",", ";");
	}
	
	/**
	 *  count the number of white char in the string from the specify position (stop at the first element that is not a white char)
	 * @param data Data to parse.
	 * @param pos Start position in the string.
	 * @param filePos new poistion of te file to add.
	 * @return number of white element.
	 */
	public static int countWhiteChar(final String data, final int pos, final FilePos filePos) {
		filePos.clear();
		int white = 0;
		for (int iii = pos; iii < data.length(); iii++) {
			filePos.check(data.charAt(iii));
			if (!Tools.isWhiteChar(data.charAt(iii))) {
				break;
			}
			white++;
		}
		filePos.decrement();
		return white;
	}
	
	public static String createPosPointer(final String line, final int pos) {
		final StringBuilder out = new StringBuilder();
		int iii;
		for (iii = 0; iii < pos && iii < line.length(); iii++) {
			if (line.charAt(iii) == '\t') {
				out.append("\t");
			} else {
				out.append(" ");
			}
		}
		for (; iii < pos; iii++) {
			out.append(" ");
		}
		out.append("^");
		return out.toString();
	}
	
	// based on this: https://stackoverflow.com/questions/4052840/most-efficient-way-to-make-the-first-character-of-a-string-lower-case
	public static String decapitalizeFirst(final String string) {
		if (string == null || string.length() == 0) {
			return string;
		}
		final char[] c = string.toCharArray();
		c[0] = Character.toLowerCase(c[0]);
		return new String(c);
	}
	
	/**
	 *  Display the current element that is currently parse.
	 * @param val Char that is parsed.
	 * @param filePos Position of the char in the file.
	 */
	public static void drawElementParsed(final Character val, final FilePos filePos) {
		//		if (val == '\n') {
		//			LOGGER.error(filePos + " parse '\\n'");
		//		} else if (val == '\t') {
		//			LOGGER.error(filePos + " parse '\\t'");
		//		} else {
		//			LOGGER.error(filePos + " parse '" + val + "'");
		//		}
	}
	
	public static String extractLine(final String data, final int pos) {
		// search back : '\n'
		int startPos = data.lastIndexOf('\n', pos);
		if (startPos == pos) {
			startPos = 0;
		} else {
			startPos++;
		}
		// search forward : '\n'
		int stopPos = pos;
		if (data.length() == pos) {
			stopPos = pos;
		} else if (data.charAt(pos) != '\n') {
			stopPos = data.indexOf('\n', pos);
			if (stopPos == pos) {
				stopPos = data.length();
			}
		}
		if (startPos == -1) {
			startPos = 0;
		} else if (startPos >= data.length()) {
			return "";
		}
		if (stopPos == -1) {
			return "";
		}
		if (stopPos >= data.length()) {
			stopPos = data.length();
		}
		return data.substring(startPos, stopPos);
	}
	
	public static boolean isWhiteChar(final Character val) {
		if (val == ' ' || val == '\t' || val == '\n' || val == '\r') {
			return true;
		}
		return false;
	}
	
	/**
	 * get the next power 2 if the input
	 * @param value Value that we want the next power of 2
	 * @return result value
	 */
	public static int nextP2(final int value) {
		int val = 1;
		for (int iii = 1; iii < 31; iii++) {
			if (value <= val) {
				return val;
			}
			val *= 2;
		}
		LOGGER.error("impossible CASE....");
		return val;
	}
	
	public static Boolean[] parseBooleanClassStringList(String data) {
		data = Tools.cleanNumberList(data);
		final String[] dataArray = data.split(";");
		final Boolean[] out = new Boolean[dataArray.length];
		int count = 0;
		for (final String str : dataArray) {
			out[count++] = Boolean.valueOf(str);
		}
		return out;
	}
	
	public static boolean[] parseBooleanStringList(String data) {
		data = Tools.cleanNumberList(data);
		final String[] dataArray = data.split(";");
		final boolean[] out = new boolean[dataArray.length];
		int count = 0;
		for (final String str : dataArray) {
			out[count++] = Boolean.parseBoolean(str);
		}
		return out;
	}
	
	public static Byte[] parseByteClassStringList(String data) {
		data = Tools.cleanNumberList(data);
		final String[] dataArray = data.split(";");
		final Byte[] out = new Byte[dataArray.length];
		int count = 0;
		for (final String str : dataArray) {
			out[count++] = Byte.parseByte(str);
		}
		return out;
	}
	
	public static byte[] parseByteStringList(String data) {
		data = Tools.cleanNumberList(data);
		final String[] dataArray = data.split(";");
		final byte[] out = new byte[dataArray.length];
		int count = 0;
		for (final String str : dataArray) {
			out[count++] = Byte.parseByte(str);
		}
		return out;
	}
	
	public static Double[] parseDoubleClassStringList(String data) {
		data = Tools.cleanNumberList(data);
		final String[] dataArray = data.split(";");
		final Double[] out = new Double[dataArray.length];
		int count = 0;
		for (final String str : dataArray) {
			out[count++] = Double.parseDouble(str);
		}
		return out;
	}
	
	public static double[] parseDoubleStringList(String data) {
		data = Tools.cleanNumberList(data);
		final String[] dataArray = data.split(";");
		final double[] out = new double[dataArray.length];
		int count = 0;
		for (final String str : dataArray) {
			out[count++] = Double.parseDouble(str);
		}
		return out;
	}
	
	public static Float[] parseFloatClassStringList(String data) {
		data = Tools.cleanNumberList(data);
		final String[] dataArray = data.split(";");
		final Float[] out = new Float[dataArray.length];
		int count = 0;
		for (final String str : dataArray) {
			out[count++] = Float.parseFloat(str);
		}
		return out;
	}
	
	public static float[] parseFloatStringList(String data) {
		data = Tools.cleanNumberList(data);
		final String[] dataArray = data.split(";");
		final float[] out = new float[dataArray.length];
		int count = 0;
		for (final String str : dataArray) {
			out[count++] = Float.parseFloat(str);
		}
		return out;
	}
	
	public static Integer[] parseIntegerClassStringList(String data) {
		data = Tools.cleanNumberList(data);
		final String[] dataArray = data.split(";");
		final Integer[] out = new Integer[dataArray.length];
		int count = 0;
		for (final String str : dataArray) {
			out[count++] = Integer.parseInt(str);
		}
		return out;
	}
	
	public static int[] parseIntegerStringList(String data) {
		data = Tools.cleanNumberList(data);
		final String[] dataArray = data.split(";");
		final int[] out = new int[dataArray.length];
		int count = 0;
		for (final String str : dataArray) {
			out[count++] = Integer.parseInt(str);
		}
		return out;
	}
	
	public static Long[] parseLongClassStringList(String data) {
		data = Tools.cleanNumberList(data);
		final String[] dataArray = data.split(";");
		final Long[] out = new Long[dataArray.length];
		int count = 0;
		for (final String str : dataArray) {
			out[count++] = Long.parseLong(str);
		}
		return out;
	}
	
	public static long[] parseLongStringList(String data) {
		data = Tools.cleanNumberList(data);
		final String[] dataArray = data.split(";");
		final long[] out = new long[dataArray.length];
		int count = 0;
		for (final String str : dataArray) {
			out[count++] = Long.parseLong(str);
		}
		return out;
	}
	
	public static Short[] parseShortClassStringList(String data) {
		data = Tools.cleanNumberList(data);
		final String[] dataArray = data.split(";");
		final Short[] out = new Short[dataArray.length];
		int count = 0;
		for (final String str : dataArray) {
			out[count++] = Short.parseShort(str);
		}
		return out;
	}
	
	public static short[] parseShortStringList(String data) {
		data = Tools.cleanNumberList(data);
		final String[] dataArray = data.split(";");
		final short[] out = new short[dataArray.length];
		int count = 0;
		for (final String str : dataArray) {
			out[count++] = Short.parseShort(str);
		}
		return out;
	}
	
	private static String readFile(final Path path, final Charset encoding) throws IOException {
		final byte[] encoded = Files.readAllBytes(path);
		return new String(encoded, encoding);
	}
	
	// transform the Text with :
	//	     "&lt;"   == "<"
	//	     "&gt;"   == ">"
	//	     "&amp;"  == "&"
	//	     "&apos;" == "'"
	//	     "&quot;" == """
	public static String replaceSpecialChar(final String inval) {
		String out = inval;
		out = out.replace("&lt;", "<");
		out = out.replace("&gt;", ">");
		out = out.replace("&apos;", "'");
		out = out.replace("&quot;", "\"");
		out = out.replace("&amp;", "&");
		//EXMLERROR("INNN '"<< inval << "' => '" << out << "'");
		return out;
	}
	
	public static String replaceSpecialCharOut(final String inval) {
		String out = inval;
		out = out.replace("<", "&lt;");
		out = out.replace(">", "&gt;");
		out = out.replace("'", "&apos;");
		out = out.replace("\"", "&quot;");
		out = out.replace("&", "&amp;");
		//EXMLERROR("OUTTT '"<< inval << "' => '" << out << "'");
		return out;
	}
	
	public static String toString(final boolean[] data) {
		final StringBuilder out = new StringBuilder();
		for (int iii = 0; iii < data.length; iii++) {
			if (iii != 0) {
				out.append(";");
			}
			out.append(data[iii]);
		}
		return out.toString();
	}
	
	public static String toString(final Boolean[] data) {
		final StringBuilder out = new StringBuilder();
		for (int iii = 0; iii < data.length; iii++) {
			if (iii != 0) {
				out.append(";");
			}
			out.append(data[iii]);
		}
		return out.toString();
	}
	
	public static String toString(final byte[] data) {
		final StringBuilder out = new StringBuilder();
		for (int iii = 0; iii < data.length; iii++) {
			if (iii != 0) {
				out.append(";");
			}
			out.append(data[iii]);
		}
		return out.toString();
	}
	
	public static String toString(final Byte[] data) {
		final StringBuilder out = new StringBuilder();
		for (int iii = 0; iii < data.length; iii++) {
			if (iii != 0) {
				out.append(";");
			}
			out.append(data[iii]);
		}
		return out.toString();
	}
	
	public static String toString(final double[] data) {
		final StringBuilder out = new StringBuilder();
		for (int iii = 0; iii < data.length; iii++) {
			if (iii != 0) {
				out.append(";");
			}
			out.append(data[iii]);
		}
		return out.toString();
	}
	
	public static String toString(final Double[] data) {
		final StringBuilder out = new StringBuilder();
		for (int iii = 0; iii < data.length; iii++) {
			if (iii != 0) {
				out.append(";");
			}
			out.append(data[iii]);
		}
		return out.toString();
	}
	
	public static String toString(final float[] data) {
		final StringBuilder out = new StringBuilder();
		for (int iii = 0; iii < data.length; iii++) {
			if (iii != 0) {
				out.append(";");
			}
			out.append(data[iii]);
		}
		return out.toString();
	}
	
	public static String toString(final Float[] data) {
		final StringBuilder out = new StringBuilder();
		for (int iii = 0; iii < data.length; iii++) {
			if (iii != 0) {
				out.append(";");
			}
			out.append(data[iii]);
		}
		return out.toString();
	}
	
	public static String toString(final int[] data) {
		final StringBuilder out = new StringBuilder();
		for (int iii = 0; iii < data.length; iii++) {
			if (iii != 0) {
				out.append(";");
			}
			out.append(data[iii]);
		}
		return out.toString();
	}
	
	public static String toString(final Integer[] data) {
		final StringBuilder out = new StringBuilder();
		for (int iii = 0; iii < data.length; iii++) {
			if (iii != 0) {
				out.append(";");
			}
			out.append(data[iii]);
		}
		return out.toString();
	}
	
	public static String toString(final long[] data) {
		final StringBuilder out = new StringBuilder();
		for (int iii = 0; iii < data.length; iii++) {
			if (iii != 0) {
				out.append(";");
			}
			out.append(data[iii]);
		}
		return out.toString();
	}
	
	public static String toString(final Long[] data) {
		final StringBuilder out = new StringBuilder();
		for (int iii = 0; iii < data.length; iii++) {
			if (iii != 0) {
				out.append(";");
			}
			out.append(data[iii]);
		}
		return out.toString();
	}
	
	public static String toString(final short[] data) {
		final StringBuilder out = new StringBuilder();
		for (int iii = 0; iii < data.length; iii++) {
			if (iii != 0) {
				out.append(";");
			}
			out.append(data[iii]);
		}
		return out.toString();
	}
	
	public static String toString(final Short[] data) {
		final StringBuilder out = new StringBuilder();
		for (int iii = 0; iii < data.length; iii++) {
			if (iii != 0) {
				out.append(";");
			}
			out.append(data[iii]);
		}
		return out.toString();
	}
	
	private Tools() {}
}
