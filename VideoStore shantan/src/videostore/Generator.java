
package videostore;

import java.util.LinkedList;
import java.util.Random;


public class Generator<T extends Structure> {

    private final String vid = "Video";
    private int vidCtr;
    private int vidId;
    private final String cust = "Customer";
    private int custCtr;
    private int custId;
    private final String list;
    private final Random rand;
    private final Random requestHelper;
    //the queue, instead of an actual queue
    protected LinkedList<String> q;
    //lists that hold every video and customer generated for use in requests
    private LinkedList<Customer> custDB;
    private LinkedList<Video> vidDB;

    public Generator(int numCust, int numVid, int numRequests, String list) {
        this.list = list;
        vidCtr = vidId = 0;
        custCtr = custId = 0;
        rand = new Random(System.currentTimeMillis());
        requestHelper = new Random(System.currentTimeMillis());
        q = new LinkedList<>();
        custDB = new LinkedList<>();
        vidDB = new LinkedList<>();
        //generate all videos, customers, and requests
        for (int i = 0; i < numCust; i++) {
            genCust();
        }
        for (int i = 0; i < numVid; i++) {
            genVid();
        }
        genRequests(numRequests);
    }

    /**
     * Give the customer list to the manager when asked
     *
     * @return the list of all customers generated
     */
    public LinkedList<Customer> getCusDB() {
        return custDB;
    }

    /**
     * Give the video list to the manager when asked
     *
     * @return the list of all videos generated
     */
    public LinkedList<Video> getVidDB() {
        return vidDB;
    }

    /**
     * Generate a new video
     *
     * @return the generated video
     */
    private Video genVid() {
        Video v = new Video(vid + vidCtr++, "" + vidId++);
        vidDB.add(v);
        return v;
    }

    /**
     * Generate a new customer
     *
     * @return the generated customer
     */
    private Customer genCust() {
        Customer c = new Customer(cust + custCtr++, "" + custId++, list);
        custDB.add(c);
        return c;
    }

    /**
     * Randomly generate requests and place into the "queue"
     *
     * @param numRequests the number of requests to generate
     */
    private void genRequests(int numRequests) {
        //TODO implement the random request part of the generator
        int nextTransaction;
        Customer c;
        Video v;
        for (int i = 0; i < numRequests; i++) {
            nextTransaction = rand.nextInt(8);
            c = custDB.get(requestHelper.nextInt(custDB.size()));
            v = vidDB.get(requestHelper.nextInt(vidDB.size()));
            switch (nextTransaction % 3) {
                //request 6, check out a video
                case 0:
                    q.add("6," + v.getTitle() + "," + v.getId() + "," + c.getName() + "," + c.getId());
                    break;
                //request 7, check in a video
                case 1:
                    q.add("7," + v.getTitle() + "," + v.getId() + "," + c.getName() + "," + c.getId());
                    break;
                //request 5, check if a video is in the store
                case 2:
                    q.add("5," + v.getTitle() + "," + v.getId());
                    break;
            }
        }
    }

    /**
     * Get the requests to process
     *
     * @return the queue of all requests generated
     */
    public LinkedList<String> getRequests() {
        return q;
    }
}
