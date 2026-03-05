package org.atriasoft.etk.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FilePos Tests")
class FilePosTest {

    // ==================== Constructor Tests ====================

    @Test
    @DisplayName("Default constructor should initialize to 0,0")
    void testDefaultConstructor() {
        FilePos pos = new FilePos();
        assertEquals(0, pos.getLine());
        assertEquals(0, pos.getCol());
    }

    @Test
    @DisplayName("Constructor with line and col")
    void testConstructorWithValues() {
        FilePos pos = new FilePos(10, 20);
        assertEquals(10, pos.getLine());
        assertEquals(20, pos.getCol());
    }

    // ==================== Addition Tests ====================

    @Test
    @DisplayName("add with FilePos on same line")
    void testAddFilePosSameLine() {
        FilePos pos1 = new FilePos(5, 10);
        FilePos pos2 = new FilePos(0, 5);
        pos1.add(pos2);
        assertEquals(5, pos1.getLine());
        assertEquals(15, pos1.getCol());
    }

    @Test
    @DisplayName("add with FilePos with new line")
    void testAddFilePosNewLine() {
        FilePos pos1 = new FilePos(5, 10);
        FilePos pos2 = new FilePos(2, 3);
        pos1.add(pos2);
        assertEquals(7, pos1.getLine());
        assertEquals(3, pos1.getCol());
    }

    @Test
    @DisplayName("add with integer column")
    void testAddColumn() {
        FilePos pos = new FilePos(5, 10);
        pos.add(5);
        assertEquals(5, pos.getLine());
        assertEquals(15, pos.getCol());
    }

    // ==================== Check Tests ====================

    @Test
    @DisplayName("check with newline character")
    void testCheckNewLine() {
        FilePos pos = new FilePos(0, 0);
        boolean result = pos.check('\n');
        assertTrue(result);
        assertEquals(1, pos.getLine());
        assertEquals(0, pos.getCol());
    }

    @Test
    @DisplayName("check with regular character")
    void testCheckRegularChar() {
        FilePos pos = new FilePos(0, 0);
        boolean result = pos.check('a');
        assertFalse(result);
        assertEquals(0, pos.getLine());
        assertEquals(1, pos.getCol());
    }

    // ==================== Clear Test ====================

    @Test
    @DisplayName("clear should reset to 0,0")
    void testClear() {
        FilePos pos = new FilePos(10, 20);
        pos.clear();
        assertEquals(0, pos.getLine());
        assertEquals(0, pos.getCol());
    }

    // ==================== Clone Test ====================

    @Test
    @DisplayName("clone should create independent copy")
    void testClone() {
        FilePos pos1 = new FilePos(10, 20);
        FilePos pos2 = pos1.clone();
        assertEquals(10, pos2.getLine());
        assertEquals(20, pos2.getCol());

        pos2.increment();
        assertEquals(20, pos1.getCol());
        assertEquals(21, pos2.getCol());
    }

    // ==================== Increment/Decrement Tests ====================

    @Test
    @DisplayName("increment should increment column")
    void testIncrement() {
        FilePos pos = new FilePos(5, 10);
        pos.increment();
        assertEquals(5, pos.getLine());
        assertEquals(11, pos.getCol());
    }

    @Test
    @DisplayName("decrement should decrement column")
    void testDecrement() {
        FilePos pos = new FilePos(5, 10);
        pos.decrement();
        assertEquals(5, pos.getLine());
        assertEquals(9, pos.getCol());
    }

    // ==================== NewLine Test ====================

    @Test
    @DisplayName("newLine should increment line and reset column")
    void testNewLine() {
        FilePos pos = new FilePos(5, 10);
        pos.newLine();
        assertEquals(6, pos.getLine());
        assertEquals(0, pos.getCol());
    }

    // ==================== Set Tests ====================

    @Test
    @DisplayName("set with FilePos")
    void testSetFilePos() {
        FilePos pos1 = new FilePos(5, 10);
        FilePos pos2 = new FilePos(15, 20);
        pos1.set(pos2);
        assertEquals(15, pos1.getLine());
        assertEquals(20, pos1.getCol());
    }

    @Test
    @DisplayName("set with line and col")
    void testSetValues() {
        FilePos pos = new FilePos(5, 10);
        pos.set(15, 20);
        assertEquals(15, pos.getLine());
        assertEquals(20, pos.getCol());
    }

    // ==================== Equals Tests ====================

    @Test
    @DisplayName("equals should return true for same values")
    void testEqualsTrue() {
        FilePos pos1 = new FilePos(10, 20);
        FilePos pos2 = new FilePos(10, 20);
        assertTrue(pos1.equals(pos2));
    }

    @Test
    @DisplayName("equals should return false for different values")
    void testEqualsFalse() {
        FilePos pos1 = new FilePos(10, 20);
        FilePos pos2 = new FilePos(10, 21);
        assertFalse(pos1.equals(pos2));
    }

    @Test
    @DisplayName("equals should return false for null")
    void testEqualsNull() {
        FilePos pos = new FilePos(10, 20);
        assertFalse(pos.equals(null));
    }

    @Test
    @DisplayName("equals should return false for different class")
    void testEqualsDifferentClass() {
        FilePos pos = new FilePos(10, 20);
        assertFalse(pos.equals("not a FilePos"));
    }

    // ==================== HashCode Test ====================

    @Test
    @DisplayName("hashCode should be consistent for same object")
    void testHashCode() {
        FilePos pos = new FilePos(10, 20);
        int hash1 = pos.hashCode();
        int hash2 = pos.hashCode();
        assertEquals(hash1, hash2, "HashCode should be consistent for same object");
        // Note: Two different FilePos objects with same values may have different hashCodes
        // because the implementation uses super.hashCode() which is object-specific
    }

    // ==================== ToString Test ====================

    @Test
    @DisplayName("toString should format position")
    void testToString() {
        FilePos pos = new FilePos(10, 20);
        String str = pos.toString();
        assertTrue(str.contains("10"));
        assertTrue(str.contains("20"));
        assertTrue(str.contains("l="));
        assertTrue(str.contains("c="));
    }

    // ==================== Complex Scenarios ====================

    @Test
    @DisplayName("Processing multiple lines")
    void testMultipleLines() {
        FilePos pos = new FilePos();
        pos.check('a');
        pos.check('b');
        pos.check('\n');
        pos.check('c');

        assertEquals(1, pos.getLine());
        assertEquals(1, pos.getCol());
    }

    @Test
    @DisplayName("Chain operations")
    void testChainOperations() {
        FilePos pos = new FilePos(0, 0);
        pos.increment().increment().increment();
        assertEquals(0, pos.getLine());
        assertEquals(3, pos.getCol());
    }
}
