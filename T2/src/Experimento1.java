import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Experimento1 {
    public static void main(String[] args) {
        double[] factors = {0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0};
        Random rand = new Random();

        for (double factor : factors) {
            int N = (int) (1_000_000 * factor);  // Escalando N
            int M = 100 * N; // Número de búsquedas proporcional a N

            System.out.println("Valor actual de N: " + N);
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

            /* ------------------------- Experimento 1 ------------------------- */
            System.out.println("Inicio del Experimento 1 con N = " + N);

            // Tiempo de inserción en BinarySearchTree
            long startTime = System.nanoTime();
            for (int key : A) {
                bst.insert(key);
            }
            long endTime = System.nanoTime();
            System.out.println("Tiempo de inserción ABB (Experimento 1): " + (endTime - startTime) / 1e6 + " ms");

            // Tiempo de inserción en SplayTree
            startTime = System.nanoTime();
            for (int key : A) {
                splayTree.insert(key);
            }
            endTime = System.nanoTime();
            System.out.println("Tiempo de inserción Splay Tree (Experimento 1): " + (endTime - startTime) / 1e6 + " ms");

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
            double abbSearchTime = (endTime - startTime) / 1e6;
            System.out.println("Tiempo de búsqueda ABB (Experimento 1): " + abbSearchTime + " ms");
            System.out.println("Costo promedio de búsqueda ABB: " + (abbSearchTime / M) + " ms por búsqueda");

            // Realizar búsquedas en SplayTree
            startTime = System.nanoTime();
            for (int key : B) {
                splayTree.search(key);
            }
            endTime = System.nanoTime();
            double splaySearchTime = (endTime - startTime) / 1e6;
            System.out.println("Tiempo de búsqueda Splay Tree (Experimento 1): " + splaySearchTime + " ms");
            System.out.println("Costo promedio de búsqueda Splay Tree: " + (splaySearchTime / M) + " ms por búsqueda");

            System.out.println("---------------------------------------------------");
        }
    }
}
