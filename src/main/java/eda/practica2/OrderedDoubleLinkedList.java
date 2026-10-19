package eda.practica2;

public class OrderedDoubleLinkedList<T extends Comparable<T>> extends DoubleLinkedList<T> implements OrderedListADT<T> {

    @Override
    public void add(T elem) {
        if (isEmpty()) {
            addWhenEmpty(elem);
            return;
        }
        Node<T> last = first.prev;

        if (elem.compareTo(first.data) == 0 || elem.compareTo(last.data) == 0) {
            return;
        }
        if (elem.compareTo(last.data) > 0) {
            insertAdd(elem, last, first);
            return;
        }
        if (elem.compareTo(first.data) < 0) {
            insertAdd(elem, last, first);
            actualizarFirst(first.prev);
            return;
        }

        Node<T> current = first;
        while (current.next != first && elem.compareTo(current.next.data) > 0) {
            current = current.next;
        }
        if (elem.compareTo(current.next.data) == 0) {
            return;
        }
        insertAdd(elem, current, current.next);

    }

      public T findOrAdd(T elem) {
        if (isEmpty()) {
            addWhenEmpty(elem);
            return elem;
        }

        int comparacionConPrimero = elem.compareTo(first.data);
        if (comparacionConPrimero == 0) {
            return first.data;
        }
        if (comparacionConPrimero < 0) {
            insertAdd(elem, first.prev, first);
            actualizarFirst(first.prev);
            return elem;
        }

        Node<T> last = first.prev;
        int comparacionConUltimo = elem.compareTo(last.data);
        if (comparacionConUltimo == 0) {
            return last.data;
        }
        if (comparacionConUltimo > 0) {
            insertAdd(elem, last, first);
            return elem;
        }

        Node<T> current = first;
        while (elem.compareTo(current.next.data) > 0) {
            current = current.next;
        }
        int comparacionConSiguiente = elem.compareTo(current.next.data);
        if (comparacionConSiguiente == 0) {
            return current.next.data;
        }
        insertAdd(elem, current, current.next);
        return elem;
    }

    /*
    * Método para fusionar dos listas y ordenarlas. Por ahorro de memoria se podria borrar la lista que se recibe de parametro pero se prefiere clonar elemento por elemento y fusionarlo dentro de la lista 
    * Contras -> Complejidad O(n*m) (n -> this, m -> lista)
     */
    @Override
    public void merge(DoubleLinkedList<T> lista) {
        if (lista == null || lista.isEmpty()) {
            return; // comprobamos si la lista que recibimos esta vacia o nula -> si es así, no hacemos nada
        }
        Node<T> actual = lista.first; // creamos un "indice" de la lista que recibimos 
        for (int i = 0; i < lista.count; i++) {
            add(actual.data); // dejamos que add() haga el resto del trabajo de algoritmia de comparaciones
            actual = actual.next; // actualizamos el indice
        }
    }
    // add tira de insertAdd el cual al no saber donde insertar va a buscarlo cada vez en la lista, por eso nos queda cuadratico,
    // el otro dia Eneko explico que para simplificar estos metodos necesitaremos punteros, muchos punteros.

    //Para este caso en concreto necesitaremos minimo dos punteros, uno que avance por la lista pequeña y otro por la lista grande,
    //Dado que los dos estan ordenados, un puntero que avanza por la lista grande y cuando detecta que su elemento es mayor que el primero de la lista pequeña, vuelve uno atras y hace un insertAdd.
    //en ese momento se carga el elemento de la lista pequeña y compara con donde se habia quedado en la lista grande, si no es, avanza el de la lista grande desde donde se habia quedado, de esta forma solo recorremos la grande una vez y la pequeña una vez. n+m
    public void mergeEficaz(DoubleLinkedList<T> lista) {
        if (lista == null || lista.first == null) {
            return;
        }
        if (isEmpty()) { // si nuestra lista esta vacia se clona con la del parametro
            first = lista.first;
            count = lista.count;
            return;
        }
        int contadorOperaciones = 0;
        Node<T> p1 = first; // creamos los punteros para que recorran las listas
        Node<T> p2 = lista.first;

        int cuantos1 = count; // guardamos en auxiliares la cantidad total de elementos de cada lista.
        int cuantos2 = lista.count;

        while (cuantos1 > 0 && cuantos2 > 0) { // fusionamos ambas listas
            contadorOperaciones++;

            if (p1.data.compareTo(p2.data) <= 0) { // si esta ordenado, seguimos comprobando
                p1 = p1.next;
                cuantos1--;
            } else { // si no reemplazamos
                insertAdd(p2.data, p1.prev, p1); // si esta desordenado, añadimos en nuestra lista el elemento anterior a p1.
                if (p1 == first) { // comprobamos si hemos añadido un elemento antes del first
                    actualizarFirst(first.prev); // p2 pasa a ser el nuevo first de nuestra lista
                }
                p2 = p2.next; // actualizamos el puntero de la lista del segundo 
                cuantos2--;
            }
        }

        // si aun quedan elementos en la otra lista, entonces es que quedan los mayores, por lo que irian al final.
        while (cuantos2 > 0) {
            contadorOperaciones++;
            insertAdd(p2.data, first.prev, first); // insertamos cada elemento al final en orden
            p2 = p2.next;
            cuantos2--;
        }
        System.out.println("    mergueEficaz operaciones realizadas: " + contadorOperaciones);

    }
}
