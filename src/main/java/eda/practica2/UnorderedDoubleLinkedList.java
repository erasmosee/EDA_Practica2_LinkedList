package eda.practica2;

public class UnorderedDoubleLinkedList<T> extends DoubleLinkedList<T> implements UnorderedListADT<T> {

    /**
     * Las funciones addToFront y addToRear, insertan en el mismo sitio, delante
     * del First, la diferencia entre ellos es que uno actualiza a donde apunta
     * el First y el otro no. Por ello usamos un booleano al llamar a addTo
     * desde addToFront y desde addToRear. Dado que solo se le va a llamar desde
     * addToFront y desde addToRear addTo es privado.
     */
    private void addTo(T elem, boolean front) {
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
            if (front) {
                first = newNode;
            }
        }

        count++;

    }

    @Override
    // añade un elemento entre el first y el last y actualiza el first.
    public void addToFront(T elem) {
        addTo(elem, true);
    }

    @Override
    // añade un elemento entre el first y el last pero no actualiza el first.
    public void addToRear(T elem) {
        addTo(elem, false);
    }

    @Override
    public void addAfter(T elem, T target) {// TODO
        /**
         * Este metodo me plantea las siguientes dudas: Sobre el elemento que
         * vamos a insertar. ¿se comprueba si ya existe? En caso de no
         * comprobarlo, quiere decir que tendremos elementos repetidos, detras
         * de cual de todos los repetidos queremos insertarlo. Creo conveniente
         * revisar TODA la lista (una iteracion completa) para ver si existe, y
         * en dicha iteracion guardarnos el elemento Target, para cuando hayamos
         * comprobado toda la lista, insertar nuestro elemento detras de target,
         * actualizando los elementos Target, Target.next y Target.next.next.
         */
    }
}
