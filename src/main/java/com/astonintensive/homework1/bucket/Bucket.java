package com.astonintensive.homework1.bucket;

import java.util.List;

public interface Bucket<K extends Comparable<K>, V> {
    boolean put(K key, V value);

    V get(K key);

    boolean remove(K key);

    boolean checkSize();

    Bucket<K, V> convert();

    List<K> keySet();

}
