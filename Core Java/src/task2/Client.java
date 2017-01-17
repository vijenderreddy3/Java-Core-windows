package task2;

public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Book b1=new Book("Core Java",350);
		Book b2=new Book("Advanced Java",1000);
		Book b3=new Book("Core Java& Advanced Java",1350);
		System.out.println("1st book details:\nBook Name:"+b1.getBookName()+"\nNumber of pages:"+b1.getNoOfPagesInBook()+"\nAuthor of the book:"+b1.getAuthorname()+"\nAuthor Gender:"+b1.getAuthorgender()+"\nAuthor Email:"+b1.getAuthoremail());
		System.out.println("\n2nd book details:\nBook Name:"+b2.getBookName()+"\nNumber of pages:"+b2.getNoOfPagesInBook()+"\nAuthor of the book:"+b2.getAuthorname()+"\nAuthor Gender:"+b2.getAuthorgender()+"\nAuthor Email:"+b2.getAuthoremail());
		System.out.println("\n3rd book details:\nBook Name:"+b3.getBookName()+"\nNumber of pages:"+b3.getNoOfPagesInBook()+"\nAuthor of the book:"+b3.getAuthorname()+"\nAuthor Gender:"+b3.getAuthorgender()+"\nAuthor Email:"+b3.getAuthoremail());
	}

}
