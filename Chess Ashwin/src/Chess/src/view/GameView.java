package view;


import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import model.CArray;
import model.CBoard;
import model.CPiece;
import model.Const;
import model.Coord;
import model.process.CBProcess;
import model.process.LoadData;
import view.gameplay.GamePlay;
import view.gameplay.HumanVSCom;
import view.gameplay.HumanVSHuman;
import view.gameplay.HumanVSHumanLan;


public class GameView extends JFrame {
	
	public ContentPanel contentPane;
	public JPanel whiteDiePanel;
	public JPanel borderPanel;
	public BoardPanel boardPanel;
	public JPanel blackDiePanel;
	//JPanel menu;
	
	public JLabel lblTime;
	
	public GamePlay gamePlay;
	
	public BufferedImage chessPiece[][];
	//public BufferedImage blackChessPiece[];
	public BufferedImage chooseImage;
	public BufferedImage lossImage;
	public BufferedImage commoveImage;
	
	public int gameType;
	
	MainMenu mainMenu;
	
	public Socket socketGame = null;
	public ServerSocket serverSocket = null;
	
	public ObjectInputStream gameInput;
	public ObjectOutputStream gameOutput;
	
	//public ObjectInputStream chatInput;
	//public ObjectOutputStream chatOutput;
	
	boolean isServer;

	/**
	 * Create the frame of gamePlay human vs com
	 */
	public GameView(MainMenu mainMenu, int gameType, int color, int depth, int val) {
		this.gameType = gameType;
		this.mainMenu = mainMenu;
		
		chessPiece = new BufferedImage[2][];
		chessPiece[Const.WHITE] = LoadData.getWhiteChess();
		chessPiece[Const.BLACK] = LoadData.getBlackChess();
        chooseImage     = LoadData.getImage("./res/choose.png");
        lossImage 	    = LoadData.getImage("./res/loss.png");
        commoveImage    = LoadData.getImage("./res/commove.png");
        
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 1018, 642);
		
		contentPane = new ContentPanel();
		
		contentPane.setBackground(Color.BLACK);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		boardPanel = new BoardPanel();
		contentPane.add(boardPanel);
		
		borderPanel = new BorderPanel();
		contentPane.add(borderPanel);
		
		whiteDiePanel = new DiePanel(Const.WHITE);
		contentPane.add(whiteDiePanel);
		
		blackDiePanel = new DiePanel(Const.BLACK);
		contentPane.add(blackDiePanel);
		
		/*menu = new MenuPanel();
		contentPane.add(menu);*/
		
		lblTime = new JLabel("00:00:00");
		lblTime.setForeground(new Color(120, 120, 120));
		lblTime.setFont(new Font("Consolas", Font.PLAIN, 33));
		lblTime.setBounds(285, 11, 350, 57);
		contentPane.add(lblTime);
		
		setVisible(true);
		
		if(gameType == Const.HUMAN_VS_COM)
			gamePlay = new HumanVSCom(this, color, depth, val);
		else if(gameType == Const.HUMAN_VS_HUMAN)
			gamePlay = new HumanVSHuman(this);
	}
	
	/**
	 * Game view cho Human VS human
	 * @param gameType
	 */
	public GameView(MainMenu mainMenu, int gameType) {
		this.gameType = gameType;
		this.mainMenu = mainMenu;
		
		chessPiece = new BufferedImage[2][];
		chessPiece[Const.WHITE] = LoadData.getWhiteChess();
		chessPiece[Const.BLACK] = LoadData.getBlackChess();
        chooseImage     = LoadData.getImage("./res/choose.png");
        lossImage 	    = LoadData.getImage("./res/loss.png");
        commoveImage    = LoadData.getImage("./res/commove.png");
        
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 1018, 642);
		
		contentPane = new ContentPanel();
		
		contentPane.setBackground(Color.BLACK);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		whiteDiePanel = new DiePanel(Const.WHITE);
		contentPane.add(whiteDiePanel);
		
		boardPanel = new BoardPanel();
		contentPane.add(boardPanel);
		
		borderPanel = new BorderPanel();
		contentPane.add(borderPanel);
		
		blackDiePanel = new DiePanel(Const.BLACK);
		contentPane.add(blackDiePanel);
		
		/*menu = new MenuPanel();
		contentPane.add(menu);*/
		
		lblTime = new JLabel("00:00:00");
		lblTime.setForeground(new Color(120, 120, 120));
		lblTime.setFont(new Font("Consolas", Font.PLAIN, 33));
		lblTime.setBounds(285, 11, 350, 57);
		contentPane.add(lblTime);
		
		setVisible(true);
		
		gamePlay = new HumanVSHuman(this);
	}
	
	public GameView(MainMenu mainMenu, int gameType, boolean isServer, Socket socketGame, ServerSocket serverSocket) {
		this.gameType = gameType;
		this.mainMenu = mainMenu;
		this.isServer = isServer;
		
		chessPiece = new BufferedImage[2][];
		chessPiece[Const.WHITE] = LoadData.getWhiteChess();
		chessPiece[Const.BLACK] = LoadData.getBlackChess();
        chooseImage     = LoadData.getImage("./res/choose.png");
        lossImage 	    = LoadData.getImage("./res/loss.png");
        commoveImage    = LoadData.getImage("./res/commove.png");
        
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 1018, 642);
		
		contentPane = new ContentPanel();
		
		contentPane.setBackground(Color.BLACK);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		whiteDiePanel = new DiePanel(Const.WHITE);
		contentPane.add(whiteDiePanel);
		
		boardPanel = new BoardPanel();
		contentPane.add(boardPanel);
		
		borderPanel = new BorderPanel();
		contentPane.add(borderPanel);
		
		blackDiePanel = new DiePanel(Const.BLACK);
		contentPane.add(blackDiePanel);
		
		/*menu = new MenuPanel();
		contentPane.add(menu);*/
		
		lblTime = new JLabel("00:00:00");
		lblTime.setForeground(new Color(120, 120, 120));
		lblTime.setFont(new Font("Consolas", Font.PLAIN, 33));
		lblTime.setBounds(285, 11, 350, 57);
		contentPane.add(lblTime);
		
		setVisible(true);
		
		// khoi tao server
		if(isServer){
			this.socketGame = socketGame;
			this.serverSocket = serverSocket;
			
			try {
				gameInput = new ObjectInputStream(socketGame.getInputStream());
				gameOutput = new ObjectOutputStream(socketGame.getOutputStream());
				
				//chatInput = new ObjectInputStream(socketChat.getInputStream());
				//chatOutput = new ObjectOutputStream(socketChat.getOutputStream());
				
				gamePlay = new HumanVSHumanLan(this, Const.WHITE);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//khoi tao client
		else{
			this.socketGame = socketGame;
			//this.socketChat = socketChat;
			
			try {
				gameOutput = new ObjectOutputStream(socketGame.getOutputStream());
				gameInput = new ObjectInputStream(socketGame.getInputStream());
				
				//chatOutput = new ObjectOutputStream(socketChat.getOutputStream());
				//chatInput = new ObjectInputStream(socketChat.getInputStream());
				
				gamePlay = new HumanVSHumanLan(this, Const.BLACK);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	public JPanel getWhiteDiePanel() {
		return whiteDiePanel;
	}

	public void setWhiteDiePanel(JPanel whiteDiePanel) {
		this.whiteDiePanel = whiteDiePanel;
	}

	public BoardPanel getBoardPanel() {
		return boardPanel;
	}

	public void setBoardPanel(BoardPanel boardPanel) {
		this.boardPanel = boardPanel;
	}

	public JPanel getBlackDiePanel() {
		return blackDiePanel;
	}

	public void setBlackDiePanel(JPanel blackDiePanel) {
		this.blackDiePanel = blackDiePanel;
	}

	public JLabel getLblTime() {
		return lblTime;
	}

	public void setLblTime(JLabel lblTime) {
		this.lblTime = lblTime;
	}
	
	public class BorderPanel extends JPanel{
		BufferedImage borderImage;
		
		public BorderPanel(){
			borderImage = LoadData.getImage("./res/border.png");
			setBackground( new Color(0, 0, 0, 1) );
			setBounds(122, 112, 470, 470);
			setLayout(null);
		}
		
		@Override
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(borderImage, 0, 0, 474, 474, null);
		}
	}
	
	public class BoardPanel extends JPanel{
		BufferedImage cPiece;
		
		float currentX;
		float currentY;
		
		float deltaX;
		float deltaY;
		
		private BufferedImage boardImage;
		
		public BoardPanel(){
			boardImage = LoadData.getImage("./res/chessboard.png");
            
			setBounds(158, 149, 400, 400);
			
			addMouseListener(new MouseAdapter(){
				public void mousePressed(MouseEvent e) {
					gamePlay.clickOnPanel(e);
				}	
	        });
		}
		
		@Override
		public void paintComponent(Graphics gC){
			super.paintComponent(gC);
			Image image = createImage(400,  400);
			Graphics g = image.getGraphics();
			
			g.drawImage(boardImage, 0, 0, 400, 400, null);
			
			CBoard paintBoard = gamePlay.paintBoard;
			Coord from = gamePlay.coordIMoveFrom;
			Coord to = gamePlay.coordIMoveTo;
			
			// In vi tri chon quan co neu dang chon
			for(Coord coord : gamePlay.listMove){
				g.drawImage(chooseImage,
					  coord.x * Const.UNIT, (7 - coord.y) * Const.UNIT,
      				  Const.UNIT, Const.UNIT,
      				  null);
			}
			
			// neu quan minh vua bi chieu thi in ra thong bao
			Coord kingLossPos = CBProcess.kingWillLoss(paintBoard, gamePlay.myColor);
			if(kingLossPos != null)
				g.drawImage(lossImage,
						  kingLossPos.x * Const.UNIT, (7 - kingLossPos.y) * Const.UNIT,
	      				  Const.UNIT, Const.UNIT,
	      				  null);
			
			// neu quan minh vua bi chieu thi in ra thong bao
			kingLossPos = CBProcess.kingWillLoss(paintBoard, gamePlay.enemyColor);
			if(kingLossPos != null)
				g.drawImage(lossImage,
						  kingLossPos.x * Const.UNIT, (7 - kingLossPos.y) * Const.UNIT,
	      				  Const.UNIT, Const.UNIT,
	      				  null);
			
			// in ra nuoc vua di cua com
			if(gamePlay.coordEnemyMoved != null)
				g.drawImage(commoveImage,
							gamePlay.coordEnemyMoved.x * Const.UNIT, (7 - gamePlay.coordEnemyMoved.y) * Const.UNIT,
	      				  Const.UNIT, Const.UNIT,
	      				  null);
            // in ra vi tri hien tai cua quan co
			if(from != null)
            	g.drawImage(chooseImage,
            				  from.x * Const.UNIT, (7 - from.y) * Const.UNIT,
            				  Const.UNIT, Const.UNIT,
            				  null);
            
            // In cac quan co mau trang
            CPiece temp;
            for(int i = 0; i < paintBoard.cArray[Const.WHITE].n; i++){
            	temp = paintBoard.cArray[Const.WHITE].array[i];
            	g.drawImage(chessPiece[Const.WHITE][temp.type],
            			      temp.pos.x * Const.UNIT, (7 - temp.pos.y) * Const.UNIT,
            			      Const.UNIT, Const.UNIT,
            			      null);
            }
            
            // In cac quan co mau den
            for(int i = 0; i < paintBoard.cArray[Const.BLACK].n; i++){
            	temp = paintBoard.cArray[Const.BLACK].array[i];
            	g.drawImage(chessPiece[Const.BLACK][temp.type],
            			      temp.pos.x * Const.UNIT, (7 - temp.pos.y) * Const.UNIT,
            			      Const.UNIT, Const.UNIT,
            			      null);
            }
            
            if(cPiece != null)
				g.drawImage(cPiece, (int)currentX, 350 - (int)currentY, 50, 50, null);
            
            gC.drawImage(image, 0, 0, 400, 400, null);
		}
		
		
		public void animate(Coord from, Coord to, int type, int color){
			gamePlay.paintBoard = new CBoard(gamePlay.cBoard);
			gamePlay.paintBoard.cArray[color].delete(gamePlay.paintBoard.slot(from).cPiece);
			
			
			cPiece = chessPiece[color][type];
			
			currentX = from.x * Const.UNIT;
			currentY = from.y * Const.UNIT;
			
			deltaX = (to.x - from.x) * Const.UNIT * 1.0f / 300f;
			deltaY = (to.y - from.y) * Const.UNIT * 1.0f / 300f;
				
			new Thread(){
				public void run(){
					// disable undo
					contentPane.btnUndo.setEnabled(false);
					
					for(int i = 0; i < 300; i++){
						currentX += deltaX;
						currentY += deltaY;
						try {
							Thread.sleep(2);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						boardPanel.repaint();
					}
					
					cPiece = null;
					
					gamePlay.isEnemySelect = !gamePlay.isEnemySelect;
					gamePlay.paintBoard = new CBoard(gamePlay.cBoard);
					boardPanel.repaint();
					whiteDiePanel.repaint();
					blackDiePanel.repaint();
					
					// enable tro lai
					contentPane.btnUndo.setEnabled(true);
				}
			}.start();
		}
	}
	
	public class DiePanel extends JPanel{
		private BufferedImage dieImage;
		private int color;
		
		public DiePanel(int color){
			this.color = color;
			dieImage = LoadData.getImage("./res/dieboard.png");
			if(color == Const.BLACK)
				setBounds(604, 149, 100, 400);
			else
				setBounds(10, 149, 100, 400);
		}
		
		@Override
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(dieImage, 0, 0, 100, 400, null);
			
			CArray list = gamePlay.listDiedCPiece[color];
					      
			BufferedImage[] image = chessPiece[color];
			
			for(int i = 0; i < list.n; i++)
			{
				int x = i / 8;
				int y = i % 8;
				
				g.drawImage(image[list.array[i].type],
      			      x * Const.UNIT, y * Const.UNIT,
      			      Const.UNIT, Const.UNIT,
      			      null);
			}
		}
	}

	public class ContentPanel extends JPanel{
		public JButton btnNewGame;
		public JButton btnUndo;
		public JButton btnMainMenu;
		public JButton btnChat;
		
		public JScrollPane scrpWaysOfWhite;
		public JScrollPane scrpWaysOfBlack;
		
		public JList lstWaysOfWhite;
		public JList lstWaysOfBlack;
		
		public JLabel lbWaysOfWhite;
		public JLabel lbWaysOfBlack;
		
		BufferedImage wallImage;
		
		public ContentPanel(){
			wallImage = LoadData.getImage("./res/wall.png");
			InputStream is = null;
			Font fontButton = null;
			
			try {
				is = new FileInputStream("./res/hacjiuza.ttf");
				fontButton = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(35f);
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
			
			this.setBounds(0, 0, 1018, 642);
			this.setLayout(null);
			
			//Font fontButton = new Font("consolas", Font.BOLD, 30);
			Font fontList = new Font("consolas", Font.BOLD, 15);
			
			btnNewGame = new JButton("New game");
			btnNewGame.setBounds(750, 140, 230, 50);
			btnNewGame.setFont(fontButton);
			btnNewGame.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					gamePlay.newGame();
				}
			});
			
			
			btnChat = new JButton("Chat");
			btnChat.setBounds(750, 80, 230, 50);
			btnChat.setFont(fontButton);
			btnChat.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					//gamePlay.undo();
				}
			});
			
			btnUndo = new JButton("Undo");
			btnUndo.setBounds(750, 80, 230, 50);
			btnUndo.setFont(fontButton);
			btnUndo.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					gamePlay.undo();
				}
			});
			
			if(gameType != Const.HUMAN_VS_HUMAN_LAN){
				this.add(btnUndo);
				this.add(btnNewGame);
			}
			
			btnMainMenu = new JButton("Main menu");
			btnMainMenu.setBounds(750, 20, 230, 50);
			//btnMainMenu.setBounds(750, 140, 230, 50);
			btnMainMenu.setFont(fontButton);
			btnMainMenu.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					gamePlay.isHidden = true;
					GameView.this.setVisible(false);
					mainMenu.setVisible(true);
					
					if(gameType == Const.HUMAN_VS_HUMAN_LAN){
						try{
							serverSocket.close();
							if(isServer){
								socketGame.close();
							}
						}
						catch(Exception e){
							e.printStackTrace();
						}
					}
				}
				
			});
			this.add(btnMainMenu);
			
			lbWaysOfWhite = new JLabel("WHITE");
			lbWaysOfWhite.setBounds(750, 250, 80, 50);
			lbWaysOfWhite.setForeground(Color.WHITE);
			this.add(lbWaysOfWhite);
			
			lstWaysOfWhite = new JList();
			lstWaysOfWhite.setForeground(new Color(120, 120, 120));
			lstWaysOfWhite.setBackground(Color.BLACK);
			lstWaysOfWhite.setFont(fontList);
			
			scrpWaysOfWhite = new JScrollPane(lstWaysOfWhite);
			scrpWaysOfWhite.setBounds(830, 200, 150, 150);
			scrpWaysOfWhite.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			this.add(scrpWaysOfWhite);
			
			lbWaysOfWhite = new JLabel("BLACK");
			lbWaysOfWhite.setBounds(750, 410, 80, 50);
			lbWaysOfWhite.setForeground(Color.WHITE);
			this.add(lbWaysOfWhite);
			
			lstWaysOfBlack = new JList();
			lstWaysOfBlack.setForeground(new Color(120, 120, 120));
			lstWaysOfBlack.setBackground(Color.BLACK);
			lstWaysOfBlack.setFont(fontList);
			
			scrpWaysOfBlack = new JScrollPane(lstWaysOfBlack);
			scrpWaysOfBlack.setBounds(830, 360, 150, 150);
			scrpWaysOfBlack.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			this.add(scrpWaysOfBlack);
		}
		
		@Override
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(wallImage, 0, 0, 1018, 642, null);
		}
	}
}
