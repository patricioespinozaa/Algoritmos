/**
 * Clase SplayTree que representa un árbol binario de búsqueda autoajustable.
 * Después de cada operación de búsqueda o inserción, el nodo accedido o insertado
 * se lleva a la raíz para mejorar la eficiencia en accesos futuros.
 */
public class SplayTree {
    Node root; // Nodo raíz del árbol

    /**
     * Realiza una rotación hacia la derecha en el árbol con raíz en el nodo x.
     * Este tipo de rotación se usa en las operaciones de "zig" o "zig-zig".
     *
     * @param x Nodo raíz donde se realiza la rotación.
     * @return Nuevo nodo raíz después de la rotación.
     */
    private Node rightRotate(Node x) {
        Node y = x.left;
        x.left = y.right;
        y.right = x;
        return y;
    }

    /**
     * Realiza una rotación hacia la izquierda en el árbol con raíz en el nodo x.
     * Este tipo de rotación se usa en las operaciones de "zag" o "zag-zag".
     *
     * @param x Nodo raíz donde se realiza la rotación.
     * @return Nuevo nodo raíz después de la rotación.
     */
    private Node leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        y.left = x;
        return y;
    }

    /**
     * Realiza la operación splay para llevar el nodo con la clave especificada a la raíz.
     * Se ejecutan diferentes rotaciones según la posición de key relativa a root.
     *
     * @param root Nodo raíz actual del árbol.
     * @param key Clave a buscar y llevar a la raíz.
     * @return Nuevo nodo raíz después de la operación splay.
     */
    private Node splay(Node root, int key) {
        // Caso base: El árbol está vacío o la clave ya está en la raíz.
        if (root == null || root.key == key)
            return root;

        // Caso zig o zig-zig: key está en el subárbol izquierdo.
        if (key < root.key) {
            // Si no hay subárbol izquierdo, retornar la raíz actual.
            if (root.left == null) return root;

            // Caso zig-zig: key está en el subárbol izquierdo del subárbol izquierdo.
            if (key < root.left.key) {
                root.left.left = splay(root.left.left, key);
                root = rightRotate(root);
            }
            // Caso zig-zag: key está en el subárbol derecho del subárbol izquierdo.
            else if (key > root.left.key) {
                root.left.right = splay(root.left.right, key);
                if (root.left.right != null)
                    root.left = leftRotate(root.left);
            }
            // Realizar rotación zig si es necesario.
            return (root.left == null) ? root : rightRotate(root);
        }
        // Caso zag o zag-zag: key está en el subárbol derecho.
        else {
            // Si no hay subárbol derecho, retornar la raíz actual.
            if (root.right == null) return root;

            // Caso zag-zag: key está en el subárbol derecho del subárbol derecho.
            if (key > root.right.key) {
                root.right.right = splay(root.right.right, key);
                root = leftRotate(root);
            }
            // Caso zag-zig: key está en el subárbol izquierdo del subárbol derecho.
            else if (key < root.right.key) {
                root.right.left = splay(root.right.left, key);
                if (root.right.left != null)
                    root.right = rightRotate(root.right);
            }
            // Realizar rotación zag si es necesario.
            return (root.right == null) ? root : leftRotate(root);
        }
    }

    /**
     * Inserta una clave en el Splay Tree.
     * La clave insertada se lleva a la raíz mediante la operación splay.
     *
     * @param key Clave a insertar.
     */
    public void insert(int key) {
        // Si el árbol está vacío, crea un nuevo nodo como raíz.
        if (root == null) {
            root = new Node(key);
            return;
        }

        // Llevar el nodo con la clave a la raíz.
        root = splay(root, key);

        // Si la clave ya existe en la raíz, no hacer nada.
        if (root.key == key) return;

        // Crear un nuevo nodo y reorganizar el árbol.
        Node newNode = new Node(key);
        if (key < root.key) {
            newNode.right = root;
            newNode.left = root.left;
            root.left = null;
        } else {
            newNode.left = root;
            newNode.right = root.right;
            root.right = null;
        }
        root = newNode; // La nueva raíz es el nodo insertado.
    }

    /**
     * Busca una clave en el Splay Tree.
     * Si se encuentra, la clave se lleva a la raíz mediante la operación splay.
     *
     * @param key Clave a buscar.
     * @return true si la clave está en el árbol, false en caso contrario.
     */
    public boolean search(int key) {
        root = splay(root, key); // Lleva el nodo con la clave buscada a la raíz.
        return root != null && root.key == key; // Verifica si la clave es la raíz actual.
    }
}
