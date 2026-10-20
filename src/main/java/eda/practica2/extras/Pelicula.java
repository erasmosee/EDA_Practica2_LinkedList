package eda.practica2.extras;

import java.util.Iterator;

import eda.practica2.OrderedDoubleLinkedList;

public class Pelicula implements Comparable<Pelicula> {

    // atributos
    private String id;
    private String name;
    private OrderedDoubleLinkedList<Actor> actorLinkedList = new OrderedDoubleLinkedList<>();

    public Pelicula(String sId, String sName) { // Constructora
        id = sId;
        name = sName;
    }

    public String getId() {
        return id;
    }

    public void setId(String sId) {
        this.id = sId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Pelicula other = (Pelicula) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        if (id == null) {
            return 0;
        }
        return id.hashCode();
    }

    @Override
    public int compareTo(Pelicula arg0) {
        //return name.compareToIgnoreCase(arg0.name);
        return id.compareTo(arg0.id);
    }

    @Override
    public String toString() {
        return name + " " + id;
    }

    public OrderedDoubleLinkedList<Actor> getActorLinkedList() {
        return actorLinkedList;
    }

    public void setActorLinkedList(OrderedDoubleLinkedList<Actor> actorLinkedList) {
        this.actorLinkedList = actorLinkedList;
    }

    public void combinarCon(Pelicula otra) {
        Iterator<Actor> it = otra.getActorLinkedList().iterator();

        while (it.hasNext()) {
            Actor actor = it.next();
            Actor actorDelaPelicula = actorLinkedList.findOrAdd(actor);
            actorDelaPelicula.getPeliculaLinkedList().add(this);
        }
    }
} // end Pelicula
