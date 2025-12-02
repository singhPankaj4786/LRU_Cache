package com.pankaj.cache;

import java.util.HashMap;
import java.util.Map;

public class LRUCache<K, V> {

    private final int capacity;
    private final Map<K, CacheNode<K, V>> map;
    private final DoublyLinkedList<K, V> dll;

    public LRUCache(int capacity) {
        if (capacity <= 0)
            throw new IllegalArgumentException("Capacity must be > 0");

        this.capacity = capacity;
        this.map = new HashMap<>();
        this.dll = new DoublyLinkedList<>();
    }

    public V get(K key) {
        CacheNode<K, V> node = map.get(key);

        if (node == null) {
            return null;
        }

        // Move node to the front (most recently used)
        dll.remove(node);
        dll.addFirst(node);

        return node.value;
    }

    public void put(K key, V value) {

        // If key already exists → update + move to head
        if (map.containsKey(key)) {
            CacheNode<K, V> node = map.get(key);
            node.value = value;

            dll.remove(node);
            dll.addFirst(node);
            return;
        }

        // Key does NOT exist → create new node
        CacheNode<K, V> newNode = new CacheNode<>(key, value);
        dll.addFirst(newNode);
        map.put(key, newNode);

        // Evict if over capacity
        if (map.size() > capacity) {
            CacheNode<K, V> toRemove = dll.removeLast();
            map.remove(toRemove.key);
        }
    }

    public int size() {
        return map.size();
    }
}
