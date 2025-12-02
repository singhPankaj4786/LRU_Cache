package com.pankaj.cache;

public class DoublyLinkedList<K, V> {

    private final CacheNode<K, V> head;
    private final CacheNode<K, V> tail;
    private int size = 0;

    public DoublyLinkedList() {
        head = new CacheNode<>(null, null);
        tail = new CacheNode<>(null, null);

        head.next = tail;
        tail.prev = head;
    }

    // Add node after head → Most Recently Used
    public void addFirst(CacheNode<K, V> node) {
        node.next = head.next;
        node.prev = head;

        head.next.prev = node;
        head.next = node;

        size++;
    }

    // Remove any node from DLL
    public void remove(CacheNode<K, V> node) {
        if (node == head || node == tail) {
            return; // never remove fake nodes
        }
        node.prev.next = node.next;
        node.next.prev = node.prev;

        node.next = null;
        node.prev = null;

        size--;
    }

    // Remove the least recently used node (just before tail)
    public CacheNode<K, V> removeLast() {
        if (tail.prev == head) {
            return null; // empty
        }
        CacheNode<K, V> last = tail.prev;
        remove(last);
        return last;
    }

    public int size() {
        return size;
    }
}
