package model.process;
import model.Coord;


public class CProcess {
	
	/*public static Coord getCoordBefore(Coord from, Coord to){
		
		if (!isOnDiagonal(from, to) && !isOnLine(from, to)){
			return null;
		}
		
		Coord coord = new Coord(to);
		if(to.x > from.x)
			coord.x = to.x - 1;
		else if(to.x < from.x)
			coord.x = to.x + 1;
		
		if(to.y > from.y)
			coord.y = to.y - 1;
		else if(to.y < from.y)
			coord.y = to.y + 1;
		
		return coord;
	}*/

	public static boolean isOnCBoard(Coord coord){
		
		if ((coord.x >= 0) && (coord.x <= 7))
			if ((coord.y >= 0) && (coord.y <= 7))
				return true;
		
		return false;
	}
	
	public static boolean isOnCBoard(int x, int y){
		
		if ((x >= 0) && (x <= 7))
			if ((y >= 0) && (y <= 7))
				return true;
		
		return false;
	}

	
	public static boolean isOnDiagonal(Coord coord_1, Coord coord_2){
		if ((Math.abs(coord_1.x - coord_2.x)) == ( Math.abs(coord_1.y - coord_2.y)))
			return true;
		
		return false;
	}

	
	public static boolean isOnLine(Coord coord_1, Coord coord_2){
		if ((coord_1.x == coord_2.x)||(coord_1.y == coord_2.y))
			return true;
		
		return false;
	}

}
	

