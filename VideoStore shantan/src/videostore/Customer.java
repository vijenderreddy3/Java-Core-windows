
package videostore;

public class Customer<T extends Structure> implements Comparable<Customer> {

    //the customer name
    private String name;
    //the customer id
    private String id;
    //a list of videos currently rented out by the customer
    private Structure<Video> rentVideo;
    private String list;

    /**
     * Create a customer
     *
     * @param name customer name
     * @param id customer id
     * @param list the data structure that this customer will use to store data
     */
    public Customer(String name, String id, String list) {
        this.name = name;
        this.id = id;
        this.list = list;
        switch (list) {
            case "a":
                rentVideo = new AVLTree();
                break;
            case "b":
                rentVideo = new BinarySearchTree();
                break;
            case "d":
                rentVideo = new DoublyLinkedList();
                break;
            case "s":
                rentVideo = new SinglyLinkedList();
                break;
            default:
                rentVideo = new Structure();
                break;
        }
    }

    /**
     * Accessor for name field
     *
     * @return the name of the customer
     */
    public String getName() {
        return name;
    }

    /**
     * add a video to the list of this customers rented videos
     *
     * @param v the video to add
     */
    public void rentVid(Video v) {
        switch (list) {
            case "a":
                AVLTree temp = (AVLTree) rentVideo;
                temp.insert(v);
                break;
            case "b":
                BinarySearchTree temp1 = (BinarySearchTree) rentVideo;
                temp1.insert(v);
                break;
            case "d":
                DoublyLinkedList temp2 = (DoublyLinkedList) rentVideo;
                temp2.insert(v);
                break;
            default:
                SinglyLinkedList temp3 = (SinglyLinkedList) rentVideo;
                temp3.insert(v);
                break;
        }
    }

    /**
     * take a video out of the rented video list
     *
     * @param v the video to take out of the list
     */
    public void returnVid(Video v) {
        switch (list) {
            case "a":
                AVLTree temp = (AVLTree) rentVideo;
                if (temp.contains(v)) {
                    temp.remove(v);
                }
                break;
            case "b":
                BinarySearchTree temp1 = (BinarySearchTree) rentVideo;
                if (temp1.contains(v)) {
                    temp1.remove(v);
                }
                break;
            case "d":
                DoublyLinkedList temp2 = (DoublyLinkedList) rentVideo;
                if (temp2.contains(v)) {
                    temp2.remove(v);
                }
                break;
            default:
                SinglyLinkedList temp3 = (SinglyLinkedList) rentVideo;
                if (temp3.contains(v)) {
                    temp3.remove(v);
                }
                break;
        }

    }

    /**
     * Accessor for id field
     *
     * @return the id of the customer
     */
    public String getId() {
        return id;
    }

    /**
     * Accessor for the list
     *
     * @return the rentVideo data structure
     */
    public Structure getRentVideo() {
        switch (list) {
            case "a":
                return (AVLTree) rentVideo;
            case "b":
                return (BinarySearchTree) rentVideo;
            case "d":
                return (DoublyLinkedList) rentVideo;
            default:
                return (SinglyLinkedList) rentVideo;
        }
    }

    /**
     * the toString method
     *
     * @return String representation of the customer
     */
    @Override
    public String toString() {
        return name + "; " + id;
    }

    /**
     * the compareTo method! The name and id must match
     *
     * @param c the customer to compare to
     * @return 0 if the names match, 1 if this.name > c.name, -1 if this.name <
     * c.name
     */
    @Override
    public int compareTo(Customer c) {
        if (compareId(c) == 0 && compareName(c) == 0) {
            return 0;
        } else if (compareId(c) < 0 && compareName(c) < 0) {
            return -1;
        } else if (compareId(c) < 0 && compareName(c) > 0) {
            return -2;
        } else if (compareId(c) > 0 && compareName(c) < 0) {
            return 2;
        } else {
            return 1;
        }
    }

    /**
     * compare this customer's id with that of another customer's id
     *
     * @param c the customer to compare to
     * @return 0 if the id's match, 1 if this.id > c.id, -1 if this.id < c.id
     */
    public int compareId(Customer c) {
        if (this.id.compareToIgnoreCase(c.getId()) == 0) {
            return 0;
        } else if (this.id.compareToIgnoreCase(c.getId()) < 0) {
            return -1;
        } else {
            return 1;
        }
    }

    /**
     * Compare names of customers
     *
     * @param c the customer to compare with
     * @return 0 if this.name=c.name, -1 if
     * this.name<c.name, 1 if this.name>c.name
     */
    public int compareName(Customer c) {
        if (this.name.compareToIgnoreCase(c.getName()) == 0) {
            return 0;
        } else if (this.name.compareToIgnoreCase(c.getName()) < 0) {
            return -1;
        } else {
            return 1;
        }
    }
}
