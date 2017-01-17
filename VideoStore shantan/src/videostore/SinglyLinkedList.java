
package videostore;


public class SinglyLinkedList<E extends Comparable<E>> extends Structure {

    //the head of the list
    private Node<E> head;

    /**
     * Create an empty list
     */
    public SinglyLinkedList() {
        head = null;
    }

    /**
     * Add new node to tail
     *
     * @param n the node to add
     */
    public void add(Node<E> n) {
        //check whether head is null
        if (head == null) {
            //n is the head
            head = n;
        } //otherwise, must search through the list to find the tail
        else {
            Node cur = head;
            while (cur.getNext() != null) {
                cur = cur.getNext();
            }
            //set next of cur to new node
            cur.setNext(n);
        }
        //set new node next to null
        n.setNext(null);
    }

    /**
     * Method to insert data directly into the list
     *
     * @param e the data to be inserted in the list
     */
    public void insert(E e) {
        add(new Node<>(e, null));
    }

    /**
     * Remove the last node
     */
    @Override
    public void remove() {
        //check whether the head is null
        if (head == null) {
            //do nothing
        } //otherwise, find node before the tail node
        else {
            //check whether there are only 2 items in the list
            if (head.getNext() == null) {
                head = null;
                return;
            }
            Node cur = head;
            while (cur.getNext().getNext() != null) {
                cur = cur.getNext();
            }
            cur.setNext(null);
        }
    }

    /**
     * Remove a specified object from the list
     *
     * @param e the object to remove
     */
    public void remove(E e) {
        //check whether the head is null
        if (head == null) {
            //do nothing
        } //otherwise, find node before the tail node
        else {
            //check whether there are only 2 items in the list
            if (head.getNext() == null) {
                if (head.get().compareTo(e) == 0) {
                    head = null;
                }
                return;
            }
            Node<E> cur = head;
            while (cur.getNext() != null && cur.get().compareTo(e) != 0) {
                cur = cur.getNext();
            }
            //target node is head of list
            if (cur == head) {
                Node<E> temp = head;
                head = (head.getNext());
                temp.setNext(null);
                temp.set(null);
            } else {
                Node<E> prev = getPrev(cur);
                prev.setNext(cur.getNext());
                cur.setNext(null);
                cur.set(null);
            }
        }
    }

    private Node<E> getPrev(Node<E> target) {
        if (head.get().compareTo(target.get()) == 0) {
            return head;
        } else {
            Node<E> cur = head;
            while (cur.getNext().get().compareTo(target.get()) != 0) {
                cur = cur.getNext();
            }
            return cur;
        }
    }

    /**
     * Reverse the nodes in the SLL
     */
    public void reverse() {
        if (head == null) {
            //do nothing
        } else {
            if (head.getNext() == null) {
                //do nothing
            } else {
                //find the last node
                Node<E> cur = head;
                Node<E> next;
                Node<E> prev = null;
                while (cur != null) {
                    //next is the next node in the list
                    next = cur.getNext();
                    //cur.next is changed to the previous node
                    cur.setNext(prev);
                    //prev is changed to cur
                    prev = cur;
                    //cur is set to next for the next node in the list
                    cur = next;
                }
                //now the head of the list is prev, so it should be set as such
                head = prev;
            }
        }
    }

    /**
     * Find the specified element in the list
     *
     * @param e the thing to find
     * @return the data that the element contains
     */
    public Node<E> find(E e) {
        if (!contains(e) || head == null) {
            return null;
        } else {
            Node<E> temp = head;
            while (temp.get().compareTo(e) < 0 && temp.getNext() != null) {
                temp = temp.getNext();
            }
            return temp;
        }
    }

    /**
     * Get the head of the SSL
     *
     * @return the head of the list
     */
    public Node getHead() {
        return head;
    }

    /**
     * Set the head to the node
     *
     * @param n the new head node
     */
    public void setHead(Node<E> n) {
        head = n;
    }

    /**
     * Checks whether the list contains the specified element
     *
     * @param e the element to check for
     * @return true if the element is in the list, false if it is not
     */
    public boolean contains(E e) {
        if (head == null) {
            return false;
        } else {
            //search through the list to find the element
            Node<E> cur = head;
            while (cur != null) {
                if (cur.get().compareTo(e) == 0) {
                    return true;
                } else if (cur.get().compareTo(e) < 0 || cur.get().compareTo(e) > 0) {
                    cur = cur.getNext();
                }
            }
            return false;
        }
    }

    /**
     * Print out the list
     */
    public void print() {
        if (head == null) {
            System.out.println("There is nothing in this SLL...");
        } else {
            Node cur = head;
            while (cur != null) {
                System.out.println(cur.get());
                cur = cur.getNext();
            }
        }
    }
}
