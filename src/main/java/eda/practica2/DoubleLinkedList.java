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
                first.prev.prev.next = first.prev.next;
                first.prev = first.prev.prev;
            }
            count--;
        }
        return aux.data;
    }
//Esta funcion da una vuelta por la lista buscando que el elemento pasado sea igual a alguno existente y mirando no llegar al primero.

    protected Node<T> locate(T elem) {
        Node<T> aux;
        aux = first;
        int contador = 0;
        if (!Objects.equals(elem, first)) {
            // Elem no es igual que first
            while (!Objects.equals(elem, aux.data) && contador < size()) {
                //Miramos si elem es igual que el nodo actual hasta que lleguemos al ultimo que chocara con first
                aux = aux.next;
                contador++;
            }
            if (Objects.equals(aux.data, first)) { // Ha llegado al ultimo
                aux = null;
            }
        }
        return aux;
    }

    @Override
    public T remove(T elem
    ) {
        Node<T> aux;
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            aux = locate(elem);

            if (aux == first) {
                return removeFirst();
            }
            if (Objects.equals(aux.data, elem)) {
                aux.prev.next = aux.next;
                aux.next.prev = aux.prev;
                count--;
            } else {
                return null;
            }
        }
        return aux.data;
    }

    @Override
    public T first() {
        //Da acceso al primer elemento de la lista
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            return first.data;
        }
    }

    @Override
    public T last() {
        //Da acceso al último elemento de la lista
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            return first.prev.data;
        }
    }

    @Override
    public boolean contains(T elem
    ) {//TODO
        Node<T> aux;
        if (isEmpty()) {
            return false;

        } else {
            aux = locate(elem);

            return Objects.equals(aux.data, elem);
        }
    }

    @Override
    public T find(T elem
    ) {//TODO

        //Determina si la lista contiene un elemento concreto, y develve su referencia, null en caso de que no está
        Node<T> aux;
        if (isEmpty()) {
            throw new NoSuchElementException();

        } else {
            aux = locate(elem);

            if (Objects.equals(aux.data, elem)) {
                return aux.data;
            } else {
                return null;
            }
        }
    }

    // Funcion a la que le preguntas si esta vacio y si esta vacio inserta el elemento. devuelve si estaba o no vacia antes de insertar
    public void addWhenEmpty(T elem) {
        Node<T> newNode = new Node<>(elem);
        newNode.next = newNode;
        newNode.prev = newNode;
        first = newNode;
    }

    @Override
    public boolean isEmpty() //Determina si la lista está vacía
    {
        return first == null;
    }

    public boolean isAlone() //Determina si la lista tiene un unico elemento
    {
        return first == first.next;
    }

    public void actualizarFirst(Node<T> node) //Determina si la lista tiene un unico elemento
    {
        first = node;
    }

    @Override
    public int size() //Determina el número de elementos de la lista
    {
        return count;
    }

    @Override
    public Iterator<T> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<T> {//TODO

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

    protected class Node<T> {

        protected T data; 			// dato del nodo
        protected Node<T> prev; 	// puntero al anterior nodo de la lista
        protected Node<T> next; 	// puntero al siguiente nodo de la lista
        // -------------------------------------------------------------

        protected Node(T dd) // constructor
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
        public Node(T dd, Node<T> prev, Node<T> next) // constructor
        {
            data = dd;
            this.next = next;
            this.prev = prev;
        }
    }

    /**
     * insertAdd inserta un elemento entre prev y next, actualizando prev.next y
     * next.prev
     *
     * @param elem
     * @param prev
     * @param next
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
