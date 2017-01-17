public class Videos {
	//Single Link list
	private class Node
	{
		public Node next;
		public String title;
		public String id;
		public boolean rent=false;
		public String rent_by=null;
	}
	//DOubly Link list
	private class dNode
	{
		public dNode next;
		public dNode pre;
		public String title;
		public String id;
		public boolean rent=false;
		public String rent_by=null;
	}
	
	//Binary tree
	private class btreeNode
	{
		public btreeNode left;
		public btreeNode right;
		public String title;
		public String id;
		public boolean rent=false;
		public String rent_by=null;
	}
	//Node Link list obj declair
	public Node head,tail;
	public dNode dhead,dtail;
	public btreeNode root,avlroot;
	//Constructor
	public Videos()
	{
		head=null;
		tail=null;
		root=null;
	}
	// Overload Constructor
	public Videos(String t,String id)
	{
		insert(t, id);
	}
	//Single Link list
	//that function insert also insert value in all node list single double bainary 
	public void insert(String t,String id)
	{
		if(head==null)
		{
			head=new Node();
			head.title=t;
			head.id=id;
			tail=head;
			tail.next=null;
		}
		else
		{
			Node temp=new Node();
			temp.title=t;
			temp.id=id;
			tail.next=temp;
			tail=temp;
			tail.next=null;
		}
	}
	//Double Link list
	public void double_link_list_insert(String t,String id)
	{
		if(dhead==null)
		{
			dhead=new dNode();
			dhead.title=t;
			dhead.id=id;
			dtail=dhead;
			dtail.next=null;
		}
		else
		{
			dNode temp=new dNode();
			temp.title=t;
			temp.id=id;
			dtail.next=temp;
			temp.pre=dtail;
			dtail=temp;
			dtail.next=null;
		}
	}
	//Btree Link list
	public void insertbtree(String t,String id)
	{
		if(root==null)
		{
			root=new btreeNode();
			root.id=id;
			root.title=t;
			root.left=null;
			root.right=null;
		}
		else 
		{
			int ti=0,tm=0;
			btreeNode temp=root,pre=root;
			while(temp.left!=null && temp.right==null)
			{
				ti= t.length();
				tm=temp.title.length();
				if(ti<tm)
				{
					pre=temp;
					temp=temp.left;
				}
				else
				{
					pre=temp;
					temp=temp.right;
				}
			}
			if(ti>tm)
			{
				btreeNode newNode=new btreeNode();
				newNode.title=t;
				newNode.id=id;
				newNode.left=null;
				newNode.right=null;
				temp.left=newNode;
				temp.right=null;
				
			}
			else
			{
				btreeNode newNode=new btreeNode();
				newNode.title=t;
				newNode.id=id;
				newNode.left=null;
				newNode.right=null;
				temp.right=newNode;
				temp.left=null;
			}
		}
	}
	
	//AVL tree Link list insert tree
	public void insertAVLtree(String t,String id)
	{
		if(avlroot==null)
		{
			avlroot=new btreeNode();
			avlroot.id=id;
			avlroot.title=t;
			avlroot.left=null;
			avlroot.right=null;
		}
		else 
		{
			int ti=0,tm=0;
			btreeNode temp=avlroot,pre=avlroot;
			while(temp.left!=null && temp.right==null)
			{
				ti= t.length();
				tm=temp.title.length();
				if(ti<tm)
				{
					pre=temp;
					temp=temp.left;
				}
				else
				{
					pre=temp;
					temp=temp.right;
				}
			}
			if(ti>tm)
			{
				btreeNode newNode=new btreeNode();
				newNode.title=t;
				newNode.id=id;
				newNode.left=null;
				newNode.right=null;
				temp.left=newNode;
				temp.right=null;
				
			}
			else
			{
				btreeNode newNode=new btreeNode();
				newNode.title=t;
				newNode.id=id;
				newNode.left=null;
				newNode.right=null;
				temp.right=newNode;
				temp.left=null;
			}
		}
		balance(avlroot);
	}
	//find avl tree height
	int alv_tree_height(btreeNode start)
	{
		int heit=0;
		if(start!=null)
		{
			int l_heit=alv_tree_height(start.left);
			int r_heit=alv_tree_height(start.right);
			if(l_heit>r_heit)
			{
				heit=l_heit+1;
			}
			else
			{
				heit=r_heit+1;
			}	
		}
		return heit;
	}
	// AVL Tree Dfrence
	int diffance(btreeNode start)
	{
	    int l_height = alv_tree_height (start.left);
	    int r_height = alv_tree_height (start.right);
	    int dif= l_height - r_height;
	    return dif;
	}
	// AvL right Rutation
	btreeNode Right_rotation(btreeNode start)
	{
		btreeNode temp;
	    temp = start.right;
	    start.right = start.left;
	    start.left = start;
	    return temp;
	}
	// AvL Left Rutation
	btreeNode Left_rotation(btreeNode start)
	{
		btreeNode temp;
	    temp = start.left;
	    start.left = start.right;
	    start.right = start;
	    return temp;
	}
	
	// AvL Left to right Rutation
	btreeNode Left_To_Right_rotation(btreeNode start)
	{
		btreeNode temp;
	    temp = start.left;
	    start.left = Right_rotation(temp);
	    return Left_rotation(start);
	}
	
	// AvL Left to right Rutation
	btreeNode Right_To_Left__rotation(btreeNode start)
	{
		btreeNode temp;
	    temp = start.right;
	    start.right = Left_rotation(temp);
	    return Left_rotation(start);
	}
	
	//AVL Tree Balnace 
	public void balance(btreeNode start)
	{
	    int bal_factor = diffance (start);
	    if (bal_factor > 1)
	    {
	        if (diffance (start.left) > 0)
	        {
	        	start = Left_rotation (start);
	        }
	        else
	        {
	        	start = Right_rotation (start);
	        }
	    }
	    else if (bal_factor < -1)
	    {
	        if (diffance (start.right) > 0)
	        {
	        	start = Right_To_Left__rotation (start);
	        }
	        else
	        {
	        	start = Left_To_Right_rotation (start);
	        }
	    }
	}
	//Double Link list Delete Node
	public String double_link_list_Delete(String value)
	{
		dNode temp=dhead,cur=dhead;
		if(head!=null)
		{
			int i=0;
			while(temp!=null)
			{
				if(value.equals(temp.id) ||value.equals(temp.title))
				{
					i=1;
					break;
				}
				cur=temp;
				temp=temp.next;
			}
			if(i==1)
			{
				if(temp==dhead)
				{
					dhead=dhead.next;
					dhead.pre=null;
					temp.next=null;
				}
				else if(temp==dtail)
				{
					
					tail=tail.next=null;
					temp.pre=null;
				}
				else
				{
					temp=temp.next;
					cur.next=temp;
					temp.pre=cur;
				}
				return "video is deleted form data structure";
			}
			else
			{
				return "video could not found";
			}
		}
		else
		{
			return "empty";
		}
	}
	
	//delete Single LInk List Node
	public String delete(String value)
	{
		
		Node temp=head,cur=head;
		if(head!=null)
		{
			int i=0;
			while(temp!=null)
			{
				if(value.equals(temp.id) ||value.equals(temp.title))
				{
					System.out.println("value :"+value+" :temp:"+temp.title);
					i=1;
					break;
				}
				cur=temp;
				temp=temp.next;
			}
			if(i==1)
			{
				if(temp==head)
				{
					head=head.next;
					temp.next=null;
				}
				else if(temp==tail)
				{
					tail=cur;
					tail.next=null;
				}
				else
				{
					temp=temp.next;
					cur.next=temp;
				}
				return "video is deleted form data structure";
			}
			else
			{
				return "video could not found";
			}
		}
		else
		{
			return "empty";
		}
	}
	//delete Btree LInk List Node
	public String delete_btree(String value)
	{
		if(root==null)
		{
			return "Empty Tree";
		}
		else 
		{
			int ti=0,tm=0,flag=0;
			btreeNode temp=root,pre=root;
			while(temp!=null)
			{
				ti= value.length();
				tm=temp.title.length();
				if(value.equals(temp.title))
				{
					flag=1;
					break;
				}
				else if(ti<tm)
				{
					pre=temp;
					temp=temp.left;
					
				}
				else
				{
					pre=temp;
					temp=temp.right;
				}
			}
			if(flag==1)
			{
				if(temp==null && temp==null)
				{
					if(pre.left==temp)
					{
						pre.left=null;
					}
					else
					{
						pre.right=null;
					}
				}
				else if(temp!=null && temp!=null)
				{
					btreeNode pretem=root;
					while(temp!=null)
					{
						pretem=temp;
						temp=temp.left;
					}
					String t=temp.title;
					String i=temp.id;
					temp.title=pre.title;
					temp.id=pre.id;
					pre.id=i;
					pre.title=t;
					if(temp.left==null && temp.right==null)
					{
						
					}
					else
					{
						if(temp.left==null && temp.right!=null)
						{
							pretem.left=temp.right;
						}
						else
						{
							pretem.right=temp.left;
						}
					}
				}
				return "tree Node is Deleted";
			}
			else
			{
				return "Could Not Found";
			}
		}
	}
	//delete AVL tree LInk List Node
	public String delete_AVLtree(String value)
	{
		if(avlroot==null)
		{
			return "Empty Tree";
		}
		else 
		{
			int ti=0,tm=0,flag=0;
			btreeNode temp=avlroot,pre=avlroot;
			while(temp!=null)
			{
				ti= value.length();
				tm=temp.title.length();
				if(value.equals(temp.title))
				{
					flag=1;
					break;
				}
				else if(ti<tm)
				{
					pre=temp;
					temp=temp.left;
					
				}
				else
				{
					pre=temp;
					temp=temp.right;
				}
			}
			if(flag==1)
			{
				if(temp==null && temp==null)
				{
					if(pre.left==temp)
					{
						pre.left=null;
					}
					else
					{
						pre.right=null;
					}
				}
				else if(temp!=null && temp!=null)
				{
					btreeNode pretem=avlroot;
					while(temp!=null)
					{
						pretem=temp;
						temp=temp.left;
					}
					String t=temp.title;
					String i=temp.id;
					temp.title=pre.title;
					temp.id=pre.id;
					pre.id=i;
					pre.title=t;
					if(temp.left==null && temp.right==null)
					{
						
					}
					else
					{
						if(temp.left==null && temp.right!=null)
						{
							pretem.left=temp.right;
						}
						else
						{
							pretem.right=temp.left;
						}
					}
				}
				return "tree Node is Deleted";
			}
			else
			{
				return "Could Not Found";
			}
		}
	}

	//get All Title Single link List
	public void get_title()
	{
		Node temp=head;
		if(head!=null)
		{
			while(temp!=null)
			{
				System.out.println("name :"+temp.title);
				temp=temp.next;	
			}
		}
		else
		{
			System.out.println("Empty :");
		}	
	}
	//get all title of video
	public void get_video_title()
	{
		Node temp=head;
		if(head!=null)
		{
			while(temp!=null)
			{
				System.out.println("name :"+temp.title);
				temp=temp.next;
			}
		}
		else
		{
			System.out.println("Empty :");
		}	
	}
	//get all Id of video
    //get All Title Double link List
	public void get_ID()
	{
    	dNode temp=dhead;
		if(dhead!=null)
		{
			while(temp!=null)
			{
				System.out.println("ID :"+temp.id);
				temp=temp.next;
			}
		}
		else
		{
			System.out.println("Empty :");
		}
	}
	//Find Video ID
	/*public String findVideoID(String title){
		Node temp=head;
		if(head!=null)
		{
			while(temp!=null)
			{
				if(title.equals(temp.id))
				{
					return temp.id;
				}
				temp=temp.next;
			}
			return null;
			//if(i==0)
			//{
				//return null;
			//}
			//else{
				//return null;
			//}
		}
		else
		{
			return null;
		}
	}*/
	//find data from Single link list
  //find all video	through title or id 
    public boolean find(String value)
    {
    	Node temp=head;
		if(head!=null)
		{
			int i=0;
			while(temp!=null)
			{
				if(value.equals(temp.id) ||value.equals(temp.title))
				{
					i=1;
				}
				temp=temp.next;
			}
			if(i==0)
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		else
		{
			return false;
		}
    }
    //find data from Double link list
    public boolean find_DLL(String value)
    {
    	dNode temp=dhead;
		if(head!=null)
		{
			int i=0;
			while(temp!=null)
			{
				if(value.equals(temp.id) ||value.equals(temp.title))
				{
					i=1;
				}
				temp=temp.next;
			}
			if(i==0)
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		else
		{
			return false;
		}
    }
    //Find data from btree
    public String find_btree(String t)
	{
		if(root==null)
		{
			return "Empty Tree";
		}
		else 
		{
			int ti=0,tm=0,flag=0;
			btreeNode temp=root;
			while(temp!=null )
			{
				ti= t.length();
				tm=temp.title.length();
				if(t.equals(temp.title))
				{
					flag=1;
					break;
				}
				else if(ti<tm)
				{
					temp=temp.left;
					
				}
				else
				{
					temp=temp.right;
				}
			}
			if(flag==1)
			{
				return "Found";
			}
			else
			{
				return "Not Found";
			}
		}
	}
    
    //Find data form avl Tree
    public String find_AVLtree(String t)
	{
		if(avlroot==null)
		{
			return "Empty Tree";
		}
		else 
		{
			int ti=0,tm=0,flag=0;
			btreeNode temp=avlroot;
			while(temp!=null )
			{
				ti= t.length();
				tm=temp.title.length();
				if(t.equals(temp.title))
				{
					flag=1;
					break;
				}
				else if(ti<tm)
				{
					temp=temp.left;
					
				}
				else
				{
					temp=temp.right;
				}
			}
			if(flag==1)
			{
				return "Found";
			}
			else
			{
				return "Not Found";
			}
		}
	}

    
    //Display data from DLL
    public void display_DLL()
    {
    	dNode temp=dhead;
		if(dhead!=null)
		{
			while(temp!=null)
			{
				System.out.println("Video :[ Title : " + temp.title + ", ID : " + temp.id+" ]");
				temp=temp.next;
			}
		}
		else
		{
			System.out.println("Empty :");
		}
    }
    public void checkOut_SLL(String title,String cust_name){
    	//System.out.println("Entered checkOut in video.");
    	Node temp=head;
		if(head!=null)
		{
			//System.out.println("Entered IF and check existence of other variiables.");
			int i=0;
			while(temp!=null)
			{
				
				//System.out.println("Entered while ");
				if(temp.title.equals(title))
				{
					i=i+1;
					if(temp.rent==true)
					{
							System.out.println("Video not available to rent.");
					}
					else if(temp.rent==false)
					{
						temp.rent=true;
						temp.rent_by=cust_name;
					}
				}
				temp=temp.next;
			}
			if(i==0)
			{
				System.out.println("No matching video found");
			}
		}
		else
		{
			System.out.println("Empty :");
		}
    }
    public void checkOut_DLL(String title, String cust_name)
    {
    	dNode temp=dhead;
		if(dhead!=null)
		{
			//System.out.println("Entered IF and check existence of other variiables.");
			int i=0;
			while(temp!=null)
			{
				
				//System.out.println("Entered while ");
				if(temp.title.equals(title))
				{
					i=i+1;
					if(temp.rent==true)
					{
							System.out.println("Video not available to rent.");
					}
					else if(temp.rent==false)
					{
						temp.rent=true;
						temp.rent_by=cust_name;
					}
				}
				temp=temp.next;
			}
			if(i==0)
			{
				System.out.println("No matching video found");
			}
		}
		else
		{
			System.out.println("Empty :");
		}
    }
    
    public void checkIn_SLL(String title,String cust_name)
    {
    	Node temp=head;
		if(head!=null)
		{
			//System.out.println("Entered IF and check existence of other variiables.");
			int i=0;
			while(temp!=null)
			{
				
				//System.out.println("Entered while ");
				if(temp.title.equals(title))
				{
					i=i+1;
					if(temp.rent==false)
					{
							System.out.println("Video not rented.");
					}
					else if(temp.rent==true)
					{
						temp.rent=false;
						temp.rent_by=null;
					}
				}
				temp=temp.next;
			}
			if(i==0)
			{
				System.out.println("No matching video found");
			}
		}
		else
		{
			System.out.println("Empty :");
		}
    }
    public void checkIn_DLL(String title,String cust_name)
    {
    	dNode temp=dhead;
		if(dhead!=null)
		{
			//System.out.println("Entered IF and check existence of other variiables.");
			int i=0;
			while(temp!=null)
			{
				
				//System.out.println("Entered while ");
				if(temp.title.equals(title))
				{
					i=i+1;
					if(temp.rent==false)
					{
							System.out.println("Video not rented.");
					}
					else if(temp.rent==true)
					{
						temp.rent=false;
						temp.rent_by=null;
					}
				}
				temp=temp.next;
			}
			if(i==0)
			{
				System.out.println("No matching video found");
			}
		}
		else
		{
			System.out.println("Empty :");
		}
    }
//to display instore videos
    public boolean display_instore_SLL()
    {
    	Node temp=head;
		if(head!=null)
		{
			while(temp!=null)
			{
				if(temp.rent==false)
				{
				System.out.println("Video :[ Title : " + temp.title + ", ID : " + temp.id+" ]");
				}
				temp=temp.next;
			}
			return true;
		}
		else
		{
		 return false;
		}
    }
    public boolean display_instore_DLL()
    {
    	dNode temp=dhead;
		if(dhead!=null)
		{
			while(temp!=null)
			{
				if(temp.rent==false)
				{
				System.out.println("Video :[ Title : " + temp.title + ", ID : " + temp.id+" ]");
				}
				temp=temp.next;
			}
			return true;
		}
		else
		{
		 return false;
		}
    }
    public boolean display_rent_videos_SLL()
    {
    	Node temp=head;
		if(head!=null)
		{
			while(temp!=null)
			{
				if(temp.rent==true)
				{
				System.out.println("Video " + temp.title + ", ID : " + temp.id+", rented by "+temp.rent_by);
				}
				temp=temp.next;
			}
			return true;
		}
		else
		{
		 return false;
		}
    }
    public boolean display_rent_videos_DLL()
    {
    	dNode temp=dhead;
		if(dhead!=null)
		{
			while(temp!=null)
			{
				if(temp.rent==true)
				{
				System.out.println("Video " + temp.title + ", ID : " + temp.id+", rented by "+temp.rent_by);
				}
				temp=temp.next;
			}
			return true;
		}
		else
		{
		 return false;
		}
    }
    public boolean display_rent_videos_bycust_SLL(String cust)
    {
    	Node temp=head;
		if(head!=null)
		{
			while(temp!=null)
			{
				if(temp.rent==true&&temp.rent_by.equals(cust))
				{
				System.out.println("Video " + temp.title + ", ID : " + temp.id);
				}
				temp=temp.next;
			}
			return true;
		}
		else
		{
		 return false;
		}
    }
    public boolean display_rent_videos_bycust_DLL(String cust)
    {
    	dNode temp=dhead;
		if(dhead!=null)
		{
			while(temp!=null)
			{
				if(temp.rent==true&&temp.rent_by.equals(cust))
				{
				System.out.println("Video " + temp.title + ", ID : " + temp.id);
				}
				temp=temp.next;
			}
			return true;
		}
		else
		{
		 return false;
		}
    }
 // dispay information
   //Display data from SLL
    
    public void display_SLL()
    {
    	Node temp=head;
		if(head!=null)
		{
			while(temp!=null)
			{
				
				System.out.println("Video :[ Title : " + temp.title + ", ID : " + temp.id+" ]");
				
				temp=temp.next;
			}
		}
		else
		{
			System.out.println("Empty :");
		}
    }
  //Display data from Btree
    public void inOrder(btreeNode binTree)
    {
        if (binTree != null)
        {
            inOrder(binTree.left);
            System.out.println("Video :[ Title : " + binTree.title + ", ID : " + binTree.id+" ]");
            inOrder(binTree.right);
        } 
    }
    public void Display_Binary()
    {
        inOrder(root);
    }
    public void Display_AVL()
    {
        inOrder(avlroot);
    }
}
