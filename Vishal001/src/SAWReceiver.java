//package com.agundamaneni;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class SAWReceiver {
	public final int PACKET_SIZE = 1024;
	
	private DatagramPacket packet_ack ;
	private byte data_received[] ;
	private DatagramSocket SAWServerSocket;
	private int prev_packet = -1;
	private boolean isTransfered = false ;
	private int port_num;
	private DatagramSocket socket_ack;
	private byte data_receivedBuffer[] ;
	private DatagramPacket packet_received ;
	private String file;
	
	public SAWReceiver(int port_num, String file) throws SocketException, FileNotFoundException {
		this.port_num = port_num;
		this.file = file;
	}
	
	public boolean receive() throws IOException {
	
		SAWServerSocket = new DatagramSocket(port_num);
		socket_ack = new DatagramSocket();
		
		FileOutputStream fos_receiver = new FileOutputStream(new File(file) , true);
		
		while (!isTransfered) {

			data_receivedBuffer = new byte[PACKET_SIZE];
			packet_received = new DatagramPacket(data_receivedBuffer,
						data_receivedBuffer.length);
			SAWServerSocket.receive(packet_received);
			System.out.println("Packet Received");
			data_received = packet_received.getData();
			System.out.println("Packet stored to byte array");
			int packet_size = packet_received.getLength();
			System.out.println("Packet size"+packet_size);
			int packet_num = getNum() ;
			System.out.println("Packet number:"+packet_num);
			/*boolean isOkay=true;
			if(packet_num == prev_packet){
				isOkay=true;
			}*/
			System.out.println("pevious packet"+prev_packet);
			boolean isOkay = (packet_num != prev_packet) ;
			System.out.println("boolean isokay is assigned:"+isOkay);
			if (isOkay) {
				System.out.println("is okay is true");
					boolean isTransferedOk = data_received[2] > 0 ;
					System.out.println("isTransferedok after"+isTransferedOk);
					if (isTransferedOk) {
						System.out.println("isTransfered in before"+isTransfered);
						isTransfered = true;
						System.out.println("isTransfered in after"+isTransfered);
					}
					System.out.println("isTransferedok after if"+isTransferedOk);
					fos_receiver.write(data_received, 3, packet_size - 3);
					System.out.println("Data write to file");
			}
			InetAddress ipAddress = packet_received.getAddress();
			System.out.println("ipaddress:"+ipAddress);
					
			byte[] ack_b = new byte[2];
			//ackPacket[0] = (byte)(lastSequenceNumber >> 8);
	        //ackPacket[1] = (byte)(lastSequenceNumber);
			ack_b[0] = (byte) (packet_num >>8);
			ack_b[1]=(byte)packet_num;
			packet_ack = new DatagramPacket(ack_b, ack_b.length,ipAddress, port_num + 1);
			socket_ack.send(packet_ack);
			System.out.println("Ack sent");
			prev_packet = packet_num;
					
			if(isTransfered){
				prev_packet = -1;
				isTransfered = true ;
				fos_receiver.close();
			}
				
		}
		
		SAWServerSocket.close();
		socket_ack.close();
		return true;
		
	}
	
	private int getNum(){
		return ((0xff & data_received[0]) << 8) | (0xff & data_received[1]) ;
	}
}