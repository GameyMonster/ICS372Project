package edu.ics372.grocerystore.business.collections;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.ics372.grocerystore.business.entities.Member;
import edu.ics372.grocerystore.business.iterators.FilteredIterator;

public class MemberList implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<Member> members = new LinkedList<Member>();
	private static MemberList memberList;

	/**
	 * memberList constructor.
	 */
	private MemberList() {

	}

	/**
	 * Singleton
	 * 
	 * @return
	 */
	public static MemberList getInstance() {
		if (memberList == null) {
			memberList = new MemberList();
		}
		return memberList;
	}

	/**
	 * Adding member to the collection list
	 * 
	 * @param member
	 * @return true or false.
	 */
	public boolean insertMember(Member member) {
		return members.add(member);
	}

	/**
	 * Get Members information
	 * 
	 * @param memberId
	 * @return member, else return null if there are no member found
	 */
	public Member getMember(String memberId) {
		Iterator<Member> iterator = members.listIterator();
		while (iterator.hasNext()) {
			Member member = iterator.next();
			if (memberId.equals(member.getId())) {
				return member;
			}
		}
		return null;
	}

	/**
	 * @return Iterator of a Member
	 */
	public Iterator<Member> getMembers(String memberName) {
		return new FilteredIterator<Member>(members.iterator(), member -> member.getName().equals(memberName));
	}

	/**
	 * Remove member from the List
	 * 
	 * @param memberId
	 * @return true if successful, else false
	 */
	public boolean removeMember(String memberId) {
		Member member = getMember(memberId);
		if (member == null) {
			return false;
		} else {
			return members.remove(member);
		}

	}

	/**
	 * Check if a member is a member on the list
	 * 
	 * @param memberId
	 * @return member, else false.
	 */
	public boolean isMember(String memberId) {
		Iterator<Member> iterator = members.listIterator();
		while (iterator.hasNext()) {
			Member member = iterator.next();
			if (memberId.equals(member.getId())) {
				return member.isMember();
			}
		}
		return false;
	}

	/**
	 * Searching the Members name
	 * 
	 * @param memberName
	 * @return
	 */
	public Iterator<Member> getMembersByName(String memberName) {
		return new FilteredIterator<Member>(members.iterator(), member -> member.getName().equals(memberName));
	}

	public Iterator<Member> getMembers() {
		return members.listIterator();
	}
}
