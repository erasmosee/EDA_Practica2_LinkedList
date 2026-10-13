package eda.practica2;

import java.util.Iterator;

public class PruebaUnorderedDoubleLinkedList {

    public static void visualizarNodos(UnorderedDoubleLinkedList<Integer> l) {
        Iterator<Integer> it = l.iterator();
        System.out.println();
        while (it.hasNext()) {
            Integer num = it.next();
            System.out.println(num);
        }
    }

    public static void main(String[] args) {

        UnorderedDoubleLinkedList<Integer> udll = new UnorderedDoubleLinkedList<>();
       
       for (int i = 5; i < 10; i++) {
            System.out.println("Agregamos elemento " + i);
        udll.addToRear(i);
       }
       for (int i = 4; i > 0; i--) {
            System.out.println("Agregamos elemento " + i);
        udll.addToFront(i);
       }
        udll.remove(7);

        System.out.print(" Lista ...............");
        visualizarNodos(udll);
        System.out.println(" Num elementos: " + udll.size());

        System.out.println("Prueba Find ...............");
        System.out.println("9? " + udll.find(9));
        System.out.println("0? " + udll.find(0));
        System.out.println("7? " + udll.find(7));

    }
}
