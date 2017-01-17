package task3;

public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Batsman viratKohli=new Batsman("Virat Kohli",26,100,20);
		Bowler ashwin=new Bowler("Ashwin",28,200,40);
		Bowler nehra=new Bowler("Nehra",35,400,100);
		WicketKeeper dhoni=new WicketKeeper("Dhoni",32,350,100);
	
		System.out.println("cricketer details:");
		System.out.println("Player "+viratKohli.getName()+" is a batsman of Age:"+viratKohli.getAge()+", played:"+viratKohli.getNoOfMatchesPlayed()+" matches and has got "+viratKohli.getNoOfCenturies()+" centuries");
		System.out.println("Player "+ashwin.getName()+" is a bowler of Age:"+ashwin.getAge()+", played:"+ashwin.getNoOfMatchesPlayed()+" matches and has got "+ashwin.getNoOfWickets()+" wickets");
		System.out.println("Player "+nehra.getName()+" is a bowler of Age:"+nehra.getAge()+", played:"+nehra.getNoOfMatchesPlayed()+" matches and has got "+nehra.getNoOfWickets()+" wickets");
		System.out.println("Player "+dhoni.getName()+" is a wicket keeper of Age:"+dhoni.getAge()+", played:"+dhoni.getNoOfMatchesPlayed()+" matches and has got "+dhoni.getNoOfStumps()+" stumps");
	}

}
