package eda.practica2.extras;

import java.io.IOException;

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

       
    }
}
