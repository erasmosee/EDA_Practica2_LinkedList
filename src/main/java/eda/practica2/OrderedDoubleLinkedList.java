package eda.practica2;

import eda.practica2.extras.Actor;
import eda.practica2.extras.Pelicula;

public class OrderedDoubleLinkedList<T extends Comparable<T>> extends DoubleLinkedList<T> implements OrderedListADT<T> {

    /**
     * <b>O(n)</b>: inserta {@code elem} en orden si no existe. {@code n} es el
     * tamaño de la lista.
     *
     * @param elem elemento que se va a insertar
     */
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

    /**
     * <b>O(m(n + m))</b> en el peor caso: añade a esta lista los elementos de
     * {@code lista}. {@code n} y {@code m} son los tamaños iniciales de ambas
     * listas, respectivamente. No elimina elementos de {@code lista}.
     *
     * @param lista lista ordenada cuyos elementos se incorporan
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

    /**
     * <b>O(n + m)</b> en recorridos e inserciones: fusiona ambas listas en
     * orden. {@code n} y {@code m} son sus tamaños iniciales; combinar
     * elementos iguales añade el coste de {@code combinarCon}.
     *
     * @param lista lista ordenada que se incorpora a esta lista
     */
    public void mergeEficaz(DoubleLinkedList<T> lista) {

        if (lista == null || lista.first == null) {
            return;
        }
        if (isEmpty()) { // si nuestra lista esta vacia se clona con la del parametro
            first = lista.first;
            count = lista.count;
            return;
        }
        Node<T> p1 = first; // creamos los punteros para que recorran las listas
        Node<T> p2 = lista.first;

        int cuantos1 = count; // guardamos en auxiliares la cantidad total de elementos de cada lista.
        int cuantos2 = lista.count;

        while (cuantos1 > 0 && cuantos2 > 0) { // fusionamos ambas listas

            if (p1.data.compareTo(p2.data) < 0) { // si esta ordenado, seguimos comprobando
                p1 = p1.next;
                cuantos1--;
            } else if (p1.data.compareTo(p2.data) == 0) {
                combinarIguales(p1.data, p2.data);
                p1 = p1.next;
                p2 = p2.next;
                cuantos1--;
                cuantos2--;
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
            insertAdd(p2.data, first.prev, first); // insertamos cada elemento al final en orden
            p2 = p2.next;
            cuantos2--;
        }

    }

    /**
     *
     * <b>O(n + m)</b> en recorridos e inserciones: fusiona ambas listas en
     * orden y elimina de {@code lista} cada elemento procesado. {@code n} y
     * {@code m} son sus tamaños iniciales; combinar iguales añade su coste.
     *
     * @param lista lista ordenada de la que se toman los elementos
     */
    @Deprecated
    public void mergePruebas(DoubleLinkedList<T> lista) {
        if (lista == null || lista.first == null || lista == this) {
            return;
        }
        if (isEmpty()) {
            first = lista.first;
            count = lista.count;
            return;
        }
        Node<T> currentGrande = first;

        while (lista.count > 0) {

            boolean seguir = true;
            Node<T> current = lista.first;
            if (seguir && current.data.compareTo(first.data) < 0) {
                // Si current es menor que el primero de listaGrande entonces es el nuervo primero
                insertFirst(current.data);
                seguir = false;
            }

            if (seguir && current.data.compareTo(first.prev.data) > 0) {
                // Si current es mayor que el ultimo de listaGrande entonces es el nuervo ultimo
                insertAdd(current.data, first.prev, first);
                seguir = false;
            }

            int resultado = currentGrande.data.compareTo(current.data);
            if (seguir && resultado == 0) { // Existe en la lista
                combinarIguales(currentGrande.data, current.data);
                seguir = false;
            } else if (seguir && resultado < 0) { // si esta ordenado, seguimos comprobando
                currentGrande = currentGrande.next;
            } else if (seguir && resultado > 0) { // si no reemplazamos

                insertAdd(current.data, currentGrande.prev, currentGrande);
                seguir = false;

            }
            if (!seguir) {
                lista.removeFirst();
            }
        }

    }

    /**
     * <b>O(n(m + n + k))</b>: combina las X de {@code otro} con las de este Y.
     * {@code n} es el número de X de {@code otro}, {@code m} el número inicial
     * de X de este Y y {@code k} el máximo de Y asociados a una X.
     *
     * Siendo X Actor o Pelicula e Y el que no sea X
     *
     * @param otro Y cuyas X se incorporan a este Y
     */
    private void combinarIguales(T actual, T entrante) {
        if (actual instanceof Actor actor) {
            actor.combinarCon((Actor) entrante);
        } else if (actual instanceof Pelicula pelicula) {
            pelicula.combinarCon((Pelicula) entrante);
        }
    }
}
