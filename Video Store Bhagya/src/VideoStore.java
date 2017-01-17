//package com.videostore.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("serial")
public class VideoStore implements Serializable{
	private List<Video> videos;
	private List<Customer> customers;
	public VideoStore(){
		videos = new LinkedList<Video>();
		customers = new LinkedList<Customer>();
	}
	public List<Video> getVideos() {
		return videos;
	}
	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}
	public List<Customer> getCustomers() {
		return customers;
	}
	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
	
}
