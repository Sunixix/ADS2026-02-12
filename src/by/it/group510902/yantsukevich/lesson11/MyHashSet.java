package by.it.group510902.yantsukevich.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyHashSet<E> implements Set<E> {

    private static class Node<E> {
        E data;
        Node<E> next;

        Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }
    }

    private Node<E>[] buckets;
    private int size;

    private static final int capacity = 16;

    @SuppressWarnings("unchecked")
    public MyHashSet() {
        buckets = (Node<E>[]) new Node[capacity];
    }

    private int getIndex(Object element) {
        if (element == null) {
            return 0;
        }

        return (element.hashCode() & 0x7fffffff) % buckets.length;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        buckets = (Node<E>[]) new Node[capacity];
        size = 0;
    }

    @Override
    public boolean contains(Object element) {
        int index = getIndex(element);

        Node<E> current = buckets[index];

        while (current != null) {
            if (element == null
                    ? current.data == null
                    : element.equals(current.data)) {
                return true;
            }

            current = current.next;
        }

        return false;
    }

    @Override
    public boolean add(E element) {
        if (contains(element)) {
            return false;
        }

        int index = getIndex(element);

        buckets[index] = new Node<>(element, buckets[index]);
        size++;

        return true;
    }

    @Override
    public boolean remove(Object element) {
        int index = getIndex(element);

        Node<E> current = buckets[index];
        Node<E> previous = null;

        while (current != null) {
            if (element == null
                    ? current.data == null
                    : element.equals(current.data)) {

                if (previous == null) {
                    buckets[index] = current.next;
                } else {
                    previous.next = current.next;
                }

                size--;
                return true;
            }

            previous = current;
            current = current.next;
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        boolean first = true;

        for (Node<E> bucket : buckets) {
            Node<E> current = bucket;

            while (current != null) {
                if (!first) {
                    result.append(", ");
                }

                result.append(current.data);
                first = false;

                current = current.next;
            }
        }

        result.append("]");
        return result.toString();
    }


    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return null;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }
}