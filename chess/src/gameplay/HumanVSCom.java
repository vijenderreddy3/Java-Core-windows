package gameplay;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import model.CArray;
import model.CBoard;
import model.CPiece;
import model.Const;
import model.Coord;
import process.CBProcess;
import process.CPProcess;
import view.GameView;
import ai.ComAlphaBeta;
import ai.Computer;

public class HumanVSCom extends GamePlay{
	
	public Computer com;
	public Thread comThread;
	public Random random;
	public HumanVSCom(GameView gameView, int myColor, int depth, int val){
		super(gameView, myColor);
		random = new Random();
		comThread = new ComThread();
		comThread.start();
	}
	public void clickOnPanel(MouseEvent e) {
		boolean isRoque = false;
		if(isGameOver)
			return;
		synchronized(cBoard){
			Coord coord = new Coord(e.getX() / Const.UNIT, 7 - e.getY() / Const.UNIT);
			CPiece temp = cBoard.slot(coord).cPiece;
			if(temp != null){
				if(temp.color == myColor){
					if(coordIMoveFrom != null){
						coordIMoveTo = coord;
						if(cBoard.slot(coordIMoveFrom).cPiece.type == Const.KING && cBoard.slot(coordIMoveTo).cPiece.type == Const.ROOK){
							if(CBProcess.roque(cBoard, cBoard.slot(coordIMoveFrom).cPiece, cBoard.slot(coordIMoveTo).cPiece, this)){
								listUndo.push(isEnemySelect);
								listUndo.push(coordIMoveTo);
								if(coordIMoveTo.x == 7){
									listUndo.push(cBoard.slot[coordIMoveFrom.x + 2][coordIMoveFrom.y].cPiece);
									listUndo.push(cBoard.slot[coordIMoveFrom.x + 1][coordIMoveFrom.y].cPiece);
								}
								else{
									listUndo.push(cBoard.slot[coordIMoveFrom.x - 2][coordIMoveFrom.y].cPiece);
									listUndo.push(cBoard.slot[coordIMoveFrom.x - 1][coordIMoveFrom.y].cPiece);
								}
								listUndo.push(Const.REQUE);				// Put to backup
								// Reset the parameters
								isRoque = true;
								coordIMoveFrom = null;
								coordIMoveTo = null;
								listMove.clear();
								isEnemySelect = true;
								// Based on the list
								ways[myColor].add("Reque");
								lstWays[myColor].setListData(ways[myColor]);
							}
						}
						else if(cBoard.slot(coordIMoveFrom).cPiece.type == Const.ROOK && cBoard.slot(coordIMoveTo).cPiece.type == Const.KING)
							if(CBProcess.roque(cBoard, cBoard.slot(coordIMoveTo).cPiece, cBoard.slot(coordIMoveFrom).cPiece, this)){
								listUndo.push(isEnemySelect);
								listUndo.push(coordIMoveFrom);
								if(coordIMoveFrom.x == 7){
									listUndo.push(cBoard.slot[coordIMoveTo.x + 2][coordIMoveTo.y].cPiece);
									listUndo.push(cBoard.slot[coordIMoveTo.x + 1][coordIMoveTo.y].cPiece);
								}
								else{
									listUndo.push(cBoard.slot[coordIMoveTo.x - 2][coordIMoveTo.y].cPiece);
									listUndo.push(cBoard.slot[coordIMoveTo.x - 1][coordIMoveTo.y].cPiece);
								}
								listUndo.push(Const.REQUE);				
								coordIMoveFrom = null;
								coordIMoveTo = null;
								isRoque = true;
								listMove.clear();
								isEnemySelect = true;
								ways[myColor].add("Reque");
								lstWays[myColor].setListData(ways[myColor]);
							}
					}
					if(isRoque == false){
						coordIMoveFrom = new Coord(temp.pos);
						listMove = CPProcess.getWays(temp, cBoard);
					}
				}
				else if(coordIMoveFrom != null){
					coordIMoveTo = temp.pos;
					
					if(coordIMoveFrom.equals(coordIMoveTo)){
						coordIMoveFrom = null;
						coordIMoveTo = null;
						return;
					}
					
					if(CPProcess.moveableTo(cBoard.slot(coordIMoveFrom).cPiece, coordIMoveTo, cBoard)){	
						listUndo.push(cBoard.slot(coordIMoveFrom).cPiece.firstMoved);
						listUndo.push(isEnemySelect);
						listUndo.push(cBoard.slot(coordIMoveTo).cPiece);
						listUndo.push(coordIMoveTo);
						listUndo.push(coordIMoveFrom);
						listUndo.push(Const.NORMAL);
						CBProcess.finalMove(cBoard, coordIMoveFrom, coordIMoveTo, this);
						isEnemySelect = true;
						ways[myColor].add(coordIMoveFrom + " ---> " + coordIMoveTo);
						lstWays[myColor].setListData(ways[myColor]);
						synchronized(count){
							count = 0l;
						}
						int state = CBProcess.gameOver(cBoard);
						if(state != Const.STATE_CONTINUE){
							synchronized(timeCount){
								timeCount.notify();
							}
							synchronized(lblTimeView){
								lblTimeView.setText("GAMEOVER");
							}
							synchronized(comThread){
								comThread.notify();
							}
							isGameOver = true;
						}
					}
					
					listMove.clear();
					coordIMoveFrom = null;
					coordIMoveTo = null;
				}
			}
			else if(coordIMoveFrom != null){
				coordIMoveTo = cBoard.slot(coord).coord;
				
				if(coordIMoveFrom.equals(coordIMoveTo)){
					coordIMoveFrom = null;
					coordIMoveTo = null;
					return;
				}
				
				if(CPProcess.moveableTo(cBoard.slot(coordIMoveFrom).cPiece, coordIMoveTo, cBoard)){
					// backup
					
					listUndo.push(cBoard.slot(coordIMoveFrom).cPiece.firstMoved);
					listUndo.push(isEnemySelect);
					listUndo.push(cBoard.slot(coordIMoveTo).cPiece);
					listUndo.push(coordIMoveTo);
					listUndo.push(coordIMoveFrom);
					listUndo.push(Const.NORMAL);
					CBProcess.finalMove(cBoard, coordIMoveFrom, coordIMoveTo, this);
					isEnemySelect = true;
					ways[myColor].add(coordIMoveFrom + " ---> " + coordIMoveTo);
					lstWays[myColor].setListData(ways[myColor]);
					
					synchronized(count){
						count = 0l;
					}
					int state = CBProcess.gameOver(cBoard);
					if(state != Const.STATE_CONTINUE){
						synchronized(timeCount){
							timeCount.notify();
						}
						
						synchronized(lblTimeView){
							lblTimeView.setText("GAMEOVER");
						}
						
						synchronized(comThread){
							comThread.notify();
						}
						
						isGameOver = true;
					}
				}
				
				listMove.clear();
				coordIMoveFrom = null;
				coordIMoveTo = null;
			}
			
			paintBoard = new CBoard(cBoard);
			panelChessBoard.repaint();
			panelWhiteDied.repaint();
			panelBlackDied.repaint();
		}
	}
	
	public void newGame(){
		cBoard = new CBoard();
		paintBoard = new CBoard();
		
		listDiedCPiece = new CArray[2];
		listDiedCPiece[Const.WHITE] = new CArray();
		listDiedCPiece[Const.BLACK] = new CArray();
		
		comThread = new ComThread();
		timeCount = new TimeCounter();
		
		count = 0l;
		coordIMoveFrom  = null;
		coordEnemyMoved = null;
		listMove.clear();
		isGameOver = false;
		isEnemySelect = false;
		comThread.resume();
		timeCount.resume();
		
		ways[myColor].clear();
		ways[enemyColor].clear();
		lstWays[myColor].setListData(ways[myColor]);
		lstWays[enemyColor].setListData(ways[enemyColor]);
		
		panelChessBoard.repaint();
		panelWhiteDied.repaint();
		panelBlackDied.repaint();
		
		listUndo.clear();
	}
	
 	public class ComThread extends Thread{
		@Override
		public void run() {
			while(!isHidden){
				synchronized (cBoard) {
					synchronized(isEnemySelect){
						if(isEnemySelect && !isGameOver){
							synchronized(com){
								com.nextAction(cBoard);
							}
							paintBoard = new CBoard(cBoard);
							CBProcess.refresh(paintBoard);
							
							panelChessBoard.repaint();
							panelWhiteDied.repaint();
							panelBlackDied.repaint();							
							synchronized(count){
								count = 0l;
							}
							int state = CBProcess.gameOver(cBoard);
							if(state != Const.STATE_CONTINUE){
								synchronized(timeCount){
									timeCount.notify();
								}
								
								synchronized(lblTimeView){
									lblTimeView.setText("GAMEOVER");
								}
								
								isGameOver = true;
								
								synchronized(this){
									notify();
								}
							}
							isEnemySelect = false;
						}
					}
				}
			}
		}
	}
	
	public void setEval(int eval){
		synchronized(com){
			com.setEval(eval);
		}
	}
	
	public void setDepth(int dep){
		synchronized(com){
			com.setDepth(dep);
		}
	}
	
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
						if(ways[enemyColor].size() > 0)
							ways[enemyColor].remove(ways[enemyColor].size() - 1);
						lstWays[enemyColor].setListData(ways[enemyColor]);
					}
					else{
						if(ways[myColor].size() > 0)
							ways[myColor].remove(ways[myColor].size() - 1);
						lstWays[myColor].setListData(ways[myColor]);
					}
					
					if(comSelect == true){
						undo();
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
					
					if(comSelect == true){
						undo();
					}
				}
			}
		}
	}
}
