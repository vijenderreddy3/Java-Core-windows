
public class DList {
	private DListNode head;
	private DListNode tail;
	private int size;
	public void removeTail()
	{
		if(size>=2)
		{
			tail.prev.next=null;
			tail=tail.prev;
		}
		if(size==1)
		{
			
		}
	}
}
