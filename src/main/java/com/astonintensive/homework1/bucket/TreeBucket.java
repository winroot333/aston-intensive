package com.astonintensive.homework1.bucket;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TreeBucket<K extends Comparable<K>, V> implements Bucket<K, V> {

    public static final int MIN_SIZE = 6;
    private TreeNode<K, V> root = null;
    private int size = 0;

    private List<K> keysToConvert;
    private List<V> valuesToConvert;

    private boolean newItemAdded = false;

    @Override
    public boolean put(K key, V value) {
        if (Objects.isNull(root)) {
            root = new TreeNode<K, V>(key, value, null);
            size++;
            return true;
        }
        newItemAdded = false;
        insertNode(key, value, root);
        if (newItemAdded) {
            size++;
            return true;
        }
        return false;
    }

    private void insertNode(K key, V value, TreeNode<K, V> parentNode) {

        if (key.compareTo(parentNode.key) == 0) {
            parentNode.value = value;
        }

        if (key.compareTo(parentNode.key) < 0) {
            if (parentNode.left == null) {
                parentNode.left = new TreeNode<K, V>(key, value, parentNode);
                newItemAdded = true;
            } else {
                insertNode(key, value, parentNode.left);
            }
        }
        if (key.compareTo(parentNode.key) > 0) {
            if (parentNode.right == null) {
                parentNode.right = new TreeNode<K, V>(key, value, parentNode);
                newItemAdded = true;
            } else {
                insertNode(key, value, parentNode.right);
            }
        }
    }

    @Override
    public V get(K key) {
        TreeNode<K, V> node = root;
        while (node != null) {
            if (node.key.compareTo(key) == 0) {
                return node.value;
            }
            if (node.key.compareTo(key) > 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return null;
    }

    @Override
    public boolean remove(K key) {
        if (get(key) != null) {
            removeNode(key, root);
            size--;
            return true;
        }
        return false;
    }

    private TreeNode<K, V> removeNode(K key, TreeNode<K, V> node) {
        if (node == null) {
            return null;
        }

        if (key.compareTo(node.key) < 0) {
            node.left = removeNode(key, node.left);
        } else if (key.compareTo(node.key) > 0) {
            node.right = removeNode(key, node.right);
        } else {
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
        }

        TreeNode<K, V> original = node;
        node = node.right;
        while (node.left != null) {
            node = node.left;
        }

        node.right = removeNode(key, original.right);
        node.left = original.left;
        return node;
    }

    @Override
    public boolean checkSize() {
        return size >= MIN_SIZE;
    }

    @Override
    public Bucket<K, V> convert() {
        keysToConvert = new ArrayList<>();
        valuesToConvert = new ArrayList<>();
        fillConvertLists(root);

        ListBucket<K, V> bucket = new ListBucket<>();
        for (int i = 0; i < keysToConvert.size(); i++) {
            bucket.put(keysToConvert.get(i), valuesToConvert.get(i));
        }
        return bucket;
    }

    @Override
    public List<K> keySet() {
        keysToConvert = new ArrayList<>();
        fillConvertLists(root);
        return keysToConvert;
    }

    private void fillConvertLists(TreeNode<K, V> node) {
        if (node != null) {
            keysToConvert.add(node.key);
            valuesToConvert.add(node.value);
            fillConvertLists(node.left);
            fillConvertLists(node.right);
        }
    }

    private static class TreeNode<K, V> {
        K key;
        V value;
        TreeNode<K, V> left;
        TreeNode<K, V> right;
        TreeNode<K, V> parent;

        public TreeNode(K key, V value, TreeNode<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }
    }
}
