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

    private static final int NIVEL_INFO = 10;
    private static final boolean MOSTRAR_TAG = false;
    private static long contadorComplejidad;
    private static final Map<String, Integer> nivelesTags = new HashMap<>();

    static {

        int valor = 1;

        nivelesTags.put("leerFicheroHashMap", valor);

        valor++; //2
        nivelesTags.put("leerFicheroFichero", valor);
        nivelesTags.put("leerFicheroTamaño", valor);
        nivelesTags.put("leerFicheroRegistros", valor);
        nivelesTags.put("leerFicheroListado", valor);
        valor++; //3
        nivelesTags.put("leerFichero", valor);
        valor++; //4
        nivelesTags.put("tieneRepesDetalle", valor);
        nivelesTags.put("estaOrdenadaDetalle", valor);
        nivelesTags.put("tieneRepes", valor);
        nivelesTags.put("estaOrdenada", valor);
        valor++; //5
        nivelesTags.put("metodofind", valor);
        nivelesTags.put("metodogetActor", valor);
        nivelesTags.put("datosLeidos", valor);
        valor++; //6
        nivelesTags.put("leerCarpeta", valor);
        valor++; //7
        nivelesTags.put("tamanioLote", valor);
        nivelesTags.put("detalleComplejidad", valor);
        valor++; //8
        nivelesTags.put("mergeDetalle", valor);

        valor++; //9
        nivelesTags.put("tipoPrueba", valor); // Tipo de prueba a realizar
        nivelesTags.put("tiempoPrueba", valor); // Tiempo que dura la prueba
        nivelesTags.put("tiempo", valor); // Tiempo que dura la prueba
        valor++; //10
        nivelesTags.put("programa", valor); // Mensaje de inicio del programa
        valor++; //11
        nivelesTags.put("main", valor);
        nivelesTags.put("tablaLatex", valor); // Mensaje de inicio del programa

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
                if (MOSTRAR_TAG) {
                    System.out.println(tag + ": Tiempo (ms): " + devolverCronometro(tag, tiempo));
                } else {
                    System.out.println("Tiempo (ms): " + devolverCronometro(tag, tiempo));
                }

            } else {
                System.out.print(" - Tiempo (ms): " + devolverCronometro(tag, tiempo) + " ");
            }
        }
    }

    public static void traza(String tag, String traza, boolean salto) {
        if (debeMostrar(tag)) {
            if (salto) {
                if (MOSTRAR_TAG) {
                    System.out.println(tag + ": " + traza);
                } else {
                    System.out.println(traza);
                }
            } else {
                System.out.print(" - " + traza + " ");

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
