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

        BinarySearchTree bst = new BinarySearchTree();
        SplayTree splayTree = new SplayTree();
        Set<Integer> uniqueKeys = new HashSet<>();

        // Genera N enteros únicos
        while (uniqueKeys.size() < N) {
            int key = rand.nextInt(N * 10);
            uniqueKeys.add(key);
        }

        // Convierte el conjunto a una lista para poder hacer inserciones en orden aleatorio
        List<Integer> A = new ArrayList<>(uniqueKeys);
        Collections.shuffle(A); // Orden aleatorio para las inserciones

        // Tiempo de inserción en ABB
        long startTime = System.nanoTime();
        for (int key : A) {
            bst.insert(key);
        }
        long endTime = System.nanoTime();
        System.out.println("Tiempo de inserción ABB: " + (endTime - startTime) / 1e6 + " ms");

        // Tiempo de inserción en Splay Tree
        startTime = System.nanoTime();
        for (int key : A) {
            splayTree.insert(key);
        }
        endTime = System.nanoTime();
        System.out.println("Tiempo de inserción Splay Tree: " + (endTime - startTime) / 1e6 + " ms");

        // Crear el arreglo B para realizar las búsquedas
        List<Integer> B = new ArrayList<>(M);
        int copies = M / N;
        for (int key : A) {
            for (int j = 0; j < copies; j++) {
                B.add(key);
            }
        }
        Collections.shuffle(B); // Permutación aleatoria de B

        // Realiza búsquedas en ABB usando el arreglo B
        startTime = System.nanoTime();
        for (int key : B) {
            bst.search(key);
        }
        endTime = System.nanoTime();
        System.out.println("Tiempo de búsqueda ABB: " + (endTime - startTime) / 1e6 + " ms");

        // Realiza búsquedas en Splay Tree usando el arreglo B
        startTime = System.nanoTime();
        for (int key : B) {
            splayTree.search(key);
        }
        endTime = System.nanoTime();
        System.out.println("Tiempo de búsqueda Splay Tree: " + (endTime - startTime) / 1e6 + " ms");
    }
}
