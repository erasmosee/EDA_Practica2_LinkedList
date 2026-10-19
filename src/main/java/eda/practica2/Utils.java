package eda.practica2;

import java.util.HashMap;
import java.util.Map;

public class Utils {

    private static final int NIVEL_INFO = 3;
    private static final Map<String, Integer> nivelesTags = new HashMap<>();

    static {
        nivelesTags.put("inicio", 9);
        nivelesTags.put("mergePruebas", 2);
        nivelesTags.put("mergeEficaz", 2);

        nivelesTags.put("leerCarpeta", 3);
        nivelesTags.put("leerCarpetaFichero", 3);
        nivelesTags.put("leerCarpetaTamaño", 3);
        nivelesTags.put("leerCarpetaRegistros", 3);
        nivelesTags.put("leerCarpetaListado", 3);
        nivelesTags.put("leerCarpetaFin", 5);

        nivelesTags.put("leerFicheroHashMap", 2);
        nivelesTags.put("leerFichero", 2);
        nivelesTags.put("main", 5);

        nivelesTags.put("metodofind", 2);
        nivelesTags.put("metodogetActor", 2);
        nivelesTags.put("tieneRepes", 8);
        nivelesTags.put("estaOrdenada", 8);
        nivelesTags.put("tieneRepesDetalle", 3);
        nivelesTags.put("estaOrdenadaDetalle", 3);
    }

    public static boolean debeMostrar(String tag) {
        int umbral = nivelesTags.getOrDefault(tag, 0);

        if (0 == umbral) {
            System.out.println("Falta agregar TAG de :" + tag);
        }
        return NIVEL_INFO < umbral;
    }

    public static long iniciarCronometro() {
        return System.currentTimeMillis();
    }

    public static long devolverCronometro(String tag, long tiempo) {
        long retorno = 0;
        if (debeMostrar(tag)) {
            retorno = System.currentTimeMillis() - tiempo;
        }
        return retorno;
    }

    public static void mostrarCronometro(String tag, long tiempo) {
        if (debeMostrar(tag)) {
            if (tiempo > 10000) {
                System.out.println(tag + ": Tiempo (s): " + devolverCronometro(tag, tiempo) / 1000);
            } else {
                System.out.println(tag + ": Tiempo (ms): " + devolverCronometro(tag, tiempo));
            }
        }
    }

    public static void traza(String tag, String traza) {
        if (debeMostrar(tag)) {
            System.out.println(tag + ": " + traza);
        }
    }
}
