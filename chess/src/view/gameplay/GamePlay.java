package view.gameplay;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import model.CArray;
import model.CBoard;
import model.CPiece;
import model.Const;
import model.Coord;
import model.process.CBProcess;
import view.GameView;
import view.GameView.BoardPanel;

abstract public class GamePlay {
	public CBoard cBoard;
	public CBoard paintBoard;
	public Vector<String> ways[];
	public BoardPanel panelChessBoard;
	public JPanel panelWhiteDied;
	public JPanel panelBlackDied;
	public JLabel lblTimeView;
	public JList<String> lstWays[];
	
	public CArray[] listDiedCPiece; 
	
	
	public Coord coordIMoveFrom = null; 
	public Coord coordIMoveTo = null;	
	public Coord coordEnemyMoved = null;
	public ArrayList<Coord> listMove;	
	
	public Stack<Object> listUndo;  
	public Thread timeCount;
	
	public Boolean isGameOver = false;
	public Boolean isEnemySelect = false;
	public Boolean isHidden = false;
	
	public Long count;
	public int myColor = Const.WHITE; 
	public int enemyColor = Const.BLACK;
	
	public GamePlay(GameView gameView, int color){
		this.myColor = color;
		this.enemyColor = 1 - color;
		if(myColor == Const.WHITE)
			isEnemySelect = false;
		else
			isEnemySelect = true;
		
		cBoard = new CBoard();
		paintBoard = new CBoard();
		
		panelChessBoard = gameView.getBoardPanel();
		panelWhiteDied = gameView.getWhiteDiePanel();
		panelBlackDied = gameView.getBlackDiePanel();
		lblTimeView = gameView.getLblTime();
		
		lstWays = new JList[2];
		lstWays[Const.WHITE] = gameView.contentPane.lstWaysOfWhite;
		lstWays[Const.BLACK]= gameView.contentPane.lstWaysOfBlack;
		
		ways = new Vector[2];
		ways[Const.WHITE] = new Vector<String>();
		lstWays[Const.WHITE].setListData(ways[Const.WHITE]);
		ways[Const.BLACK] = new Vector<String>();
		lstWays[Const.BLACK].setListData(ways[Const.BLACK]);
		
		listDiedCPiece = new CArray[2];
		listDiedCPiece[Const.WHITE] = new CArray();
		listDiedCPiece[Const.BLACK] = new CArray();
		
		listUndo = new Stack<Object>();
		
		listMove = new  ArrayList<Coord>();
		
		count = new Long(0);
		
		timeCount = new TimeCounter();
		timeCount.start();
	}
	
	abstract public void newGame();
	
	public void undo(){
		synchronized(cBoard){
			if(listUndo.size() > 0){
				coordIMoveFrom = null;
				coordIMoveTo = null;
				listMove.clear();
				
				if(((Integer)listUndo.pop()) == Const.NORMAL){ 
					Coord from = (Coord) listUndo.pop();
					Coord to = (Coord) listUndo.pop();
					CPiece cPiece = (CPiece) listUndo.pop();
					Boolean comSelect = (Boolean) listUndo.pop();
					Boolean firstMove = (Boolean) listUndo.pop();
					
					CBProcess.move(cBoard, to, from);
					if(cPiece != null){
						cBoard.slot(to).cPiece = cPiece;
						cBoard.cArray[cPiece.color].add(cPiece);
						listDiedCPiece[cPiece.color].delete(cPiece);
					}
					this.isEnemySelect = comSelect;
					cBoard.slot(from).cPiece.firstMoved = firstMove;
					isGameOver = false;
					
					listMove.clear();
					coordEnemyMoved = null;
					
					paintBoard = new CBoard(cBoard);
					panelChessBoard.repaint();
					panelWhiteDied.repaint();
					panelBlackDied.repaint();
					
					if(comSelect == true){
						ways[enemyColor].remove(ways[enemyColor].size() - 1);
						lstWays[enemyColor].setListData(ways[enemyColor]);
					}
					else{
						ways[myColor].remove(ways[myColor].size() - 1);
						lstWays[myColor].setListData(ways[enemyColor]);
					}
				}
				else{ 
					CPiece rook = (CPiece)listUndo.pop();
					CPiece king = (CPiece)listUndo.pop();
					Coord pos = (Coord)listUndo.pop();
					Boolean comSelect = (Boolean)listUndo.pop();
					
					if(pos.x == 7){
						king.pos.x = 4;
						rook.pos.x = 7;
						
						cBoard.slot(king.pos).cPiece = king;
						cBoard.slot(rook.pos).cPiece = rook;
						
						cBoard.slot[5][king.pos.y].cPiece = null;
						cBoard.slot[6][king.pos.y].cPiece = null;
						
						king.firstMoved = false;
						rook.firstMoved = false;
					}
					else{
						king.pos.x = 4;
						rook.pos.x = 0;
						
						cBoard.slot(king.pos).cPiece = king;
						cBoard.slot(rook.pos).cPiece = rook;
						
						cBoard.slot[3][king.pos.y].cPiece = null;
						cBoard.slot[2][king.pos.y].cPiece = null;
						
						king.firstMoved = false;
						rook.firstMoved = false;
					}
					
					paintBoard = new CBoard(cBoard);
					panelChessBoard.repaint();
					panelWhiteDied.repaint();
					panelBlackDied.repaint();
					
					this.isEnemySelect = comSelect;
					
					if(comSelect == true){
						ways[enemyColor].remove(ways[enemyColor].size() - 1);
						lstWays[enemyColor].setListData(ways[enemyColor]);
					}
					else{
						ways[myColor].remove(ways[myColor].size() - 1);
						lstWays[myColor].setListData(ways[enemyColor]);
					}
				}
			}
		}
	}
	
	public void setTimeView(long count) {
		
		long c = count;
		long mls = (c/10) % 100;
		c = c/10;
		long ss = (c/100) % 60;
		c = c / 100;
		long ms = c / 60;
		
		String s = String.format("%02d:%02d:%02d", ms, ss, mls);
		synchronized(lblTimeView){
			if(!isGameOver)
				lblTimeView.setText(s);
		}
	}
	
	public class TimeCounter extends Thread{
		public long time_1 = System.currentTimeMillis();
		public long time_2 = 0;

		public void run(){
			while(!isHidden){
				
				time_2 = System.currentTimeMillis();
				synchronized(count){
					count += time_2 - time_1;
				}
				time_1 = time_2;
				
				setTimeView(count);
				try {
					sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	abstract public void clickOnPanel(MouseEvent e);
}
