package eda;

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

    @Override
    public T remove(T elem) {
        Node<T> aux;
        int contador = 0;
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            aux = first;
            while (!Objects.equals(aux.data, elem) && contador < size()) {
                aux = aux.next;
                contador++;
            }
            if (aux == first && Objects.equals(aux.data, elem)) {
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
    public boolean contains(T elem) {//TODO
        Node<T> aux;
        int contador = 0;
        if (isEmpty()) {
            return false;

        } else {
            aux = first;
            while (!Objects.equals(aux.data, elem) && contador < size()) {
                aux = aux.next;
                contador++;
            }
            return Objects.equals(aux.data, elem);
        }
    }

    @Override
    public T find(T elem) {//TODO

        //Determina si la lista contiene un elemento concreto, y develve su referencia, null en caso de que no está
        Node<T> aux;
        int contador = 0;
        if (isEmpty()) {
            throw new NoSuchElementException();

        } else {
            aux = first;
            while (contador < count && !Objects.equals(aux.data, elem)) {
                aux = aux.next;
                contador++;
            }
            if (Objects.equals(aux.data, elem)) {
                return null;
            } else {
                return aux.data;
            }
        }
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
