package basics;

public class ArithemeticOperators {

	public static void main(String[] args) {
		int x=10,y=3;
		add(x,y);
		sub(x,y);
		mul(x,y);
		divi(x,y);

	}
	public static void add(int a, int b)
	{
		int c=a+b;
		System.out.println("Addition of given 2 numbers is :"+c);
	}
	public static void sub(int a, int b)
	{
		int c=a-b;
		System.out.println("Subsraction of given 2 numbers is :"+c);
	}
	/**
	 * @param a
	 * @param b
	 */
	public static void mul(int a, int b)
	{
		int c=a*b;
		System.out.println("Multiplication of given 2 numbers is :"+c);
	}
	/**
	 * @param a
	 * @param b
	 */
	public static void divi(int a, int b)
	{
		float c=a/b;
		System.out.println("Division of given 2 numbers is :"+c);
	}
}
