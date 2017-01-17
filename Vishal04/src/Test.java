//package com.assig635;

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
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Test extends JFrame {
	private static final long serialVersionUID = 1L;
	
	public static final int PROT_SAW = 0;
	public static final int PROT_GBN = 1;
	public static final int DEFAULT_PORT = 7777;

	File selected_file = null;
	Test testFrame;

	JButton listen_button;
	JButton send_button;
	JButton selectfile_button;
	JComboBox<String> prot_combobox;
	JTextField ipaddress_textbox;
	JTextField port_textbox;
	JTextField simulation_textbox;
	
	JLabel status_label;

	ListenThread listener = null;
	SenderThread sender = null;

	public class ListenThread extends Thread {
		public int listen_protocol;
		public int listen_port;
		
		ListenThread(int protocol, int port) {
			listen_protocol = protocol;
			listen_port = port;
		}

		public void run() {
			boolean result = false;
			if(listen_protocol==PROT_SAW) {
				try {
					StopAndWaitListener receiver = new StopAndWaitListener(listen_port, "COSC635_P2_DataRecieved.txt");
					result = receiver.receive();
				} catch (SocketException e) {
				} catch (Exception e) {
				}
			} else if(listen_protocol==PROT_GBN) {
				try {
					GetBackNListener receiver = new GetBackNListener(listen_port, "COSC635_P2_DataRecieved.txt");
					result = receiver.receive();
				} catch (SocketException e) {
				} catch (Exception e) {
				}
			}
			if(result)
				JOptionPane.showMessageDialog(null, "Received new file!!!");
			listen_button.setEnabled(true);
		}
	}

	public class SenderThread extends Thread {
		String send_ipaddr;
		String send_file;
		
		public int send_protocol;
		public int send_port;
		public int simulation_value;
		
		SenderThread(String ip, int port, int protocol, int simulation, File file) {
			send_ipaddr = ip;
			send_protocol = protocol;
			send_port = port;
			simulation_value = simulation;
			send_file = file.getPath();
		}
		
		public void run() {
			boolean result = false;
			if(send_protocol==PROT_SAW) {
				StopAndWaitSender sender;
				try {
					sender = new StopAndWaitSender(send_ipaddr, send_port, simulation_value, send_file);
					try {
						result = sender.send();
					} catch (Exception e) {
						result = false;
						e.printStackTrace();
					}
					if(result) {
						String msg = String.format("packet count:%d, sent:%d, lossed:%d", sender.send_count,sender.send_tries,sender.send_loss);
						status_label.setText(msg);
					}
				} catch (SocketException e) {
					JOptionPane.showMessageDialog(null, "Failed creating socket!");
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(null, "file not found!");
					e.printStackTrace();
				} catch (UnknownHostException e) {
					JOptionPane.showMessageDialog(null, "invalid network address!");
					e.printStackTrace();
				}
			} else if(send_protocol==PROT_GBN){
				try {
					GetBackNSender sender = new GetBackNSender(send_ipaddr, send_port, simulation_value, send_file);
					try {
						result = sender.send();
					} catch (Exception e) {
						result = false;
					}
					if(result) {
						String msg = String.format("packet total:%d, sent:%d, lossed:%d", sender.packet_total,sender.packet_sent,sender.packet_loss);
						status_label.setText(msg);
					}
				} catch (SocketException e) {
					JOptionPane.showMessageDialog(null, "Failed creating socket!");
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(null, "file not found!");
					e.printStackTrace();
				} catch (UnknownHostException e) {
					JOptionPane.showMessageDialog(null, "invalid network address!");
					e.printStackTrace();
				}
			}
			send_button.setEnabled(true);
		}
	}
	
	public Test()
	{
		super("[Test] Stop And Wait & Get Back N(=5)");

		setLayout(new GridLayout(7,2,10,10));

		ipaddress_textbox = new JTextField("127.0.0.1");
		port_textbox = new JTextField(String.valueOf(DEFAULT_PORT));
		simulation_textbox = new JTextField(String.valueOf("5"));

		status_label = new JLabel("packet total:0, sent:0, lossed:0");

		add(new JLabel("Protocol :", SwingConstants.RIGHT));
		prot_combobox = new JComboBox<>();
		prot_combobox.insertItemAt("Stop and Wait", 0);
		prot_combobox.insertItemAt("Go Back N", 1);
		prot_combobox.setSelectedIndex(0);
		add(prot_combobox);

		add(new JLabel("ip or host addr :", SwingConstants.RIGHT));
		add(ipaddress_textbox);

		add(new JLabel("send or receive port :", SwingConstants.RIGHT));
		add(port_textbox);
		
		add(new JLabel("simulation[0..99]:", SwingConstants.RIGHT));
		add(simulation_textbox);
		
		add(new JLabel("send status :", SwingConstants.RIGHT));
		add(status_label);

		add(new JLabel("please click for receive!", SwingConstants.RIGHT));
		listen_button = new JButton("Receive");
		listen_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int proto = prot_combobox.getSelectedIndex();
				int port = Integer.valueOf(port_textbox.getText());
				if(port<1000) {
					JOptionPane.showMessageDialog(null, "Please input valid port!");
					return;
				}
				listen_button.setEnabled(false);
				
				listener = new ListenThread(proto, port);
				listener.start();
			}
		});
		add(listen_button);

		selectfile_button = new JButton("Select file");
		selectfile_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				JFileChooser chooser = new JFileChooser();
				int ret = chooser.showOpenDialog(testFrame);
				if (ret == JFileChooser.APPROVE_OPTION) {
	               selected_file = chooser.getSelectedFile();
	            }
			}
		});
		add(selectfile_button);

		send_button = new JButton("Send file");
		send_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(selected_file==null){
					JOptionPane.showMessageDialog(null, "Please select file for Send!");
					return;
				}

				String ip = ipaddress_textbox.getText();
				int proto = prot_combobox.getSelectedIndex();
				int port = Integer.valueOf(port_textbox.getText());
				int sim = Integer.valueOf(simulation_textbox.getText());
				if(port<1000) {
					JOptionPane.showMessageDialog(null, "Please input valid port!");
					return;
				}
				if(sim<0 && sim>99) {
					JOptionPane.showMessageDialog(null, "Please input valid simulation!\n[0..99]");
					return;
				}
				send_button.setEnabled(false);
				sender = new SenderThread(ip, port, proto, sim, selected_file);
				sender.start();
			}
		});
		add(send_button);
	}

	public static void main(String[] args) {
		Test mainFrame = new Test();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setResizable(false);
		mainFrame.setSize(500, 320);
		
		mainFrame.setLocation(300, 100);
		mainFrame.setVisible(true);
	}
}
