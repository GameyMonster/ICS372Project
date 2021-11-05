package edu.ics372.grocerystore.business.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.oobook.grocerystore.business.facade.Result;

import edu.ics372.grocerystore.business.entities.Transaction;

public class SafeTransactionIterator implements Iterator<Result> {
	private Iterator<Transaction> iterator;
	private Result result = new Result();

	/**
	 * The user of SafeTransactionIterator must supply an Iterator to Transaction.
	 * 
	 * @param iterator Iterator<Transaction>
	 */
	public SafeTransactionIterator(Iterator<Transaction> iterator) {
		this.iterator = iterator;
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public Result next() {
		if (iterator.hasNext()) {
			result.setTransactionFields(iterator.next());
		} else {
			throw new NoSuchElementException("No such element");
		}
		return result;
	}
}