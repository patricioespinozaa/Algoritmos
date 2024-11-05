public class SplayTree {
    Node root;

    // Rotaciones para el Splay Tree
    private Node rightRotate(Node x) {
        Node y = x.left;
        x.left = y.right;
        y.right = x;
        return y;
    }

    private Node leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        y.left = x;
        return y;
    }

    // Operación splay
    private Node splay(Node root, int key) {
        if (root == null || root.key == key)
            return root;

        if (key < root.key) {
            if (root.left == null) return root;

            if (key < root.left.key) {
                root.left.left = splay(root.left.left, key);
                root = rightRotate(root);
            } else if (key > root.left.key) {
                root.left.right = splay(root.left.right, key);
                if (root.left.right != null)
                    root.left = leftRotate(root.left);
            }
            return (root.left == null) ? root : rightRotate(root);
        } else {
            if (root.right == null) return root;

            if (key > root.right.key) {
                root.right.right = splay(root.right.right, key);
                root = leftRotate(root);
            } else if (key < root.right.key) {
                root.right.left = splay(root.right.left, key);
                if (root.right.left != null)
                    root.right = rightRotate(root.right);
            }
            return (root.right == null) ? root : leftRotate(root);
        }
    }

    // Inserta un nodo en el Splay Tree
    public void insert(int key) {
        if (root == null) {
            root = new Node(key);
            return;
        }
        root = splay(root, key);
        if (root.key == key) return;

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
        root = newNode;
    }

    // Busca un nodo en el Splay Tree
    public boolean search(int key) {
        root = splay(root, key);
        return root != null && root.key == key;
    }
}

