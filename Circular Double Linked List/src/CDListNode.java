
public class CDListNode {
	public Object item;
	public CDListNode next;
	public CDListNode prev;
	public CDListNode()
	{
	}
	public CDListNode(Object item,CDListNode next,CDListNode prev)
	{
		this.item=item;
		this.next=next;
		this.prev=prev;
	}
}
