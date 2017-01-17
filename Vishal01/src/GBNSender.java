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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GBNSender {
	public final int PACKET_SIZE = 4096;
	
	private int packet_num = 1 ;
	private boolean isEOF = false; 
	private int port_num;
	private DatagramSocket socket_sender;
	private List<DataPacket> packet_list;
	private FileInputStream fis_datasent;
	public boolean isThreadRunning;
	private int num_acked;
	private int sim_num ;
	public int packet_loss = 0 ;
	public int packet_sent = 0 ;
	public int packet_total = 0 ;
	private InetAddress receive_ip ;

	Random rand_gen;
	public GBNSender(String receiver_ip, int port_num , int sim_num, String file) throws FileNotFoundException, SocketException, UnknownHostException {
		
		this.sim_num = sim_num ;
		this.port_num = port_num;
		this.isThreadRunning = false;
		
		fis_datasent = new FileInputStream(new File(file));
		packet_list = new ArrayList<DataPacket>();
		socket_sender = new DatagramSocket();
		receive_ip = InetAddress.getByName(receiver_ip);
		
		rand_gen = new Random(System.currentTimeMillis());
	}
	
	public boolean send() throws Exception{		

		int data_length = fis_datasent.available();
		
		final DatagramSocket socket_ack = new DatagramSocket(port_num + 1) ;
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				while (!isThreadRunning) {
					
					byte[] ack_byte = new byte[2];
				
					DatagramPacket packet_ack = new DatagramPacket(ack_byte, ack_byte.length);
					try {
						socket_ack.setSoTimeout(5);
					} catch (SocketException e) {
						e.printStackTrace();
					}
					try {
						socket_ack.receive(packet_ack);
					}catch(SocketTimeoutException s){
						//isThreadRunning = true ;
					}catch (IOException e) {
						e.printStackTrace();
					}
					byte[] ackData = packet_ack.getData();
					int packet_ack_num = getAckPaket(ackData) ;
					num_acked = Math.max(num_acked, packet_ack_num);
					Thread.yield();
				}

				socket_ack.close();

			}
			
		}).start();
		
		while(!isThreadRunning) {
			while(true) {
				if(packet_list.size() > 0){
					if(packet_list.get(0).getpacket_num() <= num_acked){
						packet_list.remove(0);
					}else{
						break;
					}
				}else{
					break;
				}
			}
			
			while (packet_list.size() < 5 && !isEOF) {
				packet_total++ ;	
				int availableData = fis_datasent.available();

				int datalen = 0 ;
				
				if(availableData >= (PACKET_SIZE - 3)){
					datalen = PACKET_SIZE-3 ;
				}else{
					datalen = availableData ;
				}
				
				if(availableData <= PACKET_SIZE -3){
					isEOF = true ;
				}else{
					isEOF = false ;
				}

				byte sendData[] = new byte[datalen + 3];

				sendData[0] = (byte) (packet_num >> 8);
				sendData[1] = (byte) (packet_num);

				if(isEOF){
					sendData[2] = (byte)1 ;
				}else{
					sendData[2] = (byte)0 ;
				}

				fis_datasent.read(sendData, 3, datalen);
				
				DatagramPacket packet_send = new DatagramPacket(sendData, sendData.length,
						receive_ip, port_num);

				packet_list.add(new DataPacket(packet_send, false, packet_num));

				packet_num++;
			}

			int ran_num = rand_gen.nextInt(99) ;
			
			if(ran_num < sim_num && packet_total*sim_num/99>packet_loss){
				packet_loss++ ;
				packet_sent++;
				continue ;
			}
			
			boolean packet_sends = false;
			
			for (DataPacket packet : packet_list) {

				if (packet.getpacket_num() > num_acked) {

					packet_sends = packet_sends ||
					System.currentTimeMillis() > (packet.getTimeLastSent() + 90);

					if (packet_sends) {
						socket_sender.send(packet.getPacket());
						packet.setTimeLastSent(System.currentTimeMillis());
						packet_sent++;
					} else {
						break;
					}

				}

			}
			
			if (packet_list.size() == 0) {
				isThreadRunning = true;
			}
			
			Thread.yield();

		}
		
		socket_sender.close();
		isThreadRunning = true;
		return true;
		
	}
	
	private class DataPacket {
		private DatagramPacket window_packet;
		private boolean isAck;
		private int packet_num;
		private long last_sent;

		public DataPacket(DatagramPacket p_packet, boolean p_acked, int p_packet_num) {
			this.packet_num = p_packet_num;
			this.window_packet = p_packet;
			this.last_sent = 0;
			this.isAck = p_acked;
		}
		
		public DatagramPacket getPacket() {
			return window_packet;
		}

		public boolean isAcked() {
			return isAck;
		}

		public void packet_ack() {
			this.isAck = true;
		}

		public int getpacket_num() {
			return packet_num;
		}

		public long getTimeLastSent() {
			return last_sent;
		}

		public void setTimeLastSent(long timeLastSent) {
			this.last_sent = timeLastSent;
		}
	}

	private int getAckPaket(byte[] ackData){
		return ((ackData[1] << 8) & 0x0000FF00) | 
				(ackData[0] & 0x000000FF) ;
	}
}

