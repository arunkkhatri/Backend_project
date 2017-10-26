package test_accts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.JOptionPane;

import accts.Account;
import enums.AccType;
import exceptions.AmountOverflowException;
import exceptions.InsufficientBalanceException;

public class TestAccount {
	Map<Integer, Account> map;

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
	}

	public void addAccount(Account account) {
		map.put(account.getAccno(), account);
		System.out.println("Cheers, Account added successfully!");
	}

	public void getAccountDetails(int accno) {

		if (map.containsKey(accno))
			System.out.println(map.get(accno));
		else
			System.out.println("Account doesn't exist..");
	}

	public void removeAccount(int accno) {

		if (map.containsKey(accno)) {
			map.remove(accno);
			System.out.println("Account " + accno + " removed..");
		} else
			System.out.println("Account doesn't exist.. ) ");
	}

	public void withdraw(int accno, double amount) throws InsufficientBalanceException {

		if (map.containsKey(accno)) {
			Account account = map.get(accno);
			account.withdraw(amount);

			map.put(accno, account);
		} else
			System.out.println("Account doesn't exist.. ) ");
	}

	public void deposit(int accno, double amount) throws AmountOverflowException {

		if (map.containsKey(accno)) {
			Account account = map.get(accno);
			account.deposit(amount);

			map.put(accno, account);
		} else
			System.out.println("Account doesn't exist.. ) ");
	}

	public void transferFund(int source, int dest, double amount)
			throws InsufficientBalanceException, AmountOverflowException {

		if (map.containsKey(source) && map.containsKey(dest)) {
			withdraw(source, amount);
			deposit(dest, amount);
			System.out.println("Amount Rs." + amount + "/- transferred from source " + source + " to dest " + dest
					+ " successfully");
		} else
			System.out.println("Wrong Account Number...");
	}

	public void getAllAccounts() {
		System.out.println("All Account Details");
		System.out.println("=============================================================================");

		for (Account account : map.values())
			System.out.println(account);
	}

	public void sortAccountsByAccno() {
		System.out.println("All Account Details by Accno");
		System.out.println("=============================================================================");

		TreeMap<Integer, Account> tm = new TreeMap<>(map);

		for (Account account : tm.values())
			System.out.println(account);
	}

	public void sortAccountsByName() {
		System.out.println("All Account Details by Name");
		System.out.println("=============================================================================");

		ArrayList<Account> accounts = new ArrayList<>(map.values());
		Collections.sort(accounts, (a, b) -> a.getName().compareTo(b.getName()));

		for (Account account : accounts)
			System.out.println(account);
	}

	public void sortAccountsByDob() {
		System.out.println("All Account Details by Dob");
		System.out.println("=============================================================================");

		ArrayList<Account> accounts = new ArrayList<>(map.values());
		Collections.sort(accounts, (a, b) -> a.getDob().compareTo(b.getDob()));

		for (Account account : accounts)
			System.out.println(account);
	}

	public void sortAccountsByBalance() {
		System.out.println("All Account Details by Balance");
		System.out.println("=============================================================================");

		ArrayList<Account> accounts = new ArrayList<>(map.values());
		Collections.sort(accounts, (a, b) -> (int) (a.getBalance() - b.getBalance()));

		for (Account account : accounts)
			System.out.println(account);
	}

	public void save() throws IOException {
		FileOutputStream fos = new FileOutputStream("accounts.ser");
		ObjectOutputStream oos = new ObjectOutputStream(fos);

		oos.writeObject(map);
		oos.close();
		System.out.println(map.size() + " accounts sorted in a file");
	}

	public static void main(String args[]) {
		Scanner sc = null;
		TestAccount ta = null;
		int choice = 0;

		try {

			sc = new Scanner(System.in);
			ta = new TestAccount();

			while (choice < 14) {
				System.out.println("Banking Operations");
				System.out.println("==================");
				System.out.println("1. Add Account Details");
				System.out.println("2. Get Account Details");
				System.out.println("3. Remover Account");
				System.out.println("4. Withdraw");
				System.out.println("5. Deposit");
				System.out.println("6. Transfer Funds");
				System.out.println("7. Get all Accounts");
				System.out.println("8. Sort the accounts by accno");
				System.out.println("9. Sort the accounts by Name");
				System.out.println("10. Sort the accoutns by Balance");
				System.out.println("11. Sort the accoutns by Dob");
				System.out.println("12. Get min balance account");
				System.out.println("13. Get min balance account");
				System.out.println("14. Exit");
				System.out.println("Enter your choice");
				choice = sc.nextInt();

				switch (choice) {
				case 1:
					Scanner s1 = new Scanner(JOptionPane.showInputDialog("Enter name,balance,dob(dd/MM/YYYY),SAVINGS/CURRENT"));
					Account account = new Account(s1.next(),s1.nextDouble(),s1.next(),AccType.valueOf(s1.next()));
					ta.addAccount(account);
					ta.save();
					break;
				case 2:
					Scanner s2 = new Scanner(JOptionPane.showInputDialog("Enter Accno"));
					ta.getAccountDetails(s2.nextInt());
					break;

				case 3:
					Scanner s3 = new Scanner(JOptionPane.showInputDialog("Enter Accno"));
					ta.removeAccount(s3.nextInt());
					break;
					
				case 4:
					Scanner s4 = new Scanner(JOptionPane.showInputDialog("Enter Accno & Amount"));
					ta.withdraw(s4.nextInt(),s4.nextDouble());
					break;
					
				case 5:
					Scanner s5 = new Scanner(JOptionPane.showInputDialog("Enter Accno & Amount"));
					ta.withdraw(s5.nextInt(),s5.nextDouble());
					break;
					
				case 6:
					Scanner s6 = new Scanner(JOptionPane.showInputDialog("Enter Source Accno, dest accno & Amount"));
					ta.transferFund(s6.nextInt(),s6.nextInt(),s6.nextDouble());
					break;
					
				case 7:
					ta.getAllAccounts();
					break;
					
				case 8:
					ta.sortAccountsByAccno();
					break;

				case 9:
					ta.sortAccountsByName();
					break;
					
				case 10:
					ta.sortAccountsByBalance();
					break;
					
				case 11:
					ta.sortAccountsByDob();
					break;
					
				case 12:
					ta.getMinAccountBalance();
					break;
					
				case 13:
					ta.getMaxAccountBalance();
					break;
					
				default:
					ta.save();
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getMaxAccountBalance() {
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
		
	}
	
}