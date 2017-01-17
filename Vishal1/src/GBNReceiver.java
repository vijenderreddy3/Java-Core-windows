//package com.agundamaneni;

import java.io.File;
import java.io.FileOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class GBNReceiver {
	public final int PACKET_SIZE = 4096;

	private int port_num;
	private int prev_packet = 0;
	private DatagramSocket socket_received;
	private DatagramSocket socket_ack;
	private boolean isTransfered = false;
	private FileOutputStream fos_received ;
	private String file;
	
	public GBNReceiver(int port_num, String file) throws SocketException {
		this.port_num = port_num;
		this.file = file;
		socket_received = new DatagramSocket(port_num);
		socket_ack = new DatagramSocket();
	}

	public boolean receive() throws Exception{
		while (!isTransfered) {
			byte receivedDataBuffer[] = new byte[PACKET_SIZE];
			DatagramPacket receivedPacket = new DatagramPacket(receivedDataBuffer, receivedDataBuffer.length);

			socket_received.receive(receivedPacket);
			fos_received = new FileOutputStream(new File(file) , true);
			
			byte receivedData[] = receivedPacket.getData();
		
			int currentPacketSize = receivedPacket.getLength();
	
			int packetNum = (0x0000FF00 & (receivedData[0] << 8)) | (0x000000FF & receivedData[1]);
	
			if (packetNum == (prev_packet + 1)) {
				if (receivedData[2] > 0) {
					isTransfered = true;
				}
				fos_received.write(receivedData, 3, currentPacketSize - 3);
							
				prev_packet++;
			}
	
			InetAddress sender_ip = receivedPacket.getAddress();
						
			byte[] b_ack = new byte[2];
			b_ack[1] = (byte) (prev_packet >>> 8);
			b_ack[0] = (byte) prev_packet;
						
			DatagramPacket ackPacket = new DatagramPacket(b_ack, b_ack.length,
						sender_ip, port_num + 1);
			socket_ack.send(ackPacket);
						
			if(isTransfered){
				fos_received.close();
				prev_packet = 0;
				isTransfered = true ;
			}
		}
			
		socket_received.close();
		socket_ack.close();
		return true;
	}
}
