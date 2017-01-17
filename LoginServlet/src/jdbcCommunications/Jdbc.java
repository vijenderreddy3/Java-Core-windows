package jdbcCommunications;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Jdbc {
	private static final String jdbcUrl="jdbc:oracle:thin:@localhost:1521:xe";
	private static final String jdbcclass="oracle.jdbc.driver.OracleDriver";
	private static final String jdbcUserName="vrmali0";
	private static final String jdbcPassword="FATHERmother5";
	private static final String checkLoginDetails="select * from userdetails where username=? and password=?";
	private static Connection conObj=null;
	private static PreparedStatement psCheckLogin=null;
	public static boolean jdbcConn(){
		boolean isConnection=false;
		try {
			Class.forName(jdbcclass);
			isConnection=false;
		} catch (ClassNotFoundException ex) {
			System.out.println("Class not found");
			isConnection=false;
		}
		try{
			conObj=DriverManager.getConnection(jdbcUrl,jdbcUserName,jdbcPassword);
			isConnection=true;
		}catch(SQLException sqle){
			System.out.println("Connection not established");
		}
		return isConnection;
	}
	public static boolean checkLogin(String username,String password){
		boolean verify=false;
		try{
		psCheckLogin=conObj.prepareStatement(checkLoginDetails);
		psCheckLogin.setString(1, username);
		psCheckLogin.setString(2, password);
		ResultSet loginVerify=psCheckLogin.executeQuery();
		if(loginVerify.next()){
			System.out.println(loginVerify.getString(1));
			
			verify=true;
			loginVerify.close();
		}else{
			verify=false;
		}
		//
		}catch(SQLException sqle){
			System.out.println("Connection not established");
		}
		return verify;
	}
	public void closeConnections(){
		try{
		conObj.close();
		psCheckLogin.close();
		}catch(SQLException sqlex)
		{
			sqlex.printStackTrace();
		}
	}
}
