import java.util.HashMap;
import java.util.Map;

public class ArrayListtest {

	public static void main(String[] args) {
		AL a=new AL(2,"vijay");
		Map<Integer, String > two=new HashMap<Integer,String>();
		two.put(1,"vijay");
		two.put(1,"Sagar");
		System.out.println("Array list values"+two.get(1));
	}

}
