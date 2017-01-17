//package com.videostore.daoimpl;

import java.util.ArrayList;
import java.util.List;

//import com.videostore.dao.VideoStoreDao;
//import com.videostore.datastructures.AVLTree;
//import com.videostore.db.VideoStoreDb;
//import com.videostore.model.Video;
//import com.videostore.model.VideoStore;

public class VideoStoreDaoImplAVL implements VideoStoreDao{
	public AVLTree avl;
	private List<Video> videoList;
	public VideoStoreDaoImplAVL(){
		this.avl = new AVLTree();
		videoList = VideoStoreDb.getAllVideos();
		Video video;
		for(int i = 0;i<videoList.size();++i){
			video = videoList.get(i);
			avl.insert(video.getTitle(), video);
		}
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
		if(avl.checkVideo(title)){
			return true;
		}
		return false;
	}

	@Override
	public boolean checkIn(String title) {
		Video video = getVideoObject(title);
		if(video!=null){
			video.setOnRent(false);
			avl.insert(title, video);
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
			avl.insert(title, video);
			updateDb();
			return true;
		}
		return false;
	}

	@Override
	public void printListOfAllVideos() {	
		avl.inorder(2);
		
	}

	@Override
	public void printTitlesOfAllVideos() {
		avl.inorder(1);
		
	}
	public void updateDb(){
		videoList = new ArrayList<Video>();
		videoList = avl.getInList();
		VideoStore videoStore = VideoStoreDb.readFromDatabase();
		videoStore.setVideos(videoList);
		VideoStoreDb.writeToDatabase(videoStore);
	}

}
