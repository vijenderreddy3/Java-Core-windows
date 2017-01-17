package model;

import java.io.Serializable;

public class Coord implements Serializable{
	
	public int x ;
	public int y;
	
	public Coord(int x , int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Coord (Coord coord)
	{
		this.x = coord.x;
		this.y = coord.y;
	}
	
	public boolean equals (Coord coord)
	{
		if ((this.x == coord.x )&&(this.y == coord.y))
			return true;
		return false;
	}
	
	public void set (int x , int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void set (Coord coord)
	{
		this.x = coord.x ;
		this.y = coord.y;
	}
	
	public String toString(){
		return (char)(x + 97) + "" + (y + 1);
	}

}
