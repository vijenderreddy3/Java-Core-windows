public class ListNodes{
	int item;
	ListNodes next;
	public ListNodes()
	{
	}
	public ListNodes nth(int position)
	{
		if(position==1)
			return this;
		else if((position<1)||(next==null))
		{
		return null;	
		}
		else
		{
		return next.nth(position-1);	
		}
	}
	public ListNodes(int item,ListNodes next){
		this.item=item;
		this.next=next;
	}
	public ListNodes(int item)
	{
		this(item,null);
	}
	public void insertAfter(int item)
	{
		next= new ListNodes(item,next);
	}
}
