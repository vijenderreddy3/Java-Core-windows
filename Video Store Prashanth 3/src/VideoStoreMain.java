import java.util.Random;
import java.util.Scanner;


public class VideoStoreMain {

	
	static Videos vdeo=new Videos();
	static public Customers cus=new Customers();
	static public rent r=new rent();
	/**
	 * @param args
	 */
	static public int List_Type()
	{
		System.out.println("Press 1 for Single Link List ");
		System.out.println("Press 2 for Double Link List ");
		System.out.println("Press 3 for Binary tree  ");
		Scanner op = new Scanner(System.in);
		int ch = op.nextInt();
		return ch;
	}
	static public void AddVideoInStore()
	{
		System.out.print("How to many video you have to enter:");
		Scanner chose = new Scanner(System.in);
		int n = chose.nextInt();
		for(int a=0;a<n;a++)
		{
			System.out.print("Enter video Title:");
			Scanner title = new Scanner(System.in);
			String ti = title.next();
			System.out.print("Enter vidro ID:");
			Scanner id = new Scanner(System.in);
			String i = id.next();
			System.out.println("__________________________________________");
			switch(List_Type())
			{
			case 1:
				{
					vdeo.insert(ti,i);
					break;
				}
			case 2:
				{
					vdeo.double_link_list_insert(ti, i);
					break;
				}
			case 3:
				{
					vdeo.insertbtree(ti,i);
				}
			default:
				{
					a--;
					System.out.println("Invalid select");
				}
			}
		}
	}
	static public void deletVideo(String t)
	{
		
		switch(List_Type())
		{
		case 1:
			{
				System.out.println("Video is :"+vdeo.delete(t));
				break;
			}
		case 2:
			{
				System.out.println("Video is :"+vdeo.double_link_list_Delete(t));
				break;
			}
		case 3:
			{
				System.out.println("Video is :"+vdeo.delete_btree(t));
				break;
			}
		default:
			{
				System.out.println("Invalid select");
				break;
			}
		}
		
	}
	static public void AddCustomer()
	{
		System.out.print("How to many Customer you have to enter:");
		Scanner chose = new Scanner(System.in);
		int n = chose.nextInt();
		for(int a=0;a<n;a++)
		{
			System.out.print("Enter Customer Name:");
			Scanner title = new Scanner(System.in);
			String ti = title.next();
			System.out.print("Enter Customer ID:");
			Scanner id = new Scanner(System.in);
			String i = id.next();
			System.out.println("__________________________________________");
			switch(List_Type())
			{
			case 1:
				{
					cus.insert(ti,i);
					break;
				}
			case 2:
				{
					cus.double_link_list_insert(ti, i);
					break;
				}
			case 3:
				{
					cus.insertbtree(ti, i);
					break;
				}
			default:
				{
					a--;
					System.out.println("Invalid select");
					break;
				}
			}
			
		}
	}
	static public void deletCustomer(String t)
	{
		switch(List_Type())
		{
		case 1:
			{
				System.out.println("Video is :"+cus.delete(t));
				break;
			}
		case 2:
			{
				System.out.println("Video is :"+cus.double_link_list_Delete(t));
				break;
			}
		case 3:
			{
				cus.delete_btree(t);
				break;
			}
			
		default:
			{
				System.out.println("Invalid select");
			}
		}
	}
	static public void printAllCustomers()
	{
		switch(List_Type())
		{
		case 1:
			{
				cus.display_SLL();
				break;
			}
		case 2:
			{
				cus.display_DLL();
				break;
			}
		case 3:
			{
				cus.Display_Binary();
				break;
			}
		default:
			{
				System.out.println("Invalid select");
			}
		}
	}
	static public void printAllVideos()
	{
		switch(List_Type())
		{
		case 1:
			{
				vdeo.display_SLL();
				break;
			}
		case 2:
			{
				vdeo.display_DLL();
				break;
			}
		case 3:
			{
				vdeo.Display_Binary();
				break;
			}
		default:
			{
				System.out.println("Invalid select");
			}
		}
	}
	static public void printInStoreVideos()
	{
		switch(List_Type())
		{
		case 1:
			{
				vdeo.display_SLL();
				break;
			}
		case 2:
			{
				vdeo.display_DLL();
				break;
			}
		case 3:
			{
				vdeo.Display_Binary();
				break;
			}
		default:
			{
				System.out.println("Invalid select");
			}
		}
	}
	static public void printAllRentVideos()
	{ 
		r.dispay_Video();
	}
	static public void printVideosRentByACustomer()
	{
		switch(List_Type())
		{
		case 1:
			{
				r.display_customer_SLL();
				break;
			}
		case 2:
			{
				r.display_customer_DLL();
				break;
			}
		case 3:
			{
				r.Display_Binary();
				break;
			}
		default:
			{
				System.out.println("Invalid select");
			}
		}
	}
	
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
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
				AddVideoInStore();
			}
			else if(chose ==2)
			{
				
				System.out.print("Enter Video Title/id :");
				Scanner title = new Scanner(System.in);
				String ti = title.nextLine();
				deletVideo(ti);
			}
			else if(chose ==3)
			{
				AddCustomer();
			}
			else if(chose ==4)
			{
				System.out.print("Enter Customer Name/id :");
				Scanner title = new Scanner(System.in);
				String ti = title.next();
				deletCustomer(ti);
			}
			else if(chose ==5)
			{
				System.out.print("Enter Video Title/id :");
				Scanner title = new Scanner(System.in);
				String ti = title.next();
				if(vdeo.find(ti));
				{
					System.out.println("found :"+ti);
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
					System.out.print("Enter current day :");
					Scanner day = new Scanner(System.in);
					int d = day.nextInt();
					r.checkin(ti, c, d);
				}
				
			}
			else if(chose ==7)
			{
				System.out.print("Enter Video Title/id :");
				Scanner title = new Scanner(System.in);
				String ti = title.next();
				System.out.print("Enter cutomer Name/id :");
				Scanner cust = new Scanner(System.in);
				String c = cust.next();
				System.out.print("Enter current day :");
				Scanner day = new Scanner(System.in);
				int d = day.nextInt();
				r.checkin(ti, c, d);
				
			}
			else if(chose ==8)
			{
				printAllCustomers();
			}
			else if(chose ==9)
			{
				printAllVideos();
			}
			else if(chose ==10)
			{
				printAllVideos();
			}
			else if(chose ==11)
			{
				printAllRentVideos();
			}
			else if(chose ==12)
			{
				printVideosRentByACustomer();
			}
			else if(chose ==13)
			{
				break;
			}
		}
		
		int c=0;
		int req=0;
		/*insert data in queue using randam function*/
		Queue q=new Queue();
		while(c<20)
		{
			if(req>300)
			{
				break;
			}
			else
				{
				
				System.out.print("Enter current day :");
				Scanner p = new Scanner(System.in);
				int rq = p.nextInt();
				req=req+rq;
				switch(List_Type())
				{
				case 1:
					{
						q.enqueSLL(rq);
						break;
					}
				case 2:
					{
						q.enqueDLL(rq);
						break;
					}
				case 3:
					{
						q.enqueBinaryTree(rq);
						break;
					}
				default:
					{
						System.out.println("Invalid select");
					}
				}
			}
			c++;
		}
		
		System.out.println("Single Link list :" +q.servicetime_SLL());
		System.out.println("Double Link list :" +q.servicetime_DLL());
		System.out.println("Binary search Tree list :" +q.servicetime_Btree());
		System.out.println("AVL Link list :" +q.servicetime_AVL());
		
	}

}
