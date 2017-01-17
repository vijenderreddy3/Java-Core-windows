package basics;

public class Formula2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int a=13,d=5;
		float b=7.6F,c=7.3F;
		System.out.println("The result of formula (a+b)*(c-d)for the given values a=13,b=7.6,c=7.3,d=5: "+mul(add(a,b),sub(c,d)));
	}
	public static float add(int a, float b){
		return a+b;
	}
	public static float sub(float c, int d){
		return c-d;
	}
	public static int mul(float x, float y){
		return (int)(x*y);
	}
}
