
/**
 * Customer that rent videos from store and return videos back
 */
public class Customer implements Comparable<Customer> {

    private String name;
    private String id;
    /**
     * Data structure for rented videos by customer
     */
    private DataStructure<Video> rentVideo;

    public Customer(String id, String dataType) {
        this.id = id;
        setRentVideo(dataType);
    }

    public Customer(String name, String id, String dataType) {
        this.name = name;
        this.id = id;
        setRentVideo(dataType);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addRentVideo(Video video) {
        rentVideo.insert(video);
    }

    public Video removeRentVideo(Video video) {
        return rentVideo.remove(video);
    }

    public DataStructure<Video> getRentVideo() {
        return rentVideo;
    }

    /**
     * Set Data structure type
     *
     * @param dataType String
     */
    private void setRentVideo(String dataType) {
        if (dataType.equals("AVL")) {
            rentVideo = new AVL<Video>();
        }
        else if (dataType.equals("BST")) {
            rentVideo = new BST<Video>();

        }
        else if (dataType.equals("DLL")) {
            rentVideo = new DLL<Video>();

        }
        else if (dataType.equals("SLL")) {
            rentVideo = new SLL<Video>();
        }
    }

    /**
     * compare between two customers using id, used to sort customers
     *
     * @param o Customer
     * @return int
     */
    @Override
    public int compareTo(Customer o) {
        return id.compareTo(o.id);
    }

    /**
     * Compare between two customers if both have same id, used to check match
     * customers
     *
     * @param obj Object
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        return id.equals(((Customer) obj).id);
    }

    /**
     * Return String representing the rented videos by customer with his name
     * and id
     *
     * @return String
     */
    @Override
    public String toString() {
        String output = String.format("%s: %s", id, name);
        if (rentVideo.toString().isEmpty()) {
            output += "-- No rented videos";
        }
        else {
            output += "-- Rented videos: " + rentVideo.toString();
        }
        return output;
    }

}
