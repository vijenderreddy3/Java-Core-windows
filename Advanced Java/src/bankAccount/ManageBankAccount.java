package bankAccount;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ManageBankAccount {
	public static final String databaseURL = "jdbc:oracle:thin:@localhost:1521:xe";
	public static final String className = "oracle.jdbc.driver.OracleDriver";
	public static final String username = "vrmali0";
	public static final String password = "FATHERmother5";

	public static final String create_Account = "insert into account values(ACCOUNT_NUMBER.nextval,?,?,?,?)";
	public static final String account_verify="select Ac_name from account where ac_name=?";
	public static final String return_account_no = "select Ac_No from account where Ac_Name=? and Ac_Last_Update=?";
	public static final String delete_Account = "delete account where Ac_No=? and Ac_Name=(?)";
	public static final String balance_check = "select Ac_Amount from Account where Ac_No=?"; 
	public static final String update_amount = "update account set AC_AMOUNT=? where Ac_No=?";
	public static final float initialBalance = 0.0F;

	public static void main(String[] args) {

		try {
			Class.forName(className);
		} catch (ClassNotFoundException ex) {
			System.out.println("Class not found");
		}
		int userInput = 0;
		try {
			Connection conObj = DriverManager.getConnection(databaseURL, username, password);

			PreparedStatement ps_create_Account = conObj.prepareStatement(create_Account);
			PreparedStatement ps_account_verify=conObj.prepareStatement(account_verify);
			PreparedStatement ps_return_account_no = conObj.prepareStatement(return_account_no);
			PreparedStatement ps_delete_Account = conObj.prepareStatement(delete_Account);
			PreparedStatement ps_balance_check = conObj.prepareStatement(balance_check);
			PreparedStatement ps_update_amount = conObj.prepareStatement(update_amount);
			for (int i = 0; i >= 0; i++) {
				System.out.println("1.Create a new Account");
				System.out.println("2.Delete a Account");
				System.out.println("3.Withdraw Amount");
				System.out.println("4.Funds Transfer");
				System.out.println("5. Deposite Amount");
				System.out.println("Enter '0' to exit");
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				try {
					userInput = Integer.parseInt(br.readLine());

				} catch (IOException e) {
					System.out.println("Your choice not entered: IOExcepton");
				}
				conObj.setAutoCommit(false);
				switch (userInput) {
				case 1:
					System.out.println("********************************************");
					System.out.println("Welcome to Bank");
					String name = "";
					try {
						System.out.print("Enter your name to create new account:");
						name = br.readLine();
						ps_account_verify.setString(1, name);
						ResultSet account_verify=ps_account_verify.executeQuery();
						if(account_verify.next()){
							System.out.println("you already have an account with us.");
						}
						else{

							String today_Date_string = jdbc.DateUtility.sdf.format(new Date());
							Date today_Date_util = jdbc.DateUtility.stringToUtilDate(today_Date_string);
							java.sql.Date today_Date_sql = new java.sql.Date(today_Date_util.getTime());
							String timeStamp_string = jdbc.DateUtility.stsf.format(new Date());
							Date timeStamp_util = jdbc.DateUtility.stringToUtilDate(timeStamp_string);
							java.sql.Timestamp timeStamp_sql = new java.sql.Timestamp(timeStamp_util.getTime());

							ps_create_Account.setString(1, name);
							ps_create_Account.setFloat(2, initialBalance);
							ps_create_Account.setDate(3, today_Date_sql);
							ps_create_Account.setTimestamp(4, timeStamp_sql);
							System.out.println("-----------------------------------------------------");
							int row_count = ps_create_Account.executeUpdate();
							if (row_count == 1) {
								ps_return_account_no.setString(1, name);
								ps_return_account_no.setTimestamp(2, timeStamp_sql);
								ResultSet account_No = ps_return_account_no.executeQuery();
								account_No.next();
								System.out.println(
										"Account Created successfully And your account number is: " + account_No.getInt(1));
								account_No.close();
								conObj.commit();

							} else {
								System.out.println("Account not created, Try Agin with correct information");
							}
							System.out.println("-----------------------------------------------------");
							System.out.println("");
							System.out.println("********************************************");


						}
					} catch (IOException e) {
						System.out.println("Failed to read yoour name");
					}
										break;
				case 2:
					System.out.println("********************************************");
					System.out.println("Do delete your account we need some of your Information.");
					System.out.println("Enter your personla Account number:");
					int Account_No = 0;
					String account_name = "";
					try {
						Account_No = Integer.parseInt(br.readLine());
					} catch (IOException e) {
						System.out.println("Failed to read your Account number");
					}
					ps_balance_check.setInt(1, Account_No);
					ResultSet balance = ps_balance_check.executeQuery();
					if (balance.next()) {
						System.out.println("Enter your name as it appear in your account");
						try {
							account_name = br.readLine();
						} catch (IOException e) {
							System.out.println("Failed to read your name");
						}
						ps_delete_Account.setInt(1, Account_No);
						ps_delete_Account.setString(2, account_name);
						System.out.println("-----------------------------------------------------");
						if (balance.getFloat(1) > 0.0F || balance.getFloat(1) < 0.0F) {
							System.out
									.println("It is not possible to delete your bank account.Your account balance is: "
											+ balance.getFloat(1));
						} else {
							int count = ps_delete_Account.executeUpdate();
							if (count == 1) {
								System.out.println("Your Account deleted successfully.");
								conObj.commit();

							} else {
								System.out.println("Failed to delete your account due to technical reason.");
							}
						}
						System.out.println("-----------------------------------------------------");

					} else {
						System.out.println("No account found with the given information, Try Again.");
					}
					balance.close();
					System.out.println("********************************************");

					break;
				case 3:
					System.out.println("********************************************");
					System.out.println("Enter your account number");
					float withdraw_amount = 0.0F;
					Account_No = 0;
					try {
						Account_No = Integer.parseInt(br.readLine());
					} catch (IOException e) {
						System.out.println("Failed to read your Account number.");
					}
					ps_balance_check.setInt(1, Account_No);
					balance = ps_balance_check.executeQuery();
					if (balance.next()) {
						System.out.println("Enter the amount you want to withdraw:");
						try {
							withdraw_amount = Float.parseFloat(br.readLine());
						} catch (IOException e) {
							System.out.println("Failed to read your request.");
						}
						System.out.println("-----------------------------------------------------");
						if (balance.getFloat(1) >= withdraw_amount) {
							float new_balance = balance.getFloat(1) - withdraw_amount;
							ps_update_amount.setFloat(1, new_balance);
							ps_update_amount.setInt(2, Account_No);
							int row_count = ps_update_amount.executeUpdate();

							if (row_count == 1) {
								System.out.println(
										"Amount withdrawn from your bank account .Your account new balance is: "
												+ new_balance);
								conObj.commit();
							} else {
								System.out.println("Failed to withdraw money due to technical reasons.");
							}

						} else {
							System.out.println(
									"Insufficient amount in your account, your account balance is: " + balance.getFloat(1));
						}
						System.out.println("-----------------------------------------------------");

					} else {
						System.out.println("No account found with the given information, Try Again.");
					}
					balance.close();
					System.out.println("********************************************");

					break;
				case 4:
					int your_account_number = 0;
					int another_account_number = 0;
					float transfer_amount = 0.0F;
					System.out.println("********************************************");
					System.out.println("Enter your Account Number:");
					try {
						your_account_number = Integer.parseInt(br.readLine());
					} catch (IOException e) {
						System.out.println("Failed to read your Account number.");
					}
					//System.out.println("Enter Your name as it appear in the account, to verify your account:");
					ps_balance_check.setInt(1, your_account_number);
					ResultSet your_balance = ps_balance_check.executeQuery();
					
					if (your_balance.next()) {
						float your_balance_float=your_balance.getFloat(1);
						System.out.println("Enter another Account Number to which you want to transfer money:");
						try {
							another_account_number = Integer.parseInt(br.readLine());
						} catch (IOException e) {
							System.out.println("Failed to read receiver Account number.");
						}
						ps_balance_check.setInt(1, another_account_number);
						ResultSet another_balance = ps_balance_check.executeQuery();
						
						if (another_balance.next()) {
							System.out.println(
									"Enter the amount you want to transfer to account: " + another_account_number);
							try {
								transfer_amount = Float.parseFloat(br.readLine());
							} catch (IOException e) {
								System.out.println("Failed to read Amount to transfer.");
							}
							System.out.println("-----------------------------------------------------");
							if (your_balance_float >= transfer_amount) {
								System.out.println("if entered");
								float your_new_balance = your_balance_float - transfer_amount;
								ps_update_amount.setFloat(1, your_new_balance);
								ps_update_amount.setInt(2, your_account_number);
								int row_count_your = ps_update_amount.executeUpdate();
								if (row_count_your == 1) {
									float another_new_balance = another_balance.getFloat(1) + transfer_amount;
									ps_update_amount.setFloat(1, another_new_balance);
									ps_update_amount.setInt(2, another_account_number);
									int row_count_another = ps_update_amount.executeUpdate();
									if (row_count_another == 1) {
										conObj.commit();
										System.out.println(
												"Funds transfered from your bank account to :" + another_account_number
														+ ".Your account new balance is: " + your_new_balance);
									} else {
										System.out.println("Due to technical reasons transfer has failed.");
									}
								} else {
									System.out.println("Failed to transfer money due to technical reasons.");
								}
							} else {
								System.out.println(
										"Insufficient amount in your account, Transfer of funds not possible. your balance is: "
												+ your_balance.getFloat(1));
							}
							System.out.println("-----------------------------------------------------");
							another_balance.close();
						} else {
							System.out.println("The account with number: " + another_account_number + " is not found");
						}
					} else {
						System.out.println("We can not find your account with the number:" + your_account_number);
					}
					System.out.println("********************************************");
					your_balance.close();
					break;
				case 5:
					System.out.println("********************************************");
					System.out.println("Enter your account number:");
					your_account_number = 0;
					try {
						your_account_number = Integer.parseInt(br.readLine());
					} catch (IOException e) {
						System.out.println("Unable to get your account details.");
					}
					ps_balance_check.setInt(1, your_account_number);
					balance = ps_balance_check.executeQuery();
					if (balance.next()) {
						System.out.println("Enter the amount you want to deposite to your account:");
						float deposite_amount = 0.0F;
						try {
							deposite_amount = Float.parseFloat(br.readLine());
						} catch (IOException e) {
							System.out.println("Entered wrong amount");
						}
						System.out.println("-----------------------------------------------------");
						float your_new_balance = 0.0F;
						your_new_balance = balance.getFloat(1) + deposite_amount;
						ps_update_amount.setFloat(1, your_new_balance);
						ps_update_amount.setInt(2, your_account_number);
						int row_count = 0;
						row_count = ps_update_amount.executeUpdate();
						if (row_count == 1) {
							conObj.commit();
							System.out.println(
									"Money deposited to your account. Your account balance is: " + your_new_balance);
						} else {
							System.out.println("Money not deposited to your account due to technical reasons.");
						}
						System.out.println("-----------------------------------------------------");

					} else {
						System.out.println("No account found with the account number:" + your_account_number);
					}

					System.out.println("********************************************");
					break;
				case 0:
					System.exit(0);
				
				}
			}
		} catch (

		SQLException e) {
			System.out.println("Database Connection not established" + e.getMessage());
		}
	}
}
