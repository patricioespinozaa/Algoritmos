/**
 * Clase IterativeSplayTree que representa un árbol binario de búsqueda autoajustable.
 * Después de cada operación de búsqueda o inserción, el nodo accedido o insertado
 * se lleva a la raíz para mejorar la eficiencia en accesos futuros.
 */
public class IterativeSplayTree {
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
     * Realiza la operación splay de manera iterativa para llevar el nodo con la clave especificada a la raíz.
     * La operación splay se basa en rotaciones dobles o simples (zig-zig, zag-zag, zig, zag) para mover el nodo.
     *
     * @param key Clave a buscar y llevar a la raíz.
     * @return Nuevo nodo raíz después de la operación splay.
     */
    private Node splay(int key) {
        if (root == null || root.key == key) {
            return root;
        }

        Node current = root;
        Node dummy = new Node(0);  // Nodo auxiliar
        Node leftTreeMax = dummy;
        Node rightTreeMin = dummy;

        while (current != null && current.key != key) {
            if (key < current.key) {
                if (current.left == null) break;

                if (key < current.left.key) {
                    // Zig-zig: Rotación derecha doble
                    current = rightRotate(current);
                    if (current.left == null) break;
                }
                // Conectar el subárbol derecho
                rightTreeMin.left = current;
                rightTreeMin = current;
                current = current.left;
            } else {
                if (current.right == null) break;

                if (key > current.right.key) {
                    // Zag-zag: Rotación izquierda doble
                    current = leftRotate(current);
                    if (current.right == null) break;
                }
                // Conectar el subárbol izquierdo
                leftTreeMax.right = current;
                leftTreeMax = current;
                current = current.right;
            }
        }

        // Reconstruir el árbol
        leftTreeMax.right = current.left;
        rightTreeMin.left = current.right;
        current.left = dummy.right;
        current.right = dummy.left;
        root = current;
        return root;
    }

    /**
     * Inserta una clave en el Splay Tree de forma iterativa.
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
        root = splay(key);

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
        root = splay(key); // Lleva el nodo con la clave buscada a la raíz.
        return root != null && root.key == key; // Verifica si la clave es la raíz actual.
    }

    /**
     * Método para imprimir el árbol en orden.
     * Utiliza un recorrido en orden (in-order traversal) para imprimir las claves.
     */
    public void inorder() {
        inorderHelper(root);
    }

    private void inorderHelper(Node node) {
        if (node != null) {
            inorderHelper(node.left);
            System.out.print(node.key + " ");
            inorderHelper(node.right);
        }
    }

    /**
     * Clase interna que representa un nodo en el árbol binario de búsqueda.
     * Cada nodo tiene una clave, un hijo izquierdo y un hijo derecho.
     */
    public static class Node {
        int key;    // Clave del nodo
        Node left;  // Subárbol izquierdo
        Node right; // Subárbol derecho

        public Node(int item) {
            key = item;
            left = right = null;
        }
    }
}
