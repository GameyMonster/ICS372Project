package edu.ics372.grocerystore.business.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class FilteredIterator<T> implements Iterator<T> {
	private T item;
	private Predicate<T> predicate;
	private Iterator<T> iterator;

	public FilteredIterator(Iterator<T> iterator, Predicate<T> predicate) {
		this.predicate = predicate;
		this.iterator = iterator;
		getNextItem();
	}

	@Override
	public boolean hasNext() {
		return item != null;
	}

	@Override
	public T next() {
		if (!hasNext()) {
			throw new NoSuchElementException("No such element");
		}
		T returnValue = item;
		getNextItem();
		return returnValue;
	}

	/**
	 * This method searches for the next item that satisfies the predicate. If none
	 * is found, the item field is set to null.
	 */
	private void getNextItem() {
		while (iterator.hasNext()) {
			item = iterator.next();
			if (predicate.test(item)) {
				return;
			}
		}
		item = null;
	}
}