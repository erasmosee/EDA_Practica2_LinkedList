package eda.practica2;

public class OrderedDoubleLinkedList<T extends Comparable<T>> extends DoubleLinkedList<T> implements OrderedListADT<T> {

    @Override
    public void add(T elem) {
        if (isEmpty()) {
            addWhenEmpty(elem);
            return;
        }

        Node<T> last = first.prev;
        if (elem.compareTo(last.data) >= 0) {
            insertAdd(elem, last, first);
            return;
        }

        if (elem.compareTo(first.data) < 0) {
            insertAdd(elem, last, first);
            actualizarFirst(first.prev);
            return;
        }

        Node<T> current = first;
        while (current.next != first && elem.compareTo(current.next.data) >= 0) {
            current = current.next;
        }

        insertAdd(elem, current, current.next);
    }

    /*
    * Método para fusionar dos listas y ordenarlas. Por ahorro de memoria se podria borrar la lista que se recibe de parametro pero se prefiere clonar elemento por elemento y fusionarlo dentro de la lista 
    * Contras -> Complejidad O(n*m) (n -> this, m -> lista)
    */
    @Override
    public void merge(DoubleLinkedList<T> lista) {
        if (lista == null || lista.isEmpty()) return; // comprobamos si la lista que recibimos esta vacia o nula -> si es así, no hacemos nada
        
        Node<T> actual = lista.first; // creamos un "indice" de la lista que recibimos 
        for (int i = 0; i < lista.count; i++){
            add(actual.data); // dejamos que add() haga el resto del trabajo de algoritmia de comparaciones
            actual = actual.next; // actualizamos el indice
        }
    }
}
