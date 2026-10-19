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

public class LectorPeliculasActores {

    public int leerCarpeta(String rutaCarpeta, OrderedDoubleLinkedList<Actor> listaActores, OrderedDoubleLinkedList<Pelicula> listaPeliculas) throws IOException {
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

        int registrosLeidos = 0;
        int registrosAntLeidos;
        // le pongo 10 porque no quiero recorrer los 54 ficheros a cada prueba
        // for (int i = 0; i < ficherosTxt.size(); i++) {
        for (int i = 0; i < ficherosTxt.size(); i++) {
            File fichero = ficherosTxt.get(i);
            System.out.println("Leyendo fichero " + (i + 1) + " de " + ficherosTxt.size() + ": " + fichero.getName() + " ");

            OrderedDoubleLinkedList<Actor> actoresDelFichero = new OrderedDoubleLinkedList<>();
            OrderedDoubleLinkedList<Pelicula> peliculasDelFichero = new OrderedDoubleLinkedList<>();

            long tiempoInicio = Utils.iniciarCronometro();
            registrosAntLeidos = registrosLeidos;
            registrosLeidos += leerFicheroHashMap(fichero, actoresDelFichero, peliculasDelFichero);

            listaActores.mergePruebas(actoresDelFichero);
            listaPeliculas.mergePruebas(peliculasDelFichero);

            System.out.println("    Tamaño fichero leido: " + fichero.length());
            System.out.println("    Registros leidos: " + (registrosLeidos - registrosAntLeidos));
            System.out.println("    Tamaño listaActores: " + listaActores.size());
            System.out.println("    Tamaño listaPeliculas: " + listaPeliculas.size());

            Utils.mostrarCronometro("Tiempo (ms):", tiempoInicio);
        }

        System.out.println("Lectura de carpeta terminada.");
        return registrosLeidos;
    }

    private int leerFichero(File fichero, OrderedDoubleLinkedList<Actor> actoresDelFichero, OrderedDoubleLinkedList<Pelicula> peliculasDelFichero) throws IOException {
        long tiempoInicio = Utils.iniciarCronometro();

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

                Actor actor = actoresDelFichero.findOrAdd(new Actor(idActor, nombreActor));
                Pelicula pelicula = peliculasDelFichero.findOrAdd(new Pelicula(idPelicula, nombrePelicula));
                actor.getPeliculaLinkedList().add(pelicula);
                pelicula.getActorLinkedList().add(actor);

                registrosLeidos++;

            }

        }
        Utils.mostrarCronometro("leerFichero: Tiempo (ms):", tiempoInicio);
        return registrosLeidos;

    }

    private int leerFicheroHashMap(File fichero, OrderedDoubleLinkedList<Actor> actoresDelFichero, OrderedDoubleLinkedList<Pelicula> peliculasDelFichero) throws IOException {
        long tiempoInicio = Utils.iniciarCronometro();
        HashMap<String, Actor> actoresPorId = new HashMap<>();
        HashMap<String, Pelicula> peliculasPorId = new HashMap<>();
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

                actor.getPeliculaLinkedList().add(pelicula);
                pelicula.getActorLinkedList().add(actor);
                registrosLeidos++;
            }
        }

        ArrayList<Actor> actoresOrdenados = new ArrayList<>(actoresPorId.values());
        Collections.sort(actoresOrdenados);
        for (Actor actor : actoresOrdenados) {
            actoresDelFichero.add(actor);
        }

        ArrayList<Pelicula> peliculasOrdenadas = new ArrayList<>(peliculasPorId.values());
        Collections.sort(peliculasOrdenadas);
        for (Pelicula pelicula : peliculasOrdenadas) {
            peliculasDelFichero.add(pelicula);
        }

        Utils.mostrarCronometro("leerFicheroHashMap: Tiempo (ms):", tiempoInicio);
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
