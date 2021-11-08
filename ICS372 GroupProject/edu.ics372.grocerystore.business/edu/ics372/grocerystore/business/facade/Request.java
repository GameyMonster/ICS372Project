package edu.ics372.grocerystore.business.facade;

import java.util.Calendar;

public class Request extends DataTransfer {
	// singleton static instance
	private static Request request = null;
	// dates used in filtering member transactions
	private Calendar startDate;
	private Calendar endDate;

	/**
	 * This is a singleton class. Hence the private constructor.
	 */
	private Request() {

	}

	/**
	 * Returns the only instance of the class.
	 * 
	 * @return the only instance
	 */
	public static Request instance() {
		if (Request.request == null) {
			Request.request = new Request();
		}
		return Request.request;
	}

	/**
	 * Getter for startDate
	 * 
	 * @return start date for filtering member transactions
	 */
	public Calendar getStartDate() {
		return this.startDate;
	}

	/**
	 * Getter for endDate
	 * 
	 * @return end date for filtering member transactions
	 */
	public Calendar getEndDate() {
		return this.endDate;
	}

	/**
	 * Setter for startDate
	 * 
	 * @param startDate earliest date for filtering member transactions
	 */
	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	/**
	 * Setter for endDate
	 * 
	 * @param endDate latest date for filtering member transactions
	 */
	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}
}