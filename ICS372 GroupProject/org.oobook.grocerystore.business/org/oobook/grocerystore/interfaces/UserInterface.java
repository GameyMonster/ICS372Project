package org.oobook.grocerystore.interfaces;

/**
 * 
 * @author Brahma Dathan and Sarnath Ramnath
 * @Copyright (c) 2010
 
 * Redistribution and use with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - the use is for academic purpose only
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   - Neither the name of Brahma Dathan or Sarnath Ramnath
 *     may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * The authors do not make any claims regarding the correctness of the code in this module
 * and are not responsible for any loss or damage resulting from its use.  
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.oobook.grocerystore.business.facade.GroceryStore;
import org.oobook.grocerystore.business.facade.Request;
import org.oobook.grocerystore.business.facade.Result;

public class UserInterface {
	private static UserInterface userInterface;
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static Library groceryStore;
	private static final int EXIT = 0;
	private static final int ADD_MEMBER = 1;
	private static final int REMOVE_MEMBER = 2;
	private static final int GET_MEMBER_INFO = 3;
	private static final int ADD_PRODUCTS = 4;
	private static final int CHECKOUT = 5;
	private static final int PRODUCT_INFO = 6;
	private static final int PROCESS_SHIPMENT = 7;
	private static final int CHANGE_PRICE = 8;
	private static final int GET_TRANSACTIONS = 9;
	private static final int GET_MEMBERS = 10;
	private static final int GET_PRODUCTS = 11;
	private static final int GET_ORDERS = 12;
	private static final int SAVE = 13;
	private static final int RETRIEVE = 14;
	private static final int HELP = 15;

	/**
	 * Made private for singleton pattern. Conditionally looks for any saved data.
	 * Otherwise, it gets a singleton Library object.
	 */
	private UserInterface() {
		if (yesOrNo("Look for saved data and  use it?")) {
			retrieve();
		} else {
			library = Library.instance();
		}

	}

	/**
	 * Supports the singleton pattern
	 * 
	 * @return the singleton object
	 */
	public static UserInterface instance() {
		if (userInterface == null) {
			return userInterface = new UserInterface();
		} else {
			return userInterface;
		}
	}

	/**
	 * Gets a token after prompting
	 * 
	 * @param prompt - whatever the user wants as prompt
	 * @return - the token from the keyboard
	 * 
	 */
	public String getToken(String prompt) {
		do {
			try {
				System.out.println(prompt);
				String line = reader.readLine();
				StringTokenizer tokenizer = new StringTokenizer(line, "\n\r\f");
				if (tokenizer.hasMoreTokens()) {
					return tokenizer.nextToken();
				}
			} catch (IOException ioe) {
				System.exit(0);
			}
		} while (true);
	}

	/**
	 * Gets a name after prompting
	 * 
	 * @param prompt - whatever the user wants as prompt
	 * @return - the token from the keyboard
	 * 
	 */
	public String getName(String prompt) {
		do {
			try {
				System.out.println(prompt);
				String line = reader.readLine();
				return line;
			} catch (IOException ioe) {
				System.exit(0);
			}
		} while (true);

	}

	/**
	 * Queries for a yes or no and returns true for yes and false for no
	 * 
	 * @param prompt The string to be prepended to the yes/no prompt
	 * @return true for yes and false for no
	 * 
	 */
	private boolean yesOrNo(String prompt) {
		String more = getToken(prompt + " (Y|y)[es] or anything else for no");
		if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
			return false;
		}
		return true;
	}

	/**
	 * Converts the string to a number
	 * 
	 * @param prompt the string for prompting
	 * @return the integer corresponding to the string
	 * 
	 */
	public int getNumber(String prompt) {
		do {
			try {
				String item = getToken(prompt);
				Integer number = Integer.valueOf(item);
				return number.intValue();
			} catch (NumberFormatException nfe) {
				System.out.println("Please input a number ");
			}
		} while (true);
	}

	/**
	 * Prompts for a date and gets a date object
	 * 
	 * @param prompt the prompt
	 * @return the data as a Calendar object
	 */
	public Calendar getDate(String prompt) {
		do {
			try {
				Calendar date = new GregorianCalendar();
				String item = getToken(prompt);
				DateFormat dateFormat = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
				date.setTime(dateFormat.parse(item));
				return date;
			} catch (Exception fe) {
				System.out.println("Please input a date as mm/dd/yy");
			}
		} while (true);
	}

	/**
	 * Prompts for a command from the keyboard
	 * 
	 * @return a valid command
	 * 
	 */
	public int getCommand() {
		do {
			try {
				int value = Integer.parseInt(getToken("Enter command:" + HELP + " for help"));
				if (value >= EXIT && value <= HELP) {
					return value;
				}
			} catch (NumberFormatException nfe) {
				System.out.println("Enter a number");
			}
		} while (true);
	}

	/**
	 * Displays the help screen
	 * 
	 */
	public void help() {
		System.out.println("Enter a number between 0 and 15 as explained below:");
		System.out.println(EXIT + " to Exit\n");
		System.out.println(ADD_MEMBER + " to add a member");
		System.out.println(REMOVE_MEMBER + " to remove a member");
		System.out.println(GET_MEMBER_INFO + " to get a members information");
		System.out.println(ADD_PRODUCTS + " to  add products");
		System.out.println(CHECKOUT + " to  checkout a  member");
		System.out.println(PRODUCT_INFO + " to  return product information");
		System.out.println(PROCESS_SHIPMENT + " to  process a shipment");
		System.out.println(CHANGE_PRICE + " to  change price");
		System.out.println(GET_TRANSACTIONS + " to  print transactions");
		System.out.println(GET_MEMBERS + " to  print all members");
		System.out.println(GET_PRODUCTS + " to  print all products");
		System.out.println(GET_ORDERS + " to  print all orders");
		System.out.println(SAVE + " to  save data");
		System.out.println(RETRIEVE + " to  ");
		System.out.println(HELP + " for help");
	}

	/**
	 * Method to be called for adding a member. Prompts the user for the appropriate
	 * values and uses the appropriate Library method for adding the member.
	 * 
	 */
	public void addMember() {
		Request.instance().setMemberName(getName("Enter member name"));
		Request.instance().setMemberAddress(getName("Enter address"));
		Request.instance().setMemberPhone(getName("Enter phone"));

		// DATE JOINED
		Request.instance().setMemberDate(getName("Enter date joined in mm/dd/yyyy"));
		// FEE PAID
		Request.instance().setMemberFee(getName("Enter fee"));

		Result result = grocerStore.addMember(Request.instance());
		if (result.getResultCode() != Result.OPERATION_COMPLETED) {
			System.out.println("Could not add member");
		} else {
			System.out.println(result.getMemberName() + "'s id is " + result.getMemberId());
		}
	}

	/**
	 *
	 * Method to remove a specific member
	 */
	public void removeMember() {
		Request.instance().setMemberName(getName("Enter member name to remove"));

		Result result = groceryStore.removeMember(Request.instance());
		if (result.getResultCode() != Result.OPERATION_COMPLETED) {
			System.out.println("Could not remove member");
		} else {
			System.out.println(result.getMemberName() + "Successfully removed");
		}

	}

	public void getMemberInfo() {
		Request.instance().setMemberName(getName("Enter member name to retrieve information"));

		Result result = groceryStore.getMember(Request.instance());
		if (result.getResultCode() != Result.OPERATION_COMPLETED) {
			System.out.println("Could not find member");
		} else {
			System.out.println(result.getMemberName() + "information is, " + result.getMemberAddress()
					+ ", Phone number" + result.getMemberPhone() + ", Date joined" + result.getMemberDate()
					+ ", member ID is" + result.getMemberId());
		}

	}

	/**
	 * Method to be called for adding a product. Prompts the user for the
	 * appropriate values and uses the appropriate Library method for adding the
	 * book.
	 * 
	 */

	public void addPoducts() {
		do {
			Request.instance().setProductTitle(getName("Enter  title"));
			Request.instance().setProductId(getToken("Enter id"));
			// set the price
			Request.instance().setProductPrice(getPrice("Enter price"));
			// min reorder level
			Request.instance().setProductMinReorder(getReorder("Enter minimum reorderlevel"));

			Result result = groceryStore.addProdcut(Request.instance());
			if (result.getResultCode() != Result.OPERATION_COMPLETED) {
				System.out.println("Product could not be added");
			} else {
				System.out.println("Product " + result.getProductTitle() + " added");
			}
		} while (yesOrNo("Add more products?"));
	}

	public void checkout(){
		Request.instance().setMemberId(getToken("Enter member id"));
		// Do we need to enter the date
		
		Request.instance().setDate("Enter the date"));
		// Ask for products 
		
		Reuqest.instance().
		//ask if there are any more produts
		
		Request.instance().setCheckOut(getmember ID

}

	public void productInfo() {
		Request.instance().getProductID(getToken("Enter Product ID"));
		Iterator<Result> iterator = library.getProductInfo();
		System.out.println("Product information (name, price, id)");
		while (iterator.hasNext()) {
			Result result = iterator.next();
			System.out.println(result.getProductName() + " " + result.getProductPrice() + " " + result.getProductID());
		}

	}

	public void processShipment() {

	}

	public void changePrice() {

	}

	public void getTransactions() {
		Request.instance().setMemberId(getToken("Enter member id"));
		// need to change in here
		Request.instance().setDate(getDate("Please enter the date for which you want records as mm/dd/yy"));
		Iterator<Result> result = library.getTransactions(Request.instance());
		while (result.hasNext()) {
			Result transaction = result.next();
			System.out.println(transaction.getTransactionType() + "   " + transaction.getBookTitle() + "\n");
		}
		System.out.println("\n End of transactions \n");
	}

	/**
	 * Displays all members
	 */
	public void getMembers() {
		Iterator<Result> iterator = library.getMembers();
		System.out.println("List of members (name, address, phone, id)");
		while (iterator.hasNext()) {
			Result result = iterator.next();
			System.out.println(result.getMemberName() + " " + result.getMemberAddress() + " " + result.getMemberPhone()
					+ " " + result.getMemberId());
		}
		System.out.println("End of listing");
	}

	/**
	 * Gets and prints all products.
	 */
	public void getProducts() {
		Iterator<Result> iterator = library.getBooks();
		System.out.println("List of books (title, author, id, borrower id, due date)");
		while (iterator.hasNext()) {
			Result result = iterator.next();
			System.out.println(result.getBookTitle() + " " + result.getBookAuthor() + " " + result.getBookId() + " "
					+ result.getBookBorrower() + " " + result.getBookDueDate());
		}
		System.out.println("End of listing");
	}

	/**
	 * Gets and prints all orders.
	 */
	public void getOrders() {
		Iterator<Result> iterator = library.getBooks();
		System.out.println("List of books (title, author, id, borrower id, due date)");
		while (iterator.hasNext()) {
			Result result = iterator.next();
			System.out.println(result.getBookTitle() + " " + result.getBookAuthor() + " " + result.getBookId() + " "
					+ result.getBookBorrower() + " " + result.getBookDueDate());
		}
		System.out.println("End of listing");
	}

	/**
	 * Method to be called for saving the Library object. Uses the appropriate
	 * Library method for saving.
	 * 
	 */
	private void save() {
		if (library.save()) {
			System.out.println(" The library has been successfully saved in the file LibraryData \n");
		} else {
			System.out.println(" There has been an error in saving \n");
		}
	}

	/**
	 * Method to be called for retrieving saved data. Uses the appropriate Library
	 * method for retrieval.
	 * 
	 */
	private void retrieve() {
		try {
			if (library == null) {
				library = Library.retrieve();
				if (library != null) {
					System.out.println(" The library has been successfully retrieved from the file LibraryData \n");
				} else {
					System.out.println("File doesnt exist; creating new library");
					library = Library.instance();
				}
			}
		} catch (Exception cnfe) {
			cnfe.printStackTrace();
		}
	}

	/**
	 * Orchestrates the whole process. Calls the appropriate method for the
	 * different functionalities.
	 * 
	 */
	public void process() {
		int command;
		help();
		while ((command = getCommand()) != EXIT) {
			switch (command) {
			case ADD_MEMBER:
				addMember();
				break;
			case REMOVE_MEMBER:
				removeMember();
				break;
			case GET_MEMBER_INFO:
				getMemberInfo();
				break;
			case ADD_PRODUCTS:
				addPoducts();
				break;
			case CHECKOUT:
				checkout();
				break;
			case PRODUCT_INFO:
				productInfo();
				break;
			case PROCESS_SHIPMENT:
				processShipment();
				break;
			case CHANGE_PRICE:
				changePrice();
				break;
			case GET_TRANSACTIONS:
				getTransactions();
				break;
			case GET_MEMBERS:
				getMembers();
				break;
			case GET_PRODUCTS:
				getProducts();
				break;
			case GET_ORDERS:
				getOrders();
				break;
			case SAVE:
				save();
				break;
			case RETRIEVE:
				retrieve();
				break;
			case HELP:
				help();
				break;
			}
		}
	}

	/**
	 * The method to start the application. Simply calls process().
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		UserInterface.instance().process();
	}
}
