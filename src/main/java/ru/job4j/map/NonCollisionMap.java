package ru.job4j.map;

import java.util.*;
import java.util.function.Predicate;

public class NonCollisionMap<K, V> implements SimpleMap<K, V> {

    private static final float LOAD_FACTOR = 0.75f;

    private int capacity = 8;

    private int count = 0;

    private int modCount = 0;

    private MapEntry<K, V>[] table = new MapEntry[capacity];

    @Override
    public boolean put(K key, V value) {
        if ((float) count / (float) capacity >= LOAD_FACTOR) {
            expand();
        }

        boolean rsl = false;
        int index = indexFor(hash(Objects.hashCode(key)));

        if (table[index] == null) {
            table[index] = new MapEntry<>(key, value);
            count++;
            modCount++;
            rsl = true;
        }
        return rsl;
    }

    private int hash(int hashCode) {
        return (hashCode == 0) ? 0 : hashCode ^ (hashCode >>> 16);
    }

    private int indexFor(int hash) {
        return (capacity - 1) & hash;
    }

    private void expand() {
        capacity *= 2;
        Predicate<NonCollisionMap.MapEntry<K, V>> expandCondition =
                Objects::nonNull;
        getNewTable(expandCondition);
    }

    @Override
    public V get(K key) {
        int index = indexFor(hash(Objects.hashCode(key)));
        if (table[index] != null) {
            K foundKey = table[index].key;
            if (Objects.hashCode(foundKey) == Objects.hashCode(key)
                    && Objects.equals(key, foundKey)) {
                return table[index].value;
            }
        }
        return null;
    }

    @Override
    public boolean remove(K key) {
        boolean rsl = false;
        if (this.get(key) != null) {
            Predicate<NonCollisionMap.MapEntry<K, V>> removeCondition =
                    entry -> entry != null && !Objects.equals(entry.key, key);
            getNewTable(removeCondition);
            modCount++;
            count--;
            rsl = true;
        }
        return rsl;
    }

    private void getNewTable(Predicate<NonCollisionMap.MapEntry<K, V>> condition) {
        MapEntry<K, V>[] newTable = new MapEntry[capacity];
        for (MapEntry<K, V> entry : table) {
            if (condition.test(entry)) {
                int newIndex = indexFor(hash(Objects.hashCode(entry.key)));
                newTable[newIndex] = entry;
            }
        }
        table = Arrays.copyOf(newTable, capacity);
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<>() {

            private int index;

            private int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                while (index < capacity && table[index] == null) {
                    index++;
                }
                return index < capacity;
            }

            @Override
            public K next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return table[index++].key;
            }
        };
    }

    private static class MapEntry<K, V> {

        K key;
        V value;

        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
