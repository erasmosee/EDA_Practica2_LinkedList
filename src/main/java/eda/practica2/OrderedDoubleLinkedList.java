package eda.practica2;

public class OrderedDoubleLinkedList<T> extends DoubleLinkedList<T> implements OrderedListADT<T> {

    @Override
    public void add(T elem) {//TODO Esto agrega elementos pero no en orden, HAY QUE MIRAR COMO HACER QUE AGREGE EN ORDEN

        if (isEmpty()) {
            addWhenEmpty(elem);
        } else {
            Node<T> current = first;
            while (current.next != null && current.next != first) {
                current = current.next;
            }
            insertAdd(elem, current, current.next);
        }
    }


    @Override
    public void merge(DoubleLinkedList<T> lista) {//TODO

    }

}
