package jdbc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtility {
	
	public static String dateFormat="mm-dd-yyyy";
	public static SimpleDateFormat sdf=new SimpleDateFormat(dateFormat); 
	public static String timeStampFormat="MM-dd-yyyy hh:mm:ss";
	public static SimpleDateFormat stsf=new SimpleDateFormat(timeStampFormat);
	
	public static java.util.Date stringToUtilDate(String stringDate){
		Date dateObj=null;
		try{
		dateObj=sdf.parse(stringDate);
		}catch(ParseException pe){
			System.out.println("Enter the date in following format"+dateFormat);
		}
		return dateObj;
	}
	public static java.util.Date stringToUtilTimeStamp(String stringTimeStamp){
		Date timeStampObj=null;
		try{
		timeStampObj=stsf.parse(stringTimeStamp);
		}catch(ParseException pe){
			System.out.println("Enter the date in following format"+dateFormat);
		}
		return timeStampObj;
	}
	
}
