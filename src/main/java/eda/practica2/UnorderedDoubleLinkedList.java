package eda.practica2;

public class UnorderedDoubleLinkedList<T> extends DoubleLinkedList<T> implements UnorderedListADT<T> {

    @Override
    public void addToFront(T elem) {//TODO
        // añade un elemento al comienzo

    }

    @Override
    public void addToRear(T elem) {//TODO
        // añade un elemento al final
        Node<T> newNode = new Node<>(elem);
        if (isEmpty()) {
            newNode.next = newNode;
            newNode.prev = newNode;
            first = newNode;
        } else {
            newNode.prev = first.prev;
            newNode.next = first;
            first.prev.next = newNode;
            first.prev = newNode;
        }
        count++;
    }

    @Override
    public void addAfter(T elem, T target) {//TODO
        // añade elem detrás de otro elemento concreto, target,  que ya se encuentra en la lista

    }

}
