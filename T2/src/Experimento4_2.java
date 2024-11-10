import java.util.Arrays;
import java.util.Random;

public class Experimento4_2 {

    public static void main(String[] args) {
        double[] factors = {0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0};
        for (double factor : factors) {
            int N = (int) (factor * 1_000_000);
            int M = 100 * N;
            int[] A = new int[N];
            Random rand = new Random();

            // Inicializa A con valores aleatorios
            for (int i = 0; i < N; i++) {
                A[i] = rand.nextInt(N);
            }

            // Copia y ordena A en C
            int[] C = Arrays.copyOf(A, N);
            Arrays.sort(C);

            // Inserta los elementos de C en el árbol Splay
            IterativeSplayTree tree = new IterativeSplayTree();
            for (int value : C) {
                tree.insert(value);
            }

            // Calcula la constante C para la función de probabilidad f
            double constantC = calculateConstantC(N);

            // Realiza M búsquedas en el árbol usando la función f para determinar las probabilidades
            long startTime = System.nanoTime();
            for (int i = 0; i < M; i++) {
                int index = selectIndex(N, constantC);
                tree.search(A[index]);
            }
            long endTime = System.nanoTime();

            // Calcula el tiempo promedio de búsqueda
            double averageSearchTime = (endTime - startTime) / (double) M;
            System.out.printf("N = %d, M = %d, Tiempo promedio de búsqueda: %.2f ns%n", N, M, averageSearchTime);
        }
    }

    /**
     * Calcula la constante C para la función de probabilidad f de forma que la suma de las probabilidades sea 1.
     *
     * @param N Número de elementos
     * @return La constante C
     */
    private static double calculateConstantC(int N) {
        double sum = 0.0;
        for (int i = 0; i < N; i++) {
            sum += 1.0 / ((i + 1) * (i + 1));
        }
        return 1.0 / sum;
    }

    /**
     * Selecciona un índice en el arreglo A basado en la función de probabilidad f(i) = C / (i + 1)^2
     * y la constante C para ajustar las probabilidades.
     *
     * @param N Número de elementos en el arreglo A
     * @param constantC La constante C calculada para normalizar la función f
     * @return Un índice basado en la probabilidad calculada
     */
    private static int selectIndex(int N, double constantC) {
        Random rand = new Random();
        double threshold = rand.nextDouble();
        double cumulativeProbability = 0.0;

        for (int i = 0; i < N; i++) {
            cumulativeProbability += constantC / ((i + 1) * (i + 1));
            if (cumulativeProbability >= threshold) {
                return i;
            }
        }
        return N - 1; // Retorna el último índice si no se alcanzó el umbral
    }
}
