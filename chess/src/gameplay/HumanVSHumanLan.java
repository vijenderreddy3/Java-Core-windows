package gameplay;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.CBoard;
import model.CPiece;
import model.Const;
import model.Coord;
import process.CBProcess;
import process.CPProcess;
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
		if(isGameOver)
			return;
		
		synchronized(cBoard){
			Coord coord = new Coord(e.getX() / Const.UNIT, 7 - e.getY() / Const.UNIT);
			
			CPiece temp = cBoard.slot(coord).cPiece;
			
			if(!isEnemySelect){
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
									listUndo.push(Const.REQUE);
									isRoque = true;
									coordIMoveFrom = null;
									coordIMoveTo = null;
									listMove.clear();
									isEnemySelect = true;
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
							sendObject(coordIMoveFrom, coordIMoveTo);
							
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
						
						listUndo.push(cBoard.slot(coordIMoveFrom).cPiece.firstMoved);
						listUndo.push(isEnemySelect);
						listUndo.push(cBoard.slot(coordIMoveTo).cPiece);
						listUndo.push(coordIMoveTo);
						listUndo.push(coordIMoveFrom);
						listUndo.push(Const.NORMAL);
						
						CBProcess.finalMove(cBoard, coordIMoveFrom, coordIMoveTo, this);
						
						sendObject(coordIMoveFrom, coordIMoveTo);
						
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
					
					CBProcess.finalMove(cBoard, from, to, HumanVSHumanLan.this);
					ways[enemyColor].add(from + " ---> " + to);
					lstWays[enemyColor].setListData(ways[enemyColor]);
					
					isEnemySelect = false;
					
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
					
					paintBoard = new CBoard(cBoard);
					panelChessBoard.repaint();
					panelWhiteDied.repaint();
					panelBlackDied.repaint();
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
