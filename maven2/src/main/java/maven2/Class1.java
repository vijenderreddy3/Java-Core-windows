package maven2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Class1 {

	public static void main(String[] args) {
		ApplicationContext obj= new ClassPathXmlApplicationContext("NewFile.xml");
		Test_class tObj= (Test_class)obj.getBean("test_classObj");
		tObj.display();
	}

}
