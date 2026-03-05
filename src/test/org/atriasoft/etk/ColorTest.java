package org.atriasoft.etk;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Color Tests")
class ColorTest {

    private static final float EPSILON = 0.001f;

    // ==================== Constructor Tests ====================

    @Test
    @DisplayName("Constructor with floats (RGB)")
    void testConstructorFloatsRGB() {
        Color c = new Color(0.5f, 0.6f, 0.7f);
        assertEquals(0.5f, c.r(), EPSILON);
        assertEquals(0.6f, c.g(), EPSILON);
        assertEquals(0.7f, c.b(), EPSILON);
        assertEquals(1.0f, c.a(), EPSILON);
    }

    @Test
    @DisplayName("Constructor with floats (RGBA)")
    void testConstructorFloatsRGBA() {
        Color c = new Color(0.5f, 0.6f, 0.7f, 0.8f);
        assertEquals(0.5f, c.r(), EPSILON);
        assertEquals(0.6f, c.g(), EPSILON);
        assertEquals(0.7f, c.b(), EPSILON);
        assertEquals(0.8f, c.a(), EPSILON);
    }

    @Test
    @DisplayName("Constructor with ints (RGB)")
    void testConstructorIntsRGB() {
        Color c = new Color(128, 192, 255);
        assertEquals(128 / 255.0f, c.r(), EPSILON);
        assertEquals(192 / 255.0f, c.g(), EPSILON);
        assertEquals(255 / 255.0f, c.b(), EPSILON);
        assertEquals(1.0f, c.a(), EPSILON);
    }

    @Test
    @DisplayName("Constructor with ints (RGBA)")
    void testConstructorIntsRGBA() {
        Color c = new Color(128, 192, 255, 200);
        assertEquals(128 / 255.0f, c.r(), EPSILON);
        assertEquals(192 / 255.0f, c.g(), EPSILON);
        assertEquals(255 / 255.0f, c.b(), EPSILON);
        assertEquals(200 / 255.0f, c.a(), EPSILON);
    }

    @Test
    @DisplayName("Constructor with doubles")
    void testConstructorDoubles() {
        Color c = new Color(0.5, 0.6, 0.7, 0.8);
        assertEquals(0.5f, c.r(), EPSILON);
        assertEquals(0.6f, c.g(), EPSILON);
        assertEquals(0.7f, c.b(), EPSILON);
        assertEquals(0.8f, c.a(), EPSILON);
    }

    // ==================== Named Color Tests ====================

    @Test
    @DisplayName("BLACK constant")
    void testBlackConstant() {
        assertEquals(0.0f, Color.BLACK.r(), EPSILON);
        assertEquals(0.0f, Color.BLACK.g(), EPSILON);
        assertEquals(0.0f, Color.BLACK.b(), EPSILON);
        assertEquals(1.0f, Color.BLACK.a(), EPSILON);
    }

    @Test
    @DisplayName("WHITE constant")
    void testWhiteConstant() {
        assertEquals(1.0f, Color.WHITE.r(), EPSILON);
        assertEquals(1.0f, Color.WHITE.g(), EPSILON);
        assertEquals(1.0f, Color.WHITE.b(), EPSILON);
        assertEquals(1.0f, Color.WHITE.a(), EPSILON);
    }

    @Test
    @DisplayName("RED constant")
    void testRedConstant() {
        assertEquals(1.0f, Color.RED.r(), EPSILON);
        assertEquals(0.0f, Color.RED.g(), EPSILON);
        assertEquals(0.0f, Color.RED.b(), EPSILON);
        assertEquals(1.0f, Color.RED.a(), EPSILON);
    }

    @Test
    @DisplayName("GREEN constant")
    void testGreenConstant() {
        assertEquals(0.0f, Color.GREEN.r(), EPSILON);
        assertTrue(Color.GREEN.g() > 0.0f);
        assertEquals(0.0f, Color.GREEN.b(), EPSILON);
        assertEquals(1.0f, Color.GREEN.a(), EPSILON);
    }

    @Test
    @DisplayName("BLUE constant")
    void testBlueConstant() {
        assertEquals(0.0f, Color.BLUE.r(), EPSILON);
        assertEquals(0.0f, Color.BLUE.g(), EPSILON);
        assertEquals(1.0f, Color.BLUE.b(), EPSILON);
        assertEquals(1.0f, Color.BLUE.a(), EPSILON);
    }

    @Test
    @DisplayName("NONE constant")
    void testNoneConstant() {
        assertEquals(0.0f, Color.NONE.r(), EPSILON);
        assertEquals(0.0f, Color.NONE.g(), EPSILON);
        assertEquals(0.0f, Color.NONE.b(), EPSILON);
        assertEquals(0.0f, Color.NONE.a(), EPSILON);
    }

    // ==================== get() Tests ====================

    @Test
    @DisplayName("get should return named color")
    void testGet() {
        Color c = Color.get("red");
        assertNotNull(c);
        assertEquals(Color.RED, c);
    }

    @Test
    @DisplayName("get should be case insensitive")
    void testGetCaseInsensitive() {
        Color c1 = Color.get("red");
        Color c2 = Color.get("RED");
        Color c3 = Color.get("Red");
        assertEquals(c1, c2);
        assertEquals(c1, c3);
    }

    @Test
    @DisplayName("get should return null for unknown color")
    void testGetUnknown() {
        Color c = Color.get("notacolor");
        assertNull(c);
    }

    // ==================== valueOf() Tests ====================

    @Test
    @DisplayName("valueOf should parse named color")
    void testValueOfNamed() throws Exception {
        Color c = Color.valueOf("red");
        assertEquals(Color.RED, c);
    }

    @Test
    @DisplayName("valueOf should parse #RGB")
    void testValueOfRGB() throws Exception {
        Color c = Color.valueOf("#F00");
        assertEquals(1.0f, c.r(), EPSILON);
        assertEquals(0.0f, c.g(), EPSILON);
        assertEquals(0.0f, c.b(), EPSILON);
    }

    @Test
    @DisplayName("valueOf should parse #RGBA")
    void testValueOfRGBA() throws Exception {
        Color c = Color.valueOf("#F00F");
        assertEquals(1.0f, c.r(), EPSILON);
        assertEquals(0.0f, c.g(), EPSILON);
        assertEquals(0.0f, c.b(), EPSILON);
        assertEquals(1.0f, c.a(), EPSILON);
    }

    @Test
    @DisplayName("valueOf should parse #RRGGBB")
    void testValueOfRRGGBB() throws Exception {
        Color c = Color.valueOf("#FF0000");
        assertEquals(1.0f, c.r(), EPSILON);
        assertEquals(0.0f, c.g(), EPSILON);
        assertEquals(0.0f, c.b(), EPSILON);
    }

    @Test
    @DisplayName("valueOf should parse #RRGGBBAA")
    void testValueOfRRGGBBAA() throws Exception {
        Color c = Color.valueOf("#FF0000FF");
        assertEquals(1.0f, c.r(), EPSILON);
        assertEquals(0.0f, c.g(), EPSILON);
        assertEquals(0.0f, c.b(), EPSILON);
        assertEquals(1.0f, c.a(), EPSILON);
    }

    @Test
    @DisplayName("valueOf should parse rgb(r,g,b)")
    void testValueOfRgb() throws Exception {
        Color c = Color.valueOf("rgb(1.0,0.0,0.0)");
        assertEquals(1.0f, c.r(), EPSILON);
        assertEquals(0.0f, c.g(), EPSILON);
        assertEquals(0.0f, c.b(), EPSILON);
    }

    @Test
    @DisplayName("valueOf should parse rgba(r,g,b,a)")
    void testValueOfRgba() throws Exception {
        Color c = Color.valueOf("1.0,0.0,0.0,0.5");
        assertEquals(1.0f, c.r(), EPSILON);
        assertEquals(0.0f, c.g(), EPSILON);
        assertEquals(0.0f, c.b(), EPSILON);
        assertEquals(0.5f, c.a(), EPSILON);
    }

    @Test
    @DisplayName("valueOf should parse argb(a,r,g,b)")
    void testValueOfArgb() throws Exception {
        Color c = Color.valueOf("argb(0.5,1.0,0.0,0.0)");
        assertEquals(1.0f, c.r(), EPSILON);
        assertEquals(0.0f, c.g(), EPSILON);
        assertEquals(0.0f, c.b(), EPSILON);
        assertEquals(0.5f, c.a(), EPSILON);
    }

    @Test
    @DisplayName("valueOf should parse comma-separated values")
    void testValueOfCommaSeparated() throws Exception {
        Color c = Color.valueOf("1.0,0.5,0.0");
        assertEquals(1.0f, c.r(), EPSILON);
        assertEquals(0.5f, c.g(), EPSILON);
        assertEquals(0.0f, c.b(), EPSILON);
    }

    @Test
    @DisplayName("valueOf should clamp values")
    void testValueOfClamp() throws Exception {
        Color c = Color.valueOf("1.5,0.5,-0.5");
        assertEquals(1.0f, c.r(), EPSILON);
        assertEquals(0.5f, c.g(), EPSILON);
        assertEquals(0.0f, c.b(), EPSILON);
    }

    @Test
    @DisplayName("valueOf should handle empty string")
    void testValueOfEmpty() throws Exception {
        Color c = Color.valueOf("");
        assertEquals(0.0f, c.r(), EPSILON);
        assertEquals(0.0f, c.g(), EPSILON);
        assertEquals(0.0f, c.b(), EPSILON);
        assertEquals(1.0f, c.a(), EPSILON);
    }

    @Test
    @DisplayName("valueOf should throw exception for invalid format")
    void testValueOfInvalid() {
        assertThrows(Exception.class, () -> Color.valueOf("#12"));
        assertThrows(Exception.class, () -> Color.valueOf("1.0,2.0"));
    }

    // ==================== with Methods ====================

    @Test
    @DisplayName("withR should change red component (float)")
    void testWithRFloat() {
        Color c1 = new Color(0.5f, 0.6f, 0.7f, 0.8f);
        Color c2 = c1.withR(0.9f);
        assertEquals(0.9f, c2.r(), EPSILON);
        assertEquals(0.6f, c2.g(), EPSILON);
        assertEquals(0.7f, c2.b(), EPSILON);
        assertEquals(0.8f, c2.a(), EPSILON);
    }

    @Test
    @DisplayName("withG should change green component (float)")
    void testWithGFloat() {
        Color c1 = new Color(0.5f, 0.6f, 0.7f, 0.8f);
        Color c2 = c1.withG(0.9f);
        assertEquals(0.5f, c2.r(), EPSILON);
        assertEquals(0.9f, c2.g(), EPSILON);
        assertEquals(0.7f, c2.b(), EPSILON);
        assertEquals(0.8f, c2.a(), EPSILON);
    }

    @Test
    @DisplayName("withB should change blue component (float)")
    void testWithBFloat() {
        Color c1 = new Color(0.5f, 0.6f, 0.7f, 0.8f);
        Color c2 = c1.withB(0.9f);
        assertEquals(0.5f, c2.r(), EPSILON);
        assertEquals(0.6f, c2.g(), EPSILON);
        assertEquals(0.9f, c2.b(), EPSILON);
        assertEquals(0.8f, c2.a(), EPSILON);
    }

    @Test
    @DisplayName("withA should change alpha component (float)")
    void testWithAFloat() {
        Color c1 = new Color(0.5f, 0.6f, 0.7f, 0.8f);
        Color c2 = c1.withA(0.3f);
        assertEquals(0.5f, c2.r(), EPSILON);
        assertEquals(0.6f, c2.g(), EPSILON);
        assertEquals(0.7f, c2.b(), EPSILON);
        assertEquals(0.3f, c2.a(), EPSILON);
    }

    @Test
    @DisplayName("withR should change red component (int)")
    void testWithRInt() {
        Color c1 = new Color(128, 128, 128, 255);
        Color c2 = c1.withR(255);
        assertEquals(1.0f, c2.r(), EPSILON);
    }

    @Test
    @DisplayName("withG should change green component (int)")
    void testWithGInt() {
        Color c1 = new Color(128, 128, 128, 255);
        Color c2 = c1.withG(255);
        assertEquals(1.0f, c2.g(), EPSILON);
    }

    @Test
    @DisplayName("withB should change blue component (int)")
    void testWithBInt() {
        Color c1 = new Color(128, 128, 128, 255);
        Color c2 = c1.withB(255);
        assertEquals(1.0f, c2.b(), EPSILON);
    }

    @Test
    @DisplayName("withA should change alpha component (int)")
    void testWithAInt() {
        Color c1 = new Color(128, 128, 128, 255);
        Color c2 = c1.withA(128);
        assertEquals(128 / 255.0f, c2.a(), EPSILON);
    }

    // ==================== toString Tests ====================

    @Test
    @DisplayName("toString should format as rgba")
    void testToString() {
        Color c = new Color(0.5f, 0.6f, 0.7f, 0.8f);
        String s = c.toString();
        assertTrue(s.startsWith("rgba("));
        assertTrue(s.contains("0.5"));
        assertTrue(s.contains("0.6"));
        assertTrue(s.contains("0.7"));
        assertTrue(s.contains("0.8"));
    }

    @Test
    @DisplayName("toStringSharp should format as hex")
    void testToStringSharp() {
        Color c = new Color(255, 0, 128, 255);
        String s = c.toStringSharp();
        assertTrue(s.startsWith("#"));
        assertTrue(s.contains("FF"));
        assertTrue(s.contains("00"));
        assertTrue(s.contains("80"));
    }

    @Test
    @DisplayName("toStringSharp should include alpha when < 1")
    void testToStringSharpWithAlpha() {
        Color c = new Color(255, 0, 128, 200);
        String s = c.toStringSharp();
        assertEquals(9, s.length()); // #RRGGBBAA
    }

    @Test
    @DisplayName("toStringSharp should omit alpha when = 1")
    void testToStringSharpWithoutAlpha() {
        Color c = new Color(255, 0, 128, 255);
        String s = c.toStringSharp();
        assertEquals(7, s.length()); // #RRGGBB
    }

    // ==================== Edge Cases ====================

    @Test
    @DisplayName("valueOf should handle whitespace")
    void testValueOfWhitespace() throws Exception {
        Color c = Color.valueOf("  rgb( 1.0 , 0.0 , 0.0 )  ");
        assertEquals(1.0f, c.r(), EPSILON);
        assertEquals(0.0f, c.g(), EPSILON);
        assertEquals(0.0f, c.b(), EPSILON);
    }

    @Test
    @DisplayName("Named colors should be accessible")
    void testNamedColors() {
        assertNotNull(Color.ALICE_BLUE);
        assertNotNull(Color.ANTIQUE_WHITE);
        assertNotNull(Color.CRIMSON);
        assertNotNull(Color.DODGER_BLUE);
        assertNotNull(Color.FOREST_GREEN);
        assertNotNull(Color.YELLOW_GREEN);
    }
}
