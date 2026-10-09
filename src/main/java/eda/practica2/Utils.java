package eda.practica2;

public class Utils {

    public static long iniciarCronometro() {
        return System.currentTimeMillis();
    }

    public static void mostrarCronometro(String mensaje, long tiempo) {
        System.out.println(mensaje + " " + (System.currentTimeMillis() - tiempo));
    }
}
