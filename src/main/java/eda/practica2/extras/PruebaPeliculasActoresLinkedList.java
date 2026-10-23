package eda.practica2.extras;

import java.io.IOException;
import java.util.Iterator;

import eda.practica2.OrderedDoubleLinkedList;
import eda.practica2.Utils;

public class PruebaPeliculasActoresLinkedList {

    public static void main(String[] args) throws IOException {
        Utils.traza("programa", "iniciando programa");
        long tiempoPrograma = Utils.iniciarCronometro();

        String ruta = Utils.cargarPropiedad("datos.ruta");
        if (args.length > 0) {
            ruta = args[0];
        }

        int[] metodosMerge = {1};
        int tamanioLote = 127;

        //LectorHashMapPeliculasActores lector = new LectorHashMapPeliculasActores();
        for (int metodoMerge : metodosMerge) {
            //      ejecutarPrueba(ruta, 1, metodoMerge, 0);
            // for (int i = 0; i < 10; i++) {
            ejecutarPrueba(ruta, 2, metodoMerge, tamanioLote);
            //}

        }
        Utils.mostrarCronometro("programa", tiempoPrograma);
        Utils.traza("programa", "fin programa");

    }

    private static void ejecutarPrueba(String ruta, int metodoHash, int metodoMerge, int tamanioLote) throws IOException {
        Utils.iniciarContadorcomparaciones();
        long tiempoPrueba = Utils.iniciarCronometro();

        OrderedDoubleLinkedList<Actor> actores = new OrderedDoubleLinkedList<>();
        OrderedDoubleLinkedList<Pelicula> peliculas = new OrderedDoubleLinkedList<>();
        LectorPeliculasActores lector = new LectorPeliculasActores();



        int registrosLeidos = lector.leerCarpeta(ruta, actores, peliculas, metodoHash, metodoMerge, tamanioLote);


        Utils.traza("detalleComplejidad", "nº comparaciones del metodo: " + Utils.consultarContadorcomparaciones());
        Utils.traza("estaOrdenada", "Actores supera prueba ordenados: " + estaOrdenada(actores));
        Utils.traza("tieneRepes", "Actores supera prueba repetidos: " + tieneRepes(actores));

        Utils.traza("estaOrdenada", "Películas supera prueba ordenadas: " + estaOrdenada(peliculas));
        Utils.traza("tieneRepes", "Películas supera prueba repetidos: " + tieneRepes(peliculas));

        Utils.traza("datosLeidos", "Registros leídos: " + registrosLeidos);
        Utils.traza("datosLeidos", "Actores: " + actores.size());
        Utils.traza("datosLeidos", "Películas: " + peliculas.size());
        Utils.mostrarCronometro("tiempoPrueba", tiempoPrueba);

        System.out.println();
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
