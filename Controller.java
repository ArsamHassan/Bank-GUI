//Arsam Hassan
//Banking Application
package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import application.Bank;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Controller {
	@FXML private TextField username;
	@FXML private TextField password;
	@FXML private TextField phoneNumber;
	@FXML private TextField chequing;
	@FXML private TextField savings;
	@FXML private Label errorAccount;
	@FXML private Label successfulLabel;
	@FXML private Label errorLogin;
	@FXML private Label successfulLogin;
	@FXML private Label missingCreateField;
	@FXML private Label missingLoginField;
	@FXML private Label chequingAmount;
	@FXML private Label savingsAmount;
	@FXML private Label totalBankBalance;
	
	@FXML private ComboBox<String> transferLocation;
	@FXML private Label transferMoneyLabel1;
	@FXML private Label transferMoneyLabel2;
	@FXML private TextField transferMoneyEmail;
	@FXML private TextField transferMoneyAmount1;
	@FXML private RadioButton transferFromChequing;
	@FXML private RadioButton transferFromSavings;
	@FXML private Label transferInsuffecientFunds;
	@FXML private Label transferInsuffecientFunds1;
	
	@FXML private RadioButton payFromChequing;
	@FXML private RadioButton payFromSavings;
	@FXML private TextField paymentMoneyEmail;
	@FXML private Label paymentInsuffecientFunds;
	@FXML private Label paymentSuccessful;
	@FXML private TextField payMoneyAmount;
	
	@FXML private RadioButton depositToChequing;
	@FXML private RadioButton depositToSavings;
	@FXML private TextField depositMoneyAmount;
	@FXML private Label depositInsuffecientFunds	;
	@FXML private Label depositSuccessful;
	
	@FXML private TextField newUsername;
	@FXML private Label usernameAlrExists;
	@FXML private Label usernameChangedSuccessfully;
	
	@FXML private TextField newPhoneNumber;
	@FXML private Label phoneNumberAlrExists;
	@FXML private Label phoneNumberChangedSuccessfully;
	
	@FXML private TextField newPassword;
	@FXML private Label passwordAlrExists;
	@FXML private Label passwordChangedSuccessfully;
	
	
	@FXML private TextField terminateUsername;
	@FXML private TextField terminatePassword;
	@FXML private Label terminateSuccessful;
	@FXML private Label terminateUnsuccessful;
	@FXML private RadioButton terminateConfirmOption;
	
	@FXML private Label historyLabel;
	@FXML private Button revealButton;
	
	ArrayList<Bank> accounts = new ArrayList<Bank>();
	
	//makes user go back to the main screen
	public void backButton(ActionEvent e) throws IOException
	{
		Parent view = FXMLLoader.load(getClass().getResource("/application/Mainscreen.fxml"));
		Scene scene = new Scene(view);
		Stage window  = (Stage)((Node)e.getSource()).getScene().getWindow();
		String  style= getClass().getResource("application.css").toExternalForm();
		view.getStylesheets().add(style);
		window.setScene(scene);
		window.show();
	}
	//makes user go back to the menu screen
	public void backButtonMenu(ActionEvent e) throws IOException
	{
		Parent view = FXMLLoader.load(getClass().getResource("/application/Menu.fxml"));
		Scene scene = new Scene(view);
		Stage window  = (Stage)((Node)e.getSource()).getScene().getWindow();
		String  style= getClass().getResource("application.css").toExternalForm();
		view.getStylesheets().add(style);
		window.setScene(scene);
		window.show();
	}

	//makes user go back from the second tab of "Edit Account" to the main "Edit Account" screen
	public void backButtonEdit(ActionEvent e) throws IOException
	{
		Parent view = FXMLLoader.load(getClass().getResource("/application/EditAccount.fxml"));
		Scene scene = new Scene(view);
		Stage window  = (Stage)((Node)e.getSource()).getScene().getWindow();
		String  style= getClass().getResource("application.css").toExternalForm();
		view.getStylesheets().add(style);
		window.setScene(scene);
		window.show();
	}
	
	//opens the screen to allow user to create an account
	public void createAccount(ActionEvent e) throws IOException
	{
		Parent view = FXMLLoader.load(getClass().getResource("/application/CreateAccount.fxml"));
		Scene scene = new Scene(view);
		Stage window  = (Stage)((Node)e.getSource()).getScene().getWindow();
		String  style= getClass().getResource("application.css").toExternalForm();
		view.getStylesheets().add(style);
		window.setScene(scene);
		window.show();
	}
	
	//opens the screen to allow user to login
	public void login(ActionEvent e) throws IOException
	{
		Parent view = FXMLLoader.load(getClass().getResource("/application/Login.fxml"));
		Scene scene = new Scene(view);
		Stage window  = (Stage)((Node)e.getSource()).getScene().getWindow();
		String  style= getClass().getResource("application.css").toExternalForm();
		view.getStylesheets().add(style);
		window.setScene(scene);
		window.show();
	}
	
	//allows user to create an instance of the Bank class (an account) and opens the menu screen
	public void createAccountSubmit(ActionEvent e) throws IOException
	{
		Scanner bankBalanceReader = new Scanner(new File("bankBalance.txt"));
		Scanner accountInfoReader = new Scanner(new File("accountInfo.txt"));

		while(bankBalanceReader.hasNext())
		{
			StringTokenizer token = new StringTokenizer(accountInfoReader.nextLine(), " ");
			StringTokenizer token2 = new StringTokenizer(bankBalanceReader.nextLine(), " ");
			Bank newAccount = new Bank();
			newAccount.setUserName(token.nextToken());
			newAccount.setPassword(token.nextToken());
			newAccount.setPhoneNumber(token.nextToken());
			newAccount.setChequing(Double.parseDouble(token2.nextToken()));
			newAccount.setSavings(Double.parseDouble(token2.nextToken()));
			newAccount.setBankBalance(Double.parseDouble(token2.nextToken()));
			
			accounts.add(newAccount);
		}
		String name = username.getText();
		
		if(username.getText().equals("") || password.getText().equals("") || chequing.getText().equals("") || savings.getText().equals("") || phoneNumber.getText().equals(""))
		{
			missingCreateField.setVisible(true);
			successfulLabel.setVisible(false);
			errorAccount.setVisible(false);
		}
		else if(!Bank.duplicateAccount(accounts, name, phoneNumber.getText()))
		{
			Bank person = new Bank();
			Bank.createAccount(person, username.getText(), password.getText(), Double.parseDouble(chequing.getText()), Double.parseDouble(savings.getText()),phoneNumber.getText());
			accounts.add(person);
			successfulLabel.setVisible(true);
			errorAccount.setVisible(false);
			missingCreateField.setVisible(false);
		}
		else
		{
			successfulLabel.setVisible(false);
			errorAccount.setVisible(true);
			missingCreateField.setVisible(false);
		}
	}
	
	//allows user to login with their information and a successful login will open the menu screen
	public void loginSubmit(ActionEvent e) throws IOException
	{
		FileWriter indexWriter = new FileWriter("indexHolder.txt", false);
		Scanner bankBalanceReader = new Scanner(new File("bankBalance.txt"));
		Scanner accountInfoReader = new Scanner(new File("accountInfo.txt"));

		while(bankBalanceReader.hasNext())
		{
			StringTokenizer token = new StringTokenizer(accountInfoReader.nextLine(), " ");
			StringTokenizer token2 = new StringTokenizer(bankBalanceReader.nextLine(), " ");
			Bank newAccount = new Bank();
			newAccount.setUserName(token.nextToken());
			newAccount.setPassword(token.nextToken());
			newAccount.setPhoneNumber(token.nextToken());
			newAccount.setChequing(Double.parseDouble(token2.nextToken()));
			newAccount.setSavings(Double.parseDouble(token2.nextToken()));
			newAccount.setBankBalance(Double.parseDouble(token2.nextToken()));
			
			accounts.add(newAccount);
		}
		if(username.getText().equals("") || password.getText().equals(""))
		{
			missingLoginField.setVisible(true);
			errorLogin.setVisible(false);
			successfulLogin.setVisible(false);
		}
		//if login is successful
		else if(Bank.verifyLogin(accounts, username.getText(), password.getText()))
		{
			//finds the specific account associated with the username
			for(int i = 0; i < accounts.size(); i++)
			{
				String accountName = username.getText();
				if(accounts.get(i).getUserName().equals(accountName))
				{
					File file = new File("indexHolder.txt");
					file.delete();
					indexWriter.write(i + "");
					i = accounts.size();
				}
			}
			successfulLogin.setVisible(true);
			errorLogin.setVisible(false);
			missingLoginField.setVisible(false);

			
			Parent view = FXMLLoader.load(getClass().getResource("/application/Menu.fxml"));
			Scene scene = new Scene(view);
			Stage window  = (Stage)((Node)e.getSource()).getScene().getWindow();
			String  style= getClass().getResource("application.css").toExternalForm();
			view.getStylesheets().add(style);
			window.setScene(scene);
			window.show();
		}
		else
		{
			errorLogin.setVisible(true);
			successfulLogin.setVisible(false);
			missingLoginField.setVisible(false);
		}
		indexWriter.close();
	}
	
	//opens the screen to allow user to check their balance including their chequing and savings amount
	public void checkBalance(ActionEvent e) throws IOException
	{
		Parent view = FXMLLoader.load(getClass().getResource("/application/CheckBalance.fxml"));
		Scene scene = new Scene(view);
		Stage window  = (Stage)((Node)e.getSource()).getScene().getWindow();
		String  style= getClass().getResource("application.css").toExternalForm();
		view.getStylesheets().add(style);
		window.setScene(scene);
		window.show();
		Scanner bankBalanceReader = new Scanner(new File("bankBalance.txt"));
		Scanner accountInfoReader = new Scanner(new File("accountInfo.txt"));

		while(bankBalanceReader.hasNext())
		{
			StringTokenizer token = new StringTokenizer(accountInfoReader.nextLine(), " ");
			StringTokenizer token2 = new StringTokenizer(bankBalanceReader.nextLine(), " ");
			Bank newAccount = new Bank();
			newAccount.setUserName(token.nextToken());
			newAccount.setPassword(token.nextToken());
			newAccount.setPhoneNumber(token.nextToken());
			newAccount.setChequing(Double.parseDouble(token2.nextToken()));
			newAccount.setSavings(Double.parseDouble(token2.nextToken()));
			newAccount.setBankBalance(Double.parseDouble(token2.nextToken()));
			
			accounts.add(newAccount);
		}

		bankBalanceReader.close();
		accountInfoReader.close();
	}
	
	//opens the screen which allows the user to transfer money between their accounts or to an external email
	public void transferMoney(ActionEvent e) throws IOException
	{
		Parent view = FXMLLoader.load(getClass().getResource("/application/TransferMoney.fxml"));
		Scene scene = new Scene(view);
		Stage window  = (Stage)((Node)e.getSource()).getScene().getWindow();
		String  style= getClass().getResource("application.css").toExternalForm();
		view.getStylesheets().add(style);
		window.setScene(scene);
		window.show();
	}
	
	//opens the screen which allows user to make payment to a payment address from either chequing or savings account
	public void makePayment(ActionEvent e) throws IOException
	{
		Parent view = FXMLLoader.load(getClass().getResource("/application/MakePayment.fxml"));
		Scene scene = new Scene(view);
		Stage window  = (Stage)((Node)e.getSource()).getScene().getWindow();
		String  style= getClass().getResource("application.css").toExternalForm();
		view.getStylesheets().add(style);
		window.setScene(scene);
		window.show();
	}
	
	//opens the screen which allows user to deposit any amount of money to savings or chequing
	public void depositMoney(ActionEvent e) throws IOException
	{
		Parent view = FXMLLoader.load(getClass().getResource("/application/DepositMoney.fxml"));
		Scene scene = new Scene(view);
		Stage window  = (Stage)((Node)e.getSource()).getScene().getWindow();
		String  style= getClass().getResource("application.css").toExternalForm();
		view.getStylesheets().add(style);
		window.setScene(scene);
		window.show();
	}
	
	//opens the screen which allows user to edit their account credentials including username, password or phonenumber
	public void editAccount(ActionEvent e) throws IOException
	{
		Parent view = FXMLLoader.load(getClass().getResource("/application/EditAccount.fxml"));
		Scene scene = new Scene(view);
		Stage window  = (Stage)((Node)e.getSource()).getScene().getWindow();
		String  style= getClass().getResource("application.css").toExternalForm();
		view.getStylesheets().add(style);
		window.setScene(scene);
		window.show();
	}
	
	//opens the screen which allows the user to delete their account by confirming their user identity
	public void terminateAccount(ActionEvent e) throws IOException
	{
		Parent view = FXMLLoader.load(getClass().getResource("/application/TerminateAccount.fxml"));
		Scene scene = new Scene(view);
		Stage window  = (Stage)((Node)e.getSource()).getScene().getWindow();
		String  style= getClass().getResource("application.css").toExternalForm();
		view.getStylesheets().add(style);
		window.setScene(scene);
		window.show();
	}
	
	//button which reveals the chequing amount in the "Check BankBalance"
	public void chequingRevealButton(ActionEvent e) throws IOException
	{
		Scanner bankBalanceReader = new Scanner(new File("bankBalance.txt"));
		Scanner accountInfoReader = new Scanner(new File("accountInfo.txt"));

		while(bankBalanceReader.hasNext())
		{
			StringTokenizer token = new StringTokenizer(accountInfoReader.nextLine(), " ");
			StringTokenizer token2 = new StringTokenizer(bankBalanceReader.nextLine(), " ");
			Bank newAccount = new Bank();
			newAccount.setUserName(token.nextToken());
			newAccount.setPassword(token.nextToken());
			newAccount.setPhoneNumber(token.nextToken());
			newAccount.setChequing(Double.parseDouble(token2.nextToken()));
			newAccount.setSavings(Double.parseDouble(token2.nextToken()));
			newAccount.setBankBalance(Double.parseDouble(token2.nextToken()));
			
			accounts.add(newAccount);
		}
		
		Scanner indexReader = new Scanner(new File("indexHolder.txt"));
		chequingAmount.setText(accounts.get(Integer.parseInt(indexReader.next())).getChequing() + "");
	}
	
	//button which reveals the savings and total amount in the "Check BankBalance"
	public void savingsRevealButton(ActionEvent e) throws IOException
	{
		Scanner bankBalanceReader = new Scanner(new File("bankBalance.txt"));
		Scanner accountInfoReader = new Scanner(new File("accountInfo.txt"));

		while(bankBalanceReader.hasNext())
		{
			StringTokenizer token = new StringTokenizer(accountInfoReader.nextLine(), " ");
			StringTokenizer token2 = new StringTokenizer(bankBalanceReader.nextLine(), " ");
			Bank newAccount = new Bank();
			newAccount.setUserName(token.nextToken());
			newAccount.setPassword(token.nextToken());
			newAccount.setPhoneNumber(token.nextToken());
			newAccount.setChequing(Double.parseDouble(token2.nextToken()));
			newAccount.setSavings(Double.parseDouble(token2.nextToken()));
			newAccount.setBankBalance(Double.parseDouble(token2.nextToken()));
			
			accounts.add(newAccount);
		}
		
		Scanner indexReader = new Scanner(new File("indexHolder.txt"));
		Bank currentIndex = accounts.get(Integer.parseInt(indexReader.next()));
		savingsAmount.setText(currentIndex.getSavings() + "");
		totalBankBalance.setText(currentIndex.getBankBalance() + "");
	}
	
	//this sets the possible options for the combo box in "Transfer Money" if transfer from chequing is selected
	public void transferChequing(ActionEvent e)
	{
		transferLocation.getItems().clear();
		transferLocation.setDisable(false);
		transferLocation.getItems().addAll("Savings", "External Account");
	}
	//this sets the possible options for the combo box in "Transfer Money" if transfer from savings is selected
	public void transferSavings(ActionEvent e)
	{
		transferLocation.getItems().clear();
		transferLocation.setDisable(false);
		transferLocation.getItems().addAll("Chequing", "External Account");
	}
	//this opens labels and textfields that are appropriate for either "Chequing/Savings" or "External Account" option in "Transfer Money" screen
	public void transferLocationSelection(ActionEvent e)
	{
		try
		{
			if(transferLocation.getValue().equals("Chequing") || transferLocation.getValue().equals("Savings"))
			{
				transferMoneyLabel2.setVisible(true);
				transferMoneyLabel1.setVisible(false);
				transferMoneyEmail.setVisible(false);
				transferMoneyAmount1.setVisible(true);
			}
			else
			{
				transferMoneyLabel1.setVisible(true);
				transferMoneyLabel2.setVisible(true);
				transferMoneyEmail.setVisible(true);
				transferMoneyAmount1.setVisible(true);
			}
		}
		catch(NullPointerException f){}
	}
	
	//sends the amount of money entered in text field to the designated transfer location
	public void sendTransferMoney(ActionEvent e) throws IOException
	{
		Scanner bankBalanceReader = new Scanner(new File("bankBalance.txt"));
		Scanner accountInfoReader = new Scanner(new File("accountInfo.txt"));
		ArrayList<Bank> account = new ArrayList<Bank>();

		while(bankBalanceReader.hasNext())
		{
			StringTokenizer token = new StringTokenizer(accountInfoReader.nextLine(), " ");
			StringTokenizer token2 = new StringTokenizer(bankBalanceReader.nextLine(), " ");
			Bank newAccount = new Bank();
			newAccount.setUserName(token.nextToken());
			newAccount.setPassword(token.nextToken());
			newAccount.setPhoneNumber(token.nextToken());
			newAccount.setChequing(Double.parseDouble(token2.nextToken()));
			newAccount.setSavings(Double.parseDouble(token2.nextToken()));
			newAccount.setBankBalance(Double.parseDouble(token2.nextToken()));
			
			account.add(newAccount);
		}
		Scanner indexReader = new Scanner(new File("indexHolder.txt"));
		
		Bank currentIndex = account.get(Integer.parseInt(indexReader.next()));

		if(transferFromChequing.isSelected())
		{
			currentIndex.transferMoney(transferMoneyEmail, Double.parseDouble(transferMoneyAmount1.getText()), transferFromChequing, transferLocation, transferInsuffecientFunds, transferInsuffecientFunds1);			
		}
		else
		{
			currentIndex.transferMoney(transferMoneyEmail, Double.parseDouble(transferMoneyAmount1.getText()), transferFromSavings, transferLocation, transferInsuffecientFunds, transferInsuffecientFunds1);			
		}
		FileWriter eraseContent = new FileWriter("bankBalance.txt", false);
		FileWriter eraseContent2 = new FileWriter("accountInfo.txt", false);
		eraseContent.write("");
		eraseContent2.write("");

		//rewrites into the files using the new modified information as stored in the Bank arraylist by iterating through each of the accounts (objects)
		for(int i = 0; i < account.size(); i++)
		{
			FileWriter reWriteContent = new FileWriter("bankBalance.txt", true);
			FileWriter reWriteContent2 = new FileWriter("accountInfo.txt", true);
			
			reWriteContent.write(String.valueOf(account.get(i).getChequing()) + " ");
			reWriteContent.write(String.valueOf(account.get(i).getSavings()) + " ");
			reWriteContent.write(String.valueOf(account.get(i).getChequing() + account.get(i).getSavings()) + "\n");
			reWriteContent2.write(account.get(i).getUserName() + " ");
			reWriteContent2.write(account.get(i).getPassword() + " ");
			reWriteContent2.write(account.get(i).getPhoneNumber() + "\n");
			
			eraseContent.close();
			eraseContent2.close();
			reWriteContent.close();
			reWriteContent2.close();
		}
	}
	
	//sends the amount of money written in text field to the designated email address
	public void sendPayment(ActionEvent e) throws IOException
	{
		Scanner bankBalanceReader = new Scanner(new File("bankBalance.txt"));
		Scanner accountInfoReader = new Scanner(new File("accountInfo.txt"));
		ArrayList<Bank> account = new ArrayList<Bank>();

		while(bankBalanceReader.hasNext())
		{
			StringTokenizer token = new StringTokenizer(accountInfoReader.nextLine(), " ");
			StringTokenizer token2 = new StringTokenizer(bankBalanceReader.nextLine(), " ");
			Bank newAccount = new Bank();
			newAccount.setUserName(token.nextToken());
			newAccount.setPassword(token.nextToken());
			newAccount.setPhoneNumber(token.nextToken());
			newAccount.setChequing(Double.parseDouble(token2.nextToken()));
			newAccount.setSavings(Double.parseDouble(token2.nextToken()));
			newAccount.setBankBalance(Double.parseDouble(token2.nextToken()));
			
			account.add(newAccount);
		}
		Scanner indexReader = new Scanner(new File("indexHolder.txt"));
		
		Bank currentIndex = account.get(Integer.parseInt(indexReader.next()));

		if(payFromChequing.isSelected())
		{
			currentIndex.makePayment(paymentMoneyEmail, Double.parseDouble(payMoneyAmount.getText()), payFromChequing, paymentInsuffecientFunds, paymentSuccessful);			
		}
		else
		{
			currentIndex.makePayment(paymentMoneyEmail, Double.parseDouble(payMoneyAmount.getText()), payFromSavings, paymentInsuffecientFunds, paymentSuccessful);			
		}
		FileWriter eraseContent = new FileWriter("bankBalance.txt", false);
		FileWriter eraseContent2 = new FileWriter("accountInfo.txt", false);
		eraseContent.write("");
		eraseContent2.write("");

		//rewrites into the files using the new modified information as stored in the Bank arraylist by iterating through each of the accounts (objects)
		for(int i = 0; i < account.size(); i++)
		{
			FileWriter reWriteContent = new FileWriter("bankBalance.txt", true);
			FileWriter reWriteContent2 = new FileWriter("accountInfo.txt", true);
			
			reWriteContent.write(String.valueOf(account.get(i).getChequing()) + " ");
			reWriteContent.write(String.valueOf(account.get(i).getSavings()) + " ");
			reWriteContent.write(String.valueOf(account.get(i).getChequing() + account.get(i).getSavings()) + "\n");
			reWriteContent2.write(account.get(i).getUserName() + " ");
			reWriteContent2.write(account.get(i).getPassword() + " ");
			reWriteContent2.write(account.get(i).getPhoneNumber() + "\n");
			
			eraseContent.close();
			eraseContent2.close();
			reWriteContent.close();
			reWriteContent2.close();
		}
	}
	
	//sets pay money amount and email visible when payment option is selected
	public void paymentOption(ActionEvent e)
	{
		payMoneyAmount.setDisable(false);
		paymentMoneyEmail.setDisable(false);
	}

	//sets deposit money amount visible when deposit option is selected
	public void depositOption(ActionEvent e)
	{
		depositMoneyAmount.setDisable(false);
	}
	
	//this sends the amount of money written in the deposit to either chequing or savings depending on what was selected by the user
	public void depositSend(ActionEvent e) throws IOException
	{
		Scanner bankBalanceReader = new Scanner(new File("bankBalance.txt"));
		Scanner accountInfoReader = new Scanner(new File("accountInfo.txt"));
		ArrayList<Bank> account = new ArrayList<Bank>();

		while(bankBalanceReader.hasNext())
		{
			StringTokenizer token = new StringTokenizer(accountInfoReader.nextLine(), " ");
			StringTokenizer token2 = new StringTokenizer(bankBalanceReader.nextLine(), " ");
			Bank newAccount = new Bank();
			newAccount.setUserName(token.nextToken());
			newAccount.setPassword(token.nextToken());
			newAccount.setPhoneNumber(token.nextToken());
			newAccount.setChequing(Double.parseDouble(token2.nextToken()));
			newAccount.setSavings(Double.parseDouble(token2.nextToken()));
			newAccount.setBankBalance(Double.parseDouble(token2.nextToken()));
			
			account.add(newAccount);
		}
		Scanner indexReader = new Scanner(new File("indexHolder.txt"));
		
		Bank currentIndex = account.get(Integer.parseInt(indexReader.next()));

		if(depositToChequing.isSelected())
		{
			currentIndex.depositMoney(Double.parseDouble(depositMoneyAmount.getText()), depositToChequing, depositSuccessful);
		}
		else
		{
			currentIndex.depositMoney(Double.parseDouble(depositMoneyAmount.getText()), depositToSavings, depositSuccessful);			
		}	
		FileWriter eraseContent = new FileWriter("bankBalance.txt", false);
		FileWriter eraseContent2 = new FileWriter("accountInfo.txt", false);
		eraseContent.write("");
		eraseContent2.write("");

		//rewrites into the files using the new modified information as stored in the Bank arraylist by iterating through each of the accounts (objects)
		for(int i = 0; i < account.size(); i++)
		{
			FileWriter reWriteContent = new FileWriter("bankBalance.txt", true);
			FileWriter reWriteContent2 = new FileWriter("accountInfo.txt", true);
			
			reWriteContent.write(String.valueOf(account.get(i).getChequing()) + " ");
			reWriteContent.write(String.valueOf(account.get(i).getSavings()) + " ");
			reWriteContent.write(String.valueOf(account.get(i).getChequing() + account.get(i).getSavings()) + "\n");
			reWriteContent2.write(account.get(i).getUserName() + " ");
			reWriteContent2.write(account.get(i).getPassword() + " ");
			reWriteContent2.write(account.get(i).getPhoneNumber() + "\n");
			
			eraseContent.close();
			eraseContent2.close();
			reWriteContent.close();
			reWriteContent2.close();
		}
	}
	
	//allows user to change their username
	public void changeUsername(ActionEvent e) throws IOException
	{
		Parent view = FXMLLoader.load(getClass().getResource("/application/EditUsername.fxml"));
		Scene scene = new Scene(view);
		Stage window  = (Stage)((Node)e.getSource()).getScene().getWindow();
		String  style= getClass().getResource("application.css").toExternalForm();
		view.getStylesheets().add(style);
		window.setScene(scene);
		window.show();
	}
	
	//allows the user to chnage their password
	public void changePassword(ActionEvent e) throws IOException
	{
		Parent view = FXMLLoader.load(getClass().getResource("/application/EditPassword.fxml"));
		Scene scene = new Scene(view);
		Stage window  = (Stage)((Node)e.getSource()).getScene().getWindow();
		String  style= getClass().getResource("application.css").toExternalForm();
		view.getStylesheets().add(style);
		window.setScene(scene);
		window.show();
	}
	
	//allows the user to change their phoneNumber
	public void changePhoneNumber(ActionEvent e) throws IOException
	{
		Parent view = FXMLLoader.load(getClass().getResource("/application/EditPhoneNumber.fxml"));
		Scene scene = new Scene(view);
		Stage window  = (Stage)((Node)e.getSource()).getScene().getWindow();
		String  style= getClass().getResource("application.css").toExternalForm();
		view.getStylesheets().add(style);
		window.setScene(scene);
		window.show();
	}
	
	//changes the username to the new one written by the user
	public void confirmNewUsername(ActionEvent e) throws IOException
	{
		Scanner bankBalanceReader = new Scanner(new File("bankBalance.txt"));
		Scanner accountInfoReader = new Scanner(new File("accountInfo.txt"));
		ArrayList<Bank> account = new ArrayList<Bank>();

		while(bankBalanceReader.hasNext())
		{
			StringTokenizer token = new StringTokenizer(accountInfoReader.nextLine(), " ");
			StringTokenizer token2 = new StringTokenizer(bankBalanceReader.nextLine(), " ");
			Bank newAccount = new Bank();
			newAccount.setUserName(token.nextToken());
			newAccount.setPassword(token.nextToken());
			newAccount.setPhoneNumber(token.nextToken());
			newAccount.setChequing(Double.parseDouble(token2.nextToken()));
			newAccount.setSavings(Double.parseDouble(token2.nextToken()));
			newAccount.setBankBalance(Double.parseDouble(token2.nextToken()));

			account.add(newAccount);
		}

		Scanner indexReader = new Scanner(new File("indexHolder.txt"));
		
		Bank currentIndex = account.get(Integer.parseInt(indexReader.next()));

		boolean success = currentIndex.editAccount(account, 1, newUsername, usernameAlrExists, usernameChangedSuccessfully);
		
		FileWriter eraseContent2 = new FileWriter("accountInfo.txt", false);
		eraseContent2.write("");

		if(success)
		{
			Parent view = FXMLLoader.load(getClass().getResource("/application/Mainscreen.fxml"));
			Scene scene = new Scene(view);
			Stage window  = (Stage)((Node)e.getSource()).getScene().getWindow();
			String  style= getClass().getResource("application.css").toExternalForm();
			view.getStylesheets().add(style);
			window.setScene(scene);
			window.show();
		}
		
		//rewrites into the files using the new modified information as stored in the Bank arraylist by iterating through each of the accounts (objects)
		for(int i = 0; i < account.size(); i++)
		{
			FileWriter reWriteContent2 = new FileWriter("accountInfo.txt", true);
			reWriteContent2.write(account.get(i).getUserName() + " ");
			reWriteContent2.write(account.get(i).getPassword() + " ");
			reWriteContent2.write(account.get(i).getPhoneNumber() + "\n");
			
			eraseContent2.close();
			reWriteContent2.close();
		}
	}
	
	//changes the user password to the new one written by user
	public void confirmNewPassword(ActionEvent e) throws IOException
	{
		Scanner bankBalanceReader = new Scanner(new File("bankBalance.txt"));
		Scanner accountInfoReader = new Scanner(new File("accountInfo.txt"));
		ArrayList<Bank> account = new ArrayList<Bank>();

		while(bankBalanceReader.hasNext())
		{
			StringTokenizer token = new StringTokenizer(accountInfoReader.nextLine(), " ");
			StringTokenizer token2 = new StringTokenizer(bankBalanceReader.nextLine(), " ");
			Bank newAccount = new Bank();
			newAccount.setUserName(token.nextToken());
			newAccount.setPassword(token.nextToken());
			newAccount.setPhoneNumber(token.nextToken());
			newAccount.setChequing(Double.parseDouble(token2.nextToken()));
			newAccount.setSavings(Double.parseDouble(token2.nextToken()));
			newAccount.setBankBalance(Double.parseDouble(token2.nextToken()));

			account.add(newAccount);
		}

		Scanner indexReader = new Scanner(new File("indexHolder.txt"));
		
		Bank currentIndex = account.get(Integer.parseInt(indexReader.next()));

		currentIndex.editAccount(account, 2, newPassword, passwordAlrExists, passwordChangedSuccessfully);

		
		FileWriter eraseContent2 = new FileWriter("accountInfo.txt", false);
		eraseContent2.write("");

		//rewrites into the files using the new modified information as stored in the Bank arraylist by iterating through each of the accounts (objects)
		for(int i = 0; i < account.size(); i++)
		{
			FileWriter reWriteContent2 = new FileWriter("accountInfo.txt", true);
			reWriteContent2.write(account.get(i).getUserName() + " ");
			reWriteContent2.write(account.get(i).getPassword() + " ");
			reWriteContent2.write(account.get(i).getPhoneNumber() + "\n");
			
			eraseContent2.close();
			reWriteContent2.close();
		}
		Parent view = FXMLLoader.load(getClass().getResource("/application/Mainscreen.fxml"));
		Scene scene = new Scene(view);
		Stage window  = (Stage)((Node)e.getSource()).getScene().getWindow();
		String  style= getClass().getResource("application.css").toExternalForm();
		view.getStylesheets().add(style);
		window.setScene(scene);
		window.show();
	}
	
	//changes the user phone number to the new one written by user
	public void confirmNewPhoneNumber(ActionEvent e) throws IOException
	{
		Scanner bankBalanceReader = new Scanner(new File("bankBalance.txt"));
		Scanner accountInfoReader = new Scanner(new File("accountInfo.txt"));
		ArrayList<Bank> account = new ArrayList<Bank>();

		while(bankBalanceReader.hasNext())
		{
			StringTokenizer token = new StringTokenizer(accountInfoReader.nextLine(), " ");
			StringTokenizer token2 = new StringTokenizer(bankBalanceReader.nextLine(), " ");
			Bank newAccount = new Bank();
			newAccount.setUserName(token.nextToken());
			newAccount.setPassword(token.nextToken());
			newAccount.setPhoneNumber(token.nextToken());
			newAccount.setChequing(Double.parseDouble(token2.nextToken()));
			newAccount.setSavings(Double.parseDouble(token2.nextToken()));
			newAccount.setBankBalance(Double.parseDouble(token2.nextToken()));

			account.add(newAccount);
		}

		Scanner indexReader = new Scanner(new File("indexHolder.txt"));
		
		Bank currentIndex = account.get(Integer.parseInt(indexReader.next()));

		currentIndex.editAccount(account, 3, newPhoneNumber, phoneNumberAlrExists, phoneNumberChangedSuccessfully);
		
		
		FileWriter eraseContent2 = new FileWriter("accountInfo.txt", false);
		eraseContent2.write("");

		//rewrites into the files using the new modified information as stored in the Bank arraylist by iterating through each of the accounts (objects)
		for(int i = 0; i < account.size(); i++)
		{
			FileWriter reWriteContent2 = new FileWriter("accountInfo.txt", true);
			reWriteContent2.write(account.get(i).getUserName() + " ");
			reWriteContent2.write(account.get(i).getPassword() + " ");
			reWriteContent2.write(account.get(i).getPhoneNumber() + "\n");
			
			eraseContent2.close();
			reWriteContent2.close();
		}
	}
	
	
	//deletes the user account after confirming their credientials
	public void terminateAccountConfirm(ActionEvent e) throws IOException
	{
		Scanner bankBalanceReader = new Scanner(new File("bankBalance.txt"));
		Scanner accountInfoReader = new Scanner(new File("accountInfo.txt"));
		ArrayList<Bank> account = new ArrayList<Bank>();

		while(bankBalanceReader.hasNext())
		{
			StringTokenizer token = new StringTokenizer(accountInfoReader.nextLine(), " ");
			StringTokenizer token2 = new StringTokenizer(bankBalanceReader.nextLine(), " ");
			Bank newAccount = new Bank();
			newAccount.setUserName(token.nextToken());
			newAccount.setPassword(token.nextToken());
			newAccount.setPhoneNumber(token.nextToken());
			newAccount.setChequing(Double.parseDouble(token2.nextToken()));
			newAccount.setSavings(Double.parseDouble(token2.nextToken()));
			newAccount.setBankBalance(Double.parseDouble(token2.nextToken()));

			account.add(newAccount);
		}

		Scanner indexReader = new Scanner(new File("indexHolder.txt"));
		
		Bank currentIndex = account.get(Integer.parseInt(indexReader.next()));

		if(currentIndex.deleteAccount(account, terminateConfirmOption, terminateUsername, terminatePassword, terminateSuccessful, terminateUnsuccessful))
		{
			Parent view = FXMLLoader.load(getClass().getResource("/application/Mainscreen.fxml"));
			Scene scene = new Scene(view);
			Stage window  = (Stage)((Node)e.getSource()).getScene().getWindow();
			String  style= getClass().getResource("application.css").toExternalForm();
			view.getStylesheets().add(style);
			window.setScene(scene);
			window.show();
		}
		
		FileWriter eraseContent = new FileWriter("bankBalance.txt", false);
		FileWriter eraseContent2 = new FileWriter("accountInfo.txt", false);
		eraseContent.write("");
		eraseContent2.write("");

		//rewrites into the files using the new modified information as stored in the Bank arraylist by iterating through each of the accounts (objects)
		for(int i = 0; i < account.size(); i++)
		{
			FileWriter reWriteContent = new FileWriter("bankBalance.txt", true);
			FileWriter reWriteContent2 = new FileWriter("accountInfo.txt", true);
			
			reWriteContent.write(String.valueOf(account.get(i).getChequing()) + " ");
			reWriteContent.write(String.valueOf(account.get(i).getSavings()) + " ");
			reWriteContent.write(String.valueOf(account.get(i).getChequing() + account.get(i).getSavings()) + "\n");
			reWriteContent2.write(account.get(i).getUserName() + " ");
			reWriteContent2.write(account.get(i).getPassword() + " ");
			reWriteContent2.write(account.get(i).getPhoneNumber() + "\n");
			
			eraseContent.close();
			eraseContent2.close();
			reWriteContent.close();
			reWriteContent2.close();
		}
	}
	
	
	//opens the screen where history of transactions and deposits and other activity can be displayed
	public void history(ActionEvent e) throws IOException
	{
		Parent view = FXMLLoader.load(getClass().getResource("/application/History.fxml"));
		Scene scene = new Scene(view);
		Stage window  = (Stage)((Node)e.getSource()).getScene().getWindow();
		String  style= getClass().getResource("application.css").toExternalForm();
		view.getStylesheets().add(style);
		window.setScene(scene);
		window.show();
	}
	
	//reveals all the history associated with the user's account
	public void historyReveal(ActionEvent e) throws FileNotFoundException
	{
		Scanner bankBalanceReader = new Scanner(new File("bankBalance.txt"));
		Scanner accountInfoReader = new Scanner(new File("accountInfo.txt"));
		ArrayList<Bank> account = new ArrayList<Bank>();

		while(bankBalanceReader.hasNext())
		{
			StringTokenizer token = new StringTokenizer(accountInfoReader.nextLine(), " ");
			StringTokenizer token2 = new StringTokenizer(bankBalanceReader.nextLine(), " ");
			Bank newAccount = new Bank();
			newAccount.setUserName(token.nextToken());
			newAccount.setPassword(token.nextToken());
			newAccount.setPhoneNumber(token.nextToken());
			newAccount.setChequing(Double.parseDouble(token2.nextToken()));
			newAccount.setSavings(Double.parseDouble(token2.nextToken()));
			newAccount.setBankBalance(Double.parseDouble(token2.nextToken()));

			account.add(newAccount);
		}
		revealButton.setVisible(false);
		Scanner indexReader = new Scanner(new File("indexHolder.txt"));
		
		Bank currentUser = account.get(Integer.parseInt(indexReader.next()));
		
		Scanner historyReader = new Scanner(new File("history.txt"));
		
		while(historyReader.hasNextLine())
		{
			String text = historyReader.nextLine();
			if(text.substring(1, text.lastIndexOf("]")).equals(currentUser.getUserName()))
			{
				historyLabel.setText(historyLabel.getText() + "\n" + text);
			}
		}
		
	}
	
}
