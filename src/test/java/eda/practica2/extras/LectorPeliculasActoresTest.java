package eda.practica2.extras;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import eda.practica2.OrderedDoubleLinkedList;

class LectorPeliculasActoresTest {

    @TempDir
    Path carpetaTemporal;

    @Test
    void leeActoresYPeliculasDelFichero() throws IOException {
        String datos2000 = "Q000000001 ### Actor 1 ### Q000000101 ### Pelicula 1\n"
                     + "Q000000001 ### Actor 1 ### Q000000102 ### Pelicula 2\n"
                     + "Q000000001 ### Actor 1 ### Q000000103 ### Pelicula 3\n"
                     + "Q000000002 ### Actor 2 ### Q000000101 ### Pelicula 1\n";
        Files.writeString(carpetaTemporal.resolve("fichero_2000.txt"), datos2000);
        String datos2001 = "Q000000001 ### Actor 1 ### Q000000101 ### Pelicula 1\n"
                     + "Q000000003 ### Actor 3 ### Q000000102 ### Pelicula 2\n"
                     + "Q000000003 ### Actor 3 ### Q000000103 ### Pelicula 3\n"
                     + "Q000000002 ### Actor 2 ### Q000000101 ### Pelicula 1\n";
        Files.writeString(carpetaTemporal.resolve("fichero_2001.txt"), datos2001);

        OrderedDoubleLinkedList<Actor> actores = new OrderedDoubleLinkedList<>();
        OrderedDoubleLinkedList<Pelicula> peliculas = new OrderedDoubleLinkedList<>();
        LectorPeliculasActores lector = new LectorPeliculasActores();

        int registros = lector.leerCarpeta(
                carpetaTemporal.toString(), actores, peliculas, 2, 1, 0);

        assertEquals(8, registros);
        assertEquals(3, actores.size());
        assertEquals(3, peliculas.size());

        Iterator<Actor> iterador = actores.iterator();
        Actor actor = iterador.next();
        assertEquals("Q000000001", actor.getId());
        assertEquals(4, actor.getPeliculaLinkedList().size());
    }
}
