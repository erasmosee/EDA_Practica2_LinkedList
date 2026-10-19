package eda.practica2.extras;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

import eda.practica2.OrderedDoubleLinkedList;
import eda.practica2.Utils;

public class LectorHashMapPeliculasActores {

    public int leerCarpeta(String rutaCarpeta,
            OrderedDoubleLinkedList<Actor> listaActores,
            OrderedDoubleLinkedList<Pelicula> listaPeliculas) throws IOException {
        File carpeta = new File(rutaCarpeta);
        File[] ficheros = carpeta.listFiles();
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

        // Declaramso hashmaps tal y como lo haciamos en la practica 1, vamos a usarlos para leer, grabar en hasmaps y luego mostrar una lista ordeanda
        HashMap<String, Actor> actoresPorId = new HashMap<>();
        HashMap<String, Pelicula> peliculasPorId = new HashMap<>();
        int registrosLeidos = 0;
        int registrosAntLeidos;

        for (int i = 0; i < ficherosTxt.size(); i++) {
            File fichero = ficherosTxt.get(i);
            System.out.print("Leyendo fichero " + (i + 1) + " de "
                    + ficherosTxt.size() + ": " + fichero.getName() + " ");

            long tiempoInicio = Utils.iniciarCronometro();
            registrosAntLeidos = registrosLeidos;

            registrosLeidos += leerFichero(fichero, actoresPorId, peliculasPorId);

            System.out.println("    Tamaño fichero leido: " + fichero.length());
            System.out.println("    Registros leidos: " + (registrosLeidos - registrosAntLeidos));
            System.out.println("    Tamaño listaActores: " + actoresPorId.size());
            System.out.println("    Tamaño listaPeliculas: " + peliculasPorId.size());
            Utils.mostrarCronometro("Tiempo (ms):", tiempoInicio);
        }
        // Usamos las listas ordenadas para guardar los datos en las likenList
        ArrayList<Actor> actoresOrdenados = new ArrayList<>(actoresPorId.values());
        Collections.sort(actoresOrdenados);
        for (Actor actor : actoresOrdenados) {
            listaActores.add(actor);
        }

        ArrayList<Pelicula> peliculasOrdenadas = new ArrayList<>(peliculasPorId.values());
        Collections.sort(peliculasOrdenadas);
        for (Pelicula pelicula : peliculasOrdenadas) {
            listaPeliculas.add(pelicula);
        }

        System.out.println("Lectura de carpeta terminada.");
        return registrosLeidos;
    }

    private int leerFichero(File fichero,
            HashMap<String, Actor> actoresPorId,
            HashMap<String, Pelicula> peliculasPorId) throws IOException {
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
                    throw new IOException("Formato incorrecto en " + fichero.getPath()
                            + ", línea " + numeroLinea);
                }

                String idActor = extraerId(datos[0]);
                String nombreActor = datos[1].trim();
                String idPelicula = extraerId(datos[2]);
                String nombrePelicula = datos[3].trim();

                Actor actor = actoresPorId.get(idActor);
                if (actor == null) {
                    actor = new Actor(idActor, nombreActor);
                    actoresPorId.put(idActor, actor);
                }

                Pelicula pelicula = peliculasPorId.get(idPelicula);
                if (pelicula == null) {
                    pelicula = new Pelicula(idPelicula, nombrePelicula);
                    peliculasPorId.put(idPelicula, pelicula);
                }

                if (actor.getPeliculaLinkedList().find(pelicula) == null) {
                    actor.getPeliculaLinkedList().add(pelicula);
                }
                if (pelicula.getActorLinkedList().find(actor) == null) {
                    pelicula.getActorLinkedList().add(actor);
                }

                registrosLeidos++;
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
