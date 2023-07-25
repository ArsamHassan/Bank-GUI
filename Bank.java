//Muhammad Arsam Hassan
//banking application
package application;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class Bank{
	Scanner input = new Scanner(System.in);
	
	//instance variables
	private String userName;
	private String password;
	private double bankBalance;
	private double chequing;
	private double savings;
	private String phoneNumber;
	
	//creates a new instance of the Bank class. each object is a new user account
	public static void createAccount(Bank person, String accountUsername, String password, Double chequing, Double savings, String accountPhoneNumber) throws IOException
	{
		//accesses the accountInfo and bankbalance file to be able to write to it
		FileWriter output1 = new FileWriter("accountInfo.txt", true);
		FileWriter output2 = new FileWriter("bankBalance.txt", true);
			
		person.setUserName(accountUsername);
		output1.write(person.getUserName() + " ");
		
		person.setPassword(password);
		output1.write(person.getPassword() + " ");
		
		person.setChequing(chequing);
		output2.write(String.valueOf(person.getChequing()) + " ");
		
		person.setSavings(savings);
		output2.write(String.valueOf(person.getSavings()) + " ");
		
		person.setBankBalance(person.getChequing() + person.getSavings());
		output2.write(String.valueOf(person.getChequing() + person.getSavings()) + "\n");
		
		person.setPhoneNumber(accountPhoneNumber);
		output1.write(person.getPhoneNumber() + "\n");
		
		output1.close();
		output2.close();
	}
	
	//allows user to deposit money into their chequing or savings account
	public void depositMoney(double deposit, RadioButton value, Label depositSuccess) throws IOException
	{
		FileWriter outputH = new FileWriter("history.txt", true);
		//if user wants to deposit to chequing
		if(value.getText().equals("Chequing"))
		{
			outputH.write("[" + userName + "] :");
			chequing += deposit;
			bankBalance += deposit;
			depositSuccess.setVisible(true);
			outputH.write("$" + deposit + " dollars has been deposited to Chequing\n");
		}
		//if user wants to deposit to savings
		else
		{
			outputH.write("[" + userName + "] :");
			savings += deposit;
			bankBalance += deposit;
			depositSuccess.setVisible(true);
			outputH.write("$" + deposit + " dollars has been deposited to Savings\n");
		}
		outputH.close();
	}
	
	
	//allows user to transfer money between their chequing/savings or to an external email
	public void transferMoney(TextField transferMoneyEmail, Double transfer, RadioButton value, ComboBox<String> value2, Label lackFunds, Label enoughFunds) throws IOException
	{
		FileWriter outputH = new FileWriter("history.txt", true);
		//if user wants to transfer from chequing account
		if(value.getText().equals("Chequing"))
		{
			if(verifyFunds(chequing, transfer))
			{
				outputH.write("[" + userName + "] :");
				//if user wants to transfer to savings
				if(value2.getValue().equals("Savings"))
				{
					lackFunds.setVisible(false);
					enoughFunds.setVisible(true);
					savings += transfer;
					chequing -= transfer;
					outputH.write("$" + transfer + " dollars has been transferred to Savings from Chequing\n");
				}
				//if user wants to transfer to external email
				else if(value2.getValue().equals("External Account"))
				{
					enoughFunds.setVisible(true);
					lackFunds.setVisible(false);
					chequing -= transfer;
					bankBalance -= transfer;
					outputH.write("$" + transfer + " dollars has been transferred to " + transferMoneyEmail.getText() + " from Chequing\n");
				}
			}
			else
			{
				enoughFunds.setVisible(false);
				lackFunds.setVisible(true);
			}
		}
		else
		{
			if(verifyFunds(savings, transfer))
			{
				outputH.write("[" + userName + "] :");
				//if user wants to transfer to savings
				if(value2.getValue().equals("Chequing"))
				{
					lackFunds.setVisible(false);
					enoughFunds.setVisible(true);
					chequing += transfer;
					savings -= transfer;
					outputH.write("$" + transfer + " dollars has been transferred to Chequing from Savings\n");
				}
				//if user wants to transfer to external email
				else if(value2.getValue().equals("External Account"))
				{
					enoughFunds.setVisible(true);
					lackFunds.setVisible(false);
					savings -= transfer;
					bankBalance -= transfer;
					outputH.write("$" + transfer + " dollars has been transferred to " + transferMoneyEmail.getText() + " from Savings\n");
				}
			}
			else
			{
				enoughFunds.setVisible(false);
				lackFunds.setVisible(true);
			}
		}
		outputH.close();
	}
	
	//allows user to make a payment to an external email
	public void makePayment(TextField paymentEmail, Double payAmount, RadioButton value, Label lackFunds, Label enoughFunds) throws IOException
	{
		FileWriter outputH = new FileWriter("history.txt", true);
		if(value.getText().equals("Chequing"))
		{
			if(verifyFunds(chequing, payAmount))
			{
				outputH.write("[" + userName + "] :");
				enoughFunds.setVisible(true);
				lackFunds.setVisible(false);
				chequing -= payAmount;
				bankBalance -= payAmount;
				outputH.write("$" + payAmount + " dollars has been sent to " + paymentEmail.getText() + " from Chequing\n");
			}
			else
			{
				enoughFunds.setVisible(false);
				lackFunds.setVisible(true);
			}			
		}
		else
		{
			if(verifyFunds(savings, payAmount))
			{
				outputH.write("[" + userName + "] :");
				enoughFunds.setVisible(true);
				lackFunds.setVisible(false);
				savings -= payAmount;
				bankBalance -= payAmount;outputH.write("$" + payAmount + " has been sent to " + paymentEmail.getText() + " from Savings\n");
			}
			else
			{
				enoughFunds.setVisible(false);
				lackFunds.setVisible(true);
				System.out.println("Not enough funds!");
			}
		}
		outputH.close();
	}
	
	//this method returns a true/false statement depending on whether the username and password are correct in the login
	public static boolean verifyLogin(ArrayList<Bank> accounts, String user, String pass) throws FileNotFoundException
	{
		boolean exists = false;
		//iterates through for loop to find if the username and password exist and are correct
		for(int i = 0; i < accounts.size(); i++)
		{

			String username = accounts.get(i).getUserName();
			String password = accounts.get(i).getPassword();

			if (!user.equals(username) || !pass.equals(password))
			{
				exists = false;
			}
			else
			{
				return true;
			}	
		}
		return exists;
	}

	//method to verify if enough funds exist to complete a transfer or payment
	public boolean verifyFunds(double totalFunds, double transferFunds)
	{
		if((totalFunds - transferFunds) < 0)
		{
			return false;
		}
		return true;
	}
	
	//checks for duplicate accounts. used for when a new account is being created.
	public static boolean duplicateAccount(ArrayList<Bank> accounts, String username, String phoneNumber)
	{
		boolean dup = false;
		System.out.println(username);
		for(int i = 0; i < accounts.size(); i++)
		{
			String accountName = accounts.get(i).getUserName();
			if(username.equals(accountName))
			{
				return true;
			}
			else
			{
				String accountPN = accounts.get(i).getPhoneNumber();
				if(phoneNumber.equals(accountPN))
				{
					return true;
				}
				dup = false;
			}
		}
		return dup;
	}
	
	//method used to edit an account. change the username or password of an already existing account
	public boolean editAccount(ArrayList<Bank> accounts, int userChoice, TextField newInput, Label alrExist, Label successChange)
	{
		//if user wants to change the username
		if(userChoice == 1)
		{
			String newUser = newInput.getText();
			boolean alreadyExists = false;
			//checks to see if the new username already exists in database
			for(int i = 0; i < accounts.size(); i++)
			{
				if(newUser.equals(accounts.get(i).getUserName()))
				{
					alreadyExists = true;
				}
			}
			//if new username already exists, the username edit is not completed
			if(alreadyExists)
			{
				alrExist.setVisible(true);
				successChange.setVisible(false);
				return false;
			}
			//if new username does not already exist, the username is changed
			else
			{
				this.setUserName(newUser);
				successChange.setVisible(true);
				alrExist.setVisible(false);
				return true;
			}
		}
		//if user wants to change password
		else if(userChoice == 2)
		{
			String newPass = newInput.getText();
			boolean alreadyExists = false;
			//checks to see if the new password is the same as the current password
			if(newPass.equals(this.getPassword()))
			{
				alreadyExists = true;
			}
			//if new password is the same as current password, the password change is not completed
			if(alreadyExists)
			{
				alrExist.setVisible(true);
				successChange.setVisible(false);
				return false;
			}
			//if new password is different, the password is changed
			else
			{
				this.setPassword(newPass);
				successChange.setVisible(true);
				alrExist.setVisible(false);
				return true;
			}
		}
		else
		{
			String newPN = newInput.getText();
			boolean alreadyExists = false;
			//checks to see if the new phone number already exists in database
			for(int i = 0; i < accounts.size(); i++)
			{
				if(newPN.equals(accounts.get(i).getPhoneNumber()))
				{
					alreadyExists = true;
				}
			}
			//if new phone number already exists, the phone number edit is not completed
			if(alreadyExists)
			{
				alrExist.setVisible(true);
				successChange.setVisible(false);
				return false;
			}
			//if new phone number does not already exist, the phone number is changed
			else
			{
				this.setPhoneNumber(newPN);
				alrExist.setVisible(false);
				successChange.setVisible(true);
				return true;
			}
		}
	}
	
	
	//terminates an account by removing it from the database
	public boolean deleteAccount(ArrayList<Bank> accounts, RadioButton confirm, TextField username1, TextField password1, Label terminateSuccessful, Label terminateUnsuccessful) throws IOException
	{
		Scanner inputH = new Scanner(new File("history.txt"));
		//when user confirms to delete account
		if(confirm.isSelected())
		{
			String username = username1.getText();
			String password = password1.getText();


			//if username and password are also correct
			if(verifyLogin(accounts, username, password))
			{
				int index = 0;
				//finds the specific account that is associated with the username from the arraylist
				for(int i = 0; i < accounts.size(); i++)
				{
					if (userName.equals(accounts.get(i).getUserName()))
					{
						index = i;
					}
				}
				//removes the account
				accounts.remove(index);
				terminateUnsuccessful.setVisible(false);
				terminateSuccessful.setVisible(true);
				inputH.close();
				return true;
			}	
			else
			{
				terminateUnsuccessful.setVisible(true);
				terminateSuccessful.setVisible(false);
				inputH.close();
				return false;
			}
		}
		inputH.close();
		return false;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public double getBankBalance() throws FileNotFoundException {
		return bankBalance;
	}

	public void setBankBalance(double bankBalance) {
		this.bankBalance = bankBalance;
	}

	public double getChequing() {
		return chequing;
	}

	public void setChequing(double chequing) {
		this.chequing = chequing;
	}

	public double getSavings() {
		return savings;
	}

	public void setSavings(double savings) {
		this.savings = savings;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public void checkBankBalance() throws FileNotFoundException {
		Scanner reader = new Scanner(new File("bankBalance.txt"));
		System.out.println("\n" + reader.nextLine() + "\n" + reader.nextLine() + "\n" + reader.nextLine());
	}
	
	
}
