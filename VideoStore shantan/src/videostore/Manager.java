
package videostore;


public class Manager {

    //the list of videos contained in the store
    private Structure videoInStore;
    //the list of customers that come to the store
    private Structure customers;
    //the list of videos checked out of the store
    private Structure videoCheckedOut;
    private String type;

    /**
     * Create a manager with the supplied data structure
     *
     * @param type the type of data structure to use
     */
    public Manager(String type) {
        this.type = type;
        switch (type) {
            case "s":
                videoCheckedOut = new SinglyLinkedList<>();
                videoInStore = new SinglyLinkedList<>();
                customers = new SinglyLinkedList<>();
                break;
            case "d":
                videoCheckedOut = new DoublyLinkedList<>();
                videoInStore = new DoublyLinkedList<>();
                customers = new DoublyLinkedList<>();
                break;
            case "b":
                videoCheckedOut = new BinarySearchTree<>();
                videoInStore = new BinarySearchTree<>();
                customers = new BinarySearchTree<>();
                break;
            default:
                videoCheckedOut = new AVLTree<>();
                videoInStore = new AVLTree<>();
                customers = new AVLTree<>();
                break;
        }
    }

    /**
     * Add a video to the videoInStore list
     *
     * @param v the video to put in the list
     */
    public void setVideoInStore(Video v) {
        switch (type) {
            case "a":
                AVLTree temp = (AVLTree) videoInStore;
                temp.insert(v);
                break;
            case "b":
                BinarySearchTree temp1 = (BinarySearchTree) videoInStore;
                temp1.insert(v);
                break;
            case "d":
                DoublyLinkedList temp2 = (DoublyLinkedList) videoInStore;
                temp2.insert(v);
                break;
            case "s":
                SinglyLinkedList temp3 = (SinglyLinkedList) videoInStore;
                temp3.insert(v);
                break;
        }
    }

    /**
     * Add a customer to the stores customer list
     *
     * @param c the customer to add
     */
    public void setCustomer(Customer c) {
        switch (type) {
            case "a":
                AVLTree temp = (AVLTree) customers;
                temp.insert(c);
                break;
            case "b":
                BinarySearchTree temp1 = (BinarySearchTree) customers;
                temp1.insert(c);
                break;
            case "d":
                DoublyLinkedList temp2 = (DoublyLinkedList) customers;
                temp2.insert(c);
                break;
            case "s":
                SinglyLinkedList temp3 = (SinglyLinkedList) customers;
                temp3.insert(c);
                break;
        }
    }

    /**
     * Delete a customer from the customer list if in it
     *
     * @param c the customer to delete
     */
    public void deleteCustomer(Customer c) {
        switch (type) {
            case "a":
                AVLTree temp = (AVLTree) customers;
                if (temp.contains(c)) {
                    temp.remove(c);
                }
                break;
            case "b":
                BinarySearchTree temp1 = (BinarySearchTree) customers;
                if (temp1.contains(c)) {
                    temp1.remove(c);
                }
                break;
            case "d":
                DoublyLinkedList temp2 = (DoublyLinkedList) customers;
                if (temp2.contains(c)) {
                    temp2.remove(c);
                }
                break;
            case "s":
                SinglyLinkedList temp3 = (SinglyLinkedList) customers;
                if (temp3.contains(c)) {
                    temp3.remove(c);
                }
                break;
        }

    }

    /**
     * Check whether a customer is in the database
     *
     * @param c the customer to look for
     * @return true if the customer is in the database, false if it is not
     */
    public boolean check(Customer c) {
        switch (type) {
            case "a":
                AVLTree temp = (AVLTree) customers;
                return temp.contains(c);
            case "b":
                BinarySearchTree temp1 = (BinarySearchTree) customers;
                return temp1.contains(c);
            case "d":
                DoublyLinkedList temp2 = (DoublyLinkedList) customers;
                return temp2.contains(c);
            case "s":
                SinglyLinkedList temp3 = (SinglyLinkedList) customers;
                return temp3.contains(c);
        }
        return customers.contains(c);
    }

    /**
     * Delete a video from the in store list if it is in it
     *
     * @param v the video to delete from the in store list
     */
    public void deleteVideo(Video v) {
        switch (type) {
            case "a":
                AVLTree temp = (AVLTree) videoInStore;
                if (temp.contains(v)) {
                    temp.remove(v);
                }
                break;
            case "b":
                BinarySearchTree temp1 = (BinarySearchTree) videoInStore;
                if (temp1.contains(v)) {
                    temp1.remove(v);
                }
                break;
            case "d":
                DoublyLinkedList temp2 = (DoublyLinkedList) videoInStore;
                if (temp2.contains(v)) {
                    temp2.remove(v);
                }
                break;
            case "s":
                SinglyLinkedList temp3 = (SinglyLinkedList) videoInStore;
                if (temp3.contains(v)) {
                    temp3.remove(v);
                }
                break;
        }
    }

    /**
     * Check whether a particular video is in the store
     *
     * @param v the video to check for
     * @return true if the video is in the store, false if it is not
     */
    public boolean checkIn(Video v) {
        switch (type) {
            case "a":
                AVLTree temp = (AVLTree) videoInStore;
                return temp.contains(v);
            case "b":
                BinarySearchTree temp1 = (BinarySearchTree) videoInStore;
                return temp1.contains(v);
            case "d":
                DoublyLinkedList temp2 = (DoublyLinkedList) videoInStore;
                return temp2.contains(v);
            default:
                SinglyLinkedList temp3 = (SinglyLinkedList) videoInStore;
                return temp3.contains(v);
        }
    }

    /**
     * Check whether a particular video is rented out
     *
     * @param v the video to check for
     * @return true if the video is in the rented list, false if it is not
     */
    public boolean checkOut(Video v) {
        switch (type) {
            case "a":
                AVLTree temp = (AVLTree) videoCheckedOut;
                return temp.contains(v);
            case "b":
                BinarySearchTree temp1 = (BinarySearchTree) videoCheckedOut;
                return temp1.contains(v);
            case "d":
                DoublyLinkedList temp2 = (DoublyLinkedList) videoCheckedOut;
                return temp2.contains(v);
            default:
                SinglyLinkedList temp3 = (SinglyLinkedList) videoCheckedOut;
                return temp3.contains(v);
        }
    }

    /**
     * Check out a video from the store Add it to the videoCheckOut list
     *
     * @param v the video to check out
     * @param c the customer checking out the video
     * @return true if the video is checked out successfully, false if it is not
     */
    public boolean checkOut(Video v, Customer c) {
        if (checkIn(v) && check(c)) {
            switch (type) {
                case "a":
                    AVLTree temp = (AVLTree) customers;
                    BNode foo = temp.find(c);
                    Customer target = (Customer) foo.get();
                    target.rentVid(v);
                    temp = (AVLTree) videoInStore;
                    temp.remove(v);
                    temp = (AVLTree) videoCheckedOut;
                    temp.insert(v);
                    return true;
                case "b":
                    BinarySearchTree temp1 = (BinarySearchTree) customers;
                    BNode foo1 = temp1.find(c);
                    Customer target1 = (Customer) foo1.get();
                    target1.rentVid(v);
                    temp1 = (BinarySearchTree) videoInStore;
                    temp1.remove(v);
                    temp1 = (BinarySearchTree) videoCheckedOut;
                    temp1.insert(v);
                    return true;
                case "d":
                    DoublyLinkedList temp2 = (DoublyLinkedList) customers;
                    DNode foo2 = temp2.find(c);
                    Customer target2 = (Customer) foo2.get();
                    target2.rentVid(v);
                    temp2 = (DoublyLinkedList) videoInStore;
                    temp2.remove(v);
                    temp2 = (DoublyLinkedList) videoCheckedOut;
                    temp2.insert(v);
                    return true;
                default:
                    SinglyLinkedList temp3 = (SinglyLinkedList) customers;
                    Node foo3 = temp3.find(c);
                    Customer target3 = (Customer) foo3.get();
                    target3.rentVid(v);
                    temp3 = (SinglyLinkedList) videoInStore;
                    temp3.remove(v);
                    temp3 = (SinglyLinkedList) videoCheckedOut;
                    temp3.insert(v);
                    return true;
            }
        }
        //video or customer does not exist
        return false;
    }

    /**
     * Helper method to get a specific customer from the list when required
     *
     * @param c the customer to retrieve
     * @return the customer c that is held in the customers list
     */
    public Customer getCust(Customer c) {
        switch (type) {
            case "a":
                AVLTree temp = (AVLTree) customers;
                if (temp.contains(c)) {
                    BNode herp = temp.find(c);
                    return (Customer) herp.get();
                }
                return null;
            case "b":
                BinarySearchTree temp1 = (BinarySearchTree) customers;
                if (temp1.contains(c)) {
                    BNode herp = temp1.find(c);
                    return (Customer) herp.get();
                }
                return null;
            case "d":
                DoublyLinkedList temp2 = (DoublyLinkedList) customers;
                if (temp2.contains(c)) {
                    DNode herp = temp2.find(c);
                    return (Customer) herp.get();
                }
                return null;
            default:
                SinglyLinkedList temp3 = (SinglyLinkedList) customers;
                if (temp3.contains(c)) {
                    Node herp = temp3.find(c);
                    return (Customer) herp.get();
                }
                return null;
        }
    }

    /**
     * Check in a video to the store when a customer returns it Add it to the
     * videoInStore list
     *
     * @param v the video to check out
     * @param c the customer returning the video
     * @return true if the video is checked in successfully, false if it is not
     */
    public boolean checkIn(Video v, Customer c) {
        if (checkOut(v) && check(c)) {
            switch (type) {
                case "a":
                    AVLTree temp = (AVLTree) customers;
                    BNode foo = temp.find(c);
                    Customer returner = (Customer) foo.get();
                    returner.returnVid(v);
                    temp = (AVLTree) videoCheckedOut;
                    temp.remove(v);
                    temp = (AVLTree) videoInStore;
                    temp.insert(v);
                    return true;
                case "b":
                    BinarySearchTree temp1 = (BinarySearchTree) customers;
                    BNode foo1 = temp1.find(c);
                    Customer returner1 = (Customer) foo1.get();
                    returner1.returnVid(v);
                    temp1 = (BinarySearchTree) videoCheckedOut;
                    temp1.remove(v);
                    temp1 = (BinarySearchTree) videoInStore;
                    temp1.insert(v);
                    return true;
                case "d":
                    DoublyLinkedList temp2 = (DoublyLinkedList) customers;
                    DNode foo2 = temp2.find(c);
                    Customer returner2 = (Customer) foo2.get();
                    returner2.returnVid(v);
                    temp2 = (DoublyLinkedList) videoCheckedOut;
                    temp2.remove(v);
                    temp2 = (DoublyLinkedList) videoInStore;
                    temp2.insert(v);
                    return true;
                default:
                    SinglyLinkedList temp3 = (SinglyLinkedList) customers;
                    Node foo3 = temp3.find(c);
                    Customer returner3 = (Customer) foo3.get();
                    returner3.returnVid(v);
                    temp3 = (SinglyLinkedList) videoCheckedOut;
                    temp3.remove(v);
                    temp3 = (SinglyLinkedList) videoInStore;
                    temp3.insert(v);
                    return true;
            }
        }
        //video or customer does not exist
        return false;
    }

    /**
     * Print all the customers
     */
    public void printAllCust() {
        System.out.println("Customers:");
        switch (type) {
            case "a":
                AVLTree temp = (AVLTree) customers;
                temp.print();
                break;
            case "b":
                BinarySearchTree temp1 = (BinarySearchTree) customers;
                temp1.print();
                break;
            case "d":
                DoublyLinkedList temp2 = (DoublyLinkedList) customers;
                temp2.print();
                break;
            case "s":
                SinglyLinkedList temp3 = (SinglyLinkedList) customers;
                temp3.print();
                break;
        }
    }

    /**
     * Print all videos
     */
    public void printAllVid() {
        printVidInStore();
        printVidOut();
    }

    /**
     * Print all the videos in the store
     */
    public void printVidInStore() {
        System.out.println("Videos in store: ");
        switch (type) {
            case "a":
                AVLTree temp = (AVLTree) videoInStore;
                temp.print();
                break;
            case "b":
                BinarySearchTree temp1 = (BinarySearchTree) videoInStore;
                temp1.print();
                break;
            case "d":
                DoublyLinkedList temp2 = (DoublyLinkedList) videoInStore;
                temp2.print();
                break;
            case "s":
                SinglyLinkedList temp3 = (SinglyLinkedList) videoInStore;
                temp3.print();
                break;
        }
    }

    /**
     * Print the videos rented out
     */
    public void printVidOut() {
        System.out.println("Checked out videos: ");
        switch (type) {
            case "a":
                AVLTree temp = (AVLTree) videoCheckedOut;
                temp.print();
                break;
            case "b":
                BinarySearchTree temp1 = (BinarySearchTree) videoCheckedOut;
                temp1.print();
                break;
            case "d":
                DoublyLinkedList temp2 = (DoublyLinkedList) videoCheckedOut;
                temp2.print();
                break;
            case "s":
                SinglyLinkedList temp3 = (SinglyLinkedList) videoCheckedOut;
                temp3.print();
                break;
        }
    }

    /**
     * Print all videos rented by a customer
     *
     * @param c the customer to print videos rented by
     */
    public void printVidCust(Customer c) {
        switch (type) {
            case "a":
                AVLTree temp = (AVLTree) customers;
                if (temp.contains(c)) {
                    BNode foo = temp.find(c);
                    Customer targ = (Customer) foo.get();
                    temp = (AVLTree) targ.getRentVideo();
                    temp.print();
                } else {
                    System.out.println("That customer does not exist in the system...");
                }
                break;
            case "b":
                BinarySearchTree temp1 = (BinarySearchTree) customers;
                if (temp1.contains(c)) {
                    BNode foo1 = temp1.find(c);
                    Customer targ = (Customer) foo1.get();
                    temp1 = (BinarySearchTree) targ.getRentVideo();
                    temp1.print();
                } else {
                    System.out.println("That customer does not exist in the system...");
                }
                break;
            case "d":
                DoublyLinkedList temp2 = (DoublyLinkedList) customers;
                if (temp2.contains(c)) {
                    DNode foo2 = temp2.find(c);
                    Customer targ = (Customer) foo2.get();
                    temp2 = (DoublyLinkedList) targ.getRentVideo();
                    temp2.print();
                } else {
                    System.out.println("That customer does not exist in the system...");
                }
                break;
            default:
                SinglyLinkedList temp3 = (SinglyLinkedList) customers;
                if (temp3.contains(c)) {
                    Node foo3 = temp3.find(c);
                    Customer targ = (Customer) foo3.get();
                    temp3 = (SinglyLinkedList) targ.getRentVideo();
                    temp3.print();
                } else {
                    System.out.println("That customer does not exist in the system...");
                }
                break;
        }
    }
}
