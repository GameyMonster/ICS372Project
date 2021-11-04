package org.oobook.grocerystore.business.entities;

public class Order {
	private String orderID;
	private String productID;
	private Product product;
	private int quantity;
	private static int idCounter = 0;

	/**
	 * Constructs order, creates unique ID.
	 * 
	 * @param productID
	 * @param quantity
	 * @param product
	 */
	public Order(Product product, int quantity) {
		this.productID = product.getId();
		this.quantity = quantity;
		this.orderID = String.valueOf(idCounter);
		this.product = product;
		idCounter++;
	}

	/**
	 * @return Product in order.
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * Sets product for order.
	 * 
	 * @param product
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * @return quantity of product.
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Sets quantity in order.
	 * 
	 * @param quantity
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return Order ID.
	 */
	public String getOrderID() {
		return orderID;
	}

	/**
	 * @return Product ID.
	 */
	public String getProductID() {
		return productID;
	}

	/**
	 * Sets product ID.
	 * 
	 * @param productID
	 */
	public void setProductID(String productID) {
		this.productID = productID;
	}

	/**
	 * Sets order ID.
	 * 
	 * @param orderID
	 */
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	/**
	 * Constructs string for an order.
	 */
	public String toString() {
		return "Order: " + orderID + ", product: " + productID + ", " + product.getName() + ", quantity: " + quantity;
	}

}