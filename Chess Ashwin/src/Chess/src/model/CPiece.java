package model;

public class CPiece {
	public Coord pos;
	public int color;
	public int type;
	
	public int mCo;		// he so mot nuoc co the di chuyen (mobility couefficient)
	public int loss;	// gia tri bi mat khi bi chieu
	public int value;	// gia tri quan co
	
	public int ways;	// so nuoc di hien tai co the di tren ban co
	public boolean firstMoved;
	
	public int orderOfMoved = 0; // lan di chuyen gan nhat tren ban co, mac dinh la 0 neu chua di chuyen
	
	public CPiece(Coord coord , int type , int color)
	{
		this.pos = new Coord(coord);
		this.type = type;
		this.color = color;
		firstMoved = false;
		
		// Khoi tao cac gia tri ban dau
		if(color == Const.WHITE){
			
				switch(type){
			case Const.KING :
				value 	= Const.KING_VALUE;
				loss 	= - Const.KING_LOSS;
				ways	= 0;
				mCo 	= Const.KING_MCO;
				break;
				
			case Const.QUEEN :
				value 	= Const.QUEEN_VALUE;
				loss 	= - Const.QUEEN_LOSS;
				ways	= 0;
				mCo 	= Const.QUEEN_MCO;
				break;
				
			case Const.BISHOP : 
				
				value 	= Const.BISHOP_VALUE;
				loss 	= - Const.BISHOP_LOSS;
				ways	= 0;
				mCo 	= Const.BISHOP_MCO;
				break;
				
			case Const.KNIGHT : 
				
				value 	= Const.KNIGHT_VALUE;
				loss 	= - Const.KNIGHT_LOSS;
				ways	= 0;
				mCo 	= Const.KNIGHT_MCO;
				break;
				
				
			case Const.ROOK : 
				value 	= Const.ROOK_VALUE;
				loss 	= - Const.ROOK_LOSS;
				ways	= 0;
				mCo 	= Const.ROOK_MCO;
				break;
			case Const.PAWN : 
				
				value 	= Const.PAWN_VALUE;
				loss 	= - Const.PAWN_LOSS;
				ways	= 0;
				mCo 	= Const.PAWN_MCO;
				break;
			}
		}
		else {
			switch(type){
		case Const.KING :
			value 	= - Const.KING_VALUE;
			loss 	=  Const.KING_LOSS;
			ways	= 0;
			mCo 	= - Const.KING_MCO;
			break;
			
		case Const.QUEEN :
			value 	= - Const.QUEEN_VALUE;
			loss 	=  Const.QUEEN_LOSS;
			ways	= 0;
			mCo 	= - Const.QUEEN_MCO;
			break;
			
		case Const.BISHOP : 
			
			value 	= - Const.BISHOP_VALUE;
			loss 	=  Const.BISHOP_LOSS;
			ways	= 0;
			mCo 	= - Const.BISHOP_MCO;
			break;
			
		case Const.KNIGHT : 
			
			value 	= -Const.KNIGHT_VALUE;
			loss 	=  Const.KNIGHT_LOSS;
			ways	= 0;
			mCo 	= -Const.KNIGHT_MCO;
			break;
			
			
		case Const.ROOK : 
			value 	= -Const.ROOK_VALUE;
			loss 	=  Const.ROOK_LOSS;
			ways	= 0;
			mCo 	= -Const.ROOK_MCO;
			break;
		case Const.PAWN : 
			
			value 	= -Const.PAWN_VALUE;
			loss 	=  Const.PAWN_LOSS;
			ways	= 0;
			mCo 	= -Const.PAWN_MCO;
			break;
			}
		}
	}
	
	public CPiece(CPiece cPiece) {
		this.pos = new Coord(cPiece.pos);
		this.firstMoved = cPiece.firstMoved;
		this.loss = cPiece.loss;
		this.mCo = cPiece.mCo;
		this.type = cPiece.type;
		this.value = cPiece.value;
		this.ways = cPiece.ways;
		this.color = cPiece.color;
	}

	public void move(Coord coord){
		this.pos.set(coord);
		firstMoved = true;
	}
}

