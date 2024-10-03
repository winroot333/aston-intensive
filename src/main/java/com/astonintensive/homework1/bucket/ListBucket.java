package com.astonintensive.homework1.bucket;

import java.util.ArrayList;
import java.util.List;

public class ListBucket<K extends Comparable<K>, V> implements Bucket<K, V> {

    private static final int MAX_SIZE = 8;
    private int size = 0;
    Node<K, V> head = null;

    public ListBucket(K key, V value) {
        head = new Node<K, V>(key, value);
    }

    ListBucket() {
    }

    @Override
    public boolean put(K key, V value) {
        Node<K, V> newNode = new Node<>(key, value);
        if (head == null) {
            head = newNode;
            size++;
        } else {
            Node<K, V> node = head;
            boolean rewritten = false;
            if (node.key.equals(key)) {
                node.value = value;
                rewritten = true;
            }

            while (node.next != null) {
                if (newNode.key.equals(node.key)) {
                    node.value = newNode.value;
                    rewritten = true;
                    break;
                }
                node = node.next;
            }
            //  если не нашли с таким ключом добавляем новое
            if (!rewritten) {
                node.next = newNode;
                size++;
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(K key) {
        if (head == null) {
            return null;
        }
        Node<K, V> node = head;
        if (node.key.equals(key)) {
            return node.value;
        }
        while (node.next != null) {
            if (node.key.equals(key)) {
                return node.value;
            }
            node = node.next;
        }
        return null;
    }

    @Override
    public boolean remove(K key) {
        Node<K, V> node = head;
        if (node.key.equals(key)) {
            head = head.next;
        }
        while (node.next != null) {
            if (node.key.equals(key)) {
                node.next = node.next.next;
                size--;
                return true;

            }
            node = node.next;
        }
        return false;
    }

    @Override
    public boolean checkSize() {
        return size <= MAX_SIZE;
    }

    @Override
    public Bucket<K, V> convert() {
        TreeBucket<K, V> tree = new TreeBucket<>();
        Node<K, V> node = head;
        while (node != null) {
            tree.put(node.key, node.value);
            node = node.next;
        }
        return tree;
    }

    @Override
    public List<K> keySet() {
        List<K> keys = new ArrayList<>();
        Node<K, V> node = head;
        if (node != null) {
            keys.add(node.key);
            while (node.next != null) {
                keys.add(node.key);
                node = node.next;
            }
        }
        return keys;
    }

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> next = null;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
