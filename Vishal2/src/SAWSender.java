//package com.dudesameerkhn.cosc635;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Random;

public class SAWSender {
	public final int PACKET_SIZE = 4096;
	
	private DatagramSocket udp_sock_send;
	private DatagramSocket udp_sock_ack;
	
	private FileInputStream fis;
	
	private int sim_num;
	
	public int packet_loss;
	public int packet_sent;
	public int totalPacket;
	
	private int port_number;
	private byte packet_num;
	private boolean ack_received;
	private boolean isTransfered;
	private InetAddress host_address;

	public SAWSender(String host, int port_number, int sim_num, String file) throws SocketException, FileNotFoundException, UnknownHostException{
		this.port_number = port_number;
		this.sim_num = sim_num;
		udp_sock_send = new DatagramSocket();
		udp_sock_ack = new DatagramSocket(port_number + 1);

		fis = new FileInputStream(new File(file));

		host_address = InetAddress.getByName(host);
		
		refresh();
	}

	public boolean send() throws Exception {
		refresh();
		while (!isTransfered) {

			int avail_data = fis.available();

			int data_num = 0;

			totalPacket += 1;

			if (avail_data >= (PACKET_SIZE-3)) {
				data_num = PACKET_SIZE-3;
			} else {
				data_num = avail_data;
			}

			if (avail_data <= (PACKET_SIZE - 3)) {
				isTransfered = true;
			} else {
				isTransfered = false;
			}

			byte send_byte[] = new byte[data_num + 3];

			send_byte[0] = 0;
			send_byte[1] = packet_num;

			if (isTransfered) {
				send_byte[2] = (byte) 1;
			} else {
				send_byte[2] = (byte) 0;
			}

			fis.read(send_byte, 3, data_num);

			DatagramPacket sendPacket = new DatagramPacket(send_byte, send_byte.length, host_address, port_number);
			while (!ack_received) {
				try {
					int sim_rand = new Random(System.currentTimeMillis()).nextInt(99);

					if (sim_rand < sim_num) {
						packet_loss++;
						packet_sent++;
						continue;
					}
					
					udp_sock_send.send(sendPacket);
					packet_sent++;

					byte[] b_ack = new byte[1];
					byte[] ackData;
					DatagramPacket ackPacket;

					ackPacket = new DatagramPacket(b_ack, b_ack.length);
					udp_sock_ack.setSoTimeout(90);
					udp_sock_ack.receive(ackPacket);

					ackData = ackPacket.getData();

					ack_received = (ackData[0] == packet_num);
				} catch (SocketTimeoutException e) {
					ack_received = false;
				}
			}
			ack_received = false;

			packet_num = (byte) ((packet_num + 1)%100);

		}

		udp_sock_send.close();
		udp_sock_ack.close();
		return true;
	}

	private void refresh() {
		totalPacket = 0;
		packet_loss = 0;
		packet_sent = 0;

		packet_num = 0;

		ack_received = false;

		isTransfered = false;
	}
}
