
public interface user_interface 
{
	public void VerifyUserEntry();
	public int VerifyID(String [] username, String [] password, String x_u, String x_p);
	public void List(int x);
	public void print_course_asgt(String courseName);
	public void print_faculty_course(String facultyName);
}
