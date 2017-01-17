
package videostore;

public class AVLTree<E extends Comparable<E>> extends BinarySearchTree {

    /**
     * Construct an empty AVL tree
     */
    public AVLTree() {
        root = null;
    }

    //TODO imnplement the insert and remove balancing methods
    /**
     * balance the tree if necessary after insertion or deletion
     *
     * @param w the inserted/deleted node
     */
    private void balance(BNode<E> w) {
        //find the node that is unbalanced, going up from w
        BNode<E> targParent = super.findParent(root, w);
        while (targParent != null) {
            if (checkBalance(targParent)) {
                targParent = super.findParent(root, targParent);
            } else {
                BNode<E> y = getHigherChild(targParent);
                BNode<E> x = getHigherChild(y);
                //case 1
                if (y == targParent.getR() && x == y.getR()) {
                    //get all subtrees
                    BNode<E> t1 = targParent.getR().getL();
                    //rebalance the nodes
                    BNode<E> zParent = super.findParent(root, targParent);
                    //z is the root
                    if (zParent == null) {
                        //t0, t2, and t3 are already in correct place
                        targParent.setR(t1);
                        y.setL(targParent);
                        root = y;
                    } else {
                        targParent.setR(t1);
                        y.setL(targParent);
                        zParent.setR(y);
                    }
                } //case 2
                else if (y == targParent.getL() && x == targParent.getL()) {
                    BNode<E> t2 = y.getR();
                    BNode<E> zParent = super.findParent(root, targParent);
                    //z is the root
                    if (zParent == null) {
                        targParent.setL(t2);
                        y.setR(targParent);
                        root = y;
                    } else {
                        targParent.setL(t2);
                        y.setR(targParent);
                        zParent.setL(y);
                    }
                } //case 3
                else if (y == targParent.getR() && x == y.getL()) {
                    BNode<E> t1 = x.getL();
                    BNode<E> t2 = x.getR();
                    BNode<E> zParent = super.findParent(root, targParent);
                    //z is the root
                    if (zParent == null) {
                        targParent.setR(t1);
                        y.setL(t2);
                        x.setL(targParent);
                        x.setR(y);
                        root = x;
                    } else {
                        targParent.setR(t1);
                        y.setL(t2);
                        x.setL(targParent);
                        x.setR(y);
                        zParent.setR(x);
                    }
                } //case 4
                else if (y == targParent.getL() && x == y.getR()) {
                    BNode<E> t1 = x.getL();
                    BNode<E> t2 = x.getR();
                    BNode<E> zParent = super.findParent(root, targParent);
                    if (zParent == null) {
                        targParent.setL(t2);
                        y.setR(t1);
                        x.setL(y);
                        x.setR(targParent);
                        root = x;
                    } else {
                        targParent.setL(t2);
                        y.setR(t1);
                        x.setL(y);
                        x.setR(targParent);
                        zParent.setL(x);
                    }
                } //none of the cases, should never be reached
                else {
                    System.out.println("Not sure");
                }
            }
        }

    }

    private BNode<E> getHigherChild(BNode<E> targ) {
        int leftHeight = super.height(targ.getL());
        int rightHeight = super.height(targ.getR());
        if (leftHeight > rightHeight) {
            return targ.getL();
        }else if(leftHeight == rightHeight){
			if(targ == findParent(root, targ).getR()){
				return targ.getR();
			}else{
				return targ.getL();
			}
		}
		else {
            return targ.getR();
        }
    }

    public boolean checkBalance(BNode<E> targ) {
        int leftHeight = super.height(targ.getL());
        int rightHeight = super.height(targ.getR());
        if (leftHeight == rightHeight || leftHeight == rightHeight - 1 || rightHeight == leftHeight - 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Insert into the AVL tree
     *
     * @param target the node to insert
     */
    public void insert(BNode<E> target) {
        super.insert(target.get());
        balance(target);
    }

    /**
     * Remove the target node
     *
     * @param target the node to remove
     */
    public void remove(BNode<E> target) {
        BNode<E> targParent = super.findParent(root, target);
        super.remove(target.get());
        balance(targParent);
    }

    /**
     * Print method for AVL tree
     */
    @Override
    public void print() {
        if (root == null) {
            System.out.println("There is nothing in this AVL Tree...");
        } else {
            this.inorderTraversal();
        }
    }
}
