import java.util.Scanner;

public class User implements user_interface 
{
	String [] assgList = {"Assg#1c1", "Assg#2c1", "Assg#3c2", "Assg#4c2", "Assg#5c3", "Assg#6c3", "Assg#7c3", "Assg#8c4", "Assg#9c4"};
	String [] courList = {"c1f1", "c2f1", "c3f1", "c4f2", "c5f1"};
	String [] facuList = {"f1", "f2", "f3"};
	static int count = 0;
	static int countOne =0;
	public static void main(String[] args) 
	{
		//start by invoking the VERIFYUSERENTRY method..
		User x = new User();
		x.VerifyUserEntry();
	}
	public void VerifyUserEntry() 
	{
		//This method will be used to ask the user the following question:
		//"Are you a student(1) or a teacher(2)? Enter 1 or 2."
		Scanner scan = new Scanner(System.in);
		System.out.println("Are you a student(1) or a teacher(2)? Enter 1 or 2.");
		int role = scan.nextInt();
		//The user has four attempts to enter the right value (1 or 2)
		//Once the user chooses to take the role of a student (1) or faculty(2)
		//invoke either STUDENT_GREETING or FACULTY_GREETING from either student or faculty class
		if((role == 1 || role == 2) && count < 3)
		{
			if(role == 1)
			{
				Student stu = new Student();
				stu.student_greeting();
			}
			else if(role == 2)
			{
				Faculty fac = new Faculty();
				fac.faculty_greeting();
			}
		}
		else
		{
			System.out.println("Invalid Entry");
			count++;
			if(count > 2)
			{
				System.out.println("Good Bye");
				System.exit(0);
			}
			VerifyUserEntry();
		}
		
	}
	public int VerifyID(String [] username, String [] password, String x_u, String x_p)
	{
		//This method will accept the two arrays (list of username and password)
		//and user's two entries as a username and password
		//Users can make three attempts to turn in the right username/password
		//after which the application should exit.
		int place = -1;
		
		for(int i = 0; i < username.length ; i++ )
		{
			if(username[i].equals(x_u))
				place = i;	
				
		}
		if(place == -1)
		{
			System.out.println("Invalid username and/or password");
			countOne++;
			
			if(countOne > 2)
			{
				System.out.println("You have made threee unsuccesful attempts to log into students Portal.");
				System.out.println("Goodbye");
				System.exit(0);
			}
			Student stu = new Student();
			stu.student_greeting();
		}
		if(password[place].equals(x_p))
		{
				return 1;
		}
		else
		{
			System.out.println("Invalid username and/or password");
			countOne++;
			
			if(countOne > 2)
			{
				System.out.println("You have made threee unsuccesful attempts to log into students Portal.");
				System.out.println("Goodbye");
				System.exit(0);
			}
			Student stu = new Student();
			stu.student_greeting();
			return 0;
		}
	}	
	public void List(int x) 
	{	
		//This method will accept an int 
		//and print either the faculty or course list
		
	}
	public void print_course_asgt(String courseName)
	{
		//This method would take in a course name and:
		//1) check if it is a valid course name and if it is not, exit the application.
		//2) If it is a valid name, print out the assignments for a given course
	}
	public void print_faculty_course(String facultyName)
	{
		//This method would take in a faculty name and:
		//1) check if it is a valid faculty name and if it is not, exit the application.
		//2) If it is a valid name, print out the courses that are given by the faculty
		//member.
				
	}
}
