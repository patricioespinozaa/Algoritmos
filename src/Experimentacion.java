package src;
import java.util.Random;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Experimentacion {
    public static void main(String[] args) {
        // Definimos los rangos para el número de elementos (2^10 a 2^24) y el costoPromedioMaximo
        int[] potenciasDeDos = {10, 12, 14, 16, 18, 20, 22, 24};
        double[] costosPromedioMaximos = {10, 20, 30, 40, 50}; // Diferentes valores de maxCostoPromedio

        // Nombre del archivo de logs
        String nombreArchivo = "resultados_experimentos.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo, true))) { // 'true' para no sobrescribir

            // Escribimos una cabecera inicial para el archivo de logs
            writer.write("Experimentos de Hashing Lineal\n");
            writer.write("------------------------------------\n");

            // Para cada combinación de cantidad de datos y costo promedio máximo
            for (int potencia : potenciasDeDos) {
                int cantidadDeDatos = (int) Math.pow(2, potencia); // Número de datos a insertar

                for (double maxCostoPromedio : costosPromedioMaximos) {
                    // Crear una nueva tabla hash con el maxCostoPromedio
                    Hashing tablaHash = new Hashing(maxCostoPromedio);

                    Random random = new Random();

                    // Insertar elementos aleatorios en la tabla
                    for (int i = 0; i < cantidadDeDatos; i++) {
                        long elemento = random.nextLong();
                        tablaHash.insertar(elemento);
                    }
                    // Guardamos los resultados del experimento en el archivo de logs
                    writer.write("Datos insertados: " + cantidadDeDatos + "\n");
                    writer.write("Costo Promedio Máximo: " + maxCostoPromedio + "\n");
                    writer.write("Promedio de accesos I/O: " + tablaHash.getPromedio() + "\n");
                    writer.write("------------------------------------\n");

                    // También puedes imprimir los resultados en consola si lo deseas
                    System.out.println("Inserciones completadas para " + cantidadDeDatos + " elementos con costoPromedioMaximo: " + maxCostoPromedio);
                    System.out.println("Promedio de accesos I/O: " + tablaHash.getPromedio());
                    System.out.println("----------------------------------------------------");
                }
            }

            // Al final del proceso, cerramos el archivo automáticamente gracias al try-with-resources

        } catch (IOException e) {
            System.out.println("Error al escribir el archivo de logs: " + e.getMessage());
        }
    }
}




