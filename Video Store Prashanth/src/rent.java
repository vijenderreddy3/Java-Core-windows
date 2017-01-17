

public class rent {

	//Delare all obj and attribute
	Node root,avlroot;
	public Customers cus=new Customers();
	public Videos v=new Videos();
	int rnt,inday,outday;
	//Constructor
	public rent()
	{
		root=null;
	}
	// verify customer is information
	public boolean verifycustomer(String data)
	{
		if(v.find(data))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	//verify video infomartion in data structure
	public boolean verifyvideo(String title)
	{
		if(cus.find(title)=="found")
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	// checkout video 
	public void checkout(String data_type,String title,String id,String c,String cid)
    {
    	v.insert(title, c);
    	//System.out.println("Video:"+title+" rented by :"+c);
    }
	//checkIn video
    public void checkin(String title,String cus)
    {
    	//1st verfyvideo & cutomer infomation then check in
    	if(verifyvideo(title) && verifycustomer(cus))
    	{
    		//outday=day;
    		v.delete(title);
    		
    	}
    	else
    	{
    		System.out.println("this video could not checkout");
    	}
    	
    }
    //insert data in btree in basis of rent
    public void btree_insert(int r)
    {
    	if(root==null)
    	{
    		root=new Node();
    		root.totalrent=r;
    	}
    	else
    	{
    		Node temp=root;
    		while(temp.left!=null && temp.right!=null)
    		{
    			if(temp.totalrent>r)
    			{
    				temp=temp.left;
    			}
    			else
    			{
    				temp=temp.right;
    			}
    		}
    		if(temp.totalrent>r)
    		{
    			Node newNode=new Node();
    			newNode.totalrent=r;
    			temp.left=newNode;
    		}
    		else 
    		{
    			Node newNode=new Node();
    			newNode.totalrent=r;
    			temp.right=newNode;
    		}
    	}
    }
    //insert data in avl tree in basis of rent
    public void AVLtree_insert(int r)
    {
    	if(avlroot==null)
    	{
    		avlroot=new Node();
    		avlroot.totalrent=r;
    	}
    	else
    	{
    		Node temp=avlroot;
    		while(temp.left!=null && temp.right!=null)
    		{
    			if(temp.totalrent>r)
    			{
    				temp=temp.left;
    			}
    			else
    			{
    				temp=temp.right;
    			}
    		}
    		if(temp.totalrent>r)
    		{
    			Node newNode=new Node();
    			newNode.totalrent=r;
    			temp.left=newNode;
    		}
    		else 
    		{
    			Node newNode=new Node();
    			newNode.totalrent=r;
    			temp.right=newNode;
    		}
    	}
    	balance(avlroot);
    }
    int alv_tree_height(Node start)
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
	int diffance(Node start)
	{
	    int l_height = alv_tree_height (start.left);
	    int r_height = alv_tree_height (start.right);
	    int dif= l_height - r_height;
	    return dif;
	}
	// AvL right Rutation
	Node Right_rotation(Node start)
	{
		Node temp;
	    temp = start.right;
	    start.right = start.left;
	    start.left = start;
	    return temp;
	}
	// AvL Left Rutation
	Node Left_rotation(Node start)
	{
		Node temp;
	    temp = start.left;
	    start.left = start.right;
	    start.right = start;
	    return temp;
	}
	
	// AvL Left to right Rutation
	Node Left_To_Right_rotation(Node start)
	{
		Node temp;
	    temp = start.left;
	    start.left = Right_rotation(temp);
	    return Left_rotation(start);
	}
	
	// AvL Left to right Rutation
	Node Right_To_Left__rotation(Node start)
	{
		Node temp;
	    temp = start.right;
	    start.right = Left_rotation(temp);
	    return Left_rotation(start);
	}
	
	//AVL Tree Balnace 
	public void balance(Node start)
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


    // get video all information 
    public void GetVideo()
    {
    	v.get_video_title();
    }
 // display all information of video
    public void dispay_Video()
    {
    	v.display_SLL();
    	v.display_DLL();
    }
 // display all information of customer
    public void display_customer_SLL()
    {
    	cus.display_SLL();
    }
    public void display_customer_DLL()
    {
    	cus.display_DLL();
    }
    public void inOrder(Node binTree)
    {
        if (binTree != null)
        {
            inOrder(binTree.left);
            System.out.println( " :"+binTree.totalrent );
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
