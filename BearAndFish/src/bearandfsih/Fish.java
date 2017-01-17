package bearandfsih;

public class Fish {
	String name;
	private int count=1;
	public String type(){
		String type="FISH";
		return type;
	}
	public String creat(){
		name="F"+count;
		count=count+1;
		return name;
	}
	public String addCreat(int fishCount){
		name="F"+fishCount;
		fishCount=fishCount+1;
		//temp=count;
		return name;
	}
	public String eat(String fish1,String fish2,int fCount){
		
		String newfish;
		newfish=addCreat(fCount+1);
		System.out.println(fish1+" moved to "+fish2+" and created new fish "+newfish);
		return newfish;
	}
}
