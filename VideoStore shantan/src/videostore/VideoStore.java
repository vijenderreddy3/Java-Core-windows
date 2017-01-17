
package videostore;

import java.util.LinkedList;
import java.util.Scanner;


public class VideoStore<T extends Structure> {

    private static Scanner scan;
    private static Manager man;
    private static boolean s, d, b, a = false;
    private static Generator gen;
    private static long startTime, endTime, totalTime = 0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 1) {
            //create input scanner
            scan = new Scanner(System.in);
            //use the correct data structure
            switch (args[0]) {
                case "SLL":
                    s = true;
                    break;
                case "DLL":
                    d = true;
                    break;
                case "BST":
                    b = true;
                    break;
                case "AVL":
                    a = true;
                    break;
                default:
                    System.out.println("Please enter a valid datastructure choice (SLL, DLL, BST, or AVL)"
                            + "and run the program again...");
                    System.exit(-1);
                    break;
            }
            //create the manager based on the argument input
            if (s) {
                man = new Manager("s");
            } else if (d) {
                man = new Manager("d");
            } else if (b) {
                man = new Manager("b");
            } else if (a) {
                man = new Manager("a");
            } else {
                System.out.println("Something has gone horribly wrong x(...");
            }
            //print the menu
            printMenu();
            //get user input and interact with the user
            while (true) {
                getInput();
            }
        } else if (args.length == 4) {
            //TODO implement the generation stuff here for no interaction
            //generate all the videos, customers, and requests
            switch (args[0]) {
                case "SLL":
                    s = true;
                    gen = new Generator(Integer.parseInt(args[1]),
                            Integer.parseInt(args[2]), Integer.parseInt(args[3]), "s");
                    break;
                case "DLL":
                    d = true;
                    gen = new Generator(Integer.parseInt(args[1]),
                            Integer.parseInt(args[2]), Integer.parseInt(args[3]), "d");
                    break;
                case "BST":
                    b = true;
                    gen = new Generator(Integer.parseInt(args[1]),
                            Integer.parseInt(args[2]), Integer.parseInt(args[3]), "b");
                    break;
                case "AVL":
                    a = true;
                    gen = new Generator(Integer.parseInt(args[1]),
                            Integer.parseInt(args[2]), Integer.parseInt(args[3]), "a");
                    break;
                default:
                    System.out.println("Please enter a valid datastructure choice (SLL, DLL, BST, or AVL)"
                            + "and run the program again...");
                    System.exit(-1);
                    break;
            }
            //create the manager based on the argument input
            if (s) {
                man = new Manager("s");
            } else if (d) {
                man = new Manager("d");
            } else if (b) {
                man = new Manager("b");
            } else if (a) {
                man = new Manager("a");
            } else {
                System.out.println("Something has gone horribly wrong x(...");
                System.exit(-1);
            }
            //put all the videos, customers, and requests into their own lists
            LinkedList<Video> tempVid = gen.getVidDB();
            while (!tempVid.isEmpty()) {
                man.setVideoInStore(tempVid.removeFirst());
            }
            LinkedList<Customer> tempCust = gen.getCusDB();
            while (!tempCust.isEmpty()) {
                man.setCustomer(tempCust.removeFirst());
            }
            LinkedList<String> requests = gen.getRequests();
            //process requests
            while (!requests.isEmpty()) {
                String r = requests.removeFirst();
                String[] foo = r.split(",");
                //start the timer for each request
                startTime = System.nanoTime();
                switch (foo[0]) {
                    //check if a video is in the store
                    case "5":
                        man.checkIn(new Video(foo[1], foo[2]));
                        break;
                    //check out a video
                    case "6":
                        man.checkOut(new Video(foo[1], foo[2]), new Customer(foo[3], foo[4], ""));
                        break;
                    //check in a video
                    case "7":
                        man.checkIn(new Video(foo[1], foo[2]), new Customer(foo[3], foo[4], ""));
                        break;
                }
                //stop the timer for each request
                endTime = System.nanoTime();
                //add the time for each request to the total time
                totalTime += endTime - startTime;
            }
            if (s) {
                System.out.println("The total time for all requests using the SLL "
                        + "data structure is: " + totalTime + " ns");
            } else if (d) {
                System.out.println("The total time for all requests using the DLL "
                        + "data structure is: " + totalTime + " ns");
            } else if (b) {
                System.out.println("The total time for all requests using the BST "
                        + "data structure is: " + totalTime + " ns");
            } else if (a) {
                System.out.println("The total time for all requests using the AVL "
                        + "data structure is: " + totalTime + " ns");
            } else {
                System.out.println("Something has gone horribly wrong in the "
                        + "automatic generation x(...");
                System.exit(-1);
            }
        } else {
            System.out.println("A strange amount of arguments were passed to the program :(...");
            System.exit(-1);
        }
    }

    public static void printMenu() {
        System.out.println("=================================================");
        System.out.println("Select one of the following:");
        System.out.println("1: To add a video");
        System.out.println("2: To delete a video");
        System.out.println("3: To add a customer");
        System.out.println("4: To delete a customer");
        System.out.println("5: To check if a particular video is in store");
        System.out.println("6: To check out a video");
        System.out.println("7: To check in a video");
        System.out.println("8: To print all customers");
        System.out.println("9: To print all videos");
        System.out.println("10: To print in store videos");
        System.out.println("11: To print all rented videos");
        System.out.println("12: To print the videos rented by a customer");
        System.out.println("13: To exit");
        System.out.println("=================================================");
    }

    public static void getInput() {
        int menuChoice = scan.nextInt();
        String menuInput = "";
        String[] info;
        Video newVid = new Video(null, null);
        Customer newCust = new Customer(null, null, "");
        switch (menuChoice) {
            //add a video
            case 1:
                System.out.println("Please enter the Title and ID of the video to add, separated by a comma:");
                menuInput = scan.next();
                info = menuInput.split(",");
                if (info.length > 2) {
                    System.out.println("Videos only have a title and an id...");
                    printMenu();
                    break;
                } else if (info.length < 2) {
                    System.out.println("Not enough information has been entered...");
                    printMenu();
                    break;
                }
                newVid = new Video(info[0], info[1]);
                System.out.println("Adding " + newVid.toString() + " to in store videos");
                //add the newVid to in store video list
                man.setVideoInStore(newVid);
                //show the main menu
                printMenu();
                break;
            //delete a video
            case 2:
                System.out.println("Please enter the Title and ID of the video to delete, separated by a comma:");
                menuInput = scan.next();
                info = menuInput.split(",");
                if (info.length > 2) {
                    System.out.println("Videos only have a title and an id...");
                    printMenu();
                    break;
                } else if (info.length < 2) {
                    System.out.println("Not enough information has been entered...");
                    printMenu();
                    break;
                }
                newVid = new Video(info[0], info[1]);
                //delete the video from the video list
                if (man.checkIn(newVid)) {
                    man.deleteVideo(newVid);
                }
                //show the main menu
                printMenu();
                break;
            //add a customer
            case 3:
                System.out.println("Please enter the Name and ID of the customer, separated by a comma:");
                menuInput = scan.next();
                info = menuInput.split(",");
                if (info.length > 2) {
                    System.out.println("Customers only have a name and an id...");
                    printMenu();
                    break;
                } else if (info.length < 2) {
                    System.out.println("Not enough information has been entered...");
                    printMenu();
                    break;
                }
                if (a) {
                    newCust = new Customer(info[0], info[1], "a");
                } else if (d) {
                    newCust = new Customer(info[0], info[1], "d");
                } else if (b) {
                    newCust = new Customer(info[0], info[1], "b");
                } else {
                    newCust = new Customer(info[0], info[1], "s");
                }
                //add newCust to customer list
                man.setCustomer(newCust);
                //show the main menu
                printMenu();
                break;
            //delete a customer
            case 4:
                System.out.println("Please enter the Name and ID of the customer, separated by a comma:");
                menuInput = scan.next();
                info = menuInput.split(",");
                if (info.length > 2) {
                    System.out.println("Customers only have a name and an id...");
                    printMenu();
                    break;
                } else if (info.length < 2) {
                    System.out.println("Not enough information has been entered...");
                    printMenu();
                    break;
                }
                //delete the customer from the customer list
                newCust = new Customer(info[0], info[1], "");
                if (man.check(newCust)) {
                    man.deleteCustomer(newCust);
                }
                //show the main menu
                printMenu();
                break;
            //check if a video is in the store
            case 5:
                System.out.println("Please enter the Title and ID of the video to check for, separated by a comma:");
                menuInput = scan.next();
                info = menuInput.split(",");
                if (info.length > 2) {
                    System.out.println("Videos only have a title and an id...");
                    printMenu();
                    break;
                } else if (info.length < 2) {
                    System.out.println("Not enough information has been entered...");
                    printMenu();
                    break;
                }
                newVid = new Video(info[0], info[1]);
                //print the value returned by the checkIn() method in the manager
                System.out.println(man.checkIn(newVid));
                //show the main menu
                printMenu();
                break;
            //check out a video
            case 6:
                System.out.println("Please enter the Title and ID of the video to check out and the name and id of the customer, separated by commas:");
                menuInput = scan.next();
                info = menuInput.split(",");
                if (info.length > 4) {
                    System.out.println("Too much information has been entered...");
                    printMenu();
                    break;
                } else if (info.length < 4) {
                    System.out.println("Not enough information has been entered...");
                    printMenu();
                    break;
                }
                newVid = new Video(info[0], info[1]);
                //get the customer from the list of customers
                newCust = man.getCust(new Customer(info[2], info[3], ""));
                //perform checkout routine for the video
                man.checkOut(newVid, newCust);
                //show the main menu
                printMenu();
                break;
            //check in a video
            case 7:
                System.out.println("Please enter the Title and ID of the video to check in and the name and id of the customer, separated by commas:");
                menuInput = scan.next();
                info = menuInput.split(",");
                if (info.length > 4) {
                    System.out.println("Too much information has been entered...");
                    printMenu();
                    break;
                } else if (info.length < 4) {
                    System.out.println("Not enough information has been entered...");
                    printMenu();
                    break;
                }
                //create video from supplied info
                newVid = new Video(info[0], info[1]);
                //grab the customer from the customers list
                newCust = man.getCust(new Customer(info[2], info[3], ""));
                //perform check in routing
                man.checkIn(newVid, newCust);
                //show the main menu
                printMenu();
                break;
            //print all customers
            case 8:
                //print out all the customers
                man.printAllCust();
                //show the main menu
                printMenu();
                break;
            //print all videos
            case 9:
                //print out all videos (in store and rented)
                man.printAllVid();
                //show the main menu
                printMenu();
                break;
            //print all videos in the store
            case 10:
                //print out all in store videos
                man.printVidInStore();
                //show the main menu
                printMenu();
                break;
            //print all rented videos
            case 11:
                //print out all rented videos
                man.printVidOut();
                //show the main menu
                printMenu();
                break;
            //print all videos rented by a customer
            case 12:
                System.out.println("Please enter the Name and ID of the customer, separated by a comma:");
                menuInput = scan.next();
                info = menuInput.split(",");
                if (info.length > 2) {
                    System.out.println("Customers only have a name and an id...");
                    printMenu();
                    break;
                } else if (info.length < 2) {
                    System.out.println("Not enough information has been entered...");
                    printMenu();
                    break;
                } else if (!man.check(new Customer(info[0], info[1], ""))) {
                    System.out.println("That customer does not exist in the database...");
                    printMenu();
                    break;
                } else {
                    //get the rented videos structure held by the customer information entered
                    Structure struc = man.getCust(new Customer(info[0], info[1], "")).getRentVideo();
                    if (struc == null) {
                        System.out.println("Not sure what happened here...");
                        printMenu();
                        break;
                    }
                    struc.print();
                    //show the main menu
                    printMenu();
                    break;
                }
            //exit
            case 13:
                //close scanner
                scan.close();
                //print goodbye message
                System.out.println("Goobye :)");
                //exit
                System.exit(0);
                break;
            default:
                //ask for a valid choice
                System.out.println("Please enter a valid number choice... :(");
                printMenu();
                break;
        }
    }
}
