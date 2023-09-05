package ru.job4j.collection;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ForwardLinked<T> implements SimpleLinked<T> {
    private int size;
    private int modCount;
    private Node<T> head;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, null);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> next = head;
            while (next.next != null) {
                next = next.next;
            }
            next.next = newNode;
        }
        size++;
        modCount++;
    }

    @Override
    public T get(int index) {
        Objects.checkIndex(index, size);
        Node<T> curr = head;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        return curr.item;
    }

    public void addFirst(T value) {
        Node<T> newNode = new Node<>(value, null);
        Node<T> next = head;
        head = newNode;
        if (next != null) {
            newNode.next = next;
            head.next = next;
        }
        size++;
        modCount++;
    }

    public T deleteFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        Node<T> deletedNode = head;
        head = deletedNode.next;
        if (head != null) {
            head.next = deletedNode.next.next;
            deletedNode.next = null;
        }
        T deletedItem = deletedNode.item;
        deletedNode.item = null;
        size--;
        modCount++;
        return deletedItem;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int expectedModCount = modCount;
            private Node<T> curr = head;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return curr != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T value = curr.item;
                curr = curr.next;
                return value;
            }
        };
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;

        Node(T element, Node<T> next) {
            this.item = element;
            this.next = next;
        }
    }
}
