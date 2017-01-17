
/**
 * Hold information for video in store, and it's state (rented or not)
 */
public class Video implements Comparable<Video> {

    private String title;
    private String id;
    private boolean isRent;

    public Video(String id) {
        this.id = id;
    }

    public Video(String title, String id) {
        this.title = title;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRent(boolean isRent) {
        this.isRent = isRent;
    }

    public boolean isRent() {
        return isRent;
    }

    /**
     * compare between two videos using id, used to sort videos
     *
     * @param o Videos
     * @return int
     */
    @Override
    public int compareTo(Video o) {
        return id.compareTo(o.id);
    }

    /**
     * Compare between two videos if both have same id, used to check match
     * videos
     *
     * @param obj Object
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        return id.equals(((Video) obj).id);
    }

    /**
     * Return String representing video details
     *
     * @return
     */
    @Override
    public String toString() {
        return String.format("%s: %s", id, title);
    }

}
