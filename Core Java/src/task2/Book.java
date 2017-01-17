package task2;

public class Book {
	private static final String authorName="Vijay";
	private static final String authorGender="Male";
	private static final String authorEmail="vijenderreddy3@gmail.com";
	private String bookName;
	private long noOfPagesInBook;
	Book(String bookName,long noOfPagesInBook){
		this.bookName =bookName;
		this.noOfPagesInBook=noOfPagesInBook;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public long getNoOfPagesInBook() {
		return noOfPagesInBook;
	}
	public void setNoOfPagesInBook(long noOfPagesInBook) {
		this.noOfPagesInBook = noOfPagesInBook;
	}
	public static String getAuthorname() {
		return authorName;
	}
	public static String getAuthorgender() {
		return authorGender;
	}
	public static String getAuthoremail() {
		return authorEmail;
	}

}
