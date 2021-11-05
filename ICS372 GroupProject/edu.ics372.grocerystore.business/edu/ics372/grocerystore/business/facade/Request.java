package edu.ics372.grocerystore.business.facade;

import java.util.Calendar;

public class Request extends DataTransfer {
	private static Request request;
	private Calendar startDate;
	private Calendar endDate;

	private Request() {
		super();
	}

	public static Request instance() {
		if (request == null) {
			request = new Request();
		}
		return request;
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}
}