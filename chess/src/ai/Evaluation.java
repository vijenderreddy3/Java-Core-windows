package ai;

import model.CArray;
import model.CBoard;
import model.CPiece;
import model.Const;

public abstract class Evaluation {
	public static int cal(CBoard cBoard, int color, int type)
	{
		switch(type){
		case Const.EVAL_VML : return cal_vml(cBoard, color);
		case Const.EVAL_VM  : return cal_vm(cBoard, color);
		case Const.EVAL_V   : return cal_v(cBoard, color);
		}
		
		return 0;
	}
	private static int cal_vml(CBoard cBoard, int color){
		int value = 0;
		
			CArray cArray;
			CPiece temp;
			cArray  = cBoard.cArray[Const.WHITE];
			for(int i = 0; i < cArray.n; i++){
				temp = cArray.array[i];
				value += temp.value;
				value += temp.ways * temp.mCo;
				if(color == Const.WHITE)
					if(cBoard.cTable.getBitB(temp.pos) == 1)
						value += temp.loss;
			}
			cArray  = cBoard.cArray[Const.BLACK];
			for(int i = 0; i < cArray.n; i++){
				temp = cArray.array[i];
				value += temp.value;
				value += temp.ways * temp.mCo;
				
				if(color == Const.BLACK)
					if(cBoard.cTable.getBitW(temp.pos) == 1)
						value += temp.loss;
			}
			return value;
	}
	public static int cal_vm(CBoard cBoard, int color) {
		int value = 0;
		
		CArray cArray;
		CPiece temp;
		
		cArray  = cBoard.cArray[Const.WHITE];
		for(int i = 0; i < cArray.n; i++){
			temp = cArray.array[i];
			value += temp.value;
			value += temp.ways * temp.mCo;
		}
		cArray  = cBoard.cArray[Const.BLACK];
		for(int i = 0; i < cArray.n; i++){
			temp = cArray.array[i];
			value += temp.value;
			value += temp.ways * temp.mCo;
		}
		return value;
	}
	public static int cal_v(CBoard cBoard, int color) {
		int value = 0;
		
		CArray cArray;
		CPiece temp;
		
		cArray  = cBoard.cArray[Const.WHITE];
		for(int i = 0; i < cArray.n; i++){
			temp = cArray.array[i];
			value += temp.value;
		}
		
		cArray  = cBoard.cArray[Const.BLACK];
		for(int i = 0; i < cArray.n; i++){
			temp = cArray.array[i];
			value += temp.value;
		}
		return value;
	}
}
