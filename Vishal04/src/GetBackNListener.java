//package com.assig635;

import java.io.File;
import java.io.FileOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class GetBackNListener {
	public final int PACKET_SIZE = 4096;

	private int port_num;
	private String file;
	
	public GetBackNListener(int port_num, String file) throws SocketException {
		this.port_num = port_num;
		this.file = file;
	}

	public boolean receive() throws Exception{
		DatagramSocket socket_recv = new DatagramSocket(port_num);
		DatagramSocket socket_ack = new DatagramSocket();

		FileOutputStream fos = new FileOutputStream(new File(file), true);

		int prev_packet = 0;
		boolean isTransfered = false;
		
		while (!isTransfered) {
			byte receivedDataBuffer[] = new byte[PACKET_SIZE];
			DatagramPacket recv_packet = new DatagramPacket(receivedDataBuffer, receivedDataBuffer.length);

			socket_recv.receive(recv_packet);
			
			byte data_recvd[] = recv_packet.getData();
		
			int currentPacketSize = recv_packet.getLength();
	
			int packetNum = (0x0000FF00 & (data_recvd[0] << 8)) | (0x000000FF & data_recvd[1]);
	
			if (packetNum == (prev_packet + 1)) {
				if (data_recvd[2] > 0) {
					isTransfered = true;
				}
				fos.write(data_recvd, 3, currentPacketSize - 3);
				prev_packet++;
			}

			InetAddress sender_ip = recv_packet.getAddress();

			byte[] data_ack = new byte[2];
			data_ack[1] = (byte) (prev_packet >>> 8);
			data_ack[0] = (byte) (prev_packet);

			DatagramPacket ack_packet = new DatagramPacket(data_ack, data_ack.length, sender_ip, port_num + 1);
			socket_ack.send(ack_packet);
			System.out.println(String.format("ack req %d", prev_packet));
			if(isTransfered){
				fos.close();
				prev_packet = 0;
				isTransfered = true ;
			}
		}
			
		socket_recv.close();
		socket_ack.close();

		return true;
	}
}
