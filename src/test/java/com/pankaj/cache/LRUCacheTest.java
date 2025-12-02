package com.pankaj.cache;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LRUCacheTest {

    @Test
    public void testBasicLRU() {
        LRUCache<Integer, String> cache = new LRUCache<>(2);

        cache.put(1, "A");
        cache.put(2, "B");

        assertEquals("A", cache.get(1));  // 1 becomes MRU

        cache.put(3, "C"); // evicts key 2 (LRU)

        assertNull(cache.get(2));
        assertEquals("A", cache.get(1));
        assertEquals("C", cache.get(3));
    }

    @Test
    public void testUpdateKeyMovesToHead() {
        LRUCache<Integer, String> cache = new LRUCache<>(2);

        cache.put(1, "A");
        cache.put(2, "B");

        // Update key 1 → should move to MRU
        cache.put(1, "A_updated");

        cache.put(3, "C"); // should evict B

        assertNull(cache.get(2));
        assertEquals("A_updated", cache.get(1));
    }
}
