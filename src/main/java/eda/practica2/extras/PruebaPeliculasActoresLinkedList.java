package eda.practica2.extras;

import java.io.IOException;
import java.util.Iterator;

import eda.practica2.OrderedDoubleLinkedList;

public class PruebaPeliculasActoresLinkedList {

    private static final String RUTA_DATOS =
            "C:\\Workspace\\UPV\\EDA\\EDA_Practica1_Peliculas_Actores\\resources";

    public static void main(String[] args) throws IOException {
        String ruta = RUTA_DATOS;
        if (args.length > 0) {
            ruta = args[0];
        }

        OrderedDoubleLinkedList<Actor> actores = new OrderedDoubleLinkedList<>();
        OrderedDoubleLinkedList<Pelicula> peliculas = new OrderedDoubleLinkedList<>();

        LectorPeliculasActores lector = new LectorPeliculasActores();
        int registrosLeidos = lector.leerCarpeta(ruta, actores, peliculas);

        System.out.println("Registros leídos: " + registrosLeidos);
        System.out.println("Actores: " + actores.size());
        System.out.println("Películas: " + peliculas.size());
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
}
