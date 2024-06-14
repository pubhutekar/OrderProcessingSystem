package org.techhub;

import java.util.HashMap;
import java.util.Map;

public class InventoryCache<K, V> {
    private final int capacity;
    private final Map<K, Node<K, V>> map;
    private final CustomLinkedList<K, V> list;

    public InventoryCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.list = new CustomLinkedList<>();
    }

    public V get(K key) {
        Node<K, V> node = map.get(key);
        if (node == null) {
            return null;
        }
        list.moveToFront(node);
        return node.value;
    }

    public void put(K key, V value) {
        Node<K, V> node = map.get(key);
        if (node == null) {
            if (map.size() >= capacity) {
                Node<K, V> tail = list.removeTail();
                map.remove(tail.key);
            }
            node = new Node<>(key, value);
            map.put(key, node);
            list.addFirst(node);
        } else {
            node.value = value;
            list.moveToFront(node);
        }
    }

    private static class CustomLinkedList<K, V> {
        private Node<K, V> head;
        private Node<K, V> tail;

        public CustomLinkedList() {
            head = new Node<>(null, null);
            tail = new Node<>(null, null);
            head.next = tail;
            tail.prev = head;
        }

        public void addFirst(Node<K, V> node) {
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
        }

        public void moveToFront(Node<K, V> node) {
            remove(node);
            addFirst(node);
        }

        public Node<K, V> removeTail() {
            if (tail.prev == head) {
                return null;
            }
            Node<K, V> node = tail.prev;
            remove(node);
            return node;
        }

        private void remove(Node<K, V> node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> prev;
        Node<K, V> next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
