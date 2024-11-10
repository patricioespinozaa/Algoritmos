/**
 * Clase IterativeBinarySearchTree implementa un Árbol Binario de Búsqueda (ABB) con inserción
 * y búsqueda de nodos de manera iterativa. Esta clase ofrece métodos para insertar y buscar nodos
 * en el árbol sin utilizar recursión.
 */
public class IterativeBinarySearchTree {
    /**
     * Nodo raíz del árbol. Comienza como null, indicando que el árbol está vacío.
     */
    Node root = null;

    /**
     * Constructor de la clase IterativeBinarySearchTree.
     * Inicializa un árbol vacío sin nodos.
     */
    IterativeBinarySearchTree() {}

    // ======================== METODO DE INSERCION ITERATIVA ======================== //

    /**
     * Inserta un nuevo nodo con la clave especificada en el árbol, de manera iterativa.
     * Si la clave ya existe en el árbol, no se realiza ninguna inserción.
     *
     * @param key Clave del nuevo nodo a insertar.
     */
    void insert(int key) {
        // Crea el nuevo nodo que se insertará.
        Node newNode = new Node(key);

        // Si el árbol está vacío, se asigna el nuevo nodo como la raíz.
        if (root == null) {
            root = newNode;
            return;
        }

        // Comienza la búsqueda de la posición donde se insertará el nuevo nodo.
        Node current = root;
        Node parent = null;

        // Recorre el árbol hasta encontrar el lugar adecuado para el nuevo nodo.
        while (current != null) {
            parent = current;
            if (key < current.key) {
                current = current.left;
            } else if (key > current.key) {
                current = current.right;
            } else {
                // Si la clave ya existe, no se realiza ninguna inserción y se sale.
                return;
            }
        }

        // Inserta el nuevo nodo como hijo izquierdo o derecho del nodo padre.
        if (key < parent.key) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
    }

    // ======================== METODO DE BUSQUEDA ITERATIVA ======================== //

    /**
     * Busca un nodo con la clave especificada en el árbol, de manera iterativa.
     * Retorna `true` si se encuentra el nodo, o `false` si no se encuentra.
     *
     * @param key Clave del nodo a buscar.
     * @return `true` si el nodo con la clave existe, `false` si no.
     */
    boolean search(int key) {
        // Comienza la búsqueda desde la raíz del árbol.
        Node current = root;

        // Recorre el árbol de manera iterativa.
        while (current != null) {
            if (key == current.key) {
                return true; // Se encontró el nodo con la clave.
            } else if (key < current.key) {
                current = current.left; // Si la clave es menor, va al subárbol izquierdo.
            } else {
                current = current.right; // Si la clave es mayor, va al subárbol derecho.
            }
        }
        return false; // Si el nodo no se encuentra, retorna false.
    }
}
