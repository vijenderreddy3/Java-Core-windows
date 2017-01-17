//package com.videostore.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

//import com.videostore.dao.CustomerDao;
//import com.videostore.dao.VideoStoreDao;
//import com.videostore.daoimpl.CustomerDaoImpl;
//import com.videostore.daoimpl.VideoStoreDaoImpl;
//import com.videostore.daoimpl.VideoStoreDaoImplAVL;
//import com.videostore.daoimpl.VideoStoreDaoImplBST;

public class VideoStoreMain {
	public static void main(String[] args){
		System.out.println("Welcome to Video Store");
		VideoStoreDao videoStoreDao = null;
		List<Video> videolist = new ArrayList<Video>();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter which Datastruture to Use");
		System.out.println("1. Linked List");
		System.out.println("2. Binary Search Tree");
		System.out.println("3. AVL");
		String dataStrutureString = "1";
		int dataStructure =1;
		try {
			dataStrutureString = br.readLine();
		} catch (IOException e1) {
			System.out.println("Please enter a valid entry");
		}
		dataStructure = Integer.parseInt(dataStrutureString);
		if(dataStructure==1){
			System.out.println("You have Choosen LINKED LIST");
			videoStoreDao = new VideoStoreDaoImpl();
		}
		else if(dataStructure==2){
			System.out.println("You have Choosen BST");
			videoStoreDao = new VideoStoreDaoImplBST();
		}
		else if(dataStructure==3){
			videoStoreDao = new VideoStoreDaoImplAVL();
			System.out.println("You have Choosen AVL");
		}
		String title = "";
		String videoIn="";
		int choice;
		do{
			
			System.out.println("\nPlease Enter Your choice");
			System.out.println("1. Check If The Video Is Prensent Or Not");
			System.out.println("2. Check Out the Video");
			System.out.println("3. Check In the Video");
			System.out.println("4. Print The titles of the Videos");
			System.out.println("5. Print List of All Videos");
			System.out.println("6. Process Pending Customers");
			System.out.println("7. Exit");
			System.out.println("8.Add video");
			String chc="8";
			try {
				chc = br.readLine();
			} catch (IOException e) {
				System.out.println("Please enter your choice");
			}
			choice = Integer.parseInt(chc);
			switch(choice){
			case 1:
				System.out.println("Check If The Video Is Prensent Or Not");
				System.out.println("Enter the title of the Video");
				try {
					title = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("Please enter the title correclty");
				}
				if(videoStoreDao.checkVideoInStore(title)){
					System.out.println("Video is Available");
				}
				else{
					System.out.println("Video is not available");
				}
				break;
			case 2:
				System.out.println("Check Out the Video");
				System.out.println("Enter the title of the Video");
				try {
					title = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("Please enter the title correclty");
				}
				videoStoreDao.checkOut(title);
				break;
			case 3:
				System.out.println("Check In the Video");
				System.out.println("Enter the title of the Video");
				try {
					title = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("Please enter the title correclty");
				}
				videoStoreDao.checkIn(title);
				break;
			case 4:
				System.out.println("Print The titles of the Videos");
				videoStoreDao.printTitlesOfAllVideos();
				break;
			case 5:
				System.out.println("Print List of All Videos");
				videoStoreDao.printListOfAllVideos();
				break;
			case 6:
				System.out.println("Processing Customers");
				CustomerDao customerDao = new CustomerDaoImpl();
				customerDao.processUnprocessedCustomers(4);
				break;
			case 7:
				System.out.println("!! Thank You !!");
				break;
			case 8:
				System.out.println("Enter Video Name");
				try{
				videoIn=br.readLine();
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("Please enter the title correclty");
				}
				videolist.add(new Video(videoIn,false, 100.00));
				break;
			}
		}while(choice!=7);
	}
}
