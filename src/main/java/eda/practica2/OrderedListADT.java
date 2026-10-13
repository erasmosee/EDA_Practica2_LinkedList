package eda.practica2;

public interface OrderedListADT<T> extends ListADT<T> {

    public void add(T elem);
    // añade un elemento a la lista (en el lugar de orden que le corresponde)

    //Implementar comparable pero dejar esta funcion para el final
    public void merge(DoubleLinkedList<T> zerrenda);

}
