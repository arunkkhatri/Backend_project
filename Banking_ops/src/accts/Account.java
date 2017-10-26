package accts;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import enums.AccType;
import exceptions.AmountOverflowException;
import exceptions.InsufficientBalanceException;

import java.text.SimpleDateFormat;;

public class Account implements Serializable, Comparable<Account> {

	private static final long serialVersionUID = -2031041531171327835L;

	private int accno;
	private String name;
	private double balance;
	private Date dob;
	private AccType accType;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	Random r = new Random();

	public int compareTo(Account o) {

		return accno - o.accno;
	}

	public int getAccno() {
		return accno;
	}

	public String getName() {
		return name;
	}

	public double getBalance() {
		return balance;
	}

	public Date getDob() {
		return dob;
	}

	public AccType getAccType() {
		return accType;
	}

	public Account() {

	}

	public Account(String name, double balance, AccType accType) throws ParseException {
		this.accno = r.nextInt(6000);
		this.name = name;
		this.balance = balance;
		//this.dob = sdf.parse(dob);
		this.accType = accType;
	}

	public String toString() {
		return "Account [accno=" + accno + ", name=" + name + ", balance=" + balance + ", dob=" + sdf.format(dob)
				+", accType=" + accType + "]";
	}

	public void withdraw(double amount) throws InsufficientBalanceException {
		if (balance - amount < 2000)
			throw new InsufficientBalanceException("You should maintain minimum balance of Rs.2000.00/-");

		balance = balance - amount;

		System.out.println("Account No " + accno + " is debited by Rs " + amount + "/-");
		System.out.println("Available Balance :" + balance);
	}

	public void deposit(double amount) throws AmountOverflowException {
		if (amount > 50000)
			throw new AmountOverflowException("You can deposit maximum Rs.50000.00/-");

		balance = balance + amount;

		System.out.println("Account No " + accno + " is credited by Rs " + amount + "/-");
		System.out.println("Available Balance :" + balance);
	}

}