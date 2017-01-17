public class DAYS
{
	public static void main(String args[])
	{
		DAYS one = new DAYS();
		System.out.println(one.Work("Mary"));
		one.Project("Mary");
		String name = "Ashwin Naini";
		boolean happy = true;
		System.out.println(name +" "+happy);
	}
		public String Work(String x)
		{
			return "Hello "+x+"!";
		}
		
		public void Project(String y)
		{
			System.out.println(y+" is not happy today!");
		}
	
}