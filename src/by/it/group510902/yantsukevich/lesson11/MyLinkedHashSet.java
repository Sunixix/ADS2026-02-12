package by.it.group510902.yantsukevich.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyLinkedHashSet<E> implements Set<E> {

    private static class Node<E> {
        E data;

        Node<E> nextHash;

        Node<E> nextOrder;

        Node(E data) {
            this.data = data;
        }
    }

    private Node<E>[] buckets;

    private Node<E> first;
    private Node<E> last;

    private int size;

    private static final int CAPACITY = 16;

    public MyLinkedHashSet() {
        buckets = (Node<E>[]) new Node[CAPACITY];
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
    public void clear() {
        buckets = (Node<E>[]) new Node[CAPACITY];

        first = null;
        last = null;

        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
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

            current = current.nextHash;
        }

        return false;
    }

    @Override
    public boolean add(E element) {
        if (contains(element)) {
            return false;
        }

        int index = getIndex(element);

        Node<E> newNode = new Node<>(element);

        newNode.nextHash = buckets[index];
        buckets[index] = newNode;

        if (first == null) {
            first = newNode;
            last = newNode;
        } else {
            last.nextOrder = newNode;
            last = newNode;
        }

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
                    buckets[index] = current.nextHash;
                } else {
                    previous.nextHash = current.nextHash;
                }

                Node<E> orderCurrent = first;
                Node<E> orderPrevious = null;

                while (orderCurrent != null) {

                    if (orderCurrent == current) {

                        if (orderPrevious == null) {
                            first = orderCurrent.nextOrder;
                        } else {
                            orderPrevious.nextOrder = orderCurrent.nextOrder;
                        }

                        if (last == orderCurrent) {
                            last = orderPrevious;
                        }

                        break;
                    }

                    orderPrevious = orderCurrent;
                    orderCurrent = orderCurrent.nextOrder;
                }

                size--;

                return true;
            }

            previous = current;
            current = current.nextHash;
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");

        Node<E> current = first;

        while (current != null) {

            if (current != first) {
                result.append(", ");
            }

            result.append(current.data);

            current = current.nextOrder;
        }

        result.append("]");

        return result.toString();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object element : c) {
            if (!contains(element)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean changed = false;

        for (E element : c) {
            if (add(element)) {
                changed = true;
            }
        }

        return changed;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean changed = false;

        for (Object element : c) {
            if (remove(element)) {
                changed = true;
            }
        }

        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean changed = false;

        Node<E> current = first;

        while (current != null) {
            Node<E> next = current.nextOrder;

            if (!c.contains(current.data)) {
                remove(current.data);
                changed = true;
            }

            current = next;
        }

        return changed;
    }

    // Заглушки

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
}