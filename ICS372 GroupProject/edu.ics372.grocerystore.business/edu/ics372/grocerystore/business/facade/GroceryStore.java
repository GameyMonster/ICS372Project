package edu.ics372.grocerystore.business.facade;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.ics372.grocerystore.business.collections.MemberList;
import edu.ics372.grocerystore.business.collections.OrderList;
import edu.ics372.grocerystore.business.collections.TransactionList;
import edu.ics372.grocerystore.business.entities.Member;
import edu.ics372.grocerystore.business.entities.Order;
import edu.ics372.grocerystore.business.entities.Product;
import edu.ics372.grocerystore.business.entities.ProductList;
import edu.ics372.grocerystore.business.entities.Transaction;
import edu.ics372.grocerystore.business.iterators.SafeMemberIterator;
import edu.ics372.grocerystore.business.iterators.SafeOrderIterator;
import edu.ics372.grocerystore.business.iterators.SafeProductIterator;

public class GroceryStore implements Serializable {
	private static final long serialVersionUID = 1L;
	private MemberList members = MemberList.getInstance();
	private OrderList orders = OrderList.getInstance();
	private ProductList products = ProductList.getInstance();
	private TransactionList transactions = TransactionList.getInstance();
	private List<Product> checkOutList = new LinkedList<Product>();
	private static GroceryStore groceryStore;

	/**
	 * Private for the singleton pattern Creates the catalog and member collection
	 * objects
	 */
	private GroceryStore() {

	}

	/**
	 * Supports the singleton pattern
	 * 
	 * @return the singleton object
	 */
	public static GroceryStore instance() {
		if (groceryStore == null) {
			groceryStore = new GroceryStore();
		}

		return groceryStore;
	}

	/**
	 * Organizes the operations for adding a member
	 * 
	 * @param name    member name
	 * @param address member address
	 * @param phone   member phone
	 * @param fee     member fee
	 * @return the Member object created
	 */
	public Result addMember(Request request) {
		Result result = new Result();
		Member member = new Member(request.getMemberName(), request.getMemberAddress(), request.getMemberPhoneNumber(),
				Double.parseDouble(request.getMemberFeePaid()));
		if (members.insertMember(member)) {
			result.setResultCode(Result.OPERATION_COMPLETED);
		} else {
			result.setResultCode(Result.OPERATION_FAILED);
		}

		result.setMemberFields(member);

		return result;
	}

	/**
	 * Organizes the operations for remove a member
	 * 
	 * @param name    member name
	 * @param address member address
	 * @param phone   member phone
	 * @param fee     member fee
	 * @return the Member object created
	 */
	public Result removeMember(Request request) {
		Result result = new Result();
		Member member = members.getMember(request.getMemberID());
		if (member == null) {
			result.setResultCode(Result.MEMBER_NOT_FOUND);
			return result;
		}

		if (members.removeMember(request.getMemberID())) {
			result.setMemberFields(member);
			result.setResultCode(Result.OPERATION_COMPLETED);
			return result;
		}

		result.setResultCode(Result.OPERATION_FAILED);
		return result;
	}

	/**
	 * Returns an iterator to the return a member info
	 * 
	 * @param request - stores the member id
	 * @return iterator to the Result objects storing info about issued books
	 */
	public Iterator<Result> getMemberInfo(Request request) {
		return new SafeMemberIterator(members.getMembersByName(request.getMemberName()));
	}

	/**
	 * Adding the Product to the system
	 * 
	 * @param request
	 * @return
	 */
	public Result addProduct(Request request) {

		Result result = new Result();
		Product product = products.getProductByName(request.getProductName());
		result.setProductID(request.getProductName());
		result.setProductStock(request.getProductStock());
		if (!products.isProduct(request.getProductID())) {
			result.setResultCode(Result.PRODUCT_NOT_FOUND);
		} else if (!products.hasStock(request.getProductID(), Integer.parseInt(request.getProductStock()))) {
			result.setResultCode(Result.PRODUCT_OUT_OF_STOCK);
		} else {
			result.setProductFields(product);
			result.setProductStock(request.getProductStock());
			result.setResultCode(Result.OPERATION_COMPLETED);
			product = new Product(product.getName(), product.getReorderLevel(),
					Integer.parseInt(result.getProductStock()), product.getPrice());
			product.setId(result.getProductID());
			checkOutList.add(product);
		}
		return result;
	}

	/**
	 * Create a checkout for the member
	 * 
	 * @param request
	 * @return
	 */
	public Result createNewCheckout(Request request) {
		Result result = new Result();
		checkOutList = new LinkedList<Product>();
		result.setMemberID(request.getMemberID());
		if (members.isMember(request.getMemberID())) {
			result.setResultCode(Result.OPERATION_COMPLETED);
		} else {
			result.setResultCode(Result.MEMBER_NOT_FOUND);
		}

		return result;
	}

	/**
	 * Adding the Product To The Checkout
	 * 
	 * @param request
	 * @return
	 */
	public Result addProductToCheckout(Request request) {
		Result result = new Result();
		Product product = products.getProductById(request.getProductID());
		result.setProductID(request.getProductID());
		result.setProductStock(request.getProductStock());
		if (!products.isProduct(request.getProductID())) {
			result.setResultCode(Result.PRODUCT_NOT_FOUND);
		} else if (!products.hasStock(request.getProductID(), Integer.parseInt(request.getProductStock()))) {
			result.setResultCode(Result.PRODUCT_OUT_OF_STOCK);
		} else {
			result.setProductFields(product);
			result.setProductStock(request.getProductStock());
			result.setResultCode(Result.OPERATION_COMPLETED);
			product = new Product(product.getName(), product.getReorderLevel(),
					Integer.parseInt(result.getProductStock()), product.getPrice());
			product.setId(result.getProductID());
			checkOutList.add(product);
		}
		return result;
	}

	/**
	 * Creating the list of the product that has been purchased
	 * 
	 * @param request
	 * @return
	 */
	public Iterator<Result> completeCheckout(Request request) {
		List<Result> resultList = new LinkedList<Result>();
		double total = 0;
		if (!checkOutList.isEmpty()) {
			Iterator<Product> iterator = checkOutList.listIterator();
			while (iterator.hasNext()) {
				Result result = new Result();
				Product checkOutProduct = iterator.next();
				Product product = products.getProductById(checkOutProduct.getId());
				product.setStock(product.getStock() - checkOutProduct.getStock());
				if (product.getStock() <= product.getReorderLevel()) {
					if (orders.search(product.getId()) == null) {
						if (orders.addOrder(new Order(product, product.getReorderLevel() * 2))) {
							result.setResultCode(Result.PRODUCT_REORDERED);
						} else {
							result.setResultCode(Result.PRODUCT_ALREADY_ORDERED);
						}
					}
				}
				total += checkOutProduct.getStock() * checkOutProduct.getPrice();
				result.setProductFields(checkOutProduct);
				result.setMemberID(request.getMemberID());
				resultList.add(result);
			}
			Transaction transaction = new Transaction(request.getMemberID(), total);
			transactions.insertTransaction(transaction);
			resultList.get(resultList.size() - 1).setTransactionFields(transaction);
		}
		return resultList.iterator();
	}

	/**
	 * Get the Product Information on the system
	 * 
	 * @param request
	 * @return
	 */
	public Result getProductInfo(Request request) {
		Result result = new Result();

		Product product = products.getProductByName(request.getProductName());
		if (product == null) {
			result.setResultCode(Result.PRODUCT_NOT_FOUND);
			result.setProductName(request.getProductName());
		} else {
			result.setResultCode(Result.OPERATION_COMPLETED);
			result.setProductFields(product);
		}
		return result;
	}

	/**
	 * Create a shipment for the product has no more quantity recently
	 * 
	 * @param request
	 * @return
	 */
	public Result processShipment(Request request) {
		Result result = new Result();
		Product product = products.getProductById(request.getProductID());
		Order order = orders.search(product.getId());
		int quantity = Integer.parseInt(request.getProductStock());
		if (product.equals(null)) {
			result.setResultCode(Result.PRODUCT_NOT_FOUND);
			return result;
		} else if (order == null) {
			result.setResultCode(Result.ORDER_NOT_FOUND);
			return result;
		} else if (quantity != order.getQuantity()) {
			result.setResultCode(Result.INCORRECT_RECEIVED_QUANTITY);
			return result;
		}

		product.addStock(order.getQuantity());
		result.setResultCode(Result.OPERATION_COMPLETED);
		orders.removeOrder(product.getId());
		result.setProductFields(product);
		return result;
	}

	/**
	 * Changing the price on the system
	 * 
	 * @param request
	 * @return
	 */
	public Result changePrice(Request request) {
		Result result = new Result();
		result.setProductID(request.getProductID());

		// check that product id exists
		Product product = products.getProductById(request.getProductID());
		// if not set result code to PRODUCT_NOT_FOUND
		if (product == null) {
			result.setResultCode(Result.PRODUCT_NOT_FOUND);
			return result;
		}
		product.setPrice(Double.parseDouble(request.getProductPrice()));
		result.setResultCode(Result.OPERATION_COMPLETED);
		result.setProductFields(product);

		return result;
	}

	/**
	 * Print all the transaction of the members
	 * 
	 * @param request
	 * @return
	 */
	public Iterator<Result> printTransactions(Request request) {
		List<Result> resultList = new LinkedList<Result>();

		Member member = members.getMember(request.getMemberID());
		if (member == null) {
			Result result = new Result();
			result.setMemberID(request.getMemberID());
			result.setResultCode(Result.MEMBER_NOT_FOUND);
			resultList.add(result);
			return resultList.iterator();
		}
		// Get the date from the purchased items
		Calendar startDate = request.getStartDate();
		Calendar endDate = request.getEndDate();
		if (startDate.compareTo(endDate) > 0) {
			Result result = new Result();
			result.setMemberID(request.getMemberID());
			result.setResultCode(Result.INVALID_DATES);
			resultList.add(result);
			return resultList.iterator();
		}
		// Retrieve all the transaction from the member
		Iterator<Transaction> transactionIterator = transactions.getTransactions(request.getMemberID(), startDate,
				endDate);
		while (transactionIterator.hasNext()) {
			Transaction transaction = transactionIterator.next();
			Result result = new Result();
			result.setMemberID(request.getMemberID());
			result.setResultCode(Result.OPERATION_COMPLETED);
			result.setTransactionFields(transaction);
			resultList.add(result);
		}
		return resultList.iterator();
	}

	/**
	 * Print all the members on the system
	 * 
	 * @return
	 */
	public Iterator<Result> listAllMembers() {
		return new SafeMemberIterator(members.getMembers());

	}

	/**
	 * Print all of the Products on the system
	 * 
	 * @return
	 */
	public Iterator<Result> listAllProducts() {
		return new SafeProductIterator(products.getIterator());
	}

	/**
	 * Print all of the Product that hasn't purchased
	 * 
	 * @return
	 */
	public Iterator<Result> listOutstandingOrders() {
		return new SafeOrderIterator(orders.iterator());
	}

	public static boolean save() {
		try {
			FileOutputStream file = new FileOutputStream("GroceryStoreData");
			ObjectOutputStream output = new ObjectOutputStream(file);
			output.writeObject(groceryStore);
			output.close();
			file.close();
			return true;
		} catch (IOException expections) {
			expections.printStackTrace();
			return false;
		}
	}

	/**
	 * Retrieve the save system file
	 * 
	 * @return
	 */
	public static GroceryStore retrieve() {
		try {
			FileInputStream file = new FileInputStream("GroceryStoreData");
			ObjectInputStream input = new ObjectInputStream(file);
			groceryStore = (GroceryStore) input.readObject();
			input.close();
			file.close();
			return groceryStore;
		} catch (IOException expections) {
			expections.printStackTrace();
			return null;
		} catch (ClassNotFoundException expections) {
			expections.printStackTrace();
			return null;
		}
	}
}