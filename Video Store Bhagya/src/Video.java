//package com.videostore.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Video implements Serializable{
	private String title;
	private boolean onRent;
	private double rentPrice;
	public Video(String title, boolean onRent, double rentPrice){
		this.title = title;
		this.onRent = onRent;
		this.rentPrice = rentPrice;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean isOnRent() {
		return onRent;
	}
	public void setOnRent(boolean onRent) {
		this.onRent = onRent;
	}
	public double getRentPrice() {
		return rentPrice;
	}
	public void setRentPrice(double rentPrice) {
		this.rentPrice = rentPrice;
	}
	
}
