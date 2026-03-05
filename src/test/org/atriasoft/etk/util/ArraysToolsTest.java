package org.atriasoft.etk.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ArraysTools Tests")
class ArraysToolsTest {

    // ==================== fill Tests ====================

    @Test
    @DisplayName("fill should fill array with value")
    void testFill() {
        Integer[] array = new Integer[5];
        ArraysTools.fill(array, 42);
        for (Integer value : array) {
            assertEquals(42, value);
        }
    }

    @Test
    @DisplayName("fill should handle null array")
    void testFillNull() {
        assertDoesNotThrow(() -> ArraysTools.fill(null, 42));
    }

    // ==================== fill2 Tests ====================

    @Test
    @DisplayName("fill2 should fill 2D float array")
    void testFill2Float() {
        float[][] array = new float[3][4];
        ArraysTools.fill2(array, 3.14f);
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                assertEquals(3.14f, array[i][j], 0.001f);
            }
        }
    }

    @Test
    @DisplayName("fill2 should fill 2D object array")
    void testFill2Object() {
        String[][] array = new String[2][3];
        ArraysTools.fill2(array, "test");
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                assertEquals("test", array[i][j]);
            }
        }
    }

    @Test
    @DisplayName("fill2 should handle null array")
    void testFill2Null() {
        assertDoesNotThrow(() -> ArraysTools.fill2((float[][]) null, 1.0f));
        assertDoesNotThrow(() -> ArraysTools.fill2((String[][]) null, "test"));
    }

    // ==================== toPrimitive Tests ====================

    @Test
    @DisplayName("toPrimitive should convert Byte array")
    void testToPrimitiveByte() {
        Byte[] input = {1, 2, 3, 4, 5};
        byte[] output = ArraysTools.toPrimitive(input);
        assertEquals(5, output.length);
        for (int i = 0; i < input.length; i++) {
            assertEquals(input[i], output[i]);
        }
    }

    @Test
    @DisplayName("toPrimitive should convert Integer array")
    void testToPrimitiveInteger() {
        Integer[] input = {10, 20, 30, 40, 50};
        int[] output = ArraysTools.toPrimitive(input);
        assertEquals(5, output.length);
        for (int i = 0; i < input.length; i++) {
            assertEquals(input[i], output[i]);
        }
    }

    @Test
    @DisplayName("toPrimitive should convert Long array")
    void testToPrimitiveLong() {
        Long[] input = {100L, 200L, 300L};
        long[] output = ArraysTools.toPrimitive(input);
        assertEquals(3, output.length);
        for (int i = 0; i < input.length; i++) {
            assertEquals(input[i], output[i]);
        }
    }

    @Test
    @DisplayName("toPrimitive should convert boolean array")
    void testToPrimitiveBoolean() {
        boolean[] input = {true, false, true};
        boolean[] output = ArraysTools.toPrimitive(input);
        assertEquals(3, output.length);
        for (int i = 0; i < input.length; i++) {
            assertEquals(input[i], output[i]);
        }
    }

    // ==================== listToPrimitive Tests ====================

    @Test
    @DisplayName("listByteToPrimitive should convert list")
    void testListByteToPrimitive() {
        List<Object> list = new ArrayList<>();
        list.add((byte) 1);
        list.add((byte) 2);
        list.add((byte) 3);

        byte[] output = ArraysTools.listByteToPrimitive(list);
        assertEquals(3, output.length);
        assertEquals((byte) 1, output[0]);
        assertEquals((byte) 2, output[1]);
        assertEquals((byte) 3, output[2]);
    }

    @Test
    @DisplayName("listIntegerToPrimitive should convert list")
    void testListIntegerToPrimitive() {
        List<Object> list = new ArrayList<>();
        list.add(10);
        list.add(20);
        list.add(30);

        int[] output = ArraysTools.listIntegerToPrimitive(list);
        assertEquals(3, output.length);
        assertEquals(10, output[0]);
        assertEquals(20, output[1]);
        assertEquals(30, output[2]);
    }

    @Test
    @DisplayName("listLongToPrimitive should convert list")
    void testListLongToPrimitive() {
        List<Object> list = new ArrayList<>();
        list.add(100L);
        list.add(200L);
        list.add(300L);

        long[] output = ArraysTools.listLongToPrimitive(list);
        assertEquals(3, output.length);
        assertEquals(100L, output[0]);
        assertEquals(200L, output[1]);
        assertEquals(300L, output[2]);
    }

    @Test
    @DisplayName("listBooleanToPrimitive should convert list")
    void testListBooleanToPrimitive() {
        List<Object> list = new ArrayList<>();
        list.add(true);
        list.add(false);
        list.add(true);

        boolean[] output = ArraysTools.listBooleanToPrimitive(list);
        assertEquals(3, output.length);
        assertTrue(output[0]);
        assertFalse(output[1]);
        assertTrue(output[2]);
    }

    @Test
    @DisplayName("listFloatToPrimitive should convert list")
    void testListFloatToPrimitive() {
        List<Object> list = new ArrayList<>();
        list.add(1.5f);
        list.add(2.5f);
        list.add(3.5f);

        float[] output = ArraysTools.listFloatToPrimitive(list);
        assertEquals(3, output.length);
        assertEquals(1.5f, output[0], 0.001f);
        assertEquals(2.5f, output[1], 0.001f);
        assertEquals(3.5f, output[2], 0.001f);
    }

    @Test
    @DisplayName("listDoubleToPrimitive should convert list")
    void testListDoubleToPrimitive() {
        List<Object> list = new ArrayList<>();
        list.add(1.5);
        list.add(2.5);
        list.add(3.5);

        double[] output = ArraysTools.listDoubleToPrimitive(list);
        assertEquals(3, output.length);
        assertEquals(1.5, output[0], 0.001);
        assertEquals(2.5, output[1], 0.001);
        assertEquals(3.5, output[2], 0.001);
    }

    // ==================== listToPrimitiveAuto Tests ====================

    @Test
    @DisplayName("listToPrimitiveAuto should convert byte list")
    void testListToPrimitiveAutoByte() {
        List<Object> list = new ArrayList<>();
        list.add((byte) 1);
        list.add((byte) 2);

        byte[] output = (byte[]) ArraysTools.listToPrimitiveAuto(byte.class, list);
        assertNotNull(output);
        assertEquals(2, output.length);
    }

    @Test
    @DisplayName("listToPrimitiveAuto should convert int list")
    void testListToPrimitiveAutoInt() {
        List<Object> list = new ArrayList<>();
        list.add(10);
        list.add(20);

        int[] output = (int[]) ArraysTools.listToPrimitiveAuto(int.class, list);
        assertNotNull(output);
        assertEquals(2, output.length);
    }

    @Test
    @DisplayName("listToPrimitiveAuto should convert long list")
    void testListToPrimitiveAutoLong() {
        List<Object> list = new ArrayList<>();
        list.add(100L);
        list.add(200L);

        long[] output = (long[]) ArraysTools.listToPrimitiveAuto(long.class, list);
        assertNotNull(output);
        assertEquals(2, output.length);
    }

    @Test
    @DisplayName("listToPrimitiveAuto should convert boolean list")
    void testListToPrimitiveAutoBoolean() {
        List<Object> list = new ArrayList<>();
        list.add(true);
        list.add(false);

        boolean[] output = (boolean[]) ArraysTools.listToPrimitiveAuto(boolean.class, list);
        assertNotNull(output);
        assertEquals(2, output.length);
    }

    @Test
    @DisplayName("listToPrimitiveAuto should convert float list")
    void testListToPrimitiveAutoFloat() {
        List<Object> list = new ArrayList<>();
        list.add(1.5f);
        list.add(2.5f);

        float[] output = (float[]) ArraysTools.listToPrimitiveAuto(float.class, list);
        assertNotNull(output);
        assertEquals(2, output.length);
    }

    @Test
    @DisplayName("listToPrimitiveAuto should convert double list")
    void testListToPrimitiveAutoDouble() {
        List<Object> list = new ArrayList<>();
        list.add(1.5);
        list.add(2.5);

        double[] output = (double[]) ArraysTools.listToPrimitiveAuto(double.class, list);
        assertNotNull(output);
        assertEquals(2, output.length);
    }

    @Test
    @DisplayName("listToPrimitiveAuto should return null for unknown type")
    void testListToPrimitiveAutoUnknown() {
        List<Object> list = new ArrayList<>();
        list.add("test");

        Object output = ArraysTools.listToPrimitiveAuto(String.class, list);
        assertNull(output);
    }

    // ==================== primitivaArrayToSting Tests ====================

    @Test
    @DisplayName("primitivaArrayToSting should format byte array")
    void testPrimitivaArrayToStingByte() {
        byte[] array = {1, 2, 3};
        String output = ArraysTools.primitivaArrayToSting(array);
        assertEquals("1;2;3", output);
    }

    @Test
    @DisplayName("primitivaArrayToSting should format int array")
    void testPrimitivaArrayToStingInt() {
        int[] array = {10, 20, 30};
        String output = ArraysTools.primitivaArrayToSting(array);
        assertEquals("10;20;30", output);
    }

    @Test
    @DisplayName("primitivaArrayToSting should format long array")
    void testPrimitivaArrayToStingLong() {
        long[] array = {100L, 200L, 300L};
        String output = ArraysTools.primitivaArrayToSting(array);
        assertEquals("100;200;300", output);
    }

    @Test
    @DisplayName("primitivaArrayToSting should format boolean array")
    void testPrimitivaArrayToStingBoolean() {
        boolean[] array = {true, false, true};
        String output = ArraysTools.primitivaArrayToSting(array);
        assertEquals("true;false;true", output);
    }

    @Test
    @DisplayName("primitivaArrayToSting should format float array")
    void testPrimitivaArrayToStingFloat() {
        float[] array = {1.5f, 2.5f, 3.5f};
        String output = ArraysTools.primitivaArrayToSting(array);
        assertEquals("1.5;2.5;3.5", output);
    }

    @Test
    @DisplayName("primitivaArrayToSting should format double array")
    void testPrimitivaArrayToStingDouble() {
        double[] array = {1.5, 2.5, 3.5};
        String output = ArraysTools.primitivaArrayToSting(array);
        assertEquals("1.5;2.5;3.5", output);
    }

    @Test
    @DisplayName("primitivaArrayToSting should handle empty array")
    void testPrimitivaArrayToStingEmpty() {
        int[] array = {};
        String output = ArraysTools.primitivaArrayToSting(array);
        assertEquals("", output);
    }

    @Test
    @DisplayName("primitivaArrayToSting with StringBuilder should append")
    void testPrimitivaArrayToStingStringBuilder() {
        int[] array = {1, 2, 3};
        StringBuilder sb = new StringBuilder("prefix:");
        ArraysTools.primitivaArrayToSting(array, sb);
        assertEquals("prefix:1;2;3", sb.toString());
    }

    // ==================== Edge Cases ====================

    @Test
    @DisplayName("Empty list should convert to empty array")
    void testEmptyList() {
        List<Object> list = new ArrayList<>();
        int[] output = ArraysTools.listIntegerToPrimitive(list);
        assertEquals(0, output.length);
    }

    @Test
    @DisplayName("Single element list should work")
    void testSingleElement() {
        List<Object> list = new ArrayList<>();
        list.add(42);
        int[] output = ArraysTools.listIntegerToPrimitive(list);
        assertEquals(1, output.length);
        assertEquals(42, output[0]);
    }
}
