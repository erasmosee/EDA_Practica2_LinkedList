package eda.practica2.extras;

import eda.practica2.OrderedDoubleLinkedList;

public class Actor implements Comparable<Actor> {

    // atributos
    private String id;
    private String name;
    private OrderedDoubleLinkedList<Pelicula> PeliculaLinkedList = new OrderedDoubleLinkedList<>();

    public Actor(String sId, String sName) { // Constructora
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
        Actor other = (Actor) obj;
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
    public int compareTo(Actor arg0) {
        //return name.compareToIgnoreCase(arg0.name);
        return id.compareTo(arg0.id);
    }

    @Override
    public String toString() {
        return name + " " + id;
    }

    public OrderedDoubleLinkedList<Pelicula> getPeliculaLinkedList() {
        return PeliculaLinkedList;
    }

    public void setPeliculaLinkedList(OrderedDoubleLinkedList<Pelicula> PeliculaLinkedList) {
        this.PeliculaLinkedList = PeliculaLinkedList;
    }

} // end Actor
