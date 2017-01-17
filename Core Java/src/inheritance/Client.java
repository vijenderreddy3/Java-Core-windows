package inheritance;

public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Google g=new Google("vijay",'M',"Java Developer","04-10-1988","Google");
		Microsoft m=new Microsoft("Babu",'M',"Senior Java Engineer","06-01-1980","Microsoft");
		System.out.println("Google details:");
		System.out.println("name : "+g.getName()+", sex: "+g.getSex()+", jobtitle: "+g.getJobTitle()+", Date of Birth: "+g.getDateOfBirth()+", Organization: "+g.getOrganization());
		System.out.println("Microsoft details:");
		System.out.println("name : "+m.getName()+", sex: "+m.getSex()+", jobtitle: "+m.getJobTitle()+", Date of Birth: "+m.getDateOfBirth()+", Organization: "+m.getOrganization());
	}
}
