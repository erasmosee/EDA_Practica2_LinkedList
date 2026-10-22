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
     * Esta funcion da una vuelta por la lista buscando que el elemento pasado
     * sea igual a alguno existente y mirando no llegar al primero.
     *
     * @param elem
     * @return
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

    @Override
    //Da acceso al primer elemento de la lista
    public T first() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            return first.data;
        }
    }

    @Override
    //Da acceso al último elemento de la lista
    public T last() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            return first.prev.data;
        }
    }

    @Override
    //Determina si la lista contiene un elemento concreto, y develve su dato, null en caso de que no está
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

    @Override
    //Determina si la lista contiene un elemento concreto, y develve su dato, null en caso de que no está
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

    @Override
    //Determina si la lista está vacía
    public boolean isEmpty() {
        return first == null;
    }

    //Determina si la lista tiene un unico elemento
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

    @Override
    //Determina el número de elementos de la lista
    public int size() {
        return count;
    }

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
