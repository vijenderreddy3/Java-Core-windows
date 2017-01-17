//package com.videostore.daoimpl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

//import com.videostore.dao.CustomerDao;
//import com.videostore.dao.VideoStoreDao;
//import com.videostore.db.VideoStoreDb;
//import com.videostore.model.Customer;
//import com.videostore.model.VideoStore;

public class CustomerDaoImpl implements CustomerDao{
	
	@Override
	public void processUnprocessedCustomers(int n) {
		List<Customer> customerList = VideoStoreDb.getAllCustomerToBeProcessed();
		Queue<Customer> queue = new LinkedList<Customer>();
		Random randomNumber = new Random();
		int i=0;
		while(!customerList.isEmpty() && i<n){
			int num = randomNumber.nextInt(customerList.size());	//generate random index
			queue.add(customerList.get(num));	//add a random customer to queue
			customerList.remove(num);	//remove customer from list
			++i;
		}
		processCustomers(queue);
		
	}
	public void processCustomers(Queue<Customer> queue){
		Customer customer;
		List<Customer> customerList = new ArrayList<Customer>();
		VideoStoreDao videoStoreDao = new VideoStoreDaoImpl();
		List<String> rentVideoList = new ArrayList<String>();
		List<String> requestVideoList = new ArrayList<String>();
		while(!queue.isEmpty()){
			customer = queue.poll();
			System.out.println("Customer Name: "+customer.getName());
			
			rentVideoList = customer.getRentVideoList();
			requestVideoList = customer.getRequestVideoList();
			for(int i=0;i<rentVideoList.size();++i){
				System.out.println("Customer Request List: "+ rentVideoList.get(i)+" is cheking out");
				videoStoreDao.checkIn(rentVideoList.get(i));
			}
			rentVideoList = new ArrayList<String>();
			for(int i=0;i<requestVideoList.size();++i){
				String title = requestVideoList.get(i);
				System.out.println("Customer Request List: "+title);
				if(videoStoreDao.checkVideoInStore(title)){
					System.out.println("Video is available");
					videoStoreDao.checkOut(title);
				}
				else{
					System.out.println("No Video with name "+title+" is available in the store");
				}
			}
			requestVideoList = new ArrayList<String>();
			customer.setRentVideoList(rentVideoList);
			customer.setRequestVideoList(requestVideoList);
			customerList.add(customer);
		}
		VideoStore videoStore = VideoStoreDb.readFromDatabase();
		videoStore.setCustomers(customerList);
		VideoStoreDb.writeToDatabase(videoStore);
	}
	
}
