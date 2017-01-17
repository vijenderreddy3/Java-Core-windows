
package videostore;


public class DoublyLinkedList<E extends Comparable<E>> extends Structure {

    //the first and last nodes of the list
    private DNode<E> header, trailer;

    public DoublyLinkedList() {
        header = new DNode<>(null, null, null);
        trailer = new DNode<>(null, null, header);
        header.setNext(trailer);
    }

    //TODO: impl all the accessing, getting, adding, removing, etc.
    /**
     * Get the header of the list
     *
     * @return the header DNode
     */
    public DNode<E> getHeader() {
        return header;
    }

    /**
     * Get the trailer of the list
     *
     * @return the trailer DNode
     */
    public DNode<E> getTrailer() {
        return trailer;
    }

    /**
     * Change the header
     *
     * @param head the new header DNode
     */
    public void setHeader(DNode<E> head) {
        header = head;
    }

    /**
     * Change the trailer
     *
     * @param trail the new trailer node
     */
    public void setTrailer(DNode<E> trail) {
        trailer = trail;
    }

    /**
     * Add a node to the list
     *
     * @param node the node to add to the list
     */
    public void add(DNode<E> node) {
        node.setNext(trailer);
        node.setPrev(trailer.getPrev());
        trailer.getPrev().setNext(node);
        trailer.setPrev(node);
    }

    /**
     * Method to insert data directly into the list
     *
     * @param e the data to be inserted in the list
     */
    public void insert(E e) {
        add(new DNode<>(e, null, null));
    }

    /**
     * Find the node that contains the element
     *
     * @param e the element to search for
     * @return the node containing the element
     */
    public DNode<E> find(E e) {
        if (contains(e)) {
            DNode<E> target = header.getNext();
            while (target.getNext() != trailer && target.get().compareTo(e) < 0) {
                target = target.getNext();
            }
            return target;
        }
        return null;
    }

    /**
     * Checks whether the list contains the specified element
     *
     * @param e the element to check for
     * @return true if the element is in the list, false if it is not
     */
    public boolean contains(E e) {
        if (header.getNext() == trailer) {
            return false;
        } else {
            //search through the list to find the element
            DNode<E> cur = header.getNext();
            while (cur != trailer) {
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
     * Remove a node from the end of the list
     */
    @Override
    public void remove() {
        if (trailer.getPrev() == header) {
            //do nothing, there is nothing in the list
        } else {
            //find the last node in the list
            DNode<E> last = trailer.getPrev();
            //set the node before this Next to the trailer
            last.getPrev().setNext(trailer);
            //set trailer's prev to whatever is before the last node
            trailer.setPrev(last.getPrev());
            //set the last nodes pointers to null
            last.setNext(null);
            last.setPrev(null);
        }
    }

    /**
     * Remove a specified object from the list
     *
     * @param e the object to remove
     */
    public void remove(E e) {
        //check whether the head is null
        if (header.getNext() == trailer) {
            //do nothing
        } else {
            DNode<E> cur = header.getNext();
            while (cur.getNext() != trailer && cur.get().compareTo(e) != 0) {
                cur = cur.getNext();
            }
            cur.getPrev().setNext(cur.getNext());
            cur.getNext().setPrev(cur.getPrev());
            cur.setNext(null);
            cur.setPrev(null);
            cur.set(null);
        }
    }

    /**
     * Print the list
     */
    @Override
    public void print() {
        if (header.getNext() == trailer) {
            System.out.println("There is nothing in this DLL...");
        } else {
            DNode cur = header.getNext();
            while (cur != trailer) {
                System.out.println(cur.get());
                cur = cur.getNext();
            }
        }
    }
}
