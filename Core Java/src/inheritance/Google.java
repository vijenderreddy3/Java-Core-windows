package inheritance;

public class Google extends Employee {
	private String organization;
	private int count=0;
	public Google(){
		setCount();
		System.out.println("Google default constructor");
	}
	public Google(String name, char sex, String jobTitle, String dateOfBirth, String organization){
		super(name,sex,jobTitle,dateOfBirth);
		setCount();
		setOrganization(organization);
		System.out.println("Google Parameterised Constructor");
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
