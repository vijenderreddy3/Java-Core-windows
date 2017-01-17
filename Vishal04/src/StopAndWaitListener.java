//package com.assig635;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class StopAndWaitListener {
	public final int PACKET_SIZE = 4096;

	private int port_num;
	private String file;
	
	public StopAndWaitListener(int port_num, String file) throws SocketException, FileNotFoundException {
		this.port_num = port_num;
		this.file = file;
	}
	
	public boolean receive() throws IOException {
		DatagramSocket socket_recv = new DatagramSocket(port_num);
		DatagramSocket socket_ack = new DatagramSocket();

		DatagramPacket packet_ack;
		DatagramPacket packet_received;
		
		FileOutputStream fos_receiver = new FileOutputStream(new File(file) , true);
		
		int prev_packet = -1;
		
		byte data_received[];
		byte data_receivedBuffer[];
		
		boolean recvd_done = false;
	
		while (!recvd_done) {
			data_receivedBuffer = new byte[PACKET_SIZE];
			packet_received = new DatagramPacket(data_receivedBuffer,data_receivedBuffer.length);
			socket_recv.receive(packet_received);
			data_received = packet_received.getData();
			int packet_size = packet_received.getLength();
			int packet_num = ((0x0000FF00 & (data_received[0] << 8)) | (0x000000FF & data_received[1]));
			boolean isOkay = packet_num != prev_packet;
			
			if (isOkay) {
				boolean isTransferedOk = data_received[2] > 0;
				if (isTransferedOk) {
					recvd_done = true;
				}
				fos_receiver.write(data_received, 3, packet_size - 3);
			}

			InetAddress ipAddress = packet_received.getAddress();
					
			byte[] ack_b = new byte[1];
			ack_b[0] = (byte) packet_num;
			packet_ack = new DatagramPacket(ack_b, ack_b.length,
							ipAddress, port_num + 1);
			socket_ack.send(packet_ack);
					
			prev_packet = packet_num;
					
			if(recvd_done){
				prev_packet = -1;
				recvd_done = true;
				fos_receiver.close();
			}
		}

		socket_recv.close();
		socket_ack.close();
		
		return true;
	}
}