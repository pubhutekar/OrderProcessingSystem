package org.techhub;

import java.util.AbstractList;
import java.util.Arrays;

public class OrderList<E> extends AbstractList<E> {
    private Object[] orders;
    private int size;

    public OrderList() {
        orders = new Object[10]; // initial capacity
        size = 0;
    }

    @Override
    public E get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return (E) orders[index];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean add(E e) {
        if (size == orders.length) {
            orders = Arrays.copyOf(orders, size * 2); // double the capacity
        }
        orders[size++] = e;
        return true;
    }

    @Override
    public E remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        E removedOrder = (E) orders[index];
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(orders, index + 1, orders, index, numMoved);
        }
        orders[--size] = null; // clear to let GC do its work
        return removedOrder;
    }
}
