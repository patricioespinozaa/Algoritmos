import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Experimento2 {
    public static void main(String[] args) {
        double[] factors = {0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0};
        Random rand = new Random();
        int iteration = 1;
        // Ruta donde se guardarán los resultados
        String filePath = "../resultados/experimento2_resultados.csv";

        try (FileWriter fileWriter = new FileWriter(filePath);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            // Escribir encabezado del archivo CSV
            printWriter.println("N,M,Tiempo_Insercion_ABB,Tiempo_Insercion_Splay,Tiempo_Busqueda_ABB,Tiempo_Busqueda_Splay,Costo_Promedio_ABB,Costo_Promedio_Splay");

            for (double factor : factors) {
                int N = (int) (1_000_000 * factor);  // Escalando N
                int M = 100 * N; // Número de búsquedas proporcional a N

                System.out.println("Iteración: " + iteration);
                System.out.println("Valor de N: " + N);
                System.out.println("Número de búsquedas M: " + M);

                // Crear e inicializar árboles
                BinarySearchTree bst = new BinarySearchTree();
                SplayTree splayTree = new SplayTree();

                // Generar N enteros únicos para insertar
                Set<Integer> uniqueKeys = new HashSet<>();
                while (uniqueKeys.size() < N) {
                    int key = rand.nextInt(N * 10);
                    uniqueKeys.add(key);
                }
                List<Integer> A = new ArrayList<>(uniqueKeys);  // Lista de elementos únicos
                Collections.shuffle(A); // Orden aleatorio para inserciones

                System.out.println("\nInicio del Experimento 2 con N = " + N);

                // Tiempo de inserción en BinarySearchTree
                long startTime = System.nanoTime();
                for (int key : A) {
                    bst.insert(key);
                }
                long endTime = System.nanoTime();
                double tiempoInsercionABB = (endTime - startTime) / 1e6;
                System.out.println("Tiempo de inserción ABB (Experimento 2): " + tiempoInsercionABB + " ms");

                // Tiempo de inserción en SplayTree
                startTime = System.nanoTime();
                for (int key : A) {
                    splayTree.insert(key);
                }
                endTime = System.nanoTime();
                double tiempoInsercionSplay = (endTime - startTime) / 1e6;
                System.out.println("Tiempo de inserción Splay Tree (Experimento 2): " + tiempoInsercionSplay + " ms");

                // Calcular constante C para función de probabilidad
                double C = calculateC(N);

                // Crear arreglo B con probabilidades sesgadas -> f(i) = C / (i+1)^2 donde se guardan la cantidad de busquedas
                List<Integer> B = new ArrayList<>(M);
                for (int i = 0; i < N; i++) {
                    int frequency = (int) Math.floor(M * (C / Math.pow(i + 1, 2)));
                    for (int j = 0; j < frequency; j++) {
                        B.add(A.get(i));
                    }
                }
                Collections.shuffle(B); // Permutación aleatoria de B para búsquedas sesgadas

                // Realizar búsquedas en BinarySearchTree con B sesgado
                startTime = System.nanoTime();
                for (int key : B) {
                    bst.search(key);
                }
                endTime = System.nanoTime();
                double tiempoBusquedaABB = (endTime - startTime) / 1e6;
                double costoPromedioABB = tiempoBusquedaABB / M;
                System.out.println("Tiempo de búsqueda ABB (Experimento 2): " + tiempoBusquedaABB + " ms");
                System.out.println("Costo promedio de búsqueda ABB: " + costoPromedioABB + " ms por búsqueda");

                // Realizar búsquedas en SplayTree con B sesgado
                startTime = System.nanoTime();
                for (int key : B) {
                    splayTree.search(key);
                }
                endTime = System.nanoTime();
                double tiempoBusquedaSplay = (endTime - startTime) / 1e6;
                double costoPromedioSplay = tiempoBusquedaSplay / M;
                System.out.println("Tiempo de búsqueda Splay Tree (Experimento 2): " + tiempoBusquedaSplay + " ms");
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

    // Calcular la constante C para función de probabilidad f(i) = C / (i+1)^2
    private static double calculateC(int N) {
        double sum = 0.0;
        for (int i = 1; i <= N; i++) {
            sum += 1.0 / Math.pow(i+1, 2);
        }
        return 1.0 / sum;
    }
}
