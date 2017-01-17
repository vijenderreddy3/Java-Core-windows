//package com.videostore.datastructures;

//import com.videostore.model.Video;

public class LinkedList{
	public class Node{
		private Video value;
		private Node next;
		public Node(){
			
		}
		public Node(Video value){
			this.value = value;
			this.next = null;
		}
		public Video getValue() {
			return value;
		}
		public void setValue(Video value) {
			this.value = value;
		}
		public Node getNext() {
			return next;
		}
		public void setNext(Node next) {
			this.next = next;
		}
		
	}
	public Node head;
	public int size = 0;
	
	public int getSize(){
		return this.size;
	}
	
	public boolean add(Video value){
		Node newNode = new Node(value);
		if(head==null){
			head = newNode;
			return true;
		}
		else{
			Node current = new Node();
			current = head;
			while(current.getNext()!=null){
				current = current.getNext();
			}
			current.setNext(newNode);
			size++;
			return true;
		}
	}
	
	public boolean update(String title, boolean onRent){
		if(head==null){
			System.out.println("Noting to update");
			return false;
		}
		else{
			Node current = new Node();
			current = head;
			while(current!=null){
				if(current.getValue().getTitle().equalsIgnoreCase(title)){
					current.getValue().setOnRent(onRent);
					System.out.println("Updated Successfully!!");
					return true;
				}
				current = current.getNext();
			}
			System.out.println("No Video with such name");
		}
		return false;
	}
	
	public boolean checkInList(String title){
		if(head==null){
			return false;
		}
		else{
			Node current = new Node();
			current = head;
			while(current!=null){
				if(current.getValue().getTitle().equalsIgnoreCase(title)){
					if(!current.getValue().isOnRent()){
						return true;
					}
				}
				current = current.getNext();
			}
		}
		return false;
	}
}
