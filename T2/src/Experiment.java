import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Experiment {
    public static void main(String[] args) {
        int N = 100000;  // Número de elementos a insertar
        int M = 100 * N; // Número de búsquedas
        Random rand = new Random();

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
        System.out.println("Inicio del Experimento 1:");

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
        System.out.println("Tiempo de búsqueda ABB (Experimento 1): " + (endTime - startTime) / 1e6 + " ms");

        // Realizar búsquedas en SplayTree
        startTime = System.nanoTime();
        for (int key : B) {
            splayTree.search(key);
        }
        endTime = System.nanoTime();
        System.out.println("Tiempo de búsqueda Splay Tree (Experimento 1): " + (endTime - startTime) / 1e6 + " ms");


        /* ------------------------- Experimento 2 ------------------------- */
        System.out.println("\nInicio del Experimento 2:");

        // Reinicializar árboles para el segundo experimento
        bst = new BinarySearchTree();
        splayTree = new SplayTree();

        // Tiempo de inserción en BinarySearchTree para Experimento 2
        startTime = System.nanoTime();
        for (int key : A) {
            bst.insert(key);
        }
        endTime = System.nanoTime();
        System.out.println("Tiempo de inserción ABB (Experimento 2): " + (endTime - startTime) / 1e6 + " ms");

        // Tiempo de inserción en SplayTree para Experimento 2
        startTime = System.nanoTime();
        for (int key : A) {
            splayTree.insert(key);
        }
        endTime = System.nanoTime();
        System.out.println("Tiempo de inserción Splay Tree (Experimento 2): " + (endTime - startTime) / 1e6 + " ms");

        // Calcular constante C para función de probabilidad
        double C = calculateC(N);

        // Crear arreglo B con probabilidades sesgadas
        B.clear();
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
        System.out.println("Tiempo de búsqueda ABB (Experimento 2): " + (endTime - startTime) / 1e6 + " ms");

        // Realizar búsquedas en SplayTree con B sesgado
        startTime = System.nanoTime();
        for (int key : B) {
            splayTree.search(key);
        }
        endTime = System.nanoTime();
        System.out.println("Tiempo de búsqueda Splay Tree (Experimento 2): " + (endTime - startTime) / 1e6 + " ms");
    }

    // Calcular la constante C para función de probabilidad f(i) = C / (i+1)^2
    private static double calculateC(int N) {
        double sum = 0.0;
        for (int i = 1; i <= N; i++) {
            sum += 1.0 / Math.pow(i, 2);
        }
        return 1.0 / sum;
    }
}
