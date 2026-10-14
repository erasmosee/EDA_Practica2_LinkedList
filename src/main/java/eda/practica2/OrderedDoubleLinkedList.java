package eda.practica2;

public class OrderedDoubleLinkedList<T extends Comparable<T>> extends DoubleLinkedList<T> implements OrderedListADT<T> {

    @Override
    public void add(T elem) {
        if (isEmpty()) {
            addWhenEmpty(elem);
            return;
        }

        if (elem.compareTo(first.data) < 0) {
            insertAdd(elem, first.prev, first);
            first = first.prev;
            return;
        }

        Node<T> current = first;
        while (current.next != first && elem.compareTo(current.next.data) >= 0) {
            current = current.next;
        }

        insertAdd(elem, current, current.next);
    }


    @Override
    public void merge(DoubleLinkedList<T> lista) {//TODO

    }

}
