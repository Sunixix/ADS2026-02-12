package by.it.group510902.yantsukevich.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyTreeSet<E extends Comparable<E>> implements Set<E> {

    private Object[] elements;
    private int size;

    private static final int CAPACITY = 10;

    public MyTreeSet() {
        elements = new Object[CAPACITY];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        elements = new Object[CAPACITY];
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object element) {
        return findIndex(element) >= 0;
    }

    @Override
    public boolean add(E element) {
        int position = findPosition(element);

        if (position < size && compare(elements[position], element) == 0) {
            return false;
        }

        ensureCapacity();

        for (int i = size; i > position; i--) {
            elements[i] = elements[i - 1];
        }

        elements[position] = element;
        size++;

        return true;
    }

    @Override
    public boolean remove(Object element) {
        int position = findIndex(element);

        if (position < 0) {
            return false;
        }

        // Сдвигаем элементы влево
        for (int i = position; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }

        elements[size - 1] = null;
        size--;

        return true;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");

        for (int i = 0; i < size; i++) {
            if (i > 0) {
                result.append(", ");
            }

            result.append(elements[i]);
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

        for (int i = size - 1; i >= 0; i--) {
            if (!c.contains(elements[i])) {
                remove(elements[i]);
                changed = true;
            }
        }

        return changed;
    }

    private int findPosition(E element) {
        int left = 0;
        int right = size;

        while (left < right) {
            int middle = (left + right) / 2;

            if (compare(elements[middle], element) < 0) {
                left = middle + 1;
            } else {
                right = middle;
            }
        }

        return left;
    }

    private int findIndex(Object element) {
        if (element == null) {
            return -1;
        }

        int left = 0;
        int right = size - 1;

        while (left <= right) {
            int middle = (left + right) / 2;

            int comparison = ((E) elements[middle])
                    .compareTo((E) element);

            if (comparison == 0) {
                return middle;
            }

            if (comparison < 0) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }

        return -1;
    }

    private int compare(Object first, Object second) {
        return ((E) first).compareTo((E) second);
    }

    private void ensureCapacity() {
        if (size < elements.length) {
            return;
        }

        Object[] newElements = new Object[elements.length * 2];

        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }

        elements = newElements;
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
}