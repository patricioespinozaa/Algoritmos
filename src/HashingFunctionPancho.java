package tarea1;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.zip.CRC32;

public class HashingFunction {

    // Función de hashing que devuelve un valor entre 0 y 2^64 - 1 basado en la clave
    public static long hashLong(long input) {
        input = (~input) + (input << 21); // mezcla de bits
        input = input ^ (input >>> 24);
        input = (input + (input << 3)) + (input << 8); // mezcla de bits
        input = input ^ (input >>> 14);
        input = (input + (input << 2)) + (input << 4); // mezcla de bits
        input = input ^ (input >>> 28);
        input = input + (input << 31);
        return input & 0xFFFFFFFFFFFFFFFFL; // asegura que sea positivo (64 bits)
    }

    public static void main(String[] args) {
        // Ejemplo de uso
        long elemento = 7;

        // Generar el valor hash
        long hashValue = h(elemento);

        System.out.println("Hash basado en la clave para el elemento: " + hashValue);
    }
}

