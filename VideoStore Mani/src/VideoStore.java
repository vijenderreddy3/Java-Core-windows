
import java.util.Scanner;

public class VideoStore {

    /**
     * Videos in store
     */
    private static DataStructure<Video> videoInStore;

    /**
     * Customers booking videos from store
     */
    private static DataStructure<Customer> customers;

    /**
     * Data type AVL, BST, SLL or DLL
     */
    private static String dataType;

    /**
     * Set Video Data Structure Type
     */
    private static void setVideoInStore() {
        if (dataType.equals("AVL")) {
            videoInStore = new AVL<Video>();
        }
        else if (dataType.equals("BST")) {
            videoInStore = new BST<Video>();

        }
        else if (dataType.equals("DLL")) {
            videoInStore = new DLL<Video>();

        }
        else if (dataType.equals("SLL")) {
            videoInStore = new SLL<Video>();
        }
    }

    /**
     * Set Customer Data Structure Type
     */
    private static void setCustomer() {
        if (dataType.equals("AVL")) {
            customers = new AVL<Customer>();
        }
        else if (dataType.equals("BST")) {
            customers = new BST<Customer>();

        }
        else if (dataType.equals("DLL")) {
            customers = new DLL<Customer>();

        }
        else if (dataType.equals("SLL")) {
            customers = new SLL<Customer>();
        }
    }

    /**
     * Add new Video to Store
     */
    private static void addVideo() {
        Video video;
        Scanner input = new Scanner(System.in);
        //ask user for video info
        //ask for id
        System.out.println("Enter video id");
        String id = input.nextLine();
        video = new Video(id);
        //id there is video with same id, show error and repeat
        while (videoInStore.search(video) != null) {
            System.out.println("Error: video exist with same id.");
            //ask for new video id or -1 to cancel operation
            System.out.println("Enter video id or -1 to return:");
            id = input.nextLine();
            if (id.equals("-1")) // if-1, exit method (cancel operation)
                return;
            video = new Video(id);
        }
        //ask for video title
        System.out.println("Enter video title");
        String title = input.nextLine();
        video.setTitle(title);
        //add to data structure
        videoInStore.insert(video);
        System.out.println("Video added successfully.");
    }

    /**
     * Delete video from store
     */
    private static void deleteVideo() {
        Video video;
        Scanner input = new Scanner(System.in);
        //ask for video id
        System.out.println("Enter video id");
        String id = input.nextLine();
        video = new Video(id);
        //if no video with id exist in store, show error and ask again
        while (videoInStore.search(video) == null) {
            System.out.println("Error: video doesn't exist.");
            System.out.println("Enter video id or -1 to return:"); // take another id or exit
            id = input.nextLine();
            if (id.equals("-1")) // if -1 then exit method (cancel operation)
                return;
            video = new Video(id);
        }
        //remove from store
        videoInStore.remove(video);
        System.out.println("Video removed successfully.");
    }

    /**
     * Add customer to store
     */
    private static void addCustomer() {
        Customer customer;
        Scanner input = new Scanner(System.in);
        //ask for customer id
        System.out.println("Enter customer id:");
        String id = input.nextLine();
        customer = new Customer(id, dataType);
        //if there is customer with same id, then show eror and take another id
        while (customers.search(customer) != null) {
            System.out.println("Error: customer exist with same id.");
            System.out.println("Enter customer id or -1 to return:"); // ask for another id or -1 to cancel operation
            id = input.nextLine();
            if (id.equals("-1"))// if -1 cancel add operation and return
                return;
            customer = new Customer(id, dataType);
        }
        //ask for customer name
        System.out.println("Enter customer name");
        String name = input.nextLine();
        customer.setName(name);
        //insert customer to store
        customers.insert(customer);
        System.out.println("Customer added successfully.");
    }

    /**
     * Remove customer from store
     */
    private static void deleteCustomer() {
        Customer customer;
        ///ask for customer id
        Scanner input = new Scanner(System.in);
        System.out.println("Enter customer id:");
        String id = input.nextLine();
        customer = new Customer(id, dataType);
        //if no customer with id exist, show error and take again
        while (customers.search(customer) == null) {
            System.out.println("Error: customer doesn't exist.");
            System.out.println("Enter customer id or -1 to return:");
            id = input.nextLine();
            if (id.equals("-1"))
                return;
            customer = new Customer(id, dataType);
        }
        //remove customer
        customers.remove(customer);
        System.out.println("Customer removed successfully.");
    }

    /**
     * Check if certain video exist in store
     *
     * @return
     */
    private static boolean check() {
        Video video;
        //ask for video id
        Scanner input = new Scanner(System.in);
        System.out.println("Enter video id");
        String id = input.nextLine();
        video = new Video(id);
        //if not exist, show message and return
        if (videoInStore.search(video) == null) {
            System.out.println("Video is not in store.");
            return false;
        }
        //if exist but rent show it's not in store
        if (videoInStore.search(video).isRent()) {
            System.out.println("Video is not in store.");
            return false;
        }
        //if exist and not rent show message
        System.out.println("Video is in store.");
        return true;
    }

    /**
     * Customer checkIn a video, return true if customer id and video id exist
     * and valid. otherwise false
     *
     * @return boolean
     */
    private static boolean checkOut() {
        Customer customer;
        //ask for customer id
        Scanner input = new Scanner(System.in);
        System.out.println("Enter customer id:");
        String customerID = input.nextLine();
        customer = new Customer(customerID, dataType);
        //if no customer with same id exist, show error and ask again
        while (customers.search(customer) == null) {
            System.out.println("Error: customer doesn't exist.");
            System.out.println("Enter customer id or -1 to return:");
            customerID = input.nextLine();
            if (customerID.equals("-1"))
                return false;
            customer = new Customer(customerID, dataType);
        }
        Video video;
        //ask for video id
        System.out.println("Enter video id");
        String videoID = input.nextLine();
        video = new Video(videoID);
        //if no customer with same id, show error and return
        while (videoInStore.search(video) == null || videoInStore.search(video).isRent()) {
            System.out.println("Error: video doesn't exist or already rented.");
            System.out.println("Enter video id or -1 to return:");
            videoID = input.nextLine();
            if (videoID.equals("-1"))
                return false;
            video = new Video(videoID);
        }
        //set this video to be rent
        videoInStore.search(video).setRent(true);
        //add the video to customer rented videos
        customers.search(customer).addRentVideo(videoInStore.search(video));
        //show message
        System.out.println("Customer rented video successfully.");
        return true;
    }

    /**
     * Check out a video from customer and return to store. return true if
     * customer rented video and valid, otherwise false
     *
     * @return boolean
     */
    private static boolean checkIn() {
        Customer customer;
        Scanner input = new Scanner(System.in);
        //ask for customer id
        System.out.println("Enter customer id:");
        String customerID = input.nextLine();
        customer = new Customer(customerID, dataType);
        //if no customer with this id exist, show error and ask again
        while (customers.search(customer) == null) {
            System.out.println("Error: customer doesn't exist.");
            System.out.println("Enter customer id or -1 to return:");
            customerID = input.nextLine();
            if (customerID.equals("-1"))
                return false;
            customer = new Customer(customerID, dataType);
        }
        //ask for video id
        Video video;
        System.out.println("Enter video id");
        String videoID = input.nextLine();
        video = new Video(videoID);
        //if no video with same id exist, show error and ask again
        while (customers.search(customer).getRentVideo().search(video) == null) {
            System.out.println("Error: Customer didn't rent this video.");
            System.out.println("Enter video id or -1 to return:");
            videoID = input.nextLine();
            if (videoID.equals("-1"))
                return false;
            video = new Video(videoID);
        }
        //get the customer rented videos and remove the video from it
        customers.search(customer).removeRentVideo(video);
        //set the video in store to be not rented
        videoInStore.search(video).setRent(false);
        //show message
        System.out.println("Customer returned video to store successfully.");
        return true;
    }

    /**
     * Print all customers in store
     */
    private static void printAllCustomers() {
        String output = customers.toString();
        //is empty, show message
        if (output.isEmpty())
            System.out.println("No customers in video store.");
        //otherwise print result
        else
            System.out.println(output);
    }

    /**
     * Print all videos in store
     */
    private static void printAllVideos() {
        String output = videoInStore.toString();
        //if empty, show message
        if (output.isEmpty())
            System.out.println("No videos in video store");
        //otherwise print result
        else
            System.out.println(output);
    }

    /**
     * Print all videos in store but only not rented ones
     */
    private static void printInStoreVideos() {
        //get all videos in store and put in array
        String[] inStoreVds = videoInStore.toString().split(",");
        //create data structure with same type and add the not rented videos only to it
        DataStructure<Video> other = null;
        if (dataType.equals("AVL")) {
            other = new AVL<Video>();
        }
        else if (dataType.equals("BST")) {
            other = new BST<Video>();

        }
        else if (dataType.equals("DLL")) {
            other = new DLL<Video>();

        }
        else {
            other = new SLL<Video>();
        }
        //add not rented videos
        for (int i = 0; i < inStoreVds.length; i++) {
            String[] token = inStoreVds[i].split(":");
            String id = token[0].trim();
            String title = token[1].trim();
            Video video = new Video(title, id);
            //if video not rented, then add it
            if (!videoInStore.search(video).isRent()) {
                other.insert(video);
            }
        }
        //if no videos added, show message
        if (other.toString().isEmpty()) {
            System.out.println("There is no videos in store");
        }
        //if there is video in store and not rented, print them
        else {
            System.out.println("In store videos: ");
            System.out.println(other.toString());
        }
    }

    /**
     * Print all videos rented by customers
     */
    private static void printAllRentVideos() {
        //get all videos in store and put in array
        String[] inStoreVds = videoInStore.toString().split(",");
        //create data structure with same type and add rented videos to it
        DataStructure<Video> other = null;
        if (dataType.equals("AVL")) {
            other = new AVL<Video>();
        }
        else if (dataType.equals("BST")) {
            other = new BST<Video>();

        }
        else if (dataType.equals("DLL")) {
            other = new DLL<Video>();

        }
        else {
            other = new SLL<Video>();
        }
        //go over all videos
        for (int i = 0; i < inStoreVds.length; i++) {
            String[] token = inStoreVds[i].split(":");
            String id = token[0].trim();
            String title = token[1].trim();
            Video video = new Video(title, id);
            //if video is rented
            if (videoInStore.search(video).isRent()) {
                other.insert(video);
            }
        }
        //if no rented videos show message
        if (other.toString().isEmpty()) {
            System.out.println("There is no rented videos");
        }
        //if there is rented videos, print them
        else {
            System.out.println("Rent videos: ");
            System.out.println(other.toString());
        }
    }

    /**
     * Print videos rented by certain customer
     */
    private static void printVideoRentByACustomer() {
        Customer customer;
        Scanner input = new Scanner(System.in);
        //get customer id
        System.out.println("Enter customer id:");
        String id = input.nextLine();
        customer = new Customer(id, dataType);
        //if no customer with this id, show error and ask again
        while (customers.search(customer) == null) {
            System.out.println("Error: no customer with this id.");
            System.out.println("Enter customer id or -1 to return:");
            id = input.nextLine();
            if (id.equals("-1"))
                return;
            customer = new Customer(id, dataType);
        }
        //if there is no videos rented by this customer show message
        if (customers.search(customer).getRentVideo().toString().isEmpty()) {
            System.out.println("Customer didn't rent any videos");
        }
        //if there is videos rented by this client, print them
        else {
            System.out.println("Customer Rented Videos:");
            System.out.println(customers.search(customer).getRentVideo().toString());
        }
    }

    /**
     * Print menu
     */
    public static void menu() {
        System.out.println("");
        System.out.println("##################################################");
        System.out.println("1.  Add a video ");
        System.out.println("2.  Delete a video ");
        System.out.println("3.  Add a customer");
        System.out.println("4.  Delete a customer");
        System.out.println("5.  Check if a particular video is in store");
        System.out.println("6.  Check out a video ");
        System.out.println("7.  Check in a video");
        System.out.println("8.  Print all customers");
        System.out.println("9.  Print all videos");
        System.out.println("10. Print in store videos");
        System.out.println("11. Print all rent videos ");
        System.out.println("12. Print the videos rent by a customer");
        System.out.println("13. Exit");
        System.out.print(">>>>>>>>>>> ");
    }

    /**
     * Main method to start the application. <br>
     * The Data structure type is sent by args, if no args sent the program will
     * consider SLL as default data structure to use
     *
     * @param args String[]
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            dataType = "AVL";
        }
        else {
            //if wrong data structure send, show message and end program
            if (!args[0].equals("AVL") && !args[0].equals("BST") && !args[0].equals("DLL") && !args[0].equals("SLL")) {
                System.out.println("Argument sent must be AVL, BST, DLL or SLL only");
                System.exit(0);
            }
            //otherwise set data type
            dataType = args[0];

        }
        //set data structure for videos and customers
        setVideoInStore();
        setCustomer();
        //read user choices
        Scanner input = new Scanner(System.in);
        int option = 0;
        while (option != 13) {
            menu(); // show menu
            option = input.nextInt(); // read option
            //switch option
            switch (option) {
                case 1: // if add video
                    addVideo();
                    break;
                case 2: // if delete video
                    deleteVideo();
                    break;
                case 3: // if add customer
                    addCustomer();
                    break;
                case 4: // if delete customer
                    deleteCustomer();
                    break;
                case 5: // if check that video exist in store
                    check();
                    break;
                case 6: // if check out a video to customer
                    checkOut();
                    break;
                case 7: // if check in video from customer
                    checkIn();
                    break;
                case 8: // if print all customers
                    printAllCustomers();
                    break;
                case 9: // if print all videos
                    printAllVideos();
                    break;
                case 10: // if print not rented videos
                    printInStoreVideos();
                    break;
                case 11: // if print rented videos
                    printAllRentVideos();
                    break;
                case 12: // if print customer rented videos
                    printVideoRentByACustomer();
                    break;
                case 13: // if exit
                    System.out.println("Goodbye");
                    break;
                default: // if invalid option
                    System.out.println("Error: not valid option.");
            }
        }
    }

}
