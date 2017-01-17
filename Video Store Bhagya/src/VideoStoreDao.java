//package com.videostore.dao;


public interface VideoStoreDao {
	public boolean checkVideoInStore(String title);
	public boolean checkIn(String title);
	public boolean checkOut(String title);
	public void printListOfAllVideos();
	public void printTitlesOfAllVideos();
}
