//package com.agundamaneni;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Random;

public class SAWSender {
	public final int PACKET_SIZE = 1024;
	private int port_number;
	private DatagramSocket SAWClientSocket;
	private DatagramSocket socket_ack;
	private FileInputStream fis_reader;
	private int sim_num;
	public int packet_loss;
	public int packet_sent;
	public int totalPacket;
	private byte packet_num;
	private boolean ack_received;
	private boolean isTransfered;
	private InetAddress ip_address;

	Random rand_gen;
	public SAWSender(String host, int port_number, int sim_num, String file) throws SocketException, FileNotFoundException, UnknownHostException{
		this.port_number = port_number;
		this.sim_num = sim_num;
		SAWClientSocket = new DatagramSocket();
		socket_ack = new DatagramSocket(port_number + 1);

		fis_reader = new FileInputStream(new File(file));

		ip_address = InetAddress.getByName(host);
		
		rand_gen = new Random(System.currentTimeMillis());
		refresh();
	}

	public boolean send() throws Exception {
		refresh();
		while (!isTransfered) {

			int avail_data = fis_reader.available();

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

			fis_reader.read(send_byte, 3, data_num);

			DatagramPacket sendPacket = new DatagramPacket(send_byte, send_byte.length, ip_address, port_number);
			while (!ack_received) {
				try {
					int sim_rand = rand_gen.nextInt(99);

					if (sim_rand < sim_num && totalPacket*sim_num/99>packet_loss) {
						packet_loss++;
						packet_sent++;
						continue;
					}
					
					SAWClientSocket.send(sendPacket);
					packet_sent++;

					byte[] b_ack = new byte[2];
					byte[] ackData;
					DatagramPacket ackPacket;

					ackPacket = new DatagramPacket(b_ack, b_ack.length);
					socket_ack.setSoTimeout(90);
					socket_ack.receive(ackPacket);

					ackData = ackPacket.getData();

					ack_received = (ackData[1] == packet_num);
				} catch (SocketTimeoutException e) {
					ack_received = false;
				}

			}

			ack_received = false;

			int temp = packet_num + 1;
			int tempa = temp % 100;

			packet_num = (byte) tempa;

		}

		SAWClientSocket.close();
		socket_ack.close();
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
