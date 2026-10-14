package eda.practica2;

public class UnorderedDoubleLinkedList<T> extends DoubleLinkedList<T> implements UnorderedListADT<T> {

    /**
     * Funcion para agregar elementos entre el ultimo y el primero actualizando
     * o no el puntero first en base a un booleano
     *
     * @param elem
     * @param front
     */
    private void addTo(T elem, boolean front) {
        if (isEmpty()) {
            addWhenEmpty(elem);
        } else {
            insertAdd(elem, first.prev, first);
            if (front) {
                actualizarFirst(first.prev);
            }
        }


    }

    /**
     * (non-Javadoc) Añade un elemento entre el first y el last y actualiza el
     * first.
     *
     * @see eda.practica2.UnorderedListADT#addToFront(java.lang.Object)
     */
    @Override
    public void addToFront(T elem) {
        addTo(elem, true);
    }

    /**
     * (non-Javadoc) Añade un elemento entre el first y el last pero no
     * actualiza el first.
     *
     * @see eda.practica2.UnorderedListADT#addToRear(java.lang.Object)
     */
    @Override
    public void addToRear(T elem) {
        addTo(elem, false);
    }

    /**
     * (non-Javadoc) Añade un elemento despues del target Si la lista esta
     * vacia, el target no esta en la lista, por lo que insertamso target y
     * luego elem.
     *
     * @see eda.practica2.UnorderedListADT#addAfter(java.lang.Object,
     * java.lang.Object)
     */
    @Override
    public void addAfter(T elem, T target) {

        if (isEmpty()) {
            // Si la lista esta vacia, el target no esta en la lista, por lo que insertamso target y luego elem.
            addWhenEmpty(target);
            addAfter(elem, target);
        } else {
            Node<T> nodoTarget = locate(target);
            insertAdd(elem, nodoTarget, nodoTarget.next);
        }
    }

}
