package eda.practica2;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Utils {

    private static final int NIVEL_INFO = 5;
    private static long contadorComplejidad;
    private static final Map<String, Integer> nivelesTags = new HashMap<>();

    static {
        nivelesTags.put("inicio", 9); // Mensaje de inicio del programa
        nivelesTags.put("tipoPrueba", 8); // Tipo de prueba a realizar
        nivelesTags.put("tiempo", 8); // Tiempo que dura la prueba

        nivelesTags.put("mergeDetalle", 7);
        nivelesTags.put("mergeDetalleComplejidad", 6);

        nivelesTags.put("leerCarpeta", 3);
        nivelesTags.put("leerCarpetaFichero", 3);
        nivelesTags.put("leerCarpetaTamaño", 3);
        nivelesTags.put("leerCarpetaRegistros", 3);
        nivelesTags.put("leerCarpetaListado", 3);
        nivelesTags.put("leerCarpetaFin", 5);

        nivelesTags.put("leerFicheroHashMap", 2);
        nivelesTags.put("leerFichero", 2);
        nivelesTags.put("main", 9);
        nivelesTags.put("datosLeidos", 4);

        nivelesTags.put("metodofind", 5);
        nivelesTags.put("metodogetActor", 5);
        nivelesTags.put("tieneRepes", 5);
        nivelesTags.put("estaOrdenada", 5);
        nivelesTags.put("tieneRepesDetalle", 4);
        nivelesTags.put("estaOrdenadaDetalle", 4);
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
        Utils.mostrarCronometro(tag, tiempo, true);
    }

    public static void mostrarCronometro(String tag, long tiempo, boolean salto) {
        if (debeMostrar(tag)) {
            if (salto) {
                System.out.println(tag + ": Tiempo (ms): " + devolverCronometro(tag, tiempo));

            } else {
                System.out.print(" - Tiempo (ms): " + devolverCronometro(tag, tiempo));
            }
        }
    }

    public static void traza(String tag, String traza, boolean salto) {
        if (debeMostrar(tag)) {
            if (salto) {
                System.out.println(tag + ": " + traza);

            } else {
                System.out.print(" - " + traza);

            }
        }
    }

    public static void traza(String tag, String traza) {
        traza(tag, traza, true);
    }

    public static String cargarPropiedad(String inPropiedad) throws IOException {

        //Me estaba volviendo loco intentando cargar el fichero config asi que he tenido que pedir sopitas con el getResourceAsStream para localizar el config de cualquier manera.
        Properties ficheroProperties = new Properties();
        InputStream recurso = Utils.class.getResourceAsStream("/config.properties");
        if (recurso == null) {
            throw new IOException("No se encontró config.properties en el classpath. "
                    + "Debe estar en src/main/resources.");
        }

        try (InputStream input = recurso; Reader reader = new InputStreamReader(input, StandardCharsets.UTF_8)) {
            ficheroProperties.load(reader);
        }

        String outPropiedad = ficheroProperties.getProperty(inPropiedad);
        if (outPropiedad == null) {
            throw new IOException("No se encontró la propiedad '" + inPropiedad + "' en config.properties.");
        }
        return outPropiedad.trim();
    }

    public static void iniciarContadorcomparaciones() {
        contadorComplejidad = 0;
    }

    public static void incrementarContadorcomparaciones() {
        contadorComplejidad++;
    }

    public static long consultarContadorcomparaciones() {
        return contadorComplejidad;
    }

}
