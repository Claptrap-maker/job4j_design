package ru.job4j.tree;

import java.util.*;
import java.util.function.Predicate;

public class SimpleTree<E> implements Tree<E> {
    private final Node<E> root;

    public SimpleTree(final E root) {
        this.root = new Node<>(root);
    }

    @Override
    public boolean add(E parent, E child) {
        boolean result = false;
        Optional<Node<E>> parentOptional = findBy(parent);
        Optional<Node<E>> childOptional = findBy(child);
        if (parentOptional.isPresent() && childOptional.isEmpty()) {
            parentOptional.get().children.add(new Node<>(child));
            result = true;
        }
        return result;
    }

    public boolean isBinary() {
        Predicate<Node<E>> isBinaryCondition = element -> element.children.size() > 2;
        return findByPredicate(isBinaryCondition).isEmpty();
    }

    @Override
    public Optional<Node<E>> findBy(E value) {
        Predicate<Node<E>> findByCondition = element -> element.value.equals(value);
        return findByPredicate(findByCondition);
    }

    private Optional<Node<E>> findByPredicate(Predicate<Node<E>> condition) {
        Optional<Node<E>> result = Optional.empty();
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(this.root);
        while (!data.isEmpty()) {
            Node<E> element = data.poll();
            if (condition.test(element)) {
                result = Optional.of(element);
                break;
            }
            data.addAll(element.children);
        }
        return result;
    }
}
