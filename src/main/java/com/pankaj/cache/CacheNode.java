package com.pankaj.cache;

public class CacheNode<K, V> {
    K key;
    V value;
    CacheNode<K, V> prev;
    CacheNode<K, V> next;

    public CacheNode(K key, V value) {
        this.key = key;
        this.value = value;
    }
}
