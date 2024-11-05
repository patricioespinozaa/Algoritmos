public class BinarySearchTree {
    Node root;

    BinarySearchTree() {
        root = null;
    }

    // Inserta un nuevo nodo en el árbol
    void insert(int key) {
        root = insertRec(root, key);
    }

    Node insertRec(Node root, int key) {
        if (root == null) {
            root = new Node(key);
            return root;
        }
        if (key < root.key)
            root.left = insertRec(root.left, key);
        else if (key > root.key)
            root.right = insertRec(root.right, key);
        return root;
    }

    // Busca un nodo en el árbol
    boolean search(int key) {
        return searchRec(root, key) != null;
    }

    Node searchRec(Node root, int key) {
        if (root == null || root.key == key)
            return root;
        if (root.key > key)
            return searchRec(root.left, key);
        return searchRec(root.right, key);
    }
}
