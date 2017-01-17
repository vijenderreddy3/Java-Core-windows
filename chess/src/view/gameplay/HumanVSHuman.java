package view.gameplay;

import java.awt.event.MouseEvent;

import model.CBoard;
import model.CPiece;
import model.Const;
import model.Coord;
import model.process.CBProcess;
import model.process.CPProcess;

import view.GameView;

public class HumanVSHuman extends GamePlay{

	public HumanVSHuman(GameView gameView) {
		super(gameView, Const.WHITE);
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
									
									paintBoard = new CBoard(cBoard);
									this.panelChessBoard.repaint();
									this.panelWhiteDied.repaint();
									this.panelBlackDied.repaint();
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
									paintBoard = new CBoard(cBoard);
									this.panelChessBoard.repaint();
									this.panelWhiteDied.repaint();
									this.panelBlackDied.repaint();
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
							panelChessBoard.animate(coordIMoveFrom, coordIMoveTo, cBoard.slot(coordIMoveFrom).cPiece.type, cBoard.slot(coordIMoveFrom).cPiece.color);
														
							CBProcess.finalMove(cBoard, coordIMoveFrom, coordIMoveTo, this);
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
						panelChessBoard.animate(coordIMoveFrom, coordIMoveTo, cBoard.slot(coordIMoveFrom).cPiece.type, cBoard.slot(coordIMoveFrom).cPiece.color);
						CBProcess.finalMove(cBoard, coordIMoveFrom, coordIMoveTo, this);
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
			else{
				if(temp != null){
					if(temp.color == enemyColor){
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
									isEnemySelect = false;
									ways[enemyColor].add("Reque");
									lstWays[enemyColor].setListData(ways[enemyColor]);
									paintBoard = new CBoard(cBoard);
									this.panelChessBoard.repaint();
									this.panelWhiteDied.repaint();
									this.panelBlackDied.repaint();
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
									isEnemySelect = false;
									ways[enemyColor].add("Reque");
									lstWays[enemyColor].setListData(ways[enemyColor]);
									paintBoard = new CBoard(cBoard);
									this.panelChessBoard.repaint();
									this.panelWhiteDied.repaint();
									this.panelBlackDied.repaint();
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
							panelChessBoard.animate(coordIMoveFrom, coordIMoveTo, cBoard.slot(coordIMoveFrom).cPiece.type, cBoard.slot(coordIMoveFrom).cPiece.color);
							
							CBProcess.finalMove(cBoard, coordIMoveFrom, coordIMoveTo, this);
							ways[enemyColor].add(coordIMoveFrom + " ---> " + coordIMoveTo);
							lstWays[enemyColor].setListData(ways[enemyColor]);
							
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
						panelChessBoard.animate(coordIMoveFrom, coordIMoveTo, cBoard.slot(coordIMoveFrom).cPiece.type, cBoard.slot(coordIMoveFrom).cPiece.color);
						
						CBProcess.finalMove(cBoard, coordIMoveFrom, coordIMoveTo, this);
						
						ways[enemyColor].add(coordIMoveFrom + " ---> " + coordIMoveTo);
						lstWays[enemyColor].setListData(ways[enemyColor]);
						
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
		}
		
		this.panelChessBoard.repaint();
	}

	@Override
	public void newGame() {
		// TODO Auto-generated method stub
		
	}

}
