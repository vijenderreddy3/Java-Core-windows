
public class test {

	public static void main(String[] args) {
		ListNodes l1=new ListNodes();
		ListNodes l2=new ListNodes();
		ListNodes l3=new ListNodes();
		l1.item=7;
		l2.item=0;
		l3.item=6;
		l1.next=l2;
		l2.next=l3;
		l3.next=null;
		l2.insertAfter(3);
		ListNodes list=new ListNodes();
		list=l1.nth(3);
		System.out.println("nth item:"+list.item);
		System.out.println("List items:"+l1.item+","+l2.item+","+l2.next.item+","+l2.next.next.item+".");
	
	}
}
