import java.util.*;
public class Overloading {
	public static String username = "John";
	public static String password = "Doe";
	public static String uname;
	public static String upword;
	public static int count = 1;
	static Scanner sc = new Scanner(System.in);
	public static void main(String[] args) throws Exception
	{
		if(count==1)
		{
		System.out.println("Please enter a username:");
		uname=sc.nextLine();
		System.out.println("please enter a pasword:");
		upword=sc.nextLine();
		}
		if (username.equals(uname) && password.equals(upword))
		{
			verify();
		}
		else
		{
			verify(uname,upword);
		}
	}
	public static void verify(String user_name,String pass)throws Exception 
	{
		if(count<3){
		count=count+1;
		System.out.println("Invalid Entry.");	
		System.out.println("Please enter a VALID username:");
		uname = sc.nextLine();
		System.out.println("Please enter a VALID password:");
		upword = sc.nextLine();
		main(null);
		}
		else
		{
			System.out.println("You have made three attempts to login. Your account is locked.");
		}
	}
	public static void verify()
	{
		System.out.println("Valid Entry");
		System.out.println("Welcome.");
	}
}
