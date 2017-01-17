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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class StopandWaitServer {
	private JFrame details;
	private JPanel controlPanel;
	private JTextArea textArea;
	private int portNumber;
	private String fileName;
	DatagramSocket receiveServerSocket = null;
	DatagramSocket sendServerSocket = null;
	byte[] receiveData = new byte[1024];
	byte[] sendData = new byte[1024];
	String response = null;
	int numberOfItems = 0;
	FileOutputStream fileouputt = null;
	File file = null;

	/*public StopandWaitServer(int portNumber, String fileName) throws SocketException, FileNotFoundException {
		prepareGUI();
		this.portNumber = portNumber + 1;
		this.fileName = fileName;
		serverSocket = new DatagramSocket(portNumber + 1);
		// sendServerSocket=new DatagramSocket((portNumber+1));
		file = new File(fileName);
		fileouputt = new FileOutputStream(fileName);

	}

	public void receiveDataThroughStopandWait() throws IOException {
		// System.out.println("Started receiving Data");
		textArea.append("\nStarted receiving Data");
		while (true) {
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			// System.out.println("receivedData:" + receiveData.length);
			// textArea.append("\nreceivedData:" + receiveData.length);
			try {
				serverSocket.setSoTimeout(100000);
				serverSocket.receive(receivePacket);
			} catch (SocketTimeoutException ex) {
				serverSocket.close();
				System.exit(0);
				fileouputt.close();
			}
			receiveData = receivePacket.getData();
			int len = receivePacket.getLength();
			// System.out.println("Length of the file::" + len);
			fileouputt.write(receiveData);
			// FileUtils.writeByteArrayToFile(file, receiveData);
			int fileLength = (int) file.length();
			// System.out.println("Data Received:::::::::::::" +
			// receiveData[1022]);
			textArea.append("\nReceived packet number:" + receiveData[1022]);
			if (fileLength > 0) {
				response = "Success" + receiveData[1022];
				// System.out.println("response;;;;;;;;'" + response);
			} else
				response = "Failure" + receiveData[1022];

			sendData = response.getBytes();
			InetAddress IPAddress = receivePacket.getAddress();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, (portNumber - 1));
			serverSocket.send(sendPacket);
			textArea.append("-----------------------------------------------------");
		}
	}*/
	public StopandWaitServer(int portNumber, String fileName) throws SocketException, FileNotFoundException {
		prepareGUI();
		this.portNumber = portNumber;
		this.fileName = fileName;
		receiveServerSocket = new DatagramSocket(portNumber + 3);
		sendServerSocket=new DatagramSocket((portNumber+2));
		file = new File(fileName);
		fileouputt = new FileOutputStream(fileName);

	}

	public void receiveDataThroughStopandWait() throws IOException {
		// System.out.println("Started receiving Data");
		textArea.append("\nStarted receiving Data");
		details.setVisible(true);
		while (true) {
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			// System.out.println("receivedData:" + receiveData.length);
			// textArea.append("\nreceivedData:" + receiveData.length);
			try {
				receiveServerSocket.setSoTimeout(100000);
				receiveServerSocket.receive(receivePacket);
			} catch (SocketTimeoutException ex) {
				receiveServerSocket.close();
				System.exit(0);
				fileouputt.close();
			}
			receiveData = receivePacket.getData();
			int len = receivePacket.getLength();
			// System.out.println("Length of the file::" + len);
			fileouputt.write(receiveData);
			// FileUtils.writeByteArrayToFile(file, receiveData);
			int fileLength = (int) file.length();
			// System.out.println("Data Received:::::::::::::" +
			// receiveData[1022]);
			textArea.append("\nReceived packet number:" + receiveData[1022]);
			details.setVisible(true);
			if (fileLength > 0) {
				response = "Success" + receiveData[1022];
				// System.out.println("response;;;;;;;;'" + response);
			} else
				response = "Failure" + receiveData[1022];

			sendData = response.getBytes();
			InetAddress IPAddress = receivePacket.getAddress();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, (portNumber +1));
			sendServerSocket.send(sendPacket);
			textArea.append("\n-----------------------------------------------------");
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
		details = new JFrame("Receiving packets details:");
		details.setSize(800, 800);
		details.setLayout(new GridLayout(1, 1));
		details.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		details.add(controlPanel);
		details.pack();
		textArea.append("This is string one");
		details.setVisible(true);
	}
}
