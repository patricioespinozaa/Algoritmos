// ============================================== FUNCION HASH: VALORES ENTRE 0 Y 2^64 - 1 ============================================== //

import java.util.Random;

public class HashFunction {
    private Random random;

    public HashFunction() {
        random = new Random();
    }

    // Función de hash que genera un valor entre 0 y 2^64 - 1
    public long hash() {
        // Genera un número aleatorio entre 0 y 2^64 - 1
        return random.nextLong() & 0xFFFFFFFFFFFFFFFFL;
    }

    public static void main(String[] args) {
        HashFunction hashFunction = new HashFunction();

        // Ejemplo de generar y mostrar 10 valores de hash
        for (int i = 0; i < 10; i++) {
            long hashValue = hashFunction.hash();
            System.out.println("Hash Value: " + hashValue);
        }
    }
}


