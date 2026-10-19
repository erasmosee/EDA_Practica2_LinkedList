package eda.practica2.extras;

import java.io.IOException;
import java.util.Iterator;

import eda.practica2.OrderedDoubleLinkedList;
import eda.practica2.Utils;

public class PruebaPeliculasActoresLinkedList {

    private static final String RUTA_DATOS = "C:\\Workspace\\UPV\\EDA\\EDA_Practica1_Peliculas_Actores\\resources";

    public static void main(String[] args) throws IOException {
        String ruta = RUTA_DATOS;
        if (args.length > 0) {
            ruta = args[0];
        }

        OrderedDoubleLinkedList<Actor> actores = new OrderedDoubleLinkedList<>();
        OrderedDoubleLinkedList<Pelicula> peliculas = new OrderedDoubleLinkedList<>();
        long tiempoInicio = Utils.iniciarCronometro();

       LectorPeliculasActores lector = new LectorPeliculasActores();
         //LectorHashMapPeliculasActores lector = new LectorHashMapPeliculasActores();

        int registrosLeidos = lector.leerCarpeta(ruta, actores, peliculas);
        System.out.println("Actores ordenados: " + estaOrdenada(actores));
        System.out.println("Actores repetidos: " + tieneRepes(actores));

        System.out.println("Películas ordenadas: " + estaOrdenada(peliculas));
        System.out.println("Películas repetidos: " + tieneRepes(peliculas));
        System.out.println("Registros leídos: " + registrosLeidos);
        System.out.println("Actores: " + actores.size());
        System.out.println("Películas: " + peliculas.size());
        Utils.mostrarCronometro("Tiempo (ms):", tiempoInicio);

        System.out.println();
        System.out.println("Película - 3564299: " + peliculas.find(new Pelicula("Q3564299", "")));
        Pelicula pelicula = peliculas.find(new Pelicula("Q3564299", ""));

        System.out.println();

        if (pelicula != null) {
            System.out.println("Actores de " + pelicula + ":");

            Iterator<Actor> it = pelicula.getActorLinkedList().iterator();
            while (it.hasNext()) {
                System.out.println(it.next());
            }
        }
    }

    private static <T extends Comparable<T>> boolean estaOrdenada(OrderedDoubleLinkedList<T> lista) {
        Iterator<T> it = lista.iterator();

        if (!it.hasNext()) {
            return true;
        }

        T anterior = it.next();
        while (it.hasNext()) {
            T actual = it.next();

            if (anterior.compareTo(actual) > 0) {
                System.out.println("Desorden detectado entre: " + anterior + " y " + actual);
                return false;
            }

            anterior = actual;
        }

        return true;
    }

    private static <T extends Comparable<T>> boolean tieneRepes(OrderedDoubleLinkedList<T> lista) {
        Iterator<T> it = lista.iterator();

        if (!it.hasNext()) {
            return true;
        }

        T anterior = it.next();
        while (it.hasNext()) {
            T actual = it.next();

            if (anterior.compareTo(actual) == 0) {
                System.out.println("Repetidos detectados entre: " + anterior + " y " + actual);
                return false;
            }

            anterior = actual;
        }

        return true;
    }
}
