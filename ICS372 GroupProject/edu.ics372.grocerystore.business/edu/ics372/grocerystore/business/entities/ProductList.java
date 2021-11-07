package edu.ics372.grocerystore.business.entities;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ProductList implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<Product> products = new LinkedList<Product>();
	private static ProductList productList;

	/**
	 * Private constructor for singleton.
	 */
	private ProductList() {

	}

	/**
	 * Returns single object, if it has not been created already.
	 * 
	 * @return instance of a ProductList
	 */
	public static ProductList getInstance() {
		if (productList == null) {
			productList = new ProductList();
		}
		return productList;
	}

	/**
	 * Returns iterator for the ProductList.
	 * 
	 * @return
	 */
	public Iterator<Product> getIterator() {
		return products.iterator();
	}

	/**
	 * Add a product to ProductList
	 * 
	 * @param product
	 * @return
	 */
	public boolean insertProduct(Product product) {
		return products.add(product);
	}

	/**
	 * Not having two products have the same name
	 * 
	 * @param name
	 * @return
	 */
	public boolean nameAvailable(String name) {
		Iterator<Product> iterator = getInstance().getIterator();
		while (iterator.hasNext()) {
			Product product = iterator.next();
			if (product.getName().equals(name)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checking if the product exist from the ProductList
	 * 
	 * @param productId
	 * @return
	 */
	public boolean isProduct(String productId) {
		Iterator<Product> iterator = getInstance().getIterator();
		while (iterator.hasNext()) {
			Product product = iterator.next();
			if (product.getId().equals(productId)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checking if the product is in stock
	 * 
	 * @param productId
	 * @param stock
	 * @return
	 */
	public boolean hasStock(String productId, int stock) {
		Iterator<Product> iterator = getInstance().getIterator();
		while (iterator.hasNext()) {
			Product product = iterator.next();
			if (product.getId().equals(productId)) {
				return (product.getStock() >= stock);
			}
		}
		return false;
	}

	/**
	 * Search the Product ID on the ProductList
	 * 
	 * @param productId
	 * @return
	 */
	public Product getProductById(String productId) {
		Iterator<Product> iterator = getInstance().getIterator();
		while (iterator.hasNext()) {
			Product product = iterator.next();
			if (product.getId().equals(productId)) {
				return product;
			}
		}
		return null;
	}

	/**
	 * Search the Product Name on the ProductList
	 * 
	 * @param productName
	 * @return
	 */
	public Product getProductByName(String productName) {
		Iterator<Product> iterator = getInstance().getIterator();
		while (iterator.hasNext()) {
			Product product = iterator.next();
			if (product.getName().equals(productName)) {
				return product;
			}
		}
		return null;
	}
}