package model;

public class CArray {
	public CPiece [] array;
	public int n;
	
	public CArray(){
		array = new CPiece[16];
		n = 0;
	}	
	public CArray(CArray array2) {
		this.n = array2.n;
		array = new CPiece[16];
		for(int i = 0; i < n; i++)
			array[i] = new CPiece(array2.array[i]);
	}
	public boolean add(CPiece cPiece){
		if(n >= 16)
			return false;
		
		array[n] = cPiece;
		n++;
		return true;
	}
	public boolean delete(CPiece cPiece){
		for(int i = 0; i < n; i++)
			if(array[i] == cPiece){
				array[i] = array[n - 1];
				array[n - 1] = null;
				n--;
				return true;
			}
		return false;
	}
	public CPiece getKing(){
		for(int i = 0; i < n; i++){
			if(array[i].type == Const.KING)
				return array[i];
		}
		System.out.println("MAT VUA");
		return null;
	}
}
