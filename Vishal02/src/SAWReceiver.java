//package com.dudesameerkhn.cosc635;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class SAWReceiver {
	public final int PACKET_SIZE = 4096;

	private DatagramSocket udp_sock_ack;
	private DatagramSocket udp_sock_recv;
	private DatagramPacket udpp_ack;
	private DatagramPacket udpp_recv;
	
	private byte data_received[];
	private byte data_receivedBuffer[];

	private int prev_packet = -1;
	private int port_num;
	
	private String file;

	public SAWReceiver(int port_num, String file) throws SocketException, FileNotFoundException {
		this.port_num = port_num;
		this.file = file;
	}
	
	public boolean receive() throws IOException {
	
		udp_sock_recv = new DatagramSocket(port_num);
		udp_sock_ack = new DatagramSocket();
		
		FileOutputStream fos_receiver = new FileOutputStream(new File(file) , true);

		boolean isTransfered = false;
		
		while (!isTransfered) {
			data_receivedBuffer = new byte[PACKET_SIZE];
			udpp_recv = new DatagramPacket(data_receivedBuffer,
						data_receivedBuffer.length);
			udp_sock_recv.receive(udpp_recv);
			data_received = udpp_recv.getData();
			int packet_size = udpp_recv.getLength();
			int packet_num = getNum();
			boolean isOkay = packet_num != prev_packet;
			
			if (isOkay) {
				boolean isTransferedOk = data_received[2] > 0;
				if (isTransferedOk) {
					isTransfered = true;
				}
				fos_receiver.write(data_received, 3, packet_size - 3);
			}

			InetAddress ipAddress = udpp_recv.getAddress();
					
			byte[] ack_b = new byte[1];
			ack_b[0] = (byte) packet_num;
			udpp_ack = new DatagramPacket(ack_b, ack_b.length,
							ipAddress, port_num + 1);
			udp_sock_ack.send(udpp_ack);
					
			prev_packet = packet_num;
					
			if(isTransfered){
				prev_packet = -1;
				isTransfered = true;
				fos_receiver.close();
			}
				
		}
		
		udp_sock_recv.close();
		udp_sock_ack.close();
		return true;
	}
	
	private int getNum(){
		return (0x0000FF00 & (data_received[0] << 8)) | (0x000000FF & data_received[1]);
	}
}