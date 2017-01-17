//package com.assig635;

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

public class StopAndWaitSender {
	private final int PACKET_SIZE = 4096;
	private final int PACKET_TIMEOUT = 90;
	
	private DatagramSocket socket_recv;
	private DatagramSocket socket_ack;
	
	private FileInputStream is;
	
	private boolean recvd_ack;
	private boolean recvd_done;
	
	private InetAddress ipaddr;
	private int port;
	private int simulation;
	
	public byte packet_no;
	public int send_loss;
	public int send_tries;
	public int send_count;

	Random rnd_generator;

	public StopAndWaitSender(String host, int port_number, int sim_num, String file) throws SocketException, FileNotFoundException, UnknownHostException{
		port = port_number;
		simulation = sim_num;
		socket_recv = new DatagramSocket();
		socket_ack = new DatagramSocket(port_number + 1);

		is = new FileInputStream(new File(file));
		ipaddr = InetAddress.getByName(host);

		rnd_generator = new Random(System.currentTimeMillis());

		init_status();
	}

	private void init_status() {
		send_count = 0;
		send_loss = 0;
		send_tries = 0;

		packet_no = 0;

		recvd_ack = false;
		recvd_done = false;
	}
	
	public boolean send() throws Exception {
		
		init_status();
		
		while (!recvd_done) {

			int remain_data = is.available();

			int data_num = remain_data;;

			if (remain_data >= (PACKET_SIZE - 3)) {
				data_num = PACKET_SIZE - 3;
			}

			if (remain_data <= (PACKET_SIZE - 3)) {
				recvd_done = true;
			} else {
				recvd_done = false;
			}

			byte send_byte[] = new byte[data_num + 3];

			send_byte[0] = 0;
			send_byte[1] = packet_no;

			if (recvd_done) {
				send_byte[2] = (byte) 1;
			} else {
				send_byte[2] = (byte) 0;
			}

			is.read(send_byte, 3, data_num);

			DatagramPacket sendPacket = new DatagramPacket(send_byte, send_byte.length, ipaddr, port);
			send_count ++;
			
			while (!recvd_ack) {
				try {
					int ran_num = rnd_generator.nextInt(99);

					if (ran_num < simulation && send_count*(simulation+1)/99>send_loss) {
						send_loss++;
						send_tries++;
						continue;
					}
					
					socket_recv.send(sendPacket);
					send_tries++;

					byte[] b_ack = new byte[1];
					byte[] ackData;
					DatagramPacket ackPacket;

					ackPacket = new DatagramPacket(b_ack, b_ack.length);
					socket_ack.setSoTimeout(PACKET_TIMEOUT);
					socket_ack.receive(ackPacket);

					ackData = ackPacket.getData();

					recvd_ack = (ackData[0] == packet_no);
				} catch (SocketTimeoutException e) {
					recvd_ack = false;
				}
			}

			recvd_ack = false;
			packet_no = (byte)((packet_no + 1) % 100);
		}

		socket_recv.close();
		socket_ack.close();
		return true;
	}
}
