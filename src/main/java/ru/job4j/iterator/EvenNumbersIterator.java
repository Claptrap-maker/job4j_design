package ru.job4j.iterator;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class EvenNumbersIterator implements Iterator<Integer> {

    private int[] data;
    private int index;

    public EvenNumbersIterator(int[] data) {
        this.data = data;
    }

    @Override
    public boolean hasNext() {
        return Arrays.stream(data)
                .skip(index)
                .anyMatch(number -> number % 2 == 0);
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        for (int i = index; i < data.length; i++) {
            if (data[i] % 2 == 0) {
                index = i;
                break;
            }
        }
        return data[index++];
    }
}
