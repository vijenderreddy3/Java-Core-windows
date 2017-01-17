package bearandfsih;
//import java.util.*;
public class Bear {
private int count=1;
String name;
	public String type(){
		String type="BEAR";
		return type;
	}
	//public int temp;
	public String creat(){
		name="B"+count;
		count=count+1;
		return name;
	}
	public String addCreat(int bearCount){
		name="B"+bearCount;
		return name;
	}
	public String eat(String bear,String fish,int bCount){
		String newbear;
		newbear=addCreat(bCount+1);
		System.out.println(bear+" bear eat "+fish+" fish, and created new bear"+newbear);
		return newbear;
	}
	public String collide(int bCount){
		String newbear;
		newbear=addCreat(bCount+1);
		System.out.print(" and created new bear:"+newbear);
		System.out.println("");
		return newbear;
	}
}
