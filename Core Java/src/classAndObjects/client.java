package classAndObjects;

public class client {

	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		StudentDetails[] sd=new StudentDetails[4];
		for (int i=0;i<4;i++){
		sd[i]=new StudentDetails();
		}
		sd[0].name= "Babu";
		sd[0].college="Vasavi";
		sd[0].department ="ECE";
		sd[0].aggregate =75;
		sd[0].location ="Krishna";
		
		sd[1].name= "Raju";
		sd[1].college="CBIT";
		sd[1].department ="CSE";
		sd[1].aggregate =55;
		sd[1].location ="East Godavari";
		
		sd[2].name= "Hari";
		sd[2].college="Vasavi";
		sd[2].department ="EEE";
		sd[2].aggregate =85;
		sd[2].location ="Guntur";
		
		sd[3].name= "Vishnu";
		sd[3].college="CBIT";
		sd[3].department ="EEE";
		sd[3].aggregate =85;
		sd[3].location ="Guntur";
		int count =0;
	for (int i=0;i<4;i++){
		if (sd[i].college =="Vasavi" ){
			count++;
		System.out.println("Student "+sd[i].name+" is studing in "+sd[i].college +" college and belongs to "+sd[i].department +" department having arregate of "+sd[i].aggregate +" from "+sd[i].location );	
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
