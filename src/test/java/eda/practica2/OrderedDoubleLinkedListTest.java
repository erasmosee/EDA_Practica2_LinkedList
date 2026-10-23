package eda.practica2;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import eda.practica2.extras.Actor;

class OrderedDoubleLinkedListTest {

    private OrderedDoubleLinkedList<String> listaLetras;

    @BeforeEach
    void setUp() {
        listaLetras = new OrderedDoubleLinkedList<>();
    }

    @Test
    @DisplayName("testOrdenYDuplicados")
    void testOrdenYDuplicados() {
        setUp();
        listaLetras.add("C");
        listaLetras.add("A");
        listaLetras.add("B");
        listaLetras.add("A"); // Duplicado

        assertEquals(3, listaLetras.size());

        Iterator<String> it = listaLetras.iterator();
        assertEquals("A", it.next());
        assertEquals("B", it.next());
        assertEquals("C", it.next());
        assertFalse(it.hasNext());
    }

    @Test
    @DisplayName("testFindOrAdd")
    void testFindOrAdd() {
        setUp();
        String res1 = listaLetras.findOrAdd("B");
        assertEquals("B", res1);
        assertEquals(1, listaLetras.size());

        String res2 = listaLetras.findOrAdd("B");
        assertEquals("B", res2);
        assertEquals(1, listaLetras.size()); // No añade duplicado
    }

    // --- PRUEBAS DE MERGE ---
    @Test
    @DisplayName("testMergeVaciaConVacia")
    void testMergeVaciaConVacia() {
        setUp();
        OrderedDoubleLinkedList<String> fuente = new OrderedDoubleLinkedList<>();
        listaLetras.merge(fuente);
        assertTrue(listaLetras.isEmpty());
        assertEquals(0, listaLetras.size());
    }

    @Test
    @DisplayName("testMergeVaciaConDatos")
    void testMergeVaciaConDatos() {
        setUp();
        OrderedDoubleLinkedList<String> fuente = new OrderedDoubleLinkedList<>();
        fuente.add("A");
        fuente.add("B");

        listaLetras.merge(fuente);

        assertEquals(2, listaLetras.size());
        assertEquals("A", listaLetras.first());
        assertEquals("B", listaLetras.last());

        // Comprobación de independencia / comportamiento del contrato
        fuente.add("C");
        assertFalse(listaLetras.contains("C"), "Modificar la fuente tras merge no debería afectar a destino si son independientes");
    }

    @Test
    @DisplayName("testMergeConDatosConVacia")
    void testMergeConDatosConVacia() {
        setUp();
        listaLetras.add("A");
        listaLetras.add("B");
        OrderedDoubleLinkedList<String> fuente = new OrderedDoubleLinkedList<>();

        listaLetras.merge(fuente);
        assertEquals(2, listaLetras.size());
    }

    @Test
    @DisplayName("testMergeRangosSeparados")
    void testMergeRangosSeparados() {
        setUp();
        listaLetras.add("A");
        listaLetras.add("B");

        OrderedDoubleLinkedList<String> fuente = new OrderedDoubleLinkedList<>();
        fuente.add("C");
        fuente.add("D");

        listaLetras.merge(fuente);

        assertEquals(4, listaLetras.size());
        assertEquals("A", listaLetras.first());
        assertEquals("D", listaLetras.last());
    }

    @Test
    @DisplayName("testMergeRangosIntercalados")
    void testMergeRangosIntercalados() {
        setUp();
        listaLetras.add("A");
        listaLetras.add("C");

        OrderedDoubleLinkedList<String> fuente = new OrderedDoubleLinkedList<>();
        fuente.add("B");
        fuente.add("D");

        listaLetras.merge(fuente);

        assertEquals(4, listaLetras.size());
        Iterator<String> it = listaLetras.iterator();
        assertEquals("A", it.next());
        assertEquals("B", it.next());
        assertEquals("C", it.next());
        assertEquals("D", it.next());
    }

    @Test
    @DisplayName("testMergeConIDsRepetidos")
    void testMergeConIDsRepetidos() {
        setUp();
        OrderedDoubleLinkedList<Actor> actores1 = new OrderedDoubleLinkedList<>();
        OrderedDoubleLinkedList<Actor> actores2 = new OrderedDoubleLinkedList<>();

        actores1.add(new Actor("Q000000001", "Actor 1", 2020));
        actores2.add(new Actor("Q000000001", "Actor 1", 2021));

        actores1.merge(actores2);

        assertEquals(1, actores1.size());
        Actor combinado = actores1.first();
        assertTrue(combinado.getAnios().contains(2020));
        assertTrue(combinado.getAnios().contains(2021));
    }

    @Test
    @DisplayName("testAutoFusion")
    void testAutoFusion() {
        setUp();
        listaLetras.add("A");
        listaLetras.add("B");

        assertDoesNotThrow(() -> listaLetras.merge(listaLetras));
        assertTrue(listaLetras.size() >= 2);
    }
}
