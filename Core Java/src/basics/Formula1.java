package basics;

public class Formula1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int a=13,b=5,c=2;
		float d=7.0F;
		System.out.println("The result of formula (((a+b)*c)/d)for the given values a=10,b=5,c=2,d=4: "+div(mul(add(a,b),c),d));
	}
	public static int add(int a, int b){
		return a+b;
	}
	public static int mul(int x, int c){
		return x*c;
	}
	public static float div(int y, float d){
		return y/d;
	}

}
