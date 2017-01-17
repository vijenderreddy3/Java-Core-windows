
public class rent {

	//Delare all obj and attribute
	Node root;
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
	public void checkout(String title,String id,String c,String i,int day)
    {
    	cus.insert(c, i);
    	v.insert(title, id);
    	inday=day;
    }
	//checkIn video
    public void checkin(String title,String cus,int day)
    {
    	//1st verfyvideo & cutomer infomation then check in
    	if(verifyvideo(title) && verifycustomer(cus))
    	{
    		outday=day;
    		int r=inday-outday;
    		btree_insert(r*5);
    		System.out.println("Total rant :"+r);
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
}
