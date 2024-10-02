package src;
// ======================== ESTRUCTURA DE PÁGINA ======================== //


/* 
 ==== Realizado ====
- Los elementos a almacenar serán del tipo long long de C/C++, es decir, enteros de 64 bits (si
  utilizan otro lenguaje permitido deben asegurarse de que cumplan con ese tamaño). 
- 
*/

public class Pagina {
    private static final int MAX_ELEMENTOS = 128;        // 1024 bytes/8 bytes por cada long
    private long[] elementos;                            // Arreglo para almacenar hasta 128 enteros de 64 bits
    private int numElementos;                            // Contador de elementos actuales en la página

    // Constructor: inicializa el arreglo de elementos y el contador
    public Pagina() {
        this.elementos = new long[MAX_ELEMENTOS];        // Inicializa el arreglo con 128 elementos, cantidad máxima de elementos por pagina
        this.numElementos = 0;                           // Inicializa el contador de elementos en 0
    }

    public long[] getPagina() {                          // Retorna la página con los elementos en ella
        return elementos;
    }

    public boolean estaLlena() {                         // Verifica si la página está llena
        return numElementos >= MAX_ELEMENTOS;
    }

    public boolean insertarElemento(long elemento) {     // Inserta un nuevo elemento si hay espacio
        if (estaLlena()) {
            return false;                                   // -> No se puede insertar si la página está llena
        }
        elementos[numElementos++] = elemento;            // * Else: Inserta el elemento y aumenta el contador de elementos en la página
        return true;
    }

    public int getNumElementos() {                       // Obtiene el número actual de elementos en la página
        return numElementos;
    }

    public long obtenerElemento(int index) {            // Recupera un elemento en una posición dada
        if (index >= 0 && index < numElementos) {       // Verifica que el índice esté dentro del rango
            return elementos[index];                        // -> Retorna el elemento en la posición dada
        } else {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }
    }

    public boolean contieneElemento(long elemento) {    // Verifica si la página contiene un elemento dado
        for (int i = 0; i < numElementos; i++) {        // Recorre todos los elementos en la página
            if (elementos[i] == elemento) {
                return true;                                // -> Elemento encontrado
            }
        }
        return false;                                       // -> Elemento no encontrado
    }

    public void vaciarPagina() {                        // Elimina todos los elementos de la página (usado en rehashing)
        this.elementos = new long[elementos.length];    // Reinicia el array
        this.numElementos = 0;                          // Reinicia el contador de elementos presentes en la página
    }

    // Imprime todos los elementos de la página
    /*
    public void imprimirElementos() {
        System.out.print("Página: ");
        for (int i = 0; i < numElementos; i++) {
            System.out.print(elementos[i] + " ");
        }
        System.out.println();
    }
    */

    // Imprime todos los elementos de la página
    public void imprimirElementos() {
        System.out.print("Página: ");
        for (int i = 0; i < numElementos; i++) {
            System.out.print(elementos[i] + " ");
        }
        System.out.println();
    }

    // Test
    public static void main(String[] args) {
        Pagina pagina = new Pagina();
        pagina.insertarElemento(3);
        pagina.insertarElemento(5);
        pagina.insertarElemento(34);
        pagina.insertarElemento(35);
        pagina.insertarElemento(34);
        pagina.imprimirElementos();
    }
}