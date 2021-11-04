package org.oobook.grocerystore.business.collections;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.oobook.grocerystore.business.entities.Product;

public class Inventory implements Iterable<Product>, Serializable {
	private static final long serialVersionUID = 1L;
	private static Inventory inventory;
	private List<Product> products = new LinkedList<Product>();

	private Inventory() {
	}

	public static Inventory instance() {
		if (inventory == null) {
			return inventory = new Inventory();
		} else {
			return inventory;
		}
	}

	/**
	 * Checks whether a product with a given product id exists.
	 * 
	 * @param productId the id of the book
	 * @return true if the product exists
	 * 
	 */
	public Product search(String productId) {
		for (Iterator<Product> iterator = products.iterator(); iterator.hasNext();) {
			Product product = (Product) iterator.next();
			if (product.getId().equals(productId)) {
				return product;
			}
		}
		return null;
	}

	/**
	 * Inserts a product into the collection
	 * 
	 * @param product the product to be inserted
	 * @return true if the product could be inserted. Currently always true
	 */
	public boolean insertProduct(Product product) {
		products.add(product);
		return true;
	}

	/**
	 * Returns an iterator to all products
	 * 
	 * @return iterator to the collection
	 */
	@Override
	public Iterator<Product> iterator() {
		return products.iterator();
	}

	/**
	 * String form of the collection
	 * 
	 */
	public String toString() {
		return products.toString();
	}
}
