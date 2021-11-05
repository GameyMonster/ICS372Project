package edu.ics372.grocerystore.business.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.oobook.grocerystore.business.facade.Result;

import edu.ics372.grocerystore.business.entities.Product;

public class SafeProductIterator implements Iterator<Result> {
	private Iterator<Product> iterator;
	private Result result = new Result();

	/**
	 * The user of SafeProductIterator must supply an Iterator to Product.
	 * 
	 * @param iterator Iterator<Product>
	 */
	public SafeProductIterator(Iterator<Product> iterator) {
		this.iterator = iterator;
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public Result next() {
		if (iterator.hasNext()) {
			result.setProductFields(iterator.next());
		} else {
			throw new NoSuchElementException("No such element");
		}
		return result;
	}
}
