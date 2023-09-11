package ru.job4j.collection;

import java.util.NoSuchElementException;

public class SimpleQueue<T> {
    private final SimpleStack<T> in = new SimpleStack<>();
    private final SimpleStack<T> out = new SimpleStack<>();
    private int inSize;
    private int outSize;

    public T poll() {
        if (inSize == 0 && outSize == 0) {
            throw new NoSuchElementException("Queue is empty");
        }
        if (inSize != outSize) {
            for (int i = 0; i < outSize; i++) {
                in.push(out.pop());
            }
            for (int i = 0; i < inSize; i++) {
                out.push(in.pop());
            }
            outSize = inSize;
            inSize = 0;
        }
        outSize--;
        return out.pop();
    }

    public void push(T value) {
        in.push(value);
        inSize++;
    }
}
