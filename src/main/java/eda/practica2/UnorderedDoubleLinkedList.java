package eda.practica2;

public class UnorderedDoubleLinkedList<T> extends DoubleLinkedList<T> implements UnorderedListADT<T> {

    /*
     * IDEA:
     * hacer un metodo addTo(T elem, boolean front){
     * que haga todo lo que ambos metodos tienen en comun.
     * y que simplemente use el booleano para saber cuando actualizar el first, que
     * es lo unico que varia.
     * asi el metodo addToFront solo tendria que llamar a addTo(T,true) el el metodo
     * addToRear tendria que llamar al addTo(T,false).
     * De esta manera no se duplica codigo.
     * }
     * 
     */

    public void addTo(T elem, boolean front) {
        Node<T> newNode = new Node<>(elem);
        if (isEmpty()) {
            newNode.next = newNode;
            newNode.prev = newNode;
            first = newNode;
        }

        else {
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

    // addToFront y addToRear, insertan en el mismo sitio, delante del First, luego
    // ya uno actualiza el First y el otro no.
    @Override
    // añade un elemento al comienzo
    public void addToFront(T elem) {
        addTo(elem, true);
    }

    @Override
    // añade un elemento al final
    public void addToRear(T elem) {
        addTo(elem, false);
    }

    @Override
    public void addAfter(T elem, T target) {// TODO
        // añade elem detrás de otro elemento concreto, target, que ya se encuentra en
        // la lista
    }
}
