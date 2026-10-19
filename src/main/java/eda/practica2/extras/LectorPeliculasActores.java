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
        int registrosAntLeidos = 0;
        // for (int i = 0; i < ficherosTxt.size(); i++) {
        for (int i = 0; i < 10; i++) {
            File fichero = ficherosTxt.get(i);
            System.out.println("Leyendo fichero " + (i + 1) + " de " + ficherosTxt.size() + ": " + fichero.getName() + " ");

            OrderedDoubleLinkedList<Actor> actoresDelFichero = new OrderedDoubleLinkedList<>();
            OrderedDoubleLinkedList<Pelicula> peliculasDelFichero = new OrderedDoubleLinkedList<>();

            long tiempoInicio = Utils.iniciarCronometro();
            registrosAntLeidos = registrosLeidos;
            registrosLeidos += leerFichero(fichero, listaActores, listaPeliculas, actoresDelFichero, peliculasDelFichero);

            listaActores.mergeEficaz(actoresDelFichero);
            listaPeliculas.mergeEficaz(peliculasDelFichero);
            System.out.println("    Tamaño fichero leido: " + fichero.length());
            System.out.println("    Registros leidos: " + (registrosLeidos - registrosAntLeidos));
            System.out.println("    Tamaño listaActoresDelFichero: " + actoresDelFichero.size());
            System.out.println("    Tamaño listaPeliculassDelFichero: " + peliculasDelFichero.size());
            System.out.println("    Tamaño listaActores: " + listaActores.size());
            System.out.println("    Tamaño listaPeliculas: " + listaPeliculas.size());

            Utils.mostrarCronometro("Tiempo (ms):", tiempoInicio);
        }

        System.out.println("Lectura de carpeta terminada.");
        return registrosLeidos;
    }

    private int leerFichero(File fichero, OrderedDoubleLinkedList<Actor> actoresAcumulados, OrderedDoubleLinkedList<Pelicula> peliculasAcumuladas, OrderedDoubleLinkedList<Actor> actoresDelFichero, OrderedDoubleLinkedList<Pelicula> peliculasDelFichero) throws IOException {
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

                //   Actor actor = actoresAcumulados.find(new Actor(idActor, ""));
                //    if (actor == null) {
                //Actor actor = actoresDelFichero.find(new Actor(idActor, ""));
                //       if (actor == null) {
                // Actor actor = new Actor(idActor, nombreActor);
                // actoresDelFichero.add(actor);
                //        }
                //    }
                Actor actor = actoresDelFichero.findOrAdd(new Actor(idActor, nombreActor));

                //   Pelicula pelicula = peliculasAcumuladas.find(new Pelicula(idPelicula, ""));
                //   if (pelicula == null) {
                //   Pelicula    pelicula = peliculasDelFichero.find(new Pelicula(idPelicula, ""));
                //       if (pelicula == null) {
                // Pelicula pelicula = new Pelicula(idPelicula, nombrePelicula);
                // peliculasDelFichero.add(pelicula);
                //        }
                //    }
                Pelicula pelicula = peliculasDelFichero.findOrAdd(new Pelicula(idPelicula, nombrePelicula));

                // if (actor.getPeliculaLinkedList().find(pelicula) == null) {
                actor.getPeliculaLinkedList().add(pelicula);
                // }
                // if (pelicula.getActorLinkedList().find(actor) == null) {
                pelicula.getActorLinkedList().add(actor);
                //   }

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
