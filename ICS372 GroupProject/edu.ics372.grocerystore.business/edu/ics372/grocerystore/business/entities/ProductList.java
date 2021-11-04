package edu.ics372.grocerystore.business.entities;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ProductList {
	private List<Product> products = new LinkedList<Product>();
	private static ProductList productList;

	private ProductList() {

	}

	public static ProductList getInstance() {
		if (productList == null) {
			productList = new ProductList();
		}
		return productList;
	}

	public Iterator<Product> getIterator() {
		return products.iterator();
	}

	public boolean insertProduct(Product product) {
		return products.add(product);
	}

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