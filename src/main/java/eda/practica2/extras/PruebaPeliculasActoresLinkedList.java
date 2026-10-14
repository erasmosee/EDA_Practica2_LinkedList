package eda.practica2.extras;

import java.util.Iterator;

import eda.practica2.OrderedDoubleLinkedList;

public class PruebaPeliculasActoresLinkedList {

    public static void visualizarNodosActor(OrderedDoubleLinkedList<Actor> l) {
        Iterator<Actor> it = l.iterator();
        System.out.println();
        while (it.hasNext()) {
            Actor num = it.next();
            System.out.println(num);
        }
    }

    public static void visualizarNodosPelicula(OrderedDoubleLinkedList<Pelicula> l) {
        Iterator<Pelicula> it = l.iterator();
        System.out.println();
        while (it.hasNext()) {
            Pelicula num = it.next();
            System.out.println(num);
        }
    }

    public static void main(String[] args) {

        OrderedDoubleLinkedList<Pelicula> odll2 = new OrderedDoubleLinkedList<>();
        odll2.add(new Pelicula("jon", "1111"));
        odll2.add(new Pelicula("ana", "7777"));
        odll2.add(new Pelicula("amaia", "3333"));
        odll2.add(new Pelicula("unai", "8888"));
        odll2.add(new Pelicula("pedro", "2222"));
        odll2.add(new Pelicula("olatz", "5555"));

        odll2.remove(new Pelicula("", "8888"));

        System.out.print("Lista ...............");
        visualizarNodosPelicula(odll2);
        System.out.println(" Num elementos: " + odll2.size());

        System.out.println("Prueba Find ...............");
        System.out.println("2222? " + odll2.find(new Pelicula("", "2222")));
        System.out.println("5555? " + odll2.find(new Pelicula("", "5555")));
        System.out.println("7777? " + odll2.find(new Pelicula("", "7777")));
        System.out.println("8888? " + odll2.find(new Pelicula("", "8888")));

    }
}
