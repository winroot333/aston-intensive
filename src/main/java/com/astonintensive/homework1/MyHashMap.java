package com.astonintensive.homework1;

import com.astonintensive.homework1.bucket.Bucket;
import com.astonintensive.homework1.bucket.ListBucket;
import java.util.*;

public class MyHashMap<K extends Comparable<K>, V> implements MyMap<K, V> {

    private final int DEFAULT_SIZE = 16;
    private final int GROWTH_FACTOR = 2;
    private final double LOAD_FACTOR = 0.75;

    Bucket<K, V>[] array;
    int size = 0;

    public MyHashMap() {
        this.array = new Bucket[DEFAULT_SIZE];
    }

    private void checkBucketLoad(int bucketIndex) {
        if ((double) size / array.length > this.LOAD_FACTOR) {
            grow();
        }
        if (!array[bucketIndex].checkSize()){
            array[bucketIndex] = array[bucketIndex].convert();
        }
    }

    private int getElementBucket(K key) {
        return Math.abs(key.hashCode() % array.length);
    }

    private void grow() {
        Bucket<K, V>[] oldArray = array;
        this.array = new Bucket[array.length * GROWTH_FACTOR];
        this.size = 0;
        for (Bucket<K, V> bucket : oldArray) {
            if (bucket != null) {
                for (K key : bucket.keySet()) {
                    put(key, bucket.get(key));
                }
            }
        }
    }

    @Override
    public void put(K key, V value) {
        int elementBucket = this.getElementBucket(key);
        if (array[elementBucket] == null) {
            array[elementBucket] = new ListBucket<K, V>(key, value);
            size++;
            checkBucketLoad(elementBucket);
        } else {
            if (array[elementBucket].put(key, value)) {
                size++;
                checkBucketLoad(elementBucket);
            }
        }
    }

    @Override
    public V get(K key) {
        int elementBucket = getElementBucket(key);
        if (array[elementBucket] == null) {
            return null;
        }
        return array[elementBucket].get(key);
    }

    @Override
    public void remove(K key) {
        if (Objects.nonNull(key)) {
            int elementBucket = getElementBucket(key);
            if (array[elementBucket].remove(key)) {
                size--;
                checkBucketLoad(elementBucket);
            }
        }
    }

    @Override
    public int size() {
        return size;
    }
}
