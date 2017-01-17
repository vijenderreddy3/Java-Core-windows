package treeSetTaskStudent;

import java.util.Iterator;
import java.util.TreeSet;

public class TreeSetStudentTask {

	public static void main(String[] args) {
		Student s1=new Student("babu",100);
		Student s2=new Student("vijender",75);
		Student s3=new Student("chandu",80);
		Student s4=new Student("john",70);
		Student s5=new Student("paul",65);
		Student s6=new Student("satwick",90);
		Student s7=new Student("baby",75);
		
		TreeSet<Student> tsobj=new TreeSet<Student>();
		tsobj.add(s1);
		tsobj.add(s2);
		tsobj.add(s3);
		tsobj.add(s4);
		tsobj.add(s5);
		tsobj.add(s6);
		tsobj.add(s7);
		
		Iterator<Student> itr=tsobj.iterator();
		while(itr.hasNext()){
			System.out.println(itr.next());
		
		}
		System.out.println("***************");
		System.out.println("Student with Highest marks:"+tsobj.first());
		System.out.println("Student with lowest marks:"+tsobj.last());
		System.out.println("Students who got less than 70 marks:"+tsobj.tailSet(s4));
		System.out.println("Students who got marks greater than 90 :"+tsobj.headSet(s6));
		System.out.println("Students who got marks greater than 90 :"+tsobj.headSet(s6));
		System.out.println("Students who got marks with in range 70 to 90 :"+tsobj.subSet(s6,s4));
		
		
		
	}

}
