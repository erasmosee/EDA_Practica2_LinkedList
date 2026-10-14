package eda.practica2;

import java.util.Iterator;

public class PruebaOrderedDoubleLinkedList {

    public static void visualizarNodosInteger(OrderedDoubleLinkedList<Integer> l) {
        Iterator<Integer> it = l.iterator();
        System.out.println();
        while (it.hasNext()) {
            Integer num = it.next();
            System.out.println(num);
        }
    }

        public static void visualizarNodosPersona(OrderedDoubleLinkedList<Persona> l) {
        Iterator<Persona> it = l.iterator();
        System.out.println();
        while (it.hasNext()) {
            Persona num = it.next();
            System.out.println(num);
        }
    }

    public static void main(String[] args) {

        OrderedDoubleLinkedList<Integer> odll = new OrderedDoubleLinkedList<>();

        for (int i = 10; i > 0; i--) {
        System.out.println("Agregamos elemento " + i);

            odll.add(i);
            
        }
        odll.remove(7);
        odll.remove(7);

        System.out.print(" Lista ...............");
        visualizarNodosInteger(odll);
        System.out.println("Num elementos: " + odll.size());

        System.out.println("Prueba Find ...............");
        System.out.println("20? " + odll.find(20));
        System.out.println("9? " + odll.find(9));
        System.out.println("0? " + odll.find(0));
        System.out.println("7? " + odll.find(7));

        OrderedDoubleLinkedList<Persona> odll2 = new OrderedDoubleLinkedList<>();
        odll2.add(new Persona("jon", "1111"));
        odll2.add(new Persona("ana", "7777"));
        odll2.add(new Persona("amaia", "3333"));
        odll2.add(new Persona("unai", "8888"));
        odll2.add(new Persona("pedro", "2222"));
        odll2.add(new Persona("olatz", "5555"));

        odll2.remove(new Persona("", "8888"));

        System.out.print("Lista ...............");
        visualizarNodosPersona(odll2);
        System.out.println(" Num elementos: " + odll2.size());

        System.out.println("Prueba Find ...............");
        System.out.println("2222? " + odll2.find(new Persona("", "2222")));
        System.out.println("5555? " + odll2.find(new Persona("", "5555")));
        System.out.println("7777? " + odll2.find(new Persona("", "7777")));
        System.out.println("8888? " + odll2.find(new Persona("", "8888")));

    }
}
