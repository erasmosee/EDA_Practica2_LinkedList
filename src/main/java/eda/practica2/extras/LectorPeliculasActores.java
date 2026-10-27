package eda.practica2.extras;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import eda.practica2.OrderedDoubleLinkedList;
import eda.practica2.Utils;

public class LectorPeliculasActores {

    public int leerCarpeta(String rutaCarpeta, OrderedDoubleLinkedList<Actor> listaActores, OrderedDoubleLinkedList<Pelicula> listaPeliculas,int tamanioLote) throws IOException {
        File carpeta = new File(rutaCarpeta);
        File[] ficheros = carpeta.listFiles();
        Utils.traza("leerCarpeta", "leer carpeta inicio");

        if (ficheros == null) {
            throw new IOException("No se puede leer la carpeta: " + rutaCarpeta);
        }
        // vamos metiendo cada fichero en el arraylist de ficheros
        ArrayList<File> ficherosTxt = new ArrayList<>();
        for (File fichero : ficheros) {
            if (fichero.isFile() && fichero.getName().endsWith(".txt")) {
                ficherosTxt.add(fichero);
            }
        }
        Collections.sort(ficherosTxt);

        int registrosLeidos = 0;
        int registrosAntLeidos;
        // le pongo 10 porque no quiero recorrer los 54 ficheros a cada prueba
        long tiempoCarpeta = Utils.iniciarCronometro();

        for (int i = 0; i < ficherosTxt.size(); i++) {
            File fichero = ficherosTxt.get(i);
            Utils.traza("leerFichero", "Leyendo fichero " + (i + 1) + " de " + ficherosTxt.size() + ": " + fichero.getName() + " ");
            int anioFichero = Integer.parseInt(fichero.getName().substring(fichero.getName().lastIndexOf('_') + 1, fichero.getName().lastIndexOf('.')));
            OrderedDoubleLinkedList<Actor> actoresDelFichero = new OrderedDoubleLinkedList<>();
            OrderedDoubleLinkedList<Pelicula> peliculasDelFichero = new OrderedDoubleLinkedList<>();

            long tiempoInicio = Utils.iniciarCronometro();
            registrosAntLeidos = registrosLeidos;

            registrosLeidos += leerFichero(fichero, actoresDelFichero, peliculasDelFichero, anioFichero,tamanioLote);

            listaActores.merge(actoresDelFichero);
            listaPeliculas.merge(peliculasDelFichero);

            Utils.traza("leerFicheroTamaño", "Tamaño fichero leido: " + fichero.length());
            Utils.traza("leerFicheroRegistros", "Registros leidos: " + (registrosLeidos - registrosAntLeidos));
            Utils.traza("leerFicheroListado", "Tamaño listaActores: " + listaActores.size());
            Utils.traza("leerFicheroListado", "Tamaño listaPeliculas: " + listaPeliculas.size());

            Utils.mostrarCronometro("leerFichero", tiempoInicio);
        }
        Utils.mostrarCronometro("leerCarpeta", tiempoCarpeta);

        Utils.traza("leerCarpeta", "Lectura de carpeta terminada.");

        return registrosLeidos;
    }

    private int leerFichero(File fichero, OrderedDoubleLinkedList<Actor> actoresDelFichero, OrderedDoubleLinkedList<Pelicula> peliculasDelFichero, int anioFichero,int tamanioLote) throws IOException {
        OrderedDoubleLinkedList<Actor> actoresLote = new OrderedDoubleLinkedList<>();
        OrderedDoubleLinkedList<Pelicula> peliculasLote = new OrderedDoubleLinkedList<>();
        int registrosEnLote = 0;
        int registrosLeidos = 0;
        int numeroLinea = 0;

        try (Scanner entrada = new Scanner(fichero, StandardCharsets.UTF_8)) {
            while (entrada.hasNextLine()) {

                String linea = entrada.nextLine();
                numeroLinea++;

                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] datos = linea.split("\\s+###\\s+");
                if (datos.length != 4) {
                    throw new IOException("Formato incorrecto en " + fichero.getPath() + ", línea " + numeroLinea);
                }

                String idActor = extraerId(datos[0]);
                String nombreActor = datos[1].trim();
                String idPelicula = extraerId(datos[2]);
                String nombrePelicula = datos[3].trim();

                Actor actor;
                Pelicula pelicula;

                actor = actoresLote.findOrAdd(new Actor(idActor, nombreActor, anioFichero));
                pelicula = peliculasLote.findOrAdd(new Pelicula(idPelicula, nombrePelicula, anioFichero));

                actor.getPeliculaLinkedList().addToFront(pelicula);
                pelicula.getActorLinkedList().addToFront(actor);
                registrosLeidos++;
                registrosEnLote++;
                if ( registrosEnLote == tamanioLote) {

                    actoresDelFichero.merge(actoresLote);
                    peliculasDelFichero.merge(peliculasLote);

                    actoresLote = new OrderedDoubleLinkedList<>();
                    peliculasLote = new OrderedDoubleLinkedList<>();
                    registrosEnLote = 0;
                }
            }
            if (registrosEnLote > 0) {
                actoresDelFichero.merge(actoresLote);
                peliculasDelFichero.merge(peliculasLote);
            }
        }
        return registrosLeidos;
    }

    private String extraerId(String texto) {
        String valor = texto.trim();
        int posicion = valor.indexOf("/entity/");
        if (posicion >= 0) {
            return valor.substring(posicion + 8);
        }
        return valor;
    }
}
