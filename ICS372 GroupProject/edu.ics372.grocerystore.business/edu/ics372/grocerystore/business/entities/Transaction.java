package edu.ics372.grocerystore.business.entities;

import java.util.Calendar;

/**
 * Represent a single Transaction (User visited the store, total price paid
 * during the visit)
 * 
 * @author Joshua
 *
 */
public class Transaction {
	private String memberID;
	private Calendar date;
	private double totalPrice;

	public Transaction(String memberID, double totalPrice) {
		this.memberID = memberID;
		this.totalPrice = totalPrice;
		this.date = Calendar.getInstance();
	}

	public String getMemberID() {
		return this.memberID;
	}

	public Calendar getDate() {
		return this.date;
	}

	public double getTotalPrice() {
		return this.totalPrice;
	}

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
