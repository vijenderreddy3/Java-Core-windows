package application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import javafx.application.Platform;

public class GBNServer {

	private int port_num;
	int prev_packet = 0;
	private DatagramSocket socket_received;
	private DatagramSocket socket_ack;
	boolean isTransfered = false;
	private FileOutputStream fos_received ;

	public GBNServer(int port_num) throws SocketException {
		this.port_num = port_num;
		
		socket_received = new DatagramSocket(port_num);
		socket_ack = new DatagramSocket();
	}

	public boolean receive() throws Exception{

		while (!isTransfered) {
		byte receivedDataBuffer[] = new byte[1024];
		DatagramPacket receivedPacket = new DatagramPacket(receivedDataBuffer, receivedDataBuffer.length);
	
					
		socket_received.receive(receivedPacket);
					
		fos_received = new FileOutputStream(new File("C://data//COSC635_2148_P2_DataReceived.txt") , true);
					
		byte receivedData[] = receivedPacket.getData();
	
		int currentPacketSize = receivedPacket.getLength();

		int packetNum = ((receivedData[0] & 0xFF) << 24) | ((receivedData[1] & 0xFF) << 16) |
						((receivedData[2] & 0xFF) << 8) | (receivedData[3] & 0xFF) ;

		if (packetNum == (prev_packet + 1)) {
			if (receivedData[4] > 0) {
				isTransfered = true;
			}
			fos_received.write(receivedData, 5, currentPacketSize - 5);
						
			prev_packet++;
						
		}

		InetAddress sender_ip = receivedPacket.getAddress();
					
		byte[] b_ack = new byte[4];
		b_ack[0] = (byte) ((prev_packet >> 24) & 0xFF);
		b_ack[1] = (byte) ((prev_packet >> 16) & 0xFF);
		b_ack[2] = (byte) ((prev_packet >> 8) & 0xFF);
		b_ack[3] = (byte) (prev_packet & 0xFF) ;
					
		DatagramPacket ackPacket = new DatagramPacket(b_ack, b_ack.length,
					sender_ip, port_num + 1);
		socket_ack.send(ackPacket);
					
		if(isTransfered){
			fos_received.close();
			prev_packet = 0;
			isTransfered = false ;
					
			Platform.runLater(new Runnable(){

				@Override
				public void run() {
					try {
						DialogClass.instance.show();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
		
	socket_received.close();
	socket_ack.close();
	return true;
		
	}
}
