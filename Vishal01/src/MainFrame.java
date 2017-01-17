//package com.agundamaneni;

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

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	public static final int PROTOCOL_SAW = 0;
	public static final int PROTOCOL_GBN = 1;
	public static final int DEFAULT_PORT = 5678;

	MainFrame mMainFrame;

	JButton mBtnListen;
	JButton mBtnSend;
	JButton mBtnOpenFile;
	JComboBox<String> mCmbProtocol;
	
	JTextField mTextIp;
	JTextField mTextPort;
	JTextField mTextSim;
	
	JLabel mLabelTotal;
	JLabel mLabelSent;
	JLabel mLabelLoss;

	ReceiverThread mServerThread = null;
	SenderThread mClientThread = null;
	
	File mSendFile = null;
	
	public MainFrame()
	{
		super("SAW, GBN Send/Receiver");

		setLayout(new GridLayout(4,4,10,10));

		mTextIp = new JTextField("localhost");
		mTextPort = new JTextField(String.valueOf(DEFAULT_PORT));
		mTextSim = new JTextField(String.valueOf("1"));
		
		mLabelTotal = new JLabel("0");
		mLabelSent = new JLabel("0");
		mLabelLoss = new JLabel("0");
		
		add(new JLabel("IP :", SwingConstants.RIGHT));
		add(mTextIp);
		add(new JLabel("Total :", SwingConstants.RIGHT));
		add(mLabelTotal);
		
		add(new JLabel("Port :", SwingConstants.RIGHT));
		add(mTextPort);
		add(new JLabel("Sent :", SwingConstants.RIGHT));
		add(mLabelSent);
		
		add(new JLabel("Simulation:", SwingConstants.RIGHT));
		add(mTextSim);
		add(new JLabel("Loss :", SwingConstants.RIGHT));
		add(mLabelLoss);
		
		mCmbProtocol = new JComboBox<>();
		mCmbProtocol.insertItemAt("Stop and Wait", 0);
		mCmbProtocol.insertItemAt("Go Back N", 1);
		mCmbProtocol.setSelectedIndex(0);
		add(mCmbProtocol);

		mBtnListen = new JButton("Listen");
		mBtnListen.addActionListener(new ActionListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int proto = mCmbProtocol.getSelectedIndex();
				int port = Integer.valueOf(mTextPort.getText());
				if(port<1000) {
					JOptionPane.showMessageDialog(null, "Please input valid port!");
					return;
				}
				mBtnListen.setEnabled(false);
				
				mServerThread = new ReceiverThread(proto, port);
				mServerThread.start();
			}
		});
		add(mBtnListen);

		mBtnSend = new JButton("Send");
		mBtnSend.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(mSendFile==null){
					JOptionPane.showMessageDialog(null, "Please select file for Send!");
					return;
				}

				String ip = mTextIp.getText();
				int proto = mCmbProtocol.getSelectedIndex();
				int port = Integer.valueOf(mTextPort.getText());
				int sim = Integer.valueOf(mTextSim.getText());
				if(port<1000) {
					JOptionPane.showMessageDialog(null, "Please input valid port!");
					return;
				}
				if(sim<1 && sim>99) {
					JOptionPane.showMessageDialog(null, "Please input valid simulation!\n[0..99]");
					return;
				}
				
				mBtnSend.setEnabled(false);
				mClientThread = new SenderThread(ip, port, proto, sim, mSendFile);
				mClientThread.start();
			}
		});
		add(mBtnSend);
		
		mBtnOpenFile = new JButton("Open for Send");
		mBtnOpenFile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {

				JFileChooser dlg = new JFileChooser(new File("."));
				int ret = dlg.showOpenDialog(mMainFrame);
				if (ret == JFileChooser.APPROVE_OPTION) {
	               mSendFile = dlg.getSelectedFile();
	            }
			}
		});
		add(mBtnOpenFile);
	}	

	public class ReceiverThread extends Thread {

		public int mProtocol;
		public int mPort;
		public boolean mRun;
		
		ReceiverThread(int protocol, int port) {
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
				mBtnListen.setEnabled(true);
		}
	}

	public class SenderThread extends Thread {
		String mIp;
		String mFile;
		public int mProtocol;
		public int mPort;
		public int mSim;
		
		SenderThread(String ip, int port, int protocol, int simulation, File file) {
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
						mLabelTotal.setText(String.valueOf(sender.totalPacket));
						mLabelSent.setText(String.valueOf(sender.packet_sent));
						mLabelLoss.setText(String.valueOf(sender.packet_loss));
					}
				} catch (SocketException e) {
					JOptionPane.showMessageDialog(null, "[Error] create socket!");
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(null, "[Error] file not found!");
					e.printStackTrace();
				} catch (UnknownHostException e) {
					JOptionPane.showMessageDialog(null, "[Error] invalid host!");
					e.printStackTrace();
				}
			} else if(mProtocol==PROTOCOL_GBN){
				try {
					GBNSender sender = new GBNSender(mIp, mPort, mSim, mFile);
					try {
						result = sender.send();
					} catch (Exception e) {
						result = false;
					}
					if(result) {
						mLabelTotal.setText(String.valueOf(sender.packet_total));
						mLabelSent.setText(String.valueOf(sender.packet_sent));
						mLabelLoss.setText(String.valueOf(sender.packet_loss));
					}
				} catch (SocketException e) {
					JOptionPane.showMessageDialog(null, "[Error] create socket!");
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(null, "[Error] file not found!");
					e.printStackTrace();
				} catch (UnknownHostException e) {
					JOptionPane.showMessageDialog(null, "[Error] invalid host!");
					e.printStackTrace();
				}
			}
			
			mBtnSend.setEnabled(true);
		}
	}
	
	public static void main(String[] args) {
		MainFrame mainFrame = new MainFrame();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setResizable(false);
		mainFrame.setSize(530, 220);
		
		mainFrame.setLocation(100, 100);
		mainFrame.setVisible(true);
	}
}
