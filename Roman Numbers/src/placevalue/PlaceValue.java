package placevalue;

public class PlaceValue {

	public static void main(String[] args) {
		int x=6;
		int thou=(x/1000)*1000;
		int hundred=((x%1000)/100)*100;
		int ten=(((x%1000)%100)/10)*10;
		int one=(((x%1000)%100)%10);
		float round=Math.round(x/1000);
		double floor=Math.floor(x/1000);
		System.out.println("Thousand:"+thou+" hundred:"+hundred+"tens:"+ten+" ones:"+one+" Round:"+round+" floor:"+floor );
	}

}
