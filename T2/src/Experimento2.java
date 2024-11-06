import java.util.List;

public class Experimento2 {
    public static void main(String[] args) {
        int N = 100000;
        int M = 100 * N;

        BinarySearchTree bst = new BinarySearchTree();
        SplayTree splayTree = new SplayTree();

        // Generar enteros únicos y ordenar aleatoriamente
        List<Integer> A = ExperimentUtils.generateUniqueKeys(N, N * 10);

        // Tiempo de inserción en ABB
        long startTime = System.nanoTime();
        for (int key : A) {
            bst.insert(key);
        }
        long endTime = System.nanoTime();
        System.out.println("Tiempo de inserción ABB (Experimento 2): " + (endTime - startTime) / 1e6 + " ms");

        // Tiempo de inserción en Splay Tree
        startTime = System.nanoTime();
        for (int key : A) {
            splayTree.insert(key);
        }
        endTime = System.nanoTime();
        System.out.println("Tiempo de inserción Splay Tree (Experimento 2): " + (endTime - startTime) / 1e6 + " ms");

        // Calcular la constante C
        double C_constant = ExperimentUtils.calculateC(N);

        // Crear arreglo B para búsquedas sesgadas
        List<Integer> B = ExperimentUtils.createSearchArray(A, M, true, C_constant);

        // Realizar búsquedas en ABB
        startTime = System.nanoTime();
        for (int key : B) {
            bst.search(key);
        }
        endTime = System.nanoTime();
        System.out.println("Tiempo de búsqueda ABB (Experimento 2): " + (endTime - startTime) / 1e6 + " ms");

        // Realizar búsquedas en Splay Tree
        startTime = System.nanoTime();
        for (int key : B) {
            splayTree.search(key);
        }
        endTime = System.nanoTime();
        System.out.println("Tiempo de búsqueda Splay Tree (Experimento 2): " + (endTime - startTime) / 1e6 + " ms");
    }
}
