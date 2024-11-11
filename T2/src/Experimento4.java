import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Arrays;

public class Experimento4 {

    public static void main(String[] args) {
        double[] factors = {0.01, 0.02, 0.03, 0.04, 0.05, 0.06, 0.07, 0.08, 0.09, 0.1};
        Random rand = new Random();
        int iteration = 10;
        String filePath = "../resultados/experimento4_resultados.csv";

        try (FileWriter fileWriter = new FileWriter(filePath);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            // Escribir encabezado del archivo CSV
            printWriter.println("N,M,Tiempo_Insercion_ABB,Tiempo_Insercion_Splay,Tiempo_Busqueda_ABB,Tiempo_Busqueda_Splay,Costo_Promedio_ABB,Costo_Promedio_Splay");

            for (double factor : factors) {
                int N = (int) (factor * 1_000_000);
                int M = 100 * N;

                System.out.println("Iteración: " + iteration);
                System.out.println("Valor de N: " + N);
                System.out.println("Número de búsquedas M: " + M);

                // Generar N enteros únicos para insertar
                Set<Integer> uniqueKeys = new HashSet<>();
                while (uniqueKeys.size() < N) {
                    int key = rand.nextInt(N * 10);
                    uniqueKeys.add(key);
                }
                List<Integer> A = new ArrayList<>(uniqueKeys);
                Collections.shuffle(A);

                // Copia y ordena A en C
                int[] C = A.stream().mapToInt(Integer::intValue).toArray();
                Arrays.sort(C);

                System.out.println("\nInicio del Experimento 4 con N = " + N);

                // Crear e inicializar árboles
                IterativeBinarySearchTree bst = new IterativeBinarySearchTree();
                IterativeSplayTree splayTree = new IterativeSplayTree();

                // Tiempo de inserción en BinarySearchTree
                long startTime = System.nanoTime();
                for (int value : C) {
                    bst.insert(value);
                }
                long endTime = System.nanoTime();
                double tiempoInsercionABB = (endTime - startTime) / 1e6;
                System.out.println("Tiempo de inserción ABB (Experimento 4): " + tiempoInsercionABB + " ms");

                // Tiempo de inserción en SplayTree
                startTime = System.nanoTime();
                for (int value : C) {
                    splayTree.insert(value);
                }
                endTime = System.nanoTime();
                double tiempoInsercionSplay = (endTime - startTime) / 1e6;
                System.out.println("Tiempo de inserción Splay Tree (Experimento 4): " + tiempoInsercionSplay + " ms");

                // Calcular constante C para función de probabilidad
                double constantC = calculateConstantC(N);
                System.out.println("Constante C calculada para función de probabilidad: " + constantC);

                // Crear arreglo B con probabilidades sesgadas
                ArrayList<Integer> B = new ArrayList<>();
                for (int i = 0; i < N; i++) {
                    int frequency = (int) Math.floor(M * (constantC / Math.pow(i + 1, 2)));
                    for (int j = 0; j < frequency; j++) {
                        B.add(A.get(i));
                    }
                }
                Collections.shuffle(B);

                // Realizar búsquedas en BinarySearchTree con B sesgado
                startTime = System.nanoTime();
                for (int key : B) {
                    bst.search(key);
                }
                endTime = System.nanoTime();
                double tiempoBusquedaABB = (endTime - startTime) / 1e6;
                double costoPromedioABB = tiempoBusquedaABB / M;
                System.out.println("Tiempo de búsqueda ABB (Experimento 4): " + tiempoBusquedaABB + " ms");
                System.out.println("Costo promedio de búsqueda ABB: " + costoPromedioABB + " ms por búsqueda");

                // Realizar búsquedas en SplayTree con B sesgado
                startTime = System.nanoTime();
                for (int key : B) {
                    splayTree.search(key);
                }
                endTime = System.nanoTime();
                double tiempoBusquedaSplay = (endTime - startTime) / 1e6;
                double costoPromedioSplay = tiempoBusquedaSplay / M;
                System.out.println("Tiempo de búsqueda Splay Tree (Experimento 4): " + tiempoBusquedaSplay + " ms");
                System.out.println("Costo promedio de búsqueda Splay Tree: " + costoPromedioSplay + " ms por búsqueda\n");

                // Guardar los resultados en el archivo CSV
                printWriter.printf("%d,%d,%.4f,%.4f,%.4f,%.4f,%.8f,%.8f%n",
                        N, M, tiempoInsercionABB, tiempoInsercionSplay, tiempoBusquedaABB, tiempoBusquedaSplay, costoPromedioABB, costoPromedioSplay);
                iteration++;
                System.out.println("---------------------------------------------------");
            }

        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    /**
     * Calcula la constante C para la función de probabilidad f de forma que la suma de las probabilidades sea 1.
     *
     * @param N Número de elementos
     * @return La constante C
     */
    private static double calculateConstantC(int N) {
        double sum = 0.0;
        for (int i = 0; i < N; i++) {
            sum += 1.0 / Math.pow(i + 1, 2);
        }
        return 1.0 / sum;
    }
}
