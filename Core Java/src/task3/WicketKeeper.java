package task3;

public class WicketKeeper extends CricketerDetails{
	private int noOfStumps;
	public WicketKeeper(String name,int age,long noOfMatchesPlayed,int noOfStumps){
		super(name,age,noOfMatchesPlayed);
		setNoOfStumps(noOfStumps);
	}
	public int getNoOfStumps() {
		return noOfStumps;
	}
	
	public void setNoOfStumps(int noOfStumps) {
		this.noOfStumps = noOfStumps;
	}
}
