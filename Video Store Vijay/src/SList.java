
public class SList {
	private SingleLinkedListNode head;
	private int size;
	public SList()
	{
		head=null;
		size=0;
	}
	public void addVideo(Object item)
	{
		head=new SingleLinkedListNode(item,null);
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
