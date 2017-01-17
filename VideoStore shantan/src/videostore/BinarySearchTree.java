
package videostore;


public class BinarySearchTree<E extends Comparable<E>> extends BinaryTree {

    /**
     * Constructor for BST
     */
    public BinarySearchTree() {
        root = null;
    }

    /**
     * Inserts a node in the BST where it belongs
     *
     * @param newItem the data to put in the BST
     */
    public void insert(E newItem) {
        root = insertHelper(root, new BNode<>(newItem));
    }

    /**
     * helper method for insert
     *
     * @param rt the root node
     * @param newNode the node to insert
     * @return the new root after the insert
     */
    private BNode<E> insertHelper(BNode<E> rt, BNode<E> newNode) {
        if (rt == null) {
            rt = newNode;
            return rt;
        }
        if (newNode.compareTo(rt) < 0) {
            rt.setL(insertHelper(rt.getL(), newNode));
        } else {
            rt.setR(insertHelper(rt.getR(), newNode));
        }
        return rt;
    }

    /**
     * Finds the maximum data in the tree
     *
     * @return the maximum data
     */
    public E max() {
        if (root == null) {
            return null;
        }
        BNode<E> tmp = maxHelper(root);
        return tmp.get();
    }

    private BNode<E> maxHelper(BNode<E> rt) {
        return (rt.getR() == null) ? rt : maxHelper(rt.getR());
    }

    /**
     * Finds the minimum data in the tree
     *
     * @return the minimum data in the tree
     */
    public E min() {
        if (root == null) {
            return null;
        }
        BNode<E> tmp = minHelper(root);
        return tmp.get();
    }

    private BNode<E> minHelper(BNode<E> rt) {
        return rt.getL() == null ? rt : minHelper(rt.getL());
    }

    /**
     * Finds the binary tree node which contains the data target
     *
     * @param target the data to find in the tree
     * @return null if the data isn't in the tree, or the node if the data is in
     * the tree
     */
    public BNode<E> find(E target) {
        return findHelper(root, new BNode(target));
    }

    private BNode<E> findHelper(BNode<E> rt, BNode<E> target) {
        if (rt == null) {
            return null;
        }
        if (target.compareTo(rt) < 0) {
            return findHelper(rt.getL(), target);
        }
        if (target.compareTo(rt) > 0) {
            return findHelper(rt.getR(), target);
        }
        return rt;
    }

    /**
     * Find the parent of the target node
     *
     * @param rt the root
     * @param target the target node
     * @return the parent node of target
     */
    public BNode<E> findParent(BNode<E> rt, BNode<E> target) {
        if (target.compareTo(rt) < 0) {
            if (rt.getL() == null) {
                return null;
            } else if (target.compareTo(rt.getL()) == 0) {
                return rt;
            } else {
                return findParent(rt.getL(), target);
            }
        } else {
            if (rt.getR() == null) {
                return null;
            } else if (target.compareTo(rt.getR()) == 0) {
                return rt;
            } else {
                return findParent(rt.getR(), target);
            }
        }
    }

    private BNode<E> successorParent(BNode<E> rt) {
        if (rt == null) {
            return null;
        }
        if (rt.getR() == null) {
            return null;
        }
        if (rt.getR().getL() == null) {
            return rt;
        }
        BNode<E> temp = rt.getR();
        while (temp.getL().getL() != null) {
            temp = temp.getL();
        }
        return temp;
    }

    /**
     * Removes a node from the tree with the specified data in it
     *
     * @param target What to look for to remove
     */
    public void remove(E target) {
        //there is no tree
        if (root == null) {
            return;
        }
        //put the target into a tree node
        BNode<E> targetNode = new BNode(target);
        //if the root is to be removed
        if (root.compareTo(targetNode) == 0) {
            BNode<E> sp1 = successorParent(root);
            //there is not right subtree
            if (sp1 == null) {
                root = root.getL();
            } //there is only one right child
            else if (sp1 == root) {
                sp1.getR().setL(root.getL());
                root = root.getR();
            } //theres is a successor in the right subtree that isnt the root's child
            else {
                BNode<E> rightChildOfSucc = sp1.getL().getR();
                sp1.getL().setL(root.getL());
                sp1.getL().setR(root.getR());
                root = sp1.getL();
                sp1.setL(rightChildOfSucc);
            }
            return;
        }
        BNode<E> targetParent = findParent(root, targetNode);
        //the target isnt in the tree
        if (targetParent == null) {
            return;
        }
        //the target has a left child at least
        if (targetParent.getL() != null) {
            //the left child is the node to remove
            if (targetParent.getL().compareTo(targetNode) == 0) {
                BNode<E> rNode = targetParent.getL();
                BNode<E> sp2 = successorParent(rNode);
                //the node is a leaf
                if (rNode.getL() == null && rNode.getR() == null) {
                    targetParent.setL(null);
                } //the node doesn't have a successor
                else if (sp2 == null) {
                    targetParent.setL(targetParent.getL().getL());
                } //the successor is a child of the node to remove
                else if (sp2 == rNode) {
                    sp2.getR().setL(rNode.getL());
                    targetParent.setL(sp2.getR());
                } //all other cases
                else {
                    BNode<E> rightChildOfSucc = sp2.getL().getR();
                    sp2.getL().setL(rNode.getL());
                    sp2.getL().setR(rNode.getR());
                    targetParent.setL(sp2.getL());
                    sp2.setL(rightChildOfSucc);
                }
                return;
            }
        }
        //the node to remove is in the right subtree
        if (targetParent.getR() != null) {
            if (targetParent.getR().compareTo(targetNode) == 0) {
                BNode<E> rNode = targetParent.getR();
                BNode<E> sp3 = successorParent(rNode);
                //the node is a leaf
                if (rNode.getL() == null && rNode.getR() == null) {
                    targetParent.setR(null);
                } //the node doesn't have a successor
                else if (sp3 == null) {
                    targetParent.setR(targetParent.getR().getL());
                } //the successor is a child of the node to remove
                else if (sp3 == rNode) {
                    sp3.getR().setL(sp3.getL());
                    targetParent.setR(sp3.getR());
                } //the other cases
                else {
                    BNode<E> rightChildOfSucc = sp3.getL().getR();
                    sp3.getL().setL(rNode.getL());
                    sp3.getL().setR(rNode.getR());
                    targetParent.setR(sp3.getL());
                    sp3.setL(rightChildOfSucc);
                }
            }
        }
    }

    /**
     * Checks whether the list contains the specified element
     *
     * @param e the element to check for
     * @return true if the element is in the list, false if it is not
     */
    public boolean contains(E e) {
        if (find(e) != null) {
            return true;
        }
        return false;
    }

    /**
     * print the nodes if any are in the tree
     */
    @Override
    public void print() {
        if (root == null) {
            System.out.println("There is nothing in this BST...");
        } else {
            this.inorderTraversal();
        }
    }
}
