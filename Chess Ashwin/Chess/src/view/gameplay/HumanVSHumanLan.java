package view.gameplay;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.CBoard;
import model.CPiece;
import model.Const;
import model.Coord;
import model.process.CBProcess;
import model.process.CPProcess;
import view.GameView;

public class HumanVSHumanLan extends GamePlay{
	ObjectInputStream input;
	ObjectOutputStream output;

	public HumanVSHumanLan(GameView gameView, int color) {
		super(gameView, color);
		input = gameView.gameInput;
		output = gameView.gameOutput;
		
		(new Receiver()).start();
	}

	@Override
	public void clickOnPanel(MouseEvent e) {
		boolean isRoque = false;
		// neu ket thuc game roi thi thoi
		if(isGameOver)
			return;
		
		synchronized(cBoard){
			Coord coord = new Coord(e.getX() / Const.UNIT, 7 - e.getY() / Const.UNIT);
			
			CPiece temp = cBoard.slot(coord).cPiece;
			
			if(!isEnemySelect){
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
							panelChessBoard.animate(coordIMoveFrom, coordIMoveTo, cBoard.slot(coordIMoveFrom).cPiece.type, cBoard.slot(coordIMoveFrom).cPiece.color);
							//////////////////////////////////////////////////////////////////////////////////////////////
							
							CBProcess.finalMove(cBoard, coordIMoveFrom, coordIMoveTo, this);
							// gui du lieu
							sendObject(coordIMoveFrom, coordIMoveTo);
							
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
									timeCount.notify();
								}
								
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
						
						// thuc hien di chuyen
						///////////////////////////////////////////////////////////////////////////////////////////////
						// ve animation
						panelChessBoard.animate(coordIMoveFrom, coordIMoveTo, cBoard.slot(coordIMoveFrom).cPiece.type, cBoard.slot(coordIMoveFrom).cPiece.color);
						//////////////////////////////////////////////////////////////////////////////////////////////
						
						
						CBProcess.finalMove(cBoard, coordIMoveFrom, coordIMoveTo, this);
						
						// gui du lieu
						sendObject(coordIMoveFrom, coordIMoveTo);
						
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
								timeCount.notify();
							}
							
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
				
				panelChessBoard.repaint();
			}
		}
	}

	@Override
	public void newGame() {
		// TODO Auto-generated method stub
		
	}

	public class Receiver extends Thread{
		public void run(){
			while(!isHidden){
				try {
					Coord from = (Coord)input.readObject();
					Coord to = (Coord)input.readObject();
					
					///////////////////////////////////////////////////////////////////////////////////////////////
					// ve animation
					panelChessBoard.animate(from, to, cBoard.slot(from).cPiece.type, cBoard.slot(from).cPiece.color);
					//////////////////////////////////////////////////////////////////////////////////////////////
					
					CBProcess.finalMove(cBoard, from, to, HumanVSHumanLan.this);
					ways[enemyColor].add(from + " ---> " + to);
					lstWays[enemyColor].setListData(ways[enemyColor]);
					
					// kiem tra van co ket thuc chua
					int state = CBProcess.gameOver(cBoard);
					if(state != Const.STATE_CONTINUE){
						synchronized(timeCount){
							timeCount.notify();
						}
						
						synchronized(lblTimeView){
							lblTimeView.setText("GAMEOVER");
						}
						
						isGameOver = true;
					}

					panelChessBoard.repaint();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void sendObject(Coord from, Coord to){
		try {
			output.writeObject(from);
			output.writeObject(to);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
