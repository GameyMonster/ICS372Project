package edu.ics372.grocerystore.business.collections;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.ics372.grocerystore.business.entities.Member;

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
	 * adding member to the list
	 * 
	 * @param member
	 * @return true or false.
	 */
	public boolean insertMember(Member member) {
		return members.add(member);
	}

	/**
	 * Get Members information of a member given their ID
	 * 
	 * @param memberId
	 * @return member, null if there are no member found
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
	public Iterator<Member> getMembers() {
		return members.listIterator();
	}

	/**
	 * Remove member from the List
	 * 
	 * @param memberId
	 * @return true if successful, else false
	 */
	public Member removeMember(String memberId) {
		Iterator<Member> iterator = members.listIterator();
		Member member = null;
		while (iterator.hasNext()) {
			member = iterator.next();
			if (memberId.equals(member.getId())) {
				iterator.remove();
				return member;
			}
		}
		return member;
	}

	/**
	 * Check if a member is a member on the list
	 * 
	 * @param memberId
	 * @return true if is a member, false if not.
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
}
