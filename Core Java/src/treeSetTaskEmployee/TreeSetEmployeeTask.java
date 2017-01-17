package treeSetTaskEmployee;

import java.util.Iterator;
import java.util.TreeSet;

public class TreeSetEmployeeTask {

	public static void main(String[] args) {
		Employee e1=new Employee(1,"babu");
		Employee e2=new Employee(2,"vijender");
		Employee e3=new Employee(3,"chandu");
		Employee e4=new Employee(1,"john");
		Employee e5=new Employee(1,"paul");
		Employee e6=new Employee(1,"satwik");
		Employee e7=new Employee(1,"baby");
		TreeSet tsobj=new TreeSet();
		tsobj.add(e1);
		tsobj.add(e2);
		tsobj.add(e3);
		tsobj.add(e4);
		tsobj.add(e5);
		tsobj.add(e6);
		tsobj.add(e7);
		Iterator itr=tsobj.iterator();
		while(itr.hasNext()){
			System.out.println(itr.next());
		}
	}
}
