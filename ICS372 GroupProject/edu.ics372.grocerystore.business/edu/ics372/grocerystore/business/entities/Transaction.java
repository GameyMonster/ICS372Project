package edu.ics372.grocerystore.business.entities;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Represent a single Transaction (User visited the store, total price paid
 * during the visit)
 * 
 * @author Joshua Vang
 *
 */
public class Transaction implements Serializable {
	private static final long serialVersionUID = 1L;
	private String memberID;
	private Calendar date;
	private double totalPrice;

	/**
	 * Create the Transaction constructor
	 * 
	 * @param memberID
	 * @param totalPrice
	 */
	public Transaction(String memberID, double totalPrice) {
		this.memberID = memberID;
		this.totalPrice = totalPrice;
		this.date = Calendar.getInstance();
	}

	/**
	 * @return the memberID
	 */
	public String getMemberID() {
		return memberID;
	}

	/**
	 * @param memberID the memberID to set
	 */
	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}

	/**
	 * @return the date
	 */
	public Calendar getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Calendar date) {
		this.date = date;
	}

	/**
	 * @return the totalPrice
	 */
	public double getTotalPrice() {
		return totalPrice;
	}

	/**
	 * @param totalPrice the totalPrice to set
	 */
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * Check to see if the Transaction was made by a member
	 * 
	 * @param memberID
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public boolean checkTransaction(String memberID, Calendar startDate, Calendar endDate) {

		if (this.memberID != memberID) {
			return false;
		}

		if (this.date.before(startDate) || this.date.after(endDate)) {
			return false;
		}

		return true;
	}
}
