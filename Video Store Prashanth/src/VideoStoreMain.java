import java.util.Random;
import java.util.Scanner;


public class VideoStoreMain {

	
	static Videos vdeo=new Videos();
	static public Customers cus=new Customers();
	static public rent r=new rent();
	static public String data_type;
	/**
	 * @param args
	 */
	
static public int List_Type(String type)
	{
	if (type.equals("SLL"))
	{
		return 1;
	}
	else if (type.equals("DLL"))
	{
		return 2;
	}
	else if (type.equals("BST"))
	{
		return 3;
	}
	else if(type.equals("AVL"))
	{
		return 4;
	}
	else
	{
		return 0;
	}
}
	static public void AddVideoInStore(String type)
	{
		System.out.print("How many video you have to enter:");
		Scanner chose = new Scanner(System.in);
		int n = chose.nextInt();
		for(int a=0;a<n;a++)
		{
			System.out.print("Enter video ID:");
			Scanner id = new Scanner(System.in);
			String i = id.next();
			System.out.print("Enter video Title:");
			Scanner title = new Scanner(System.in);
			String ti = title.next();
			System.out.println("__________________________________________");
			switch(List_Type(type))
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
					break;
				}
			case 4:
				{
					vdeo.insertAVLtree(ti, i);
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
	static public void deletVideo(String type,String t)
	{
		
		switch(List_Type(type))
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
		case 4:
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
	static public void AddCustomer(String type)
	{
		System.out.print("How many Customer you have to enter:");
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
			switch(List_Type(type))
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
			case 4:
				{
					cus.insertAVLtree(ti, i);
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
	static public void deletCustomer(String type,String t)
	{
		switch(List_Type(type))
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
		case 4:
			{
				cus.delete_AVLtree(t);
				break;
			}
			
		default:
			{
				System.out.println("Invalid select");
				break;
			}
		}
	}
	static public void printAllCustomers(String type)
	{
		switch(List_Type(type))
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
		case 4:
			{
				cus.Display_AVL();
				break;
			}
		default:
			{
				System.out.println("Invalid select");
				break;
			}
		}
	}
	static public void printAllVideos(String type)
	{
		switch(List_Type(type))
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
		case 4:
			{
				vdeo.Display_AVL();
				break;
			}
		default:
			{
				System.out.println("Invalid select");
				break;
			}
		}
	}
	static public void printInStoreVideos(String type)
	{
		switch(List_Type(type))
		{
		case 1:
			{
				vdeo.display_instore_SLL();
				break;
			}
		case 2:
			{
				vdeo.display_instore_DLL();
				break;
			}
		case 3:
			{
				vdeo.Display_Binary();
				break;
			}
		case 4:
			{
				vdeo.Display_AVL();
				break;
			}
		default:
			{
				System.out.println("Invalid select");
				break;
			}
		}
	}
	static public void checkOut(String type,String title, String video_id,String cus_name,String cus_id )
	{
		switch(List_Type(type))
		{
		case 1:
			{
				vdeo.checkOut_SLL(title,cus_name);
				break;
			}
		case 2:
			{
				vdeo.checkOut_DLL(title,cus_name);
				break;
			}
		case 3:
			{
				vdeo.Display_Binary();
				break;
			}
		case 4:
			{
				vdeo.Display_AVL();
				break;
			}
		default:
			{
				System.out.println("Invalid select");
				break;
			}
		}
	}
	static public void checkIn(String type,String title, String cus_name)
	{
		switch(List_Type(type))
		{
		case 1:
			{
				vdeo.checkIn_SLL(title,cus_name);
				break;
			}
		case 2:
			{
				vdeo.checkIn_DLL(title,cus_name);
				break;
			}
		case 3:
			{
				vdeo.Display_Binary();
				break;
			}
		case 4:
			{
				vdeo.Display_AVL();
				break;
			}
		default:
			{
				System.out.println("Invalid select");
				break;
			}
		}
	}
	static public void printAllRentVideos(String type)
	{
		switch(List_Type(type))
		{
		case 1:
			{
				vdeo.display_rent_videos_SLL();
				break;
			}
		case 2:
			{
				vdeo.display_rent_videos_DLL();
				break;
			}
		case 3:
			{
				r.Display_Binary();
				break;
			}
			
		case 4:
			{
				r.Display_AVL();
				break;
			}
		default:
			{
				System.out.println("Invalid select");
				break;
			}
		}
	}
	static public void printVideosRentByACustomer(String type,String cust)
	{
		switch(List_Type(type))
		{
		case 1:
			{
				vdeo.display_rent_videos_bycust_SLL(cust);
				break;
			}
		case 2:
			{
				vdeo.display_rent_videos_bycust_DLL(cust);
				break;
			}
		case 3:
			{
				r.Display_Binary();
				break;
			}
			
		case 4:
			{
				r.Display_AVL();
				break;
			}
		default:
			{
				System.out.println("Invalid select");
				break;
			}
		}
	}
	
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		
		if (args.length == 0) {
				System.out.println("Please pass the data structure type as Argument. It must be  AVL, BST, DLL or SLL only");
				System.exit(0);
        }
        else {
            //if wrong data structure send, show message and end program
            if (!args[0].equals("AVL") && !args[0].equals("BST") && !args[0].equals("DLL") && !args[0].equals("SLL")) {
                System.out.println("Argument sent must be AVL, BST, DLL or SLL only");
                System.exit(0);
            }
        data_type=args[0];
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
				AddVideoInStore(data_type);
			}
			else if(chose ==2)
			{
				
				System.out.print("Enter Video Title/id :");
				Scanner title = new Scanner(System.in);
				String ti = title.nextLine();
				deletVideo(data_type,ti);
			}
			else if(chose ==3)
			{
				AddCustomer(data_type);
			}
			else if(chose ==4)
			{
				System.out.print("Enter Customer Name/id :");
				Scanner title = new Scanner(System.in);
				String ti = title.next();
				deletCustomer(data_type,ti);
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
				System.out.print("Enter Video Title:");
				Scanner title = new Scanner(System.in);
				String ti = title.next();
				System.out.print("Enter Video ID:");
				Scanner Id = new Scanner(System.in);
				String video_id = Id.next();
				System.out.print("Enter Customer Name :");
				Scanner cust = new Scanner(System.in);
				String ctitle = cust.next();
				System.out.print("Enter Customer ID :");
				Scanner custID = new Scanner(System.in);
				String cust_id = custID.next();
				if(vdeo.find(ti) && cus.find(ctitle)=="found");
				{
					//System.out.print("Enter current day :");
					//Scanner day = new Scanner(System.in);
					//int d = day.nextInt();
					checkOut(data_type,ti,video_id,ctitle,cust_id);
					r.checkout(data_type, ti, video_id, ctitle, cust_id);
				}
			}
			else if(chose ==7)
			{
				System.out.print("Enter Video Title/id :");
				Scanner sc = new Scanner(System.in);
				String ti = sc.next();
				System.out.print("Enter Customer Name/id :");
				//Scanner cust = new Scanner(System.in);
				String c = sc.next();
				checkIn(data_type,ti,c);
				
			}
			else if(chose ==8)
			{
				printAllCustomers(data_type);
			}
			else if(chose ==9)
			{
				printAllVideos(data_type);
			}
			else if(chose ==10)
			{
				printInStoreVideos(data_type);
			}
			else if(chose ==11)
			{
				printAllRentVideos(data_type);
			}
			else if(chose ==12)
			{
				System.out.print("Enter Customer name:");
				Scanner sc = new Scanner(System.in);
				String cust_name = sc.next();
				printVideosRentByACustomer(data_type,cust_name);
			}
			else if(chose ==13)
			{
				System.exit(0);
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
				switch(List_Type(data_type))
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
				case 4:
					{
						q.enqueAVLTree(rq);
						break;
					}
				default:
					{
						System.out.println("Invalid select");
						break;
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
