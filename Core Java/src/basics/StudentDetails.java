package basics;

public class StudentDetails {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String[] name={"Babu","Vijay","Hari","Sri"};
		String[] course={"Java","Core Java","PEGA",".Net"};
		int[] fee={5000,9000,15000,10000};
		String[] address={"Hyderabad","Maryland","Virginia","North Carolia"};
		int[] phone_number={12345,67890,123456789,987654321};
		System.out.println("Student details:");
		for(int i=0;i<4;i++){
			details(name[i],course[i],fee[i],address[i],phone_number[i]);
		}
	}
	public static void details(String name,String course,int fee,String address,int phone_number)
	{
		System.out.println("The student "+name+" is attending "+course+" course and paying fee of INR "+fee+". He is living in "+address+" and his phone number is : "+phone_number);
	}

}
