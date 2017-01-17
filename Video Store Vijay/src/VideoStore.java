import java.util.Scanner;

public class VideoStore {
   static public String data_type;
	public static void main(String[] args) {
		if(args.length ==0){
			System.out.println("Pass the datastructure as an argument");
			System.exit(0);
		}
		else if(args.length==1){
			if (!args[0].equals("AVL") && !args[0].equals("BST") && !args[0].equals("DLL") && !args[0].equals("SLL")) {
                System.out.println("Argument sent must be AVL, BST, DLL or SLL only");
                System.exit(0);
            }
			data_type=args[0];
		}
		else if(args.length==4){
			
		}
		while(true)
		{
			System.out.println("Press 1 for add a video ");
			System.out.println("Press 2 for delete a video  ");
			System.out.println("Press 3 for add a customer ");
			System.out.println("Press 4 for delete acustomer ");
			System.out.println("Press 5 for check if a particular video is in store");
			System.out.println("Press 6 for check out a video ");
			System.out.println("Press 7 for check in a video ");
			System.out.println("Press 8 for print all customers ");
			System.out.println("Press 9 for print all videos ");
			System.out.println("Press 10 for print in store videos ");
			System.out.println("Press 11 for print all rent videos ");
			System.out.println("Press 12 print the videos rent by a customer ");
			System.out.println("Press 13 for Exit");
			
			Scanner option = new Scanner(System.in);
			int chose = option.nextInt();
			if(chose==1)
			{
				System.out.print("Enter Video Title/id :");
				Scanner title = new Scanner(System.in);
				String ti = title.nextLine();
				data_type.addVideo(ti);
			}
			else if(chose ==2)
			{
				
				System.out.print("Enter Video Title/id :");
				Scanner title = new Scanner(System.in);
				String ti = title.nextLine();
				
			}
			else if(chose ==3)
			{
				
			}
			else if(chose ==4)
			{
				System.out.print("Enter Customer Name/id :");
				Scanner title = new Scanner(System.in);
				String ti = title.next();
				
			}
			else if(chose ==5)
			{
				System.out.print("Enter Video Title/id :");
				Scanner title = new Scanner(System.in);
				String ti = title.next();
				if(vdeo.find(ti))
				{
					System.out.println("Video found :"+ti);
				}
				else
				{
					System.out.println("Video not found.");
				}
			}
			else if(chose ==6)
			{
				System.out.print("Enter Video Title/id :");
				Scanner title = new Scanner(System.in);
				String ti = title.next();
				System.out.print("Enter Customer Name/id :");
				Scanner cust = new Scanner(System.in);
				String c = cust.next();
				if(vdeo.find(ti) && cus.find(c)=="found");
				{
					//System.out.print("Enter current day :");
					//Scanner day = new Scanner(System.in);
					//int d = day.nextInt();
					r.checkin(ti, c);
				}
				
			}
			else if(chose ==7)
			{
				System.out.print("Enter Video Title/id :");
				Scanner title = new Scanner(System.in);
				String ti = title.next();
				System.out.print("Enter Customer Name/id :");
				Scanner cust = new Scanner(System.in);
				String c = cust.next();
				System.out.print("Enter current day :");
				Scanner day = new Scanner(System.in);
				int d = day.nextInt();
				r.checkin(ti, c);
				
			}
			else if(chose ==8)
			{
				
			}
			else if(chose ==9)
			{
				
			}
			else if(chose ==10)
			{
				
			}
			else if(chose ==11)
			{
				
			}
			else if(chose ==12)
			{
				
			}
			else if(chose ==13)
			{
				System.exit(0);
				break;
			}
		}
	}
}
