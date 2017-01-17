
public class AL {
	int id;
	String name;
	AL(int id,String name){
		this.id=id;
		this.name=name;
	}
	@Override
	public String toString() {
		return this.id+" "+this.name ;
	}
}
