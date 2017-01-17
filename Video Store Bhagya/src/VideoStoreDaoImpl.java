//package com.videostore.daoimpl;


import java.util.ArrayList;
import java.util.List;

//import com.videostore.dao.VideoStoreDao;
//import com.videostore.datastructures.LinkedList;
//import com.videostore.datastructures.LinkedList.Node;
//import com.videostore.db.VideoStoreDb;
//import com.videostore.model.Video;
//import com.videostore.model.VideoStore;

public class VideoStoreDaoImpl implements VideoStoreDao{
	public LinkedList videoLinkedList;
	private List<Video> videoList;
	public VideoStoreDaoImpl(){
		this.videoLinkedList = new LinkedList();
		videoList = VideoStoreDb.getAllVideos();
		for(int i = 0;i<videoList.size();++i){
			videoLinkedList.add(videoList.get(i));
		}
	}

	@Override
	public boolean checkVideoInStore(String title) {
		if(videoLinkedList.checkInList(title)){
			return true;
		}
		return false;
	}

	@Override
	public boolean checkIn(String title) {	
		if(videoLinkedList.update(title, false)){
			System.out.println("Checked in Successfully!!");
			updateDb();
			return true;
		}
		System.out.println("No Video Record");
		return false;
	}

	@Override
	public boolean checkOut(String title) {	
		if(videoLinkedList.update(title, true)){
			System.out.println("Checked Out Successfully!!");
			updateDb();
			return true;
		}
		System.out.println("No Video Record");
		return false;
	}


	@Override
	public void printListOfAllVideos() {
		LinkedList.Node current = videoLinkedList.head;
		while(current!=null){
			System.out.print(current.getValue().getTitle()+"\t");
			System.out.print(current.getValue().getRentPrice()+"\t");
			if(current.getValue().isOnRent()){
				System.out.println("Not Available");
			}
			else{
				System.out.println("Available");
			}
			current =current.getNext();
		}
	}
	public void updateDb(){
		videoList = new ArrayList<Video>();
		LinkedList.Node current = videoLinkedList.head;
		while(current!=null){
			videoList.add(current.getValue());
			current = current.getNext();
		}
		VideoStore videoStore = VideoStoreDb.readFromDatabase();
		videoStore.setVideos(videoList);
		VideoStoreDb.writeToDatabase(videoStore);
	}
	@Override
	public void printTitlesOfAllVideos() {
		LinkedList.Node current = videoLinkedList.head;
		while(current!=null){
			System.out.print(current.getValue().getTitle()+"\t");
			current =current.getNext();
		}
	}
}
