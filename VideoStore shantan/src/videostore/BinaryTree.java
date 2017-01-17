
package videostore;

public class BinaryTree<E extends Comparable<E>> extends Structure {

    public BNode<E> root;

    /**
     * clears the tree
     */
    public void clear() {
        root = null;
    }

    /**
     * Find the number of nodes in the tree
     *
     * @return the number of nodes in the tree
     */
    public int size() {
        return sizeHelper(root);
    }

    private int sizeHelper(BNode<E> rt) {
        if (rt == null) {
            return 0;
        }
        return sizeHelper(rt.getL()) + sizeHelper(rt.getR());
    }

    /**
     * Finds the height of the tree
     *
     * @return the height of the tree
     */
    public int height() {
        return heightHelper(root, -1);
    }

    /**
     * Get height of given node
     *
     * @param target the node to find the height of
     * @return the height of the node
     */
    public int height(BNode<E> target) {
        return heightHelper(target, -1);
    }

    private int heightHelper(BNode<E> rt, int ht) {
        if (rt == null) {
            return ht;
        }
        return Math.max(heightHelper(rt.getL(), ht + 1), heightHelper(rt.getR(), ht + 1));
    }

    /**
     * Will conduct a preorder traversal on the binary tree
     */
    public void preorderTraversal() {
        preorderHelper(root);
        System.out.println();
    }

    private void preorderHelper(BNode<E> rt) {
        if (rt == null) {
            return;
        }
        System.out.print("\t" + rt.get());
        preorderHelper(rt.getL());
        preorderHelper(rt.getR());
    }

    /**
     * Conducts an inorder tree traversal
     */
    public void inorderTraversal() {
        inorderHelper(root);
    }

    private void inorderHelper(BNode<E> rt) {
        if (rt == null) {
            return;
        }
        inorderHelper(rt.getL());
        System.out.println(rt.get());
        inorderHelper(rt.getR());
    }

    public void postorderTraversal() {
        postorderHelper(root);
        System.out.println();
    }

    private void postorderHelper(BNode<E> rt) {
        if (rt == null) {
            return;
        }
        postorderHelper(rt.getL());
        postorderHelper(rt.getR());
        System.out.print("\t" + rt.get());
    }
}
