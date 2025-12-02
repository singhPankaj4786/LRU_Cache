package com.pankaj.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ThreadSafeLRUCache<K, V> {

    private final int capacity;
    private final Map<K, CacheNode<K, V>> map;
    private final DoublyLinkedList<K, V> dll;

    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();
    private final Lock writeLock = rwLock.writeLock();

    public ThreadSafeLRUCache(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("Invalid capacity");
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.dll = new DoublyLinkedList<>();
    }
    public int size() {
        readLock.lock();
        try {
            return map.size();
        } finally {
            readLock.unlock();
        }
    }


//    public V get(K key) {
//        readLock.lock();
//        try {
//            CacheNode<K, V> node = map.get(key);
//            if (node == null) return null;
//
//            // ⭐ Switch to write lock (safe upgrade)
//            readLock.unlock();
//            writeLock.lock();
//
//            try {
//                dll.remove(node);
//                dll.addFirst(node);
//                return node.value;
//            } finally {
//                writeLock.unlock();
//                readLock.lock();
//            }
//
//        } finally {
//            readLock.unlock();
//        }
//    }

    public V get(K key) {
        CacheNode<K, V> node;

        // Step 1: Read lock for lookup
        readLock.lock();
        try {
            node = map.get(key);
            if (node == null) {
                return null;
            }
        } finally {
            readLock.unlock();
        }

        // Step 2: Now take write lock to update LRU order
        writeLock.lock();
        try {
            // Re-validate (another thread may have changed it)
            CacheNode<K, V> freshNode = map.get(key);
            if (freshNode != null) {
                dll.remove(freshNode);
                dll.addFirst(freshNode);
                return freshNode.value;
            }
            return null; // key was removed in between
        } finally {
            writeLock.unlock();
        }
    }

//    public void put(K key, V value) {
//        writeLock.lock();
//        try {
//            CacheNode<K, V> node = map.get(key);
//
//            if (node != null) {
//                node.value = value;
//                dll.remove(node);
//                dll.addFirst(node);
//                return;
//            }
//
//            CacheNode<K, V> newNode = new CacheNode<>(key, value);
//            dll.addFirst(newNode);
//            map.put(key, newNode);
//
//            if (map.size() > capacity) {
//                CacheNode<K, V> lru = dll.removeLast();
//                map.remove(lru.key);
//            }
//
//        } finally {
//            writeLock.unlock();
//        }
//    }

    public void put(K key, V value) {
        writeLock.lock();
        try {
            CacheNode<K, V> node = map.get(key);

            if (node != null) {
                node.value = value;
                dll.remove(node);
                dll.addFirst(node);
                return;
            }

            CacheNode<K, V> newNode = new CacheNode<>(key, value);
            dll.addFirst(newNode);
            map.put(key, newNode);

            if (map.size() > capacity) {
                CacheNode<K, V> lru = dll.removeLast();
                map.remove(lru.key);
            }
        } finally {
            writeLock.unlock();
        }
    }

}
