package org.atriasoft.etk;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Dimension1f Tests")
class Dimension1fTest {

    private static final float EPSILON = 0.001f;

    // ==================== Constructor Tests ====================

    @Test
    @DisplayName("Default constructor should create zero pixel dimension")
    void testDefaultConstructor() {
        Dimension1f dim = new Dimension1f();
        assertEquals(0.0f, dim.size(), EPSILON);
        assertEquals(Distance.PIXEL, dim.type());
    }

    @Test
    @DisplayName("Constructor with size should create pixel dimension")
    void testConstructorWithSize() {
        Dimension1f dim = new Dimension1f(100.0f);
        assertEquals(100.0f, dim.size(), EPSILON);
        assertEquals(Distance.PIXEL, dim.type());
    }

    @Test
    @DisplayName("Constructor with size and type")
    void testConstructorWithSizeAndType() {
        Dimension1f dim = new Dimension1f(50.0f, Distance.POURCENT);
        assertEquals(50.0f, dim.size(), EPSILON);
        assertEquals(Distance.POURCENT, dim.type());
    }

    @Test
    @DisplayName("Record canonical constructor")
    void testRecordConstructor() {
        Dimension1f dim = new Dimension1f(10.5f, Distance.CENTIMETER);
        assertEquals(10.5f, dim.size(), EPSILON);
        assertEquals(Distance.CENTIMETER, dim.type());
    }

    // ==================== Constants Tests ====================

    @Test
    @DisplayName("ZERO constant")
    void testZeroConstant() {
        assertEquals(0.0f, Dimension1f.ZERO.size(), EPSILON);
        assertEquals(Distance.PIXEL, Dimension1f.ZERO.type());
    }

    @Test
    @DisplayName("Conversion constants should have correct values")
    void testConversionConstants() {
        assertEquals(1.0f / 25.4f, Dimension1f.INCH_TO_MILLIMETER, EPSILON);
        assertEquals(1.0f / 304.8f, Dimension1f.FOOT_TO_MILLIMETER, EPSILON);
        assertEquals(1.0f / 1000.0f, Dimension1f.METER_TO_MILLIMETER, EPSILON);
        assertEquals(1.0f / 10.0f, Dimension1f.CENTIMETER_TO_MILLIMETER, EPSILON);
        assertEquals(1.0f / 1000000.0f, Dimension1f.KILOMETER_TO_MILLIMETER, EPSILON);

        assertEquals(25.4f, Dimension1f.MILLIMETER_TO_INCH, EPSILON);
        assertEquals(304.8f, Dimension1f.MILLIMETER_TO_FOOT, EPSILON);
        assertEquals(1000.0f, Dimension1f.MILLIMETER_TO_METER, EPSILON);
        assertEquals(10.0f, Dimension1f.MILLIMETER_TO_CENTIMETER, EPSILON);
        assertEquals(1000000.0f, Dimension1f.MILLIMETER_TO_KILOMETER, EPSILON);
    }

    // ==================== valueOf() Tests ====================

    @Test
    @DisplayName("valueOf with pixels")
    void testValueOfPixels() {
        Dimension1f dim = Dimension1f.valueOf("100px");
        assertEquals(100.0f, dim.size(), EPSILON);
        assertEquals(Distance.PIXEL, dim.type());
    }

    @Test
    @DisplayName("valueOf with percentage")
    void testValueOfPercentage() {
        Dimension1f dim = Dimension1f.valueOf("50%");
        assertEquals(50.0f, dim.size(), EPSILON);
        assertEquals(Distance.POURCENT, dim.type());
    }

    @Test
    @DisplayName("valueOf with centimeters")
    void testValueOfCentimeters() {
        Dimension1f dim = Dimension1f.valueOf("10cm");
        assertEquals(10.0f, dim.size(), EPSILON);
        assertEquals(Distance.CENTIMETER, dim.type());
    }

    @Test
    @DisplayName("valueOf with millimeters")
    void testValueOfMillimeters() {
        Dimension1f dim = Dimension1f.valueOf("5mm");
        assertEquals(5.0f, dim.size(), EPSILON);
        assertEquals(Distance.MILLIMETER, dim.type());
    }

    @Test
    @DisplayName("valueOf with meters")
    void testValueOfMeters() {
        Dimension1f dim = Dimension1f.valueOf("2m");
        assertEquals(2.0f, dim.size(), EPSILON);
        assertEquals(Distance.METER, dim.type());
    }

    @Test
    @DisplayName("valueOf with kilometers")
    void testValueOfKilometers() {
        Dimension1f dim = Dimension1f.valueOf("1km");
        assertEquals(1.0f, dim.size(), EPSILON);
        assertEquals(Distance.KILOMETER, dim.type());
    }

    @Test
    @DisplayName("valueOf with inches")
    void testValueOfInches() {
        Dimension1f dim = Dimension1f.valueOf("12in");
        assertEquals(12.0f, dim.size(), EPSILON);
        assertEquals(Distance.INCH, dim.type());
    }

    @Test
    @DisplayName("valueOf with feet")
    void testValueOfFeet() {
        Dimension1f dim = Dimension1f.valueOf("3ft");
        assertEquals(3.0f, dim.size(), EPSILON);
        assertEquals(Distance.FOOT, dim.type());
    }

    @Test
    @DisplayName("valueOf with decimal values")
    void testValueOfDecimal() {
        Dimension1f dim = Dimension1f.valueOf("10.5px");
        assertEquals(10.5f, dim.size(), EPSILON);
        assertEquals(Distance.PIXEL, dim.type());
    }

    // ==================== getPixel() Tests ====================

    @Test
    @DisplayName("getPixel should return size for pixel type")
    void testGetPixelForPixelType() {
        Dimension1f dim = new Dimension1f(100.0f, Distance.PIXEL);
        assertEquals(100.0f, dim.getPixel(200.0f), EPSILON);
    }

    @Test
    @DisplayName("getPixel should calculate percentage correctly")
    void testGetPixelForPercentage() {
        Dimension1f dim = new Dimension1f(50.0f, Distance.POURCENT);
        assertEquals(100.0f, dim.getPixel(200.0f), EPSILON);
    }

    @Test
    @DisplayName("getPixel should calculate for 100%")
    void testGetPixelFor100Percent() {
        Dimension1f dim = new Dimension1f(100.0f, Distance.POURCENT);
        assertEquals(200.0f, dim.getPixel(200.0f), EPSILON);
    }

    @Test
    @DisplayName("getPixel should calculate for 25%")
    void testGetPixelFor25Percent() {
        Dimension1f dim = new Dimension1f(25.0f, Distance.POURCENT);
        assertEquals(50.0f, dim.getPixel(200.0f), EPSILON);
    }

    @Test
    @DisplayName("getPixel should convert millimeters")
    void testGetPixelForMillimeters() {
        Dimension1f dim = new Dimension1f(25.4f, Distance.MILLIMETER);
        float expected = 25.4f * (72.0f / 25.4f);
        assertEquals(expected, dim.getPixel(200.0f), EPSILON);
    }

    @Test
    @DisplayName("getPixel should convert centimeters")
    void testGetPixelForCentimeters() {
        Dimension1f dim = new Dimension1f(1.0f, Distance.CENTIMETER);
        float expected = 1.0f * 0.1f * (72.0f / 25.4f);
        assertEquals(expected, dim.getPixel(200.0f), EPSILON);
    }

    @Test
    @DisplayName("getPixel should convert meters")
    void testGetPixelForMeters() {
        Dimension1f dim = new Dimension1f(1.0f, Distance.METER);
        float expected = 1.0f * 0.001f * (72.0f / 25.4f);
        assertEquals(expected, dim.getPixel(200.0f), EPSILON);
    }

    @Test
    @DisplayName("getPixel should convert inches")
    void testGetPixelForInches() {
        Dimension1f dim = new Dimension1f(1.0f, Distance.INCH);
        float expected = 1.0f * (1.0f / 25.4f) * (72.0f / 25.4f);
        assertEquals(expected, dim.getPixel(200.0f), EPSILON);
    }

    @Test
    @DisplayName("getPixel should convert feet")
    void testGetPixelForFeet() {
        Dimension1f dim = new Dimension1f(1.0f, Distance.FOOT);
        float expected = 1.0f * (1.0f / 304.8f) * (72.0f / 25.4f);
        assertEquals(expected, dim.getPixel(200.0f), EPSILON);
    }

    // ==================== With Methods ====================

    @Test
    @DisplayName("withSize should update size")
    void testWithSize() {
        Dimension1f dim = new Dimension1f(100.0f, Distance.PIXEL);
        Dimension1f result = dim.withSize(200.0f);
        assertEquals(200.0f, result.size(), EPSILON);
        assertEquals(Distance.PIXEL, result.type());
    }

    @Test
    @DisplayName("withType should update type")
    void testWithType() {
        Dimension1f dim = new Dimension1f(100.0f, Distance.PIXEL);
        Dimension1f result = dim.withType(Distance.POURCENT);
        assertEquals(100.0f, result.size(), EPSILON);
        assertEquals(Distance.POURCENT, result.type());
    }

    @Test
    @DisplayName("withSize should not modify original")
    void testWithSizeImmutability() {
        Dimension1f dim = new Dimension1f(100.0f, Distance.PIXEL);
        Dimension1f result = dim.withSize(200.0f);
        assertEquals(100.0f, dim.size(), EPSILON);
        assertEquals(200.0f, result.size(), EPSILON);
    }

    @Test
    @DisplayName("withType should not modify original")
    void testWithTypeImmutability() {
        Dimension1f dim = new Dimension1f(100.0f, Distance.PIXEL);
        Dimension1f result = dim.withType(Distance.CENTIMETER);
        assertEquals(Distance.PIXEL, dim.type());
        assertEquals(Distance.CENTIMETER, result.type());
    }

    // ==================== toString Tests ====================

    @Test
    @DisplayName("toString should format with unit for pixels")
    void testToStringPixels() {
        Dimension1f dim = new Dimension1f(100.0f, Distance.PIXEL);
        assertEquals("100.0px", dim.toString());
    }

    @Test
    @DisplayName("toString should format with unit for percentage")
    void testToStringPercentage() {
        Dimension1f dim = new Dimension1f(50.0f, Distance.POURCENT);
        assertEquals("50.0%", dim.toString());
    }

    @Test
    @DisplayName("toString should format with unit for centimeters")
    void testToStringCentimeters() {
        Dimension1f dim = new Dimension1f(10.0f, Distance.CENTIMETER);
        assertEquals("10.0cm", dim.toString());
    }

    @Test
    @DisplayName("toString should format decimal values")
    void testToStringDecimal() {
        Dimension1f dim = new Dimension1f(10.5f, Distance.PIXEL);
        assertEquals("10.5px", dim.toString());
    }

    // ==================== getType Tests ====================

    @Test
    @DisplayName("getType should return correct type")
    void testGetType() {
        Dimension1f dim1 = new Dimension1f(100.0f, Distance.PIXEL);
        assertEquals(Distance.PIXEL, dim1.getType());

        Dimension1f dim2 = new Dimension1f(50.0f, Distance.POURCENT);
        assertEquals(Distance.POURCENT, dim2.getType());

        Dimension1f dim3 = new Dimension1f(10.0f, Distance.CENTIMETER);
        assertEquals(Distance.CENTIMETER, dim3.getType());
    }

    // ==================== Edge Cases ====================

    @Test
    @DisplayName("Zero size should work correctly")
    void testZeroSize() {
        Dimension1f dim = new Dimension1f(0.0f, Distance.PIXEL);
        assertEquals(0.0f, dim.size(), EPSILON);
        assertEquals(0.0f, dim.getPixel(100.0f), EPSILON);
    }

    @Test
    @DisplayName("Negative size should be allowed")
    void testNegativeSize() {
        Dimension1f dim = new Dimension1f(-10.0f, Distance.PIXEL);
        assertEquals(-10.0f, dim.size(), EPSILON);
    }

    @Test
    @DisplayName("Very large size should work")
    void testLargeSize() {
        Dimension1f dim = new Dimension1f(Float.MAX_VALUE, Distance.PIXEL);
        assertEquals(Float.MAX_VALUE, dim.size(), EPSILON);
    }

    @Test
    @DisplayName("Very small size should work")
    void testSmallSize() {
        Dimension1f dim = new Dimension1f(0.001f, Distance.PIXEL);
        assertEquals(0.001f, dim.size(), EPSILON);
    }

    @Test
    @DisplayName("Zero percent should calculate correctly")
    void testZeroPercent() {
        Dimension1f dim = new Dimension1f(0.0f, Distance.POURCENT);
        assertEquals(0.0f, dim.getPixel(200.0f), EPSILON);
    }

    @Test
    @DisplayName("200 percent should calculate correctly")
    void test200Percent() {
        Dimension1f dim = new Dimension1f(200.0f, Distance.POURCENT);
        assertEquals(400.0f, dim.getPixel(200.0f), EPSILON);
    }

    // ==================== Round-trip Tests ====================

    @Test
    @DisplayName("valueOf and toString should be inverse operations")
    void testRoundTrip() {
        String[] inputs = {"100px", "50%", "10cm", "5mm", "2m", "1km", "12in", "3ft"};

        for (String input : inputs) {
            Dimension1f dim = Dimension1f.valueOf(input);
            String output = dim.toString();
            Dimension1f dim2 = Dimension1f.valueOf(output);

            assertEquals(dim.size(), dim2.size(), EPSILON, "Round-trip failed for " + input);
            assertEquals(dim.type(), dim2.type(), "Round-trip failed for " + input);
        }
    }

    @Test
    @DisplayName("Record equality should work correctly")
    void testRecordEquality() {
        Dimension1f dim1 = new Dimension1f(100.0f, Distance.PIXEL);
        Dimension1f dim2 = new Dimension1f(100.0f, Distance.PIXEL);
        Dimension1f dim3 = new Dimension1f(100.0f, Distance.POURCENT);
        Dimension1f dim4 = new Dimension1f(200.0f, Distance.PIXEL);

        assertEquals(dim1, dim2);
        assertNotEquals(dim1, dim3);
        assertNotEquals(dim1, dim4);
    }

    @Test
    @DisplayName("Record hashCode should be consistent")
    void testRecordHashCode() {
        Dimension1f dim1 = new Dimension1f(100.0f, Distance.PIXEL);
        Dimension1f dim2 = new Dimension1f(100.0f, Distance.PIXEL);

        assertEquals(dim1.hashCode(), dim2.hashCode());
    }

    @Test
    @DisplayName("Record toString should include all fields")
    void testRecordToStringIncludesFields() {
        Dimension1f dim = new Dimension1f(100.0f, Distance.PIXEL);
        String str = dim.toString();
        assertTrue(str.contains("100"));
        assertTrue(str.contains("px"));
    }
}
