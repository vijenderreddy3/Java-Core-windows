
public class Queue {
	//declair all object and attribut
	Signlelinklist FSLL,RSLL;
	doublclinklist FDLL,RDLL;
	btreelinklist Root;
	int service;
	public Queue(){
		FSLL=RSLL=null;
		FDLL=RDLL=null;
		Root=null;
	}
	// insert information in equeu SLL
	public void enqueSLL(int req)
	{
		
		if(FSLL==null)
		{
			FSLL=new Signlelinklist();
			FSLL.totelrequest=req;
			RSLL=FSLL;
			RSLL.link=null;
		}
		else
		{
			Signlelinklist temp=new Signlelinklist();
			temp.totelrequest=req;
			RSLL.link=temp;
			RSLL=temp;
			RSLL.link=null;
		}
		
	}
	// insert information in equeu DLL
	public void enqueDLL(int req)
	{
		
		if(FDLL==null)
		{
			FDLL=new doublclinklist();
			FDLL.totelrequest=req;
			RDLL=FDLL;
			RDLL.Next=null;	
		}
		else
		{
			doublclinklist temp=new doublclinklist();
			temp.totelrequest=req;
			RDLL.Next=temp;
			temp.Pre=RDLL;
			RDLL=temp;
			RDLL.Next=null;
		}
	}
	public void display()
	{
		if(FDLL!=null)
		{
			while(FDLL!=null)
			{
				System.out.println(FDLL.totelrequest);
				FDLL=FDLL.Next;
			}
		}
	}
	// insert information in equeu Btree
	public void enqueBinaryTree(int req)
	{
		if(Root==null)
    	{
    		Root=new btreelinklist();
    		Root.totelrequest=req;
			Root.left=null;
			Root.right=null;
    	}
    	else
    	{
    		btreelinklist temp=Root;
    		while(temp.left!=null && temp.right!=null)
    		{
    			if(temp.totelrequest>req)
    			{
    				temp=temp.left;
    			}
    			else
    			{
    				temp=temp.right;
    			}
    		}
    		if(temp.totelrequest>req)
    		{
    			btreelinklist newNode=new btreelinklist();
    			newNode.totelrequest=req;
    			temp.left=newNode;
    			newNode.left=null;
    			newNode.right=null;
    		}
    		else 
    		{
    			btreelinklist newNode=new btreelinklist();
    			newNode.totelrequest=req;
    			temp.right=newNode;
    			newNode.left=null;
    			newNode.right=null;
    		}
    	}
	}
	//we assum every signle request take 1 sec to process task
	//Calculate service time in SLL
	public int servicetime_SLL()
	{
		service=0;
		if(FSLL!=null)
		{
			while(FSLL!=null)
			{
				service=service+FSLL.totelrequest;
				FSLL=FSLL.link;
			}
		}
		return service;
	}
	//Calculate service time in DLL
	public int servicetime_DLL()
	{
		int ser=0;
		if(FDLL!=null)
		{
			while(FDLL!=null)
			{
				ser=ser+FDLL.totelrequest;
				FDLL=FDLL.Next;
			}
		}
		return service;
	}
	//Calculate service time in BTree
	public int servicetime_Btree()
	{
		service=0;
		if(Root!=null)
    	{
    		btreelinklist temp=Root;
    		while(temp.left!=null && temp.right!=null)
    		{
    			if(temp.totelrequest>service)
    			{
    				service=service+temp.totelrequest;
    				temp=temp.left;
    			}
    			else
    			{
    				service=service+temp.totelrequest;
    				temp=temp.right;
    			}
    		}
    	}
		return service;
	}
	public int servicetime_AVL()
	{
		service=0;
		if(Root!=null)
    	{
    		btreelinklist temp=Root;
    		while(temp.left!=null && temp.right!=null)
    		{
    			if(temp.totelrequest>service)
    			{
    				service=service+temp.totelrequest;
    				temp=temp.left;
    			}
    			else
    			{
    				service=service+temp.totelrequest;
    				temp=temp.right;
    			}
    		}
    	}
		return service;
	}
}
