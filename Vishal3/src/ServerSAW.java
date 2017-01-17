//package com.johnglen.cosc635;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ServerSAW {
	public final int PACKET_SIZE = 4096;
	
	private DatagramPacket packet_ack ;
	private byte data_received[] ;
	private DatagramSocket socket_received;
	private int prev_packet = -1;
	private boolean isTransfered = false ;
	private int port_num;
	private DatagramSocket socket_ack;
	private byte data_receivedBuffer[] ;
	private DatagramPacket packet_received ;
	private String file;
	
	public ServerSAW(int port_num, String file) throws SocketException, FileNotFoundException {
		this.port_num = port_num;
		this.file = file;
	}
	
	public boolean receive() throws IOException {
	
		socket_received = new DatagramSocket(port_num);
		socket_ack = new DatagramSocket();
		
		FileOutputStream fos_receiver = new FileOutputStream(new File(file) , true);
		
		while (!isTransfered) {

			data_receivedBuffer = new byte[PACKET_SIZE];
			packet_received = new DatagramPacket(data_receivedBuffer,data_receivedBuffer.length);
			socket_received.receive(packet_received);
			data_received = packet_received.getData();
			int packet_size = packet_received.getLength();
			int packet_num = getNum() ;
			boolean isOkay = packet_num != prev_packet ;

			if (isOkay) {
				boolean isTransferedOk = data_received[2] > 0 ;
				if (isTransferedOk) {
					isTransfered = true;
				}
				fos_receiver.write(data_received, 3, packet_size - 3);
			}

			InetAddress ipAddress = packet_received.getAddress();
					
			byte[] ack_b = new byte[1];
			ack_b[0] = (byte) packet_num;
			packet_ack = new DatagramPacket(ack_b, ack_b.length, ipAddress, port_num + 1);
			socket_ack.send(packet_ack);

			prev_packet = packet_num;

			if(isTransfered){
				prev_packet = -1;
				isTransfered = true ;
				fos_receiver.close();
			}	
		}
		
		socket_received.close();
		socket_ack.close();
		return true;
	}
	
	private int getNum(){
		return (0x0000FF00 & (data_received[0] << 8)) | (0x000000FF & data_received[1]) ;
	}
}