package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class JdbcConnection {

	public static final String databaseURL = "jdbc:oracle:thin:@localhost:1521:xe";
	public static final String className = "oracle.jdbc.driver.OracleDriver";
	public static final String username = "vrmali0";
	public static final String password = "FATHERmother5";
	public static final String select_stmt="select EMP_DOB from EMP where EMP_lname=?";
	public static final String insert_stmt="insert into EMP values(?,?,?,?,?,?,?,?,?,?)";

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			Class.forName(className);
		} catch (ClassNotFoundException ex) {
			System.out.println("Class not found");
		}
		try {
			Connection conObj= DriverManager.getConnection(databaseURL, username, password);
			System.out.println("Connection scuccesful");
			Statement query =conObj.createStatement();
			PreparedStatement ps=conObj.prepareStatement(insert_stmt);
			PreparedStatement ps_select_stmt=conObj.prepareStatement(select_stmt);
			ps.setInt(1, 120);
			ps.setString(2, "Mr.");
			ps.setString(3, "MALI");
			ps.setString(4, "Vijender");
			ps.setString(5, "R");
			//ps.setDate(6,);
			Date dateofBirth=DateUtility.stringToUtilDate("04-10-1988");
			java.sql.Date dOB=new java.sql.Date(dateofBirth.getTime()); 
			ps.setDate(6, dOB);
			java.sql.Date dOJ=new java.sql.Date(DateUtility.stringToUtilDate("10-20-2016").getTime());
			ps.setDate(7,dOJ);
			ps.setString(8, "532");
			ps.setString(9, "6-2459");
			ps.setInt(10, 100);
			//119,'Mr.','Mali','Vijender','R','10-APR-88','20-OCT-2016','059','466-2459',110)
			ResultSet rs=query.executeQuery("select * from EMP");
			while(rs.next()){
				System.out.println(rs.getInt(1)+"   "+rs.getString(2)+"   "+rs.getString(3)+"    "+rs.getString(4)+"    "+rs.getString(5)+"    "+rs.getDate(6)+"    "+rs.getDate(7)+"    "+rs.getString(8)+"    "+rs.getString(9)+"    "+rs.getInt(10) );
				}
			rs.close();
			Date timeStamp=new Date();
			System.out.println(timeStamp);
			String timeStampString=DateUtility.stsf.format(timeStamp);
			System.out.println("Time Stamp String:"+timeStampString);
			Date timeStampUtil=DateUtility.stringToUtilTimeStamp(timeStampString);
			System.out.println("timestamp Util:"+timeStampUtil);
			java.sql.Date sqldatetest=new java.sql.Date(timeStampUtil.getTime());
			java.sql.Timestamp st=new java.sql.Timestamp(timeStampUtil.getTime());
			System.out.println("tiemstamp sql:"+st);
			System.out.println();
			String name="Mali";
			ps_select_stmt.setString(1,name);
			rs=ps_select_stmt.executeQuery();
			while(rs.next()){
				System.out.println(rs.getDate(1));
				}
			while(rs.next()){
			System.out.println(rs.getInt(1)+"   "+rs.getString(2)+"   "+rs.getString(3)+"    "+rs.getString(4)+"    "+rs.getString(5)+"    "+rs.getDate(6)+"    "+rs.getDate(7)+"    "+rs.getString(8)+"    "+rs.getString(9)+"    "+rs.getInt(10) );
			}
			//int noOfUpdatedRows=ps.executeUpdate();
			//System.out.println("Number of rows inserted"+noOfUpdatedRows);
			
		} catch (SQLException sqlex) {
			System.out.println("Database Connection not established"+sqlex.getMessage());
			
		}
		
	}
}
