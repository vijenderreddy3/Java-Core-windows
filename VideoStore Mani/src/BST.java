
import java.util.ArrayList;

public class BST<T extends Comparable<T>> extends DataStructure<T> {

    /**
     * Inner class tree node
     */
    public class TreeNode<T extends Comparable<T>> {

        int height;
        T element;
        TreeNode<T> left;
        TreeNode<T> right;

        public TreeNode(T e) {
            element = e;
        }
    }

    protected TreeNode<T> root;
    protected int size = 0;

    /**
     * Returns true if the element is in the tree
     */
    public boolean contains(T e) {
        TreeNode<T> current = root; // Start from the root

        while (current != null) {
            if (e.compareTo(current.element) < 0) {
                current = current.left;
            }
            else if (e.compareTo(current.element) > 0) {
                current = current.right;
            }
            else // element matches current.element
                return true; // Element is found
        }

        return false;
    }

    public T search(T e) {
        TreeNode<T> current = root; // Start from the root

        while (current != null) {
            if (e.compareTo(current.element) < 0) {
                current = current.left;
            }
            else if (e.compareTo(current.element) > 0) {
                current = current.right;
            }
            else // element matches current.element
                return current.element; // Element is found
        }

        return null;
    }

    /**
     * Insert element o into the binary tree Return true if the element is
     * inserted successfully
     */
    public void insert(T e) {
        if (root == null)
            root = new TreeNode<T>(e); // Create a new root
        else {
            // Locate the parent node
            TreeNode<T> parent = null;
            TreeNode<T> current = root;
            while (current != null) {
                System.out.println("test");
                if (e.compareTo(current.element) < 0) {
                    parent = current;
                    current = current.left;
                }
                else if (e.compareTo(current.element) > 0) {
                    parent = current;
                    current = current.right;
                }
            }
            // Create the new node and attach it to the parent node
            if (e.compareTo(parent.element) < 0)
                parent.left = new TreeNode<T>(e);
            else
                parent.right = new TreeNode<T>(e);
        }

        size++;
    }

    /**
     * Inorder traversal from the root
     */
    public String inorder() {
        return inorder(root, "");
    }

    /**
     * Inorder traversal from a subtree
     */
    private String inorder(TreeNode<T> root, String output) {
        if (root == null)
            return output;
        inorder(root.left, output);
        output += root.element + ", ";
        inorder(root.right, output);
        return output;
    }

    /**
     * Postorder traversal from the root
     */
    public String postorder() {
        return postorder(root, "");
    }

    /**
     * Postorder traversal from a subtree
     */
    private String postorder(TreeNode<T> root, String output) {
        if (root == null)
            return output;
        postorder(root.left, output);
        postorder(root.right, output);
        output += root.element + ", ";
        return output;
    }

    /**
     * Preorder traversal from the root
     */
    public String preorder() {
        StringBuilder output = new StringBuilder();
        preorder(root, output);
        return output.toString();
    }

    /**
     * Preorder traversal from a subtree
     */
    protected void preorder(TreeNode<T> root, StringBuilder output) {
        if (root == null)
            return;
        output.append(root.element + ",  ");
        preorder(root.left, output);
        preorder(root.right, output);
    }

    /**
     * Get the number of nodes in the tree
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns a path from the root leading to the specified element
     */
    public ArrayList<TreeNode<T>> path(T e) {
        ArrayList<TreeNode<T>> list = new ArrayList<TreeNode<T>>();
        TreeNode<T> current = root; // Start from the root

        while (current != null) {
            list.add(current); // Add the node to the list
            if (e.compareTo(current.element) < 0) {
                current = current.left;
            }
            else if (e.compareTo(current.element) > 0) {
                current = current.right;
            }
            else
                break;
        }

        return list; // Return an array of nodes
    }

    /**
     * Delete an element from the binary tree. Return true if the element is
     * deleted successfully Return false if the element is not in the tree
     */
    public T remove(T e) {
        T temp = search(e);
        if (temp == null)
            return null;
        // Locate the node to be deleted and also locate its parent node
        TreeNode<T> parent = null;
        TreeNode<T> current = root;
        while (current != null) {
            if (e.compareTo(current.element) < 0) {
                parent = current;
                current = current.left;
            }
            else if (e.compareTo(current.element) > 0) {
                parent = current;
                current = current.right;
            }
            else
                break; // Element is in the tree pointed by current
        }

        if (current == null)
            return null; // Element is not in the tree

        // Case 1: current has no left children
        if (current.left == null) {
            // Connect the parent with the right child of the current node
            if (parent == null) {
                root = current.right;
            }
            else if (e.compareTo(parent.element) < 0)
                parent.left = current.right;
            else
                parent.right = current.right;
        }
        else {
            // Case 2: The current node has a left child
            // Locate the rightmost node in the left subtree of
            // the current node and also its parent
            TreeNode<T> parentOfRightMost = current;
            TreeNode<T> rightMost = current.left;

            while (rightMost.right != null) {
                parentOfRightMost = rightMost;
                rightMost = rightMost.right; // Keep going to the right
            }

            // Replace the element in current by the element in rightMost
            current.element = rightMost.element;

            // Eliminate rightmost node
            if (parentOfRightMost.right == rightMost)
                parentOfRightMost.right = rightMost.left;
            else
                // Special case: parentOfRightMost == current
                parentOfRightMost.left = rightMost.left;
        }

        size--;
        return temp; // Element inserted
    }

    @Override
    public String toString() {
        String output = preorder();
        if (output.length() < 3)
            return "";
        return output.substring(0, output.length() - 3);
    }

}
