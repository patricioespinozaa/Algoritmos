import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Experimento4 {
    public static void main(String[] args) {
        int N = 100000;
        int M = 100 * N;

        BinarySearchTree bst = new BinarySearchTree();
        SplayTree splayTree = new SplayTree();

        // Generar enteros únicos en A y hacer copia ordenada en C
        List<Integer> A = ExperimentUtils.generateUniqueKeys(N, N * 10);
        List<Integer> C = new ArrayList<>(A);
        Collections.sort(C);

        // Tiempo de inserción en ABB usando C
        long startTime = System.nanoTime();
        for (int key : C) {
            bst.insert(key);
        }
        long endTime = System.nanoTime();
        System.out.println("Tiempo de inserción ABB (Experimento 4): " + (endTime - startTime) / 1e6 + " ms");

        // Tiempo de inserción en Splay Tree usando C
        startTime = System.nanoTime();
        for (int key : C) {
            splayTree.insert(key);
        }
        endTime = System.nanoTime();
        System.out.println("Tiempo de inserción Splay Tree (Experimento 4): " + (endTime - startTime) / 1e6 + " ms");

        // Calcular la constante C
        double C_constant = ExperimentUtils.calculateC(N);

        // Crear arreglo B para búsquedas sesgadas usando A (desordenado)
        List<Integer> B = ExperimentUtils.createSearchArray(A, M, true, C_constant);

        // Realizar búsquedas en ABB
        startTime = System.nanoTime();
        for (int key : B) {
            bst.search(key);
        }
        endTime = System.nanoTime();
        System.out.println("Tiempo de búsqueda ABB (Experimento 4): " + (endTime - startTime) / 1e6 + " ms");

        // Realizar búsquedas en Splay Tree
        startTime = System.nanoTime();
        for (int key : B) {
            splayTree.search(key);
        }
        endTime = System.nanoTime();
        System.out.println("Tiempo de búsqueda Splay Tree (Experimento 4): " + (endTime - startTime) / 1e6 + " ms");
    }
}
