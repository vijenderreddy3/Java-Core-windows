//package com.dudesameerkhn.cosc635;

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
import javax.swing.SwingConstants;

public class MainApp extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	public static final int PROTOCOL_SAW = 0;
	public static final int PROTOCOL_GBN = 1;
	public static final int DEFAULT_PORT = 8888;

	MainApp main_window;
	
	JComboBox<String> cmb_prpto;
	
	JButton btn_listen;
	JButton btn_send;
	JButton btn_select_file;

	JTextField	txt_host;
	JTextField	txt_port;
	JTextField	txt_simulation;
	JLabel		status_totla;
	JLabel		status_sent;
	JLabel		status_loss;
	JLabel		status_file;

	ThreadReceiver threadReceiver = null;
	ThreadSender threadSender = null;
	
	File file_for_send = null;
	String protocols[]={"Stop and Wait","Go Back N"};
	
	public MainApp() {
		super("COSC635 SAW/GBN [dudesameerkhn]");

		setLayout(new GridLayout(6,1,10,10));

		txt_host = new JTextField("localhost");
		txt_port = new JTextField(String.valueOf(DEFAULT_PORT));
		txt_simulation = new JTextField(String.valueOf("1"));
		cmb_prpto = new JComboBox<>(protocols);
		cmb_prpto.setSelectedIndex(0);

		btn_select_file = new JButton("select file");
		btn_select_file.addActionListener(this);

		btn_listen = new JButton("listen");
		btn_listen.addActionListener(this);
		
		btn_send = new JButton("send");
		btn_send.addActionListener(this);

		status_totla = new JLabel("total:0");
		status_sent = new JLabel("sent:0");
		status_loss = new JLabel("loss:0");
		status_file = new JLabel("  File:[None]");
		
		//row 1
		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(1,2,5,5));
		panel1.add(new JLabel("protocol :", SwingConstants.RIGHT));
		panel1.add(cmb_prpto);
		add(panel1);
		
		//row 2
		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(1,4,5,5));
		panel2.add(new JLabel("IP :", SwingConstants.RIGHT));
		panel2.add(txt_host);
		panel2.add(new JLabel("Port :", SwingConstants.RIGHT));
		panel2.add(txt_port);
		add(panel2);

		//row 3
		JPanel panel3 = new JPanel();
		panel3.setLayout(new GridLayout(1,2,5,5));
		panel3.add(status_file);
		panel3.add(btn_select_file);
		add(panel3);

		//row 4
		JPanel panel4 = new JPanel();
		panel4.setLayout(new GridLayout(1,3,5,5));
		panel4.add(new JLabel("simulation :", SwingConstants.RIGHT));
		panel4.add(txt_simulation);
		panel4.add(btn_send);
		add(panel4);
		
		//row 5
		JPanel panel5 = new JPanel();
		panel5.setLayout(new GridLayout(1,4,5,5));
		panel5.add(new JLabel("[STATUS]", SwingConstants.CENTER));
		panel5.add(status_totla);
		panel5.add(status_sent);
		panel5.add(status_loss);
		add(panel5);

		//row 6
		add(btn_listen);
	}	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(btn_listen == arg0.getSource()) {
			int proto = cmb_prpto.getSelectedIndex();
			int port = Integer.valueOf(txt_port.getText());
			if(port<1000) {
				JOptionPane.showMessageDialog(null, "port incorrect!");
				return;
			}
			btn_listen.setEnabled(false);
			threadReceiver = new ThreadReceiver(proto, port);
			threadReceiver.start();
		} else if(btn_send==arg0.getSource()) {
			if(file_for_send==null){
				JOptionPane.showMessageDialog(null, "please select file for send!");
				return;
			}
			String ip = txt_host.getText();
			int proto = cmb_prpto.getSelectedIndex();
			int port = Integer.valueOf(txt_port.getText());
			int sim = Integer.valueOf(txt_simulation.getText());
			if(port<1000) {
				JOptionPane.showMessageDialog(null, "port incorrect!");
				return;
			}
			if(sim<1 && sim>99) {
				JOptionPane.showMessageDialog(null, "simulation range is [0..99]");
				return;
			}
			
			btn_send.setEnabled(false);
			threadSender = new ThreadSender(ip, port, proto, sim, file_for_send);
			threadSender.start();
		} else if(btn_select_file==arg0.getSource()) {
			JFileChooser dlg = new JFileChooser(new File("."));
			int ret = dlg.showOpenDialog(main_window);
			if (ret == JFileChooser.APPROVE_OPTION) {
               file_for_send = dlg.getSelectedFile();
               status_file.setText(String.format("  File:[%s]", file_for_send.getName()));
            }
		}
	}
	
	public class ThreadReceiver extends Thread {

		public int mProtocol;
		public int mPort;
		public boolean mRun;
		
		ThreadReceiver(int protocol, int port) {
			mProtocol = protocol;
			mPort = port;
		}

		public void run() {
			boolean result = false;
			if(mProtocol==PROTOCOL_SAW) {
				try {
					SAWReceiver receiver = new SAWReceiver(mPort, "COSC635_P2_DataRecieved.txt");
					result = receiver.receive();
				} catch (SocketException e) {
				} catch (Exception e) {
				}
			} else if(mProtocol==PROTOCOL_GBN) {
				try {
					GBNReceiver receiver = new GBNReceiver(mPort, "COSC635_P2_DataRecieved.txt");
					result = receiver.receive();
				} catch (SocketException e) {
				} catch (Exception e) {
				}
			}
			if(result)
				JOptionPane.showMessageDialog(null, "Received new file!!!");
			btn_listen.setEnabled(true);
		}
	}

	public class ThreadSender extends Thread {
		String mIp;
		String mFile;
		public int mProtocol;
		public int mPort;
		public int mSim;
		
		ThreadSender(String ip, int port, int protocol, int simulation, File file) {
			mIp = ip;
			mProtocol = protocol;
			mPort = port;
			mSim = simulation;
			mFile = file.getPath();
		}
		
		public void run() {
			boolean result = false;
			if(mProtocol==PROTOCOL_SAW) {
				SAWSender sender;
				try {
					sender = new SAWSender(mIp, mPort, mSim, mFile);
					try {
						result = sender.send();
					} catch (Exception e) {
						result = false;
						e.printStackTrace();
					}
					if(result) {
						status_totla.setText(String.valueOf(sender.totalPacket));
						status_sent.setText(String.valueOf(sender.packet_sent));
						status_loss.setText(String.valueOf(sender.packet_loss));
					}
				} catch (SocketException e) {
					JOptionPane.showMessageDialog(null, "*Error\ncreate socket!");
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(null, "*Error\nfile not found!");
					e.printStackTrace();
				} catch (UnknownHostException e) {
					JOptionPane.showMessageDialog(null, "*Error\ninvalid host!");
					e.printStackTrace();
				}
			} else if(mProtocol==PROTOCOL_GBN) {
				try {
					GBNSender sender = new GBNSender(mIp, mPort, mSim, mFile);
					try {
						result = sender.send();
					} catch (Exception e) {
						result = false;
					}
					if(result) {
						status_totla.setText(String.valueOf(sender.packet_total));
						status_sent.setText(String.valueOf(sender.packet_sent));
						status_loss.setText(String.valueOf(sender.packet_loss));
					}
				} catch (SocketException e) {
					JOptionPane.showMessageDialog(null, "*Error\ncreate socket!");
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(null, "*Error\nfile not found!");
					e.printStackTrace();
				} catch (UnknownHostException e) {
					JOptionPane.showMessageDialog(null, "*Error\ninvalid host!");
					e.printStackTrace();
				}
			}
			btn_send.setEnabled(true);
		}
	}
	
	public static void main(String[] args) {
		MainApp mainFrame = new MainApp();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setResizable(false);
		mainFrame.setSize(430, 320);
		mainFrame.setLocation(400, 300);
		mainFrame.setVisible(true);
	}
}
