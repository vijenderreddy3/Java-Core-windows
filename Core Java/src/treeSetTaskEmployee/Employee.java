package treeSetTaskEmployee;

public class Employee implements  Comparable{
	private int id;
	private String name;
	public Employee(){
		
	}
	public Employee(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	@Override
	public String toString() {
		return (this.id+", "+this.name); 
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.id;
	}
	@Override
	public boolean equals(Object obj) {
		Employee e1=this;
		Employee e2=(Employee)obj;
		if(e1.id==e2.id){
			if(e1.name.equals(e2.name)){
				return true;
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
	}
	@Override
	public int compareTo(Object o) {
		Employee o1=this;
		Employee o2=(Employee)o;
		if(o1.id<o2.id){
			return -1;
		}
		else if(o1.id>o2.id){
			return 1;
		}
		else{
			if(o1.name.length()<o2.name.length()){
				return -1;
			}
			else if(o1.name.length()>o2.name.length()){
				return 1;
			}
			else{
				return o1.name.compareTo(o2.name);
			}
			/*else {
				if((o1.name.compareTo(o2.name))<0){
					return -1;
				}
				else if((o1.name.compareTo(o2.name))>0){
					return 1;
				}
				else{
					return 0;
				}
			}*/
		}
	}
}
