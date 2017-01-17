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
		
		// Tinh tong diem cua cac quan tren ban co
			CArray cArray;
			CPiece temp;
			
			cArray  = cBoard.cArray[Const.WHITE];
			for(int i = 0; i < cArray.n; i++){
				temp = cArray.array[i];
				value += temp.value;
				value += temp.ways * temp.mCo;
				// neu quan co nay nam trong pham vi quan den co the an duoc thi value cong them loss
				if(color == Const.WHITE)
					if(cBoard.cTable.getBitB(temp.pos) == 1)
						value += temp.loss;
			}
			
			// tinh tong diem quan den
			cArray  = cBoard.cArray[Const.BLACK];
			for(int i = 0; i < cArray.n; i++){
				temp = cArray.array[i];
				value += temp.value;
				value += temp.ways * temp.mCo;
				
				// neu quan co nay nam trong pham vi quan trang co the an duoc thi value cong them loss
				if(color == Const.BLACK)
					if(cBoard.cTable.getBitW(temp.pos) == 1)
						value += temp.loss;
			}
			return value;
	}
	
	public static int cal_vm(CBoard cBoard, int color) {
		int value = 0;
		
		// Tinh tong diem cua cac quan tren ban co
		CArray cArray;
		CPiece temp;
		
		cArray  = cBoard.cArray[Const.WHITE];
		for(int i = 0; i < cArray.n; i++){
			temp = cArray.array[i];
			value += temp.value;
			value += temp.ways * temp.mCo;
		}
		
		// tinh tong diem quan den
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
		
		// Tinh tong diem cua cac quan tren ban co
		CArray cArray;
		CPiece temp;
		
		cArray  = cBoard.cArray[Const.WHITE];
		for(int i = 0; i < cArray.n; i++){
			temp = cArray.array[i];
			value += temp.value;
		}
		
		// tinh tong diem quan den
		cArray  = cBoard.cArray[Const.BLACK];
		for(int i = 0; i < cArray.n; i++){
			temp = cArray.array[i];
			value += temp.value;
		}
		return value;
	}
}
