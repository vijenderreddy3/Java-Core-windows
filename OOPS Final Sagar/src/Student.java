import java.util.Scanner;

public class Student extends User 
{
	public static void student_greeting()
	{
		//This method will be used to:
		//(1) accept the student's username and password
        //It should then invoke VERIFYID(in User) to verify the information.
		//If the user is validated:
		//(a) ask the user if he/she would like to see a list of available courses.
		//If the user answered no, exit the application.
		//If the user answered yes, invoke the LIST method(in user) and 
		//print out the Course list.
		//(3) ask the user if he/she wishes to see the assignment list for a given course. 
		//If the user answered no, exit the application.
		//If the user answered yes, invoke the PRINT_COURSE_ASGT method (in user)  
		//and use it to print out the assignment list for a given course.
		String [] username = {"JMary", "DJoe", "SDeath", "MSmith", "XMary"};
		String [] pswd = {"J111", "D111", "S111", "M111", "X111"};	
		
		Scanner cred = new Scanner(System.in);
		System.out.println("Please Enter your Username");
		String uName = cred.nextLine();
		cred = new Scanner(System.in);
		System.out.println("Please enter your password");
		String pwd = cred.nextLine();
		
		User x = new User();
		int isValid = x.VerifyID(username, pswd, uName, pwd);
		
		System.out.println("Welcome: "+uName);
		System.out.println("---------------------------------------------------");
		System.out.println("You are logged into the students portal");
		System.out.println("----------------------------------------------------");
		
		Scanner watch = new Scanner(System.in);
		System.out.println("Would you like to see a course list(Yes,No)?");
		String  courseWatch = watch.next();
		
		if(courseWatch.equals("yes") || courseWatch.equals("Yes"))
		{
			for(int  i = 0; i < x.courList.length; i++)
				System.out.println("-------------"+x.courList[i]);
		}
		else
		{
			System.out.println("Goodbye");
			System.exit(0);
		}
		watch = new Scanner(System.in);
		System.out.println("Type in the name of the course to view its assignment list.Type EXIT to quit application");
		String  assWatch = watch.next();
		
		System.out.println("Printing assignment List for the course: "+assWatch);
		System.out.println("----------------------------------------------------");
		String firstTwo = assWatch.substring(0, 2);
		for(int i = 0 ; i<x.assgList.length;i++)
		{
			String assiLastChar = x.assgList[i].substring(6, 8);
			if(assiLastChar.equals(firstTwo))
			System.out.println(x.assgList[i]);
		}
		System.out.println("----------------------------------------------------");
		System.out.println("Goodbye");
		System.out.println("----------------------------------------------------");
		
		
	}
}
