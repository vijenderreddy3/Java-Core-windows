package view.gameplay;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import model.CArray;
import model.CBoard;
import model.CPiece;
import model.Const;
import model.Coord;
import model.process.CBProcess;
import model.process.CPProcess;
import view.GameView;
import ai.ComAlphaBeta;
import ai.Computer;

public class HumanVSCom extends GamePlay{
	
	public Computer com;
	
	public Thread comThread;
	
	//public int depth = 4;
	
	// bo khoi tao so ngau nhien cho viec chon nuoc di cuoi trong ai
	public Random random;
	
	public HumanVSCom(GameView gameView, int myColor, int depth, int val){
		super(gameView, myColor);

		random = new Random();
		
		com = new ComAlphaBeta(depth, 1 - myColor, val, this);
		
		comThread = new ComThread();
		comThread.start();
		
	}
	
	public void clickOnPanel(MouseEvent e) {
		boolean isRoque = false;
		// neu ket thuc game roi thi thoi
		if(isEnemySelect)
			return;
		
		if(isGameOver)
			return;
		
		synchronized(cBoard){
			Coord coord = new Coord(e.getX() / Const.UNIT, 7 - e.getY() / Const.UNIT);
			
			CPiece temp = cBoard.slot(coord).cPiece;
			
			// truong hop o dich co quan
			if(temp != null){
				// neu la quan minh thi kiem tra nhap thanh, neu khong nhap thanh thi dua vao from
				if(temp.color == myColor){
					// kiem tra nhap thanh
					if(coordIMoveFrom != null){
						coordIMoveTo = coord;
						if(cBoard.slot(coordIMoveFrom).cPiece.type == Const.KING && cBoard.slot(coordIMoveTo).cPiece.type == Const.ROOK){
							if(CBProcess.roque(cBoard, cBoard.slot(coordIMoveFrom).cPiece, cBoard.slot(coordIMoveTo).cPiece, this)){
								// backup lai
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
								listUndo.push(Const.REQUE);				// dua vao trang thai backup
								// dat lai cac thong so
								isRoque = true;
								coordIMoveFrom = null;
								coordIMoveTo = null;
								listMove.clear();
								isEnemySelect = true;
								
								// dua vao danh sach
								ways[myColor].add("Reque");
								lstWays[myColor].setListData(ways[myColor]);
							}
								
						}
						else if(cBoard.slot(coordIMoveFrom).cPiece.type == Const.ROOK && cBoard.slot(coordIMoveTo).cPiece.type == Const.KING)
							if(CBProcess.roque(cBoard, cBoard.slot(coordIMoveTo).cPiece, cBoard.slot(coordIMoveFrom).cPiece, this)){
								// backup lai
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
								listUndo.push(Const.REQUE);				// dua vao trang thai backup
								// dat lai cac thong so
								coordIMoveFrom = null;
								coordIMoveTo = null;
								isRoque = true;
								listMove.clear();
								isEnemySelect = true;
								
								// dua vao danh sach
								ways[myColor].add("Reque");
								lstWays[myColor].setListData(ways[myColor]);
							}
					}
					
					// neu chua nhap thanh thi dua vao from
					// neu la quan minh thi dua vao from
					if(isRoque == false){
						coordIMoveFrom = new Coord(temp.pos);
						// lay ve tat ca cac duong di co the --> dung de ve
						listMove = CPProcess.getWays(temp, cBoard);
					}
				}
				// neu la quan dich
				else if(coordIMoveFrom != null){
					coordIMoveTo = temp.pos;
					
					if(coordIMoveFrom.equals(coordIMoveTo)){
						coordIMoveFrom = null;
						coordIMoveTo = null;
						return;
					}
					
					if(CPProcess.moveableTo(cBoard.slot(coordIMoveFrom).cPiece, coordIMoveTo, cBoard)){
						// backup nuoc di de undo
						
						listUndo.push(cBoard.slot(coordIMoveFrom).cPiece.firstMoved);
						listUndo.push(isEnemySelect);
						listUndo.push(cBoard.slot(coordIMoveTo).cPiece);
						listUndo.push(coordIMoveTo);
						listUndo.push(coordIMoveFrom);
						listUndo.push(Const.NORMAL);
						// thuc hien di chuyen
						///////////////////////////////////////////////////////////////////////////////////////////////
						// ve animation
						panelChessBoard.animate(coordIMoveFrom, coordIMoveTo,cBoard.slot(coordIMoveFrom).cPiece.type, cBoard.slot(coordIMoveFrom).cPiece.color);
						//////////////////////////////////////////////////////////////////////////////////////////////
						CBProcess.finalMove(cBoard, coordIMoveFrom, coordIMoveTo, this);
						//isEnemySelect = true;
						
						// dua vao danh sach
						ways[myColor].add(coordIMoveFrom + " ---> " + coordIMoveTo);
						lstWays[myColor].setListData(ways[myColor]);
						
						synchronized(count){
							count = 0l;
						}
						
						// kiem tra van co ket thuc chua
						int state = CBProcess.gameOver(cBoard);
						if(state != Const.STATE_CONTINUE){
							
							synchronized(lblTimeView){
								lblTimeView.setText("GAMEOVER");
							}
							isGameOver = true;
						}
					}
					
					listMove.clear();
					coordIMoveFrom = null;
					coordIMoveTo = null;
				}
			}
			// neu o temp khong co quan
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
					
					// di chuyen
					///////////////////////////////////////////////////////////////////////////////////////////////
					// ve animation
					panelChessBoard.animate(coordIMoveFrom, coordIMoveTo, cBoard.slot(coordIMoveFrom).cPiece.type, cBoard.slot(coordIMoveFrom).cPiece.color);
					//////////////////////////////////////////////////////////////////////////////////////////////
					CBProcess.finalMove(cBoard, coordIMoveFrom, coordIMoveTo, this);
					//isEnemySelect = true;
					
					// dua vao danh sach
					ways[myColor].add(coordIMoveFrom + " ---> " + coordIMoveTo);
					lstWays[myColor].setListData(ways[myColor]);
					
					synchronized(count){
						count = 0l;
					}
					
					// kiem tra van co ket thuc chua
					int state = CBProcess.gameOver(cBoard);
					if(state != Const.STATE_CONTINUE){
						synchronized(timeCount){
							//timeCount.notify();
						}
						
						synchronized(lblTimeView){
							lblTimeView.setText("GAMEOVER");
						}
						
						synchronized(comThread){
							//comThread.notify();
						}
						
						isGameOver = true;
					}
				}
				
				listMove.clear();
				coordIMoveFrom = null;
				coordIMoveTo = null;
			}
			
			//paintBoard = new CBoard(cBoard);
			panelChessBoard.repaint();
			//panelWhiteDied.repaint();
			//panelBlackDied.repaint();
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
		
		//comThread.interrupt();
		//timeCount.interrupt();
		//comThread = new ComThread();
		//timeCount = new TimeCounter();
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
				//dung mot khoang thoi gian de giam tai nguyen CPU su dung
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (cBoard) {
					synchronized(isEnemySelect){
						if(isEnemySelect && !isGameOver){
							// buoc di ke tiep
							synchronized(com){
								com.nextAction(cBoard);
							}
							
							synchronized(count){
								count = 0l;
							}
							
							// kiem tra van co ket thuc chua
							CBProcess.refresh(paintBoard);
							int state = CBProcess.gameOver(cBoard);
							if(state != Const.STATE_CONTINUE){
								synchronized(timeCount){
									//timeCount.notify();
								}
								
								synchronized(lblTimeView){
									lblTimeView.setText("GAMEOVER");
								}
								
								isGameOver = true;
								
								synchronized(this){
									//notify();
								}
							}
							
							// doi cho den khi animate thuc hien xong cong viec ( thi isEnemySelect
							// se chuyen sang false  )
							while(isEnemySelect != false);
							
							paintBoard = new CBoard(cBoard);
							
							panelChessBoard.repaint();
							panelWhiteDied.repaint();
							panelBlackDied.repaint();
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
				// tranh loi NullPointer khi from van con ma quan co da bi undo
				coordIMoveFrom = null;
				coordIMoveTo = null;
				listMove.clear();
				
				if(((Integer)listUndo.pop()) == Const.NORMAL){ // truong hop backup binh thuong
					Coord from = (Coord) listUndo.pop();
					Coord to = (Coord) listUndo.pop();
					CPiece cPiece = (CPiece) listUndo.pop();
					Boolean comSelect = (Boolean) listUndo.pop();
					Boolean firstMove = (Boolean) listUndo.pop();
					
					// di chuyen nguoc lai
					CBProcess.move(cBoard, to, from);
					// neu quan trong slot(to) bi an thi :
					// - them no vao ban co
					// - xoa di trong danh sach an
					if(cPiece != null){
						cBoard.slot(to).cPiece = cPiece;
						cBoard.cArray[cPiece.color].add(cPiece);
						listDiedCPiece[cPiece.color].delete(cPiece);
					}
					// thay doi lai luot di
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
				else{ // truong hop nhap thanh
					// chu y undo trong truong hop nay la undo khi da di chuyen roi
					// khong nhu truong hop NORMAL la undo truoc di chuyen
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
