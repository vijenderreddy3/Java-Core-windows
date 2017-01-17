//package com.videostore.model;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Customer  implements Serializable{
	private String name;
	private List<String> rentVideoList;
	private List<String> requestVideoList;
	private double pendingMoney;
	public Customer(String name, List<String> rentVideoList,List<String> requestVideoList,double pendingMoney){ 
		this.name = name;
		this.rentVideoList =rentVideoList;
		this.pendingMoney = pendingMoney;
		this.requestVideoList = requestVideoList;
	}
	
	public List<String> getRequestVideoList() {
		return requestVideoList;
	}

	public void setRequestVideoList(List<String> requestVideoList) {
		this.requestVideoList = requestVideoList;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getRentVideoList() {
		return rentVideoList;
	}
	public void setRentVideoList(List<String> rentVideoList) {
		this.rentVideoList = rentVideoList;
	}
	public double getPendingMoney() {
		return pendingMoney;
	}
	public void setPendingMoney(double pendingMoney) {
		this.pendingMoney = pendingMoney;
	}
	
}
