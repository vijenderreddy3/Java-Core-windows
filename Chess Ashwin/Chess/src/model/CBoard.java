package model;

public class CBoard {
	public CArray [] cArray ;
	public CTable cTable;
	public Slot[][] slot;
	
	public CBoard(){
		cArray = new CArray[2];
		cArray[0] = new CArray();
		cArray[1] = new CArray();
		for(int i = 0; i < 8; i++){
			cArray[Const.WHITE].add(new CPiece(new Coord(i, 1), Const.PAWN, Const.WHITE));
			cArray[Const.BLACK].add(new CPiece(new Coord(i, 6), Const.PAWN, Const.BLACK));
		}
		
		cArray[Const.WHITE].add(new CPiece(new Coord(0, 0), Const.ROOK,   Const.WHITE));
		cArray[Const.WHITE].add(new CPiece(new Coord(7, 0), Const.ROOK,   Const.WHITE));
		cArray[Const.WHITE].add(new CPiece(new Coord(1, 0), Const.KNIGHT, Const.WHITE));
		cArray[Const.WHITE].add(new CPiece(new Coord(6, 0), Const.KNIGHT, Const.WHITE));
		cArray[Const.WHITE].add(new CPiece(new Coord(2, 0), Const.BISHOP, Const.WHITE));
		cArray[Const.WHITE].add(new CPiece(new Coord(5, 0), Const.BISHOP, Const.WHITE));
		cArray[Const.WHITE].add(new CPiece(new Coord(3, 0), Const.QUEEN,  Const.WHITE));
		cArray[Const.WHITE].add(new CPiece(new Coord(4, 0), Const.KING,   Const.WHITE));
		
		cArray[Const.BLACK].add(new CPiece(new Coord(0, 7), Const.ROOK,   Const.BLACK));
		cArray[Const.BLACK].add(new CPiece(new Coord(7, 7), Const.ROOK,   Const.BLACK));
		cArray[Const.BLACK].add(new CPiece(new Coord(1, 7), Const.KNIGHT, Const.BLACK));
		cArray[Const.BLACK].add(new CPiece(new Coord(6, 7), Const.KNIGHT, Const.BLACK));
		cArray[Const.BLACK].add(new CPiece(new Coord(2, 7), Const.BISHOP, Const.BLACK));
		cArray[Const.BLACK].add(new CPiece(new Coord(5, 7), Const.BISHOP, Const.BLACK));
		cArray[Const.BLACK].add(new CPiece(new Coord(3, 7), Const.QUEEN,  Const.BLACK));
		cArray[Const.BLACK].add(new CPiece(new Coord(4, 7), Const.KING,   Const.BLACK));
		
		cTable = new CTable();
		slot = new Slot[8][8];
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++)
				slot[i][j] = new Slot(new Coord(i, j));
		
		for(CPiece cp : cArray[0].array){
			slot[cp.pos.x][cp.pos.y].cPiece = cp;
		}
		
		for(CPiece cp : cArray[1].array){
			slot[cp.pos.x][cp.pos.y].cPiece = cp;
		}
	}
	
	public CBoard(CBoard cBoard){
		cArray = new CArray[2];
		cArray[0] = new CArray(cBoard.cArray[0]);
		cArray[1] = new CArray(cBoard.cArray[1]);
		cTable = new CTable(cBoard.cTable);
		
		slot = new Slot[8][8];
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++)
				slot[i][j] = new Slot(new Coord(i, j));
		
		for(int i = 0; i < cArray[0].n; i++){
			slot[cArray[0].array[i].pos.x][cArray[0].array[i].pos.y].cPiece = cArray[0].array[i];
		}
		
		for(int i = 0; i < cArray[1].n; i++){
			slot[cArray[1].array[i].pos.x][cArray[1].array[i].pos.y].cPiece = cArray[1].array[i];
		}
	}
	
	public Slot slot(Coord coord){
		if(coord == null)
			System.out.println("NULL");
		return slot[coord.x][coord.y];
	}
}
