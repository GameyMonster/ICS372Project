package edu.ics372.grocerystore.business.collections;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.ics372.grocerystore.business.entities.Order;

public class OrderList {
	private List<Order> orders = new LinkedList<Order>();
	private static OrderList orderList;

	/**
	 * Private constructor for singleton.
	 */
	private OrderList() {

	}

	/**
	 * Returns single object, if it has not been created already.
	 * 
	 * @return
	 */
	public static OrderList getInstance() {
		if (orderList == null) {
			orderList = new OrderList();
		}
		return orderList;
	}

	/**
	 * Add order to list.
	 * 
	 * @param order
	 * @return
	 */
	public boolean addOrder(Order order) {
		orders.add(order);
		return true;
	}

	/**
	 * Removes order from the list, given product ID.
	 * 
	 * @param productID
	 * @return
	 */
	public boolean removeOrder(String productID) {
		Order newOrder = search(productID);
		if (newOrder == null) {
			return false;
		} else {
			return orders.remove(newOrder);
		}
	}

	/**
	 * Returns product object from its order given the product ID.
	 * 
	 * @param productID
	 * @return
	 */
	public Order search(String productID) {
		Order order = null;
		for (Iterator<Order> iterator = orders.iterator(); iterator.hasNext();) {
			order = (Order) iterator.next();
			if (order.getProductID().equals(productID)) {
				return order;
			}
		}
		return null;
	}

	/**
	 * Returns iterator for the OrderList.
	 * 
	 * @return
	 */
	public Iterator<Order> iterator() {
		return orders.iterator();
	}

	/**
	 * Returns string.
	 */
	public String toString() {
		return orders.toString();
	}
}