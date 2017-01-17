package model;

public class Const {
	// cac che do choi
	public static final int HUMAN_VS_COM = 0;
	public static final int HUMAN_VS_HUMAN = 1;
	public static final int HUMAN_VS_HUMAN_LAN = 2;
	
	// dinh luong quan co
	public static final int KING 	= 0;
	public static final int QUEEN 	= 1;
	public static final int BISHOP 	= 2;
	public static final int KNIGHT 	= 3;
	public static final int ROOK 	= 4;
	public static final int PAWN 	= 5;
	
	// gia tri quan co
	public static final int KING_VALUE 		= 1000000;
	public static final int QUEEN_VALUE		= 1000;
	public static final int BISHOP_VALUE 	= 350;
	public static final int KNIGHT_VALUE	= 300;
	public static final int ROOK_VALUE		= 600;
	public static final int PAWN_VALUE		= 100;
	
	// gia tri quan co bi mat khi bi chieu
	public static final int KING_LOSS	= 50;
	public static final int QUEEN_LOSS	= 40;
	public static final int BISHOP_LOSS	= 20;
	public static final int KNIGHT_LOSS	= 20;
	public static final int ROOK_LOSS	= 25;
	public static final int PAWN_LOSS	= 5;
	
	// he so gia tri cua mot nuoc co the di chuyen
	public static final int KING_MCO	= 0;
	public static final int QUEEN_MCO	= 1;
	public static final int BISHOP_MCO	= 1;
	public static final int KNIGHT_MCO	= 2;
	public static final int ROOK_MCO	= 1;
	public static final int PAWN_MCO	= 1;
	
	// Mau sac cua quan co
	public static final int BLACK = 0;
	public static final int WHITE = 1;
	
	// cac ham dinh tri
	public static final int EVAL_V = 0;
	public static final int EVAL_VM = 1;
	public static final int EVAL_VML = 2;
	
	// trang thai cua ban co
	public static final int STATE_CONTINUE = 2;
	public static final int STATE_BLACK_WIN = 0;
	public static final int STATE_WHITE_WIN = 0;
	
	// so pixel cua mot o
	public static final int UNIT = 50;
	
	// Trang thai backup
	public static final Integer NORMAL = 0;
	public static final Integer REQUE = 1;
}
