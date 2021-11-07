package edu.ics372.grocerystore.business.collections;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.ics372.grocerystore.business.entities.Transaction;

public class TransactionList implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<Transaction> transactions = new LinkedList<Transaction>();
	private static TransactionList transactionList;

	/**
	 * Private constructor for singleton.
	 */
	private TransactionList() {

	}

	/**
	 * Returns single object, if it has not been created already.
	 * 
	 * @return instance of a TransactionList
	 */
	public static TransactionList getInstance() {
		if (transactionList == null) {
			transactionList = new TransactionList();
		}
		return transactionList;
	}

	/**
	 * @return Iterator of a Transactions
	 */
	public Iterator<Transaction> getTransactions() {
		return transactions.iterator();
	}

	/**
	 * Insert the product to the Transaction
	 * 
	 * @param transaction
	 * @return
	 */
	public boolean insertTransaction(Transaction transaction) {
		return transactions.add(transaction);
	}

	/**
	 * Return a list of Transaction between the dates
	 * 
	 * @param memberId
	 * @param startDate
	 * @param EndDate
	 * @return
	 */
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