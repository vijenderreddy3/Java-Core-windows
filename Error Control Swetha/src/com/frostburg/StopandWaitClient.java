/**
 * 
 */
package com.frostburg;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * @author Madhuri Kankanala
 *
 */
public class StopandWaitClient {
	private JFrame details;
	private JPanel controlPanel;
	private JTextArea textArea;
	private String hostName;
	private int portNumber;
	private int pseudoNumber;
	private String fileName;
	private int packetLoss=0;
	DatagramSocket sawClientSocket = null;
	DatagramSocket receiveSocket=null;
	String ackSaw = null;
	byte[] receiveData = new byte[1024];
	byte[] sendData = new byte[1024];
	// StopandWaitServer stopandWaitServer;

	public StopandWaitClient(String hostName, int portNumber, int pseudoNumber, String fileName) throws Exception {
		prepareGUI();
		this.hostName = hostName;
		this.portNumber = portNumber;
		this.pseudoNumber = pseudoNumber;
		this.fileName = fileName;
		sawClientSocket = new DatagramSocket(portNumber);
		receiveSocket=new DatagramSocket(portNumber+1);
	}

	public void sendFileDataThroughStopandWait() throws IOException {

		// System.out.println("Entered into sendFileDataThroughStopandWait
		// method::::::::");

		// packetLoss = generatePseudoCode(pseudoNumber);
		// if (packetLoss >= 0) {

		File file = new File(fileName);
		double fileSize = file.length();
		int fileLength = (int) fileSize;
		int numberOfPacketsToSend = fileLength / 1024;
		textArea.append("\nnumberOfPacketsToSend-->" + numberOfPacketsToSend);
		details.setVisible(true);
		// System.out.println("numberOfPacketsToSend-->"+numberOfPacketsToSend);
		int remaingPackets = numberOfPacketsToSend % 1024;
		if (remaingPackets > 0)
			numberOfPacketsToSend = numberOfPacketsToSend + 1;
		int noByte = 1024;
		int startIndex = 0;
		sendData = new byte[(int) numberOfPacketsToSend * 1022];

		receiveData = new byte[(int) numberOfPacketsToSend * 1022];
		FileInputStream fileInputStream = new FileInputStream(file);

		for (int getBytes = 0; getBytes < numberOfPacketsToSend; getBytes++) {

			boolean check = false;
			check = errorControl();
			if (!check) {
				byte[] sendSubStringData = new byte[1024];

				sendSubStringData = Arrays.copyOfRange(sendData, startIndex, startIndex + 1024);
				/*
				 * for(int i=0;i<1022;i++){
				 * sendSubStringData[startIndex+i]=fileInputStream.read(
				 * sendSubStringData); }
				 */

				fileInputStream.read(sendSubStringData);
				sendSubStringData[1023] = 0;
				int packetNumber = 1022;
				sendSubStringData[packetNumber] = (byte) (getBytes + 1);

				InetAddress IPAddress = InetAddress.getByName(hostName);
				// System.out.println("sendData
				// length:::"+sendSubStringData.length);
				textArea.append("\nSending packet:" + (getBytes + 1));
				details.setVisible(true);
				DatagramPacket sendPacket = new DatagramPacket(sendSubStringData, sendSubStringData.length, IPAddress,
						(portNumber + 3));
				sawClientSocket.send(sendPacket);
				// System.out.println("After
				// StartIndex::::::"+startIndex+"numberofBytes:::::"+noByte+"
				// ,numberOfPacketsToSend-"+numberOfPacketsToSend);
				receivedAcknowledgement();

				if (getBytes == numberOfPacketsToSend - 1)
					noByte = remaingPackets;
				startIndex = startIndex + noByte;
				packetNumber = startIndex + noByte;
			} else {
				textArea.append("\nFailed to send packet:" + (getBytes + 1));
				--getBytes;
			}
			textArea.append("\n-----------------------------------------------------");
		}
		/*
		 * } else { System.out.println(
		 * "Unable to send data to StopandWait Server"); }
		 */
		textArea.append("\nCompleted sending file to receiver");
		textArea.append("\nNumber of packets lost:" + packetLoss);
		details.setVisible(true);
		sawClientSocket.close();

	}

	public boolean errorControl() {
		boolean condition = false;
		int random = (int )(Math.random() * 100 + 1);
		//System.out.println("randomNumber::::::::" + random);

		if (pseudoNumber > random) {
			packetLoss++;

			return !condition;
		}
		return condition;
	}

	public void receivedAcknowledgement() throws IOException {

		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		//sawClientSocket.setSoTimeout(100000);
		//sawClientSocket.receive(receivePacket);
		receiveSocket.setSoTimeout(100000);
		receiveSocket.receive(receivePacket);

		ackSaw = new String(receivePacket.getData());
		if (ackSaw.length() > 0) {
			//System.out.println("Response :::::::::::::::::" + ackSaw);
			textArea.append("\nAcknowledgement for packet:"+ackSaw);
			details.setVisible(true);
		}

	}

	private void prepareGUI() {

		controlPanel = new JPanel();
		controlPanel.setSize(600, 800);
		textArea = new JTextArea();
		textArea.setAlignmentX(JLabel.CENTER);
		textArea.setSize(600, 800);
		textArea.setFont(new Font("Serif", Font.ITALIC, 16));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(new Dimension(800, 600));
		controlPanel.add(scroll);
		details = new JFrame("sending packets details:");
		details.setSize(800, 800);
		details.setLayout(new GridLayout(1, 1));
		details.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		details.add(controlPanel);
		details.pack();
		details.setVisible(true);
	}
}
