package eda.practica2;

public class OrderedDoubleLinkedList<T> extends DoubleLinkedList<T> implements OrderedListADT<T> {

    @Override
    public void add(T elem) {//TODO Esto agrega elementos pero no en orden,
        Node<T> newNode = new Node<>(elem);
        if (isEmpty()) {
            newNode.next = newNode;
            newNode.prev = newNode;
            first = newNode;
        } else {
            Node<T> current = first;
            while (current.next != null && current.next != first) {
                current = current.next;
            }
            current.next = newNode;
            newNode.prev = current;
            newNode.next = first;
            newNode.prev = newNode;
            count++;
        }
    }

    @Override
    public void merge(DoubleLinkedList<T> lista) {//TODO

    }

}
