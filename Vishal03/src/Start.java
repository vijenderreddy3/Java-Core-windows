//package com.johnglen.cosc635;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Start extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	public static final int PROT_SAW = 0;
	public static final int PROT_GBN = 1;
	public static final int UDP_PORT = 5555;

	Start mainframe;

	JButton buttonReceive;
	JButton buttonSend;
	JButton buttonSelect;
	JComboBox<String> comboboxProtocol;
	JTextField textIpaddress;
	JTextField textPort;
	JTextField textSimulation;
	JLabel labelFile;
	JLabel labelStatus;
	
	File file_send = null;
	String protocols[]={"SAW(Stop and Wait)","GBN(Go Back N)"};
	
	public Start() {
		super("[COSC635] *by johnglen*");

		textIpaddress = new JTextField("localhost");
		textPort = new JTextField(String.valueOf(UDP_PORT));
		textSimulation = new JTextField(String.valueOf("1"));
		
		labelFile = new JLabel("file[null]");
		labelStatus = new JLabel("Total:0, Sent:0, Loss:0");
		
		setLayout(new GridLayout(7,2,10,10));

		add(new JLabel("protocol"));
		add(new JLabel("simulation [0..99]"));

		comboboxProtocol = new JComboBox<>(protocols);
		comboboxProtocol.setSelectedIndex(0);
		add(comboboxProtocol);
		add(textSimulation);

		add(new JLabel("host"));
		add(new JLabel("port"));
		
		add(textIpaddress);
		add(textPort);
		
		add(labelFile);
		add(labelStatus);

		buttonSelect = new JButton("Select File...");
		buttonSelect.addActionListener(this);
		add(buttonSelect);
		buttonSend = new JButton("Send");
		buttonSend.addActionListener(this);
		add(buttonSend);
		
		add(new JPanel());
		buttonReceive = new JButton("Receiving");
		buttonReceive.addActionListener(this);
		add(buttonReceive);
	}
	
	public static void main(String[] args) {
		Start mainFrame = new Start();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setResizable(false);
		mainFrame.setSize(380, 420);
		
		mainFrame.setLocation(100, 100);
		mainFrame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource()==buttonSelect) {
			JFileChooser dlg = new JFileChooser(new File("."));
			int ret = dlg.showOpenDialog(mainframe);
			if (ret == JFileChooser.APPROVE_OPTION) {
               file_send = dlg.getSelectedFile();
               labelFile.setText(String.format("file[%s]", file_send.getName()));
            }
			return;
		}
		
		if(arg0.getSource()==buttonSend) {
			if(file_send==null){
				JOptionPane.showMessageDialog(null, "Please select file");
				return;
			}

			final String ip = textIpaddress.getText();
			final int proto = comboboxProtocol.getSelectedIndex();
			final int port = Integer.valueOf(textPort.getText());
			final int sim = Integer.valueOf(textSimulation.getText());
			if(port<1000) {
				JOptionPane.showMessageDialog(null, "Please input valid port!");
				return;
			}
			if(sim<1 && sim>99) {
				JOptionPane.showMessageDialog(null, "Please input valid simulation!\n[0..99]");
				return;
			}
			
			buttonSend.setEnabled(false);
			
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					boolean result = false;
					if(proto==PROT_SAW) {
						ClientSAW sender;
						try {
							sender = new ClientSAW(ip, port, sim, file_send.getPath());
							try {
								result = sender.send();
							} catch (Exception e) {
								result = false;
								e.printStackTrace();
							}
							if(result)
								labelStatus.setText(String.format("Total %d, Sent %d, Loss %d",sender.totalPacket, sender.packet_sent, sender.packet_loss));
						} catch (SocketException e) {
							JOptionPane.showMessageDialog(null, "[---Warning---]\n create socket!");
							e.printStackTrace();
						} catch (FileNotFoundException e) {
							JOptionPane.showMessageDialog(null, "[---Warning---]\n file not found!");
							e.printStackTrace();
						} catch (UnknownHostException e) {
							JOptionPane.showMessageDialog(null, "[---Warning---]\n invalid host!");
							e.printStackTrace();
						}
					} else if(proto==PROT_GBN){
						try {
							ClientGBN sender = new ClientGBN(ip, port, sim, file_send.getPath());
							try {
								result = sender.send();
							} catch (Exception e) {
								result = false;
							}
							if(result) {
								labelStatus.setText(String.format("Total %d, Sent %d, Loss %d",sender.packet_total, sender.packet_sent, sender.packet_loss));
							}
						} catch (SocketException e) {
							JOptionPane.showMessageDialog(null, "[---Warning---]\n create socket!");
							e.printStackTrace();
						} catch (FileNotFoundException e) {
							JOptionPane.showMessageDialog(null, "[---Warning---]\n file not found!");
							e.printStackTrace();
						} catch (UnknownHostException e) {
							JOptionPane.showMessageDialog(null, "[---Warning---]\n invalid host!");
							e.printStackTrace();
						}
					}
					
					buttonSend.setEnabled(true);
				}
			}).start();
		}
		
		if(arg0.getSource()==buttonReceive) {
			final int proto = comboboxProtocol.getSelectedIndex();
			final int port = Integer.valueOf(textPort.getText());
			if(port<1000) {
				JOptionPane.showMessageDialog(null, "invalid port!");
				return;
			}
			buttonReceive.setEnabled(false);
			
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					boolean result = false;
					if(proto==PROT_SAW) {
						try {
							ServerSAW receiver = new ServerSAW(port, "COSC635_P2_DataRecieved.txt");
							result = receiver.receive();
						} catch (SocketException e) {
						} catch (Exception e) {
						}
					} else if(proto==PROT_GBN) {
						try {
							ServerGBN receiver = new ServerGBN(port, "COSC635_P2_DataRecieved.txt");
							result = receiver.receive();
						} catch (SocketException e) {
						} catch (Exception e) {
						}
					}
					if(result)
						JOptionPane.showMessageDialog(null, "Received new file!!!");
					buttonReceive.setEnabled(true);
				}
			}).start();
		}
	}
}
