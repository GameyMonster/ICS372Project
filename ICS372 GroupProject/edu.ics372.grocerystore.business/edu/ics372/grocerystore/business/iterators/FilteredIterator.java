package edu.ics372.grocerystore.business.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

/**
 * Iterator that uses a predicate to determine if an item should be yielded.
 */
public class FilteredIterator<T> implements Iterator<T> {
	private T item;
	// Predicate used to test if item should be yielded
	private Predicate<T> predicate;
	// iterator adapter
	private Iterator<T> iterator;

	/**
	 * FilteredIterator Constructor. Calls getNextItem() to set item to the first
	 * valid element.
	 * 
	 * @param iterator  iterator that is being adapted by FilteredIterator
	 * @param predicate predicate used to test if an item should be yielded
	 */
	public FilteredIterator(Iterator<T> iterator, Predicate<T> predicate) {
		this.iterator = iterator;
		this.predicate = predicate;
		getNextItem();
	}

	/**
	 * Check if there is another item to be yielded.
	 * 
	 * @return true if there is another item to be yielded by iterator
	 */
	@Override
	public boolean hasNext() {
		return item != null;
	}

	/**
	 * Get next valid value to yield.
	 * 
	 * @return next value to yield
	 */
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
	 * Find next item in iterator that satisfies predicate
	 */
	public void getNextItem() {
		while (iterator.hasNext()) {
			item = iterator.next();
			if (predicate.test(item)) {
				return;
			}
		}
		item = null;
	}

}