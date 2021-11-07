package edu.ics372.grocerystore.business.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.ics372.grocerystore.business.entities.Member;
import edu.ics372.grocerystore.business.facade.Result;

public class SafeMemberIterator implements Iterator<Result> {
	private Iterator<Member> iterator;
	private Result result = new Result();

	/**
	 * The user of SafeMemberIterator must supply an Iterator to Member.
	 * 
	 * @param iterator Iterator<Member>
	 */
	public SafeMemberIterator(Iterator<Member> iterator) {
		this.iterator = iterator;
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public Result next() {
		if (iterator.hasNext()) {
			result.setMemberFields(iterator.next());
			result.setResultCode(Result.OPERATION_COMPLETED);
		} else {
			throw new NoSuchElementException("No such element");
		}
		return result;
	}
}