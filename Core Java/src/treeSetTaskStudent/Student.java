package treeSetTaskStudent;

public class Student implements Comparable{
	public String name;
	public int marks;

	public Student() {

	}

	public Student(String name, int marks) {
		super();
		this.name = name;
		this.marks = marks;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMarks() {
		return marks;
	}

	public void setMarks(int marks) {
		this.marks = marks;
	}

	@Override
	public int hashCode() {
		return this.marks;
	}

	@Override
	public boolean equals(Object obj) {
		Student s1 = this;
		Student s2 = (Student)obj;
		if (s1.marks == s2.marks&&s1.name.equals(s2.name)) {
			return true;
		}
		else if(s1.marks !=s2.marks&&s1.name.equals(s2.name)){
			return true;
		}
		else{
			return false;	
		}
	}
	@Override
	public String toString() {
		return this.name+" "+this.marks;
	}
	@Override
	public int compareTo(Object o) {
		Student s1 = this;
		Student s2 = (Student)o;
		if (s1.marks <s2.marks){
			return 1;
		}
		else if(s1.marks>s2.marks){
			return -1;
		}
		else{
			return s1.name.compareTo(s2.name);
			/*if (s1.name.compareTo(s2.name)<0){
				return -1;
			}
			else if(s1.name.compareTo(s2.name)>0){
				return 1;
			}
			else{
				return 0;
			}*/
		}
	}
}
