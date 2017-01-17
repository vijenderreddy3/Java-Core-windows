
package videostore;

public class Video implements Comparable<Video> {

    //the title of the video
    private String title;
    //the id tag of the video
    private String id;

    /**
     * Constructor for Video objects
     *
     * @param title the title of the video
     * @param id the id tag of the video
     */
    public Video(String title, String id) {
        this.title = title;
        this.id = id;
    }

    /**
     * Accessor for title of video
     *
     * @return the title of the video
     */
    public String getTitle() {
        return title;
    }

    /**
     * Accessor for id tag of video
     *
     * @return the id tag of video
     */
    public String getId() {
        return id;
    }

    /**
     * the toString method
     *
     * @return a String representing the video
     */
    @Override
    public String toString() {
        return title + "; " + id;
    }

    /**
     * the compareTo method! The titles and id's must match
     *
     * @param v the video to compare to
     * @return 0 if the titles match, 1 if this.title > v.title, -1 if
     * this.title < v.title
     */
    @Override
    public int compareTo(Video v) {
        if (compareId(v) == 0 && compareTitle(v) == 0) {
            return 0;
        } else if (compareId(v) < 0 && compareTitle(v) < 0) {
            return -1;
        } else if (compareId(v) < 0 && compareTitle(v) > 0) {
            return -2;
        } else if (compareId(v) > 0 && compareTitle(v) < 0) {
            return 2;
        } else {
            return 1;
        }
    }

    /**
     * compare this videos id with that of another videos id
     *
     * @param v the customer to compare to
     * @return 0 if the id's match, 1 if this.id > v.id, -1 if this.id < v.id
     */
    public int compareId(Video v) {
        if (this.id.compareToIgnoreCase(v.getId()) == 0) {
            return 0;
        } else if (this.id.compareToIgnoreCase(v.getId()) < 0) {
            return -1;
        } else {
            return 1;
        }
    }

    /**
     * Compare the titles of the videos
     *
     * @param v the video to compare with
     * @return 0 if the titles match, -1 if
     * this.title<v.title, 1 if this.title>v.title
     */
    public int compareTitle(Video v) {
        if (this.title.compareToIgnoreCase(v.getTitle()) == 0) {
            return 0;
        } else if (this.title.compareToIgnoreCase(v.getTitle()) < 0) {
            return -1;
        } else {
            return 1;
        }
    }
}
