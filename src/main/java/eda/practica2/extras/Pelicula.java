package eda.practica2.extras;

import java.util.Iterator;

import eda.practica2.OrderedDoubleLinkedList;
import eda.practica2.Utils;

public class Pelicula implements Comparable<Pelicula> {

    // atributos
    private String id;
    private String name;
    private int anio;
    private OrderedDoubleLinkedList<Actor> actorLinkedList = new OrderedDoubleLinkedList<>();

    public Pelicula(String sId, String sName, int iAnio) { // Constructora
        id = sId;
        name = sName;
        anio = iAnio;
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

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
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
        Utils.incrementarContadorcomparaciones();
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

    /**
     * <b>O(n(m + n + k))</b>: combina los actores de {@code otra} con los de
     * esta película. {@code n} es el número de actores de {@code otra},
     * {@code m} el número inicial de actores de esta película y {@code k} el
     * máximo de películas asociadas a un actor.
     *
     * @param otra película cuyos actores se incorporan
     */
    public void combinarCon(Pelicula otra) {
        Iterator<Actor> it = otra.getActorLinkedList().iterator();

        while (it.hasNext()) {
            Actor actor = it.next();
            Actor actorDelaPelicula = actorLinkedList.findOrAdd(actor);
            actorDelaPelicula.getPeliculaLinkedList().add(this);
        }
    }
} // end Pelicula
