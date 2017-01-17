import java.util.Comparator;


public class MinComparator implements Comparator<Object>{

	@Override
	public int compare(Object o1, Object o2) {
		return (((Node)o1).freq - ((Node)o2).freq) > 0? 1 : -1;
	}
}
