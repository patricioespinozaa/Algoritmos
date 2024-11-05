// ABB Clasico

public class BinarySearchTree {
    Node root;

    BinarySearchTree() {
        root = null;
    }

    // ======================== INSERCION EN ABB CLASICO ======================== //

    // Insertar un nuevo nodo en el árbol
    void insert(int key) {
        root = insertRec(root, key);
    }

    Node insertRec(Node root, int key) {                 // Sea x el elemento a insertar
        if (root == null) {                              // Si la raíz es vacía
            root = new Node(key);                        // se crea un árbol de un solo nodo que contiene el elemento a insertar
            return root;
        }

        // En caso contrario, se compara x con el elemento de la raíz r:
        if (key < root.key)                              // Si x <r, se realiza el mismo proceso en el subárbol izquierdo.
            root.left = insertRec(root.left, key);
        else if (key > root.key)                         // Si x >r, se realiza el mismo proceso en el subárbol derechox
            root.right = insertRec(root.right, key);
        return root;
    }

    // ======================== BUSQUEDA EN ABB CLASICO ======================== //

    // Buscar un nodo en el árbol
    boolean search(int key) {
        return searchRec(root, key) != null;
    }

    Node searchRec(Node root, int key) {                //  Sea x el elemento buscado
        if (root == null || root.key == key)            //  se inicia la búsqueda en la raíz
            return root;                                // Si el elemento en la raíz es x, se termina la búsqueda con éxito

        // En caso contrario, se compara x con el elemento de la raíz r:
        if (key < root.key)                             // Si x <r, se realiza el mismo proceso en el subárbol izquierdo.
            return searchRec(root.left, key);
        return searchRec(root.right, key);              // Si x >r, se realiza el mismo proceso en el subárbol derecho.

        // Si eventualmente el subárbol donde deberíamos seguir la búsqueda es vacío, significa que el elemento
        // no existe, terminando el proceso de manera infructuosa.
    }
}
