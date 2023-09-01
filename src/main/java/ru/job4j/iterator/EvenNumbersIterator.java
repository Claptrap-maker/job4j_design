package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class EvenNumbersIterator implements Iterator<Integer> {

    private int[] data;
    private int index;

    public EvenNumbersIterator(int[] data) {
        this.data = data;
    }

    private int getEvenNumberIndex(int[] array) {
        for (int i = index; i < array.length; i++) {
            if (array[i] % 2 == 0) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean hasNext() {
        return getEvenNumberIndex(data) != -1;
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        index = getEvenNumberIndex(data);
        return data[index++];
    }
}
