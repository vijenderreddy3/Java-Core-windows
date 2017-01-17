package classAndObjects;

public class ClientPrivate {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			StudentDetailsPrivate[] sdp=new StudentDetailsPrivate[4];
			for (int i=0;i<4;i++){
				sdp[i]=new StudentDetailsPrivate();
			}
			//calling one class main method from another class.
			//client.main(null);


			sdp[0].setName("Babu");
			sdp[0].setCollege("Vasavi");
			sdp[0].setDepartment("ECE");
			sdp[0].setAggregate(75);
			sdp[0].setLocation("Krishna");
			
			sdp[1].setName("Raju");
			sdp[1].setCollege("CBIT");
			sdp[1].setDepartment("CSE");
			sdp[1].setAggregate(55);
			sdp[1].setLocation("East Godavari");
			
			sdp[2].setName( "Hari");
			sdp[2].setCollege("Vasavi");
			sdp[2].setDepartment("EEE");
			sdp[2].setAggregate(85);
			sdp[2].setLocation("Guntur");
			
			sdp[3].setName( "Vishnu");
			sdp[3].setCollege("CBIT");
			sdp[3].setDepartment("EEE");
			sdp[3].setAggregate(85);
			sdp[3].setLocation("Guntur");
			int count =0;
		for (int i=0;i<4;i++){
			if (sdp[i].getCollege() =="Vasavi" ){
				count++;
			System.out.println("Student "+sdp[i].getName()+" is studing in "+sdp[i].getCollege() +" college and belongs to "+sdp[i].getDepartment() +" department having arregate of "+sdp[i].getAggregate() +" from "+sdp[i].getLocation() );	
			}
		}
		if (count==0){
			System.out.println("There are no students from Vasavi college");
		}
		else {
			System.out.println("There are "+count+" students from Vasavi college");
		}
		}
}


	


