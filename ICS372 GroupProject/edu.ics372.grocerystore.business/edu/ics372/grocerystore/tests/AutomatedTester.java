package edu.ics372.grocerystore.tests;

/**
 * @author Joshua Vang, Donald Roden
 */
import edu.ics372.grocerystore.business.entities.Member;
import edu.ics372.grocerystore.business.entities.Product;
import edu.ics372.grocerystore.business.entities.ProductList;
import edu.ics372.grocerystore.business.facade.GroceryStore;
import edu.ics372.grocerystore.business.facade.Request;
import edu.ics372.grocerystore.business.facade.Result;

public class AutomatedTester {
	private final GroceryStore grocery = GroceryStore.instance();
	private final String[] names = { "n1", "n2", "n3", "n4", "n5", "n6", "n7" };
	private final String[] addresses = { "a1", "a2", "a3", "a4", "a5", "a6", "a7" };
	private final String[] phones = { "p1", "p2", "p3", "p4", "p5", "p6", "p7" };
	private final double[] fees = { 1.00, 2.20, 3.10, 4.56, 5.33, 6.99, 7.65 };
	private final Member[] members = new Member[7];
	private final String[] membersID = { "i1", "i2", "i3", "i4", "i5", "i6", "i7" };
	private final String[] productName = { "product1", "product2", "product3", "product4", "product5", "product6",
			"product7" };
	private final String[] productID = { "0", "1", "2", "3", "4", "5", "6" };
	private final String[] productPrice = { "2.50", "30.00", "16.10", "21.50", "16.99", "2.99", "19.99" };
	private final String[] newPrice = { "6.99", "2.00", "20.15", "12.00", "0.99", "5.13", "14.69" };
	private final String[] reOrderLevel = { "22", "16", "50", "2", "15", "25", "33" };

	/**
	 * Create members and assert that they were created correctly.
	 */
	public void testAddMember() {
		for (int count = 0; count < members.length; count++) {
			Request.instance().setMemberAddress(addresses[count]);
			Request.instance().setMemberName(names[count]);
			Request.instance().setMemberPhoneNumber(phones[count]);
			Request.instance().setMemberFeePaid(Double.toString(fees[count]));
			Result result = GroceryStore.instance().addMember(Request.instance());
			assert result.getResultCode() == Result.OPERATION_COMPLETED;
			assert result.getMemberName().equals(names[count]);
			assert result.getMemberPhoneNumber().equals(phones[count]);
			assert result.getMemberAddress().equals(addresses[count]);
			assert result.getMemberFeePaid().equals((Double.toString(fees[count])));
		}
	}

	/**
	 * Assert that remove member correctly removes members
	 */
	public void testRemoveMember() {
		for (int count = 0; count < members.length; count++) {
			Request.instance().setMemberID(membersID[count]);
			Result result = GroceryStore.instance().removeMember(Request.instance());
			assert result.getMemberName().equals(names[count]);
			assert result.getMemberAddress().equals(addresses[count]);
			assert result.getMemberPhoneNumber().equals(phones[count]);
			assert result.getMemberFeePaid().equals((Double.toString(fees[count])));
		}
	}

	/**
	 * Tests addProduct function.
	 */
	public void testAddProduct() {
		// addProducts();
		for (int count = 0; count < 3; count++) {
			Request.instance().setProductName(productName[count]);
			Request.instance().setProductReorderLevel(reOrderLevel[count]);
			Request.instance().setProductPrice(newPrice[count]);
			Result result = GroceryStore.instance().addProduct(Request.instance());

			assert result.getProductName().equals(productName[count]);
			assert result.getProductReorderLevel().equals(reOrderLevel[count]);
			assert result.getProductPrice().equals(productPrice[count]);
			assert result.getOrderQuantity().equals(Integer.toString(Integer.parseInt(reOrderLevel[count]) * 2));
		}
	}

	/**
	 * Tests shipments of the orders
	 */
	public void testShipment() {
		addProducts();
		for (int count = 0; count < 3; count++) {
			System.out.println("count: " + count);
			Request.instance().setProductID(productID[count]);
//Request.instance().setProductID("0");
			Request.instance().setProductStock(Integer.toString(Integer.parseInt(reOrderLevel[count]) * 2));
//public Product(String name, int reorderLevel, int stock, double price) {
//ProductList.getInstance().insertProduct(new Product(productName[count],Integer.valueOf(reOrderLevel[count]),0,Double.valueOf(productPrice[count])));
			Result result = GroceryStore.instance().processShipment(Request.instance());
			assert result.getProductName().equals(productName[count]);
			assert result.getProductReorderLevel().equals(reOrderLevel[count]);
			assert result.getProductPrice().equals(productPrice[count]);
			System.out.println("OK");
		}
	}

	public void addProducts() {
		for (int count = 0; count < 7; count++) {
			ProductList.getInstance().insertProduct(new Product(productName[count],
					Integer.valueOf(reOrderLevel[count]), 0, Double.valueOf(productPrice[count])));
		}
	}

	/**
	 * Test the ProductInfo
	 */
	public void testProductInfo() {
		for (int count = 0; count < 3; count++) {
			Request.instance().setProductName(productName[count]);
			Result result = GroceryStore.instance().getProductInfo(Request.instance());

			assert result.getProductName().equals(productName[count]);
			assert result.getProductReorderLevel().equals(reOrderLevel[count]);
			assert result.getProductPrice().equals(productPrice[count]);
			assert result.getProductID().equals(productID[count]);
		}

	}

	/**
	 * Invokes all the test methods.
	 */
	public void testAll() {
		testAddMember();
		// testRemoveMember();
		testAddProduct();
		testShipment();
		testProductInfo();

	}

	public static void main(String[] args) {
		new AutomatedTester().testAll();
	}
}