
import java.util.ArrayList;

public class AVL<T extends Comparable<T>> extends BST<T> {

    /**
     * Override the insert method to balance the tree if necessary
     */
    public void insert(T o) {
        super.insert(o);
        balancePath(o); // Balance from o to the root if necessary
    }

    /**
     * Update the height of a specified node
     */
    private void updateHeight(TreeNode<T> node) {
        if (node.left == null && node.right == null) // node is a leaf
            node.height = 0;
        else if (node.left == null) // node has no left subtree
            node.height = 1 + ((TreeNode<T>) (node.right)).height;
        else if (node.right == null) // node has no right subtree
            node.height = 1 + ((TreeNode<T>) (node.left)).height;
        else
            node.height = 1 + Math.max(((TreeNode<T>) (node.right)).height,
                    ((TreeNode<T>) (node.left)).height);
    }

    /**
     * Balance the nodes in the path from the specified node to the root if
     * necessary
     */
    private void balancePath(T o) {
        ArrayList<TreeNode<T>> path = path(o);
        for (int i = path.size() - 1; i >= 0; i--) {
            TreeNode<T> A = (TreeNode<T>) (path.get(i));
            updateHeight(A);
            TreeNode<T> parentOfA = (A == root) ? null : (TreeNode<T>) (path.get(i - 1));

            switch (balanceFactor(A)) {
                case -2:
                    if (balanceFactor((TreeNode<T>) A.left) <= 0) {
                        balanceLL(A, parentOfA); // Perform LL rotation
                    }
                    else {
                        balanceLR(A, parentOfA); // Perform LR rotation
                    }
                    break;
                case +2:
                    if (balanceFactor((TreeNode<T>) A.right) >= 0) {
                        balanceRR(A, parentOfA); // Perform RR rotation
                    }
                    else {
                        balanceRL(A, parentOfA); // Perform RL rotation
                    }
            }
        }
    }

    /**
     * Return the balance factor of the node
     */
    private int balanceFactor(TreeNode<T> node) {
        if (node.right == null) // node has no right subtree
            return -node.height;
        else if (node.left == null) // node has no left subtree
            return +node.height;
        else
            return ((TreeNode<T>) node.right).height
                    - ((TreeNode<T>) node.left).height;
    }

    /**
     * Balance LL (see Figure 9.1)
     */
    private void balanceLL(TreeNode<T> A, TreeNode<T> parentOfA) {
        TreeNode<T> B = A.left; // A is left-heavy and B is left-heavy

        if (A == root) {
            root = B;
        }
        else if (parentOfA.left == A) {
            parentOfA.left = B;
        }
        else {
            parentOfA.right = B;
        }

        A.left = B.right; // Make T2 the left subtree of A
        B.right = A; // Make A the left child of B
        updateHeight((TreeNode<T>) A);
        updateHeight((TreeNode<T>) B);
    }

    /**
     * Balance LR (see Figure 9.1(c))
     */
    private void balanceLR(TreeNode<T> A, TreeNode<T> parentOfA) {
        TreeNode<T> B = A.left; // A is left-heavy
        TreeNode<T> C = B.right; // B is right-heavy

        if (A == root) {
            root = C;
        }
        else if (parentOfA.left == A) {
            parentOfA.left = C;
        }
        else {
            parentOfA.right = C;
        }

        A.left = C.right; // Make T3 the left subtree of A
        B.right = C.left; // Make T2 the right subtree of B
        C.left = B;
        C.right = A;

        // Adjust heights
        updateHeight((TreeNode<T>) A);
        updateHeight((TreeNode<T>) B);
        updateHeight((TreeNode<T>) C);
    }

    /**
     * Balance RR (see Figure 9.1(b))
     */
    private void balanceRR(TreeNode<T> A, TreeNode<T> parentOfA) {
        TreeNode<T> B = A.right; // A is right-heavy and B is right-heavy

        if (A == root) {
            root = B;
        }
        else if (parentOfA.left == A) {
            parentOfA.left = B;
        }
        else {
            parentOfA.right = B;
        }

        A.right = B.left; // Make T2 the right subtree of A
        B.left = A;
        updateHeight((TreeNode<T>) A);
        updateHeight((TreeNode<T>) B);
    }

    /**
     * Balance RL (see Figure 9.1(d))
     */
    private void balanceRL(TreeNode<T> A, TreeNode<T> parentOfA) {
        TreeNode<T> B = A.right; // A is right-heavy
        TreeNode<T> C = B.left; // B is left-heavy

        if (A == root) {
            root = C;
        }
        else if (parentOfA.left == A) {
            parentOfA.left = C;
        }
        else {
            parentOfA.right = C;
        }

        A.right = C.left; // Make T2 the right subtree of A
        B.left = C.right; // Make T3 the left subtree of B
        C.left = A;
        C.right = B;

        // Adjust heights
        updateHeight((TreeNode<T>) A);
        updateHeight((TreeNode<T>) B);
        updateHeight((TreeNode<T>) C);
    }

    /**
     * Remove an element from the binary tree. Return true if the element is
     * removed successfully Return false if the element is not in the tree
     */
    public T remove(T element) {
        if (root == null)
            return null; // Element is not in the tree
        T temp = search(element);
        // Locate the node to be removed and also locate its parent node
        TreeNode<T> parent = null;
        TreeNode<T> current = root;
        while (current != null) {
            if (element.compareTo(current.element) < 0) {
                parent = current;
                current = current.left;
            }
            else if (element.compareTo(current.element) > 0) {
                parent = current;
                current = current.right;
            }
            else
                break; // Element is in the tree pointed by current
        }

        if (current == null)
            return null; // Element is not in the tree

        // Case 1: current has no left children (See Figure 23.6)
        if (current.left == null) {
            // Connect the parent with the right child of the current node
            if (parent == null) {
                root = current.right;
            }
            else if (element.compareTo(parent.element) < 0)
                parent.left = current.right;
            else
                parent.right = current.right;

            // Balance the tree if necessary
            balancePath(parent.element);
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
                // Special case: parentOfRightMost is current
                parentOfRightMost.left = rightMost.left;

            // Balance the tree if necessary
            balancePath(parentOfRightMost.element);
        }

        size--;
        return temp; // Element inserted
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
