
public class CDList {
	private CDListNode head;
	private int size=0;
	private CDListNode sentinal=new CDListNode();
	private CDListNode temp=new CDListNode();
	public CDList()
	{
		sentinal.item=null;
		sentinal.next=sentinal;
		sentinal.prev=sentinal;
	}
	public void insertFront(Object item)
	{
		if(size==0)
		{
			head=new CDListNode(item,sentinal,sentinal);
			sentinal.next=head;
			sentinal.prev=head;
			size++;
			System.out.println("First node of the list added"+item);
		}
		else if(size>0)
		{   
			sentinal.next=new CDListNode(item,head,head.prev);
			head=sentinal.next;
			head.next.prev=head;
			size++;
			System.out.println("Node added at the front of list."+item);
		}
		temp=head;
	}
	public void insertBack(Object item)
	{
		if(size==0)
		{
			head=new CDListNode(item,sentinal,sentinal);
			sentinal.next=head;
			sentinal.prev=head;
			size++;
			System.out.println("First node of the list added."+item);
		}
		else if(size>0)
		{   
			
			sentinal.prev=new CDListNode(item,sentinal,sentinal.prev);
			sentinal.prev.prev.next=sentinal.prev;
			size++;
			System.out.println("Node added at the back of list."+item);
		}
	}
	
	public void display()
	{
		//System.out.println(head.item);
		if(temp.next!=head)
		{
			System.out.println(temp.item);
			temp=temp.next;
			display();
		}	
	}
		
	/*public void removeFront()
	{
		if(size>=2)
		{
			sentinal.prev.next=null;
			tail=tail.prev;
		}
		if(size==1)
		{
			
		}
	}*/
}
