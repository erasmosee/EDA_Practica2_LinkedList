package eda.practica2;

public class OrderedDoubleLinkedList<T> extends DoubleLinkedList<T> implements OrderedListADT<T> {

    @Override
    public void add(T elem) {//TODO
        Node<T> newNode = new Node<T>(elem);
        if (isEmpty()) {
            first = newNode;
        } else {
            Node<T> current = first;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
            count++;
                }
    }

    @Override
    public void merge(DoubleLinkedList<T> lista) {//TODO

    }

}
