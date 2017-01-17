
package videostore;


public class DNode<E extends Comparable<E>> extends SuperNode {

    //the object to hold
    private E t;
    //the pointers to the other nodes
    private DNode<E> next, prev;

    /**
     * Creates a new DNode
     *
     * @param t the object to hold
     * @param n the next node
     * @param p the previous node
     */
    public DNode(E t, DNode<E> n, DNode<E> p) {
        this.t = t;
        this.next = n;
        this.prev = p;
    }

    /**
     * Access the object held by the node
     *
     * @return the object in the node
     */
    public E get() {
        return t;
    }

    /**
     * Get the next node
     *
     * @return the next node
     */
    public DNode<E> getNext() {
        return next;
    }

    /**
     * Get the previous node
     *
     * @return the previous node
     */
    public DNode<E> getPrev() {
        return prev;
    }

    /**
     * Set the next node
     *
     * @param n the new next
     */
    public void setNext(DNode<E> n) {
        this.next = n;
    }

    /**
     * Set the previous node
     *
     * @param p the new prev
     */
    public void setPrev(DNode<E> p) {
        this.prev = p;
    }

    /**
     * Set the data held in this node
     *
     * @param e the new data to set
     */
    public void set(E e) {
        this.t = e;
    }

    /**
     * The compareTo method!!
     *
     * @param target what to compare with
     * @return -1 if the data is less than target, 0 if they are equal, and 1 if
     * the target is greater
     */
    public int compareTo(Node<E> target) {
        return (((Comparable) (this.t)).compareTo((Comparable) (target.get())));
    }

    /**
     * The toString method!
     *
     * @return the data held in the node as a string
     */
    @Override
    public String toString() {
        return t.toString();
    }
}
