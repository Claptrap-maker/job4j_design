package ru.job4j.iterator;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class NonNullIterator implements Iterator<Integer> {

    private Integer[] data;
    private int index;

    public NonNullIterator(Integer[] data) {
        this.data = data;
    }

    @Override
    public boolean hasNext() {
        return Arrays.stream(data)
                .skip(index)
                .anyMatch(Objects::nonNull);
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        for (int i = index; i < data.length; i++) {
            if (data[i] != null) {
                index = i;
                break;
            }
        }
        return data[index++];
    }
}
