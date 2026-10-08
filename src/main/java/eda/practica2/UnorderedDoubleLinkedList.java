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
