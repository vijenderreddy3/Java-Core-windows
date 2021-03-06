package carManagement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CarManagementJDBC {
	public static final String className = "oracle.jdbc.driver.OracleDriver";
	public static final String connectionURL = "jdbc:oracle:thin:@localhost:1521:xe";
	public static final String databseUsername = "vrmali0";
	public static final String databasePassword = "FATHERmother5";

	public static final String createQuery = "insert into car_jdbc values(?,?,?,?,?,?)";
	public static final String searchBrand = "select * from car_jdbc where brand=?";
	public static final String searchPriceLess = "select * from car_jdbc where Price<?";
	public static final String searchPriceEqulas = "select * from car_jdbc where Price=?";
	public static final String searchPriceGreater = "select * from car_jdbc where Price>?";
	public static final String searchBrandModel = "select * from car_jdbc where brand=? and model_name=?";
	public static final String searchBrandModeVariant = "select * from car_jdbc where brand=? and model_name=? and variant=?";
	public static final String searchEngineCapacity = "select * from car_jdbc where Engine_Capacity=?";
	public static final String searchFuel = "select * from car_jdbc where Fuel=?";
	public static final String deleteQuery = "delete car_jdbc where Brand=? and model_name=? and variant=?";
	public static PreparedStatement ps = null;

	public static void main(String[] args) {
		try {
			Class.forName(className);
		} catch (ClassNotFoundException ex) {
			System.out.println("Class not found");
		}
		try {
			Connection conObj = DriverManager.getConnection(connectionURL, databseUsername, databasePassword);

			conObj.setAutoCommit(false);
			while (true) {
				System.out.println("1.CREATE");
				System.out.println("2.SEARCH");
				System.out.println("3.DELETE");
				System.out.println("Enter '0' to exit.");
				int choice = 0;
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

				choice = Integer.parseInt(br.readLine());
				switch (choice) {
				case 1:
					System.out.println("********************************************");
					System.out.println("Enter car brand which you want to create:");
					String brand_name = br.readLine();
					System.out.println("Enter model of the car:");
					String model_name = br.readLine();
					System.out.println("Enter variant:");
					String variant = br.readLine();
					ps = conObj.prepareStatement(searchBrandModeVariant);
					ps.setString(1, brand_name);
					ps.setString(2, model_name);
					ps.setString(3, variant);
					ResultSet rs = ps.executeQuery();
					String yesOrNo = "";
					while (rs.next()) {
						System.out.println(
								"There is a record found for :" + brand_name + "-->" + model_name + "-->" + variant);
						System.out.println("Do you want to continue: Yes/No.");
						yesOrNo = br.readLine();
					}
					if (yesOrNo.equals("yes") || yesOrNo.equals("YES") || yesOrNo.equals("Yes")) {
						System.out.println("Enter fuel type petrol or Diesel for :" + brand_name + "-->" + model_name
								+ "-->" + variant + ":");
						String fuel = br.readLine();
						if (fuel.equals("petrol") || fuel.equals("Petrol") || fuel.equals("PETROL")
								|| fuel.equals("diesel") || fuel.equals("Diesel") || fuel.equals("DIESEL")) {
							System.out.println("Enter car price:");
							float price = Float.parseFloat(br.readLine());
							System.out.println("Enter Engine capacity:");
							int capacity = Integer.parseInt(br.readLine());
							ps = conObj.prepareStatement(createQuery);
							ps.setString(1, brand_name);
							ps.setString(2, model_name);
							ps.setString(3, variant);
							ps.setString(4, fuel);
							ps.setFloat(5, price);
							ps.setInt(6, capacity);
							int inserted_records = ps.executeUpdate();
							System.out.println("-----------------------------------------");
							if (inserted_records == 1) {
								System.out.println("Your car details entered succesfully.");
								conObj.commit();
							} else {
								System.out.println("Failed to enter your new car details.");
							}
							System.out.println("-----------------------------------------");
						} else {
							System.out.println("you entered wrong fuel type.");
						}
					} else {
						System.out.println("New car details not entered");
					}
					break;
				case 2:
					break;
				case 3:
					System.out.println("This is case 3");
					break;

				case 0:
					System.exit(0);
				}
			}
		} catch (SQLException sqle) {
			System.out.println("Connection not established");
		} catch (IOException ioe) {
			System.out.println("Your input not red.");
		}

	}

}
