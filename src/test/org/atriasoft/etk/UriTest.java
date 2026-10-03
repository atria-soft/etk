package org.atriasoft.etk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Uri Tests")
class UriTest {

	private static Map<String, String> properties(final String... keyValues) {
		final Map<String, String> out = new HashMap<>();
		for (int iii = 0; iii < keyValues.length; iii += 2) {
			out.put(keyValues[iii], keyValues[iii + 1]);
		}
		return out;
	}

	private static void assertSameUri(final Uri expected, final Uri actual) {
		assertEquals(expected.getGroup(), actual.getGroup(), "group of " + expected);
		assertEquals(expected.getPath(), actual.getPath(), "path of " + expected);
		assertEquals(expected.getproperties(), actual.getproperties(), "properties of " + expected);
	}

	// ==================== toString ====================

	@Test
	@DisplayName("toString writes the group, the path and the properties")
	void testToString() {
		assertEquals("DATA:a.png", new Uri("DATA", "a.png").toString());
		assertEquals("DATA:a.png?lib=ewol", new Uri("DATA", "a.png", "ewol").toString());
	}

	@Test
	@DisplayName("toString joins the properties with '&', sorted by key")
	void testToStringJoinsSortedProperties() {
		final Uri uri = new Uri("FONTS", "a.svg", properties("size", "14", "lib", "esvg", "bold", ""));
		assertEquals("FONTS:a.svg?bold=&lib=esvg&size=14", uri.toString());
	}

	@Test
	@DisplayName("toString marks an URI without group with a leading ':'")
	void testToStringWithoutGroup() {
		assertEquals(":a.png", new Uri("a.png").toString());
		assertEquals(":C:/a.png", new Uri(null, "C:/a.png").toString());
	}

	// ==================== valueOf ====================

	@Test
	@DisplayName("valueOf without group uses the DATA group")
	void testValueOfDefaultGroup() {
		final Uri uri = Uri.valueOf("img/a.png");
		assertEquals("DATA", uri.getGroup());
		assertEquals("img/a.png", uri.getPath());
		assertEquals(Map.of(), uri.getproperties());
	}

	@Test
	@DisplayName("valueOf upper-cases the group and lower-cases the library")
	void testValueOfCase() {
		final Uri uri = Uri.valueOf("fonts:Free.svg?lib=ESVG&Size=14");
		assertEquals("FONTS", uri.getGroup());
		assertEquals("Free.svg", uri.getPath());
		assertEquals(properties("lib", "esvg", "Size", "14"), uri.getproperties());
	}

	@Test
	@DisplayName("valueOf with an empty group gives an URI without group")
	void testValueOfEmptyGroup() {
		final Uri uri = Uri.valueOf(":a.png");
		assertNull(uri.getGroup());
		assertEquals("a.png", uri.getPath());
	}

	@Test
	@DisplayName("valueOf keeps the ':' of the path after the group")
	void testValueOfColonInPath() {
		final Uri uri = Uri.valueOf("FILE:/home/user/a:b.png");
		assertEquals("FILE", uri.getGroup());
		assertEquals("/home/user/a:b.png", uri.getPath());
	}

	@Test
	@DisplayName("valueOf does not read a ':' of a property as the group separator")
	void testValueOfColonInProperty() {
		final Uri uri = Uri.valueOf("a.png?origin=x:y");
		assertEquals("DATA", uri.getGroup());
		assertEquals("a.png", uri.getPath());
		assertEquals(properties("origin", "x:y"), uri.getproperties());
	}

	@Test
	@DisplayName("valueOf gives an empty value to a property without '='")
	void testValueOfPropertyWithoutValue() {
		final Uri uri = Uri.valueOf("DATA:a.png?flag&lib=ewol");
		assertEquals(properties("flag", "", "lib", "ewol"), uri.getproperties());
	}

	@Test
	@DisplayName("valueOf keeps the '=' of a property value")
	void testValueOfEqualInValue() {
		final Uri uri = Uri.valueOf("DATA:a.png?filter=a=b");
		assertEquals(properties("filter", "a=b"), uri.getproperties());
	}

	@Test
	@DisplayName("valueOf ignores empty property elements")
	void testValueOfEmptyProperties() {
		assertEquals(Map.of(), Uri.valueOf("DATA:a.png?").getproperties());
		assertEquals(properties("x", "1", "y", "2"), Uri.valueOf("DATA:a.png?x=1&&y=2&").getproperties());
	}

	// ==================== Round trip ====================

	@Test
	@DisplayName("valueOf(toString()) gives back the same URI")
	void testRoundTripFromUri() {
		final List<Uri> uris = List.of(//
				new Uri("DATA", "a.png"), //
				new Uri("THEME", "shape/Button.json", "ewol"), //
				new Uri("FONTS", "a.svg", properties("lib", "esvg", "size", "14")), //
				new Uri("DATA", "a.png", properties("flag", "", "lib", "ewol")), //
				new Uri("DATA", "a.png", properties("filter", "a=b", "origin", "x:y")), //
				new Uri("FILE", "/home/user/a:b.png"), //
				new Uri("FILE", "/home/user/map.emap", properties("mode", "rw")), //
				new Uri("data", "lower/case.png"), //
				new Uri("DATA", ""), //
				new Uri("a.png"), //
				new Uri(null, "C:/a.png", properties("lib", "ewol")), //
				new Uri("", "empty/group.png"));
		for (final Uri uri : uris) {
			assertSameUri(uri, Uri.valueOf(uri.toString()));
		}
	}

	@Test
	@DisplayName("toString(valueOf()) gives back the same string for a written URI")
	void testRoundTripFromString() {
		final List<String> values = List.of(//
				"DATA:a.png", //
				"THEME:shape/Button.json?lib=ewol", //
				"FONTS:a.svg?lib=esvg&size=14", //
				"DATA:a.png?flag=&lib=ewol", //
				"FILE:/home/user/a:b.png", //
				"DATA:", //
				":a.png", //
				":C:/a.png?lib=ewol");
		for (final String value : values) {
			assertEquals(value, Uri.valueOf(value).toString());
		}
	}

	@Test
	@DisplayName("A null property value is stored as an empty value")
	void testNullPropertyValue() {
		final Map<String, String> prop = new HashMap<>();
		prop.put("flag", null);
		final Uri uri = new Uri("DATA", "a.png", prop);
		assertEquals("", uri.getProperty("flag"));
		uri.setProperty("other", null);
		assertEquals("", uri.getProperty("other"));
		assertSameUri(uri, Uri.valueOf(uri.toString()));
	}

	// ==================== withLib ====================

	@Test
	@DisplayName("withLib lower-cases the library like every library name")
	void testWithLib() {
		final Uri uri = new Uri("DATA", "a.png").withLib("EWOL");
		assertEquals("ewol", uri.getProperty("lib"));
		assertEquals("DATA:a.png?lib=ewol", uri.toString());
	}

	@Test
	@DisplayName("withLib(null) removes the library")
	void testWithLibNull() {
		final Uri uri = new Uri("DATA", "a.png", "ewol").withLib(null);
		assertFalse(uri.hasProperty("lib"));
	}
}
