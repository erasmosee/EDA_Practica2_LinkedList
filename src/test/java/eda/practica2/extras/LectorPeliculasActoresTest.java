package eda.practica2.extras;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import eda.practica2.OrderedDoubleLinkedList;
import eda.practica2.Utils;

class LectorPeliculasActoresTest {

    @TempDir
    Path carpetaTemporal;

    @Test
    void leeActoresYPeliculasDelFichero() throws IOException {

        String ruta = Utils.cargarPropiedad("test.ruta");

        OrderedDoubleLinkedList<Actor> actores = new OrderedDoubleLinkedList<>();
        OrderedDoubleLinkedList<Pelicula> peliculas = new OrderedDoubleLinkedList<>();
        LectorPeliculasActores lector = new LectorPeliculasActores();

        int registros = lector.leerCarpeta(ruta, actores, peliculas, 2, 1, 0);

        assertEquals(100, registros);
        assertEquals(50, actores.size());
        assertEquals(40, peliculas.size());

        Iterator<Actor> iterador = actores.iterator();
        Actor actor = iterador.next();
        assertEquals("Q106816", actor.getId());
        assertEquals("Àlex Brendemühl", actor.getName());
        assertEquals(1, actor.getPeliculaLinkedList().size());
        actor = iterador.next();
        assertEquals("Q107617828", actor.getId());
        assertEquals("Yiannis Niarros", actor.getName());
        assertEquals(1, actor.getPeliculaLinkedList().size());

        Actor encontrado = actores.find(new Actor("Q8353578", "", 0));
        assertNotNull(encontrado);
        assertEquals("Dani Rovira", encontrado.getName());
        assertTrue(encontrado.getAnios().contains(2015));

    }
}
