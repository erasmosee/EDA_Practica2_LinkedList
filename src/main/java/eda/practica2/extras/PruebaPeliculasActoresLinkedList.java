package eda.practica2.extras;

import java.io.IOException;
import java.util.Iterator;

import eda.practica2.OrderedDoubleLinkedList;
import eda.practica2.Utils;

public class PruebaPeliculasActoresLinkedList {

    public static void main(String[] args) throws IOException {
        String ruta = Utils.cargarPropiedad("datos.ruta");
        if (args.length > 0) {
            ruta = args[0];
        }
        Utils.traza("inicio", "iniciando lectura");

        //LectorHashMapPeliculasActores lector = new LectorHashMapPeliculasActores();
        for (int i = 1; i <= 2; i++) {

            for (int j = 1; j <= 2; j++) {
                OrderedDoubleLinkedList<Actor> actores = new OrderedDoubleLinkedList<>();
                OrderedDoubleLinkedList<Pelicula> peliculas = new OrderedDoubleLinkedList<>();
                LectorPeliculasActores lector = new LectorPeliculasActores();

                long tiempoInicio = Utils.iniciarCronometro();

                switch (Integer.parseInt(String.valueOf(i) + String.valueOf(j))) {
                    case 11 ->
                        Utils.traza("tipoPrueba", "1-1: HashMap - MergeEficaz");
                    case 12 ->
                        Utils.traza("tipoPrueba", "1-2: HashMap - MergePrueba");
                    case 21 ->
                        Utils.traza("tipoPrueba", "2-1: LinkedList - MergeEficaz");
                    case 22 ->
                        Utils.traza("tipoPrueba", "2-2: LinkedList - MergePrueba");
                    default ->
                        throw new AssertionError();
                }
                int registrosLeidos = lector.leerCarpeta(ruta, actores, peliculas, i, j);
                Utils.traza("estaOrdenada", "Actores supera prueba ordenados: " + estaOrdenada(actores));
                Utils.traza("tieneRepes", "Actores supera prueba repetidos: " + tieneRepes(actores));

                Utils.traza("estaOrdenada", "Películas supera prueba ordenadas: " + estaOrdenada(peliculas));
                Utils.traza("tieneRepes", "Películas supera prueba repetidos: " + tieneRepes(peliculas));

                Utils.traza("datosLeidos", "Registros leídos: " + registrosLeidos);
                Utils.traza("datosLeidos", "Actores: " + actores.size());
                Utils.traza("datosLeidos", "Películas: " + peliculas.size());
                Utils.mostrarCronometro("tiempo", tiempoInicio);

                System.out.println();
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
                Utils.traza("estaOrdenadaDetalle", "Desorden detectado entre: " + anterior + " y " + actual);
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
                Utils.traza("tieneRepesDetalle", "Repetidos detectados entre: " + anterior + " y " + actual);
                return false;
            }

            anterior = actual;
        }

        return true;
    }

}
