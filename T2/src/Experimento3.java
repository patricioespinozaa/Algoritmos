import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Experimento3 {
    public static void main(String[] args) {
        double[] factors = {0.01, 0.02, 0.03, 0.04, 0.05, 0.06, 0.07, 0.08, 0.09, 0,1}; 
        Random rand = new Random();
        String filePath = "../resultados/experimento3_resultados.csv";

        try (FileWriter fileWriter = new FileWriter(filePath);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            // Escribir encabezado del archivo CSV
            printWriter.println("N,M,Tiempo_Insercion_ABB,Tiempo_Insercion_Splay,Tiempo_Busqueda_ABB,Tiempo_Busqueda_Splay,Costo_Promedio_ABB,Costo_Promedio_Splay");

            for (double factor : factors) {
                int N = (int) (1_000_000 * factor);  // Escalando N
                int M = 100 * N; // Número de búsquedas proporcional a N

                System.out.println("Valor de N: " + N);
                System.out.println("Número de búsquedas M: " + M);

                // Crear e inicializar árboles
                IterativeBinarySearchTree bst = new IterativeBinarySearchTree();
                IterativeSplayTree splayTree = new IterativeSplayTree();

                // Generar N enteros únicos para insertar
                Set<Integer> uniqueKeys = new HashSet<>();
                while (uniqueKeys.size() < N) {
                    int key = rand.nextInt(N * 10);
                    uniqueKeys.add(key);
                }
                List<Integer> A = new ArrayList<>(uniqueKeys);  // Lista de elementos únicos

                //* ==== Ordenar A antes de realizar la inserción en ambos árboles ==== *//
                Collections.sort(A);

                System.out.println("\nInicio del Experimento 3 con N = " + N);

                // Tiempo de inserción en BinarySearchTree
                long startTime = System.nanoTime();
                for (int key : A) {
                    bst.insert(key);
                }
                long endTime = System.nanoTime();
                double tiempoInsercionABB = (endTime - startTime) / 1e6;
                System.out.println("Tiempo de inserción ABB (Experimento 3): " + tiempoInsercionABB + " ms");

                // Tiempo de inserción en SplayTree
                startTime = System.nanoTime();
                for (int key : A) {
                    splayTree.insert(key);
                }
                endTime = System.nanoTime();
                double tiempoInsercionSplay = (endTime - startTime) / 1e6;
                System.out.println("Tiempo de inserción Splay Tree (Experimento 3): " + tiempoInsercionSplay + " ms");

                // Crear arreglo B para búsquedas aleatorias
                List<Integer> B = new ArrayList<>(M);
                int copies = M / N;
                for (int key : A) {
                    for (int j = 0; j < copies; j++) {
                        B.add(key);
                    }
                }
                Collections.shuffle(B); // Permutación aleatoria de B

                // Realizar búsquedas en BinarySearchTree
                startTime = System.nanoTime();
                for (int key : B) {
                    bst.search(key);
                }
                endTime = System.nanoTime();
                double tiempoBusquedaABB = (endTime - startTime) / 1e6;
                double costoPromedioABB = tiempoBusquedaABB / M;
                System.out.println("Tiempo de búsqueda ABB (Experimento 3): " + tiempoBusquedaABB + " ms");
                System.out.println("Costo promedio de búsqueda ABB: " + costoPromedioABB + " ms por búsqueda");

                // Realizar búsquedas en SplayTree
                startTime = System.nanoTime();
                for (int key : B) {
                    splayTree.search(key);
                }
                endTime = System.nanoTime();
                double tiempoBusquedaSplay = (endTime - startTime) / 1e6;
                double costoPromedioSplay = tiempoBusquedaSplay / M;
                System.out.println("Tiempo de búsqueda Splay Tree (Experimento 3): " + tiempoBusquedaSplay + " ms");
                System.out.println("Costo promedio de búsqueda Splay Tree: " + costoPromedioSplay + " ms por búsqueda");

                // Guardar los resultados en el archivo CSV
                printWriter.printf("%d,%d,%.4f,%.4f,%.4f,%.4f,%.8f,%.8f%n",
                        N, M, tiempoInsercionABB, tiempoInsercionSplay, tiempoBusquedaABB, tiempoBusquedaSplay, costoPromedioABB, costoPromedioSplay);

                System.out.println("---------------------------------------------------");
            }
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }
}
