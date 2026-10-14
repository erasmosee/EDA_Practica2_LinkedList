package eda.practica2;

public class Utils {

    public static long iniciarCronometro() {
        return System.currentTimeMillis();
    }

    public static long devolverCronometro(long tiempo) {
        return System.currentTimeMillis() - tiempo;
    }

    public static void mostrarCronometro(String mensaje, long tiempo) {
        System.out.println(mensaje + " " + devolverCronometro(tiempo));
    }
}
