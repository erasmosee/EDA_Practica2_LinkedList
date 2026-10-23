package eda.practica2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import eda.practica2.extras.Actor;
import eda.practica2.extras.Pelicula;

class UnorderedDoubleLinkedListTest {

    UnorderedDoubleLinkedList<Pelicula> listaPeliculas = new UnorderedDoubleLinkedList<>();
    UnorderedDoubleLinkedList<Actor> listaActores = new UnorderedDoubleLinkedList<>();
    UnorderedDoubleLinkedList<String> listaLetras = new UnorderedDoubleLinkedList<>();

    @BeforeEach
    void setUp() {
        listaLetras = new UnorderedDoubleLinkedList<>();
    }

    @Test
    @DisplayName("testAddAfterSinTarget")
    void testAddAfterSinTarget() {

        Actor actorA = new Actor("Q000000001", "Actor 1", 2026);
        Actor actorB = new Actor("Q000000002", "Actor 2", 2026);
        Actor actorC = new Actor("Q000000003", "Actor 3", 2026);

        Actor actorD = new Actor("Q000000004", "Actor 4", 2026);
        Actor actorE = new Actor("Q000000005", "Actor 5", 2026);

        listaActores.addToRear(actorA);
        listaActores.addToRear(actorB);
        listaActores.addToRear(actorC);

        listaActores.addAfter(actorD, actorE);

        assertEquals(3, listaActores.size());
        assertEquals(List.of("Q000000001", "Q000000002", "Q000000003"), idsActores(listaActores));
    }

    @Test
    @DisplayName("testAddToRearaddAfter")
    void testAddToRearaddAfter() {

        Pelicula peliculaA = new Pelicula("Q000000001", "Pelicula 1", 2026);
        Pelicula peliculaB = new Pelicula("Q000000002", "Pelicula 2", 2026);
        Pelicula peliculaC = new Pelicula("Q000000003", "Pelicula 3", 2026);

        listaPeliculas.addToRear(peliculaA);
        listaPeliculas.addToRear(peliculaB);

        listaPeliculas.addAfter(peliculaC, peliculaA);

        assertEquals(List.of("Q000000001", "Q000000003", "Q000000002"), idsPeliculas(listaPeliculas));
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

    @Test
    @DisplayName("testInsertarFrontRear")
    void testInsertarFrontRear() {
        setUp();
        assertTrue(listaLetras.isEmpty());
        assertEquals(0, listaLetras.size());

        // Inserción en lista vacía
        listaLetras.addToFront("B"); // [B]
        assertEquals(1, listaLetras.size());
        assertEquals("B", listaLetras.first());
        assertEquals("B", listaLetras.last());

        // Inserción al principio (1 elemento -> varios)
        listaLetras.addToFront("A"); // [A, B]
        assertEquals(2, listaLetras.size());
        assertEquals("A", listaLetras.first());
        assertEquals("B", listaLetras.last());

        // Inserción al final
        listaLetras.addToRear("C"); // [A, B, C]
        assertEquals(3, listaLetras.size());
        assertEquals("A", listaLetras.first());
        assertEquals("C", listaLetras.last());
    }

    @Test
    @DisplayName("testAddAfterListaVacia")
    void testAddAfterListaVacia() {
        setUp();
        // Caso 1: Lista vacía (inserta target y luego elem)
        listaLetras.addAfter("Elem1", "Target1");
        assertEquals(2, listaLetras.size());
        assertEquals("Target1", listaLetras.first());
        assertEquals("Elem1", listaLetras.last());

        // Caso 2: Target presente
        listaLetras.addAfter("ElemMedio", "Target1"); // [Target1, ElemMedio, Elem1]
        assertEquals(3, listaLetras.size());

        // Caso 3: Target ausente en lista no vacía (no modifica la lista)
        listaLetras.addAfter("NoInsertar", "TargetInexistente");
        assertEquals(3, listaLetras.size());
        assertFalse(listaLetras.contains("NoInsertar"));
    }

    @Test
    @DisplayName("testContainsYFind")
    void testContainsYFind() {
        setUp();
        // Lista vacía
        assertFalse(listaLetras.contains("A"));
        assertNull(listaLetras.find("A"));

        listaLetras.addToRear("X");
        listaLetras.addToRear("Y");

        // Elemento presente
        assertTrue(listaLetras.contains("X"));
        assertEquals("X", listaLetras.find("X"));

        // Elemento ausente
        assertFalse(listaLetras.contains("Z"));
        assertNull(listaLetras.find("Z"));
    }

    @Test
    @DisplayName("testEliminaciones")
    void testEliminaciones() {
        setUp();
        // Excepciones en lista vacía
        assertThrows(NoSuchElementException.class, () -> listaLetras.removeFirst());
        assertThrows(NoSuchElementException.class, () -> listaLetras.removeLast());
        assertThrows(NoSuchElementException.class, () -> listaLetras.remove("X"));

        listaLetras.addToRear("A");
        listaLetras.addToRear("B");
        listaLetras.addToRear("C");
        listaLetras.addToRear("D"); // [A, B, C, D]

        // Eliminar intermedio
        assertEquals("B", listaLetras.remove("B"));
        assertEquals(3, listaLetras.size());
        assertFalse(listaLetras.contains("B"));

        // Eliminar primero
        assertEquals("A", listaLetras.removeFirst());
        assertEquals(2, listaLetras.size());
        assertEquals("C", listaLetras.first());

        // Eliminar último
        assertEquals("D", listaLetras.removeLast());
        assertEquals(1, listaLetras.size());
        assertEquals("C", listaLetras.first());
        assertEquals("C", listaLetras.last());

        // Eliminar único elemento restante
        assertEquals("C", listaLetras.removeFirst());
        assertTrue(listaLetras.isEmpty());
        assertEquals(0, listaLetras.size());
    }
}
