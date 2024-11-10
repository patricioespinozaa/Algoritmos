/**
 * Clase Node representa un nodo en un Árbol Binario de Búsqueda (ABB).
 * Cada nodo contiene una clave de tipo entero y referencias a sus nodos hijos
 * izquierdo y derecho, lo que permite la construcción de una estructura de árbol.
 */
public class Node {
    /** Clave del nodo, que representa el valor almacenado en este nodo.*/
    int key;

    /** Referencia al hijo izquierdo de este nodo.*/
    Node left;

    /** Referencia al hijo derecho de este nodo.*/
    Node right;

    /**
     * Constructor de la clase Node.
     * Inicializa el nodo con una clave dada y establece los hijos izquierdo y derecho en null.
     * @param item Clave del nodo, un valor entero que se almacenará en este nodo.
     */
    public Node(int item) {
        this.key = item;
        this.left = null;
        this.right = null;
    }
}
