//package com.videostore.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

//import com.videostore.model.Customer;
//import com.videostore.model.Video;
//import com.videostore.model.VideoStore;

public class VideoStoreDb {
	public static final String fileName = "videostore.ser";
	
	public static boolean writeToDatabase(VideoStore videoStore){
		File file = null;
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try{
			file = new File(fileName);
			if(!file.exists()){
				file.createNewFile();
			}
			fos = new FileOutputStream(file);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(videoStore);
			fos.close();
			oos.close();
			System.out.println("Data inserted into the database successfully..!!");
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
			System.err.println("Writing to Database Failed..!!");
			return false;
		}
	}
	
	public static VideoStore readFromDatabase(){
		File file = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try{
			file = new File(fileName);
			if(!file.exists()){
				return null;
			}
			fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);
			VideoStore videoStore = (VideoStore)ois.readObject();
			System.out.println("Reading from Database was successfully Done..!!");
			return videoStore;
		}catch(Exception ex){
			ex.printStackTrace();
			System.err.println("Reading from Database Failed..!!");
			return null;
		}
	}
	
	public static void createSomeData(){
		VideoStore videoStore = new VideoStore();
		List<Video> videolist = new ArrayList<Video>();
		videolist.add(new Video("Harry Potter",false, 100.00));
		videolist.add(new Video("Spider Man",true, 200.00));
		videolist.add(new Video("Iron Man",false, 150.00));
		videolist.add(new Video("Millon Dollar Arm",false, 200.00));
		List<Customer> customerList = new ArrayList<Customer>();
		ArrayList<String> rentVideoList = new ArrayList<String>();
		rentVideoList.add("Harry Potter");
		rentVideoList.add("Spider Man");
		ArrayList<String> requestVideoList = new ArrayList<String>();
		requestVideoList.add("Iron Man");
		customerList.add(new Customer("Sam",rentVideoList, requestVideoList, 100.00));
		videoStore.setVideos(videolist);
		videoStore.setCustomers(customerList);
		writeToDatabase(videoStore);
	}
	
	public static void displayData(){
		VideoStore videoStore = readFromDatabase();
		List<Video> videolist = new ArrayList<Video>();
		videolist = videoStore.getVideos();
		for(Video video : videolist){
			System.out.print(video.getTitle()+"\t");
			System.out.print(video.getRentPrice()+"\t");
			System.out.println(video.isOnRent());
		}
		List<Customer> customerList = new ArrayList<Customer>();
		customerList = videoStore.getCustomers();
		for(Customer customer: customerList){
			System.out.println(customer.getName());
			System.out.println(customer.getRentVideoList().size());
			System.out.println(customer.getRequestVideoList().size());
		}
	}
	
	public static void main(String[] args){
		createSomeData();
		displayData();
	}
	
	public static List<Video> getAllVideos(){
		VideoStore videoStore = readFromDatabase();
		return videoStore.getVideos();
	}
	
	public static List<Customer> getAllCustomerToBeProcessed(){
		VideoStore videoStore = readFromDatabase();
		return videoStore.getCustomers();
	}
}
