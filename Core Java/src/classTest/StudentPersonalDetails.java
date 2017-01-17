package classTest;

import classAndObjects.StudentDetails;

public class StudentPersonalDetails {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			classAndObjects.StudentDetails[] student=new classAndObjects.StudentDetails[4];
			for (int i=0;i<4;i++){
				student[i]=new StudentDetails();
				}
				student[0].name= "Babu";
				student[0].college="Vasavi";
				student[0].department ="ECE";
				//student[0].aggregate =75;
				student[0].location ="Krishna";
				
				student[1].name= "Raju";
				student[1].college="CBIT";
				student[1].department ="CSE";
				//student[1].aggregate =55;
				student[1].location ="East Godavari";
				
				student[2].name= "Hari";
				student[2].college="Vasavi";
				student[2].department ="EEE";
				//student[2].aggregate =85;
				student[2].location ="Guntur";
				
				student[3].name= "Vishnu";
				student[3].college="CBIT";
				student[3].department ="EEE";
				//student[3].aggregate =85;
				student[3].location ="Guntur";
			for (int i=0;i<4;i++){
				System.out.println("Student "+student[i].name+" is studing in "+student[i].college +" college and belongs to "+student[i].department +" department and from "+student[i].location );	
				
			}
			
	}

}
