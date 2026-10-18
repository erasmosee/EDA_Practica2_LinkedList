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
    // add tira de insertAdd el cual al no saber donde insertar va a buscarlo cada vez en la lista, por eso nos queda cuadratico,
    // el otro dia Eneko explico que para simplificar estos metodos necesitaremos punteros, muchos punteros.

    //Para este caso en concreto necesitaremos minimo dos punteros, uno que avance por la lista pequeña y otro por la lista grande,
    //Dado que los dos estan ordenados, un puntero que avanza por la lista grande y cuando detecta que su elemento es mayor que el primero de la lista pequeña, vuelve uno atras y hace un insertAdd.
    //en ese momento se carga el elemento de la lista pequeña y compara con donde se habia quedado en la lista grande, si no es, avanza el de la lista grande desde donde se habia quedado, de esta forma solo recorremos la grande una vez y la pequeña una vez. n+m
}
