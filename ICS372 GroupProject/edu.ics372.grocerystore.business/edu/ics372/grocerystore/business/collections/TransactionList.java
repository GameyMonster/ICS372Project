package edu.ics372.grocerystore.business.collections;

import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.ics372.grocerystore.business.entities.Transaction;

public class TransactionList {
	private List<Transaction> transactions = new LinkedList<Transaction>();
	private static TransactionList transactionList;

	private TransactionList() {

	}

	public static TransactionList getInstance() {
		if (transactionList == null) {
			transactionList = new TransactionList();
		}
		return transactionList;
	}

	public Iterator<Transaction> getTransactions() {
		return transactions.iterator();
	}

	public boolean insertTransaction(Transaction transaction) {
		return transactions.add(transaction);
	}

	public Iterator<Transaction> getTransactions(String memberId, Calendar startDate, Calendar EndDate) {
		LinkedList<Transaction> transactionList = new LinkedList<Transaction>();
		Iterator<Transaction> iterator = transactions.iterator();
		while (iterator.hasNext()) {
			Transaction transaction = iterator.next();
			if (transaction.getMemberID().equals(memberId) && transaction.getDate().compareTo(startDate) >= 0
					&& transaction.getDate().compareTo(EndDate) <= 0) {
				transactionList.add(transaction);
			}
		}
		return transactionList.iterator();
	}
}