package maven2;

public class Test_class {
	private int first;
	private int second;
	
	public Test_class(){
		System.out.println("Default Constroctor");
	}
	public Test_class(int second){
		this.second=second;
	}
	public int getSecond() {
		return second;
	}
	public void setSecond(int second) {
		this.second = second;
	}
	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}
	public void display(){
		System.out.println("Display method in test_class :First:" +getFirst());
		System.out.println("Second:"+getSecond());
	}
}
