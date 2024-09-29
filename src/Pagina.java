package src;
// ======================== ESTRUCTURA DE PÁGINA ======================== //

public class Pagina {
    private static final int MAX_ELEMENTOS = 128; // 1024 bytes/8 bytes por cada long
    private long[] elementos;                     // Arreglo para almacenar hasta 128 enteros de 64 bits
    private int numElementos;                     // Contador de elementos actuales en la página

    // Constructor: inicializa el arreglo de elementos y el contador
    public Pagina() {
        this.elementos = new long[MAX_ELEMENTOS];
        this.numElementos = 0;
    }

    // Verifica si la página está llena
    public boolean estaLlena() {
        return numElementos >= MAX_ELEMENTOS;
    }

    // Inserta un nuevo elemento si hay espacio
    public boolean insertarElemento(long elemento) {
        if (estaLlena()) {
            return false; // No se puede insertar si la página está llena
        }
        elementos[numElementos++] = elemento;
        return true;
    }

    // Obtiene el número actual de elementos en la página
    public int getNumElementos() {
        return numElementos;
    }

    // Recupera un elemento en una posición dada
    public long obtenerElemento(int index) {
        if (index >= 0 && index < numElementos) {
            return elementos[index];
        } else {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }
    }

    // Contiene elemento
    public boolean contieneElemento(long elemento) {
        for (int i = 0; i < numElementos; i++) {
            if (elementos[i] == elemento) {
                return true; // Elemento encontrado
            }
        }
        return false; // Elemento no encontrado
    }

    // Imprime todos los elementos de la página
    public void imprimirElementos() {
        System.out.print("Página: ");
        for (int i = 0; i < numElementos; i++) {
            System.out.print(elementos[i] + " ");
        }
        System.out.println();
    }
}