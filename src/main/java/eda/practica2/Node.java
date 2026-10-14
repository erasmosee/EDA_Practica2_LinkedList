package eda.practica2;

@Deprecated 
public class Node<T> {

    @Deprecated
    public T data; 			// dato del nodo
    @Deprecated
    public Node<T> prev; 	// puntero al anterior nodo de la lista
    @Deprecated
    public Node<T> next; 	// puntero al siguiente nodo de la lista
    // -------------------------------------------------------------

    @Deprecated
    public Node(T dd) // constructor
    {
        data = dd;
        next = null;
        prev = null;
    }
    
}
