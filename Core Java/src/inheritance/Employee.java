package inheritance;

public class Employee {

	private String name;
	private char sex;
	private String jobTitle;
	private String dateOfBirth;
	public Employee(){
		System.out.println("Employee class default Constructor");	
	}
	public Employee(String name, char sex, String jobTitle, String dateOfBirth){
		setName(name);
		setSex(sex);
		setJobTitle(jobTitle);
		setDateOfBirth(dateOfBirth);
		System.out.println("Employee class Parameterised Constructor");
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public char getSex() {
		return sex;
	}
	public void setSex(char sex) {
		this.sex = sex;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
}
