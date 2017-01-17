package process;

import gameplay.GamePlay;
import gameplay.HumanVSCom;

import java.util.ArrayList;

import model.CArray;
import model.CBoard;
import model.CPiece;
import model.CTable;
import model.Const;
import model.Coord;

public class CBProcess {
	public static ArrayList<Coord> generate(CBoard cBoard, int color){
		ArrayList<Coord> list = new ArrayList<Coord>();
		CArray tempArray = cBoard.cArray[color];
		ArrayList<Coord> tempList;
		Coord from;
		
		for(int i = 0; i < tempArray.n; i++){
			tempList = CPProcess.getWays(tempArray.array[i], cBoard);
			from = cBoard.slot(tempArray.array[i].pos).coord;
			
			for(Coord  to: tempList){
				list.add(from);
				list.add(to);
			}
		}
		
		return list;
	}
	
	public static void refresh(CBoard cBoard){
		cBoard.cTable.tableB.clear();
		cBoard.cTable.tableW.clear();
		
		CPiece cp;
		for(int i = 0; i < cBoard.cArray[Const.WHITE].n; i++){
			cp = cBoard.cArray[Const.WHITE].array[i];
			CPProcess.refresh(cp, cBoard);
		}
		
		for(int i = 0; i < cBoard.cArray[Const.BLACK].n; i++){
			cp = cBoard.cArray[Const.BLACK].array[i];
			CPProcess.refresh(cp, cBoard);
		}
	}
	
	public static void move(CBoard cBoard, Coord from, Coord to){
		if(cBoard.slot(to).cPiece != null)
			cBoard.cArray[cBoard.slot(to).cPiece.color].delete(cBoard.slot(to).cPiece);
		cBoard.slot(to).cPiece = cBoard.slot(from).cPiece;
		cBoard.slot(from).cPiece = null;
		cBoard.slot(to).cPiece.move(to);
		bestow(cBoard.slot(to).cPiece, cBoard);
	}
	
	public static void finalMove(CBoard cBoard, Coord from, Coord to, GamePlay gamePlay){
		if(cBoard.slot(to).cPiece != null){
			gamePlay.listDiedCPiece[cBoard.slot(to).cPiece.color].add(cBoard.slot(to).cPiece);
			cBoard.cArray[cBoard.slot(to).cPiece.color].delete(cBoard.slot(to).cPiece);
		}
		cBoard.slot(to).cPiece = cBoard.slot(from).cPiece;
		
		cBoard.slot(from).cPiece = null;
		
		cBoard.slot(to).cPiece.move(to);
		bestow(cBoard.slot(to).cPiece, cBoard);
	}
	
	public static int gameOver(CBoard cBoard){
		boolean bKing = false;
		boolean wKing = false;
		
		for(int i = 0; i < cBoard.cArray[Const.BLACK].n; i++)
			if(cBoard.cArray[Const.BLACK].array[i].type == Const.KING){
				bKing = true;
				break;
			}
		
		for(int i = 0; i < cBoard.cArray[Const.WHITE].n; i++)
			if(cBoard.cArray[Const.WHITE].array[i].type == Const.KING){
				wKing = true;
				break;
			}
		
		if(!bKing){
				return Const.STATE_WHITE_WIN;
			}
		else if(!wKing){
			return Const.STATE_BLACK_WIN;
		}
		
		else return Const.STATE_CONTINUE;
	}
	
	public static void bestow(CPiece cPiece, CBoard cBoard){
		if(cPiece.type == Const.PAWN)
			switch(cPiece.color){
			case Const.WHITE :
				if(cPiece.pos.y == 7){
					cPiece.type = Const.QUEEN;
				}
				break;
			case Const.BLACK :
				if(cPiece.pos.y == 0){
					cPiece.type = Const.QUEEN;
				}
			}
	}
	
	
	public static boolean hasCPieceOn(CBoard cBoard, Coord from, Coord to){
		if(from.x == to.x){
			int x = from.x;
			int i = from.y;
			if(from.y < to.y)
				while(++i < to.y){
					if(cBoard.slot[x][i].cPiece != null)
						return true;
				}
			else
				while(--i > to.y){
					if(cBoard.slot[x][i].cPiece != null)
						return true;
				}
		}
		else if(from.y == to.y){
			int y = from.y;
			int i = from.x;
			if(from.x < to.x)
				while(++i < to.x){
					if(cBoard.slot[i][y].cPiece != null)
						return true;
				}
			else
				while(--i > to.x){
					if(cBoard.slot[i][y].cPiece != null)
						return true;
				}
		}
		else if(Math.abs(from.x - to.x) == Math.abs(from.y - to.y)){
			int x = from.x;
			int y = from.y;
			
			if(from.x < to.x && from.y < to.y)
				while(++x < to.x){
					++y;
					if(cBoard.slot[x][y].cPiece != null)
						return true;
				}
			else if(from.x < to.x && from.y > to.y)
				while(++x < to.x){
					--y;
					if(cBoard.slot[x][y].cPiece != null)
						return true;
				}
			else if(from.x > to.x && from.y > to.y)
				while(--x > to.x){
					--y;
					if(cBoard.slot[x][y].cPiece != null)
						return true;
				}
			else
				while(--x > to.x){
					++y;
					if(cBoard.slot[x][y].cPiece != null)
						return true;
				}
		}
		
		return false;
	}
	
	public static Coord kingWillLoss(CBoard cBoard, int color){
		CBProcess.refresh(cBoard);
		CPiece king = null;
		for(int i = 0; i < cBoard.cArray[color].n; i++)
			if(cBoard.cArray[color].array[i].type == Const.KING)
				king = cBoard.cArray[color].array[i];
		
		if(king != null && color == Const.BLACK){
			if(cBoard.cTable.getBitW(king.pos) == 1)
				return king.pos;
		}
		
		else if(king != null && color == Const.WHITE){
			if(cBoard.cTable.getBitB(king.pos) == 1)
				return king.pos;
		}
		
		return null;
	}
	
	public static boolean roque(CBoard cBoard, CPiece king, CPiece pawn, GamePlay gamePlay){
		int direction = king.pos.x > pawn.pos.x ? -1 : 1;
		CTable dangerous = cBoard.cTable;
		
		CBProcess.refresh(cBoard);
		if(king.firstMoved | pawn.firstMoved)
			return false;
		
		if(CBProcess.hasCPieceOn(cBoard, king.pos, pawn.pos))
			return false;
		
		if(king.color == Const.WHITE){
			if(dangerous.getBitB(king.pos) == 1)
				return false;
			if(dangerous.getBitB(king.pos.x + 1 * direction, king.pos.y) == 1)
				return false;
			if(dangerous.getBitB(king.pos.x + 2 * direction, king.pos.y) == 1)
				return false;
			
			CBProcess.finalMove(cBoard, king.pos, new Coord(king.pos.x + 2 * direction, king.pos.y), gamePlay);
			CBProcess.finalMove(cBoard, pawn.pos, new Coord(king.pos.x - 1 * direction, king.pos.y), gamePlay);
			return true;
		}
		
		return false;
	}
	
}