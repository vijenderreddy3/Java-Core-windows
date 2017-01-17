package task3;

public class Bowler extends CricketerDetails{
	private int noOfWickets;
	public Bowler(String name,int age,long noOfMatchesPlayed,int noOfWickets){
		super(name,age,noOfMatchesPlayed);
		setNoOfWickets(noOfWickets);
	}
	public int getNoOfWickets() {
		return noOfWickets;
	}

	public void setNoOfWickets(int noOfWickets) {
		this.noOfWickets = noOfWickets;
	}
	
}
