
package videostore;


public class Node<E extends Comparable<E>> extends SuperNode {

    //the item to store
    private E t;
    //the next node
    private Node<E> next;

    /**
     * Constructor for Nodes
     *
     * @param t the element the node stores
     * @param next the next node in the list
     */
    public Node(E t, Node<E> next) {
        this.t = t;
        this.next = next;
    }

    /**
     * Accessor for the element in the node
     *
     * @return the element contained in the node
     */
    @Override
    public E get() {
        return t;
    }

    /**
     * Set the element in the node to a new element
     *
     * @param t the new element for the node to hold
     */
    public void set(E t) {
        this.t = t;
    }

    /**
     * Accessor for the next node in the list
     *
     * @return the next node
     */
    public Node<E> getNext() {
        return next;
    }

    /**
     * Change the next item in the list
     *
     * @param next the new next node
     */
    public void setNext(Node<E> next) {
        this.next = next;
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
