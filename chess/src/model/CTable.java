package model;

import java.util.BitSet;

public class CTable {
	public BitSet tableW;
	public BitSet tableB;
	
	public CTable(){		
		tableW = new BitSet(64);
		tableB = new BitSet(64);
	}
	
	public CTable(CTable cTable) {
		tableW = new BitSet(64);
		tableB = new BitSet(64);
	}

	public int getBitB(int x, int y){
		return tableB.get(y * 8 + x) ? 1 : 0;
	}
	
	public int getBitB(Coord coord){
		return tableB.get(coord.y * 8 + coord.x) ? 1 : 0;
	}
	
	public int getBitW(int x, int y){
		return tableW.get(y * 8 + x) ? 1 : 0;
	}
	
	public int getBitW(Coord coord){
		return tableW.get(coord.y * 8 + coord.x) ? 1 : 0;
	}
	
	public void setBitB(int x, int y, int v){
		tableB.set(y * 8 + x, v == 1 ? true : false);
	}
	
	public void setBitB(Coord coord, int v){
		tableB.set(coord.y * 8 + coord.x, v == 1 ? true : false);
	}
	
	public void setBitW(int x, int y, int v){
		tableW.set(y * 8 + x, v == 1 ? true : false);
	}
	
	public void setBitW(Coord coord, int v){
		tableW.set(coord.y * 8 + coord.x, v == 1 ? true : false);
	}
	
	public void setBit(Coord coord, int v, int color){
		if(color == Const.WHITE){
			setBitW(coord, v);
		}
		else{
			setBitB(coord, v);
		}
	}
	
	public void setBit(int x, int y, int v, int color){
		if(color == Const.WHITE){
			setBitW(x, y, v);
		}
		else{
			setBitB(x, y, v);
		}
	}
	
	public String toString(){
		String s = "White :\n";
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++)
				s += getBitW(j, i);
			s += '\n';
		}
		
		s += "Black :\n";
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++)
				s += getBitB(j, i);
			s += '\n';
		}
		
		return s;
	}
	
	public void reset(){
		tableB.clear();
		tableW.clear();
	}
}
