package eda.practica2;

public interface UnorderedListADT<T> extends ListADT<T> {

    /**
     * Añade un elemento entre el first y el last pero actualizando el first.
     *
     * @param elem
     */
    public void addToFront(T elem);

    /**
     * Añade un elemento entre el first y el last pero no actualiza el first.
     *
     * @param elem
     */
    public void addToRear(T elem);

    /**
     * Añade un elemento despues del target. Si la lista esta vacia, inserta
     * primero target y luego elem. Si la lista no esta vacia y target no se
     * encuentra, no inserta nada.
     *
     * @param elem
     * @param target
     */
    public void addAfter(T elem, T target);

}
