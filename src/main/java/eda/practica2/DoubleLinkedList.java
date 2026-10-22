package eda.practica2;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * DoubleLinkedList
 *
 * @param <T>
 */
public class DoubleLinkedList<T> implements ListADT<T> {

    // Atributos
    protected Node<T> first;  // apuntador al primer elemento
    protected String descr;  // descripción
    protected int count;

    // Constructor
    public DoubleLinkedList() {
        first = null;
        descr = "";
        count = 0;
    }

    @Override
    public void setDescr(String nom) {
        descr = nom;
    }

    @Override
    public String getDescr() {
        return descr;
    }

    /**
     * <b>O(1)</b>: elimina el primer nodo, actualiza {@code first} y los enlaces
     * entre el nuevo primero y el último.
     *
     * @return el elemento eliminado
     * @throws NoSuchElementException si la lista está vacía
     */
    @Override
    public T removeFirst() {
        Node<T> aux;
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            aux = first;
            if (isAlone()) {
                first = null;
            } else {
                first.prev.next = first.next;
                first.next.prev = first.prev;
                first = first.next;
            }
            count--;
        }
        return aux.data;
    }

    /**
     * <b>O(1)</b>: elimina el último nodo y actualiza los enlaces entre el
     * nuevo último y {@code first}.
     *
     * @return el elemento eliminado
     * @throws NoSuchElementException si la lista está vacía
     */
    @Override
    public T removeLast() {
        Node<T> aux;
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            aux = first.prev;
            if (isAlone()) {
                first = null;
            } else {
                first.prev.prev.next = first;
                first.prev = first.prev.prev;
            }
            count--;
        }
        return aux.data;
    }

    /**
     * <b>O(n)</b>: busca el nodo cuyo dato es igual a {@code elem}.
     *
     * @param elem elemento que se busca
     * @return el nodo encontrado o {@code null} si no existe
     */
    protected Node<T> locate(T elem) {
        if (isEmpty()) {
            return null;
        }
        Node<T> aux = first;
        do {
            if (Objects.equals(elem, aux.data)) {
                return aux;
            }
            aux = aux.next;
        } while (aux != first);
        //Si llega al aux = first de nuevo es que se ha pasado la lista entera y no lo ha encontrado, retorna null

        return null;
    }

    /**
     * <b>O(n)</b>: busca y elimina el primer elemento igual a {@code elem},
     * actualizando los enlaces de sus nodos vecinos.
     *
     * @param elem elemento que se quiere eliminar
     * @return el elemento eliminado, o {@code null} si no se encuentra
     * @throws NoSuchElementException si la lista está vacía
     */
    @Override
    public T remove(T elem) {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Node<T> aux = locate(elem);
        // Si locate devuelve null es que no esta en la lista.
        if (aux == null) {
            return null;
        }
        // si aux es first, lanzamos el removeFirst que ya tenemos.
        if (aux == first) {
            return removeFirst();
        }

        aux.prev.next = aux.next;
        aux.next.prev = aux.prev;
        count--;

        return aux.data;
    }

    /**
     * <b>O(1)</b>: devuelve el primer elemento.
     *
     * @return el primer elemento
     * @throws NoSuchElementException si la lista está vacía
     */
    @Override
    public T first() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            return first.data;
        }
    }

    /**
     * <b>O(1)</b>: devuelve el último elemento.
     *
     * @return el último elemento
     * @throws NoSuchElementException si la lista está vacía
     */
    @Override
    public T last() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            return first.prev.data;
        }
    }

    /**
     * <b>O(n)</b>: comprueba si la lista contiene un elemento igual a
     * {@code elem}.
     *
     * @param elem elemento que se busca
     * @return {@code true} si está en la lista
     */
    @Override
    public boolean contains(T elem) {
        Node<T> aux;
        if (isEmpty()) {
            return false;
        } else {
            aux = locate(elem);
            //Locate devuelve null si no lo encuentra, 
            //Si aux es null devolvemos false
            if (aux != null) {
                return Objects.equals(aux.data, elem);
            } else {
                return false;
            }
        }
    }

    /**
     * <b>O(n)</b>: busca y devuelve un elemento igual a {@code elem}.
     *
     * @param elem elemento que se busca
     * @return el elemento encontrado o {@code null} si no existe
     */
    @Override
    public T find(T elem) {

        Node<T> aux;
        if (isEmpty()) {
            return null;

        } else {
            aux = locate(elem);

            if (aux == null) {
                return null;
            }
            if (Objects.equals(aux.data, elem)) {
                return aux.data;
            } else {
                return null;
            }
        }
    }

    /**
     * <b>O(1)</b>: crea el único nodo de la lista vacía y enlaza sus referencias
     * {@code next} y {@code prev} consigo mismo.
     *
     * @param elem elemento que se va a insertar
     */
    public void addWhenEmpty(T elem) {
        Node<T> newNode = new Node<>(elem);
        newNode.next = newNode;
        newNode.prev = newNode;
        first = newNode;
        count++;
    }

    /**
     * <b>O(1)</b>: inserta {@code elem} al principio y actualiza {@code first}.
     *
     * @param elem elemento que se va a insertar
     */
    public void insertFirst(T elem) {
        if (isEmpty()) {
            addWhenEmpty(elem);
            return;
        }

        insertAdd(elem, first.prev, first);
        first = first.prev;
    }

    /**
     * <b>O(1)</b>: comprueba si la lista está vacía.
     *
     * @return {@code true} si no contiene elementos
     */
    @Override
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * <b>O(1)</b>: comprueba si la lista contiene un único nodo.
     *
     * @return {@code true} si contiene un único nodo
     */
    public boolean isAlone() {
        return first == first.next;
    }

    /**
     * <b>O(1)</b>: cambia la referencia al primer nodo; no modifica los enlaces
     * ni el número de elementos.
     *
     * @param node nodo que pasará a ser el primero
     */
    public void actualizarFirst(Node<T> node) {
        first = node;
    }

    /**
     * <b>O(1)</b>: devuelve el número de elementos de la lista.
     *
     * @return número de elementos
     */
    @Override
    //Determina el número de elementos de la lista
    public int size() {
        return count;
    }

    /**
     * <b>O(1)</b>: crea un iterador para recorrer la lista.
     *
     * @return iterador de la lista
     */
    @Override
    public Iterator<T> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<T> {

        private Node<T> aux = first;
        private int contador = count;

        @Override
        public boolean hasNext() {
            return contador > 0;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            T data = aux.data;
            aux = aux.next;
            contador--;
            return data;

        }
    }

    protected class Node<N> {

        protected N data; 			// dato del nodo
        protected Node<N> prev; 	// puntero al anterior nodo de la lista
        protected Node<N> next; 	// puntero al siguiente nodo de la lista
        // -------------------------------------------------------------

        protected Node(N dd) // constructor
        {
            data = dd;
            next = null;
            prev = null;
        }

        /**
         * Constructor que asigna previo y siguiente.
         *
         * @param dd
         * @param prev
         * @param next
         */
        public Node(N dd, Node<N> prev, Node<N> next) // constructor
        {
            data = dd;
            this.next = next;
            this.prev = prev;
        }

    }

    /**
     * <b>O(1)</b>: inserta un elemento entre los nodos {@code prev} y
     * {@code next}, actualizando {@code prev.next} y {@code next.prev}.
     *
     * @param elem elemento que se va a insertar
     * @param prev nodo previo al nuevo
     * @param next nodo siguiente al nuevo
     */
    public void insertAdd(T elem, Node<T> prev, Node<T> next) {
        Node<T> newNode = new Node<>(elem, prev, next);
        prev.next = newNode;
        next.prev = newNode;
        count++;
    }

    public void visualizarNodos() {
        System.out.println(this.toString());
    }

    /**
     * <b>O(n^2)</b>: concatena la representación de todos los elementos.
     *
     * @return representación textual de la lista
     */
    @Override
    public String toString() {
        String result = new String();
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            T elem = it.next();
            result = result + "[" + elem.toString() + "] \n";
        }
        return "DoubleLinkedList " + result + "]";
    }

}
