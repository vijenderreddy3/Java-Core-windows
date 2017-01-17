package application;

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

import javafx.scene.control.Label;

public class GBNClient {

	private int packet_num = 1 ;
	private boolean isEOF = false;
	private String receiver_ip;
	private int port_num;
	private DatagramSocket socket_sender;
	private List<WindowPacket> packet_window;
	private FileInputStream fis_datasent;
	public boolean isThreadRunning;
	private int num_acked;
	private int sim_num ;
	private int packet_loss = 0 ;
	private int packet_total = 0 ;
	private Label total ;
	private Label loss ;
	private Label sent ;
	private InetAddress receive_ip ;

	public GBNClient(String receiver_ip, int port_num , int sim_num , Label total , Label loss , Label sent) throws FileNotFoundException, SocketException, UnknownHostException {

		this.sim_num = sim_num ;
		this.receiver_ip = receiver_ip;
		this.port_num = port_num;
		this.isThreadRunning = false;

		this.total = total ;
		this.loss = loss ;
		this.sent = sent ;

		fis_datasent = new FileInputStream(new File("C://data//COSC635_2148_P2_DataSent.txt"));
		packet_window = new ArrayList<WindowPacket>();
		socket_sender = new DatagramSocket();
		receive_ip = InetAddress.getByName(receiver_ip);
	}

	public boolean send() throws Exception{

			int data_length = fis_datasent.available();

			DatagramSocket socket_ack = new DatagramSocket(port_num + 1) ;

			new Thread(new Runnable(){

				@Override
				public void run() {
					while (!isThreadRunning) {

						byte[] ack_byte = new byte[4];

						DatagramPacket packet_ack = new DatagramPacket(ack_byte, ack_byte.length);
						try {
							socket_ack.setSoTimeout(5);
						} catch (SocketException e) {
							e.printStackTrace();
						}
						try {
							socket_ack.receive(packet_ack);
						} catch(SocketTimeoutException e){
							//isThreadRunning = true ;
						} catch (IOException e) {
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
					if(packet_window.size() > 0){
						if(packet_window.get(0).getpacket_num() <= num_acked){
							packet_window.remove(0);
						}else{
							System.out.println();
							break ;
						}
					}else{
						System.out.println();
						break ;
					}
				}

				while (packet_window.size() < 5 && !isEOF) {
					packet_total++ ;
					int availableData = fis_datasent.available();

					int datalen = 0 ;

					if(availableData >= (1024 - 5)){
						datalen = 1019 ;
					}else{
						datalen = availableData ;
					}

					if(availableData <= 1021){
						isEOF = true ;
					}else{
						isEOF = false ;
					}

					byte sendData[] = new byte[datalen + 5];

					byte[] result = new byte[4] ;
					result[0] = (byte) ((packet_num >> 24) & 0xFF);
					result[1] = (byte) ((packet_num >> 16) & 0xFF);
					result[2] = (byte) ((packet_num >> 8) & 0xFF);
					result[3] = (byte) (packet_num & 0xFF);

					/* Packet number. */
					sendData[0] = result[0] ;
					sendData[1] = result[1] ;
					sendData[2] = result[2] ;
					sendData[3] = result[3] ;

					if(isEOF){
						sendData[4] = (byte)1 ;
					}else{
						sendData[4] = (byte)0 ;
					}

					fis_datasent.read(sendData, 5, datalen);

					DatagramPacket packet_send = new DatagramPacket(sendData, sendData.length,
							receive_ip, port_num);

					packet_window.add(new WindowPacket(packet_send, false, packet_num));

					packet_num++;
				}

				boolean packet_sends = false;

				for (WindowPacket packet : packet_window) {

					if (packet.getpacket_num() > num_acked) {

						packet_sends = packet_sends ||
						System.currentTimeMillis() > (packet.getTimeLastSent() + 30);

						if (packet_sends) {
							int ran_num = new Random(System.currentTimeMillis()).nextInt(99) ;

							if(ran_num < sim_num){
								packet_loss++ ;
							}else{
								socket_sender.send(packet.getPacket());
							}
							packet.setTimeLastSent(System.currentTimeMillis());
						} else {
							break;
						}

					}

				}

				if (packet_window.size() == 0) {
					isThreadRunning = true;
				}

				Thread.yield();

			}

		total.setText(packet_total + "");
		loss.setText(packet_loss + "");
		sent.setText(packet_total + "");

		socket_sender.close();
		isThreadRunning = true;
		return true;

	}

	private class WindowPacket {
		private DatagramPacket window_packet;
		private boolean isAck;
		private int packet_num;
		private long last_sent;

		public WindowPacket(DatagramPacket p_packet, boolean p_acked, int p_packet_num) {
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

	private class Ack_Thread extends Thread {

		private DatagramSocket socket_ack;

		public Ack_Thread(int port_num) throws SocketException {
			this.socket_ack = new DatagramSocket(port_num + 1);
		}

		public void run() {

			while (!isThreadRunning) {

					byte[] ack_byte = new byte[4];

					DatagramPacket packet_ack = new DatagramPacket(ack_byte, ack_byte.length);
					try {
						socket_ack.setSoTimeout(5);
					} catch (SocketException e) {
						e.printStackTrace();
					}
					try {
						socket_ack.receive(packet_ack);
					} catch (IOException e) {
						e.printStackTrace();
					}
					byte[] ackData = packet_ack.getData();
					int packet_ack_num = getAckPaket(ackData) ;
					num_acked = Math.max(num_acked, packet_ack_num);


				Thread.yield();
			}

			socket_ack.close();

		}

	}

	private int getAckPaket(byte[] ackData){
		return ((ackData[0] & 0xFF) << 24) | ((ackData[1] & 0xFF) << 16)
		        | ((ackData[2] & 0xFF) << 8) | (ackData[3] & 0xFF);
	}

}

