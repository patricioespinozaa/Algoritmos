public class IterativeBinarySearchTree {
    Node root = null;

    // Constructor
    IterativeBinarySearchTree() {}

    // Método de inserción iterativa
    void insert(int key) {
        Node newNode = new Node(key);
        if (root == null) {
            root = newNode;
            return;
        }

        Node current = root;
        Node parent = null;

        // Recorre el árbol hasta encontrar la posición correcta para el nuevo nodo
        while (current != null) {
            parent = current;
            if (key < current.key) {
                current = current.left;
            } else if (key > current.key) {
                current = current.right;
            } else {
                // Si la clave ya existe, no se inserta duplicado y se sale
                return;
            }
        }

        // Inserta el nuevo nodo como hijo izquierdo o derecho del nodo padre
        if (key < parent.key) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
    }

    // Método de búsqueda iterativa
    boolean search(int key) {
        Node current = root;
        while (current != null) {
            if (key == current.key) {
                return true;
            } else if (key < current.key) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return false;
    }
}
