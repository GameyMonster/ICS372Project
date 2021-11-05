package edu.ics372.grocerystore.business.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.oobook.grocerystore.business.facade.Result;

import edu.ics372.grocerystore.business.entities.Order;

public class SafeOrderIterator implements Iterator<Result> {
	private Iterator<Order> iterator;
	private Result result = new Result();

	/**
	 * The user of SafeOrderIterator must supply an Iterator to Order.
	 * 
	 * @param iterator Iterator<Order>
	 */
	public SafeOrderIterator(Iterator<Order> iterator) {
		this.iterator = iterator;
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public Result next() {
		if (iterator.hasNext()) {
			result.setOrderFields(iterator.next());
		} else {
			throw new NoSuchElementException("No such element");
		}
		return result;
	}
}