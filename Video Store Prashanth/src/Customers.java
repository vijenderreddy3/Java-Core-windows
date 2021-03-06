public class Customers {
	//Single Link list
	public class Node
	{
		public Node next;
		public String Name;
		public String id;
	}
	//DOubly Link list
	public class dNode
	{
		public dNode next;
		public dNode pre;
		public String Name;
		public String id;
	}
	//Binary tree
	private class btreeNode
		{
			public btreeNode left;
			public btreeNode right;
			public String Name;
			public String id;
		}
	//Node Link list obj declair
	public Node head,tail;
	public dNode dhead,dtail;
	public Videos v=new Videos();
	public btreeNode root,avlroot;
	//Constructor
	public Customers()
	{
	}
	// Overload Constructor
	public Customers(String cus,String id)
	{
		insert( cus, id);
	}
	//Single Link list
	//that function insert also insert value in all node list single double bainary
	public void insert(String cus,String id)
	{
		double_link_list_insert(cus,id);
		if(head==null)
		{
			head=new Node();
			head.Name=cus;
			head.id=id;
			tail=head;
			tail.next=null;
		}
		else
		{
			Node temp=new Node();
			temp.Name=cus;
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
			dhead.Name=t;
			dhead.id=id;
			dtail=dhead;
			dtail.next=null;
		}
		else
		{
			dNode temp=new dNode();
			temp.Name=t;
			temp.id=id;
			dtail.next=temp;
			temp.pre=dtail;
			dtail=temp;
			dtail.next=null;
		}
	}
	//insert btree 	
	public void insertbtree(String t,String id)
	{
		if(root==null)
		{
			root=new btreeNode();
			root.id=id;
			root.Name=t;
			root.left=null;
			root.right=null;
		}
		else 
		{
			int ti=0,tm=0;
			btreeNode temp=root,pre=root;
			while(temp.left!=null && temp.right!=null)
			{
				ti= t.length();
				tm=temp.Name.length();
				if(ti<tm)
				{
					temp=temp.left;
					
				}
				else
				{
					temp=temp.right;
				}
			}
			if(ti>tm)
			{
				btreeNode newNode=new btreeNode();
				newNode.Name=t;
				newNode.id=id;
				newNode.left=null;
				newNode.right=null;
				temp.left=newNode;
			}
			else
			{
				btreeNode newNode=new btreeNode();
				newNode.Name=t;
				newNode.id=id;
				newNode.left=null;
				newNode.right=null;
				temp.right=newNode;
			}
		}
	}
	//AVL Tree Insert 
	public void insertAVLtree(String t,String id)
	{
		if(avlroot==null)
		{
			avlroot=new btreeNode();
			avlroot.id=id;
			avlroot.Name=t;
			avlroot.left=null;
			avlroot.right=null;
		}
		else 
		{
			int ti=0,tm=0;
			btreeNode temp=avlroot,pre=avlroot;
			while(temp.left!=null && temp.right!=null)
			{
				ti= t.length();
				tm=temp.Name.length();
				if(ti<tm)
				{
					temp=temp.left;
					
				}
				else
				{
					temp=temp.right;
				}
			}
			if(ti>tm)
			{
				btreeNode newNode=new btreeNode();
				newNode.Name=t;
				newNode.id=id;
				newNode.left=null;
				newNode.right=null;
				temp.left=newNode;
			}
			else
			{
				btreeNode newNode=new btreeNode();
				newNode.Name=t;
				newNode.id=id;
				newNode.left=null;
				newNode.right=null;
				temp.right=newNode;
			}
		}
		balance(avlroot);
	}
	
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

	//Single Link list Delete Node
	public String delete(String value)
	{
		Node temp=head,cur=head;
		if(head!=null)
		{
			int i=0;
			while(temp!=null)
			{
				if(value.equals(temp.id) ||value.equals(temp.Name))
				{
					i=1;
					break;
				}
				cur=temp;
				temp=temp.next;
			}
			if(i==0)
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
					head.next=null;
				}
				else
				{
					temp=temp.next;
					cur.next=temp;
				}
				return "cutomer is deleted form data structure";
			}
			else
			{
				return "cutomer could not found";
			}
		}
		else
		{
			return "empty";
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
				if(value.equals(temp.id) ||value.equals(temp.Name))
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
	//Btree Link List Delete Node
	public String delete_btree(String value)
	{
		if(root==null)
		{
			return "Empty Tree";
		}
		else if(root.left==null && root.right==null)
		{
			
			if(value.equals(root.Name))
			{
				root=null;
				return "tree Node is Deleted";
			}
			else
			{
				return "Could not found";
			}
		}
		else 
		{
			
			int ti=0,tm=0,flag=0;
			btreeNode temp=root,pre=root;
			while(temp!=null)
			{
				ti= value.length();
				tm=temp.Name.length();
				if(value.equals(temp.Name))
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
					String t=temp.Name;
					String i=temp.id;
					temp.Name=pre.Name;
					temp.id=pre.id;
					pre.id=i;
					pre.Name=t;
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
	//AVLTree lnk list delete node
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
				tm=temp.Name.length();
				if(value.equals(temp.Name))
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
					String t=temp.Name;
					String i=temp.id;
					temp.Name=pre.Name;
					temp.id=pre.id;
					pre.id=i;
					pre.Name=t;
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

	//get all Name of Customer
	public void get_Name()
	{
		Node temp=head;
		if(head!=null)
		{
			while(temp!=null)
			{
				System.out.println("name :"+temp.Name);
				temp=temp.next;
				
			}
			return ;
		}
		else
		{
			System.out.println("Empty :");
		}
		
	}
	//get all ID of Custormer
    public void get_ID()
	{
    	Node temp=head;
		if(head!=null)
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
  //find all Customer through Name or id 
    public String find(String value)
    {
    	Node temp=head;
		if(head!=null)
		{
			int i=0;
			while(temp!=null)
			{
				if(value.equals(temp.id) ||value.equals(temp.Name))
				{
					i=1;
				}
				temp=temp.next;
			}
			if(i==0)
			{
				return "Not found";
			}
			else
			{
				return "found";
			}
		}
		else
		{
			return "empty";
		}
    }

    public boolean find_DLL(String value)
    {
    	dNode temp=dhead;
		if(head!=null)
		{
			int i=0;
			while(temp!=null)
			{
				if(value.equals(temp.id) ||value.equals(temp.Name))
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
			while(temp!=null)
			{
				ti= t.length();
				tm=temp.Name.length();
				if(t.equals(temp.Name))
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
    //Find Data from avl tree
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
			while(temp!=null)
			{
				ti= t.length();
				tm=temp.Name.length();
				if(t.equals(temp.Name))
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

    public void display_SLL()
    {
    	Node temp=head;
		if(head!=null)
		{
			while(temp!=null)
			{
				System.out.println("Customer :[ Name : " + temp.Name + ", ID : " + temp.id+" ]");
				temp=temp.next;
			}
		}
		else
		{
			System.out.println("Empty :");
		}
    }
    
    public void display_DLL()
    {
    	dNode temp=dhead;
		if(dhead!=null)
		{
			while(temp!=null)
			{
				System.out.println("Customer :[ Name : " + temp.Name + ", ID : " + temp.id+" ]");
				temp=temp.next;
			}
		}
		else
		{
			System.out.println("Empty :");
		}
    }
    
    public void inOrder(btreeNode binTree)
    {
        if (binTree != null)
        {
            inOrder(binTree.left);
            System.out.println("Video :[ Name : " + binTree.Name + ", ID : " + binTree.id+" ]");
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
