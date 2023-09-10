package ru.job4j.collection;

import java.util.NoSuchElementException;

public class SimpleQueue<T> {
    private final SimpleStack<T> in = new SimpleStack<>();
    private final SimpleStack<T> out = new SimpleStack<>();
    boolean inHasChanged;
    private int inSize;
    private int outSize;

    public T poll() {
        if (inHasChanged) {
            reverseStack();
            inHasChanged = false;
        }
        if (outSize == 0) {
            throw new NoSuchElementException("Queue is empty");
        }
        outSize--;
        return out.pop();
    }

    private void reverseStack() {
        for (int i = 0; i < outSize; i++) {
            in.push(out.pop());
        }
        outSize = 0;
        for (int i = 0; i < inSize; i++) {
            out.push(in.pop());
            outSize++;
        }
        inSize = 0;
    }

    public void push(T value) {
        in.push(value);
        inSize++;
        inHasChanged = true;
    }
}
