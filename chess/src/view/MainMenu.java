package view;


import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import model.Const;
import model.process.LoadData;

import view.gameplay.GamePlay;
import java.net.*;
import java.io.*;


public class MainMenu extends JFrame{
	JTextField textPortServer;
	JTextField TextPortClient;
	JTextField textInetAddress;
	JTextField textUsername;
	
	public static int random_client;
	public static int random_server;
	public static int PORT_GAME;
	public static String IP ;
	public static String Username;
	public ServerSocket serverSocketGame;
	public List<String> online_users=new ArrayList<String>();
	public List<String> client_online_users=new ArrayList<String>();
	public List<String> client_players_list=new ArrayList<String>();
	public static Socket socketGame;
	
	public JPanel contenPane;
	public HumanVSComPanel comPanel;
	public HumanVSHumanPanel huPanel;

	public JLabel player_name;
	public CardLayout car;
	public DPanel mainMenu;
	
	public JDialog dialog;
	public Ww Wframe;
	public Username Uframe;
	 private BufferedImage image;
	
	public MainMenu()
	{
		super("Chess");
		image = LoadData.getImage(".\\res\\Chess.jpg");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 500);
		setResizable(false);
		
		
		contenPane = new JPanel();
		contenPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contenPane);
		contenPane.setLayout(car);

		
		mainMenu = new DPanel();
		contenPane.add(mainMenu);
		
		comPanel = new HumanVSComPanel();
		comPanel.setVisible(false);
		contenPane.add(comPanel);
		
		huPanel = new HumanVSHumanPanel();
		huPanel.setVisible(false);
		contenPane.add(huPanel);
		
		dialog = new JDialog();
		dialog.setVisible(false);
		Wframe=new Ww();
		Wframe.setVisible(false);
		Uframe=new Username();
		Uframe.setVisible(false);
		setVisible(true);
	}
	
	public class DPanel extends JPanel{
	    public DPanel() {
	    	this.setBounds(0, 0, 500,500);
			this.setLayout(null);
	    	
	       
	    	JButton btnComAndCom = new JButton("Human VS COM");
			btnComAndCom.setForeground(new Color(124, 252, 0));
			btnComAndCom.setBackground(new Color(128, 0, 0));
			btnComAndCom.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					DPanel.this.setVisible(false);
					comPanel.setVisible(true);
				}
			});
			btnComAndCom.setBounds(171, 194, 160, 53);
			this.add(btnComAndCom);
			
			JButton btnHuAndHu = new JButton("Human VS Human");
			btnHuAndHu.setForeground(new Color(124, 252, 0));
			btnHuAndHu.setBackground(new Color(128, 0, 0));
			btnHuAndHu.setBounds(171, 275, 160 , 53);
			btnHuAndHu.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					DPanel.this.setVisible(false);
					huPanel.setVisible(true);
				}
			});
			this.add(btnHuAndHu);
			
			JButton btnExit = new JButton("EXIT");
			btnExit.setForeground(new Color(124, 252, 0));
			btnExit.setBackground(new Color(128, 0, 0));
			btnExit.setBounds(171, 362, 160, 53);
			btnExit.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.exit(0);
				}
			});
			this.add(btnExit);
	    }

	    @Override
	    public void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        g.drawImage(image, 0, 0, 500, 500, null);     
	    }
	}
	
	
	public class HumanVSComPanel extends JPanel
	{
		public HumanVSComPanel()
		{
			this.setBounds(0, 0, 500,500);
			this.setLayout(null);
			
			final JSlider slider = new JSlider();
			slider.setPaintTicks(true);
			slider.setPaintLabels(true);
			slider.setSnapToTicks(true);
			slider.setMinimum(1);
			slider.setMaximum(5);
			slider.setValue(4);
			slider.setForeground(new Color(0, 0, 0));
			slider.setBounds(142, 94, 312, 50);
			slider.setMajorTickSpacing(1);
			slider.setMinorTickSpacing(1);
			this.add(slider);
			
			JLabel lblLevel = new JLabel("Level  :");
			lblLevel.setBounds(26, 105, 80, 14);
			this.add(lblLevel);
			
			final JRadioButton rdbtnBlack = new JRadioButton("Black  ");
			rdbtnBlack.setBounds(26, 174, 109, 23);
			this.add(rdbtnBlack);
			
			final JRadioButton rdbtnWhite = new JRadioButton("White");
			rdbtnWhite.setBounds(26, 236, 109, 23);
			rdbtnWhite.setSelected(true);
			this.add(rdbtnWhite);
			
			ButtonGroup bg = new ButtonGroup();
			bg.add(rdbtnWhite);
			bg.add(rdbtnBlack);
			
			JButton btnBack = new JButton("BACK");
			btnBack.setBounds(89, 343, 89, 23);
			btnBack.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					mainMenu.setVisible(true);
					HumanVSComPanel.this.setVisible(false);
				}
			});
			
			this.add(btnBack);
			
			JButton btnNext = new JButton("NEXT");
			btnNext.setBounds(295, 343, 89, 23);
			btnNext.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					int color = -1;
					int depth = -1;
					int val = -1;
					
					if(rdbtnBlack.isSelected())
						color = Const.BLACK;
					if(rdbtnWhite.isSelected())
						color = Const.WHITE;
						
					switch(slider.getValue()){
					case 1 :
						depth = 3;
						val = Const.EVAL_VM;
						break;
					case 2 :
						depth = 4;
						val = Const.EVAL_VM;
						break;
					case 3 :
						depth = 3;
						val = Const.EVAL_VML;
						break;
					case 4 :
						depth = 4;
						val = Const.EVAL_VML;
						break;
					case 5 :
						depth = 5;
						val = Const.EVAL_VML;
						break;
					}
					
					MainMenu.this.setVisible(false);
					new GameView(MainMenu.this, Const.HUMAN_VS_COM, color, depth, val);
				}
			});
			this.add(btnNext);
		}
	}
	
	public class HumanVSHumanPanel extends JPanel
	{
		JPanel panel;
		JPanel panel_1;
		JPanel panel_2;
		
		JTextField textPortServer;
		JTextField TextPortClient;
		JTextField textInetAddress;
		
		JRadioButton rdbtnServer;
		JRadioButton rdbtnClient;
		
		public HumanVSHumanPanel()
		{
			this.setBounds(0, 0, 500,500);
			this.setLayout(null);
			
			
			final JRadioButton rdbtnCom = new JRadioButton("On a computer ");
			rdbtnCom.setSelected(true);
			rdbtnCom.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					enablePanel(false, panel);
					enablePanel(false, panel_2);
					enablePanel(false, panel_1);
				}
			});
			
			rdbtnCom.setBounds(39, 52, 198, 23);
			this.add(rdbtnCom);
			
			final JRadioButton rdbtnOnALan = new JRadioButton("On a Lan network");
			rdbtnOnALan.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					enablePanel(true, panel);
					
					if(rdbtnClient.isSelected())
						enablePanel(true, panel_2);
					else
						enablePanel(false, panel_2);
					
					if(rdbtnServer.isSelected())
						enablePanel(true, panel_1);
					else
						enablePanel(false, panel_1);
					
				}
			});
			rdbtnOnALan.setBounds(39, 111, 181, 23);
			this.add(rdbtnOnALan);
			
			ButtonGroup bg = new ButtonGroup();
			bg.add(rdbtnOnALan);
			bg.add(rdbtnCom);
			
			panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel.setBounds(39, 160, 395, 257);
			this.add(panel);
			panel.setLayout(null);
			
			rdbtnServer = new JRadioButton("Server ");
			rdbtnServer.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					enablePanel(false, panel_2);
					enablePanel(true, panel_1);
				}
			});
			rdbtnServer.setBounds(10, 29, 109, 23);
			rdbtnServer.setSelected(true);
			panel.add(rdbtnServer);
			
			rdbtnClient = new JRadioButton("Client");
			rdbtnClient.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					enablePanel(false, panel_1);
					enablePanel(true, panel_2);
				}
			});
			rdbtnClient.setBounds(10, 115, 109, 23);
			panel.add(rdbtnClient);
			
			ButtonGroup bg1 = new ButtonGroup();
			bg1.add(rdbtnClient);
			bg1.add(rdbtnServer);
			
			panel_1 = new JPanel();
			panel_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_1.setBounds(25, 53, 345, 55);
			panel.add(panel_1);
			panel_1.setLayout(null);
			
			JLabel lblPort = new JLabel("Port  :");
			lblPort.setBounds(10, 8, 78, 14);
			panel_1.add(lblPort);
			
			textPortServer = new JTextField();
			textPortServer.setBounds(133, 5, 176, 30);
			panel_1.add(textPortServer);
			textPortServer.setColumns(10);
			
			panel_2 = new JPanel();
			panel_2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_2.setBounds(25, 145, 343, 89);
			panel.add(panel_2);
			panel_2.setLayout(null);
			
			JLabel lblPort_1 = new JLabel("Port :");
			lblPort_1.setBounds(10, 47, 69, 14);
			panel_2.add(lblPort_1);
			
			JLabel lblInetaddress = new JLabel("InetAddress  :");
			lblInetaddress.setBounds(10, 22, 85, 14);
			panel_2.add(lblInetaddress);
			
			textInetAddress = new JTextField();
			textInetAddress.setBounds(129, 19, 181, 30);
			panel_2.add(textInetAddress);
			textInetAddress.setColumns(10);
			
			TextPortClient = new JTextField();
			TextPortClient.setBounds(129, 58, 181, 30);
			panel_2.add(TextPortClient);
			TextPortClient.setColumns(10);
			
			JButton btnBack = new JButton("BACK");
			btnBack.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					mainMenu.setVisible(true);
					HumanVSHumanPanel.this.setVisible(false);
				}
			});
			btnBack.setBounds(229, 428, 89, 23);
			this.add(btnBack);
			
			JButton btnNext = new JButton("NEXT");
			btnNext.setBounds(345, 428, 89, 23);
			btnNext.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					if(rdbtnCom.isSelected()){
						MainMenu.this.setVisible(false);
						new GameView(MainMenu.this, Const.HUMAN_VS_HUMAN);
					}
					else if(rdbtnOnALan.isSelected()){
						if(rdbtnServer.isSelected()){
							PORT_GAME=Integer.parseInt(textPortServer.getText());
							MainMenu.this.setVisible(false);
							Wframe.setVisible(true);
							start();
						}
						else if(rdbtnClient.isSelected()){
							try {
								PORT_GAME=Integer.parseInt(TextPortClient.getText());
								IP=textInetAddress.getText();
								
								socketGame = new Socket(IP, PORT_GAME);
								
								//Usernamae.send_uername(socketGame);
								/*random_client = (int)(Math.random()*2);
								DataOutputStream outToServer = new DataOutputStream(socketGame.getOutputStream());
								outToServer.writeInt(random_client);*/
								HumanVSHumanPanel.this.setVisible(false);
								MainMenu.this.setVisible(false);
								Uframe.setVisible(true);
								//Wframe.message.setVisible(true);
								//Wframe.message1.setVisible(false);
								//new GameView(MainMenu.this, Const.HUMAN_VS_HUMAN_LAN, false, socketGame, serverSocketGame,random_client);
								//MainMenu.this.setVisible(false);
							} catch (UnknownHostException e1) {
								e1.printStackTrace();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							
						}
					}
				}
			});
			this.add(btnNext);
			
			enablePanel(false, panel);
			enablePanel(false, panel_2);
			enablePanel(false, panel_1);
			
		}
	}
	
	public void enablePanel(boolean bool,JPanel panel)
	{
		Component [] com = panel.getComponents();
		
		if(bool == true)
		{
			for (int i = 0 ; i < com.length ; i++)
				com[i].setEnabled(true);
		}
		else
		{
			for (int i = 0 ; i< com.length ; i++)
				com[i].setEnabled(false);
		}
	}
	public class JDialog extends JFrame{
		private JLabel message;
		private JLabel message1;
		public JDialog(){
			super("Waiting");
			getContentPane().setLayout(null);
			setBounds(0, 0, 1000, 750);
			setLocationRelativeTo(null);
			
			setDefaultCloseOperation(EXIT_ON_CLOSE);	
			
			message = new JLabel("Waiting for client ...");
			getContentPane().add(message);
			message.setBounds(90, 0, 210, 90);
			setResizable(false);
			message1 = new JLabel("Player1 one waiting for client ...");
			getContentPane().add(message1);
			message1.setBounds(90, 50, 210, 90);
			setResizable(false);
			
		}
	}
	public class Ww extends JFrame{
		private JLabel message;
		private JLabel message1;
		public Ww(){
			super("Waiting 2");
			getContentPane().setLayout(null);
			setBounds(0, 0, 750, 750);
			setLocationRelativeTo(null);
			
			setDefaultCloseOperation(EXIT_ON_CLOSE);	
			
			message = new JLabel("Waiting for player ...");
			getContentPane().add(message);
			message.setBounds(90, 0, 210, 90);
			setResizable(false);
			message1 = new JLabel("Player1 one waiting for client ...");
			getContentPane().add(message1);
			message.setBounds(90, 100, 210, 90);
			setResizable(false);
			JButton btnJoin = new JButton("Join");
			btnJoin.setForeground(new Color(124, 252, 0));
			btnJoin.setBackground(new Color(128, 0, 0));
			btnJoin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Ww.this.setVisible(false);
					new GameView(MainMenu.this, Const.HUMAN_VS_HUMAN_LAN, false, socketGame, serverSocketGame,random_client);
				}
			});
			btnJoin.setBounds(220,0, 100, 30);
			this.add(btnJoin);
		}
	}
	
	public class Username extends JFrame{
		private JLabel message;
		private JLabel message1;
		public Username(){
			super("Login");
			getContentPane().setLayout(null);
			setBounds(0, 0, 250, 250);
			setLocationRelativeTo(null);
			
			setDefaultCloseOperation(EXIT_ON_CLOSE);	
			JLabel lbUsername = new JLabel("UserName  :");
			lbUsername.setBounds(10, 10, 78, 14);
			getContentPane().add(lbUsername);
			
			textUsername = new JTextField();
			textUsername.setBounds(10, 10, 176, 30);
			getContentPane().add(textUsername);
			textUsername.setColumns(10);
			JButton btnok = new JButton("Ok");
			btnok.setForeground(new Color(124, 252, 0));
			btnok.setBackground(new Color(128, 0, 0));
			btnok.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent arg0) {
					Username=textUsername.getText();
					send_username();
					Uframe.setVisible(false);
					receive_usernames();
					Wframe.setVisible(true);
					player_name =new JLabel("List of Players available online" );
					getContentPane().add(player_name);
					message.setBounds(90,0, 210, 90);
					setResizable(false);
					int position=0;
					for(int i=0;i<client_online_users.size();i++){
						player_name =new JLabel("Player "+client_online_users.get(i));
						getContentPane().add(player_name);
						message.setBounds(90, position+20, 210, 90);
						position=position+20;
						setResizable(false);
					}
					
					//new GameView(MainMenu.this, Const.HUMAN_VS_HUMAN_LAN, false, socketGame, serverSocketGame,random_client);
				}
			});
			btnok.setBounds(220,0, 100, 30);
			this.add(btnok);
		}
		
		public void send_username(){
			
			try{
				while(true){
				DataOutputStream outToServer = new DataOutputStream(socketGame.getOutputStream());
				outToServer.writeChars(Username);
				}
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
public void receive_usernames(){
			
			try{
				DataInputStream inputStream = new DataInputStream(socketGame.getInputStream());
				String temp;
				while((temp=inputStream.readLine()) != null){
					client_online_users.add(temp);	
				}
				//String temp=inputStream.readLine(); 
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	public void start()
    {
        try(ServerSocket serverSocket = new ServerSocket(PORT_GAME))
        {
            System.out.println("Server is up on " + PORT_GAME + " ...");
            while(true)
            {
                Socket socket = serverSocket.accept();
                new Thread(() -> new WaitingThread()).start();
            }
        }catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }
	public class WaitingThread extends Thread{
		public void run(){
			try {
				
			    serverSocketGame = new ServerSocket(PORT_GAME);
				socketGame = serverSocketGame.accept();
				 DataInputStream usernameFromClient = new DataInputStream(socketGame.getInputStream());
		            String temp = usernameFromClient.readLine();
		            online_users.add(temp);
		            //random_server=(temp+1)%2;
		        DataOutputStream usernamesoutputStream = new DataOutputStream(socketGame.getOutputStream());
		            
		            for(int i=0;i<online_users.size();i++){
		       
		            		if(online_users.get(i)==Username) {
		            			continue;
		            		}
		            		else{
		            			usernamesoutputStream.writeChars(online_users.get(i));	
		            
		            		}
		            }
		            
				new GameView(MainMenu.this, Const.HUMAN_VS_HUMAN_LAN, true, socketGame, serverSocketGame,random_server);
				dialog.setVisible(false);
				//dialog.message.setVisible(false);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}


