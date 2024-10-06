// ======================== ESTRUCTURA PARA LA EXPERIMENTACION ======================== //

package src;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Experimentacion {

    public static void main(String[] args) {
        int[] tamaniosN = new int[]{10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24}; // Tamaños de N (2^10 a 2^24)
        double[] costosMaximos = new double[]{10.0, 30.0, 50.0, 70.0, 90.0};                     // Diferentes valores de costo máximo permitido
        String archivoResultados = "resultados_experimentos.txt";

        // Listas para almacenar todas las series de datos para el gráfico (1)
        List<List<Integer>> listaCantidadDatos = new ArrayList<>();
        List<List<Integer>> listaCostosReales = new ArrayList<>();

        // Listas para el gráfico (2): Porcentaje de llenado y costos reales
        List<List<Double>> listaPorcentajesLlenado = new ArrayList<>();
        List<List<Integer>> listaCostosRealesLlenado = new ArrayList<>();

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(archivoResultados, true))) {
            escritor.write("Experimentos de Hashing Lineal\n");
            escritor.write("------------------------------------\n");

            for (double costoMaximo : costosMaximos) {
                List<Integer> cantidadDatos = new ArrayList<>(); // Eje X
                List<Integer> costosReales = new ArrayList<>();  // Eje Y (para los costos reales)
                List<Double> porcentajesLlenado = new ArrayList<>(); // Para el gráfico (2)

                for (int tamanioN : tamaniosN) {
                    int N = (int) Math.pow(2.0, tamanioN); // Cantidad de datos
                    Hashing tablaHash = new Hashing(costoMaximo);
                    Random random = new Random();

                    for (int i = 0; i < N; i++) {
                        long numero = random.nextLong();
                        tablaHash.insertar(numero);

                        // Recolectamos datos cada 1000 inserciones
                        if (i % 1000 == 0) {
                            cantidadDatos.add(i); // Cantidad de datos ingresados
                            costosReales.add(tablaHash.getPromedio()); // Costo promedio real
                            porcentajesLlenado.add(tablaHash.porcentajeLlenado()); // Porcentaje de llenado
                        }
                    }

                    // Escribir datos en el archivo
                    escritor.write("Datos insertados: " + N + "\n");
                    escritor.write("Costo Promedio Máximo: " + costoMaximo + "\n");
                    escritor.write("Costo Promedio Real (I/Os): " + tablaHash.getPromedio() + "\n");
                    escritor.write("Porcentaje de llenado final: " + tablaHash.porcentajeLlenado() + "%\n");
                    escritor.write("------------------------------------\n");

                    // Mostrar en consola para verificar el progreso
                    System.out.println("Inserciones completadas para " + N + " elementos con cmáx: " + costoMaximo);
                    System.out.println("Costo Promedio Real (I/Os): " + tablaHash.getPromedio());
                    System.out.println("Porcentaje de llenado: " + tablaHash.porcentajeLlenado() + "%");
                    System.out.println("----------------------------------------------------");
                }

                // Añadir los datos de cada cmáx a las listas globales
                listaCantidadDatos.add(cantidadDatos);
                listaCostosReales.add(costosReales);
                listaPorcentajesLlenado.add(porcentajesLlenado);
                listaCostosRealesLlenado.add(costosReales);
            }

            // Graficar costo promedio real vs cantidad de datos ingresados (todas las curvas de cmáx en un gráfico)
            Graficar.graficarCostoPromedioVsDatosMultipleCmax(listaCantidadDatos, listaCostosReales, costosMaximos);

            // Graficar porcentaje de llenado vs costos reales (para cada cmáx)
            for (int i = 0; i < costosMaximos.length; i++) {
                Graficar.graficarLlenadoVsCostos(listaCantidadDatos.get(i), listaPorcentajesLlenado.get(i), listaCostosReales.get(i), costosMaximos[i]);
            }

        } catch (IOException e) {
            System.out.println("Error al escribir el archivo de logs: " + e.getMessage());
        }
    }
}
