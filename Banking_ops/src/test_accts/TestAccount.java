package test_accts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;
import java.sql.*;

import javax.swing.JOptionPane;

import Bank.database;
import accts.Account;
import enums.AccType;
import exceptions.AmountOverflowException;
import exceptions.InsufficientBalanceException;

public class TestAccount {
	/*Map<Integer, Account> map;

	public TestAccount() throws IOException, ClassNotFoundException {

		File file = new File("accounts.ser");

		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			map = (Map<Integer, Account>) ois.readObject();
			ois.close();
			System.out.println("Its an existing Map :" + map.size());
		} else {
			map = new HashMap<>();
			System.out.println("Its a New Map :" + map.size());
		}
	}*/
	
/*	private static void createTable() throws SQLException {
		Connection con= database.getConnection();
		String sqlCreate = "CREATE TABLE IF NOT EXISTS " + "bank.accounts"
				+ "  (   accno            INTEGER,"
				+ "name           VARCHAR(20),"
				+ "   balance          INTEGER,"
				+ "   accType VARCHAR(20))";

		Statement stmt = con.createStatement();
		stmt.execute(sqlCreate);
	}
	*/
	
	
	public void addAccount(int accno,Account account) {
		Connection con= null;
		try {
			con= database.getConnection();
			//Class.forName("com.mysql.jdbc.Driver");
			//con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","akumar15");
			Statement statement = con.createStatement();
			statement.executeUpdate("insert into accounts values("+accno+",'"+account.getName()+"','"+account.getBalance()+"','"+account.getAccType().toString()+"');");
			System.out.println("Cheers, Account added successfully!");
			con.commit();
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}

	/*public void addAccount(Account account) {
		map.put(account.getAccno(), account);
		System.out.println("Cheers, Account added successfully!");
	}*/
	
	
	public void getAccountDetails(int accno) {
		Connection con =null;
		try {
			con = database.getConnection();
			//Class.forName("com.mysql.jdbc.Driver");
			//con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","akumar15");
			Statement statement = con.createStatement();
			
			ResultSet rs = statement.executeQuery("select * from accounts where accno="+accno+"");
			if(rs.next())
			{
				System.out.println("[accno:"+rs.getInt("accno")+";name:"+rs.getString("name")+";balance:"+rs.getInt("balance")+";accType:"+rs.getString("accType")+"]");
			}
			else
			{
				System.out.println("Account doesn't exists...");
			}
			con.commit();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/*public void getAccountDetails(int accno) {

		if (map.containsKey(accno))
			System.out.println(map.get(accno));
		else
			System.out.println("Account doesn't exist..");
	}*/
	
	
	public void removeAccount(int accno){
		Connection con = null;
		try {
			con = database.getConnection();
			//Class.forName("com.mysql.jdbc.Driver");
			//con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","akumar15");
			Statement statement = con.createStatement();
			
			statement.executeUpdate("delete from accounts where accno="+accno+"");
			
			System.out.println("Account " + accno + " removed..");
			con.commit();
			con.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}

/*	public void removeAccount(int accno) {

		if (map.containsKey(accno)) {
			map.remove(accno);
			System.out.println("Account " + accno + " removed..");
		} else
			System.out.println("Account doesn't exist.. ) ");
	}
*/
	
	
	public void withdraw(int accno, double amount){
		double balance =0;
		//Connection con = null;
		
		try {
			Connection con = database.getConnection();
			//Class.forName("com.mysql.jdbc.Driver");
			//con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","akumar15");
			Statement mystate = con.createStatement();
			ResultSet rs  = mystate.executeQuery("select balance from accounts where accno="+accno+"");
			//balance = rs.getDouble("balance");
			if(rs.next()){
				balance = rs.getInt("balance");
			}
			//System.out.println(accno);
			//System.out.println(amount);
			//System.out.println(balance);
			//System.out.println(balance);
			if((balance-amount)>2000){
				Statement statement = con.createStatement();
				statement.executeUpdate("update accounts set balance= "+(balance-amount)+" where accno="+accno+"");
				System.out.println("Account "+accno+" have been debited by "+amount+"");
			}
			else
			{
				System.out.println("Insufficient Balance");
			}
			con.commit();
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}
	/*public void withdraw(int accno, double amount) throws InsufficientBalanceException {

		if (map.containsKey(accno)) {
			Account account = map.get(accno);
			account.withdraw(amount);

			map.put(accno, account);
		} else
			System.out.println("Account doesn't exist.. ) ");
	}*/
	
	
	public void deposit(int accno, double amount) throws AmountOverflowException {
		double balance =0;
		Connection con = null;
		
		try {
			con = database.getConnection();
			//Class.forName("com.mysql.jdbc.Driver");
			//con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","akumar15");
			Statement mystate = con.createStatement();
			ResultSet rs = mystate.executeQuery("select * from accounts where accno="+accno+";");
			//balance = rs.getDouble("balance");
			if(rs.next()){
				balance = rs.getInt("balance");
			}
			if((balance+amount)<50000){
				Statement statement = con.createStatement();
				statement.executeUpdate("update accounts set balance= "+(balance+amount)+" where accno="+accno+"");
				System.out.println("Account "+accno+" have been credited by "+amount+"");
			}
			else
			{
				System.out.println("Amount limit exceeded for account");
			}
			con.commit();
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}

	/*public void deposit(int accno, double amount) throws AmountOverflowException {

		if (map.containsKey(accno)) {
			Account account = map.get(accno);
			account.deposit(amount);

			map.put(accno, account);
		} else
			System.out.println("Account doesn't exist.. ) ");
	}*/
	
	
	public void transferFund(int source, int dest, double amount)
			throws InsufficientBalanceException, AmountOverflowException{
		double balanceSource=0;
		double balanceDest=0;
		Connection con = null;
		
		try {
			con = database.getConnection();
			//Class.forName("com.mysql.jdbc.Driver");
			//con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","akumar15");
			Statement mystate = con.createStatement();
			ResultSet rs = mystate.executeQuery("select balance from accounts where accno="+source+";");
			if(rs.next()){
				balanceSource = rs.getInt("balance");
			}
			//balanceSource = rs.getInt("balance");
			rs = mystate.executeQuery("select balance from accounts where accno="+dest+";");
			if(rs.next()){
				balanceDest = rs.getInt("balance");
			}
			//balanceDest = rs.getInt("balance");
			if((balanceSource-amount)>2000 && balanceDest+amount < 50000){
				mystate.executeUpdate("update accounts set balance= "+(balanceSource -amount)+" where accno="+source+"");
				mystate.executeUpdate("update accounts set balance= "+(balanceDest+amount)+" where accno="+dest+"");
				System.out.println("Account "+source+" have been debited by "+amount+"");
				System.out.println("Account "+dest+" have been credited by "+amount+"");
			}
			con.commit();
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}

	/*public void transferFund(int source, int dest, double amount)
			throws InsufficientBalanceException, AmountOverflowException {

		if (map.containsKey(source) && map.containsKey(dest)) {
			withdraw(source, amount);
			deposit(dest, amount);
			System.out.println("Amount Rs." + amount + "/- transferred from source " + source + " to dest " + dest
					+ " successfully");
		} else
			System.out.println("Wrong Account Number...");
	}*/
	
	
	public void getAllAccounts() {
		Connection con = null;
		try {
			
		con = database.getConnection();
		/*Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","akumar15");*/
		System.out.println("All Account Details:");
		System.out.println("==============================================================");
		Statement mystmt = con.createStatement(); 
		ResultSet rs = mystmt.executeQuery("select * from accounts");
		while(rs.next())
		{
			System.out.println("[accno:"+rs.getInt("accno")+";name:"+rs.getString("name")+";balance:"+rs.getInt("balance")+";accType:"+rs.getString("accType")+"]");
		}
		con.commit();
		con.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}

	/*public void getAllAccounts() {
		System.out.println("All Account Details");
		System.out.println("=============================================================================");

		for (Account account : map.values())
			System.out.println(account);
	}

	
*/
	
/*public static void vikas() {
		
		Connection con=null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","akumar15");
			Statement mystmt = con.createStatement();
			ResultSet rs = mystmt.executeQuery("select * from accounts");
			
			while(rs.next()){
				System.out.println(rs.getLong("accno"));
				System.out.println(rs.getString("name"));
			}
		}
		catch(Exception e){
			System.out.println("No connection established...");
		}
	}*/
	public static void main(String args[]) {
		Scanner sc = null;
		TestAccount ta = null;
		int choice = 0;

		try {

			sc = new Scanner(System.in);
			ta = new TestAccount();

			while (choice < 8) {
				System.out.println("Banking Operations");
				System.out.println("==================");
				System.out.println("1. Add Account Details");
				System.out.println("2. Get Account Details");
				System.out.println("3. Remove Account");
				System.out.println("4. Withdraw");
				System.out.println("5. Deposit");
				System.out.println("6. Transfer Funds");
				System.out.println("7. Get all Accounts");
				System.out.println("8. Exit");
				System.out.println("Enter your choice");
				choice = sc.nextInt();

				switch (choice) {
				case 1:
					Scanner s1 = new Scanner(JOptionPane.showInputDialog("Enter name,balance,SAVINGS/CURRENT"));
					Account account = new Account(s1.next(),s1.nextDouble(),AccType.valueOf(s1.next()));
					Random r = new Random();
					int accno = r.nextInt(100)+1;
					ta.addAccount(accno, account);
					break;
				case 2:
					Scanner s2 = new Scanner(JOptionPane.showInputDialog("Enter Account number"));
					//System.out.println(s2.next());
					accno= s2.nextInt();
					ta.getAccountDetails(accno);
					
					break;

				case 3:
					Scanner s3 = new Scanner(JOptionPane.showInputDialog("Enter Accno"));
					//System.out.println(s3.nextInt());
					accno = s3.nextInt();
					ta.removeAccount(accno);
					break;
					
				case 4:
					Scanner s4 = new Scanner(JOptionPane.showInputDialog("Enter Accno & Amount"));
					accno = s4.nextInt();
					double amount = s4.nextDouble();
					ta.withdraw(accno, amount);
					break;
					
				case 5:
					Scanner s5 = new Scanner(JOptionPane.showInputDialog("Enter Accno & Amount"));
					ta.deposit(s5.nextInt(),s5.nextDouble());
					break;
					
				case 6:
					Scanner s6 = new Scanner(JOptionPane.showInputDialog("Enter Source Accno, dest accno & Amount"));
					ta.transferFund(s6.nextInt(),s6.nextInt(),s6.nextDouble());
					break;
					
				case 7:
					ta.getAllAccounts();
					break;

				default:
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*public void getMaxAccountBalance() {
		System.out.println("Account Details with Maximum Balance");
		System.out.println("=============================================================================");

		ArrayList<Account> accounts = new ArrayList<>(map.values());
		System.out.println(Collections.max(map.values() , (a,b) -> (int)(a.getBalance() - b.getBalance())));
		
	}

	public void getMinAccountBalance() {
		
		System.out.println("Account Details with Minimum Balance");
		System.out.println("=============================================================================");

		ArrayList<Account> accounts = new ArrayList<>(map.values());
		System.out.println(Collections.min(map.values() , (a,b) -> (int)(a.getBalance() - b.getBalance())));
		
	}*/
	
}