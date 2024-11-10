// Clase BinarySearchTree representa un Árbol Binario de Búsqueda (ABB).
public class BinarySearchTree {
    // Nodo raíz del árbol.
    Node root;

    // Constructor de la clase. Inicializa el árbol vacío.
    BinarySearchTree() {
        root = null;
    }

    // ======================== INSERCION EN ABB CLASICO ======================== //

    /**
     * Inserta un nuevo nodo con la clave especificada en el árbol.
     * @param key Clave del nuevo nodo a insertar.
     */
    void insert(int key) {
        root = insertRec(root, key);
    }

    /**
     * Método recursivo para insertar un nodo en el ABB.
     * @param root Nodo raíz actual del subárbol.
     * @param key Clave del nuevo nodo a insertar.
     * @return Nodo actualizado después de la inserción.
     */
    Node insertRec(Node root, int key) {
        // Si el subárbol está vacío, crea un nuevo nodo con la clave dada.
        if (root == null) {
            root = new Node(key);
            return root;
        }

        // Compara la clave a insertar con el valor en el nodo raíz:
        if (key < root.key) {
            // Si la clave es menor, inserta en el subárbol izquierdo.
            root.left = insertRec(root.left, key);
        } else if (key > root.key) {
            // Si la clave es mayor, inserta en el subárbol derecho.
            root.right = insertRec(root.right, key);
        }
        // Retorna el nodo raíz (no cambia si la clave ya existe).
        return root;
    }

    // ======================== BUSQUEDA EN ABB CLASICO ======================== //

    /**
     * Busca un nodo en el árbol.
     * @param key Clave del nodo a buscar.
     * @return true si el nodo con la clave dada existe, false en caso contrario.
     */
    boolean search(int key) {
        return searchRec(root, key) != null;
    }

    /**
     * Método recursivo para buscar un nodo en el ABB.
     * @param root Nodo raíz actual del subárbol.
     * @param key Clave del nodo a buscar.
     * @return Nodo que contiene la clave buscada, o null si no existe.
     */
    Node searchRec(Node root, int key) {
        // Caso base: si el nodo es null o contiene la clave buscada.
        if (root == null || root.key == key)
            return root;

        // Compara la clave buscada con el valor en el nodo raíz:
        if (key < root.key) {
            // Si la clave es menor, busca en el subárbol izquierdo.
            return searchRec(root.left, key);
        }
        // Si la clave es mayor, busca en el subárbol derecho.
        return searchRec(root.right, key);
    }
}
