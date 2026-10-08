package eda.practica2;

public class UnorderedDoubleLinkedList<T> extends DoubleLinkedList<T> implements UnorderedListADT<T> {

    //addToFront y addToRear, insertan en el mismo sitio, delante del First, luego ya uno actualiza el First y el otro no.
    @Override
    // añade un elemento al comienzo
    public void addToFront(T elem) {

        Node<T> newNode = new Node<>(elem);
        if (isEmpty()) {
            // Esta solico, se apunta a si mismo en el previo y el siguiente.
            newNode.next = newNode;
            newNode.prev = newNode;
        } else {
            //Establecemos a donde va a apuntar el newNode, en este caso al actual previo de first y a first en el previo y el next del newnode respectivamente
            newNode.prev = first.prev;
            newNode.next = first;
            // Una vez tenemos el newNode bien alimentado, hacemos que la lista lo apunte a el. diciendo que el siguiente al ultimo sea newNode y que el previo al first sea Newnode.
            first.prev.next = newNode;
            first.prev = newNode;
        }
        //tambien hay que actualizar el first. en este caso se actualiza en ambos casos.
        first = newNode;
        count++;
    }

    @Override
    // añade un elemento al final
    public void addToRear(T elem) {
        Node<T> newNode = new Node<>(elem);
        if (isEmpty()) {
            // Esta solico, se apunta a si mismo en el previo y el siguiente.
            newNode.next = newNode;
            newNode.prev = newNode;
            //tambien hay que actualizar el first. en este caso solo se actualiza si la lista estaba vacia.
            first = newNode;
        } else {
            //Establecemos a donde va a apuntar el newNode, en este caso al actual previo de first y a first en el previo y el next del newnode respectivamente
            newNode.prev = first.prev;
            newNode.next = first;
            // Una vez tenemos el newNode bien alimentado, hacemos que la lista lo apunte a el. diciendo que el siguiente al ultimo sea newNode y que el previo al first sea Newnode.
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
