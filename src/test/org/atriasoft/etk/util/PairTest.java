package org.atriasoft.etk.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pair Tests")
class PairTest {

    // ==================== Constructor Tests ====================

    @Test
    @DisplayName("Constructor should create pair with specified values")
    void testConstructor() {
        Pair<String, Integer> p = new Pair<>("test", 42);
        assertEquals("test", p.first);
        assertEquals(42, p.second);
    }

    @Test
    @DisplayName("of() factory method should create pair")
    void testOf() {
        Pair<String, Integer> p = Pair.of("test", 42);
        assertEquals("test", p.first);
        assertEquals(42, p.second);
    }

    // ==================== Equality Tests ====================

    @Test
    @DisplayName("equals should return true for same values")
    void testEqualsTrue() {
        Pair<String, Integer> p1 = Pair.of("test", 42);
        Pair<String, Integer> p2 = Pair.of("test", 42);
        assertEquals(p1, p2);
    }

    @Test
    @DisplayName("equals should return false for different first")
    void testEqualsFalseDifferentFirst() {
        Pair<String, Integer> p1 = Pair.of("test1", 42);
        Pair<String, Integer> p2 = Pair.of("test2", 42);
        assertNotEquals(p1, p2);
    }

    @Test
    @DisplayName("equals should return false for different second")
    void testEqualsFalseDifferentSecond() {
        Pair<String, Integer> p1 = Pair.of("test", 42);
        Pair<String, Integer> p2 = Pair.of("test", 43);
        assertNotEquals(p1, p2);
    }

    @Test
    @DisplayName("equals should return true for same instance")
    void testEqualsSameInstance() {
        Pair<String, Integer> p = Pair.of("test", 42);
        assertEquals(p, p);
    }

    @Test
    @DisplayName("equals should return false for null")
    void testEqualsNull() {
        Pair<String, Integer> p = Pair.of("test", 42);
        assertNotEquals(p, null);
    }

    @Test
    @DisplayName("equals should return false for different class")
    void testEqualsDifferentClass() {
        Pair<String, Integer> p = Pair.of("test", 42);
        assertNotEquals(p, "not a pair");
    }

    // ==================== HashCode Tests ====================

    @Test
    @DisplayName("hashCode should be consistent")
    void testHashCodeConsistent() {
        Pair<String, Integer> p = Pair.of("test", 42);
        int hash1 = p.hashCode();
        int hash2 = p.hashCode();
        assertEquals(hash1, hash2);
    }

    @Test
    @DisplayName("hashCode should be equal for equal pairs")
    void testHashCodeEqual() {
        Pair<String, Integer> p1 = Pair.of("test", 42);
        Pair<String, Integer> p2 = Pair.of("test", 42);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    @DisplayName("hashCode should likely differ for different pairs")
    void testHashCodeDifferent() {
        Pair<String, Integer> p1 = Pair.of("test1", 42);
        Pair<String, Integer> p2 = Pair.of("test2", 42);
        assertNotEquals(p1.hashCode(), p2.hashCode());
    }

    // ==================== with Methods ====================

    @Test
    @DisplayName("withFirst should change first value")
    void testWithFirst() {
        Pair<String, Integer> p1 = Pair.of("test", 42);
        Pair<String, Integer> p2 = p1.withFirst("newtest");
        assertEquals("newtest", p2.first);
        assertEquals(42, p2.second);
        // Original should be unchanged
        assertEquals("test", p1.first);
    }

    @Test
    @DisplayName("withSecond should change second value")
    void testWithSecond() {
        Pair<String, Integer> p1 = Pair.of("test", 42);
        Pair<String, Integer> p2 = p1.withSecond(100);
        assertEquals("test", p2.first);
        assertEquals(100, p2.second);
        // Original should be unchanged
        assertEquals(42, p1.second);
    }

    // ==================== toString Test ====================

    @Test
    @DisplayName("toString should format as (first, second)")
    void testToString() {
        Pair<String, Integer> p = Pair.of("test", 42);
        String s = p.toString();
        assertEquals("(test, 42)", s);
    }

    // ==================== Generic Type Tests ====================

    @Test
    @DisplayName("Pair should work with different types")
    void testDifferentTypes() {
        Pair<Integer, String> p1 = Pair.of(42, "test");
        assertEquals(42, p1.first);
        assertEquals("test", p1.second);

        Pair<Double, Boolean> p2 = Pair.of(3.14, true);
        assertEquals(3.14, p2.first);
        assertTrue(p2.second);
    }

    @Test
    @DisplayName("Pair should work with same types")
    void testSameTypes() {
        Pair<Integer, Integer> p = Pair.of(10, 20);
        assertEquals(10, p.first);
        assertEquals(20, p.second);
    }

    @Test
    @DisplayName("Pair should work with null values")
    void testNullValues() {
        // This will throw NullPointerException in equals() if both are null
        // but the Pair itself can be created
        Pair<String, String> p = new Pair<>(null, "test");
        assertNull(p.first);
        assertEquals("test", p.second);
    }

    // ==================== Edge Cases ====================

    @Test
    @DisplayName("Nested pairs should work")
    void testNestedPairs() {
        Pair<String, Integer> inner = Pair.of("inner", 42);
        Pair<Pair<String, Integer>, String> outer = Pair.of(inner, "outer");
        assertEquals(inner, outer.first);
        assertEquals("outer", outer.second);
        assertEquals("inner", outer.first.first);
        assertEquals(42, outer.first.second);
    }
}
