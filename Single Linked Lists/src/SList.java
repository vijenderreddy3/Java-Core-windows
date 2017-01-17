
public class SList {
	private SListNode head;
	private int size;
	public SList()
	{
		head=null;
		size=0;
	}
	public void insertFront(Object item)
	{
		head=new SListNode(item,head);
		size++;
	}
	public void deleteFront()
	{
		if(head!=null)
			head=head.next;
		size--;
	}
	private SListNode temp=new SListNode(head.item,head);
	public void display()
	{   
		//if(temp.next!=null)
		//{
		System.out.println(temp.item);
		//temp=temp.next;
		//display();
		//}
	}
}
