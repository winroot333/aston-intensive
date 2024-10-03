package com.astonintensive.homework1;

public class MapRunner {
    public static void main(String[] args) {
        MyMap<Integer, Integer> map = new MyHashMap<>();
        for (int i = 1; i < 100; i++) {
            map.put(i, i);
        }

        System.out.println(map.get(15));
        map.remove(15);
        System.out.println(map.get(15));

        // проверка преобразования в дерево
        int cnt = 0;
        for (int i = 0; i < 100000; i++) {
            if ((double) i % 256 == 3) {
                map.put(i, i);
                cnt++;
                if (cnt == 30) {
                    break;
                }
            }
        }

        System.out.println(map.get(259));
    }
}
