package eda;

public class UnorderedDoubleLinkedList<T> extends DoubleLinkedList<T> implements UnorderedListADT<T> {

    @Override
    public void addToFront(T elem) {
        // añade un elemento al comienzo
        //TODO

    }

    @Override
    public void addToRear(T elem) {
        Node<T> newNode = new Node<>(elem);
        if (isEmpty()) {
            first = newNode;
            first.next = newNode;
            first.prev = newNode;
        } else {
            first.prev.next = newNode;
            first.prev = newNode;
            count++;
        }
    }

    @Override
    public void addAfter(T elem, T target) {
        // añade elem detrás de otro elemento concreto, target,  que ya se encuentra en la lista
        //TODO
    }

}
