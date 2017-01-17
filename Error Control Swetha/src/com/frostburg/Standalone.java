package com.frostburg;

import java.util.Scanner;

public class Standalone  {
	static String hostname=null;
	static int portNumber=0;
	static int pseudoNumber=0;
	static String receivedFileName = "COSC635_P2_DataRecieved.txt";
	static String sendFilename = "COSC635_P2_DataSent.txt";

	
	
	public static void main(String[] args) throws Exception {
		 Scanner sc=new Scanner(System.in);
		 System.out.println("Do you want to send or receive data:"); 
		 String input=sc.nextLine();
		 if(input.equals("send")){
			 System.out.println("Enter receiver IP Address:");
			 hostname=sc.next();
			 System.out.println("Enter receiver port number:");
			 portNumber=sc.nextInt();
			 System.out.println("Enter number of packets to loss:");
			 pseudoNumber=sc.nextInt();
			 StopandWaitClient stopandWaitClient = new StopandWaitClient(hostname,portNumber,pseudoNumber,sendFilename);
			 stopandWaitClient.sendFileDataThroughStopandWait();
		 }else if(input.equals("receive")){
			 System.out.println("Enter sender port number:");
			 portNumber=sc.nextInt();
			 StopandWaitServer stopAndWaitServer=new StopandWaitServer(portNumber,receivedFileName);
			 stopAndWaitServer.receiveDataThroughStopandWait();
		 }else{
			 System.out.println("invalid input");
		 }
	}
}