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
    public static long murmurHash3(long key) {
        key ^= (key >>> 33);
        key *= 0xff51afd7ed558ccdL;
        key ^= (key >>> 33);
        key *= 0xc4ceb9fe1a85ec53L;
        key ^= (key >>> 33);
        return key & 0xFFFFFFFFFFFFFFFFL; // Asegura que sea positivo
    }
    public static void main(String[] args) {
        HashFunction hashFunction = new HashFunction();

        for (int i = 0; i < 10; i++) {
            long elemento = hashFunction.random.nextLong();
            System.out.println(elemento);
            long hashValue = murmurHash3(elemento);
            System.out.println("Hash Value: " + hashValue);
            long k = hashValue % (long) Math.pow(2,  1);
            System.out.println("Hash Value: " + k);
        }
    }
}


