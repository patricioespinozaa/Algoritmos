import java.util.*;

public class ExperimentUtils {
    public static List<Integer> generateUniqueKeys(int N, int range) {
        Set<Integer> uniqueKeys = new HashSet<>();
        Random rand = new Random();
        while (uniqueKeys.size() < N) {
            int key = rand.nextInt(range);
            uniqueKeys.add(key);
        }
        return new ArrayList<>(uniqueKeys);
    }

    public static double calculateC(int N) {
        double sum = 0.0;
        for (int i = 1; i <= N; i++) {
            sum += 1.0 / Math.pow(i, 2);
        }
        return 1.0 / sum;
    }

    public static List<Integer> createSearchArray(List<Integer> A, int M, boolean useProbability, double C_constant) {
        List<Integer> B = new ArrayList<>();
        if (useProbability) {
            for (int i = 0; i < A.size(); i++) {
                int frequency = (int) Math.floor(M * (C_constant / Math.pow(i + 1, 2)));
                for (int j = 0; j < frequency; j++) {
                    B.add(A.get(i));
                }
            }
        } else {
            int copies = M / A.size();
            for (int key : A) {
                for (int j = 0; j < copies; j++) {
                    B.add(key);
                }
            }
        }
        Collections.shuffle(B);
        return B;
    }
}
