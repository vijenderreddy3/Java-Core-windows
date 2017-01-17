package task3;

public class Batsman extends CricketerDetails {

	private int noOfCenturies;
	public Batsman(String name,int age,long noOfMatchesPlayed,int noOfCenturies){
		super(name,age,noOfMatchesPlayed);
		setNoOfCenturies(noOfCenturies);
	}
	public int getNoOfCenturies() {
		return noOfCenturies;
	}

	public void setNoOfCenturies(int noOfCenturies) {
		this.noOfCenturies = noOfCenturies;
	}

}
