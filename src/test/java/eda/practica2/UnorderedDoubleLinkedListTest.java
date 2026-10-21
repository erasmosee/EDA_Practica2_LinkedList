package eda.practica2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import eda.practica2.extras.Actor;
import eda.practica2.extras.Pelicula;

class UnorderedDoubleLinkedListTest {

    @Test
    void addAfterNoInsertaSiTargetNoExiste() {
        UnorderedDoubleLinkedList<Actor> lista = new UnorderedDoubleLinkedList<>();

        Actor actorA = new Actor("Q000000001", "Actor A");
        Actor actorB = new Actor("Q000000002", "Actor B");
        Actor actorC = new Actor("Q000000003", "Actor C");

        Actor actorD = new Actor("Q000000004", "Actor D");
        Actor actorE = new Actor("Q000000005", "Actor E");

        lista.addToRear(actorA);
        lista.addToRear(actorB);
        lista.addToRear(actorC);

        lista.addAfter(actorD, actorE);

        assertEquals(3, lista.size());
        assertEquals(List.of("Q000000001", "Q000000002", "Q000000003"), idsActores(lista));
    }

    @Test
    void addAfterInsertaDetrasSiTargetExiste() {
        UnorderedDoubleLinkedList<Pelicula> lista = new UnorderedDoubleLinkedList<>();

        Pelicula peliculaA = new Pelicula("Q000000001", "Pelicula A");
        Pelicula peliculaB = new Pelicula("Q000000002", "Pelicula B");
        Pelicula peliculaC = new Pelicula("Q000000003", "Pelicula C");

        lista.addToRear(peliculaA);
        lista.addToRear(peliculaB);

        lista.addAfter(peliculaC, peliculaA);

        assertEquals(List.of("Q000000001", "Q000000003", "Q000000002"), idsPeliculas(lista));
    }

    private static List<String> idsActores(UnorderedDoubleLinkedList<Actor> lista) {
        List<String> ids = new ArrayList<>();
        Iterator<Actor> iterator = lista.iterator();
        while (iterator.hasNext()) {
            ids.add(iterator.next().getId());
        }
        return ids;
    }

    private static List<String> idsPeliculas(UnorderedDoubleLinkedList<Pelicula> lista) {
        List<String> ids = new ArrayList<>();
        Iterator<Pelicula> iterator = lista.iterator();
        while (iterator.hasNext()) {
            ids.add(iterator.next().getId());
        }
        return ids;
    }
}
