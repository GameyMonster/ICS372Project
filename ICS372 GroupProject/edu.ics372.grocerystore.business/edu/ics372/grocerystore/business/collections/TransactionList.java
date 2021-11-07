package edu.ics372.grocerystore.business.collections;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.ics372.grocerystore.business.entities.Transaction;
import edu.ics372.grocerystore.business.iterators.FilteredIterator;

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
	 * List of Transaction between the dates
	 * 
	 * @param memberId
	 * @param startDate
	 * @param EndDate
	 * @return
	 */
	public Iterator<Transaction> getTransactions(String memberId, Calendar startDate, Calendar EndDate) {
		return new FilteredIterator<Transaction>(transactions.iterator(),
				transaction -> transaction.checkTransaction(memberId, startDate, EndDate));
	}
}