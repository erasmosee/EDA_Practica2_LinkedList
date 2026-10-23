package eda.practica2.extras;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import eda.practica2.OrderedDoubleLinkedList;
import eda.practica2.Utils;

class LectorPeliculasActoresTest {

    @Test
    void leeActoresYPeliculasDelFichero() throws IOException {

        String ruta = Utils.cargarPropiedad("test.ruta");

        OrderedDoubleLinkedList<Actor> actores = new OrderedDoubleLinkedList<>();
        OrderedDoubleLinkedList<Pelicula> peliculas = new OrderedDoubleLinkedList<>();
        LectorPeliculasActores lector = new LectorPeliculasActores();

        int registros = lector.leerCarpeta(ruta, actores, peliculas);

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

    @Test
    @DisplayName("Verificación de relaciones cruzadas Actor-Película superando el lote de 127 registros")
    void testRelacionesActorPeliculaConLotes(@TempDir Path tempDir) throws IOException {
        File ficheroTest = tempDir.resolve("datos_2024.txt").toFile();

        // Escribimos 150 registros (>127) para cruzar el límite del lote
        try (FileWriter writer = new FileWriter(ficheroTest)) {
            for (int i = 1; i <= 150; i++) {
                String actorId = "/entity/nm" + String.format("%03d", i);
                String actorNombre = "Actor " + i;
                String peliculaId = "/entity/tt" + String.format("%03d", (i % 10) + 1); // 10 películas repartidas
                String peliculaNombre = "Pelicula " + ((i % 10) + 1);

                writer.write(actorId + " ### " + actorNombre + " ### " + peliculaId + " ### " + peliculaNombre + "\n");
            }
        }

        OrderedDoubleLinkedList<Actor> listaActores = new OrderedDoubleLinkedList<>();
        OrderedDoubleLinkedList<Pelicula> listaPeliculas = new OrderedDoubleLinkedList<>();

        LectorPeliculasActores lector = new LectorPeliculasActores();
        int registros = lector.leerCarpeta(tempDir.toString(), listaActores, listaPeliculas);

        assertEquals(150, registros);
        assertEquals(150, listaActores.size(), "Debe haber 150 actores únicos");
        assertEquals(10, listaPeliculas.size(), "Debe haber 10 películas únicas");

        // 1. Verificar sin duplicados en lista principal de actores usando iterator() explícito
        HashSet<String> idsActores = new HashSet<>();
        Iterator<Actor> itActores = listaActores.iterator();
        while (itActores.hasNext()) {
            Actor actor = itActores.next();
            assertTrue(idsActores.add(actor.getId()), "No deben existir IDs de actores duplicados");

            // 2. Verificar que cada película del actor contiene de vuelta a ese actor
            Iterator<Pelicula> itPeliculasDelActor = actor.getPeliculaLinkedList().iterator();
            while (itPeliculasDelActor.hasNext()) {
                Pelicula p = itPeliculasDelActor.next();
                Pelicula pEncontrada = listaPeliculas.find(p);
                assertNotNull(pEncontrada, "La película debe estar en la lista principal");
                assertTrue(pEncontrada.getActorLinkedList().contains(actor),
                        "La película debe contener la relación de vuelta hacia el actor");
            }
        }

        // 3. Verificar sin duplicados en lista principal de películas usando iterator() explícito
        HashSet<String> idsPeliculas = new HashSet<>();
        Iterator<Pelicula> itPeliculas = listaPeliculas.iterator();
        while (itPeliculas.hasNext()) {
            Pelicula pelicula = itPeliculas.next();
            assertTrue(idsPeliculas.add(pelicula.getId()), "No deben existir IDs de películas duplicados");

            // 4. Verificar que cada actor de la película contiene de vuelta a esa película
            Iterator<Actor> itActoresDeLaPelicula = pelicula.getActorLinkedList().iterator();
            while (itActoresDeLaPelicula.hasNext()) {
                Actor a = itActoresDeLaPelicula.next();
                Actor aEncontrado = listaActores.find(a);
                assertNotNull(aEncontrado, "El actor debe estar en la lista principal");
                assertTrue(aEncontrado.getPeliculaLinkedList().contains(pelicula),
                        "El actor debe contener la relación de vuelta hacia la película");
            }
        }
    }
}
