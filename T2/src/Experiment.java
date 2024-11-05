import java.util.Random;

public class Experiment {
    public static void main(String[] args) {
        int N = 100000; // Número de elementos a insertar
        int M = 100 * N; // Número de búsquedas
        Random rand = new Random();

        BinarySearchTree bst = new BinarySearchTree();
        SplayTree splayTree = new SplayTree();

        // Inserción en ABB y Splay Tree
        for (int i = 0; i < N; i++) {
            int key = rand.nextInt(N * 10); // Genera números únicos
            bst.insert(key);
            splayTree.insert(key);
        }

        // Realiza búsquedas aleatorias en ABB
        long startTime = System.nanoTime();
        for (int i = 0; i < M; i++) {
            int key = rand.nextInt(N * 10);
            bst.search(key);
        }
        long endTime = System.nanoTime();
        System.out.println("Tiempo de búsqueda ABB: " + (endTime - startTime) / 1e6 + " ms");

        // Realiza búsquedas aleatorias en Splay Tree
        startTime = System.nanoTime();
        for (int i = 0; i < M; i++) {
            int key = rand.nextInt(N * 10);
            splayTree.search(key);
        }
        endTime = System.nanoTime();
        System.out.println("Tiempo de búsqueda Splay Tree: " + (endTime - startTime) / 1e6 + " ms");
    }
}
