package model.process;

import java.util.ArrayList;

import model.CBoard;
import model.CPiece;
import model.CTable;
import model.Const;
import model.Coord;

public class CPProcess {

	// tra ve danh sach cac diem co the toi
	// tham chieu dua vao list lay tu slot
	public static ArrayList<Coord> getWays(CPiece cPiece, CBoard cBoard) {	
		switch(cPiece.type){
		case Const.KING 	: 	return getWaysOfKing  (cPiece, cBoard);
		case Const.QUEEN 	: 	return getWaysOfQueen (cPiece, cBoard);
		case Const.BISHOP 	: 	return getWaysOfBishop(cPiece, cBoard);
		case Const.KNIGHT 	: 	return getWaysOfKnight(cPiece, cBoard);
		case Const.ROOK 	: 	return getWaysOfRook  (cPiece, cBoard);
		case Const.PAWN 	: 	return getWaysOfPawn  (cPiece, cBoard);
		}
		
		return null;
	}

	private static ArrayList<Coord> getWaysOfPawn(CPiece cPiece, CBoard cBoard) {		
		ArrayList<Coord> listCoord = new ArrayList<Coord>();
		Coord coord;
		
		if(cPiece.color == Const.BLACK){
			// truong hop co the an
			// O dan xet co o torng ban co khong
			if(CProcess.isOnCBoard(cPiece.pos.x - 1, cPiece.pos.y - 1)){
				coord = cBoard.slot[cPiece.pos.x - 1][cPiece.pos.y - 1].coord;
				CPiece temp = cBoard.slot(coord).cPiece;
				// O dang xet co quan dich khong
				if(temp != null)
					// Quan trong o do co cung mau voi minh ko
					if(temp.color == Const.WHITE){
						// neu khac mau thi them toa do do vao
						listCoord.add(coord);
					}
			}
			
			// O dan xet co o torng ban co khong
			if(CProcess.isOnCBoard(cPiece.pos.x + 1, cPiece.pos.y - 1)){
				coord = cBoard.slot[cPiece.pos.x + 1][cPiece.pos.y - 1].coord;
				
				CPiece temp = cBoard.slot(coord).cPiece;
				// O dang xet co quan dich khong
				if(temp != null)
					// Quan trong o do co cung mau voi minh ko
					if(temp.color == Const.WHITE){
						// neu khac mau thi them toa do do vao
						listCoord.add(coord);
					}
			}
			
			// truong hop di chuyen
			// di chuyen lan dau
			if(!cPiece.firstMoved){
				boolean before = false;
				if(CProcess.isOnCBoard(cPiece.pos.x, cPiece.pos.y - 1)){
					coord = cBoard.slot[cPiece.pos.x][cPiece.pos.y - 1].coord;
					
					if(cBoard.slot(coord).cPiece == null){
						listCoord.add(coord);
						before = true;
					}
				}
				
				if(before)
					if(CProcess.isOnCBoard(cPiece.pos.x, cPiece.pos.y - 2)){
						coord = cBoard.slot[cPiece.pos.x][cPiece.pos.y - 2].coord;
						
						if(cBoard.slot(coord).cPiece == null){
							listCoord.add(coord);
						}
					}
			}
			// khong phai lan dau di chuyen
			else{
				if(CProcess.isOnCBoard(cPiece.pos.x, cPiece.pos.y - 1)){
					coord = cBoard.slot[cPiece.pos.x][cPiece.pos.y - 1].coord;
					
					if(cBoard.slot(coord).cPiece == null){
						listCoord.add(coord);
					}
				}
			}
		}
		// Neu la ben trang
		else {
			// truong hop co the an
			// O dan xet co o torng ban co khong
			if(CProcess.isOnCBoard(cPiece.pos.x - 1, cPiece.pos.y + 1)){
				coord = cBoard.slot[cPiece.pos.x - 1][cPiece.pos.y + 1].coord;
				CPiece temp = cBoard.slot(coord).cPiece;
				// O dang xet co quan dich khong
				if(temp != null)
					// Quan trong o do co cung mau voi minh ko
					// neu khac mau thi them toa do do vao
					if(temp.color == Const.BLACK){
						listCoord.add(coord);
					}
			}
			
			// O dan xet co o torng ban co khong
			if(CProcess.isOnCBoard(cPiece.pos.x + 1, cPiece.pos.y + 1)){
				coord = cBoard.slot[cPiece.pos.x + 1][cPiece.pos.y + 1].coord;
				
				CPiece temp = cBoard.slot(coord).cPiece;
				// O dang xet co quan dich khong
				if(temp != null)
					// Quan trong o do co cung mau voi minh ko
					if(temp.color == Const.BLACK){
						// neu khac mau thi them toa do do vao
						listCoord.add(coord);
					}
			}
			
			// truong hop di chuyen
			// di chuyen lan dau
			if(!cPiece.firstMoved){
				boolean before = false;
				if(CProcess.isOnCBoard(cPiece.pos.x, cPiece.pos.y + 1)){
					coord = cBoard.slot[cPiece.pos.x][cPiece.pos.y + 1].coord;
					
					if(cBoard.slot(coord).cPiece == null){
						listCoord.add(coord);
						before = true;
					}
				}
				
				if(before)
					if(CProcess.isOnCBoard(cPiece.pos.x, cPiece.pos.y + 2)){
						coord = cBoard.slot[cPiece.pos.x][cPiece.pos.y + 2].coord;
						
						if(cBoard.slot(coord).cPiece == null){
							listCoord.add(coord);
						}
					}
			}
			// khong phai lan dau di chuyen
			else{
				if(CProcess.isOnCBoard(cPiece.pos.x, cPiece.pos.y + 1)){
					coord = cBoard.slot[cPiece.pos.x][cPiece.pos.y + 1].coord;
					
					if(cBoard.slot(coord).cPiece == null){
						listCoord.add(coord);
					}
				}
			}
		}
		
		return listCoord;
	}

	private static ArrayList<Coord> getWaysOfRook(CPiece cPiece, CBoard cBoard) {
		ArrayList<Coord> listCoord = new ArrayList<Coord>();
		Coord coord;
		
		if(CProcess.isOnCBoard(cPiece.pos.x, cPiece.pos.y + 1)){
			coord = cBoard.slot[cPiece.pos.x][cPiece.pos.y + 1].coord;
			
			while(CProcess.isOnCBoard(coord)){
				// neu la vung trong thi them vung do vao va nhay len vung ke tiep 
				if(cBoard.slot(coord).cPiece == null){
					listCoord.add(coord);
					
					if(CProcess.isOnCBoard(coord.x, coord.y + 1))
						coord = cBoard.slot[coord.x][coord.y + 1].coord;
					else
						break;
				}
				// neu la quan dich thi co the an
				else if(cBoard.slot(coord).cPiece.color != cPiece.color){
					listCoord.add(coord);
					break;
				}
				// neu la quan minh thi thoi
				else
					break;
			}
		}
		
		if(CProcess.isOnCBoard(cPiece.pos.x, cPiece.pos.y - 1)){
			coord = cBoard.slot[cPiece.pos.x][cPiece.pos.y - 1].coord;
			
			while(CProcess.isOnCBoard(coord)){
				// neu la vung trong thi them vung do vao va nhay len vung ke tiep 
				if(cBoard.slot(coord).cPiece == null){
					listCoord.add(coord);

					if(CProcess.isOnCBoard(coord.x, coord.y - 1))
						coord = cBoard.slot[coord.x][coord.y - 1].coord;
					else
						break;
				}
				// neu la quan dich thi co the an
				else if(cBoard.slot(coord).cPiece.color != cPiece.color){
					listCoord.add(coord);
					break;
				}
				// neu la quan minh thi thoi
				else
					break;
			}
		}
		
		if(CProcess.isOnCBoard(cPiece.pos.x + 1, cPiece.pos.y)){
			coord = cBoard.slot[cPiece.pos.x + 1][cPiece.pos.y].coord;
			
			while(CProcess.isOnCBoard(coord)){
				// neu la vung trong thi them vung do vao va nhay len vung ke tiep 
				if(cBoard.slot(coord).cPiece == null){
					listCoord.add(coord);
					
					if(CProcess.isOnCBoard(coord.x + 1, coord.y))
						coord = cBoard.slot[coord.x + 1][coord.y].coord;
					else
						break;
				}
				// neu la quan dich thi co the an
				else if(cBoard.slot(coord).cPiece.color != cPiece.color){
					listCoord.add(coord);
					break;
				}
				// neu la quan minh thi thoi
				else
					break;
			}
		}
		
		if(CProcess.isOnCBoard(cPiece.pos.x - 1, cPiece.pos.y)){
			coord = cBoard.slot[cPiece.pos.x - 1][cPiece.pos.y].coord;
			
			while(CProcess.isOnCBoard(coord)){
				// neu la vung trong thi them vung do vao va nhay len vung ke tiep 
				if(cBoard.slot(coord).cPiece == null){
					listCoord.add(coord);
					
					if(CProcess.isOnCBoard(coord.x - 1, coord.y))
						coord = cBoard.slot[coord.x - 1][coord.y].coord;
					else
						break;
				}
				// neu la quan dich thi co the an
				else if(cBoard.slot(coord).cPiece.color != cPiece.color){
					listCoord.add(coord);
					break;
				}
				// neu la quan minh thi thoi
				else
					break;
			}
		}
		
		return listCoord;
	}

	private static ArrayList<Coord> getWaysOfKnight(CPiece cPiece, CBoard cBoard) {
		ArrayList<Coord> listCoord = new ArrayList<Coord>();
		Coord coord = null;
		
		
		if(CProcess.isOnCBoard(cPiece.pos.x - 2, cPiece.pos.y - 1)){
			coord = cBoard.slot[cPiece.pos.x - 2][cPiece.pos.y - 1].coord;
			if(cBoard.slot(coord).cPiece == null){
				listCoord.add(coord);
			}
			else if(cBoard.slot(coord).cPiece.color != cPiece.color){
				listCoord.add(coord);
			}
		}
		
		if(CProcess.isOnCBoard(cPiece.pos.x - 2, cPiece.pos.y + 1)){
			coord = cBoard.slot[cPiece.pos.x - 2][cPiece.pos.y + 1].coord;
			if(cBoard.slot(coord).cPiece == null){
				listCoord.add(coord);
			}
			else if(cBoard.slot(coord).cPiece.color != cPiece.color){
				listCoord.add(coord);
			}
		}
		
		
		if(CProcess.isOnCBoard(cPiece.pos.x + 2, cPiece.pos.y - 1)){
			coord = cBoard.slot[cPiece.pos.x + 2][cPiece.pos.y - 1].coord;
			
			if(cBoard.slot(coord).cPiece == null){
				listCoord.add(coord);
			}
			else if(cBoard.slot(coord).cPiece.color != cPiece.color){
				listCoord.add(coord);
			}
		}
		
		
		if(CProcess.isOnCBoard(cPiece.pos.x + 2, cPiece.pos.y + 1)){
			coord = cBoard.slot[cPiece.pos.x + 2][cPiece.pos.y + 1].coord;
			if(cBoard.slot(coord).cPiece == null){
				listCoord.add(coord);
			}
			else if(cBoard.slot(coord).cPiece.color != cPiece.color){
				listCoord.add(coord);
			}
		}
		
		
		if(CProcess.isOnCBoard(cPiece.pos.x - 1, cPiece.pos.y - 2)){
			coord = cBoard.slot[cPiece.pos.x - 1][cPiece.pos.y - 2].coord;
			if(cBoard.slot(coord).cPiece == null){
				listCoord.add(coord);
			}
			else if(cBoard.slot(coord).cPiece.color != cPiece.color){
				listCoord.add(coord);
			}
		}
		
		
		if(CProcess.isOnCBoard(cPiece.pos.x - 1, cPiece.pos.y + 2)){
			coord = cBoard.slot[cPiece.pos.x - 1][cPiece.pos.y + 2].coord;
			if(cBoard.slot(coord).cPiece == null){
				listCoord.add(coord);
			}
			else if(cBoard.slot(coord).cPiece.color != cPiece.color){
				listCoord.add(coord);
			}
		}
		
		
		if(CProcess.isOnCBoard(cPiece.pos.x + 1, cPiece.pos.y - 2)){
			coord = cBoard.slot[cPiece.pos.x + 1][cPiece.pos.y - 2].coord;
			if(cBoard.slot(coord).cPiece == null){
				listCoord.add(coord);
			}
			else if(cBoard.slot(coord).cPiece.color != cPiece.color){
				listCoord.add(coord);
			}
		}
		
		
		if(CProcess.isOnCBoard(cPiece.pos.x + 1, cPiece.pos.y + 2)){
			coord = cBoard.slot[cPiece.pos.x + 1][cPiece.pos.y + 2].coord;
			if(cBoard.slot(coord).cPiece == null){
				listCoord.add(coord);
			}
			else if(cBoard.slot(coord).cPiece.color != cPiece.color){
				listCoord.add(coord);
			}
		}
		
		return listCoord;
	}

	private static ArrayList<Coord> getWaysOfBishop(CPiece cPiece, CBoard cBoard) {
		ArrayList<Coord> listCoord = new ArrayList<Coord>();
		Coord coord;
		
		if(CProcess.isOnCBoard(cPiece.pos.x + 1, cPiece.pos.y + 1)){
			coord = cBoard.slot[cPiece.pos.x + 1][cPiece.pos.y + 1].coord;
			
			while(CProcess.isOnCBoard(coord)){
				// neu la vung trong thi them vung do vao va nhay len vung ke tiep 
				if(cBoard.slot(coord).cPiece == null){
					listCoord.add(coord);
					
					if(CProcess.isOnCBoard(coord.x + 1, coord.y + 1))
						coord = cBoard.slot[coord.x + 1][coord.y + 1].coord;
					else
						break;
				}
				// neu la quan dich thi co the an
				else if(cBoard.slot(coord).cPiece.color != cPiece.color){
					listCoord.add(coord);
					break;
				}
				// neu la quan minh thi thoi
				else
					break;
			}
		}
		
		if(CProcess.isOnCBoard(cPiece.pos.x - 1, cPiece.pos.y - 1)){
			coord = cBoard.slot[cPiece.pos.x - 1][cPiece.pos.y - 1].coord;
			
			while(CProcess.isOnCBoard(coord)){
				// neu la vung trong thi them vung do vao va nhay len vung ke tiep 
				if(cBoard.slot(coord).cPiece == null){
					listCoord.add(coord);
					
					if(CProcess.isOnCBoard(coord.x - 1, coord.y - 1))
						coord = cBoard.slot[coord.x - 1][coord.y - 1].coord;
					else
						break;
				}
				// neu la quan dich thi co the an
				else if(cBoard.slot(coord).cPiece.color != cPiece.color){
					listCoord.add(coord);
					break;
				}
				// neu la quan minh thi thoi
				else
					break;
			}
		}
		
		if(CProcess.isOnCBoard(cPiece.pos.x + 1, cPiece.pos.y - 1)){
			coord = cBoard.slot[cPiece.pos.x + 1][cPiece.pos.y - 1].coord;
			
			while(CProcess.isOnCBoard(coord)){
				// neu la vung trong thi them vung do vao va nhay len vung ke tiep 
				if(cBoard.slot(coord).cPiece == null){
					listCoord.add(coord);
					
					if(CProcess.isOnCBoard(coord.x + 1, coord.y - 1))
						coord = cBoard.slot[coord.x + 1][coord.y - 1].coord;
					else
						break;
				}
				// neu la quan dich thi co the an
				else if(cBoard.slot(coord).cPiece.color != cPiece.color){
					listCoord.add(coord);
					break;
				}
				// neu la quan minh thi thoi
				else
					break;
			}
		}
		
		if(CProcess.isOnCBoard(cPiece.pos.x - 1, cPiece.pos.y + 1)){
			coord = cBoard.slot[cPiece.pos.x - 1][cPiece.pos.y + 1].coord;
			
			while(CProcess.isOnCBoard(coord)){
				// neu la vung trong thi them vung do vao va nhay len vung ke tiep 
				if(cBoard.slot(coord).cPiece == null){
					listCoord.add(coord);
					
					if(CProcess.isOnCBoard(coord.x - 1, coord.y + 1))
						coord = cBoard.slot[coord.x - 1][coord.y + 1].coord;
					else
						break;
				}
				// neu la quan dich thi co the an
				else if(cBoard.slot(coord).cPiece.color != cPiece.color){
					listCoord.add(coord);
					break;
				}
				// neu la quan minh thi thoi
				else
					break;
			}
		}
		
		return listCoord;
	}

	private static ArrayList<Coord> getWaysOfQueen(CPiece cPiece, CBoard cBoard) {
		ArrayList<Coord> listCoord = new ArrayList<Coord>();
		Coord coord;
		
		if(CProcess.isOnCBoard(cPiece.pos.x, cPiece.pos.y + 1)){
			coord = cBoard.slot[cPiece.pos.x][cPiece.pos.y + 1].coord;
			
			while(CProcess.isOnCBoard(coord)){
				// neu la vung trong thi them vung do vao va nhay len vung ke tiep 
				if(cBoard.slot(coord).cPiece == null){
					listCoord.add(coord);
					
					
					
					if(CProcess.isOnCBoard(coord.x, coord.y + 1))
						coord = cBoard.slot[coord.x][coord.y + 1].coord;
					else
						break;
				}
				// neu la quan dich thi co the an
				else if(cBoard.slot(coord).cPiece.color != cPiece.color){
					listCoord.add(coord);
					
					
					break;
				}
				// neu la quan minh thi thoi
				else
					break;
			}
		}
		
		if(CProcess.isOnCBoard(cPiece.pos.x, cPiece.pos.y - 1)){
			coord = cBoard.slot[cPiece.pos.x][cPiece.pos.y - 1].coord;
			
			while(CProcess.isOnCBoard(coord)){
				// neu la vung trong thi them vung do vao va nhay len vung ke tiep 
				if(cBoard.slot(coord).cPiece == null){
					listCoord.add(coord);
					
					

					if(CProcess.isOnCBoard(coord.x, coord.y - 1))
						coord = cBoard.slot[coord.x][coord.y - 1].coord;
					else
						break;
				}
				// neu la quan dich thi co the an
				else if(cBoard.slot(coord).cPiece.color != cPiece.color){
					listCoord.add(coord);
					
					
					break;
				}
				// neu la quan minh thi thoi
				else
					break;
			}
		}
		
		if(CProcess.isOnCBoard(cPiece.pos.x + 1, cPiece.pos.y)){
			coord = cBoard.slot[cPiece.pos.x + 1][cPiece.pos.y].coord;
			
			while(CProcess.isOnCBoard(coord)){
				// neu la vung trong thi them vung do vao va nhay len vung ke tiep 
				if(cBoard.slot(coord).cPiece == null){
					listCoord.add(coord);
					
					
					
					if(CProcess.isOnCBoard(coord.x + 1, coord.y))
						coord = cBoard.slot[coord.x + 1][coord.y].coord;
					else
						break;
				}
				// neu la quan dich thi co the an
				else if(cBoard.slot(coord).cPiece.color != cPiece.color){
					listCoord.add(coord);
					
					
					break;
				}
				// neu la quan minh thi thoi
				else
					break;
			}
		}
		
		if(CProcess.isOnCBoard(cPiece.pos.x - 1, cPiece.pos.y)){
			coord = cBoard.slot[cPiece.pos.x - 1][cPiece.pos.y].coord;
			
			while(CProcess.isOnCBoard(coord)){
				// neu la vung trong thi them vung do vao va nhay len vung ke tiep 
				if(cBoard.slot(coord).cPiece == null){
					listCoord.add(coord);
					
					
					
					if(CProcess.isOnCBoard(coord.x - 1, coord.y))
						coord = cBoard.slot[coord.x - 1][coord.y].coord;
					else
						break;
				}
				// neu la quan dich thi co the an
				else if(cBoard.slot(coord).cPiece.color != cPiece.color){
					listCoord.add(coord);
					
					
					break;
				}
				// neu la quan minh thi thoi
				else
					break;
			}
		}
		
		if(CProcess.isOnCBoard(cPiece.pos.x + 1, cPiece.pos.y + 1)){
			coord = cBoard.slot[cPiece.pos.x + 1][cPiece.pos.y + 1].coord;
			
			while(CProcess.isOnCBoard(coord)){
				// neu la vung trong thi them vung do vao va nhay len vung ke tiep 
				if(cBoard.slot(coord).cPiece == null){
					listCoord.add(coord);
					
					
					
					if(CProcess.isOnCBoard(coord.x + 1, coord.y + 1))
						coord = cBoard.slot[coord.x + 1][coord.y + 1].coord;
					else
						break;
				}
				// neu la quan dich thi co the an
				else if(cBoard.slot(coord).cPiece.color != cPiece.color){
					listCoord.add(coord);
					
					
					break;
				}
				// neu la quan minh thi thoi
				else
					break;
			}
		}
		
		if(CProcess.isOnCBoard(cPiece.pos.x - 1, cPiece.pos.y - 1)){
			coord = cBoard.slot[cPiece.pos.x - 1][cPiece.pos.y - 1].coord;
			
			while(CProcess.isOnCBoard(coord)){
				// neu la vung trong thi them vung do vao va nhay len vung ke tiep 
				if(cBoard.slot(coord).cPiece == null){
					listCoord.add(coord);
					
					
					
					if(CProcess.isOnCBoard(coord.x - 1, coord.y - 1))
						coord = cBoard.slot[coord.x - 1][coord.y - 1].coord;
					else
						break;
				}
				// neu la quan dich thi co the an
				else if(cBoard.slot(coord).cPiece.color != cPiece.color){
					listCoord.add(coord);
					
					
					break;
				}
				// neu la quan minh thi thoi
				else
					break;
			}
		}
		
		if(CProcess.isOnCBoard(cPiece.pos.x + 1, cPiece.pos.y - 1)){
			coord = cBoard.slot[cPiece.pos.x + 1][cPiece.pos.y - 1].coord;
			
			while(CProcess.isOnCBoard(coord)){
				// neu la vung trong thi them vung do vao va nhay len vung ke tiep 
				if(cBoard.slot(coord).cPiece == null){
					listCoord.add(coord);
					
					
					
					if(CProcess.isOnCBoard(coord.x + 1, coord.y - 1))
						coord = cBoard.slot[coord.x + 1][coord.y - 1].coord;
					else
						break;
				}
				// neu la quan dich thi co the an
				else if(cBoard.slot(coord).cPiece.color != cPiece.color){
					listCoord.add(coord);
					
					
					break;
				}
				// neu la quan minh thi thoi
				else
					break;
			}
		}
		
		if(CProcess.isOnCBoard(cPiece.pos.x - 1, cPiece.pos.y + 1)){
			coord = cBoard.slot[cPiece.pos.x - 1][cPiece.pos.y + 1].coord;
			
			while(CProcess.isOnCBoard(coord)){
				// neu la vung trong thi them vung do vao va nhay len vung ke tiep 
				if(cBoard.slot(coord).cPiece == null){
					listCoord.add(coord);
					
					
					
					if(CProcess.isOnCBoard(coord.x - 1, coord.y + 1))
						coord = cBoard.slot[coord.x - 1][coord.y + 1].coord;
					else
						break;
				}
				// neu la quan dich thi co the an
				else if(cBoard.slot(coord).cPiece.color != cPiece.color){
					listCoord.add(coord);
					
					
					break;
				}
				// neu la quan minh thi thoi
				else
					break;
			}
		}
		
		return listCoord;
	}

	private static ArrayList<Coord> getWaysOfKing(CPiece cPiece, CBoard cBoard) {
		ArrayList<Coord> listCoord = new ArrayList<Coord>();
		Coord coord = null;
		
		
		if(CProcess.isOnCBoard(cPiece.pos.x - 1, cPiece.pos.y - 1)){
			coord = cBoard.slot[cPiece.pos.x - 1][cPiece.pos.y - 1].coord;
			
			if(cBoard.slot(coord).cPiece == null){
				listCoord.add(coord);
				
				
			}
			else if(cBoard.slot(coord).cPiece.color != cPiece.color){
				listCoord.add(coord);
				
				
			}
		}
		
		if(CProcess.isOnCBoard(cPiece.pos.x - 1, cPiece.pos.y + 1)){
			coord = cBoard.slot[cPiece.pos.x - 1][cPiece.pos.y + 1].coord;
			
			if(cBoard.slot(coord).cPiece == null){
				listCoord.add(coord);
				
				
			}
			else if(cBoard.slot(coord).cPiece.color != cPiece.color){
				listCoord.add(coord);
				
				
			}
		}
		
		if(CProcess.isOnCBoard(cPiece.pos.x + 1, cPiece.pos.y - 1)){
			coord = cBoard.slot[cPiece.pos.x + 1][cPiece.pos.y - 1].coord;
			
			if(cBoard.slot(coord).cPiece == null){
				listCoord.add(coord);
				
				
			}
			else if(cBoard.slot(coord).cPiece.color != cPiece.color){
				listCoord.add(coord);
				
				
			}
		}
		
		if(CProcess.isOnCBoard(cPiece.pos.x + 1, cPiece.pos.y + 1)){
			coord = cBoard.slot[cPiece.pos.x + 1][cPiece.pos.y + 1].coord;
			
			if(cBoard.slot(coord).cPiece == null){
				listCoord.add(coord);
				
				
			}
			else if(cBoard.slot(coord).cPiece.color != cPiece.color){
				listCoord.add(coord);
				
				
			}
		}
		
		if(CProcess.isOnCBoard(cPiece.pos.x, cPiece.pos.y - 1)){
			coord = cBoard.slot[cPiece.pos.x][cPiece.pos.y - 1].coord;
			
			if(cBoard.slot(coord).cPiece == null){
				listCoord.add(coord);
				
				
			}
			else if(cBoard.slot(coord).cPiece.color != cPiece.color){
				listCoord.add(coord);
				
				
			}
		}
		
		if(CProcess.isOnCBoard(cPiece.pos.x, cPiece.pos.y + 1)){
			coord = cBoard.slot[cPiece.pos.x][cPiece.pos.y + 1].coord;
			
			if(cBoard.slot(coord).cPiece == null){
				listCoord.add(coord);
				
				
			}
			else if(cBoard.slot(coord).cPiece.color != cPiece.color){
				listCoord.add(coord);
				
				
			}
		}
		
		if(CProcess.isOnCBoard(cPiece.pos.x - 1, cPiece.pos.y)){
			coord = cBoard.slot[cPiece.pos.x - 1][cPiece.pos.y].coord;
			
			if(cBoard.slot(coord).cPiece == null){
				listCoord.add(coord);
				
				
			}
			else if(cBoard.slot(coord).cPiece.color != cPiece.color){
				listCoord.add(coord);
				
				
			}
		}
		
		if(CProcess.isOnCBoard(cPiece.pos.x + 1, cPiece.pos.y)){
			coord = cBoard.slot[cPiece.pos.x + 1][cPiece.pos.y].coord;
			
			if(cBoard.slot(coord).cPiece == null){
				listCoord.add(coord);
				
				
			}
			else if(cBoard.slot(coord).cPiece.color != cPiece.color){
				listCoord.add(coord);
				
				
			}
		}
		
		return listCoord;
	}
	
	public static void refresh(CPiece cPiece, CBoard cBoard) {
		cPiece.ways = 0;
		
		switch(cPiece.type){
		case Const.KING 	: 	refreshKing  (cPiece, cBoard); break;
		case Const.QUEEN 	: 	refreshQueen (cPiece, cBoard); break;
		case Const.BISHOP 	: 	refreshBishop(cPiece, cBoard); break;
		case Const.KNIGHT 	: 	refreshKnight(cPiece, cBoard); break;
		case Const.ROOK 	: 	refreshRook  (cPiece, cBoard); break;
		case Const.PAWN 	: 	refreshPawn  (cPiece, cBoard); break;
		}
	}

	private static void refreshPawn(CPiece cPiece, CBoard cBoard) {
		Coord coord;
		
		if(cPiece.color == Const.BLACK){
			// truong hop co the an
			// O dan xet co o torng ban co khong
			if(CProcess.isOnCBoard(cPiece.pos.x - 1, cPiece.pos.y - 1)){
				coord = cBoard.slot[cPiece.pos.x - 1][cPiece.pos.y - 1].coord;
				CPiece temp = cBoard.slot(coord).cPiece;
				// O dang xet co quan dich khong
				if(temp != null)
					// Quan trong o do co cung mau voi minh ko
					if(temp.color == Const.WHITE){
						// them so nuoc di cua quan co
						cPiece.ways++;
						// dat lai bit
						cBoard.cTable.setBitB(coord, 1);
					}
			}
			
			// O dan xet co o torng ban co khong
			if(CProcess.isOnCBoard(cPiece.pos.x + 1, cPiece.pos.y - 1)){
				coord = cBoard.slot[cPiece.pos.x + 1][cPiece.pos.y - 1].coord;
				
				CPiece temp = cBoard.slot(coord).cPiece;
				// O dang xet co quan dich khong
				if(temp != null)
					// Quan trong o do co cung mau voi minh ko
					if(temp.color == Const.WHITE){
						// them so nuoc di cua quan co
						cPiece.ways++;
						// dat lai bit
						cBoard.cTable.setBitB(coord, 1);
					}
			}
			
			// truong hop di chuyen
			// di chuyen lan dau
			if(!cPiece.firstMoved){
				if(CProcess.isOnCBoard(cPiece.pos.x, cPiece.pos.y - 1)){
					coord = cBoard.slot[cPiece.pos.x][cPiece.pos.y - 1].coord;
					
					if(cBoard.slot(coord).cPiece == null){
						cPiece.ways++;
					}
				}
				
				if(CProcess.isOnCBoard(cPiece.pos.x, cPiece.pos.y - 2)){
					coord = cBoard.slot[cPiece.pos.x][cPiece.pos.y - 2].coord;
					
					if(cBoard.slot(coord).cPiece == null){
						cPiece.ways++;
					}
				}
			}
			// khong phai lan dau di chuyen
			else{
				if(CProcess.isOnCBoard(cPiece.pos.x, cPiece.pos.y - 1)){
					coord = cBoard.slot[cPiece.pos.x][cPiece.pos.y - 1].coord;
					
					if(cBoard.slot(coord).cPiece == null){
						cPiece.ways++;
					}
				}
			}
		}
		// Neu la ben trang
		else {
			// truong hop co the an
			// O dan xet co o torng ban co khong
			if(CProcess.isOnCBoard(cPiece.pos.x - 1, cPiece.pos.y + 1)){
				coord = cBoard.slot[cPiece.pos.x - 1][cPiece.pos.y + 1].coord;
				CPiece temp = cBoard.slot(coord).cPiece;
				// O dang xet co quan dich khong
				if(temp != null)
					// Quan trong o do co cung mau voi minh ko
					// neu khac mau thi them toa do do vao
					if(temp.color == Const.BLACK){
						// them so nuoc di cua quan co
						cPiece.ways++;
						// dat lai bit
						cBoard.cTable.setBitW(coord, 1);
					}
			}
			
			// O dan xet co o torng ban co khong
			if(CProcess.isOnCBoard(cPiece.pos.x + 1, cPiece.pos.y + 1)){
				coord = cBoard.slot[cPiece.pos.x + 1][cPiece.pos.y + 1].coord;
				
				CPiece temp = cBoard.slot(coord).cPiece;
				// O dang xet co quan dich khong
				if(temp != null)
					// Quan trong o do co cung mau voi minh ko
					if(temp.color == Const.BLACK){
						// them so nuoc di cua quan co
						cPiece.ways++;
						// dat lai bit
						cBoard.cTable.setBitW(coord, 1);
					}
			}
			
			// truong hop di chuyen
			// di chuyen lan dau
			if(!cPiece.firstMoved){
				if(CProcess.isOnCBoard(cPiece.pos.x, cPiece.pos.y + 1)){
					coord = cBoard.slot[cPiece.pos.x][cPiece.pos.y + 1].coord;
					
					if(cBoard.slot(coord).cPiece == null){
						cPiece.ways++;
					}
				}
				
				if(CProcess.isOnCBoard(cPiece.pos.x, cPiece.pos.y + 2)){
					coord = cBoard.slot[cPiece.pos.x][cPiece.pos.y + 2].coord;
					
					if(cBoard.slot(coord).cPiece == null){
						cPiece.ways++;
					}
				}
			}
			// khong phai lan dau di chuyen
			else{
				if(CProcess.isOnCBoard(cPiece.pos.x, cPiece.pos.y + 1)){
					coord = cBoard.slot[cPiece.pos.x][cPiece.pos.y + 1].coord;
					
					if(cBoard.slot(coord).cPiece == null){
						cPiece.ways++;
					}
				}
			}
		}
	}

	private static void refreshRook(CPiece cPiece, CBoard cBoard) {
		Coord coord;
		
		if(CProcess.isOnCBoard(cPiece.pos.x, cPiece.pos.y + 1)){
			coord = cBoard.slot[cPiece.pos.x][cPiece.pos.y + 1].coord;
			
			while(CProcess.isOnCBoard(coord)){
				// neu la vung trong thi them vung do vao va nhay len vung ke tiep 
				if(cBoard.slot(coord).cPiece == null){
					cPiece.ways++;
					cBoard.cTable.setBit(coord, 1, cPiece.color);
					
					if(CProcess.isOnCBoard(coord.x, coord.y + 1))
						coord = cBoard.slot[coord.x][coord.y + 1].coord;
					else
						break;
				}
				// neu la quan dich thi co the an
				else if(cBoard.slot(coord).cPiece.color != cPiece.color){
					cPiece.ways++;
					cBoard.cTable.setBit(coord, 1, cPiece.color);
					break;
				}
				// neu la quan minh thi thoi
				else
					break;
			}
		}
		
		if(CProcess.isOnCBoard(cPiece.pos.x, cPiece.pos.y - 1)){
			coord = cBoard.slot[cPiece.pos.x][cPiece.pos.y - 1].coord;
			
			while(CProcess.isOnCBoard(coord)){
				// neu la vung trong thi them vung do vao va nhay len vung ke tiep 
				if(cBoard.slot(coord).cPiece == null){
					cPiece.ways++;
					cBoard.cTable.setBit(coord, 1, cPiece.color);

					if(CProcess.isOnCBoard(coord.x, coord.y - 1))
						coord = cBoard.slot[coord.x][coord.y - 1].coord;
					else
						break;
				}
				// neu la quan dich thi co the an
				else if(cBoard.slot(coord).cPiece.color != cPiece.color){
					cPiece.ways++;
					cBoard.cTable.setBit(coord, 1, cPiece.color);
					break;
				}
				// neu la quan minh thi thoi
				else
					break;
			}
		}
		
		if(CProcess.isOnCBoard(cPiece.pos.x + 1, cPiece.pos.y)){
			coord = cBoard.slot[cPiece.pos.x + 1][cPiece.pos.y].coord;
			
			while(CProcess.isOnCBoard(coord)){
				// neu la vung trong thi them vung do vao va nhay len vung ke tiep 
				if(cBoard.slot(coord).cPiece == null){
					cPiece.ways++;
					cBoard.cTable.setBit(coord, 1, cPiece.color);
					
					if(CProcess.isOnCBoard(coord.x + 1, coord.y))
						coord = cBoard.slot[coord.x + 1][coord.y].coord;
					else
						break;
				}
				// neu la quan dich thi co the an
				else if(cBoard.slot(coord).cPiece.color != cPiece.color){
					cPiece.ways++;
					cBoard.cTable.setBit(coord, 1, cPiece.color);
					break;
				}
				// neu la quan minh thi thoi
				else
					break;
			}
		}
		
		if(CProcess.isOnCBoard(cPiece.pos.x - 1, cPiece.pos.y)){
			coord = cBoard.slot[cPiece.pos.x - 1][cPiece.pos.y].coord;
			
			while(CProcess.isOnCBoard(coord)){
				// neu la vung trong thi them vung do vao va nhay len vung ke tiep 
				if(cBoard.slot(coord).cPiece == null){
					cPiece.ways++;
					cBoard.cTable.setBit(coord, 1, cPiece.color);
					
					if(CProcess.isOnCBoard(coord.x - 1, coord.y))
						coord = cBoard.slot[coord.x - 1][coord.y].coord;
					else
						break;
				}
				// neu la quan dich thi co the an
				else if(cBoard.slot(coord).cPiece.color != cPiece.color){
					cPiece.ways++;
					cBoard.cTable.setBit(coord, 1, cPiece.color);
					break;
				}
				// neu la quan minh thi thoi
				else
					break;
			}
		}
	}

	private static void refreshKnight(CPiece cPiece, CBoard cBoard) {
		Coord coord = null;
		
		
		if(CProcess.isOnCBoard(cPiece.pos.x - 2, cPiece.pos.y - 1)){
			coord = cBoard.slot[cPiece.pos.x - 2][cPiece.pos.y - 1].coord;
			if(cBoard.slot(coord).cPiece == null){
				cPiece.ways++;
				cBoard.cTable.setBit(coord, 1, cPiece.color);
			}
			else if(cBoard.slot(coord).cPiece.color != cPiece.color){
				cPiece.ways++;
				cBoard.cTable.setBit(coord, 1, cPiece.color);
			}
		}
		
		if(CProcess.isOnCBoard(cPiece.pos.x - 2, cPiece.pos.y + 1)){
			coord = cBoard.slot[cPiece.pos.x - 2][cPiece.pos.y + 1].coord;
			if(cBoard.slot(coord).cPiece == null){
				cPiece.ways++;
				cBoard.cTable.setBit(coord, 1, cPiece.color);
			}
			else if(cBoard.slot(coord).cPiece.color != cPiece.color){
				cPiece.ways++;
				cBoard.cTable.setBit(coord, 1, cPiece.color);
			}
		}
		
		
		if(CProcess.isOnCBoard(cPiece.pos.x + 2, cPiece.pos.y - 1)){
			coord = cBoard.slot[cPiece.pos.x + 2][cPiece.pos.y - 1].coord;
			
			if(cBoard.slot(coord).cPiece == null){
				cPiece.ways++;
				cBoard.cTable.setBit(coord, 1, cPiece.color);
			}
			else if(cBoard.slot(coord).cPiece.color != cPiece.color){
				cPiece.ways++;
				cBoard.cTable.setBit(coord, 1, cPiece.color);
			}
		}
		
		
		if(CProcess.isOnCBoard(cPiece.pos.x + 2, cPiece.pos.y + 1)){
			coord = cBoard.slot[cPiece.pos.x + 2][cPiece.pos.y + 1].coord;
			if(cBoard.slot(coord).cPiece == null){
				cPiece.ways++;
				cBoard.cTable.setBit(coord, 1, cPiece.color);
			}
			else if(cBoard.slot(coord).cPiece.color != cPiece.color){
				cPiece.ways++;
				cBoard.cTable.setBit(coord, 1, cPiece.color);
			}
		}
		
		
		if(CProcess.isOnCBoard(cPiece.pos.x - 1, cPiece.pos.y - 2)){
			coord = cBoard.slot[cPiece.pos.x - 1][cPiece.pos.y - 2].coord;
			if(cBoard.slot(coord).cPiece == null){
				cPiece.ways++;
				cBoard.cTable.setBit(coord, 1, cPiece.color);
			}
			else if(cBoard.slot(coord).cPiece.color != cPiece.color){
				cPiece.ways++;
				cBoard.cTable.setBit(coord, 1, cPiece.color);
			}
		}
		
		
		if(CProcess.isOnCBoard(cPiece.pos.x - 1, cPiece.pos.y + 2)){
			coord = cBoard.slot[cPiece.pos.x - 1][cPiece.pos.y + 2].coord;
			if(cBoard.slot(coord).cPiece == null){
				cPiece.ways++;
				cBoard.cTable.setBit(coord, 1, cPiece.color);
			}
			else if(cBoard.slot(coord).cPiece.color != cPiece.color){
				cPiece.ways++;
				cBoard.cTable.setBit(coord, 1, cPiece.color);
			}
		}
		
		
		if(CProcess.isOnCBoard(cPiece.pos.x + 1, cPiece.pos.y - 2)){
			coord = cBoard.slot[cPiece.pos.x + 1][cPiece.pos.y - 2].coord;
			if(cBoard.slot(coord).cPiece == null){
				cPiece.ways++;
				cBoard.cTable.setBit(coord, 1, cPiece.color);
			}
			else if(cBoard.slot(coord).cPiece.color != cPiece.color){
				cPiece.ways++;
				cBoard.cTable.setBit(coord, 1, cPiece.color);
			}
		}
		
		
		if(CProcess.isOnCBoard(cPiece.pos.x + 1, cPiece.pos.y + 2)){
			coord = cBoard.slot[cPiece.pos.x + 1][cPiece.pos.y + 2].coord;
			if(cBoard.slot(coord).cPiece == null){
				cPiece.ways++;
				cBoard.cTable.setBit(coord, 1, cPiece.color);
			}
			else if(cBoard.slot(coord).cPiece.color != cPiece.color){
				cPiece.ways++;
				cBoard.cTable.setBit(coord, 1, cPiece.color);
			}
		}
	}

	private static void refreshBishop(CPiece cPiece, CBoard cBoard) {
		Coord coord;
		
		if(CProcess.isOnCBoard(cPiece.pos.x + 1, cPiece.pos.y + 1)){
			coord = cBoard.slot[cPiece.pos.x + 1][cPiece.pos.y + 1].coord;
			
			while(CProcess.isOnCBoard(coord)){
				// neu la vung trong thi them vung do vao va nhay len vung ke tiep 
				if(cBoard.slot(coord).cPiece == null){
					cPiece.ways++;
					cBoard.cTable.setBit(coord, 1, cPiece.color);
					
					if(CProcess.isOnCBoard(coord.x + 1, coord.y + 1))
						coord = cBoard.slot[coord.x + 1][coord.y + 1].coord;
					else
						break;
				}
				// neu la quan dich thi co the an
				else if(cBoard.slot(coord).cPiece.color != cPiece.color){
					cPiece.ways++;
					cBoard.cTable.setBit(coord, 1, cPiece.color);
					break;
				}
				// neu la quan minh thi thoi
				else
					break;
			}
		}
		
		if(CProcess.isOnCBoard(cPiece.pos.x - 1, cPiece.pos.y - 1)){
			coord = cBoard.slot[cPiece.pos.x - 1][cPiece.pos.y - 1].coord;
			
			while(CProcess.isOnCBoard(coord)){
				// neu la vung trong thi them vung do vao va nhay len vung ke tiep 
				if(cBoard.slot(coord).cPiece == null){
					cPiece.ways++;
					cBoard.cTable.setBit(coord, 1, cPiece.color);
					
					if(CProcess.isOnCBoard(coord.x - 1, coord.y - 1))
						coord = cBoard.slot[coord.x - 1][coord.y - 1].coord;
					else
						break;
				}
				// neu la quan dich thi co the an
				else if(cBoard.slot(coord).cPiece.color != cPiece.color){
					cPiece.ways++;
					cBoard.cTable.setBit(coord, 1, cPiece.color);
					break;
				}
				// neu la quan minh thi thoi
				else
					break;
			}
		}
		
		if(CProcess.isOnCBoard(cPiece.pos.x + 1, cPiece.pos.y - 1)){
			coord = cBoard.slot[cPiece.pos.x + 1][cPiece.pos.y - 1].coord;
			
			while(CProcess.isOnCBoard(coord)){
				// neu la vung trong thi them vung do vao va nhay len vung ke tiep 
				if(cBoard.slot(coord).cPiece == null){
					cPiece.ways++;
					cBoard.cTable.setBit(coord, 1, cPiece.color);
					
					if(CProcess.isOnCBoard(coord.x + 1, coord.y - 1))
						coord = cBoard.slot[coord.x + 1][coord.y - 1].coord;
					else
						break;
				}
				// neu la quan dich thi co the an
				else if(cBoard.slot(coord).cPiece.color != cPiece.color){
					cPiece.ways++;
					cBoard.cTable.setBit(coord, 1, cPiece.color);
					break;
				}
				// neu la quan minh thi thoi
				else
					break;
			}
		}
		
		if(CProcess.isOnCBoard(cPiece.pos.x - 1, cPiece.pos.y + 1)){
			coord = cBoard.slot[cPiece.pos.x - 1][cPiece.pos.y + 1].coord;
			
			while(CProcess.isOnCBoard(coord)){
				// neu la vung trong thi them vung do vao va nhay len vung ke tiep 
				if(cBoard.slot(coord).cPiece == null){
					cPiece.ways++;
					cBoard.cTable.setBit(coord, 1, cPiece.color);
					
					if(CProcess.isOnCBoard(coord.x - 1, coord.y + 1))
						coord = cBoard.slot[coord.x - 1][coord.y + 1].coord;
					else
						break;
				}
				// neu la quan dich thi co the an
				else if(cBoard.slot(coord).cPiece.color != cPiece.color){
					cPiece.ways++;
					cBoard.cTable.setBit(coord, 1, cPiece.color);
					break;
				}
				// neu la quan minh thi thoi
				else
					break;
			}
		}
	}

	private static void refreshQueen(CPiece cPiece, CBoard cBoard) {
		Coord coord;
		
		if(CProcess.isOnCBoard(cPiece.pos.x, cPiece.pos.y + 1)){
			coord = cBoard.slot[cPiece.pos.x][cPiece.pos.y + 1].coord;
			
			while(CProcess.isOnCBoard(coord)){
				// neu la vung trong thi them vung do vao va nhay len vung ke tiep 
				if(cBoard.slot(coord).cPiece == null){
					cPiece.ways++;
					cBoard.cTable.setBit(coord, 1, cPiece.color);
					
					if(CProcess.isOnCBoard(coord.x, coord.y + 1))
						coord = cBoard.slot[coord.x][coord.y + 1].coord;
					else
						break;
				}
				// neu la quan dich thi co the an
				else if(cBoard.slot(coord).cPiece.color != cPiece.color){
					cPiece.ways++;
					cBoard.cTable.setBit(coord, 1, cPiece.color);
					break;
				}
				// neu la quan minh thi thoi
				else
					break;
			}
		}
		
		if(CProcess.isOnCBoard(cPiece.pos.x, cPiece.pos.y - 1)){
			coord = cBoard.slot[cPiece.pos.x][cPiece.pos.y - 1].coord;
			
			while(CProcess.isOnCBoard(coord)){
				// neu la vung trong thi them vung do vao va nhay len vung ke tiep 
				if(cBoard.slot(coord).cPiece == null){
					cPiece.ways++;
					cBoard.cTable.setBit(coord, 1, cPiece.color);

					if(CProcess.isOnCBoard(coord.x, coord.y - 1))
						coord = cBoard.slot[coord.x][coord.y - 1].coord;
					else
						break;
				}
				// neu la quan dich thi co the an
				else if(cBoard.slot(coord).cPiece.color != cPiece.color){
					cPiece.ways++;
					cBoard.cTable.setBit(coord, 1, cPiece.color);
					break;
				}
				// neu la quan minh thi thoi
				else
					break;
			}
		}
		
		if(CProcess.isOnCBoard(cPiece.pos.x + 1, cPiece.pos.y)){
			coord = cBoard.slot[cPiece.pos.x + 1][cPiece.pos.y].coord;
			
			while(CProcess.isOnCBoard(coord)){
				// neu la vung trong thi them vung do vao va nhay len vung ke tiep 
				if(cBoard.slot(coord).cPiece == null){
					cPiece.ways++;
					cBoard.cTable.setBit(coord, 1, cPiece.color);
					
					if(CProcess.isOnCBoard(coord.x + 1, coord.y))
						coord = cBoard.slot[coord.x + 1][coord.y].coord;
					else
						break;
				}
				// neu la quan dich thi co the an
				else if(cBoard.slot(coord).cPiece.color != cPiece.color){
					cPiece.ways++;
					cBoard.cTable.setBit(coord, 1, cPiece.color);
					break;
				}
				// neu la quan minh thi thoi
				else
					break;
			}
		}
		
		if(CProcess.isOnCBoard(cPiece.pos.x - 1, cPiece.pos.y)){
			coord = cBoard.slot[cPiece.pos.x - 1][cPiece.pos.y].coord;
			
			while(CProcess.isOnCBoard(coord)){
				// neu la vung trong thi them vung do vao va nhay len vung ke tiep 
				if(cBoard.slot(coord).cPiece == null){
					cPiece.ways++;
					cBoard.cTable.setBit(coord, 1, cPiece.color);
					
					if(CProcess.isOnCBoard(coord.x - 1, coord.y))
						coord = cBoard.slot[coord.x - 1][coord.y].coord;
					else
						break;
				}
				// neu la quan dich thi co the an
				else if(cBoard.slot(coord).cPiece.color != cPiece.color){
					cPiece.ways++;
					cBoard.cTable.setBit(coord, 1, cPiece.color);
					break;
				}
				// neu la quan minh thi thoi
				else
					break;
			}
		}
		
		if(CProcess.isOnCBoard(cPiece.pos.x + 1, cPiece.pos.y + 1)){
			coord = cBoard.slot[cPiece.pos.x + 1][cPiece.pos.y + 1].coord;
			
			while(CProcess.isOnCBoard(coord)){
				// neu la vung trong thi them vung do vao va nhay len vung ke tiep 
				if(cBoard.slot(coord).cPiece == null){
					cPiece.ways++;
					cBoard.cTable.setBit(coord, 1, cPiece.color);
					
					if(CProcess.isOnCBoard(coord.x + 1, coord.y + 1))
						coord = cBoard.slot[coord.x + 1][coord.y + 1].coord;
					else
						break;
				}
				// neu la quan dich thi co the an
				else if(cBoard.slot(coord).cPiece.color != cPiece.color){
					cPiece.ways++;
					cBoard.cTable.setBit(coord, 1, cPiece.color);
					break;
				}
				// neu la quan minh thi thoi
				else
					break;
			}
		}
		
		if(CProcess.isOnCBoard(cPiece.pos.x - 1, cPiece.pos.y - 1)){
			coord = cBoard.slot[cPiece.pos.x - 1][cPiece.pos.y - 1].coord;
			
			while(CProcess.isOnCBoard(coord)){
				// neu la vung trong thi them vung do vao va nhay len vung ke tiep 
				if(cBoard.slot(coord).cPiece == null){
					cPiece.ways++;
					cBoard.cTable.setBit(coord, 1, cPiece.color);
					
					if(CProcess.isOnCBoard(coord.x - 1, coord.y - 1))
						coord = cBoard.slot[coord.x - 1][coord.y - 1].coord;
					else
						break;
				}
				// neu la quan dich thi co the an
				else if(cBoard.slot(coord).cPiece.color != cPiece.color){
					cPiece.ways++;
					cBoard.cTable.setBit(coord, 1, cPiece.color);
					break;
				}
				// neu la quan minh thi thoi
				else
					break;
			}
		}
		
		if(CProcess.isOnCBoard(cPiece.pos.x + 1, cPiece.pos.y - 1)){
			coord = cBoard.slot[cPiece.pos.x + 1][cPiece.pos.y - 1].coord;
			
			while(CProcess.isOnCBoard(coord)){
				// neu la vung trong thi them vung do vao va nhay len vung ke tiep 
				if(cBoard.slot(coord).cPiece == null){
					cPiece.ways++;
					cBoard.cTable.setBit(coord, 1, cPiece.color);
					
					if(CProcess.isOnCBoard(coord.x + 1, coord.y - 1))
						coord = cBoard.slot[coord.x + 1][coord.y - 1].coord;
					else
						break;
				}
				// neu la quan dich thi co the an
				else if(cBoard.slot(coord).cPiece.color != cPiece.color){
					cPiece.ways++;
					cBoard.cTable.setBit(coord, 1, cPiece.color);
					break;
				}
				// neu la quan minh thi thoi
				else
					break;
			}
		}
		
		if(CProcess.isOnCBoard(cPiece.pos.x - 1, cPiece.pos.y + 1)){
			coord = cBoard.slot[cPiece.pos.x - 1][cPiece.pos.y + 1].coord;
			
			while(CProcess.isOnCBoard(coord)){
				// neu la vung trong thi them vung do vao va nhay len vung ke tiep 
				if(cBoard.slot(coord).cPiece == null){
					cPiece.ways++;
					cBoard.cTable.setBit(coord, 1, cPiece.color);
					
					if(CProcess.isOnCBoard(coord.x - 1, coord.y + 1))
						coord = cBoard.slot[coord.x - 1][coord.y + 1].coord;
					else
						break;
				}
				// neu la quan dich thi co the an
				else if(cBoard.slot(coord).cPiece.color != cPiece.color){
					cPiece.ways++;
					cBoard.cTable.setBit(coord, 1, cPiece.color);
					break;
				}
				// neu la quan minh thi thoi
				else
					break;
			}
		}
	}

	private static void refreshKing(CPiece cPiece, CBoard cBoard) {
		Coord coord = null;
		
		
		if(CProcess.isOnCBoard(cPiece.pos.x - 1, cPiece.pos.y - 1)){
			coord = cBoard.slot[cPiece.pos.x - 1][cPiece.pos.y - 1].coord;
			
			if(cBoard.slot(coord).cPiece == null){
				cPiece.ways++;
				cBoard.cTable.setBit(coord, 1, cPiece.color);
			}
			else if(cBoard.slot(coord).cPiece.color != cPiece.color){
				cPiece.ways++;
				cBoard.cTable.setBit(coord, 1, cPiece.color);
			}
		}
		
		if(CProcess.isOnCBoard(cPiece.pos.x - 1, cPiece.pos.y + 1)){
			coord = cBoard.slot[cPiece.pos.x - 1][cPiece.pos.y + 1].coord;
			
			if(cBoard.slot(coord).cPiece == null){
				cPiece.ways++;
				cBoard.cTable.setBit(coord, 1, cPiece.color);
			}
			else if(cBoard.slot(coord).cPiece.color != cPiece.color){
				cPiece.ways++;
				cBoard.cTable.setBit(coord, 1, cPiece.color);
			}
		}
		
		if(CProcess.isOnCBoard(cPiece.pos.x + 1, cPiece.pos.y - 1)){
			coord = cBoard.slot[cPiece.pos.x + 1][cPiece.pos.y - 1].coord;
			
			if(cBoard.slot(coord).cPiece == null){
				cPiece.ways++;
				cBoard.cTable.setBit(coord, 1, cPiece.color);
			}
			else if(cBoard.slot(coord).cPiece.color != cPiece.color){
				cPiece.ways++;
				cBoard.cTable.setBit(coord, 1, cPiece.color);
			}
		}
		
		if(CProcess.isOnCBoard(cPiece.pos.x + 1, cPiece.pos.y + 1)){
			coord = cBoard.slot[cPiece.pos.x + 1][cPiece.pos.y + 1].coord;
			
			if(cBoard.slot(coord).cPiece == null){
				cPiece.ways++;
				cBoard.cTable.setBit(coord, 1, cPiece.color);
			}
			else if(cBoard.slot(coord).cPiece.color != cPiece.color){
				cPiece.ways++;
				cBoard.cTable.setBit(coord, 1, cPiece.color);
			}
		}
		
		if(CProcess.isOnCBoard(cPiece.pos.x, cPiece.pos.y - 1)){
			coord = cBoard.slot[cPiece.pos.x][cPiece.pos.y - 1].coord;
			
			if(cBoard.slot(coord).cPiece == null){
				cPiece.ways++;
				cBoard.cTable.setBit(coord, 1, cPiece.color);
			}
			else if(cBoard.slot(coord).cPiece.color != cPiece.color){
				cPiece.ways++;
				cBoard.cTable.setBit(coord, 1, cPiece.color);
			}
		}
		
		if(CProcess.isOnCBoard(cPiece.pos.x, cPiece.pos.y + 1)){
			coord = cBoard.slot[cPiece.pos.x][cPiece.pos.y + 1].coord;
			
			if(cBoard.slot(coord).cPiece == null){
				cPiece.ways++;
				cBoard.cTable.setBit(coord, 1, cPiece.color);
			}
			else if(cBoard.slot(coord).cPiece.color != cPiece.color){
				cPiece.ways++;
				cBoard.cTable.setBit(coord, 1, cPiece.color);
			}
		}
		
		if(CProcess.isOnCBoard(cPiece.pos.x - 1, cPiece.pos.y)){
			coord = cBoard.slot[cPiece.pos.x - 1][cPiece.pos.y].coord;
			
			if(cBoard.slot(coord).cPiece == null){
				cPiece.ways++;
				cBoard.cTable.setBit(coord, 1, cPiece.color);
			}
			else if(cBoard.slot(coord).cPiece.color != cPiece.color){
				cPiece.ways++;
				cBoard.cTable.setBit(coord, 1, cPiece.color);
			}
		}
		
		if(CProcess.isOnCBoard(cPiece.pos.x + 1, cPiece.pos.y)){
			coord = cBoard.slot[cPiece.pos.x + 1][cPiece.pos.y].coord;
			
			if(cBoard.slot(coord).cPiece == null){
				cPiece.ways++;
				cBoard.cTable.setBit(coord, 1, cPiece.color);
			}
			else if(cBoard.slot(coord).cPiece.color != cPiece.color){
				cPiece.ways++;
				cBoard.cTable.setBit(coord, 1, cPiece.color);
			}
		}
	}
	
	public static boolean moveableTo(CPiece cPiece, Coord to, CBoard cBoard){
		switch(cPiece.type){
		case Const.PAWN:
			return moveablePawn(cPiece, to, cBoard);
		case Const.ROOK:
			return moveableRook(cPiece, to, cBoard);
		case Const.KNIGHT:
			return moveableKnight(cPiece, to, cBoard);
		case Const.BISHOP:
			return moveableBishop(cPiece, to, cBoard);
		case Const.QUEEN:
			return moveableQueen(cPiece, to, cBoard);
		case Const.KING:
			return moveableKing(cPiece, to, cBoard);
			
		}
		return true;
	}
	
	private static boolean moveableKing(CPiece cPiece, Coord to, CBoard cBoard) {
		if(Math.abs(cPiece.pos.x - to.x) > 1 || Math.abs(cPiece.pos.y - to.y) > 1)
			return false;
		// neu co dich thi duoc di chuyen, neu la quan ta thi khong di chuyen duoc
		CPiece temp = cBoard.slot(to).cPiece;
		if(temp != null)
			if(temp.color == cPiece.color)
				return false;
			
		return true;
	}


	private static boolean moveableBishop(CPiece cPiece, Coord to, CBoard cBoard) {
		if(CProcess.isOnDiagonal(cPiece.pos, to)){
			// Kiem tra co di chuyen duoc den o ngay truoc o dich khong
			// neu co quan trong vung do thi ngung
			if(CBProcess.hasCPieceOn(cBoard, cPiece.pos, to))
				return false;
			// Truong hop co quan trong slot toi
			if(cBoard.slot(to).cPiece != null)
				// truong hop la quan minh thi khong di chuyen duoc
				if(cBoard.slot(to).cPiece.color == cPiece.color)
					return false;
			
			return true;
		}
			
		return false;
	}


	private static boolean moveableQueen(CPiece cPiece, Coord to, CBoard cBoard) {
		if(CProcess.isOnDiagonal(cPiece.pos, to) || CProcess.isOnLine(cPiece.pos, to)){
			// Kiem tra co di chuyen duoc den o ngay truoc o dich khong
			// neu co quan trong vung do thi ngung
			if(CBProcess.hasCPieceOn(cBoard, cPiece.pos, to))
				return false;
			// Truong hop co quan trong slot toi
			if(cBoard.slot(to).cPiece != null)
				// truong hop la quan minh thi khong di chuyen duoc
				if(cBoard.slot(to).cPiece.color == cPiece.color)
					return false;
			
			return true;
		}
			
		return false;
	}


	private static boolean moveableKnight(CPiece cPiece, Coord to, CBoard cBoard) {
		if((Math.abs(cPiece.pos.x - to.x) == 2 && Math.abs(cPiece.pos.y - to.y) == 1) ||
		   (Math.abs(cPiece.pos.x - to.x) == 1 && Math.abs(cPiece.pos.y - to.y) == 2)){
			CPiece temp = cBoard.slot(to).cPiece;
			// neu slot dich co quan
			if(temp != null)
				// truong hop la quan minh thi khong di chuyen duoc
				if(cBoard.slot(to).cPiece.color == cPiece.color)
					return false;
				
			return true;
		}
		
		return false;
	}


	private static boolean moveableRook(CPiece cPiece, Coord to, CBoard cBoard) {
		if(CProcess.isOnLine(cPiece.pos, to)){
			// Kiem tra co di chuyen duoc den o ngay truoc o dich khong
			// neu co quan trong vung do thi ngung
			if(CBProcess.hasCPieceOn(cBoard, cPiece.pos, to))
				return false;
			// Truong hop co quan trong slot toi
			if(cBoard.slot(to).cPiece != null)
				// truong hop la quan minh thi khong di chuyen duoc
				if(cBoard.slot(to).cPiece.color == cPiece.color)
					return false;
			
			return true;
		}
			
		return false;
	}


	private static boolean moveablePawn(CPiece cPiece, Coord to, CBoard cBoard){
		// truong hop di chuyen
		// nam tren cung mot duong thang doc truc y
		if(cPiece.pos.x == to.x){
			// neu co vat can tren duong thi dung
			if(CBProcess.hasCPieceOn(cBoard, cPiece.pos, to))
				return false;
			// Neu khong thi xet tiep
			if(cPiece.color == Const.BLACK){
				if(!cPiece.firstMoved && (to.y < cPiece.pos.y && to.y >= cPiece.pos.y - 2))
					return true;
				else if(cPiece.firstMoved && to.y == cPiece.pos.y - 1)
					return true;
			}
			else{
				if(!cPiece.firstMoved && (to.y > cPiece.pos.y && to.y <= cPiece.pos.y + 2))
					return true;
				else if(cPiece.firstMoved && to.y == cPiece.pos.y + 1)
					return true;
			}
		}
		// Truong hop an
		if(cBoard.slot(to).cPiece != null){
			// neu o o to la quan minh thi khong an duoc
			if(cBoard.slot(to).cPiece.color == cPiece.color)
				return false;
			
			// toa do delta(x) != 1 thi tra ve false
			if(Math.abs(cPiece.pos.x - to.x) != 1)
				return false;
			
			if(cPiece.color == Const.BLACK && to.y - cPiece.pos.y == -1)
				return true;
			
			if(cPiece.color == Const.WHITE && to.y - cPiece.pos.y == 1)
				return true;
		}
		
		return false;
	}
}
