public class BinarySearchTree {
    // Node class representing each node in the binary tree
    class Node {
        int data;
        Node left, right;

        public Node(int item) {
            data = item;
            left = right = null;
        }
    }

    // Root of Binary Tree
    Node root;

    // Constructor
    BinarySearchTree() {
        root = null;
    }

    // Method to insert a new node
    void insert(int key) {
        root = insertRec(root, key);
    }

    // A recursive function to insert a new key in BST
    Node insertRec(Node root, int key) {
        // If the tree is empty, return a new node
        if (root == null) {
            root = new Node(key);
            return root;
        }

        // Otherwise, recur down the tree
        if (key < root.data)
            root.left = insertRec(root.left, key);
        else if (key > root.data)
            root.right = insertRec(root.right, key);

        // Return the (unchanged) node pointer
        return root;
    }

    // Method to do inorder traversal of tree
    void inorder() {
        inorderRec(root);
    }

    // A utility function to do inorder traversal of BST
    void inorderRec(Node root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.print(root.data + " ");
            inorderRec(root.right);
        }
    }

    // Method to search a key in BST
    boolean search(int key) {
        return searchRec(root, key);
    }

    // A recursive function to search a given key in BST
    boolean searchRec(Node root, int key) {
        // Base Cases: root is null or key is present at root
        if (root == null || root.data == key)
            return root != null;

        // Key is greater than root's key
        if (root.data < key)
            return searchRec(root.right, key);

        // Key is smaller than root's key
        return searchRec(root.left, key);
    }

    // Depth First Search (DFS) implementation
    void depthFirstSearch() {
        System.out.println("Depth First Search (Pre-order traversal):");
        dfsRec(root);
        System.out.println();
    }

    // A utility function to do DFS of BST
    void dfsRec(Node node) {
        if (node == null)
            return;

        // First print data of node
        System.out.print(node.data + " ");

        // Then recur on left subtree
        dfsRec(node.left);

        // Now recur on right subtree
        dfsRec(node.right);
    }

    // Method to delete a key from the tree
    void deleteKey(int key) {
        root = deleteRec(root, key);
    }

    // A recursive function to delete a node from the BST
    Node deleteRec(Node root, int key) {
        // Base case: If the tree is empty
        if (root == null) return root;

        // Recursive calls for ancestors of the node to be deleted
        if (key < root.data)
            root.left = deleteRec(root.left, key);
        else if (key > root.data)
            root.right = deleteRec(root.right, key);

        // Node to be deleted is found
        else {
            // Case 1: Node with only one child or no child
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;

            // Case 2: Node with two children
            // Get the inorder successor (smallest in the right subtree)
            root.data = minValue(root.right);

            // Delete the inorder successor
            root.right = deleteRec(root.right, root.data);
        }

        return root;
    }

    // A utility function to find the minimum value node in the subtree
    int minValue(Node root) {
        int minv = root.data;
        while (root.left != null) {
            minv = root.left.data;
            root = root.left;
        }
        return minv;
    }

    // Driver Code
    public static void main(String[] args) {
        BinarySearchTree tree = new BinarySearchTree();

        /* Let's create the following BST
              50
           /     \
          30      70
         /  \    /  \
        20  40  60  80 */
        tree.insert(50);
        tree.insert(30);
        tree.insert(20);
        tree.insert(40);
        tree.insert(70);
        tree.insert(60);
        tree.insert(80);

        // Print inorder traversal of the BST
        System.out.println("Inorder traversal of the binary tree:");
        tree.inorder();
        System.out.println();

        // Search for a key
        int key = 60;
        System.out.println("\nSearching for key " + key + ": " + 
                           (tree.search(key) ? "Found" : "Not Found"));

        // Perform DFS
        tree.depthFirstSearch();

        // Delete a key from the BST
        tree.deleteKey(20);
        System.out.println("\nInorder traversal after deleting 20:");
        tree.inorder();
        System.out.println();

        // Delete another key
        tree.deleteKey(30);
        System.out.println("\nInorder traversal after deleting 30:");
        tree.inorder();
        System.out.println();

        // Delete another key
        tree.deleteKey(50);
        System.out.println("\nInorder traversal after deleting 50:");
        tree.inorder();
    }
}
