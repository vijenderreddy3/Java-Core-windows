
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Test1 {

	private JFrame frame;
	private JLabel headerLabel;
	private JLabel statusLabel;
	private JPanel controlPanel;
	private JPanel radioPanel;
	private JPanel comboPanel;
	private JPanel buttonPanel;
	String algorithm = null;
	static String receivedFileName = "COSC635_P2_DataRecieved.txt";
	static String sendFilename = "COSC635_P2_DataSent.txt";

	public Test1() {
		prepareGUI();
	}

	public static void main(String[] args) {
		Test1 obj = new Test1();
		obj.showTextFieldDemo();
	}

	private void prepareGUI() {
		frame = new JFrame("File Transfer");
		frame.setSize(400, 400);
		frame.setLayout(new GridLayout(6, 1));
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		headerLabel = new JLabel("", JLabel.CENTER);
		statusLabel = new JLabel("", JLabel.CENTER);

		statusLabel.setSize(350, 100);

		controlPanel = new JPanel();
		comboPanel = new JPanel();
		radioPanel = new JPanel();
		buttonPanel = new JPanel();
		// controlPanel.setLayout(new FlowLayout());
		controlPanel.setLayout(new GridLayout(3, 2));
		frame.add(headerLabel);
		frame.add(radioPanel);
		frame.add(comboPanel);
		frame.add(controlPanel);
		frame.add(buttonPanel);
		frame.add(statusLabel);
		frame.setVisible(true);
	}

	private void showTextFieldDemo() {
		JPanel textip = new JPanel();
		textip.setSize(150, 35);
		JPanel textport = new JPanel();
		textport.setSize(150, 35);
		JPanel textpseu = new JPanel();
		textpseu.setSize(150, 35);
		JLabel ipAddress = new JLabel("IP Address: ", JLabel.RIGHT);
		final JTextField ipAddressText = new JTextField(10);
		textip.add(ipAddressText);
		JLabel portNumber = new JLabel("PortNumber: ", JLabel.RIGHT);
		final JTextField portNumberText = new JTextField(10);
		textport.add(portNumberText);
		JLabel pseudoNumber = new JLabel("pseudoNumber: ", JLabel.RIGHT);
		final JTextField pseudoNumberText = new JTextField(10);
		textpseu.add(pseudoNumberText);
		ActionListener sendOrReceiveListner;
		
		sendOrReceiveListner =	new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
			String buttonClicked = ((JButton) e.getSource()).getText();
			System.out.println(buttonClicked);
			if(buttonClicked.equals("send")){
				String ip=ipAddressText.getText();
				int pseudoNum =Integer.valueOf(pseudoNumberText.getText()); 
        		int port=Integer.valueOf(portNumberText.getText());
				if(algorithm.equals("SAW")){
	        			
		        			statusLabel.setText("Succesfully sent all the data");
		        		
				}else if(algorithm.equals("GBN")){
					
				}
			}else if(buttonClicked.equals("receive")){
				int port=Integer.valueOf(portNumberText.getText());
				if(algorithm.equals("SAW")){
	        			
							statusLabel.setText("Succesfully received all the data");
		        		
				}else if(algorithm.equals("GBN")){
					
				}
			}
		}
	};
		JButton sendB = new JButton("send");
		sendB.addActionListener(sendOrReceiveListner);
		JButton receiveB = new JButton("receive");
		receiveB.addActionListener(sendOrReceiveListner);
		
		headerLabel.setText("File Transfer using SAW and GBN");
		ActionListener radioButtonActionListener;

		String[] choice = { "--Select--", "SEND", "RECEIVE" };
		final JComboBox<String> sr = new JComboBox<String>(choice);
		sr.setSelectedIndex(0);
		sr.setSize(200, 30);
		sr.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JComboBox<String> selected = (JComboBox<String>) e.getSource();
				String sendOrReceive = (String) selected.getSelectedItem();

				if (sendOrReceive.equals("SEND")) {
					portNumber.setHorizontalAlignment(JLabel.RIGHT);
					controlPanel.removeAll();
					buttonPanel.removeAll();
					controlPanel.add(ipAddress);
					controlPanel.add(textip);
					controlPanel.add(portNumber);
					controlPanel.add(textport);
					controlPanel.add(pseudoNumber);
					controlPanel.add(textpseu);
					buttonPanel.add(sendB);
					controlPanel.revalidate();
					controlPanel.repaint();
					buttonPanel.revalidate();
					buttonPanel.repaint();
					frame.setVisible(true);

				} else if (sendOrReceive.equals("RECEIVE")) {
					portNumber.setHorizontalAlignment(JLabel.CENTER);
					controlPanel.removeAll();
					buttonPanel.removeAll();
					controlPanel.add(portNumber);
					controlPanel.add(textport);
					buttonPanel.add(receiveB);
					controlPanel.revalidate();
					controlPanel.repaint();
					buttonPanel.revalidate();
					buttonPanel.repaint();
					frame.setVisible(true);

				} else {

				}

			}
		});
		radioButtonActionListener = new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				algorithm = ((JRadioButton) e.getSource()).getText();
				comboPanel.add(sr);
				sr.setVisible(true);
				frame.setVisible(true);

			}
		};
		final JRadioButton saw = new JRadioButton("SAW");
		saw.setText("SAW");
		saw.addActionListener(radioButtonActionListener);
		saw.setHorizontalAlignment(AbstractButton.CENTER);
		final JRadioButton gbn = new JRadioButton("GBN");
		gbn.setText("GBN");
		gbn.addActionListener(radioButtonActionListener);
		gbn.setHorizontalAlignment(AbstractButton.CENTER);
		ButtonGroup bG = new ButtonGroup();
		bG.add(saw);
		bG.add(gbn);
		radioPanel.add(saw);
		radioPanel.add(gbn);

		frame.setVisible(true);
	}
}
