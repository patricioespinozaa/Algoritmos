import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

// IGUAL AL EXPERIMENTO 1 PERO GUARDA LAS RESULTADOS PARA PODER GRAFICAR ** REQUIERE PROBRARLO

public class Experimento1 {
    public static void main(String[] args) {
        double[] factors = {0.1, 0.2};
        Random rand = new Random();
        int iteration = 1;

        // Abrir el archivo para escribir resultados
        try (FileWriter fileWriter = new FileWriter("../resultados/experimento1_resultados.csv");
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            // Escribir encabezado del archivo CSV
            printWriter.println("Iteracion,N,M,Tiempo_Insercion_ABB,Tiempo_Insercion_Splay,Tiempo_Busqueda_ABB,Tiempo_Busqueda_Splay,Costo_Promedio_ABB,Costo_Promedio_Splay");

            for (double factor : factors) {
                int N = (int) (1_000_000 * factor);  // Escalando N
                int M = 100 * N; // Número de búsquedas proporcional a N

                System.out.println("Iteración: " + iteration);
                System.out.println("Valor actual de N: " + N);
                System.out.println("Número de búsquedas M: " + M +"\n");

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

                /* ------------------------- Experimento 1 ------------------------- */
                System.out.println("Inicio del Experimento 1 con N = " + N +"\n");

                // Medir tiempos de inserción y búsqueda
                long startTime, endTime;

                // Tiempo de inserción en BinarySearchTree
                startTime = System.nanoTime();
                for (int key : A) {
                    bst.insert(key);
                }
                endTime = System.nanoTime();
                double tiempoInsercionABB = (endTime - startTime) / 1e6;
                System.out.println("Tiempo de inserción ABB (Experimento 1): " + tiempoInsercionABB + " ms");

                // Tiempo de inserción en SplayTree
                startTime = System.nanoTime();
                for (int key : A) {
                    splayTree.insert(key);
                }
                endTime = System.nanoTime();
                double tiempoInsercionSplay = (endTime - startTime) / 1e6;
                System.out.println("Tiempo de inserción Splay Tree (Experimento 1): " + tiempoInsercionSplay + " ms\n");

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
                System.out.println("Tiempo de búsqueda ABB (Experimento 1): " + tiempoBusquedaABB + " ms");
                System.out.println("Costo promedio de búsqueda ABB: " + costoPromedioABB + " ms por búsqueda\n");    

                // Realizar búsquedas en SplayTree
                startTime = System.nanoTime();
                for (int key : B) {
                    splayTree.search(key);
                }
                endTime = System.nanoTime();
                double tiempoBusquedaSplay = (endTime - startTime) / 1e6;
                double costoPromedioSplay = tiempoBusquedaSplay / M;
                System.out.println("Tiempo de búsqueda Splay Tree (Experimento 1): " + tiempoBusquedaSplay + " ms");
                System.out.println("Costo promedio de búsqueda Splay Tree: " + (costoPromedioSplay / M) + " ms por búsqueda\n");

                // Guardar los resultados de esta iteración en el archivo CSV
                printWriter.printf("%d,%d,%d,%.4f,%.4f,%.4f,%.4f,%.8f,%.8f%n",
                        iteration, N, M, tiempoInsercionABB, tiempoInsercionSplay, tiempoBusquedaABB, tiempoBusquedaSplay, costoPromedioABB, costoPromedioSplay);

                iteration++;
                System.out.println("---------------------------------------------------");
            }
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }
}
