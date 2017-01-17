package task3;

public class CricketerDetails {

	private String name;
	private int age;
	private long noOfMatchesPlayed;
		public CricketerDetails(String name,int age,long noOfMatchesPlayed){
		setName(name);
		setAge(age);
		setNoOfMatchesPlayed(noOfMatchesPlayed);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public long getNoOfMatchesPlayed() {
		return noOfMatchesPlayed;
	}
	public void setNoOfMatchesPlayed(long noOfMatchesPlayed) {
		this.noOfMatchesPlayed = noOfMatchesPlayed;
	}
	
}
