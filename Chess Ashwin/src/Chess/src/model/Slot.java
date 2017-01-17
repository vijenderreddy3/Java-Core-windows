package model;

public class Slot {
	
	public Coord coord;
	public CPiece cPiece;
	
	public Slot(Coord coord){
		this.coord = new Coord(coord);
		cPiece = null;
	}

}
