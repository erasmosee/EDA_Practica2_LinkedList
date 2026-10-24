package eda.practica2;

public class UnorderedDoubleLinkedList<T> extends DoubleLinkedList<T> implements UnorderedListADT<T> {

    /**
     * <b>O(1)</b>: inserta un elemento al principio o al final.
     *
     * @param elem elemento que se va a insertar
     * @param front {@code true} para insertar al principio
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
     * <b>O(n)</b>: busca un elemento igual a {@code elem} o lo inserta en
     * orden. {@code n} es el tamaño de la lista.
     *
     * @param elem elemento que se busca o se va a insertar
     * @return el elemento ya almacenado si es igual, o {@code elem} si se
     * inserta
     */
    public T findOrAdd(T elem) {
        if (isEmpty()) {
            addWhenEmpty(elem);
            return elem;
        }
        Node<T> aux = locate(elem);
        if (aux == null) {
            addToRear(elem);
        }

        return elem;
    }

    /**
     * <b>O(1)</b>: inserta {@code elem} al principio y actualiza {@code first}
     * y los enlaces entre el nuevo nodo y los extremos de la lista.
     *
     * @param elem elemento que se va a insertar
     */
    @Override
    public void addToFront(T elem) {
        addTo(elem, true);
    }

    /**
     * <b>O(1)</b>: inserta {@code elem} al final, actualizando los enlaces
     * entre el nuevo nodo y los extremos de la lista.
     *
     * @param elem elemento que se va a insertar
     */
    @Override
    public void addToRear(T elem) {
        addTo(elem, false);
    }

    /**
     * <b>O(n)</b>: busca {@code target} e inserta {@code elem} a continuación,
     * actualizando los enlaces de los nodos vecinos. Si la lista está vacía,
     * inserta primero {@code target} y después {@code elem}; si no está vacía y
     * no encuentra {@code target}, no modifica la lista.
     *
     * @param elem elemento que se va a insertar
     * @param target elemento después del cual se insertará {@code elem}
     */
    @Override
    public void addAfter(T elem, T target) {

        if (isEmpty()) {
            // Si la lista esta vacia, el target no esta en la lista, por lo que insertamso target y luego elem.
            addWhenEmpty(target);
            addAfter(elem, target);
        } else {
            Node<T> nodoTarget = locate(target);
            if (nodoTarget != null) {
                insertAdd(elem, nodoTarget, nodoTarget.next);
            }
        }
    }

}
