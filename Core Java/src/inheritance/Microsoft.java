package inheritance;

public class Microsoft extends Employee{
	private String organization;
	private int count=0;
	public Microsoft(){
		setCount();
		System.out.println("Microsoft default constructor");
	}
	public Microsoft(String name, char sex, String jobTitle, String dateOfBirth, String organization){
		super(name,sex,jobTitle,dateOfBirth);
		setCount();
		setOrganization(organization);
		System.out.println("Microsoft Parameterised Constructor");
	}
	public String getOrganization() {
		return this.organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public int getCount(){
		return count;
	}
	public void setCount() {
		count++;
	}
	
}
