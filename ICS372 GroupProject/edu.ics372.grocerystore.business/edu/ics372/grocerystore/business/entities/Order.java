package edu.ics372.grocerystore.business.entities;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Order implements Serializable {
	private static final long serialVersionUID = 1L;
	private String orderID;
	private String productID;
	private Product product;
	private int quantity;
	private static int idCounter = 0;

	/**
	 * Constructs an order
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

	public static void save(ObjectOutputStream output) throws IOException {
		output.writeObject(idCounter);
	}

	public static void retrieve(ObjectInputStream input) throws IOException, ClassNotFoundException {
		idCounter = (int) input.readObject();
	}

	public String toString() {
		return "Order: " + orderID + ", product: " + productID + ", " + product.getName() + ", quantity: " + quantity;
	}

}