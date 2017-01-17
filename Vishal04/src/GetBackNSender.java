//package com.assig635;

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

public class GetBackNSender {
	public final int PACKET_SIZE = 4096;

	public boolean bStop;

	private InetAddress receive_ip;
	private int port_num;
	private int simulation;
	private int packet_num = 1;
	
	private int acked_packet_number;
	
	public int packet_loss = 0;
	public int packet_sent = 0;
	public int packet_total = 0;

	private List<NodePacket> packet_list;
	private FileInputStream fis;
	
	private Random rnd_generator;

	public GetBackNSender(String receiver_ip, int port_num , int sim_num, String file) throws FileNotFoundException, SocketException, UnknownHostException {
		this.simulation = sim_num;
		this.port_num = port_num;
		this.bStop = false;
		
		fis = new FileInputStream(new File(file));
		packet_list = new ArrayList<NodePacket>();
		receive_ip = InetAddress.getByName(receiver_ip);
		
		rnd_generator = new Random(System.currentTimeMillis());
	}
	
	public boolean send() throws Exception{

		final DatagramSocket socket_sender = new DatagramSocket();
		final DatagramSocket socket_ack = new DatagramSocket(port_num + 1);
		
		new Thread(new Runnable(){
			@Override
			public void run() {
				while (!bStop) {
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
						//bStop = true;
					}catch (IOException e) {
						e.printStackTrace();
					}
					byte[] ackData = packet_ack.getData();
					int packet_ack_num = ((ackData[1] << 8) & 0x0000FF00) | (ackData[0] & 0x000000FF);
					if(packet_ack_num>0) {
						acked_packet_number = Math.max(acked_packet_number, packet_ack_num);
						System.out.println(String.format("acked:%d",packet_ack_num));
					}
					Thread.yield();
				}
				socket_ack.close();
			}
		}).start();

		boolean isEOF = false; 
		
		while(!bStop) {
			while(true) {
				if(packet_list.size() > 0){
					if(packet_list.get(0).getpacket_num() <= acked_packet_number){
						packet_list.remove(0);
					}else{
						break;
					}
				}else{
					break;
				}
			}

			while (packet_list.size() < 5 && !isEOF) {
				packet_total++;
				
				int availableData = fis.available();
				int datalen = 0;
				
				if(availableData >= (PACKET_SIZE - 3)){
					datalen = PACKET_SIZE-3;
				}else{
					datalen = availableData;
				}
				
				if(availableData <= PACKET_SIZE -3){
					isEOF = true;
				}else{
					isEOF = false;
				}

				byte sendData[] = new byte[datalen + 3];

				sendData[0] = (byte) (packet_num >> 8);
				sendData[1] = (byte) (packet_num);

				if(isEOF){
					sendData[2] = (byte)1;
				}else{
					sendData[2] = (byte)0;
				}

				fis.read(sendData, 3, datalen);
				
				DatagramPacket packet_send = new DatagramPacket(sendData, sendData.length, receive_ip, port_num);

				packet_list.add(new NodePacket(packet_send, packet_num));
				packet_num++;
			}

			int ran_num = rnd_generator.nextInt(99);

			if (ran_num < simulation && packet_total*(simulation+1)/99>packet_loss) {
				packet_loss++;
				packet_sent++;
				continue;
			}
			
			boolean packet_sends = false;

			for (NodePacket packet : packet_list) {

				if (packet.getpacket_num() > acked_packet_number) {

					packet_sends = packet_sends ||
					System.currentTimeMillis() > (packet.getTimeLastSent() + 30);

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
				bStop = true;
			}
			Thread.yield();
		}
		
		socket_sender.close();
		bStop = true;
		return true;
	}
	
	private class NodePacket {
		private DatagramPacket packet;
		private int packet_num;
		private long last_send_tick;

		public NodePacket(DatagramPacket p_packet, int p_packet_num) {
			this.packet = p_packet;
			this.packet_num = p_packet_num;
			this.last_send_tick = 0;
		}

		public DatagramPacket getPacket() {
			return packet;
		}

		public int getpacket_num() {
			return packet_num;
		}

		public long getTimeLastSent() {
			return last_send_tick;
		}

		public void setTimeLastSent(long timeLastSent) {
			this.last_send_tick = timeLastSent;
		}
	}
}