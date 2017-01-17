//package com.videostore.daoimpl;

import java.util.ArrayList;
import java.util.List;

//import com.videostore.dao.VideoStoreDao;
//import com.videostore.datastructures.BinarySearchTree;
//import com.videostore.db.VideoStoreDb;
//import com.videostore.model.Video;
//import com.videostore.model.VideoStore;

public class VideoStoreDaoImplBST implements VideoStoreDao{
	public BinarySearchTree bst;
	private List<Video> videoList;
	public VideoStoreDaoImplBST(){
		this.bst = new BinarySearchTree();
		videoList = VideoStoreDb.getAllVideos();
		Video video;
		for(int i = 0;i<videoList.size();++i){
			video = videoList.get(i);
			bst.put(video.getTitle(), video);
		}
		System.out.println("Size of bst"+bst.size());
	}
	
	public Video getVideoObject(String title){
		for(Video v: videoList){
			if(v.getTitle().equalsIgnoreCase(title)){
				return v;
			}
		}
		return null;
	}
	@Override
	public boolean checkVideoInStore(String title) {
		if(bst.checkIfPresent(title)){
			return true;
		}
		return false;
	}
	@Override
	public boolean checkIn(String title) {
		Video video = getVideoObject(title);
		if(video!=null){
			video.setOnRent(false);
			bst.put(title, video);
			updateDb();
			return true;
		}
		return false;
	}
	@Override
	public boolean checkOut(String title) {
		Video video = getVideoObject(title);
		if(video!=null){
			video.setOnRent(true);
			bst.put(title, video);
			updateDb();
			return true;
		}
		return false;
	}

	@Override
	public void printListOfAllVideos() {
		this.bst.inOrder(2);
		
	}
	@Override
	public void printTitlesOfAllVideos() {
		this.bst.inOrder(1);
		
	}
	public void updateDb(){
		videoList = new ArrayList<Video>();
		videoList = bst.getInList();
		VideoStore videoStore = VideoStoreDb.readFromDatabase();
		videoStore.setVideos(videoList);
		VideoStoreDb.writeToDatabase(videoStore);
	}
}
