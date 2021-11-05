package org.oobook.grocerystore.business.facade;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

public class GroceryStore {
	private MemberList members = MemberList.getInstance();
	private OrderList orders = OrderList.getInstance();
	private ProductList products = ProductList.getInstance();
	private TransactionList transactions = TransactionList.getInstance();
	private List<Product> checkOutList = new LinkedList<Product>();

	private static GroceryStore groceryStore;

	private GroceryStore() {

	}

	public static GroceryStore instance() {
		if (groceryStore == null) {
			groceryStore = new GroceryStore();
		}

		return groceryStore;
	}

	public Result addMember(Request request) {
		Result result = new Result();

		// Attempt to add member to member list
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

	public Result removeMember(Request request) {
		Result result = new Result();
		Member member = null;

		// Attempt to remove member
		result.setMemberID(request.getMemberID());
		if (!members.isMember(request.getMemberID())) {
			result.setResultCode(Result.MEMBER_NOT_FOUND);
		} else if ((member = members.removeMember(request.getMemberID())) != null) {
			result.setResultCode(Result.OPERATION_COMPLETED);
			result.setMemberFields(member);
		} else {
			result.setResultCode(Result.OPERATION_FAILED);
		}

		return result;
	}

	public Iterator<Result> getMemberInfo(Request request) {
		List<Result> resultList = new LinkedList<Result>();

		// get list of all members with name entered in request
		Iterator<Member> memberIterator = members.getMembers();
		while (memberIterator.hasNext()) {
			Member member = memberIterator.next();
			if (member.getName().equals(request.getMemberName())) {
				Result result = new Result();
				result.setMemberFields(member);
				result.setResultCode(Result.OPERATION_COMPLETED);
				resultList.add(result);
			}
		}

		return resultList.iterator();
	}

	public Result addProduct(Request request) {

		Result result = new Result();
		result.setProductName(request.getProductName());
		Product product = new Product(request.getProductName(), Integer.parseInt(request.getProductReorderLevel()),
				Integer.parseInt(request.getProductReorderLevel()) * 2, Double.parseDouble(request.getProductPrice()));
		result.setProductFields(product);

		// attempt to add product
		// check if name is taken
		if (!products.nameAvailable(request.getProductName())) {
			result.setResultCode(Result.PRODUCT_NAME_INVALID);
		} else if (products.insertProduct(product)) {
			result.setResultCode(Result.OPERATION_COMPLETED);
		} else {
			result.setResultCode(Result.OPERATION_FAILED);
		}

		return result;
	}

	public Result createNewCheckout(Request request) {
		Result result = new Result();
		// reset checkout list
		checkOutList = new LinkedList<Product>();

		// verify member exists
		result.setMemberID(request.getMemberID());
		if (members.isMember(request.getMemberID())) {
			result.setResultCode(Result.OPERATION_COMPLETED);
		} else {
			result.setResultCode(Result.MEMBER_NOT_FOUND);
		}

		return result;
	}

	public Result addProductToCheckout(Request request) {
		Result result = new Result();
		// add a single product to groceryStores checkoutList object
		// check that product exists in productList AND contains enough stock
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
			// TODO
			// how to add checkout quantity?
			// create new product and copy fields?
			// make checkout list two dimensional?

			// I think this would work,i'm creating a new product based on the original
			// product.
			// with stock = checkout quantity.
			// then setId and lastly add the product to checkOutList
			product = new Product(product.getName(), product.getReorderLevel(),
					Integer.parseInt(result.getProductStock()), product.getPrice());
			product.setId(result.getProductID());
			checkOutList.add(product);
		}
		// this product's stock field will be reused as quantity to checkout
		// check that product exists in productList AND contains enough stock
		// set result code (PRODUCT_NOT_FOUND, PRODUCT_OUT_OF_STOCK,
		// OPERATION_COMPLETED)
		return result;
	}

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
							// change to product already ordered?
							// isnt technically a failure for the result because
							// the transaction is still created and added?
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
		// TODO
		// actor has finished adding products to checkoutList X
		// ensure list is not empty X
		// create transaction and add to transaction list X
		// check reorder level for each product checked out X
		// if product is reordered make sure to set result code for that product result
		// X
		// to PRODUCT_REORDERED X
		// else set result code based on success X
		return resultList.iterator();
	}

	public Result getProductInfo(Request request) {
		Result result = new Result();

		// search for product by name
		// if not found set resultCode to PRODUCT_NOT_FOUND
		// else set product fields in result and set result code to OPERATION_COMPLETED
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

		// TODO
		// check that product id exists
		// if nto set result code to PRODUCT_NOT_FOUND
		// check if exists in orderList
		// if not set result code to ORDER_NOT_FOUND
		// check that quantity received matches order quantity
		// if nto set result code to INCORRECT_RECIEVED_QUANTITY
		// update product stock in productList
		// set result code to OPERATION_COMPLETED
		// set all product fields in result
		// REMOVE FROM ORDER LIST

		return result;
	}

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
		// update price in productList
		// set result code to OPERATION_COMPLETED
		// set all product fields in result
		product.setPrice(Double.parseDouble(request.getProductPrice()));
		result.setResultCode(Result.OPERATION_COMPLETED);
		result.setProductFields(product);

		return result;
	}

	public Iterator<Result> printTransactions(Request request) {
		List<Result> resultList = new LinkedList<Result>();
		// TODO
		// verify member id, if not found create a single result and
		// set its result code to MEMBER_NOT_FOUND
		Member member = members.getMember(request.getMemberID());
		if (member == null) {
			Result result = new Result();
			result.setMemberID(request.getMemberID());
			result.setResultCode(Result.MEMBER_NOT_FOUND);
			resultList.add(result);
			return resultList.iterator();
		}
		// verify dates are valid options (start date occurs on or before end date)
		// if not, create single result and set result code to INVALID_DATES
		Calendar startDate = request.getStartDate();
		Calendar endDate = request.getEndDate();
		if (startDate.compareTo(endDate) > 0) {
			Result result = new Result();
			result.setMemberID(request.getMemberID());
			result.setResultCode(Result.INVALID_DATES);
			resultList.add(result);
			return resultList.iterator();
		}
		// else retrieve list of transactions, create a result for each transaction,
		// set all relevant fields, and set result code to OPERATION_COMPLETED
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

	public Iterator<Result> listAllMembers() {
		List<Result> resultList = new LinkedList<Result>();
		Iterator<Member> iterator = members.getMembers();
		while (iterator.hasNext()) {
			Member member = iterator.next();
			Result result = new Result();
			result.setMemberFields(member);
			resultList.add(result);
		}
		// create a list of results corresponding
		// to each entry in memberList

		return resultList.iterator();
	}

	public Iterator<Result> listAllProducts() {
		List<Result> resultList = new LinkedList<Result>();
		Iterator<Product> iterator = products.getIterator();

		// create a list of results corresponding to each entry in products
		while (iterator.hasNext()) {
			Product product = iterator.next();
			Result result = new Result();
			result.setProductFields(product);
			resultList.add(result);
		}

		return resultList.iterator();
	}

	public Iterator<Result> listOutstandingOrders() {
		List<Result> resultList = new LinkedList<Result>();
		Iterator<Order> iterator = orders.iterator();

		// create a list of results corresponding
		// to each entry in orders
		while (iterator.hasNext()) {
			Order order = iterator.next();
			Result result = new Result();
			result.setOrderFields(order);
			resultList.add(result);
		}

		return resultList.iterator();
	}

	public static boolean save() {
		try {
			FileOutputStream file = new FileOutputStream("GroceryStoreData");
			ObjectOutputStream output = new ObjectOutputStream(file);
			output.writeObject(groceryStore);
			// Member.save(output);
			// TODO
			// save anything else???
			output.close();
			file.close();
			return true;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return false;
		}
	}

	public static GroceryStore retrieve() {
		try {
			FileInputStream file = new FileInputStream("GroceryStoreData");
			ObjectInputStream input = new ObjectInputStream(file);
			groceryStore = (GroceryStore) input.readObject();
			// Member.retrieve(input);
			// TODO
			// retrieve anything that needs to be???
			input.close();
			file.close();
			return groceryStore;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
			return null;
		}
	}
}