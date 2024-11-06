import java.util.Collections;
import java.util.List;

public class Experimento3 {
    public static void main(String[] args) {
        int N = 100000;
        int M = 100 * N;

        BinarySearchTree bst = new BinarySearchTree();
        SplayTree splayTree = new SplayTree();

        // Generar enteros únicos y ordenar
        List<Integer> A = ExperimentUtils.generateUniqueKeys(N, N * 10);
        Collections.sort(A); // Ordenar A

        // Tiempo de inserción en ABB
        long startTime = System.nanoTime();
        for (int key : A) {
            bst.insert(key);
        }
        long endTime = System.nanoTime();
        System.out.println("Tiempo de inserción ABB (Experimento 3): " + (endTime - startTime) / 1e6 + " ms");

        // Tiempo de inserción en Splay Tree
        startTime = System.nanoTime();
        for (int key : A) {
            splayTree.insert(key);
        }
        endTime = System.nanoTime();
        System.out.println("Tiempo de inserción Splay Tree (Experimento 3): " + (endTime - startTime) / 1e6 + " ms");

        // Crear arreglo B para búsquedas aleatorias
        List<Integer> B = ExperimentUtils.createSearchArray(A, M, false, 0);

        // Realizar búsquedas en ABB
        startTime = System.nanoTime();
        for (int key : B) {
            bst.search(key);
        }
        endTime = System.nanoTime();
        System.out.println("Tiempo de búsqueda ABB (Experimento 3): " + (endTime - startTime) / 1e6 + " ms");

        // Realizar búsquedas en Splay Tree
        startTime = System.nanoTime();
        for (int key : B) {
            splayTree.search(key);
        }
        endTime = System.nanoTime();
        System.out.println("Tiempo de búsqueda Splay Tree (Experimento 3): " + (endTime - startTime) / 1e6 + " ms");
    }
}
