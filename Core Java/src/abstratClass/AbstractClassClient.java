package abstratClass;

public class AbstractClassClient extends AbstractClassEx{

	public static void main(String[] args) {
		AbstractClassClient a=new AbstractClassClient();
		a.f2();
		a.f1();
	}

	@Override
	public void f2() {
		System.out.println("This is an abstract method");
	}

}
