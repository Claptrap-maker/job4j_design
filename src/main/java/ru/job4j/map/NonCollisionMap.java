package ru.job4j.map;

import java.util.*;

public class NonCollisionMap<K, V> implements SimpleMap<K, V> {

    private static final float LOAD_FACTOR = 0.75f;

    private int capacity = 8;

    private int count = 0;

    private int modCount = 0;

    private MapEntry<K, V>[] table = new MapEntry[capacity];

    @Override
    public boolean put(K key, V value) {
        boolean rsl = false;

        if (capacity * LOAD_FACTOR <= count) {
            expand();
        }

        int index = getIndex(key);

        if (table[index] == null) {
            table[index] = new MapEntry<>(key, value);
            count++;
            modCount++;
            rsl = true;
        }
        return rsl;
    }

    private int getIndex(K key) {
        return indexFor(hash(Objects.hashCode(key)));
    }

    private int hash(int hashCode) {
        return hashCode ^ (hashCode >>> 16);
    }

    private int indexFor(int hash) {
        return (capacity - 1) & hash;
    }

    private void expand() {
        capacity *= 2;
        MapEntry<K, V>[] newTable = new MapEntry[capacity];
        for (MapEntry<K, V> entry : table) {
            if (entry != null) {
                int newIndex = getIndex(entry.key);
                newTable[newIndex] = entry;
            }
        }
        table = Arrays.copyOf(newTable, capacity);
    }

    @Override
    public V get(K key) {
        V value = null;
        int index = getIndex(key);
        if (table[index] != null) {
            K foundKey = table[index].key;
            if (isKeyEqual(key, foundKey)) {
                value = table[index].value;
            }
        }
        return value;
    }

    private boolean isKeyEqual(K key, K foundKey) {
        return Objects.hashCode(foundKey) == Objects.hashCode(key)
                && Objects.equals(key, foundKey);
    }

    @Override
    public boolean remove(K key) {
        boolean rsl = false;
        if (this.get(key) != null) {
            table[getIndex(key)] = null;
            modCount++;
            count--;
            rsl = true;
        }
        return rsl;
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
